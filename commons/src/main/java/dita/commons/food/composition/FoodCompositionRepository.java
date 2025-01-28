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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.DataSource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.commons.food.composition.Dtos.FoodCompositionRepositoryDto;
import dita.commons.sid.SemanticIdentifier;

@RequiredArgsConstructor
public class FoodCompositionRepository {

    @Getter @Accessors(fluent=true)
    private final FoodComponentCatalog componentCatalog;
    private final Map<SemanticIdentifier, FoodComposition> internalMap;

    public static FoodCompositionRepository empty() {
        return new FoodCompositionRepository();
    }

    public FoodCompositionRepository() {
        this.componentCatalog = new FoodComponentCatalog();
        this.internalMap = new ConcurrentHashMap<>();
    }

    public FoodCompositionRepository put(
            @Nullable final FoodComposition entry) {
        if(entry==null) return this;
        if(entry.foodId()==null) return this;
        if(entry.datapoints()==null) return this;
        internalMap.put(entry.foodId(), entry);
        entry.datapoints().values().forEach(datapoint->{
            componentCatalog.put(datapoint.component());
        });
        return this;
    }

    public int compositionCount() {
        return internalMap.values().size();
    }

    public Stream<FoodComposition> streamCompositions() {
        return internalMap.values().stream();
    }

    // -- LOOKUP

    public Optional<FoodComposition> lookupEntry(
            @Nullable final SemanticIdentifier foodId){
        if(foodId==null) return Optional.empty();
        return Optional.ofNullable(internalMap.get(foodId));
    }

    public FoodComposition lookupEntryElseFail(
            @Nullable final SemanticIdentifier foodId){
        return lookupEntry(foodId)
                .orElseThrow(()->_Exceptions.noSuchElement("map has no entry for foodId=%s",
                        foodId));
    }

    public Optional<Map<SemanticIdentifier, FoodComponentDatapoint>> lookup(
            @Nullable final SemanticIdentifier foodId){
        return lookupEntry(foodId)
                .map(FoodComposition::datapoints);
    }

    public Map<SemanticIdentifier, FoodComponentDatapoint> lookupElseFail(
            @Nullable final SemanticIdentifier foodId){
        return lookupEntryElseFail(foodId).datapoints();
    }

    // -- SERIALIZE

    public String toYaml() {
        return Dtos.toDto(this).toYaml();
    }

    public static Try<FoodCompositionRepository> tryFromYaml(@Nullable final DataSource ds) {
        return FoodCompositionRepositoryDto.tryFromYaml(ds)
                .mapSuccessAsNullable(Dtos::fromDto);
    }

}
