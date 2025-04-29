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
package dita.globodiet.manager.editing.wip;

import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.value.Markup;

import lombok.RequiredArgsConstructor;

import dita.globodiet.params.recipe_list.Recipe;
import dita.globodiet.params.recipe_list.RecipeDeps.Recipe_dependentRecipeIngredientMappedByRecipe;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="listOfRecipe", position = Position.PANEL)
@RequiredArgsConstructor
public class Recipe_listIngredients {

    @Inject private FactoryService factoryService;
    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    final Recipe recipe;

    @MemberSupport
    public Markup act() {
        var html = new StringBuilder();
        html.append("<h3>%s</h3>".formatted(recipe.title()));

        var ingredients = factoryService.mixin(Recipe_dependentRecipeIngredientMappedByRecipe.class, recipe)
            .coll().stream()
            .sorted((a, b)->Double.compare(
                b.getPercentageOrProportionAsConsumedForRecipeIngredients(),
                a.getPercentageOrProportionAsConsumedForRecipeIngredients()))
            .toList();
        html.append("<ul>%s</ul>".formatted(
            ingredients.stream()
                .map(ingr->"  <li>%.2f%% %s (code=%s)</li>"
                    .formatted(
                        ingr.getPercentageOrProportionAsConsumedForRecipeIngredients(),
                        ingr.getName(),
                        ingr.getFoodOrRecipeCode()))
                .collect(Collectors.joining("\n"))
        ));

        return Markup.valueOf(html.toString());
    }

}
