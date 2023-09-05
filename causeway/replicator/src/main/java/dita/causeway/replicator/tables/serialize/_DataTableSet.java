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

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.core.metamodel.consent.InteractionInitiatedBy;
import org.apache.causeway.core.metamodel.facets.object.value.ValueSerializer.Format;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.object.ManagedObjects;

import dita.causeway.replicator.tables.model.DataColumn;
import dita.causeway.replicator.tables.model.DataTable;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml.InsertMode;
import dita.commons.types.tabular.DataBase;
import dita.commons.types.tabular.DataBase.Column;
import dita.commons.types.tabular.DataBase.Table;
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
                        .ofCollection(
                                repositoryService
                                    .allInstances(dataTable.getElementType().getCorrespondingClass()))
                        .map(entityPojo->
                                ManagedObject.adaptSingular(dataTable.getElementType(), entityPojo)));
        });
        return this;
    }

    public _DataTableSet populateFromTabularData(
            final DataBase dataBase, final DataBase.Format formatOptions) {

        dataBase.dataTables()
        .forEach(tableEntry->{

            val entityLogicalTypeName = tableEntry.key();

            val dataTable = Optional.ofNullable(dataTableByLogicalName.get(entityLogicalTypeName))
                    .orElse(null);
            if(dataTable==null) {
                return; // skip
            }
            val entitySpec = dataTable.getElementType();
            val entityClass = entitySpec.getCorrespondingClass();
            val factoryService = entitySpec.getFactoryService();

            Can<String> colLiterals = tableEntry.columns().map(Column::name);

            System.err.printf("table %s%n", entityLogicalTypeName);
            //System.err.printf("  cols:%n");

            final int[] colIndexMapping = guardAgainstColumnsVsMetamodelMismatch(
                    dataTable,
                    Can.ofIterable(colLiterals)
                        .map(_DataTableSet::parseColumnIdFromStringified));

            //System.err.printf("  rows:%n");
            val dataElements = tableEntry.rows()
                .map(row->{
                    //System.err.printf("  - %s%n", rowLiteral);

                    // create a new entity instance from each row

                    val entityPojo = factoryService.detachedEntity(entityClass);
                    val entity = ManagedObject.adaptSingular(entitySpec, entityPojo);


                    int colIndex = 0;

                    for(val col : dataTable.getDataColumns()){
                        val colMetamodel = col.getPropertyMetaModel();
                        val valueSpec = colMetamodel.getElementType();
                        // assuming value
                        val valueFacet = valueSpec.valueFacetElseFail();

                        // parse value
                        final String valueStringified = row.cellLiterals().get(colIndexMapping[colIndex]);
                        val value = valueStringified!=null
                                ? ManagedObject.adaptSingular(
                                        valueSpec,
                                        valueFacet.destring(Format.JSON, valueStringified))
                                : colMetamodel.getDefault(entity);

                        // directly set entity property
                        colMetamodel.set(entity, value, InteractionInitiatedBy.PASS_THROUGH);

                        colIndex++;
                    }
                    return entity;
                });

            dataTable.setDataElements(dataElements);

        });

        return this;
    }

    public DataBase toDataBase(final DataBase.Format formatOptions) {
        return new DataBase(dataTables.map(dataTable->
            new Table(
                    dataTable.getElementType().getLogicalTypeName(),
                    dataTable.getDataColumns()
                        .map(col->new DataBase.Column(
                            col.getPropertyMetaModel().getId(),
                            col.getColumnDescription())),
                    dataTable.getDataRows().map(dataRow->new DataBase.Row(

                            dataTable.getDataColumns()
                            .stream()
                            .map(dataRow::getCellElement)
                            .map(cellValue->stringify(cellValue, formatOptions))
                            .collect(Collectors.toList())

                            )))
        ));
    }

    // -- WRITING TO DB

    public _DataTableSet insertToDatabasse(
            final RepositoryService repositoryService,
            final InsertMode insertMode) {
        // delete all existing entities
        if(insertMode.isDeleteAllThenAdd()) {
            dataTables.forEach(dataTable->{
                val entityClass = dataTable.getElementType().getCorrespondingClass();
                repositoryService.removeAll(entityClass);
            });
        }
        // insert new entities
        dataTables.forEach(dataTable->{
            dataTable.getDataElements().forEach(entity->{
                repositoryService.persist(entity);
            });
        });
        return this;
    }

    // -- HELPER

    /**
     * All serialized columns must exist in current meta-model (as entity fields/properties) and vice versa.
     * @returns the data-table-column index to serialized column index mapping
     */
    private int[] guardAgainstColumnsVsMetamodelMismatch(
            final DataTable dataTable,
            final Can<String> colLiterals) {

        // sort for canonical comparison
        val colLiteralsSorted = colLiterals
                .sorted((a, b)->_Strings.compareNullsFirst(
                        _Strings.asLowerCase.apply(a),
                        _Strings.asLowerCase.apply(b)));
        // sort for canonical comparison
        val colFromMetamodelSorted = dataTable.getDataColumns().sorted(DataColumn::compareTo);

        colLiteralsSorted.zip(colFromMetamodelSorted, (String colLiteral, DataColumn col)->{
            // verify read in data matches meta-model
            _Assert.assertEquals(colLiteral, col.getPropertyMetaModel().getId(), ()->
                    String.format("Column specifications %s from %s do not match current meta-model.",
                            colLiterals,
                            dataTable.getLogicalName()));
        });
        colFromMetamodelSorted.zip(colLiteralsSorted, (DataColumn col, String colLiteral)->{
             // verify read in data matches meta-model
            _Assert.assertEquals(colLiteral, col.getPropertyMetaModel().getId(), ()->
                    String.format("Column specifications %s from %s do not match current meta-model.",
                            colLiterals,
                            dataTable.getLogicalName()));
        });
        final int[] colIndexMapping = new int[colLiterals.size()];
        dataTable.getDataColumns().forEach(IndexedConsumer.zeroBased((int index, DataColumn col)->{
            col.getPropertyMetaModel().getId();
            colIndexMapping[index] = colLiterals.indexOf(col.getPropertyMetaModel().getId());
        }));
        return colIndexMapping;
    }

    private static String parseColumnIdFromStringified(final String colLiteral) {
        return _Strings.splitThenStream(colLiteral, ":")
                .findFirst()
                .orElse(colLiteral);
    }


    private static String stringify(
            final @Nullable ManagedObject cellValue,
            final @NonNull DataBase.Format formatOptions) {

        if(ManagedObjects.isNullOrUnspecifiedOrEmpty(cellValue)) {
            return formatOptions.nullSymbol();
        }

        val valueSpec = cellValue.getSpecification();

        // assuming value
        val valueFacet = valueSpec.valueFacetElseFail();

        @SuppressWarnings("unchecked")
        val stringifiedValue = formatOptions.encodeCellValue(
                        valueFacet.enstring(Format.JSON, cellValue.getPojo()));
        return stringifiedValue;
    }

}
