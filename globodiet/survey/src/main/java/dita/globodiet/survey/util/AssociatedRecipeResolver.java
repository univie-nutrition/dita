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

import lombok.NonNull;

import dita.commons.format.FormatUtils;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
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

    public AssociatedRecipeResolver(FoodDescriptionModel foodDescriptionModel) {
        this(foodDescriptionModel, new FoodToCompositeConverter(foodDescriptionModel));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecallNode24> T transform(final T node) {

        return switch(node) {
            // we replace the original food by its associated recipe
            case Food origFood -> {
                var fdmAdapter = new FDMAdapter(origFood.sid().systemId(), foodDescriptionModel);

                var associatedRecipe = fdmAdapter
                        .associatedRecipe(NameWithCode.parseAssocRecipe(origFood.name()))
                        .orElse(null);
                if(associatedRecipe==null) yield (T)origFood;

                // replace the (proxy-) food node by its associated composite node ..
                var recordBuilder = foodToCompositeConverter.foodToRecipe(origFood, associatedRecipe);
                recordBuilder.name(NameWithCode.parseAssocFood(associatedRecipe.name()).nameWithResolvedSuffix());
                yield (T)recordBuilder.build();
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
        Optional<Recipe> associatedRecipe(@NonNull final NameWithCode nameWithCode) {
            var associatedRecipeSid = nameWithCode.associatedRecipeSid(systemId).orElse(null);
            if(associatedRecipeSid == null) return Optional.empty();
            var associatedRecipe = foodDescriptionModel.lookupRecipeBySid(associatedRecipeSid);
            if(associatedRecipe.isEmpty()) {
                throw _Exceptions.illegalArgument("failed to resolve recipe for %s using sid %s",
                        nameWithCode.name(),
                        associatedRecipeSid);
            }
            return associatedRecipe;
        }

    }

    private record NameWithCode(String name, String code) {
        static NameWithCode parseAssocFood(final String nameAndCode) {
            return parse(nameAndCode, "{assocFood=");
        }
        static NameWithCode parseAssocRecipe(final String nameAndCode) {
            return parse(nameAndCode, "{assocRecp=");
        }
        static NameWithCode parse(final String nameAndCode, final String magic) {
            int c1 =nameAndCode.indexOf(magic);
            if(c1==-1) return new NameWithCode(nameAndCode, null);
            int c2 = nameAndCode.indexOf("}", c1);
            return new NameWithCode(
                    nameAndCode.substring(0, c1).trim(),
                    FormatUtils.fillWithLeadingZeros(5, nameAndCode.substring(c1 + magic.length(), c2)));
        }
        String nameWithResolvedSuffix() {
            return name() + " {resolved}";
        }
        private Optional<SemanticIdentifier> associatedRecipeSid(final SystemId systemId) {
            return Optional.ofNullable(code())
                    .map(code->ObjectId.Context.RECIPE.sid(systemId, code));
        }
    }

}
