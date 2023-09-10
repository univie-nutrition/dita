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
package dita.globodiet.manager.help;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.springframework.stereotype.Component;

import org.apache.causeway.applib.services.metamodel.MetaModelService;
import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;

import dita.globodiet.manager.DitaModuleGdManager;
import dita.tooling.orm.OrmModel;

@Component
@Named(DitaModuleGdManager.NAMESPACE + ".DitaEntityDiagramPage")
public class DitaEntityDiagramPage extends DitaEntityDiagramPageAbstract {

    @Inject
    public DitaEntityDiagramPage(
            final OrmModel.Schema gdParamsSchema,
            final MetaModelService metaModelService) {
        super(gdParamsSchema, metaModelService);
    }

    @Override
    public String getTitle() {
        return "Dita Entity Diagram";
    }

    @Override
    protected String diagramTitle() {
        return "Entity Relations";
    }

    @Override
    protected String renderObjectGraph(final ObjectGraph objectGraph) {
        return super.renderObjectGraphUsingPlantuml(objectGraph);
    }

}

