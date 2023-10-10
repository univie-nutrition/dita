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
import dita.globodiet.dom.params.food_descript.Brand;
import dita.globodiet.dom.params.food_descript.Brand_foodSubSubgroup;
import dita.globodiet.dom.params.food_descript.Brand_foodSubgroup;
import dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor;
import dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor_foodSubSubgroup;
import dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor_foodSubgroup;
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
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods;
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods_foodSubSubgroup;
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods_foodSubgroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_foodSubSubgroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_foodSubgroup;
import dita.globodiet.dom.params.food_table.ItemDefinition;
import dita.globodiet.dom.params.food_table.ItemDefinition_foodOrRecipeSubgroup;
import dita.globodiet.dom.params.food_table.ItemDefinition_foodSubSubgroup;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodSubgroupDeps {
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
    public static class FoodSubgroup_dependentBrandMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<Brand> coll() {
            return dependantLookup.findDependants(
                Brand.class,
                Brand_foodSubgroup.class,
                Brand_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentBrandMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<Brand> coll() {
            return dependantLookup.findDependants(
                Brand.class,
                Brand_foodSubSubgroup.class,
                Brand_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentCrossReferenceBetweenFoodGroupAndDescriptorMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<CrossReferenceBetweenFoodGroupAndDescriptor> coll() {
            return dependantLookup.findDependants(
                CrossReferenceBetweenFoodGroupAndDescriptor.class,
                CrossReferenceBetweenFoodGroupAndDescriptor_foodSubgroup.class,
                CrossReferenceBetweenFoodGroupAndDescriptor_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentCrossReferenceBetweenFoodGroupAndDescriptorMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<CrossReferenceBetweenFoodGroupAndDescriptor> coll() {
            return dependantLookup.findDependants(
                CrossReferenceBetweenFoodGroupAndDescriptor.class,
                CrossReferenceBetweenFoodGroupAndDescriptor_foodSubSubgroup.class,
                CrossReferenceBetweenFoodGroupAndDescriptor_foodSubSubgroup::prop,
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
    public static class FoodSubgroup_dependentProbingQuestionPathwayForFoodsMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ProbingQuestionPathwayForFoods> coll() {
            return dependantLookup.findDependants(
                ProbingQuestionPathwayForFoods.class,
                ProbingQuestionPathwayForFoods_foodSubgroup.class,
                ProbingQuestionPathwayForFoods_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentProbingQuestionPathwayForFoodsMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ProbingQuestionPathwayForFoods> coll() {
            return dependantLookup.findDependants(
                ProbingQuestionPathwayForFoods.class,
                ProbingQuestionPathwayForFoods_foodSubSubgroup.class,
                ProbingQuestionPathwayForFoods_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentQuantificationMethodsPathwayForFoodGroupMappedByFoodSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<QuantificationMethodsPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodsPathwayForFoodGroup.class,
                QuantificationMethodsPathwayForFoodGroup_foodSubgroup.class,
                QuantificationMethodsPathwayForFoodGroup_foodSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentQuantificationMethodsPathwayForFoodGroupMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<QuantificationMethodsPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodsPathwayForFoodGroup.class,
                QuantificationMethodsPathwayForFoodGroup_foodSubSubgroup.class,
                QuantificationMethodsPathwayForFoodGroup_foodSubSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentItemDefinitionMappedByFoodOrRecipeSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ItemDefinition> coll() {
            return dependantLookup.findDependants(
                ItemDefinition.class,
                ItemDefinition_foodOrRecipeSubgroup.class,
                ItemDefinition_foodOrRecipeSubgroup::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FoodSubgroup_dependentItemDefinitionMappedByFoodSubSubgroup {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodSubgroup mixee;

        @MemberSupport
        public List<ItemDefinition> coll() {
            return dependantLookup.findDependants(
                ItemDefinition.class,
                ItemDefinition_foodSubSubgroup.class,
                ItemDefinition_foodSubSubgroup::prop,
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
