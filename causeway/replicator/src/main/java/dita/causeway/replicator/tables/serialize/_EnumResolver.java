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
package dita.causeway.replicator.tables.serialize;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.SneakyThrows;

record _EnumResolver(
        Class<? extends Enum<?>> cls,
        Method enumValueGetter) {

    @SuppressWarnings("unchecked")
    static Optional<_EnumResolver> get(final Class<?> cls, final String enumValueGetter) {
        return cls.isEnum()
                ? lookupEnumValueGetter(cls, enumValueGetter)
                        .map(getter->new _EnumResolver(
                        (Class<? extends Enum<?>>)cls,
                        getter))
                : Optional.empty();
    }

    @Nullable @SneakyThrows
    public Enum<?> resolve(@Nullable final String stringified) {
        for (Enum<?> obj : cls.getEnumConstants()) {
            var matchOn = enumValueGetter.invoke(obj); //nullable
            if(matchOn==null) {
                // fall through
            } else if(matchOn instanceof String s
                    && s.isBlank()) {
                matchOn = null;
            } else {
                matchOn = ""+matchOn;
            }
            if(Objects.equals(_Strings.emptyToNull(stringified), matchOn)) return obj;
        }
        if(stringified==null) return null; // if does not match on null explicitly, then return null as the resolved object
        throw _Exceptions.noSuchElement("enum %s does not match on '%s'", cls, stringified);
    }

    // -- HELPER

    private static Optional<Method> lookupEnumValueGetter(final Class<?> cls, final String enumValueGetter) {
        try {
            return Optional.of(cls.getMethod(enumValueGetter));
        } catch (NoSuchMethodException | SecurityException e) {
            return Optional.empty();
        }
    }

}
