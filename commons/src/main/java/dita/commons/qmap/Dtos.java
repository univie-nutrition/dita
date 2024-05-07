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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.JsonUtils.JacksonCustomizer;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.commons.io.YamlUtils.YamlLoadCustomizer;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import dita.commons.jaxb.JaxbAdapters;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;

@UtilityClass
class Dtos {

    // -- QUALIFIED MAP ENTRY

    public record QualifiedMapEntryDto(
            @NonNull SemanticIdentifier source,
            @Nullable List<SemanticIdentifier> qualifier,
            @NonNull SemanticIdentifier target) {
    }

    QualifiedMapEntryDto toDto(final QualifiedMapEntry entry) {
        return new QualifiedMapEntryDto(
                entry.source(),
                entry.qualifier()!=null
                    ? entry.qualifier().elements().toList()
                    : null,
                entry.target());
    }

    QualifiedMapEntry fromDto(final QualifiedMapEntryDto dto) {
        var qualifier = new LinkedHashSet<SemanticIdentifier>();
        _NullSafe.stream(dto.qualifier())
            .forEach(qualifier::add);
        return new QualifiedMapEntry(
                dto.source,
                SemanticIdentifierSet.ofCollection(dto.qualifier),
                dto.target);
    }

    // -- QUALIFIED MAP

    public record QualifiedMapDto(
            Collection<QualifiedMapEntry> qualifiedMapEntries) {
        public String toYaml() {
            return YamlUtils.toStringUtf8(this, yamlOptions());
        }
        public static Try<QualifiedMapDto> tryFromYaml(@Nullable final DataSource ds) {
            if(ds==null) return Try.failure(_Exceptions.noSuchElement("missing datasource"));
            return YamlUtils.tryReadCustomized(
                    QualifiedMapDto.class, ds, yamlMillionCodePointsLimit(200), yamlOptions());
        }
    }

    QualifiedMapDto toDto(final QualifiedMap map) {
        return new QualifiedMapDto(
                map.streamEntries().toList());
    }

    QualifiedMap fromDto(@Nullable final QualifiedMapDto dto) {
        if(dto==null) return null;
        var map = new QualifiedMap(new ConcurrentHashMap<>());
        dto.qualifiedMapEntries.forEach(map::put);
        return map;
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
