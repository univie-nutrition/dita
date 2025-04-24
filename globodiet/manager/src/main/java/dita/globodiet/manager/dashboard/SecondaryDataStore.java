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
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.causeway.replicator.tables.model.DataTableService;

public record SecondaryDataStore(DataTableService dataTableService) {

    Optional<EntityManagerFactory> createEntityManagerFactory(final String profile) {
        return createEntityManagerFactory(profile, new AsciiDocBuilder());
    }

    Optional<EntityManagerFactory> createEntityManagerFactory(
            final String profile,
            final AsciiDocBuilder adoc) {
        var conf = new Properties();

        var additionalConfigDir = new File("/opt/config");
        if(additionalConfigDir.exists()) {
            DataSource.ofFile(new File(additionalConfigDir, "application-" + profile + ".properties"))
                .tryReadAndAccept(conf::load);
        } else {
            DataSource.ofResource(getClass(), "/config/application-" + profile + ".properties")
                .tryReadAndAccept(conf::load);
        }

        // sanity check
        if(configValue(conf, "spring.datasource.url")==null) {
            adoc.append(doc->{
                var sourceBlock = AsciiDocFactory.sourceBlock(doc, "txt", "missing application-SQLSERVER.properties");
                sourceBlock.setTitle("Replication Setup");
                System.err.println("missing application-" + profile + ".properties");
            });
            return Optional.empty();
        }

        var p = new Properties();
        p.setProperty(PersistenceUnitProperties.JDBC_DRIVER, configValue(conf, "spring.datasource.driver-class-name"));
        p.setProperty(PersistenceUnitProperties.JDBC_URL, configValue(conf, "spring.datasource.url"));
        p.setProperty(PersistenceUnitProperties.JDBC_USER, configValue(conf, "spring.datasource.username"));

        p.setProperty("eclipselink.target-database", "sqlserver");
        p.setProperty("eclipselink.logging.level", "INFO");
        p.setProperty("eclipselink.ddl-generation", "none");
        p.setProperty("eclipselink.cache.shared.default", "false");
        p.setProperty("eclipselink.jdbc.allow-native-sql-queries", "true");

        // output settings, suppress password
        adoc.append(doc->{
            var sb = new StringBuilder();
            p.forEach((k, v)->{
                sb.append(String.format("%s: %s\n", k, v));
            });
            var sourceBlock = AsciiDocFactory.sourceBlock(doc, "txt", sb.toString());
            sourceBlock.setTitle("Replication Setup");
        });

        System.err.printf("secondard connecton:%n %s%n", p);

        p.setProperty(PersistenceUnitProperties.JDBC_PASSWORD, configValue(conf, "spring.datasource.password"));

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("DitaModuleGdParams", p);
        return Optional.of(emf);
    }

    // -- HELPER

    private String configValue(final Properties properties, final String key) {
        return properties.getProperty(key);
    }

}
