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
package dita.foodex.validate.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.val;

import dita.foodex.validate.service.EfsaCodeAnalyserService.Options;

//@Disabled("needs test resources")
class EfsaCodeAnalyserServiceTest {

    private static EfsaCodeAnalyserService analyser;

    @BeforeAll
    static void prepare() {

        EfsaCodeAnalyserService.consoleLoggerOnly();

        //GlobalUtil is a mess with its static initializers
        System.setProperty("workingDir", "N:\\dev\\EFSA-catalogues-browser-onlyapp-win-64bit");

        val options = Options.builder()
                .workingDir("N:\\dev\\EFSA-catalogues-browser-onlyapp-win-64bit")
                .build();

        analyser = new EfsaCodeAnalyserService();
        analyser.init(options);
    }

    @Test
    void expectsNoWarning() {
        val validation = analyser.validate("A01SP#F22.A07SS$F28.A07GT$F28.A07HS");
        assertEquals("", validation);
    }

    @Test
    void expectsWarning() {
        val validation = analyser.validate("A01SP#F04.A0EZM$F22.A07SS$F28.A07HS");
        assertEquals("BR12> The F04 ingredient facet can only be used as a minor ingredient "
                + "to derivative or raw primary commodity terms.(A0EZM) ;LOW;LOW", validation);
    }

}
