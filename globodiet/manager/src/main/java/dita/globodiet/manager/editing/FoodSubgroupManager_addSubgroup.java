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

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.Domain;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.commons.services.idgen.IdGeneratorService;
import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import dita.globodiet.manager.blobstore.BlobStore;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Domain.Exclude //TODO there are 36 arrows pointing at FoodSubgroup, so we perhaps rather need to clone
@Action
@ActionLayout(
        fieldSetId="listOfFoodSubgroup",
        position = Position.PANEL,
        describedAs = "Add an new FoodSubgroup (or FoodSubSubgroup)")
@RequiredArgsConstructor
public class FoodSubgroupManager_addSubgroup {

    @Inject BlobStore blobStore;
    @Inject RepositoryService repositoryService;
    @Inject FactoryService factoryService;
    @Inject IdGeneratorService idGeneratorService;
    @Inject ForeignKeyLookupService foreignKeyLookupService;

    protected final FoodSubgroup.Manager mixee;

    @MemberSupport
    public FoodSubgroup act(@ParameterTuple final FoodSubgroup.Params p) {
        val grp = repositoryService.detachedEntity(new FoodSubgroup());

        grp.setFatDuringCookingSubgroupQ(p.fatDuringCookingSubgroupQ());
        grp.setFatOrSauceSubgroupThatCanBeLeftOverInTheDishQ(p.fatOrSauceSubgroupThatCanBeLeftOverInTheDishQ());
        grp.setFatOrSauceSweetenerSubgroupQ(p.fatOrSauceSweetenerSubgroupQ());
        grp.setFoodGroupCode(p.foodGroup().getCode());
        grp.setFoodSubgroupCode(p.foodSubgroupCode());
        grp.setFoodSubSubgroupCode(p.foodSubSubgroupCode());
        grp.setName(p.name());
        grp.setShortName(p.shortName());
        repositoryService.persist(grp);
        foreignKeyLookupService.clearCache(FoodSubgroup.class);
        return grp;
    }

    // -- CHOICES

    @MemberSupport public List<FoodGroup> choicesFoodGroup(final FoodSubgroup.Params p) {
        return repositoryService.allInstances(FoodGroup.class);
    }

}
