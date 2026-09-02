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
import java.util.List;
import java.util.function.Function;

import org.apache.causeway.commons.io.JsonUtils.JacksonCustomizer;
import org.apache.causeway.commons.io.YamlUtils;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Diff;
import dita.commons.types.Pair;
import dita.commons.util.FormatUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.manager.versions.FdmDiffFactory.FdmDiff;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

record FdmDiffYamlWriter(FdmDiff fdmDiff) {
	String toYaml() {
		var ctx = new Context(fdmDiff.mainFdm()::facetSetLiteral);
		var model = new Model(
				fdmDiff.foodDiff().leftOuter(),
				fdmDiff.foodDiff().rightOuter(),
				fdmDiff.foodDiff().innerMismatch()
					.stream()
					.map(FoodChange::of)
					.toList(),

				fdmDiff.recipeDiff().leftOuter(),
				fdmDiff.recipeDiff().rightOuter(),
				fdmDiff.recipeDiff().innerMismatch()
					.stream()
					.map(pair->RecipeChange.of(ctx, pair, fdmDiff.ingredientDiffByRecipeSid().getOrDefault(pair.left().sid(), Diff.empty())))
					.toList());

		return YamlUtils.toStringUtf8(model, FormatUtils.yamlOptions(), pairAdapter());
	}

	record Context(Function<SemanticIdentifierSet, String> facetLiteralProvider) {
	}
	record Model(
			List<FoodDescriptionModel.Food> foodAdded,
			List<FoodDescriptionModel.Food> foodRemoved,
			List<FoodChange> foodChanged,
			List<FoodDescriptionModel.Recipe> recipeAdded,
			List<FoodDescriptionModel.Recipe> recipeRemoved,
			List<RecipeChange> recipChanged) {
	}
	record FoodChange(
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
	record RecipeChange(
			SemanticIdentifier recipeSid,
			String recipeName,
			Pair<String, String> nameChange,
			Pair<SemanticIdentifier, SemanticIdentifier> groupChange,
			List<IngredientAdded> ingredientAdded,
			List<IngredientRemoved> ingredientRemoved,
			List<IngredientChanged> ingredientChanged) {
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
	record IngredientAdded(
			String name,
			SemanticIdentifierSet foodFacetSids,
			String foodFacets,
			BigDecimal amountGrams) {
		static IngredientAdded of(
				final Context ctx,
				final FoodDescriptionModel.RecipeIngredientResolved ingr) {
			return new IngredientAdded(ingr.food().name(),
					ingr.foodFacetSids(),
					ctx.facetLiteralProvider.apply(ingr.foodFacetSids()),
					ingr.amountGrams());
		}
	}
	record IngredientRemoved(
			String name,
			SemanticIdentifierSet foodFacetSids,
			String foodFacets) {
		static IngredientRemoved of(
				final Context ctx,
				final FoodDescriptionModel.RecipeIngredientResolved ingr) {
			return new IngredientRemoved(ingr.food().name(),
					ingr.foodFacetSids(),
					ctx.facetLiteralProvider.apply(ingr.foodFacetSids()));
		}
	}
	record IngredientChanged(
			String name,
			SemanticIdentifierSet foodFacetSids,
			String foodFacets,
			Pair<BigDecimal, BigDecimal> amountChange,
			Pair<BigDecimal, BigDecimal> rawToCookedChange) {
		static IngredientChanged of(
				final Context ctx,
				final Pair<FoodDescriptionModel.RecipeIngredientResolved, FoodDescriptionModel.RecipeIngredientResolved> pair) {
			var main = pair.left();
			var base = pair.right();
			var amountChange = Pair.of(main.amountGrams(), base.amountGrams());
			var rawToCookedChange = Pair.of(main.rawToCookedCoefficient(), base.rawToCookedCoefficient());
			return new IngredientChanged(main.food().name(),
					main.foodFacetSids(),
					ctx.facetLiteralProvider.apply(main.foodFacetSids()),
					amountChange.equal() ? null : amountChange,
					rawToCookedChange.equal() ? null : rawToCookedChange);
		}
	}

	static class PairSerializer extends StdSerializer<Pair<?, ?>> {
		protected PairSerializer() {
			super(Pair.class);
		}
		@Override
		public void serialize(final Pair<?, ?> value, final JsonGenerator gen, final SerializationContext ctxt) throws JacksonException {
			gen.writeStartObject();
			gen.writePOJOProperty("old", value.right());
			gen.writePOJOProperty("new", value.left());
			gen.writeEndObject();
		}
    }

    static JacksonCustomizer pairAdapter() {
        return builder->
            builder.addModule(new SimpleModule()
                    .addSerializer(new PairSerializer()));
    }

//	String toYaml() {
//		emitFoodDiff();
//		emitRecipeDiff();
//		return writer.toString();
//	}
//	private void emitFoodDiff() {
//		if(fdmDiff.foodDiff.leftOuter().isEmpty()) {
//			writer.write("foodAdded: []").nl();
//		} else {
//			writer.write("foodAdded:").nl();
//			fdmDiff.foodDiff.leftOuter().forEach(food->{
//				foodSid(1, food);
//				food(1, food);
//			});
//		}
//		if(fdmDiff.foodDiff.rightOuter().isEmpty()) {
//			writer.write("foodRemoved: []").nl();
//		} else {
//			writer.write("foodRemoved:").nl();
//			fdmDiff.foodDiff.rightOuter().forEach(food->{
//				foodSid(1, food);
//				food(1, food);
//			});
//		}
//		if(fdmDiff.foodDiff.innerMismatch().isEmpty()) {
//			writer.write("foodChanged: []").nl();
//		} else {
//			writer.write("foodChanged:").nl();
//			fdmDiff.foodDiff.innerMismatch().forEach(pair->{
//				foodSidSq(0, pair.left());
//				food(1, pair.left(), pair.right());
//			});
//		}
//	}
//	private void emitRecipeDiff() {
//		if(fdmDiff.recipeDiff.leftOuter().isEmpty()) {
//			writer.write("recipeAdded: []").nl();
//		} else {
//			writer.write("recipeAdded:").nl();
//			fdmDiff.recipeDiff.leftOuter().forEach(recipe->{
//				recipeSid(1, recipe);
//				recipe(1, recipe);
//				ingredients(2, recipe);
//			});
//		}
//		if(fdmDiff.recipeDiff.rightOuter().isEmpty()) {
//			writer.write("recipeRemoved: []").nl();
//		} else {
//			writer.write("recipeRemoved:").nl();
//			fdmDiff.recipeDiff.rightOuter().forEach(recipe->{
//				recipeSid(1, recipe);
//				recipe(1, recipe);
//			});
//		}
//
//		var ingrAllSame = fdmDiff.ingredientDiffByRecipeSid.values().stream()
//			.allMatch(Diff::isAllSame);
//
//		if(ingrAllSame
//				&& fdmDiff.recipeDiff.innerMismatch().isEmpty()) {
//			writer.write("recipeChanged: []").nl();
//		} else {
//			writer.write("recipeChanged:").nl();
//
//			var seenRecipeSids = new HashSet<SemanticIdentifier>();
//
//			fdmDiff.recipeDiff.innerMismatch().forEach(pair->{
//				recipeSidSq(0, pair.left());
//				recipe(1, pair.left(), pair.right());
//				var recipeSid = pair.left().sid();
//				seenRecipeSids.add(recipeSid);
//				emitIngredientDiff(1, recipeSid);
//			});
//
//			_Sets.minus(fdmDiff.mainFdm.recipeBySid().keySet(), seenRecipeSids)
//				.forEach(recipeSid->{
//					var recipe = Objects.requireNonNull(fdmDiff.mainFdm.recipeBySid().get(recipeSid));
//					recipeSid(2, recipe);
//					recipe(2, recipe);
//					// has no change to name or group but to ingredients
//					emitIngredientDiff(1, recipeSid);
//				});
//		}
//
//	}
//	private void foodSid(final int i, final Food food) {
//		writer.ind(i).write("sid: ", food.sid().toStringNoBox()).nl();
//	}
//	private void foodSidSq(final int i, final Food food) {
//		writer.ind(i).sq().write("sid: ", food.sid().toStringNoBox()).nl();
//	}
//	private void food(final int i, final Food food) {
//		writer.ind(i).write("name: ", food.name()).nl();
//		writer.ind(i).write("group: ", food.groupSid().toStringNoBox()).nl();
//	}
//	private void food(final int i, final Food main, final Food base) {
//		boolean sameName = Objects.equals(main.name(), base.name());
//		boolean sameGroup = Objects.equals(main.groupSid(), base.groupSid());
//		if(sameName) {
//			writer.ind(i).write("name: ", main.name()).nl();
//		}
//		if(sameGroup) {
//			writer.ind(i).write("group: ", main.groupSid().toStringNoBox()).nl();
//		}
//		writer.ind(i).write("old:").nl();
//		if(!sameName) {
//			writer.ind(i+1).write("name: ", base.name()).nl();
//		}
//		if(!sameGroup) {
//			writer.ind(i+1).write("group: ", base.groupSid().toStringNoBox()).nl();
//		}
//		writer.ind(i).write("new:").nl();
//		if(!sameName) {
//			writer.ind(i+1).write("name: ", main.name()).nl();
//		}
//		if(!sameGroup) {
//			writer.ind(i+1).write("group: ", main.groupSid().toStringNoBox()).nl();
//		}
//	}
//	private void recipeSid(final int i, final Recipe recipe) {
//		writer.ind(i).write("sid: ", recipe.sid().toStringNoBox()).nl();
//	}
//	private void recipeSidSq(final int i, final Recipe recipe) {
//		writer.ind(i).sq().write("sid: ", recipe.sid().toStringNoBox()).nl();
//	}
//	private void recipe(final int i, final Recipe recipe) {
//		writer.ind(i).write("name: ", recipe.name()).nl();
//		writer.ind(i).write("group: ", recipe.groupSid().toStringNoBox()).nl();
//	}
//	private void recipe(final int i, final Recipe main, final Recipe base) {
//		boolean sameName = Objects.equals(main.name(), base.name());
//		boolean sameGroup = Objects.equals(main.groupSid(), base.groupSid());
//		if(sameName) {
//			writer.ind(i).write("name: ", main.name()).nl();
//		}
//		if(sameGroup) {
//			writer.ind(i).write("group: ", main.groupSid().toStringNoBox()).nl();
//		}
//		writer.ind(i).write("old:").nl();
//		if(!sameName) {
//			writer.ind(i+1).write("name: ", base.name()).nl();
//		}
//		if(!sameGroup) {
//			writer.ind(i+1).write("group: ", base.groupSid().toStringNoBox()).nl();
//		}
//		writer.ind(i).write("new:").nl();
//		if(!sameName) {
//			writer.ind(i+1).write("name: ", main.name()).nl();
//		}
//		if(!sameGroup) {
//			writer.ind(i+1).write("group: ", main.groupSid().toStringNoBox()).nl();
//		}
//	}
//	private void emitIngredientDiff(final int i, final SemanticIdentifier recipeSid) {
//		var ingrDiff = fdmDiff.ingredientDiffByRecipeSid.getOrDefault(recipeSid, Diff.empty());
//		if(ingrDiff.isAllSame())
//			return;
//		if(ingrDiff.leftOuter().isEmpty()) {
//			writer.ind(i).write("ingredientAdded: []").nl();
//		} else {
//			writer.ind(i).write("ingredientAdded:").nl();
//			ingrDiff.leftOuter().forEach(ingr->{
//				foodSid(2, ingr.food());
//				food(2, ingr.food());
//			});
//		}
//		if(ingrDiff.rightOuter().isEmpty()) {
//			writer.ind(i).write("ingredientRemoved: []").nl();
//		} else {
//			writer.ind(i).write("ingredientRemoved:").nl();
//			ingrDiff.rightOuter().forEach(ingr->{
//				foodSid(2, ingr.food());
//				food(2, ingr.food());
//			});
//		}
//		if(ingrDiff.innerMismatch().isEmpty()) {
//			writer.ind(i).write("ingredientChanged: []").nl();
//		} else {
//			writer.ind(i).write("ingredientChanged:").nl();
//			ingrDiff.innerMismatch().forEach(pair->{
//				foodSidSq(1, pair.left().food());
//				//food(2, pair.left().food(), pair.right().food());
//			});
//		}
//	}
//	private void ingredients(final int i, final Recipe recipe) {
//		_NullSafe.stream(fdmDiff.mainFdm.ingredientsByRecipeSid().get(recipe.sid()))
//			.forEach(ingr->ingredient(i, ingr));
//	}
//	private void ingredient(final int i, final RecipeIngredientResolved ingr) {
//		writer.ind(i).write("name: ", ingr.food().name()).nl();
//		writer.ind(i).write("facets: ", ingr.foodFacetSids().toStringNoBox()).nl();
//		writer.ind(i).write("amountGrams: ", ingr.amountGrams().toPlainString()).nl();
//	}
}