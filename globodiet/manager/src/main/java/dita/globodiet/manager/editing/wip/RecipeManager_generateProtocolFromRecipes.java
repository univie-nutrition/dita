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
package dita.globodiet.manager.editing.wip;

import java.math.BigDecimal;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;

import lombok.RequiredArgsConstructor;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredient;
import dita.globodiet.params.recipe_list.Recipe;
import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Annotated.Annotation;
import dita.recall24.dto.util.Recall24DtoUtils;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="listOfRecipe", position = Position.PANEL)
@RequiredArgsConstructor
public class RecipeManager_generateProtocolFromRecipes {

    @Inject private RepositoryService repositoryService;
    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    final Recipe.Manager recipeManager;

    @MemberSupport
    public Clob act(Campaign campaign) {
        var foodDescriptionModel = Campaigns.foodDescriptionModel(campaign.secondaryKey(), surveyBlobStore);
        var recordFactory = new RecordFactory(foodDescriptionModel);
        var memorizedFoods = foodDescriptionModel.ingredientsByRecipeSid().entrySet().stream()
            .map(entry->{
                var recipeSid = entry.getKey();
                var ingredients = entry.getValue();
                return new MemorizedFood24(
                    "Example Consumption Record for Recipe " + recipeSid, 
                    Can.of(recordFactory.toCompositeRecord(recipeSid, ingredients)));
            })
            .collect(Can.toCan());
        
        var interview = Recall24DtoUtils.interviewSample(memorizedFoods);
        var respondent = Recall24DtoUtils.respondentSample(Can.of(interview));
        var interviewSet = InterviewSet24.of(Can.of(respondent));
        return Clob.of("RecipesAsProtocol", CommonMimeType.YAML, interviewSet.toYaml());
    }
    
    @MemberSupport
    List<Campaign> choicesCampaign() {
        return repositoryService.allInstances(Campaign.class);
    }
    
    // -- HELPER
    
    private record RecordFactory(FoodDescriptionModel foodDescriptionModel) {
        Record24.Composite toCompositeRecord(SemanticIdentifier recipeSid, List<RecipeIngredient> ingredients) {
            var recipe = foodDescriptionModel.recipeBySid().get(recipeSid);
            return Record24.composite(
                recipe.name(), 
                recipeSid,
                SemanticIdentifierSet.empty(), //TODO missing recipe facets
                ingredients.stream().map(this::toIngredientRecord).collect(Can.toCan()), 
                Can.empty());
        }
        
        Record24.Food toIngredientRecord(FoodDescriptionModel.RecipeIngredient ingredient) {
            final SemanticIdentifier sid = ingredient.foodSid();
            final String name = foodDescriptionModel.foodBySid().get(sid).name();
            final SemanticIdentifierSet facetSids = ingredient.foodFacetSids();
            final BigDecimal amountConsumed = ingredient.amountGrams();
            final ConsumptionUnit consumptionUnit = ConsumptionUnit.GRAM;
            final BigDecimal rawPerCookedRatio = BigDecimal.ONE.negate(); //TODO missing ratio
            final Can<Record24> usedDuringCooking = Can.empty();
            final Can<Annotation> annotations = Can.empty();
            return Record24.food(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, usedDuringCooking, annotations);
        }
    }

}
