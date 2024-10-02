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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.commons.util.NumberUtils;
import tech.units.indriya.internal.function.Calculator;

/**
 * Food components are quantified in units of parts, mass or volume
 * per {@link ReferenceUnit}.
 * @see ReferenceUnit
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum QuantificationUnit {
    PARTS("Parts", "parts"),
    MASS_IN_GRAM("Mass [g]", "g"),
    VOLUME_IN_MILLILITER("Volume [ml]", "ml");

    private final String title;
    private final String symbol;

    // -- FORMATTING

    public String format(final Number amount) {
        if(amount==null) return "?";
        switch (this) {
        case PARTS:
            return NumberUtils.scientificFormat(amount) + " parts";
        case MASS_IN_GRAM:
            return NumberUtils.scientificFormat(amount) + " g";
        case VOLUME_IN_MILLILITER:
            return NumberUtils.scientificFormat(
                    Calculator.of(amount).multiply(0.001).peek()) + " l";
        default:
            throw new IllegalArgumentException("unknown unit");
        }
    }

}
