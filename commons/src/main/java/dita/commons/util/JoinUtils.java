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
package dita.commons.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JoinUtils {

    /// always returns an immutable list (non-null)
    /// fails on duplicate detection
    public <T> List<T> joinAndSortFailOnDuplicates(@Nullable List<T> list1, @Nullable List<T> list2, final Comparator<T> comparator) {
        if(list1==null) {
			list1 = List.of();
		}
        if(list2==null) {
			list2 = List.of();
		}

        final var seen = new TreeSet<>(comparator);

        for (T item : list1) {
            if (!seen.add(item))
                throw new IllegalArgumentException("Duplicate found in first list: " + item);
        }
        for (T item : list2) {
            if (!seen.add(item))
                throw new IllegalArgumentException("Duplicate found across lists: " + item);
        }

        return seen.stream().toList();
    }

    public <T, K>
    Collector<T, ?, Map<K,T>> toTreeMap(final Function<? super T, ? extends K> keyMapper) {
    	return Collectors.toMap(keyMapper, UnaryOperator.identity(), uniqueKeysMapMerger(), TreeMap::new);
    }

    public <T, K, U>
    Collector<T, ?, Map<K,U>> toTreeMap(final Function<? super T, ? extends K> keyMapper,
                                    final Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, uniqueKeysMapMerger(), TreeMap::new);
    }

    public <T> BinaryOperator<T> uniqueKeysMapMerger() {
		return (a, b)-> {throw new IllegalStateException("Duplicate key attempted merging values %s and %s".formatted(a, b)); };
    }
}
