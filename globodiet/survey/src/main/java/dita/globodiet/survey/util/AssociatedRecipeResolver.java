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

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.NonNull;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.format.FormatUtils;
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.fdm.FoodDescriptionModel;
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

                var foodNameWithCode = NameWithCode.parseAssocRecipe(origFood.name());
                var associatedRecipeSid = Optional.ofNullable(foodNameWithCode.code())
                        .map(code->SidUtils.GdContext.RECIPE.sid(origFood.sid().systemId(), code))
                        .orElse(null);
                if(associatedRecipeSid == null) yield (T)origFood;

                var associatedRecipe = foodDescriptionModel.recipeBySid().get(associatedRecipeSid);
                if(associatedRecipe==null) {
                    throw _Exceptions.illegalArgument("failed to resolve recipe for %s", origFood.name());
                }
                var recipeNameWithCode = NameWithCode.parseAssocFood(associatedRecipe.name());

                var recordBuilder = new Composite.Builder();

                //TODO[dita-globodiet-survey-24] replace the (proxy-) food node by its associated composite node
                recordBuilder.type(Record24.Type.COMPOSITE);
                recordBuilder.name(recipeNameWithCode.name() + " {resolved}");
                recordBuilder.sid(associatedRecipeSid);
                recordBuilder.subRecords().add(Record24
                        .comment(origFood.name(), origFood.sid(), origFood.facetSids(),
                                Can.ofCollection(origFood.annotations().values())));

                recordBuilder.annotations().clear();
                recordBuilder.annotations().add(new Annotation("group",
                        associatedRecipe.groupSid()
                            .mapObjectId(o->o.mapContext(_->SidUtils.GdContext.RECIPE_GROUP.id()))
                        ));

                recordBuilder.facetSids(SemanticIdentifierSet.wip());

                var recipeIngredients = foodDescriptionModel.ingredientsByRecipeSid()
                        .get(associatedRecipeSid);
                _NullSafe.stream(recipeIngredients)
                    .map(ingr->{
                        var food = foodDescriptionModel.foodBySid().get(ingr.foodSid());
                        var foodBuilder = new Food.Builder()
                            .name(food.name())
                            .sid(ingr.foodSid())
                            .facetSids(SemanticIdentifierSet.empty())
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

    record NameWithCode(String name, String code) {
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
    }

}
