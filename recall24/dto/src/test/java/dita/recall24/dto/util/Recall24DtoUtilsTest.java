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
package dita.recall24.dto.util;

import java.math.BigDecimal;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.apache.causeway.commons.collections.Can;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.RuntimeAnnotated.Annotation;
import dita.testing.ApprovalTestOptions;

class Recall24DtoUtilsTest {
    
    private InterviewSet24 interviewSet; 
    
    @BeforeEach
    void setup() {
        final SemanticIdentifier sid = SemanticIdentifier.parse("at.gd/2.0:food/00107");
        final String name = "Zwiebel";
        final SemanticIdentifierSet facetSids = SemanticIdentifierSet.parse("at.gd/2.0:fd/0204, at.gd/2.0:fd/0399, at.gd/2.0:fd/0499");
        final BigDecimal amountConsumed = new BigDecimal("12");
        final ConsumptionUnit consumptionUnit = ConsumptionUnit.GRAM;
        final BigDecimal rawPerCookedRatio = new BigDecimal("0.8");
        final Can<Record24> usedDuringCooking = Can.empty();
        final Can<Annotation> annotations = Can.empty();
        var food = Record24.food(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, usedDuringCooking, annotations);
        var mem = new MemorizedFood24("Example Consumption Record", Can.of(food));
        var interview = Recall24DtoUtils.interviewSample(Can.of(mem));
        var respondent = Recall24DtoUtils.respondentSample(Can.of(interview));
        this.interviewSet = InterviewSet24.of(Can.of(respondent));
    }
    
    @Test
    @UseReporter(DiffReporter.class)
    void yamlRoundtrip() {
        var originalYaml = interviewSet.toYaml();
        System.err.printf("%s%n", originalYaml);
        var interviewSetAfterRoundtrip = Recall24DtoUtils.parseYaml(originalYaml);
        Approvals.verify(interviewSetAfterRoundtrip.toYaml(), ApprovalTestOptions.yamlOptions());
    }
}
