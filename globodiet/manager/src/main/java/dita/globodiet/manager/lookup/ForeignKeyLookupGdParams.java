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
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.linking.DeepLinkService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.value.Markup;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.commons.types.BiString;
import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.classification.RecipeGroup;
import dita.globodiet.dom.params.classification.RecipeSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood;
import dita.globodiet.dom.params.food_descript.Brand;
import dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodAndFacet;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor;
import dita.globodiet.dom.params.food_descript.RuleAppliedToFacet;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrASubSubgroup;
import dita.globodiet.dom.params.food_probing.ProbingQuestionsPathwaysForFood;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup;
import dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_foodSubgroups;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe;
import dita.globodiet.dom.params.recipe_description.BrandForRecipe;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrASubgroup;
import dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipe;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted;
import dita.globodiet.dom.params.setting.GroupOrSubgroupThatCanBeSubstitutable;
import lombok.NonNull;
import lombok.val;

@Service
public class ForeignKeyLookupGdParams
implements ForeignKeyLookupService {

    @Inject RepositoryService repositoryService;
    @Inject DeepLinkService deepLinkService;

    @Override
    public <L, F> Optional<F> unary(final L localEntity, final String localFieldName, final Object localField,
            final Class<F> foreignType, final Function<F, Object> foreignFieldGetter) {

        if(localField == null) return Optional.empty();

        if(FoodOrProductOrAlias.class.equals(foreignType)) {
            // do not lookup SH entries (aliases)
            return repositoryService.uniqueMatch(foreignType, foreign->
                Objects.equals(localField, foreignFieldGetter.apply(foreign))
                    && !Objects.equals("SH", ((FoodOrProductOrAlias)foreign).getTypeOfItem()));
        }
        if(FoodSubgroup.class.equals(foreignType)) {
            return foodSubgroup(foreignType, GroupDiscriminator.auto(localEntity));
        }
        if(RecipeSubgroup.class.equals(foreignType)) {
            return recipeSubgroup(foreignType, GroupDiscriminator.auto(localEntity));
        }
        if(FacetDescriptor.class.equals(foreignType)) {
            return facetDescriptor(foreignType, SecondaryKeyForFacetDescriptor.auto(localEntity));
        }

        return repositoryService.uniqueMatch(foreignType, foreign->
            Objects.equals(localField, foreignFieldGetter.apply(foreign)));
    }

    @Override
    public <L, F> Optional<F> binary(final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Function<F, Object> foreignFieldGetter1,
            final Function<F, Object> foreignFieldGetter2) {

        if(localField == null) return Optional.empty();
        if(FacetDescriptor.class.equals(foreignType)) {
            val key = decodeFacetDescriptorLookupKey((String) localField);
            return repositoryService.uniqueMatch(foreignType, foreign->
                key.equalsPair(
                        (String)foreignFieldGetter1.apply(foreign),
                        (String)foreignFieldGetter2.apply(foreign)));

        }
        throw _Exceptions.unrecoverable("2-ary foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    @Override
    public <L, F1, F2> Optional<Either<F1, F2>> either(final L localEntity, final Object localField,
            final Class<F1> foreignType1, final Function<F1, Object> foreignFieldGetter1,
            final Class<F2> foreignType2, final Function<F2, Object> foreignFieldGetter2) {

        final Optional<F1> left = unary(localEntity, "", localField, foreignType1, foreignFieldGetter1);
        final Optional<F2> right = left.isPresent()
                ? Optional.empty()
                : unary(localEntity, "", localField, foreignType2, foreignFieldGetter2);
        final Either<F1, F2> either = left.isPresent()
                ? Either.left(left.get())
                : right.isPresent()
                        ? Either.right(right.get())
                        : null;

        return Optional.ofNullable(either);

        //FoodOrProductOrAlias.class, foreign->foreign.getFoodIdNumber(),
        //MixedRecipeName.class, foreign->foreign.getRecipeIDNumber())

        //PhotoForQuantity.class, foreign->foreign.getCode(),
        //Shape.class, foreign->foreign.getShapeCode())

        //FoodGroup.class, foreign->foreign.getFoodGroupCode(),
        //RecipeGroup.class, foreign->foreign.getRecipeGroupCode())

        //FoodSubgroup.class, foreign->foreign.getFoodSubgroupCode(),
        //RecipeGroupOrSubgroup.class, foreign->foreign.getRecipeSubgroupCode())
    }

    /**
     * @see ThicknessForShapeMethod_foodSubgroups
     */
    @Override
    public <L, F> Markup plural(
            final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Can<Function<F, Object>> foreignFieldGetters) {

        if(FoodSubgroup.class.equals(foreignType)) {
            val keys = decodeGroupListLookupKey((String) localField);
            final Can<Either<FoodGroup, FoodSubgroup>> groups = keys.map(this::foodGroupOrSubgroupElseThrow);
            return new Markup(groups.stream()
                    .map(group->group.fold(FoodGroup::title, FoodSubgroup::title))
                    .collect(Collectors.joining("<br>")));
        }
        if(RecipeSubgroup.class.equals(foreignType)) {
            val keys = decodeGroupListLookupKey((String) localField);
            final Can<Either<RecipeGroup, RecipeSubgroup>> groups = keys.map(this::recipeGroupOrSubgroupElseThrow);
            return new Markup(groups.stream()
                    .map(group->group.fold(RecipeGroup::title, RecipeSubgroup::title))
                    .collect(Collectors.joining("<br>")));
        }

        throw _Exceptions.unrecoverable("plural foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    // -- HELPER

    /**
     * Facet string multiple (descface.facet_code + descface.descr_code)comma separated (e.g. 0401,0203,051)
     */
    static BiString decodeFacetDescriptorLookupKey(final @NonNull String input) {
        _Assert.assertEquals(4, input.length());
        return new BiString(input.substring(0, 2), input.substring(2));
    }

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

    /**
     * Pair of {@link String}. Simply a typed tuple. With the second field an optional
     */
    record GroupDiscriminator(LookupMode lookupMode, String left, Optional<String> middle, Optional<String> right) {
        public GroupDiscriminator {

            _Strings.requireNonEmpty(left, "left");
            middle.ifPresent(r->_Strings.requireNonEmpty(r, "middle"));
            right.ifPresent(r->_Strings.requireNonEmpty(r, "right"));
        }
        static GroupDiscriminator of(final String left, final String middle, final String right) {
            return new GroupDiscriminator(LookupMode.STRICT, left, Optional.ofNullable(middle), Optional.ofNullable(right));
        }
        static GroupDiscriminator ofRelaxed(final String left, final String middle, final String right) {
            return new GroupDiscriminator(LookupMode.RELAXED, left, Optional.ofNullable(middle), Optional.ofNullable(right));
        }
        static GroupDiscriminator auto(final Object entity) {
            if(entity instanceof Brand local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof FoodOrProductOrAlias local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof GroupOrSubgroupThatCanBeSubstitutable local) {
                return ofRelaxed(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof MaximumValueForAFoodOrASubSubgroup local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof PercentOfFatLeftInTheDishForFood local) {
                return of(local.getFatGroupCode(), local.getFatSubgroupCode(), local.getFatSubSubgroupCode());
            }
            //TODO ambiguous with fss...
            if(entity instanceof PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof PercentOfFatUseDuringCookingForFood local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof ProbingQuestionsPathwaysForFood local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof RuleAppliedToFacet local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }
            if(entity instanceof QuantificationMethodsPathwayForFoodGroup local) {
                return of(local.getFoodGroupCode(), local.getFoodSubgroupCode(), local.getFoodSubSubgroupCode());
            }

            if(entity instanceof BrandForRecipe local) {
                return of(local.getRecipeGroupCode(), local.getRecipeSubgroupCode(), null);
            }
            if(entity instanceof MaximumValueForARecipeOrASubgroup local) {
                return of(local.getRecipeGroupCode(), local.getRecipeSubgroupCode(), null);
            }
            //FIXME ambiguous type to return here? RECIPE- or FOOD-GROUP which shall it be
            if(entity instanceof PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe local) {
                return of(local.getRecipeGroupCode(), local.getRecipeSubgroupCode(), null);
            }
            if(entity instanceof ProbingQuestionPathwayForRecipe local) {
                return of(local.getRecipeGroupCode(), local.getRecipeSubgroupCode(), null);
            }
            if(entity instanceof Recipe local) {
                return of(local.getRecipeGroupCode(), local.getRecipeSubgroupCode(), null);
            }

            throw _Exceptions.illegalArgument("Unrecognized entity %s", entity.getClass());

        }

//        public boolean equalsTuple(final String left, final String middle, final String right) {
//            return Objects.equals(this.left, left)
//                && Objects.equals(this.middle.orElse(null), middle)
//                && Objects.equals(this.right.orElse(null), right);
//        }
    }

    /**
     * comma separated (e.g. 02 or 0300)
     */
    static GroupDiscriminator decodeGroupLookupKey(final @NonNull String input) {
        _Assert.assertTrue(input.length()==2
                || input.length()==4
                || input.length()==6);
        return GroupDiscriminator.of(
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
    static Can<GroupDiscriminator> decodeGroupListLookupKey(final @NonNull String input) {
        return _Strings.splitThenStream(input, ",")
            .map(chunk->decodeGroupLookupKey(chunk))
            .collect(Can.toCan());
    }

    // -- GROUP LOOKUPS (FOOD)

    private Either<FoodGroup, FoodSubgroup> foodGroupOrSubgroupElseThrow(final GroupDiscriminator discriminator){
        return discriminator.middle().isPresent()
                ? Either.right(foodSubgroupElseThrow(discriminator.left(), discriminator.middle().get(), discriminator.right().orElse(null)))
                : Either.left(foodGroupElseThrow(discriminator.left()));
    }

    private FoodGroup foodGroupElseThrow(
            final String groupDiscriminator){
        return repositoryService.uniqueMatch(FoodGroup.class, group->
                    Objects.equals(groupDiscriminator, group.getCode()))
                .orElseThrow(()->_Exceptions.noSuchElement("FoodGroup not found matching %s",
                        _Strings.nonEmpty(groupDiscriminator).orElse("-")));
    }

    @SuppressWarnings("unchecked")
    private <F> Optional<F> foodSubgroup(final Class<F> foreignType, final GroupDiscriminator key){

        final String d1 = key.left();
        final String d2 = key.middle().orElse(null);
        final String d3 = key.right().orElse(null);

        switch (key.lookupMode()) {
        case STRICT:
            return Optional.of((F)foodSubgroupElseThrow(d1, d2, d3));
        case RELAXED:
            return Optional.of((F)lookupFoodSubgroup(d1, d2, d3)
                    .orElseGet(()->Unresolvables.foodSubgroupNotFound(d1, d2, d3)));
        case NULLABLE:
            return (Optional<F>)lookupFoodSubgroup(d1, d2, d3);
        }
        throw _Exceptions.unmatchedCase(key.lookupMode());
    }

    private Optional<FoodSubgroup> lookupFoodSubgroup(
            final String groupDiscriminator,
            final String subgroupDiscriminator,
            final String subsubgroupDiscriminator){
        return repositoryService.uniqueMatch(FoodSubgroup.class, subgroup->
            Objects.equals(groupDiscriminator, subgroup.getFoodGroupCode())
                    && Objects.equals(subgroupDiscriminator, subgroup.getFoodSubgroupCode())
                    && Objects.equals(subsubgroupDiscriminator, subgroup.getFoodSubSubgroupCode()));
    }

    private FoodSubgroup foodSubgroupElseThrow(
            final String groupDiscriminator,
            final String subgroupDiscriminator,
            final String subsubgroupDiscriminator){
        return repositoryService.uniqueMatch(FoodSubgroup.class, subgroup->
            Objects.equals(groupDiscriminator, subgroup.getFoodGroupCode())
                    && Objects.equals(subgroupDiscriminator, subgroup.getFoodSubgroupCode())
                    && Objects.equals(subsubgroupDiscriminator, subgroup.getFoodSubSubgroupCode()))
                .orElseThrow(()->_Exceptions.noSuchElement("FoodSubgroup not found matching %s|%s|%s",
                        _Strings.nonEmpty(groupDiscriminator).orElse("-"),
                        _Strings.nonEmpty(subgroupDiscriminator).orElse("-"),
                        _Strings.nonEmpty(subsubgroupDiscriminator).orElse("-")));
    }

    // -- GROUP LOOKUPS (RECIPE)

    private Either<RecipeGroup, RecipeSubgroup> recipeGroupOrSubgroupElseThrow(final GroupDiscriminator discriminator){
        return discriminator.middle().isPresent()
                ? Either.right(recipeSubgroupElseThrow(discriminator.left(), discriminator.middle().get()))
                : Either.left(recipeGroupElseThrow(discriminator.left()));
    }

    private RecipeGroup recipeGroupElseThrow(
            final String groupDiscriminator){
        return repositoryService.uniqueMatch(RecipeGroup.class, group->
                    Objects.equals(groupDiscriminator, group.getCode()))
                .orElseThrow(()->_Exceptions.noSuchElement("RecipeGroup not found matching %s",
                        _Strings.nonEmpty(groupDiscriminator).orElse("-")));
    }

    @SuppressWarnings("unchecked")
    private <F> Optional<F> recipeSubgroup(final Class<F> foreignType, final GroupDiscriminator discriminator){
        return Optional.of((F)recipeSubgroupElseThrow(
                discriminator.left(),
                discriminator.middle().orElse(null)));
    }

    private RecipeSubgroup recipeSubgroupElseThrow(
            final String groupDiscriminator,
            final String subgroupDiscriminator){
        return repositoryService.uniqueMatch(RecipeSubgroup.class, subgroup->
            Objects.equals(groupDiscriminator, subgroup.getRecipeGroupCode())
                    && Objects.equals(subgroupDiscriminator, subgroup.getCode()))
                .orElseThrow(()->_Exceptions.noSuchElement("RecipeSubgroup not found matching %s|%s",
                        _Strings.nonEmpty(groupDiscriminator).orElse("-"),
                        _Strings.nonEmpty(subgroupDiscriminator).orElse("-")));
    }

    // -- FACET DESCRIPTOR LOOKUP

    record SecondaryKeyForFacetDescriptor(LookupMode lookupMode, String facetCode, String descriptorCode) {
        static SecondaryKeyForFacetDescriptor auto(final Object referencedFromEntity) {
            if(referencedFromEntity instanceof CrossReferenceBetweenFoodAndFacet x) {
                return new SecondaryKeyForFacetDescriptor(LookupMode.STRICT, x.getFacetCode(), x.getDescriptorCode());
            }
            if(referencedFromEntity instanceof FacetDescriptorThatCannotBeSubstituted x) {
                return new SecondaryKeyForFacetDescriptor(LookupMode.STRICT, x.getFacetCode(), x.getDescriptorCode());
            }
            if(referencedFromEntity instanceof ImprobableSequenceOfFacetAndDescriptor x) {
                return new SecondaryKeyForFacetDescriptor(LookupMode.RELAXED, x.getFacetCode(), x.getDescriptorCode());
            }
            throw _Exceptions.noSuchElement("SecondaryKeyForFacetDescriptor not implemented for %s",
                    referencedFromEntity.getClass());
        }
    }

    @SuppressWarnings("unchecked")
    private <F> Optional<F> facetDescriptor(
            final Class<F> foreignType, final SecondaryKeyForFacetDescriptor key) {
        switch (key.lookupMode()) {
        case STRICT:
            return Optional.of((F)facetDescriptorElseThrow(key));
        case RELAXED:
            return Optional.of((F)facetDescriptorElseUnresolved(key));
        case NULLABLE:
            return (Optional<F>)lookupFacetDescriptor(key);
        }
        throw _Exceptions.unmatchedCase(key.lookupMode());
    }

    private Optional<FacetDescriptor> lookupFacetDescriptor(final SecondaryKeyForFacetDescriptor key) {
        return repositoryService.uniqueMatch(FacetDescriptor.class, fd->
            Objects.equals(fd.getFacetCode(), key.facetCode())
                    && Objects.equals(fd.getCode(), key.descriptorCode()));
    }
    private FacetDescriptor facetDescriptorElseThrow(final SecondaryKeyForFacetDescriptor key) {
        return lookupFacetDescriptor(key)
                .orElseThrow(()->_Exceptions.noSuchElement("FacetDescriptor not found matching %s|%s",
                        _Strings.nonEmpty(key.facetCode()).orElse("-"),
                        _Strings.nonEmpty(key.descriptorCode()).orElse("-")));
    }
    private FacetDescriptor facetDescriptorElseUnresolved(final SecondaryKeyForFacetDescriptor key) {
        return lookupFacetDescriptor(key)
                .orElseGet(()->Unresolvables.facetDescriptorNotFound(key.facetCode(), key.facetCode()));
    }

}
