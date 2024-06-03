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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.commons.collections.Can;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import dita.commons.types.IntRef;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

/**
 * Represents a (single) 24h recall interview event.
 */
public sealed interface Interview24 extends RecallNode24
permits Interview24.Dto {

    /**
     * Interview date.
     */
    LocalDate interviewDate();

    /**
     *  Each respondent can have one ore more interviews within the context of a specific survey.
     *  This ordinal denotes the n-th interview (when ordered by interview date).
     */
    int interviewOrdinal();

    // -- PARENT / CHILD

    /**
     * Parent respondent of this interview.
     */
    Respondent24 parentRespondent();

    /**
     * Respondent supplementary data for this interview.
     */
    RespondentSupplementaryData24 respondentSupplementaryData();

    /**
     * The meals of this interview.
     */
    Can<? extends Meal24> meals();

    // -- SHORTCUTS

    /**
     * Gets the respondent's alias.
     * @see Respondent24#alias()
     */
    default String respondentAlias() {
        return parentRespondent().alias();
    }

    // -- DTO

    public record Dto(

            /**
             * Respondent of this interview.
             */
            @JsonIgnore
            ObjectRef<Respondent24.Dto> parentRespondentRef,

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
            RespondentSupplementaryData24.Dto respondentSupplementaryData,

            /**
             * The meals of this interview.
             */
            @TreeSubNodes
            Can<Meal24.Dto> meals

            ) implements Interview24 {

        public static Dto of(
                final Respondent24.Dto respondent,
                /**
                 * Interview date.
                 */
                final LocalDate interviewDate,
                /**
                 * Respondent meta-data for this interview.
                 */
                final RespondentSupplementaryData24.Dto respondentSupplementaryData,
                /**
                 * The meals of this interview.
                 */
                final Can<Meal24.Dto> meals) {
            var interview = new Dto(new ObjectRef<>(respondent), interviewDate, IntRef.of(-1), respondentSupplementaryData, meals);
            respondentSupplementaryData.parentInterviewRef().setValue(interview);
            meals.forEach(meal24->meal24.parentInterviewRef().setValue(interview));
            return interview;
        }

        @Override
        public Respondent24.Dto parentRespondent() {
            return parentRespondentRef.getValue();
        }

        @Override
        public int interviewOrdinal() {
            return interviewOrdinalRef().getValue();
        }

        public boolean matchesRespondent(final Respondent24.Dto candidate) {
            return Objects.equals(parentRespondent(), candidate);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder().respondent(parentRespondent()).interviewDate(interviewDate)
                    .respondentSupplementaryData(respondentSupplementaryData);
        }

    }

    // -- BUILDER

    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<Dto> {
        Respondent24.Dto respondent;
        LocalDate interviewDate;
        RespondentSupplementaryData24.Dto respondentSupplementaryData;
        final List<Meal24.Dto> meals = new ArrayList<>();
        @Override
        public Dto build() {
            return Dto.of(respondent, interviewDate, respondentSupplementaryData, Can.ofCollection(meals));
        }
    }

}