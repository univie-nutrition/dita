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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.commons.internal.assertions._Assert;

import dita.commons.food.composition.FoodComponentDatapoint.DatapointSemantic;
import dita.commons.food.composition.FoodComposition.ConcentrationUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.util.NumberUtils;

public class DatapointMap {

	public static @NonNull DatapointMap of(final Collection<FoodComponentDatapoint> datapoints) {
		var dpm = new DatapointMap();
		if(datapoints!=null) {
			datapoints.forEach(dpm::put);
		}
		return dpm;
	}
	public static Collector<FoodComponentDatapoint, ?, DatapointMap> collector() {
		return new Collector<FoodComponentDatapoint, DatapointMap, DatapointMap>(){
			@Override public Supplier<DatapointMap> supplier() {
				return DatapointMap::new;
			}
			@Override public BiConsumer<DatapointMap, FoodComponentDatapoint> accumulator() {
				return DatapointMap::put;
			}
			@Override public BinaryOperator<DatapointMap> combiner() {
				return (a, b) -> (a.size()>b.size() ? a.join(b) : b.join(a));
			}
			@Override public Function<DatapointMap, DatapointMap> finisher() {
				return UnaryOperator.identity();
			}
			@Override public Set<Characteristics> characteristics() {
				return Set.of(Characteristics.UNORDERED);
			}
		};
	}

	public DatapointMap join(final DatapointMap other) {
		if(other==null || other.size()==0) return this;
		other.values().forEach(this::put);
		return this;
	}

	private record FoodComponentDatapointCompressed(
			//FoodComponentDatapoint orig,
	        FoodComponent component,
	        /// bit 63: DatapointSemantic
	        /// bit 61..62: ConcentrationUnit
	        /// bit 60: scale sign (1=negative)
	        /// bit 56..59: unsigned scale (4 bit)
	        /// bit 55: value sign (1=negative)
	        /// bit 0..54: unsigned value (55 bit)
	        long packedSemanticUnitAndScale) {

		static FoodComponentDatapointCompressed compress(final FoodComponentDatapoint dp) {
			return new FoodComponentDatapointCompressed( //dp,
					dp.component(),
	                pack(dp.concentrationUnit())
	                | pack(dp.datapointSemantic())
	                | pack(dp.datapointValue())
	                );
		}
		FoodComponentDatapoint uncompress() {
			var res =  new FoodComponentDatapoint(component,
					unpackConcentrationUnit(packedSemanticUnitAndScale),
					unpackDatapointSemantic(packedSemanticUnitAndScale),
					unpackValue(packedSemanticUnitAndScale));
//			_Assert.assertEquals(orig.component(), res.component());
//			_Assert.assertEquals(orig.concentrationUnit(), res.concentrationUnit());
//			_Assert.assertEquals(orig.datapointSemantic(), res.datapointSemantic());
//			_Assert.assertNumberEquals(orig.datapointValue().doubleValue(), res.datapointValue().doubleValue(), 1E-3, ()->"%d:%d"
//				.formatted(orig.datapointValue().scale(),
//						res.datapointValue().scale()));

			return res;
		}
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

	private final Map<SemanticIdentifier, FoodComponentDatapointCompressed> delegate = new TreeMap<>();

	public int size() {
		return delegate.size();
	}

	public Optional<FoodComponentDatapoint> lookup(final SemanticIdentifier componentId) {
		return Optional.ofNullable(delegate.get(componentId))
				.map(FoodComponentDatapointCompressed::uncompress);
	}

	public void put(final FoodComponentDatapoint value) {
		delegate.put(value.componentId(), FoodComponentDatapointCompressed.compress(value));
	}

	public Collection<FoodComponentDatapoint> values() {
		return delegate.values().stream()
				.map(FoodComponentDatapointCompressed::uncompress)
				.toList();
	}

}
