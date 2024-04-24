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

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.testing.integtestsupport.applib.CausewayIntegrationTestAbstract;

import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.globodiet.survey.util.InterviewUtils;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.treeview.applib.factories.TreeNodeFactory;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class InterviewXmlParserIntegrationTest extends CausewayIntegrationTestAbstract {

    @Inject @Qualifier("survey") BlobStore surveyBlobStore;
    @Inject FactoryService factoryService;

    @Test
    void parsingFromBlobStore() {
        assertNotNull(surveyBlobStore);

        InterviewUtils.streamSources(surveyBlobStore, NamedPath.empty(), true)
        .limit(1)
        .map(ds->InterviewXmlParser.parse(ds, null))
        .forEach(interviewSet24->{
            System.err.printf("%s%n", interviewSet24.toYaml());

            TreeNode<Object> root = TreeNodeFactory.wrap(interviewSet24, factoryService);

            root.iteratorDepthFirst()
                .forEachRemaining(node->System.err.printf("node: %s%n", node.getValue()));
        });
    }

}