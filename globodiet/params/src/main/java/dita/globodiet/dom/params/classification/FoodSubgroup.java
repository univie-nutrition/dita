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
package dita.globodiet.dom.params.classification;

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
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DependentDefaultsPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Food groups further narrowed down by subgroups and optional sub-subgroups
 */
@Named("dita.globodiet.params.classification.FoodSubgroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Food groups further narrowed down by subgroups and optional sub-subgroups",
        cssClassFa = "solid layer-group darkgreen"
)
@PersistenceCapable(
        table = "SUBGROUP"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FoodSubgroup implements FoodGrouping, HasSecondaryKey<FoodSubgroup> {
    /**
     * Food group code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food group code\n"
                            + "----\n"
                            + "required=true, unique=false",
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
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Food sub-group code\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
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
            sequence = "3",
            describedAs = "Food sub-sub-group code\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.NOWHERE
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
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Name of the food (sub-)(sub-)group\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
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
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
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
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "0=non fat/sauce subgroup\n"
                            + "1= fat/sauce subgroup that can be left over in the dish\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
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
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "0=non fat during cooking subgroup\n"
                            + "1= fat during cooking subgroup\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
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
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Short Name of the food (sub-)(sub-)group\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME_SHORT",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String shortName;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s|%s)", 
         name, 
         dita.commons.format.FormatUtils.emptyToDash(foodGroupCode), 
         dita.commons.format.FormatUtils.emptyToDash(foodSubgroupCode), 
         dita.commons.format.FormatUtils.emptyToDash(foodSubSubgroupCode))
        ;
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getFoodGroupCode(), getFoodSubgroupCode(), getFoodSubSubgroupCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getFoodGroupCode(), getFoodSubgroupCode(), getFoodSubSubgroupCode())));
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
     * Parameter model for @{link FoodSubgroup}
     * @param foodGroupCode Food group code
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
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES
            )
            @ParameterLayout(
                    describedAs = "Food group code"
            )
            FoodGroup foodGroupCode,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES
            )
            @ParameterLayout(
                    describedAs = "Food sub-group code"
            )
            String foodSubgroupCode,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food sub-sub-group code"
            )
            String foodSubSubgroupCode,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES
            )
            @ParameterLayout(
                    describedAs = "Name of the food (sub-)(sub-)group"
            )
            String name,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES
            )
            @ParameterLayout(
                    describedAs = "0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup"
            )
            FatOrSauceSweetenerSubgroupQ fatOrSauceSweetenerSubgroupQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES
            )
            @ParameterLayout(
                    describedAs = "0=non fat/sauce subgroup\n"
                                    + "1= fat/sauce subgroup that can be left over in the dish"
            )
            FatOrSauceSubgroupThatCanBeLeftOverInTheDishQ fatOrSauceSubgroupThatCanBeLeftOverInTheDishQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES
            )
            @ParameterLayout(
                    describedAs = "0=non fat during cooking subgroup\n"
                                    + "1= fat during cooking subgroup"
            )
            FatDuringCookingSubgroupQ fatDuringCookingSubgroupQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES
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
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodSubgroup} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable FoodSubgroup",
            cssClassFa = "skull red"
    )
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
