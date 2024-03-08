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
package dita.globodiet.dom.params.food_descript;

import dita.commons.services.lookup.Cloneable;
import dita.commons.services.lookup.HasSecondaryKey;
import dita.commons.services.lookup.ISecondaryKey;
import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
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
 * Descriptor for food facets (not recipe facets)
 */
@Named("dita.globodiet.params.food_descript.FoodDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Descriptor for food facets (not recipe facets)",
        cssClassFa = "solid utensils .food-color,\n"
                        + "solid tag .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "DESCFACE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_FoodDescriptor",
        members = {"facetCode", "code"}
)
public class FoodDescriptor implements Cloneable<FoodDescriptor>, HasSecondaryKey<FoodDescriptor> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Facet code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Facet code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String facetCode;

    /**
     * Descriptor code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Descriptor code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESCR_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Descriptor name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Descriptor name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESCR_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * 0=default without consequences in the algorithms regarding cooking
     * 1=raw (not cooked)
     * 2=asks the question 'fat used during cooking?'
     * 3=found in austrian data for 'frittiert' - invalid enum constant?
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "0=default without consequences in the algorithms regarding cooking\n"
                            + "1=raw (not cooked)\n"
                            + "2=asks the question 'fat used during cooking?'\n"
                            + "3=found in austrian data for 'frittiert' - invalid enum constant?",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESC_COOK",
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
    private Cooking cooking;

    /**
     * 0=Multiple choice (allowed)
     * 1=Single (exclusive) choice
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "0=Multiple choice (allowed)\n"
                            + "1=Single (exclusive) choice",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESC_SINGLE",
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
    private Choice choice;

    /**
     * 0=Regular choice
     * 1=Choice with additional text as provided by the interviewer (other: [...])
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "0=Regular choice\n"
                            + "1=Choice with additional text as provided by the interviewer (other: [...])",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESC_OTHER",
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
    private OtherQ otherQ;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s)", name, facetCode, code);
    }

    @Override
    public String toString() {
        return "FoodDescriptor(" + "facetCode=" + getFacetCode() + ","
         +"code=" + getCode() + ","
         +"name=" + getName() + ","
         +"cooking=" + getCooking() + ","
         +"choice=" + getChoice() + ","
         +"otherQ=" + getOtherQ() + ")";
    }

    @Programmatic
    @Override
    public FoodDescriptor copy() {
        var copy = repositoryService.detachedEntity(new FoodDescriptor());
        copy.setFacetCode(getFacetCode());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setCooking(getCooking());
        copy.setChoice(getChoice());
        copy.setOtherQ(getOtherQ());
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
    public FoodDescriptor.Manager getNavigableParent() {
        return new FoodDescriptor.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getFacetCode(), getCode());
    }

    @RequiredArgsConstructor
    public enum Cooking {
        /**
         * without consequences in the algorithms regarding cooking
         */
        DEFAULT(0, "Default"),

        /**
         * not cooked
         */
        RAW(1, "Raw"),

        /**
         * Asks the question 'fat used during cooking?'
         */
        FATUSEDQ(2, "FatUsedQ"),

        /**
         * found in austrian data for 'frittiert' - invalid enum constant?
         */
        DEEPFRIED(3, "Deepfried");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum Choice {
        /**
         * Multiple choice (allowed)
         */
        MULTI(0, "Multi"),

        /**
         * Single (exclusive) choice
         */
        SINGLE(1, "Single");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum OtherQ {
        /**
         * Regular choice
         */
        REGULAR(0, "Regular"),

        /**
         * Choice with additional text as provided by the interviewer (other: [...])
         */
        OTHER(1, "Other");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link FoodDescriptor}
     */
    @Named("dita.globodiet.params.food_descript.FoodDescriptor.Manager")
    @DomainObjectLayout(
            describedAs = "Descriptor for food facets (not recipe facets)",
            cssClassFa = "solid utensils .food-color,\n"
                            + "solid tag .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Food Descriptor";
        }

        @Collection
        public final List<FoodDescriptor> getListOfFoodDescriptor() {
            return searchService.search(FoodDescriptor.class, FoodDescriptor::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link FoodDescriptor}
     * @param facet Facet code
     * @param code Descriptor code
     * @param name Descriptor name
     * @param cooking 0=default without consequences in the algorithms regarding cooking
     * 1=raw (not cooked)
     * 2=asks the question 'fat used during cooking?'
     * 3=found in austrian data for 'frittiert' - invalid enum constant?
     * @param choice 0=Multiple choice (allowed)
     * 1=Single (exclusive) choice
     * @param otherQ 0=Regular choice
     * 1=Choice with additional text as provided by the interviewer (other: [...])
     */
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet code"
            )
            FoodFacet facet,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptor code"
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptor name"
            )
            String name,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=default without consequences in the algorithms regarding cooking\n"
                                    + "1=raw (not cooked)\n"
                                    + "2=asks the question 'fat used during cooking?'\n"
                                    + "3=found in austrian data for 'frittiert' - invalid enum constant?"
            )
            Cooking cooking,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=Multiple choice (allowed)\n"
                                    + "1=Single (exclusive) choice"
            )
            Choice choice,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=Regular choice\n"
                                    + "1=Choice with additional text as provided by the interviewer (other: [...])"
            )
            OtherQ otherQ) {
    }

    /**
     * SecondaryKey for @{link FoodDescriptor}
     * @param facetCode Facet code
     * @param code Descriptor code
     */
    public final record SecondaryKey(
            String facetCode,
            String code) implements ISecondaryKey<FoodDescriptor> {
        @Override
        public Class<FoodDescriptor> correspondingClass() {
            return FoodDescriptor.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodDescriptor} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable FoodDescriptor",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.food_descript.FoodDescriptor.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends FoodDescriptor implements ViewModel {
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
