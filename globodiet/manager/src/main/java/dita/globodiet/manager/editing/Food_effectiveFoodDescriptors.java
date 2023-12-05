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
package dita.globodiet.manager.editing;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.internal.base._Strings;

import dita.commons.services.idgen.IdGeneratorService;
import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodDescriptor;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.FoodDeps.Food_dependentFacetDescriptorPathwayForFoodMappedByFood;
import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodGroupDeps.FoodGroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import dita.globodiet.dom.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup;
import dita.globodiet.dom.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.manager.blobstore.BlobStore;
import lombok.RequiredArgsConstructor;

/**
 * With {@link FacetDescriptorPathwayForFoodGroup} a set of facet/descriptors is defined
 * for a specific food classification.
 * Optionally, for an individual food, a subset of facets can be selected.
 */
@Collection
@CollectionLayout(
        describedAs = "Food Descriptors in effect associated with this individual food.\n"
                + "With {@table GROUPFAC} a set of facet/descriptors is defined"
                + "for a specific food classification.\n"
                + "Optionally, for an individual food, a subset of facets can be selected.")
@RequiredArgsConstructor
public class Food_effectiveFoodDescriptors {

    @Inject BlobStore blobStore;
    @Inject RepositoryService repositoryService;
    @Inject FactoryService factoryService;
    @Inject IdGeneratorService idGeneratorService;
    @Inject ForeignKeyLookupService foreignKeyLookupService;

    protected final Food mixee;

    //TODO perhaps sort by display order
    @MemberSupport
    public List<FoodDescriptor> coll() {

        // find the most specialized food classification
        var foodSubOrSubSubgroup = foodSubOrSubSubgroup();
        final List<FacetDescriptorPathwayForFoodGroup> facetDescriptorPathwayForFoodGroup = foodSubOrSubSubgroup==null
            ? lookupFacetDescriptorPathwayForFoodGroup(foodGroup())
            : _Strings.isEmpty(foodSubOrSubSubgroup.getFoodSubSubgroupCode())
                    ? lookupFacetDescriptorPathwayForFoodSubgroup(foodSubOrSubSubgroup)
                    : lookupFacetDescriptorPathwayForFoodSubSubgroup(foodSubOrSubSubgroup);

        var foodDescriptorsAsDefinedByFoodClassification = facetDescriptorPathwayForFoodGroup.stream()
            .map(this::foodDescriptor)
            .toList();

        // filter by individual food's subset of facets (if any)
        var facetDescriptorPathwayForFood = lookupFacetDescriptorPathwayForFood();
        if(!facetDescriptorPathwayForFood.isEmpty()) {
            final Set<String> facetCodeSubset = facetDescriptorPathwayForFood.stream()
                .map(this::foodFacetCode)
                .collect(Collectors.toSet());
            return foodDescriptorsAsDefinedByFoodClassification.stream()
                    .filter(foodDescriptor->facetCodeSubset.contains(foodDescriptor.getFacetCode()))
                    .toList();
        }

        return foodDescriptorsAsDefinedByFoodClassification;
    }

    // -- HELPER

    private FoodGroup foodGroup() {
        return foreignKeyLookupService.unique(new FoodGroup.SecondaryKey(mixee.getFoodGroupCode()));
    }

    @Nullable
    private FoodSubgroup foodSubOrSubSubgroup() {
        return foreignKeyLookupService.nullable(new FoodSubgroup.SecondaryKey(
                mixee.getFoodGroupCode(),
                mixee.getFoodSubgroupCode(),
                mixee.getFoodSubSubgroupCode()));
    }

    private String foodFacetCode(final FacetDescriptorPathwayForFood foodPathway) {
        return foodPathway.getMandatoryInSequenceOfFacetsCode();
    }

    private FoodDescriptor foodDescriptor(final FacetDescriptorPathwayForFoodGroup groupPathway) {
        return foreignKeyLookupService.unique(new FoodDescriptor.SecondaryKey(
                groupPathway.getFacetCode(),
                groupPathway.getDescriptorCode()));
    }

    private List<FacetDescriptorPathwayForFood> lookupFacetDescriptorPathwayForFood() {
        var mixin = factoryService.mixin(Food_dependentFacetDescriptorPathwayForFoodMappedByFood.class, mixee);
        return mixin.coll();
    }

    private List<FacetDescriptorPathwayForFoodGroup> lookupFacetDescriptorPathwayForFoodGroup(final FoodGroup foodGroup) {
        var mixin = factoryService.mixin(FoodGroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodGroup.class, foodGroup);
        return mixin.coll();
    }

    private List<FacetDescriptorPathwayForFoodGroup> lookupFacetDescriptorPathwayForFoodSubgroup(final FoodSubgroup foodSubgroup) {
        var mixin = factoryService.mixin(FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup.class, foodSubgroup);
        return mixin.coll();
    }

    private List<FacetDescriptorPathwayForFoodGroup> lookupFacetDescriptorPathwayForFoodSubSubgroup(final FoodSubgroup foodSubgroup) {
        var mixin = factoryService.mixin(FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup.class, foodSubgroup);
        return mixin.coll();
    }

}
