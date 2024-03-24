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

import org.causewaystuff.blobstore.applib.BlobStore;
import org.causewaystuff.commons.types.NamedPath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.commons.io.DataSource;

import dita.globodiet.survey.DitaModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import dita.globodiet.survey.util.InterviewUtils;

@SpringBootTest(classes = {
        DitaModuleGdSurvey.class
        })
@PrivateDataTest
class InterviewXmlParserTest {

    @Inject @Qualifier("survey") BlobStore surveyBlobStore;
    private InterviewXmlParser parser = new InterviewXmlParser();

    @Test
    void parsingFromBlobStore() {
        assertNotNull(surveyBlobStore);

        InterviewUtils.streamSources(surveyBlobStore, NamedPath.empty(), true)
        .limit(1)
        .map(InterviewUtils::parse)
        .forEach(iSet->{
            System.err.printf("%s%n", iSet.toJson());
        });
    }

    @Test
    void parsingSample() {
        var xml = InterviewSampler.sampleXml();
        var iSet = parser.parse(DataSource.ofStringUtf8(xml));
        //System.err.printf("%s%n", iSet.toJson());
    }

}
