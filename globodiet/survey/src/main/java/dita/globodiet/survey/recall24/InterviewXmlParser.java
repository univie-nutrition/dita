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

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.JaxbUtils;

import dita.recall24.dto.InterviewSetDto;
import lombok.NonNull;

/**
 * Parses GloboDiet XML Interview files into recall24 data structures.
 */
public class InterviewXmlParser {

    public Try<InterviewSetDto> tryParse(final @NonNull DataSource source) {
        return Try.call(()->parse(source));
    }

    public InterviewSetDto parse(final @NonNull DataSource source) {
        var iSet = new InterviewSetDto();

        var dto = JaxbUtils.tryRead(_Dtos.Itv.class, source)
                .valueAsNonNullElseFail();

        return iSet;
    }

}
