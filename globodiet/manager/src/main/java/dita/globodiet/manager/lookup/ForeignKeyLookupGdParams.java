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

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.commons.types.BiString;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.classification.RecipeSubgroup;
import dita.globodiet.dom.params.food_descript.Brand;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import lombok.NonNull;
import lombok.val;

@Service
public class ForeignKeyLookupGdParams
implements ForeignKeyLookupService {

    @Inject RepositoryService repositoryService;

    @Override
    public <L, F> Optional<F> unary(final L localEntity, final String localFieldName, final Object localField,
            final Class<F> foreignType, final Function<F, Object> foreignFieldGetter) {

        if(localField == null) return Optional.empty();

        if(FoodOrProductOrAlias.class.equals(foreignType)) {
            // do not lookup SH entries (aliases)
            return repositoryService.uniqueMatch(foreignType, foreign->
                Objects.equals(localField, foreignFieldGetter.apply(foreign))
                    && !Objects.equals("SH", ((FoodOrProductOrAlias)foreign).getTypeOfItem()));
        } else if(FoodSubgroup.class.equals(foreignType)) {
            // discrimination by 3 fields, where first 2 are always populated
            val isLookingForSubSubgroup = localFieldName.toLowerCase().contains("subsub");
            if(localEntity instanceof Brand) {
                val local = (Brand) localEntity;
                val subsubgroup = isLookingForSubSubgroup
                        ? local.getFoodSubSubgroupCode()
                        : null;
                return repositoryService.uniqueMatch(foreignType, foreign->{
                    val foodSubgroup = (FoodSubgroup)foreign;
                    return Objects.equals(local.getFoodGroupCode(), foodSubgroup.getFoodGroupCode())
                            && Objects.equals(local.getFoodSubgroupCode(), foodSubgroup.getFoodSubgroupCode())
                            && Objects.equals(subsubgroup, foodSubgroup.getFoodSubSubgroupCode());
                });
            } else if(localEntity instanceof FoodOrProductOrAlias) {
                val local = (FoodOrProductOrAlias) localEntity;
                val subsubgroup = isLookingForSubSubgroup
                        ? local.getFoodSubSubgroupCode()
                        : null;
                return repositoryService.uniqueMatch(foreignType, foreign->{
                    val foodSubgroup = (FoodSubgroup)foreign;
                    return Objects.equals(local.getFoodGroupCode(), foodSubgroup.getFoodGroupCode())
                            && Objects.equals(local.getFoodSubgroupCode(), foodSubgroup.getFoodSubgroupCode())
                            && Objects.equals(subsubgroup, foodSubgroup.getFoodSubSubgroupCode());
                });
            }
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

        } else if(RecipeSubgroup.class.equals(foreignType)) {
            val keys = decodeGroupListLookupKey((String) localField);

            return switch(keys.getCardinality()) {
            case ZERO -> Optional.empty();
            case ONE -> repositoryService.uniqueMatch(foreignType, foreign->
                keys.getFirstElseFail().equalsPair(
                        (String)foreignFieldGetter1.apply(foreign),
                        (String)foreignFieldGetter2.apply(foreign)));
            case MULTIPLE ->
                // FIXME[DITA-110] need a way to perhaps display multiple links in tables
                Optional.empty();
            };
        }

        throw _Exceptions.unrecoverable("2-ary foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    @Override
    public <L, F1, F2> Optional<Either<F1, F2>> binary(final L localEntity, final Object localField,
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

    @Override
    public <L, F> Optional<F> ternary(
            final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Function<F, Object> foreignFieldGetter1,
            final Function<F, Object> foreignFieldGetter2,
            final Function<F, Object> foreignFieldGetter3) {

        if(FoodSubgroup.class.equals(foreignType)) {
            val keys = decodeGroupListLookupKey((String) localField);
            // FIXME[DITA-110] return FoodSubgroup
            return Optional.empty();
        }

        throw _Exceptions.unrecoverable("3-ary foreign key lookup not implemented for foreign type %s",
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

    /**
     * Pair of {@link String}. Simply a typed tuple. With the second field an optional
     */
    record GroupDiscriminator(String left, Optional<String> right) {

        public GroupDiscriminator {
            _Strings.requireNonEmpty(left, "left");
            right.ifPresent(r->_Strings.requireNonEmpty(r, "right"));
        }

        public boolean equalsPair(final String left, final String right) {
            return Objects.equals(this.left, left)
                && Objects.equals(this.right.orElse(null), right);
        }

    }

    /**
     * comma separated (e.g. 02 or 0300)
     */
    static GroupDiscriminator decodeGroupLookupKey(final @NonNull String input) {
        _Assert.assertTrue(input.length()==2
                || input.length()==4);
        return new GroupDiscriminator(input.substring(0, 2), input.length()==4
                ? Optional.of(input.substring(2))
                : Optional.empty());
    }

    /**
     * comma separated (e.g. 02,0300,0301)
     */
    static Can<GroupDiscriminator> decodeGroupListLookupKey(final @NonNull String input) {
        return _Strings.splitThenStream(input, ",")
            .map(chunk->decodeGroupLookupKey(chunk))
            .collect(Can.toCan());
    }

}
