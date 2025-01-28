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
    QualifiedMap fcoMapping,
    QualifiedMap pocMapping,
    QualifiedMap nutMapping,
    InterviewSet24 interviewSet) {

    public static ReportContext empty() {
        return new ReportContext(FoodCompositionRepository.empty(), FoodDescriptionModel.empty(),
            QualifiedMap.empty(), QualifiedMap.empty(), QualifiedMap.empty(), InterviewSet24.empty());
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

        var systemId = Campaigns.systemId(firstCampaign); // requires persistence
        var correction = Campaigns.correction(firstCampaign); // requires persistence

        var pool = Executors.newFixedThreadPool(6);
        var interviewSetCorrectedFuture = pool.submit(()->Campaigns.interviewSetCorrected(systemId, campaignKeys, correction, surveyBlobStore));
        var nutMappingFuture = pool.submit(()->Campaigns.nutMapping(firstCampaign, surveyBlobStore));
        var fcoMappingFuture = pool.submit(()->Campaigns.fcoMapping(firstCampaign, surveyBlobStore));
        var pocMappingFuture = pool.submit(()->Campaigns.pocMapping(firstCampaign, surveyBlobStore));
        var fcdbFuture = pool.submit(()->Campaigns.fcdb(firstCampaign, surveyBlobStore));
        var fdmFuture = pool.submit(()->Campaigns.foodDescriptionModel(firstCampaign, surveyBlobStore));
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
        if(!interviewSet.isEmpty()) {

            //TODO[dita-globodiet-survey] perhaps don't hardcode interview-set post-processors: make this a configuration concern
            var prepared = interviewSet
                    .transform(new AssociatedRecipeResolver(fdmFuture.get()))
                    .transform(new QualifiedMappingResolver(nutMappingFuture.get()))
                    .transform(new IngredientToRecipeResolver(fdmFuture.get()))
                    // to handle ingredients from the previous transformer
                    .transform(new QualifiedMappingResolver(nutMappingFuture.get()));

            interviewSet = prepared;
        }

        return new ReportContext(fcdbFuture.get(), fdmFuture.get(),
            fcoMappingFuture.get(), pocMappingFuture.get(), nutMappingFuture.get(), interviewSet);
    }

    public boolean isEmpty() {
        return interviewSet.isEmpty();
    }

}
