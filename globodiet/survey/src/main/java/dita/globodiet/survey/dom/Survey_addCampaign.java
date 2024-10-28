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

import java.util.List;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

@Action(
        semantics = SemanticsOf.IDEMPOTENT
)
@ActionLayout(
        fieldSetId = "dependentCampaignMappedBySurvey",
        sequence = "1",
        describedAs = "Adds a Campaign to this Survey",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
public class Survey_addCampaign {

    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookup;

    private final Survey mixee;

    @MemberSupport
    public Survey act(@ParameterTuple final Campaign.Params p) {

        var campaign = repositoryService.detachedEntity(new Campaign());
        campaign.setSurveyCode(mixee.secondaryKey().code());
        campaign.setCode(p.code());
        campaign.setName(p.name());
        campaign.setDescription(p.description());

        repositoryService.persist(campaign);
        foreignKeyLookup.clearCache(Campaign.class);
        return mixee;
    }

    //TODO this is not used - but required to pass MM validation
    @MemberSupport
    public List<Survey> choicesSurvey() {
        return List.of();
    }

    @MemberSupport
    public Survey defaultSurvey() {
        return mixee;
    }

    @MemberSupport
    public boolean hideSurvey() {
        return true;
    }

}
