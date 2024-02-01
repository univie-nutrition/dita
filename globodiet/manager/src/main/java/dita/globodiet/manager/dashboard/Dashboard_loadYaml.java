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
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml.InsertMode;
import dita.commons.types.TabularData;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.dom.params.recipe_list.Recipe.AliasQ;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.dashboard.Dashboard_generateYaml.ExportFormat;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Action// TODO temorary (restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_loadYaml {

    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;

    final Dashboard dashboard;

    @MemberSupport
    public AsciiDoc act(
            @Parameter
            final ExportFormat format,
            @Parameter
            @ParameterLayout(named = "tableData")
            final Clob tableData) {

        val adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Table Import Result"));
        adoc.append(doc->{
            val sourceBlock = AsciiDocFactory.sourceBlock(doc, "yml",
                    tableSerializer.load(tableData,
                            format==ExportFormat.ENTITY
                                ? TabularData.NameTransformer.IDENTITY
                                : table2entity,
                            BlobStore.paramsTableFilter(),
                            InsertMode.DELETE_ALL_THEN_ADD,
                            entity->{
                                if(entity.getSpecification().isAssignableFrom(Recipe.class)) {
                                    var recipe = (Recipe)entity.getPojo();
                                    if(recipe.getAliasQ() == AliasQ.ALIAS) {
                                        recipe.setRecipeGroupCode("");
                                    }
                                }

                            }));
            sourceBlock.setTitle("Serialized Table Data (yaml)");
        });

        return adoc.buildAsValue();
    }

}
