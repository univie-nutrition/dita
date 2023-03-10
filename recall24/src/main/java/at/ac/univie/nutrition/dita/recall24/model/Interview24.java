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

import java.time.LocalDate;

import org.apache.causeway.commons.collections.Can;

import at.ac.univie.nutrition.dita.commons.types.IntRef;

public record Interview24(

        /**
         * Respondent of this interview.
         */
        Respondent24 respondent,

        /**
         * Interview date.
         */
        LocalDate interviewDate,

        /**
         *  Each respondent can have one ore more interviews within the context of a specific survey.
         *  This ordinal denotes the n-th interview (when ordered by interview date).
         */
        IntRef interviewOrdinalRef,

        /**
         * Respondent meta-data for this interview.
         */
        RespondentMetaData24 respondentMetaData,

        /**
         * The meals of this interview.
         */
        Can<Meal24> meals

        ) implements at.ac.univie.nutrition.dita.recall24.api.Interview24 {

    @Override
    public int interviewOrdinal() {
        return interviewOrdinalRef().getValue();
    }

}
