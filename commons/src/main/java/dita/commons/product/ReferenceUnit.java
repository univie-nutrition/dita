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
package dita.commons.product;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.commons.types.MetricUnits;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

@Getter @Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReferenceUnit {
	PER_PART("/part", Quantities.getQuantity(1., MetricUnits.PARTS.inverse())),
	PER_100_GRAM("/100g", Quantities.getQuantity(0.01, Units.GRAM.inverse())),
	PER_100_MILLILITER("/100ml", Quantities.getQuantity(0.01, MetricPrefix.MILLI(Units.LITRE).inverse()));

    private final String title;
	private final Quantity<?> metricQuantity;

	public static Quantity<?> toMetricQuantity(final ReferenceUnit quantificationUnit) {
		return quantificationUnit!=null
		        ? quantificationUnit.metricQuantity()
                : null;
	}

}
