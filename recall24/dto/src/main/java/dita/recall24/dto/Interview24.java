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

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import dita.commons.types.IntRef;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

/**
 * Represents a (single) 24h recall interview event.
 */
@DomainObject
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
             * Date of food consumption. Typically one day before the interview.
             */
            LocalDate consumptionDate,

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
            @CollectionLayout(navigableSubtree = "1")
            Can<Meal24> meals,

            /**
             * mutable
             */
            Map<String, Serializable> annotations
            ) implements RecallNode24, Annotated {


    /** canonical constructor */
    public Interview24(
        final ObjectRef<Respondent24> parentRespondentRef,
        final LocalDate interviewDate,
        final LocalDate consumptionDate,
        final IntRef interviewOrdinalRef,
        final RespondentSupplementaryData24 respondentSupplementaryData,
        final Can<Meal24> meals,
        final Map<String, Serializable> annotations) {

        this.parentRespondentRef = parentRespondentRef;
        this.interviewDate = interviewDate;
        this.consumptionDate = consumptionDate;
        this.interviewOrdinalRef = interviewOrdinalRef;
        this.respondentSupplementaryData = respondentSupplementaryData;
        this.meals = meals;
        this.annotations = annotations;
        this.respondentSupplementaryData.parentInterviewRef().setValue(this);
        this.meals.forEach(meal24->meal24.parentInterviewRef().setValue(this));
    }

    public Interview24(
        final LocalDate interviewDate,
        final LocalDate consumptionDate,
        final RespondentSupplementaryData24 respondentSupplementaryData,
        final Can<Meal24> meals) {
        this(new ObjectRef<>(null), interviewDate, consumptionDate, IntRef.of(-1), respondentSupplementaryData, meals, new LinkedHashMap<>());
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

    public Interview24 withDataSource(final NamedPath namedPath) {
        putAnnotation(Annotated.DATASOURCE, namedPath);
        return this;
    }

    public Optional<NamedPath> dataSource() {
        return Optional.ofNullable(annotations().get(Annotated.DATASOURCE))
            .map(NamedPath.class::cast);
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
        LocalDate consumptionDate;
        RespondentSupplementaryData24 respondentSupplementaryData;
        final List<Meal24> meals = new ArrayList<>();
        final List<Annotation> annotations = new ArrayList<>();

        static Builder of(final Interview24 dto) {
            var builder = new Builder()
                .respondent(dto.parentRespondent())
                .interviewDate(dto.interviewDate)
                .consumptionDate(dto.consumptionDate)
                .respondentSupplementaryData(dto.respondentSupplementaryData());
            dto.meals.forEach(builder.meals::add);
            dto.streamAnnotations().forEach(builder.annotations::add);
            return builder;
        }

        @Override
        public Interview24 build() {
            var dto = new Interview24(interviewDate, consumptionDate,
                respondentSupplementaryData, Can.ofCollection(meals));
            dto.parentRespondentRef().setValue(respondent);
            _NullSafe.stream(annotations).forEach(dto::putAnnotation);
            return dto;
        }
    }

}