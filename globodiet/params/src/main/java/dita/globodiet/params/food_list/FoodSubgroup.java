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

import dita.globodiet.params.classification.FoodGrouping;
import io.github.causewaystuff.companion.applib.services.iconfa.IconFaService;
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
import javax.jdo.annotations.Unique;
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
import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * Food groups further narrowed down by subgroups and optional sub-subgroups
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.food_list.FoodSubgroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Food groups further narrowed down by subgroups and optional sub-subgroups",
        cssClassFa = "solid utensils .food-color,\n"
                        + "solid layer-group .food-color .ov-size-80 .ov-right-55 .ov-bottom-55,\n"
                        + "solid circle-chevron-down .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "SUBGROUP"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_FoodSubgroup",
        members = {"foodGroupCode", "foodSubgroupCode", "foodSubSubgroupCode"}
)
public class FoodSubgroup implements Cloneable<FoodSubgroup>, FoodGrouping, HasSecondaryKey<FoodSubgroup> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Food group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String foodGroupCode;

    /**
     * Food sub-group code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Food sub-group code"
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubgroupCode;

    /**
     * Food sub-sub-group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "3",
            describedAs = "Food sub-sub-group code"
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
     * Name of the food (sub-)(sub-)group
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Name of the food (sub-)(sub-)group"
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * 0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup"
    )
    @Column(
            name = "SGRP_FSS",
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
    private FatOrSauceSweetenerSubgroupQ fatOrSauceSweetenerSubgroupQ;

    /**
     * 0=non fat/sauce subgroup
     * 1= fat/sauce subgroup that can be left over in the dish
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "0=non fat/sauce subgroup\n"
                            + "1= fat/sauce subgroup that can be left over in the dish"
    )
    @Column(
            name = "SGRP_FLO",
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
    private FatOrSauceSubgroupThatCanBeLeftOverInTheDishQ fatOrSauceSubgroupThatCanBeLeftOverInTheDishQ;

    /**
     * 0=non fat during cooking subgroup
     * 1= fat during cooking subgroup
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "0=non fat during cooking subgroup\n"
                            + "1= fat during cooking subgroup"
    )
    @Column(
            name = "SGRP_FDC",
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
    private FatDuringCookingSubgroupQ fatDuringCookingSubgroupQ;

    /**
     * Short Name of the food (sub-)(sub-)group
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Short Name of the food (sub-)(sub-)group"
    )
    @Column(
            name = "NAME_SHORT",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String shortName;

    @Inject
    IconFaService iconFaService;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s|%s)", 
         name, 
         dita.commons.util.FormatUtils.emptyToDash(foodGroupCode), 
         dita.commons.util.FormatUtils.emptyToDash(foodSubgroupCode), 
         dita.commons.util.FormatUtils.emptyToDash(foodSubSubgroupCode))
        ;
    }

    @Override
    public String toString() {
        return "FoodSubgroup(" + "foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"name=" + getName() + ","
         +"fatOrSauceSweetenerSubgroupQ=" + getFatOrSauceSweetenerSubgroupQ() + ","
         +"fatOrSauceSubgroupThatCanBeLeftOverInTheDishQ=" + getFatOrSauceSubgroupThatCanBeLeftOverInTheDishQ() + ","
         +"fatDuringCookingSubgroupQ=" + getFatDuringCookingSubgroupQ() + ","
         +"shortName=" + getShortName() + ")";
    }

    @Programmatic
    @Override
    public FoodSubgroup copy() {
        var copy = repositoryService.detachedEntity(new FoodSubgroup());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setName(getName());
        copy.setFatOrSauceSweetenerSubgroupQ(getFatOrSauceSweetenerSubgroupQ());
        copy.setFatOrSauceSubgroupThatCanBeLeftOverInTheDishQ(getFatOrSauceSubgroupThatCanBeLeftOverInTheDishQ());
        copy.setFatDuringCookingSubgroupQ(getFatDuringCookingSubgroupQ());
        copy.setShortName(getShortName());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @NotPersistent
    public FoodSubgroup.Manager getNavigableParent() {
        return new FoodSubgroup.Manager(searchService, "");
    }

    @ObjectSupport
    public FontAwesomeLayers iconFaLayers() {
        return iconFaService.iconFaLayers(this);
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getFoodGroupCode(), 
        getFoodSubgroupCode(), 
        getFoodSubSubgroupCode());
    }

    @RequiredArgsConstructor
    public enum FatOrSauceSweetenerSubgroupQ {
        /**
         * non fat/sauce/sweetener subgroup
         */
        NO("0", "no"),

        /**
         * fat/sauce/sweetener subgroup
         */
        YES("1", "yes");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum FatOrSauceSubgroupThatCanBeLeftOverInTheDishQ {
        /**
         * non fat/sauce subgroup
         */
        NO("0", "no"),

        /**
         * fat/sauce subgroup that can be left over in the dish
         */
        YES("1", "yes");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum FatDuringCookingSubgroupQ {
        /**
         * non fat during cooking subgroup
         */
        NO("0", "no"),

        /**
         * fat during cooking subgroup
         */
        YES("1", "yes");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link FoodSubgroup}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.food_list.FoodSubgroup.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Food groups further narrowed down by subgroups and optional sub-subgroups",
            cssClassFa = "solid utensils .food-color,\n"
                            + "solid layer-group .food-color .ov-size-80 .ov-right-55 .ov-bottom-55,\n"
                            + "solid circle-chevron-down .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
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
            return "Manage Food Subgroup";
        }

        @Collection
        public final List<FoodSubgroup> getListOfFoodSubgroup() {
            return searchService.search(FoodSubgroup.class, FoodSubgroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link FoodSubgroup}
     * @param foodGroup Food group code
     * @param foodSubgroupCode Food sub-group code
     * @param foodSubSubgroupCode Food sub-sub-group code
     * @param name Name of the food (sub-)(sub-)group
     * @param fatOrSauceSweetenerSubgroupQ 0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup
     * @param fatOrSauceSubgroupThatCanBeLeftOverInTheDishQ 0=non fat/sauce subgroup
     * 1= fat/sauce subgroup that can be left over in the dish
     * @param fatDuringCookingSubgroupQ 0=non fat during cooking subgroup
     * 1= fat during cooking subgroup
     * @param shortName Short Name of the food (sub-)(sub-)group
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Food group code"
            )
            FoodGroup foodGroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Food sub-group code"
            )
            String foodSubgroupCode,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food sub-sub-group code"
            )
            String foodSubSubgroupCode,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Name of the food (sub-)(sub-)group"
            )
            String name,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup"
            )
            FatOrSauceSweetenerSubgroupQ fatOrSauceSweetenerSubgroupQ,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=non fat/sauce subgroup\n"
                                    + "1= fat/sauce subgroup that can be left over in the dish"
            )
            FatOrSauceSubgroupThatCanBeLeftOverInTheDishQ fatOrSauceSubgroupThatCanBeLeftOverInTheDishQ,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=non fat during cooking subgroup\n"
                                    + "1= fat during cooking subgroup"
            )
            FatDuringCookingSubgroupQ fatDuringCookingSubgroupQ,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Short Name of the food (sub-)(sub-)group"
            )
            String shortName) {
    }

    /**
     * SecondaryKey for @{link FoodSubgroup}
     * @param foodGroupCode Food group code
     * @param foodSubgroupCode Food sub-group code
     * @param foodSubSubgroupCode Food sub-sub-group code
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String foodGroupCode,
            String foodSubgroupCode,
            String foodSubSubgroupCode) implements ISecondaryKey<FoodSubgroup> {
        @Override
        public Class<FoodSubgroup> correspondingClass() {
            return FoodSubgroup.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodSubgroup} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable FoodSubgroup",
            describedAs = "Unresolvable FoodSubgroup",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.food_list.FoodSubgroup.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends FoodSubgroup implements ViewModel {
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
