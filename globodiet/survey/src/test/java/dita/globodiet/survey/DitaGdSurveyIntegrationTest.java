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
import java.util.function.Predicate;

import jakarta.inject.Inject;

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
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.bls.BLS302;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;
import dita.globodiet.survey.dom.ReportContext;
import dita.globodiet.survey.dom.Survey;
import dita.globodiet.survey.util.SidUtils;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Respondent24;
import dita.recall24.reporter.tabular.TabularReport;
import dita.recall24.reporter.tabular.TabularReport.Aggregation;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;

@Log4j2
public abstract class DitaGdSurveyIntegrationTest
extends CausewayIntegrationTestAbstract {

    @Inject @Qualifier("survey") protected BlobStore surveyBlobStore;

    protected FoodCompositionRepository loadFcdb() {
        return Campaigns.fcdb(campaignKeyForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadNutMapping() {
        return Campaigns.nutMapping(campaignKeyForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadFcoMapping() {
        return Campaigns.fcoMapping(campaignKeyForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadPocMapping() {
        return Campaigns.pocMapping(campaignKeyForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadSpecialDayMapping() {
        return Campaigns.specialDayMapping(campaignKeyForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadSpecialDietMapping() {
        return Campaigns.specialDietMapping(campaignKeyForTesting(), surveyBlobStore);
    }

    protected FoodDescriptionModel loadFoodDescriptionModel() {
        return Campaigns.foodDescriptionModel(campaignKeyForTesting(), surveyBlobStore);
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

    @SneakyThrows
    protected TabularReport tabularReport(
        final Aggregation aggregation,
        final Predicate<Campaign.SecondaryKey> campaignFilter,
        final Predicate<Respondent24> respondentFiler,
        final int maxNutrientColumns) {

        var reportContext = ReportContext.load(campaignKeysForTesting().filter(campaignFilter), surveyBlobStore);

        final InterviewSet24 interviewSet= reportContext.interviewSet().filter(respondentFiler);
        final SystemId systemId = SystemId.parse(SYSTEM_ID);
        final SemanticIdentifierSet languageQualifier = SidUtils.languageQualifier("de");

        final FoodCompositionRepository foodCompositionRepo = reportContext.foodCompositionRepository();
        final Can<FoodComponent> foodComponents = loadEnabledFoodComponents(reportContext.foodCompositionRepository().componentCatalog())
                .stream()
                .limit(maxNutrientColumns)
                .collect(Can.toCan());

        return new TabularReport(interviewSet, systemId, languageQualifier,
                reportContext.specialDayMapping(),
                reportContext.specialDietMapping(),
                reportContext.fcoMapping(),
                reportContext.pocMapping(),
                reportContext.foodDescriptionModel(),
                foodCompositionRepo, foodComponents, aggregation);
    }

    // -- HELPER

    static String SURVEY_CODE = "at-national-2026";
    static String SYSTEM_ID = "at.gd/2.0";

    private Can<Campaign.SecondaryKey> campaignKeysForTesting() {
        return Can.of(campaignKeyForTesting("wave1"), campaignKeyForTesting("wave2"), campaignKeyForTesting("wave3"));
    }

    private Campaign.SecondaryKey campaignKeyForTesting() {
        return campaignKeyForTesting("wave1");
    }

    private Campaign.SecondaryKey campaignKeyForTesting(final String campaignId) {
        _Context.computeIfAbsent(Survey.class, ()->{
            var survey = new Survey();
            survey.setCode(SURVEY_CODE);
            survey.setSystemId(SYSTEM_ID);
            survey.setCorrection(loadCorrections(SURVEY_CODE));
            return survey;
        });
        return new Campaign.SecondaryKey(SURVEY_CODE, campaignId);
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
