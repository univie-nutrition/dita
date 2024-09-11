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
package dita.globodiet.survey;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.RequiredArgsConstructor;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.format.FormatUtils;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.recall24.dto.RecallNode24.Annotation;
import dita.recall24.dto.RecallNode24.Builder24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Food;

@RequiredArgsConstructor
public class AssociatedRecipeResolver implements Transfomer {

    private final FoodDescriptionModel foodDescriptionModel;

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

    @Override
    public void accept(final Builder24<?> builder) {
        switch(builder) {
            case Food.Builder recordBuilder -> {
                var origFood = recordBuilder.build();
                var foodNameWithCode = NameWithCode.parseAssocRecipe(origFood.name());
                var associatedRecipeSid = Optional.ofNullable(foodNameWithCode.code())
                        .map(code->ObjectId.Context.RECIPE.sid(origFood.sid().systemId(), code))
                        .orElse(null);
                if(associatedRecipeSid == null) return;

                var associatedRecipe = foodDescriptionModel.recipeBySid().get(associatedRecipeSid);
                System.err.printf("associatedRecipe=%s%n", associatedRecipe);

                if(associatedRecipe==null) {
                    throw _Exceptions.illegalArgument("failed to resolve %s", origFood.name());
                }

                var recipeNameWithCode = NameWithCode.parseAssocFood(associatedRecipe.name());

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
                            .mapObjectId(o->o.mapContext(_->ObjectId.Context.RECIPE_GROUP.id()))
                        ));

                recordBuilder.facetSids(SemanticIdentifierSet.wip());

                var recipeIngredients = foodDescriptionModel.ingredientsByRecipeSid()
                        .get(associatedRecipeSid);
                _NullSafe.stream(recipeIngredients)
                    .map(ingr->{
                        var food = foodDescriptionModel.foodBySid().get(ingr.foodSid());
                        var foodBuilder = new Food.Builder(Record24.Type.FOOD)
                            .name(food.name())
                            .sid(ingr.foodSid())
                            .facetSids(SemanticIdentifierSet.empty())
                            .amountConsumed(BigDecimal.ONE.negate()) //TODO[dita-globodiet-survey-24] fix actual amount
                            .consumptionUnit(ConsumptionUnit.GRAM);  //TODO[dita-globodiet-survey-24] fix actual unit
                        return foodBuilder.build();
                    })
                    .forEach(recordBuilder.subRecords()::add);
            }
            default -> {}
        }
    }

}
