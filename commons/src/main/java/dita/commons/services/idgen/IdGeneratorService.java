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
package dita.commons.services.idgen;

import java.util.Optional;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.NonNull;

public interface IdGeneratorService {

    /**
     * Optionally returns the next 'free' id for given {@code entityType},
     * based on whether supported.
     */
    <T> Optional<T> next(
            @NonNull Class<T> idType,
            @NonNull Class<?> entityType,
            @Nullable final Object options);

    default <T> T nextElseFail(
            @NonNull final Class<T> idType,
            @NonNull final Class<?> entityType) {
        return nextElseFail(idType, entityType, null);
    }

    default <T> T nextElseFail(
            @NonNull final Class<T> idType,
            @NonNull final Class<?> entityType,
            @Nullable final Object options) {
        return next(idType, entityType, options)
                .orElseThrow(()->_Exceptions.unrecoverable(
                        "Id generation not implemented for entity type %s", entityType));
    }

}
