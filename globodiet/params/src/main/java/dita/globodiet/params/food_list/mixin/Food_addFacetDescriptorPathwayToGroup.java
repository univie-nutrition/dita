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
package dita.globodiet.params.food_list.mixin;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.collections._Sets;

import lombok.RequiredArgsConstructor;

import dita.globodiet.params.food_descript.FoodDescriptor;
import dita.globodiet.params.food_descript.FoodFacet;
import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.params.services.food.FoodFacetHelperService;

/**
 */
@Action
@ActionLayout(
        associateWith = "effectiveFoodDescriptors",
        position = Position.PANEL,
        sequence = "1",
        describedAs = "Add food facet/descriptors to the (effective) grouping for the food facet/descriptor pathway."
                + "\n"
                + "Yet fields facetDisplayOrder, descriptorDisplayOrder, defaultFlag and notInNameFlag "
                + "must be manually corrected afterwards.")
@RequiredArgsConstructor
public class Food_addFacetDescriptorPathwayToGroup {

    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject private FoodFacetHelperService foodFacetHelperService;

    protected final Food mixee;

    @MemberSupport
    public Food act(@Parameter final FoodFacet foodFacet) {
        if(foodFacet==null) {
            return mixee; // just in case
        }
        var grouping = foodFacetHelperService
                .effectiveGroupingUsedForFacetDescriptorPathway(mixee);

        var foodDescriptorsToAdd = Can.ofCollection(repositoryService
                .allMatches(FoodDescriptor.class, fd->foodFacet.getCode().equals(fd.getFacetCode())));

        var groupingAsEither = grouping.toEither();
        foodDescriptorsToAdd.forEach(fd->{

            var entity = repositoryService.detachedEntity(new FacetDescriptorPathwayForFoodGroup());

            groupingAsEither
            .accept(
                group->{
                    entity.setFoodGroupCode(group.getCode());
                    entity.setFoodSubgroupCode(null);
                    entity.setFoodSubSubgroupCode(null);
                },
                subgroup->{
                    entity.setFoodGroupCode(subgroup.getFoodGroupCode());
                    entity.setFoodSubgroupCode(subgroup.getFoodSubgroupCode());
                    entity.setFoodSubSubgroupCode(subgroup.getFoodSubSubgroupCode());
                });

            entity.setFoodFacetCode(fd.getFacetCode());
            entity.setFacetDisplayOrder(-1);

            entity.setFoodDescriptorCode(fd.getCode());
            entity.setDescriptorDisplayOrder(-1);

            entity.setDefaultFlag(null);
            entity.setNotInNameFlag(null);

            repositoryService.persist(entity);
        });

        foreignKeyLookupService.clearCache(FacetDescriptorPathwayForFoodGroup.class);

        return mixee;
    }

    @MemberSupport
    private List<FoodFacet> choicesFoodFacet() {
        var allFoodFacets = repositoryService.allInstances(FoodFacet.class)
                .stream()
                .map(FoodFacet::secondaryKey)
                .collect(Collectors.toSet());
        var facetsChosenForPathway = foodFacetCodesAsDefinedByFoodClassification(
                foodFacetHelperService
                    .effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(mixee));

        return _Sets.minus(
                allFoodFacets,
                facetsChosenForPathway)
            .stream()
            .map(foreignKeyLookupService::unique)
            .toList();
    }

    // -- HELPER

    private Set<FoodFacet.SecondaryKey> foodFacetCodesAsDefinedByFoodClassification(
            final List<FacetDescriptorPathwayForFoodGroup> pathwayForFoodGroup) {
        return pathwayForFoodGroup.stream()
                .map(FacetDescriptorPathwayForFoodGroup::getFoodFacetCode)
                .map(FoodFacet.SecondaryKey::new)
                .collect(Collectors.toSet());
    }

}
