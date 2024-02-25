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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
import org.apache.causeway.core.metamodel.tabular.simple.DataColumn;
import org.apache.causeway.core.metamodel.tabular.simple.DataRow;
import org.apache.causeway.core.metamodel.tabular.simple.DataTable;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml.InsertMode;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml.StringNormalizer;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml.StringNormalizerFactory;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Column;
import dita.commons.types.TabularData.Table;

/**
 * Represents an ordered set of {@link DataTable}(s). Order is by logical entity type name (lexicographically).
 */
class _DataTableSet {

    // -- CONSTRUCTION

    @Getter
    private final @NonNull Can<DataTable> dataTables;
    private final @NonNull Map<String, DataTable> dataTableByLogicalName;

    public _DataTableSet(
            final Can<DataTable> dataTables) {
        this.dataTables = dataTables;
        this.dataTableByLogicalName = dataTables.stream()
                .collect(Collectors.toMap(DataTable::getLogicalName, UnaryOperator.identity()));
    }

    // -- POPULATING

    public _DataTableSet populateFromDatabase() {
        dataTables.forEach(DataTable::populateEntities);
        return this;
    }
    
    public _DataTableSet populateFromSecondaryConnection(
            final PersistenceManager pm) {
        dataTables.forEach(dataTable->{
            final var entityClass = dataTable.getElementType().getCorrespondingClass();
            System.err.printf("reading secondary table %s%n", entityClass.getSimpleName());
            
            pm.currentTransaction().begin();
            List<?> allInstances = pm.newQuery(entityClass).executeResultList(entityClass);
            dataTable.setDataElementPojos(allInstances);
            pm.currentTransaction().commit();
        });
        return this;
    }

    public _DataTableSet populateFromTabularData(
            final TabularData dataBase, 
            final TabularData.Format formatOptions) {

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

            final Can<String> colNames = tableEntry.columns().map(Column::name);

            System.err.printf("read table %s | %s%n", entityLogicalTypeName, colNames);
            //System.err.printf("  cols:%n");

            final int[] colIndexMapping =
                    guardAgainstColumnsVsMetamodelMismatch(dataTable, colNames);

            //System.err.printf("  rows:%n");
            val dataElements = tableEntry.rows()
                .map(row->{
                    // create a new entity instance from each row

                    val entityPojo = factoryService.detachedEntity(entityClass);
                    val entity = ManagedObject.adaptSingular(entitySpec, entityPojo);

                    int colIndex = 0;

                    for(val col : dataTable.getDataColumns()){
                        val colMetamodel = col.getMetamodel();
                        val valueSpec = colMetamodel.getElementType();
                        // assuming value
                        val valueFacet = valueSpec.valueFacetElseFail();
                        val cls = valueSpec.getCorrespondingClass();
                        final String valueStringified = row.cellLiterals().get(colIndexMapping[colIndex]);

                        // parse value
                        ManagedObject value = _EnumResolver.get(cls, "getMatchOn")
                                .map(r->{
                                    var enumObj = r.resolve(valueStringified);
                                    return enumObj!=null
                                            ? ManagedObject.adaptSingular(valueSpec, enumObj)
                                            : ManagedObject.empty(valueSpec);
                                })
                                .orElseGet(()->
                                    valueStringified!=null
                                            ? ManagedObject.adaptSingular(
                                                    valueSpec,
                                                    valueFacet.destring(Format.JSON, valueStringified))
                                            : ManagedObject.empty(valueSpec));

                        // directly set entity property
                        colMetamodel.getSpecialization()
                        .left()
                        .ifPresent(prop->prop.set(entity, value, InteractionInitiatedBy.PASS_THROUGH));

                        colIndex++;
                    }
                    return entity;
                });

            dataTable.setDataElements(dataElements);

        });

        return this;
    }

    public TabularData toTabularData(
            final TabularData.Format formatOptions) {
        return new TabularData(dataTables.map(dataTable->
            toTable(dataTable, formatOptions, (x, y)->StringNormalizer.IDENTITY)));
    }
    
    public TabularData toTabularData(
            final TabularData.Format formatOptions,
            final StringNormalizerFactory stringNormalizerFactory) {
        return new TabularData(dataTables.map(dataTable->
            toTable(dataTable, formatOptions, stringNormalizerFactory)));
    }
    
    private Table toTable(
            final DataTable dataTable,
            final TabularData.Format formatOptions,
            final StringNormalizerFactory stringNormalizerFactory) {
        
        var rows = dataTable.getDataRows()
                .map(dataRow->new TabularData.Row(
                    dataTable.getDataColumns()
                        .stream()
                        .map(column->stringify(column, dataRow, stringNormalizerFactory, formatOptions))
                        .toList()
                ));
        
        return new Table(
                dataTable.getElementType().getLogicalTypeName(),
                dataTable.getDataColumns()
                    .map(col->new TabularData.Column(
                        col.getMetamodel().getId(),
                        col.getColumnDescription())),
                rows);
    }
    
    private String stringify(
            final DataColumn column,
            final DataRow dataRow,
            final StringNormalizerFactory stringNormalizerFactory,
            final TabularData.Format formatOptions) {
        final DataTable dataTable = dataRow.getParentTable();
        var entityClass = dataTable.getElementType().getCorrespondingClass();
        var stringNormalizer = stringNormalizerFactory.stringNormalizer(entityClass, column.getColumnId());
        
        var cells = dataRow.getCellElements(column, InteractionInitiatedBy.PASS_THROUGH);
        var cellValue = cells.getSingleton().orElse(null); // assuming not multivalued
        return stringify(cellValue, formatOptions, stringNormalizer);
    }

    public _DataTableSet modifyObject(final Consumer<? super ManagedObject> modifier) {
        dataTables.forEach(dataTable->{
            dataTable.streamDataElements().forEach(modifier);
        });
        return this;
    }


    // -- WRITING TO DB

    public _DataTableSet insertToDatabase(
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
            dataTable.streamDataElements().forEach(entity->{
                repositoryService.persist(entity);
            });
        });
        return this;
    }

    public _DataTableSet replicateToDatabase(
            final PersistenceManager pm, final StringBuilder log) {

        // delete all existing entities
        dataTables.forEach(dataTable->{
            pm.currentTransaction().begin();
            val entityClass = dataTable.getElementType().getCorrespondingClass();
            Query<?> query = pm.newQuery(String.format("DELETE FROM %s", entityClass.getName()));

            //log
            logAppend(log, String.format("DELETE FROM %s", entityClass.getName()));

            query.execute();
            pm.currentTransaction().commit();
        });

        // insert new entities
        dataTables.forEach(dataTable->{

            var elementCount = dataTable.getElementCount();
            //log
            logAppend(log, String.format("copy %d from %s (%s)",
                    elementCount, dataTable.getTableFriendlyName(), dataTable.getLogicalName()));

            pm.currentTransaction().begin();

            dataTable.streamDataElements()
                .map(ManagedObject::getPojo)
                .forEach(IndexedConsumer.offset(1, (index, pojo)->{

                    pm.makePersistent(pojo);

                    // report progress to console
                    int percent = 100*index/elementCount;
                    if(elementCount>100
                            && (index%100) == 0) {
                        System.err.printf("\t%d%%%n", percent);
                    }
                }));

            pm.currentTransaction().commit();

//            pm.currentTransaction().begin();
//            pm.makePersistentAll(
//                 dataTable.streamDataElements()
//                     .map(ManagedObject::getPojo)
//                     .toList());
//            pm.currentTransaction().commit();
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
            final Can<String> colNames) {

        // sort for canonical comparison
        val colNamesSorted = colNames
                .sorted((a, b)->_Strings.compareNullsFirst(
                        _Strings.asLowerCase.apply(a),
                        _Strings.asLowerCase.apply(b)));
        // sort for canonical comparison
        val colFromMetamodelSorted = dataTable.getDataColumns().sorted(orderByColumnIdIgnoringCase());

        colNamesSorted.zip(colFromMetamodelSorted, (String colName, DataColumn col)->{
            // verify read in data matches meta-model
            _Assert.assertEquals(colName, col.getMetamodel().getId(), ()->
                    String.format("Column specifications %s from %s do not match current meta-model.",
                            colNames,
                            dataTable.getLogicalName()));
        });
        colFromMetamodelSorted.zip(colNamesSorted, (DataColumn col, String colName)->{
             // verify read in data matches meta-model
            _Assert.assertEquals(colName, col.getMetamodel().getId(), ()->
                    String.format("Column specifications %s from %s do not match current meta-model.",
                            colNames,
                            dataTable.getLogicalName()));
        });
        final int[] colIndexMapping = new int[colNames.size()];
        dataTable.getDataColumns().forEach(IndexedConsumer.zeroBased((int index, DataColumn col)->{
            col.getMetamodel().getId();
            colIndexMapping[index] = colNames.indexOf(col.getMetamodel().getId());
        }));
        return colIndexMapping;
    }
    
    private static String stringify(
            final @Nullable ManagedObject cellValue,
            final @NonNull TabularData.Format formatOptions,
            final @NonNull StringNormalizer stringNormalizer) {

        if(ManagedObjects.isNullOrUnspecifiedOrEmpty(cellValue)) {
            return formatOptions.nullSymbol();
        }

        var valueSpec = cellValue.getSpecification();

        if(cellValue.getPojo() instanceof Enum enumeration) {
            // stringify Enum as uppercase name
            return String.format("%s", enumeration.name());
        }

        // assuming value
        var valueFacet = valueSpec.valueFacetElseFail();

        @SuppressWarnings("unchecked")
        val stringifiedValue = formatOptions.encodeCellValue(
                stringNormalizer.apply(
                        valueFacet.enstring(Format.JSON, cellValue.getPojo())));
        return stringifiedValue;
    }

    /**
     * Alphabetical column order using column's underlying (member) id.
     */
    public static Comparator<DataColumn> orderByColumnIdIgnoringCase() {
        return (o1, o2) -> _Strings.compareNullsFirst(
                _Strings.asLowerCase.apply(o1.getColumnId()),
                _Strings.asLowerCase.apply(o2.getColumnId()));
    }

    private void logAppend(final StringBuilder log, final String msg) {
        log.append(msg).append('\n');
        System.err.println(msg);
    }

}
