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
package dita.globodiet.survey.dom;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.internal.context._Context;

import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier.SystemId;
import io.github.causewaystuff.commons.base.util.RuntimeUtils;

@UtilityClass
public class Surveys {

    // -- SYSTEM ID

    private Map<String, SystemId> systemIdBySurveyCode = new ConcurrentHashMap<>();

    /**
     * System ID part of semantic identifiers for given survey secondary key.
     * e.g. {@code at.gd/2.0}
     */
    public SystemId systemId(final Survey.SecondaryKey surveyKey) {
        return systemIdBySurveyCode.computeIfAbsent(surveyKey.code(), _->systemId(survey(surveyKey)));
    }

    /**
     * System ID part of semantic identifiers for given survey.
     * e.g. {@code at.gd/2.0}
     */
    public SystemId systemId(@Nullable final Survey survey) {
        return Optional.ofNullable(survey)
            .map(Survey::getSystemId)
            .map(SystemId::parse)
            .orElseGet(()->new SystemId("undefined"));
    }

    private Survey survey(final Survey.SecondaryKey surveyKey) {
        var repo = RuntimeUtils.getRepositoryService();
        var survey = repo.firstMatch(Survey.class, s->surveyKey.code().equals(s.getCode()));
        return survey
            // JUnit support
            .orElseGet(()->_Context.lookup(Survey.class).orElse(null));
    }

}
