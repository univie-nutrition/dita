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
import dita.causeway.replicator.tables.model.DataTableOptions;
import dita.causeway.replicator.tables.model.DataTableProvider;
import dita.causeway.replicator.tables.model.DataTables;
import lombok.val;

@Service(DitaModuleDatabaseReplicator.NAMESPACE + "TableSerializerYaml")
public class TableSerializerYaml {

    @Inject RepositoryService repositoryService;
    @Inject DataTableProvider dataTableProvider;

    public Clob clob(final String name, final Predicate<ObjectSpecification> filter) {
        val yaml = dataTables(filter)
                .populateFromDatabase(repositoryService)
                .toYaml(DataTableOptions.FormatOptions.defaults());
        return Clob.of(name, CommonMimeType.YAML, yaml);
    }

    /**
     * Returns the serialized version of the load result.
     */
    public String load(final Clob clob, final Predicate<ObjectSpecification> filter) {

        val yaml = dataTables(filter)
                .populateFromYaml(clob.asString(), DataTableOptions.FormatOptions.defaults())
                .toYaml(DataTableOptions.FormatOptions.defaults());

        //TODO update those filtered list of tables; then persist; then reassess
        return yaml;
    }

    // -- HELPER

    private DataTables dataTables(final Predicate<ObjectSpecification> filter){
        val dataTables = new DataTables(
                dataTableProvider.streamDataTables()
                    .filter(dataTable->filter.test(dataTable.getElementType()))
                    .collect(Can.toCan()));
        return dataTables;
    }

}
