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
import javax.jdo.PersistenceManagerFactory;

import org.datanucleus.api.jdo.metadata.api.ClassMetadataImpl;
import org.datanucleus.metadata.AbstractClassMetaData;

import org.apache.causeway.commons.internal.reflection._Reflect;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.causeway.replicator.tables.model.DataTableService;
import dita.globodiet.manager.versions.VersionsService;
import lombok.SneakyThrows;

public record SecondaryDataStore(DataTableService dataTableService) {

    Optional<PersistenceManagerFactory> createPersistenceManagerFactory(
            final String profile){
        return createPersistenceManagerFactory(profile, new AsciiDocBuilder());
    }

    Optional<PersistenceManagerFactory> createPersistenceManagerFactory(
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

        System.err.printf("secondard connecton:%n %s%n", dn);

        dn.setProperty("javax.jdo.option.ConnectionPassword", configValue(conf, "spring.datasource.password"));

        final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(dn);
        return postProcessDnMetaModel(pmf);
    }

    // -- HELPER

    private String configValue(final Properties properties, final String key) {
        return properties.getProperty(key);
    }

    private Optional<PersistenceManagerFactory> postProcessDnMetaModel(
            final PersistenceManagerFactory pmf) {
        try {
            dataTableService.streamEntities()
            .filter(VersionsService.paramsTableFilter()) //XXX reuse the filter from above, don't recreate
            .map(ObjectSpecification::getCorrespondingClass)
            .map(Class::getName)
            .forEach(entityClassName->toEntityWithNonDurableId(pmf, entityClassName));
        } catch (Exception e) {
            e.printStackTrace(); //XXX could report issues into adoc result
            return Optional.empty();
        }
        return Optional.of(pmf);
    }

    @SneakyThrows
    private void toEntityWithNonDurableId(final PersistenceManagerFactory pmf, final String entityClassName) {
        var metaData = pmf.getMetadata(entityClassName);
        // equivalent to metaData1.setIdentityType(IdentityType.NONDURABLE);
        _Reflect.setFieldOn(AbstractClassMetaData.class.getDeclaredField("identityType"),
                ((ClassMetadataImpl)metaData).getInternal(),
                org.datanucleus.metadata.IdentityType.NONDURABLE);
        // debug
        //System.err.printf("metaData1%n%s%n", metaData1);
    }

}
