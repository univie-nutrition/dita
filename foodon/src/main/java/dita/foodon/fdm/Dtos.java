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

import java.util.Collection;
import java.util.List;

import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredient;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredientResolved;
import lombok.Builder;
import lombok.experimental.UtilityClass;

@UtilityClass
class Dtos {

    @Builder
    record FoodDescriptionModelDto (
            Collection<Food> food,
            Collection<Recipe> recipes,
            Collection<RecipeIngredient> ingredients,
            Collection<ClassificationFacet> classificationFacets) {
    }

    FoodDescriptionModelDto toDto(final FoodDescriptionModel fdm) {
        return new FoodDescriptionModelDto(
                fdm.foodBySid().values(),
                fdm.recipeBySid().values(),
                fdm.ingredientsByRecipeSid().values().stream()
                	.flatMap(List::stream)
                	.map(RecipeIngredientResolved::data)
                	.toList(),
                fdm.classificationFacetBySid().values());
    }

    FoodDescriptionModel fromDto(final FoodDescriptionModelDto dto) {
        return FdmUtils.resolve(
        		dto.food(),
        		dto.recipes(),
        		dto.ingredients(),
        		dto.classificationFacets());
    }

}
