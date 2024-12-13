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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.assertions._Assert;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import dita.commons.food.consumption.FoodConsumption;
import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.recall24.dto.RuntimeAnnotated.Annotation;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

public sealed interface Record24 extends RecallNode24
permits
    Record24.Comment,
    Record24.Composite,
    Record24.TypeOfFatUsed,
    Record24.TypeOfMilkOrLiquidUsed,
    Record24.Consumption {

    default void visitDepthFirst(final int level, final IndexedConsumer<Record24> onRecord) {
        onRecord.accept(level, this);
    }

    default QualifiedMapKey asQualifiedMapKey() {
        return new QualifiedMapKey(sid(), facetSids());
    }

    /**
     * Memorized food this record belongs to.
     */
    default MemorizedFood24 parentMemorizedFood() {
        return parentMemorizedFoodRef().getValue();
    }

    ObjectRef<MemorizedFood24> parentMemorizedFoodRef();

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

        COMMENT,

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

        public boolean isInformal() {
            return this == COMMENT
                || this == TYPE_OF_FAT_USED
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

    /**
     * A composition of identified generic food or recipes.
     */
    public record Composite(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24> parentMemorizedFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            /**
             * Nested records.
             */
            @CollectionLayout(navigableSubtree = "1")
            Can<? extends Record24> subRecords,
            Map<String, Annotation> annotations

            ) implements Record24, RuntimeAnnotated {

        @Override
        public void visitDepthFirst(final int level, final IndexedConsumer<Record24> onRecord) {
            onRecord.accept(level, this);
            subRecords.forEach(rec->{
                switch (rec) {
                case Composite composite -> composite.visitDepthFirst(level+1, onRecord);
                default -> {}
                }
            });
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<Composite> asBuilder() {
            return Builder.of(this);
        }

        @Getter @Setter @Accessors(fluent=true)
        public static class Builder implements Builder24<Composite> {
            private @NonNull Record24.Type type;

            private String name;
            private SemanticIdentifier sid;
            private SemanticIdentifierSet facetSids;

            final List<Record24> subRecords = new ArrayList<>();
            final List<Annotation> annotations = new ArrayList<>();

            static Builder of(final Composite composite) {
                var builder = new Builder().type(composite.type)
                        .name(composite.name()).sid(composite.sid()).facetSids(composite.facetSids());

                composite.subRecords().forEach(sr->builder.subRecords.add(sr));
                composite.annotations().values().forEach(builder.annotations::add);
                return builder;
            }

            public Builder addAnnotation(final Annotation annotation) {
                annotations.add(annotation);
                return this;
            }

            public Builder replaceSubRecords(final UnaryOperator<Record24> mapper) {
                var replacedSubRecords = subRecords.stream()
                    .map(mapper)
                    .toList();
                subRecords.clear();
                subRecords.addAll(replacedSubRecords);
                return this;
            }

            @Override
            public Composite build() {
                var composite = composite(name, sid, facetSids, Can.ofCollection(subRecords), Can.ofCollection(annotations));
                composite.subRecords().stream()
                    .map(Record24.class::cast)
                    .forEach(child->child.parentMemorizedFoodRef()
                            .setValue(composite.parentMemorizedFood()));
                return composite;
            }
        }
    }

    public sealed interface Consumption extends Record24, RuntimeAnnotated
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
                    name(),
                    Objects.requireNonNull(sid()),
                    Objects.requireNonNull(facetSids()),
                    Objects.requireNonNull(consumptionUnit()),
                    Objects.requireNonNull(amountConsumed()));
        }

        Consumption withAnnotationAdded(@Nullable Annotation annotation);
    }

    /**
     * Generic food, identified within the standard food description model (food-list)
     * or identified within eg. a recipe data base.
     */
    public record Food(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24> parentMemorizedFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio,
            Optional<TypeOfFatUsed> typeOfFatUsedDuringCooking,
            Optional<TypeOfMilkOrLiquidUsed> typeOfMilkOrLiquidUsedDuringCooking,
            Map<String, Annotation> annotations
            ) implements Consumption {

        @Override
        public void visitDepthFirst(final int level, final IndexedConsumer<Record24> onRecord) {
            onRecord.accept(level, this);
            typeOfFatUsedDuringCooking.ifPresent(x->onRecord.accept(level + 1, x));
            typeOfMilkOrLiquidUsedDuringCooking.ifPresent(x->onRecord.accept(level + 1, x));
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<Food> asBuilder() {
            return Builder.of(this);
        }

        @Getter @Setter @Accessors(fluent=true)
        public static class Builder implements Builder24<Food> {
            private @NonNull Record24.Type type;

            private String name;
            private SemanticIdentifier sid;
            private SemanticIdentifierSet facetSids;
            private BigDecimal amountConsumed;
            private ConsumptionUnit consumptionUnit;
            private BigDecimal rawPerCookedRatio;

            final List<Record24> subRecords = new ArrayList<>();
            final List<Annotation> annotations = new ArrayList<>();

            static Builder of(final Food food) {
                var builder = new Builder().type(food.type)
                    .name(food.name()).sid(food.sid()).facetSids(food.facetSids())
                    .amountConsumed(food.amountConsumed)
                    .consumptionUnit(food.consumptionUnit)
                    .rawPerCookedRatio(food.rawPerCookedRatio);
                food.annotations().values().forEach(builder.annotations::add);
                return builder;
            }

            public Builder addAnnotation(final Annotation annotation) {
                annotations.add(annotation);
                return this;
            }

            @Override
            public Food build() {
                var food = food(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio,
                        Can.ofCollection(subRecords), Can.ofCollection(annotations));
                return food;
            }
        }

        @Override
        public Consumption withAnnotationAdded(@Nullable final Annotation annotation) {
            if(annotation==null) return this;
            return ((Food.Builder)asBuilder())
                    .addAnnotation(annotation)
                    .build();
        }
    }

    /**
     * Type of fat used during cooking.
     */
    public record TypeOfFatUsed(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24> parentMemorizedFoodRef,
            /** Food this record belongs to. */
            @JsonIgnore
            ObjectRef<Food> parentFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids
            ) implements Record24 {

        public Food parentFood() {
            return parentFoodRef.getValue();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<TypeOfFatUsed> asBuilder() {
            return Builder.of(this);
        }

        @Getter @Setter @Accessors(fluent=true)
        public static class Builder implements Builder24<TypeOfFatUsed> {
            private @NonNull Record24.Type type;

            private String name;
            private SemanticIdentifier sid;
            private SemanticIdentifierSet facetSids;

            static Builder of(final TypeOfFatUsed typeOfFatUsed) {
                var builder = new Builder().type(typeOfFatUsed.type)
                        .name(typeOfFatUsed.name()).sid(typeOfFatUsed.sid()).facetSids(typeOfFatUsed.facetSids());
                return builder;
            }

            @Override
            public TypeOfFatUsed build() {
                var typeOfFatUsed = typeOfFatUsed(name, sid, facetSids);
                return typeOfFatUsed;
            }
        }
    }

    /**
     * Type of milk or liquid used during cooking.
     */
    public record TypeOfMilkOrLiquidUsed(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24> parentMemorizedFoodRef,
            /** Food this record belongs to. */
            @JsonIgnore
            ObjectRef<Food> parentFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids
            ) implements Record24 {

        public Food parentFood() {
            return parentFoodRef.getValue();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<TypeOfMilkOrLiquidUsed> asBuilder() {
            return Builder.of(this);
        }

        @Getter @Setter @Accessors(fluent=true)
        public static class Builder implements Builder24<TypeOfMilkOrLiquidUsed> {
            private @NonNull Record24.Type type;

            private String name;
            private SemanticIdentifier sid;
            private SemanticIdentifierSet facetSids;

            static Builder of(final TypeOfMilkOrLiquidUsed typeOfMilkOrLiquidUsed) {
                var builder = new Builder().type(typeOfMilkOrLiquidUsed.type)
                        .name(typeOfMilkOrLiquidUsed.name()).sid(typeOfMilkOrLiquidUsed.sid()).facetSids(typeOfMilkOrLiquidUsed.facetSids());
                return builder;
            }

            @Override
            public TypeOfMilkOrLiquidUsed build() {
                var typeOfMilkOrLiquidUsed = typeOfMilkOrLiquidUsed(name, sid, facetSids);
                return typeOfMilkOrLiquidUsed;
            }
        }
    }

    /**
     * Fat consumed due to the fact that it was used for frying during cooking.
     */
    public record FryingFat(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24> parentMemorizedFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio,
            Map<String, Annotation> annotations
            ) implements Consumption {

        public FryingFat {
            _Assert.assertEquals(Type.FRYING_FAT, type);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<FryingFat> asBuilder() {
            return Builder.of(this);
        }

        @Getter @Setter @Accessors(fluent=true)
        public static class Builder implements Builder24<FryingFat> {
            private @NonNull Record24.Type type;

            private String name;
            private SemanticIdentifier sid;
            private SemanticIdentifierSet facetSids;
            private BigDecimal amountConsumed;
            private ConsumptionUnit consumptionUnit;
            private BigDecimal rawPerCookedRatio;
            final List<Annotation> annotations = new ArrayList<>();

            static Builder of(final FryingFat fryingFat) {
                var builder = new Builder().type(fryingFat.type)
                        .name(fryingFat.name()).sid(fryingFat.sid()).facetSids(fryingFat.facetSids())
                        .amountConsumed(fryingFat.amountConsumed)
                        .consumptionUnit(fryingFat.consumptionUnit)
                        .rawPerCookedRatio(fryingFat.rawPerCookedRatio);
                fryingFat.annotations().values().forEach(builder.annotations::add);
                return builder;
            }

            public Builder addAnnotation(final Annotation annotation) {
                annotations.add(annotation);
                return this;
            }

            @Override
            public FryingFat build() {
                var fryingFat = fryingFat(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, Can.ofCollection(annotations));
                return fryingFat;
            }
        }

        @Override
        public Consumption withAnnotationAdded(@Nullable final Annotation annotation) {
            if(annotation==null) return this;
            return ((FryingFat.Builder)asBuilder())
                    .addAnnotation(annotation)
                    .build();
        }
    }

    /**
     * Product identified within a product data base.<p>e.g. supplements
     */
    public record Product(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24> parentMemorizedFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            BigDecimal amountConsumed,
            ConsumptionUnit consumptionUnit,
            BigDecimal rawPerCookedRatio,
            Map<String, Annotation> annotations
            ) implements Consumption {

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<Product> asBuilder() {
            return Builder.of(this);
        }

        @Getter @Setter @Accessors(fluent=true)
        public static class Builder implements Builder24<Product> {
            private @NonNull Record24.Type type;

            private String name;
            private SemanticIdentifier sid;
            private SemanticIdentifierSet facetSids;
            private BigDecimal amountConsumed;
            private ConsumptionUnit consumptionUnit;
            private BigDecimal rawPerCookedRatio;
            final List<Annotation> annotations = new ArrayList<>();

            static Builder of(final Product product) {
                var builder = new Builder().type(product.type)
                        .name(product.name()).sid(product.sid()).facetSids(product.facetSids())
                        .amountConsumed(product.amountConsumed)
                        .consumptionUnit(product.consumptionUnit)
                        .rawPerCookedRatio(product.rawPerCookedRatio);
                product.annotations().values().forEach(builder.annotations::add);
                return builder;
            }

            public Builder addAnnotation(final Annotation annotation) {
                annotations.add(annotation);
                return this;
            }

            @Override
            public Product build() {
                var product = product(name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, Can.ofCollection(annotations));
                return product;
            }
        }

        @Override
        public Consumption withAnnotationAdded(final Annotation annotation) {
            if(annotation==null) return this;
            return ((Product.Builder)asBuilder())
                    .addAnnotation(annotation)
                    .build();
        }
    }

    /**
     * Type of fat used during cooking.
     */
    public record Comment(
            /** Memorized food this record belongs to. */
            @JsonIgnore
            ObjectRef<MemorizedFood24> parentMemorizedFoodRef,
            Type type,
            String name,
            SemanticIdentifier sid,
            SemanticIdentifierSet facetSids,
            Map<String, Annotation> annotations
            ) implements Record24, RuntimeAnnotated {

        @SuppressWarnings("unchecked")
        @Override
        public Builder24<Comment> asBuilder() {
            return Builder.of(this);
        }

        @Getter @Setter @Accessors(fluent=true)
        public static class Builder implements Builder24<Comment> {
            private @NonNull Record24.Type type;

            private String name;
            private SemanticIdentifier sid;
            private SemanticIdentifierSet facetSids;

            final List<Annotation> annotations = new ArrayList<>();

            static Builder of(final Comment comment) {
                var builder = new Builder().type(comment.type)
                        .name(comment.name()).sid(comment.sid()).facetSids(comment.facetSids());
                comment.annotations().values().forEach(builder.annotations::add);
                return builder;
            }

            public Builder addAnnotation(final Annotation annotation) {
                annotations.add(annotation);
                return this;
            }

            @Override
            public Comment build() {
                var comment = comment(name, sid, facetSids, Can.ofCollection(annotations));
                return comment;
            }
        }
    }

    // -- FACTORIES

    public static Comment comment(
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
            final Can<Annotation> annotations) {
        var comment = new Comment(ObjectRef.empty(), Record24.Type.COMMENT,
                name, sid, facetSids, annotations.toMap(Annotation::key));
        return comment;
    }

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
            final Can<? extends Record24> subRecords,
            final Can<Annotation> annotations) {
        var composite = new Composite(ObjectRef.empty(), Record24.Type.COMPOSITE,
                name, sid, facetSids, subRecords, annotations.toMap(Annotation::key));
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
            final BigDecimal rawPerCookedRatio,
            final Can<Annotation> annotations) {
        return new Product(ObjectRef.empty(), Record24.Type.PRODUCT,
                name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, annotations.toMap(Annotation::key));
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
            final BigDecimal rawPerCookedRatio,
            final Can<Annotation> annotations) {
        return new FryingFat(ObjectRef.empty(), Record24.Type.FRYING_FAT,
                name, sid, facetSids, amountConsumed, consumptionUnit, rawPerCookedRatio, annotations.toMap(Annotation::key));
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
            final Can<Record24> usedDuringCooking,
            final Can<Annotation> annotations) {

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
                typeOfFatUsed, typeOfMilkOrLiquidUsed, annotations.toMap(Annotation::key));

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

}
