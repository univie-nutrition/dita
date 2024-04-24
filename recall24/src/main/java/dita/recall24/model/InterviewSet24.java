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
package dita.recall24.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.JsonUtils;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.val;

import dita.commons.jaxb.JaxbAdapters;
import dita.commons.types.Message;
import dita.recall24.util.Recall24ModelUtils;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

/**
 * Holds a collective of respondents and their individual 24h recall interviews.
 */
public record InterviewSet24(

        /**
         * Respondents that belong to this survey.
         */
        @TreeSubNodes
        Can<Respondent24> respondents,

        @JsonIgnore
        Map<String, Annotation> annotations

        ) implements dita.recall24.api.InterviewSet24, Node24 {

    public static InterviewSet24 of(
            /** Respondents that belong to this survey. */
            final Can<Respondent24> respondents) {
        return new InterviewSet24(respondents, new HashMap<>());
    }

    public static InterviewSet24 empty() {
        return of(Can.empty());
    }

    @Override
    public Optional<Respondent24> lookupRespondent(
            final String respondentAlias) {
        return respondents.stream()
            .filter(res->Objects.equals(res.alias(), respondentAlias))
            .findFirst();
    }

    @Override
    public Can<? extends Interview24> lookupInterviews(
            final String respondentAlias) {
        return lookupRespondent(respondentAlias)
                .map(Respondent24::interviews)
                .orElseGet(Can::empty);
    }

    /**
     * Respondents are sorted by respondent-alias.
     * Interviews are sorted by interview-date.
     */
    public InterviewSet24 normalized() {
        var respondentsSorted = respondents
            .sorted((a, b)->a.alias().compareTo(b.alias()))
            .map(respondent->respondent.normalize());
        return new InterviewSet24(respondentsSorted, copy(annotations));
    }

    public int interviewCount() {
        return (int) respondents().stream().mapToInt(resp->resp.interviews().size()).sum();
    }

    public Stream<Interview24> streamInterviews() {
        return this.respondents().stream()
                .flatMap(resp->resp.interviews().stream());
    }

    /**
     * Returns a split into partitionCountYield parts of the given model, if possible.
     * This is, if there are at least partitionCountYield respondents.
     * <p>
     * Check the result's cardinality.
     */
    public Can<InterviewSet24> split(final int partitionCountYield) {
        val respondentBiPartition = this.respondents().partitionOuterBound(partitionCountYield);
        return respondentBiPartition.map(InterviewSet24::of);
    }

    /**
     * Returns a joined model of the models passed in.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public InterviewSet24 join(
            final @Nullable InterviewSet24 other,
            final @Nullable Consumer<Message> messageConsumer) {

        if(other==null) return this;

        var interviews = Stream.concat(
                this.streamInterviews(),
                other.streamInterviews())
                .toList();

        return Recall24ModelUtils.join(interviews, messageConsumer);
    }

    /**
     * Returns a joined model of the models passed in.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public static InterviewSet24 join(
            final @Nullable Iterable<InterviewSet24> models,
            final @Nullable Consumer<Message> messageConsumer) {
        var interviews = _NullSafe.stream(models)
                .flatMap(InterviewSet24::streamInterviews)
                .toList();

        return Recall24ModelUtils.join(interviews, messageConsumer);
    }

    public String toJson() {
        return JsonUtils.toStringUtf8(this,
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter()),
                JsonUtils::indentedOutput);
    }

    public String toYaml() {
        return YamlUtils.toStringUtf8(this,
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter()));
    }

    // -- ANNOTATIONS

    public InterviewSet24 annotate(final Annotation annotation) {
        annotations.put(annotation.key(), annotation);
        return this;
    }

    private Map<String, Annotation> copy(final Map<String, Annotation> map) {
        var copy = new HashMap<String, Annotation>();
        copy.putAll(map);
        return copy;
    }

}
