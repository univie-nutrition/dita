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
package dita.globodiet.survey.util;

import java.math.BigDecimal;

import org.apache.causeway.commons.collections.Can;

import lombok.NonNull;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.recall24.dto.RecallNode24.Annotation;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.Record24.Food;

public record FoodToCompositeConverter(@NonNull FoodDescriptionModel foodDescriptionModel) {
    
    public Record24.Composite.Builder foodToRecipe(
            Food origFood,
            Recipe recipe,
            String nameSuffix) {
        var recordBuilder = new Composite.Builder();

        recordBuilder.type(Record24.Type.COMPOSITE);
        recordBuilder.sid(recipe.sid());
        recordBuilder.name(NameWithCode.parseAssocFood(recipe.name()).name() + " {" + nameSuffix + "}");
        
        // there are no implicit recipe facets we could use here, hence empty
        recordBuilder.facetSids(SemanticIdentifierSet.empty());
        // store GloboDiet food description group data as annotation
        recordBuilder.annotations().clear();
        recordBuilder.annotations().add(new Annotation("group", recipeGroupSid(recipe)));
        
        // keep the original food as comment
        recordBuilder.subRecords().add(origFoodAsComment(origFood));
        
        var origFoodConsumedOverRecipeMass = new BigDecimal(
                origFood.amountConsumed().doubleValue() //TODO[dita-globodiet-survey] might not always be in GRAM
                / foodDescriptionModel.sumAmountGramsForRecipe(recipe).doubleValue());

        // to the composite record we are building here,
        // we add each recipe ingredient as sub-record
        foodDescriptionModel.streamIngredients(recipe)
            .map(ingr->{
                var food = foodDescriptionModel.lookupFoodBySid(ingr.foodSid()).orElseThrow();
                var foodBuilder = new Food.Builder()
                    .name(food.name())
                    .sid(ingr.foodSid())
                    .facetSids(ingr.foodFacetSids())
                    .amountConsumed(ingr.amountGrams().multiply(origFoodConsumedOverRecipeMass))
                    .consumptionUnit(ConsumptionUnit.GRAM); // for recipes this is always in GRAM
                foodBuilder.annotations().add(new Annotation("group", foodGroupSid(food)));
                return foodBuilder.build();
            })
            .forEach(recordBuilder.subRecords()::add);
        
        return recordBuilder;
    }
    
    // -- HELPER
    
    private SemanticIdentifier foodGroupSid(final FoodDescriptionModel.Food food) {
        return food.groupSid()
                .mapObjectId(o->o.mapContext(_->SidUtils.GdContext.FOOD_GROUP.id()));
    }
    
    private SemanticIdentifier recipeGroupSid(final FoodDescriptionModel.Recipe recipe) {
        return recipe.groupSid()
                .mapObjectId(o->o.mapContext(_->SidUtils.GdContext.RECIPE_GROUP.id()));
    }
    
    private Record24.Comment origFoodAsComment(final Food origFood) {
        var name = "%s, amount=%s, raw/cooked=%s".formatted(
                origFood.name().replace(" {", ", ").replace("}", ""),
                origFood.consumptionUnit().format(origFood.amountConsumed()),
                origFood.rawPerCookedRatio());

        return Record24.comment(name, origFood.sid(), origFood.facetSids(),
                Can.ofCollection(origFood.annotations().values()));
    }
}
