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
package dita.globodiet.dom.params.food_descript;

import dita.commons.services.lookup.DependantLookupService;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood_facetDescriptors;
import dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood;
import dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood_facetDescriptor;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_cookingMethodFacetDescriptor;
import dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood;
import dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood_facetDescriptors;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrGroup;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrGroup_facetDescriptor;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodPathwayForFoodGroup_physicalStateFacetDescriptor;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient_facetDescriptors;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted_descriptor;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacetDescriptorDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(FacetDescriptor_dependentDensityFactorForFoodMappedByFacetDescriptors.class,
        FacetDescriptor_dependentEdiblePartCoefficientForFoodMappedByFacetDescriptor.class,
        FacetDescriptor_dependentPercentOfFatUseDuringCookingForFoodMappedByCookingMethodFacetDescriptor.class,
        FacetDescriptor_dependentRawToCookedConversionFactorForFoodMappedByFacetDescriptors.class,
        FacetDescriptor_dependentFacetDescriptorPathwayForFoodGroupMappedByDescriptor.class,
        FacetDescriptor_dependentImprobableSequenceOfFacetAndDescriptorMappedByDescriptor.class,
        FacetDescriptor_dependentRuleAppliedToFacetMappedByFacetDescriptor.class,
        FacetDescriptor_dependentMaximumValueForAFoodOrGroupMappedByFacetDescriptor.class,
        FacetDescriptor_dependentQuantificationMethodPathwayForFoodGroupMappedByPhysicalStateFacetDescriptor.class,
        FacetDescriptor_dependentRecipeIngredientMappedByFacetDescriptors.class,
        FacetDescriptor_dependentFacetDescriptorThatCannotBeSubstitutedMappedByDescriptor.class);
    }

    @Collection
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentDensityFactorForFoodMappedByFacetDescriptors {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

        @MemberSupport
        public List<DensityFactorForFood> coll() {
            return dependantLookup.findDependants(
                DensityFactorForFood.class,
                DensityFactorForFood_facetDescriptors.class,
                DensityFactorForFood_facetDescriptors::coll,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentEdiblePartCoefficientForFoodMappedByFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

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
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentPercentOfFatUseDuringCookingForFoodMappedByCookingMethodFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

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
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentRawToCookedConversionFactorForFoodMappedByFacetDescriptors {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

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
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentFacetDescriptorPathwayForFoodGroupMappedByDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForFoodGroup.class,
                FacetDescriptorPathwayForFoodGroup_descriptor.class,
                FacetDescriptorPathwayForFoodGroup_descriptor::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentImprobableSequenceOfFacetAndDescriptorMappedByDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

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
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentRuleAppliedToFacetMappedByFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

        @MemberSupport
        public List<RuleAppliedToFacet> coll() {
            return dependantLookup.findDependants(
                RuleAppliedToFacet.class,
                RuleAppliedToFacet_facetDescriptor.class,
                RuleAppliedToFacet_facetDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentMaximumValueForAFoodOrGroupMappedByFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

        @MemberSupport
        public List<MaximumValueForAFoodOrGroup> coll() {
            return dependantLookup.findDependants(
                MaximumValueForAFoodOrGroup.class,
                MaximumValueForAFoodOrGroup_facetDescriptor.class,
                MaximumValueForAFoodOrGroup_facetDescriptor::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentQuantificationMethodPathwayForFoodGroupMappedByPhysicalStateFacetDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

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
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentRecipeIngredientMappedByFacetDescriptors {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

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
    @RequiredArgsConstructor
    public static class FacetDescriptor_dependentFacetDescriptorThatCannotBeSubstitutedMappedByDescriptor {
        @Inject
        DependantLookupService dependantLookup;

        private final FacetDescriptor mixee;

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
