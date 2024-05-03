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
package dita.commons.ontologies;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier;

/**
 * German's Bundeslebenmittelschlüssel (BLS 3.02).
 */
@UtilityClass
public class BLS302 {

    public static final String SYSTEM_ID = "BLS-3.02";

    @RequiredArgsConstructor
    public enum DietaryDataCategory {
        /** Amino Acids (Aminosäuren)*/
        AMINO_ACIDS("E"),
        /** Fatty Acids (Fettsäuren)*/
        FATTY_ACIDS("F"),
        /** Summary Numbers (Gesamtkennzahlen)*/
        SUMMARY_NUMBERS("G"),
        /** Carbohydrates (Kohlenhydrate)*/
        CARBOHYDRATES("K0"),
        /** Fibers (Ballaststoffe)*/
        FIBERS("K1"),
        /** Mineral Nutrients (Mineralstoffe)*/
        MINERAL_NUTRIENTS("M0"),
        /** Micro Nutrients (Spurenelemente)*/
        MICRO_NUTRIENTS("M1"),
        /** Dietary Supplements (Nahrungsergänzungsmittel)*/
        DIETARY_SUPPLEMENTS("S"),
        /** Vitamins (Vitamine)*/
        VITAMINS("V"),
        /** Composition (Zusammensetzung)*/
        COMPOSITION("Z");

        final String blsCode;

        public SemanticIdentifier componentId(final String objectId) {
            return new SemanticIdentifier(SYSTEM_ID, blsCode, objectId);
        }

        public static DietaryDataCategory parse(final String blsCode){
            if(StringUtils.hasLength(blsCode)) switch (blsCode) {
            case "E": return AMINO_ACIDS;
            case "F": return FATTY_ACIDS;
            case "G": return SUMMARY_NUMBERS;
            case "K0": return CARBOHYDRATES;
            case "K1": return FIBERS;
            case "M0": return MINERAL_NUTRIENTS;
            case "M1": return MICRO_NUTRIENTS;
            case "S": return DIETARY_SUPPLEMENTS;
            case "V": return VITAMINS;
            case "Z": return COMPOSITION;
            default:
            }
            throw _Exceptions.illegalArgument("Unknown dietary data category blsCode '%s'", blsCode);
        }

    }

    public SemanticIdentifier foodId(final String objectId) {
        return new SemanticIdentifier(SYSTEM_ID, null, objectId);
    }

}
