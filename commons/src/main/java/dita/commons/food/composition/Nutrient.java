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

import org.apache.causeway.applib.annotation.Title;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.types.MetricUnits;
import tech.units.indriya.AbstractUnit;
import tech.units.indriya.unit.Units;

/**
 * Represents a nutrient or other food component. 
 * A food component may or may not be quantity related ().
 */
public record Nutrient(
        SemanticIdentifier componentId,
        ComponentUnit componentUnit) {
    
    @RequiredArgsConstructor
    public enum ComponentUnit {
        ONE("𝟙", AbstractUnit.ONE),
        RATIO("⅟₁", AbstractUnit.ONE),
        CALORIES("cal", MetricUnits.CALORIES),
        JOUL("J", Units.JOULE),
        GRAM("g", Units.GRAM),
        BREAD_EXCHANGE("BE", MetricUnits.BREAD_EXCHANGE)
        ;

        final String display;
        @Getter final javax.measure.Unit<?> unit;

        public static ComponentUnit parse(final String x){
            switch (x) {
            case "BE": return BREAD_EXCHANGE;
            case "cal": return CALORIES;
            case "J": return JOUL;
            case "g": return GRAM;
            case "𝟙": 
            case "1": return ONE;
            case "⅟₁": 
            case "/": return RATIO;
            default: throw new IllegalArgumentException("Unknown unit: " + x);
            }
        }

        @Title
        public String shortForm() {
            return display;
        }

        public boolean isGram() { return this==GRAM; }
        public boolean isQuantityRelated() { return this!=RATIO; }

    }
}
