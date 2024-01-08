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

import java.io.File;
import java.util.Optional;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.NameTransformer;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.dashboard.Dashboard_generateYaml.ExportFormat;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Action//(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL,
    cssClassFa = "solid clone",
    describedAs = "Replicates given table-data (YAML) to a secondary MS-SQL instance."
            + "\nWarning: Long-Running")
@RequiredArgsConstructor
public class Dashboard_replicateYaml {

    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;

    final Dashboard dashboard;

    @MemberSupport
    public AsciiDoc act(
            @Parameter
            final ExportFormat format,
            @Parameter
            @ParameterLayout(named = "tableData")
            final Clob tableData) {

        val adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Table Replicate Result"));

        createPersistenceManagerFactory(adoc)
            .ifPresent(pmf->{
                var pm = pmf.getPersistenceManager();
                try {
                    adoc.append(doc->{
                      val sourceBlock = AsciiDocFactory.sourceBlock(doc, "yml", replicate(tableData,
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
                BlobStore.paramsTableFilter(), pm);
    }

    private Optional<PersistenceManagerFactory> createPersistenceManagerFactory(final AsciiDocBuilder adoc) {
        var conf = new Properties();

        var additionalConfigDir = new File("/opt/config");
        if(additionalConfigDir.exists()) {
            DataSource.ofFile(new File(additionalConfigDir, "application-SQLSERVER.properties"))
                .tryReadAndAccept(conf::load);
        } else {
            DataSource.ofResource(getClass(), "/config/application-SQLSERVER.properties")
                .tryReadAndAccept(conf::load);
        }

        // sanity check
        if(configValue(conf, "spring.datasource.url")==null) {
            adoc.append(doc->{
                var sourceBlock = AsciiDocFactory.sourceBlock(doc, "txt", "missing application-SQLSERVER.properties");
                sourceBlock.setTitle("Replication Setup");
            });
            return Optional.empty();
        }

        var dn = new Properties();
        dn.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
        dn.setProperty("javax.jdo.option.ConnectionURL", configValue(conf, "spring.datasource.url"));
        dn.setProperty("javax.jdo.option.ConnectionUserName", configValue(conf, "spring.datasource.username"));
        dn.setProperty("datanucleus.query.jdoql.allowAll", "true");

        // output settings, suppress password
        adoc.append(doc->{
            var sb = new StringBuilder();
            dn.forEach((k, v)->{
                sb.append(String.format("%s: %s\n", k, v));
            });
            var sourceBlock = AsciiDocFactory.sourceBlock(doc, "txt", sb.toString());
            sourceBlock.setTitle("Replication Setup");
        });

        dn.setProperty("javax.jdo.option.ConnectionPassword", configValue(conf, "spring.datasource.password"));

        final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(dn);
        return Optional.of(pmf);
    }

    private String configValue(final Properties properties, final String key) {
        return properties.getProperty(key);
    }

}
