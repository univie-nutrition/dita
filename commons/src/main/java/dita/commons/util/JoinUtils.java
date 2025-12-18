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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;

import org.jspecify.annotations.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JoinUtils {

    /// always returns an immutable list (non-null)
    /// fails on duplicate detection
    /// @param itemFormatter is used to format the offending items for exception messages
    public <T> List<T> joinAndSortFailOnDuplicates(@Nullable List<T> list1, @Nullable List<T> list2,
            final Comparator<T> comparator, final Function<T, String> itemFormatter) {
        if(list1==null) {
			list1 = List.of();
		}
        if(list2==null) {
			list2 = List.of();
		}

        final var seen = new TreeSet<>(comparator);

        for (T item : list1) {
            if (!seen.add(item)) {
                var duplicate = seen.stream().filter(it->comparator.compare(it, item)==0)
                        .findFirst().orElseThrow();
                throw new IllegalArgumentException("Duplicate found in first list. Offending items:\n  %s\n  %s\n"
                        .formatted(itemFormatter.apply(duplicate), itemFormatter.apply(item)));
            }
        }
        for (T item : list2) {
            if (!seen.add(item)) {
                var duplicate = seen.stream().filter(it->comparator.compare(it, item)==0)
                        .findFirst().orElseThrow();
                throw new IllegalArgumentException("Duplicate found across lists Offending items:\n  %s\n  %s\n"
                        .formatted(itemFormatter.apply(duplicate), itemFormatter.apply(item)));
            }
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

    /// equality by key
    public record DistinctEntry<K, T> (
            K key,
            T firstElementEncountered) {
        @Override public final boolean equals(final Object obj) {
            return obj instanceof DistinctEntry other
                ? Objects.equals(this.key, other.key)
                : false;
        }
        @Override public final int hashCode() {
            return Objects.hashCode(key);
        }
    }

    public static <T, K> Gatherer<T, ?, T> distinct(final Function<T, K> keyExtractor) {
        return Gatherer.<T, Set<DistinctEntry<K, T>>, T>ofSequential(
                LinkedHashSet::new,
                (set, element, _)-> {
                    set.add(new DistinctEntry<>(keyExtractor.apply(element), element));
                    return true;
                },
                (set, downstream) -> set.stream().map(DistinctEntry::firstElementEncountered)
                    .allMatch(downstream::push)); //handles case when downstream stops accepting elements
    }

    public <T, K> DistinctToListCollector<T, K> distinctToList(final Function<T, K> keyExtractor) {
        return new DistinctToListCollector<>(keyExtractor);
    }

    /// elements are distinguished by equality of the key extraction result
    /// result of collecting is an unmodifiable list
    private record DistinctToListCollector<T, K>(
            Function<T, K> keyExtractor) implements Collector<T, Set<DistinctEntry<K, T>>, List<T>> {

        @Override public Supplier<Set<DistinctEntry<K, T>>> supplier() {
            return LinkedHashSet::new;
        }
        @Override public BiConsumer<Set<DistinctEntry<K, T>>, T> accumulator() {
            return (seen, element) -> {
                seen.add(new DistinctEntry<>(keyExtractor.apply(element), element));
            };
        }
        @Override public BinaryOperator<Set<DistinctEntry<K, T>>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }
        @Override public Function<Set<DistinctEntry<K, T>>, List<T>> finisher() {
            return set -> set.stream().map(DistinctEntry::firstElementEncountered).toList();
        }
        @Override public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.UNORDERED); // due to combiner not truly respecting encounter order
        }
    }

}
