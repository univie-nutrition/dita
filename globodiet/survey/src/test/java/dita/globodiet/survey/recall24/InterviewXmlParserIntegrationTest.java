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

import dita.commons.qmap.QualifiedMap;
import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.Node24;
import dita.recall24.util.Recall24ModelUtils;
import dita.recall24.util.Recall24SummaryStatistics;
import io.github.causewaystuff.commons.base.types.NamedPath;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class InterviewXmlParserIntegrationTest extends DitaGdSurveyIntegrationTest {

    @Test
    void parsingFromBlobStore() {

        var stats = new Recall24SummaryStatistics();
        var ingredientProcessor = new IngredientProcessor(stats, "GD-AT20240507", loadNutMapping());

        loadAndStreamInterviews(NamedPath.of("at-national-2026"), null)
        //.limit(1)
        .map(nutriDbTransfomer())
        .map(interviewSet24 -> Recall24ModelUtils.wrapAsTreeNode(interviewSet24, factoryService))
        .forEach(rootNode->{
            rootNode
                .streamDepthFirst()
                .map(TreeNode::getValue)
                .forEach((Node24 node)->{
                    stats.accept((dita.recall24.api.Node24) node);
                    switch(node) {
                    case Ingredient24 ingr -> ingredientProcessor.accept(ingr);
                    default -> {}
                    }
                });
        });

        System.err.println("=== STATS ===");
        System.err.println(stats.formatted());
        System.err.println("=============");
    }

    record IngredientProcessor(Recall24SummaryStatistics stats, String systemId, QualifiedMap nutMapping)
    implements Consumer<Ingredient24> {
        @Override
        public void accept(final Ingredient24 ingr) {
            var mapKey = ingr.qualifiedMapKey(systemId);
            var mapEntry = nutMapping.lookupEntry(mapKey);
            if(mapEntry.isPresent()) {
                stats.ingredientStats().mappedCount().increment();
            } else {
                System.err.printf("unmapped ingr: %s (%s)%n", ingr.name(), mapKey);
            }
        }
    }

}
