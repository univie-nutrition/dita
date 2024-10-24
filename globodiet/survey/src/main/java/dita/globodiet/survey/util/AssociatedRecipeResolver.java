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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.NonNull;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.format.FormatUtils;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Annotation;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
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
        @NonNull FoodDescriptionModel foodDescriptionModel
        ) implements Transfomer {

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecallNode24> T transform(final T node) {

        return switch(node) {
            case Food origFood -> {

                var fdmAdapter = new FDMAdapter(origFood.sid().systemId(), foodDescriptionModel);

                var associatedRecipe = fdmAdapter
                        .associatedRecipe(NameWithCode.parseAssocRecipe(origFood.name()))
                        .orElse(null);
                if(associatedRecipe==null) yield (T)origFood;

                var recordBuilder = new Composite.Builder();

                // replace the (proxy-) food node by its associated composite node ..

                recordBuilder.type(Record24.Type.COMPOSITE);
                recordBuilder.name(NameWithCode.parseAssocFood(associatedRecipe.name()).nameWithResolvedSuffix());
                recordBuilder.sid(associatedRecipe.sid());
                // keep the original food as comment
                recordBuilder.subRecords().add(origFoodAsComment(origFood));

                // store GloboDiet food description group data as annotation
                recordBuilder.annotations().clear();
                recordBuilder.annotations().add(new Annotation("group",
                        associatedRecipe.groupSid()
                            .mapObjectId(o->o.mapContext(_->SidUtils.GdContext.RECIPE_GROUP.id()))
                        ));

                //TODO[dita-globodiet-survey-24] what facets to put here?
                recordBuilder.facetSids(SemanticIdentifierSet.wip());

                fdmAdapter.streamIngredientsOfRecipeSid(associatedRecipe.sid())
                    .map(ingr->{
                        var food = fdmAdapter.foodBySid(ingr.foodSid());
                        var foodBuilder = new Food.Builder()
                            .name(food.name())
                            .sid(ingr.foodSid())
                            .facetSids(SemanticIdentifierSet.empty()) //TODO[dita-globodiet-survey] missing ingredient facets
                            .amountConsumed(BigDecimal.ONE.negate()) //TODO[dita-globodiet-survey-24] fix actual amount
                            .consumptionUnit(ConsumptionUnit.GRAM);  //TODO[dita-globodiet-survey-24] fix actual unit
                        return foodBuilder.build();
                    })
                    .forEach(recordBuilder.subRecords()::add);

                yield (T)recordBuilder.build();

            }
            default -> node;
        };
    }

    // -- HELPER

    Record24.Comment origFoodAsComment(final Food origFood) {
        var name = "%s, amount=%s, raw/cooked=%s".formatted(
                origFood.name().replace(" {", ", ").replace("}", ""),
                origFood.consumptionUnit().format(origFood.amountConsumed()),
                origFood.rawPerCookedRatio());

        return Record24.comment(name, origFood.sid(), origFood.facetSids(),
                Can.ofCollection(origFood.annotations().values()));
    }

    private record FDMAdapter(
            @NonNull SystemId systemId,
            @NonNull FoodDescriptionModel foodDescriptionModel) {
        FoodDescriptionModel.Food foodBySid(final SemanticIdentifier sid) {
            return foodDescriptionModel.foodBySid().get(sid);
        }

        Stream<FoodDescriptionModel.RecipeIngredient> streamIngredientsOfRecipeSid(final SemanticIdentifier sid){
            return _NullSafe.stream(foodDescriptionModel.ingredientsByRecipeSid()
                    .get(sid));
        }

        /**
         * Optionally returns the associated {@link Recipe},
         * based on whether a suffix {assocRecp=‹recipe-code›} is present.
         * <p>
         * Fails if the recipe-code is present but cannot be resolved.
         */
        Optional<Recipe> associatedRecipe(@NonNull final NameWithCode nameWithCode) {
            var associatedRecipeSid = nameWithCode.associatedRecipeSid(systemId).orElse(null);
            if(associatedRecipeSid == null) return Optional.empty();
            var associatedRecipe = foodDescriptionModel.recipeBySid().get(associatedRecipeSid);
            if(associatedRecipe==null) {
                throw _Exceptions.illegalArgument("failed to resolve recipe for %s using sid %s",
                        nameWithCode.name(),
                        associatedRecipeSid);
            }
            return Optional.of(associatedRecipe);
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
                    .map(code->SidUtils.GdContext.RECIPE.sid(systemId, code));
        }
    }

}
