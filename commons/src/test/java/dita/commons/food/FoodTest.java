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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.commons.food.composition.FoodComposition;
import dita.commons.food.composition.FoodCompositionDatabase;
import dita.commons.food.composition.Nutrient;
import dita.commons.food.composition.NutrientFraction;
import dita.commons.food.composition.NutrientQuantified;
import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumptionAndComposition;
import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMapEntry;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;

class FoodTest {
    
    enum LanguaLFacetCharacteristic {
        FOOD_GROUP,
        FOOD_ORIGIN,
        PHYSICAL_ATTRIBUTES,
        PROCESSING,
        PACKAGING,
        DIETARY_USES,
        GEOGRAPHIC_ORIGIN,
        MISCELLANEOUS_CHARACTERISTICS
    }
    
    @RequiredArgsConstructor
    enum LanguaLFacet {
        /**
         * Derived from a combination of consumption, functional, manufacturing & legal characteristics.
         */
        PRODUCT_TYPE("A"),
        /**
         * Species of plant or animal, or chemical food source.
         */
        FOOD_SOURCE("B"),
        
        /**
         * Part of Plant or Animal.
         */
        PART_OF("C"), 
        /**
         * Physical State, Shape or Form.
         * e.g. liquid, semi-liquid, solid, whole natural shape, divided into pieces
         */
        PHYSICAL_STATE_SHAPE_OR_FORM("E"), 
        /**
         * Extent of Heat Treatment.
         */
        EXTENT_OF_HEAT_TREATMENT("F"), 
        /**
         * Cooked by dry or moist heat; cooked with fat; cooked by microwave.
         */
        COOKING_METHOD("G"),
        /**
         * Additional processing steps, including adding, substituting, or removing components. 
         */
        TREATMENT_APPLIED("H"), 
        /**
         * Any preservation method applied
         */
        PRESERVATION_METHOD("J"),
        PACKING_MEDIUM("K"),
        /**
         * Container material, form, and possibly other characteristics
         */
        CONTAINER_OR_WRAPPING("M"), 
        /**
         * The surface(s) with which the food is in contact. 
         */
        FOOD_CONTACT("N"), 
        /**
         * Human or animal; special dietary characteristics or claims
         */
        CONSUMER_GROUP_OR_DIETARY_USE("P"),
        /**
         * Country of origin, preparation of consumption.
         */
        GEOGRAPHIC_PLACES_AND_REGIONS("R"), 
        /**
         * Additional miscellaneous descriptors.
         */
        ADJUNCT_CHARACTERISTICS("Z"), 
        ;
        @Getter @Accessors(fluent=true)
        final String letterCode;
    }
    
    @Test
    void test() {
        
        var gdId = "AT-GD-2024.05";
        var blsId = "DE-BLS-3.02";

        // setup food consumption
        var gdBanana = new SemanticIdentifier(gdId, "FOOD", "00136"); // Banana
        var gdFacetRaw = new SemanticIdentifier(gdId, LanguaLFacet.COOKING_METHOD.letterCode(), "0399");
        var raw = new SemanticIdentifierSet(Set.of(gdFacetRaw));
        var bananaConsumption = new FoodConsumption(gdBanana, raw, new BigDecimal(64));
        
        // setup food composition database (map)
        var blsBanana = new SemanticIdentifier(blsId, LanguaLFacet.PRODUCT_TYPE.letterCode(), "F503100"); // Banana raw
        var nutZuckerGesamtId = new SemanticIdentifier(blsId, "NUTRIENT", "KMD");
        var nutZuckerGesamt = new Nutrient(nutZuckerGesamtId);  
        
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
                Set.of(new NutrientQuantified(nutZuckerGesamt, 
                        new BigDecimal("17.267").multiply(new BigDecimal("0.64")))), 
                fcac.nutrients());
    }
}
