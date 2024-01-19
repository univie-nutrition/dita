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
package dita.globodiet.manager.services.lookup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.bookmark.Bookmark;
import org.apache.causeway.applib.services.bookmark.BookmarkService;
import org.apache.causeway.applib.services.linking.DeepLinkService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Timing;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.internal.functions._Predicates;

import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.commons.services.lookup.HasSecondaryKey;
import dita.commons.services.lookup.ISecondaryKey;
import dita.globodiet.dom.params.classification.FoodGrouping;
import dita.globodiet.dom.params.classification.RecipeGrouping;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFoodOrRecipe;
import dita.globodiet.dom.params.food_descript.FoodDescriptor;
import dita.globodiet.dom.params.food_list.ComposedRecipeIngredient;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import dita.globodiet.dom.params.nutrient.NutrientForFoodOrGroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFood;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipe;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipeGroup;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe;
import dita.globodiet.dom.params.recipe_list.RecipeGroup;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_list.RecipeSubgroup;
import dita.globodiet.dom.params.setting.GroupSubstitution;
import lombok.val;

@Service
public class ForeignKeyLookupGdParams
implements ForeignKeyLookupService {

    @Inject BookmarkService bookmarkService;
    @Inject RepositoryService repositoryService;
    @Inject DeepLinkService deepLinkService;

    // -- PREFILTER

    private static <T> Predicate<T> prefilter(final Class<T> entityClass){
        if(Food.class.equals(entityClass)) {
            // do not lookup SH entries (aliases)
            return t->((Food) t).getTypeOfItem() != Food.TypeOfItem.ALIAS;
        }
        return _Predicates.alwaysTrue();
    }

    // -- CACHING

    @Override
    public void clearCache(final Class<?>... types) {
        Can.ofArray(types)
            .forEach(lookupCache.map()::remove);
    }

    @Override
    public void clearAllCaches() {
        lookupCache.map().clear();
    }

    private static record LookupCache(Map<Class<?>, SecondaryKeyToId<?>> map){
        @SuppressWarnings("unchecked")
        public <T> SecondaryKeyToId<T> computeIfAbsent(
                final Class<T> entityClass,
                final BookmarkService bookmarkService,
                final RepositoryService repositoryService) {
            return (SecondaryKeyToId<T>) map.computeIfAbsent(entityClass,
                    t->SecondaryKeyToId.populate(t, bookmarkService, repositoryService));
        }
    }
    private static record SecondaryKeyToId<T>(Map<ISecondaryKey<?>, Bookmark> map){
        public static <T> SecondaryKeyToId<T> populate(final Class<T> entityClass,
                final BookmarkService bookmarkService,
                final RepositoryService repositoryService) {
            final SecondaryKeyToId<T> lookup = new SecondaryKeyToId<>(new HashMap<>());

            var stopWatch = _Timing.now();
            {
                repositoryService.allMatches(entityClass, prefilter(entityClass)).stream()
                .forEach(t->{
                     var secondaryKey = ((HasSecondaryKey<?>)t).secondaryKey();
                     lookup.map().put(secondaryKey, bookmarkService.bookmarkForElseFail(t));
                });
            }
            stopWatch.stop();

            System.err.printf("generating lookup cache for %s took %.3f s%n", entityClass.getSimpleName(), stopWatch.getSeconds());

            return lookup;
        }
        public Optional<T> lookup(final ISecondaryKey<T> lookupKey, final BookmarkService bookmarkService) {
            val bookmark = map().get(lookupKey);
            return bookmarkService.lookup(bookmark, lookupKey.correspondingClass());
        }
    }
    private LookupCache lookupCache = new LookupCache(new ConcurrentHashMap<>());

    // -- LOOKUP

    @Override
    public <T> T nullable(final ISecondaryKey<T> lookupKey) {
        return lookup(lookupKey).orElse(null);
    }

    @Override
    public <T> T unique(final ISecondaryKey<T> lookupKey) {
        return lookup(lookupKey)
                .orElseGet(lookupKey::unresolvable);
    }

    private <T> Optional<T> lookup(final ISecondaryKey<T> lookupKey) {
        final Class<T> entityClass = lookupKey.correspondingClass();
        final SecondaryKeyToId<T> secondaryKeyToId = lookupCache
                .computeIfAbsent(entityClass, bookmarkService, repositoryService);
        return secondaryKeyToId.lookup(lookupKey, bookmarkService);
    }

    @Override
    public int switchOn(final Object entity) {
        if(entity instanceof ComposedRecipeIngredient x) {
            return x.getType() == ComposedRecipeIngredient.Type.RECIPE
                    ? 2
                    : 1;
        }
        if(entity instanceof DensityFactorForFoodOrRecipe x) {
            return x.getForFoodOrRecipe() == DensityFactorForFoodOrRecipe.ForFoodOrRecipe.RECIPE
                    ? 2
                    : 1;
        }
        if(entity instanceof NutrientForFoodOrGroup x) {
            return x.getTypeOfRecord() == NutrientForFoodOrGroup.TypeOfRecord.RECIPE
                    ? 2
                    : 1;
        }
        if(entity instanceof GroupSubstitution x) {
            return x.getType() == GroupSubstitution.Type.RECIPE
                    ? 2
                    : 1;
        }
        if(entity instanceof RecipeIngredient x) {
            return x.getTypeOfItem() == RecipeIngredient.TypeOfItem.RECIPE
                    ? 2
                    : 1;
        }
        if(entity instanceof StandardUnitForFoodOrRecipe x) {
            return x.getType() == StandardUnitForFoodOrRecipe.Type.RECIPE
                    ? 1 //2
                    : 1;
        }
        if(entity instanceof QuantificationMethodPathwayForFood x) {
            return x.getQuantificationMethod() == QuantificationMethodPathwayForFood.QuantificationMethod.PHOTO
                    ? 1
                    : 2;
        }
        if(entity instanceof QuantificationMethodPathwayForFoodGroup x) {
            return x.getQuantificationMethod() == QuantificationMethodPathwayForFoodGroup.QuantificationMethod.PHOTO
                    ? 1
                    : 2;
        }
        if(entity instanceof QuantificationMethodPathwayForRecipe x) {
            return x.getQuantificationMethod() == QuantificationMethodPathwayForRecipe.QuantificationMethod.PHOTO
                    ? 1
                    : 2;
        }
        if(entity instanceof QuantificationMethodPathwayForRecipeGroup x) {
            return x.getQuantificationMethod() == QuantificationMethodPathwayForRecipeGroup.QuantificationMethod.PHOTO
                    ? 1
                    : 2;
        }

        throw _Exceptions.unrecoverable("switchOn type %s not implemented",
                entity.getClass().getSimpleName());
    }

    @Override
    public <T> Can<ISecondaryKey<T>> decodeLookupKeyList(final Class<T> foreignType, final String stringList) {
        if(FoodGrouping.class.isAssignableFrom(foreignType)) {
            var keys = Decoders.decodeFoodGroupLookupKeyList(stringList).stream()
                    .map(either->either.fold(
                            (FoodGroup.SecondaryKey left)->satisfiesRequired(foreignType, FoodGroup.class)
                                    ? left
                                    : null,
                            (FoodSubgroup.SecondaryKey right)->satisfiesRequired(foreignType, FoodSubgroup.class)
                                    ? right
                                    : null))
                    .filter(_NullSafe::isPresent)
                    .map(ISecondaryKey.class::cast)
                    .collect(Can.toCan());
            return _Casts.uncheckedCast(keys);

        }
        if(RecipeGrouping.class.isAssignableFrom(foreignType)) {
            var keys = Decoders.decodeRecipeGroupLookupKeyList(stringList).stream()
                    .map(either->either.fold(
                            (RecipeGroup.SecondaryKey left)->satisfiesRequired(foreignType, RecipeGroup.class)
                                    ? left
                                    : null,
                            (RecipeSubgroup.SecondaryKey right)->satisfiesRequired(foreignType, RecipeSubgroup.class)
                                    ? right
                                    : null))
                    .filter(_NullSafe::isPresent)
                    .map(ISecondaryKey.class::cast)
                    .collect(Can.toCan());
            return _Casts.uncheckedCast(keys);
        }
        if(FoodDescriptor.class.equals(foreignType)) {
            return _Casts.uncheckedCast(Decoders.decodeFacetDecriptorLookupKeyList(stringList));
        }
        throw _Exceptions.unrecoverable("decodeLookupKey(List) not implemented for foreign type %s",
                foreignType.getName());
    }

    static <T> boolean satisfiesRequired(final Class<T> requiredType, final Class<?> actualType) {
        return requiredType.isAssignableFrom(actualType);
    }

}
