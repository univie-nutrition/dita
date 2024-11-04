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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._NullSafe;

import dita.commons.sid.SemanticIdentifier;

/**
 * Provides a set of food ontologies,
 * that allow to describe consumed food,
 * including categorization (grouping) and descriptive facets.
 */
public record FoodDescriptionModel(
        Map<SemanticIdentifier, Food> foodBySid,
        Map<SemanticIdentifier, Recipe> recipeBySid,
        Map<SemanticIdentifier, List<RecipeIngredient>> ingredientsByRecipeSid,
        Map<SemanticIdentifier, ClassificationFacet> classificationFacetBySid
        ) {

    public record Food(
            SemanticIdentifier sid,
            String name,
            SemanticIdentifier groupSid) {
    }

    public record Recipe(
            SemanticIdentifier sid,
            String name,
            SemanticIdentifier groupSid) {
    }

    public record RecipeIngredient(
            SemanticIdentifier recipeSid,
            SemanticIdentifier foodSid,
            /**
             * Amount consumed in gram.
             */
            BigDecimal amountGrams) {
    }

    public record ClassificationFacet(
            SemanticIdentifier sid,
            String name) {
    }

    // -- FACTORIES

    public static FoodDescriptionModel empty() {
        return new FoodDescriptionModel(
                new HashMap<SemanticIdentifier, Food>(),
                new HashMap<SemanticIdentifier, Recipe>(),
                new HashMap<SemanticIdentifier, List<RecipeIngredient>>(),
                new HashMap<SemanticIdentifier, ClassificationFacet>());
    }

    // -- UTIL

    /**
     * Streams all ingredients of given recipe.
     */
    public Stream<RecipeIngredient> streamIngredients(@Nullable final Recipe recipe) {
        return recipe!=null
                ? _NullSafe.stream(ingredientsByRecipeSid.get(recipe.sid()))
                : Stream.empty();
    }

    /**
     * Returns sum of amount gram consumed over all ingredients of given recipe.
     */
    public BigDecimal sumAmountGramsForRecipe(@Nullable final Recipe recipe) {
        return streamIngredients(recipe)
            .map(RecipeIngredient::amountGrams)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // -- IO

    public static FoodDescriptionModel fromYaml(final String yaml) {
        return FdmUtils.fromYaml(yaml);
    }

    public String toYaml() {
        return FdmUtils.toYaml(this);
    }

}
