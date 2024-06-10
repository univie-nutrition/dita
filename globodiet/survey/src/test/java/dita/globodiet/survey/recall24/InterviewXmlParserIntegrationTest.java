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

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.commons.collections.Can;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.qmap.QualifiedMapEntry;
import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.util.Recall24DtoUtils;
import dita.recall24.dto.util.Recall24SummaryStatistics;
import dita.recall24.dto.util.Recall24SummaryStatistics.MappingTodo;
import io.github.causewaystuff.commons.base.types.NamedPath;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class InterviewXmlParserIntegrationTest extends DitaGdSurveyIntegrationTest {

    @Test
    void parsingFromBlobStore() {

        var stats = new Recall24SummaryStatistics();
        var recordProcessor = new RecordProcessor(stats, "GD-AT20240507", loadNutMapping());

        var correction = Correction24.tryFromYaml("""
                respondents:
                - alias: "EB0070"
                  newAlias: "EB_0070"
                - alias: "EB:0029"
                  newAlias: "EB_0029"
                - alias: "EB_0061"
                  dateOfBirth: "1977-04-24"
                - alias: "EB_0058"
                  dateOfBirth: "1992-08-28"
                - alias: "EB_0038"
                  dateOfBirth: "2002-09-21"
                - alias: "EB_0093"
                  sex: FEMALE
                """)
                .valueAsNullableElseFail();


        loadAndStreamInterviews(NamedPath.of("at-national-2026"), correction, null)
        //.limit(1)
        .map(nutriDbTransfomer())
        .map(interviewSet24 -> Recall24DtoUtils.wrapAsTreeNode(interviewSet24, factoryService))
        .forEach(rootNode->{
            rootNode
                .streamDepthFirst()
                .map(TreeNode::getValue)
                .forEach((RecallNode24 node)->{
                    stats.accept((dita.recall24.dto.RecallNode24) node);
                    switch(node) {
                    case Record24.Consumption cRec -> recordProcessor.accept(cRec);
                    default -> {}
                    }
                });
        });

        System.err.println(stats.consumptionStats()
            .reportMappingTodos());

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
