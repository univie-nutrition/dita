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

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dita.commons.types.Sex;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Correction24.RespondentCorr;

class Correction24Test {

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

        // debug
        //System.err.printf("Correction24Test%n%s%n", corr.toYaml());

        assertEquals(corr, Correction24.tryFromYaml(corr.toYaml()).valueAsNonNullElseFail());
    }
}
