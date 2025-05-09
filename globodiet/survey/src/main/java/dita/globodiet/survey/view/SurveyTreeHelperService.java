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
package dita.globodiet.survey.view;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.core.metamodel.context.MetaModelContext;

import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;
import dita.globodiet.survey.dom.ReportContext;
import dita.globodiet.survey.dom.Survey;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

@Service
public class SurveyTreeHelperService {

    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;
    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    private final Map<InspectionContext, InterviewSet24> cache = new ConcurrentHashMap<>();

    public Optional<Survey> survey(final InspectionContext inspectionContext) {
        return inspectionContext.surveySecondaryKey()
            .map(foreignKeyLookupService::unique);
    }

    public InterviewSet24 root(final InspectionContext inspectionContext) {
        return cache.computeIfAbsent(inspectionContext, this::loadInterviewSet);
    }

    //TODO perhaps provide an invalidate button, for administrative purposes
    public void invalidateCache() {
        cache.clear();
    }

    // -- UTILITY

    static SurveyTreeHelperService instance() {
        return MetaModelContext.instanceElseFail().lookupServiceElseFail(SurveyTreeHelperService.class);
    }

    // -- HELPER

    /// basic corrections and filters applied, but no interview transformations
    private InterviewSet24 loadInterviewSet(final InspectionContext inspectionContext) {
        var survey = survey(inspectionContext).orElse(null);
        if(survey==null) return InterviewSet24.empty();

        var respFilter = inspectionContext.respondentFilterSecondaryKey()
            .map(foreignKeyLookupService::unique)
            .orElse(null);

        var campaignKeys = Campaigns.listAll(factoryService, survey)
            .map(Campaign::secondaryKey);
        var reportContext = ReportContext.load(campaignKeys, surveyBlobStore, respFilter);
        return reportContext.interviewSet();
    }

}
