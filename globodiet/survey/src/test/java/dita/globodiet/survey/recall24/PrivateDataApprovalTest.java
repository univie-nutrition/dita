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

import java.util.concurrent.ExecutionException;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.globodiet.survey.utils.ApprovalTestOptions;
import dita.recall24.reporter.tabular.TabularReport.Aggregation;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
@Log4j2
class PrivateDataApprovalTest extends DitaGdSurveyIntegrationTest {

    @Test
    @UseReporter(DiffReporter.class)
    void parsingFromBlobStore() throws InterruptedException, ExecutionException {
        var tabularReport = tabularReport(Aggregation.NONE, 4);
        Approvals.verify(tabularReport.reportTsv(), ApprovalTestOptions.tsvOptions());
    }

}
