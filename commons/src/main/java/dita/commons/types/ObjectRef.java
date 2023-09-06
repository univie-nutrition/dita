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
package dita.commons.types;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Specifically designed to be used with Java record types,
 * to allow circular references such as parent child relations.
 * <p>
 * Nullable mutable variant of {@link SneakyRef}
 *
 * @see SneakyRef
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE) @AllArgsConstructor
public final class ObjectRef<T> {

    public static <T> ObjectRef<T> of(final @NonNull T value) {
        return new ObjectRef<>(value);
    }

    public static <T> ObjectRef<T> empty() {
        return new ObjectRef<>();
    }

    @Getter @Setter
    private T value;

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ObjectRef;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return value==null
                ? "ObjectRef[]"
                : value.getClass().isPrimitive()
                    ? value.toString()
                    : String.format("ObjectRef[%s]", value.getClass().getSimpleName());
    }

}
