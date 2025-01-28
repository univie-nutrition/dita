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

import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;

import dita.globodiet.params.food_descript.FoodDescriptor;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import dita.globodiet.params.recipe_list.RecipeGroup;
import dita.globodiet.params.recipe_list.RecipeSubgroup;
import org.jspecify.annotations.NonNull;

import lombok.experimental.UtilityClass;

@UtilityClass
class Decoders {

    // -- SINGULAR

    /**
     * Facet string multiple (descface.facet_code + descface.descr_code)comma separated (e.g. 0401,0203,051)
     */
    FoodDescriptor.SecondaryKey decodeFacetDecriptorLookupKey(final @NonNull String input) {
        _Assert.assertEquals(4, input.length(),
                ()->String.format("invalid 4 digit facet descriptor lookup key: %s", input));
        return new FoodDescriptor.SecondaryKey(input.substring(0, 2), input.substring(2));
    }

    /**
     * either 2, 4 or 6 digits (e.g. 02, 0300 or 010633)
     */
    Either<FoodGroup.SecondaryKey, FoodSubgroup.SecondaryKey> decodeFoodGroupLookupKey(final @NonNull String input) {
        _Assert.assertTrue(input.length()==2
                || input.length()==4
                || input.length()==6);
        var d1 = input.substring(0, 2);
        var d2 = input.length()>=4
                ? input.substring(2, 4)
                : null;
        var d3 = input.length()==6
                ? input.substring(4)
                : null;
        return input.length()>=4
                ? Either.right(new FoodSubgroup.SecondaryKey(d1, d2, d3))
                : Either.left(new FoodGroup.SecondaryKey(d1));
    }

    /**
     * either 2 or 4 digits (e.g. 02 or 0300)
     */
    Either<RecipeGroup.SecondaryKey, RecipeSubgroup.SecondaryKey> decodeRecipeGroupLookupKey(final @NonNull String input) {
        _Assert.assertTrue(input.length()==2
                || input.length()==4);
        var d1 = input.substring(0, 2);
        var d2 = input.length()>=4
                ? input.substring(2, 4)
                : null;
        return input.length()>=4
                ? Either.right(new RecipeSubgroup.SecondaryKey(d1, d2))
                : Either.left(new RecipeGroup.SecondaryKey(d1));
    }

    // -- PLURAL

    /**
     * comma separated (e.g. 0300,0301)
     */
    Can<FoodDescriptor.SecondaryKey> decodeFacetDecriptorLookupKeyList(final @Nullable String input) {
        return decodeStringList(input)
                .map(Decoders::decodeFacetDecriptorLookupKey)
                .collect(Can.toCan());
    }

    /**
     * comma separated list of either 2, 4 or 6 digit chunks
     */
    Can<Either<FoodGroup.SecondaryKey, FoodSubgroup.SecondaryKey>> decodeFoodGroupLookupKeyList(final @Nullable String input) {
        return decodeStringList(input)
                .map(Decoders::decodeFoodGroupLookupKey)
                .collect(Can.toCan());
    }

    /**
     * comma separated list of either 2 or 4 digit chunks
     */
    Can<Either<RecipeGroup.SecondaryKey, RecipeSubgroup.SecondaryKey>> decodeRecipeGroupLookupKeyList(final @Nullable String input) {
        return decodeStringList(input)
                .map(Decoders::decodeRecipeGroupLookupKey)
                .collect(Can.toCan());
    }

    // -- HELPER

    private Stream<String> decodeStringList(final @Nullable String input) {
        return _Strings.isEmpty(input)
                ? Stream.empty()
                : _Strings.splitThenStream(input, ",")
                .map(chunk->chunk.trim());
    }

}
