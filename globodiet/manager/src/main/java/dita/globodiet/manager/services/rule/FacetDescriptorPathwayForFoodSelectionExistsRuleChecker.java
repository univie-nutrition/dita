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
package dita.globodiet.manager.services.rule;

import java.util.ArrayList;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.collections._Maps;
import org.apache.causeway.commons.internal.collections._Multimaps;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import dita.commons.services.rules.RuleChecker;
import dita.globodiet.dom.params.classification.FoodGrouping.FoodGroupingKey;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.manager.services.food.FoodFacetHelperService;

@Component
@Log4j2
public class FacetDescriptorPathwayForFoodSelectionExistsRuleChecker
implements RuleChecker {

    @Inject private RepositoryService repositoryService;
    @Inject private FoodFacetHelperService foodFacetHelperService;

    @Override
    public String title() {
        return "Selection of Food Facet/Descriptor Pathway is valid.";
    }
    @Override
    public String description() {
        return "Facets selected for Food Facet/Descriptor Pathway must exist within "
                + "associated (effective) Facet/Descriptor Pathway For Food Group";
    }

    @Override
    public Can<RuleViolation> check(@NonNull final Class<?> entityType) {
        if(FacetDescriptorPathwayForFood.class.equals(entityType)) {
            return checkForFood();
        }
        return Can.empty();
    }

    // -- HELPER

    private Can<RuleViolation> checkForFood() {
        var violations = new ArrayList<RuleViolation>();

        var foodGroupingKeyByFoodKey = _Maps.<String, FoodGroupingKey>newHashMap();
        var facetCodesAllowedByFoodGroupingKey = _Multimaps.<FoodGroupingKey, String>newSetMultimap();
        var facetCodesAllowedByFoodKey = _Multimaps.<String, String>newSetMultimap();

        var foodEntries = Can.ofCollection(repositoryService.allInstances(Food.class));
        log.info("Generating multimap 'facetCodesAllowedByFoodKey'.");
        int progress = 0;
        for(var partition : foodEntries.partitionOuterBound(10)) {
            for(var food : partition) {
                var grouping = foodFacetHelperService.effectiveGroupingUsedForFacetDescriptorPathway(food);
                var groupingKey = grouping.groupingKey();
                foodGroupingKeyByFoodKey.put(food.getCode(), groupingKey);
                if(facetCodesAllowedByFoodGroupingKey.get(groupingKey)==null) {
                    foodFacetHelperService.facetCodesAllowedForFoodGrouping(grouping)
                        .forEach(facetCode->facetCodesAllowedByFoodGroupingKey.putElement(groupingKey, facetCode));
                }
                facetCodesAllowedByFoodGroupingKey.getOrElseEmpty(groupingKey).forEach(facetCode->
                    facetCodesAllowedByFoodKey.putElement(food.getCode(), facetCode));
            }
            progress+=10;
            log.info("progress {}%", progress);
        }
        log.info("done.");

        var entries = repositoryService.allInstances(FacetDescriptorPathwayForFood.class);
        for(var entry : entries) {
            var referencedFacetCode = entry.getSelectedFoodFacetCode();
            if(referencedFacetCode==null) continue; //TODO should we report?
            var allowedFacetCodes = facetCodesAllowedByFoodKey.getOrElseEmpty(entry.getFoodCode());
            // assert referencedFacetCode exists within effective group
            if(!allowedFacetCodes.contains(referencedFacetCode)) {
                violations.add(RuleViolation.severe("For food %s, selected food facet code %s is not declared in"
                        + " associated (effective) facet/descriptor pathway for food group (%s)",
                        entry.getFoodCode(),
                        referencedFacetCode,
                        allowedFacetCodes
                            .stream()
                            .sorted()
                            .collect(Collectors.joining(","))));
            }
        }
        return Can.ofCollection(violations);
    }

}
