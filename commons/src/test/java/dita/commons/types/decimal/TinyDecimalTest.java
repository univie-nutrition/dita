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
package dita.commons.types.decimal;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;

class TinyDecimalTest {

    @RequiredArgsConstructor
    enum Scenario {
        NAN(Integer.MAX_VALUE),
        D0(0),
        D3(3),
        DM3(-3),
        D6(6),
        DM6(-6),
        ;
        final int exponent;
        BigDecimal bigDecimal(final long significand) {
            return exponent==Integer.MAX_VALUE
                    ? null
                    : new BigDecimal(significand).scaleByPowerOfTen(exponent);
        }
    }

    @ParameterizedTest
    @EnumSource(Scenario.class)
    void test(final Scenario scenario) {

        var bd = scenario.bigDecimal(1234567890);

        long encoded = TinyDecimals.toLongExact(bd);
        var bdAfterRoundTrip = TinyDecimals.toBigDecimal(encoded);

        assertEquals(bd, bdAfterRoundTrip);

        System.err.printf("%s->%s%n", bd, bdAfterRoundTrip);
    }
}
