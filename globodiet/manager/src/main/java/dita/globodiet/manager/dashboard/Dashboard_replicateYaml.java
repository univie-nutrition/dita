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
package dita.globodiet.manager.dashboard;

import javax.jdo.PersistenceManager;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import lombok.RequiredArgsConstructor;


import dita.causeway.replicator.tables.model.DataTableService;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.NameTransformer;
import dita.globodiet.manager.versions.VersionsExportService;
import dita.globodiet.manager.versions.VersionsExportService.ExportFormat;

@Action//(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL,
    cssClassFa = "solid clone",
    describedAs = "Replicates given table-data (YAML) to a secondary MS-SQL instance."
            + "\nWarning: Long-Running"
            + "\nEnable TLSv1: goto jdk/lib/security/java.security, "
            + "find option jdk.tls.disabledAlgorithms and delete TLSv1")
@RequiredArgsConstructor
public class Dashboard_replicateYaml {

    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;
    @Inject DataTableService dataTableService;

    final Dashboard dashboard;

    @MemberSupport
    public AsciiDoc act(
            @Parameter
            final ExportFormat format,
            @Parameter
            @ParameterLayout(named = "tableData")
            final Clob tableData) {

        var adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Table Replicate Result"));

        new SecondaryDataStore(dataTableService)
        .createPersistenceManagerFactory("SQLSERVER", adoc)
            .ifPresent(pmf->{
                var pm = pmf.getPersistenceManager();
                try {
                    adoc.append(doc->{
                      var sourceBlock = AsciiDocFactory.sourceBlock(doc, "yml", replicate(tableData,
                              format==ExportFormat.ENTITY
                                  ? TabularData.NameTransformer.IDENTITY
                                  : table2entity, pm));
                      sourceBlock.setTitle("Replication Result");
                  });
                } finally {
                    pm.close();
                    pmf.close();
                }
            });

        //TODO finally issue an SQL statement
        // BACKUP DATABASE GloboDiet TO DISK = '/var/opt/mssql/backup/dita/GloboDiet2024.bak'

        return adoc.buildAsValue();
    }

    // -- HELPER

    private String replicate(final Clob tableData, final NameTransformer nameTransformer, final PersistenceManager pm) {
        return tableSerializer.replicate(tableData, nameTransformer,
                VersionsExportService.paramsTableFilter(), pm);
    }

}
