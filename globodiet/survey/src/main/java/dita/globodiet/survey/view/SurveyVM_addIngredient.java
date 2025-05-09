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
package dita.globodiet.survey.view;

import java.util.List;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;

import lombok.RequiredArgsConstructor;

import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.recall24.dto.RecallNode24;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Action
@ActionLayout(
        associateWith = "content",
        sequence = "1",
        position = Position.PANEL)
@RequiredArgsConstructor
public class SurveyVM_addIngredient {

    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;
    private final SurveyVM mixee;

    @MemberSupport
    public String act(
            final Food food,
            final double amount,
            final String facetSidList
            ) {
        return "TODO (%s)".formatted(recallNode().getClass());
    }

    @MemberSupport
    public boolean hidden() {
        return !(recallNode() instanceof dita.recall24.dto.Record24.Composite);
    }


    @MemberSupport
    public List<Food> autoCompleteFood(final String search) {
        return foodDescriptionModel().foodBySid().values()
            .stream()
            .filter(food->food.name().contains(search))
            .toList();
    }

    // -- HELPER

    FoodDescriptionModel foodDescriptionModel() {
        return null; //FIXME
    }

    RecallNode24 recallNode() {
        return mixee.recallNode();
    }


}
