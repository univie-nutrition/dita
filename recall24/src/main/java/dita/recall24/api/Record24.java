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
package dita.recall24.api;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;

public interface Record24 extends RecallNode24 {

    @Deprecated
    public static enum Type {

        /**
         * Generic food, identified within the standard food description model (food-list)
         * or identified within eg. a recipe data base.
         */
        FOOD,

        /**
         * A composition of identified generic food or recipes.
         * <p>
         * Eg. 'on-the-fly' recipes, that were recorded during an interview.
         */
        COMPOSITE,

        /**
         * Product identified within a product data base. <p>Eg. supplements.
         */
        PRODUCT,

        /**
         * To be ignored by statistical analysis. However, providing commentary.
         */
        INFORMAL,

        /**
         * Incomplete record of some sort, that is NOT {@link #INFORMAL}.
         * Required to be post-processed before statistical analysis.
         * <p>
         * Eg. food that cannot be identified within the standard food description model (food-list),
         * or other data base, because an appropriate entry is yet missing.
         */
        INCOMPLETE,
        ;

        /** @see #FOOD */
        public boolean isFood() { return this == FOOD; }
        /** @see #COMPOSITE */
        public boolean isComposite() { return this == COMPOSITE; }
        /** @see #PRODUCT */
        public boolean isProduct() { return this == PRODUCT; }
        /** @see #INFORMAL */
        public boolean isInformal() { return this == INFORMAL; }
        /** @see #INCOMPLETE */
        public boolean isIncomplete() { return this == INCOMPLETE; }

        /** to DTO */
        public String stringify() {
            return name().toLowerCase();
        }

        /** from DTO */
        @Nullable
        public static Type destringify(final String stringified) {
            return StringUtils.hasLength(stringified)
                    ? Type.valueOf(stringified.toUpperCase())
                    : null;
        }
    }

    /**
     * Memorized food this record belongs to.
     */
    MemorizedFood24 parentMemorizedFood();

    /**
     * The type of this record.
     */
    @Deprecated
    Record24.Type type();

    /**
     * The name of this record.
     */
    String name();

    /**
     * Comma separated list of facet identifiers,
     * ordered (by some natural order).
     */
    String facetSids();

    /**
     * Ingredients of this record.
     */
    @Deprecated
    Can<? extends Ingredient24> ingredients();

}