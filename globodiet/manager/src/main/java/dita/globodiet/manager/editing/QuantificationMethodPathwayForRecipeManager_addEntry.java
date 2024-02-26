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
package dita.globodiet.manager.editing;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import dita.commons.services.idgen.IdGeneratorService;
import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipe;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipe.QuantificationMethod;

@Action
@ActionLayout(
        fieldSetId = "listOfQuantificationMethodPathwayForRecipe",
        position = Position.PANEL,
        describedAs = "Add a (new) Quantification Method Pathway for a Recipe")
@RequiredArgsConstructor
public class QuantificationMethodPathwayForRecipeManager_addEntry {

    @Inject private RepositoryService repositoryService;
    @Inject private FactoryService factoryService;
    @Inject private IdGeneratorService idGeneratorService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    final QuantificationMethodPathwayForRecipe.Manager mixee;

    //TODO WIP
    @MemberSupport
    public QuantificationMethodPathwayForRecipe act(
            @Parameter final String recipeCode,
            @Parameter final QuantificationMethod quantificationMethod,
            @Parameter final String photoCode) {
        var quantMethodPathway =
                repositoryService.detachedEntity(new QuantificationMethodPathwayForRecipe());

        quantMethodPathway.setRecipeCode(recipeCode);
        quantMethodPathway.setPhotoCode(photoCode);
        quantMethodPathway.setQuantificationMethod(quantificationMethod);

        repositoryService.persist(repositoryService);
        return quantMethodPathway;
    }

    @MemberSupport
    public String disableAct() {
        return "Work in progress";
    }

}
