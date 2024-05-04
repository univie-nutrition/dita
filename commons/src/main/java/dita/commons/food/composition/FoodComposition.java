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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.assertions._Assert;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumption.ConsumptionQuantification;
import dita.commons.sid.SemanticIdentifier;

public record FoodComposition(
        @NonNull SemanticIdentifier foodId,
        /**
         * Nature of how to quantify the amount of dietary components consumed for this associated food (or product).
         */
        @NonNull CompositionQuantification compositionQuantification,
        @NonNull Map<SemanticIdentifier, FoodComponentDatapoint> datapoints) {

    /**
     * Nature of how to quantify the amount of dietary components consumed for the associated food (or product).
     */
    @RequiredArgsConstructor
    public enum CompositionQuantification {
        /**
         * Datapoint values are given as per 100g (consumed).
         */
        PER_100_GRAM(ConsumptionQuantification.GRAM),
        /**
         * Datapoint values are given as per 100ml (consumed).
         */
        PER_100_ML(ConsumptionQuantification.MILLILITER),
        /**
         * Datapoint values are given as per part (consumed).
         * e.g. dietary supplement tablets
         */
        PER_PART(ConsumptionQuantification.PART);

        private final ConsumptionQuantification expectedConsumptionQuantification;

        /**
         * Whether given consumption has the expected metric unit for quantification to succeed.
         */
        public boolean isCommensurable(final @NonNull FoodConsumption consumption) {
            return expectedConsumptionQuantification.equals(consumption.consumptionQuantification());
        }

        BigDecimal multiply(final @NonNull FoodConsumption consumption, final @NonNull BigDecimal datapointValue) {
            _Assert.assertEquals(expectedConsumptionQuantification, consumption.consumptionQuantification(),
                    ()->"consumption has incommensurable unit");
            return switch (this) {
            case PER_100_GRAM -> consumption.amountConsumed().multiply(datapointValue).scaleByPowerOfTen(-2);
            case PER_100_ML -> consumption.amountConsumed().multiply(datapointValue).scaleByPowerOfTen(-2);
            case PER_PART -> consumption.amountConsumed().multiply(datapointValue);
            };
        }

    }

    public Optional<FoodComponentDatapoint> datapoint(final @Nullable SemanticIdentifier componentId) {
        return Optional.ofNullable(componentId)
                .map(datapoints::get);
    }

    public Stream<FoodComponentDatapoint> streamDatapoints() {
        return datapoints.values().stream();
    }

}
