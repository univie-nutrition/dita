package dita.commons.util;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;

class NumberUtilsTest {

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
        BIG_DEC_NEG_MICRO(new BigDecimal("-0.000123456789"), "-123.457µ"),
        ;
        final Number number;
        final String formatExpectation;
    }

    @ParameterizedTest
    @EnumSource(Scenario.class)
    void scientificFormat(final Scenario scenario) {
        assertEquals(scenario.formatExpectation, NumberUtils.scientificFormat(scenario.number));
    }
}
