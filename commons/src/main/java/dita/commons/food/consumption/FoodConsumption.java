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

import lombok.RequiredArgsConstructor;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.QuantificationUnit;

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

    //TODO[dita-commons] perhaps consolidate those 2 enums
    /**
     * Unit of amount consumed.
     */
    @RequiredArgsConstructor
    public enum ConsumptionUnit {
        /**
         * Amount consumed is given in gram.
         */
        GRAM(QuantificationUnit.MASS_IN_GRAM),
        /**
         * Amount consumed is given in milliliter.
         */
        MILLILITER(QuantificationUnit.VOLUME_IN_MILLILITER),
        /**
         * Amount consumed is given in parts (e.g. tablets).
         */
        PART(QuantificationUnit.PARTS);
        public final QuantificationUnit quantificationUnit;

        // -- FORMATTING

        public String format(final Number amount) {
            return quantificationUnit.format(amount);
        }
    }

    public QualifiedMap.QualifiedMapKey qualifiedMapKey() {
        return new QualifiedMapKey(foodId, facetIds);
    }

}
