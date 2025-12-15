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
import java.util.Optional;
import java.util.stream.Stream;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.internal.assertions._Assert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.types.MetricUnits;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

public record FoodComposition(
        @NonNull SemanticIdentifier foodId,
        @NonNull ConcentrationUnit concentrationUnit,
        @NonNull DatapointMap datapoints) {

    /**
     * Unit in which concentration values are given.
     */
    @RequiredArgsConstructor
    public enum ConcentrationUnit {
        /**
         * Concentration values are given as per 100g (consumed).
         */
        PER_100_GRAM("/100g", ConsumptionUnit.GRAM, -2,
                Quantities.getQuantity(0.01, Units.GRAM.inverse())),
        /**
         * Concentration values are given as per 100ml (consumed).
         */
        PER_100_MILLILITER("/100ml", ConsumptionUnit.MILLILITER, -2,
                Quantities.getQuantity(0.01, MetricPrefix.MILLI(Units.LITRE).inverse())),
        /**
         * Concentration values are given as per part (consumed).
         * e.g. dietary supplement tablets
         */
        PER_PART("/part", ConsumptionUnit.PART, 0,
                Quantities.getQuantity(1., MetricUnits.PARTS.inverse()));

        @Getter @Accessors(fluent = true) private final String title;

        private final ConsumptionUnit expectedConsumptionQuantification;

        /**
         * When consumed amount is given in 'gram', then concentration data is given in 'per 100g'.
         * Likewise when 'ml' then 'per 100ml', or when 'part' then 'per part'.
         */
        private final int concentrationScale;

        @Getter @Accessors(fluent = true) private final Quantity<?> metricQuantity;

        /**
         * Whether given consumption has the expected metric unit for quantification to succeed.
         */
        public boolean isCommensurable(final @NonNull FoodConsumption consumption) {
            return expectedConsumptionQuantification.equals(consumption.consumptionUnit());
        }

        BigDecimal multiply(final @NonNull FoodConsumption consumption, final @NonNull BigDecimal datapointValue) {
            _Assert.assertEquals(expectedConsumptionQuantification, consumption.consumptionUnit(),
                    ()->"consumption has incommensurable unit");
            return consumption.amountConsumed().multiply(datapointValue).scaleByPowerOfTen(concentrationScale);
        }

        double multiplyAsDouble(final @NonNull FoodConsumption consumption, final @NonNull BigDecimal datapointValue) {
            _Assert.assertEquals(expectedConsumptionQuantification, consumption.consumptionUnit(),
                    ()->"consumption has incommensurable unit");
            return consumption.amountConsumed().doubleValue()
                    * datapointValue.scaleByPowerOfTen(concentrationScale).doubleValue();
        }

    }

    public Optional<FoodComponentDatapoint> lookupDatapoint(final @Nullable SemanticIdentifier componentId) {
        return Optional.ofNullable(componentId)
                .flatMap(datapoints::lookup);
    }

    public Stream<FoodComponentDatapoint> streamDatapoints() {
        return datapoints.values().stream();
    }

}
