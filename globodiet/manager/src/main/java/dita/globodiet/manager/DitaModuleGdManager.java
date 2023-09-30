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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.extensions.docgen.help.CausewayModuleExtDocgenHelp;
import org.apache.causeway.extensions.docgen.help.applib.HelpNode.HelpTopic;
import org.apache.causeway.extensions.docgen.help.topics.domainobjects.CausewayEntityDiagramPage;
import org.apache.causeway.extensions.exceldownload.wkt.ui.CausewayModuleExtExcelDownloadWicketUi;
import org.apache.causeway.extensions.secman.encryption.spring.CausewayModuleExtSecmanEncryptionSpring;
import org.apache.causeway.extensions.secman.integration.CausewayModuleExtSecmanIntegration;
import org.apache.causeway.extensions.secman.integration.authenticator.AuthenticatorSecmanAutoConfiguration;
import org.apache.causeway.extensions.secman.jdo.CausewayModuleExtSecmanPersistenceJdo;
import org.apache.causeway.persistence.jdo.datanucleus.CausewayModulePersistenceJdoDatanucleus;
import org.apache.causeway.valuetypes.asciidoc.metamodel.semantics.AsciiDocValueSemantics;
import org.apache.causeway.valuetypes.asciidoc.ui.wkt.CausewayModuleValAsciidocUiWkt;
import org.apache.causeway.viewer.restfulobjects.jaxrsresteasy.CausewayModuleViewerRestfulObjectsJaxrsResteasy;
import org.apache.causeway.viewer.restfulobjects.viewer.CausewayModuleViewerRestfulObjectsViewer;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import dita.causeway.replicator.DitaModuleDatabaseReplicator;
import dita.commons.types.TabularData;
import dita.globodiet.dom.params.DitaModuleGdParams;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion_currentlyCheckedOutVersion;
import dita.globodiet.manager.blobstore.ParameterDataVersion_updateDescription;
import dita.globodiet.manager.blobstore.ParameterDataVersion_updateName;
import dita.globodiet.manager.dashboard.Dashboard;
import dita.globodiet.manager.editing.BrandManager_add;
import dita.globodiet.manager.editing.FoodManager_add;
import dita.globodiet.manager.help.DitaEntityDiagramPage;
import dita.globodiet.manager.help.DitaEntityDiagramPage2;
import dita.globodiet.manager.metadata.Persistable_schema;
import dita.globodiet.manager.services.layout.FallbackLayoutDataSourceGdParams;
import dita.globodiet.manager.services.lookup.DependantLookupGdParams;
import dita.globodiet.manager.services.lookup.ForeignKeyLookupGdParams;
import dita.globodiet.manager.services.search.SearchServiceGdParams;
import dita.globodiet.schema.GdEntityGen;
import dita.globodiet.schema.transform.EntityToTableTransformerFromSchema;
import dita.globodiet.schema.transform.TableToEntityTransformerFromSchema;
import dita.tooling.orm.OrmModel;
import lombok.val;

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
    CausewayModulePersistenceJdoDatanucleus.class,
    CausewayModuleViewerWicketViewer.class,

    // REST
    CausewayModuleViewerRestfulObjectsViewer.class,
    CausewayModuleViewerRestfulObjectsJaxrsResteasy.class,

    // Security Manager Extension
    CausewayModuleExtSecmanIntegration.class,
    CausewayModuleExtSecmanPersistenceJdo.class,
    CausewayModuleExtSecmanEncryptionSpring.class,
    AuthenticatorSecmanAutoConfiguration.class,

    AsciiDocValueSemantics.class,
    CausewayModuleValAsciidocUiWkt.class,
    CausewayModuleExtExcelDownloadWicketUi.class, // allows for collection download as excel

    // Help Pages
    DitaEntityDiagramPage.class,
    DitaEntityDiagramPage2.class,
    CausewayEntityDiagramPage.class,
    // Help Modules
    DitaModuleGdManager.Help.class, // to register before the DocGen module
    CausewayModuleExtDocgenHelp.class,

    // Homepage
    Dashboard.class,

    // Services
    DependantLookupGdParams.class,
    ForeignKeyLookupGdParams.class,
    SearchServiceGdParams.class,
    FallbackLayoutDataSourceGdParams.class,

    // Mixins
    HasCurrentlyCheckedOutVersion_currentlyCheckedOutVersion.class,
    ParameterDataVersion_updateName.class,
    ParameterDataVersion_updateDescription.class,
    Persistable_schema.class,
    // Manager Mixins
    BrandManager_add.class,
    FoodManager_add.class,

    // -- MODULES
    DitaModuleGdParams.class,
    DitaModuleDatabaseReplicator.class,

})
public class DitaModuleGdManager {

    public final static String NAMESPACE = "dita.globodiet.manager";

    @Bean
    public OrmModel.Schema gdParamsSchema() {
        val schema = OrmModel.Schema.fromYaml(DataSource.ofResource(GdEntityGen.class, "/gd-params.schema.yaml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail());
        return schema;
    }

    @Bean @Qualifier("entity2table")
    public TabularData.NameTransformer entity2table(final OrmModel.Schema gdParamsSchema) {
        return new EntityToTableTransformerFromSchema("dita.globodiet", gdParamsSchema);
    }

    @Bean @Qualifier("table2entity")
    public TabularData.NameTransformer table2entity(final OrmModel.Schema gdParamsSchema) {
        return new TableToEntityTransformerFromSchema("dita.globodiet", gdParamsSchema);
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
                final DitaEntityDiagramPage ditaEntityDiagramPage,
                final DitaEntityDiagramPage2 ditaEntityDiagramPage2) {

            val root = HelpTopic.root("Topics");

            //root.addPage(welcomeHelpPage);

            root.subTopic("Causeway")
                .addPage(causewayEntityDiagramPage);

            root.subTopic("Dita")
                .addPage(ditaEntityDiagramPage)
                .addPage(ditaEntityDiagramPage2);

            return root;
        }

    }

}
