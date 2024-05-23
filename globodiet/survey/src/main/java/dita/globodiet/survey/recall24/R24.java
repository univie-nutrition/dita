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
package dita.globodiet.survey.recall24;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;

import lombok.experimental.UtilityClass;

import dita.commons.food.consumption.FoodConsumption;
import dita.commons.types.IntRef;
import dita.commons.types.Sex;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

@UtilityClass
public class R24 {

    public record Respondent(
            /**
             * Anonymized respondent identifier, unique to the corresponding survey.
             */
            String alias,

            LocalDate dateOfBirth,

            Sex sex,

            /**
             * Interviews that this respondent was subject to.
             */
            @TreeSubNodes
            Can<Interview> interviews

            ) {

        /**
         * Interviews are sorted by interview-date.
         * All ordinals are filled in. //TODO
         */
        Respondent normalize() {
            var interviewsSorted = interviews()
                    .sorted((a, b)->a.interviewDate().compareTo(b.interviewDate()));

            interviewsSorted.forEach(IndexedConsumer.offset(1, (ordinal, inv)->
                inv.interviewOrdinalRef().setValue(ordinal))); // fill in interview's ordinal

            return new Respondent(alias, dateOfBirth, sex, interviewsSorted);
        }

    }

    public record RespondentMetaData(

            /**
             * Parent interview.
             */
            @JsonIgnore
            ObjectRef<Interview> parentInterviewRef,

            /**
             * Diet as practiced on the interview date.
             */
            String specialDietId,

            /**
             * Special day as practiced on the interview date.
             */
            String specialDayId,

            /**
             * Respondent's height in units of centimeter,
             * as measured on the interview date.
             */
            BigDecimal heightCM,

            /**
             * Respondent's weight in units of kilogram,
             * as measured on the interview date.
             */
            BigDecimal weightKG

            ) {

        public Interview parentInterview() {
            return parentInterviewRef.getValue();
        }
    }

    public record Interview(

            /**
             * Respondent of this interview.
             */
            @JsonIgnore
            ObjectRef<Respondent> parentRespondentRef,

            /**
             * Interview date.
             */
            LocalDate interviewDate,

            /**
             *  Each respondent can have one ore more interviews within the context of a specific survey.
             *  This ordinal denotes the n-th interview (when ordered by interview date).
             */
            IntRef interviewOrdinalRef,

            /**
             * Respondent meta-data for this interview.
             */
            RespondentMetaData respondentMetaData,

            /**
             * The meals of this interview.
             */
            @TreeSubNodes
            Can<Meal> meals

            ) {

        public static Interview of(
                final Respondent respondent,
                /**
                 * Interview date.
                 */
                final LocalDate interviewDate,
                /**
                 * Respondent meta-data for this interview.
                 */
                final RespondentMetaData respondentMetaData,
                /**
                 * The meals of this interview.
                 */
                final Can<Meal> meals) {
            var interview = new Interview(new ObjectRef<>(respondent), interviewDate, IntRef.of(-1), respondentMetaData, meals);
            respondentMetaData.parentInterviewRef().setValue(interview);
            meals.forEach(meal24->meal24.parentInterviewRef().setValue(interview));
            return interview;
        }

        public Respondent parentRespondent() {
            return parentRespondentRef.getValue();
        }

        public int interviewOrdinal() {
            return interviewOrdinalRef().getValue();
        }

        public boolean matchesRespondent(final Respondent candidate) {
            return Objects.equals(parentRespondent(), candidate);
        }

    }

    public record Meal(

            /**
             * Parent interview.
             */
            @JsonIgnore
            ObjectRef<Interview> parentInterviewRef,

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
            @TreeSubNodes
            Can<MemorizedFood> memorizedFood

            ) {

        public static Meal of(
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
                final Can<MemorizedFood> memorizedFood
                ) {
            var meal = new Meal(ObjectRef.empty(), hourOfDay, foodConsumptionOccasionId,
                    foodConsumptionPlaceId, memorizedFood);
            memorizedFood.forEach(mem->mem.parentMealRef().setValue(meal));
            return meal;
        }

        public Interview parentInterview() {
            return parentInterviewRef.getValue();
        }
    }

    public record MemorizedFood(
            /**
             * Meal this memorized food belongs to.
             */
            @JsonIgnore
            ObjectRef<Meal> parentMealRef,

            /**
             * Freetext, describing this memorized food.
             */
            String name,

            /**
             * Records for this memorized food.
             */
            @TreeSubNodes
            Can<Record> records

            ) {

        public static MemorizedFood of(
                /**
                 * Freetext, describing this memorized food.
                 */
                final String name,
                /**
                 * Top level {@link Record}(s) for this memorized food.
                 */
                final Can<Record> topLevelRecords) {

            var memorizedFood = new MemorizedFood(ObjectRef.empty(), name, topLevelRecords);
            //topLevelRecords.forEach(rec->rec.parentMemorizedFoodRef().setValue(memorizedFood));
            return memorizedFood;
        }

        public Meal parentMeal() {
            return parentMealRef.getValue();
        }
    }

    // -- RECORDS

    public record Note(
            String text) {
    }
    public record TypeOfFatUsed() {

    }
    public record TypeOfMilkOrLiquidUsed() {

    }

    public sealed interface Record permits Food, Product, Composite, FatSauceOrSweetener {
        List<Note> notes();
        default Optional<FoodConsumption> consumption() { return Optional.empty(); }
        default Optional<TypeOfFatUsed> typeOfFatUsedDuringCooking() { return Optional.empty(); }
        default Optional<TypeOfMilkOrLiquidUsed> typeOfMilkOrLiquidUsedDuringCooking() { return Optional.empty(); }
    }

    public record Food(
            Optional<FoodConsumption> consumption,
            Optional<TypeOfFatUsed> typeOfFatUsedDuringCooking,
            Optional<TypeOfMilkOrLiquidUsed> typeOfMilkOrLiquidUsedDuringCooking,
            List<Note> notes
            ) implements Record {

    }
    public record Product(
            Optional<FoodConsumption> consumption,
            List<Note> notes
            ) implements Record {

    }
    public record Composite(
            List<Record> subRecords,
            List<Note> notes
            ) implements Record {
    }
    public record FatSauceOrSweetener(
            List<Note> notes
            ) implements Record {

    }

}
