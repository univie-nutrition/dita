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
package dita.recall24.model.corr;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.NonNull;

import dita.commons.types.Sex;
import dita.recall24.model.Node24;
import dita.recall24.model.Respondent24;

/**
 * Models interview data corrections. WIP
 */
public record Correction24(List<RespondentCorr> respondents) {

    public record RespondentCorr(
            @NonNull String alias,
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

    public UnaryOperator<Node24> asOperator() {
        record Helper(Map<String, RespondentCorr> respCorrByAlias) {
            Respondent24 correct(final Respondent24 resp) {
                var respCorr = respCorrByAlias.get(resp.alias());
                if(respCorr==null) return resp;
                return new Respondent24(resp.alias(),
                        respCorr.dateOfBirth()!=null
                            ? respCorr.dateOfBirth()
                            : resp.dateOfBirth(),
                        respCorr.sex()!=null
                            ? respCorr.sex()
                            : resp.sex(),
                        Can.empty());
            }
        }
        var helper = new Helper(respondents.stream()
            .collect(Collectors.toMap(RespondentCorr::alias, UnaryOperator.identity())));

        return (final Node24 node) -> switch(node) {
        case Respondent24 resp -> helper.correct(resp);
        default -> node;
        };
    }

}
