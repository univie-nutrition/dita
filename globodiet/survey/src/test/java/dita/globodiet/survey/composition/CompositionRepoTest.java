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
package dita.globodiet.survey.composition;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.food.composition.FoodComponentDatapoint;
import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.ontologies.BLS302;
import dita.commons.sid.SemanticIdentifier;
import dita.globodiet.survey.DitaTestModuleGdSurvey;
import dita.globodiet.survey.PrivateDataTest;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.commons.compression.SevenZUtils;

@SpringBootTest(classes = {
        DitaTestModuleGdSurvey.class,
        })
@PrivateDataTest
class CompositionRepoTest {

    @Inject @Qualifier("survey") BlobStore surveyBlobStore;

    @Test
    void loading() throws IOException {
        assertNotNull(surveyBlobStore);

        var fcdbDataSource = SevenZUtils.decompress(
                surveyBlobStore.lookupBlob(NamedPath.of("fcdb", "fcdb.7z")).orElseThrow().asDataSource());

        var foodCompositionRepo = FoodCompositionRepository.tryFromYaml(fcdbDataSource)
            .valueAsNonNullElseFail();

        assertEquals(14814, foodCompositionRepo.compositionCount());

        toCSV(foodCompositionRepo);
    }

    private void toCSV(final FoodCompositionRepository repo) {

        var catalog = repo.componentCatalog();

        final List<String> lines = new ArrayList<>();

        //header
        lines.add("foodId," + BlsTerm.streamTerms()
            .map(BlsTerm::componentId)
            .map(SemanticIdentifier::objectId)
            .collect(Collectors.joining(",")));

        repo.streamCompositions()
        .sorted((a, b)->_Strings.compareNullsFirst(a.foodId().objectId(), b.foodId().objectId()))
        .forEach(comp->{
            lines.add(
                    comp.foodId().objectId()
                    + ","
                    + BlsTerm.streamTerms()
                        .map(BlsTerm::componentId)
                        .map(componentId->comp.lookupDatapoint(componentId).orElseThrow())
                        .map(FoodComponentDatapoint::datapointValue)
                        .map(BigDecimal.class::cast)
                        .map(BigDecimal::toString)
                        .collect(Collectors.joining(",")));
        });
        TextUtils.writeLinesToFile(lines, new File("d:/tmp/_scratch/bls2.csv"), StandardCharsets.UTF_8);
    }

    enum BlsTerm {
        // SBLS, ST, STE,
        GCAL, GJ, GCALZB, GJZB, ZW, ZE, ZF, ZK, ZB, ZM, ZO, ZA, VA, VAR, VAC, VD, VE, VEAT, VK, VB1, VB2, VB3, VB3A, VB5,
        VB6, VB7, VB9G, VB12, VC, MNA, MK, MCA, MMG, MP, MS, MCL, MFE, MZN, MCU, MMN, MF, MJ, KAM, KAS, KAX, KA, KMT, KMF,
        KMG, KM, KDS, KDM, KDL, KD, KMD, KPOR, KPON, KPG, KPS, KP, KBP, KBH, KBU, KBC, KBL, KBW, KBN, EILE, ELEU, ELYS,
        EMET, ECYS, EPHE, ETYR, ETHR, ETRP, EVAL, EARG, EHIS, EEA, EALA, EASP, EGLU, EGLY, EPRO, ESER, ENA, EH, EP, F40,
        F60, F80, F100, F120, F140, F150, F160, F170, F180, F200, F220, F240, FS, F141, F151, F161, F171, F181, F201, F221,
        F241, FU, F162, F164, F182, F183, F184, F193, F202, F203, F204, F205, F222, F223, F224, F225, F226, FP, FK, FM, FL,
        FO3, FO6, FG, FC, GFPS, GKB, GMKO, GP;
        SemanticIdentifier componentId() {
            return BLS302.id(name());
        }
        static Stream<BlsTerm> streamTerms(){
            return Stream.of(values());
        }

    }

}
