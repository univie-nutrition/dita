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
import org.apache.causeway.commons.internal.base._Refs;
import org.apache.causeway.commons.internal.base._Strings;

import dita.commons.format.FormatUtils;
import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.recall24.api.Record24.Type;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.Node24;
import io.github.causewaystuff.treeview.applib.factories.TreeNodeFactory;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class InterviewXmlParserIntegrationTest extends DitaGdSurveyIntegrationTest {

    @Test
    void parsingFromBlobStore() {

        var ingredientProcessor = new IngredientProcessor(new Stats(), "GD-AT20240507", loadNutMapping());

        loadAndStreamInterviews(null)
        //.limit(1)
        .forEach(interviewSet24->{
            var root = TreeNodeFactory.wrap(Node24.class, interviewSet24, factoryService);
            root.streamDepthFirst()
                .map(TreeNode::getValue)
                .forEach((Node24 node)->{
                    switch(node) {
                    case Ingredient24 ingr -> ingredientProcessor.accept(ingr);
                    default -> {}
                    }
                });
        });

        System.err.printf("%s%n", ingredientProcessor.stats);

    }

    record Stats(
            _Refs.IntReference ingredientCount,
            _Refs.IntReference unmappedCount) {
        Stats() {
            this(_Refs.intRef(0), _Refs.intRef(0));
        }
    }

    record IngredientProcessor(Stats stats, String systemId, QualifiedMap nutMapping)
    implements Consumer<Ingredient24> {
        @Override
        public void accept(final Ingredient24 ingr) {
            stats.ingredientCount().incAndGet();
            var mapKey = extractQualifiedMapKey(ingr);
            var mapEntry = nutMapping.lookupEntry(mapKey);
            if(!mapEntry.isPresent()) {
                stats.unmappedCount().incAndGet();
                System.err.printf("unmapped ingr: %s (%s)%n", ingr.name(), mapKey);
            }

        }
        // -- HELPER
        private QualifiedMap.QualifiedMapKey extractQualifiedMapKey(final Ingredient24 ingr) {
            var source = new SemanticIdentifier(systemId, toPrefixedSourceKey(ingr.sid(), ingr.parentRecord().type()));
            return new QualifiedMapKey(source,
                    SemanticIdentifierSet.ofStream(_Strings.splitThenStream(ingr.facetSids(), ",")
                            .map(facetId->new SemanticIdentifier(systemId, facetId))));
        }
        //TODO replace with a model transformation step
        private String toPrefixedSourceKey(final String key, final Type type) {
            return switch (type) {
                case FOOD -> "N" + FormatUtils.noLeadingZeros(key);
                default -> key;
            };
        }
    }

}
