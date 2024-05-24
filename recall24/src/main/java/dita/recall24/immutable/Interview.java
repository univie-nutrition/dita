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
package dita.recall24.immutable;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.commons.collections.Can;

import dita.commons.types.IntRef;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

public record Interview(

        /**
         * Respondent of this interview.
         */
        @JsonIgnore
        ObjectRef<Respondent> parentRespondentRef,

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
        RespondentSupplementaryData respondentMetaData,

        /**
         * The meals of this interview.
         */
        @TreeSubNodes
        Can<Meal> meals

        ) implements dita.recall24.api.Interview24, RecallNode {

    public static Interview of(
            final Respondent respondent,
            /**
             * Interview date.
             */
            final LocalDate interviewDate,
            /**
             * Respondent meta-data for this interview.
             */
            final RespondentSupplementaryData respondentMetaData,
            /**
             * The meals of this interview.
             */
            final Can<Meal> meals) {
        var interview = new Interview(new ObjectRef<>(respondent), interviewDate, IntRef.of(-1), respondentMetaData, meals);
        respondentMetaData.parentInterviewRef().setValue(interview);
        meals.forEach(meal24->meal24.parentInterviewRef().setValue(interview));
        return interview;
    }

    @Override
    public Respondent parentRespondent() {
        return parentRespondentRef.getValue();
    }

    @Override
    public int interviewOrdinal() {
        return interviewOrdinalRef().getValue();
    }

    public boolean matchesRespondent(final Respondent candidate) {
        return Objects.equals(parentRespondent(), candidate);
    }

}