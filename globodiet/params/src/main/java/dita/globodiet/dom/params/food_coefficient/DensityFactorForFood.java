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
package dita.globodiet.dom.params.food_coefficient;

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
 * Density factor for food
 */
@Named("dita.globodiet.params.food_coefficient.DensityFactorForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "Density factor for food"
)
@PersistenceCapable(
        table = "DENSITY"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class DensityFactorForFood {
    /**
     * Food identification number (FOODNUM)
     * either Foods.foodnum OR Mixedrec.r_idnum
     */
    @Property
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Food identification number (FOODNUM)\n"
                            + "either Foods.foodnum OR Mixedrec.r_idnum\n"
                            + "----\n"
                            + "required=true, unique=false",
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
     * Density coefficient
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Density coefficient\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "D_FACTOR",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double densityCoefficient;

    /**
     * Facet string
     * multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Facet string\n"
                            + "multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACETS_STR",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String facetDescriptorsLookupKey;

    /**
     * Priority order
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Priority order\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PRIORITY",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String priority;

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
            sequence = "5",
            describedAs = "1 = without un-edible part,\n"
                            + "2 = with un-edible (as estimated)\n"
                            + "----\n"
                            + "required=false, unique=true",
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
     * 1 = raw,
     * 2 = cooked (as estimated)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "1 = raw,\n"
                            + "2 = cooked (as estimated)\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RAWCOOK",
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
     * 1 = density for food/ingredient,
     * 2 = density for recipe
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "1 = density for food/ingredient,\n"
                            + "2 = density for recipe\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "D_TYPE",
            allowsNull = "false"
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
    private ForFoodOrRecipe forFoodOrRecipe;

    @ObjectSupport
    public String title() {
        return String.format("%f", densityCoefficient);
    }

    @RequiredArgsConstructor
    public enum WithUnediblePartQ {
        /**
         * without un-edible part,
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
    public enum RawOrCooked {
        /**
         * no description
         */
        RAW("1", "raw"),

        /**
         *  as estimated
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
    public enum ForFoodOrRecipe {
        /**
         * density for food/ingredient,
         */
        FOOD(1, "Food"),

        /**
         * density for recipe
         */
        RECIPE(2, "Recipe");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link DensityFactorForFood}
     */
    @Named("dita.globodiet.params.food_coefficient.DensityFactorForFood.Manager")
    @DomainObjectLayout(
            describedAs = "Density factor for food"
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
            return "Manage Density Factor For Food";
        }

        @Collection
        public final List<DensityFactorForFood> getListOfDensityFactorForFood() {
            return searchService.search(DensityFactorForFood.class, DensityFactorForFood::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
