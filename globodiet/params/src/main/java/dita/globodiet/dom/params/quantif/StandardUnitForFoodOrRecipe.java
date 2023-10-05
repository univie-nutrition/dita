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
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.quantif;

import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
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
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * standard units for foods and recipes
 */
@Named("dita.globodiet.params.quantif.StandardUnitForFoodOrRecipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "standard units for foods and recipes"
)
@PersistenceCapable(
        table = "M_STDUTS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class StandardUnitForFoodOrRecipe {
    /**
     * Standard unit quantity
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Standard unit quantity",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "STDU_QUANT",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double standardUnitQuantity;

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
            name = "ID_NUM",
            allowsNull = "false",
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
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "1 = raw,\n"
                            + "2 = cooked (as estimated)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RAW_COOKED",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private RawOrCooked rawOrCooked;

    /**
     * 1 = without un-edible part,
     * 2 = with un-edible (as estimated)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "1 = without un-edible part,\n"
                            + "2 = with un-edible (as estimated)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "EDIB",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
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
                            + "2 = STDU for recipe",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
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
            describedAs = "Comment attached to the standard unit",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "COMMENT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String commentAttachedToTheStandardUnit;

    /**
     * Standard unit code for the same food/recipe (0001, 0002, 0003)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Standard unit code for the same food/recipe (0001, 0002, 0003)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "UNIT_CODE",
            allowsNull = "false",
            length = 4
    )
    @Getter
    @Setter
    private String standardUnitCode;

    /**
     * G = in grams,
     * V = in ml (volume)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "G = in grams,\n"
                            + "V = in ml (volume)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "STDU_UNIT",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String unit;

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
            describedAs = "Order to display the standard unit",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "D_ORDER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double orderToDisplayTheStandardUnit;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "StandardUnitForFoodOrRecipe(" + "standardUnitQuantity=" + getStandardUnitQuantity() + ","
         +"foodOrRecipeCode=" + getFoodOrRecipeCode() + ","
         +"rawOrCooked=" + getRawOrCooked() + ","
         +"withUnediblePartQ=" + getWithUnediblePartQ() + ","
         +"type=" + getType() + ","
         +"commentAttachedToTheStandardUnit=" + getCommentAttachedToTheStandardUnit() + ","
         +"standardUnitCode=" + getStandardUnitCode() + ","
         +"unit=" + getUnit() + ","
         +"orderToDisplayTheStandardUnit=" + getOrderToDisplayTheStandardUnit() + ")";
    }

    @RequiredArgsConstructor
    public enum RawOrCooked {
        /**
         * no description
         */
        RAW("1", "raw"),

        /**
         * cooked as estimated
         */
        COOKED("2", "cooked");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum WithUnediblePartQ {
        /**
         * without un-edible part
         */
        UN_EDIBLE_EXCLUDED("1", "un-edible excluded"),

        /**
         * with un-edible (as estimated)
         */
        UN_EDIBLE_INCLUDED("2", "un-edible included");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum Type {
        /**
         * no description
         */
        FOOD("1", "Food"),

        /**
         * no description
         */
        RECIPE("2", "Recipe");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link StandardUnitForFoodOrRecipe}
     */
    @Named("dita.globodiet.params.quantif.StandardUnitForFoodOrRecipe.Manager")
    @DomainObjectLayout(
            describedAs = "standard units for foods and recipes"
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
