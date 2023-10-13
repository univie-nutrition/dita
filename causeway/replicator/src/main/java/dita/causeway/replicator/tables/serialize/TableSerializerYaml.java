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

import java.util.function.Predicate;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;

import dita.causeway.replicator.DitaModuleDatabaseReplicator;
import dita.causeway.replicator.tables.model.DataTableService;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.NameTransformer;
import lombok.val;

@Service(DitaModuleDatabaseReplicator.NAMESPACE + "TableSerializerYaml")
public class TableSerializerYaml {

    @Inject RepositoryService repositoryService;
    @Inject DataTableService dataTableService;

    public Clob clob(
            final String name,
            final NameTransformer nameTransformer,
            final Predicate<ObjectSpecification> filter) {
        val yaml = dataTables(filter)
                .populateFromDatabase(repositoryService)
                .toTabularData(format())
                .transform(nameTransformer)
                .toYaml(format());
        return Clob.of(name, CommonMimeType.YAML, yaml);
    }

    public enum InsertMode {
        ADD,
        DELETE_ALL_THEN_ADD;
        public boolean isAdd() { return this == ADD;}
        public boolean isDeleteAllThenAdd() { return this == DELETE_ALL_THEN_ADD;}
    }

    /**
     * Returns the serialized version of the load result.
     */
    public String load(
            final Clob clob,
            final NameTransformer nameTransformer,
            final Predicate<ObjectSpecification> filter,
            final InsertMode insertMode) {

        val tabularData = TabularData.populateFromYaml(clob.asString(), format())
                .transform(nameTransformer);

        val yaml = dataTables(filter)
                .populateFromTabularData(tabularData, format())
                .insertToDatabase(repositoryService, insertMode)
                .toTabularData(format())
                .toYaml(TabularData.Format.defaults());
        return yaml;
    }

    // -- HELPER

    private _DataTableSet dataTables(final Predicate<ObjectSpecification> filter){
        val dataTables = new _DataTableSet(
                dataTableService.streamDataTables()
                    .filter(dataTable->filter.test(dataTable.getElementType()))
                    .collect(Can.toCan()));
        return dataTables;
    }

    private static TabularData.Format format() {
        return TabularData.Format.defaults();
    }

}
