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
package dita.globodiet.params.pathway.mixin;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jakarta.inject.Inject;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import dita.globodiet.params.food_descript.FoodDescriptor;
import dita.globodiet.params.food_descript.FoodFacet;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup;

/**
 */
@Action
@ActionLayout(
        associateWith = "listOfFacetDescriptorPathwayForFoodGroup",
        position = Position.PANEL,
        sequence = "1",
        describedAs = "Adds an entry to the list of facet/descriptor pathway for food-group.")
@RequiredArgsConstructor
public class FacetDescriptorPathwayForFoodGroupManager_addEntry {

    @Inject private RepositoryService repositoryService;
    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    protected final FacetDescriptorPathwayForFoodGroup.Manager mixee;

    @MemberSupport
    public FacetDescriptorPathwayForFoodGroup act(
            @ParameterTuple final FacetDescriptorPathwayForFoodGroup.Params p) {
        var e = factoryService.detachedEntity(new FacetDescriptorPathwayForFoodGroup());

        // classification
        e.setFoodGroupCode(p.foodGroup().getCode());
        e.setFoodSubgroupCode(foodSubgroupCode(p.foodSubgroup()));
        e.setFoodSubSubgroupCode(foodSubSubgroupCode(p.foodSubgroup()));

        // cross reference
        e.setFoodFacetCode(p.foodFacet().getCode());
        e.setFacetDisplayOrder(p.facetDisplayOrder());
        e.setFoodDescriptorCode(p.foodDescriptor().getCode());
        e.setDescriptorDisplayOrder(p.descriptorDisplayOrder());

        // flags
        e.setDefaultFlag(p.defaultFlag());
        e.setNotInNameFlag(p.notInNameFlag());

        repositoryService.persist(e);
        foreignKeyLookupService.clearCache(FacetDescriptorPathwayForFoodGroup.class);
        return e;
    }

    // -- DEFAULTS

    @MemberSupport public int defaultFacetDisplayOrder(
            @ParameterTuple final FacetDescriptorPathwayForFoodGroup.Params p) {
        return -1;
    }
    @MemberSupport public int defaultDescriptorDisplayOrder(
            @ParameterTuple final FacetDescriptorPathwayForFoodGroup.Params p) {
        return -1;
    }

    // -- CHOICES

    @MemberSupport public List<FoodGroup> choicesFoodGroup(
            final FacetDescriptorPathwayForFoodGroup.Params p) {
        return repositoryService.allInstances(FoodGroup.class);
    }
    @MemberSupport public List<FoodSubgroup> choicesFoodSubgroup(
            final FacetDescriptorPathwayForFoodGroup.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()==null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode()));
    }
    @MemberSupport public List<FoodSubgroup> choicesFoodSubSubgroup(
            final FacetDescriptorPathwayForFoodGroup.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()!=null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode())
                    && Objects.equals(fg.getFoodSubgroupCode(), p.foodSubgroup().getFoodSubgroupCode()));
    }

    @MemberSupport public List<FoodFacet> choicesFoodFacet(
            final FacetDescriptorPathwayForFoodGroup.Params p) {
        return repositoryService.allInstances(FoodFacet.class);
    }
    @MemberSupport public List<FoodDescriptor> choicesFoodDescriptor(
            final FacetDescriptorPathwayForFoodGroup.Params p) {
        return p.foodFacet()!=null
                ? repositoryService.allMatches(FoodDescriptor.class,
                        fd->Objects.equals(fd.getFacetCode(), p.foodFacet().getCode()))
                : Collections.emptyList();
    }

    // -- HELPER

    private String foodSubgroupCode(final @Nullable FoodSubgroup foodSubgroup) {
        return foodSubgroup!=null
                ? foodSubgroup.getFoodSubgroupCode()
                : null;
    }

    private String foodSubSubgroupCode(final @Nullable FoodSubgroup foodSubgroup) {
        return foodSubgroup!=null
                ? foodSubgroup.getFoodSubSubgroupCode()
                : null;
    }

}
