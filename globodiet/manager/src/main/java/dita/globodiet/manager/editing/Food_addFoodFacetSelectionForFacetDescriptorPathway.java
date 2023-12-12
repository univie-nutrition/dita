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

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.internal.collections._Sets;

import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodFacet;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.manager.services.food.FoodFacetHelperService;
import dita.globodiet.manager.services.food.FoodHelperService;
import lombok.RequiredArgsConstructor;

/**
 */
@Action
@ActionLayout(
        associateWith = "effectiveFoodDescriptors",
        position = Position.PANEL,
        describedAs = "Edit the food facet subset to be selected in effect for the food facet/descriptor pathway.")
@RequiredArgsConstructor
public class Food_addFoodFacetSelectionForFacetDescriptorPathway {

    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject private FoodHelperService foodHelperService;
    @Inject private FoodFacetHelperService foodFacetHelperService;

    protected final Food mixee;

    @MemberSupport
    public Food act(@Parameter final FoodFacet foodFacet) {

        var entity = repositoryService.detachedEntity(new FacetDescriptorPathwayForFood());

        entity.setFoodCode(mixee.getCode());
        entity.setDisplayOrder(-1); // TODO needs post processing
        entity.setMandatoryInSequenceOfFacetsCode(foodFacet.getCode());
        repositoryService.persist(entity);
        foreignKeyLookupService.clearCache(FacetDescriptorPathwayForFood.class);
        postProcessDisplayOrder();
        return mixee;
    }

    @MemberSupport
    public List<FoodFacet> choicesFoodFacet() {
        var facetsAvailableForPathway = foodFacetCodesAsDefinedByFoodClassification(
                foodFacetHelperService
                    .effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(mixee));

        return _Sets.minus(
                facetsAvailableForPathway,
                foodHelperService.selectedFacetDescriptorPathwayForFoodAsFoodFacetSecondaryKeySet(mixee))
            .stream()
            .map(foreignKeyLookupService::unique)
            .toList();
    }

    // -- HELPER

    private Set<FoodFacet.SecondaryKey> foodFacetCodesAsDefinedByFoodClassification(
            final List<FacetDescriptorPathwayForFoodGroup> pathwayForFoodGroup) {
        return pathwayForFoodGroup.stream()
                .map(FacetDescriptorPathwayForFoodGroup::getFacetCode)
                .map(FoodFacet.SecondaryKey::new)
                .collect(Collectors.toSet());
    }

    private void postProcessDisplayOrder() {
        // TODO implements, also do this when deleting relation
    }

}
