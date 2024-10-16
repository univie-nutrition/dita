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

import org.apache.causeway.commons.collections.Can;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

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
            @TreeSubNodes
            Can<Record24> topLevelRecords

            ) implements RecallNode24 {

    public static MemorizedFood24 of(
            /**
             * Free text, describing this memorized food.
             */
            final String name,
            /**
             * Top level record(s) for this memorized food.
             * Those may themselves have sub records.
             */
            final Can<Record24> topLevelRecords) {

        var memorizedFood24 = new MemorizedFood24(ObjectRef.empty(), name, topLevelRecords);
        topLevelRecords.forEach(rec->rec.parentMemorizedFoodRef().setValue(memorizedFood24));
        return memorizedFood24;
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
            var dto = MemorizedFood24.of(name, Can.ofCollection(topLevelRecords));
            dto.topLevelRecords().forEach(child->child.parentMemorizedFoodRef().setValue(dto));
            return dto;
        }
    }

}