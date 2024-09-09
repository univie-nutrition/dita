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
import java.util.List;
import java.util.Map;

import dita.commons.sid.SemanticIdentifier;

/**
 * Provides a set of food ontologies,
 * that allow to describe consumed food,
 * including categorization (grouping) and descriptive facets.
 */
public record FoodDescriptionModel(
        Map<SemanticIdentifier, Food> foodBySid,
        Map<SemanticIdentifier, Recipe> recipeBySid,
        Map<SemanticIdentifier, List<RecipeIngredient>> ingredientsByRecipeSid
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
            BigDecimal amountGrams) {
    }

    public record ClassificationFacet(
            SemanticIdentifier sid,
            String name) {
    }

}
