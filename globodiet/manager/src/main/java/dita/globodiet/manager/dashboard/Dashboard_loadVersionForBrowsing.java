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
package dita.globodiet.manager.dashboard;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.springframework.beans.factory.annotation.Qualifier;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.globodiet.manager.versions.ParameterDataVersion;
import dita.globodiet.manager.versions.VersionsExportService.ExportFormat;
import dita.globodiet.manager.versions.VersionsView;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
        //restrictTo = RestrictTo.PROTOTYPING
        )
@ActionLayout(
	fieldSetName="About",
	position = Position.PANEL,
	describedAs = "Loads Parameter Data for Browsing from a Parameter Data Version. (Takes a couple of minutes.)")
@RequiredArgsConstructor
public class Dashboard_loadVersionForBrowsing {

    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;
    @Inject FactoryService factoryService;

    final Dashboard dashboard;

    @MemberSupport
    public AsciiDoc act(
    		@ParameterLayout(describedAs =
    				"Select Parameter Data Version to load for browsing.")
    		final ParameterDataVersion version) {
    	return factoryService.mixin(Dashboard_loadParameterData.class, dashboard)
    		.act(ExportFormat.TABLE, zippedParameterData(version));
    }

	@MemberSupport
    public List<ParameterDataVersion> choicesVersion() {
		return factoryService.viewModel(new VersionsView())
			.getVersions()
			.toList();
    }

	private Blob zippedParameterData(final ParameterDataVersion version) {
		return version.zippedParameterData();
	}

}
