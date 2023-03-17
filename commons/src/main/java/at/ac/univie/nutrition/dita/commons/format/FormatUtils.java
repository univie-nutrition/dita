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
package at.ac.univie.nutrition.dita.commons.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

import lombok.val;
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

        val decimal = BigDecimal.valueOf(x)
                .setScale(scale, RoundingMode.HALF_UP);

        final char decimalSeparator = locale
                .map(DecimalFormatSymbols::new)
                .map(DecimalFormatSymbols::getDecimalSeparator)
                .orElse('.');

        val plainString = decimal.stripTrailingZeros().toPlainString();

        return decimalSeparator=='.'
                ? plainString
                : plainString.replace('.', decimalSeparator);
    }
}
