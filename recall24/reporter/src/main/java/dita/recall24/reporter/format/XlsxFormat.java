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

import java.io.File;
import java.nio.file.Files;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.tabular.TabularModel;
import org.apache.causeway.extensions.tabular.excel.exporter.ExcelFileWriter;

import lombok.SneakyThrows;

public record XlsxFormat(HighlightingPredicates highlighting) {

    public XlsxFormat() {
        this(new HighlightingPredicates() {});
    }

    @SneakyThrows
    public Blob writeBlob(final TabularModel tabularModel, final String name) {
        var tempFile = File.createTempFile(this.getClass().getCanonicalName(), name);
        try {
            this.writeFile(tabularModel, tempFile);
            return Blob.of(name, CommonMimeType.XLSX, DataSource.ofFile(tempFile).bytes());
        } finally {
            Files.deleteIfExists(tempFile.toPath()); // cleanup
        }
    }

    public void writeFile(final TabularModel tabularModel, final File file) {
        var options = ExcelFileWriter.Options.builder()
            .cellStyleFunction(cell->highlighting.isWip(cell)
                    ? ExcelFileWriter.Options.CustomCellStyle.DANGER
                    : highlighting.isComment(cell)
                        ? ExcelFileWriter.Options.CustomCellStyle.GRAY
                        : highlighting.isComposite(cell)
                            ? ExcelFileWriter.Options.CustomCellStyle.INDIGO
                            : highlighting.isFryingFat(cell)
                                ? ExcelFileWriter.Options.CustomCellStyle.MINT
                                : ExcelFileWriter.Options.CustomCellStyle.DEFAULT)
            .rowStyleFunction(row->highlighting.containsWip(row)
                    ? ExcelFileWriter.Options.CustomCellStyle.WARNING
                    : ExcelFileWriter.Options.CustomCellStyle.DEFAULT)
            .build();

        new ExcelFileWriter(options).write(tabularModel, file);
    }

}
