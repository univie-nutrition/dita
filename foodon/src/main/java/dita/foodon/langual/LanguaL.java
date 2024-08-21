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
package dita.foodon.langual;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier;

@UtilityClass
public class LanguaL {

    public enum FacetCharacteristic {
        FOOD_GROUP,
        FOOD_ORIGIN,
        PHYSICAL_ATTRIBUTES,
        PROCESSING,
        PACKAGING,
        DIETARY_USES,
        GEOGRAPHIC_ORIGIN,
        MISCELLANEOUS_CHARACTERISTICS
    }

    @Getter @Accessors(fluent=true)
    @RequiredArgsConstructor
    public enum Facet {
        /**
         * Derived from a combination of consumption, functional, manufacturing & legal characteristics.
         */
        PRODUCT_TYPE("A", FacetCharacteristic.FOOD_GROUP),
        /**
         * Species of plant or animal, or chemical food source.
         */
        FOOD_SOURCE("B", FacetCharacteristic.FOOD_ORIGIN),

        /**
         * Part of Plant or Animal.
         */
        PART_OF("C", FacetCharacteristic.FOOD_ORIGIN),
        /**
         * Physical State, Shape or Form.
         * e.g. liquid, semi-liquid, solid, whole natural shape, divided into pieces
         */
        PHYSICAL_STATE_SHAPE_OR_FORM("E", FacetCharacteristic.PHYSICAL_ATTRIBUTES),
        /**
         * Extent of Heat Treatment.
         */
        EXTENT_OF_HEAT_TREATMENT("F", FacetCharacteristic.PROCESSING),
        /**
         * Cooked by dry or moist heat; cooked with fat; cooked by microwave.
         */
        COOKING_METHOD("G", FacetCharacteristic.PROCESSING),
        /**
         * Additional processing steps, including adding, substituting, or removing components.
         */
        TREATMENT_APPLIED("H", FacetCharacteristic.PROCESSING),
        /**
         * Any preservation method applied
         */
        PRESERVATION_METHOD("J", FacetCharacteristic.PROCESSING),
        PACKING_MEDIUM("K", FacetCharacteristic.PACKAGING),
        /**
         * Container material, form, and possibly other characteristics
         */
        CONTAINER_OR_WRAPPING("M", FacetCharacteristic.PACKAGING),
        /**
         * The surface(s) with which the food is in contact.
         */
        FOOD_CONTACT("N", FacetCharacteristic.PACKAGING),
        /**
         * Human or animal; special dietary characteristics or claims
         */
        CONSUMER_GROUP_OR_DIETARY_USE("P", FacetCharacteristic.DIETARY_USES),
        /**
         * Country of origin, preparation of consumption.
         */
        GEOGRAPHIC_PLACES_AND_REGIONS("R", FacetCharacteristic.GEOGRAPHIC_ORIGIN),
        /**
         * Additional miscellaneous descriptors.
         */
        ADJUNCT_CHARACTERISTICS("Z", FacetCharacteristic.MISCELLANEOUS_CHARACTERISTICS),
        ;

        final String letterCode;
        final FacetCharacteristic characteristic;

        public SemanticIdentifier facetId(final String systemId, final String facetTermCode) {
            return new SemanticIdentifier(systemId, facetTermCode);
        }
    }

}
