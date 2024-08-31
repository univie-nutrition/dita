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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodComponent.ComponentUnit;
import dita.commons.food.composition.FoodComponentQuantified;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.food.consumption.FoodConsumptionWithComposition;
import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;

class FoodTest {

    private SemanticIdentifier blsBananaId = SemanticIdentifier.parse("de.bls/3.02", "food/F503100"); // Banana raw

    @Test
    void qMapCreation() {

        // setup food consumption
        var bananaConsumption = createFoodConsumption();
        var foodCompositionRepo = FoodCompositionSampler.createFoodCompositionRepository();
        //debug
        //System.err.printf("%s%n", foodCompositionRepo.toYaml());

        final FoodComponent blsZuckerGesamt = foodCompositionRepo.componentCatalog()
                .lookupEntryElseFail(SemanticIdentifier.parse("de.bls/3.02", "comp/KMD"));

        // setup nutrient mapping
        final QualifiedMap qMap = createQMap();

        // verify lookups
        assertEquals(
                Optional.empty(),
                qMap.lookupTarget(bananaConsumption.foodId(), SemanticIdentifierSet.empty()));

        assertEquals(
                Optional.of(blsBananaId),
                qMap.lookupTarget(bananaConsumption.foodId(), bananaConsumption.facetIds()));

        var bananaComposition = foodCompositionRepo.lookupEntryElseFail(blsBananaId);

        var fcac = new FoodConsumptionWithComposition(bananaConsumption, bananaComposition);

        assertEquals(
                Map.of(
                        blsZuckerGesamt.componentId(),
                        new FoodComponentQuantified(blsZuckerGesamt, ComponentUnit.GRAM.quantity(
                        new BigDecimal("17.267").multiply(new BigDecimal("0.64"))))),
                fcac.quantifiedComponents());
        assertEquals(
                Optional.of(new FoodComponentQuantified(blsZuckerGesamt, ComponentUnit.GRAM.quantity(
                        new BigDecimal("17.267").multiply(new BigDecimal("0.64"))))),
                fcac.quantifiedComponent(blsZuckerGesamt));
    }

    @Test
    void qMapRoundtrip() {
        // setup nutrient mapping
        final QualifiedMap qMap = createQMap();
        var yaml = qMap.toYaml();
        System.err.printf("%s%n", yaml);
        var qMapAfterRoundtrip = QualifiedMap.tryFromYaml(DataSource.ofStringUtf8(yaml))
                .valueAsNonNullElseFail();

        //debug
        System.err.printf("%s%n", yaml);

        assertEquals(qMap, qMapAfterRoundtrip);
    }

    // -- HELPER

    QualifiedMap createQMap() {
        // setup food consumption
        var bananaConsumption = createFoodConsumption();
        // setup nutrient mapping
        final QualifiedMap qMap = new QualifiedMap(new HashMap<>());
        qMap.put(new QualifiedMapEntry(bananaConsumption.foodId(), bananaConsumption.facetIds(), blsBananaId));
        return qMap;
    }

    FoodConsumption createFoodConsumption() {
        var gdBanana = SemanticIdentifier.parse("at.gd/2.0", "food/00136"); // Banana
        var gdFacetRaw = SemanticIdentifier.parse("at.gd/2.0", "fd/0399");
        var facets = new SemanticIdentifierSet(Can.of(gdFacetRaw));
        var bananaConsumption = new FoodConsumption("Banana",  gdBanana, facets, ConsumptionUnit.GRAM, new BigDecimal(64));
        return bananaConsumption;
    }

}
