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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Diff;
import dita.commons.types.Pair;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredientResolved;

public record FdmDiffFactory() {

	public FdmDiff diff(final FoodDescriptionModel main, final FoodDescriptionModel base) {
		var diff = diffRaw(main, base);
		var ctx = new Context(diff.mainFdm()::facetSetLiteral);
		var model = new FdmDiff(
				diff.foodDiff().leftOuter(),
				diff.foodDiff().rightOuter(),
				diff.foodDiff().innerMismatch()
					.stream()
					.map(FoodChange::of)
					.toList(),

				diff.recipeDiff().leftOuter(),
				diff.recipeDiff().rightOuter(),
				Stream.concat(
					diff.recipeDiff().innerMismatch()
						.stream()
						.map(pair->RecipeChange.of(
								ctx, pair, diff.ingredientDiffByRecipeSid().getOrDefault(pair.left().sid(), Diff.empty()))),
					diff.recipeDiff().innerMatch()
						.stream()
						.map(Pair::left)
						.map((final FoodDescriptionModel.Recipe mainRecp)->RecipeChange.of(
								ctx, mainRecp, diff.ingredientDiffByRecipeSid().getOrDefault(mainRecp.sid(), Diff.empty())))
				)
				.filter(Objects::nonNull)
				.toList());
		return model;
	}

	public record FdmDiff(
			List<FoodDescriptionModel.Food> foodAdded,
			List<FoodDescriptionModel.Food> foodRemoved,
			List<FoodChange> foodChanged,
			List<FoodDescriptionModel.Recipe> recipesAdded,
			List<FoodDescriptionModel.Recipe> recipesRemoved,
			List<RecipeChange> recipesChanged) {
		public String toYaml() {
			return new FdmDiffYamlWriter(this).toYaml();
		}
	}
	public record FoodChange(
			SemanticIdentifier foodSid,
			String foodName,
			Pair<String, String> nameChange,
			Pair<SemanticIdentifier, SemanticIdentifier> groupChange) {
		static FoodChange of(final Pair<FoodDescriptionModel.Food, FoodDescriptionModel.Food> pair) {
			var main = pair.left();
			var base = pair.right();
			var nameChange = Pair.of(main.name(), base.name());
			var groupChange = Pair.of(main.groupSid(), base.groupSid());
			return new FoodChange(
					main.sid(),
					nameChange.equal() ? main.name() : null,
					nameChange.equal() ? null : nameChange,
					groupChange.equal() ? null : groupChange);
		}
	}
	public record RecipeChange(
			SemanticIdentifier recipeSid,
			String recipeName,
			Pair<String, String> nameChange,
			Pair<SemanticIdentifier, SemanticIdentifier> groupChange,
			List<IngredientAdded> ingredientsAdded,
			List<IngredientRemoved> ingredientsRemoved,
			List<IngredientChanged> ingredientsChanged) {
		static RecipeChange of(
				final Context ctx,
				final FoodDescriptionModel.Recipe recipe,
				final Diff<FoodDescriptionModel.RecipeIngredientResolved, FoodDescriptionModel.RecipeIngredientResolved> ingrDiff) {
			if(ingrDiff.isAllSame())
				return null;
			return new RecipeChange(
					recipe.sid(),
					recipe.name(),
					null,
					null,
					ingrDiff.leftOuter().stream()
						.map(it->IngredientAdded.of(ctx, it))
						.toList(),
					ingrDiff.rightOuter().stream()
						.map(it->IngredientRemoved.of(ctx, it))
						.toList(),
					ingrDiff.innerMismatch().stream()
						.map(it->IngredientChanged.of(ctx, it))
						.toList());
		}
		static RecipeChange of(
				final Context ctx,
				final Pair<FoodDescriptionModel.Recipe, FoodDescriptionModel.Recipe> pair,
				final Diff<FoodDescriptionModel.RecipeIngredientResolved, FoodDescriptionModel.RecipeIngredientResolved> ingrDiff) {
			var main = pair.left();
			var base = pair.right();
			var nameChange = Pair.of(main.name(), base.name());
			var groupChange = Pair.of(main.groupSid(), base.groupSid());
			return new RecipeChange(
					main.sid(),
					nameChange.equal() ? main.name() : null,
					nameChange.equal() ? null : nameChange,
					groupChange.equal() ? null : groupChange,
					ingrDiff.leftOuter().stream()
						.map(it->IngredientAdded.of(ctx, it))
						.toList(),
					ingrDiff.rightOuter().stream()
						.map(it->IngredientRemoved.of(ctx, it))
						.toList(),
					ingrDiff.innerMismatch().stream()
						.map(it->IngredientChanged.of(ctx, it))
						.toList());
		}
	}
	public record IngredientAdded(
			@JsonIgnore RecipeIngredientResolved.Key key,
			SemanticIdentifier foodSid,
			String name,
			SemanticIdentifierSet foodFacetSids,
			String foodFacets,
			BigDecimal amountGrams,
			int relativeMassPermille) {
		static IngredientAdded of(
				final Context ctx,
				final FoodDescriptionModel.RecipeIngredientResolved ingr) {
			return new IngredientAdded(
					new RecipeIngredientResolved.Key(ingr.recipeSid(), ingr.foodSid(), ingr.foodFacetSids().hashCode()),
					ingr.foodSid(),
					ingr.food().name(),
					ingr.foodFacetSids(),
					ctx.facetLiteralProvider.apply(ingr.foodFacetSids()),
					ingr.amountGrams(),
					ingr.relativeMassPermille());
		}
	}
	public record IngredientRemoved(
			@JsonIgnore RecipeIngredientResolved.Key key,
			SemanticIdentifier foodSid,
			String name,
			SemanticIdentifierSet foodFacetSids,
			String foodFacets) {
		static IngredientRemoved of(
				final Context ctx,
				final FoodDescriptionModel.RecipeIngredientResolved ingr) {
			return new IngredientRemoved(
					new RecipeIngredientResolved.Key(ingr.recipeSid(), ingr.foodSid(), ingr.foodFacetSids().hashCode()),
					ingr.foodSid(),
					ingr.food().name(),
					ingr.foodFacetSids(),
					ctx.facetLiteralProvider.apply(ingr.foodFacetSids()));
		}
	}
	public record IngredientChanged(
			@JsonIgnore RecipeIngredientResolved.Key key,
			SemanticIdentifier foodSid,
			String name,
			SemanticIdentifierSet foodFacetSids,
			String foodFacets,
			Pair<BigDecimal, BigDecimal> amountChange,
			Pair<Integer, Integer> amountChangePpm,
			Pair<BigDecimal, BigDecimal> rawToCookedChange) {
		static IngredientChanged of(
				final Context ctx,
				final Pair<FoodDescriptionModel.RecipeIngredientResolved, FoodDescriptionModel.RecipeIngredientResolved> pair) {
			var main = pair.left();
			var base = pair.right();
			var amountChange = Pair.of(main.amountGrams(), base.amountGrams());
			var amountChangePpm = Pair.of(main.relativeMassPermille(), base.relativeMassPermille());
			var rawToCookedChange = Pair.of(main.rawToCookedCoefficient(), base.rawToCookedCoefficient());
			return new IngredientChanged(
					new RecipeIngredientResolved.Key(main.recipeSid(), main.foodSid(), main.foodFacetSids().hashCode()),
					main.foodSid(),
					main.food().name(),
					main.foodFacetSids(),
					ctx.facetLiteralProvider.apply(main.foodFacetSids()),
					amountChange.equal() ? null : amountChange,
					amountChangePpm.equal() ? null : amountChangePpm,
					rawToCookedChange.equal() ? null : rawToCookedChange);
		}
	}

	// ------

	private record FdmDiffRaw(
			FoodDescriptionModel mainFdm,
			FoodDescriptionModel baseFdm,
			Diff<FoodDescriptionModel.Food, FoodDescriptionModel.Food> foodDiff,
			Diff<FoodDescriptionModel.Recipe, FoodDescriptionModel.Recipe> recipeDiff,
			Map<SemanticIdentifier, Diff<FoodDescriptionModel.RecipeIngredientResolved, FoodDescriptionModel.RecipeIngredientResolved>> ingredientDiffByRecipeSid) {
	}

	private FdmDiffRaw diffRaw(final FoodDescriptionModel main, final FoodDescriptionModel base) {
		var fdmDiff = new FdmDiffRaw(
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

	record Context(Function<SemanticIdentifierSet, String> facetLiteralProvider) {
	}

}
