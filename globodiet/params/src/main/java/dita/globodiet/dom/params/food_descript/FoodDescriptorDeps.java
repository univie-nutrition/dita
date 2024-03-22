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
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.dom.params.food_descript;

import dita.globodiet.dom.params.food_coefficient.DensityFactorForFoodOrRecipe;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFoodOrRecipe_facetDescriptors;
import dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood;
import dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood_facetDescriptor;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_cookingMethodFacetDescriptor;
import dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood;
import dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood_facetDescriptors;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFoodGroup_foodDescriptor;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFoodGroup_physicalStateFacetDescriptor;
import dita.globodiet.dom.params.quantif.MaximumValueForFoodOrGroup;
import dita.globodiet.dom.params.quantif.MaximumValueForFoodOrGroup_facetDescriptor;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient_facetDescriptors;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted_descriptor;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.causewaystuff.domsupport.decorate.CollectionTitleDecorator;
import org.causewaystuff.domsupport.services.lookup.DependantLookupService;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodDescriptorDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(FoodDescriptor_dependentDensityFactorForFoodOrRecipeMappedByFacetDescriptors.class,
        FoodDescriptor_dependentEdiblePartCoefficientForFoodMappedByFacetDescriptor.class,
        FoodDescriptor_dependentPercentOfFatUseDuringCookingForFoodMappedByCookingMethodFacetDescriptor.class,
        FoodDescriptor_dependentRawToCookedConversionFactorForFoodMappedByFacetDescriptors.class,
        FoodDescriptor_dependentFoodFacetRuleMappedByFacetDescriptor.class,
        FoodDescriptor_dependentImprobableSequenceOfFacetAndDescriptorMappedByDescriptor.class,
        FoodDescriptor_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodDescriptor.class,
        FoodDescriptor_dependentQuantificationMethodPathwayForFoodGroupMappedByPhysicalStateFacetDescriptor.class,
        FoodDescriptor_dependentMaximumValueForFoodOrGroupMappedByFacetDescriptor.class,
        FoodDescriptor_dependentRecipeIngredientMappedByFacetDescriptors.class,
        FoodDescriptor_dependentFacetDescriptorThatCannotBeSubstitutedMappedByDescriptor.class);
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentDensityFactorForFoodOrRecipeMappedByFacetDescriptors {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<DensityFactorForFoodOrRecipe> coll() {
            return dependantLookup.findDependants(
                DensityFactorForFoodOrRecipe.class,
                DensityFactorForFoodOrRecipe_facetDescriptors.class,
                DensityFactorForFoodOrRecipe_facetDescriptors::coll,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentEdiblePartCoefficientForFoodMappedByFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<EdiblePartCoefficientForFood> coll() {
            return dependantLookup.findDependants(
                EdiblePartCoefficientForFood.class,
                EdiblePartCoefficientForFood_facetDescriptor.class,
                EdiblePartCoefficientForFood_facetDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentPercentOfFatUseDuringCookingForFoodMappedByCookingMethodFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<PercentOfFatUseDuringCookingForFood> coll() {
            return dependantLookup.findDependants(
                PercentOfFatUseDuringCookingForFood.class,
                PercentOfFatUseDuringCookingForFood_cookingMethodFacetDescriptor.class,
                PercentOfFatUseDuringCookingForFood_cookingMethodFacetDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentRawToCookedConversionFactorForFoodMappedByFacetDescriptors {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<RawToCookedConversionFactorForFood> coll() {
            return dependantLookup.findDependants(
                RawToCookedConversionFactorForFood.class,
                RawToCookedConversionFactorForFood_facetDescriptors.class,
                RawToCookedConversionFactorForFood_facetDescriptors::coll,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentFoodFacetRuleMappedByFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<FoodFacetRule> coll() {
            return dependantLookup.findDependants(
                FoodFacetRule.class,
                FoodFacetRule_facetDescriptor.class,
                FoodFacetRule_facetDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentImprobableSequenceOfFacetAndDescriptorMappedByDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<ImprobableSequenceOfFacetAndDescriptor> coll() {
            return dependantLookup.findDependants(
                ImprobableSequenceOfFacetAndDescriptor.class,
                ImprobableSequenceOfFacetAndDescriptor_descriptor.class,
                ImprobableSequenceOfFacetAndDescriptor_descriptor::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForFoodGroup.class,
                FacetDescriptorPathwayForFoodGroup_foodDescriptor.class,
                FacetDescriptorPathwayForFoodGroup_foodDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentQuantificationMethodPathwayForFoodGroupMappedByPhysicalStateFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForFoodGroup.class,
                QuantificationMethodPathwayForFoodGroup_physicalStateFacetDescriptor.class,
                QuantificationMethodPathwayForFoodGroup_physicalStateFacetDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentMaximumValueForFoodOrGroupMappedByFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<MaximumValueForFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                MaximumValueForFoodOrGroup.class,
                MaximumValueForFoodOrGroup_facetDescriptor.class,
                MaximumValueForFoodOrGroup_facetDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentRecipeIngredientMappedByFacetDescriptors {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<RecipeIngredient> coll() {
            return dependantLookup.findDependants(
                RecipeIngredient.class,
                RecipeIngredient_facetDescriptors.class,
                RecipeIngredient_facetDescriptors::coll,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodDescriptor_dependentFacetDescriptorThatCannotBeSubstitutedMappedByDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodDescriptor mixee;

        @MemberSupport
        public List<FacetDescriptorThatCannotBeSubstituted> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorThatCannotBeSubstituted.class,
                FacetDescriptorThatCannotBeSubstituted_descriptor.class,
                FacetDescriptorThatCannotBeSubstituted_descriptor::prop,
                mixee);
        }
    }
}
