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
package dita.commons.util;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;

class NumberUtilsTest {

    // -- FORMAT

    @RequiredArgsConstructor
    enum Scenario {
        NULL(null, "null"),
        DOUBLE_NAN(Double.NaN, "NaN"),
        DOUBLE_PI(Math.PI, "3.142"),
        DOUBLE_NEG_PI(-Math.PI, "-3.142"),
        FLOAT_PI((float)Math.PI, "3.142"),
        FLOAT_NEG_PI((float)-Math.PI, "-3.142"),
        LONG(Long.MAX_VALUE, "9.223E"), //9_223_372_036_854_775_807 9.223 x 10^18 Exa
        NEG_LONG(Long.MIN_VALUE, "-9.223E"),
        INT(Integer.MAX_VALUE, "2.147G"), //2_147_483_647 Giga
        NEG_INT(Integer.MIN_VALUE, "-2.147G"),
        BIG_DEC_ONE(BigDecimal.ONE, "1"),
        BIG_DEC_KILO(new BigDecimal("123456.789"), "123.457k"),
        BIG_DEC_MICRO(new BigDecimal("0.000123456789"), "123.457µ"),
        BIG_DEC_NEG_KILO(new BigDecimal("-123456.789"), "-123.457k"),
        BIG_DEC_NEG_MICRO(new BigDecimal("-0.000123456789"), "-123.457µ");

        final Number number;
        final String formatExpectation;
    }

    @ParameterizedTest
    @EnumSource(Scenario.class)
    void scientificFormat(final Scenario scenario) {
        assertEquals(scenario.formatExpectation, NumberUtils.scientificFormat(scenario.number));
    }

    // -- ROUNDING

    @RequiredArgsConstructor
    enum Scenario2 {
        NULL(null, "null"),
        A(new BigDecimal("123456.789"), "123456.79"),
        B(new BigDecimal("-123456.789"), "-123456.79"),
        C(new BigDecimal("0.000123456789"), "0.00012"),
        D(new BigDecimal("-0.000123456789"), "-0.00012"),
        E(new BigDecimal("1.23"), "1.23"),
        F(new BigDecimal("0.23"), "0.23"),
        G(new BigDecimal("1.00"), "1"),
        H(new BigDecimal("0.20"), "0.2");

        final BigDecimal number;
        final String formatExpectation;
    }


    @ParameterizedTest
    @EnumSource(Scenario2.class)
    void reducedPrecision(final Scenario2 scenario) {
        assertEquals(scenario.formatExpectation, "" + NumberUtils.reducedPrecision(scenario.number, 2));
    }

}
