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

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.NonNull;

import dita.commons.types.Sex;

public record Correction24(List<RespondentCorr> respondents) {

    public Correction24() {
        this(new ArrayList<>());
    }

    public String toYaml() {
        return YamlUtils.toStringUtf8(this);
    }

    public static Try<Correction24> tryFromYaml(final String yaml) {
        return YamlUtils.tryRead(Correction24.class, yaml);
    }

    public record RespondentCorr(
            @NonNull String alias,
            @Nullable LocalDate dateOfBirth,
            @Nullable Sex sex) {
    }

}
