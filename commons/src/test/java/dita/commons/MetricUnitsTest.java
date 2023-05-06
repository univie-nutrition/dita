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
package dita.commons;

import java.io.Serializable;

import javax.measure.Quantity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.resources._Serializables;

import dita.commons.types.MetricUnits;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.Accessors;
import tech.units.indriya.unit.Units;

class MetricUnitsTest {

    @RequiredArgsConstructor
    @Getter @Accessors(fluent=true)
    enum Scenario {
        ONE(MetricUnits.one(), "1.000", Double.NaN),
        MATH_PI(MetricUnits.dimensionless(Math.PI), "3.142", Double.NaN),
        G(MetricUnits.grams(Math.PI), "3.142g", Math.PI),
        L(MetricUnits.litres(Math.PI), "3.142l", Double.NaN),
        MILLI_G(MetricUnits.milliGrams(Math.PI), "3.142mg", Math.PI * 0.001),
        MILLI_L(MetricUnits.milliLitres(Math.PI), "3.142ml", Double.NaN),
        ;
        final Quantity<?> quantity;
        final String expectedFormat;
        final double expectedAsGrams;
        void assertValidFormat() {
            assertEquals(expectedFormat(), MetricUnits.formatted(quantity()));
        }
        void assertValidSerialization() {
            val q = (Serializable)quantity();
            assertEquals(q, roundtrip(q));
        }
        void assertValidConversion() {
            assertFalse(MetricUnits.asUnit(quantity(), Units.LUX).isPresent());
            assertEquals(expectedAsGrams(),
                MetricUnits.asUnit(quantity(), Units.GRAM)
                .map(Quantity::getValue)
                .map(Number::doubleValue)
                .orElse(Double.NaN),
                1E-12);
        }

        // -- HELPER

        @SneakyThrows
        private static <T extends Serializable> T roundtrip(final T object) {
            val bytes = _Serializables.write(object);
            return _Casts.uncheckedCast(
                    _Serializables.read(object.getClass(), bytes));
        }
    }

    @ParameterizedTest
    @EnumSource
    void testFormats(final Scenario scenario) {
        scenario.assertValidFormat();
    }

    @ParameterizedTest
    @EnumSource
    void testSerialization(final Scenario scenario) {
        scenario.assertValidSerialization();
    }

    @ParameterizedTest
    @EnumSource
    void testConversion(final Scenario scenario) {
        scenario.assertValidConversion();
    }

}
