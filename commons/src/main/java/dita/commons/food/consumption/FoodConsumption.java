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
package dita.commons.food.consumption;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.util.NumberUtils;
import tech.units.indriya.internal.function.Calculator;

/**
 * Represents an amount of some food or product that was consumed.
 */
public record FoodConsumption(
        String name,
        SemanticIdentifier foodId,
        SemanticIdentifierSet facetIds,
        /**
         * Amount consumed is given in units of.
         */
        ConsumptionUnit consumptionUnit,
        BigDecimal amountConsumed) {

    /**
     * Unit of amount consumed.
     */
    @RequiredArgsConstructor
    public enum ConsumptionUnit {
        /**
         * Amount consumed is given in gram.
         */
        GRAM("Mass [g]", "g"),
        /**
         * Amount consumed is given in milliliter.
         */
        MILLILITER("Volume [ml]", "ml"),
        /**
         * Amount consumed is given in parts (e.g. tablets).
         */
        PART("Parts", "parts");

        @Getter @Accessors(fluent = true) private final String title;
        @Getter @Accessors(fluent = true) private final String symbol;

        // -- FORMATTING

        public String format(final Number amount) {
            if(amount==null) return "?";
            return switch (this) {
                case PART->NumberUtils.scientificFormat(amount) + " parts";
                case GRAM->NumberUtils.scientificFormat(amount, " ") + "g";
                case MILLILITER->NumberUtils.scientificFormat(
                            Calculator.of(amount).multiply(0.001).peek(), " ") + "l";
            };
        }

    }

    public QualifiedMap.QualifiedMapKey qualifiedMapKey() {
        return new QualifiedMapKey(foodId, facetIds);
    }

}
