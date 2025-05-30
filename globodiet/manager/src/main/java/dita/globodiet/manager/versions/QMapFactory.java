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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.causeway.commons.io.ZipUtils;
import org.apache.causeway.commons.io.ZipUtils.EntryBuilder;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Table;

record QMapFactory(
        SystemId systemId,
        SemanticIdentifierSet languageQualifier,
        TabularData tabularData) {

    public EntryBuilder createZipOfYamls() {
        var entryBuilder = ZipUtils.zipEntryBuilder();
        append("fco", entryBuilder, streamFoodConsumptionOccasion());
        append("poc", entryBuilder, streamPlaceOfConsumption());
        append("sday", entryBuilder, streamSpecialDayPredefinedAnswer());
        append("sdiet", entryBuilder, streamSpecialDietPredefinedAnswer());
        return entryBuilder;
    }

    // -- HELPER

    private void append(final String name, final EntryBuilder entryBuilder, final Stream<QualifiedMapEntry> stream) {
        var qMap = new QualifiedMap(new LinkedHashMap<>());
        stream
            .sorted((a, b)->a.source().compareTo(b.source()))
            .forEach(qMap::put);
        entryBuilder.addAsUtf8(name + ".yaml", qMap.toYaml());
    }

    // -- ENTITIES

    private Optional<Table> lookupTableByKey(final String key) {
        return tabularData.dataTables().stream()
                .filter(dataTable->dataTable.key().equals(key))
                .findFirst();
    }

    //    - dita.globodiet.params.setting.FoodConsumptionOccasion:
    //        0 "textDisplayedOnScreen: FCO long label (text displayed on screen)"
    //        1 "mode: FCO type: if =1 the FCO can be selected several times (e.g. During morning)"
    //        2 "shortLabelToIdentifyEasily: FCO short label to identify easily the FCO"
    //        3 "displayInNutrientCheckScreenQ: 0=non main FCO|1=main FCO (to be displayed in nutrient check screen)"
    //        4 "code: Food Consumption Occasion code"
    private Stream<QualifiedMapEntry> streamFoodConsumptionOccasion() {
        return lookupTableByKey("dita.globodiet.params.setting.FoodConsumptionOccasion").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->foodConsumptionOccasionFromRowData(row.cellLiterals()));
    }
    private QualifiedMapEntry foodConsumptionOccasionFromRowData(final List<String> cellLiterals) {
        return new QualifiedMapEntry(
                new SemanticIdentifier(systemId(), new ObjectId("fco", cellLiterals.get(4))),
                languageQualifier,
                SemanticIdentifier.literal(cellLiterals.get(0)));
    }

    //    - dita.globodiet.params.setting.PlaceOfConsumption:
    //        0 "name: Place of consumption name"
    //        1 "otherPlaceQ: 0=not a 'Other' place|1='Other' place"
    //        2 "code: Place of consumption code"
    private Stream<QualifiedMapEntry> streamPlaceOfConsumption() {
        return lookupTableByKey("dita.globodiet.params.setting.PlaceOfConsumption").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->placeOfConsumptionFromRowData(row.cellLiterals()));
    }
    private QualifiedMapEntry placeOfConsumptionFromRowData(final List<String> cellLiterals) {
        return new QualifiedMapEntry(
                new SemanticIdentifier(systemId(), new ObjectId("poc", cellLiterals.get(2))),
                languageQualifier,
                SemanticIdentifier.literal(cellLiterals.get(0)));
    }

    //    - dita.globodiet.params.setting.SpecialDayPredefinedAnswer:
    //        0 "specialDayCode: Special day code"
    //        1 "specialDayLabel: Special day label"
    private Stream<QualifiedMapEntry> streamSpecialDayPredefinedAnswer() {
        return lookupTableByKey("dita.globodiet.params.setting.SpecialDayPredefinedAnswer").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->specialDayPredefinedAnswerFromRowData(row.cellLiterals()));
    }
    private QualifiedMapEntry specialDayPredefinedAnswerFromRowData(final List<String> cellLiterals) {
        return new QualifiedMapEntry(
                new SemanticIdentifier(systemId(), new ObjectId("sday", cellLiterals.get(0))),
                languageQualifier,
                SemanticIdentifier.literal(cellLiterals.get(1)));
    }

    //    - dita.globodiet.params.setting.SpecialDietPredefinedAnswer:
    //        0 "specialDietCode: Special diet code"
    //        1 "specialDietLabel: Special diet label"
    private  Stream<QualifiedMapEntry> streamSpecialDietPredefinedAnswer() {
        return lookupTableByKey("dita.globodiet.params.setting.SpecialDietPredefinedAnswer").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->specialDietPredefinedAnswerFromRowData(row.cellLiterals()));
    }
    private QualifiedMapEntry specialDietPredefinedAnswerFromRowData(final List<String> cellLiterals) {
        return new QualifiedMapEntry(
                new SemanticIdentifier(systemId(), new ObjectId("sdiet", cellLiterals.get(0))),
                languageQualifier,
                SemanticIdentifier.literal(cellLiterals.get(1)));
    }

}
