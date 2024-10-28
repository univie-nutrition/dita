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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.experimental.UtilityClass;

import dita.commons.format.FormatUtils;
import dita.commons.sid.SemanticIdentifier;
import dita.foodon.fdm.Dtos.FoodDescriptionModelDto;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredient;

@UtilityClass
public class FdmUtils {

    // -- READING

    public static FoodDescriptionModel fromYaml(final String yaml) {
        return YamlUtils.tryRead(FoodDescriptionModelDto.class, yaml, FormatUtils.yamlOptions())
            .mapSuccessWhenPresent(FdmUtils::fromDto)
            .valueAsNonNullElseFail();
    }

    public FoodDescriptionModel fromYaml(final DataSource ds) {
        return YamlUtils.tryRead(FoodDescriptionModelDto.class, ds, FormatUtils.yamlOptions())
            .mapSuccessWhenPresent(FdmUtils::fromDto)
            .valueAsNonNullElseFail();
    }

    // -- WRITING

    public void toYaml(
            final FoodDescriptionModel fdm,
            final DataSink ds) {
        YamlUtils.write(toDto(fdm), ds, FormatUtils.yamlOptions());
    }

    public String toYaml(
            final FoodDescriptionModel fdm) {
        return YamlUtils.toStringUtf8(toDto(fdm), FormatUtils.yamlOptions());
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

    public Stream<RecipeIngredient> streamIngredients(final FoodDescriptionModel fdm) {
        return fdm.ingredientsByRecipeSid().values().stream().flatMap(List::stream);
    }

    // -- UTILS

    public Map<SemanticIdentifier, Food> collectFoodBySid(
            final @Nullable Collection<Food> food) {
        return collectFoodBySid(_NullSafe.stream(food));
    }
    public Map<SemanticIdentifier, Food> collectFoodBySid(
            final @Nullable Stream<Food> foodStream) {
        final Map<SemanticIdentifier, Food> map = new HashMap<>();
        if(foodStream==null) return map;
        foodStream.forEach(food->map.put(food.sid(), food));
        return map;
    }

    public Map<SemanticIdentifier, Recipe> collectRecipeBySid(
            final @Nullable Collection<Recipe> recipes) {
        return collectRecipeBySid(_NullSafe.stream(recipes));
    }
    public Map<SemanticIdentifier, Recipe> collectRecipeBySid(
            final @Nullable Stream<Recipe> recipeStream) {
        final Map<SemanticIdentifier, Recipe> map = new HashMap<>();
        if(recipeStream==null) return map;
        recipeStream.forEach(recipe->map.put(recipe.sid(), recipe));
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

    // -- HELPER

    FoodDescriptionModelDto toDto(final FoodDescriptionModel fdm) {
        return new FoodDescriptionModelDto(
                fdm.foodBySid().values(),
                fdm.recipeBySid().values(),
                streamIngredients(fdm).toList(),
                fdm.classificationFacetBySid().values());
    }

    FoodDescriptionModel fromDto(final FoodDescriptionModelDto dto) {
        return new FoodDescriptionModel(
                collectFoodBySid(dto.food()),
                collectRecipeBySid(dto.recipes()),
                collectIngredientsByRecipeSid(dto.ingredients()),
                collectClassificationFacetBySid(dto.classificationFacets()));
    }

}
