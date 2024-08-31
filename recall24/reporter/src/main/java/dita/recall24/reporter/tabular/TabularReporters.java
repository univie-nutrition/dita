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

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.core.metamodel.tabular.simple.DataTable;
import org.apache.causeway.extensions.tabular.excel.exporter.CollectionContentsAsExcelExporter;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodComponentQuantified;
import dita.commons.food.composition.FoodComposition;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.foodon.bls.BLS302;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.reporter.dom.ConsumptionRecord;
import dita.recall24.reporter.dom.ConsumptionRecord.ConsumptionRecordBuilder;

@UtilityClass
public class TabularReporters {

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
        /**
         * Variant that groups by food-group.
         */
        RESPONDENT_AVERAGE_GROUP_BY_FOOD_GROUP,
        /**
         * Variant that groups by food-group and food-subgroup.
         */
        RESPONDENT_AVERAGE_GROUP_BY_FOOD_GROUP_AND_SUBGROUP
    }

    public enum Feature {
        BRANDNAMES,
        FACETS
    }

    public record TabularReport(
            InterviewSet24.Dto interviewSet,
            SystemId systemId,
            QualifiedMap nutMapping,
            QualifiedMap fcoMapping,
            SemanticIdentifierSet fcoQualifier,
            QualifiedMap pocMapping,
            SemanticIdentifierSet pocQualifier,
            FoodCompositionRepository foodCompositionRepo,
            Aggregation aggregation) {

        @Setter
        private static class RowFactory {
            @Getter @Accessors(fluent=true)
            final ConsumptionRecordBuilder builder = ConsumptionRecord.builder();
            final Set<String> respondentAliasSeenBefore = new HashSet<>();
            private int respondentOrdinal;

            // factory method for composites
            ConsumptionRecord compositeHeader(final Record24.Composite comp) {
                return builder
                    .food(comp.name())
                    .foodId(comp.sid().toStringNoBox())
                    .facetIds(comp.facetSids().toStringNoBox())
                    .quantity(null)
                    .fcdbId(null)
                    .GCALZB(null)
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
                            .map(sid->sid.toStringNoBox())
                            .orElse(null))
                    .GCALZB(compositionEntry
                            .flatMap(e->e.lookupDatapoint(BLS302.Component.GCALZB.componentId()))
                            .map(dp->dp.quantify(foodConsumption))
                            .map(FoodComponentQuantified::quantityValue)
                            .orElse(null))
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
            void recordType(@NonNull final Record24.Type type) {
                builder.recordType(type.name());
            }
        }

        public void report(final File file) {

            var rowFactory = new RowFactory();
            var rowBuilder = rowFactory.builder();
            var consumptions = new ArrayList<ConsumptionRecord>();
            var hourOfDayFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT);

            interviewSet.streamDepthFirst()
            .forEach((final RecallNode24 node)->{
                switch(node) {
                    case Interview24.Dto iv -> {
                        rowFactory.respondentAlias(iv.parentRespondent().alias());
                        rowFactory.respondentSex(iv.parentRespondent().sex());
                        rowBuilder.interviewOrdinal(iv.interviewOrdinal());
                        rowBuilder.consumptionDate(iv.interviewDate());
                        if(iv.interviewOrdinal()==1) {
                            rowFactory.respondentAge(
                                    ChronoUnit.DAYS.between(
                                            iv.parentRespondent().dateOfBirth(),
                                            iv.interviewDate()));
                        }
                    }
                    case Meal24.Dto meal -> {
                        var fcoCode = new SemanticIdentifier(systemId, new ObjectId("fco", meal.foodConsumptionOccasionId()));
                        var pocCode = new SemanticIdentifier(systemId, new ObjectId("poc", meal.foodConsumptionPlaceId()));
                        rowBuilder.fco(fcoCode.toStringNoBox());
                        rowBuilder.poc(pocCode.toStringNoBox());
                        var fcoLabel = fcoMapping.lookupEntry(fcoCode, fcoQualifier)
                            .map(QualifiedMapEntry::target)
                            .map(SemanticIdentifier::objectId)
                            .map(ObjectId::objectSimpleId)
                            .orElse("?");
                        var pocLabel = pocMapping.lookupEntry(pocCode, pocQualifier)
                            .map(QualifiedMapEntry::target)
                            .map(SemanticIdentifier::objectId)
                            .map(ObjectId::objectSimpleId)
                            .orElse("?");
                        var timeOfDayLabel = meal.hourOfDay().format(hourOfDayFormat);
                        rowBuilder.meal(String.format("%s (%s) @ %s",
                                fcoLabel, timeOfDayLabel, pocLabel));
                    }
                    case Record24.Composite comp -> {
                        rowFactory.recordType(comp.type());
                        rowBuilder.group("wip");
                        rowBuilder.subgroup("wip");
                        rowBuilder.subSubgroup("wip");
                        consumptions.add(
                                rowFactory.compositeHeader(comp));
                    }
                    case Record24.Consumption cRec -> {
                        rowFactory.recordType(cRec.type());
                        rowBuilder.group("wip");
                        rowBuilder.subgroup("wip");
                        rowBuilder.subSubgroup("wip");
                        var mappingTarget = nutMapping
                                .lookupTarget(cRec.asQualifiedMapKey());
                        var compositionEntry = mappingTarget
                                .flatMap(foodCompositionRepo::lookupEntry);
//                        if(!mappingTarget.isPresent()) {
//                            //unmapped.add(mapKey);
//                            throw _Exceptions.noSuchElement("no mappingTarget for %s", foodConsumption.qualifiedMapKey());
//                        }
//                        if(!compositionEntry.isPresent()) {
//                            //unmapped.add(mapKey);
//                            throw _Exceptions.noSuchElement("no compositionEntry for %s", mappingTarget.get());
//                        }
                        consumptions.add(
                                rowFactory.consumption(cRec, compositionEntry));
                    }
                    default -> {}
                }
            });

            var dataTable = DataTable.forDomainType(ConsumptionRecord.class);
            dataTable.setDataElementPojos(consumptions);

            new CollectionContentsAsExcelExporter().createExport(dataTable, file);
        }

        @SneakyThrows
        public Blob reportAsBlob(final String name) {
            var tempFile = File.createTempFile(this.getClass().getCanonicalName(), name);
            try {
                this.report(tempFile);
                return Blob.of(name, CommonMimeType.XLSX, DataSource.ofFile(tempFile).bytes());
            } finally {
                Files.deleteIfExists(tempFile.toPath()); // cleanup
            }
        }

    }

}
