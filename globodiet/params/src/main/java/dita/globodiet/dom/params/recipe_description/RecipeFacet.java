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
package dita.globodiet.dom.params.recipe_description;

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
 * Facet for Recipe
 */
@Named("dita.globodiet.params.recipe_description.RecipeFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet for Recipe"
)
@PersistenceCapable(
        table = "R_FACET"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeFacet implements HasSecondaryKey<RecipeFacet> {
    /**
     * Facet code for recipes
     */
    @Property
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Facet code for recipes\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Facet name
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Facet name\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Facet text (text to show on the screen describing the facet)
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Facet text (text to show on the screen describing the facet)\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_TEXT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String textToShowOnTheScreenDescribingTheFacet;

    /**
     * 0=Standard facets with descriptors available in R_Descface table
     * 1=Facets with descriptors available in RBrand table
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "0=Standard facets with descriptors available in R_Descface table\n"
                            + "1=Facets with descriptors available in RBrand table\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_TYPE",
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
    private DescriptorsAvailableForRecipeOrBrandQ descriptorsAvailableForRecipeOrBrandQ;

    /**
     * 0 = facet with single-selection of descriptor
     * 1 = facets with multi-selection of descriptors
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "0 = facet with single-selection of descriptor\n"
                            + "1 = facets with multi-selection of descriptors\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_TYPE_S",
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
    private SingleOrMultiSelectDescriptorQ singleOrMultiSelectDescriptorQ;

    /**
     * 0 = standard facet
     * 1 = Main facet (with non modified descriptor)
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "0 = standard facet\n"
                            + "1 = Main facet (with non modified descriptor)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_MAIN",
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
    private StandardOrMainFacetQ standardOrMainFacetQ;

    /**
     * Label on how to ask the facet question
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Label on how to ask the facet question\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_QUEST",
            allowsNull = "false",
            length = 300
    )
    @Getter
    @Setter
    private String labelOnHowToAskTheFacetQuestion;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
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
    public enum DescriptorsAvailableForRecipeOrBrandQ {
        /**
         * Standard facets with descriptors available in R_Descface table
         */
        STANDARD(0, "Standard"),

        /**
         * Facets with descriptors available in RBrand table
         */
        BRAND(1, "Brand");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum SingleOrMultiSelectDescriptorQ {
        /**
         * facet with single-selection of descriptor
         */
        SINGLE(0, "single"),

        /**
         * facets with multi-selection of descriptors
         */
        MULTI(1, "multi");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum StandardOrMainFacetQ {
        /**
         * no description
         */
        STANDARD(0, "Standard"),

        /**
         *  facet (with non modified descriptor)
         */
        MAIN(1, "Main");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link RecipeFacet}
     */
    @Named("dita.globodiet.params.recipe_description.RecipeFacet.Manager")
    @DomainObjectLayout(
            describedAs = "Facet for Recipe"
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
            return "Manage Recipe Facet";
        }

        @Collection
        public final List<RecipeFacet> getListOfRecipeFacet() {
            return searchService.search(RecipeFacet.class, RecipeFacet::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link RecipeFacet}
     * @param code Facet code for recipes
     * @param name Facet name
     * @param textToShowOnTheScreenDescribingTheFacet Facet text (text to show on the screen describing the facet)
     * @param descriptorsAvailableForRecipeOrBrandQ 0=Standard facets with descriptors available in R_Descface table
     * 1=Facets with descriptors available in RBrand table
     * @param singleOrMultiSelectDescriptorQ 0 = facet with single-selection of descriptor
     * 1 = facets with multi-selection of descriptors
     * @param standardOrMainFacetQ 0 = standard facet
     * 1 = Main facet (with non modified descriptor)
     * @param labelOnHowToAskTheFacetQuestion Label on how to ask the facet question
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet code for recipes"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet name"
            )
            String name,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet text (text to show on the screen describing the facet)"
            )
            String textToShowOnTheScreenDescribingTheFacet,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=Standard facets with descriptors available in R_Descface table\n"
                                    + "1=Facets with descriptors available in RBrand table"
            )
            DescriptorsAvailableForRecipeOrBrandQ descriptorsAvailableForRecipeOrBrandQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0 = facet with single-selection of descriptor\n"
                                    + "1 = facets with multi-selection of descriptors"
            )
            SingleOrMultiSelectDescriptorQ singleOrMultiSelectDescriptorQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0 = standard facet\n"
                                    + "1 = Main facet (with non modified descriptor)"
            )
            StandardOrMainFacetQ standardOrMainFacetQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Label on how to ask the facet question"
            )
            String labelOnHowToAskTheFacetQuestion) {
    }

    /**
     * SecondaryKey for @{link RecipeFacet}
     * @param code Facet code for recipes
     */
    public final record SecondaryKey(String code) implements ISecondaryKey<RecipeFacet> {
        @Override
        public Class<RecipeFacet> correspondingClass() {
            return RecipeFacet.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link RecipeFacet} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable RecipeFacet",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends RecipeFacet implements ViewModel {
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
