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
package dita.commons.food.consumption;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodComponentDatapoint;
import dita.commons.food.composition.FoodComponentQuantified;
import dita.commons.food.composition.FoodComposition;
import dita.commons.sid.SemanticIdentifier;

public record FoodConsumptionWithComposition(
        FoodConsumption consumption,
        FoodComposition composition) {

    public Map<SemanticIdentifier, FoodComponentQuantified> quantifiedComponents() {
        return composition.datapoints()
        		.values()
                .stream()
                .collect(Collectors.toMap(FoodComponentDatapoint::componentId, dp->dp.quantify(consumption)));
    }

    public Optional<FoodComponentQuantified> quantifiedComponent(@Nullable final SemanticIdentifier componentId) {
        return Optional.ofNullable(componentId)
                .flatMap(composition::lookupDatapoint)
                .map(datapoint->datapoint.quantify(consumption));
    }

    public Optional<FoodComponentQuantified> quantifiedComponent(@Nullable final FoodComponent foodComponent) {
        return Optional.ofNullable(foodComponent)
                .map(FoodComponent::componentId)
                .flatMap(this::quantifiedComponent);
    }

}
