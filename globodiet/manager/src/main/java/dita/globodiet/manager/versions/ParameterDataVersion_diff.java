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

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.springframework.beans.factory.annotation.Qualifier;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.commons.types.TabularDiff;
import dita.commons.util.FormatUtils;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@Action(semantics = SemanticsOf.IDEMPOTENT)
@ActionLayout(
		fieldSetName="About",
		position = Position.PANEL,
		describedAs = "Generates a Parameter Data Diff between this and another selected version")
@RequiredArgsConstructor
public class ParameterDataVersion_diff {

	@Inject TableSerializerYaml tableSerializer;
	@Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;
	@Inject @Qualifier("diff") TabularData.SecondaryKeyProvider secondaryKeyProvider;
	@Inject FactoryService factoryService;

	final ParameterDataVersion mixee;

	@MemberSupport
	public AsciiDoc act(
			@ParameterLayout(describedAs =
			"Select Parameter Data Version as a base to compare against.")
			final ParameterDataVersion baseVersion) {

		var yaml = new TabularDiff(
				filter(mixee.asTabularData()),
				filter(baseVersion.asTabularData()),
				secondaryKeyProvider)
			.toYaml();

		return FormatUtils.adocSourceBlock("yaml", yaml);
	}

	@MemberSupport
	public Can<ParameterDataVersion> choicesBaseVersion() {
		return factoryService.viewModel(new VersionsView())
			.getVersions()
			.filter(baseVersion->baseVersion.id() < mixee.id());
	}

	private TabularData filter(final TabularData orig) {
		return orig.transform(table2entity)
			.filter(
				table->secondaryKeyProvider.lookupSecondaryKey(table.key()).isPresent(),
				row->!row.cellLiterals().contains("ALIAS"));
	}

}
