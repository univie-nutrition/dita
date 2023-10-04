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
 * Descriptor per facet
 */
@Named("dita.globodiet.params.recipe_description.RecipeDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Descriptor per facet"
)
@PersistenceCapable(
        table = "R_DESCFACE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeDescriptor implements HasSecondaryKey<RecipeDescriptor> {
    /**
     * Facet code for recipes
     */
    @Property
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Facet code for recipes",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeFacetCode;

    /**
     * Descriptor code for recipes
     */
    @Property
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Descriptor code for recipes",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_CODE",
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
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Descriptor name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Only for facet recipe production:
     * 0=not homemade descriptor
     * 1=Homemade descriptor
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Only for facet recipe production:\n"
                            + "0=not homemade descriptor\n"
                            + "1=Homemade descriptor",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_TYPE",
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
    private HomemadeOrNot homemadeOrNot;

    /**
     * Only for facet known/unknown: 1=unknown 2=known
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Only for facet known/unknown: 1=unknown 2=known",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_KNOWN",
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
    private KnownOrUnknown knownOrUnknown;

    /**
     * Descriptor with type='other' : 1=yes 0=no
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Descriptor with type='other' : 1=yes 0=no",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_OTHER",
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
    private YesOrNo yesOrNo;

    /**
     * 0=not single descriptor
     * 1=single descriptor
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "0=not single descriptor\n"
                            + "1=single descriptor",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_SINGLE",
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
    private SingleOrNot singleOrNot;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s)", name, recipeFacetCode, code);
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getRecipeFacetCode(), getCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getRecipeFacetCode(), getCode())));
    }

    @RequiredArgsConstructor
    public enum HomemadeOrNot {
        /**
         * no description
         */
        NOT_HOMEMADE(0, "not homemade"),

        /**
         * no description
         */
        HOMEMADE(1, "Homemade");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum KnownOrUnknown {
        /**
         * Not a facet
         */
        DOES_NOT_APPLY(0, "does not apply"),

        /**
         * no description
         */
        FACET_UNKNOWN(1, "Facet unknown"),

        /**
         * no description
         */
        FACET_KNOWN(2, "Facet known");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum YesOrNo {
        /**
         * no description
         */
        NO(0, "no"),

        /**
         * no description
         */
        YES(1, "yes");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum SingleOrNot {
        /**
         * no description
         */
        NOT_SINGLE_DESCRIPTOR(0, "not single descriptor"),

        /**
         * no description
         */
        SINGLE_DESCRIPTOR(1, "single descriptor");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link RecipeDescriptor}
     */
    @Named("dita.globodiet.params.recipe_description.RecipeDescriptor.Manager")
    @DomainObjectLayout(
            describedAs = "Descriptor per facet"
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
            return "Manage Recipe Descriptor";
        }

        @Collection
        public final List<RecipeDescriptor> getListOfRecipeDescriptor() {
            return searchService.search(RecipeDescriptor.class, RecipeDescriptor::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link RecipeDescriptor}
     * @param recipeFacet Facet code for recipes
     * @param code Descriptor code for recipes
     * @param name Descriptor name
     * @param homemadeOrNot Only for facet recipe production:
     * 0=not homemade descriptor
     * 1=Homemade descriptor
     * @param knownOrUnknown Only for facet known/unknown: 1=unknown 2=known
     * @param yesOrNo Descriptor with type='other' : 1=yes 0=no
     * @param singleOrNot 0=not single descriptor
     * 1=single descriptor
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet code for recipes"
            )
            RecipeFacet recipeFacet,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptor code for recipes"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptor name"
            )
            String name,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Only for facet recipe production:\n"
                                    + "0=not homemade descriptor\n"
                                    + "1=Homemade descriptor"
            )
            HomemadeOrNot homemadeOrNot,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Only for facet known/unknown: 1=unknown 2=known"
            )
            KnownOrUnknown knownOrUnknown,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptor with type='other' : 1=yes 0=no"
            )
            YesOrNo yesOrNo,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=not single descriptor\n"
                                    + "1=single descriptor"
            )
            SingleOrNot singleOrNot) {
    }

    /**
     * SecondaryKey for @{link RecipeDescriptor}
     * @param recipeFacetCode Facet code for recipes
     * @param code Descriptor code for recipes
     */
    public final record SecondaryKey(
            String recipeFacetCode,
            String code) implements ISecondaryKey<RecipeDescriptor> {
        @Override
        public Class<RecipeDescriptor> correspondingClass() {
            return RecipeDescriptor.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link RecipeDescriptor} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable RecipeDescriptor",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends RecipeDescriptor implements ViewModel {
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
