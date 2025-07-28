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
package dita.commons.sid.dmap;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.DataSource;

import lombok.RequiredArgsConstructor;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.dmap.Dtos.DirectMapDto;

/**
 * A Direct Map relates data objects from one system to another directly.
 * Represents a collection of {@link DirectMapEntry}(s).
 */
@RequiredArgsConstructor
public class DirectMap {

    // -- FACTORIES

    public static DirectMap empty() {
        return new DirectMap(Collections.emptyMap());
    }

    // -- CONSTRUCTION

    final Map<SemanticIdentifier, DirectMapEntry> internalMap;

    // --

    public DirectMap put(@Nullable final DirectMapEntry entry) {
        if(entry==null) return this;
        if(entry.source()==null) return this;
        if(entry.target()==null) return this;
        internalMap.put(entry.source(), entry);
        return this;
    }

    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    public int entryCount() {
        return internalMap.size();
    }

    // -- STREAMS

    public Stream<DirectMapEntry> streamEntries() {
        return internalMap.values().stream();
    }

    public Stream<DirectMapEntry> streamEntriesHavingSource(
            @Nullable final SemanticIdentifier source){
        return source==null
                ? Stream.empty()
                : streamEntries().filter(entry->entry.source().equals(source));
    }

    // -- LOOKUP

    public Optional<DirectMapEntry> lookupEntry(@Nullable final SemanticIdentifier source){
        if(source==null) return Optional.empty();
        return Optional.ofNullable(internalMap.get(source));
    }

    public DirectMapEntry lookupEntryElseFail(@Nullable final SemanticIdentifier source){
        return lookupEntry(source)
                .orElseThrow(()->_Exceptions.noSuchElement("map has no entry for source=%s",
                        source));
    }

    public Optional<SemanticIdentifier> lookupTarget(@Nullable final SemanticIdentifier source){
        return lookupEntry(source)
                .map(DirectMapEntry::target);
    }

    public SemanticIdentifier lookupTargetElseFail(@Nullable final SemanticIdentifier source){
        return lookupEntryElseFail(source).target();
    }

    // -- SERIALIZE

    public String toYaml() {
        return Dtos.toDto(this).toYaml();
    }

    public static Try<DirectMap> tryFromYaml(@Nullable final DataSource ds) {
        return DirectMapDto.tryFromYaml(ds)
                .mapSuccessAsNullable(dto->Dtos.fromDto(dto));
    }

    // -- CONTRACT

    @Override
    public boolean equals(final Object obj) {
        return switch(obj) {
            case DirectMap other -> other.internalMap.equals(this.internalMap);
            default -> false;
        };
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }

}
