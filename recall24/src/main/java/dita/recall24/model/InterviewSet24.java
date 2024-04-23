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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.JsonUtils;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.val;

import dita.commons.jaxb.JaxbAdapters;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

/**
 * Holds a collective of respondents and their individual 24h recall interviews.
 */
public record InterviewSet24(

        /**
         * Respondents that belong to this survey.
         */
        @TreeSubNodes
        Can<Respondent24> respondents

        ) implements dita.recall24.api.InterviewSet24, Node24 {

    public static InterviewSet24 of(
            /** Respondents that belong to this survey. */
            final Can<Respondent24> respondents) {
        return new InterviewSet24(respondents);
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
        return new InterviewSet24(respondentsSorted);
    }

    public int interviewCount() {
        return (int) respondents().stream().mapToInt(resp->resp.interviews().size()).count();
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
     */
    public InterviewSet24 join(final @Nullable InterviewSet24 other) {

        if(other==null) return this;

        val respondents = new ArrayList<Respondent24>(this.respondents().toList());

        Optional.of(other).stream()
        .forEach(model->{
            respondents.addAll(model.respondents().toList()); //TODO handle interview joins
        });

        return InterviewSet24.of(
                Can.ofCollection(respondents).distinct());
    }

    /**
     * Returns a joined model of the models passed in.
     */
    public static InterviewSet24 join(final @Nullable Iterable<InterviewSet24> models) {

        val respondents = new ArrayList<Respondent24>();

        _NullSafe.stream(models)
        .forEach(model->{
            respondents.addAll(model.respondents().toList()); //TODO handle interview joins
        });

        return InterviewSet24.of(
                Can.ofCollection(respondents));
    }

    public String toJson() {
        return JsonUtils.toStringUtf8(this, JsonUtils::indentedOutput);
    }

    public String toYaml() {
        return YamlUtils.toStringUtf8(this,
                JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter()));
    }

}
