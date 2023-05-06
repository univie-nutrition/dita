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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.causeway.commons.io.DataPeer;

import dita.recall24.model.InterviewSet24;
import dita.recall24.util.Recall24DtoUtils;
import dita.recall24.util.Recall24ModelUtils;
import lombok.val;

class Recall24UtilsTest {

    static _SurveySampler sampler;

    @BeforeAll
    static void setup() {
        sampler = new _SurveySampler();
    }

    @Test
    void jaxbRoundtripOnInterviewSetDto() {

        val interviewSet = sampler.asInterviewSet(sampler.survey());

        val dataPeer = DataPeer.inMemory();

        Recall24DtoUtils.tryWriteSurvey(interviewSet, dataPeer)
            .ifFailureFail();

        val survey24AfterRoundtrip = Recall24DtoUtils.tryReadSurvey(dataPeer)
            .valueAsNonNullElseFail();

        assertEquals(interviewSet, survey24AfterRoundtrip);
    }

    @Test
    void roundtripOnZippedBlobOfInterviewSetDto() {

        val blob = sampler.survey();

        val interviewSet = Recall24DtoUtils.tryUnzip(blob)
            .valueAsNonNullElseFail();

        val blobAfterRoundtrip = Recall24DtoUtils.tryZip("interview-set", interviewSet)
            .valueAsNonNullElseFail();

        val interviewSetAfterRoundtrip = Recall24DtoUtils.tryUnzip(blobAfterRoundtrip)
            .valueAsNonNullElseFail();

        assertEquals(interviewSet, interviewSetAfterRoundtrip);
    }

    @Test
    void roundtripOnDtoToModelConversion() {
        val interviewSetDto = sampler.asInterviewSet(sampler.survey());
        assertEquals(interviewSetDto,
                Recall24ModelUtils.toDto(Recall24ModelUtils.fromDto(interviewSetDto)));
    }

    @Test
    void roundtripOnModelSplitAndJoin() {
        val interviewSet24 = Recall24ModelUtils.fromDto(sampler.asInterviewSet(sampler.survey()))
                .normalized();
        val biPartition = interviewSet24.split(2);

        {
            // sanity checks
            val partA = biPartition.getElseFail(0)
                    .normalized();
            val partB = biPartition.getElseFail(1)
                    .normalized();
            assertEquals(interviewSet24.respondents().size(),
                    partA.respondents().size() + partB.respondents().size());
            assertEquals(interviewSet24.interviews().size(),
                    partA.interviews().size() + partB.interviews().size());
        }

        val joined = InterviewSet24.join(biPartition)
                .normalized();

        {
            // sanity checks
            assertEquals(interviewSet24.respondents().size(),
                    joined.respondents().size());
            assertEquals(interviewSet24.interviews().size(),
                    joined.interviews().size());
        }

        assertEquals(interviewSet24.toString(), joined.toString());
        assertEquals(interviewSet24.hashCode(), joined.hashCode());
        assertEquals(interviewSet24, joined);
    }


}
