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

import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.base._Strings;
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

        /**
         * Encode into serialized format.
         */
        public String encodeCellValue(final String string) {
            return string==null
                    ? nullSymbol()
                    : check(string);
        }

        /**
         * Decode from serialized format.
         */
        public String decodeCellValue(final String string) {
            return string.equals(nullSymbol())
                    ? null
                    : string.replace(doubleQuoteSymbol(), "\"");
        }

        /**
         * Fills the given {@code cellLiterals} array with the split up {@code rowLiteral}.
         */
        public void parseRow(final @NonNull String rowLiteral, final @NonNull String[] cellLiterals) {
            _Strings.splitThenStream(rowLiteral, columnSeparator())
            .map(this::decodeCellValue)
            .forEach(IndexedConsumer.zeroBased((index, cellLiteral)->
                cellLiterals[index] = cellLiteral));
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
