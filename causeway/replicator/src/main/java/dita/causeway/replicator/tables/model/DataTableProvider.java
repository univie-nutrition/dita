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

import java.util.stream.Stream;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.config.beans.CausewayBeanTypeRegistry;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.specloader.SpecificationLoader;

import dita.causeway.replicator.DitaModuleDatabaseReplicator;
import lombok.val;

@Service(DitaModuleDatabaseReplicator.NAMESPACE + "DataTableProvider")
public class DataTableProvider {

    @Inject RepositoryService repositoryService;
    @Inject SpecificationLoader specLoader;
    @Inject CausewayBeanTypeRegistry beanTypeRegistry;

    public DataTable dataTable(final Class<?> entityType) {
        val typeSpec = specLoader.specForTypeElseFail(entityType);
        return new DataTable(typeSpec, Can.ofCollection(repositoryService.allInstances(entityType))
                .map(entityPojo->ManagedObject.adaptSingular(typeSpec, entityPojo)));
    }

    public Stream<DataTable> streamDataTables() {
        return beanTypeRegistry.getEntityTypes().keySet()
            .stream()
            .sorted((a, b)->a.getSimpleName().compareTo(b.getSimpleName()))
            .map(this::dataTable);
    }
}
