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
import java.time.LocalDate;
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

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodComponentQuantified;
import dita.commons.food.composition.FoodComposition;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.foodon.bls.BLS302;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.reporter.dom.ConsumptionRow;

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
            String systemId,
            QualifiedMap nutMapping,
            QualifiedMap fcoMapping,
            SemanticIdentifierSet fcoQualifier,
            QualifiedMap pocMapping,
            SemanticIdentifierSet pocQualifier,
            FoodCompositionRepository foodCompositionRepo,
            Aggregation aggregation) {

        @Data
        private static class RowFactory {
            final Set<String> respondentAliasSeenBefore = new HashSet<>();
            int respondentOrdinal;
            String respondentAlias;
            Sex respondentSex;
            long respondentAgeInDays;
            int interviewOrdinal;
            LocalDate consumptionDate;
            String fco;
            String poc;
            String meal;
            Record24.Type recordType;
            String group = "wip";
            String subgroup = "wip";
            String subSubgroup = "wip";
            // factory method for composites
            ConsumptionRow compositeHeader(final String systemId, final Record24.Composite comp) {
                return new ConsumptionRow(
                        respondentOrdinal,
                        respondentAlias,
                        respondentSex.ordinal(),
                        BigDecimal.valueOf(Math.round(respondentAgeInDays/36.52422)).scaleByPowerOfTen(-1),
                        interviewOrdinal,
                        consumptionDate,
                        fco,
                        poc,
                        meal,
                        Record24.Type.COMPOSITE.name(),
                        comp.name(),
                        comp.sidFullyQualified(systemId).fullFormat(":"),
                        group,
                        subgroup,
                        subSubgroup,
                        comp.facetSidsFullyQualified(systemId).fullFormat(":"),
                        null, //comp.amountConsumed(),
                        null);
            }
            // factory method for consumptions
            ConsumptionRow row(
                    final Record24.Consumption cRec,
                    final FoodConsumption foodConsumption,
                    final Optional<FoodComposition> compositionEntry){

                return new ConsumptionRow(
                        respondentOrdinal,
                        respondentAlias,
                        respondentSex.ordinal(),
                        BigDecimal.valueOf(Math.round(respondentAgeInDays/36.52422)).scaleByPowerOfTen(-1),
                        interviewOrdinal,
                        consumptionDate,
                        fco,
                        poc,
                        meal,
                        Optional.ofNullable(recordType)
                            .map(Record24.Type::name)
                            .orElse("?"),
                        foodConsumption.name(),
                        foodConsumption.foodId().fullFormat(":"),
                        group,
                        subgroup,
                        subSubgroup,
                        foodConsumption.facetIds().fullFormat(","),
                        cRec.amountConsumed(),
                        compositionEntry
                            .flatMap(e->e.lookupDatapoint(BLS302.Component.GCALZB.componentId()))
                            .map(dp->dp.quantify(foodConsumption))
                            .map(FoodComponentQuantified::quantityValue)
                            .orElse(null)
                        );
            }
            void setRespondentAlias(final String respondentAlias) {
                if(respondentAliasSeenBefore.add(respondentAlias)) respondentOrdinal++;
                this.respondentAlias = respondentAlias;
            }
        }

        public void report(final File file) {

            var rowFactory = new RowFactory();
            var consumptions = new ArrayList<ConsumptionRow>();
            var hourOfDayFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT);

            interviewSet.streamDepthFirst()
            .forEach((final RecallNode24 node)->{
                switch(node) {
                    case Interview24.Dto iv -> {
                        rowFactory.setRespondentAlias(iv.parentRespondent().alias());
                        rowFactory.setInterviewOrdinal(iv.interviewOrdinal());
                        rowFactory.setConsumptionDate(iv.interviewDate());
                        rowFactory.setRespondentSex(iv.parentRespondent().sex());
                        if(iv.interviewOrdinal()==1) {
                            rowFactory.setRespondentAgeInDays(
                                    ChronoUnit.DAYS.between(iv.parentRespondent().dateOfBirth(), iv.interviewDate()));
                        }
                    }
                    case Meal24.Dto meal -> {
                        rowFactory.setFco(meal.foodConsumptionOccasionId());
                        rowFactory.setPoc(meal.foodConsumptionPlaceId());
                        var fcoLabel = fcoMapping.lookupEntry(new SemanticIdentifier(systemId, meal.foodConsumptionOccasionId()), fcoQualifier)
                            .map(QualifiedMapEntry::target)
                            .map(SemanticIdentifier::objectId)
                            .orElse("?")
                            .replace('_', ' ');
                        var pocLabel = pocMapping.lookupEntry(new SemanticIdentifier(systemId, meal.foodConsumptionPlaceId()), pocQualifier)
                            .map(QualifiedMapEntry::target)
                            .map(SemanticIdentifier::objectId)
                            .orElse("?")
                            .replace('_', ' ');
                        var timeOfDayLabel = meal.hourOfDay().format(hourOfDayFormat);
                        rowFactory.setMeal(String.format("%s (%s) @ %s", fcoLabel, timeOfDayLabel, pocLabel));
                    }
                    case Record24.Composite comp -> {
                        rowFactory.setRecordType(comp.type());
                        consumptions.add(rowFactory.compositeHeader(systemId, comp));
                    }
                    case Record24.Consumption cRec -> {
                        rowFactory.setRecordType(cRec.type());
                        var foodConsumption = cRec.asFoodConsumption(systemId);
                        var compositionEntry = nutMapping
                                .lookupEntry(foodConsumption.qualifiedMapKey())
                                .map(QualifiedMapEntry::target)
                                .flatMap(foodCompositionRepo::lookupEntry);
                        if(!compositionEntry.isPresent()) {
                            //unmapped.add(mapKey);
                        }
                        consumptions.add(rowFactory.row(cRec, foodConsumption, compositionEntry));
                    }
                    default -> {}
                }
            });

            var dataTable = DataTable.forDomainType(ConsumptionRow.class);
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
