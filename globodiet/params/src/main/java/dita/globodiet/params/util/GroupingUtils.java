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
package dita.globodiet.params.util;

import org.apache.causeway.commons.internal.base._Strings;

import org.jspecify.annotations.NonNull;
import lombok.experimental.UtilityClass;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import dita.globodiet.params.recipe_list.Recipe;
import dita.globodiet.params.recipe_list.RecipeGroup;
import dita.globodiet.params.recipe_list.RecipeSubgroup;

@UtilityClass
public class GroupingUtils {

    // -- FOOD

    public FoodGroup.SecondaryKey foodGroupKeyForFood(final @NonNull Food food) {
        return new FoodGroup.SecondaryKey(food.getFoodGroupCode());
    }

    public FoodGroup.SecondaryKey foodGroupKeyForSubgroup(final @NonNull FoodSubgroup foodSubgroup) {
        return new FoodGroup.SecondaryKey(foodSubgroup.getFoodGroupCode());
    }

    public FoodSubgroup.SecondaryKey foodSubgroupKeyForFood(final @NonNull Food food) {
        return new FoodSubgroup.SecondaryKey(
                food.getFoodGroupCode(),
                food.getFoodSubgroupCode(),
                food.getFoodSubSubgroupCode());
    }

    /**
     * Whether given group is a SubSubgroup (not a Subgroup).
     */
    public boolean isSubSubgroup(final @NonNull FoodSubgroup foodSubOrSubSubgroup) {
        return _Strings.isNotEmpty(foodSubOrSubSubgroup.getFoodSubSubgroupCode());
    }

    /**
     * Whether given key represents a SubSubgroup (not a Subgroup).
     */
    public boolean isSubSubgroup(final FoodSubgroup.@NonNull SecondaryKey key) {
        return _Strings.isNotEmpty(key.foodSubSubgroupCode());
    }

    /**
     * Blanks out the sub-sub-groupe code:
     * {@link dita.globodiet.params.food_list.FoodSubgroup.SecondaryKey#foodSubSubgroupCode()}.
     */
    public FoodSubgroup.SecondaryKey maskSubSubgroup(final FoodSubgroup.@NonNull SecondaryKey key) {
        if(GroupingUtils.isSubSubgroup(key)) {
            // convert secondary key
            var foodSubgroupKey = new FoodSubgroup.SecondaryKey(
                    key.foodGroupCode(),
                    key.foodSubgroupCode(),
                    null);
            return foodSubgroupKey;
        }
        return key;
    }

    // -- RECIPE

    public RecipeGroup.SecondaryKey recipeGroupKeyForRecipe(final @NonNull Recipe recipe) {
        return new RecipeGroup.SecondaryKey(recipe.getRecipeGroupCode());
    }

    public RecipeGroup.SecondaryKey recipeGroupKeyForSubgroup(final @NonNull RecipeSubgroup recipeSubgroup) {
        return new RecipeGroup.SecondaryKey(recipeSubgroup.getRecipeGroupCode());
    }

    public RecipeSubgroup.SecondaryKey recipeSubgroupKeyForRecipe(final @NonNull Recipe recipe) {
        return new RecipeSubgroup.SecondaryKey(
                recipe.getRecipeGroupCode(),
                recipe.getRecipeSubgroupCode());
    }

}
