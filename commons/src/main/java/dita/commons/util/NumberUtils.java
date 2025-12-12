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
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.internal.assertions._Assert;

import lombok.experimental.UtilityClass;

import tech.units.indriya.function.DefaultNumberSystem;
import tech.units.indriya.internal.function.Calculator;

@UtilityClass
public class NumberUtils {

    /**
     * double equality by value relaxed by epsilon
     * (also considering NaN and +/- Infinity)
     */
    public boolean numberEquals(final double a, final double b, final double epsilon) {
        _Assert.assertTrue(Double.isFinite(epsilon));
        _Assert.assertTrue(epsilon >= 0.0);

        if(Double.isFinite(a)) {
            if(Double.isFinite(b))
				// abs(a-b) might actually overflow Double.MAX_VALUE, which results in Double.POSITIVE_INFINITY
                return Math.abs(a - b) <= epsilon;
            // as is finite, but b is not
            return false;
        }
        // a is not finite, but b is
        if(Double.isFinite(b)) return false;

        // a and b are not finite
        return Double.doubleToLongBits(a) == Double.doubleToLongBits(b);
    }

    /**
     * Checks element-wise equality based on {@link #numberEquals(double, double, double)}.
     * (empty arrays are considered equal)
     */
    public boolean numberArrayEquals(@Nullable final double[] a, @Nullable final double[] b, final double epsilon) {
        if(a==null || a.length==0) {
            if(b==null || b.length==0) return true;

            // a is empty, but b is not
            return false;
        }
        // a is not empty, but b is
        if(b==null || b.length==0) return false;
        if(a.length!=b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if(!numberEquals(a[i], b[i], epsilon)) return false;
        }
        return true;
    }

    // -- CONVERSION

    public BigDecimal toBigDecimal(final Number number) {
        if(number instanceof BigDecimal bigd) return bigd;
        if(number instanceof BigInteger bigint) return new BigDecimal(bigint);
        if(number instanceof Long ||
                number instanceof AtomicLong ||
                number instanceof Integer ||
                number instanceof AtomicInteger ||
                number instanceof Short ||
                number instanceof Byte)
			return BigDecimal.valueOf(number.longValue());
        if(number instanceof Double || number instanceof Float)
			return BigDecimal.valueOf(number.doubleValue());
        throw new IllegalArgumentException("unsupported number type " + number.getClass());
    }

    // -- ROUNDING

    /**
     * For numbers that are between one and minus one, we at most keep decimalPlaces significant fractional decimal places.
     * Preserves {@code null}
     */
    public static BigDecimal reducedPrecision(BigDecimal decimal, final int decimalPlaces) {
        if(decimal==null) return null;
        decimal = decimal.stripTrailingZeros();

        // If zero or positive, the scale is the number of digits to the right of the decimal point.
        if(decimal.scale()<=decimalPlaces) return decimal;

        var abs = decimal.abs();
        if(BigDecimal.ONE.compareTo(abs)==1) {
            // The precision is the number of digits in the unscaled value.
            int stripableDigitCount = decimal.precision() - decimalPlaces;
            return stripableDigitCount>0
                ? decimal.setScale(decimal.scale() - stripableDigitCount, RoundingMode.HALF_UP)
                : decimal;
        }

        return decimal.scale()>decimalPlaces
                ? decimal.setScale(decimalPlaces, RoundingMode.HALF_UP)
                : decimal;
    }

    /** Preserves {@code null} */
    public BigDecimal roundToNDecimalPlaces(final @Nullable BigDecimal value, final int decimalPlaces) {
        return value==null
                ? null
                : value.scale()>decimalPlaces // If zero or positive, the scale is the number of digits to the right of the decimal point.
                    ? value.setScale(decimalPlaces, RoundingMode.HALF_UP)
                    : value;
    }

    /** Preserves {@code null} */
    public BigDecimal stripTrailingZeros(final @Nullable BigDecimal value) {
        return value!=null
                ? value.stripTrailingZeros()
                : null;
    }

    public String roundedToScaleNoTrailingZeros(
            final double x, final int scale) {
        return roundedToScaleNoTrailingZeros(x, scale, Optional.empty());
    }

    public String roundedToScaleNoTrailingZeros(
            final double x, final int scale,
            final Optional<Locale> locale) {

        var decimal = BigDecimal.valueOf(x)
                .setScale(scale, RoundingMode.HALF_UP);

        final char decimalSeparator = locale
                .map(DecimalFormatSymbols::new)
                .map(DecimalFormatSymbols::getDecimalSeparator)
                .orElse('.');

        var plainString = decimal.stripTrailingZeros().toPlainString();

        return decimalSeparator=='.'
                ? plainString
                : plainString.replace('.', decimalSeparator);
    }

    // -- FORMATTING

    private DecimalFormat DF3 = new DecimalFormat("###.###",DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    {
        DF3.setRoundingMode(RoundingMode.HALF_UP);
    }

    private DefaultNumberSystem DFNS = new DefaultNumberSystem();

    public String scientificFormat(@Nullable final Number number){
        return scientificFormat(number, "");
    }

    public String scientificFormat(@Nullable final Number number, final String suffixDelimiter){
        if(number==null) return "null";

        if(number instanceof Double d) {
            if(d==0.) return "0";
            if(Double.isNaN(d)) return "NaN";
            if(Double.isInfinite(d)) return "Infinite";
        }
        if(number instanceof Float f) {
            if(f==0.f) return "0";
            if(Float.isNaN(f)) return "NaN";
            if(Float.isInfinite(f)) return "Infinite";
        }

        var abs = DFNS.abs(number);

        final int exp = log10(abs);
        final int exp3 = DFNS.isLessThanOne(abs)
                ? ((exp/3)*3) - 3
                : (exp/3)*3;

        final String suffix = switch (exp3) {
            case 0 -> "";
            case 3 -> "k";
            case 6 -> "M";
            case 9 -> "G";
            case 12 -> "T";
            case 15 -> "P";
            case 18 -> "E";
            case -3 -> "m";
            case -6 -> "µ";
            case -9 -> "n";
            case -12 -> "p";
            case -15 -> "f";
            case -18 -> "a";
            default  -> "E" + (exp3>0 ? "+" : "" ) + exp3;
        };

        final double fraction = Calculator.of(number)
                .multiply(Math.pow(10, -exp3))
                .peek()
                .doubleValue();
        return DF3.format(fraction) + suffixDelimiter + suffix;
    }

    public int log10(final Number number) {
        return (int)(Math.log10(number.doubleValue()));
    }

    public String percentageFormat(@Nullable final Number x) {
        if(x==null) return "NaN%";
        var asDouble = x.doubleValue();
        return Double.isFinite(asDouble)
                ? String.format("%.1f", 100. * asDouble) + "%"
                : "NaN%";
    }

}
