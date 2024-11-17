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

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.context._Context;
import org.apache.causeway.commons.io.TextUtils;
import org.apache.causeway.testing.integtestsupport.applib.CausewayIntegrationTestAbstract;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodComponentCatalog;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.qmap.QualifiedMap;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.foodon.bls.BLS302;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;
import dita.globodiet.survey.dom.Survey;
import dita.globodiet.survey.util.AssociatedRecipeResolver;
import dita.globodiet.survey.util.IngredientToRecipeResolver;
import dita.globodiet.survey.util.QualifiedMappingResolver;
import dita.globodiet.survey.util.SidUtils;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.reporter.tabular.TabularReporters;
import dita.recall24.reporter.tabular.TabularReporters.Aggregation;
import dita.recall24.reporter.tabular.TabularReporters.TabularReport;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;

@Log4j2
public abstract class DitaGdSurveyIntegrationTest
extends CausewayIntegrationTestAbstract {

    @Inject @Qualifier("survey") protected BlobStore surveyBlobStore;

    protected FoodCompositionRepository loadFcdb() {
        return Campaigns.fcdb(campaignForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadNutMapping() {
        return Campaigns.nutMapping(campaignForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadFcoMapping() {
        return Campaigns.fcoMapping(campaignForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadPocMapping() {
        return Campaigns.pocMapping(campaignForTesting(), surveyBlobStore);
    }

    protected FoodDescriptionModel loadFoodDescriptionModel() {
        return Campaigns.foodDescriptionModel(campaignForTesting(), surveyBlobStore);
    }

    protected Can<FoodComponent> loadEnabledFoodComponents(final FoodComponentCatalog foodComponentCatalog) {
        var colDef = TextUtils.readLines(loadColDef(SURVEY_CODE)).stream()
                .map(String::trim)
                .map(_Strings::emptyToNull)
                .filter(s->s!=null && !s.startsWith("#"))
                .map(s->TextUtils.cutter(s).keepBefore("\t").getValue())
                .map(BLS302::componentSid)
                .map(foodComponentCatalog::lookupEntry)
                .map(opt->opt.orElse(null))
                .collect(Can.toCan());
        return colDef;
    }

    protected InterviewSet24 loadInterviewSet() {
        return Campaigns.interviewSet(campaignForTesting(), surveyBlobStore);
//                .transform(new AssociatedRecipeResolver(loadFoodDescriptionModel()))
//                .transform(new QualifiedMappingResolver(loadNutMapping()));
    }

    @SneakyThrows
    protected TabularReport tabularReport(final Aggregation aggregation, final int maxNutrientColumns) {
        var pool = Executors.newFixedThreadPool(6);
        var nutMappingFuture = pool.submit(this::loadNutMapping);
        var fcoMappingFuture = pool.submit(this::loadFcoMapping);
        var pocMappingFuture = pool.submit(this::loadPocMapping);
        var fcdbFuture = pool.submit(this::loadFcdb);
        var fdmFuture = pool.submit(this::loadFoodDescriptionModel);
        var interviewSetFuture = pool.submit(this::loadInterviewSet);
        pool.shutdown();

        log.info("await blobstore data");
        pool.awaitTermination(20, TimeUnit.SECONDS);
        log.info("data received");

        var foodCompositionRepo = fcdbFuture.get();
        assertTrue(foodCompositionRepo.compositionCount()>10_000);
        var interviewSet = interviewSetFuture.get()
                .transform(new AssociatedRecipeResolver(fdmFuture.get()))
                .transform(new QualifiedMappingResolver(nutMappingFuture.get()))
                .transform(new IngredientToRecipeResolver(fdmFuture.get()));

        return TabularReporters.TabularReport.builder()
                .systemId(SystemId.parse(SYSTEM_ID))
                .fcoMapping(fcoMappingFuture.get())
                .fcoQualifier(SidUtils.languageQualifier("de"))
                .pocMapping(pocMappingFuture.get())
                .pocQualifier(SidUtils.languageQualifier("de"))
                .foodCompositionRepo(foodCompositionRepo)
                .foodComponents(loadEnabledFoodComponents(foodCompositionRepo.componentCatalog())
                        .stream()
                        .limit(maxNutrientColumns)
                        .collect(Can.toCan()))
                .interviewSet(interviewSet)
                .aggregation(aggregation)
                .build();
    }

    // -- HELPER

    static String SURVEY_CODE = "at-national-2026";
    static String CAMPAIGN_CODE = "wave1";
    static String SYSTEM_ID = "at.gd/2.0";

    private Campaign campaignForTesting() {
        _Context.computeIfAbsent(Survey.class, ()->{
            var survey = new Survey();
            survey.setCode(SURVEY_CODE);
            survey.setSystemId(SYSTEM_ID);
            survey.setCorrection(loadCorrections(SURVEY_CODE));
            return survey;
        });
        var campaign = new Campaign();
        campaign.setCode(CAMPAIGN_CODE);
        campaign.setSurveyCode(SURVEY_CODE);
        return campaign;
    }

    private String loadColDef(final String surveyCode) {
        return surveyBlobStore.lookupBlob(NamedPath.of(
                "surveys", surveyCode, "integtest", "col-def.txt"))
                .get()
                .toClob(StandardCharsets.UTF_8)
                .asString();
    }

    private String loadCorrections(final String surveyCode) {
        return surveyBlobStore.lookupBlob(NamedPath.of(
                "surveys", surveyCode, "integtest", "corrections.yaml"))
                .get()
                .toClob(StandardCharsets.UTF_8)
                .asString();
    }

}
