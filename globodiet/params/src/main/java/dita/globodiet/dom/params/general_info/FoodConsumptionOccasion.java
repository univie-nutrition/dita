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
package dita.globodiet.dom.params.general_info;

import dita.commons.services.lookup.HasSecondaryKey;
import dita.commons.services.lookup.ISecondaryKey;
import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
import java.lang.Class;
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
import org.apache.causeway.applib.annotation.DependentDefaultsPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Food Consumption Occasion
 */
@Named("dita.globodiet.params.general_info.FoodConsumptionOccasion")
@DomainObject
@DomainObjectLayout(
        describedAs = "Food Consumption Occasion",
        cssClassFa = "solid user-clock"
)
@PersistenceCapable(
        table = "FCO"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FoodConsumptionOccasion implements HasSecondaryKey<FoodConsumptionOccasion> {
    /**
     * Food Consumption Occasion code
     */
    @Property
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Food Consumption Occasion code\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * FCO long label (text displayed on screen)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "FCO long label (text displayed on screen)\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String textDisplayedOnScreen;

    /**
     * FCO type: if =1 the FCO can be selected several times (e.g. During morning)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "FCO type: if =1 the FCO can be selected several times (e.g. During morning)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_MODE",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String mode;

    /**
     * FCO short label to identify easily the FCO
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "FCO short label to identify easily the FCO\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_SNAME",
            allowsNull = "false",
            length = 50
    )
    @Getter
    @Setter
    private String shortLabelToIdentifyEasily;

    /**
     * 0=non main FCO
     * 1=main FCO (to be displayed in nutrient check screen)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "0=non main FCO\n"
                            + "1=main FCO (to be displayed in nutrient check screen)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_PRINCIPAL",
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
    private DisplayInNutrientCheckScreenQ displayInNutrientCheckScreenQ;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", textDisplayedOnScreen, code);
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
    public enum DisplayInNutrientCheckScreenQ {
        /**
         * non main FCO
         */
        SECONDARY(0, "secondary"),

        /**
         * main FCO (to be displayed in nutrient check screen)
         */
        PRIMARY(1, "primary");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link FoodConsumptionOccasion}
     */
    @Named("dita.globodiet.params.general_info.FoodConsumptionOccasion.Manager")
    @DomainObjectLayout(
            describedAs = "Food Consumption Occasion",
            cssClassFa = "solid user-clock"
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
            return "Manage Food Consumption Occasion";
        }

        @Collection
        public final List<FoodConsumptionOccasion> getListOfFoodConsumptionOccasion() {
            return searchService.search(FoodConsumptionOccasion.class, FoodConsumptionOccasion::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link FoodConsumptionOccasion}
     * @param code Food Consumption Occasion code
     * @param textDisplayedOnScreen FCO long label (text displayed on screen)
     * @param mode FCO type: if =1 the FCO can be selected several times (e.g. During morning)
     * @param shortLabelToIdentifyEasily FCO short label to identify easily the FCO
     * @param displayInNutrientCheckScreenQ 0=non main FCO
     * 1=main FCO (to be displayed in nutrient check screen)
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Food Consumption Occasion code"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "FCO long label (text displayed on screen)"
            )
            String textDisplayedOnScreen,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "FCO type: if =1 the FCO can be selected several times (e.g. During morning)"
            )
            String mode,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "FCO short label to identify easily the FCO"
            )
            String shortLabelToIdentifyEasily,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=non main FCO\n"
                                    + "1=main FCO (to be displayed in nutrient check screen)"
            )
            DisplayInNutrientCheckScreenQ displayInNutrientCheckScreenQ) {
    }

    /**
     * SecondaryKey for @{link FoodConsumptionOccasion}
     * @param code Food Consumption Occasion code
     */
    public final record SecondaryKey(
            String code) implements ISecondaryKey<FoodConsumptionOccasion> {
        @Override
        public Class<FoodConsumptionOccasion> correspondingClass() {
            return FoodConsumptionOccasion.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodConsumptionOccasion} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable FoodConsumptionOccasion",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends FoodConsumptionOccasion implements ViewModel {
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
