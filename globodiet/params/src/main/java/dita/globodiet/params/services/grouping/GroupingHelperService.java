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
package dita.globodiet.params.services.grouping;

import jakarta.inject.Inject;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.commons.functional.Either;

import lombok.NonNull;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import dita.globodiet.params.recipe_list.Recipe;
import dita.globodiet.params.recipe_list.RecipeGroup;
import dita.globodiet.params.recipe_list.RecipeSubgroup;
import dita.globodiet.params.util.GroupingUtils;

@Service
public class GroupingHelperService {

    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    // -- FOOD

    public Either<FoodGroup, FoodSubgroup> foodClassification(final @NonNull Food food) {
        var sub = foodSubgroup(food);
        return sub!=null
                ? Either.right(sub)
                : Either.left(foodGroup(food));
    }

    public FoodGroup foodGroup(final @NonNull Food food) {
        return foreignKeyLookupService.unique(GroupingUtils.foodGroupKeyForFood(food));
    }

    public FoodGroup foodGroup(final @NonNull FoodSubgroup foodSubgroup) {
        return foreignKeyLookupService.unique(GroupingUtils.foodGroupKeyForSubgroup(foodSubgroup));
    }

    @Nullable
    public FoodSubgroup foodSubgroup(final @NonNull Food food) {
        return foreignKeyLookupService.nullable(GroupingUtils.foodSubgroupKeyForFood(food));
    }

    /**
     * Looks up the Subgroup when given a SubSubgroup.
     * If already a Subgroup (not a SubSubgroup), then acts as identity operation.
     */
    public FoodSubgroup foodSubSubgroupToSubgroup(final @NonNull FoodSubgroup foodSubgroupOrSubSubgroup) {
        if(GroupingUtils.isSubSubgroup(foodSubgroupOrSubSubgroup)) {
            return foreignKeyLookupService.unique(
                    GroupingUtils.maskSubSubgroup(foodSubgroupOrSubSubgroup.secondaryKey()));
        }
        return foodSubgroupOrSubSubgroup;
    }

    // -- RECIPE

    public Either<RecipeGroup, RecipeSubgroup> recipeClassification(final @NonNull Recipe recipe) {
        var sub = recipeSubgroup(recipe);
        return sub!=null
                ? Either.right(sub)
                : Either.left(recipeGroup(recipe));
    }

    public RecipeGroup recipeGroup(final @NonNull Recipe recipe) {
        return foreignKeyLookupService.unique(GroupingUtils.recipeGroupKeyForRecipe(recipe));
    }

    public RecipeGroup recipeGroup(final @NonNull RecipeSubgroup recipeSubgroup) {
        return foreignKeyLookupService.unique(GroupingUtils.recipeGroupKeyForSubgroup(recipeSubgroup));
    }

    @Nullable
    public RecipeSubgroup recipeSubgroup(final @NonNull Recipe recipe) {
        return foreignKeyLookupService.nullable(GroupingUtils.recipeSubgroupKeyForRecipe(recipe));
    }

}
