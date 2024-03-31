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
package dita.globodiet.params.food_list.mixin;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.food_list.FoodDeps;
import dita.globodiet.params.nutrient.NutrientForFoodOrGroupDeps;
import dita.globodiet.params.nutrient.NutrientValue;

@Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
@ActionLayout(
        fieldSetId="details",
        sequence = "99",
        position = Position.PANEL,
        cssClassFa = "solid trash-arrow-up",
        describedAs = "Delete this food and its exclusive dependants.")
@RequiredArgsConstructor
public class Food_cascadingDelete {

    @Inject private RepositoryService repositoryService;
    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    protected final Food mixee;

    @MemberSupport
    public Food.Manager act() {

        factoryService.mixin(FoodDeps.Food_dependentDensityFactorForFoodOrRecipeMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentEdiblePartCoefficientForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFss.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFat.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentRawToCookedConversionFactorForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentFacetDescriptorPathwayForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentImprobableSequenceOfFacetAndDescriptorMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentProbingQuestionPathwayForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentQuantificationMethodPathwayForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentStandardPortionForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentNutrientForFoodOrGroupMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(dependant->{
            factoryService.mixin(NutrientForFoodOrGroupDeps
                    .NutrientForFoodOrGroup_dependentNutrientValueMappedByNutrientForFoodOrGroup.class, dependant)
            .coll()
            .forEach(nutrientValue->{
                repositoryService.remove(nutrientValue);
            });
            foreignKeyLookupService.clearCache(NutrientValue.class);
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFss.class, mixee)
        .coll()
        .forEach(dependant->{
            repositoryService.remove(dependant);
            foreignKeyLookupService.clearCache(dependant.getClass());
        });

        repositoryService.remove(mixee);
        foreignKeyLookupService.clearCache(Food.class);

        return factoryService.viewModel(Food.Manager.class);

    }

}
