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
import lombok.Value;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Dietary supplement facet
 */
@Named("dita.globodiet.params.supplement.DietarySupplementFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement facet"
)
@PersistenceCapable(
        table = "DS_FACET"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class DietarySupplementFacet implements HasSecondaryKey<DietarySupplementFacet> {
    /**
     * Facet code for Dietary Supplement
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet code for Dietary Supplement<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Facet name for Dietary Supplement
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Facet name for Dietary Supplement<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSFACET_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * To identify the mandatory facet used for quantification: 1=yes, 0=no.
     * Only 1 facet (physical state) is used for quantification.
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "To identify the mandatory facet used for quantification: 1=yes, 0=no.<br>Only 1 facet (physical state) is used for quantification.<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSFACET_QUANT",
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
    private MandatoryFacetUsedForQuantificationQ mandatoryFacetUsedForQuantificationQ;

    /**
     * Facet with Mono or Multi selection of descriptors
     * 0=mono,
     * 1=multi
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Facet with Mono or Multi selection of descriptors<br>0=mono,<br>1=multi<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSFACET_TYPE",
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
    private SingleOrMultiSelectionOfDescriptorsQ singleOrMultiSelectionOfDescriptorsQ;

    /**
     * For maintenance: Main facets to be attributed to all supplements: 1=yes, 0=no.
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "For maintenance: Main facets to be attributed to all supplements: 1=yes, 0=no.<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSFACET_MAIN",
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
    private AttributedToAllSupplementsQ attributedToAllSupplementsQ;

    /**
     * Order to ask the facet (first, second...)
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Order to ask the facet (first, second...)<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSFACET_ORDER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int orderToAsk;

    /**
     * Label on how to ask the facet question
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Label on how to ask the facet question<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DSFACET_QUEST",
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
    public enum MandatoryFacetUsedForQuantificationQ {
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
    public enum SingleOrMultiSelectionOfDescriptorsQ {
        /**
         * no description
         */
        MONO(0, "mono"),

        /**
         * no description
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
    public enum AttributedToAllSupplementsQ {
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

    /**
     * SecondaryKey for @{link DietarySupplementFacet}
     */
    @Value
    public static final class SecondaryKey implements ISecondaryKey<DietarySupplementFacet> {
        private static final long serialVersionUID = 1;

        /**
         * Facet code for Dietary Supplement
         */
        private String code;

        @Override
        public Class<DietarySupplementFacet> correspondingClass() {
            return DietarySupplementFacet.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link DietarySupplementFacet} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable DietarySupplementFacet",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends DietarySupplementFacet implements ViewModel {
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
