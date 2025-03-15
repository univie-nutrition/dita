/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.params.quantif;

import io.github.causewaystuff.companion.applib.jpa.EnumConverter;
import io.github.causewaystuff.companion.applib.jpa.EnumWithCode;
import io.github.causewaystuff.companion.applib.jpa.Persistable;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * standard units for foods and recipes
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.quantif.StandardUnitForFoodOrRecipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "standard units for foods and recipes",
        cssClassFa = "solid shapes"
)
@Entity
@Table(
        name = "M_STDUTS"
)
public class StandardUnitForFoodOrRecipe implements Persistable, Cloneable<StandardUnitForFoodOrRecipe> {
    @Inject
    @Transient
    RepositoryService repositoryService;

    @Inject
    @Transient
    SearchService searchService;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    /**
     * Standard unit code, unique for referenced food/recipe (0001, 0002, ...)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Standard unit code, unique for referenced food/recipe (0001, 0002, ...)"
    )
    @Column(
            name = "\"UNIT_CODE\"",
            nullable = false,
            length = 4
    )
    @Getter
    @Setter
    private String code;

    /**
     * Food or Recipe identification number (code)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Food or Recipe identification number (code)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"ID_NUM\"",
            nullable = false,
            length = 5
    )
    @Getter
    @Setter
    private String foodOrRecipeCode;

    /**
     * 1 = raw,
     * 2 = cooked (as estimated)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "1 = raw,\n"
                    + "2 = cooked (as estimated)"
    )
    @Column(
            name = "\"RAW_COOKED\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = RawOrCooked.Converter.class
    )
    private RawOrCooked rawOrCooked;

    /**
     * 1 = without un-edible part,
     * 2 = with un-edible (as estimated)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "1 = without un-edible part,\n"
                    + "2 = with un-edible (as estimated)"
    )
    @Column(
            name = "\"EDIB\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = WithUnediblePartQ.Converter.class
    )
    private WithUnediblePartQ withUnediblePartQ;

    /**
     * 1 = STDU for food,
     * 2 = STDU for recipe
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "1 = STDU for food,\n"
                    + "2 = STDU for recipe"
    )
    @Column(
            name = "\"TYPE\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = Type.Converter.class
    )
    private Type type;

    /**
     * Comment attached to the standard unit
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Comment attached to the standard unit"
    )
    @Column(
            name = "\"COMMENT\"",
            nullable = false,
            length = 100
    )
    @Getter
    @Setter
    private String comment;

    /**
     * Standard unit quantity (amount)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Standard unit quantity (amount)"
    )
    @Column(
            name = "\"STDU_QUANT\"",
            nullable = false
    )
    @Getter
    @Setter
    private double quantity;

    /**
     * G = in Unit grams (mass)
     * V = in Unit milliliter (volume)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "G = in Unit grams (mass)\n"
                    + "V = in Unit milliliter (volume)"
    )
    @Column(
            name = "\"STDU_UNIT\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = Unit.Converter.class
    )
    private Unit unit;

    /**
     * Order to display the standard unit
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "9",
            describedAs = "Order to display the standard unit"
    )
    @Column(
            name = "\"D_ORDER\"",
            nullable = false
    )
    @Getter
    @Setter
    private double displayOrder;

    @ObjectSupport
    public String title() {
        return String.format("%s (quantity=%.3f, comment=%s)", code, quantity, comment);
    }

    @Override
    public String toString() {
        return "StandardUnitForFoodOrRecipe(" + "code=" + getCode() + ","
         +"foodOrRecipeCode=" + getFoodOrRecipeCode() + ","
         +"rawOrCooked=" + getRawOrCooked() + ","
         +"withUnediblePartQ=" + getWithUnediblePartQ() + ","
         +"type=" + getType() + ","
         +"comment=" + getComment() + ","
         +"quantity=" + getQuantity() + ","
         +"unit=" + getUnit() + ","
         +"displayOrder=" + getDisplayOrder() + ")";
    }

    @Programmatic
    @Override
    public StandardUnitForFoodOrRecipe copy() {
        var copy = repositoryService.detachedEntity(new StandardUnitForFoodOrRecipe());
        copy.setCode(getCode());
        copy.setFoodOrRecipeCode(getFoodOrRecipeCode());
        copy.setRawOrCooked(getRawOrCooked());
        copy.setWithUnediblePartQ(getWithUnediblePartQ());
        copy.setType(getType());
        copy.setComment(getComment());
        copy.setQuantity(getQuantity());
        copy.setUnit(getUnit());
        copy.setDisplayOrder(getDisplayOrder());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @Transient
    public StandardUnitForFoodOrRecipe.Manager getNavigableParent() {
        return new StandardUnitForFoodOrRecipe.Manager(searchService, "");
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum RawOrCooked implements EnumWithCode<String> {

        /**
         * no description
         */
        RAW("1", "raw"),
        /**
         * cooked as estimated
         */
        COOKED("2", "cooked");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<RawOrCooked, String> {
            @Override
            public RawOrCooked[] values() {
                return RawOrCooked.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum WithUnediblePartQ implements EnumWithCode<String> {

        /**
         * without un-edible part
         */
        UN_EDIBLE_EXCLUDED("1", "un-edible excluded"),
        /**
         * with un-edible (as estimated)
         */
        UN_EDIBLE_INCLUDED("2", "un-edible included");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<WithUnediblePartQ, String> {
            @Override
            public WithUnediblePartQ[] values() {
                return WithUnediblePartQ.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum Type implements EnumWithCode<String> {

        /**
         * no description
         */
        FOOD("1", "Food"),
        /**
         * no description
         */
        RECIPE("2", "Recipe");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<Type, String> {
            @Override
            public Type[] values() {
                return Type.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum Unit implements EnumWithCode<String> {

        /**
         * in unit grams (mass)
         */
        GRAMS("G", "Grams"),
        /**
         * in unit milliliter (volume)
         */
        MILLILITER("V", "Milliliter");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<Unit, String> {
            @Override
            public Unit[] values() {
                return Unit.values();
            }
        }
    }

    /**
     * Manager Viewmodel for @{link StandardUnitForFoodOrRecipe}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.quantif.StandardUnitForFoodOrRecipe.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "standard units for foods and recipes",
            cssClassFa = "solid shapes"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
        )
        @PropertyLayout(
                fieldSetId = "searchBar"
        )
        @Getter
        @Setter
        private String search;

        @ObjectSupport
        public String title() {
            return "Manage Standard Unit For Food Or Recipe";
        }

        @Collection
        public final List<StandardUnitForFoodOrRecipe> getListOfStandardUnitForFoodOrRecipe() {
            return searchService.search(StandardUnitForFoodOrRecipe.class, StandardUnitForFoodOrRecipe::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
