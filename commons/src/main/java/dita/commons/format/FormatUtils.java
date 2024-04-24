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
package dita.commons.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FormatUtils {

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

    public String emptyToDash(final @Nullable String input) {
        return StringUtils.hasLength(input)
                ? input
                : "-";
    }

    public String noLeadingZeros(final @Nullable String input) {
        if(!StringUtils.hasLength(input)) {
            return input;
        }
        var result = input;
        while(result.length()>1
                && result.startsWith("0")) {
            result = result.substring(1);
        }
        return result;
    }

    public String fillString(final int length, final char character) {
        var array = new char[length];
        Arrays.fill(array, character);
        return new String(array);
    }

    public String fillWithLeadingZeros(final int stringLengthYield, final @Nullable String input) {
        if(!StringUtils.hasLength(input)) {
            return fillString(stringLengthYield, '0');
        }
        final int gap = stringLengthYield - input.length();
        if(gap<1) {
            return input;
        }
        return fillString(gap, '0') + input;
    }

    // -- JAVA UTIL FORMAT

    public String safelyFormat(final @Nullable String format) {
        return _Strings.nullToEmpty(format);
    }

    public String safelyFormat(final @Nullable String format, final Object ...args) {
        return StringUtils.hasLength(format)
                ? Try.call(()->String.format(format, args))
                        .mapFailureToSuccess(ex->"message formatting error: " + ex.getMessage())
                    .getValue()
                    .orElse("")
                : "";
    }

}
