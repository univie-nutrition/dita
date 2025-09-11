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
package dita.globodiet.survey.util;

import java.util.Optional;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import org.jspecify.annotations.NonNull;

import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24.Food;

/**
 * In 2024, the Austrian GloboDiet food description model introduced the concept of 'associated' food,
 * that can be quickly entered during interviews,
 * but requires a post-processing step later (before interview reporting),
 * with which those reported food nodes are to be replaced by an associated recipe.
 * <p>
 * The association link between food and recipe is made via references in their name:
 * <ul>
 * <li>food reference their associated recipe via suffix {assocRecp=‹recipe-code›}</li>
 * <li>recipes reference their associated food via suffix {assocFood=‹food-code›}</li>
 * </ul>
 */
public record AssociatedRecipeResolver(
        @NonNull FoodDescriptionModel foodDescriptionModel,
        @NonNull FoodToCompositeConverter foodToCompositeConverter
        ) implements Transfomer {

    public AssociatedRecipeResolver(final FoodDescriptionModel foodDescriptionModel) {
        this(foodDescriptionModel, new FoodToCompositeConverter(foodDescriptionModel));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecallNode24> T transform(final T node) {

        return switch(node) {
            // we replace the original food by its associated recipe
            case Food origFood -> {
                var fdmAdapter = new FDMAdapter(origFood.sid().systemId(), foodDescriptionModel);

                FoodDescriptionModel.Recipe associatedRecipe = fdmAdapter
                        .associatedRecipe(origFood, CodeFromName.parseAssocRecipe(origFood.name()))
                        .orElse(null);
                if(associatedRecipe==null) yield (T)origFood;

                // replace the food node by its associated composite node ..
                var composite = foodToCompositeConverter
                    .foodToRecipe(origFood, associatedRecipe, "recipe associated by food")
                    .build();

                yield (T) composite;
            }
            default -> node;
        };
    }

    // -- HELPER

    private record FDMAdapter(
            @NonNull SystemId systemId,
            @NonNull FoodDescriptionModel foodDescriptionModel) {

        /**
         * Optionally returns the associated {@link Recipe},
         * based on whether a suffix {assocRecp=‹recipe-code›} is present.
         * <p>
         * Fails if the recipe-code is present but cannot be resolved.
         */
        Optional<Recipe> associatedRecipe(final @NonNull Food origFood, final @NonNull CodeFromName codeFromName) {
            var associatedRecipeSid = codeFromName.associatedRecipeSid(systemId).orElse(null);
            if(associatedRecipeSid == null) return Optional.empty();
            var associatedRecipe = foodDescriptionModel.lookupRecipeBySid(associatedRecipeSid);
            if(associatedRecipe.isEmpty()) {
                throw _Exceptions.illegalArgument("failed to resolve recipe for %s using sid %s",
                        origFood.name(),
                        associatedRecipeSid);
            }
            return associatedRecipe;
        }

    }

}
