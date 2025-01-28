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
package dita.recall24.reporter.tabular;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodComposition;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.types.DecimalVector;

record NutrientVectorFactory(Can<FoodComponent> foodComponents) {

    DecimalVector get(
            final FoodConsumption foodConsumption,
            @Nullable final FoodComposition compositionEntry) {
        if(compositionEntry==null) return DecimalVector.empty();

        var decimals = new double[foodComponents.size()];

        foodComponents.forEach(IndexedConsumer.zeroBased((i, comp)->{
            decimals[i] = compositionEntry.lookupDatapoint(comp.componentId())
                .map(dp->dp.quantifyAsDouble(foodConsumption))
                .orElse(0.);
        }));

        return new DecimalVector(decimals);
    }

}
