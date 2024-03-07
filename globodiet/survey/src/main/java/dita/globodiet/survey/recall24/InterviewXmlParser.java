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

import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.JaxbUtils;

import lombok.NonNull;

import dita.recall24.model.Interview24;
import dita.recall24.model.InterviewSet24;
import dita.recall24.model.Respondent24;

/**
 * Parses GloboDiet XML Interview files into recall24 data structures.
 */
public class InterviewXmlParser {

    public Try<InterviewSet24> tryParse(final @NonNull DataSource source) {
        return Try.call(()->parse(source));
    }

    public InterviewSet24 parse(final @NonNull DataSource source) {
        var dto = JaxbUtils.tryRead(_Dtos.Itv.class, source)
                .valueAsNonNullElseFail();
        return createFromDto(dto);
    }

    public InterviewSet24 parse(final Clob interviewSource) {
        var dto = JaxbUtils.tryRead(_Dtos.Itv.class, interviewSource.getChars().toString())
                .valueAsNonNullElseFail();
        return createFromDto(dto);
    }

    private InterviewSet24 createFromDto(final @NonNull _Dtos.Itv dto) {

        /* Interviews that belong to this survey. */
        final Can<Interview24> interviews =
            dto.getInterviews().stream()
                .map(_Dtos.Interview::toInterview24)
                .collect(Can.toCan());

        /* Respondents collected from interviews. */
        final Can<Respondent24> respondents =
            interviews.stream()
                .map(Interview24::respondent)
                .collect(Can.toCan());

        return InterviewSet24.of(respondents, interviews);
    }


}
