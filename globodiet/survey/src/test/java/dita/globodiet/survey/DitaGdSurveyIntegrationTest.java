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

    protected InterviewSet24 loadInterviewSet() {
        var fdm = loadFoodDescriptionModel();
        return Campaigns.interviewSet(campaignForTesting(), surveyBlobStore, """
                respondents:
                - alias: "EB0070"
                  newAlias: "EB_0070"
                - alias: "EB:0029"
                  newAlias: "EB_0029"
                - alias: "EB_0061"
                  dateOfBirth: "1977-04-24"
                - alias: "EB_0058"
                  dateOfBirth: "1992-08-28"
                - alias: "EB_0038"
                  dateOfBirth: "2002-09-21"
                - alias: "EB_0093"
                  sex: FEMALE
                - alias: "EB_0088"
                  dateOfBirth: "1967-06-09"
                - alias: "EB_0032"
                  dateOfBirth: "1999-03-04"
                - alias: "EB_0116"
                  dateOfBirth: "1998-01-26"
                """)
                .transform(new AssociatedRecipeResolver(fdm));
    }

    // -- HELPER

    private Campaign campaignForTesting() {
        var campaign = new Campaign();
        campaign.setCode("wave1");
        campaign.setSurveyCode("at-national-2026");
        return campaign;
    }

}
