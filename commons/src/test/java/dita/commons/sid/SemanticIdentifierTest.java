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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.RequiredArgsConstructor;

import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;

class SemanticIdentifierTest {

    @RequiredArgsConstructor
    enum ValidScenario {
        EMPTY_SID(null, null, null, null,
                "SID[:]"),
        SIMPLE_SID("global", null, null, "none",
                "SID[global:none]"),
        FULL_SID("de.bls", "3.02", "component", "GCAL",
                "SID[de.bls/3.02:component/GCAL]"),
        BRAND("", null, "brand",
                """
                &^?zu`rüc'k "zu",/ karlovačko!""",
                "SID[:brand/"
                +
                """
                ‹&^?zu`rüc'k "zu",/ karlovačko!›"""
                + "]"),
        LEGACY("GD-AT20240507", null, null, "00010",
                "SID[GD-AT20240507:00010]"),
        ;
        final String system;
        final String version;
        final String context;
        final String object;
        final String stringified;
        public SemanticIdentifier sid() {
            var sid = new SemanticIdentifier(
                    new SystemId(system, version),
                    new ObjectId(context, object));
            return sid;
        }
    }

    @ParameterizedTest
    @EnumSource(ValidScenario.class)
    void format(final ValidScenario scenario) {
        assertEquals(scenario.stringified, scenario.sid().toString());
    }

    @ParameterizedTest
    @EnumSource(ValidScenario.class)
    void parse(final ValidScenario scenario) {
        assertEquals(scenario.sid(), SemanticIdentifier.parse(scenario.stringified));
    }

    @RequiredArgsConstructor
    enum InvalidScenario {
        A(",", null, null, null,
                "SID[,:]"),
        B(null, ",", null, null,
                "SID[,/:]"),
        C(null, null, ",", null,
                "SID[:,/]"),
        ;
        final String system;
        final String version;
        final String context;
        final String object;
        final String stringified;
        public SemanticIdentifier sid() {
            var sid = new SemanticIdentifier(
                    new SystemId(system, version),
                    new ObjectId(context, object));
            return sid;
        }
    }

    @ParameterizedTest
    @EnumSource(InvalidScenario.class)
    void constructInvalid(final InvalidScenario scenario) {
        assertThrows(IllegalArgumentException.class, ()->scenario.sid());
    }

    @ParameterizedTest
    @EnumSource(InvalidScenario.class)
    void parseInvalid(final InvalidScenario scenario) {
        assertThrows(IllegalArgumentException.class, ()->SemanticIdentifier.parse(scenario.stringified));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ä", "", "č"})
    void validStrings(final String string) {
        ParseFormatUtils.validate(string);
    }

    @ParameterizedTest
    @ValueSource(strings = {",", "{", "[", "a\tb", "a\nb", "a\rb", "!", "'", "\""})
    void invalidCharacters(final String string) {
        assertThrows(IllegalArgumentException.class, ()->ParseFormatUtils.validate(string));
    }

    @Test
    void wip() {
        assertNotEquals(SemanticIdentifier.empty(), SemanticIdentifier.wip());
    }

}
