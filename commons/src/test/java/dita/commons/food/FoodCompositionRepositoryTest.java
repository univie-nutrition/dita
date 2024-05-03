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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.causeway.commons.io.DataSource;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.ontologies.BLS302;

class FoodCompositionRepositoryTest {

    @Test
    void roundtrip() {
        // setup food consumption
        var foodCompositionRepoOrig = FoodCompositionSampler.createFoodCompositionRepository();
        var yaml = foodCompositionRepoOrig.toYaml();

        System.err.printf("BEFORE%n%s%n", yaml);

        var foodCompositionRepo = FoodCompositionRepository.tryFromYaml(DataSource.ofStringUtf8(yaml))
                .valueAsNonNullElseFail();

        System.err.printf("AFTER%n%s%n", foodCompositionRepo.toYaml());

        final FoodComponent blsZuckerGesamt = foodCompositionRepo.componentCatalog()
                .lookupEntryElseFail(BLS302.DietaryDataCategory.CARBOHYDRATES.componentId("KMD"));

        assertNotNull(blsZuckerGesamt);
    }

}
