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
package dita.globodiet.manager.editing.food;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Where;

import lombok.RequiredArgsConstructor;

import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodDescriptor;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.manager.services.food.FoodFacetHelperService;

/**
 * With {@link FacetDescriptorPathwayForFoodGroup} a set of facet/descriptors is defined
 * for a specific food classification.
 * Optionally, for an individual food, only a subset of those facets can be selected.
 */
@Collection
@CollectionLayout(
        hidden = Where.ALL_TABLES,
        sequence = "0.1",
        describedAs = "Food Descriptors in effect associated with this individual food.\n\n"
                + "With FacetDescriptorPathwayForFoodGroup (table GROUPFAC) a set of facet/descriptors is defined "
                + "for a specific food classification.\n\n"
                + "Optionally, for an individual food, only a subset of those facets can be selected "
                + "using FacetDescriptorPathwayForFood (table FOODFAEX).\n\n"
                + "Entries are ordered by facet-display-order and then descriptor-display-order. "
                + "(However, column facet-display-order is duplicated in FacetDescriptorPathwayForFood (table FOODFAEX),"
                + "where its yet unclear whether that is inferred from or actually overrides the one defined at group level.)")
@RequiredArgsConstructor
public class Food_effectiveFoodDescriptors {

    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject private FoodFacetHelperService foodFacetHelperService;

    protected final Food mixee;

    @MemberSupport
    public List<FoodDescriptor> coll() {

        var foodDescriptorsAsDefinedByFoodClassification = foodFacetHelperService
            .effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(mixee)
            .stream()
            .map(this::foodDescriptor)
            .toList();

        // filter by individual food's subset of facets (if any)
        var facetDescriptorPathwayForFood = foodFacetHelperService.listFacetDescriptorPathwayForFood(mixee);
        if(facetDescriptorPathwayForFood.isEmpty()) {
            return foodDescriptorsAsDefinedByFoodClassification;
        }
        final Set<String> facetCodeSubset = facetDescriptorPathwayForFood.stream()
            .map(FacetDescriptorPathwayForFood::getSelectedFoodFacetCode)
            .collect(Collectors.toSet());
        return foodDescriptorsAsDefinedByFoodClassification.stream()
                .filter(foodDescriptor->facetCodeSubset.contains(foodDescriptor.getFacetCode()))
                //TODO facetDescriptorPathwayForFood have their own ordering
                //is this inferred from or an override of the group level?
                .toList();
    }

    // -- HELPER

    private FoodDescriptor foodDescriptor(final FacetDescriptorPathwayForFoodGroup groupPathway) {
        return foreignKeyLookupService.unique(new FoodDescriptor.SecondaryKey(
                groupPathway.getFoodFacetCode(),
                groupPathway.getFoodDescriptorCode()));
    }

}
