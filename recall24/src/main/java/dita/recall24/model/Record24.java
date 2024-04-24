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
package dita.recall24.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.commons.collections.Can;

import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

public record Record24(

        /**
         * Memorized food this record belongs to.
         */
        @JsonIgnore
        ObjectRef<MemorizedFood24> parentMemorizedFoodRef,

        /**
         * The type of this record.
         */
        dita.recall24.api.Record24.Type type,

        /**
         * The name of this record.
         */
        String name,

        /**
         * Comma separated list of facet identifiers,
         * ordered (by some natural order).
         */
        String facetSids,

        /**
         * Ingredients of this record.
         */
        @TreeSubNodes
        Can<Ingredient24> ingredients


        ) implements dita.recall24.api.Record24, Node24 {

    public static Record24 of(
            /**
             * The type of this record.
             */
            final dita.recall24.api.Record24.Type type,
            /**
             * The name of this record.
             */
            final String name,
            /**
             * Comma separated list of facet identifiers,
             * ordered (by some natural order).
             */
            final String facetSids,
            /**
             * Ingredients of this record.
             */
            final Can<Ingredient24> ingredients) {

        var record24 = new Record24(ObjectRef.empty(), type, name, facetSids, ingredients);
        ingredients.forEach(ingr->ingr.parentRecordRef().setValue(record24));
        return record24;
    }

    @Override
    public MemorizedFood24 parentMemorizedFood() {
        return parentMemorizedFoodRef.getValue();
    }
}
