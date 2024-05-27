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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;

import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

public sealed interface Record24 extends RecallNode24
permits
    Record24.Dto {

    /**
     * Memorized food this record belongs to.
     */
    MemorizedFood24 parentMemorizedFood();

    /**
     * The name of this record.
     */
    String name();

    /**
     * Semantic identifier of this food or product, if any.
     * <p> Empty for some composites,
     * e.g. food that cannot be identified within the standard food description model (food-list),
     * or other data base, because an appropriate entry is yet missing.
     */
    @Nullable String sid();

    /**
     * Comma separated list of facet identifiers,
     * ordered (by some natural order).
     */
    String facetSids();

    default public String discriminator() {
        return "" + sid() + ";" + facetSids();
    }

    public record Note(
            String text) {
    }

    default List<Note> notes() {
        return Collections.emptyList();
    }

    // -- DTOs

    public sealed interface Dto extends Record24
    permits
        Record24.Composite,
        Record24.TypeOfFatUsed,
        Record24.TypeOfMilkOrLiquidUsed,
        Record24.Consumption {

        ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef();

        @Override
        default MemorizedFood24.Dto parentMemorizedFood() {
            return parentMemorizedFoodRef().getValue();
        }
    }

    /**
     * A composition of identified generic food or recipes.
     */
    public record Composite(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            String name,
            String sid,
            String facetSids,
            /**
             * Nested records.
             */
            @TreeSubNodes
            Can<? extends Record24> subRecords
            ) implements Record24.Dto {
    }

    public sealed interface Consumption extends Record24.Dto
    permits Food, Product {

        /**
         * Amount consumed in units of {@link #consumptionUnit()}.
         */
        BigDecimal amountConsumed();

        /**
         * @see #amountConsumed()
         */
        ConsumptionUnit consumptionUnit();

        /**
         * Raw per cooked ratio, ranging from 0. to 1.
         */
        BigDecimal rawPerCookedRatio();

        /**
         * Convert to a {@link FoodConsumption}.
         */
        FoodConsumption asFoodConsumption();
    }

    /**
     * Generic food, identified within the standard food description model (food-list)
     * or identified within eg. a recipe data base.
     */
    public record Food(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            String name,
            String sid,
            String facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio,
            TypeOfFatUsed typeOfFatUsedDuringCooking,
            TypeOfMilkOrLiquidUsed typeOfMilkOrLiquidUsedDuringCooking
            ) implements Consumption {

        @Override
        public FoodConsumption asFoodConsumption() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    /**
     * Type of fat used during cooking.
     */
    public record TypeOfFatUsed(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            Food parentFood,
            String name,
            String sid,
            String facetSids
            ) implements Record24.Dto {
    }

    /**
     * Type of milk or liquid used during cooking.
     */
    public record TypeOfMilkOrLiquidUsed(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            Food parentFood,
            String name,
            String sid,
            String facetSids
            ) implements Record24.Dto {
    }

    /**
     * Product identified within a product data base.<p>e.g. supplements
     */
    public record Product(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            String name,
            String sid,
            String facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio
            ) implements Consumption {

        @Override
        public FoodConsumption asFoodConsumption() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    // -- FACTORIES

    public static Composite composite(
            /**
             * The name of this record.
             */
            final String name,
            /**
             * Identifier of this record.
             */
            final String sid,
            /**
             * Comma separated list of facet identifiers,
             * ordered (by some natural order).
             */
            final String facetSids,
            final Can<? extends Record24> subRecords) {
        var composite = new Composite(ObjectRef.empty(), name, sid, facetSids, subRecords);
        //subRecords.forEach(rec->rec.parentRecordRef().setValue(composite));
        return composite;
    }

    public static Product product(
            /**
             * The name of this record.
             */
            final String name,
            /**
             * Identifier of this record.
             */
            final String sid,
            /**
             * Comma separated list of facet identifiers,
             * ordered (by some natural order).
             */
            final String facetSids,
            final BigDecimal amountConsumed,
            final ConsumptionUnit consumptionUnit,
            final BigDecimal rawPerCookedRatio) {
        return new Product(ObjectRef.empty(), name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio);
    }

    public static Food food(
            /**
             * The name of this record.
             */
            final String name,
            /**
             * Identifier of this record.
             */
            final String sid,
            /**
             * Comma separated list of facet identifiers,
             * ordered (by some natural order).
             */
            final String facetSids,
            final BigDecimal amountConsumed,
            final ConsumptionUnit consumptionUnit,
            final BigDecimal rawPerCookedRatio) {
        return new Food(ObjectRef.empty(), name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, null, null);
    }

}