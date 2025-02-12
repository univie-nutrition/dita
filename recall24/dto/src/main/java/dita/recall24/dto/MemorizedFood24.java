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
package dita.recall24.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.commons.collections.Can;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@DomainObject
public record MemorizedFood24(
            /**
             * Meal this memorized food belongs to.
             */
            @JsonIgnore
            ObjectRef<Meal24> parentMealRef,

            /**
             * Free text, describing this memorized food.
             */
            String name,

            /**
             * Top level record(s) for this memorized food.
             * Those may themselves have sub records.
             */
            @CollectionLayout(navigableSubtree = "1")
            Can<Record24> topLevelRecords

            ) implements RecallNode24 {

    /** canonical constructor */
    public MemorizedFood24(
        ObjectRef<Meal24> parentMealRef,
        String name,
        Can<Record24> topLevelRecords) {
        this.parentMealRef = parentMealRef;
        this.name = name;
        this.topLevelRecords = topLevelRecords;
        this.topLevelRecords.forEach(rec->rec.parentMemorizedFoodRef().setValue(this));
    }
    
    public MemorizedFood24(
            final String name,
            final Can<Record24> topLevelRecords) {
        this(ObjectRef.empty(), name, topLevelRecords);
    }

    public Meal24 parentMeal() {
        return parentMealRef.getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder24<MemorizedFood24> asBuilder() {
        return Builder.of(this);
    }

    // -- BUILDER

    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<MemorizedFood24> {
        String name;
        final List<Record24> topLevelRecords = new ArrayList<>();

        static Builder of(final MemorizedFood24 dto) {
            var builder = new Builder().name(dto.name);
            dto.topLevelRecords.forEach(builder.topLevelRecords::add);
            return builder;
        }

        @Override
        public MemorizedFood24 build() {
            return new MemorizedFood24(name, Can.ofCollection(topLevelRecords));
        }
    }

}