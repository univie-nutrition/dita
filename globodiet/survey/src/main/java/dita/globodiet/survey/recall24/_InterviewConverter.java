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
import dita.recall24.immutable.Ingredient;
import dita.recall24.immutable.Interview;
import dita.recall24.immutable.Meal;
import dita.recall24.immutable.MemorizedFood;
import dita.recall24.immutable.Record;
import dita.recall24.immutable.Respondent;
import dita.recall24.immutable.RespondentSupplementaryData;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
class _InterviewConverter {

    /**
     * parented by an Respondent24 stub, that has no children (needs post-processing)
     */
    Interview toInterview24(final _Dtos.Interview iv) {
        final var tree = iv.asTree();
        var meals = tree.childNodes().stream()
            .map(fcoNode->{
                var fcoEntry = fcoNode.entry();
                var memorizedFoods = fcoNode.childNodes().stream()
                .map(memNode->{
                    var memEntry = memNode.entry();
                    var records = memNode.childNodes().stream()
                    .map(recordNode->{
                        var recordEntry = recordNode.entry();
                        var recordSubEntries = recordNode.childNodes().stream()
                        .map(recordSubNode->{
                            var type = recordSubNode.type();
                            switch (type) {
                            case FoodSelectedAsARecipeIngredient:
                                return toIngredient24(recordSubNode.entry());
                            case TypeOfFatUsedFacet:
                            case TypeOfMilkOrLiquidUsedFacet:
                            case FatDuringCookingForFood:
                            case FatSauceOrSweeteners:
                                return null; //TODO no receiving type yet
                            default:
                                throw new IllegalArgumentException("Unexpected value: " + type);
                            }
                        })
                        .collect(Can.toCan());

                        var record24 = toRecord24(recordEntry, recordSubEntries);
                        return record24;
                    })
                    .collect(Can.toCan());

                    var memorizedFood24 = toMemorizedFood24(memEntry, records);
                    return memorizedFood24;
                })
                .collect(Can.toCan());

                var meal24 = toMeal24(fcoEntry, memorizedFoods);
                return meal24;
            })
            .collect(Can.toCan());

        return Interview.of(
                respondentStub(iv),
                iv.getInterviewDate().toLocalDate(),
                new RespondentSupplementaryData(
                        ObjectRef.<Interview>empty(),
                        iv.getSpecialDietCode(),
                        iv.getSpecialDayCode(),
                        NumberUtils.roundToNDecimalPlaces(iv.getHeightCM(), 1),
                        NumberUtils.roundToNDecimalPlaces(iv.getWeightKG(), 1)),
                meals);
    }

    // -- MEM

    private MemorizedFood toMemorizedFood24(final ListEntry listEntry, final Can<Record> records) {
        _Assert.assertTrue(_Strings.isNullOrEmpty(listEntry.getName()));
        return MemorizedFood.of(listEntry.getLabel(), records);
    }

    // -- RECORDS


    private Record toRecord24(final ListEntry listEntry, final Can<Ingredient> ingredients) {
        _Assert.assertFalse(listEntry.listEntryType().equals(ListEntryType.FoodSelectedAsARecipeIngredient));
        //TODO label() might be non empty -> information lost
        //TODO needs a switch on type actually
        return switch (listEntry.listEntryType()) {
        case Food -> Record.of(Type.FOOD, listEntry.getName(), listEntry.getFacetDescriptorCodes(), ingredients);
        case Recipe -> Record.of(Type.COMPOSITE, listEntry.getName(), listEntry.getFacetDescriptorCodes(), ingredients);
        case DietarySupplement -> Record.of(Type.PRODUCT, listEntry.getName(), listEntry.getFacetDescriptorCodes(), ingredients);
        default -> throw new IllegalArgumentException("Unexpected value: " + listEntry.listEntryType());
        };
    }

    // -- MEALS

    private Meal toMeal24(final ListEntry listEntry, final Can<MemorizedFood> memorizedFood) {
        LocalTime hourOfDay = parseLocalTimeFrom4Digits(listEntry.getFoodConsumptionHourOfDay().trim());
        return Meal.of(hourOfDay, listEntry.getFoodConsumptionOccasionId(), listEntry.getFoodConsumptionPlaceId(), memorizedFood);
    }

    // -- INGREDIENTS

    private Ingredient toIngredient24(final ListEntry listEntry) {
        _Assert.assertEquals(ListEntryType.FoodSelectedAsARecipeIngredient, listEntry.listEntryType());
        return Ingredient.of(listEntry.getFoodOrSimilarCode(), listEntry.getName(),
                listEntry.getFacetDescriptorCodes(), listEntry.getRawPerCookedRatio(), quantityConsumed(listEntry));
    }

    private javax.measure.Quantity<Mass> quantityConsumed(final ListEntry listEntry){
        return MetricUnits.grams(listEntry.getConsumedQuantity()); //TODO just guessing which one - needs double check
    }

    /**
     * has no children
     */
    private Respondent respondentStub(final _Dtos.Interview iv) {
        final Respondent respondent = new Respondent(
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
