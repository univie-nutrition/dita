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

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/// typed 2-tuple
public record Pair<L, R>(L left, R right) {

    // -- FACTORIES

    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }

    // -- UTILITY

    public static <L, R> Map<L, R> toMap(
            final @Nullable Iterable<Pair<L, R>> iterable,
            final @NonNull Supplier<Map<L, R>> mapFactory) {
        if(iterable==null) return Collections.emptyMap();
        Map<L, R> map = null;
        for(var pair : iterable) {
            if(map==null) map = mapFactory.get();
            map.put(pair.left(), pair.right());
        }
        return map!=null
            ? map
            : Collections.emptyMap();
    }

    public static <L, R> Map<L, R> toUnmodifiableMap(
            final @Nullable Iterable<Pair<L, R>> iterable,
            final @NonNull Supplier<Map<L, R>> mapFactory) {
        var map = toMap(iterable, mapFactory);
        return map.isEmpty()
            ? Collections.emptyMap()
            : Collections.unmodifiableMap(map);
    }

}
