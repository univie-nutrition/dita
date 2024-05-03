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

import java.io.IOException;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import dita.commons.food.composition.FoodCompositionRepository;
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
    }

}
