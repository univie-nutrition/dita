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
package dita.commons.food.composition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.collections._Multimaps;
import org.apache.causeway.commons.internal.collections._Multimaps.ListMultimap;

import lombok.RequiredArgsConstructor;

import dita.commons.sid.SemanticIdentifier;

@RequiredArgsConstructor
public final class DatapointMap {

	public static @NonNull DatapointMap empty() {
		return new DatapointMap(Collections.emptyMap());
	}

	public static @NonNull DatapointMap of(final Collection<FoodComponentDatapoint> datapoints) {
		return datapoints!=null
			? datapoints.stream().collect(DatapointMap.collector())
			: DatapointMap.empty();
	}

	private final Map<SemanticIdentifier, FoodComponentDatapoint> delegate;

	public int size() {
		return delegate.size();
	}

	public Optional<FoodComponentDatapoint> lookup(final SemanticIdentifier componentId) {
		return Optional.ofNullable(delegate.get(componentId));
	}

	public Stream<FoodComponentDatapoint> values() {
		return delegate.values().stream();
	}

	// -- COLLECTORS

	public static Collector<FoodComponentDatapoint, ?, DatapointMap> collector() {
	    return new DatapointCollector();
	}

	private record DatapointCollector()
	implements Collector<FoodComponentDatapoint, Map<SemanticIdentifier, FoodComponentDatapoint>, DatapointMap> {
	    @Override public Supplier<Map<SemanticIdentifier, FoodComponentDatapoint>> supplier() {
	        return TreeMap::new;
	    }
	    @Override public BiConsumer<Map<SemanticIdentifier, FoodComponentDatapoint>, FoodComponentDatapoint> accumulator() {
	        return (map, dp) -> map.put(dp.componentId(), dp);
	    }
	    @Override public BinaryOperator<Map<SemanticIdentifier, FoodComponentDatapoint>> combiner() {
	        return (a, b) -> {
	            if(a.size()>b.size()) {
	                a.putAll(b); return a;
	            } else {
	                b.putAll(a); return b;
	            }};
	    }
	    @Override public Function<Map<SemanticIdentifier, FoodComponentDatapoint>, DatapointMap> finisher() {
	        return DatapointMap::new;
	    }
	    @Override public Set<Characteristics> characteristics() {
	        return Set.of(Characteristics.UNORDERED);
	    }
	}

	/// allows for multiple data points to reference the same component, in which case all values are summed up
	/// useful for transformations and formulas
    public static Collector<FoodComponentDatapoint, ?, DatapointMap> summingCollector() {
        return new DatapointSummingCollector();
    }

    private record DatapointSummingCollector()
    implements Collector<FoodComponentDatapoint, ListMultimap<SemanticIdentifier, FoodComponentDatapoint>, DatapointMap> {
        @Override public Supplier<ListMultimap<SemanticIdentifier, FoodComponentDatapoint>> supplier() {
            return ()->_Multimaps.newListMultimap(TreeMap::new, ArrayList::new);
        }
        @Override public BiConsumer<ListMultimap<SemanticIdentifier, FoodComponentDatapoint>, FoodComponentDatapoint> accumulator() {
            return (map, dp) -> map.putElement(dp.componentId(), dp);
        }
        @Override public BinaryOperator<ListMultimap<SemanticIdentifier, FoodComponentDatapoint>> combiner() {
            return (a, b) -> {
                if(a.size()>b.size()) {
                    a.putAll(b); return a;
                } else {
                    b.putAll(a); return b;
                }};
        }
        @Override public Function<ListMultimap<SemanticIdentifier, FoodComponentDatapoint>, DatapointMap> finisher() {
            return multimap->new DatapointMap(summingReduction(multimap));
        }
        @Override public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.UNORDERED);
        }
        private Map<SemanticIdentifier, FoodComponentDatapoint> summingReduction(
                final ListMultimap<SemanticIdentifier, FoodComponentDatapoint> multimap) {
            var res = new TreeMap<SemanticIdentifier, FoodComponentDatapoint>();

            multimap.entrySet()
                .stream()
                .forEach(e->{
                   var componentId = e.getKey();
                   var dps = e.getValue();

                   var dp = dps.size() == 1
                       ? dps.get(0)
                       : new FoodComponentDatapointSum().addAll(dps);

                   res.put(componentId, dp);

                });
            return res;
        }
        private final class FoodComponentDatapointSum {
            private BigDecimal sum = BigDecimal.ZERO;
            private FoodComponentDatapoint first = null;
            private void add(final FoodComponentDatapoint next) {
                if(first == null) {
                    this.first = next;
                    this.sum = first.datapointValue();
                    return;
                }
                _Assert.assertEquals(first.component(), next.component());
                _Assert.assertEquals(first.concentrationUnit(), next.concentrationUnit());
                _Assert.assertEquals(first.datapointSemantic(), next.datapointSemantic());
                this.sum = sum.add(next.datapointValue());
            }
            FoodComponentDatapoint addAll(final Iterable<FoodComponentDatapoint> dps) {
                dps.forEach(this::add);
                return new FoodComponentDatapoint(
                        first.component(),
                        first.concentrationUnit(),
                        first.datapointSemantic(),
                        sum.stripTrailingZeros());
            }
        }
    }

}
