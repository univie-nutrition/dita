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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.assertions._Assert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
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
    SemanticIdentifier sid();

    /**
     * Set of qualifying semantic identifiers (ordered by some natural order).
     */
    SemanticIdentifierSet facetSids();

    default public String discriminator() {
        return (sid().isEmpty() ? "" : sid().toStringNoBox())
                + ";"
                + facetSids().toString();
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
         * Fat used for frying, added as Consumption record.
         */
        FRYING_FAT,

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

        public boolean isInformal() { return this == TYPE_OF_FAT_USED
                || this == TYPE_OF_MILK_OR_LIQUID_USED; }

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

    @Override
    @SuppressWarnings("unchecked")
    Builder24<Dto> asBuilder();

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

        default void visitDepthFirst(final int level, final IndexedConsumer<Dto> onRecord) {
            onRecord.accept(level, this);
        }

        default QualifiedMapKey asQualifiedMapKey() {
            return new QualifiedMapKey(sid(), facetSids());
        }

//        /**
//         * Converts {@link #sid()} to a {@link SemanticIdentifier}.
//         */
//        default SemanticIdentifier sidFullyQualified(
//                final SystemId systemId,
//                final ObjectId.Context context) {
//            return new SemanticIdentifier(systemId, new ObjectId(context, sid()));
//        }
//
//        /**
//         * Converts {@link #facetSids()} to a {@link SemanticIdentifierSet}.
//         */
//        default SemanticIdentifierSet facetSidsFullyQualified(
//                final SystemId systemId,
//                final ObjectId.Context context) {
//            return SemanticIdentifierSet.ofStream(
//                    _Strings.splitThenStream(facetSids(), ",")
//                        .map(facetSid->new SemanticIdentifier(systemId, new ObjectId(context, facetSid))));
//        }
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
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            /**
             * Nested records.
             */
            @TreeSubNodes
            Can<? extends Record24> subRecords
            ) implements Record24.Dto {

        @Override
        public void visitDepthFirst(final int level, final IndexedConsumer<Dto> onRecord) {
            onRecord.accept(level, this);
            subRecords.forEach(rec->{
                switch (rec) {
                case Dto dto -> dto.visitDepthFirst(level+1, onRecord);
                default -> {}
                }
            });
        }

        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder(type()).name(name).sid(sid).facetSids(facetSids);
        }
    }

    public sealed interface Consumption extends Record24.Dto
    permits Food, FryingFat, Product {

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
            return new FoodConsumption(
                    name(), sid(), facetSids(),
                    consumptionUnit(), amountConsumed());
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
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio,
            Optional<TypeOfFatUsed> typeOfFatUsedDuringCooking,
            Optional<TypeOfMilkOrLiquidUsed> typeOfMilkOrLiquidUsedDuringCooking
            ) implements Consumption {

        @Override
        public void visitDepthFirst(final int level, final IndexedConsumer<Dto> onRecord) {
            onRecord.accept(level, this);
            typeOfFatUsedDuringCooking.ifPresent(x->onRecord.accept(level + 1, x));
            typeOfMilkOrLiquidUsedDuringCooking.ifPresent(x->onRecord.accept(level + 1, x));
        }

        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder(type()).name(name).sid(sid).facetSids(facetSids)
                    .amountConsumed(amountConsumed).consumptionUnit(consumptionUnit)
                    .rawPerCookedRatio(rawPerCookedRatio);
        }
    }

    /**
     * Type of fat used during cooking.
     */
    public record TypeOfFatUsed(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            /** Food this record belongs to. */
            @JsonIgnore
            ObjectRef<Food> parentFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids
            ) implements Record24.Dto {

        public Food parentFood() {
            return parentFoodRef.getValue();
        }

        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder(type()).name(name).sid(sid).facetSids(facetSids);
        }
    }

    /**
     * Type of milk or liquid used during cooking.
     */
    public record TypeOfMilkOrLiquidUsed(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            /** Food this record belongs to. */
            @JsonIgnore
            ObjectRef<Food> parentFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids
            ) implements Record24.Dto {
        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder(type()).name(name).sid(sid).facetSids(facetSids);
        }

        public Food parentFood() {
            return parentFoodRef.getValue();
        }
    }

    /**
     * Fat consumed due to the fact that it was used for frying during cooking.
     */
    public record FryingFat(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24.Dto> parentMemorizedFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio
            ) implements Consumption {

        public FryingFat {
            _Assert.assertEquals(Type.FRYING_FAT, type);
        }

        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder(type()).name(name).sid(sid).facetSids(facetSids)
                    .amountConsumed(amountConsumed).consumptionUnit(consumptionUnit)
                    .rawPerCookedRatio(rawPerCookedRatio);
        }
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
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio
            ) implements Consumption {

        @Override
        public Builder24<Dto> asBuilder() {
            return new Builder(type()).name(name).sid(sid).facetSids(facetSids)
                    .amountConsumed(amountConsumed).consumptionUnit(consumptionUnit)
                    .rawPerCookedRatio(rawPerCookedRatio);
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
            final SemanticIdentifier sid,
            /**
             * Comma separated list of facet identifiers,
             * ordered (by some natural order).
             */
            final SemanticIdentifierSet facetSids,
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
            final SemanticIdentifier sid,
            /**
             * Set of qualifying semantic identifiers (ordered by some natural order).
             */
            final SemanticIdentifierSet facetSids,
            final BigDecimal amountConsumed,
            final ConsumptionUnit consumptionUnit,
            final BigDecimal rawPerCookedRatio) {
        return new Product(ObjectRef.empty(), Record24.Type.PRODUCT,
                name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio);
    }

    public static FryingFat fryingFat(
            /**
             * The name of this record.
             */
            final String name,
            /**
             * Identifier of this record.
             */
            final SemanticIdentifier sid,
            /**
             * Set of qualifying semantic identifiers (ordered by some natural order).
             */
            final SemanticIdentifierSet facetSids,
            final BigDecimal amountConsumed,
            final ConsumptionUnit consumptionUnit,
            final BigDecimal rawPerCookedRatio) {
        return new FryingFat(ObjectRef.empty(), Record24.Type.FRYING_FAT,
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
            final SemanticIdentifier sid,
            /**
             * Set of qualifying semantic identifiers (ordered by some natural order).
             */
            final SemanticIdentifierSet facetSids,
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

        var food = new Food(ObjectRef.empty(), Record24.Type.FOOD,
                name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio,
                typeOfFatUsed, typeOfMilkOrLiquidUsed);

        typeOfFatUsed.ifPresent(rec->rec.parentFoodRef().setValue(food));
        typeOfMilkOrLiquidUsed.ifPresent(rec->rec.parentFoodRef().setValue(food));

        return food;
    }

    /**
     * Type of fat used during cooking.
     */
    public static TypeOfFatUsed typeOfFatUsed(
            final String name,
            final SemanticIdentifier sid,
            final SemanticIdentifierSet facetSids) {
        return new Record24.TypeOfFatUsed(ObjectRef.empty(), ObjectRef.empty(),
                Record24.Type.TYPE_OF_FAT_USED,
                name, sid, facetSids);
    }

    /**
     * Type of milk or liquid used during cooking.
     */
    public static TypeOfMilkOrLiquidUsed typeOfMilkOrLiquidUsed(
            final String name,
            final SemanticIdentifier sid,
            final SemanticIdentifierSet facetSids) {
        return new Record24.TypeOfMilkOrLiquidUsed(ObjectRef.empty(), ObjectRef.empty(),
                Record24.Type.TYPE_OF_MILK_OR_LIQUID_USED,
                name, sid, facetSids);
    }

    // -- BUILDER

    @RequiredArgsConstructor
    @Getter @Setter @Accessors(fluent=true)
    public static class Builder implements Builder24<Dto> {
        private final Record24.Type type;

        private String name;
        private SemanticIdentifier sid;
        private SemanticIdentifierSet facetSids;
        private BigDecimal amountConsumed;
        private ConsumptionUnit consumptionUnit;
        private BigDecimal rawPerCookedRatio;

        final List<Record24.Dto> subRecords = new ArrayList<>();
        @Override
        public Dto build() {
            return switch (type) {
            case COMPOSITE -> composite(name, sid, facetSids, Can.ofCollection(subRecords));
            case FOOD -> food(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, Can.ofCollection(subRecords));
            case FRYING_FAT -> fryingFat(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio);
            case PRODUCT -> product(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio);
            case TYPE_OF_FAT_USED -> typeOfFatUsed(name, sid, facetSids);
            case TYPE_OF_MILK_OR_LIQUID_USED -> typeOfMilkOrLiquidUsed(name, sid, facetSids);
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
            };
        }
    }

}