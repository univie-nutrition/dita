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

import java.io.File;

import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;
import org.apache.causeway.extensions.tabular.excel.exporter.ExcelFileWriter;
import org.apache.causeway.extensions.tabular.excel.exporter.TabularExcelExporter;

record XlsxWriter(TabularSheet sheet) {

    public void write(final File file) {
        new TabularExcelExporter()
            .export(sheet, file, ExcelFileWriter.Options.builder()
                    .cellStyleFunction(cell->TabularFactory.isWip(cell)
                            ? ExcelFileWriter.Options.CustomCellStyle.DANGER
                            : TabularFactory.isComposite(cell)
                                ? ExcelFileWriter.Options.CustomCellStyle.INDIGO
                                : ExcelFileWriter.Options.CustomCellStyle.DEFAULT)
                    .rowStyleFunction(row->TabularFactory.containsWip(row)
                            ? ExcelFileWriter.Options.CustomCellStyle.WARNING
                            : ExcelFileWriter.Options.CustomCellStyle.DEFAULT)
                    .build());
    }

}
