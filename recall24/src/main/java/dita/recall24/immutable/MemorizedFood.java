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
package dita.recall24.immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.commons.collections.Can;

import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

public record MemorizedFood(
        /**
         * Meal this memorized food belongs to.
         */
        @JsonIgnore
        ObjectRef<Meal> parentMealRef,

        /**
         * Free text, describing this memorized food.
         */
        String name,

        /**
         * Top level record(s) for this memorized food.
         * Those may themselves have sub records.
         */
        @TreeSubNodes
        Can<Record> topLevelRecords

        ) implements dita.recall24.api.MemorizedFood24, RecallNode {

    public static MemorizedFood of(
            /**
             * Free text, describing this memorized food.
             */
            final String name,
            /**
             * Top level record(s) for this memorized food.
             * Those may themselves have sub records.
             */
            final Can<Record> topLevelRecords) {

        var memorizedFood24 = new MemorizedFood(ObjectRef.empty(), name, topLevelRecords);
        topLevelRecords.forEach(rec->rec.parentMemorizedFoodRef().setValue(memorizedFood24));
        return memorizedFood24;
    }

    @Override
    public Meal parentMeal() {
        return parentMealRef.getValue();
    }
}