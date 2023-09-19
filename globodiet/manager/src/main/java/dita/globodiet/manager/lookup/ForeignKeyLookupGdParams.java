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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.exceptions.unrecoverable.RepositoryException;
import org.apache.causeway.applib.services.linking.DeepLinkService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.value.Markup;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.classification.RecipeGroup;
import dita.globodiet.dom.params.classification.RecipeSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_foodSubgroups;
import dita.globodiet.dom.params.recipe_description.RecipeDescriptor;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.manager.lookup.SecondaryKeys.FacetDescriptorKey;
import dita.globodiet.manager.lookup.SecondaryKeys.FoodSubgroupKey;
import dita.globodiet.manager.lookup.SecondaryKeys.LookupMode;
import dita.globodiet.manager.lookup.SecondaryKeys.RecipeDescriptorKey;
import dita.globodiet.manager.lookup.SecondaryKeys.RecipeSubgroupKey;
import lombok.val;

@Service
public class ForeignKeyLookupGdParams
implements ForeignKeyLookupService {

    @Inject RepositoryService repositoryService;
    @Inject DeepLinkService deepLinkService;

    @Override
    public <L, F> Optional<F> unary(
            final Object caller,
            final L localEntity, final Object localField,
            final Class<F> foreignType, final Function<F, Object> foreignFieldGetter) {

        if(localField == null) return Optional.empty();

        if(FoodOrProductOrAlias.class.equals(foreignType)) {
            // do not lookup SH entries (aliases)
            return uniqueMatch(foreignType, foreign->
                Objects.equals(localField, foreignFieldGetter.apply(foreign))
                    && !Objects.equals("SH", ((FoodOrProductOrAlias)foreign).getTypeOfItem()),
                ()->String.format("%s AND typeOfItem!='SH'", localField));
        }
        if(FoodSubgroup.class.equals(foreignType)) {
            val mixinClass = caller.getClass();
            if(PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup.class.equals(mixinClass)
                    || PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup.class.equals(mixinClass)) {
                return FoodSubgroupKey.auto(mixinClass, localEntity).right().flatMap(secKey->secKey.lookup(repositoryService))
                        .map(foreignType::cast);
            }
            return FoodSubgroupKey.auto(mixinClass, localEntity).right().orElseThrow().lookup(repositoryService)
                    .map(foreignType::cast);
        }
        if(RecipeSubgroup.class.equals(foreignType)) {
            return RecipeSubgroupKey.auto(localEntity).right().orElseThrow().lookup(repositoryService)
                    .map(foreignType::cast);
        }
        if(FacetDescriptor.class.equals(foreignType)) {
            return FacetDescriptorKey.auto(localEntity).lookup(repositoryService)
                    .map(foreignType::cast);
        }
        if(RecipeDescriptor.class.equals(foreignType)) {
            return RecipeDescriptorKey.auto(localEntity).lookup(repositoryService)
                .map(foreignType::cast);
        }

        return uniqueMatch(foreignType, foreign->
            Objects.equals(localField, foreignFieldGetter.apply(foreign)),
            ()->String.format("%s", localField));
    }

    @Override
    public <L, F> Optional<F> binary(
            final Object caller,
            final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Function<F, Object> foreignFieldGetter1,
            final Function<F, Object> foreignFieldGetter2) {

        if(localField == null) return Optional.empty();
        if(FacetDescriptor.class.equals(foreignType)) {
            val key = FacetDescriptorKey.decodeLookupKey(LookupMode.STRICT, (String) localField);
            return uniqueMatch(foreignType, foreign->
                key.matches(
                        (String)foreignFieldGetter1.apply(foreign),
                        (String)foreignFieldGetter2.apply(foreign)),
                ()->String.format("key==%s", key));

        }
        throw _Exceptions.unrecoverable("2-ary foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    enum Triage {
        LEFT,
        RIGHT,
        TRY_LEFT_FIRST;
        boolean isLeftAllowed() {return this!=RIGHT;}
        boolean isRightAllowed() {return this!=LEFT;}
    }

    /**
     * FoodOrProductOrAlias.class, foreign->foreign.getFoodIdNumber(),<br>
     * MixedRecipeName.class, foreign->foreign.getRecipeIDNumber())
     * <p>
     * PhotoForQuantity.class, foreign->foreign.getCode(),<br>
     * Shape.class, foreign->foreign.getShapeCode())
     * <p>
     * FoodGroup.class, foreign->foreign.getFoodGroupCode(),<br>
     * RecipeGroup.class, foreign->foreign.getRecipeGroupCode())
     * <p>
     * FoodSubgroup.class, foreign->foreign.getFoodSubgroupCode(),<br>
     * RecipeGroupOrSubgroup.class, foreign->foreign.getRecipeSubgroupCode())
     */
    @Override
    public <L, F1, F2> Optional<Either<F1, F2>> either(
            final Object caller,
            final L localEntity, final Object localField,
            final Class<F1> foreignType1, final Function<F1, Object> foreignFieldGetter1,
            final Class<F2> foreignType2, final Function<F2, Object> foreignFieldGetter2) {

        Triage triage = Triage.TRY_LEFT_FIRST;

        if(FoodGroup.class.equals(foreignType1)
                && RecipeGroup.class.equals(foreignType2)
                && (localEntity instanceof RecipeIngredient recipeIngredient)) {

            triage = switch(recipeIngredient.getTypeOfItem()) {
                case 1->Triage.LEFT;
                case 2->Triage.RIGHT;
                default -> throw new IllegalArgumentException("Unexpected value: " + recipeIngredient.getTypeOfItem());
            };
        }

        final Optional<F1> left = triage.isLeftAllowed()
                ? unary(caller, localEntity, localField, foreignType1, foreignFieldGetter1)
                : Optional.empty();
        final Optional<F2> right = triage.isRightAllowed()
                && left.isEmpty()
                ? unary(caller, localEntity, localField, foreignType2, foreignFieldGetter2)
                : Optional.empty();

        final Either<F1, F2> either = left.isPresent()
                ? Either.left(left.get())
                : right.isPresent()
                        ? Either.right(right.get())
                        : null;

        return Optional.ofNullable(either);
    }

    /**
     * @see ThicknessForShapeMethod_foodSubgroups
     */
    @Override
    public <L, F> Markup plural(
            final Object caller,
            final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Can<Function<F, Object>> foreignFieldGetters) {

        if(FoodSubgroup.class.equals(foreignType)) {
            final Can<Either<FoodGroup, FoodSubgroup>> groups =
                    FoodSubgroupKey.decodeLookupKeyList((String) localField)
                    .map(key->key.map(
                            foodGroupKey->foodGroupKey.lookup(repositoryService)
                                .map(FoodGroup.class::cast)
                                .orElseThrow(),
                            foodSubgroupKey->foodSubgroupKey.lookup(repositoryService)
                                .map(FoodSubgroup.class::cast)
                                .orElseThrow())
                    );
            return new Markup(groups.stream()
                    .map(group->group.fold(FoodGroup::title, FoodSubgroup::title))
                    .collect(Collectors.joining("<br>")));
        }
        if(RecipeSubgroup.class.equals(foreignType)) {
            final Can<Either<RecipeGroup, RecipeSubgroup>> groups =
                    RecipeSubgroupKey.decodeLookupKeyList((String) localField)
                    .map(key->key.map(
                            recipeGroupKey->recipeGroupKey.lookup(repositoryService)
                                .map(RecipeGroup.class::cast)
                                .orElseThrow(),
                            recipeSubgroupKey->recipeSubgroupKey.lookup(repositoryService)
                                .map(RecipeSubgroup.class::cast)
                                .orElseThrow())
                    );
            return new Markup(groups.stream()
                    .map(group->group.fold(RecipeGroup::title, RecipeSubgroup::title))
                    .collect(Collectors.joining("<br>")));
        }
        if(FacetDescriptor.class.equals(foreignType)) {
            final Can<FacetDescriptor> descriptors =
                    FacetDescriptorKey.decodeLookupKeyList((String) localField)
                    .map(key->key.lookup(repositoryService)
                            .orElseThrow());
            return new Markup(descriptors.stream()
                    .map(FacetDescriptor::title)
                    .collect(Collectors.joining("<br>")));
        }

        throw _Exceptions.unrecoverable("plural foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    <T> Optional<T> uniqueMatch(
            final Class<T> ofType,
            final Predicate<T> predicate,
            final Supplier<String> exceptionMessageProvider) {
        try {
            return repositoryService.uniqueMatch(ofType, predicate);
        } catch (RepositoryException e) {
            throw new RepositoryException(
                    String.format("Found more than one instance of class %s matching filter %s",
                            ofType.getSimpleName(),
                            exceptionMessageProvider.get()),
                    e);
        }
    }

}
