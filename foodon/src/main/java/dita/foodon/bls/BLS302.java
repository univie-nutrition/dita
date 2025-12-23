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
package dita.foodon.bls;

import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier;
import dita.foodon.systems.FcdbSystem;

/**
 * German's Bundeslebenmittelschlüssel (BLS 3.02).
 */
@UtilityClass
public class BLS302 {

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

        public static DietaryDataCategory parse(final String blsCode){
            if(StringUtils.hasLength(blsCode)) {
                switch (blsCode) {
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
            }
            throw _Exceptions.illegalArgument("Unknown dietary data category blsCode '%s'", blsCode);
        }
    }

    public static Stream<Component> streamComponents(){
        return Stream.of(Component.values());
    }

    public enum Component {
        GCAL, GJ, GCALZB, GJZB, ZW, ZE, ZF, ZK, ZB, ZM, ZO, ZA, VA, VAR, VAC, VD, VE, VEAT, VK, VB1, VB2, VB3, VB3A, VB5,
        VB6, VB7, VB9G, VB12, VC, MNA, MK, MCA, MMG, MP, MS, MCL, MFE, MZN, MCU, MMN, MF, MJ, KAM, KAS, KAX, KA, KMT, KMF,
        KMG, KM, KDS, KDM, KDL, KD, KMD, KPOR, KPON, KPG, KPS, KP, KBP, KBH, KBU, KBC, KBL, KBW, KBN, EILE, ELEU, ELYS,
        EMET, ECYS, EPHE, ETYR, ETHR, ETRP, EVAL, EARG, EHIS, EEA, EALA, EASP, EGLU, EGLY, EPRO, ESER, ENA, EH, EP, F40,
        F60, F80, F100, F120, F140, F150, F160, F170, F180, F200, F220, F240, FS, F141, F151, F161, F171, F181, F201, F221,
        F241, FU, F162, F164, F182, F183, F184, F193, F202, F203, F204, F205, F222, F223, F224, F225, F226, FP, FK, FM, FL,
        FO3, FO6, FG, FC, GFPS, GKB, GMKO, GP;
        public SemanticIdentifier componentId() {
            return FcdbSystem.BLS302.component(name());
        }
    }

}
