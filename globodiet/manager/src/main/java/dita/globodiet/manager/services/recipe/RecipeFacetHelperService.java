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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.functional.Either;

import lombok.NonNull;

import dita.globodiet.dom.params.classification.RecipeGrouping;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipe;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipeGroup;
import dita.globodiet.dom.params.recipe_description.RecipeFacet;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.dom.params.recipe_list.RecipeDeps.Recipe_dependentFacetDescriptorPathwayForRecipeMappedByRecipe;
import dita.globodiet.dom.params.recipe_list.RecipeGroup;
import dita.globodiet.dom.params.recipe_list.RecipeGroupDeps.RecipeGroup_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeGroup;
import dita.globodiet.dom.params.recipe_list.RecipeSubgroup;
import dita.globodiet.dom.params.recipe_list.RecipeSubgroupDeps.RecipeSubgroup_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeSubgroup;
import dita.globodiet.manager.services.grouping.GroupingHelperService;

@Service
public class RecipeFacetHelperService {

    @Inject private FactoryService factoryService;
    @Inject private GroupingHelperService groupingHelperService;

    public List<FacetDescriptorPathwayForRecipeGroup> effectiveFacetDescriptorPathwayForRecipeClassification(
            final @NonNull RecipeGrouping recipeGrouping) {
        final @NonNull Either<RecipeGroup, RecipeSubgroup> recipeClassification = recipeGrouping.toEither();
        final List<FacetDescriptorPathwayForRecipeGroup> facetDescriptorPathwayForFoodGroup = recipeClassification
            .fold(
                recipeGroup->listFacetDescriptorPathwayForRecipeGroup(recipeGroup),
                recipeSubgroup->{
                    var lookupResult = listFacetDescriptorPathwayForRecipeSubgroup(recipeSubgroup);
                    // if lookup was too specific, fallback to top-level = FoodGroup
                    return lookupResult.isEmpty()
                        ? listFacetDescriptorPathwayForRecipeGroup(groupingHelperService.recipeGroup(recipeClassification.rightIfAny()))
                        : lookupResult;
                });

        return facetDescriptorPathwayForFoodGroup;
    }

    public RecipeGrouping effectiveGroupingUsedForFacetDescriptorPathway(final @NonNull Recipe recipe) {
        var recipeClassification = groupingHelperService.recipeClassification(recipe);
        final RecipeGrouping recipeGrouping = recipeClassification
                .fold(
                    recipeGroup->(RecipeGrouping)recipeGroup,
                    recipeSubgroup->{
                        var groupingResult = recipeSubgroup; // assignment is non final
                        var lookupResult = listFacetDescriptorPathwayForRecipeSubgroup(recipeSubgroup);
                        // if lookup was too specific, fallback to top-level = FoodGroup
                        return lookupResult.isEmpty()
                            ? (RecipeGrouping)groupingHelperService.recipeGroup(recipeClassification.rightIfAny())
                            : groupingResult;
                    });
        return recipeGrouping;
    }

    public List<FacetDescriptorPathwayForRecipeGroup> effectiveFacetDescriptorPathwayForRecipeClassificationHonoringDisplayOrder(
            final @NonNull RecipeGrouping recipeGrouping) {
        return effectiveFacetDescriptorPathwayForRecipeClassification(recipeGrouping).stream()
                .sorted(displayOrder())
                .toList();
    }

    public List<FacetDescriptorPathwayForRecipeGroup> effectiveFacetDescriptorPathwayForRecipeClassificationHonoringDisplayOrder(
            final @NonNull Recipe recipe) {
        return effectiveFacetDescriptorPathwayForRecipeClassificationHonoringDisplayOrder(
                effectiveGroupingUsedForFacetDescriptorPathway(recipe));
    }

    public List<FacetDescriptorPathwayForRecipe> listFacetDescriptorPathwayForRecipe(final @NonNull Recipe recipe) {
        return factoryService.mixin(Recipe_dependentFacetDescriptorPathwayForRecipeMappedByRecipe.class, recipe)
                .coll();
    }

    public List<String> facetCodesAllowedForRecipeGrouping(final RecipeGrouping recipeGrouping) {
        return effectiveFacetDescriptorPathwayForRecipeClassification(recipeGrouping)
                .stream()
                .map(FacetDescriptorPathwayForRecipeGroup::getRecipeFacetCode)
                .toList();
    }

    public List<FacetDescriptorPathwayForRecipe> selectedFacetDescriptorPathwayForRecipe(
            final @Nullable Recipe recipe) {
        if(recipe==null) return Collections.emptyList();
        var mixin = factoryService.mixin(Recipe_dependentFacetDescriptorPathwayForRecipeMappedByRecipe.class, recipe);
        return mixin.coll();
    }

    public Set<RecipeFacet.SecondaryKey> selectedFacetDescriptorPathwayForRecipeAsRecipeFacetSecondaryKeySet(
            final @Nullable Recipe recipe) {
        return selectedFacetDescriptorPathwayForRecipe(recipe)
            .stream()
            .map(FacetDescriptorPathwayForRecipe::getSelectedRecipeFacetCode)
            .map(RecipeFacet.SecondaryKey::new)
            .collect(Collectors.toSet());
    }

    // -- HELPER

    private List<FacetDescriptorPathwayForRecipeGroup> listFacetDescriptorPathwayForRecipeGroup(
            final RecipeGroup recipeGroup) {
        return factoryService.mixin(
                RecipeGroup_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeGroup.class,
                recipeGroup)
            .coll();
    }

    private List<FacetDescriptorPathwayForRecipeGroup> listFacetDescriptorPathwayForRecipeSubgroup(
            final RecipeSubgroup recipeSubgroup) {
        return factoryService.mixin(
                RecipeSubgroup_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeSubgroup.class,
                recipeSubgroup)
            .coll();
    }

    private Comparator<FacetDescriptorPathwayForRecipeGroup> displayOrder() {
        return Comparator.comparing(FacetDescriptorPathwayForRecipeGroup::getFacetDisplayOrder)
                .thenComparing(FacetDescriptorPathwayForRecipeGroup::getDescriptorDisplayOrder);
    }

}
