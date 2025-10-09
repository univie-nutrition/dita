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

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.context._Context;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.metamodel.facets.object.entity.EntityFacet;
import org.apache.causeway.core.metamodel.facets.object.entity.EntityOrmMetadata.ColumnOrmMetadata;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.core.metamodel.tabular.simple.DataTable;

import lombok.SneakyThrows;

import dita.causeway.replicator.DitaModuleDatabaseReplicator;
import dita.causeway.replicator.tables.model.DataTableService;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Column;
import dita.commons.types.TabularData.NameTransformer;
import dita.commons.types.TabularData.Row;
import dita.commons.types.TabularData.Table;
import io.github.causewaystuff.companion.applib.jpa.EnumConverter;

@Service(DitaModuleDatabaseReplicator.NAMESPACE + "TableSerializerYaml")
public class TableSerializerYaml {

    @Inject RepositoryService repositoryService;
    @Inject DataTableService dataTableService;

    public Clob clobFromDataTables(
            final String name,
            final NameTransformer nameTransformer,
            final Can<DataTable> dataTables) {
        var yaml = new _DataTableSet(dataTables)
                .toTabularData(format())
                .transform(nameTransformer)
                .toYaml(format());
        return Clob.of(name, CommonMimeType.YAML, yaml);
    }

    public Clob clobFromRepository(
            final String name,
            final NameTransformer nameTransformer,
            final Predicate<ObjectSpecification> filter,
            final boolean rowSortingEnabled) {
        var yaml = dataTableSet(filter)
                .withPopulateFromDatabase()
                .toTabularData(format())
                .transform(nameTransformer)
                .toYaml(format().withRowSorting(rowSortingEnabled));
        return Clob.of(name, CommonMimeType.YAML, yaml);
    }

    public interface StringNormalizerFactory {
        StringNormalizer stringNormalizer(final Class<?> entityClass, final String fieldId);
    }

    public Clob clobFromSecondaryConnection(
            final String name,
            final NameTransformer nameTransformer,
            final Predicate<ObjectSpecification> filter,
            final EntityManager em,
            final StringNormalizerFactory stringNormalizerFactory,
            final boolean rowSortingEnabled) {
        var yaml = dataTableSet(filter)
                .withPopulateFromSecondaryConnection(em)
                .toTabularData(format(), stringNormalizerFactory)
                .transform(nameTransformer)
                .toYaml(format().withRowSorting(rowSortingEnabled));
        return Clob.of(name, CommonMimeType.YAML, yaml);
    }

    public enum InsertMode {
        DO_NOTHING,
        ADD,
        DELETE_ALL_THEN_ADD;
        public boolean isDoNothing() { return this == DO_NOTHING;}
        public boolean isAdd() { return this == ADD;}
        public boolean isDeleteAllThenAdd() { return this == DELETE_ALL_THEN_ADD;}
    }

    public enum StringNormalizer {
        IDENTITY{
            @Override String apply(final String in) { return in; }},
        EMPTY_TO_NULL{
            @Override String apply(final String in) { return _Strings.emptyToNull(in); }};
        abstract String apply(String in);
    }

    /**
     * Populates the (primary) data-store from tabular data as given by the {@code clob}.
     * <p>
     * Returns the serialized version of the load result as yaml.
     */
    public String load(
            final Clob clob,
            final NameTransformer nameTransformer,
            final Predicate<ObjectSpecification> filter,
            final InsertMode insertMode,
            final Consumer<? super ManagedObject> modifier) {

        if(insertMode.isDoNothing()) return "Ignored";

        var tabularData = TabularData.populateFromYaml(clob.asString(), format())
                .transform(nameTransformer);

        var yaml = dataTableSet(filter)
                .withPopulateFromTabularData(tabularData, format())
                .modifyObject(modifier)
                .insertToDatabase(repositoryService, insertMode)
                .toTabularData(format())
                .toYaml(format());
        return yaml;
    }

    /**
     * Populates the (secondary) data-store as represented by {@code pm}
     * from tabular data as given by the {@code clob}.
     * <p>
     * Returns the serialized version of the load result as yaml.
     */
    public String replicate(
            final Clob clob,
            final NameTransformer nameTransformer,
            final Predicate<ObjectSpecification> filter,
            final EntityManager em) {

        System.err.printf("replicate %s%n", "gen tabularData");

        var tabularData = TabularData.populateFromYaml(clob.asString(), format())
                .transform(nameTransformer);

        System.err.printf("replicate %s%n", "start");

        var sb = new StringBuilder();
        var dataTableSet = dataTableSet(filter)
                .withPopulateFromTabularData(tabularData, format())
                .replicateToDatabase(em, sb);

        System.err.printf("replicate (tables=%d) done.%n", dataTableSet.getDataTables().size());

        return sb.toString();
    }

    /**
     * Populates the (secondary) data-store as represented by {@code pm}
     * from tabular data as given by the {@code clob}.
     * <p>
     * Returns the serialized version of the load result as yaml.
     */
    public String nativeReplicate(
            final Clob clob,
            final NameTransformer tableToEntity,
            final EntityManager em) {

        System.err.printf("replicate %s%n", "gen tabularData");

        var tabularData = TabularData.populateFromYaml(clob.asString(), format());

        System.err.printf("replicate %s%n", "start");

        var sb = new StringBuilder();
        var specLoader = MetaModelContext.instanceElseFail().getSpecificationLoader();

        tabularData.dataTables().forEach(table->{
            var entityLogicalName = tableToEntity.transformTable(table.key());
            var entitySpec = specLoader
                .specForLogicalTypeNameElseFail(entityLogicalName);
            var entityFacet = entitySpec.entityFacetElseFail();
            em.getTransaction().begin();
            deleteTable(table, em);
            em.getTransaction().commit();
            em.getTransaction().begin();
            replicateTable(table, entityFacet, em);
            em.getTransaction().commit();
        });

        System.err.printf("replicate (tables=%d) done.%n", tabularData.dataTables().size());

        return sb.toString();
    }

    // -- HELPER

    private void deleteTable(final Table table, final EntityManager em) {
        var sql = "DELETE %s".formatted(table.key());
        em.createNativeQuery(sql)
            .executeUpdate();
    }

    private void replicateTable(final Table table, final EntityFacet entityFacet, final EntityManager em) {
        System.err.printf("writing table %s%n", table.key());
        String sql = "INSERT INTO %s (%s) VALUES (%s)".formatted(
            table.key(),
            table.columns().map(Column::name).map(s->"[" + s + "]").join(", "), //MS SQL Server specific
            IntStream.range(0, table.columns().size()).mapToObj(_->"?").collect(Collectors.joining(", ")));

        var javaTypeByColName = entityFacet.getOrmMetadata()
            .columns()
            .toMap(ColumnOrmMetadata::name);

        var colMetaData = table.columns().map(Column::name).map(javaTypeByColName::get);

        table.rows().forEach(row->replicateRow(row, em, sql, colMetaData));
    }

    private void replicateRow(final Row row, final EntityManager em, final String sql, final Can<ColumnOrmMetadata> colMetaData) {
        var q = em.createNativeQuery(sql);
        row.cellLiterals().forEach(IndexedConsumer.zeroBased((i, cellLiteral)->{
            q.setParameter(i + 1, toObj(cellLiteral, colMetaData.getElseFail(i)));
        }));
        q.executeUpdate();
    }

    @SneakyThrows
    private Object toObj(final String cellLiteral, final ColumnOrmMetadata columnOrmMetadata) {
        var isNull = cellLiteral==null || format().nullSymbol().equals(cellLiteral);
        if(isNull) return null;

        return switch(columnOrmMetadata.javaType()) {
            case "java.lang.String" -> cellLiteral;
            case "java.lang.Integer" -> Integer.parseInt(cellLiteral);
            case "int" -> Integer.parseInt(cellLiteral);
            case "java.lang.Long" -> Long.parseLong(cellLiteral);
            case "long" -> Long.parseLong(cellLiteral);
            case "java.lang.Double" -> Double.parseDouble(cellLiteral);
            case "double" -> Double.parseDouble(cellLiteral);
            case "boolean" -> Boolean.parseBoolean(cellLiteral);
            default -> {
                // handle enums
                var paramCls = _Context.loadClass(columnOrmMetadata.javaType());
                if(paramCls.isEnum()) {
                    var paramEnum = Enum.valueOf(_Casts.uncheckedCast(paramCls), cellLiteral);
                    var converter = (EnumConverter)(_Context.loadClass(columnOrmMetadata.javaType() + "$Converter")
                        .getConstructor()
                        .newInstance());
                    yield converter.convertToDatabaseColumn(paramEnum);
                }
                throw new IllegalArgumentException("Unexpected value: " + columnOrmMetadata.javaType());
            }
        };
    }

    private _DataTableSet dataTableSet(final Predicate<ObjectSpecification> filter){
        var dataTables = new _DataTableSet(
                dataTableService.streamDataTables()
                    .filter(dataTable->filter.test(dataTable.elementType()))
                    .collect(Can.toCan()));
        return dataTables;
    }

    private static TabularData.Format format() {
        return TabularData.Format.defaults();
    }

}
