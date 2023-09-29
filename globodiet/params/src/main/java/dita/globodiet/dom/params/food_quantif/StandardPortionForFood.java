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
package dita.globodiet.dom.params.food_quantif;

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
 * standard portions for foods
 */
@Named("dita.globodiet.params.food_quantif.StandardPortionForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "standard portions for foods"
)
@PersistenceCapable(
        table = "M_STDPOR"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class StandardPortionForFood {
    /**
     * Standard portion quantity
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Standard portion quantity\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "STDP_QUANT",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double standardPortionQuantity;

    /**
     * Food identification number (=FOODMUM)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Food identification number (=FOODMUM)\n"
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
    private String foodCode;

    /**
     * 1 = raw,
     * 2 = cooked (as estimated)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "1 = raw,\n"
                            + "2 = cooked (as estimated)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RAW_COOKED",
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
    private RawOrCooked rawOrCooked;

    /**
     * 1 = without un-edible part,
     * 2 = with un-edible (as estimated)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "1 = without un-edible part,\n"
                            + "2 = with un-edible (as estimated)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "EDIB",
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
    private WithUnediblePartQ withUnediblePartQ;

    /**
     * Comment attached to the standard portion
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Comment attached to the standard portion\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "COMMENT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String commentAttachedToTheStandardPortion;

    /**
     * Standard portion code for the same food (0001, 0002, 0003)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Standard portion code for the same food (0001, 0002, 0003)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PORT_CODE",
            allowsNull = "false",
            length = 4
    )
    @Getter
    @Setter
    private String standardPortionCode;

    /**
     * G = in grams, V = in ml (volume)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "G = in grams, V = in ml (volume)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "STDP_UNIT",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String unit;

    /**
     * Order to display the standard portion
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Order to display the standard portion\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "D_ORDER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double orderToDisplayTheStandardPortion;

    @ObjectSupport
    public String title() {
        return this.toString();
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

    /**
     * Manager Viewmodel for @{link StandardPortionForFood}
     */
    @Named("dita.globodiet.params.food_quantif.StandardPortionForFood.Manager")
    @DomainObjectLayout(
            describedAs = "standard portions for foods"
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
            return "Manage Standard Portion For Food";
        }

        @Collection
        public final List<StandardPortionForFood> getListOfStandardPortionForFood() {
            return searchService.search(StandardPortionForFood.class, StandardPortionForFood::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
