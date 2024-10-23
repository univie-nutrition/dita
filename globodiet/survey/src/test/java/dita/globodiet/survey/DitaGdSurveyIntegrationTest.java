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
package dita.globodiet.survey;

import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.testing.integtestsupport.applib.CausewayIntegrationTestAbstract;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.qmap.QualifiedMap;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;

public abstract class DitaGdSurveyIntegrationTest
extends CausewayIntegrationTestAbstract {

    @Inject @Qualifier("survey") protected BlobStore surveyBlobStore;

    protected FoodCompositionRepository loadFcdb() {
        return Campaigns.fcdb(campaignForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadNutMapping() {
        return Campaigns.nutMapping(campaignForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadFcoMapping() {
        return Campaigns.fcoMapping(campaignForTesting(), surveyBlobStore);
    }

    protected QualifiedMap loadPocMapping() {
        return Campaigns.pocMapping(campaignForTesting(), surveyBlobStore);
    }

    protected FoodDescriptionModel loadFoodDescriptionModel() {
        return Campaigns.foodDescriptionModel(campaignForTesting(), surveyBlobStore);
    }

    protected String loadCorrections() {
        return surveyBlobStore.lookupBlob(NamedPath.of(
                "surveys", campaignForTesting().getSurveyCode(), "corrections", "test-correction.yaml"))
                .get()
                .toClob(StandardCharsets.UTF_8)
                .asString();
    }

    protected InterviewSet24 loadInterviewSet() {
        return Campaigns.interviewSet(campaignForTesting(), surveyBlobStore, loadCorrections())
                .transform(new AssociatedRecipeResolver(loadFoodDescriptionModel()));
    }

    // -- HELPER

    private Campaign campaignForTesting() {
        var campaign = new Campaign();
        campaign.setCode("wave1");
        campaign.setSurveyCode("at-national-2026");
        return campaign;
    }

}
