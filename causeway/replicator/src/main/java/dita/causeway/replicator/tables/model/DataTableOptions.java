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
package dita.causeway.replicator.tables.model;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataTableOptions {

    public record FormatOptions(
            @NonNull String columnSeparator,
            @NonNull String nullSymbol,
            @NonNull String doubleQuoteSymbol) {

        public static FormatOptions defaults() {
            return new FormatOptions("|", "ø", "¯");
        }

        public String asCellValue(final String raw) {
            return raw==null
                    ? nullSymbol()
                    : check(raw);
        }

        // -- HELPER

        private String check(final String raw) {
            if(raw.contains(columnSeparator())
                || raw.contains(nullSymbol())
                || raw.contains(doubleQuoteSymbol())) {
                throw _Exceptions.illegalArgument("raw cell value '%s' must not contain delimiter "
                        + "nor null-symbol "
                        + "nor double-quote-symbol", raw);
            }
            return raw.replace("\"", doubleQuoteSymbol());
        }

    }


}
