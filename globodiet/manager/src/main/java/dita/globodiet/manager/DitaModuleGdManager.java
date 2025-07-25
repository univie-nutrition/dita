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
package dita.globodiet.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.extensions.docgen.help.CausewayModuleExtDocgenHelp;
import org.apache.causeway.extensions.docgen.help.applib.HelpNode.HelpTopic;
import org.apache.causeway.extensions.docgen.help.topics.domainobjects.CausewayEntityDiagramPage;
import org.apache.causeway.extensions.secman.encryption.spring.CausewayModuleExtSecmanEncryptionSpring;
import org.apache.causeway.extensions.secman.integration.CausewayModuleExtSecmanIntegration;
import org.apache.causeway.extensions.secman.integration.authenticator.AuthenticatorSecmanAutoConfiguration;
import org.apache.causeway.extensions.secman.jpa.CausewayModuleExtSecmanPersistenceJpa;
import org.apache.causeway.extensions.tabular.excel.CausewayModuleExtTabularExcel;
import org.apache.causeway.persistence.jpa.eclipselink.CausewayModulePersistenceJpaEclipselink;
import org.apache.causeway.valuetypes.asciidoc.metamodel.semantics.AsciiDocValueSemantics;
import org.apache.causeway.valuetypes.asciidoc.ui.wkt.CausewayModuleValAsciidocUiWkt;
import org.apache.causeway.viewer.restfulobjects.viewer.CausewayModuleViewerRestfulObjectsViewer;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import dita.causeway.replicator.DitaModuleDatabaseReplicator;
import dita.commons.types.TabularData;
import dita.globodiet.cleaner.DitaModuleGdCleaner;
import dita.globodiet.manager.dashboard.Dashboard;
import dita.globodiet.manager.editing.wip.FoodManager_addFood;
import dita.globodiet.manager.editing.wip.Recipe_listIngredients;
import dita.globodiet.manager.help.DitaEntityDiagramPage;
import dita.globodiet.manager.help.DitaEntityDiagramPage2;
import dita.globodiet.manager.help.DitaTableNamesPage;
import dita.globodiet.manager.metadata.EntitySchemaProvider;
import dita.globodiet.manager.metadata.Persistable_schema;
import dita.globodiet.manager.schema.transform.EntityToTableTransformerFromSchema;
import dita.globodiet.manager.schema.transform.TableToEntityTransformerFromSchema;
import dita.globodiet.manager.services.iconfa.IconFaServiceGdParams;
import dita.globodiet.manager.services.idgen.IdGeneratorGdParams;
import dita.globodiet.manager.services.layout.FallbackLayoutDataSourceGdParams;
import dita.globodiet.manager.services.lookup.DependantLookupGdParams;
import dita.globodiet.manager.services.lookup.ForeignKeyLookupGdParams;
import dita.globodiet.manager.services.search.SearchServiceGd;
import dita.globodiet.manager.versions.ParameterDataVersion_updateDescription;
import dita.globodiet.manager.versions.ParameterDataVersion_updateName;
import dita.globodiet.params.DitaModuleGdParams;
import dita.globodiet.survey.DitaModuleGdSurvey;
import io.github.causewaystuff.companion.codegen.model.Schema;
import io.github.causewaystuff.companion.codegen.model.Schema.Domain;

/**
 * Makes the integral parts of the web application.
 */
@Configuration
@PropertySources({
    @PropertySource(CausewayPresets.NoTranslations),
    @PropertySource(CausewayPresets.DebugDiscovery),
    @PropertySource(CausewayPresets.DebugMetaModel),
})
@Import({
    CausewayModuleCoreRuntimeServices.class,
    CausewayModulePersistenceJpaEclipselink.class,
    CausewayModuleViewerWicketViewer.class,

    // REST
    CausewayModuleViewerRestfulObjectsViewer.class,

    // Security Manager Extension
    CausewayModuleExtSecmanIntegration.class,
    CausewayModuleExtSecmanPersistenceJpa.class,
    CausewayModuleExtSecmanEncryptionSpring.class,
    AuthenticatorSecmanAutoConfiguration.class,

    AsciiDocValueSemantics.class,
    CausewayModuleValAsciidocUiWkt.class,
    CausewayModuleExtTabularExcel.class, // allows for collection download as excel

    // Help Pages
    DitaTableNamesPage.class,
    DitaEntityDiagramPage.class,
    DitaEntityDiagramPage2.class,
    CausewayEntityDiagramPage.class,
    // Help Modules
    DitaModuleGdManager.Help.class, // to register before the DocGen module
    CausewayModuleExtDocgenHelp.class,

    // Homepage
    Dashboard.class,

    // Services/Components
    DependantLookupGdParams.class,
    ForeignKeyLookupGdParams.class,
    SearchServiceGd.class,
    FallbackLayoutDataSourceGdParams.class,
    IdGeneratorGdParams.class,
    IconFaServiceGdParams.class,

    // Mixins
    ParameterDataVersion_updateName.class,
    ParameterDataVersion_updateDescription.class,
    Persistable_schema.class,
    // Manager Mixins
    FoodManager_addFood.class,
    Recipe_listIngredients.class,

    // -- MODULES
    DitaModuleGdParams.class,
    DitaModuleGdSurvey.class,
    DitaModuleGdCleaner.class,
    DitaModuleDatabaseReplicator.class,

})
public class DitaModuleGdManager {

    public final static String NAMESPACE = "dita.globodiet.manager";

    @Bean
    public EntitySchemaProvider entitySchemaProvider() {

        var map = new HashMap<String, Schema.Entity>();

        Stream.<DataSource>of(
                DitaModuleGdParams.schemaSource(),
                DitaModuleGdSurvey.schemaSource())
            .map(DataSource::tryReadAsStringUtf8)
            .map(Try::valueAsNonNullElseFail)
            .map(Schema.Domain::fromYaml)
            .map(Domain::entities)
            .map(Map::values)
            .flatMap(Collection::stream)
            .forEach(entity->map.put(entity.namespaceResolved() + "." + entity.name(), entity));

        return new EntitySchemaProvider(map);
    }

    @Bean @Qualifier("entity2table")
    public TabularData.NameTransformer entity2table(final EntitySchemaProvider entitySchemaProvider) {
        return new EntityToTableTransformerFromSchema("dita.globodiet.params", entitySchemaProvider.asDomain());
    }

    @Bean @Qualifier("table2entity")
    public TabularData.NameTransformer table2entity(final EntitySchemaProvider entitySchemaProvider) {
        return new TableToEntityTransformerFromSchema("dita.globodiet.params", entitySchemaProvider.asDomain());
    }

    @Configuration
    public static class Help {

        /**
         * The help index (tree), overriding the default.
         */
        @Bean(NAMESPACE + ".RootHelpTopic")
        @Primary
        public HelpTopic rootHelpTopicForDita(
                final CausewayEntityDiagramPage causewayEntityDiagramPage,
                final DitaTableNamesPage ditaTableNamesPage,
                final DitaEntityDiagramPage ditaEntityDiagramPage,
                final DitaEntityDiagramPage2 ditaEntityDiagramPage2) {

            var root = HelpTopic.root("Topics");

            //root.addPage(welcomeHelpPage);

            root.subTopic("Causeway")
                .addPage(causewayEntityDiagramPage);

            root.subTopic("Dita - Globodiet Manager")
                .addPage(ditaTableNamesPage)
                .addPage(ditaEntityDiagramPage)
                .addPage(ditaEntityDiagramPage2);

            return root;
        }

    }

}
