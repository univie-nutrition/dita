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
package dita.globodiet.dom.params.food_list;

import dita.commons.services.lookup.DependantLookupService;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood_foodOrRecipe;
import dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood;
import dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood_food;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_food;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fss;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_fat;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_food;
import dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood;
import dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood_food;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_food;
import dita.globodiet.dom.params.nutrient.NutrientForFoodOrGroup;
import dita.globodiet.dom.params.nutrient.NutrientForFoodOrGroup_foodOrRecipe;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFood_food;
import dita.globodiet.dom.params.pathway.ProbingQuestionPathwayForFood;
import dita.globodiet.dom.params.pathway.ProbingQuestionPathwayForFood_food;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFood;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFood_food;
import dita.globodiet.dom.params.quantif.RecipeIngredientQuantification;
import dita.globodiet.dom.params.quantif.RecipeIngredientQuantification_ingredientFoodOrRecipe;
import dita.globodiet.dom.params.quantif.StandardPortionForFood;
import dita.globodiet.dom.params.quantif.StandardPortionForFood_food;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe_foodOrRecipe;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fss;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient_foodOrRecipe;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(Food_dependentDensityFactorForFoodMappedByFoodOrRecipe.class,
        Food_dependentEdiblePartCoefficientForFoodMappedByFood.class,
        Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFood.class,
        Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFss.class,
        Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFood.class,
        Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFat.class,
        Food_dependentRawToCookedConversionFactorForFoodMappedByFood.class,
        Food_dependentImprobableSequenceOfFacetAndDescriptorMappedByFood.class,
        Food_dependentComposedRecipeIngredientMappedByFoodOrRecipe.class,
        Food_dependentNutrientForFoodOrGroupMappedByFoodOrRecipe.class,
        Food_dependentFacetDescriptorPathwayForFoodMappedByFood.class,
        Food_dependentProbingQuestionPathwayForFoodMappedByFood.class,
        Food_dependentQuantificationMethodPathwayForFoodMappedByFood.class,
        Food_dependentRecipeIngredientQuantificationMappedByIngredientFoodOrRecipe.class,
        Food_dependentStandardPortionForFoodMappedByFood.class,
        Food_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe.class,
        Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFss.class,
        Food_dependentRecipeIngredientMappedByFoodOrRecipe.class);
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentDensityFactorForFoodMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

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
    public static class Food_dependentEdiblePartCoefficientForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<EdiblePartCoefficientForFood> coll() {
            return dependantLookup.findDependants(
                EdiblePartCoefficientForFood.class,
                EdiblePartCoefficientForFood_food.class,
                EdiblePartCoefficientForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_food.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFss {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fss.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fss::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<PercentOfFatUseDuringCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatUseDuringCookingForFood.class,
                PercentOfFatUseDuringCookingForFood_food.class,
                PercentOfFatUseDuringCookingForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFat {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<PercentOfFatUseDuringCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatUseDuringCookingForFood.class,
                PercentOfFatUseDuringCookingForFood_fat.class,
                PercentOfFatUseDuringCookingForFood_fat::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentRawToCookedConversionFactorForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<RawToCookedConversionFactorForFood> coll() {
            return dependantLookup.findDependants(
                RawToCookedConversionFactorForFood.class,
                RawToCookedConversionFactorForFood_food.class,
                RawToCookedConversionFactorForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentImprobableSequenceOfFacetAndDescriptorMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<ImprobableSequenceOfFacetAndDescriptor> coll() {
            return dependantLookup.findDependants(
                ImprobableSequenceOfFacetAndDescriptor.class,
                ImprobableSequenceOfFacetAndDescriptor_food.class,
                ImprobableSequenceOfFacetAndDescriptor_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentComposedRecipeIngredientMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

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
    public static class Food_dependentNutrientForFoodOrGroupMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<NutrientForFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                NutrientForFoodOrGroup.class,
                NutrientForFoodOrGroup_foodOrRecipe.class,
                NutrientForFoodOrGroup_foodOrRecipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentFacetDescriptorPathwayForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForFood> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForFood.class,
                FacetDescriptorPathwayForFood_food.class,
                FacetDescriptorPathwayForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentProbingQuestionPathwayForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<ProbingQuestionPathwayForFood> coll() {
            return dependantLookup.findDependants(
                ProbingQuestionPathwayForFood.class,
                ProbingQuestionPathwayForFood_food.class,
                ProbingQuestionPathwayForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentQuantificationMethodPathwayForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForFood> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForFood.class,
                QuantificationMethodPathwayForFood_food.class,
                QuantificationMethodPathwayForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentRecipeIngredientQuantificationMappedByIngredientFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<RecipeIngredientQuantification> coll() {
            return dependantLookup.findDependants(
                RecipeIngredientQuantification.class,
                RecipeIngredientQuantification_ingredientFoodOrRecipe.class,
                RecipeIngredientQuantification_ingredientFoodOrRecipe::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentStandardPortionForFoodMappedByFood {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<StandardPortionForFood> coll() {
            return dependantLookup.findDependants(
                StandardPortionForFood.class,
                StandardPortionForFood_food.class,
                StandardPortionForFood_food::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

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
    public static class Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFss {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fss.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fss::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class Food_dependentRecipeIngredientMappedByFoodOrRecipe {
        @Inject
        DependantLookupService dependantLookup;

        private final Food mixee;

        @MemberSupport
        public List<RecipeIngredient> coll() {
            return dependantLookup.findDependants(
                RecipeIngredient.class,
                RecipeIngredient_foodOrRecipe.class,
                RecipeIngredient_foodOrRecipe::prop,
                mixee);
        }
    }
}
