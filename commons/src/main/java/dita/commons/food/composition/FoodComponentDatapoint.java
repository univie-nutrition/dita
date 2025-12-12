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

import org.jspecify.annotations.NonNull;

import lombok.RequiredArgsConstructor;

import dita.commons.food.composition.FoodComponent.ComponentUnit;
import dita.commons.food.composition.FoodComposition.ConcentrationUnit;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.util.NumberUtils;

/**
 * Represents a measured or calculated value for the relative amount of a chemical substance
 * or other food component or simply a food specific fixed value like 'protein animal to plant ratio'.
 */
public record FoodComponentDatapoint(
        FoodComponent component,
        /**
         * Nature of how to quantify the amount of dietary components consumed for this associated food (or product).
         * @apiNote always same as in parent {@link FoodComposition}, provided here for convenience
         */
        ConcentrationUnit concentrationUnit,
        /**
         * Nature of how the datapoint is interpreted (as-is or as upper-bound).
         */
        DatapointSemantic datapointSemantic,
        BigDecimal datapointValue) {

	public FoodComponentDatapoint {
		datapointValue = NumberUtils.stripTrailingZeros(datapointValue);
	}

    /**
     * How the datapoint is interpreted (as-is or as upper-bound).
     */
    @RequiredArgsConstructor
    public enum DatapointSemantic {
        /**
         * The datapoint directly represents the (measured or calculated) value.
         */
        AS_IS,
        /**
         * The datapoint is an upper bound for the real value.
         */
        UPPER_BOUND;
    }

    public SemanticIdentifier componentId() {
        return component.componentId();
    }

    public boolean isCommensurable(
            final @NonNull FoodConsumption consumption) {
        return component.componentUnit().isInvariantWithRespectToAmountConusmed()
                ? true
                : concentrationUnit.isCommensurable(consumption);
    }

    /**
     * Whether or not given {@code amountConusmed} is used to quantify the result,
     * depends on the {@link ComponentUnit} of underlying {@link FoodComponent}.
     * Some of these may represent a ratio or percentage, that is independent of the amount consumed.
     */
    public FoodComponentQuantified quantify(
            final @NonNull FoodConsumption consumption) {
        var amount = component.componentUnit().isInvariantWithRespectToAmountConusmed()
                ? datapointValue
                : concentrationUnit.multiply(consumption, datapointValue);
        return new FoodComponentQuantified(component, component.componentUnit().quantity(amount));
    }

    public double quantifyAsDouble(
            final @NonNull FoodConsumption consumption) {
        return component.componentUnit().isInvariantWithRespectToAmountConusmed()
                ? datapointValue.doubleValue()
                : concentrationUnit.multiplyAsDouble(consumption, datapointValue);
    }

}
