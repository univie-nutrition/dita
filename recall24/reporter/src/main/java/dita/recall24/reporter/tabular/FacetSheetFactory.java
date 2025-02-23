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
package dita.recall24.reporter.tabular;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.tabular.TabularModel;
import org.apache.causeway.commons.tabular.TabularModel.TabularCell;
import org.apache.causeway.commons.tabular.TabularModel.TabularColumn;
import org.apache.causeway.commons.tabular.TabularModel.TabularRow;
import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;

import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;

record FacetSheetFactory(FoodDescriptionModel fdm) {

    public TabularSheet facetSheet() {
        Can<TabularColumn> columns = Can.of(
                new TabularColumn(0, "Semantic Identifier", """
                        fd..food descriptor
                        fg..food group
                        rd..recipe descriptor
                        rg..recipe goup"""),
                new TabularColumn(1, "Description", "Literal in native language"));

        Can<TabularRow> rows = fdm.classificationFacetBySid().values()
                .stream()
                .sorted((a, b)->a.sid().compareTo(b.sid()))
                .map(this::toCells)
                .map(TabularRow::new)
                .collect(Can.toCan());

        return new TabularModel.TabularSheet("Facets and Groups", columns, rows);
    }

    private Can<TabularCell> toCells(final ClassificationFacet facet) {
        return Can.of(
                TabularCell.single(facet.sid().toStringNoBox()),
                TabularCell.single(facet.name()));
    }

}
