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
package dita.globodiet.manager.services.rule;

import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.collections._Sets;

import dita.commons.services.lookup.DependantLookupService;
import dita.commons.services.rules.RuleChecker;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood.ForFoodOrRecipe;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.Food.TypeOfItem;
import lombok.NonNull;

@Component
public class FoodHasDensityFactorRuleChecker
implements RuleChecker {

    @Inject FactoryService factoryService;
    @Inject RepositoryService repositoryService;
    @Inject DependantLookupService dependantLookup;

    @Override
    public String title() {
        return "Food has Density Factor";
    }
    @Override
    public String description() {
        return "Checks that each Food has an associated Density Factor. Ignoring Alias entries.";
    }

    @Override
    public Can<RuleViolation> check(@NonNull final Class<?> entityType) {
        if(!Food.class.equals(entityType)) return Can.empty();

        var foodInstances = repositoryService.allMatches(Food.class,
                food->food.getTypeOfItem()!=TypeOfItem.ALIAS);
        var densityInstances = repositoryService.allMatches(DensityFactorForFood.class,
                df->df.getForFoodOrRecipe()==ForFoodOrRecipe.FOOD);

        var foodKeys = foodInstances.stream()
                .map(food->food.secondaryKey())
                .collect(Collectors.toSet());

        var foodKeysAccountedFor = densityInstances.stream()
                .map(df->new Food.SecondaryKey(df.getFoodOrRecipeCode()))
                .collect(Collectors.toSet());

        var foodKeysNotAccountedFor = _Sets.minus(foodKeys, foodKeysAccountedFor);

        return foodKeysNotAccountedFor.isEmpty()
                ? Can.empty()
                : Can.of(RuleViolation.severe("Missing density factor:\n%s",
                        foodKeysNotAccountedFor.stream()
                        .map(secKey->"- Food " + secKey)
                        .collect(Collectors.joining("\n"))));
    }


}
