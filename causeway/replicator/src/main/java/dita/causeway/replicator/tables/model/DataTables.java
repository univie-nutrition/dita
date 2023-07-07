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

import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.core.metamodel.object.ManagedObject;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;

public class DataTables {

    // -- CONSTRUCTION

    @Getter private final @NonNull Can<DataTable> dataTables;

    public DataTables(
            final Can<DataTable> dataTables) {
        this.dataTables = dataTables;
    }

    // -- POPULATING

    public DataTables populateFromDatabase(final RepositoryService repositoryService) {
        dataTables.forEach(dataTable->{
            dataTable.setDataElements(
                    Can
                        .ofCollection(repositoryService.allInstances(dataTable.getElementType().getCorrespondingClass()))
                        .map(entityPojo->ManagedObject.adaptSingular(dataTable.getElementType(), entityPojo)));
        });
        return this;
    }

    public DataTables populateFromYaml(final String yaml, final DataTableOptions.FormatOptions formatOptions) {
        val asMap = YamlUtils
                .tryRead(HashMap.class, yaml, loader->{
                    loader.setCodePointLimit(6 * 1024 * 1024); // 6MB
                    return loader;
                })
                .valueAsNonNullElseFail();

        //TODO parse data from the map, and populate tables, that are already in the Can<DataTable>
        System.err.printf("asMap %s%n", asMap);
        return this;
    }

    // -- WRITING

    public String toYaml(final DataTableOptions.FormatOptions formatOptions) {
        val yaml = new YamlWriter();

        yaml.write("tables:").nl();

        dataTables.forEach(dataTable->{
            yaml.ind().ul().write(dataTable.getElementType().getLogicalTypeName(), ":").nl();

            yaml.ind().ind().ind().write("cols:").nl();
            dataTable.getDataColumns().forEach(col->{

                val colName = col.getPropertyMetaModel().getId();

                val colLiteral = col.getColumnDescription()
                    .map(desc->String.format("%s: %s", colName, desc.replace('\n', '|')))
                    .orElse(colName);

                yaml.ind().ind().ind().ul().doubleQuoted(colLiteral).nl();
            });

            yaml.ind().ind().ind().write("rows:").nl();
            dataTable.getDataRows().forEach(dataRow->{
                val rowLiteral = dataTable.getDataColumns()
                        .stream()
                        .map(dataRow::getCellElement)
                        .map(cell->cell!=null ? formatOptions.asCellValue(cell.getTitle()) : formatOptions.nullSymbol())
                        .collect(Collectors.joining(formatOptions.columnSeparator()));

                yaml.ind().ind().ind().ul().doubleQuoted(rowLiteral).nl();
            });
        });
        return yaml.toString();
    }

    // -- HELPER

    private static class YamlWriter {
        final StringBuilder sb = new StringBuilder();
        @Override public String toString() { return sb.toString(); }
        YamlWriter write(final String ...s) {
            for(val str:s) sb.append(str);
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
