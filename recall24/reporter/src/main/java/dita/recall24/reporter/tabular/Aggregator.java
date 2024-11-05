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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.food.composition.FoodComponent;
import dita.commons.types.DecimalVector;
import dita.recall24.reporter.dom.ConsumptionRecord;
import dita.recall24.reporter.dom.ConsumptionRecord.ConsumptionRecordBuilder;
import dita.recall24.reporter.tabular.TabularReporters.Aggregation;

record Aggregator(
        Can<FoodComponent> foodComponents,
        Aggregation aggregation) {

    public Iterable<ConsumptionRecord> apply(final List<ConsumptionRecord> consumptions) {
        if(consumptions.isEmpty()) return consumptions;
        var consumptionStream = consumptions.stream().filter(this::canAggregate);
        return switch (aggregation) {
            case NONE -> consumptions; // identity operation
            case MEAL -> meal(consumptionStream);
            case INTERVIEW -> interview(consumptionStream);
            case RESPONDENT_AVERAGE -> respondent(consumptionStream);
            default->
                throw new IllegalArgumentException("Unexpected value: " + aggregation);
        };
    }

    // -- HELPER - MEAL

    /**
     * Sum grouped by primaryMealOrdinal.
     */
    private Iterable<ConsumptionRecord> meal(final Stream<ConsumptionRecord> consumptions) {
        return consumptions
            .collect(Collectors.groupingBy(this::parsePrimaryMealOrdinal, TreeMap::new, Collectors.toList()))
            .entrySet().stream()
            .map(e->sumOverMeals(e.getKey(), e.getValue()))
            .flatMap(Optional::stream)
            .toList();
    }

    private Optional<ConsumptionRecord> sumOverMeals(final int primaryMealOrdinal, final List<ConsumptionRecord> consumptions) {
        if(consumptions.isEmpty()) return Optional.empty();
        var builder = builder(consumptions.getFirst())
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

        if(isNutMappingWorkInProress(acc)
                || isNutMappingWorkInProress(consumption)) {
            builder
                .fcdbId(":WIP")
                .nutrients(DecimalVector.empty());
        } else {
            builder
                .fcdbId(":sum")
                .nutrients(acc.nutrients().add(consumption.nutrients()));
        }
    }

    // -- HELPER - INTERVIEW

    private Iterable<ConsumptionRecord> interview(final Stream<ConsumptionRecord> consumptions) {
        var aggr = new ArrayList<ConsumptionRecord>();

        // TODO Auto-generated method stub
        return aggr;
    }

    // -- HELPER - RESPONDENT

    private Iterable<ConsumptionRecord> respondent(final Stream<ConsumptionRecord> consumptions) {
        var aggr = new ArrayList<ConsumptionRecord>();

        // TODO Auto-generated method stub
        return aggr;
    }

    // -- OTHER

    private boolean canAggregate(final ConsumptionRecord consumption) {
        return switch(consumption.recordType()) {
            case "COMPOSITE" -> false;
            case "COMMENT" -> false;
            default -> true;
        };
    }

    private int parsePrimaryMealOrdinal(final ConsumptionRecord consumption) {
        _Assert.assertNotEmpty(consumption.mealOrdinal());
        return Integer.valueOf(TextUtils.cutter(consumption.mealOrdinal()).keepBefore(".").getValue());
    }

    private boolean isNutMappingWorkInProress(final ConsumptionRecord consumption) {
        return isWorkInProress(consumption.fcdbId());
    }

    private boolean isWorkInProress(final String sid) {
        return ":WIP".equals(sid);
    }

    private ConsumptionRecordBuilder builder(final ConsumptionRecord consumption) {
        var builder = ConsumptionRecord.builder()
            .respondentOrdinal(consumption.respondentOrdinal())
            .respondentAlias(consumption.respondentAlias())
            .respondentSex(consumption.respondentSex())
            .respondentAge(consumption.respondentAge())
            .interviewCount(consumption.interviewCount())
            .interviewOrdinal(consumption.interviewOrdinal())
            .consumptionDate(consumption.consumptionDate())
            .fco(consumption.fco())
            .poc(consumption.poc())
            .meal(consumption.meal())
            .mealOrdinal(consumption.mealOrdinal())
            .recordType(consumption.recordType())
            .food(consumption.food())
            .foodId(consumption.foodId())
            .groupId(consumption.groupId())
            .facetIds(consumption.facetIds())
            .quantity(consumption.quantity())
            .fcdbId(consumption.fcdbId())
            .nutrients(consumption.nutrients());
        return builder;
    }

}
