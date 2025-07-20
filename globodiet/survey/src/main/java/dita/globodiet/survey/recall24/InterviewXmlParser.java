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
package dita.globodiet.survey.recall24;

import java.util.List;
import java.util.function.Consumer;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.JaxbUtils;

import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.types.Message;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.dto.Annotated;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;

///Parses _GloboDiet's_ XML Interview files as {@link InterviewSet24}.
@UtilityClass
public class InterviewXmlParser {

    /**
     * Parses GloboDiet XML Interview file from given {@link DataSource} into a {@link InterviewSet24},
     * wrapped by a {@link Try}.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public Try<List<Interview24>> tryParse(
            final @NonNull DataSource source,
            final @NonNull SystemId systemId,
            final @Nullable Consumer<Message> messageConsumer) {
        return Try.call(()->parse(source, systemId, messageConsumer));
    }

    /**
     * Parses GloboDiet XML Interview file from given {@link DataSource} into a {@link InterviewSet24}.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public List<Interview24> parse(
            final @NonNull DataSource source,
            final @NonNull SystemId systemId,
            final @Nullable Consumer<Message> messageConsumer) {
        var dto = JaxbUtils.tryRead(_Dtos.Itv.class, source)
                .valueAsNullableElseFail();
        if(dto==null) {
            InterviewUtils.warnEmptyDataSource(source, messageConsumer);
            return List.of();
        }
        var interviewList = createFromDto(dto, systemId);
        interviewList.forEach(iv->iv.putAnnotation(Annotated.DATASOURCE, source.getDescription()));
        return interviewList;
    }

    /**
     * Parses GloboDiet XML Interview file from given {@link Clob} into a {@link InterviewSet24}.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public List<Interview24> parse(
            final Clob interviewSource,
            final @NonNull SystemId systemId,
            final @Nullable Consumer<Message> messageConsumer) {
        var dto = JaxbUtils.tryRead(_Dtos.Itv.class, interviewSource.chars().toString())
                .valueAsNullableElseFail();
        if(dto==null) {
            InterviewUtils.warnEmptyDataSource(interviewSource, messageConsumer);
            List.of();
        }
        var interviewList = createFromDto(dto, systemId);
        interviewList.forEach(iv->iv.putAnnotation(Annotated.DATASOURCE, interviewSource.name()));
        return interviewList;
    }

    // -- HELPER

    private List<Interview24> createFromDto(
        final _Dtos.@NonNull Itv dto,
        final @NonNull SystemId systemId) {
        var interviewConverter = new InterviewConverter(systemId);
        return dto.getInterviews().stream()
                    .map(interviewConverter::toInterview24)
                    .toList();
    }

}
