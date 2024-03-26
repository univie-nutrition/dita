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
import java.util.concurrent.ConcurrentHashMap;

import jakarta.inject.Inject;

import org.causewaystuff.blobstore.applib.BlobStore;
import org.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;

@Service
public class SurveyTreeRootNodeHelperService {

    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    private final Map<Campaign.SecondaryKey, SurveyTreeNode> cache = new ConcurrentHashMap<>();

    public SurveyTreeNode root(final Campaign.SecondaryKey campaignSecondaryKey) {
        var campaign = foreignKeyLookupService.unique(campaignSecondaryKey);
        return cache.computeIfAbsent(campaignSecondaryKey, _->
            Campaigns.surveyTreeRootNode(campaign, surveyBlobStore));
    }

    public void invalidateCache(final Campaign.SecondaryKey campaignSecondaryKey) {
        cache.remove(campaignSecondaryKey);
    }

}
