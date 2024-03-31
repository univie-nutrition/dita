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
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.params.classification.FoodGrouping.FoodGroupingKey;

/**
 */
@Action
@ActionLayout(
        associateWith = "effectiveQuantificationMethodPathways",
        position = Position.PANEL,
        sequence = "2",
        describedAs = "Copy effective quantification methods from a chosen food "
                + "to the (effective) grouping for the food quantification pathway."
        )
@RequiredArgsConstructor
public class Food_copyQuantificationMethodPathwayToGroup {

    @Inject private FactoryService factoryService;
    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    protected final Food mixee;

    @MemberSupport
    public Food act(
            @Parameter
            @ParameterLayout(describedAs = "Food to copy effective quantification methods from.")
            final Food masterFood) {

        var effectiveGrouping = factoryService.mixin(Food_effectiveGroupingUsedForQuantificationPathway.class, mixee).prop();
        var groupingKey = effectiveGrouping.groupingKey();

        var masterQuantificationPathways = factoryService.mixin(
                Food_effectiveQuantificationMethodPathways.class, masterFood).coll();
        var existingQuantificationPathwayKeys = factoryService.mixin(
                Food_effectiveQuantificationMethodPathways.class, mixee).coll()
                .stream()
                .map(qmp->qmp.secondaryKey())
                .collect(Collectors.toSet());

        masterQuantificationPathways
        .stream()
        .filter(qmp->!existingQuantificationPathwayKeys.contains(preview(qmp, groupingKey)))
        .forEach(qmp->{

            var entity = repositoryService.detachedEntity(new QuantificationMethodPathwayForFoodGroup());

            entity.setFoodGroupCode(groupingKey.groupKey());
            entity.setFoodSubgroupCode(groupingKey.subgroupKey());
            entity.setFoodSubSubgroupCode(groupingKey.subSubgroupKey());

            entity.setPhysicalStateFacetDescriptorLookupKey(qmp.getPhysicalStateFacetDescriptorLookupKey());
            entity.setRawOrCookedAsConsumed(qmp.getRawOrCookedAsConsumed());

            entity.setQuantificationMethod(qmp.getQuantificationMethod());
            entity.setPhotoOrShapeCode(qmp.getPhotoOrShapeCode());

            entity.setComment(qmp.getComment());

            repositoryService.persist(entity);
        });

        foreignKeyLookupService.clearCache(FacetDescriptorPathwayForFoodGroup.class);

        return mixee;
    }

    @MemberSupport
    private List<Food> choicesMasterFood() {
        return repositoryService.allInstances(Food.class);
    }

    // -- HELPER

    private QuantificationMethodPathwayForFoodGroup.SecondaryKey preview(
            final QuantificationMethodPathwayForFoodGroup master, final FoodGroupingKey groupingKey) {
        var mKey = master.secondaryKey();
        return new QuantificationMethodPathwayForFoodGroup.SecondaryKey(
                groupingKey.groupKey(),
                groupingKey.subgroupKey(),
                groupingKey.subSubgroupKey(),
                mKey.physicalStateFacetDescriptorLookupKey(),
                mKey.rawOrCookedAsConsumed(),
                mKey.quantificationMethod(),
                mKey.photoOrShapeCode());
    }

}
