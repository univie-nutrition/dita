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

import dita.recall24.reporter.dom.ConsumptionRecord;
import dita.recall24.reporter.dom.ConsumptionRecord.ConsumptionRecordBuilder;
import dita.recall24.reporter.tabular.TabularReport.Aggregation;

record Aggregator(
        Aggregation aggregation) {

    public Iterable<ConsumptionRecord> apply(final List<ConsumptionRecord> consumptions) {
        if(consumptions.isEmpty()) return consumptions;
        var consumptionStream = consumptions.stream().filter(this::canAggregate);
        return switch (aggregation) {
            case NONE -> consumptions; // identity operation
            case MEAL -> new AggregatorSumOverMeal().apply(consumptionStream);
            case INTERVIEW -> new AggregatorSumOverInterview().apply(consumptionStream);
            case RESPONDENT_AVERAGE -> new AggregatorAvgOverRespondent()
                .apply(new AggregatorSumOverInterview().apply(consumptionStream).stream());
            default->
                throw new IllegalArgumentException("Unexpected value: " + aggregation);
        };
    }

    // -- HELPER

    private boolean canAggregate(final ConsumptionRecord consumption) {
        return switch(consumption.recordType()) {
            case "COMPOSITE" -> false;
            case "COMMENT" -> false;
            default -> true;
        };
    }

    static boolean isNutMappingWorkInProress(final ConsumptionRecord consumption) {
        return isWorkInProress(consumption.fcdbId());
    }

    static boolean isWorkInProress(final String sid) {
        return ":WIP".equals(sid);
    }

    static ConsumptionRecordBuilder builder(final ConsumptionRecord consumption) {
        var builder = ConsumptionRecord.builder()
            .respondentOrdinal(consumption.respondentOrdinal())
            .respondentAlias(consumption.respondentAlias())
            .respondentSex(consumption.respondentSex())
            .respondentAge(consumption.respondentAge())
            .interviewCount(consumption.interviewCount())
            .interviewOrdinal(consumption.interviewOrdinal())
            .consumptionDate(consumption.consumptionDate())
            .consumptionDayOfWeek(consumption.consumptionDayOfWeek())
            .wakeUpTime(consumption.wakeUpTime())
            .fco(consumption.fco())
            .poc(consumption.poc())
            .specialDay(consumption.specialDay())
            .specialDiet(consumption.specialDiet())
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
