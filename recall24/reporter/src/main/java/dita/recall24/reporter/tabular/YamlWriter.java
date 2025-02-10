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
package dita.recall24.reporter.tabular;

import java.util.stream.Collectors;

import org.apache.causeway.commons.tabular.TabularModel.TabularCell;
import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;

record YamlWriter(TabularSheet sheet) {

    String write() {
        var sb = new StringBuilder();
        //var columns = sheet.columns();

        for(var row : sheet.rows()) {
            var rowLiteral = row.cells().join(this::toCellLabel, "|");
            sb.append(rowLiteral);
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
                obj->"" + obj,
                labels-> labels.get().collect(Collectors.joining("~")));
    }

}
