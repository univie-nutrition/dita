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

import org.apache.causeway.commons.collections.Can;

import dita.commons.types.ObjectRef;

public record MemorizedFood24(
        /**
         * Meal this memorized food belongs to.
         */
        ObjectRef<Meal24> parentMealRef,

        /**
         * Freetext, describing this memorized food.
         */
        String name,

        /**
         * Records for this memorized food.
         */
        Can<Record24> records

        ) implements dita.recall24.api.MemorizedFood24 {

    @Override
    public Meal24 parentMeal() {
        return parentMealRef.getValue();
    }
}
