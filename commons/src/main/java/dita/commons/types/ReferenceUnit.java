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
package dita.commons.types;

import java.util.Optional;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;

import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

/**
 * Food components are quantified in {@link QuantificationUnit} (units of parts, mass or volume)
 * per <i>ReferenceUnit</i>.
 * @see QuantificationUnit
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReferenceUnit {
    PER_PART("perPart", "/part", Quantities.getQuantity(1., MetricUnits.PARTS.inverse())),
    PER_100_GRAM("per100Gram", "/100g", Quantities.getQuantity(0.01, Units.GRAM.inverse())),
    PER_100_MILLILITER("per100Milliliter", "/100ml", Quantities.getQuantity(0.01, MetricPrefix.MILLI(Units.LITRE).inverse()));

    /**
     * for persistence use with
     * <pre>
        @Extension(vendorName="datanucleus", key="enum-value-getter", value="stringified")
        @Extension(vendorName="datanucleus", key="enum-check-constraint", value="true")
     * </pre>
     */
    private final String stringified;
    private final String title;
    private final Quantity<?> metricQuantity;

    // -- UTILITY

    @Deprecated
    @Nullable
    public static Quantity<?> toMetricQuantity(@Nullable final ReferenceUnit refUnit) {
        return refUnit != null
                ? refUnit.metricQuantity()
                : null;
    }

    public static Optional<Quantity<?>> asMetricQuantity(@Nullable final ReferenceUnit refUnit) {
        return Optional.ofNullable(refUnit)
                .map(ReferenceUnit::metricQuantity);
    }
}
