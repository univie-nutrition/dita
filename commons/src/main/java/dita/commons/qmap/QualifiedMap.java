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
package dita.commons.qmap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.DataSource;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import dita.commons.qmap.Dtos.QualifiedMapDto;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;

/**
 * A Qualified Map relates data objects from one system to another,
 * respecting arbitrary qualifiers. Represents a collection of {@link QualifiedMapEntry}(s).
 */
@RequiredArgsConstructor
public class QualifiedMap {

    public record QualifiedMapKey(
            /**
             * Semantic identifier of the data object that is mapped from.
             */
            SemanticIdentifier source,
            /**
             * Constraints under which this map entry is applicable.
             */
            SemanticIdentifierSet qualifier) implements Comparable<QualifiedMapKey> {

        static QualifiedMapKey from(@NonNull final QualifiedMapEntry entry) {
            return new QualifiedMapKey(entry.source(), SemanticIdentifierSet.nullToEmpty(entry.qualifier()));
        }

        @Override
        public int compareTo(final @Nullable QualifiedMapKey o) {
            return compare(this, o);
        }

        /**
         * Skips systemId information.
         * @param primaryDelimiter that separates source and qualifier
         * @param secondaryDelimiter that separates the qualifier element objectIds
         */
        public String shortFormat(final String primaryDelimiter, final String secondaryDelimiter) {
            return qualifier().elements().isEmpty()
                ? source().objectId()
                : source().objectId()
                    + primaryDelimiter
                    + qualifier().shortFormat(secondaryDelimiter);
        }

        // -- UTILITY

        public static int compare(
                final @Nullable QualifiedMapKey a,
                final @Nullable QualifiedMapKey b) {
            if(a==null) return b==null
                        ? 0
                        : -1;
            if(b==null) return 1;
            final int c = SemanticIdentifier.compare(a.source(), b.source());
            return c!=0
                ? c
                : SemanticIdentifierSet.compare(a.qualifier(), b.qualifier());
        }

    }

    final Map<QualifiedMapKey, QualifiedMapEntry> internalMap;

    public QualifiedMap put(@Nullable final QualifiedMapEntry entry) {
        if(entry==null) return this;
        if(entry.source()==null) return this;
        if(entry.target()==null) return this;
        internalMap.put(QualifiedMapKey.from(entry), entry);
        return this;
    }

    public int entryCount() {
        return internalMap.size();
    }

    // -- STREAMS

    public Stream<QualifiedMapEntry> streamEntries() {
        return internalMap.values().stream();
    }

    public Stream<QualifiedMapEntry> streamEntriesHavingSource(
            @Nullable final SemanticIdentifier source){
        return source==null
                ? Stream.empty()
                : streamEntries().filter(entry->entry.source().equals(source));
    }

    // -- LOOKUP VIA KEY

    public Optional<QualifiedMapEntry> lookupEntry(@Nullable final QualifiedMapKey key){
        if(key==null || key.source == null) return Optional.empty();
        return Optional.ofNullable(internalMap.get(key));
    }

    public QualifiedMapEntry lookupEntryElseFail(@Nullable final QualifiedMapKey key){
        return lookupEntry(key)
                .orElseThrow(()->_Exceptions.noSuchElement("map has no entry for source=%s and qualifier=%s",
                        key.source, key.qualifier));
    }

    public Optional<SemanticIdentifier> lookupTarget(@Nullable final QualifiedMapKey key){
        return lookupEntry(key)
                .map(QualifiedMapEntry::target);
    }

    public SemanticIdentifier lookupTargetElseFail(@Nullable final QualifiedMapKey key){
        return lookupEntryElseFail(key).target();
    }

    // -- LOOKUP WITHOUT KEY

    public Optional<QualifiedMapEntry> lookupEntry(
            @Nullable final SemanticIdentifier source,
            @Nullable final SemanticIdentifierSet qualifier){
        if(source==null) return Optional.empty();
        return lookupEntry(new QualifiedMapKey(source, SemanticIdentifierSet.nullToEmpty(qualifier)));
    }

    public QualifiedMapEntry lookupEntryElseFail(
            @Nullable final SemanticIdentifier source,
            @Nullable final SemanticIdentifierSet qualifier){
        return lookupEntry(source, qualifier)
                .orElseThrow(()->_Exceptions.noSuchElement("map has no entry for source=%s and qualifier=%s",
                        source, qualifier));
    }

    public Optional<SemanticIdentifier> lookupTarget(
            @Nullable final SemanticIdentifier source,
            @Nullable final SemanticIdentifierSet qualifier){
        return lookupEntry(source, qualifier)
                .map(QualifiedMapEntry::target);
    }

    public SemanticIdentifier lookupTargetElseFail(
            @Nullable final SemanticIdentifier source,
            @Nullable final SemanticIdentifierSet qualifier){
        return lookupEntryElseFail(source, qualifier).target();
    }

    // -- SERIALIZE

    public String toYaml() {
        return Dtos.toDto(this).toYaml();
    }

    public static Try<QualifiedMap> tryFromYaml(@Nullable final DataSource ds) {
        return QualifiedMapDto.tryFromYaml(ds)
                .mapSuccessAsNullable(Dtos::fromDto);
    }

}
