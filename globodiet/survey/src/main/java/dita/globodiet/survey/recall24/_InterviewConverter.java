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
package dita.globodiet.survey.recall24;

import java.time.LocalTime;

import javax.measure.quantity.Mass;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

import dita.commons.types.MetricUnits;
import dita.commons.types.Sex;
import dita.commons.util.NumberUtils;
import dita.globodiet.survey.recall24._Dtos.ListEntry;
import dita.globodiet.survey.recall24._Dtos.ListEntry.ListEntryType;
import dita.recall24.api.Record24.Type;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.Interview24;
import dita.recall24.model.Meal24;
import dita.recall24.model.MemorizedFood24;
import dita.recall24.model.Record24;
import dita.recall24.model.Respondent24;
import dita.recall24.model.RespondentMetaData24;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
class _InterviewConverter {

    /**
     * parented by an Respondent24 stub, that has no children (needs post-processing)
     */
    Interview24 toInterview24(final _Dtos.Interview iv) {
        final var tree = iv.asTree();
        var meals = tree.childNodes().stream()
            .map(fcoNode->{
                var fcoEntry = fcoNode.entry();
                var memorizedFoods = fcoNode.childNodes().stream()
                .map(qliNode->{
                    var qliEntry = qliNode.entry();
                    var records = qliNode.childNodes().stream()
                    .map(recordNode->{
                        var recordEntry = recordNode.entry();
                        var ingredients24 = recordNode.childNodes().stream()
                        .map(ingredientNode->{
                            var foodSelectedAsARecipeIngredientEntry = ingredientNode.entry();
                            return toIngredient24(foodSelectedAsARecipeIngredientEntry);
                        })
                        .collect(Can.toCan());

                        var record24 = toRecord24(recordEntry, ingredients24);
                        return record24;
                    })
                    .collect(Can.toCan());

                    var memorizedFood24 = toMemorizedFood24(qliEntry, records);
                    return memorizedFood24;
                })
                .collect(Can.toCan());

                var meal24 = toMeal24(fcoEntry, memorizedFoods);
                return meal24;
            })
            .collect(Can.toCan());

        return Interview24.of(
                respondentStub(iv),
                iv.getInterviewDate().toLocalDate(),
                new RespondentMetaData24(
                        ObjectRef.<Interview24>empty(),
                        iv.getSpecialDietCode(),
                        iv.getSpecialDayCode(),
                        NumberUtils.roundToNDecimalPlaces(iv.getHeightCM(), 1),
                        NumberUtils.roundToNDecimalPlaces(iv.getWeightKG(), 1)),
                meals);
    }

    // -- MEM

    private MemorizedFood24 toMemorizedFood24(final ListEntry listEntry, final Can<Record24> records) {
        _Assert.assertTrue(_Strings.isNullOrEmpty(listEntry.getName()));
        return MemorizedFood24.of(listEntry.getLabel(), records);
    }

    // -- RECORDS


//  Record24 toRecord24() {
//      return switch (listEntryType()) {
//      case Food -> toRecord24(Type.FOOD);
//      case FoodSelectedAsARecipeIngredient -> toRecord24(Type.FOOD); //TODO information is lost here
//      case FatDuringCookingForFood -> toRecord24(Type.INFORMAL); // TODO verify data contained is duplicated
//      case Recipe -> toRecord24(Type.COMPOSITE); //TODO ad-hoc or prepared?
//
//      case DietarySupplement -> toRecord24(Type.INCOMPLETE);
//      case FatDuringCookingForIngredient -> toRecord24(Type.INCOMPLETE);
//      case FatSauceOrSweeteners -> toRecord24(Type.INCOMPLETE);
//      //case RecipeSelectedAsARecipeIngredient -> toRecord24(Type.INCOMPLETE); //Not yet available (as stated in docs)
//      case TypeOfFatUsedFacet -> toRecord24(Type.INCOMPLETE);
//      case TypeOfMilkOrLiquidUsedFacet -> toRecord24(Type.INCOMPLETE);
//      default -> throw new IllegalArgumentException("Unexpected value: " + ListEntryType.parse(listEntryType));
//      };
//  }

    private Record24 toRecord24(final ListEntry listEntry, final Can<Ingredient24> ingredients) {
        _Assert.assertFalse(listEntry.listEntryType().equals(ListEntryType.FoodSelectedAsARecipeIngredient));
        //TODO label() might be non empty -> information lost
        return Record24.of(Type.FOOD, listEntry.getName(), listEntry.getFacetDescriptorCodes(), ingredients);
    }

    // -- MEALS

    private Meal24 toMeal24(final ListEntry listEntry, final Can<MemorizedFood24> memorizedFood) {
        LocalTime hourOfDay = parseLocalTimeFrom4Digits(listEntry.getFoodConsumptionHourOfDay().trim());
        return Meal24.of(hourOfDay, listEntry.getFoodConsumptionOccasionId(), listEntry.getFoodConsumptionPlaceId(), memorizedFood);
    }

    // -- INGREDIENTS

    private Ingredient24 toIngredient24(final ListEntry listEntry) {
        _Assert.assertEquals(ListEntryType.FoodSelectedAsARecipeIngredient, listEntry.listEntryType());
        return Ingredient24.of(listEntry.getFoodOrSimilarCode(), listEntry.getName(),
                listEntry.getFacetDescriptorCodes(), listEntry.getRawPerCookedRatio(), quantityConsumed(listEntry));
    }

    private javax.measure.Quantity<Mass> quantityConsumed(final ListEntry listEntry){
        return MetricUnits.grams(listEntry.getConsumedQuantity()); //TODO just guessing which one - needs double check
    }

    /**
     * has no children
     */
    private Respondent24 respondentStub(final _Dtos.Interview iv) {
        final Respondent24 respondent = new Respondent24(
                iv.getSubjectCode(),
                //subjectName + "|" + subjectFirstName,
                iv.getSubjectBirthDate().toLocalDate(),
                Sex.values()[iv.getSubjectSex()],
                Can.empty());

        //respondent.annotate().(new Annotation("Name", name));

        return respondent;
    }

    private LocalTime parseLocalTimeFrom4Digits(final String hhmm) {
        var hh = hhmm.substring(0, 2);
        var mm = hhmm.substring(2, 4);
        return LocalTime.of(Integer.parseInt(hh), Integer.parseInt(mm));
    }

}
