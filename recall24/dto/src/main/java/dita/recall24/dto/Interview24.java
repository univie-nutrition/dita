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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public record Interview24 (

            /**
             * Respondent of this interview.
             */
            @JsonIgnore
            ObjectRef<Respondent24> parentRespondentRef,

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
            RespondentSupplementaryData24 respondentSupplementaryData,

            /**
             * The meals of this interview.
             */
            @TreeSubNodes
            Can<Meal24> meals,

            /**
             * mutable
             */
            Map<String, Annotation> annotations
            ) implements RecallNode24, RuntimeAnnotated {

    public static Interview24 of(
            final Respondent24 respondent,
            /**
             * Interview date.
             */
            final LocalDate interviewDate,
            /**
             * Respondent meta-data for this interview.
             */
            final RespondentSupplementaryData24 respondentSupplementaryData,
            /**
             * The meals of this interview.
             */
            final Can<Meal24> meals) {
        var interview = new Interview24(new ObjectRef<>(respondent), interviewDate, IntRef.of(-1),
            respondentSupplementaryData, meals, new HashMap<>());
        respondentSupplementaryData.parentInterviewRef().setValue(interview);
        meals.forEach(meal24->meal24.parentInterviewRef().setValue(interview));
        return interview;
    }

    // -- SHORTCUTS

    /**
     * Gets the respondent's alias.
     * @see Respondent24#alias()
     */
    public String respondentAlias() {
        return parentRespondent().alias();
    }

    public Respondent24 parentRespondent() {
        return parentRespondentRef.getValue();
    }

    public int interviewOrdinal() {
        return interviewOrdinalRef().getValue();
    }

    public boolean matchesRespondent(final Respondent24 candidate) {
        return Objects.equals(parentRespondent(), candidate);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder24<Interview24> asBuilder() {
        return Builder.of(this);
    }

    // -- BUILDER

    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<Interview24> {
        Respondent24 respondent;
        LocalDate interviewDate;
        RespondentSupplementaryData24 respondentSupplementaryData;
        final List<Meal24> meals = new ArrayList<>();
        final List<Annotation> annotations = new ArrayList<>();

        static Builder of(final Interview24 dto) {
            var builder = new Builder().respondent(dto.parentRespondent()).interviewDate(dto.interviewDate)
                    .respondentSupplementaryData(dto.respondentSupplementaryData());
            dto.meals.forEach(builder.meals::add);
            dto.annotations().values().forEach(builder.annotations::add);
            return builder;
        }

        @Override
        public Interview24 build() {
            var dto = Interview24.of(respondent, interviewDate, respondentSupplementaryData, Can.ofCollection(meals));
            dto.meals().forEach(child->child.parentInterviewRef().setValue(dto));
            annotations.forEach(annot->dto.annotations().put(annot.key(), annot));
            return dto;
        }
    }

}