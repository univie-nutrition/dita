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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.commons.util.NumberUtils;
import dita.globodiet.survey.recall24._Dtos.ListEntry;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
class _InterviewConverter2 {

    /**
     * parented by an Respondent24 stub, that has no children (needs post-processing)
     */
    R24.Interview toInterview24(final String systemId, final _Dtos.Interview iv) {
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


//                        var recordSubEntries = recordNode.childNodes().stream()
//                        .map(recordSubNode->{
//                            var type = recordSubNode.type();
//                            switch (type) {
//                            case FoodSelectedAsARecipeIngredient:
//                                return toIngredient24(recordSubNode.entry());
//                            case TypeOfFatUsedFacet:
//                            case TypeOfMilkOrLiquidUsedFacet:
//                            case FatDuringCookingForFood:
//                            case FatSauceOrSweeteners:
//                                return null; //TODO no receiving type yet
//                            default:
//                                throw new IllegalArgumentException("Unexpected value: " + type);
//                            }
//                        })
//                        .collect(Can.toCan());

                        var record24 = toTopLevelRecord24(systemId, recordEntry);
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

        return R24.Interview.of(
                respondentStub(iv),
                iv.getInterviewDate().toLocalDate(),
                new R24.RespondentMetaData(
                        ObjectRef.empty(),
                        iv.getSpecialDietCode(),
                        iv.getSpecialDayCode(),
                        NumberUtils.roundToNDecimalPlaces(iv.getHeightCM(), 1),
                        NumberUtils.roundToNDecimalPlaces(iv.getWeightKG(), 1)),
                meals);
    }

    // -- MEM

    private R24.MemorizedFood toMemorizedFood24(final ListEntry listEntry, final Can<R24.Record> topLevelRecords) {
        _Assert.assertTrue(_Strings.isNullOrEmpty(listEntry.getName()));
        return R24.MemorizedFood.of(listEntry.getLabel(), topLevelRecords);
    }

    // -- RECORDS


    private R24.Record toTopLevelRecord24(final String systemId, final ListEntry listEntry) {
        //TODO label() might be non empty -> information lost
        //TODO needs a switch on type actually
        return switch (listEntry.listEntryType()) {
        //TODO assert has no sub-records
        case Food -> new R24.Food(
                consumption(systemId, listEntry),
                typeOfFatUsedDuringCooking(listEntry),
                typeOfMilkOrLiquidUsedDuringCooking(listEntry),
                notes(listEntry));
        //R24.Record.of(listEntry.getName(), listEntry.getFacetDescriptorCodes(), Can.empty());
        //case Recipe -> R24.Record.of(Type.COMPOSITE, listEntry.getName(), listEntry.getFacetDescriptorCodes(), Can.empty());
        //case DietarySupplement -> R24.Record.of(Type.PRODUCT, listEntry.getName(), listEntry.getFacetDescriptorCodes(), Can.empty());
        default -> throw new IllegalArgumentException("Unexpected value: " + listEntry.listEntryType());
        };
    }

    // -- MEALS

    private R24.Meal toMeal24(final ListEntry listEntry, final Can<R24.MemorizedFood> memorizedFood) {
        LocalTime hourOfDay = parseLocalTimeFrom4Digits(listEntry.getFoodConsumptionHourOfDay().trim());
        return R24.Meal.of(hourOfDay, listEntry.getFoodConsumptionOccasionId(), listEntry.getFoodConsumptionPlaceId(), memorizedFood);
    }

    // -- CONSUMPTION

    private Optional<FoodConsumption> consumption(final String systemId, final ListEntry listEntry) {
        switch(listEntry.listEntryType()) {
        case Food:
        case FoodSelectedAsARecipeIngredient:
        case DietarySupplement:
            var sid = semanticIdentifier(systemId, listEntry);
            var facets = semanticIdentifierSet(systemId, listEntry);
            //TODO just guessing GRAM - needs double check
            return Optional.of(new FoodConsumption(listEntry.getLabel(), sid, facets,
                    ConsumptionUnit.GRAM, listEntry.getConsumedQuantity()));
        default:
            return Optional.empty();
        }
    }

    private Optional<R24.TypeOfFatUsed> typeOfFatUsedDuringCooking(final ListEntry listEntry) {
        return Optional.empty(); //TODO flesh out
    }
    private Optional<R24.TypeOfMilkOrLiquidUsed> typeOfMilkOrLiquidUsedDuringCooking(final ListEntry listEntry) {
        return Optional.empty(); //TODO flesh out
    }
    private List<R24.Note> notes(final ListEntry listEntry) {
        return Collections.emptyList(); //TODO flesh out
    }

    /**
     * has no children
     */
    private R24.Respondent respondentStub(final _Dtos.Interview iv) {
        final R24.Respondent respondent = new R24.Respondent(
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

    private SemanticIdentifier semanticIdentifier(final String systemId, final ListEntry listEntry) {
        return new SemanticIdentifier(systemId, listEntry.getFoodOrSimilarCode());
    }

    private SemanticIdentifierSet semanticIdentifierSet(final String systemId, final ListEntry listEntry) {
        return SemanticIdentifierSet.ofStream(_Strings.splitThenStream(listEntry.getFacetDescriptorCodes(), ",")
                .map(facetId->new SemanticIdentifier(systemId, facetId)));
    }

}
