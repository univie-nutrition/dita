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
package dita.globodiet.manager.services.tabular;

import java.io.File;

import jakarta.inject.Inject;

import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;
import org.apache.causeway.core.metamodel.context.MetaModelContext;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;

@RequiredArgsConstructor
class YamlExporter {

    @Inject TableSerializerYaml tableSerializerYaml;

    @SneakyThrows
    public void export(final TabularSheet tabularSheet, final File tempFile) {

        // self managed injection
        MetaModelContext.instanceElseFail().getServiceInjector().injectServicesInto(this);

//        var persistentDataColumns = tabularSheet.getElementType()
//                .streamProperties(MixedIn.EXCLUDED)
//                .filter(prop->prop.isIncludedWithSnapshots())
//                .sorted(ObjectMember.Comparators.byMemberOrderSequence(false))
//                .collect(Can.toCan());
//
//        var transformedTable = new DataTable(
//                table.getElementType(),
//                table.getTableFriendlyName(),
//                persistentDataColumns,
//                table.streamDataElements().collect(Can.toCan()));
//
//        var clob = tableSerializerYaml.clobFromDataTables(
//                table.getTableFriendlyName(),
//                TabularData.NameTransformer.IDENTITY,
//                Can.of(transformedTable));
//        clob.writeToUtf8(tempFile);
    }

}