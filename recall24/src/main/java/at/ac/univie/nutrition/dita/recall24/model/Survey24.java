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
package at.ac.univie.nutrition.dita.recall24.model;

import java.util.List;

import at.ac.univie.nutrition.dita.recall24.api.Interview24;
import at.ac.univie.nutrition.dita.recall24.api.Respondent24;

/**
 * A named survey object, identified by a key,
 * that holds a collective of respondents and their individual 24h recall interviews.
 */
public record Survey24(
        /**
         * Survey identifier.
         */
        String key,

        /**
         * Human readable survey name.
         */
        String name,

        // -- CHILDREN

        /**
         * Respondents that belong to this survey.
         */
        List<Respondent24> respondents,


        /**
         * Interviews that belong to this survey.
         */
        List<Interview24> interviews

        ) {


    /**
     * Factory for when the collective of respondents and their individual 24h recall interviews,
     * is made up of multiple {@link InterviewSet24}s.
     */
    public static Survey24 of(
            final String surveyKey,
            final String surveyName,
            final List<InterviewSet24> interviewSets) {
        return new Survey24(surveyKey, surveyName, null, null); //FIXME add merger
    }

}
