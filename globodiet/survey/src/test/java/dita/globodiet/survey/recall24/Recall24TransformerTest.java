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

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.testing.ApprovalTestOptions;

class Recall24TransformerTest {

    private SystemId systemId = new SystemId("at.gd", "2.0");

    record NoopTransformer() implements Transfomer {
        @Override
        public <T extends RecallNode24> T transform(T node) {
            return node;
        }
    }

    @Test
    @UseReporter(DiffReporter.class)
    void parsingSample() {
        var interviewSet24 = InterviewSamples.SAMPLES.asInterviewSet(systemId, null);
        var transformed = interviewSet24.transform(new NoopTransformer());
        Approvals.verify(transformed.toYaml(), ApprovalTestOptions.yamlOptions());
    }

    @Test
    @UseReporter(DiffReporter.class)
    void parsingRefComposites() {
        var interviewSet24 = InterviewSamples.COMPOSITES.asInterviewSet(systemId, null);
        var transformed = interviewSet24.transform(new NoopTransformer());
        Approvals.verify(transformed.toYaml(), ApprovalTestOptions.yamlOptions());
    }

    @Test
    @UseReporter(DiffReporter.class)
    void parsingRefFatSouceSweetener() {
        var interviewSet24 = InterviewSamples.FAT_SOUCE_SWEETENERS.asInterviewSet(systemId, null);
        var transformed = interviewSet24.transform(new NoopTransformer());
        Approvals.verify(transformed.toYaml(), ApprovalTestOptions.yamlOptions());
    }
}
