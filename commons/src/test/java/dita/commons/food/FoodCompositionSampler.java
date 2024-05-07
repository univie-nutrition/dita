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
package dita.commons.food;

import java.math.BigDecimal;
import java.util.Map;

import javax.measure.MetricPrefix;

import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodComponent.ComponentUnit;
import dita.commons.food.composition.FoodComponentDatapoint;
import dita.commons.food.composition.FoodComponentDatapoint.DatapointSemantic;
import dita.commons.food.composition.FoodComposition;
import dita.commons.food.composition.FoodComposition.ConcentrationUnit;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.ontologies.BLS302;
import dita.commons.sid.SemanticIdentifier;

@UtilityClass
class FoodCompositionSampler {

    private SemanticIdentifier blsBananaId = BLS302.id("F503100"); // Banana raw
    // food components may have different units, e.g. GRAM, kcal, etc.
    private FoodComponent blsZuckerGesamt = new FoodComponent(
            BLS302.id("KMD"),
            MetricPrefix.MILLI,
            ComponentUnit.GRAM);

    FoodCompositionRepository createFoodCompositionRepository() {
        var foodCompositionRepo = new FoodCompositionRepository();
        var bananaComposition = new FoodComposition(blsBananaId, ConcentrationUnit.PER_100_GRAM, Map.of(
                blsZuckerGesamt.componentId(),
                datapointPer100gAsIs(blsZuckerGesamt, new BigDecimal("17.267"))));
        foodCompositionRepo.put(bananaComposition);
        return foodCompositionRepo;
    }

    private FoodComponentDatapoint datapointPer100gAsIs(final FoodComponent component, final BigDecimal datapointValue) {
        return new FoodComponentDatapoint(
                component, ConcentrationUnit.PER_100_GRAM, DatapointSemantic.AS_IS, datapointValue);
    }

}
