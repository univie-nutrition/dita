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
package dita.globodiet.params.supplement;

import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
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
import org.apache.causeway.applib.annotation.Nature;
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
 * Dietary supplement facet
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.supplement.DietarySupplementFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement facet",
        cssClassFa = "solid tablets .supplement-color,\n"
                        + "solid swatchbook .supplement-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "DS_FACET"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_DietarySupplementFacet",
        members = {"code"}
)
public class DietarySupplementFacet implements Cloneable<DietarySupplementFacet>, HasSecondaryKey<DietarySupplementFacet> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Facet code for Dietary Supplement
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Facet code for Dietary Supplement",
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
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Facet name for Dietary Supplement",
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
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "To identify the mandatory facet used for quantification: 1=yes, 0=no.\n"
                            + "Only 1 facet (physical state) is used for quantification.",
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
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Facet with Mono or Multi selection of descriptors\n"
                            + "0=mono,\n"
                            + "1=multi",
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
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "For maintenance: Main facets to be attributed to all supplements: 1=yes, 0=no.",
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
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Order to ask the facet (first, second...)",
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
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Label on how to ask the facet question",
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

    @Override
    public String toString() {
        return "DietarySupplementFacet(" + "code=" + getCode() + ","
         +"name=" + getName() + ","
         +"mandatoryFacetUsedForQuantificationQ=" + getMandatoryFacetUsedForQuantificationQ() + ","
         +"singleOrMultiSelectionOfDescriptorsQ=" + getSingleOrMultiSelectionOfDescriptorsQ() + ","
         +"attributedToAllSupplementsQ=" + getAttributedToAllSupplementsQ() + ","
         +"orderToAsk=" + getOrderToAsk() + ","
         +"labelOnHowToAskTheFacetQuestion=" + getLabelOnHowToAskTheFacetQuestion() + ")";
    }

    @Programmatic
    @Override
    public DietarySupplementFacet copy() {
        var copy = repositoryService.detachedEntity(new DietarySupplementFacet());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setMandatoryFacetUsedForQuantificationQ(getMandatoryFacetUsedForQuantificationQ());
        copy.setSingleOrMultiSelectionOfDescriptorsQ(getSingleOrMultiSelectionOfDescriptorsQ());
        copy.setAttributedToAllSupplementsQ(getAttributedToAllSupplementsQ());
        copy.setOrderToAsk(getOrderToAsk());
        copy.setLabelOnHowToAskTheFacetQuestion(getLabelOnHowToAskTheFacetQuestion());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @NotPersistent
    public DietarySupplementFacet.Manager getNavigableParent() {
        return new DietarySupplementFacet.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
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
     * Manager Viewmodel for @{link DietarySupplementFacet}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.supplement.DietarySupplementFacet.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Dietary supplement facet",
            cssClassFa = "solid tablets .supplement-color,\n"
                            + "solid swatchbook .supplement-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Dietary Supplement Facet";
        }

        @Collection
        public final List<DietarySupplementFacet> getListOfDietarySupplementFacet() {
            return searchService.search(DietarySupplementFacet.class, DietarySupplementFacet::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link DietarySupplementFacet}
     * @param code Facet code for Dietary Supplement
     * @param name Facet name for Dietary Supplement
     * @param mandatoryFacetUsedForQuantificationQ To identify the mandatory facet used for quantification: 1=yes, 0=no.
     * Only 1 facet (physical state) is used for quantification.
     * @param singleOrMultiSelectionOfDescriptorsQ Facet with Mono or Multi selection of descriptors
     * 0=mono,
     * 1=multi
     * @param attributedToAllSupplementsQ For maintenance: Main facets to be attributed to all supplements: 1=yes, 0=no.
     * @param orderToAsk Order to ask the facet (first, second...)
     * @param labelOnHowToAskTheFacetQuestion Label on how to ask the facet question
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet code for Dietary Supplement"
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet name for Dietary Supplement"
            )
            String name,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "To identify the mandatory facet used for quantification: 1=yes, 0=no.\n"
                                    + "Only 1 facet (physical state) is used for quantification."
            )
            MandatoryFacetUsedForQuantificationQ mandatoryFacetUsedForQuantificationQ,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet with Mono or Multi selection of descriptors\n"
                                    + "0=mono,\n"
                                    + "1=multi"
            )
            SingleOrMultiSelectionOfDescriptorsQ singleOrMultiSelectionOfDescriptorsQ,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "For maintenance: Main facets to be attributed to all supplements: 1=yes, 0=no."
            )
            AttributedToAllSupplementsQ attributedToAllSupplementsQ,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Order to ask the facet (first, second...)"
            )
            int orderToAsk,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Label on how to ask the facet question"
            )
            String labelOnHowToAskTheFacetQuestion) {
    }

    /**
     * SecondaryKey for @{link DietarySupplementFacet}
     * @param code Facet code for Dietary Supplement
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(String code) implements ISecondaryKey<DietarySupplementFacet> {
        @Override
        public Class<DietarySupplementFacet> correspondingClass() {
            return DietarySupplementFacet.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link DietarySupplementFacet} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable DietarySupplementFacet",
            describedAs = "Unresolvable DietarySupplementFacet",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.supplement.DietarySupplementFacet.Unresolvable")
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
