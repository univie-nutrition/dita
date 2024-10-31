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

import dita.globodiet.survey.dom.SurveyDeps.Survey_dependentCampaignMappedBySurvey;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.listing.Listing;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

@Action
@ActionLayout(
        sequence = "2",
        associateWith = "listingView",
        describedAs = "Synchronize this ReportColumnDefinition with the FCDB catalog "
                + "as associtated with the corresponding survey.",
        cssClassFa = "solid arrows-rotate",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
public class ReportColumnDefinition_sync {

    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    private final ReportColumnDefinition mixee;

    @MemberSupport
    public ReportColumnDefinition act(final Listing.MergePolicy lineMergePolicy) {
        var survey = foreignKeyLookupService.unique(new Survey.SecondaryKey(mixee.getSurveyCode()));
        var campaigns = factoryService.mixin(Survey_dependentCampaignMappedBySurvey.class, survey)
            .coll();
        var componentCatalog = Campaigns.fcdb(campaigns.getFirst(), surveyBlobStore)
                .componentCatalog();

        var listingHandler = DataUtil.listingHandlerForFoodComponents(
                str->DataUtil.destringifyElseFail(componentCatalog, str));

        var allComponents = listingHandler.createListing(componentCatalog.streamComponents());
        var currentComponents = listingHandler.parseListing(mixee.getColumnListing());
        mixee.setColumnListing(currentComponents.merge(lineMergePolicy, allComponents).toString());
        return mixee;
    }

    @MemberSupport
    public Listing.MergePolicy defaultLineMergePolicy() {
        return Listing.MergePolicy.ADD_NEW_AS_DISABLED;
    }

}
