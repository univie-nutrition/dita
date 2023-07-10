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
package dita.causeway.replicator.tables.serialize;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.core.metamodel.consent.InteractionInitiatedBy;
import org.apache.causeway.core.metamodel.facets.object.value.ValueSerializer.Format;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.object.ManagedObjects;

import dita.causeway.replicator.tables.model.DataTable;
import dita.causeway.replicator.tables.model.DataTableOptions;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;

/**
 * Represents an ordered set of {@link DataTable}(s). Order is by logical entity type name (lexicographically).
 */
class _DataTableSet {

    // -- CONSTRUCTION

    @Getter private final @NonNull Can<DataTable> dataTables;
    private final @NonNull Map<String, DataTable> dataTableByLogicalName;

    public _DataTableSet(
            final Can<DataTable> dataTables) {
        this.dataTables = dataTables;
        this.dataTableByLogicalName = dataTables.stream()
                .collect(Collectors.toMap(DataTable::getLogicalName, UnaryOperator.identity()));
    }

    // -- POPULATING

    public _DataTableSet populateFromDatabase(final RepositoryService repositoryService) {
        dataTables.forEach(dataTable->{
            dataTable.setDataElements(
                    Can
                        .ofCollection(repositoryService.allInstances(dataTable.getElementType().getCorrespondingClass()))
                        .map(entityPojo->ManagedObject.adaptSingular(dataTable.getElementType(), entityPojo)));
        });
        return this;
    }

    public _DataTableSet populateFromYaml(final String yaml, final DataTableOptions.FormatOptions formatOptions) {
        val asMap = YamlUtils
                .tryRead(HashMap.class, yaml, loader->{
                    loader.setCodePointLimit(6 * 1024 * 1024); // 6MB
                    return loader;
                })
                .valueAsNonNullElseFail();

        //TODO parse data from the map, and populate tables, that are already in the Can<DataTable>

        val tables = (Iterable<Map<String, ?>>) asMap.get("tables");
        tables.forEach(table->{
            table.entrySet().stream()
            .forEach(tableEntry->{
                val entityLogicalTypeName = tableEntry.getKey();

                val dataTable = Optional.ofNullable(dataTableByLogicalName.get(entityLogicalTypeName))
                        .orElse(null);
                if(dataTable==null) {
                    return; // skip
                }
                val entitySpec = dataTable.getElementType();
                val entityClass = entitySpec.getCorrespondingClass();
                val factoryService = entitySpec.getFactoryService();
                val objectManager = entitySpec.getObjectManager();

                val tableColsAndRows = (Map<String, ?>)tableEntry.getValue();

                val colLiterals = (Iterable<String>) tableColsAndRows.get("cols");
                val rowLiterals = (Iterable<String>) tableColsAndRows.get("rows");

                System.err.printf("table %s%n", entityLogicalTypeName);
                //System.err.printf("  cols:%n");
                _NullSafe.stream(colLiterals).forEach(colLiteral->{
                    //System.err.printf("  - %s%n", colLiteral);
                    //TODO verify matches current col. metamodel
                });

                final int colCount = (int)_NullSafe.stream(colLiterals).count();

                val cellLiterals = new String[colCount];

                //System.err.printf("  rows:%n");
                _NullSafe.stream(rowLiterals).forEach(rowLiteral->{
                    //System.err.printf("  - %s%n", rowLiteral);

                    // create a new entity instance from each row

                    val entityPojo = factoryService.detachedEntity(entityClass);
                    objectManager.adapt(entityPojo);

                    val entity = ManagedObject.adaptSingular(entitySpec, entityPojo);

                    formatOptions.parseRow(rowLiteral, cellLiterals);

                    int colIndex = 0;

                    for(val col : dataTable.getDataColumns()){
                        val colMetamodel = col.getPropertyMetaModel();
                        val valueSpec = colMetamodel.getElementType();
                        // assuming value
                        val valueFacet = valueSpec.valueFacetElseFail();

                        // parse value
                        final String valueStringified = cellLiterals[colIndex];
                        val value = valueStringified!=null
                                ? ManagedObject.adaptSingular(
                                        valueSpec,
                                        valueFacet.destring(Format.JSON, valueStringified))
                                : colMetamodel.getDefault(entity);

                        // directly set entity property
                        colMetamodel.set(entity, value, InteractionInitiatedBy.PASS_THROUGH);

                        colIndex++;
                    }

                });

            });
        });

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
                        .map(cellValue->stringify(cellValue, formatOptions))
                        .collect(Collectors.joining(formatOptions.columnSeparator()));

                yaml.ind().ind().ind().ul().doubleQuoted(rowLiteral).nl();
            });
        });
        return yaml.toString();
    }

    // -- HELPER

    private static String stringify(
            final @Nullable ManagedObject cellValue,
            final @NonNull DataTableOptions.FormatOptions formatOptions) {

        if(ManagedObjects.isNullOrUnspecifiedOrEmpty(cellValue)) {
            return formatOptions.nullSymbol();
        }

        val valueSpec = cellValue.getSpecification();

        // assuming value
        val valueFacet = valueSpec.valueFacetElseFail();

        return formatOptions.encodeCellValue(
                        valueFacet.enstring(Format.JSON, cellValue.getPojo()));
    }

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
