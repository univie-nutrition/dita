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
package dita.globodiet.dom.params.food_list;

import dita.commons.services.foreignkey.HasSecondaryKey;
import dita.commons.services.foreignkey.ISecondaryKey;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Food List and Shadow item list entry
 */
@Named("dita.globodiet.params.food_list.FoodOrProductOrAlias")
@DomainObject
@DomainObjectLayout(
        describedAs = "Food List and Shadow item list entry",
        cssClassFa = "solid utensils darkgreen"
)
@PersistenceCapable(
        table = "FOODS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FoodOrProductOrAlias implements HasSecondaryKey<FoodOrProductOrAlias> {
    /**
     * Food/C.R. Identification Code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food/C.R. Identification Code<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FOODNUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String code;

    /**
     * Food Group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "2",
            describedAs = "Food Group code<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodGroupCode;

    /**
     * Food Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food Subgroup code<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubgroupCode;

    /**
     * Food Sub(sub)group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "4",
            describedAs = "Food Sub(sub)group code<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    /**
     * Food/C.R. Name (Country name)
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Food/C.R. Name (Country name)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String foodNativeName;

    /**
     * Type of item:
     * (none) -> Normal Food Item
     * GI -> Generic Food Item
     * SH -> Shadow Item
     * CR -> Composed Recipe (a.huber: does not appear to be used anywhere)
     * Definition: its different ingredients can be identified and
     * quantified separately after preparation
     * (e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)
     * or just before mixing (e.g. coffee with milk).
     * Composed recipes are built during the interview: there is no a priori list of composed recipes.
     * They are made from items listed below/linked to a quick list item.
     * Example: Salad
     * - Lettuce
     * - Tomato
     * - Cucumber
     * - Salad dressing (can be a recipe in some projects where all sauces are in recipes)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "6",
            describedAs = "Type of item:<br>(none) -> Normal Food Item<br>GI -> Generic Food Item<br>SH -> Shadow Item<br>CR -> Composed Recipe (a.huber: does not appear to be used anywhere)<br>Definition: its different ingredients can be identified and<br>quantified separately after preparation<br>(e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)<br>or just before mixing (e.g. coffee with milk).<br>Composed recipes are built during the interview: there is no a priori list of composed recipes.<br>They are made from items listed below/linked to a quick list item.<br>Example: Salad<br>- Lettuce<br>- Tomato<br>- Cucumber<br>- Salad dressing (can be a recipe in some projects where all sauces are in recipes)<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String typeOfItem;

    /**
     * Auxiliary field to force an internal order within each subgroup
     * (if GI then 1 otherwise 2, this forces the GI at the top)
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Auxiliary field to force an internal order within each subgroup<br>(if GI then 1 otherwise 2, this forces the GI at the top)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ORDER",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String groupOrdinal;

    /**
     * 0=food
     * 1=dietary supplement
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "0=food<br>1=dietary supplement<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SUPPL",
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
    private DietarySupplementQ dietarySupplementQ;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", foodNativeName, code);
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getCode())));
    }

    @RequiredArgsConstructor
    public enum DietarySupplementQ {
        /**
         * no description
         */
        FOOD(0, "Food"),

        /**
         * no description
         */
        SUPPLEMENT(1, "Supplement");

        @Getter
        private final int matchOn;

        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * SecondaryKey for @{link FoodOrProductOrAlias}
     */
    @Value
    public static final class SecondaryKey implements ISecondaryKey<FoodOrProductOrAlias> {
        private static final long serialVersionUID = 1;

        /**
         * Food/C.R. Identification Code
         */
        private String code;

        @Override
        public Class<FoodOrProductOrAlias> correspondingClass() {
            return FoodOrProductOrAlias.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodOrProductOrAlias} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable FoodOrProductOrAlias",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends FoodOrProductOrAlias implements ViewModel {
        @Getter(
                onMethod_ = {@Override}
        )
        @Accessors(
                fluent = true
        )
        private final String viewModelMemento;

        @Override
        public String title() {
            return viewModelMemento;
        }
    }
}
