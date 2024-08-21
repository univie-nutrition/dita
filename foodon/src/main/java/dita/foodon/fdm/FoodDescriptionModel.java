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

import lombok.experimental.UtilityClass;

/**
 * Provides a set of food ontologies,
 * that allow to describe consumed food,
 * including categorization (grouping) and descriptive facets.
 */
@UtilityClass
public class FoodDescriptionModel {

    public record Food(
            String code,
            String name,
            boolean isAlias,
            String foodGroupCode,
            String foodSubgroupCode,
            String foodSubSubgroupCode) {
    }

    public record Recipe(
            String code,
            String name,
            boolean isAlias,
            String recipeGroupCode,
            String recipeSubgroupCode) {
    }

    public record FoodFacet(
            String code,
            String name) {
    }

    public record FoodDescriptor(
            String code,
            String facetCode,
            String name) {
    }

}
