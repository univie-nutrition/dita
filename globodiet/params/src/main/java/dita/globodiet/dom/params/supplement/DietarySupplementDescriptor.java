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
package dita.globodiet.dom.params.supplement;

import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
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
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Dietary supplement descriptor
 */
@Named("dita.globodiet.params.supplement.DietarySupplementDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement descriptor"
)
@PersistenceCapable(
        table = "DS_DESCFACE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class DietarySupplementDescriptor {
    /**
     * Descriptor code
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Descriptor code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSDESCR_CODE",
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
            sequence = "2",
            describedAs = "Descriptor name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSDESCR_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Facet code
     */
    @Property
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Facet code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "DSFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String facetCode;

    /**
     * Only for the facet with Dsfacet_type=1,
     * for the supplement quantification If HHM=1 Then HHM method is proposed Else No HHM=0
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Only for the facet with Dsfacet_type=1,\n"
                            + "for the supplement quantification If HHM=1 Then HHM method is proposed Else No HHM=0",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSDESCR_HHM",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int householdMeasuresProposedQ;

    /**
     * Default Descriptor.
     * When this facet is displayed, the cursor has to be focussed on the default descriptor (only 1 defaulty):
     * 1=default,
     * 0=other
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Default Descriptor.\n"
                            + "When this facet is displayed, the cursor has to be focussed on the default descriptor (only 1 defaulty):\n"
                            + "1=default,\n"
                            + "0=other",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSDESCR_DEFAULT",
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
    private DefaultDescriptor defaultDescriptor;

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
            name = "DSDESCR_OTHER",
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

    /**
     * 0=not single descriptor 1=single descriptor
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "0=not single descriptor 1=single descriptor",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSDESCR_SINGLE",
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
    private SingleDescriptorQ singleDescriptorQ;

    /**
     * Display order (1=first, 2=second, …)
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Display order (1=first, 2=second, …)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSDESCR_ORDER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int displayOrder;

    /**
     * Not in name flag
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "9",
            describedAs = "Not in name flag",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NOTINNAME",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int notInName;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
    }

    @RequiredArgsConstructor
    public enum DefaultDescriptor {
        /**
         * no description
         */
        DEFAULT(1, "default"),

        /**
         * no description
         */
        OTHER(0, "other");

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
    public enum SingleDescriptorQ {
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
     * Manager Viewmodel for @{link DietarySupplementDescriptor}
     */
    @Named("dita.globodiet.params.supplement.DietarySupplementDescriptor.Manager")
    @DomainObjectLayout(
            describedAs = "Dietary supplement descriptor"
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
            return "Manage Dietary Supplement Descriptor";
        }

        @Collection
        public final List<DietarySupplementDescriptor> getListOfDietarySupplementDescriptor() {
            return searchService.search(DietarySupplementDescriptor.class, DietarySupplementDescriptor::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
