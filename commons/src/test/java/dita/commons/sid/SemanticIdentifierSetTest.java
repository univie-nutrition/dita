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
package dita.commons.sid;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;

import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;

class SemanticIdentifierSetTest {

    final static String COMPLEX_NAME =
            """
            &^?zu`rüc'k "zu",/ karl, ovačko!""";
    final static String COMPLEX_NAME_QUOT =
            "‹" + COMPLEX_NAME + "›";

    @RequiredArgsConstructor
    enum ValidScenario {
        EMPTY_SID(null, null, null, null,
                ""),
        SIMPLE_SID("global", null, null, "none",
                "SID[global:none], "
                + "SID[global:none], "
                + "SID[global:none]"),
        FULL_SID("de.bls", "3.02", "comp", "GCAL",
                "SID[de.bls/3.02:comp/GCAL], "
                + "SID[de.bls/3.02:comp/GCAL], "
                + "SID[de.bls/3.02:comp/GCAL]"),
        BRAND(null, null, "brand", COMPLEX_NAME,
                "SID[:brand/" + COMPLEX_NAME_QUOT + "], "
                + "SID[:brand/" + COMPLEX_NAME_QUOT + "], "
                + "SID[:brand/" + COMPLEX_NAME_QUOT + "]"),
        ;
        final String system;
        final String version;
        final String context;
        final String object;
        final String stringified;
        public SemanticIdentifierSet sidSet() {
            var sid = new SemanticIdentifier(
                    new SystemId(system, version),
                    new ObjectId(context, object));
            return SemanticIdentifierSet.ofCollection(List.of(sid, sid, sid));
        }
    }

    @ParameterizedTest
    @EnumSource(ValidScenario.class)
    void format(final ValidScenario scenario) {
        assertEquals(scenario.stringified, scenario.sidSet().toString());
    }

    @ParameterizedTest
    @EnumSource(ValidScenario.class)
    void parse(final ValidScenario scenario) {
        assertEquals(scenario.sidSet(), SemanticIdentifierSet.parse(scenario.stringified));
    }

}
