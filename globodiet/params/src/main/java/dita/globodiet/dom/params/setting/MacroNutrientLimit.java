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
package dita.globodiet.dom.params.setting;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Double;
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
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.causewaystuff.companion.applib.services.lookup.Cloneable;
import org.causewaystuff.companion.applib.services.search.SearchService;

/**
 * Minimum and maximum value for macro-nutrient
 */
@Generated("org.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.setting.MacroNutrientLimit")
@DomainObject
@DomainObjectLayout(
        describedAs = "Minimum and maximum value for macro-nutrient"
)
@PersistenceCapable(
        table = "NUTLIMIT"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class MacroNutrientLimit implements Cloneable<MacroNutrientLimit> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Name of nutrient
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Name of nutrient",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 50
    )
    @Getter
    @Setter
    private String nameOfNutrient;

    /**
     * Minimum value
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Minimum value",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "MIN",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double minimumValue;

    /**
     * Maximum value
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Maximum value",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "MAX",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double maximumValue;

    /**
     * Unit (g, kcal or blank)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Unit (g, kcal or blank)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "UNIT",
            allowsNull = "true",
            length = 50
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
    private Unit unit;

    /**
     * 1=Man,
     * 2=Woman
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "1=Man,\n"
                            + "2=Woman",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SEX",
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
    private Sex sex;

    /**
     * PAL value
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "PAL value",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "VAL",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double palValue;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "MacroNutrientLimit(" + "nameOfNutrient=" + getNameOfNutrient() + ","
         +"minimumValue=" + getMinimumValue() + ","
         +"maximumValue=" + getMaximumValue() + ","
         +"unit=" + getUnit() + ","
         +"sex=" + getSex() + ","
         +"palValue=" + getPalValue() + ")";
    }

    @Programmatic
    @Override
    public MacroNutrientLimit copy() {
        var copy = repositoryService.detachedEntity(new MacroNutrientLimit());
        copy.setNameOfNutrient(getNameOfNutrient());
        copy.setMinimumValue(getMinimumValue());
        copy.setMaximumValue(getMaximumValue());
        copy.setUnit(getUnit());
        copy.setSex(getSex());
        copy.setPalValue(getPalValue());
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
    public MacroNutrientLimit.Manager getNavigableParent() {
        return new MacroNutrientLimit.Manager(searchService, "");
    }

    @RequiredArgsConstructor
    public enum Unit {
        /**
         * no description
         */
        GRAMS("g", "Grams"),

        /**
         * no description
         */
        KCAL("Kcal", "Kcal");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum Sex {
        /**
         * no description
         */
        MALE("1", "male"),

        /**
         * no description
         */
        FEMALE("2", "female");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link MacroNutrientLimit}
     */
    @Named("dita.globodiet.params.setting.MacroNutrientLimit.Manager")
    @DomainObjectLayout(
            describedAs = "Minimum and maximum value for macro-nutrient"
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
            return "Manage Macro Nutrient Limit";
        }

        @Collection
        public final List<MacroNutrientLimit> getListOfMacroNutrientLimit() {
            return searchService.search(MacroNutrientLimit.class, MacroNutrientLimit::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
