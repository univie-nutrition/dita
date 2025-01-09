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

import java.time.LocalTime;
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

/**
 * Meal of the day.
 */
@DomainObject
public record Meal24(

            /**
             * Parent interview.
             */
            @JsonIgnore
            ObjectRef<Interview24> parentInterviewRef,

            /**
             * Hour of day, when this meal took place.
             */
            LocalTime hourOfDay,

            /**
             * Identifying the occasion, when this meal took place.
             */
            String foodConsumptionOccasionId,

            /**
             * Identifying the place, where this meal took place.
             */
            String foodConsumptionPlaceId,

            /**
             * Memorized food for this meal.
             */
            @CollectionLayout(navigableSubtree = "1")
            Can<MemorizedFood24> memorizedFood

            ) implements RecallNode24 {

    public static Meal24 of(
            /**
             * Hour of day, when this meal took place.
             */
            final LocalTime hourOfDay,
            /**
             * Identifying the occasion, when this meal took place.
             */
            final String foodConsumptionOccasionId,
            /**
             * Identifying the place, where this meal took place.
             */
            final String foodConsumptionPlaceId,
            /**
             * Memorized food for this meal.
             */
            final Can<MemorizedFood24> memorizedFood
            ) {
        var meal24 = new Meal24(ObjectRef.empty(), hourOfDay, foodConsumptionOccasionId,
                foodConsumptionPlaceId, memorizedFood);
        memorizedFood.forEach(mem->mem.parentMealRef().setValue(meal24));
        return meal24;
    }

    public Interview24 parentInterview() {
        return parentInterviewRef.getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder24<Meal24> asBuilder() {
        return Builder.of(this);
    }

    // -- BUILDER

    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<Meal24> {
        LocalTime hourOfDay;
        String foodConsumptionOccasionId;
        String foodConsumptionPlaceId;
        final List<MemorizedFood24> memorizedFood = new ArrayList<>();

        static Builder of(final Meal24 dto) {
            var builder =  new Builder().hourOfDay(dto.hourOfDay)
                    .foodConsumptionOccasionId(dto.foodConsumptionOccasionId)
                    .foodConsumptionPlaceId(dto.foodConsumptionPlaceId);
            dto.memorizedFood.forEach(builder.memorizedFood::add);
            return builder;
        }

        @Override
        public Meal24 build() {
            var dto = Meal24.of(hourOfDay, foodConsumptionOccasionId, foodConsumptionPlaceId, Can.ofCollection(memorizedFood));
            dto.memorizedFood().forEach(child->child.parentMealRef().setValue(dto));
            return dto;
        }
    }

}