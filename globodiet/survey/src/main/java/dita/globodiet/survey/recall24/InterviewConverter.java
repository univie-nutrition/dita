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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.commons.util.FormatUtils;
import dita.commons.util.NumberUtils;
import dita.globodiet.survey.recall24._Dtos.Interview.ListEntryTreeNode;
import dita.globodiet.survey.recall24._Dtos.ListEntry;
import dita.globodiet.survey.recall24._Dtos.ListEntry.ListEntryType;
import dita.globodiet.survey.recall24._Dtos.Note;
import dita.globodiet.survey.util.SidUtils;
import dita.recall24.dto.Annotated;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Respondent24;
import dita.recall24.dto.RespondentSupplementaryData24;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

/// Converts from _GloboDiet's_ interview XML to {@link Interview24}.
record InterviewConverter(SystemId systemId) {

    record ContextForScanning(
        String respondentId,
        LocalDate interviewDate) {
    }

    /**
     * parented by an Respondent24 stub, that has no children (needs post-processing)
     */
    Interview24 toInterview24(final _Dtos.Interview iv) {
        final var tree = iv.asTree();

        var contextForScanning = new ContextForScanning(iv.getSubjectCode(), iv.getInterviewDate().toLocalDate());

        var meals = tree.childNodes().stream()
            .map(fcoNode->{
                var fcoEntry = fcoNode.entry();
                var memorizedFoods = fcoNode.childNodes().stream()
                .map(memNode->{
                    var memEntry = memNode.entry();
                    var records = memNode.childNodes().stream()
                    .map(recordNode->{
                        var record24 = toTopLevelRecord24(contextForScanning, recordNode);
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

        var interview = new Interview24(
                iv.getInterviewDate().toLocalDate(),
                iv.getConsumptionDate().toLocalDate(),
                new RespondentSupplementaryData24(
                        ObjectRef.empty(),
                        iv.getSpecialDietCode(),
                        iv.getSpecialDayCode(),
                        NumberUtils.roundToNDecimalPlaces(iv.getHeightCM(), 1),
                        NumberUtils.roundToNDecimalPlaces(iv.getWeightKG(), 1),
                        iv.getWakeupOnRecallDay(),
                        iv.getWakeupOnNextDay()),
                meals);
        interview.parentRespondentRef().setValue(respondentStub(iv));
        return interview;
    }

    // -- MEM

    private MemorizedFood24 toMemorizedFood24(final ListEntry listEntry, final Can<Record24> topLevelRecords) {
        _Assert.assertTrue(_Strings.isNullOrEmpty(listEntry.getName()));
        return new MemorizedFood24(listEntry.getLabel(), topLevelRecords);
    }

    // -- RECORDS

    private Record24 toTopLevelRecord24(final ContextForScanning contextForScanning, final ListEntryTreeNode topLevelRecordNode) {
        final List<ListEntryTreeNode> subEntries = topLevelRecordNode.childNodes().stream()
                .filter(x->!ListEntryType.FatSauceOrSweeteners.equals(x.type()))
                .toList();
        final int subRecordCount = subEntries.size();
        var listEntry = topLevelRecordNode.entry();

        //TODO label() might be non empty -> information lost
        return switch (listEntry.listEntryType()) {
        case Recipe -> {
            _Assert.assertTrue(subRecordCount>0, ()->"'recipe' record is expected to have at least one sub-record");

            var composite = Record24.composite(
                listEntry.getName(),
                recipeSid(listEntry),
                recipeFacets(listEntry),
                toRecords24(contextForScanning, subEntries),
                Can.of(group(SidUtils.GdContext.RECIPE_GROUP, listEntry), modification(contextForScanning, listEntry))
                );

            var notesAsLines = notes(listEntry);
            if(!notesAsLines.isEmpty()) {
                composite.putAnnotation(Annotated.NOTES, (Serializable) notesAsLines);
            }
            yield composite;
        }
        default -> toRecord24(contextForScanning, topLevelRecordNode);
        };
    }

    /**
     * Gathers all memo lines from all notes as attached to given {@code listEntry}.
     */
    private List<String> notes(final ListEntry listEntry) {
         if(_NullSafe.isEmpty(listEntry.getNotes())) return List.of();
         var notes = listEntry.getNotes();
         return notes.stream()
             .map(Note::getMemo)
             .flatMap(this::parseNoteMemo)
             .toList();
     }

    /**
     * There are potentially multiple notes per memo.
     * @return Stream of parsed memo 'lines'
     */
    private Stream<String> parseNoteMemo(final String memo) {
        return _Strings.splitThenStream(memo, "•••●")
            .filter(x->x.contains("●•••"))
            .map(x->TextUtils.cutter(x).keepBefore("●•••").getValue());
     }

    private Can<Record24> toRecords24(final ContextForScanning contextForScanning, final List<ListEntryTreeNode> nodes) {
        return _NullSafe.stream(nodes).map(node->toRecord24(contextForScanning, node)).collect(Can.toCan());
    }

    private Record24 toRecord24(final ContextForScanning contextForScanning, final ListEntryTreeNode node) {
        final List<ListEntryTreeNode> subEntries = node.childNodes().stream()
                .filter(x->!ListEntryType.FatSauceOrSweeteners.equals(x.type()))
                .toList();
        final int subRecordCount = subEntries.size();
        var listEntry = node.entry();

        return switch (listEntry.listEntryType()) {
            case RecipeSelectedAsARecipeIngredient -> {
                yield Record24.composite(
                    listEntry.getName(),
                    recipeSid(listEntry),
                    recipeFacets(listEntry),
                    toRecords24(contextForScanning, subEntries),
                    Can.of(group(SidUtils.GdContext.RECIPE_GROUP, listEntry), modification(contextForScanning, listEntry))
                    );
            }
            case Food, FoodSelectedAsARecipeIngredient -> {
                // sub-records allowed are TypeOfFatUsed and TypeOfMilkOrLiquidUsed
                var usedDuringCooking = subRecordCount>0
                     ? toRecords24(contextForScanning, subEntries)
                     : Can.<Record24>empty();
                usedDuringCooking.forEach((final Record24 dto)->{
                    switch (dto.type()) {
                        case TYPE_OF_FAT_USED, TYPE_OF_MILK_OR_LIQUID_USED -> { /* valid */}
                        default -> throw new IllegalArgumentException("Unexpected value: " + dto);
                    }
                });
                yield Record24.food(
                    listEntry.getName(), foodSid(listEntry), foodFacets(listEntry),
                    listEntry.getConsumedQuantity(), ConsumptionUnit.GRAM, listEntry.getRawToCookedCoefficient(),
                    usedDuringCooking,
                    Can.of(group(SidUtils.GdContext.FOOD_GROUP, listEntry)));
            }
            case FatDuringCookingForFood, FatDuringCookingForIngredient -> {
                _Assert.assertEquals(0, subRecordCount, ()->"'fryingFat' record is expected to have no sub-records");
                yield Record24.fryingFat(
                    listEntry.getName(), foodSid(listEntry), foodFacets(listEntry),
                    listEntry.getConsumedQuantity(), ConsumptionUnit.GRAM, listEntry.getRawToCookedCoefficient(),
                    Can.empty());
            }
            case DietarySupplement -> {
                _Assert.assertEquals(0, subRecordCount, ()->"'supplement' record is expected to have no sub-records");
                yield Record24.product(
                    listEntry.getName(), foodSid(listEntry), foodFacets(listEntry),
                    listEntry.getConsumedQuantity(), ConsumptionUnit.GRAM, listEntry.getRawToCookedCoefficient(),
                    Can.empty());
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

    private Meal24 toMeal24(final ListEntry listEntry, final Can<MemorizedFood24> memorizedFood) {
        LocalTime hourOfDay = parseLocalTimeFrom4Digits(listEntry.getFoodConsumptionHourOfDay().trim());
        return new Meal24(hourOfDay, listEntry.getFoodConsumptionOccasionId(), listEntry.getFoodConsumptionPlaceId(), memorizedFood);
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

    private Annotated.Annotation group(final SidUtils.GdContext context, final ListEntry listEntry) {
        var groupSimpleId = FormatUtils.concat(
                listEntry.getGroupCode(),
                listEntry.getSubgroupCode(),
                listEntry.getSubSubgroupCode());
        return new Annotated.Annotation("group", context
                .sid(systemId, groupSimpleId));
    }

    private static Set<String> recipeIdsOfInterest = Set.of(
        "00536",
        "00537",
        "00539",
        "00540",
        "00469",
        "00514",
        "00472",
        "00473",
        "00474",
        "00513",
        "00477",
        "00478",
        "00470",
        "00301",
        "00515",
        "00521",
        "00362",
        "00647",
        "00512",
        "00516",
        "00510",
        "00511",
        "00466");

    private Annotated.Annotation modification(final ContextForScanning contextForScanning, final ListEntry listEntry) {
        var isModified =
            (listEntry.getRecipeModificationType()
            + listEntry.getIngredientModificationType())>0;

        var id = listEntry.getFoodOrSimilarCode();
        if(StringUtils.hasLength(id)) {
            if(recipeIdsOfInterest.contains(id)) {
                System.err.printf("%s type=%s, id=%s, modified=%b%n",
                    contextForScanning, listEntry.listEntryType(), listEntry.getFoodOrSimilarCode(), isModified);
            }
        } else {
            if(!"4.1".equals(listEntry.getRecipeType())
                &&!"4.2".equals(listEntry.getRecipeType())) {
                System.err.printf("missing-id: %s%n", listEntry);
            }
        }

        return new Annotated.Annotation("isModified", isModified);
    }

    private SemanticIdentifier foodSid(final ListEntry listEntry) {
        return ObjectId.Context.FOOD.sid(systemId, listEntry.getFoodOrSimilarCode());
    }

    private SemanticIdentifier recipeSid(final ListEntry listEntry) {
        return ObjectId.Context.RECIPE.sid(systemId, listEntry.getFoodOrSimilarCode());
    }

    private SemanticIdentifierSet foodFacets(final ListEntry listEntry) {
        return SemanticIdentifierSet.ofStream(streamFacetObjectIds(SidUtils.GdContext.FOOD_DESCRIPTOR, listEntry)
                .map(objectId->new SemanticIdentifier(systemId, objectId)));
    }

    private SemanticIdentifierSet recipeFacets(final ListEntry listEntry) {
        return SemanticIdentifierSet.ofStream(streamFacetObjectIds(SidUtils.GdContext.RECIPE_DESCRIPTOR, listEntry)
                .map(objectId->new SemanticIdentifier(systemId, objectId)));
    }

    /**
     * includes brand name, if any
     */
    private static Stream<ObjectId> streamFacetObjectIds(
            final SidUtils.@Nullable GdContext context,
            final ListEntry listEntry) {
        return Stream.concat(
                _Strings.splitThenStream(listEntry.getFacetDescriptorCodes(), ",")
                    .filter(_Strings::isNotEmpty)
                    .map(objSimpleId->context.objectId(objSimpleId)),
                canonicalBrandName(listEntry)
                    .map(ObjectId.Context.BRAND::objectId)
                    .stream()
                );
    }

    private static Optional<String> canonicalBrandName(final ListEntry listEntry){
        return  _Strings.nonEmpty(listEntry.getBrandName())
            .map(String::toLowerCase)
            //TODO[dita-globodiet-survey] externalize as configuration; perhaps can auto-detect based on first descriptor in facet type = BRAND
            //exclude if brand-name is just a placeholder
            .filter(name->!name.equals("marke / produktname unbekannt"))
            .map(name->name.replace(",", " ").replace("ß", "ss"))
            .map(name->_Strings.condenseWhitespaces(name, "_"));
    }

}
