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

import dita.commons.decorate.CollectionTitleDecorator;
import dita.commons.services.lookup.DependantLookupService;
import dita.globodiet.dom.params.nutrient.NutrientForFoodOrGroup;
import dita.globodiet.dom.params.nutrient.NutrientForFoodOrGroup_foodOrRecipeGroup;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipeGroup;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipeGroup_recipeGroup;
import dita.globodiet.dom.params.pathway.ProbingQuestionPathwayForRecipe;
import dita.globodiet.dom.params.pathway.ProbingQuestionPathwayForRecipe_recipeGroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipeGroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipeGroup_recipeGroup;
import dita.globodiet.dom.params.quantif.MaximumValueForRecipeOrGroup;
import dita.globodiet.dom.params.quantif.MaximumValueForRecipeOrGroup_recipeGroup;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipeGroup;
import dita.globodiet.dom.params.recipe_description.RecipeBrand;
import dita.globodiet.dom.params.recipe_description.RecipeBrand_recipeGroup;
import dita.globodiet.dom.params.recipe_description.RecipeFacetRule;
import dita.globodiet.dom.params.recipe_description.RecipeFacetRule_recipeGroup;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeGroupDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(RecipeGroup_dependentNutrientForFoodOrGroupMappedByFoodOrRecipeGroup.class,
        RecipeGroup_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeGroup.class,
        RecipeGroup_dependentProbingQuestionPathwayForRecipeMappedByRecipeGroup.class,
        RecipeGroup_dependentQuantificationMethodPathwayForRecipeGroupMappedByRecipeGroup.class,
        RecipeGroup_dependentMaximumValueForRecipeOrGroupMappedByRecipeGroup.class,
        RecipeGroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByRecipeGroup.class,
        RecipeGroup_dependentRecipeBrandMappedByRecipeGroup.class,
        RecipeGroup_dependentRecipeFacetRuleMappedByRecipeGroup.class,
        RecipeGroup_dependentRecipeMappedByRecipeGroup.class,
        RecipeGroup_dependentRecipeIngredientMappedByFoodOrRecipeGroup.class,
        RecipeGroup_dependentRecipeSubgroupMappedByRecipeGroup.class);
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentNutrientForFoodOrGroupMappedByFoodOrRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<NutrientForFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                NutrientForFoodOrGroup.class,
                NutrientForFoodOrGroup_foodOrRecipeGroup.class,
                NutrientForFoodOrGroup_foodOrRecipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForRecipeGroup> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForRecipeGroup.class,
                FacetDescriptorPathwayForRecipeGroup_recipeGroup.class,
                FacetDescriptorPathwayForRecipeGroup_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentProbingQuestionPathwayForRecipeMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<ProbingQuestionPathwayForRecipe> coll() {
            return dependantLookup.findDependants(
                ProbingQuestionPathwayForRecipe.class,
                ProbingQuestionPathwayForRecipe_recipeGroup.class,
                ProbingQuestionPathwayForRecipe_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentQuantificationMethodPathwayForRecipeGroupMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForRecipeGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForRecipeGroup.class,
                QuantificationMethodPathwayForRecipeGroup_recipeGroup.class,
                QuantificationMethodPathwayForRecipeGroup_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentMaximumValueForRecipeOrGroupMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<MaximumValueForRecipeOrGroup> coll() {
            return dependantLookup.findDependants(
                MaximumValueForRecipeOrGroup.class,
                MaximumValueForRecipeOrGroup_recipeGroup.class,
                MaximumValueForRecipeOrGroup_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipeGroup.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentRecipeBrandMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<RecipeBrand> coll() {
            return dependantLookup.findDependants(
                RecipeBrand.class,
                RecipeBrand_recipeGroup.class,
                RecipeBrand_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentRecipeFacetRuleMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<RecipeFacetRule> coll() {
            return dependantLookup.findDependants(
                RecipeFacetRule.class,
                RecipeFacetRule_recipeGroup.class,
                RecipeFacetRule_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentRecipeMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<Recipe> coll() {
            return dependantLookup.findDependants(
                Recipe.class,
                Recipe_recipeGroup.class,
                Recipe_recipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentRecipeIngredientMappedByFoodOrRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<RecipeIngredient> coll() {
            return dependantLookup.findDependants(
                RecipeIngredient.class,
                RecipeIngredient_foodOrRecipeGroup.class,
                RecipeIngredient_foodOrRecipeGroup::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeGroup_dependentRecipeSubgroupMappedByRecipeGroup {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeGroup mixee;

        @MemberSupport
        public List<RecipeSubgroup> coll() {
            return dependantLookup.findDependants(
                RecipeSubgroup.class,
                RecipeSubgroup_recipeGroup.class,
                RecipeSubgroup_recipeGroup::prop,
                mixee);
        }
    }
}
