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
package dita.globodiet.params.food_list;

import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
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
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PrecedingParamsPolicy;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * Food, Product, On-the-fly Recipe or Alias
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.food_list.Food")
@DomainObject
@DomainObjectLayout(
        describedAs = "Food, Product, On-the-fly Recipe or Alias",
        cssClassFa = "solid utensils .food-color"
)
@PersistenceCapable(
        table = "FOODS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class Food implements Cloneable<Food>, HasSecondaryKey<Food> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Identification Code for Food, Product, On-the-fly Recipe or Alias
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Identification Code for Food, Product, On-the-fly Recipe or Alias",
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
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Food Group code",
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
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Food Subgroup code",
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
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Food Sub(sub)group code",
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
     * Native (localized) name of this Food, Product, On-the-fly Recipe or Alias
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Native (localized) name of this Food, Product, On-the-fly Recipe or Alias",
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
     * CR -> Composed Recipe (On-the-fly Recipe)
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
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Type of item:\n"
                            + "(none) -> Normal Food Item\n"
                            + "GI -> Generic Food Item\n"
                            + "SH -> Shadow Item\n"
                            + "CR -> Composed Recipe (On-the-fly Recipe)\n"
                            + "Definition: its different ingredients can be identified and\n"
                            + "quantified separately after preparation\n"
                            + "(e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)\n"
                            + "or just before mixing (e.g. coffee with milk).\n"
                            + "Composed recipes are built during the interview: there is no a priori list of composed recipes.\n"
                            + "They are made from items listed below/linked to a quick list item.\n"
                            + "Example: Salad\n"
                            + "- Lettuce\n"
                            + "- Tomato\n"
                            + "- Cucumber\n"
                            + "- Salad dressing (can be a recipe in some projects where all sauces are in recipes)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 2
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
    private TypeOfItem typeOfItem;

    /**
     * Auxiliary field to force an internal order within each subgroup
     * (if GI then 1 otherwise 2, this forces the GI at the top)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Auxiliary field to force an internal order within each subgroup\n"
                            + "(if GI then 1 otherwise 2, this forces the GI at the top)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ORDER",
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
    private GroupOrdinal groupOrdinal;

    /**
     * 0=food
     * 1=dietary supplement
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "0=food\n"
                            + "1=dietary supplement",
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

    @Override
    public String toString() {
        return "Food(" + "code=" + getCode() + ","
         +"foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"foodNativeName=" + getFoodNativeName() + ","
         +"typeOfItem=" + getTypeOfItem() + ","
         +"groupOrdinal=" + getGroupOrdinal() + ","
         +"dietarySupplementQ=" + getDietarySupplementQ() + ")";
    }

    @Programmatic
    @Override
    public Food copy() {
        var copy = repositoryService.detachedEntity(new Food());
        copy.setCode(getCode());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setFoodNativeName(getFoodNativeName());
        copy.setTypeOfItem(getTypeOfItem());
        copy.setGroupOrdinal(getGroupOrdinal());
        copy.setDietarySupplementQ(getDietarySupplementQ());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            navigable = Navigable.PARENT,
            hidden = Where.EVERYWHERE
    )
    @NotPersistent
    public Food.Manager getNavigableParent() {
        return new Food.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @RequiredArgsConstructor
    public enum TypeOfItem {
        /**
         * no description
         */
        NORMAL_FOOD("", "Normal Food"),

        /**
         * no description
         */
        GENERIC_FOOD("GI", "Generic Food"),

        /**
         * Synonym
         */
        ALIAS("SH", "Alias"),

        /**
         * Recipe, built during the interview (on the fly)
         */
        ON_THE_FLY_RECIPE("CR", "On-the-fly Recipe");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum GroupOrdinal {
        /**
         * first order, use for GI
         */
        FIRST("1", "first"),

        /**
         * second order, use for non GI
         */
        SECOND("2", "second");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum DietarySupplementQ {
        /**
         * not a Dietary Supplement
         */
        NO(0, "No"),

        /**
         * is a Dietary Supplement
         */
        YES(1, "Yes");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link Food}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.food_list.Food.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Food, Product, On-the-fly Recipe or Alias",
            cssClassFa = "solid utensils .food-color"
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
            return "Manage Food";
        }

        @Collection
        public final List<Food> getListOfFood() {
            return searchService.search(Food.class, Food::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Food}
     * @param code Identification Code for Food, Product, On-the-fly Recipe or Alias
     * @param foodGroup Food Group code
     * @param foodSubgroup Food Subgroup code
     * @param foodSubSubgroup Food Sub(sub)group code
     * @param foodNativeName Native (localized) name of this Food, Product, On-the-fly Recipe or Alias
     * @param typeOfItem Type of item:
     * (none) -> Normal Food Item
     * GI -> Generic Food Item
     * SH -> Shadow Item
     * CR -> Composed Recipe (On-the-fly Recipe)
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
     * @param groupOrdinal Auxiliary field to force an internal order within each subgroup
     * (if GI then 1 otherwise 2, this forces the GI at the top)
     * @param dietarySupplementQ 0=food
     * 1=dietary supplement
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Identification Code for Food, Product, On-the-fly Recipe or Alias"
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food Group code"
            )
            FoodGroup foodGroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food Subgroup code"
            )
            FoodSubgroup foodSubgroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food Sub(sub)group code"
            )
            FoodSubgroup foodSubSubgroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Native (localized) name of this Food, Product, On-the-fly Recipe or Alias"
            )
            String foodNativeName,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Type of item:\n"
                                    + "(none) -> Normal Food Item\n"
                                    + "GI -> Generic Food Item\n"
                                    + "SH -> Shadow Item\n"
                                    + "CR -> Composed Recipe (On-the-fly Recipe)\n"
                                    + "Definition: its different ingredients can be identified and\n"
                                    + "quantified separately after preparation\n"
                                    + "(e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)\n"
                                    + "or just before mixing (e.g. coffee with milk).\n"
                                    + "Composed recipes are built during the interview: there is no a priori list of composed recipes.\n"
                                    + "They are made from items listed below/linked to a quick list item.\n"
                                    + "Example: Salad\n"
                                    + "- Lettuce\n"
                                    + "- Tomato\n"
                                    + "- Cucumber\n"
                                    + "- Salad dressing (can be a recipe in some projects where all sauces are in recipes)"
            )
            TypeOfItem typeOfItem,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Auxiliary field to force an internal order within each subgroup\n"
                                    + "(if GI then 1 otherwise 2, this forces the GI at the top)"
            )
            GroupOrdinal groupOrdinal,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=food\n"
                                    + "1=dietary supplement"
            )
            DietarySupplementQ dietarySupplementQ) {
    }

    /**
     * SecondaryKey for @{link Food}
     * @param code Identification Code for Food, Product, On-the-fly Recipe or Alias
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(String code) implements ISecondaryKey<Food> {
        @Override
        public Class<Food> correspondingClass() {
            return Food.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Food} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Unresolvable Food",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.food_list.Food.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends Food implements ViewModel {
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
