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

import dita.commons.food.composition.Nutrient.ComponentUnit;

/**
 * Represents a measured or calculated value for the relative mass amount of a chemical substance
 * or other food component or simply a food specific fixed value like 'protein animal to plant ratio'.
 */
public record NutrientFraction(
        Nutrient nutrient,
        BigDecimal per100gOrFixedValue) {

    /**
     * Whether or not given {@code amountConusmed} is used to quantify the result,
     * depends on the {@link ComponentUnit} of underlying {@link Nutrient}.
     * Some of these may represent a ratio or percentage, that is independent of the amount consumed.
     */
    public NutrientQuantified quantify(final BigDecimal gramsConusmed) {
        var amount = nutrient.componentUnit().isInvariantWithRespectToAmountConusmed()
                ? per100gOrFixedValue
                : gramsConusmed.multiply(per100gOrFixedValue).scaleByPowerOfTen(-2);
        return new NutrientQuantified(nutrient, nutrient.componentUnit().quantity(amount));
    }

}
