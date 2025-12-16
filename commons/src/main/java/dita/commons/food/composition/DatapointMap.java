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
import java.math.BigInteger;
import java.math.RoundingMode;
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
import java.util.stream.IntStream;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.assertions._Assert;

import lombok.RequiredArgsConstructor;

import dita.commons.food.composition.FoodComponentDatapoint.DatapointSemantic;
import dita.commons.food.composition.FoodComposition.ConcentrationUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.util.NumberUtils;

@RequiredArgsConstructor
public final class DatapointMap {

	public static @NonNull DatapointMap empty() {
		return new DatapointMap(null, null, Collections.emptyMap());
	}

	public static @NonNull DatapointMap of(final Collection<FoodComponentDatapoint> datapoints) {
		return datapoints!=null
			? datapoints.stream().collect(DatapointMap.collector())
			: DatapointMap.empty();
	}

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
			return map -> {
				var comps = new FoodComponent[map.size()];
				var data = new long[map.size()];
				final Map<SemanticIdentifier, Integer> toIndex = new TreeMap<>();
				map.values().forEach(IndexedConsumer.zeroBased((i, dp)->{
					comps[i] = dp.component();
					data[i] = FoodComponentDatapointCompressor.compress(dp);
					toIndex.put(dp.componentId(), i);
				}));
				return new DatapointMap(comps, data, toIndex);
			};
		}
		@Override public Set<Characteristics> characteristics() {
			return Set.of(Characteristics.UNORDERED);
		}
	}

	private record FoodComponentDatapointCompressor() {

        /// bit 63: DatapointSemantic
        /// bit 61..62: ConcentrationUnit
        /// bit 60: scale sign (1=negative)
        /// bit 56..59: unsigned scale (4 bit)
        /// bit 55: value sign (1=negative)
        /// bit 0..54: unsigned value (55 bit)
		static long compress(final FoodComponentDatapoint dp) {
			return pack(dp.concentrationUnit())
	                | pack(dp.datapointSemantic())
	                | pack(dp.datapointValue());
		}
		static FoodComponentDatapoint uncompress(final FoodComponent component, final long packedSemanticUnitAndScale) {
			return new FoodComponentDatapoint(component,
					unpackConcentrationUnit(packedSemanticUnitAndScale),
					unpackDatapointSemantic(packedSemanticUnitAndScale),
					unpackValue(packedSemanticUnitAndScale));
		}
//			_Assert.assertEquals(orig.component(), res.component());
//			_Assert.assertEquals(orig.concentrationUnit(), res.concentrationUnit());
//			_Assert.assertEquals(orig.datapointSemantic(), res.datapointSemantic());
//			_Assert.assertNumberEquals(orig.datapointValue().doubleValue(), res.datapointValue().doubleValue(), 1E-3, ()->"%d:%d"
//				.formatted(orig.datapointValue().scale(),
//						res.datapointValue().scale()));
	    private static long pack(final DatapointSemantic sem) {
	        return (1L & sem.ordinal()) << 63;
	    }
	    private static DatapointSemantic unpackDatapointSemantic(final long packed) {
	    	return hasBit(63, packed) ? DatapointSemantic.UPPER_BOUND : DatapointSemantic.AS_IS;
	    }
	    private static long pack(final ConcentrationUnit unit) {
	        return (3L & unit.ordinal()) << 61;
	    }
	    private static ConcentrationUnit unpackConcentrationUnit(final long packed) {
	    	return ConcentrationUnit.values()[(int)((packed>>>61) & 3L)];
	    }
	    private final static long VALUE_MASK = (1L<<55)-1L;
	    private final static BigInteger UNSCALED_LIMIT = BigInteger.valueOf((VALUE_MASK));
	    private static long pack(final BigDecimal value) {
	    	if(value.signum()==0) return 0L;
	    	var adjustedValue = NumberUtils.roundToFitUnscaledLimit(value.abs(), UNSCALED_LIMIT);
	    	if(adjustedValue.scale()>15) {
				adjustedValue = adjustedValue.setScale(15, RoundingMode.HALF_UP);
			} else if(adjustedValue.scale() < -15) {
				adjustedValue = adjustedValue.setScale(-15, RoundingMode.HALF_UP);
			}
			long mask= adjustedValue.unscaledValue().longValueExact();
			if(value.signum()<0) {
				mask|=1L<<55; // sign bit
			}
			if(adjustedValue.scale()<0) {
				mask|= 1L<<60;
			}
			_Assert.assertTrue(Math.abs(adjustedValue.scale())<16, ()->"scale overflow");
			mask|= (0xfL & Math.abs(adjustedValue.scale()))<<56;
			return mask;
	    }
	    private static BigDecimal unpackValue(final long packed) {
	    	boolean isNegativeScale = hasBit(60, packed);
	    	int unsignedScale = (int)((packed>>>56) & 0xfL);
	    	boolean isNegativeValue = hasBit(55, packed);
	    	long unsignedUnscaledValue = VALUE_MASK & packed;
	        return BigDecimal.valueOf(
	        		isNegativeValue ? -unsignedUnscaledValue : unsignedUnscaledValue,
    				isNegativeScale ? -unsignedScale : unsignedScale);
	    }
	    private static boolean hasBit(final int index, final long x){
			return (x & (1L<<index)) != 0L;
		}
	}

	private final FoodComponent[] foodComponents;
	private final long[] data;
	private final Map<SemanticIdentifier, Integer> toIndex;

	public int size() {
		return toIndex.size();
	}

	public Optional<FoodComponentDatapoint> lookup(final SemanticIdentifier componentId) {
		return Optional.ofNullable(toIndex.get(componentId))
				.map(i->FoodComponentDatapointCompressor.uncompress(foodComponents[i], data[i]));
	}

	public Collection<FoodComponentDatapoint> values() {
		return IntStream.range(0, size())
				.mapToObj(i->FoodComponentDatapointCompressor.uncompress(foodComponents[i], data[i]))
				.toList();
	}

}
