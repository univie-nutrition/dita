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
package dita.recall24.util;

import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dita.recall24.api.RecallNode24;
import dita.recall24.api.Record24;

public record Recall24SummaryStatistics(
        Record24SummaryStatistics recordStats) {

    public Recall24SummaryStatistics() {
            this(new Record24SummaryStatistics());
    }

    public void accept(final RecallNode24 node24) {
        switch (node24) {
        case Record24.Dto rec -> recordStats.accept(rec);
        default -> {}
        }
    }

    public String formatted() {
        return Stream.of(
                recordStats.formatted())
            .collect(Collectors.joining("\n"));
    }

    // -- SUB TYPES

    public record Record24SummaryStatistics(
            LongAdder recordCount,
            LongAdder foodCount,
            LongAdder compositeCount,
            LongAdder incompleteCount,
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
            case Record24.Composite comp -> compositeCount.increment();
            default -> {}
//            case INCOMPLETE -> incompleteCount.increment();
//            case INFORMAL -> informalCount.increment();
            }
        }
        public String formatted() {
            return String.format("records: %d (food: %d, comp: %d, prod: %d, info: %d, incomplete: %d)",
                    recordCount.longValue(), foodCount.longValue(), compositeCount.longValue(),
                    productCount.longValue(), informalCount.longValue(), incompleteCount.longValue());
        }
    }

//    public record Ingredient24SummaryStatistics(
//            LongAdder ingredientCount,
//            LongAdder mappedCount) {
//        public Ingredient24SummaryStatistics() {
//            this(nla(), nla());
//        }
//        public void accept(final Ingredient24 ingr) {
//            ingredientCount.increment();
//        }
//        public String formatted() {
//            return String.format("ingredients: %d (unmapped: %d)",
//                    ingredientCount.longValue(), ingredientCount.longValue() - mappedCount.longValue());
//        }
//    }

    // -- HELPER

    private static LongAdder nla() {
        return new LongAdder();
    }

}
