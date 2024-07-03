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

import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSink;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.qmap.QualifiedMapEntry;
import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.util.Recall24DtoUtils;
import dita.recall24.dto.util.Recall24SummaryStatistics;
import dita.recall24.dto.util.Recall24SummaryStatistics.MappingTodo;
import dita.recall24.reporter.todo.TodoReportUtils;
import io.github.causewaystuff.commons.base.types.NamedPath;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class InterviewXmlParserIntegrationTest extends DitaGdSurveyIntegrationTest {

    @Test
    void parsingFromBlobStore() {

        final var systemId = "GD-AT20240507";

        var nutMapping = loadNutMapping();
        var correction = loadCorrection();

        var stats = new Recall24SummaryStatistics();
        var recordProcessor = new RecordProcessor(stats, systemId, nutMapping);

        var interviewSet = InterviewUtils
                .interviewSetFromBlobStrore(NamedPath.of("at-national-2026"), surveyBlobStore, correction, null)
                //.transform(new NutriDbConverters.ToNutriDbTransfomer())
                ;

        var todoReporter = new TodoReportUtils.TodoReporter(systemId, nutMapping, interviewSet);
        todoReporter.report(
                DataSink.ofFile(new File("d:/tmp/_scratch/mapping-todos.txt")));

        Recall24DtoUtils.wrapAsTreeNode(interviewSet)
            .streamDepthFirst()
            .map(TreeNode::getValue)
            .forEach((RecallNode24 node)->{
                stats.accept((dita.recall24.dto.RecallNode24) node);
                switch(node) {
                case Record24.Consumption cRec -> recordProcessor.accept(cRec);
                default -> {}
                }
            });

        System.err.println("=== STATS ===");
        System.err.println(stats.formatted());
        System.err.println("=============");
    }

    record RecordProcessor(
            Recall24SummaryStatistics stats,
            String systemId,
            QualifiedMap nutMapping)
    implements Consumer<Record24.Consumption> {
        @Override
        public void accept(final Record24.Consumption rec) {
            var mapKey = rec.asFoodConsumption(systemId).qualifiedMapKey();
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
