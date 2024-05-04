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
package dita.commons.food.composition;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.JsonUtils.JacksonCustomizer;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.commons.io.YamlUtils.YamlLoadCustomizer;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodComponentDatapoint.DatapointSemantic;
import dita.commons.food.composition.FoodComposition.CompositionQuantification;
import dita.commons.jaxb.JaxbAdapters;
import dita.commons.sid.SemanticIdentifier;

@UtilityClass
class Dtos {

    // -- DATAPOINT

    public record FoodComponentDatapointDto(
            @NonNull SemanticIdentifier componentId,
            @NonNull DatapointSemantic datapointSemantic,
            @NonNull BigDecimal datapointValue) {
    }

    FoodComponentDatapointDto toDto(final FoodComponentDatapoint datapoint) {
        return new FoodComponentDatapointDto(
                datapoint.component().componentId(),
                datapoint.datapointSemantic(),
                datapoint.datapointValue());
    }

    FoodComponentDatapoint fromDto(
            final FoodComponentDatapointDto dto,
            final CompositionQuantification compositionQuantification,
            final FoodComponentCatalog componentCatalog) {
        return new FoodComponentDatapoint(
                componentCatalog.lookupEntryElseFail(dto.componentId()),
                compositionQuantification,
                dto.datapointSemantic(),
                dto.datapointValue());
    }

    // -- FOOD COMPOSITION

    public record FoodCompositionDto(
            @NonNull SemanticIdentifier foodId,
            @NonNull CompositionQuantification compositionQuantification,
            @NonNull Collection<FoodComponentDatapointDto> datapoints) {
    }

    FoodCompositionDto toDto(final FoodComposition composition) {
        return new FoodCompositionDto(
                composition.foodId(),
                composition.compositionQuantification(),
                composition.streamDatapoints().map(Dtos::toDto).toList());
    }

    FoodComposition fromDto(
            final FoodCompositionDto dto,
            final FoodComponentCatalog componentCatalog) {
        var datapointMap = new HashMap<SemanticIdentifier, FoodComponentDatapoint>();
        dto.datapoints().stream()
            .map(d->Dtos.fromDto(d, dto.compositionQuantification(), componentCatalog))
            .forEach(dp->datapointMap.put(dp.componentId(), dp));
        return new FoodComposition(dto.foodId(), dto.compositionQuantification(), datapointMap);
    }

    // -- FOOD COMPOSITION REPOSITORY

    public record FoodCompositionRepositoryDto(
            Collection<FoodComponent> components,
            Collection<FoodCompositionDto> compositions) {

        public String toYaml() {
            return YamlUtils.toStringUtf8(this, yamlOptions());
        }
        public static Try<FoodCompositionRepositoryDto> tryFromYaml(@Nullable final DataSource ds) {
            if(ds==null) return Try.failure(_Exceptions.noSuchElement("missing datasource"));
            return YamlUtils.tryReadCustomized(
                    FoodCompositionRepositoryDto.class, ds, yamlMillionCodePointsLimit(200), yamlOptions());
        }
    }

    FoodCompositionRepositoryDto toDto(final FoodCompositionRepository repo) {
        return new FoodCompositionRepositoryDto(
                repo.componentCatalog().streamComponents().toList(),
                repo.streamCompositions().map(Dtos::toDto).toList());
    }

    FoodCompositionRepository fromDto(@Nullable final FoodCompositionRepositoryDto dto) {
        if(dto==null) return null;
        var componentCatalog = new FoodComponentCatalog();
        var internalMap = new ConcurrentHashMap<SemanticIdentifier, FoodComposition>();
        dto.components().forEach(componentCatalog::put);
        dto.compositions().forEach(comp->internalMap.put(comp.foodId(), Dtos.fromDto(comp, componentCatalog)));
        var repo = new FoodCompositionRepository(componentCatalog, internalMap);
        return repo;
    }

    // -- HELPER

    private JacksonCustomizer yamlOptions() {
        return JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierAdapter());
    }

    private YamlLoadCustomizer yamlMillionCodePointsLimit(final int millions) {
        return loader->{
            loader.setCodePointLimit(millions * 1_000_000);
            return loader;
        };
    }

}
