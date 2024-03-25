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

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Where;

import lombok.RequiredArgsConstructor;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.params.services.food.FoodQuantificationHelperService;
import dita.globodiet.params.util.QuantificationMethodPathwayKey;

/**
 * With {@link QuantificationMethodPathwayForFoodGroup} a set of quantification methods is defined
 * for a specific food classification.
 * Optionally, for an individual food, only a subset of those methods can be selected.
 */
@Collection
@CollectionLayout(
        hidden = Where.ALL_TABLES,
        sequence = "0.2",
        describedAs = "Quantification Method Pathways in effect associated with this individual food.\n\n"
                + "With QuantificationMethodForFoodGroup (table QM_GROUP) a set of methods is defined "
                + "for a specific food classification.\n\n"
                + "Optionally, for an individual food, only a subset of those methods can be selected "
                + "using QuantificationMethodPathwayForFood (table QM_FOODS).")
@RequiredArgsConstructor
public class Food_effectiveQuantificationMethodPathways {

    @Inject private FoodQuantificationHelperService foodQuantificationHelperService;

    protected final Food mixee;

    @MemberSupport
    public List<QuantificationMethodPathwayForFoodGroup> coll() {

        var grouping = foodQuantificationHelperService.effectiveGroupingUsedForQuantificationPathway(mixee);

        var quantificationMethodPathwayAsDefinedByFoodClassification = foodQuantificationHelperService
                .effectiveQuantificationMethodPathwayForFoodClassification(grouping);

        // filter by selected at food level (Dependent Quantification Method Pathway For Food mapped by Food)
        var quantificationMethodPathwayForFood = foodQuantificationHelperService.listQuantificationMethodPathwayForFood(mixee);
        if(quantificationMethodPathwayForFood.isEmpty()) {
            return quantificationMethodPathwayAsDefinedByFoodClassification;
        }
        final Set<QuantificationMethodPathwayKey> selectedKeys = quantificationMethodPathwayForFood.stream()
            .map(QuantificationMethodPathwayKey::valueOf)
            .collect(Collectors.toSet());
        return quantificationMethodPathwayAsDefinedByFoodClassification.stream()
                .filter(qmp->selectedKeys.contains(QuantificationMethodPathwayKey.valueOf(qmp)))
                //TODO ordering?
                .toList();
    }

}
