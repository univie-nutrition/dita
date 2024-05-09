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
package dita.globodiet.survey.fcdb;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.food.composition.FoodComponentDatapoint;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.foodon.bls.BLS302;
import dita.commons.sid.SemanticIdentifier;
import dita.globodiet.survey.DitaGdSurveyIntegrationTest;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class CompositionRepoTest extends DitaGdSurveyIntegrationTest {

    @Test
    void loading() throws IOException {
        var foodCompositionRepo = loadFcdb();

        assertEquals(14814, foodCompositionRepo.compositionCount());
        //toCSV(foodCompositionRepo);
    }

    // for debugging
    @SuppressWarnings("unused")
    private void toCSV(final FoodCompositionRepository repo) {

        final List<String> lines = new ArrayList<>();

        //header
        lines.add("foodId," + BLS302.streamComponents()
            .map(BLS302.Component::componentId)
            .map(SemanticIdentifier::objectId)
            .collect(Collectors.joining(",")));

        repo.streamCompositions()
        .sorted((a, b)->_Strings.compareNullsFirst(a.foodId().objectId(), b.foodId().objectId()))
        .forEach(comp->{
            lines.add(
                    comp.foodId().objectId()
                    + ","
                    + BLS302.streamComponents()
                        .map(BLS302.Component::componentId)
                        .map(componentId->comp.lookupDatapoint(componentId).orElseThrow())
                        .map(FoodComponentDatapoint::datapointValue)
                        .map(BigDecimal.class::cast)
                        .map(BigDecimal::toString)
                        .collect(Collectors.joining(",")));
        });
        TextUtils.writeLinesToFile(lines, new File("d:/tmp/_scratch/bls2.csv"), StandardCharsets.UTF_8);
    }

}
