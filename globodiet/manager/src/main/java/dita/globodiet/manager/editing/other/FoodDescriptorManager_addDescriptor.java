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
package dita.globodiet.manager.editing.other;

import java.util.List;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.commons.services.idgen.IdGeneratorService;
import org.causewaystuff.domsupport.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodDescriptor;
import dita.globodiet.dom.params.food_descript.FoodFacet;
import lombok.RequiredArgsConstructor;

@Action
@ActionLayout(fieldSetId="listOfFoodDescriptor", position = Position.PANEL)
@RequiredArgsConstructor
public class FoodDescriptorManager_addDescriptor {

    @Inject IdGeneratorService idGeneratorService;
    @Inject RepositoryService repositoryService;
    @Inject ForeignKeyLookupService foreignKeyLookupService;

    protected final FoodDescriptor.Manager mixee;

    @MemberSupport
    public FoodDescriptor act(@ParameterTuple final FoodDescriptor.Params p) {
        var foodDescriptor = repositoryService.detachedEntity(new FoodDescriptor());
        foodDescriptor.setFacetCode(p.facet().getCode());
        foodDescriptor.setCode(p.code());
        foodDescriptor.setName(p.name());
        foodDescriptor.setCooking(p.cooking());
        foodDescriptor.setOtherQ(p.otherQ());
        foodDescriptor.setChoice(p.choice());
        repositoryService.persist(foodDescriptor);
        foreignKeyLookupService.clearCache(FoodDescriptor.class);
        return foodDescriptor;
    }

    // -- DEFAULTS

    @MemberSupport public String defaultCode(final FoodDescriptor.Params p) {
        return idGeneratorService.next(String.class, FoodDescriptor.class, p)
                .orElse("??");
    }

    // -- CHOICES

    @MemberSupport public List<FoodFacet> choicesFacet() {
        return repositoryService.allInstances(FoodFacet.class);
    }

}


