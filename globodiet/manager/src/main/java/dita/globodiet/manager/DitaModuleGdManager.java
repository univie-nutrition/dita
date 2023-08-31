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

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import org.apache.causeway.core.config.presets.CausewayPresets;
import org.apache.causeway.core.runtimeservices.CausewayModuleCoreRuntimeServices;
import org.apache.causeway.extensions.docgen.help.CausewayModuleExtDocgenHelp;
import org.apache.causeway.extensions.secman.encryption.spring.CausewayModuleExtSecmanEncryptionSpring;
import org.apache.causeway.extensions.secman.integration.CausewayModuleExtSecmanIntegration;
import org.apache.causeway.extensions.secman.integration.authenticator.AuthenticatorSecmanAutoConfiguration;
import org.apache.causeway.extensions.secman.jdo.CausewayModuleExtSecmanPersistenceJdo;
import org.apache.causeway.extensions.viewer.wicket.exceldownload.ui.CausewayModuleExtExcelDownloadWicketUi;
import org.apache.causeway.persistence.jdo.datanucleus.CausewayModulePersistenceJdoDatanucleus;
import org.apache.causeway.valuetypes.asciidoc.metamodel.semantics.AsciiDocValueSemantics;
import org.apache.causeway.valuetypes.asciidoc.ui.wkt.CausewayModuleValAsciidocUiWkt;
import org.apache.causeway.viewer.restfulobjects.jaxrsresteasy.CausewayModuleViewerRestfulObjectsJaxrsResteasy;
import org.apache.causeway.viewer.restfulobjects.viewer.CausewayModuleViewerRestfulObjectsViewer;
import org.apache.causeway.viewer.wicket.viewer.CausewayModuleViewerWicketViewer;

import dita.causeway.replicator.DitaModuleDatabaseReplicator;
import dita.globodiet.dom.params.DitaModuleGdParams;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion_currentlyCheckedOutVersion;
import dita.globodiet.manager.blobstore.ParameterDataVersion_updateDescription;
import dita.globodiet.manager.blobstore.ParameterDataVersion_updateName;
import dita.globodiet.manager.dashboard.Dashboard;
import dita.globodiet.manager.food.FoodOrProductOrAlias_foodGroup2;
import dita.globodiet.manager.food.FoodOrProductOrAlias_foodSubSubgroup2;
import dita.globodiet.manager.food.FoodOrProductOrAlias_foodSubgroup2;

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

    // Docs
    CausewayModuleExtDocgenHelp.class,

    // Homepage
    Dashboard.class,

    // Mixins
    HasCurrentlyCheckedOutVersion_currentlyCheckedOutVersion.class,
    ParameterDataVersion_updateName.class,
    ParameterDataVersion_updateDescription.class,
    FoodOrProductOrAlias_foodGroup2.class,
    FoodOrProductOrAlias_foodSubgroup2.class,
    FoodOrProductOrAlias_foodSubSubgroup2.class,

    // -- MODULES
    DitaModuleGdParams.class,
    DitaModuleDatabaseReplicator.class,

})
public class DitaModuleGdManager {

    public final static String NAMESPACE = "dita.globodiet.manager";
}
