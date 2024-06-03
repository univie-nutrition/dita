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
package dita.recall24.dto;

import org.apache.causeway.commons.collections.Can;

/**
 * Holds a collective of respondents and their individual 24h recall interviews.
 */
public interface Survey24 {

    /**
     * Survey identifier.
     */
    String key();

    /**
     * Human readable survey name.
     */
    String name();

    /**
     * Respondents that belong to this survey.
     */
    Can<Respondent24.Dto> respondents();

    /**
     * Interviews that belong to this survey.
     */
    Can<Interview24.Dto> interviews();

    // -- DTO

    /**
     * A named survey object, identified by a key,
     * that holds a collective of respondents and their individual 24h recall interviews.
     */
    public record Dto(
            /**
             * Survey identifier.
             */
            String key,

            /**
             * Human readable survey name.
             */
            String name,

            InterviewSet24.Dto interviewSet

            ) implements Survey24 {


        /**
         * Factory for when the collective of respondents and their individual 24h recall interviews,
         * is made up of multiple {@link InterviewSet24}s.
         */
        public static Dto of(
                final String surveyKey,
                final String surveyName,
                final InterviewSet24.Dto interviewSet) {
            return new Dto(surveyKey, surveyName, interviewSet);
        }

        @Override
        public Can<Respondent24.Dto> respondents() {
            return interviewSet.respondents();
        }

        @Override
        public Can<Interview24.Dto> interviews() {
            return interviewSet.streamInterviews().collect(Can.toCan());
        }

    }

}