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
package dita.globodiet.survey.view;

import java.util.Optional;

import org.springframework.util.StringUtils;

import dita.globodiet.survey.dom.RespondentFilter;
import dita.globodiet.survey.dom.Survey;

/// defines what to view in an interview inspection master/detail view
public record InspectionContext(
    String surveyCode,
    String respondentFilterCode
    ) {

    // -- FACTORIES

    public static InspectionContext empty() {
        return new InspectionContext("", "");
    }

    public static InspectionContext of(
        final Survey.SecondaryKey secondaryKey,
        final Optional<RespondentFilter.SecondaryKey> respFilterOptional) {
        return new InspectionContext(
            secondaryKey.code(),
            respFilterOptional
                .map(RespondentFilter.SecondaryKey::name)
                .orElse(""));
    }

    // --

    public Optional<Survey.SecondaryKey> surveySecondaryKey() {
        return StringUtils.hasLength(surveyCode)
            ? Optional.of(new Survey.SecondaryKey(surveyCode))
            : Optional.empty();
    }

    public Optional<RespondentFilter.SecondaryKey> respondentFilterSecondaryKey() {
        return StringUtils.hasLength(surveyCode)
                && StringUtils.hasLength(respondentFilterCode)
            ? Optional.of(new RespondentFilter.SecondaryKey(surveyCode, respondentFilterCode))
            : Optional.empty();
    }


}
