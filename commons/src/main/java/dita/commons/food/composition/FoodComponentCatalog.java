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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.sid.SemanticIdentifier;

public class FoodComponentCatalog {

    private final Map<SemanticIdentifier, FoodComponent> internalMap = new ConcurrentHashMap<>();

    public FoodComponentCatalog put(
            @Nullable final FoodComponent component) {
        if(component==null) return this;
        if(component.componentId()==null) return this;
        internalMap.merge(component.componentId(), component, (a, b)->{ _Assert.assertEquals(a, b); return a;});
        return this;
    }

    public Stream<FoodComponent> streamComponents() {
        return internalMap.values().stream();
    }

    // -- YAML

    public String toYaml() {
        return Dtos.toDto(this).toYaml();
    }

    // -- LOOKUP

    public Optional<FoodComponent> lookupEntry(
            @Nullable final SemanticIdentifier componentId){
        if(componentId==null) return Optional.empty();
        return Optional.ofNullable(internalMap.get(componentId));
    }

    public FoodComponent lookupEntryElseFail(
            @Nullable final SemanticIdentifier componentId){
        return lookupEntry(componentId)
                .orElseThrow(()->_Exceptions.noSuchElement("map has no entry for componentId=%s",
                        componentId));
    }

    // -- EQUALITY

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof FoodComponentCatalog other
            ? Objects.equals(this.internalMap, other.internalMap)
            : false;
    }

}
