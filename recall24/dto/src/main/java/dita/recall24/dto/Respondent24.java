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
import java.util.Comparator;
import java.util.List;

import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import dita.commons.types.Sex;

@DomainObject
public record Respondent24(
            /**
             * Anonymized respondent identifier, unique to the corresponding survey.
             */
            String alias,

            LocalDate dateOfBirth,

            Sex sex,

            /**
             * Interviews that this respondent was subject to.
             * @apiNote On construction, interviews are sorted by consumption-date, 
             *      interview ordinals are filled in, interview parent references are filled in as well.
             */
            @CollectionLayout(navigableSubtree = "1") @NonNull
            Can<Interview24> interviews

            ) implements RecallNode24 {

    public Respondent24(
        String alias,
        LocalDate dateOfBirth,
        Sex sex,
        @NonNull Can<Interview24> interviews) {
            this.alias = alias;
            this.dateOfBirth = dateOfBirth;
            this.sex = sex;
            // interviews are sorted by consumption-date
            this.interviews = interviews.sorted(Comparator.comparing(Interview24::consumptionDate));
            // fill in interviews' parent
            this.interviews.forEach(intv->intv.parentRespondentRef().setValue(this));
            // fill in interviews' ordinal
            this.interviews.forEach(IndexedConsumer.offset(1, (ordinal, inv)->
                inv.interviewOrdinalRef().setValue(ordinal)));
    }
    
    public int interviewCount() {
        return interviews.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Respondent24.Builder asBuilder() {
        return Builder.of(this);
    }

    // -- BUILDER

    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<Respondent24> {
        private String alias;
        private LocalDate dateOfBirth;
        private Sex sex;
        private final List<Interview24> interviews = new ArrayList<>();

        static Builder of(final Respondent24 dto) {
            var builder = new Builder().alias(dto.alias).dateOfBirth(dto.dateOfBirth).sex(dto.sex);
            dto.interviews.forEach(builder.interviews::add);
            return builder;
        }

        @Override
        public Respondent24 build() {
            return new Respondent24(alias, dateOfBirth, sex, Can.ofCollection(interviews));
        }
    }

}