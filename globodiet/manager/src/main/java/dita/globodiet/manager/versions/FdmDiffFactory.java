/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.globodiet.manager.versions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.jspecify.annotations.Nullable;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.types.Diff;
import dita.commons.types.Pair;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredientResolved;

public record FdmDiffFactory() {

	public record FdmDiff(
			FoodDescriptionModel mainFdm,
			FoodDescriptionModel baseFdm,
			Diff<FoodDescriptionModel.Food, FoodDescriptionModel.Food> foodDiff,
			Diff<FoodDescriptionModel.Recipe, FoodDescriptionModel.Recipe> recipeDiff,
			Map<SemanticIdentifier, Diff<FoodDescriptionModel.RecipeIngredientResolved, FoodDescriptionModel.RecipeIngredientResolved>> ingredientDiffByRecipeSid) {

		record RecipeChange(
				SemanticIdentifier recipeSid,
				Optional<Pair<String, String>> nameChange,
				Optional<Pair<SemanticIdentifier, SemanticIdentifier>> groupChange,
				List<RecipeIngredientResolved> ingredientsAdded,
				List<RecipeIngredientResolved> ingredientsRemoved,
				List<Pair<RecipeIngredientResolved, RecipeIngredientResolved>> ingredientsChanged) {

			Optional<RecipeIngredientResolved> lookupAdditions(final RecipeIngredientResolved.Key key) {
				return ingredientsAdded.stream()
						.filter(it->it.key().equals(key))
						.findFirst();
			}
			Optional<RecipeIngredientResolved> lookupDeletions(final RecipeIngredientResolved.Key key) {
				return ingredientsRemoved.stream()
						.filter(it->it.key().equals(key))
						.findFirst();
			}
			Optional<Pair<RecipeIngredientResolved, RecipeIngredientResolved>> lookupChanges(final RecipeIngredientResolved.Key key) {
				return ingredientsChanged.stream()
						.filter(it->it.left().key().equals(key)) // left and right key must be that same
						.findFirst();
			}
	    }

	    Optional<RecipeChange> recipeChangeFor(final SemanticIdentifier recipeSid) {
	        final var main = mainFdm().lookupRecipeBySid(recipeSid).orElse(null);
	        final var base = baseFdm().lookupRecipeBySid(recipeSid).orElse(null);

	        if (main == null || base == null)
				return Optional.empty();

	        final boolean sameName = Objects.equals(main.name(), base.name());
	        final boolean sameGroup = Objects.equals(main.groupSid(), base.groupSid());
			final var ingredientDiff = ingredientDiffByRecipeSid()
					.getOrDefault(recipeSid, Diff.empty());

			return sameName
					&& sameGroup
					&& ingredientDiff.isAllSame()
				? Optional.empty()
				: Optional.of(new RecipeChange(
					recipeSid,
	        		sameName
	        			? Optional.empty()
        				: Optional.of(Pair.of(main.name(), base.name())),
    				sameGroup
	        			? Optional.empty()
        				: Optional.of(Pair.of(main.groupSid(), base.groupSid())),
    				ingredientDiff.leftOuter(),
    				ingredientDiff.rightOuter(),
    				ingredientDiff.innerMismatch()
				));
	    }

		public @Nullable String toYaml() {
			return new FdmDiffYamlWriter(this).toYaml();
		}
	}

	public FdmDiff diff(final FoodDescriptionModel main, final FoodDescriptionModel base) {
		var fdmDiff = new FdmDiff(
				main,
				base,
				Diff.typed(FoodDescriptionModel.Food.class, FoodDescriptionModel.Food.class),
				Diff.typed(FoodDescriptionModel.Recipe.class, FoodDescriptionModel.Recipe.class),
				new HashMap<>());

		fdmDiff.foodDiff().process(
				main.foodBySid().values(), base.foodBySid().values(),
				food->food.sid().toStringNoBox(), food->food.sid().toStringNoBox(),
				FoodDescriptionModel.Food::equals);

		fdmDiff.recipeDiff().process(
				main.recipeBySid().values(), base.recipeBySid().values(),
				recipe->recipe.sid().toStringNoBox(), recipe->recipe.sid().toStringNoBox(),
				(a, b) -> a.groupSid().equals(b.groupSid())
					&& a.name().equals(b.name()));

		join(main.recipeBySid().keySet(), base.recipeBySid().keySet())
			.forEach(recipeId->{
				var ingrDiff = Diff.typed(FoodDescriptionModel.RecipeIngredientResolved.class, FoodDescriptionModel.RecipeIngredientResolved.class);
				fdmDiff.ingredientDiffByRecipeSid().put(recipeId, ingrDiff);

				var leftIngredients = main.ingredientsByRecipeSid().getOrDefault(recipeId, List.of());
				var rightIngredients = base.ingredientsByRecipeSid().getOrDefault(recipeId, List.of());
				ingrDiff.process(
						leftIngredients, rightIngredients,
						RecipeIngredientResolved::key, RecipeIngredientResolved::key,
						(a, b) -> Objects.equals(a.key(), b.key())
				        	&& Math.abs(a.relativeMassPermille()-b.relativeMassPermille())<=2 // account for rounding errors +/- 2 permille
				            && Objects.equals(a.rawToCookedCoefficient(), b.rawToCookedCoefficient()));
			});

		return fdmDiff;
	}

	<T> Set<T> join(final Set<T> a, final Set<T> b) {
		var joined = new HashSet<>(a);
		joined.addAll(b);
		return joined;
	}
}
