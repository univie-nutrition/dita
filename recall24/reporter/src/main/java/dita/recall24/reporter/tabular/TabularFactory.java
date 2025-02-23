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

import java.math.BigDecimal;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedFunction;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.tabular.TabularModel;
import org.apache.causeway.commons.tabular.TabularModel.TabularCell;
import org.apache.causeway.commons.tabular.TabularModel.TabularRow;
import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;
import org.apache.causeway.core.metamodel.consent.InteractionInitiatedBy;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.tabular.simple.DataColumn;
import org.apache.causeway.core.metamodel.tabular.simple.DataRow;
import org.apache.causeway.core.metamodel.tabular.simple.DataTable;

import dita.commons.food.composition.FoodComponent;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.util.NumberUtils;
import dita.recall24.reporter.dom.ConsumptionRecord;

record TabularFactory(Can<FoodComponent> foodComponents) {

    public TabularSheet toTabularSheet(final Iterable<ConsumptionRecord> elements) {
        var dataTable = DataTable.forDomainType(ConsumptionRecord.class)
            .withDataElementPojos(elements);

        var dataColumnsFiltered = dataTable.dataColumns()
                .filter(col->!col.columnId().equals("nutrients"));

        var columns = dataColumnsFiltered
                .map(IndexedFunction.zeroBased(this::tabularColumn))
                .toArrayList();
        // add nutrient columns
        foodComponents.forEach(comp->columns.add(tabularColumn(columns.size(), comp)));

        var rows = dataTable.dataRows()
                .map(dr->tabularRow(dataColumnsFiltered, dr));

        return new TabularModel.TabularSheet(dataTable.tableFriendlyName(), Can.ofCollection(columns), rows);
    }

    static boolean isWip(final TabularCell cell) {
        return ":WIP".equals(cell.eitherValueOrLabelSupplier().leftIfAny());
    }

    static boolean isComposite(final TabularCell cell) {
        return "COMPOSITE".equals(cell.eitherValueOrLabelSupplier().leftIfAny());
    }

    static boolean containsWip(final TabularRow row) {
        return row.cells().stream().anyMatch(TabularFactory::isWip);
    }

    // -- HELPER

    private TabularModel.TabularColumn tabularColumn(final int index, final DataColumn dc) {
        return new TabularModel.TabularColumn(
                index,
                dc.columnFriendlyName(),
                dc.columnDescription().orElse(""));
    }

    //de.literal:name/‹..›
    private final static SemanticIdentifier.SystemId literalDe = new SemanticIdentifier.SystemId("de.literal");

    private TabularModel.TabularColumn tabularColumn(final int index, final FoodComponent comp) {

        var description = comp.attributes().elements().stream()
                .filter(attr->attr.systemId().equals(literalDe))
                .findFirst()
                .map(attr->attr.objectId().objectSimpleId())
                .orElse("no description");

        return new TabularModel.TabularColumn(
                index,
                comp.componentId().toStringNoBox(),
                "%s\n%s".formatted(description, comp.prefixedUnit()));
    }

    private TabularModel.TabularRow tabularRow(
            final Can<DataColumn> dataColumns,
            final DataRow dataRow) {

        var cells = dataColumns.map(dataColumn->{
            var cellElements = dataRow.getCellElements(dataColumn, InteractionInitiatedBy.PASS_THROUGH);
            return TabularModel.TabularCell.single(cellElements.getFirst().map(ManagedObject::getPojo).orElse(null));
        })
        .toArrayList();

        var consumptionRecord = (ConsumptionRecord) dataRow.rowElement().getPojo();
        var nutrients = consumptionRecord.nutrients();

        // add nutrient cells
        if(!nutrients.isEmpty()) {
            _Assert.assertEquals(foodComponents.size(), nutrients.cardinality());
            foodComponents
                .map(IndexedFunction.zeroBased((index, _)->numericalCell(nutrients.get(index))))
                .forEach(cells::add);
        } else {
            foodComponents
                .map(_->TabularModel.TabularCell.empty())
                .forEach(cells::add);
        }

        return new TabularModel.TabularRow(Can.ofCollection(cells));
    }

    TabularModel.TabularCell numericalCell(final double value) {
        // this conversion saves around 25% of resulting excel file size
        return TabularModel.TabularCell.single(NumberUtils.reducedPrecision(new BigDecimal(value), 2));
    }

}