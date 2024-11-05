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
package dita.recall24.reporter.tabular;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.types.DecimalVector;
import dita.recall24.reporter.dom.ConsumptionRecord;
import dita.recall24.reporter.dom.ConsumptionRecord.ConsumptionRecordBuilder;
record AggregatorSumOverMeal() {

    /**
     * Sum over meal, grouped by primaryMealOrdinal.
     */
    Iterable<ConsumptionRecord> apply(final Stream<ConsumptionRecord> consumptions) {
        return consumptions
            .collect(Collectors.groupingBy(this::parsePrimaryMealOrdinal, TreeMap::new, Collectors.toList()))
            .entrySet().stream()
            .map(e->sumOverMeals(e.getKey(), e.getValue()))
            .flatMap(Optional::stream)
            .toList();
    }

    // -- HELPER

    private Optional<ConsumptionRecord> sumOverMeals(final int primaryMealOrdinal, final List<ConsumptionRecord> consumptions) {
        if(consumptions.isEmpty()) return Optional.empty();
        var builder = Aggregator.builder(consumptions.getFirst())
                .mealOrdinal("" + primaryMealOrdinal);
        if(consumptions.size()>1) {
            consumptions.stream()
                .skip(1)
                .forEach(c->accumulateMealSum(builder, c));
        }
        return Optional.of(builder.build());
    }

    private void accumulateMealSum(final ConsumptionRecordBuilder builder, final ConsumptionRecord consumption) {
        var acc = builder.build();
        builder
            .recordType("SUM")
            .food(acc.food() + ", " + consumption.food())
            .foodId(":sum")
            .groupId(":sum")
            .facetIds(":sum")
            .quantity(acc.quantity().add(consumption.quantity()));

        if(Aggregator.isNutMappingWorkInProress(acc)
                || Aggregator.isNutMappingWorkInProress(consumption)) {
            builder
                .fcdbId(":WIP")
                .nutrients(DecimalVector.empty());
        } else {
            builder
                .fcdbId(":sum")
                .nutrients(acc.nutrients().add(consumption.nutrients()));
        }
    }

    private int parsePrimaryMealOrdinal(final ConsumptionRecord consumption) {
        _Assert.assertNotEmpty(consumption.mealOrdinal());
        return Integer.valueOf(TextUtils.cutter(consumption.mealOrdinal()).keepBefore(".").getValue());
    }

}
