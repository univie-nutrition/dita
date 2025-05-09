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
package dita.globodiet.survey.dom;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.collections.Can;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.qmap.QualifiedMap;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.util.AssociatedRecipeResolver;
import dita.globodiet.survey.util.IngredientToRecipeResolver;
import dita.globodiet.survey.util.QualifiedMappingResolver;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.blobstore.applib.BlobStore;
@Log4j2
public record ReportContext(
    FoodCompositionRepository foodCompositionRepository,
    FoodDescriptionModel foodDescriptionModel,
    QualifiedMap specialDayMapping,
    QualifiedMap specialDietMapping,
    QualifiedMap fcoMapping,
    QualifiedMap pocMapping,
    QualifiedMap nutMapping,
    InterviewSet24 interviewSet) {

    public static ReportContext empty() {
        return new ReportContext(FoodCompositionRepository.empty(), FoodDescriptionModel.empty(),
            QualifiedMap.empty(), QualifiedMap.empty(), QualifiedMap.empty(),
            QualifiedMap.empty(), QualifiedMap.empty(),
            InterviewSet24.empty());
    }

    public static ReportContext load(final Can<Campaign.SecondaryKey> campaignKeys, final BlobStore surveyBlobStore) {
        return load(campaignKeys, surveyBlobStore, null);
    }

    @SneakyThrows
    public static ReportContext load(final Can<Campaign.SecondaryKey> campaignKeys, final BlobStore surveyBlobStore,
        @Nullable final RespondentFilter respondentFilter) {
        if(campaignKeys==null
            || campaignKeys.isEmpty()
            || surveyBlobStore==null) {
            return empty();
        }
        var firstCampaign = campaignKeys.getFirstElseFail();
        var surveyKey = new Survey.SecondaryKey(firstCampaign.surveyCode());

        var systemId = Surveys.systemId(surveyKey); // requires persistence
        var correction = Surveys.correction(surveyKey); // requires persistence

        var pool = Executors.newFixedThreadPool(6);
        var interviewSetCorrectedFuture = pool.submit(()->Campaigns.interviewSetCorrected(systemId, campaignKeys, correction, surveyBlobStore));
        var nutMappingFuture = pool.submit(()->Surveys.nutMapping(surveyKey, surveyBlobStore));
        var fcoMappingFuture = pool.submit(()->Surveys.fcoMapping(surveyKey, surveyBlobStore));
        var pocMappingFuture = pool.submit(()->Surveys.pocMapping(surveyKey, surveyBlobStore));
        var specialDayMappingFuture = pool.submit(()->Surveys.specialDayMapping(surveyKey, surveyBlobStore));
        var specialDietMappingFuture = pool.submit(()->Surveys.specialDietMapping(surveyKey, surveyBlobStore));
        var fcdbFuture = pool.submit(()->Surveys.fcdb(surveyKey, surveyBlobStore));
        var fdmFuture = pool.submit(()->Surveys.foodDescriptionModel(surveyKey, surveyBlobStore));
        pool.shutdown();

        log.info("await blobstore data");
        pool.awaitTermination(20, TimeUnit.SECONDS);
        log.info("data received");

        var interviewSet = interviewSetCorrectedFuture.get();
        // filter interviews
        if(!interviewSet.isEmpty()
            && respondentFilter!=null) {
            var enabledAliases = DataUtil.enabledAliasesInListing(respondentFilter.getAliasListing());
            interviewSet = interviewSet.filter(resp->enabledAliases.contains(resp.alias()));
        }
        return new ReportContext(fcdbFuture.get(), fdmFuture.get(),
            specialDayMappingFuture.get(), specialDietMappingFuture.get(),
            fcoMappingFuture.get(), pocMappingFuture.get(),
            nutMappingFuture.get(), interviewSet);
    }

    //TODO[dita-globodiet-survey] perhaps don't hardcode interview-set post-processors: make this a configuration concern
    public ReportContext defaultTransform() {
        return transform(in->
            in.isEmpty()
                ? in
                : in
                    .transform(new AssociatedRecipeResolver(foodDescriptionModel()))
                    .transform(new QualifiedMappingResolver(nutMapping()))
                    .transform(new IngredientToRecipeResolver(foodDescriptionModel()))
                    // to handle ingredients from the previous transformer
                    .transform(new QualifiedMappingResolver(nutMapping())));
    }

    public ReportContext transform(final UnaryOperator<InterviewSet24> interviewTransformer) {
        return new ReportContext(foodCompositionRepository, foodDescriptionModel,
            specialDayMapping, specialDietMapping, fcoMapping, pocMapping, nutMapping,
            interviewTransformer.apply(interviewSet));
    }

    public boolean isEmpty() {
        return interviewSet.isEmpty();
    }

    // -- SHORTCUTS

    public static ReportContext loadAndTransform(final Can<Campaign.SecondaryKey> campaignKeys, final BlobStore surveyBlobStore) {
        return loadAndTransform(campaignKeys, surveyBlobStore, null);
    }

    public static ReportContext loadAndTransform(final Can<Campaign.SecondaryKey> campaignKeys, final BlobStore surveyBlobStore,
        @Nullable final RespondentFilter respondentFilter) {
        return load(campaignKeys, surveyBlobStore, respondentFilter).defaultTransform();
    }

}
