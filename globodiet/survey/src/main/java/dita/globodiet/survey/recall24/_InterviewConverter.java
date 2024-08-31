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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.commons.util.NumberUtils;
import dita.globodiet.survey.dom.SidUtils;
import dita.globodiet.survey.recall24._Dtos.Interview.ListEntryTreeNode;
import dita.globodiet.survey.recall24._Dtos.ListEntry;
import dita.globodiet.survey.recall24._Dtos.ListEntry.ListEntryType;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Respondent24;
import dita.recall24.dto.RespondentSupplementaryData24;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
class _InterviewConverter {

    /**
     * parented by an Respondent24 stub, that has no children (needs post-processing)
     */
    Interview24.Dto toInterview24(final _Dtos.Interview iv) {
        final var tree = iv.asTree();
        var meals = tree.childNodes().stream()
            .map(fcoNode->{
                var fcoEntry = fcoNode.entry();
                var memorizedFoods = fcoNode.childNodes().stream()
                .map(memNode->{
                    var memEntry = memNode.entry();
                    var records = memNode.childNodes().stream()
                    .map(recordNode->{
                        var record24 = toTopLevelRecord24(recordNode);
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

        return Interview24.Dto.of(
                respondentStub(iv),
                iv.getInterviewDate().toLocalDate(),
                new RespondentSupplementaryData24.Dto(
                        ObjectRef.empty(),
                        iv.getSpecialDietCode(),
                        iv.getSpecialDayCode(),
                        NumberUtils.roundToNDecimalPlaces(iv.getHeightCM(), 1),
                        NumberUtils.roundToNDecimalPlaces(iv.getWeightKG(), 1)),
                meals);
    }

    // -- MEM

    private MemorizedFood24.Dto toMemorizedFood24(final ListEntry listEntry, final Can<Record24.Dto> topLevelRecords) {
        _Assert.assertTrue(_Strings.isNullOrEmpty(listEntry.getName()));
        return MemorizedFood24.Dto.of(listEntry.getLabel(), topLevelRecords);
    }

    // -- RECORDS

    private Record24.Dto toTopLevelRecord24(final ListEntryTreeNode topLevelRecordNode) {
        final List<ListEntryTreeNode> subEntries = topLevelRecordNode.childNodes().stream()
                .filter(x->!ListEntryType.FatSauceOrSweeteners.equals(x.type()))
                .toList();
        final int subRecordCount = subEntries.size();
        var listEntry = topLevelRecordNode.entry();

        //TODO label() might be non empty -> information lost
        return switch (listEntry.listEntryType()) {
        case Recipe -> {
            _Assert.assertTrue(subRecordCount>0, ()->"'recipe' record is expected to have at least one sub-record");

            yield Record24.composite(
                listEntry.getName(),
                recipeSid(listEntry),
                recipeFacets(listEntry),
                toRecords24(subEntries));
        }
        default -> toRecord24(topLevelRecordNode);
        };
    }

    private Can<Record24.Dto> toRecords24(final List<ListEntryTreeNode> nodes) {
        return _NullSafe.stream(nodes).map(_InterviewConverter::toRecord24).collect(Can.toCan());
    }

    private Record24.Dto toRecord24(final ListEntryTreeNode node) {
        final List<ListEntryTreeNode> subEntries = node.childNodes().stream()
                .filter(x->!ListEntryType.FatSauceOrSweeteners.equals(x.type()))
                .toList();
        final int subRecordCount = subEntries.size();
        var listEntry = node.entry();

        return switch (listEntry.listEntryType()) {
        case Food, FoodSelectedAsARecipeIngredient -> {
            // sub-records allowed are TypeOfFatUsed and TypeOfMilkOrLiquidUsed
            var usedDuringCooking = subRecordCount>0
                 ? toRecords24(subEntries)
                 : Can.<Record24.Dto>empty();
            usedDuringCooking.forEach((final Record24.Dto dto)->{
                switch (dto.type()) {
                    case TYPE_OF_FAT_USED, TYPE_OF_MILK_OR_LIQUID_USED -> { /* valid */}
                    default -> throw new IllegalArgumentException("Unexpected value: " + dto);
                }
            });
            yield Record24.food(
                listEntry.getName(), foodSid(listEntry), foodFacets(listEntry),
                listEntry.getConsumedQuantity(), ConsumptionUnit.GRAM, listEntry.getRawPerCookedRatio(),
                usedDuringCooking);
        }
        case FatDuringCookingForFood, FatDuringCookingForIngredient -> {
            _Assert.assertEquals(0, subRecordCount, ()->"'fryingFat' record is expected to have no sub-records");
            yield Record24.fryingFat(
                listEntry.getName(), foodSid(listEntry), foodFacets(listEntry),
                listEntry.getConsumedQuantity(), ConsumptionUnit.GRAM, listEntry.getRawPerCookedRatio());
        }
        case DietarySupplement -> {
            _Assert.assertEquals(0, subRecordCount, ()->"'supplement' record is expected to have no sub-records");
            yield Record24.product(
                listEntry.getName(), foodSid(listEntry), foodFacets(listEntry),
                listEntry.getConsumedQuantity(), ConsumptionUnit.GRAM, listEntry.getRawPerCookedRatio());
        }
        case FatSauceOrSweeteners -> null; // redundant: ignore
        case TypeOfFatUsedFacet -> {
            _Assert.assertEquals(0, subRecordCount, ()->"'TypeOfFatUsedFacet' record is expected to have no sub-records");
            yield Record24.typeOfFatUsed(
                    listEntry.getName(),
                    foodSid(listEntry),
                    foodFacets(listEntry));
        }
        case TypeOfMilkOrLiquidUsedFacet -> {
            _Assert.assertEquals(0, subRecordCount, ()->"'TypeOfMilkOrLiquidUsedFacet' record is expected to have no sub-records");
            yield Record24.typeOfMilkOrLiquidUsed(
                    listEntry.getName(),
                    foodSid(listEntry),
                    foodFacets(listEntry));
        }
        default -> throw new IllegalArgumentException("Unexpected value: " + listEntry.listEntryType());
        };
    }


    // -- MEALS

    private Meal24.Dto toMeal24(final ListEntry listEntry, final Can<MemorizedFood24.Dto> memorizedFood) {
        LocalTime hourOfDay = parseLocalTimeFrom4Digits(listEntry.getFoodConsumptionHourOfDay().trim());
        return Meal24.Dto.of(hourOfDay, listEntry.getFoodConsumptionOccasionId(), listEntry.getFoodConsumptionPlaceId(), memorizedFood);
    }

    /**
     * has no children
     */
    private Respondent24.Dto respondentStub(final _Dtos.Interview iv) {
        final Respondent24.Dto respondent = new Respondent24.Dto(
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

    private static SemanticIdentifier foodSid(final ListEntry listEntry) {
        return new SemanticIdentifier(
                SidUtils.globoDietSystemId(),
                ObjectId.Context.FOOD.objectId(listEntry.getFoodOrSimilarCode()));
    }

    private static SemanticIdentifier recipeSid(final ListEntry listEntry) {
        return new SemanticIdentifier(
                SidUtils.globoDietSystemId(),
                ObjectId.Context.RECIPE.objectId(listEntry.getFoodOrSimilarCode()));
    }

    private static SemanticIdentifierSet foodFacets(final ListEntry listEntry) {
        return SemanticIdentifierSet.ofStream(streamFacetObjectIds(ObjectId.Context.FOOD_DESCRIPTOR, listEntry)
                .map(objectId->new SemanticIdentifier(SidUtils.globoDietSystemId(), objectId)));
    }

    private static SemanticIdentifierSet recipeFacets(final ListEntry listEntry) {
        return SemanticIdentifierSet.ofStream(streamFacetObjectIds(ObjectId.Context.RECIPE_DESCRIPTOR, listEntry)
                .map(objectId->new SemanticIdentifier(SidUtils.globoDietSystemId(), objectId)));
    }

    /**
     * includes brand name, if any
     */
    private static Stream<ObjectId> streamFacetObjectIds(
            final @Nullable ObjectId.Context context,
            final ListEntry listEntry) {
        return Stream.concat(
                _Strings.splitThenStream(listEntry.getFacetDescriptorCodes(), ",")
                    .filter(_Strings::isNotEmpty)
                    .map(objSimpleId->new ObjectId(context, objSimpleId)),
                canonicalBrandName(listEntry)
                    .map(ObjectId.Context.BRAND::objectId)
                    .stream()
                );
    }

    private static Optional<String> canonicalBrandName(final ListEntry listEntry){
        return  _Strings.nonEmpty(listEntry.getBrandName())
            .map(String::toLowerCase)
            //TODO[DITA-25] externalize as configuration; perhaps can auto-detect based on first descriptor in facet type = BRAND
            //exclude if brand-name is just a placeholder
            .filter(name->!name.equals("marke / produktname unbekannt"))
            .map(name->name.replace(",", " "))
            .map(name->_Strings.condenseWhitespaces(name, "_"));
    }

}
