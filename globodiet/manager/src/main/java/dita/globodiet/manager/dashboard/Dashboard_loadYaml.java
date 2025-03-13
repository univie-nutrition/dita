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

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.RequiredArgsConstructor;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml.InsertMode;
import dita.commons.types.TabularData;
import dita.commons.util.AsciiDocUtils;
import dita.globodiet.manager.versions.VersionsExportService;
import dita.globodiet.manager.versions.VersionsExportService.ExportFormat;
import dita.globodiet.params.recipe_list.Recipe;
import dita.globodiet.params.recipe_list.Recipe.AliasQ;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_loadYaml {

    @Inject private TableSerializerYaml tableSerializer;
    @Inject @Qualifier("table2entity") private TabularData.NameTransformer table2entity;

    final Dashboard dashboard;

    @MemberSupport
    public AsciiDoc act(
            @Parameter
            final ExportFormat format,
            @Parameter
            @ParameterLayout(named = "tableData")
            final Clob tableData) {

        var yamlSource = tableSerializer.load(tableData,
                format==ExportFormat.ENTITY
                ? TabularData.NameTransformer.IDENTITY
                : table2entity,
            VersionsExportService.paramsTableFilter(),
            InsertMode.DELETE_ALL_THEN_ADD,
            entity->{
                if(entity.objSpec().isAssignableFrom(Recipe.class)) {
                    var recipe = (Recipe)entity.getPojo();
                    if(recipe.getAliasQ() == Recipe.AliasQ.ALIAS) {
                        recipe.setRecipeGroupCode("");
                    } else {
                        recipe.setAliasQ(AliasQ.REGULAR);
                    }
                }
            });

        return AsciiDocUtils.yamlBlock("Table Import Result", "Serialized Table Data (yaml)", yamlSource);
    }

}
