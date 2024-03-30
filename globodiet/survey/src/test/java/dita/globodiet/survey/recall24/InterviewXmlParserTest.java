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

import org.causewaystuff.treeview.applib.factories.TreeNodeFactory;
import org.causewaystuff.treeview.metamodel.facets.TreeNodeFacetFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.core.metamodel._testing.MetaModelContext_forTesting;
import org.apache.causeway.core.metamodel.context.MetaModelContext;

class InterviewXmlParserTest {

    private InterviewXmlParser parser = new InterviewXmlParser();
    private MetaModelContext mmc;

    @BeforeEach
    void setUp() {
        mmc = MetaModelContext_forTesting.builder()
            .refiners(Can.of(TreeNodeFacetFactory::new))
            .build();
        assertNotNull(MetaModelContext.instanceNullable());
    }

    @Test
    void parsingSample() {
        var xml = InterviewSampler.sampleXml();
        var interviewSet24 = parser.parse(DataSource.ofStringUtf8(xml));

        TreeNode<Object> root = TreeNodeFactory.wrap(interviewSet24, mmc.getFactoryService());

        root.iteratorDepthFirst()
            .forEachRemaining(node->System.err.printf("node: %s%n", node.getValue()));

        //debug
        //System.err.printf("%s%n", interviewSet24.toJson());
    }


}
