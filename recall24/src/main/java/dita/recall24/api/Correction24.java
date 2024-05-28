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
package dita.recall24.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import dita.commons.types.Sex;

/**
 * Models interview data corrections. WIP
 */
@Log4j2
public record Correction24(List<RespondentCorr> respondents) {

    public record RespondentCorr(
            @NonNull String alias,
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
        record Helper(Map<String, RespondentCorr> respCorrByAlias) {
            void correct(final Respondent24.Builder builder) {
                var respCorr = respCorrByAlias.get(builder.alias());
                if(respCorr==null) return;
                log.info("about to correct {}", respCorr);

                if(respCorr.newAlias()!=null) {
                    builder.alias(respCorr.newAlias());
                }
                if(respCorr.dateOfBirth()!=null) {
                    builder.dateOfBirth(respCorr.dateOfBirth());
                }
                if(respCorr.sex()!=null) {
                    builder.sex(respCorr.sex());
                }
            }
        }
        var helper = new Helper(respondents.stream()
            .collect(Collectors.toMap(RespondentCorr::alias, UnaryOperator.identity())));

        return (final RecallNode24.Builder24<?> builder) -> {
            switch(builder) {
                case Respondent24.Builder resp -> helper.correct(resp);
                default -> {}
            }
        };
    }

}
