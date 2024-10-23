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
package dita.globodiet.survey.recall24;

import java.io.File;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.commons.collections.Can;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.util.XlsxUtils;
import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.globodiet.survey.util.SidUtils;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.util.Recall24SummaryStatistics;
import dita.recall24.dto.util.Recall24SummaryStatistics.MappingTodo;
import dita.recall24.reporter.tabular.TabularReporters;
import dita.recall24.reporter.tabular.TabularReporters.Aggregation;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class InterviewXmlParserIntegrationTest extends DitaGdSurveyIntegrationTest {

    private SystemId systemId = new SystemId("at.gd", "2.0");

    @Test
    void parsingFromBlobStore() {

        var nutMapping = loadNutMapping();
        var fcoMapping = loadFcoMapping();
        var pocMapping = loadPocMapping();
        var foodCompositionRepo = loadFcdb();
        assertEquals(14814, foodCompositionRepo.compositionCount());

        nutMapping.streamEntries()
            .limit(20)
            .forEach(e->System.err.printf("nut map: %s%n", e));

        var interviewSet = loadInterviewSet();

        var stats = new Recall24SummaryStatistics();
        var recordProcessor = new RecordProcessor(stats, systemId, nutMapping);

        //TODO[dita-globodiet-survey-24] flesh out reporting
        var xlsxFile = new File("d:/tmp/_scratch/report-no-aggregates.xlsx");
        var tabularReport = TabularReporters.TabularReport.builder()
                .interviewSet(interviewSet)
                .systemId(systemId)
                .nutMapping(nutMapping)
                .fcoMapping(fcoMapping)
                .fcoQualifier(SidUtils.languageQualifier("de"))
                .pocMapping(pocMapping)
                .pocQualifier(SidUtils.languageQualifier("de"))
                .foodCompositionRepo(foodCompositionRepo)
                .aggregation(Aggregation.NONE)
                .build();

        tabularReport.report(xlsxFile);

        XlsxUtils.launchViewerAndWaitFor(xlsxFile);

//        var todoReporter = new TodoReportUtils.TodoReporter(systemId, nutMapping, interviewSet);
//        todoReporter.report(
//                DataSink.ofFile(new File("d:/tmp/_scratch/mapping-todos.txt")));

        interviewSet.streamDepthFirst()
            .forEach((final RecallNode24 node)->{
                stats.accept(node);
                switch(node) {
                    case Record24.Consumption cRec -> recordProcessor.accept(cRec);
                    default -> {}
                }
            });

        System.out.println("=== STATS ===");
        System.out.println(stats.formatted());
        System.out.println("=============");
    }

    record RecordProcessor(
            Recall24SummaryStatistics stats,
            SystemId systemId,
            QualifiedMap nutMapping)
    implements Consumer<Record24.Consumption> {
        @Override
        public void accept(final Record24.Consumption rec) {
            var mapKey = rec.asQualifiedMapKey();
            var mapEntry = nutMapping.lookupEntry(mapKey);
            if(mapEntry.isPresent()) {
                stats.consumptionStats().mappedCount().increment();
            } else {
                stats.consumptionStats().collectMappingTodo(new MappingTodo(mapKey, similar(mapKey)));
                System.err.printf("unmapped: %s (%s)%n", rec.name(), mapKey);
            }
        }
        private Can<QualifiedMapEntry> similar(final QualifiedMapKey mapKey) {
            return nutMapping.streamEntriesHavingSource(mapKey.source())
                    .collect(Can.toCan())
                    .distinct();
        }
    }

}
