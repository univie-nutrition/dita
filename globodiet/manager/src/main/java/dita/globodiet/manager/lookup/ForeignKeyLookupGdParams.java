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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.bookmark.Bookmark;
import org.apache.causeway.applib.services.bookmark.BookmarkService;
import org.apache.causeway.applib.services.linking.DeepLinkService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.base._Timing;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.internal.functions._Predicates;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.commons.services.foreignkey.HasSecondaryKey;
import dita.commons.services.foreignkey.ISecondaryKey;
import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.classification.RecipeGroup;
import dita.globodiet.dom.params.classification.RecipeSubgroup;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import dita.globodiet.dom.params.food_list.ComposedRecipeIngredient;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFood;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe;
import dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_foodSubgroups;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import lombok.val;

@Service
public class ForeignKeyLookupGdParams
implements ForeignKeyLookupService {

    @Inject BookmarkService bookmarkService;
    @Inject RepositoryService repositoryService;
    @Inject DeepLinkService deepLinkService;

    @Override
    public <L, F> Optional<F> binary(
            final Object caller,
            final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Function<F, Object> foreignFieldGetter1,
            final Function<F, Object> foreignFieldGetter2) {

        if(localField == null) return Optional.empty();
        if(FacetDescriptor.class.equals(foreignType)) {
            val secondaryKey = Decoders.decodeFacetDecriptorLookupKey((String) localField);
            return (Optional<F>) Optional.of(unique(secondaryKey));
        }
        throw _Exceptions.unrecoverable("2-ary foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    /**
     * @see ThicknessForShapeMethod_foodSubgroups
     */
    @Override
    public <L, F> Can<F> plural(
            final Object caller,
            final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Can<Function<F, Object>> foreignFieldGetters) {

        if(FoodSubgroup.class.equals(foreignType)) {
            final Can<Either<FoodGroup, FoodSubgroup>> groups =
                    Decoders.decodeFoodGroupLookupKeyList((String) localField)
                    .map(key->key.map(
                            foodGroupKey->(FoodGroup)unique(foodGroupKey),
                            foodSubgroupKey->(FoodSubgroup)unique(foodSubgroupKey))
                    );
            return groups
                    .map(group->group.fold(foreignType::cast, foreignType::cast)); //FIXME invalid cast
        }
        if(RecipeSubgroup.class.equals(foreignType)) {
            final Can<Either<RecipeGroup, RecipeSubgroup>> groups =
                    Decoders.decodeRecipeGroupLookupKeyList((String) localField)
                    .map(key->key.map(
                            recipeGroupKey->(RecipeGroup)unique(recipeGroupKey),
                            recipeSubgroupKey->(RecipeSubgroup)unique(recipeSubgroupKey))
                    );
            return groups
                    .map(group->group.fold(foreignType::cast, foreignType::cast)); //FIXME invalid cast
        }
        if(FacetDescriptor.class.equals(foreignType)) {
            final Can<FacetDescriptor> descriptors =
                    Decoders.decodeFacetDecriptorLookupKeyList((String) localField)
                    .map(key->unique(key));
            return _Casts.uncheckedCast(descriptors);
        }

        throw _Exceptions.unrecoverable("plural foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    // -- PREFILTER

    private static <T> Predicate<T> prefilter(final Class<T> entityClass){
        if(FoodOrProductOrAlias.class.equals(entityClass)) {
            // do not lookup SH entries (aliases)
            return t->"SH".equalsIgnoreCase(((FoodOrProductOrAlias) t).getTypeOfItem());
        }
        return _Predicates.alwaysTrue();
    }

    // -- CACHING

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

            repositoryService.allMatches(entityClass, prefilter(entityClass)).stream()
            .forEach(t->{
                 var secondaryKey = ((HasSecondaryKey<?>)t).secondaryKey();
                 lookup.map().put(secondaryKey, bookmarkService.bookmarkForElseFail(t));
            });

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
        if(entity instanceof RecipeIngredient x) {
            return x.getTypeOfItem();
        }
        if(entity instanceof ComposedRecipeIngredient x) {
            return "2".equals(x.getType())
                    ? 2
                    : 1;
        }
        if(entity instanceof DensityFactorForFood x) {
            return x.getDensityForFoodOrRecipe();
        }
        if(entity instanceof QuantificationMethodsPathwayForFoodGroup x) {
            return "P".equalsIgnoreCase(x.getPhotoCode())
                    ? 1
                    : 2;
        }
        if(entity instanceof QuantificationMethodsPathwayForFood x) {
            return "P".equalsIgnoreCase(x.getPhotoCode())
                    ? 1
                    : 2;
        }
        if(entity instanceof StandardUnitForFoodOrRecipe x) {
            return "2".equals(x.getType())
                    ? 2
                    : 1;
        }
        throw _Exceptions.unrecoverable("switchOn type %s not implemented",
                entity.getClass().getSimpleName());
    }

}
