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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.JsonUtils;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import dita.commons.io.JaxbAdapters;
import dita.commons.types.Message;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.Record24.Food;
import dita.recall24.dto.util.Recall24DtoUtils;

/**
 * Holds a collective of respondents and their individual 24h recall interviews.
 */
@DomainObject
public record InterviewSet24(

            /**
             * Respondents that belong to this survey.
             */
            @CollectionLayout(navigableSubtree = "1")
            Can<Respondent24> respondents,

            Map<String, Serializable> annotations

            ) implements RecallNode24, Annotated {

    // -- FACTORIES

    public static InterviewSet24 empty() {
        return new InterviewSet24(Can.empty(), Collections.emptyMap());
    }

    public static InterviewSet24 of(
            /** Respondents that belong to this survey. */
            final Can<Respondent24> respondents) {
        return new InterviewSet24(respondents, new LinkedHashMap<>());
    }

    // -- CANONICAL CONSTRUCTOR

    /**
     * Respondents are sorted by respondent-alias.
     * Interviews are sorted by interview-date.
     * All ordinals are filled in.
     */
    public InterviewSet24(
            final Can<Respondent24> respondents,
            final Map<String, Serializable> annotations) {
        this.respondents = respondents
                .sorted(Comparator.comparing(Respondent24::alias));
        this.annotations = annotations;
    }

    public Optional<Respondent24> lookupRespondent(
            final String respondentAlias) {
        return respondents.stream()
            .filter(res->Objects.equals(res.alias(), respondentAlias))
            .findFirst();
    }

    public Can<Interview24> lookupInterviews(
            final String respondentAlias) {
        return lookupRespondent(respondentAlias)
                .map(Respondent24::interviews)
                .orElseGet(Can::empty);
    }

    public int interviewCount() {
        return respondents().stream().mapToInt(resp->resp.interviews().size()).sum();
    }

    public Stream<Interview24> streamInterviews() {
        return this.respondents().stream()
                .flatMap(resp->resp.interviews().stream());
    }

    public Stream<RecallNode24> streamDepthFirst(@NonNull final FactoryService factoryService) {
        return Recall24DtoUtils.wrapAsTreeNode(factoryService, this)
            .streamDepthFirst()
            .map(TreeNode::value);
    }

    // -- TRANSFORM

    /**
     * Returns a new tree with the transformed nodes.
     * @param transformer - transforms fields only (leave parent child relations untouched)
     */
    public InterviewSet24 transform(
            final RecallNode24.@NonNull Transfomer transformer) {
        return Recall24DtoUtils.transform(this, transformer).orElse(null);
    }

    public record TopLevelFoodTransformer(UnaryOperator<Food> operator) implements RecallNode24.Transfomer {
        @SuppressWarnings("unchecked")
        @Override
        public <T extends RecallNode24> T transform(final T node) {
            return switch(node) {
                case Food food -> (T)operator.apply(food);
                default -> node;
            };
        }
    }
    public InterviewSet24 transformTopLevelFood(final UnaryOperator<Food> operator) {
        return transform(new TopLevelFoodTransformer(operator));
    }

    public record CompositeTransformer(UnaryOperator<Composite> operator) implements RecallNode24.Transfomer {
        @SuppressWarnings("unchecked")
        @Override
        public <T extends RecallNode24> T transform(final T node) {
            return switch(node) {
                case Composite composite -> (T)operator.apply(composite);
                default -> node;
            };
        }
    }
    public InterviewSet24 transformComposites(final UnaryOperator<Composite> operator) {
        return transform(new CompositeTransformer(operator));
    }

    // -- FILTER

    public InterviewSet24 filter(final Predicate<Respondent24> respondentFilter) {
        return InterviewSet24.of(this.respondents().filter(respondentFilter));
    }

    /**
     * Returns a split into partitionCountYield parts of the given model, if possible.
     * This is, if there are at least partitionCountYield respondents.
     * <p>
     * Check the result's cardinality.
     */
    public Can<InterviewSet24> split(final int partitionCountYield) {
        var respondentBiPartition = this.respondents().partitionOuterBound(partitionCountYield);
        return respondentBiPartition.map(InterviewSet24::of);
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

        return Recall24DtoUtils.join(interviews, messageConsumer);
    }

    public String toJson() {
        return JsonUtils.toStringUtf8(this,
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierAdapter()),
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierSetAdapter()),
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter()),
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.NamedPathAdapter()),
                JsonUtils::indentedOutput);
    }

    public String toYaml() {
        return YamlUtils.toStringUtf8(this,
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierAdapter()),
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierSetAdapter()),
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter()),
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.NamedPathAdapter()));
    }

    // -- ANNOTATIONS

    public InterviewSet24 annotate(final Annotation annotation) {
        annotations.put(annotation.key(), annotation);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder24<InterviewSet24> asBuilder() {
        return Builder.of(this);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return respondents==null
                || respondents.isEmpty();
    }

    // -- BUILDER

    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<InterviewSet24> {
        final List<Annotation> annotations = new ArrayList<>();
        final List<Respondent24> respondents = new ArrayList<>();

        static Builder of(final InterviewSet24 ivSet) {
            var builder = new Builder();
            ivSet.respondents().forEach(builder.respondents::add);
            ivSet.streamAnnotations().forEach(builder.annotations::add);
            return builder;
        }

        @Override
        public InterviewSet24 build() {
            var ivSet = InterviewSet24.of(Can.ofCollection(respondents));
            _NullSafe.stream(annotations).forEach(ivSet::annotate);
            return ivSet;
        }
    }

}
