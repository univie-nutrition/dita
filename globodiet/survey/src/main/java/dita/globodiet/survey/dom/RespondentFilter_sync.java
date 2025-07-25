/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.survey.dom;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.services.factory.FactoryService;

import lombok.RequiredArgsConstructor;

import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.listing.Listing;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

@Action
@ActionLayout(
        sequence = "2",
        associateWith = "listingView",
        describedAs = "Synchronize this RespondentFilter with the list of respondents "
                + "from the corresponding survey.",
        cssClassFa = "solid arrows-rotate",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
public class RespondentFilter_sync {

    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    private final RespondentFilter mixee;

    @MemberSupport
    public RespondentFilter act(final Listing.MergePolicy lineMergePolicy) {

        var survey = foreignKeyLookupService.unique(new Survey.SecondaryKey(mixee.getSurveyCode()));
        var campaignKeys = Campaigns.listAll(factoryService, survey)
            .map(Campaign::secondaryKey);

        var listingHandler = DataUtil.listingHandlerForRespondentProxy();

        var client = new BlobStoreClient(survey.secondaryKey(), surveyBlobStore);

        var allRespondents = client.cachableInterviewsCorrected(Surveys.systemId(survey), campaignKeys)
            .get()
            .respondents();

        var allRespondentsListing = listingHandler.createListing(allRespondents);

        var currentLines = listingHandler.parseListing(mixee.getAliasListing());

        mixee.setAliasListing(currentLines.merge(lineMergePolicy, allRespondentsListing).toString());
        return mixee;
    }

    @MemberSupport
    public Listing.MergePolicy defaultLineMergePolicy() {
        return Listing.MergePolicy.ADD_NEW_AS_DISABLED;
    }

}
