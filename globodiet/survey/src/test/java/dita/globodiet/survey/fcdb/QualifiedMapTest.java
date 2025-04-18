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
package dita.globodiet.survey.fcdb;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;

import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class QualifiedMapTest extends DitaGdSurveyIntegrationTest {

    @Test
    void loading() throws IOException {
        var nutMapping = loadNutMapping();
        assertEquals(42942, nutMapping.entryCount());
    }
}
