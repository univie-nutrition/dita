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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.JsonUtils;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;
import lombok.experimental.Accessors;

import dita.commons.jaxb.JaxbAdapters;
import dita.commons.types.Message;
import dita.recall24.dto.util.Recall24DtoUtils;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

/**
 * Holds a collective of respondents and their individual 24h recall interviews.
 */
public sealed interface InterviewSet24 extends RecallNode24
permits InterviewSet24.Dto {

    /**
     * Respondents that belong to this survey.
     */
    Can<? extends Respondent24> respondents();

    Optional<? extends Respondent24> lookupRespondent(String respondentAlias);
    Can<? extends Interview24> lookupInterviews(String respondentAlias);

    // -- FACTORIES

    static InterviewSet24.Dto empty() {
        return InterviewSet24.Dto.empty();
    }


    // -- DTO

    /**
     * Holds a collective of respondents and their individual 24h recall interviews.
     */
    public record Dto(

            /**
             * Respondents that belong to this survey.
             */
            @TreeSubNodes
            Can<Respondent24.Dto> respondents,

            @JsonIgnore
            Map<String, Annotation> annotations

            ) implements InterviewSet24 {

        public static Dto of(
                /** Respondents that belong to this survey. */
                final Can<Respondent24.Dto> respondents) {
            return new Dto(respondents, new HashMap<>());
        }

        public static Dto empty() {
            return of(Can.empty());
        }

        @Override
        public Optional<Respondent24.Dto> lookupRespondent(
                final String respondentAlias) {
            return respondents.stream()
                .filter(res->Objects.equals(res.alias(), respondentAlias))
                .findFirst();
        }

        @Override
        public Can<Interview24.Dto> lookupInterviews(
                final String respondentAlias) {
            return lookupRespondent(respondentAlias)
                    .map(Respondent24.Dto::interviews)
                    .orElseGet(Can::empty);
        }

        /**
         * Respondents are sorted by respondent-alias.
         * Interviews are sorted by interview-date.
         */
        public Dto normalized() {
            var respondentsSorted = respondents
                .sorted((a, b)->a.alias().compareTo(b.alias()))
                .map(respondent->respondent.normalize());
            return new Dto(respondentsSorted, copy(annotations));
        }

        public int interviewCount() {
            return respondents().stream().mapToInt(resp->resp.interviews().size()).sum();
        }

        public Stream<Interview24.Dto> streamInterviews() {
            return this.respondents().stream()
                    .flatMap(resp->resp.interviews().stream());
        }

        /**
         * @implNote requires Causewaystuff tree metamodel integration
         */
        public Stream<RecallNode24> streamDepthFirst() {
            return Recall24DtoUtils.wrapAsTreeNode(this)
                .streamDepthFirst()
                .map(TreeNode::getValue);
        }

        /**
         * Returns a new tree with the transformed nodes.
         * @param transformer - transforms fields only (leave parent child relations untouched)
         */
        public InterviewSet24.Dto transform(
                final @NonNull RecallNode24.Transfomer transformer) {
            return Recall24DtoUtils.transform(transformer).apply(this);
        }

        /**
         * Returns a split into partitionCountYield parts of the given model, if possible.
         * This is, if there are at least partitionCountYield respondents.
         * <p>
         * Check the result's cardinality.
         */
        public Can<InterviewSet24.Dto> split(final int partitionCountYield) {
            val respondentBiPartition = this.respondents().partitionOuterBound(partitionCountYield);
            return respondentBiPartition.map(InterviewSet24.Dto::of);
        }

        /**
         * Returns a joined model of the models passed in.
         * @param messageConsumer join-algorithm might detect data inconsistencies
         */
        public InterviewSet24.Dto join(
                final @Nullable InterviewSet24.Dto other,
                final @Nullable Consumer<Message> messageConsumer) {

            if(other==null) return this;

            var interviews = Stream.concat(
                    this.streamInterviews(),
                    other.streamInterviews())
                    .toList();

            return Recall24DtoUtils.join(interviews, messageConsumer);
        }

        /**
         * Returns a joined model of the models passed in.
         * @param messageConsumer join-algorithm might detect data inconsistencies
         */
        public static InterviewSet24.Dto join(
                final @Nullable Iterable<InterviewSet24.Dto> models,
                final @Nullable Consumer<Message> messageConsumer) {
            var interviews = _NullSafe.stream(models)
                    .flatMap(InterviewSet24.Dto::streamInterviews)
                    .toList();

            return Recall24DtoUtils.join(interviews, messageConsumer);
        }

        public String toJson() {
            return JsonUtils.toStringUtf8(this,
                    JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierAdapter()),
                    JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierSetAdapter()),
                    JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter()),
                    JsonUtils::indentedOutput);
        }

        public String toYaml() {
            return YamlUtils.toStringUtf8(this,
                    JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierAdapter()),
                    JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierSetAdapter()),
                    JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter()));
        }

        // -- ANNOTATIONS

        public InterviewSet24.Dto annotate(final Annotation annotation) {
            annotations.put(annotation.key(), annotation);
            return this;
        }

        private Map<String, Annotation> copy(final Map<String, Annotation> map) {
            var copy = new HashMap<String, Annotation>();
            copy.putAll(map);
            return copy;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder();
        }

        @JsonIgnore
        public boolean isEmpty() {
            return respondents==null
                    || respondents.isEmpty();
        }

    }

    // -- BUILDER

    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<Dto> {
        final List<Respondent24.Dto> respondents = new ArrayList<>();
        @Override
        public Dto build() {
            return Dto.of(Can.ofCollection(respondents));
        }
    }

}