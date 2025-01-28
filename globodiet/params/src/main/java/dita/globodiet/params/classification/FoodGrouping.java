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
package dita.globodiet.params.classification;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;

import dita.commons.format.FormatUtils;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;

public interface FoodGrouping {

    default Either<FoodGroup, FoodSubgroup> toEither() {
        return this instanceof FoodSubgroup foodSubgroup
                ? Either.right(foodSubgroup)
                : Either.left((FoodGroup)this);
    }

    default String title() {
        return toEither()
                .fold(FoodGroup::title, FoodSubgroup::title);
    }

    public record FoodGroupingKey(
            String groupKey,
            String subgroupKey,
            String subSubgroupKey,
            /**
             * Format {@code groupCode|subgroupCode|subSubgroupCode}
             * with null replaced by dash {@literal -}.
             */
            String combinedKey,
            String lookupKey) {

        public FoodGroupingKey(
                final String foodGroupCode,
                final String foodSubgroupCode,
                final String foodSubSubgroupCode) {
            this(
                    validateGroup(foodGroupCode, foodSubgroupCode),
                    validateSubGroup(foodSubgroupCode, foodSubSubgroupCode),
                    foodSubSubgroupCode,
                    String.format("%s|%s|%s",
                            FormatUtils.emptyToDash(foodGroupCode),
                            FormatUtils.emptyToDash(foodSubgroupCode),
                            FormatUtils.emptyToDash(foodSubSubgroupCode)),
                    String.format("%s%s%s",
                            _Strings.nullToEmpty(foodGroupCode),
                            _Strings.nullToEmpty(foodSubgroupCode),
                            _Strings.nullToEmpty(foodSubSubgroupCode)));
        }

        public boolean equalsOrIsContainedIn(final @Nullable String foodGroupingLookupKey) {
            return _Strings.splitThenStream(foodGroupingLookupKey, ",")
                .anyMatch(lookupKey()::equals);
        }

        private static String validateGroup(final String foodGroupCode, final String foodSubgroupCode) {
            if(_Strings.isNullOrEmpty(foodGroupCode)) {
                _Assert.assertTrue(_Strings.isNullOrEmpty(foodSubgroupCode));
            }
            return foodGroupCode;
        }

        private static String validateSubGroup(final String foodSubgroupCode, final String foodSubSubgroupCode) {
            if(_Strings.isNullOrEmpty(foodSubgroupCode)) {
                _Assert.assertTrue(_Strings.isNullOrEmpty(foodSubSubgroupCode));
            }
            return foodSubgroupCode;
        }

        private FoodGroupingKey(
                final FoodGroup.SecondaryKey secKey) {
            this(secKey.code(), null, null);
        }

        private FoodGroupingKey(
                final FoodSubgroup.SecondaryKey secKey) {
            this(secKey.foodGroupCode(), secKey.foodSubgroupCode(), secKey.foodSubSubgroupCode());
        }
    }

    default FoodGroupingKey groupingKey() {
        return this instanceof FoodSubgroup foodSubgroup
                ? new FoodGroupingKey(foodSubgroup.secondaryKey())
                : new FoodGroupingKey(((FoodGroup)this).secondaryKey());
    }

}
