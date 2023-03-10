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
package at.ac.univie.nutrition.dita.recall24.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.base._NullSafe;

import at.ac.univie.nutrition.dita.commons.types.ObjectRef;
import lombok.val;

/**
 * Holds a collective of respondents and their individual 24h recall interviews.
 */
public record InterviewSet24(

        /**
         * Respondents that belong to this survey.
         */
        Can<Respondent24> respondents,

        /**
         * Interviews that belong to this survey.
         */
        Can<Interview24> interviews,

        ObjectRef<InterviewSet24.Helper> helperRef

        ) implements at.ac.univie.nutrition.dita.recall24.api.InterviewSet24 {

    private static record Helper(
        Map<String, Respondent24> respondentsByAlias,
        Map<String, Can<Interview24>> interviewsByAlias) {
        static Helper create(
                final Can<Respondent24> respondents,
                final Can<Interview24> interviews) {
            val helper = new Helper(
                    new TreeMap<String, Respondent24>(),
                    new TreeMap<String, Can<Interview24>>());
            respondents.forEach(res->helper.respondentsByAlias().put(res.alias(), res));
            interviews.forEach(inv->helper.interviewsByAlias()
                    .compute(inv.respondentAlias(),(k, v) -> (v == null)
                            ? Can.of(inv)
                            : v.add(inv)));
            return helper;
        }
    }

    public static InterviewSet24 of(
            /** Respondents that belong to this survey. */
            final Can<Respondent24> respondents,
            /** Interviews that belong to this survey. */
            final Can<Interview24> interviews) {
        return new InterviewSet24(respondents, interviews,
                ObjectRef.of(Helper.create(respondents, interviews)));
    }

    @Override
    public Optional<Respondent24> lookupRespondent(
            final String respondentAlias) {
        return Optional.ofNullable(helperRef.getValue().respondentsByAlias.get(respondentAlias));
    }

    @Override
    public Can<Interview24> lookupInterviews(
            final String respondentAlias) {
        return Optional.ofNullable(helperRef.getValue().interviewsByAlias.get(respondentAlias))
                .orElseGet(Can::empty);
    }

    /**
     * Respondents are sorted by respondent-alias.
     * Interviews are sorted respondent-alias then by interview-date.
     * All ordinals are filled in. //TODO
     */
    public InterviewSet24 normalized() {
        val helper = helperRef.getValue();
        val respondentsSorted = Can.ofCollection(helper.respondentsByAlias().values());
        Can<Interview24> interviewsSorted = respondentsSorted.stream()
                .map(Respondent24::alias)
                .map(this::lookupInterviews)
                .map((Can<Interview24> interviews)->
                    interviews.sorted((a, b)->a.interviewDate().compareTo(b.interviewDate())))
                .peek((Can<Interview24> interviews)->
                    interviews.forEach(IndexedConsumer.offset(1, (ordinal, inv)->
                        inv.interviewOrdinalRef().setValue(ordinal)))) // fill in interview's ordinal
                .flatMap(Can::stream)
                .collect(Can.toCan());

        return new InterviewSet24(respondentsSorted, interviewsSorted, helperRef);
    }

    /**
     * Returns a split into partitionCountYield parts of the given model, if possible.
     * This is, if there are at least partitionCountYield respondents.
     * <p>
     * Check the result's cardinality.
     */
    public Can<InterviewSet24> split(final int partitionCountYield) {

        val respondentBiPartition = this.respondents().partitionOuterBound(partitionCountYield);

        return respondentBiPartition.map(respondentGroup->{
            val aliasSet = respondentGroup.map(Respondent24::alias)
                    .stream()
                    .collect(Collectors.toSet());

            return InterviewSet24.of(
                    respondentGroup,
                    interviews().filter(iv->aliasSet.contains(iv.respondentAlias())));
        });
    }

    /**
     * Returns a joined model of the models passed in.
     */
    public static InterviewSet24 join(final @Nullable Iterable<InterviewSet24> models) {

        val respondents = new ArrayList<Respondent24>();
        val interviews = new ArrayList<Interview24>();

        _NullSafe.stream(models)
        .forEach(model->{
            respondents.addAll(model.respondents().toList()); //TODO collisions could be problematic
            interviews.addAll(model.interviews().toList()); //TODO collisions could be problematic
        });

        return InterviewSet24.of(
                Can.ofCollection(respondents),
                Can.ofCollection(interviews));
    }

}
