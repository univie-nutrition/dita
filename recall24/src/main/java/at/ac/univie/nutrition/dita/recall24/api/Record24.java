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
package at.ac.univie.nutrition.dita.recall24.api;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;

public interface Record24 {

    public static enum Type {

        /**
         * Also used as Proxy to 'Composite Food' (eg. recipe-manager) or 'Product'
         */
        FOOD,

        /**
         * Recipe with ingredients, where the recipe itself and its ingredients have facets.
         */
        COMPOSITE,

        /**
         * Has no ingredients/ eg. supplements.
         */
        PRODUCT,

        /**
         * Either to be ignored or food w/o identifier.
         */
        INFORMAL
        ;

        public boolean isFoodOrProxy() { return this == FOOD; }
        public boolean isComposite() { return this == COMPOSITE; }
        public boolean isProduct() { return this == PRODUCT; }
        public boolean isInformal() { return this == INFORMAL; }

        public String stringify() {
            return name().toLowerCase();
        }

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
    Can<? extends Ingredient24> ingredients();

}