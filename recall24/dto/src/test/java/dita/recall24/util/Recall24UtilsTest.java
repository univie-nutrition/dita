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
package dita.recall24.util;

@Deprecated
class Recall24UtilsTest {

    /*
    static _SurveySampler sampler;

    @BeforeAll
    static void setup() {
        sampler = new _SurveySampler();
    }

    @Test
    void jaxbRoundtripOnInterviewSetDto() {

        var interviewSet = sampler.asInterviewSet(sampler.survey());

        var dataPeer = DataPeer.inMemory();

        Recall24DtoUtils.tryWriteSurvey(interviewSet, dataPeer)
            .ifFailureFail();

        var survey24AfterRoundtrip = Recall24DtoUtils.tryReadSurvey(dataPeer)
            .valueAsNonNullElseFail();

        assertEquals(interviewSet, survey24AfterRoundtrip);
    }

    @Test
    void roundtripOnZippedBlobOfInterviewSetDto() {

        var blob = sampler.survey();

        var interviewSet = Recall24DtoUtils.tryUnzip(blob)
            .valueAsNonNullElseFail();

        var blobAfterRoundtrip = Recall24DtoUtils.tryZip("interview-set", interviewSet)
            .valueAsNonNullElseFail();

        var interviewSetAfterRoundtrip = Recall24DtoUtils.tryUnzip(blobAfterRoundtrip)
            .valueAsNonNullElseFail();

        assertEquals(interviewSet, interviewSetAfterRoundtrip);
    }

    @Test
    void roundtripOnDtoToModelConversion() {
        var interviewSetDto = sampler.asInterviewSet(sampler.survey());
        assertEquals(interviewSetDto,
                Recall24DtoUtils.toDto(Recall24DtoUtils.fromDto(interviewSetDto)));
    }

    @Test
    void roundtripOnModelSplitAndJoin() {
        var interviewSet24 = Recall24DtoUtils.fromDto(sampler.asInterviewSet(sampler.survey()))
                .normalized();
        var biPartition = interviewSet24.split(2);

        {
            // sanity checks
            var partA = biPartition.getElseFail(0)
                    .normalized();
            var partB = biPartition.getElseFail(1)
                    .normalized();
            assertEquals(interviewSet24.respondents().size(),
                    partA.respondents().size() + partB.respondents().size());
            assertEquals(interviewSet24.interviewCount(),
                    partA.interviewCount() + partB.interviewCount());
        }

        var joined = InterviewSet24.Dto.join(biPartition, null)
                .normalized();

        {
            // sanity checks
            assertEquals(interviewSet24.respondents().size(),
                    joined.respondents().size());
            assertEquals(interviewSet24.interviewCount(),
                    joined.interviewCount());
        }

        assertEquals(interviewSet24.toString(), joined.toString());
        assertEquals(interviewSet24.hashCode(), joined.hashCode());
        assertEquals(interviewSet24, joined);
    }
*/
}
