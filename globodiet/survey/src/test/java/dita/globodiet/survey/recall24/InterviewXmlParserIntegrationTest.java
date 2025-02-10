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
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

import dita.commons.util.XlsxUtils;
import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.util.Recall24SummaryStatistics;
import dita.recall24.reporter.tabular.TabularReport.Aggregation;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
@Log4j2
class InterviewXmlParserIntegrationTest extends DitaGdSurveyIntegrationTest {

    @Test
    void parsingFromBlobStore() throws InterruptedException, ExecutionException {
        var aggregation = Aggregation.NONE;

        var tabularReport = tabularReport(aggregation, 4);

        log.info("write report");
        var xlsxFile = new File("d:/tmp/_scratch/report-aggr-" + aggregation.name().toLowerCase() + ".xlsx");
        tabularReport.reportXlsx(xlsxFile);
        XlsxUtils.launchViewerAndWaitFor(xlsxFile);

//      var todoReporter = new TodoReportUtils.TodoReporter(systemId, nutMapping, interviewSet);
//      todoReporter.report(
//              DataSink.ofFile(new File("d:/tmp/_scratch/mapping-todos.txt")));

        log.info("collect report stats");
        var stats = new Recall24SummaryStatistics();
        //var recordProcessor = new RecordProcessor(stats, tabularReport.systemId(), tabularReport.nutMapping());

        tabularReport.interviewSet().streamDepthFirst()
            .forEach((final RecallNode24 node)->{
                stats.accept(node);
//                switch(node) {
//                    case Record24.Consumption cRec -> recordProcessor.accept(cRec);
//                    default -> {}
//                }
            });

        System.out.println("=== STATS ===");
        System.out.println(stats.formatted());
        System.out.println("=============");
    }

//    record RecordProcessor(
//            Recall24SummaryStatistics stats,
//            SystemId systemId,
//            QualifiedMap nutMapping)
//    implements Consumer<Record24.Consumption> {
//        @Override
//        public void accept(final Record24.Consumption rec) {
//            var mapKey = rec.asQualifiedMapKey();
//            var mapEntry = nutMapping.lookupEntry(mapKey);
//            if(mapEntry.isPresent()) {
//                stats.consumptionStats().mappedCount().increment();
//            } else {
//                stats.consumptionStats().collectMappingTodo(new MappingTodo(mapKey, similar(mapKey)));
//                System.out.printf("unmapped: %s (%s)%n", rec.name(), mapKey);
//            }
//        }
//        private Can<QualifiedMapEntry> similar(final QualifiedMapKey mapKey) {
//            return nutMapping.streamEntriesHavingSource(mapKey.source())
//                    .collect(Can.toCan())
//                    .distinct();
//        }
//    }

}
