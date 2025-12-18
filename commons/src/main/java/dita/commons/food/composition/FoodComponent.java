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
package dita.commons.food.composition;

import java.math.BigDecimal;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;

import org.apache.causeway.applib.annotation.MemberSupport;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.MetricUnits;
import dita.commons.util.FormatUtils;
import tech.units.indriya.AbstractUnit;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

/**
 * Represents a chemical substance or other food component
 * or simply a food specific value, that is independent of the consumed amount
 * e.g. 'recommended daily dosage' or 'protein animal to plant ratio'.
 */
public record FoodComponent(
        SemanticIdentifier componentId,
        MetricPrefix metricPrefix,
        ComponentUnit componentUnit,
        SemanticIdentifierSet attributes) {

    public FoodComponent {
        attributes = attributes!=null
                ? attributes
                : SemanticIdentifierSet.empty();
    }

    @RequiredArgsConstructor
    public enum ComponentUnit {
        ONE("𝟙", AbstractUnit.ONE),
        RATIO("⅟₁", AbstractUnit.ONE),
        PERCENT("%", AbstractUnit.ONE),
        CALORIES("cal", MetricUnits.CALORIES),
        JOUL("J", Units.JOULE),
        GRAM("g", Units.GRAM),
        BREAD_EXCHANGE("BE", MetricUnits.BREAD_EXCHANGE);

        public static ComponentUnit parse(final String symbol){
            return switch (symbol) {
            case "BE" -> BREAD_EXCHANGE;
            case "cal" -> CALORIES;
            case "J" -> JOUL;
            case "g" -> GRAM;
            case "𝟙", "1" -> ONE;
            case "⅟₁", "/" -> RATIO;
            case "%" -> PERCENT;
            default -> throw new IllegalArgumentException("Unknown component unit symbol: " + symbol);
            };
        }

        @Getter @Accessors(fluent=true)
        final String symbol;

        @Getter @Accessors(fluent=true)
        final javax.measure.Unit<?> unit;

        @MemberSupport
        public String title() {
            return symbol;
        }

        public boolean isGram() { return this==GRAM; }

        /**
         * Invariant with respect to amount consumed.
         * e.g. 'recommended daily dosage' or 'protein animal to plant ratio'
         */
        public boolean isInvariantWithRespectToAmountConusmed() { return this==RATIO || this==PERCENT; }

        public Quantity<?> quantity(final BigDecimal amount) {
            return Quantities.getQuantity(amount, unit);
        }

    }

    public String prefixedUnit() {
        return FormatUtils.prefixedUnit(this);
    }

}
