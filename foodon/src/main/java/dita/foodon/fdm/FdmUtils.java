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
package dita.foodon.fdm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.collections._Multimaps;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.YamlUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.util.FormatUtils;
import dita.foodon.fdm.Dtos.FoodDescriptionModelDto;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredient;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredientResolved;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FdmUtils {

    // -- READING

    public static FoodDescriptionModel fromYaml(final String yaml) {
        return YamlUtils.tryRead(FoodDescriptionModelDto.class, yaml, FormatUtils.yamlOptions())
            .mapSuccessWhenPresent(Dtos::fromDto)
            .valueAsNonNullElseFail();
    }

    public FoodDescriptionModel fromYaml(final DataSource ds) {
        return YamlUtils.tryRead(FoodDescriptionModelDto.class, ds, FormatUtils.yamlOptions())
            .mapSuccessWhenPresent(Dtos::fromDto)
            .valueAsNonNullElseFail();
    }

    // -- WRITING

    public void toYaml(
            final FoodDescriptionModel fdm,
            final DataSink ds) {
        YamlUtils.write(Dtos.toDto(fdm), ds, FormatUtils.yamlOptions());
    }

    public String toYaml(
            final FoodDescriptionModel fdm) {
        return YamlUtils.toStringUtf8(Dtos.toDto(fdm), FormatUtils.yamlOptions());
    }

    public Stream<Food> streamFood(final FoodDescriptionModel fdm) {
        return fdm.foodBySid().values().stream();
    }

    public Stream<Recipe> streamRecipes(final FoodDescriptionModel fdm) {
        return fdm.recipeBySid().values().stream();
    }

    public Stream<ClassificationFacet> streamClassificationFacets(final FoodDescriptionModel fdm) {
        return fdm.classificationFacetBySid().values().stream();
    }

    public Stream<RecipeIngredientResolved> streamIngredients(final FoodDescriptionModel fdm) {
        return fdm.ingredientsByRecipeSid().values().stream().flatMap(List::stream);
    }

    // -- UTILS

    public Map<SemanticIdentifier, Food> collectFoodBySid(
            final @Nullable Collection<Food> food) {
        return collectFoodBySid(_NullSafe.stream(food));
    }
    public Map<SemanticIdentifier, Food> collectFoodBySid(
            final @Nullable Stream<Food> foodStream) {
        if(foodStream==null)
            return Collections.emptyMap();
        var map = new HashMap<SemanticIdentifier, Food>();
        foodStream.forEach(food->map.put(food.sid(), food));
        return map;
    }

    public Map<SemanticIdentifier, Recipe> collectRecipeBySid(
            final @Nullable Collection<Recipe> recipes) {
        return collectRecipeBySid(_NullSafe.stream(recipes));
    }
    public Map<SemanticIdentifier, Recipe> collectRecipeBySid(
            final @Nullable Stream<Recipe> recipeStream) {
        if(recipeStream==null)
            return Collections.emptyMap();
        var map = new HashMap<SemanticIdentifier, Recipe>();
        recipeStream
            .forEach(recipe->map.put(recipe.sid(), recipe));
        return map;
    }

    public Map<SemanticIdentifier, List<RecipeIngredient>> collectIngredientsByRecipeSid(
            final @Nullable Collection<RecipeIngredient> ingredients) {
        return collectIngredientsByRecipeSid(_NullSafe.stream(ingredients));
    }
    public Map<SemanticIdentifier, List<RecipeIngredient>> collectIngredientsByRecipeSid(
            final @Nullable Stream<RecipeIngredient> ingredientStream) {
        final Map<SemanticIdentifier, List<RecipeIngredient>> map = new HashMap<>();
        if(ingredientStream==null) return map;
        ingredientStream
            .forEach(recipeIngredient->{
                var list = map.get(recipeIngredient.recipeSid());
                if(list==null) {
                    list = new ArrayList<>();
                    map.put(recipeIngredient.recipeSid(), list);
                }
                list.add(recipeIngredient);
            });
        return map;
    }

    public Map<SemanticIdentifier, ClassificationFacet> collectClassificationFacetBySid(
            final @Nullable Collection<ClassificationFacet> classificationFacets) {
        return collectClassificationFacetBySid(_NullSafe.stream(classificationFacets));
    }
    public Map<SemanticIdentifier, ClassificationFacet> collectClassificationFacetBySid(
            final @Nullable Stream<ClassificationFacet> facetStream) {
        final Map<SemanticIdentifier, ClassificationFacet> map = new HashMap<>();
        if(facetStream==null) return map;
        facetStream.forEach(classificationFacet->map.put(classificationFacet.sid(), classificationFacet));
        return map;
    }

    public static BigDecimal totalAmountGrams(
    		final Collection<RecipeIngredient> ingredients) {
    	var totalAmount = ingredients.stream()
            	.map(RecipeIngredient::amountGrams)
            	.reduce(BigDecimal.ZERO, BigDecimal::add);
    	return totalAmount;
    }

	public static FoodDescriptionModel resolve(
			final Collection<Food> food,
			final Collection<Recipe> recipes,
			final Collection<RecipeIngredient> ingredients,
			final Collection<ClassificationFacet> classificationFacets) {

		var foodBySid = FdmUtils.collectFoodBySid(food);
    	var recipeBySid = FdmUtils.collectRecipeBySid(recipes);
    	var ingredientByRecipeSid = _Multimaps.<SemanticIdentifier, RecipeIngredientResolved>newListMultimap();
        var classificationsBySid = FdmUtils.collectClassificationFacetBySid(classificationFacets);
        var totalAmount = totalAmountGrams(ingredients);

        ingredients.forEach(ingr->{

        	var resolved = new RecipeIngredientResolved(
        			recipeBySid.get(ingr.recipeSid()),
        			foodBySid.get(ingr.foodSid()),
        			ingr.amountGrams().movePointLeft(3).divide(totalAmount, 0, RoundingMode.HALF_UP).intValueExact(),
        			ingr);

        	var ingrSeenBefore = ingredientByRecipeSid.getOrElseNew(ingr.recipeSid());
        	mergeDuplicates(ingrSeenBefore, resolved);
        });

        return new FoodDescriptionModel(
        		Collections.unmodifiableMap(foodBySid),
        		Collections.unmodifiableMap(recipeBySid),
        		ingredientByRecipeSid.asUnmodifiable(),
        		Collections.unmodifiableMap(classificationsBySid));
	}

	private static void mergeDuplicates(
			final List<RecipeIngredientResolved> ingrSeenBefore,
			final RecipeIngredientResolved resolved) {
		var duplicate = ingrSeenBefore.stream()
				.filter(ingr->ingr.key().equals(resolved.key()))
				.findFirst()
				.orElse(null);
		if(duplicate==null) {
			ingrSeenBefore.add(resolved);
			return;
		}
		ingrSeenBefore.replaceAll(ingr->ingr==duplicate ? mergeDuplicate(duplicate, resolved) : ingr);

	}

	private static RecipeIngredientResolved mergeDuplicate(
			final RecipeIngredientResolved a,
			final RecipeIngredientResolved b) {
		Assert.isTrue(Objects.equals(a.rawToCookedCoefficient(), b.rawToCookedCoefficient()), ()->"cannot merge when raw-to-cooked mismatch");
		return new RecipeIngredientResolved(a.recipe(), a.food(), a.relativeMassPermille() + b.relativeMassPermille(),
				new RecipeIngredient(a.recipeSid(), a.foodSid(), a.foodFacetSids(), a.amountGrams().add(b.amountGrams()), a.rawToCookedCoefficient()));
	}

}
