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
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

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
     * The type of this record.
     */
    Record24.Type type();

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
        return _Strings.nullToEmpty(sid()) + ";" + _Strings.nullToEmpty(facetSids());
    }

    public static enum Type {

        /**
         * A composition of identified generic food or recipes.
         * <p>
         * Eg. 'on-the-fly' recipes, that were recorded during an interview.
         */
        COMPOSITE,

        /**
         * Generic food, identified within the standard food description model (food-list)
         * or identified within eg. a recipe data base.
         */
        FOOD,

        /**
         * Product identified within a product data base. <p>Eg. supplements.
         */
        PRODUCT,

        TYPE_OF_FAT_USED,
        TYPE_OF_MILK_OR_LIQUID_USED,
        ;

        /** @see #FOOD */
        public boolean isFood() { return this == FOOD; }
        /** @see #COMPOSITE */
        public boolean isComposite() { return this == COMPOSITE; }
        /** @see #PRODUCT */
        public boolean isProduct() { return this == PRODUCT; }

        /** to DTO */
        public String stringify() {
            return name().toLowerCase();
        }

        /** from DTO */
        @Nullable
        public static Type destringify(final String stringified) {
            return StringUtils.hasLength(stringified)
                    ? Type.valueOf(stringified.toUpperCase())
                    : null;
        }
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
            Type type,
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
        default FoodConsumption asFoodConsumption() {
            //FIXME[23] flesh out
            return new FoodConsumption(null, null, null, null, null);
        }
    }

    /**
     * Generic food, identified within the standard food description model (food-list)
     * or identified within eg. a recipe data base.
     */
    public record Food(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            Type type,
            String name,
            String sid,
            String facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio,
            Optional<TypeOfFatUsed> typeOfFatUsedDuringCooking,
            Optional<TypeOfMilkOrLiquidUsed> typeOfMilkOrLiquidUsedDuringCooking
            ) implements Consumption {
    }

    /**
     * Type of fat used during cooking.
     */
    public record TypeOfFatUsed(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            Type type,
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
            Type type,
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
            Type type,
            String name,
            String sid,
            String facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio
            ) implements Consumption {
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
        var composite = new Composite(ObjectRef.empty(), Record24.Type.COMPOSITE,
                name, sid, facetSids, subRecords);
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
        return new Product(ObjectRef.empty(), Record24.Type.PRODUCT,
                name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio);
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
            final BigDecimal rawPerCookedRatio,
            final Can<Record24.Dto> usedDuringCooking) {

        var typeOfFatUsed = usedDuringCooking.stream()
                .filter(TypeOfFatUsed.class::isInstance)
                .findFirst()
                .map(TypeOfFatUsed.class::cast);
        var typeOfMilkOrLiquidUsed = usedDuringCooking.stream()
                .filter(TypeOfMilkOrLiquidUsed.class::isInstance)
                .findFirst()
                .map(TypeOfMilkOrLiquidUsed.class::cast);

        return new Food(ObjectRef.empty(), Record24.Type.FOOD,
                name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio,
                typeOfFatUsed, typeOfMilkOrLiquidUsed);
    }

    /**
     * Type of fat used during cooking.
     */
    public static TypeOfFatUsed typeOfFatUsed(
            //Food parentFood, //FIXME[23] Food parentFood,
            final String name,
            final String sid,
            final String facetSids) {
        return new Record24.TypeOfFatUsed(ObjectRef.empty(), Record24.Type.TYPE_OF_FAT_USED,
                null, name, sid, facetSids);
    }

    /**
     * Type of milk or liquid used during cooking.
     */
    public static TypeOfMilkOrLiquidUsed typeOfMilkOrLiquidUsed(
            //Food parentFood, //FIXME[23] Food parentFood,
            final String name,
            final String sid,
            final String facetSids) {
        return new Record24.TypeOfMilkOrLiquidUsed(ObjectRef.empty(), Record24.Type.TYPE_OF_MILK_OR_LIQUID_USED,
                null, name, sid, facetSids);
    }

}