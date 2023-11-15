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
package dita.globodiet.dom.params.setting;

import dita.commons.services.lookup.Cloneable;
import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Integer;
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
 * Definition of recipe pathway (available for each recipe type).
 */
@Named("dita.globodiet.params.setting.RecipeTypePathway")
@DomainObject
@DomainObjectLayout(
        describedAs = "Definition of recipe pathway (available for each recipe type).",
        cssClassFa = "solid person-walking-arrow-right mediumblue"
)
@PersistenceCapable(
        table = "RCP_PATH"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeTypePathway implements Cloneable<RecipeTypePathway> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Type of recipe:
     * 1.1=Open – Known
     * 1.2=Open – Unknown
     * 1.3=Open with brand
     * 2.1=Closed
     * 2.2=Closed with brand
     * 3.0=Commercial
     * 4.1=New – Known
     * 4.2=New – Unknown
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Type of recipe:\n"
                            + "1.1=Open – Known\n"
                            + "1.2=Open – Unknown\n"
                            + "1.3=Open with brand\n"
                            + "2.1=Closed\n"
                            + "2.2=Closed with brand\n"
                            + "3.0=Commercial\n"
                            + "4.1=New – Known\n"
                            + "4.2=New – Unknown",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_TYPE",
            allowsNull = "false",
            length = 3
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
    private TypeOfRecipe typeOfRecipe;

    /**
     * 0 = The ingredient window is not displayed
     * 1 = The ingredient window is displayed
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "0 = The ingredient window is not displayed\n"
                            + "1 = The ingredient window is displayed",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_ING",
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
    private IngredientWindowIsDisplayedQ ingredientWindowIsDisplayedQ;

    /**
     * Functions allocated in NEW interview mode when the ingredient window is displayed (R_ING=1):
     * 1 = S - Substitute
     * 2 = SAD - Substitute, Add & Delete
     * 3 = SADQ - Substitute, Add, Delete & Quantify
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Functions allocated in NEW interview mode when the ingredient window is displayed (R_ING=1):\n"
                            + "1 = S - Substitute\n"
                            + "2 = SAD - Substitute, Add & Delete\n"
                            + "3 = SADQ - Substitute, Add, Delete & Quantify",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "N_IFUNCTION",
            allowsNull = "true"
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
    private FunctionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed functionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed;

    /**
     * Functions allocated in EDIT interview mode when the ingredient window is displayed (R_ING=1):
     * 1 = S - Substitute
     * 2 = SAD - Substitute, Add & Delete
     * 3 = SADQ - Substitute, Add, Delete & Quantify
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Functions allocated in EDIT interview mode when the ingredient window is displayed (R_ING=1):\n"
                            + "1 = S - Substitute\n"
                            + "2 = SAD - Substitute, Add & Delete\n"
                            + "3 = SADQ - Substitute, Add, Delete & Quantify",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "E_IFUNCTION",
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
    private FunctionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed functionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed;

    /**
     * Display of the automatic note window:
     * 0 = No display of note window
     * 1 = Display of note window
     * 2 = Display of note window only for Add & Delete functions
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Display of the automatic note window:\n"
                            + "0 = No display of note window\n"
                            + "1 = Display of note window\n"
                            + "2 = Display of note window only for Add & Delete functions",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "D_NOTES",
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
    private DisplayOfTheAutomaticNoteWindow displayOfTheAutomaticNoteWindow;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "RecipeTypePathway(" + "typeOfRecipe=" + getTypeOfRecipe() + ","
         +"ingredientWindowIsDisplayedQ=" + getIngredientWindowIsDisplayedQ() + ","
         +"functionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed=" + getFunctionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed() + ","
         +"functionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed=" + getFunctionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed() + ","
         +"displayOfTheAutomaticNoteWindow=" + getDisplayOfTheAutomaticNoteWindow() + ")";
    }

    @Programmatic
    @Override
    public RecipeTypePathway copy() {
        var copy = repositoryService.detachedEntity(new RecipeTypePathway());
        copy.setTypeOfRecipe(getTypeOfRecipe());
        copy.setIngredientWindowIsDisplayedQ(getIngredientWindowIsDisplayedQ());
        copy.setFunctionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed(getFunctionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed());
        copy.setFunctionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed(getFunctionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed());
        copy.setDisplayOfTheAutomaticNoteWindow(getDisplayOfTheAutomaticNoteWindow());
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
    public RecipeTypePathway.Manager getNavigableParent() {
        return new RecipeTypePathway.Manager(searchService, "");
    }

    @RequiredArgsConstructor
    public enum TypeOfRecipe {
        /**
         * no description
         */
        OPEN_KNOWN("1.1", "Open–Known"),

        /**
         * no description
         */
        OPEN_UNKNOWN("1.2", "Open–Unknown"),

        /**
         * no description
         */
        OPEN_WITH_BRAND("1.3", "Open with brand"),

        /**
         * no description
         */
        CLOSED("2.1", "Closed"),

        /**
         * no description
         */
        CLOSED_WITH_BRAND("2.2", "Closed with brand"),

        /**
         * no description
         */
        COMMERCIAL("3.0", "Commercial"),

        /**
         * no description
         */
        NEW_KNOWN("4.1", "New–Known"),

        /**
         * no description
         */
        NEW_UNKNOWN("4.2", "New–Unknown");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum IngredientWindowIsDisplayedQ {
        /**
         * The ingredient window is not displayed
         */
        HIDDEN(0, "hidden"),

        /**
         * The ingredient window is displayed
         */
        DISPLAYED(1, "displayed");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum FunctionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed {
        /**
         * Substitute
         */
        S(1, "S"),

        /**
         * Substitute, Add & Delete
         */
        SAD(2, "SAD"),

        /**
         * Substitute, Add, Delete & Quantify
         */
        SADQ(3, "SADQ");

        @Getter
        private final Integer matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum FunctionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed {
        /**
         * Substitute
         */
        S(1, "S"),

        /**
         * Substitute, Add & Delete
         */
        SAD(2, "SAD"),

        /**
         * Substitute, Add, Delete & Quantify
         */
        SADQ(3, "SADQ");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum DisplayOfTheAutomaticNoteWindow {
        /**
         * No display of note window
         */
        HIDDEN(0, "hidden"),

        /**
         * Display of note window
         */
        DISPLAYED(1, "displayed"),

        /**
         * Display of note window only for Add & Delete functions
         */
        DISPLAYED_ONLY_FOR_ADD_DELETE(2, "displayed only for add/delete");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link RecipeTypePathway}
     */
    @Named("dita.globodiet.params.setting.RecipeTypePathway.Manager")
    @DomainObjectLayout(
            describedAs = "Definition of recipe pathway (available for each recipe type).",
            cssClassFa = "solid person-walking-arrow-right mediumblue"
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
            return "Manage Recipe Type Pathway";
        }

        @Collection
        public final List<RecipeTypePathway> getListOfRecipeTypePathway() {
            return searchService.search(RecipeTypePathway.class, RecipeTypePathway::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
