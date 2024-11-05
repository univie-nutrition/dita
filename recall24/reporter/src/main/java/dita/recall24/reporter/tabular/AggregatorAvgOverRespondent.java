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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dita.commons.types.DecimalVector;
import dita.recall24.reporter.dom.ConsumptionRecord;
import dita.recall24.reporter.dom.ConsumptionRecord.ConsumptionRecordBuilder;
record AggregatorAvgOverRespondent() {

    /**
     * Takes output from {@link AggregatorSumOverInterview} and calculates averages, grouped by respondentOrdinal.
     */
    Iterable<ConsumptionRecord> apply(final Stream<ConsumptionRecord> consumptions) {
        return consumptions
            .collect(Collectors.groupingBy(ConsumptionRecord::respondentOrdinal, TreeMap::new, Collectors.toList()))
            .entrySet().stream()
            .map(e->avgOverInterviews(e.getKey(), e.getValue()))
            .flatMap(Optional::stream)
            .toList();
    }

    // -- HELPER

    private Optional<ConsumptionRecord> avgOverInterviews(final int primaryMealOrdinal, final List<ConsumptionRecord> consumptions) {
        if(consumptions.isEmpty()) return Optional.empty();
        var builder = Aggregator.builder(consumptions.getFirst());
        if(consumptions.size()>1) {
            // first sum all up
            consumptions.stream()
                .skip(1)
                .forEach(c->accumulateInterviewSum(builder, c));

            // then divide by interview count
            var fraction = new BigDecimal(1./consumptions.size());

            var acc = builder.build();
            builder.quantity(acc.quantity().multiply(fraction));

            if(!acc.nutrients().isEmpty()) {
                builder
                    .nutrients(acc.nutrients().multiply(fraction));
            }
        }
        return Optional.of(builder.build());
    }

    private void accumulateInterviewSum(final ConsumptionRecordBuilder builder, final ConsumptionRecord consumption) {
        var acc = builder.build();
        builder
            .interviewOrdinal(0)
            .consumptionDate(acc.consumptionDate()) // keep first
            .fco(":avg")
            .poc(":avg")
            .meal(":avg")
            .mealOrdinal(":avg")
            .recordType("AVG")
            .food(":avg")
            .foodId(":avg")
            .groupId(":avg")
            .facetIds(":avg")
            .quantity(acc.quantity().add(consumption.quantity()));

        if(Aggregator.isNutMappingWorkInProress(acc)
                || Aggregator.isNutMappingWorkInProress(consumption)) {
            builder
                .fcdbId(":WIP")
                .nutrients(DecimalVector.empty());
        } else {
            builder
                .fcdbId(":avg")
                .nutrients(acc.nutrients().add(consumption.nutrients()));
        }
    }

}
