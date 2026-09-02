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
package dita.globodiet.manager.versions;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.springframework.beans.factory.annotation.Qualifier;

import dita.commons.types.TabularData;
import dita.globodiet.manager.versions.VersionsService.VersionFilter;
import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;
import dita.globodiet.survey.dom.ReportContext;
import dita.globodiet.survey.dom.Survey;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Action(semantics = SemanticsOf.IDEMPOTENT)
@ActionLayout(
		fieldSetName="About",
		position = Position.PANEL,
		describedAs = "Given 2 Parameter Data Versions, calculates potential Corrections against the current Interview Set of this Survey")
public record ParameterDataVersion_generateCorrectionTemplate(
		ParameterDataVersion mixee,
		@Qualifier("survey") BlobStore surveyBlobStore,
		@Qualifier("table2entity") TabularData.NameTransformer nameTransformer,
		@Qualifier("diff") TabularData.SecondaryKeyProvider secondaryKeyProvider,
		FactoryService factoryService,
		RepositoryService repositoryService,
		VersionsService versionsService,
		VersionsExportService versionsExportService)  {

	@MemberSupport
	public Clob act(
			@ParameterLayout(describedAs =
			"Select Parameter Data Version as a base to compare against.")
			final ParameterDataVersion baseVersion,
			@ParameterLayout(describedAs =
					"The servey to create the Correction template for.")
			final Survey survey,
			@ParameterLayout(describedAs =
					"Amount of mass change of an ingredient relative to the recipe's total amount in units of parts per million (ppm),"
					+ "that must be exceeded in order for a change to be emitted. Changes that fall below given threshold are simply "
					+ "ignored with the output. (10000ppm = 1%)")
			final int ppmThreshold,
			@ParameterLayout(describedAs =
					"Whether to include group changes, however this is not needed "
					+ "because the Report Generator does correct groups automatically from a selected FDM.")
			final boolean includeGroupCorrections) {

		var mainVersion = mixee;

        var mainFdm = versionsExportService.getFoodDescriptionModel(mainVersion);
        var baseFdm = versionsExportService.getFoodDescriptionModel(baseVersion);

        var fdmDiff = new FdmDiffFactory().diff(mainFdm, baseFdm);

        var campaignKeys = Campaigns.listAll(factoryService, survey)
                .map(Campaign::secondaryKey);

        var reportContext = ReportContext.factory(surveyBlobStore, campaignKeys)
                .load()
                .defaultTransform();

		var corr24 = new CorrectionTemplateFactory(
				mainFdm::facetLiteral,
				fdmDiff, ppmThreshold, includeGroupCorrections)
        		.create(reportContext.interviewSet());

		return Clob.of("correction-template-v%s-v%s-%s"
				.formatted(
						baseVersion.id(),
						mainVersion.id(),
						survey.secondaryKey().code()), CommonMimeType.YAML, corr24.toYamlWithComments());
	}

	@MemberSupport
	public Can<ParameterDataVersion> choicesBaseVersion() {
		return versionsService.getVersions()
            .filter(VersionFilter.NOT_DELETED)
			.filter(baseVersion->baseVersion.id() < mixee.id());
	}

	@MemberSupport
	public List<Survey> choicesSurvey() {
		return repositoryService.allInstances(Survey.class);
	}

	@MemberSupport
	public int defaultPpmThreshold() {
		return 10_000;
	}

	@MemberSupport
	public boolean defaultIncludeGroupCorrections() {
		return false;
	}

}
