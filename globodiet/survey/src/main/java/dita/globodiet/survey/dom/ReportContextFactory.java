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
import lombok.extern.slf4j.Slf4j;

import dita.commons.sid.SemanticIdentifier.SystemId;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Slf4j
public record ReportContextFactory(
        SystemId systemId,
        BlobStore surveyBlobStore,
        Can<Campaign.SecondaryKey> campaignKeys) {

    @SneakyThrows
    public ReportContext load(
            @Nullable final RespondentFilter respondentFilter) {
        if(campaignKeys==null
            || campaignKeys.isEmpty()
            || surveyBlobStore==null) {
            return ReportContext.empty();
        }
        var firstCampaign = campaignKeys.getFirstElseFail();
        var surveyKey = new Survey.SecondaryKey(firstCampaign.surveyCode());

        var client = new BlobStoreClient(surveyKey, surveyBlobStore);

        //TODO share this instance
        var cachableInterviewSetCorrected = client.cachableInterviewsCorrected(systemId, campaignKeys);

        var pool = Executors.newFixedThreadPool(6);
        var interviewSetCorrectedFuture = pool.submit(cachableInterviewSetCorrected::get);
        var nutMappingFuture = pool.submit(client::nutMapping);
        var fcoMappingFuture = pool.submit(client::fcoMapping);
        var pocMappingFuture = pool.submit(client::pocMapping);
        var specialDayMappingFuture = pool.submit(client::specialDayMapping);
        var specialDietMappingFuture = pool.submit(client::specialDietMapping);
        var fcdbFuture = pool.submit(client::fcdb);
        var fdmFuture = pool.submit(client::foodDescriptionModel);
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


}
