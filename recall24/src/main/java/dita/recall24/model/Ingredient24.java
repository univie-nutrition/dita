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

import javax.measure.Quantity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

/**
 * Implements {@link dita.recall24.api.Ingredient24} as Java record type.
 * @see Ingredient24
 */
public record Ingredient24(
        @JsonIgnore
        ObjectRef<Record24> parentRecordRef,
        String sid,
        String name,
        String facetSids,
        double rawPerCookedRatio,
        Quantity<?> quantityCooked
        //,double fractionRelativeToParentRecipe
        ) implements dita.recall24.api.Ingredient24, Node24 {

    public static Ingredient24 of(
            final String sid,
            final String name,
            final String facetSids,
            final double rawPerCookedRatio,
            final Quantity<?> quantityCooked) {
        var ingredient24 = new Ingredient24(ObjectRef.empty(), sid, name, facetSids, rawPerCookedRatio, quantityCooked);
        return ingredient24;
    }

    @Override
    public Record24 parentRecord() {
        return parentRecordRef.getValue();
    }

}
