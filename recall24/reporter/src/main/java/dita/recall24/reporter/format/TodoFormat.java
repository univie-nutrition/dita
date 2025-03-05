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
package dita.recall24.reporter.format;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.tabular.TabularModel;
import org.apache.causeway.commons.tabular.TabularModel.TabularCell;
import org.apache.causeway.commons.tabular.TabularModel.TabularColumn;
import org.apache.causeway.commons.tabular.TabularModel.TabularRow;

import dita.recall24.reporter.todo.TodoReporter;

/**
 * introduced to double check results correspond to those produced by {@link TodoReporter}
 */
public record TodoFormat(HighlightingPredicates highlighting) {

    public TodoFormat() {
        this(new HighlightingPredicates() {});
    }

    public Clob writeClob(final TabularModel tabularModel, final String name) {
        var tsv = this.write(tabularModel);
        return Clob.of(name, CommonMimeType.CSV, tsv);
    }

    public String write(final TabularModel tabularModel) {
        var sheet = tabularModel.sheets().getFirstElseFail();
        var colNames = List.of("Fcdb Id", "Record Type", "Food", "Food Id", "Facet Ids");

        var cols = colNames.stream()
            .map(colId->sheet.columns().stream().filter(col->col.columnName().equals(colId)).findFirst().orElseThrow())
            .toList();

        // primary header row
        var headerLine = colNames.stream().skip(1).collect(Collectors.joining("\t"));

        // data rows
        var lines = new LinkedHashSet<String>();
        for(var row : sheet.rows()) {
            if(!highlighting.isWip(ColOfInterest.FCDBID.selectCell(row, cols))) continue;
            lines.add("%s\t%s\t%s\t%s".formatted(
                ColOfInterest.RECORDTYPE.asString(row, cols),
                ColOfInterest.FOOD.asString(row, cols),
                ColOfInterest.FOODID.asString(row, cols),
                ColOfInterest.FACETIDS.asString(row, cols)));
        }

        var sb = new StringBuilder();
        sb.append(headerLine).append("\n");
        lines.forEach(line->sb.append(line).append("\n"));
        return sb.toString();
    }

    // -- HELPER

    private enum ColOfInterest {
        FCDBID,
        RECORDTYPE,
        FOOD,
        FOODID,
        FACETIDS;
        TabularCell selectCell(final TabularRow row,  final List<TabularColumn> columns) {
            return row.getCell(columns.get(ordinal()));
        }
        String asString(final TabularRow row,  final List<TabularColumn> columns) {
            return ""+selectCell(row, columns).eitherValueOrLabelSupplier().leftIfAny();
        }
    }

}
