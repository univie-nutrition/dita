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
package dita.recall24.reporter.tabular;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.tabular.TabularModel;
import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodComposition;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.dmap.DirectMap;
import dita.commons.types.DecimalVector;
import dita.commons.types.Sex;
import dita.commons.util.FormatUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.recall24.dto.Annotated;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.reporter.dom.ConsumptionRecord;
import dita.recall24.reporter.dom.ConsumptionRecord.ConsumptionRecordBuilder;

public record TabularReport(
        InterviewSet24 interviewSet,
        SystemId systemId, //TODO[dita-recall24-reporter] required for fully qualified PoC and FCO (perhaps, transform earlier already)

        CodeHelper sdayHelper,
        CodeHelper sdietHelper,
        CodeHelper fcoHelper,
        CodeHelper pocHelper,

        FoodDescriptionModel fdm,

        FoodCompositionRepository foodCompositionRepo,
        Can<FoodComponent> foodComponents,
        Aggregation aggregation) {

    public enum Aggregation {
        /**
         * Each consumption is reported as is.
         */
        NONE,
        /**
         * Sum total of nutrient values for each meal.
         */
        MEAL,
        /**
         * Sum total of nutrient values for each interview.
         */
        INTERVIEW,
        /**
         * Average of nutrient values for each respondent per interview (averaged over all interviews available for a given respondent).
         */
        RESPONDENT_AVERAGE,
//        /**
//         * Variant that groups by food-group.
//         */
//        RESPONDENT_AVERAGE_GROUP_BY_FOOD_GROUP,
//        /**
//         * Variant that groups by food-group and food-subgroup.
//         */
//        RESPONDENT_AVERAGE_GROUP_BY_FOOD_GROUP_AND_SUBGROUP
    }

    public enum Feature {
        BRANDNAMES,
        FACETS
    }

    public TabularReport(
            final InterviewSet24 interviewSet,
            final SystemId systemId,

            final DirectMap specialDayMapping,
            final DirectMap specialDietMapping,
            final DirectMap fcoMapping,
            final DirectMap pocMapping,
            final FoodDescriptionModel fdm,

            final FoodCompositionRepository foodCompositionRepo,
            final Can<FoodComponent> foodComponents,
            final Aggregation aggregation) {

        this(interviewSet, systemId,
                new CodeHelper(systemId, "sday", "Special Day (sday)", specialDayMapping),
                new CodeHelper(systemId, "sdiet", "Special Diet (sdiet)", specialDietMapping),
                new CodeHelper(systemId, "fco", "Food Consumption Occasion (fco)", fcoMapping),
                new CodeHelper(systemId, "poc", "Place of Consumption (poc)", pocMapping),
                fdm, foodCompositionRepo, foodComponents, aggregation);
    }

    public TabularModel singleSheetTabularModel() {
        Can<TabularSheet> sheets = Can.of(
            new TabularFactory(foodComponents).toTabularSheet(aggregate()));
        return new TabularModel(sheets);
    }

    public TabularModel multiSheetTabularModel() {
        Can<TabularSheet> sheets = Can.of(
            new TabularFactory(foodComponents).toTabularSheet(aggregate()),
            sdayHelper.sheet(),
            sdietHelper.sheet(),
            fcoHelper.sheet(),
            pocHelper.sheet(),
            new FacetSheetFactory(fdm).facetSheet("Food Descriptor (fd)", sid->"fd".equals(sid.objectId().context())),
            new FacetSheetFactory(fdm).facetSheet("Food Group (fg)", sid->"fg".equals(sid.objectId().context())),
            new FacetSheetFactory(fdm).facetSheet("Recipe Descriptor (rd)", sid->"rd".equals(sid.objectId().context())),
            new FacetSheetFactory(fdm).facetSheet("Recipe Group (rg)", sid->"rg".equals(sid.objectId().context())));
        return new TabularModel(sheets);
    }

    // -- HELPER

    @Setter
    @RequiredArgsConstructor
    private static class RowFactory {
        @Getter @Accessors(fluent=true)
        final ConsumptionRecordBuilder builder = ConsumptionRecord.builder();
        final Set<String> respondentAliasSeenBefore = new HashSet<>();
        private int respondentOrdinal;
        private final OrdinalTracker ordinalTracker = new OrdinalTracker();
        private final NutrientVectorFactory nutrientVectorFactory;

        // factory method for composites
        ConsumptionRecord compositeHeader(final Record24.Composite comp) {
            return builder
                    .food(comp.name())
                    .foodId(comp.sid().toStringNoBox())
                    .facetIds(comp.facetSids().toStringNoBox())
                    .quantity(null)
                    .fcdbId(null)
                    .nutrients(DecimalVector.empty())
                    .build();
        }
        // factory method for consumptions
        ConsumptionRecord consumption(
                final Record24.Consumption cRec,
                final Optional<FoodComposition> compositionEntry){
            final FoodConsumption foodConsumption = cRec.asFoodConsumption();
            return builder
                    .food(cRec.name())
                    .foodId(cRec.sid().toStringNoBox())
                    .facetIds(cRec.facetSids().toStringNoBox())
                    .quantity(cRec.amountConsumed())
                    .fcdbId(compositionEntry
                            .map(FoodComposition::foodId)
                            .orElseGet(TabularReport::wipSid)
                            .toStringNoBox())
                    .nutrients(nutrientVectorFactory.get(foodConsumption, compositionEntry.orElse(null)))
                    .build();
        }
        // factory method for comments
        ConsumptionRecord comment(
                final Record24.Comment comment){
            return builder
                    .recordType(comment.type().name())
                    .mealOrdinal(null)
                    .food(comment.name())
                    .foodId(comment.sid().toStringNoBox())
                    .facetIds(comment.facetSids().toStringNoBox())
                    .quantity(null)
                    .fcdbId(null)
                    .nutrients(DecimalVector.empty())
                    .build();
        }
        void respondentAlias(final String respondentAlias) {
            if(respondentAliasSeenBefore.add(respondentAlias)) respondentOrdinal++;
            builder.respondentAlias(respondentAlias);
            builder.respondentOrdinal(respondentOrdinal);
        }
        void respondentSex(final Sex sex) {
            builder.respondentSex(sex.ordinal());
        }
        void respondentAge(final long respondentAgeInDays) {
            builder.respondentAge(
                    BigDecimal
                    .valueOf(Math.round(respondentAgeInDays/36.52422))
                    .scaleByPowerOfTen(-1));
        }
        void recordType(final Record24.@NonNull Type type) {
            builder.recordType(type.name());
        }
    }

    private Iterable<ConsumptionRecord> aggregate() {
        var rowFactory = new RowFactory(new NutrientVectorFactory(foodComponents));
        var rowBuilder = rowFactory.builder();
        var consumptions = new ArrayList<ConsumptionRecord>();

        interviewSet.streamDepthFirst()
        .forEach((final RecallNode24 node)->{
            switch(node) {
            case Interview24 iv -> {
                rowFactory.respondentAlias(iv.parentRespondent().alias());
                rowFactory.respondentSex(iv.parentRespondent().sex());
                rowBuilder.interviewOrdinal(iv.interviewOrdinal());
                rowBuilder.consumptionDate(iv.consumptionDate());
                rowBuilder.consumptionDayOfWeek(iv.consumptionDate().getDayOfWeek().getValue());
                rowBuilder.wakeUpTime(FormatUtils.hourOfDay(iv.respondentSupplementaryData().wakeupTimeOnDayOfConsumption()));
                var sdayCodes = sdayHelper.codes(iv.respondentSupplementaryData().specialDayId());
                var sdietCodes = sdietHelper.codes(iv.respondentSupplementaryData().specialDietId());
                rowBuilder.specialDay(sdayCodes.toStringNoBox());
                rowBuilder.specialDiet(sdietCodes.toStringNoBox());

                rowBuilder.interviewCount(iv.parentRespondent().interviewCount());
                if(iv.interviewOrdinal()==1) {
                    rowFactory.respondentAge(
                            ChronoUnit.DAYS.between(
                                    iv.parentRespondent().dateOfBirth(),
                                    iv.consumptionDate()));
                }
            }
            case Meal24 meal -> {
                rowFactory.ordinalTracker.nextMeal();
                var fcoCode = fcoHelper.code(meal.foodConsumptionOccasionId());
                var pocCode = pocHelper.code(meal.foodConsumptionPlaceId());
                rowBuilder.fco(fcoCode.toStringNoBox());
                rowBuilder.poc(pocCode.toStringNoBox());
                rowBuilder.meal("%s (%s) @ %s".formatted(
                        fcoHelper.label(fcoCode),
                        FormatUtils.hourOfDay(meal.hourOfDay()),
                        pocHelper.label(pocCode)));
            }
            case Record24.Comment comment -> {
                rowFactory.recordType(comment.type());
                rowBuilder.groupId(
                        comment.lookupAnnotation(Annotated.GROUP)
                        .map(Annotated.Annotation::value)
                        .map(SemanticIdentifier.class::cast)
                        .map(SemanticIdentifier::toStringNoBox)
                        .orElse(""));
                consumptions.add(
                        rowFactory.comment(comment));
            }
            case Record24.Composite comp -> {
                rowFactory.ordinalTracker.nextComposite(comp);
                rowBuilder.mealOrdinal(rowFactory.ordinalTracker.deweyOrdinal());
                rowFactory.recordType(comp.type());
                rowBuilder.groupId(
                        comp.lookupAnnotation(Annotated.GROUP)
                        .map(Annotated.Annotation::value)
                        .map(SemanticIdentifier.class::cast)
                        .map(SemanticIdentifier::toStringNoBox)
                        .orElse(""));
                consumptions.add(
                        rowFactory.compositeHeader(comp));
            }
            case Record24.Consumption cRec -> {
                rowFactory.ordinalTracker.nextConsumption(cRec);
                rowBuilder.mealOrdinal(rowFactory.ordinalTracker.deweyOrdinal());
                rowFactory.recordType(cRec.type());
                rowBuilder.groupId(
                        cRec.lookupAnnotation(Annotated.GROUP)
                        .map(Annotated.Annotation::value)
                        .map(SemanticIdentifier.class::cast)
                        .map(SemanticIdentifier::toStringNoBox)
                        .orElse(""));
                var mappingTarget = cRec.lookupAnnotation(Annotated.FCDB_ID)
                        .map(Annotated.Annotation.valueAs(SemanticIdentifier.class));
                var compositionEntry = mappingTarget
                        .flatMap(foodCompositionRepo::lookupEntry);
                if(mappingTarget.isPresent()) {
                    if(!compositionEntry.isPresent()) {
                        if(ObjectId.Context.RECIPE.matches(mappingTarget.get())) {

                        } else throw _Exceptions.noSuchElement("no FCDB composition entry for '%s'->%s",
                                cRec.name(), mappingTarget.get());
                    }
                }
                consumptions.add(
                        rowFactory.consumption(cRec, compositionEntry));
            }
            default -> {}
            }
        });

        return new Aggregator(aggregation).apply(consumptions);
    }

    private static SemanticIdentifier wipSid() {
        return new SemanticIdentifier(SystemId.empty(), new ObjectId("WIP"));
    }

}