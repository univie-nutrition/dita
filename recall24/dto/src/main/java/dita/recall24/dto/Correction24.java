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
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import dita.commons.types.Sex;

/**
 * Models interview data corrections. WIP
 */
@Slf4j
public record Correction24(List<RespondentCorr> respondents) {

    @Builder
    public record RespondentCorr(
            @NonNull String alias,
            /**
             * Whether respondent has requested withdrawal from study.
             */
            @Nullable Boolean withdraw,
            @Nullable String newAlias,
            @Nullable LocalDate dateOfBirth,
            @Nullable Sex sex) {

    }

    // -- CONSTRUCTION

    public Correction24() {
        this(new ArrayList<>());
    }

    // -- SERIALIZATION

    public String toYaml() {
        return YamlUtils.toStringUtf8(this);
    }

    public static Try<Correction24> tryFromYaml(final String yaml) {
        return YamlUtils.tryRead(Correction24.class, yaml);
    }

    // -- CORRECTION APPLICATION

    public RecallNode24.Transfomer asTransformer() {
        return new CorrectionTranformer(respondents);
    }

    // -- HELPER

    private record CorrectionTranformer(
            /**
             * Aliases that are marked for withdrawal.
             */
            Set<String> withdrawnAliases,
            Map<String, RespondentCorr> respCorrByAlias)
        implements RecallNode24.Transfomer {

        CorrectionTranformer(final List<RespondentCorr> respondentCorrs){
            this(
                    respondentCorrs.stream()
                        .filter(corr->Boolean.TRUE.equals(corr.withdraw()))
                        .map(RespondentCorr::alias)
                        .collect(Collectors.toSet()),
                    respondentCorrs
                        .stream()
                        .collect(Collectors.toMap(RespondentCorr::alias, UnaryOperator.identity())));
        }

        @Override
        public <T extends RecallNode24> boolean filter(final T node) {
            return switch(node) {
                case Respondent24 resp -> !withdrawnAliases().contains(resp.alias());
                default -> true;
            };
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends RecallNode24> T transform(final T node) {
            return switch(node) {
                case Respondent24 resp -> {
                    var respCorr = respCorrByAlias.get(resp.alias());
                    if(respCorr==null) yield node;

                    var builder = resp.asBuilder();

                    log.info("about to correct {}", respCorr);

                    if(Boolean.TRUE.equals(respCorr.withdraw())) {
                        throw _Exceptions.illegalState("withdrawn respondents should already be filtered yet got %s",
                                resp.alias());
                    }

                    if(respCorr.newAlias()!=null) {
                        builder.alias(respCorr.newAlias());
                        var associatedCorr = respCorrByAlias.get(respCorr.newAlias());
                        if(associatedCorr!=null) {
                            if(associatedCorr.dateOfBirth()!=null) {
                                builder.dateOfBirth(associatedCorr.dateOfBirth());
                            }
                            if(associatedCorr.sex()!=null) {
                                builder.sex(associatedCorr.sex());
                            }
                        }
                    }
                    if(respCorr.dateOfBirth()!=null) {
                        _Assert.assertNull(respCorr.newAlias(), ()->"dateOfBirth correction not allowed on node that has newAlias");
                        builder.dateOfBirth(respCorr.dateOfBirth());
                    }
                    if(respCorr.sex()!=null) {
                        _Assert.assertNull(respCorr.newAlias(), ()->"sex correction not allowed on node that has newAlias");
                        builder.sex(respCorr.sex());
                    }
                    yield (T)builder.build();
                }
                default -> node;
            };
        }

    }

}