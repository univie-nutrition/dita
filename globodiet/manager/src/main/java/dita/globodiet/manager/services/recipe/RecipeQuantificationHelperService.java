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
package dita.globodiet.manager.services.recipe;

import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.functional.Either;

import lombok.NonNull;

import dita.globodiet.dom.params.classification.RecipeGrouping;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipe;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipeGroup;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.dom.params.recipe_list.RecipeDeps.Recipe_dependentQuantificationMethodPathwayForRecipeMappedByRecipe;
import dita.globodiet.dom.params.recipe_list.RecipeGroup;
import dita.globodiet.dom.params.recipe_list.RecipeGroupDeps.RecipeGroup_dependentQuantificationMethodPathwayForRecipeGroupMappedByRecipeGroup;
import dita.globodiet.dom.params.recipe_list.RecipeSubgroup;
import dita.globodiet.manager.services.grouping.GroupingHelperService;

@Service
public class RecipeQuantificationHelperService {

    @Inject private FactoryService factoryService;
    @Inject private GroupingHelperService groupingHelperService;

    public List<QuantificationMethodPathwayForRecipeGroup> effectiveQuantificationMethodPathwayForRecipeClassification(
            final @NonNull RecipeGrouping recipeGrouping) {
        final @NonNull Either<RecipeGroup, RecipeSubgroup> recipeClassification = recipeGrouping.toEither();
        final List<QuantificationMethodPathwayForRecipeGroup> facetQuantificationMethodForRecipeGroup =
            recipeClassification
            .fold(
                recipeGroup->
                    listQuantificationMethodPathwayForRecipeGroup(recipeGroup),
                recipeSubgroup->
                    listQuantificationMethodPathwayForRecipeGroup(
                            groupingHelperService.recipeGroup(recipeClassification.rightIfAny())));

        return facetQuantificationMethodForRecipeGroup;
    }

    public RecipeGroup effectiveGroupingUsedForQuantificationPathway(final Recipe recipe) {
        var recipeClassification = groupingHelperService.recipeClassification(recipe);
        return recipeClassification
                .fold(
                    recipeGroup->recipeGroup,
                    recipeSubgroup->groupingHelperService.recipeGroup(recipeClassification.rightIfAny()));
    }

    public List<QuantificationMethodPathwayForRecipe> listQuantificationMethodPathwayForRecipe(final @NonNull Recipe recipe) {
        var mixin = factoryService.mixin(Recipe_dependentQuantificationMethodPathwayForRecipeMappedByRecipe.class, recipe);
        return mixin.coll();
    }

    // -- HELPER

    private List<QuantificationMethodPathwayForRecipeGroup> listQuantificationMethodPathwayForRecipeGroup(
            final RecipeGroup recipeGroup) {
        var mixin = factoryService.mixin(
                RecipeGroup_dependentQuantificationMethodPathwayForRecipeGroupMappedByRecipeGroup.class,
                recipeGroup);
        return mixin.coll();
    }

}
