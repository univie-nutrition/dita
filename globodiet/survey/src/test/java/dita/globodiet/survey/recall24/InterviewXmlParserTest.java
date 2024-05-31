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

import org.apache.causeway.commons.io.DataSource;

import dita.globodiet.survey.utils.ApprovalTestOptions;
import io.github.causewaystuff.commons.compression.SevenZUtils;

class InterviewXmlParserTest {

    @Test
    @UseReporter(DiffReporter.class)
    void parsingSample() {
        var xml = InterviewSampler.sampleXml();
        var interviewSet24 = InterviewXmlParser.parse(DataSource.ofStringUtf8(xml), null);

        Approvals.verify(interviewSet24.toYaml(), ApprovalTestOptions.yamlOptions());
    }
    
    @Test
    @UseReporter(DiffReporter.class)
    void parsingRefComposites() {
        var ds = SevenZUtils.decompress(DataSource.ofInputStreamEagerly(
                InterviewXmlParserTest.class.getResourceAsStream("Ref_Composites_202405281318.7z")));
        
        var interviewSet24 = InterviewXmlParser.parse(ds, null);
        Approvals.verify(interviewSet24.toYaml(), ApprovalTestOptions.yamlOptions());
    }
    
    @Test
    @UseReporter(DiffReporter.class)
    void parsingRefFatSouceSweetener() {
        var ds = SevenZUtils.decompress(DataSource.ofInputStreamEagerly(
                InterviewXmlParserTest.class.getResourceAsStream("Ref_FatSouceSweetener_202405300942.7z")));
        
        var interviewSet24 = InterviewXmlParser.parse(ds, null);
        Approvals.verify(interviewSet24.toYaml(), ApprovalTestOptions.yamlOptions());
    }

}
