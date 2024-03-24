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
package dita.globodiet.manager.editing.wip;

import java.util.List;
import java.util.Objects;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.Domain;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import lombok.RequiredArgsConstructor;

@Domain.Exclude
@Action
@ActionLayout(fieldSetId="listOfFood", position = Position.PANEL)
@RequiredArgsConstructor
public class FoodManager_addFood {

    @Inject RepositoryService repositoryService;

    protected final Food.Manager mixee;

    public Food.Manager act(@ParameterTuple final Food.Params p) {
        //TODO this is just a stub
        return mixee;
    }
    @MemberSupport public List<FoodGroup> choices1Act(final Food.Params p) {
        return repositoryService.allInstances(FoodGroup.class);
    }
    @MemberSupport public List<FoodSubgroup> choices2Act(final Food.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()==null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode()));
    }
    @MemberSupport public List<FoodSubgroup> choices3Act(final Food.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()!=null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode())
                    && Objects.equals(fg.getFoodSubgroupCode(), p.foodSubgroup().getFoodSubgroupCode()));
    }

}
