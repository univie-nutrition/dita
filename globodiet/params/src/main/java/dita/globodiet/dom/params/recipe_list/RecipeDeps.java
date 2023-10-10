/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.recipe_list;

import dita.commons.services.lookup.DependantLookupService;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood_foodOrRecipe;
import dita.globodiet.dom.params.food_list.ComposedRecipeIngredient;
import dita.globodiet.dom.params.food_list.ComposedRecipeIngredient_foodOrRecipe;
import dita.globodiet.dom.params.food_table.ItemDefinition;
import dita.globodiet.dom.params.food_table.ItemDefinition_foodOrRecipe;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe_foodOrRecipe;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipe;
import dita.globodiet.dom.params.recipe_description.ExceptionToFacetsPathwayForRecipe;
import dita.globodiet.dom.params.recipe_description.ExceptionToFacetsPathwayForRecipe_recipe;
import dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrGroup;
import dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrGroup_recipe;
import dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes;
import dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes_recipe;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe_recipe;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(Recipe_dependentDensityFactorForFoodMappedByFoodOrRecipe.class,
        Recipe_dependentComposedRecipeIngredientMappedByFoodOrRecipe.class,
        Recipe_dependentItemDefinitionMappedByFoodOrRecipe.class,
        Recipe_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe.class,
        Recipe_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByRecipe.class,
        Recipe_dependentExceptionToFacetsPathwayForRecipeMappedByRecipe.class,
        Recipe_dependentRecipeIngredientMappedByRecipe.class,
        Recipe_dependentRecipeIngredientMappedByFoodOrRecipe.class,
        Recipe_dependentRecipeIngredientQuantificationMappedByRecipe.class,
        Recipe_dependentMaximumValueForARecipeOrGroupMappedByRecipe.class,
        Recipe_dependentProbingQuestionPathwayForRecipesMappedByRecipe.class,
        Recipe_dependentQuantificationMethodPathwayForRecipeMappedByRecipe.class);
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentDensityFactorForFoodMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<DensityFactorForFood> coll() {
            return dependantLookup.findDependants(
                DensityFactorForFood.class,
                DensityFactorForFood_foodOrRecipe.class,
                DensityFactorForFood_foodOrRecipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentComposedRecipeIngredientMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<ComposedRecipeIngredient> coll() {
            return dependantLookup.findDependants(
                ComposedRecipeIngredient.class,
                ComposedRecipeIngredient_foodOrRecipe.class,
                ComposedRecipeIngredient_foodOrRecipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentItemDefinitionMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<ItemDefinition> coll() {
            return dependantLookup.findDependants(
                ItemDefinition.class,
                ItemDefinition_foodOrRecipe.class,
                ItemDefinition_foodOrRecipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<StandardUnitForFoodOrRecipe> coll() {
            return dependantLookup.findDependants(
                StandardUnitForFoodOrRecipe.class,
                StandardUnitForFoodOrRecipe_foodOrRecipe.class,
                StandardUnitForFoodOrRecipe_foodOrRecipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipe.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentExceptionToFacetsPathwayForRecipeMappedByRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<ExceptionToFacetsPathwayForRecipe> coll() {
            return dependantLookup.findDependants(
                ExceptionToFacetsPathwayForRecipe.class,
                ExceptionToFacetsPathwayForRecipe_recipe.class,
                ExceptionToFacetsPathwayForRecipe_recipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentRecipeIngredientMappedByRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<RecipeIngredient> coll() {
            return dependantLookup.findDependants(
                RecipeIngredient.class,
                RecipeIngredient_recipe.class,
                RecipeIngredient_recipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentRecipeIngredientMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<RecipeIngredient> coll() {
            return dependantLookup.findDependants(
                RecipeIngredient.class,
                RecipeIngredient_foodOrRecipe.class,
                RecipeIngredient_foodOrRecipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentRecipeIngredientQuantificationMappedByRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<RecipeIngredientQuantification> coll() {
            return dependantLookup.findDependants(
                RecipeIngredientQuantification.class,
                RecipeIngredientQuantification_recipe.class,
                RecipeIngredientQuantification_recipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentMaximumValueForARecipeOrGroupMappedByRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<MaximumValueForARecipeOrGroup> coll() {
            return dependantLookup.findDependants(
                MaximumValueForARecipeOrGroup.class,
                MaximumValueForARecipeOrGroup_recipe.class,
                MaximumValueForARecipeOrGroup_recipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentProbingQuestionPathwayForRecipesMappedByRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<ProbingQuestionPathwayForRecipes> coll() {
            return dependantLookup.findDependants(
                ProbingQuestionPathwayForRecipes.class,
                ProbingQuestionPathwayForRecipes_recipe.class,
                ProbingQuestionPathwayForRecipes_recipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Recipe_dependentQuantificationMethodPathwayForRecipeMappedByRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Recipe mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForRecipe> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForRecipe.class,
                QuantificationMethodPathwayForRecipe_recipe.class,
                QuantificationMethodPathwayForRecipe_recipe::prop,
                mixee);
        }
    }
}
