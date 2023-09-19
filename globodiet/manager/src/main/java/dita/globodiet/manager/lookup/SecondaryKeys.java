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
package dita.globodiet.manager.lookup;

import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.classification.RecipeGroup;
import dita.globodiet.dom.params.classification.RecipeSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood;
import dita.globodiet.dom.params.food_descript.Brand;
import dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor;
import dita.globodiet.dom.params.food_descript.RuleAppliedToFacet;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrASubSubgroup;
import dita.globodiet.dom.params.food_probing.ProbingQuestionsPathwaysForFood;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe;
import dita.globodiet.dom.params.recipe_description.BrandForRecipe;
import dita.globodiet.dom.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor;
import dita.globodiet.dom.params.recipe_description.RecipeDescriptor;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrASubgroup;
import dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipe;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted;
import dita.globodiet.dom.params.setting.GroupOrSubgroupThatCanBeSubstitutable;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SecondaryKeys {

    enum LookupMode {
        /**
         * Fail if referenced entity cannot be found.
         */
        STRICT,
        /**
         * Finding no match is valid business logic.
         */
        NULLABLE,
        /**
         * Return a pseudo entity indicating that a matching entity could not be found.
         */
        RELAXED;
        public boolean isStrict() { return this == STRICT;}
        public boolean isNullable() { return this == NULLABLE;}
        public boolean isRelaxed() { return this == RELAXED;}
    }

    static interface SecondaryKeyLookup<T> {
        Optional<T> lookup(RepositoryService repositoryService);
    }

    // -- FACET DESCRIPTOR

    record FacetDescriptorKey(LookupMode lookupMode, String facetCode, String descriptorCode)
    implements SecondaryKeyLookup<FacetDescriptor> {

        // -- FACTORIES

        static FacetDescriptorKey auto(final Object referencingEntity) {
            if(referencingEntity instanceof CrossReferenceBetweenFoodGroupAndDescriptor x) {
                return new FacetDescriptorKey(LookupMode.STRICT, x.getFacetCode(), x.getDescriptorCode());
            }
            if(referencingEntity instanceof FacetDescriptorThatCannotBeSubstituted x) {
                return new FacetDescriptorKey(LookupMode.STRICT, x.getFacetCode(), x.getDescriptorCode());
            }
            if(referencingEntity instanceof ImprobableSequenceOfFacetAndDescriptor x) {
                return new FacetDescriptorKey(LookupMode.RELAXED, x.getFacetCode(), x.getDescriptorCode());
            }
            throw _Exceptions.noSuchElement("FacetDescriptorKey not implemented for %s",
                    referencingEntity.getClass());
        }

        /**
         * Facet string multiple (descface.facet_code + descface.descr_code)comma separated (e.g. 0401,0203,051)
         */
        static FacetDescriptorKey decodeLookupKey(final LookupMode lookupMode, final @NonNull String input) {
            _Assert.assertEquals(4, input.length(),
                    ()->String.format("invalid 4 digit facet descriptor lookup key: %s", input));
            return new FacetDescriptorKey(lookupMode, input.substring(0, 2), input.substring(2));
        }

        /**
         * comma separated (e.g. 0300,0301)
         */
        static Can<FacetDescriptorKey> decodeLookupKeyList(final @Nullable String input) {
            return _Strings.isEmpty(input)
                    ? Can.empty()
                    : _Strings.splitThenStream(input, ",")
                    //TODO relaxed because of FacetDescriptor not found matching 04|03 in RawToCookedConversionFactorForFood
                    .map(chunk->decodeLookupKey(LookupMode.RELAXED, chunk))
                    .collect(Can.toCan());
        }

        // -- MATCHING

        boolean matches(final String facetCode, final String descriptorCode) {
            return Objects.equals(this.facetCode(), facetCode)
                    && Objects.equals(this.descriptorCode(), descriptorCode);
        }

        // -- FACET DESCRIPTOR LOOKUP

        @Override
        public Optional<FacetDescriptor> lookup(final RepositoryService repositoryService) {
            switch (lookupMode()) {
            case STRICT:
                return Optional.of(facetDescriptorElseThrow(repositoryService));
            case RELAXED:
                return Optional.of(facetDescriptorElseUnresolved(repositoryService));
            case NULLABLE:
                return lookupFacetDescriptor(repositoryService);
            }
            throw _Exceptions.unmatchedCase(lookupMode());
        }
        private Optional<FacetDescriptor> lookupFacetDescriptor(final RepositoryService repositoryService) {
            return repositoryService.uniqueMatch(FacetDescriptor.class, fd->
                matches(fd.getFacetCode(), fd.getCode()));
        }
        private FacetDescriptor facetDescriptorElseThrow(final RepositoryService repositoryService) {
            return lookupFacetDescriptor(repositoryService)
                    .orElseThrow(()->_Exceptions.noSuchElement("FacetDescriptor not found matching %s|%s",
                            _Strings.nonEmpty(this.facetCode()).orElse("-"),
                            _Strings.nonEmpty(this.descriptorCode()).orElse("-")));
        }
        private FacetDescriptor facetDescriptorElseUnresolved(final RepositoryService repositoryService) {
            return lookupFacetDescriptor(repositoryService)
                    .orElseGet(()->Unresolvables.facetDescriptorNotFound(this));
        }
    }

    // -- RECIPE DESCRIPTOR

    record RecipeDescriptorKey(LookupMode lookupMode, String recipeFacetCode, String recipeDescriptorCode)
    implements SecondaryKeyLookup<RecipeDescriptor> {

        // -- FACTORIES

        static RecipeDescriptorKey auto(final Object referencingEntity) {
            if(referencingEntity instanceof CrossReferenceBetweenRecipeGroupAndDescriptor x) {
                return new RecipeDescriptorKey(LookupMode.STRICT, x.getRecipeFacetCode(), x.getRecipeDescriptorCode());
            }
            throw _Exceptions.noSuchElement("RecipeDescriptorKey not implemented for %s",
                    referencingEntity.getClass());
        }

        // -- MATCHING

        boolean matches(final String recipeFacetCode, final String recipeDescriptorCode) {
            return Objects.equals(this.recipeFacetCode(), recipeFacetCode)
                    && Objects.equals(this.recipeDescriptorCode(), recipeDescriptorCode);
        }

        @Override
        public Optional<RecipeDescriptor> lookup(final RepositoryService repositoryService) {
            switch (lookupMode()) {
            case STRICT:
                return Optional.of(recipeDescriptorElseThrow(repositoryService));
            case RELAXED:
                return Optional.of(recipeDescriptorElseUnresolved(repositoryService));
            case NULLABLE:
                return lookupRecipeDescriptor(repositoryService);
            }
            throw _Exceptions.unmatchedCase(lookupMode());
        }
        private Optional<RecipeDescriptor> lookupRecipeDescriptor(final RepositoryService repositoryService) {
            return repositoryService.uniqueMatch(RecipeDescriptor.class, fd->
                matches(fd.getRecipeFacetCode(), fd.getCode()));
        }
        private RecipeDescriptor recipeDescriptorElseThrow(final RepositoryService repositoryService) {
            return lookupRecipeDescriptor(repositoryService)
                    .orElseThrow(()->_Exceptions.noSuchElement("RecipeDescriptor not found matching %s|%s",
                            _Strings.nonEmpty(this.recipeDescriptorCode()).orElse("-"),
                            _Strings.nonEmpty(this.recipeFacetCode()).orElse("-")));
        }
        private RecipeDescriptor recipeDescriptorElseUnresolved(final RepositoryService repositoryService) {
            return lookupRecipeDescriptor(repositoryService)
                    .orElseGet(()->Unresolvables.recipeDescriptorNotFound(this));
        }
    }

    // -- FOOD GROUP

    record FoodGroupKey(LookupMode lookupMode, String foodGroupCode)
    implements SecondaryKeyLookup<FoodGroup> {

        public FoodGroupKey {
            _Strings.requireNonEmpty(foodGroupCode, "foodGroupCode");
        }

        static FoodGroupKey of(final String foodGroupCode) {
            return new FoodGroupKey(LookupMode.STRICT, foodGroupCode);
        }

        // -- MATCHING

        boolean matches(final String foodGroupCode) {
            return Objects.equals(this.foodGroupCode(), foodGroupCode);
        }

        @Override
        public Optional<FoodGroup> lookup(final RepositoryService repositoryService) {
            switch (lookupMode()) {
            case STRICT:
                return Optional.of(foodGroupElseThrow(repositoryService));
//            case RELAXED:
//                return Optional.of(recipeSubgroupElseUnresolved(repositoryService));
//            case NULLABLE:
//                return lookupRecipeSubgroup(repositoryService);
            }
            throw _Exceptions.unmatchedCase(lookupMode());
        }
        private FoodGroup foodGroupElseThrow(final RepositoryService repositoryService){
            return repositoryService.uniqueMatch(FoodGroup.class, group->matches(group.getCode()))
                    .orElseThrow(()->_Exceptions.noSuchElement("FoodGroup not found matching %s",
                            foodGroupCode()));
        }
    }


    // -- FOOD SUBGROUP

    record FoodSubgroupKey(LookupMode lookupMode,
            String foodGroupCode, String foodSubgroupCode, @Nullable String foodSubSubgroupCode)
    implements SecondaryKeyLookup<FoodSubgroup> {

        public FoodSubgroupKey {
            _Strings.requireNonEmpty(foodGroupCode, "foodGroupCode");
            _Strings.requireNonEmpty(foodSubgroupCode, "foodSubgroupCode");
        }

        static Either<FoodGroupKey, FoodSubgroupKey> auto(
                final @NonNull LookupMode lookupMode,
                final @NonNull String foodGroupCode,
                final @Nullable String foodSubgroupCode,
                final @Nullable String foodSubSubgroupCode) {
            return _Strings.isEmpty(foodSubgroupCode)
                    ? Either.left(FoodGroupKey.of(foodGroupCode))
                    : Either.right(new FoodSubgroupKey(lookupMode,
                            foodGroupCode, foodSubgroupCode, foodSubSubgroupCode));
        }

        static Either<FoodGroupKey, FoodSubgroupKey> strict(
                final @NonNull String foodGroupCode,
                final @Nullable String foodSubgroupCode,
                final @Nullable String foodSubSubgroupCode) {
            return auto(LookupMode.STRICT, foodGroupCode, foodSubgroupCode, foodSubSubgroupCode);
        }

        static Either<FoodGroupKey, FoodSubgroupKey> relaxed(
                final @NonNull String foodGroupCode,
                final @Nullable String foodSubgroupCode,
                final @Nullable String foodSubSubgroupCode) {
            return auto(LookupMode.RELAXED, foodGroupCode, foodSubgroupCode, foodSubSubgroupCode);
        }

        static Either<FoodGroupKey, FoodSubgroupKey> nullable(
                final @NonNull String foodGroupCode,
                final @Nullable String foodSubgroupCode,
                final @Nullable String foodSubSubgroupCode) {
            return auto(LookupMode.NULLABLE, foodGroupCode, foodSubgroupCode, foodSubSubgroupCode);
        }

        static Either<FoodGroupKey, FoodSubgroupKey> auto(final Class<?> mixinClass, final Object entity) {
            if(entity instanceof Brand local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof CrossReferenceBetweenFoodGroupAndDescriptor local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof FoodOrProductOrAlias local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof GroupOrSubgroupThatCanBeSubstitutable local) {
                return relaxed(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof MaximumValueForAFoodOrASubSubgroup local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof PercentOfFatLeftInTheDishForFood local) {
                return strict(local.getFatGroupCode(), local.getFatSubgroupCode(), local.getFatSubSubgroupCode());
            }
            if(entity instanceof PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood local) {
                if(PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup.class.equals(mixinClass)
                        || PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup.class.equals(mixinClass)) {
                    return strict(local.getFssFatGroupCode(), local.getFssFatSubgroupCode(), local.getFssFatSubSubgroupCode());
                }
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof PercentOfFatUseDuringCookingForFood local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof ProbingQuestionsPathwaysForFood local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof RecipeIngredient local) {
                return strict(local.getFoodOrRecipeGroupCode(), local.getFoodOrRecipeSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof RuleAppliedToFacet local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof QuantificationMethodsPathwayForFoodGroup local) {
                return strict(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe local) {
                return strict(local.getFssFatGroupCode(), local.getFssFatSubgroupCode(), local.getFssFatSubSubgroupCode());
            }
            throw _Exceptions.illegalArgument("FoodSubgroupKey: Unrecognized entity %s", entity.getClass());
        }



        /**
         * either 2, 4 or 6 digits (e.g. 02, 0300 or 010633)
         */
        static Either<FoodGroupKey, FoodSubgroupKey> decodeLookupKey(final @NonNull String input) {
            _Assert.assertTrue(input.length()==2
                    || input.length()==4
                    || input.length()==6);
            return strict(
                    input.substring(0, 2),
                    input.length()>=4
                        ? input.substring(2, 4)
                        : null,
                    input.length()==6
                        ? input.substring(4)
                        : null
                    );
        }

        /**
         * comma separated (e.g. 02,0300,0301)
         */
        static Can<Either<FoodGroupKey, FoodSubgroupKey>> decodeLookupKeyList(final @NonNull String input) {
            return _Strings.splitThenStream(input, ",")
                .map(chunk->decodeLookupKey(chunk))
                .collect(Can.toCan());
        }

        // -- MATCHING

        boolean matches(
                final String foodGroupCode, final String foodSubgroupCode, final String foodSubSubgroupCode) {
            return Objects.equals(this.foodGroupCode(), foodGroupCode)
                    && Objects.equals(this.foodSubgroupCode(), foodSubgroupCode)
                    && Objects.equals(this.foodSubSubgroupCode(), foodSubSubgroupCode);
        }


        @Override
        public Optional<FoodSubgroup> lookup(final RepositoryService repositoryService) {
            switch (lookupMode()) {
            case STRICT:
                return Optional.of(foodSubgroupElseThrow(repositoryService));
            case RELAXED:
                return Optional.of(foodSubgroupElseUnresolved(repositoryService));
            case NULLABLE:
                return lookupFoodSubgroup(repositoryService);
            }
            throw _Exceptions.unmatchedCase(lookupMode());
        }

        private Optional<FoodSubgroup> lookupFoodSubgroup(final RepositoryService repositoryService){
            return repositoryService.uniqueMatch(FoodSubgroup.class, subgroup->
                matches(subgroup.getFoodGroupCode(),
                        subgroup.getFoodSubgroupCode(),
                        subgroup.getFoodSubSubgroupCode()));
        }
        private FoodSubgroup foodSubgroupElseThrow(final RepositoryService repositoryService){
            return lookupFoodSubgroup(repositoryService)
                    .orElseThrow(()->_Exceptions.noSuchElement("FoodSubgroup not found matching %s|%s|%s",
                            foodGroupCode(),
                            foodSubgroupCode(),
                            _Strings.nonEmpty(foodSubSubgroupCode()).orElse("-")));
        }
        private FoodSubgroup foodSubgroupElseUnresolved(final RepositoryService repositoryService){
            return lookupFoodSubgroup(repositoryService)
                    .orElseGet(()->Unresolvables.foodSubgroupNotFound(
                            foodGroupCode(),
                            foodSubgroupCode(),
                            foodSubSubgroupCode()));
        }
    }

    // -- RECIPE GROUP

    record RecipeGroupKey(LookupMode lookupMode, String recipeGroupCode)
    implements SecondaryKeyLookup<RecipeGroup> {

        public RecipeGroupKey {
            _Strings.requireNonEmpty(recipeGroupCode, "recipeGroupCode");
        }

        static RecipeGroupKey of(final String recipeGroupCode) {
            return new RecipeGroupKey(LookupMode.STRICT, recipeGroupCode);
        }

        // -- MATCHING

        boolean matches(final String recipeGroupCode) {
            return Objects.equals(this.recipeGroupCode(), recipeGroupCode);
        }

        @Override
        public Optional<RecipeGroup> lookup(final RepositoryService repositoryService) {
            switch (lookupMode()) {
            case STRICT:
                return Optional.of(recipeGroupElseThrow(repositoryService));
//            case RELAXED:
//                return Optional.of(recipeSubgroupElseUnresolved(repositoryService));
//            case NULLABLE:
//                return lookupRecipeSubgroup(repositoryService);
            }
            throw _Exceptions.unmatchedCase(lookupMode());
        }
        private RecipeGroup recipeGroupElseThrow(final RepositoryService repositoryService){
            return repositoryService.uniqueMatch(RecipeGroup.class, group->matches(group.getCode()))
                    .orElseThrow(()->_Exceptions.noSuchElement("RecipeGroup not found matching %s",
                            recipeGroupCode()));
        }
    }

    // -- RECIPE SUBGROUP

    record RecipeSubgroupKey(LookupMode lookupMode, String recipeGroupCode, String recipeSubgroupCode)
    implements SecondaryKeyLookup<RecipeSubgroup> {

        public RecipeSubgroupKey {
            _Strings.requireNonEmpty(recipeGroupCode, "recipeGroupCode");
            _Strings.requireNonEmpty(recipeSubgroupCode, "recipeSubgroupCode");
        }

        static RecipeSubgroupKey of(final String recipeGroupCode, final String recipeSubgroupCode) {
            return new RecipeSubgroupKey(LookupMode.STRICT, recipeGroupCode, recipeSubgroupCode);
        }

        static Either<RecipeGroupKey, RecipeSubgroupKey> auto(
                final @NonNull String recipeGroupCode,
                final @Nullable String recipeSubgroupCode) {
            return _Strings.isEmpty(recipeSubgroupCode)
                    ? Either.left(RecipeGroupKey.of(recipeGroupCode))
                    : Either.right(of(recipeGroupCode, recipeSubgroupCode));
        }

        static Either<RecipeGroupKey, RecipeSubgroupKey> auto(final Object entity) {
            if(entity instanceof BrandForRecipe local) {
                return auto(local.getRecipeGroupCode(), local.getRecipeSubgroupCode());
            }
            if(entity instanceof CrossReferenceBetweenRecipeGroupAndDescriptor local) {
                return auto(local.getRecipeGroupCode(), local.getRecipeSubgroupCode());
            }
            if(entity instanceof MaximumValueForARecipeOrASubgroup local) {
                return auto(local.getRecipeGroupCode(), local.getRecipeSubgroupCode());
            }
            if(entity instanceof PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe local) {
                return auto(local.getRecipeGroupCode(), local.getRecipeSubgroupCode());
            }
            if(entity instanceof ProbingQuestionPathwayForRecipe local) {
                return auto(local.getRecipeGroupCode(), local.getRecipeSubgroupCode());
            }
            if(entity instanceof Recipe local) {
                return auto(local.getRecipeGroupCode(), local.getRecipeSubgroupCode());
            }
            if(entity instanceof RecipeIngredient local) {
                return auto(local.getFoodOrRecipeGroupCode(), local.getFoodOrRecipeSubgroupCode());
            }
            throw _Exceptions.illegalArgument("Unrecognized entity %s", entity.getClass());
        }

        /**
         * either 2 or 4 digits (e.g. 02 or 0300)
         */
        static Either<RecipeGroupKey, RecipeSubgroupKey> decodeLookupKey(final @NonNull String input) {
            _Assert.assertTrue(input.length()==2
                    || input.length()==4);
            return auto(
                    input.substring(0, 2),
                    input.length()>=4
                        ? input.substring(2, 4)
                        : null);
        }

        /**
         * comma separated (e.g. 02,0300,0301)
         */
        static Can<Either<RecipeGroupKey, RecipeSubgroupKey>> decodeLookupKeyList(final @NonNull String input) {
            return _Strings.splitThenStream(input, ",")
                .map(chunk->decodeLookupKey(chunk))
                .collect(Can.toCan());
        }


        // -- MATCHING

        boolean matches(final String recipeGroupCode, final String recipeSubgroupCode) {
            return Objects.equals(this.recipeGroupCode(), recipeGroupCode)
                    && Objects.equals(this.recipeSubgroupCode(), recipeSubgroupCode);
        }

        @Override
        public Optional<RecipeSubgroup> lookup(final RepositoryService repositoryService) {
            switch (lookupMode()) {
            case STRICT:
                return Optional.of(recipeSubgroupElseThrow(repositoryService));
//            case RELAXED:
//                return Optional.of(recipeSubgroupElseUnresolved(repositoryService));
//            case NULLABLE:
//                return lookupRecipeSubgroup(repositoryService);
            }
            throw _Exceptions.unmatchedCase(lookupMode());
        }
        private RecipeSubgroup recipeSubgroupElseThrow(final RepositoryService repositoryService){
            return repositoryService.uniqueMatch(RecipeSubgroup.class, subgroup->
                matches(subgroup.getRecipeGroupCode(), subgroup.getCode()))
                    .orElseThrow(()->_Exceptions.noSuchElement("RecipeSubgroup not found matching %s|%s",
                            recipeGroupCode(), recipeSubgroupCode()));
        }

    }

}
