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

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.apache.causeway.commons.tabular.TabularModel;
import org.apache.causeway.commons.tabular.TabularModel.TabularCell;
import org.apache.causeway.commons.tabular.TabularModel.TabularColumn;
import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;

import dita.commons.util.NumberUtils;

/**
 * Writes a {@link TabularSheet} in tab-separated-values (TSV) format.
 */
public record TsvFormat() {

    public String write(final TabularModel tabularModel) {
        var sheet = tabularModel.sheets().getFirstElseFail();
        var sb = new StringBuilder();

        // primary header row
        sb.append(sheet.columns().join(TabularColumn::columnName, "\t")).append("\n");

        // secondary header row
        sb.append(sheet.columns().join(col->col.columnDescription().replace('\n', ' '), "\t")).append("\n");

        // data rows
        for(var row : sheet.rows()) {
            var rowLiteral = row.cells().join(this::toCellLabel, "\t");
            sb.append(rowLiteral).append("\n");
        }

        return sb.toString();
    }

    // -- HELPER

    private String toCellLabel(final TabularCell cell) {
        if(cell==null
                || cell.cardinality()==0) {
            return "";
        }
        return cell.eitherValueOrLabelSupplier()
            .fold(
                obj->format(obj),
                labels-> labels.get().collect(Collectors.joining("~")));
    }

    private String format(final Object obj) {
        if(obj instanceof BigDecimal decimal) return NumberUtils.reducedPrecision(decimal, 3).toString();
        return "" + obj;
    }

}
