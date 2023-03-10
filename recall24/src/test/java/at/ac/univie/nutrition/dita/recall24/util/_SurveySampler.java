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
package at.ac.univie.nutrition.dita.recall24.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.DataSource;

import at.ac.univie.nutrition.dita.recall24.dto.InterviewSetDto;
import lombok.Getter;
import lombok.val;
import lombok.experimental.Accessors;

@Getter @Accessors(fluent=true)
class _SurveySampler {

    private final Blob survey;

    _SurveySampler() {
        this.survey = getSurvey24SampleAsBlob("sample-survey.xml.zip");
    }

    InterviewSetDto asInterviewSet(final Blob blob) {
        val survey24 = Recall24DtoUtils.tryUnzip(blob)
                .valueAsNonNullElseFail();
        assertNotNull(survey24);
        return survey24;
    }

    // -- HELPER

    private Blob getSurvey24SampleAsBlob(final String fileName) {
        val bytes = DataSource.ofResource(this.getClass(), fileName).bytes();
        assertNotNull(bytes);
        assertTrue(bytes.length>0);
        return Blob.of(fileName, CommonMimeType.ZIP, bytes);
    }

}
