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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.collections._Lists;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.YamlUtils;

import org.jspecify.annotations.NonNull;

/**
 * Represents a database snapshot with stringified cell values.
 */
public record TabularData(Can<TabularData.Table> dataTables) {

    /**
     * Transforms table and columns names.
     */
    public static interface NameTransformer {
        String transformTable(String key);
        String transformColumn(BiString key);
        // -- IDENTITY IMPLEMENTATION
        public static class Identity implements NameTransformer {
            @Override public String transformTable(final String key) {
                return key; }
            @Override public String transformColumn(final BiString key) {
                return key.right(); }
        }
        static final Identity IDENTITY = new Identity();
        static Identity identity() { return IDENTITY; }
    }

    public record Format(
            @NonNull String columnSeparator,
            @NonNull String nullSymbol,
            @NonNull String doubleQuoteSymbol,
            boolean isRowSortingEnabled) {

        public static Format defaults() {
            return new Format("|", "ø", "¯", false);
        }

        public Format withRowSorting(final boolean rowSortingEnabled) {
            return new Format(columnSeparator, nullSymbol, doubleQuoteSymbol, rowSortingEnabled);
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

    public static record Row(List<String> cellLiterals) {

    }

    public static record Column(String name, Optional<String> description) {

    }

    public static record Table(String key, Can<Column> columns, Can<Row> rows) {

    }

    public static TabularData populateFromYaml(
            final String tableDataSerializedAsYaml, final TabularData.Format formatOptions) {

        var asMap = YamlUtils
                .tryReadCustomized(HashMap.class, tableDataSerializedAsYaml, loader->{
                    loader.setCodePointLimit(6 * 1024 * 1024); // 6MB
                })
                .valueAsNonNullElseFail();

        // parse data from the map, and populate tables, that are already in the Can<DataTable>

        var dataTables = new ArrayList<TabularData.Table>();

        @SuppressWarnings("unchecked")
        var tables = (Collection<Map<String, ?>>) asMap.get("tables");
        tables
        //.stream().limit(1)
        .forEach(table->{
            table.entrySet().stream()
            .forEach(tableEntry->{
                var tableKey = tableEntry.getKey();

                @SuppressWarnings("unchecked")
                var tableColsAndRows = (Map<String, ?>)tableEntry.getValue();
                @SuppressWarnings("unchecked")
                var colLiterals = (Collection<String>) tableColsAndRows.get("cols");

                var rowsLit = tableColsAndRows.get("rows");
                @SuppressWarnings("unchecked")
                var rowLiterals = (rowsLit instanceof Collection)
                        ? (Collection<String>) rowsLit
                        : Collections.<String>emptyList();

                System.err.printf("table %s (rows=%d)%n", tableKey, _NullSafe.size(rowLiterals));
                //System.err.printf("  cols:%n");

                var columns = Can.ofIterable(colLiterals)
                    .map(TabularData::parseColumnFromStringified);

                //columns.forEach(c->System.err.printf("   %s%n", c));

                final int colCount = _NullSafe.size(colLiterals);

                var cellLiterals = new String[colCount];

                //System.err.printf("  rows:%n");
                var rows = _NullSafe.stream(rowLiterals)
                    .map(rowLiteral->{
                        //System.err.printf("  - %s%n", rowLiteral);
                        formatOptions.parseRow(rowLiteral, cellLiterals);
                        return _Lists.ofArray(cellLiterals); // defensive copy
                    })
                    .map(Row::new)
                    .collect(Can.toCan());

                var dataTable = new TabularData.Table(tableKey, columns, rows);
                dataTables.add(dataTable);
            });
        });

        return new TabularData(Can.ofCollection(dataTables));
    }

    public TabularData transform(final NameTransformer transformer) {
        return new TabularData(dataTables.map(dataTable->
            new Table(
                    transformer.transformTable(dataTable.key()),
                    dataTable
                        .columns()
                        .map(col->
                            new Column(
                                   transformer.transformColumn(
                                           new BiString(dataTable.key(), col.name())),
                                   col.description())),
                    dataTable.rows())
        ));
    }

    // -- HELPER

    private static Column parseColumnFromStringified(final String colLiteral) {
        return
        _Strings.parseKeyValuePair(colLiteral, ':')
            .map(pair->new Column(pair.key(), Optional.of(pair.value().trim())))
            .orElseGet(()->new Column(colLiteral, Optional.empty()));

    }

    public String toYaml(final TabularData.Format formatOptions) {

        var yaml = new YamlWriter();

        yaml.write("tables:").nl();

        dataTables.forEach(dataTable->{
            yaml.ind().ul().write(dataTable.key(), ":").nl();

            yaml.ind().ind().ind().write("cols:").nl();
            dataTable.columns().forEach(col->{
                var colLiteral = col.description()
                    .map(desc->String.format("%s: %s", col.name(), desc.replace('\n', '|')))
                    .orElse(col.name());

                yaml.ind().ind().ind().ul().doubleQuoted(colLiteral).nl();
            });

            yaml.ind().ind().ind().write("rows:").nl();

            var rowLiterals = dataTable.rows()
                    .map(dataRow->
                        dataRow.cellLiterals()
                                .stream()
                                .map(cellValue->toYaml(cellValue, formatOptions))
                                .collect(Collectors.joining(formatOptions.columnSeparator())));

            if(formatOptions.isRowSortingEnabled()) {
                rowLiterals = rowLiterals
                        .sorted(String::compareTo);
            }

            rowLiterals
                .forEach(rowLiteral->{
                    yaml.ind().ind().ind().ul().doubleQuoted(rowLiteral).nl();
                });
        });
        return yaml.toString();
    }

    private String toYaml(final String cellValue, final TabularData.Format formatOptions) {
        return cellValue==null
                ? formatOptions.nullSymbol()
                : cellValue.replaceAll("\"", formatOptions.doubleQuoteSymbol());
    }

    private static class YamlWriter {
        final StringBuilder sb = new StringBuilder();
        @Override public String toString() { return sb.toString(); }
        YamlWriter write(final String ...s) {
            for(var str:s) sb.append(str);
            return this;
        }
        YamlWriter doubleQuoted(final String s) {
            write("\"", s, "\"");
            return this;
        }
        YamlWriter ind() {
            sb.append("  ");
            return this;
        }
        YamlWriter nl() {
            sb.append('\n');
            return this;
        }
        YamlWriter ul() {
            sb.append("- ");
            return this;
        }
    }
}
