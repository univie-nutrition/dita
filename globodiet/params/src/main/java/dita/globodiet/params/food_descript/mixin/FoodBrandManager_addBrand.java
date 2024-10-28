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
package dita.globodiet.params.food_descript.mixin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.services.repository.RepositoryService;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import dita.globodiet.params.food_descript.FoodBrand;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import lombok.RequiredArgsConstructor;

@Action
@ActionLayout(fieldSetId="listOfFoodBrand", position = Position.PANEL)
@RequiredArgsConstructor
public class FoodBrandManager_addBrand {

    @Inject RepositoryService repositoryService;
    @Inject ForeignKeyLookupService foreignKeyLookupService;

    protected final FoodBrand.Manager mixee;

    @MemberSupport
    public FoodBrand.Manager act(@ParameterTuple final FoodBrand.Params p) {

        var brandName = repositoryService.detachedEntity(new FoodBrand());
        brandName.setNameOfBrand(p.nameOfBrand());
        brandName.setFoodGroupCode(Optional.ofNullable(p.foodGroup())
                .map(x->x.getCode())
                .orElse(null));
        brandName.setFoodSubgroupCode(Optional.ofNullable(p.foodSubgroup())
                .map(x->x.getFoodSubgroupCode())
                .orElse(null));
        brandName.setFoodSubSubgroupCode(Optional.ofNullable(p.foodSubSubgroup())
                .map(x->x.getFoodSubSubgroupCode())
                .orElse(null));

        repositoryService.persist(brandName);
        foreignKeyLookupService.clearCache(FoodBrand.class);
        return mixee;
    }

    @MemberSupport public List<FoodGroup> choices1Act(final FoodBrand.Params p) {
        return repositoryService.allInstances(FoodGroup.class);
    }
    @MemberSupport public List<FoodSubgroup> choices2Act(final FoodBrand.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()==null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode()));
    }
    @MemberSupport public List<FoodSubgroup> choices3Act(final FoodBrand.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()!=null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode())
                    && Objects.equals(fg.getFoodSubgroupCode(), p.foodSubgroup().getFoodSubgroupCode()));
    }

}
