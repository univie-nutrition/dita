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
package dita.globodiet.manager.editing.wip;

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
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.tabular.simple.DataColumn;
import org.apache.causeway.core.metamodel.tabular.simple.DataRow;
import org.apache.causeway.core.metamodel.tabular.simple.DataTable;
import org.apache.causeway.core.metamodel.tabular.simple.DataTable.CellVisitor;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.RequiredArgsConstructor;
import lombok.val;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.manager.util.AsciiDocUtils;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class RecipeManager_importRecipes {

    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;

    final Recipe.Manager recipeManager;

    public enum ImportFormat {
        GLOBODIET_XML,
    }

    //TODO yet just a blueprint, that displays all recipes from current persistence
    @MemberSupport
    public AsciiDoc act(
            @Parameter
            final ImportFormat format,
            @Parameter
            @ParameterLayout(named = "tableData")
            final Clob recipeData) {

        var recipeTable = DataTable.forDomainType(Recipe.class)
                .populateEntities();

        val yaml = new StringBuilder();
        yaml.append("rows").append("\n");

        recipeTable.visit(new CellVisitor() {
            int rowNr = 1;
            @Override public void onRowEnter(final DataRow row) {
                yaml.append(" -rowNr: ").append(rowNr++).append("\n");
            }
            @Override public void onCell(final DataColumn column, final Can<ManagedObject> cellValues) {
                yaml.append("  ").append(column.getColumnId()).append(": ");
                cellValues
                    .forEach(ce->yaml.append(ce.getTitle()));
                yaml.append("\n");
            }
            @Override public void onRowLeave(final DataRow row) {
            }
        });

        return AsciiDocUtils.yamlBlock("Recipe Import Result", "YAML format", yaml.toString());
    }

    @MemberSupport
    public ImportFormat defaultFormat() {
        return ImportFormat.GLOBODIET_XML;
    }

}
