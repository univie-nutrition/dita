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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import org.apache.causeway.applib.services.linking.DeepLinkService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.collections._Multimaps;

import lombok.NonNull;

import dita.commons.services.rules.RuleChecker;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup;

@Component
public class FacetDescriptorPathwayForFoodDisplayOrderRuleChecker
implements RuleChecker {

    @Inject private RepositoryService repositoryService;
    @Inject private DeepLinkService deepLinkService;

    @Override
    public String title() {
        return "Display Order of Food Facet/Descriptor Pathway";
    }
    @Override
    public String description() {
        return "Display-order entries of any category must run from 1 to n (n .. the number of entries within this category).";
    }

    @Override
    public Can<RuleViolation> check(@NonNull final Class<?> entityType) {
        if(FacetDescriptorPathwayForFood.class.equals(entityType)) {
            return checkForFood();
        }
        if(FacetDescriptorPathwayForFoodGroup.class.equals(entityType)) {
            return checkForGroup();
        }
        return Can.empty();
    }

    // -- HELPER

    private Can<RuleViolation> checkForFood() {
        var violations = new ArrayList<RuleViolation>();
        var displayOrdersByFood = _Multimaps.<String, Integer>newListMultimap();
        var urlByFoodCode = new HashMap<String, URI>();
        var entries = repositoryService.allInstances(FacetDescriptorPathwayForFood.class);
        for(var entry : entries) {
            displayOrdersByFood.putElement(entry.getFoodCode(), entry.getDisplayOrder());
            urlByFoodCode.put(entry.getFoodCode(), deepLinkService.deepLinkFor(entry));
        }
        // all descriptors of a specific food must have facet-display-order 1..n
        displayOrdersByFood.forEach((foodCode, displayOrders)->{
            satisfiesOneToN(String.format("facets of food-code %s", foodCode), displayOrders)
                .ifPresent(msg->violations.add(RuleViolation.severe(msg)
                        .withURI(urlByFoodCode.get(foodCode))));
        });
        return Can.ofCollection(violations);
    }

    private Can<RuleViolation> checkForGroup() {
        var violations = new ArrayList<RuleViolation>();
        var displayOrdersByFacet1 = _Multimaps.<String, Integer>newSetMultimap(TreeSet::new);
        var displayOrdersByFacet2 = _Multimaps.<String, Integer>newListMultimap();
        var urlByFoodGroupingPlusFacet = _Multimaps.<String, URI>newListMultimap();
        var entries = repositoryService.allInstances(FacetDescriptorPathwayForFoodGroup.class);
        for(var entry : entries) {
            var foodGroupingPlusFacet = String.format("foodGrouping=%s|%s|%s, facetCode=%s",
                    entry.getFoodGroupCode(),
                    dita.commons.format.FormatUtils.emptyToDash(entry.getFoodSubgroupCode()),
                    dita.commons.format.FormatUtils.emptyToDash(entry.getFoodSubSubgroupCode()),
                    entry.getFoodFacetCode());
            displayOrdersByFacet1.putElement(foodGroupingPlusFacet, entry.getFacetDisplayOrder());
            displayOrdersByFacet2.putElement(foodGroupingPlusFacet, entry.getDescriptorDisplayOrder());
            urlByFoodGroupingPlusFacet.putElement(foodGroupingPlusFacet, deepLinkService.deepLinkFor(entry));
        }

        // every facet code must uniquely map to a facet-display-order
        displayOrdersByFacet1.forEach((foodGroupingPlusFacet, displayOrders)->{
            if(displayOrders.size()>1) {
                violations.add(RuleViolation.severe(
                        "non unique facet-display-order for food-grouping plus facet-code %s: got %s",
                                foodGroupingPlusFacet,
                                displayOrders.stream()
                                    .map(i->""+i)
                                    .collect(Collectors.joining(","))
                                )
                        .withURIs(urlByFoodGroupingPlusFacet.get(foodGroupingPlusFacet))
                        );
            }
        });

        // all descriptors of a specific facet must have display-order 1..n
        displayOrdersByFacet2.forEach((foodGroupingPlusFacet, displayOrders)->{
            satisfiesOneToN(String.format("descriptors of food-grouping plus facet-code %s", foodGroupingPlusFacet), displayOrders)
                .ifPresent(msg->violations.add(RuleViolation.severe(msg)
                        .withURIs(urlByFoodGroupingPlusFacet.get(foodGroupingPlusFacet))
                        ));
        });

        return Can.ofCollection(violations);
    }

    private static Optional<String> satisfiesOneToN(
            final String context,
            final Collection<Integer> coll) {
        var n = _NullSafe.size(coll);
        var actual = _NullSafe.stream(coll)
            .sorted()
            .map(i->""+i)
            .collect(Collectors.joining(","));
        var expected = IntStream.rangeClosed(1, n)
            .mapToObj(i->""+i)
            .collect(Collectors.joining(","));
        if(!actual.equals(expected)) {
            return Optional.of(String.format("With %s: incomplete display orders (n=%d), got %s", context, n, actual));
        }
        return Optional.empty();
    }


}
