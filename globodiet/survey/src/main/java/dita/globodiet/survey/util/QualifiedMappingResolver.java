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
package dita.globodiet.survey.util;

import org.jspecify.annotations.NonNull;

import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
import dita.commons.sid.qmap.QualifiedMap;
import dita.recall24.dto.Annotated;

public record QualifiedMappingResolver(
        @NonNull QualifiedMap nutMapping
        ) implements Transfomer {

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecallNode24> T transform(final T node) {

        return switch(node) {
            case Record24.Composite composite -> {
                var builder = (Composite.Builder) composite.asBuilder();
                builder.replaceSubRecords(this::transform); // recursive
                yield (T) builder.build();
            }
            case Record24.Consumption consumption -> {
                yield consumption.lookupAnnotation("fcdbId").isPresent()
                    ? (T) consumption
                    : (T) nutMapping
                        .lookupTarget(consumption.asQualifiedMapKey())
                        .map(fcdbId->consumption.withAnnotationAdded(new Annotated.Annotation("fcdbId", fcdbId)))
                        .orElse(consumption);
            }
            default -> node;
        };
    }

}
