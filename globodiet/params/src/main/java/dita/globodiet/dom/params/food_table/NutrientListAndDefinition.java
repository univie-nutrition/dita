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
package dita.globodiet.dom.params.food_table;

import dita.commons.services.lookup.Cloneable;
import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
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

/**
 * Nutrient list and definition
 */
@Named("dita.globodiet.params.food_table.NutrientListAndDefinition")
@DomainObject
@DomainObjectLayout(
        describedAs = "Nutrient list and definition"
)
@PersistenceCapable(
        table = "NUTRIENT"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class NutrientListAndDefinition implements Cloneable<NutrientListAndDefinition> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Nutrient code
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Nutrient code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_CODE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int nutrientCode;

    /**
     * Nutrient Name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Nutrient Name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String nutrientName;

    /**
     * Nutrient unit (e.g. kcal, g, mg…)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Nutrient unit (e.g. kcal, g, mg…)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_UNIT",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String nutrientUnit;

    /**
     * 0=not displayed in the 'nutrient checks' screen
     * 1=displayed in the 'nutrient checks' screen
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "0=not displayed in the 'nutrient checks' screen\n"
                            + "1=displayed in the 'nutrient checks' screen",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_DISPLAY",
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
    private DisplayInTheNutrientChecksScreen displayInTheNutrientChecksScreen;

    /**
     * Comment on nutrient
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Comment on nutrient",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "COMMENT",
            allowsNull = "true",
            length = 4096
    )
    @Getter
    @Setter
    private String commentOnNutrient;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "NutrientListAndDefinition(" + "nutrientCode=" + getNutrientCode() + ","
         +"nutrientName=" + getNutrientName() + ","
         +"nutrientUnit=" + getNutrientUnit() + ","
         +"displayInTheNutrientChecksScreen=" + getDisplayInTheNutrientChecksScreen() + ","
         +"commentOnNutrient=" + getCommentOnNutrient() + ")";
    }

    @Programmatic
    @Override
    public NutrientListAndDefinition copy() {
        var copy = repositoryService.detachedEntity(new NutrientListAndDefinition());
        copy.setNutrientCode(getNutrientCode());
        copy.setNutrientName(getNutrientName());
        copy.setNutrientUnit(getNutrientUnit());
        copy.setDisplayInTheNutrientChecksScreen(getDisplayInTheNutrientChecksScreen());
        copy.setCommentOnNutrient(getCommentOnNutrient());
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
    public NutrientListAndDefinition.Manager getNavigableParent() {
        return new NutrientListAndDefinition.Manager(searchService, "");
    }

    @RequiredArgsConstructor
    public enum DisplayInTheNutrientChecksScreen {
        /**
         * not displayed in the 'nutrient checks' screen
         */
        NO(0, "No"),

        /**
         * displayed in the 'nutrient checks' screen
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
     * Manager Viewmodel for @{link NutrientListAndDefinition}
     */
    @Named("dita.globodiet.params.food_table.NutrientListAndDefinition.Manager")
    @DomainObjectLayout(
            describedAs = "Nutrient list and definition"
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
            return "Manage Nutrient List And Definition";
        }

        @Collection
        public final List<NutrientListAndDefinition> getListOfNutrientListAndDefinition() {
            return searchService.search(NutrientListAndDefinition.class, NutrientListAndDefinition::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
