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

import java.util.List;

import dita.commons.food.composition.FoodComposition.ConcentrationUnit;

public record DatapointCompression(ConcentrationUnit compositionQuantification, List<String> data) {
	//TODO _Bytes::compress

//	public static DatapointCompression compress(
//			final ConcentrationUnit compositionQuantification,
//			final Collection<FoodComponentDatapoint> datapoints,
//			final FoodComponentCatalog componentCatalog) {
//		return compress(compositionQuantification, datapoints, ComponentLookup.of(componentCatalog));
//	}
//
//	public static DatapointCompression compress(
//			final ConcentrationUnit compositionQuantification,
//			final Collection<FoodComponentDatapoint> datapoints,
//			final ComponentLookup lookup) {
//		return new DatapointCompression(compositionQuantification, datapoints.stream()
//				.map(dp->Dtos.toDto(lookup.indexFor(dp.component()), dp))
//				.toList());
//	}
//
//	public Map<SemanticIdentifier, FoodComponentDatapoint> extract(final ComponentLookup lookup) {
//		return data.stream()
//			.map(dpPackedAsString->Dtos.fromDto(dpPackedAsString, compositionQuantification, lookup))
//			.collect(JoinUtils.toTreeMap(FoodComponentDatapoint::componentId));
//	}

}
