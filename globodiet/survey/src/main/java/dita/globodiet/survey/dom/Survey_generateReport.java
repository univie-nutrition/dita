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

import java.time.LocalDate;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;

import lombok.RequiredArgsConstructor;

import dita.commons.format.FormatUtils;
import dita.globodiet.survey.dom.SurveyDeps.Survey_dependentReportColumnDefinitionMappedBySurvey;
import dita.globodiet.survey.dom.SurveyDeps.Survey_dependentRespondentFilterMappedBySurvey;
import dita.globodiet.survey.util.SidUtils;
import dita.recall24.reporter.tabular.TabularReporters;
import dita.recall24.reporter.tabular.TabularReporters.Aggregation;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        choicesFrom = "dependentCampaignMappedBySurvey"
)
@ActionLayout(
        fieldSetId = "dependentCampaignMappedBySurvey",
        sequence = "2",
        cssClass = "btn-primary",
        cssClassFa = "solid file-export",
        describedAs = "Generates an Interview Report (for selected campains).",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
public class Survey_generateReport {

    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    @Inject private FactoryService factoryService;

    private final Survey mixee;

    /**
     * @see Campaign_generateReport
     */
    @MemberSupport
    public Blob act(
            final List<Campaign> campaigns,
            @Parameter(optionality = Optionality.MANDATORY)
            final RespondentFilter respondentFilter,
            @Parameter
            final ReportColumnDefinition reportColumnDefinition,
            @Parameter
            final Aggregation aggregation) {
        // see also Campaign_downloadMappingTodos
        var interviewSet = Campaigns.interviewSet(Can.ofCollection(campaigns), surveyBlobStore);
        if(interviewSet.isEmpty()) {
            return Blob.of("empty", CommonMimeType.TXT, new byte[0]);
        }

        // filter interviews
        var enabledAliases = DataUtil.enabledAliasesInListing(respondentFilter.getAliasListing());

        interviewSet.filter(resp->enabledAliases.contains(resp.alias()));

        var nutMapping = Campaigns.nutMapping(campaigns.getFirst(), surveyBlobStore);
        var fcoMapping = Campaigns.fcoMapping(campaigns.getFirst(), surveyBlobStore);
        var pocMapping = Campaigns.pocMapping(campaigns.getFirst(), surveyBlobStore);
        var foodCompositionRepo = Campaigns.fcdb(campaigns.getFirst(), surveyBlobStore);
        var systemId = Campaigns.systemId(mixee);

        var tabularReport = new TabularReporters.TabularReport(interviewSet, systemId,
                nutMapping,
                fcoMapping, SidUtils.languageQualifier("de"),
                pocMapping, SidUtils.languageQualifier("de"),
                foodCompositionRepo,
                aggregation);

        var name = String.format("%s_%s_%s",
                mixee.getCode().toLowerCase(),
                aggregation.name(),
                FormatUtils.isoDate(LocalDate.now()));
        return tabularReport.reportAsBlob(name);
    }

    @MemberSupport
    public List<RespondentFilter> choicesRespondentFilter() {
        return factoryService.mixin(Survey_dependentRespondentFilterMappedBySurvey.class, mixee)
                .coll();
    }

    @MemberSupport
    public List<ReportColumnDefinition> choicesReportColumnDefinition() {
        return factoryService.mixin(Survey_dependentReportColumnDefinitionMappedBySurvey.class, mixee)
                .coll();
    }

    @MemberSupport
    public Aggregation defaultAggregation() {
        return Aggregation.NONE;
    }

}
