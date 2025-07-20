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
package dita.recall24.model.corr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Correction24.CompositeCorr;
import dita.recall24.dto.Correction24.CompositeCorr.Addition;
import dita.recall24.dto.Correction24.CompositeCorr.Coordinates;
import dita.recall24.dto.Correction24.CompositeCorr.Deletion;
import dita.recall24.dto.Correction24.RespondentCorr;
import io.github.causewaystuff.commons.base.types.NamedPath;

class Correction24Test {

    private SystemId systemId = new SystemId("at.gd", "2.0");

    @Test
    void roundtripOnYaml() {
        var corr = new Correction24();
        corr.respondents().add(RespondentCorr.builder()
            .alias("EB_0061")
            .dateOfBirth(LocalDate.parse("1977-03-23"))
            .build());
        corr.respondents().add(RespondentCorr.builder()
            .alias("EB_0058")
            .sex(Sex.MALE)
            .build());
        corr.respondents().add(RespondentCorr.builder()
            .alias("EB_00XX")
            .withdraw(true)
            .build());

        corr.composites().add(CompositeCorr.builder()
            .coordinates(new Coordinates(recipe("00301"), "EB_0077", 1, LocalTime.of(19, 30), NamedPath.of("path", "sample.xml")))
            .addition(new Addition(food("01581"), new BigDecimal("2.5"), facets()))
            .deletion(new Deletion(food("01617")))
            .build());

        // debug
        System.err.printf("Correction24Test%n%s%n", corr.toYaml());

        assertEquals(corr, Correction24.tryFromYaml(corr.toYaml()).valueAsNonNullElseFail());
    }

    // -- HELPER

    private SemanticIdentifier recipe(final String objSimpleId) {
        return SemanticIdentifier.ObjectId.Context.RECIPE.sid(systemId, objSimpleId);
    }
    private SemanticIdentifier food(final String objSimpleId) {
        return SemanticIdentifier.ObjectId.Context.FOOD.sid(systemId, objSimpleId);
    }
    private SemanticIdentifier foodDescriptor(final String objSimpleId) {
        return new SemanticIdentifier(systemId, new ObjectId("fd", objSimpleId));
    }
    private SemanticIdentifierSet facets(final String... objSimpleIds) {
        return SemanticIdentifierSet.ofStream(Stream.of(objSimpleIds)
            .map(this::foodDescriptor));
    }

}
