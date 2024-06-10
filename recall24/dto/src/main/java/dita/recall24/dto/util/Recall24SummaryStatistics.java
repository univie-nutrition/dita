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
package dita.recall24.dto.util;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.collections._Multimaps;

import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;

public record Recall24SummaryStatistics(
        Record24SummaryStatistics recordStats,
        Consumption24SummaryStatistics consumptionStats) {

    public Recall24SummaryStatistics() {
            this(new Record24SummaryStatistics(), new Consumption24SummaryStatistics());
    }

    public void accept(final RecallNode24 node24) {
        switch (node24) {
        case Record24.Dto rec -> {
            recordStats.accept(rec);
            if(rec instanceof Record24.Consumption consumption) {
                consumptionStats.accept(consumption);
            }
        }
        default -> {}
        }
    }

    public String formatted() {
        return Stream.of(
                recordStats.formatted(),
                consumptionStats.formatted())
            .collect(Collectors.joining("\n"));
    }

    // -- SUB TYPES

    public record Record24SummaryStatistics(
            LongAdder recordCount,
            LongAdder foodCount,
            LongAdder fryingFatCount,
            LongAdder compositeCount,
            LongAdder informalCount,
            LongAdder productCount) {
        public Record24SummaryStatistics() {
            this(nla(), nla(), nla(), nla(), nla(), nla());
        }
        public void accept(final Record24.Dto rec) {
            recordCount.increment();
            switch (rec) {
            case Record24.Product prod -> productCount.increment();
            case Record24.Food food -> foodCount.increment();
            case Record24.FryingFat fryingFat -> fryingFatCount.increment();
            case Record24.Composite comp -> compositeCount.increment();
            case Record24.TypeOfFatUsed info -> informalCount.increment();
            case Record24.TypeOfMilkOrLiquidUsed info -> informalCount.increment();
            }
        }
        public String formatted() {
            return String.format("records: %d (food: %d, fat: %d, comp: %d, prod: %d, info: %d)",
                    recordCount.longValue(),
                    foodCount.longValue(), fryingFatCount.longValue(),
                    compositeCount.longValue(),
                    productCount.longValue(),
                    informalCount.longValue());
        }
    }

    public record Consumption24SummaryStatistics(
            LongAdder consumptionCount,
            LongAdder mappedCount,
            Map<QualifiedMapKey, MappingTodo> mappingTodoByKey) {
        public Consumption24SummaryStatistics() {
            this(nla(), nla(), new TreeMap<QualifiedMapKey, MappingTodo>());
        }
        public void accept(final Record24.Consumption consumption) {
            consumptionCount.increment();
        }
        public void collectMappingTodo(final MappingTodo todo) {
            mappingTodoByKey.put(todo.mapKey(), todo);
        }
        public String formatted() {
            return String.format("consumptions: %d (unmapped total: %d, unmapped unique: %d)",
                    consumptionCount.longValue(),
                    consumptionCount.longValue() - mappedCount.longValue(),
                    mappingTodoByKey.size());
        }
        public String reportMappingTodos() {
            return mappingTodoByKey.values().stream()
                    .map(MappingTodo::formatted)
                    .collect(Collectors.joining("\n"));
        }
    }

    public record MappingTodo(
            QualifiedMapKey mapKey,
            Can<QualifiedMapEntry> similar) {

        public String formatted() {

            // group all similar map-entries by target
            var similarByTarget = _Multimaps.<SemanticIdentifier, SemanticIdentifierSet>newListMultimap();
            similar.stream()
                .forEach(s->similarByTarget.putElement(s.target(), s.qualifier()));

            var similarFormatted = similarByTarget.entrySet().stream()
                .map(multiValuedEntry->{
                    var targetSid = multiValuedEntry.getKey();
                    var qualifiersFormatted = multiValuedEntry.getValue()
                        .stream()
                        .map(sidSet->String.format("    {%s}", sidSet.shortFormat(",")))
                        .sorted()
                        .toList();
                    return String.format("  - %s\n", targetSid.fullFormat(":"))
                            + qualifiersFormatted.stream()
                                .collect(Collectors.joining("\n"));
                })
                .collect(Collectors.joining("\n"));

            return mapKey.shortFormat(";", ",")
                    + (similar.isEmpty() ? "" : "\n")
                    + similarFormatted;
        }
    }

    // -- HELPER

    /** new long adder */
    private static LongAdder nla() {
        return new LongAdder();
    }

}
