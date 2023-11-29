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
package dita.globodiet.dom.params.classification;

import dita.commons.services.lookup.DependantLookupService;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood_fatSubSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood_fatSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_fatSubSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_fatSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_foodSubSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_foodSubgroup;
import dita.globodiet.dom.params.food_descript.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.dom.params.food_descript.FacetDescriptorPathwayForFoodGroup_foodSubSubgroup;
import dita.globodiet.dom.params.food_descript.FacetDescriptorPathwayForFoodGroup_foodSubgroup;
import dita.globodiet.dom.params.food_descript.FoodBrand;
import dita.globodiet.dom.params.food_descript.FoodBrand_foodSubSubgroup;
import dita.globodiet.dom.params.food_descript.FoodBrand_foodSubgroup;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_foodSubSubgroup;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_foodSubgroup;
import dita.globodiet.dom.params.food_descript.RuleAppliedToFacet;
import dita.globodiet.dom.params.food_descript.RuleAppliedToFacet_foodSubSubgroup;
import dita.globodiet.dom.params.food_descript.RuleAppliedToFacet_foodSubgroup;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.Food_foodSubSubgroup;
import dita.globodiet.dom.params.food_list.Food_foodSubgroup;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrGroup;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrGroup_foodSubSubgroup;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrGroup_foodSubgroup;
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFood;
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFood_foodSubSubgroup;
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFood_foodSubgroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodPathwayForFoodGroup_foodSubSubgroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodPathwayForFoodGroup_foodSubgroup;
import dita.globodiet.dom.params.food_table.NutrientForFoodOrGroup;
import dita.globodiet.dom.params.food_table.NutrientForFoodOrGroup_foodOrRecipeSubgroup;
import dita.globodiet.dom.params.food_table.NutrientForFoodOrGroup_foodSubSubgroup;
import dita.globodiet.dom.params.quantif.ThicknessForShapeMethod;
import dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_foodSubgroups;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubSubgroup;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubgroup;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient_foodOrRecipeSubgroup;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient_foodSubSubgroup;
import dita.globodiet.dom.params.setting.GroupSubstitution;
import dita.globodiet.dom.params.setting.GroupSubstitution_applyToFoodGroups;
import dita.globodiet.dom.params.setting.GroupSubstitution_foodSubSubgroup;
import dita.globodiet.dom.params.setting.GroupSubstitution_foodSubgroup;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodSubgroupDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(FoodSubgroup_dependentPercentOfFatLeftInTheDishForFoodMappedByFatSubgroup.class,
        FoodSubgroup_dependentPercentOfFatLeftInTheDishForFoodMappedByFatSubSubgroup.class,
        FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFoodSubgroup.class,
        FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFatSubgroup.class,
        FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFatSubSubgroup.class,
        FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFoodSubgroup.class,
        FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFatSubgroup.class,
        FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFatSubSubgroup.class,
        FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup.class,
        FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentFoodBrandMappedByFoodSubgroup.class,
        FoodSubgroup_dependentFoodBrandMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentImprobableSequenceOfFacetAndDescriptorMappedByFoodSubgroup.class,
        FoodSubgroup_dependentImprobableSequenceOfFacetAndDescriptorMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentRuleAppliedToFacetMappedByFoodSubgroup.class,
        FoodSubgroup_dependentRuleAppliedToFacetMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentFoodMappedByFoodSubgroup.class,
        FoodSubgroup_dependentFoodMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentMaximumValueForAFoodOrGroupMappedByFoodSubgroup.class,
        FoodSubgroup_dependentMaximumValueForAFoodOrGroupMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentProbingQuestionPathwayForFoodMappedByFoodSubgroup.class,
        FoodSubgroup_dependentProbingQuestionPathwayForFoodMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubgroup.class,
        FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentNutrientForFoodOrGroupMappedByFoodOrRecipeSubgroup.class,
        FoodSubgroup_dependentNutrientForFoodOrGroupMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentThicknessForShapeMethodMappedByFoodSubgroups.class,
        FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFatSubgroup.class,
        FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFatSubSubgroup.class,
        FoodSubgroup_dependentRecipeIngredientMappedByFoodOrRecipeSubgroup.class,
        FoodSubgroup_dependentRecipeIngredientMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentGroupSubstitutionMappedByFoodSubgroup.class,
        FoodSubgroup_dependentGroupSubstitutionMappedByFoodSubSubgroup.class,
        FoodSubgroup_dependentGroupSubstitutionMappedByApplyToFoodGroups.class);
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatLeftInTheDishForFoodMappedByFatSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatLeftInTheDishForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatLeftInTheDishForFood.class,
                PercentOfFatLeftInTheDishForFood_fatSubgroup.class,
                PercentOfFatLeftInTheDishForFood_fatSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatLeftInTheDishForFoodMappedByFatSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatLeftInTheDishForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatLeftInTheDishForFood.class,
                PercentOfFatLeftInTheDishForFood_fatSubSubgroup.class,
                PercentOfFatLeftInTheDishForFood_fatSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubgroup.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubSubgroup.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFatSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFatSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatUseDuringCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatUseDuringCookingForFood.class,
                PercentOfFatUseDuringCookingForFood_foodSubgroup.class,
                PercentOfFatUseDuringCookingForFood_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatUseDuringCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatUseDuringCookingForFood.class,
                PercentOfFatUseDuringCookingForFood_foodSubSubgroup.class,
                PercentOfFatUseDuringCookingForFood_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFatSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatUseDuringCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatUseDuringCookingForFood.class,
                PercentOfFatUseDuringCookingForFood_fatSubgroup.class,
                PercentOfFatUseDuringCookingForFood_fatSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFatSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatUseDuringCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatUseDuringCookingForFood.class,
                PercentOfFatUseDuringCookingForFood_fatSubSubgroup.class,
                PercentOfFatUseDuringCookingForFood_fatSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForFoodGroup.class,
                FacetDescriptorPathwayForFoodGroup_foodSubgroup.class,
                FacetDescriptorPathwayForFoodGroup_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForFoodGroup.class,
                FacetDescriptorPathwayForFoodGroup_foodSubSubgroup.class,
                FacetDescriptorPathwayForFoodGroup_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentFoodBrandMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<FoodBrand> coll() {
            return dependantLookup.findDependants(
                FoodBrand.class,
                FoodBrand_foodSubgroup.class,
                FoodBrand_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentFoodBrandMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<FoodBrand> coll() {
            return dependantLookup.findDependants(
                FoodBrand.class,
                FoodBrand_foodSubSubgroup.class,
                FoodBrand_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentImprobableSequenceOfFacetAndDescriptorMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ImprobableSequenceOfFacetAndDescriptor> coll() {
            return dependantLookup.findDependants(
                ImprobableSequenceOfFacetAndDescriptor.class,
                ImprobableSequenceOfFacetAndDescriptor_foodSubgroup.class,
                ImprobableSequenceOfFacetAndDescriptor_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentImprobableSequenceOfFacetAndDescriptorMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ImprobableSequenceOfFacetAndDescriptor> coll() {
            return dependantLookup.findDependants(
                ImprobableSequenceOfFacetAndDescriptor.class,
                ImprobableSequenceOfFacetAndDescriptor_foodSubSubgroup.class,
                ImprobableSequenceOfFacetAndDescriptor_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentRuleAppliedToFacetMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<RuleAppliedToFacet> coll() {
            return dependantLookup.findDependants(
                RuleAppliedToFacet.class,
                RuleAppliedToFacet_foodSubgroup.class,
                RuleAppliedToFacet_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentRuleAppliedToFacetMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<RuleAppliedToFacet> coll() {
            return dependantLookup.findDependants(
                RuleAppliedToFacet.class,
                RuleAppliedToFacet_foodSubSubgroup.class,
                RuleAppliedToFacet_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentFoodMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<Food> coll() {
            return dependantLookup.findDependants(
                Food.class,
                Food_foodSubgroup.class,
                Food_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentFoodMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<Food> coll() {
            return dependantLookup.findDependants(
                Food.class,
                Food_foodSubSubgroup.class,
                Food_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentMaximumValueForAFoodOrGroupMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<MaximumValueForAFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                MaximumValueForAFoodOrGroup.class,
                MaximumValueForAFoodOrGroup_foodSubgroup.class,
                MaximumValueForAFoodOrGroup_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentMaximumValueForAFoodOrGroupMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<MaximumValueForAFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                MaximumValueForAFoodOrGroup.class,
                MaximumValueForAFoodOrGroup_foodSubSubgroup.class,
                MaximumValueForAFoodOrGroup_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentProbingQuestionPathwayForFoodMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ProbingQuestionPathwayForFood> coll() {
            return dependantLookup.findDependants(
                ProbingQuestionPathwayForFood.class,
                ProbingQuestionPathwayForFood_foodSubgroup.class,
                ProbingQuestionPathwayForFood_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentProbingQuestionPathwayForFoodMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ProbingQuestionPathwayForFood> coll() {
            return dependantLookup.findDependants(
                ProbingQuestionPathwayForFood.class,
                ProbingQuestionPathwayForFood_foodSubSubgroup.class,
                ProbingQuestionPathwayForFood_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForFoodGroup.class,
                QuantificationMethodPathwayForFoodGroup_foodSubgroup.class,
                QuantificationMethodPathwayForFoodGroup_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForFoodGroup.class,
                QuantificationMethodPathwayForFoodGroup_foodSubSubgroup.class,
                QuantificationMethodPathwayForFoodGroup_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentNutrientForFoodOrGroupMappedByFoodOrRecipeSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<NutrientForFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                NutrientForFoodOrGroup.class,
                NutrientForFoodOrGroup_foodOrRecipeSubgroup.class,
                NutrientForFoodOrGroup_foodOrRecipeSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentNutrientForFoodOrGroupMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<NutrientForFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                NutrientForFoodOrGroup.class,
                NutrientForFoodOrGroup_foodSubSubgroup.class,
                NutrientForFoodOrGroup_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentThicknessForShapeMethodMappedByFoodSubgroups {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ThicknessForShapeMethod> coll() {
            return dependantLookup.findDependants(
                ThicknessForShapeMethod.class,
                ThicknessForShapeMethod_foodSubgroups.class,
                ThicknessForShapeMethod_foodSubgroups::coll,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFatSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubgroup.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFatSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> coll() {
            return dependantLookup.findDependants(
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubSubgroup.class,
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentRecipeIngredientMappedByFoodOrRecipeSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<RecipeIngredient> coll() {
            return dependantLookup.findDependants(
                RecipeIngredient.class,
                RecipeIngredient_foodOrRecipeSubgroup.class,
                RecipeIngredient_foodOrRecipeSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentRecipeIngredientMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<RecipeIngredient> coll() {
            return dependantLookup.findDependants(
                RecipeIngredient.class,
                RecipeIngredient_foodSubSubgroup.class,
                RecipeIngredient_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentGroupSubstitutionMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<GroupSubstitution> coll() {
            return dependantLookup.findDependants(
                GroupSubstitution.class,
                GroupSubstitution_foodSubgroup.class,
                GroupSubstitution_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentGroupSubstitutionMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<GroupSubstitution> coll() {
            return dependantLookup.findDependants(
                GroupSubstitution.class,
                GroupSubstitution_foodSubSubgroup.class,
                GroupSubstitution_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentGroupSubstitutionMappedByApplyToFoodGroups {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<GroupSubstitution> coll() {
            return dependantLookup.findDependants(
                GroupSubstitution.class,
                GroupSubstitution_applyToFoodGroups.class,
                GroupSubstitution_applyToFoodGroups::coll,
                mixee);
        }
    }
}
