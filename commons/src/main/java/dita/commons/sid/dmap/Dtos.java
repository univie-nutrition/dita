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

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.commons.io.YamlUtils.YamlLoadCustomizer;

import lombok.experimental.UtilityClass;

import dita.commons.util.FormatUtils;

@UtilityClass
class Dtos {

    // -- DIRECT MAP

    public record DirectMapDto(
            Collection<DirectMapEntry> directMapEntries) {
        public String toYaml() {
            return YamlUtils.toStringUtf8(this, FormatUtils.yamlOptions());
        }
        public static Try<DirectMapDto> tryFromYaml(@Nullable final DataSource ds) {
            if(ds==null) return Try.failure(_Exceptions.noSuchElement("missing datasource"));
            return YamlUtils.tryReadCustomized(
                    DirectMapDto.class, ds, yamlMillionCodePointsLimit(200), FormatUtils.yamlOptions());
        }
    }

    DirectMapDto toDto(final DirectMap map) {
        return new DirectMapDto(
                map.streamEntries()
                    .toList());
    }

    DirectMap fromDto(@Nullable final DirectMapDto dto) {
        if(dto==null) return null;
        var map = new DirectMap(new ConcurrentHashMap<>());
        dto.directMapEntries.stream()
            .forEach(map::put);
        return map;
    }

    // -- HELPER

    private YamlLoadCustomizer yamlMillionCodePointsLimit(final int millions) {
        return loader->{
            loader.setCodePointLimit(millions * 1_000_000);
        };
    }

}
