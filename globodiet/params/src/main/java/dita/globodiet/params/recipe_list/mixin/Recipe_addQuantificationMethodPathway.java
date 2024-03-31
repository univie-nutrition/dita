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
package dita.globodiet.params.recipe_list.mixin;

import java.util.List;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.RequiredArgsConstructor;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForRecipe;
import dita.globodiet.params.quantif.Photo;
import dita.globodiet.params.quantif.Shape;
import dita.globodiet.params.recipe_list.Recipe;

@Action
@ActionLayout(
        fieldSetId = "dependentQuantificationMethodPathwayForRecipeMappedByRecipe",
        position = Position.PANEL,
        describedAs = "Add a (new) Quantification Method Pathway for a Recipe")
@RequiredArgsConstructor
public class Recipe_addQuantificationMethodPathway {

    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    final Recipe mixee;

    @MemberSupport
    public Recipe act(
            @Parameter
            final QuantificationMethodPathwayForRecipe.QuantificationMethod quantificationMethod,

            /**
             * only required for QuantificationMethod PHOTO or SHAPE
             */
            @Parameter(
                    optionality = Optionality.OPTIONAL)
            @ParameterLayout(
                    describedAs = "Only required for Quantification Method PHOTO or SHAPE."
                    )
            final String photoOrShapeCode) {

        var recipeCode = mixee.getCode();
        var quantMethodPathway =
                repositoryService.detachedEntity(new QuantificationMethodPathwayForRecipe());

        quantMethodPathway.setRecipeCode(recipeCode);
        quantMethodPathway.setQuantificationMethod(quantificationMethod);
        _Strings.nonEmpty(photoOrShapeCode)
            .ifPresent(quantMethodPathway::setPhotoOrShapeCode);

        repositoryService.persist(quantMethodPathway);
        foreignKeyLookupService.clearCache(QuantificationMethodPathwayForRecipe.class);

        return mixee;
    }

    //TODO don't provide duplicates (already selected ones)
    @MemberSupport
    public List<String> choicesPhotoOrShapeCode(final QuantificationMethodPathwayForRecipe.QuantificationMethod quantificationMethod) {
        if(quantificationMethod==null) return List.of();
        return switch (quantificationMethod) {
        case PHOTO -> repositoryService.allInstances(Photo.class).stream().map(Photo::getCode).toList();
        case SHAPE -> repositoryService.allInstances(Shape.class).stream().map(Shape::getCode).toList();
        default -> List.of();
        };
    }

}
