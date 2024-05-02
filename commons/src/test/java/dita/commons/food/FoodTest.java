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
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dita.commons.food.composition.FoodComposition;
import dita.commons.food.composition.FoodCompositionDatabase;
import dita.commons.food.composition.Nutrient;
import dita.commons.food.composition.Nutrient.ComponentUnit;
import dita.commons.food.composition.NutrientFraction;
import dita.commons.food.composition.NutrientQuantified;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumptionAndComposition;
import dita.commons.langual.LanguaL;
import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;

class FoodTest {

    @Test
    void test() {

        var gdId = "AT-GD-2024.05";
        var blsId = "DE-BLS-3.02";

        // setup food consumption
        var gdBanana = LanguaL.foodId(gdId, "00136"); // Banana
        var gdFacetRaw = LanguaL.Facet.COOKING_METHOD.facetId(gdId, "0399");

        var raw = new SemanticIdentifierSet(Set.of(gdFacetRaw));
        var bananaConsumption = new FoodConsumption(gdBanana, raw, new BigDecimal(64));

        // setup food composition database (map)
        var blsBanana = LanguaL.foodId(blsId, "F503100"); // Banana raw
        var nutZuckerGesamtId = new SemanticIdentifier(blsId, "NUTRIENT", "KMD");

        //TODO food components may have different units, e.g. supplements components given in per PART
        var nutZuckerGesamt = new Nutrient(nutZuckerGesamtId, ComponentUnit.GRAM);

        var bananaComposition = new FoodComposition(blsBanana, Set.of(
                new NutrientFraction(nutZuckerGesamt, new BigDecimal("17.267"))));

        var fcdb = new FoodCompositionDatabase();
        fcdb.put(bananaComposition);

        var qMap = new QualifiedMap();
        var qMapEntry = new QualifiedMapEntry(gdBanana, raw, blsBanana);
        qMap.put(qMapEntry);

        // verify lookups
        assertEquals(
                Optional.empty(),
                qMap.lookup(gdBanana, SemanticIdentifierSet.empty()));

        assertEquals(
                Optional.of(blsBanana),
                qMap.lookup(gdBanana, raw));

        assertEquals(
                Optional.of(bananaComposition),
                fcdb.lookupEntry(blsBanana));

        var fcac = new FoodConsumptionAndComposition(bananaConsumption, bananaComposition);

        assertEquals(
                Set.of(new NutrientQuantified(nutZuckerGesamt, ComponentUnit.GRAM.quantity(
                        new BigDecimal("17.267").multiply(new BigDecimal("0.64"))))),
                fcac.nutrients());
    }
}
