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
package dita.globodiet.params.recipe_description;

import io.github.causewaystuff.companion.applib.jpa.EnumConverter;
import io.github.causewaystuff.companion.applib.jpa.EnumWithCode;
import io.github.causewaystuff.companion.applib.jpa.Persistable;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
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
 * Facet for Recipe
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.recipe_description.RecipeFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet for Recipe",
        cssClassFa = "solid stroopwafel .recipe-color,\n"
                + "solid swatchbook .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@Entity
@Table(
        name = "R_FACET",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "code"
        )
)
public class RecipeFacet implements Persistable, Cloneable<RecipeFacet>, HasSecondaryKey<RecipeFacet> {
    @Inject
    @Transient
    RepositoryService repositoryService;

    @Inject
    @Transient
    SearchService searchService;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    /**
     * Facet code for recipes
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Facet code for recipes"
    )
    @Column(
            name = "\"RFACET_CODE\"",
            nullable = false,
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Facet name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Facet name"
    )
    @Column(
            name = "\"RFACET_NAME\"",
            nullable = false,
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Facet text (text to show on the screen describing the facet)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Facet text (text to show on the screen describing the facet)"
    )
    @Column(
            name = "\"RFACET_TEXT\"",
            nullable = false,
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
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "0=Standard facets with descriptors available in R_Descface table\n"
                    + "1=Facets with descriptors available in RBrand table"
    )
    @Column(
            name = "\"RFACET_TYPE\"",
            nullable = false
    )
    @Getter
    @Setter
    @Convert(
            converter = DescriptorsAvailableForRecipeOrBrandQ.Converter.class
    )
    private DescriptorsAvailableForRecipeOrBrandQ descriptorsAvailableForRecipeOrBrandQ;

    /**
     * 0 = facet with single-selection of descriptor
     * 1 = facets with multi-selection of descriptors
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "0 = facet with single-selection of descriptor\n"
                    + "1 = facets with multi-selection of descriptors"
    )
    @Column(
            name = "\"RFACET_TYPE_S\"",
            nullable = false
    )
    @Getter
    @Setter
    @Convert(
            converter = SingleOrMultiSelectDescriptorQ.Converter.class
    )
    private SingleOrMultiSelectDescriptorQ singleOrMultiSelectDescriptorQ;

    /**
     * 0 = standard facet
     * 1 = Main facet (with non modified descriptor)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "0 = standard facet\n"
                    + "1 = Main facet (with non modified descriptor)"
    )
    @Column(
            name = "\"RFACET_MAIN\"",
            nullable = false
    )
    @Getter
    @Setter
    @Convert(
            converter = StandardOrMainFacetQ.Converter.class
    )
    private StandardOrMainFacetQ standardOrMainFacetQ;

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
            describedAs = "Label on how to ask the facet question"
    )
    @Column(
            name = "\"RFACET_QUEST\"",
            nullable = false,
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
        return "RecipeFacet(" + "code=" + getCode() + ","
         +"name=" + getName() + ","
         +"textToShowOnTheScreenDescribingTheFacet=" + getTextToShowOnTheScreenDescribingTheFacet() + ","
         +"descriptorsAvailableForRecipeOrBrandQ=" + getDescriptorsAvailableForRecipeOrBrandQ() + ","
         +"singleOrMultiSelectDescriptorQ=" + getSingleOrMultiSelectDescriptorQ() + ","
         +"standardOrMainFacetQ=" + getStandardOrMainFacetQ() + ","
         +"labelOnHowToAskTheFacetQuestion=" + getLabelOnHowToAskTheFacetQuestion() + ")";
    }

    @Programmatic
    @Override
    public RecipeFacet copy() {
        var copy = repositoryService.detachedEntity(new RecipeFacet());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setTextToShowOnTheScreenDescribingTheFacet(getTextToShowOnTheScreenDescribingTheFacet());
        copy.setDescriptorsAvailableForRecipeOrBrandQ(getDescriptorsAvailableForRecipeOrBrandQ());
        copy.setSingleOrMultiSelectDescriptorQ(getSingleOrMultiSelectDescriptorQ());
        copy.setStandardOrMainFacetQ(getStandardOrMainFacetQ());
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
    @Transient
    public RecipeFacet.Manager getNavigableParent() {
        return new RecipeFacet.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum DescriptorsAvailableForRecipeOrBrandQ implements EnumWithCode<Integer> {

        /**
         * Standard facets with descriptors available in R_Descface table
         */
        STANDARD(0, "Standard"),
        /**
         * Facets with descriptors available in RBrand table
         */
        BRAND(1, "Brand");

        private final Integer code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<DescriptorsAvailableForRecipeOrBrandQ, Integer> {
            @Override
            public DescriptorsAvailableForRecipeOrBrandQ[] values() {
                return DescriptorsAvailableForRecipeOrBrandQ.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum SingleOrMultiSelectDescriptorQ implements EnumWithCode<Integer> {

        /**
         * facet with single-selection of descriptor
         */
        SINGLE(0, "single"),
        /**
         * facets with multi-selection of descriptors
         */
        MULTI(1, "multi");

        private final Integer code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<SingleOrMultiSelectDescriptorQ, Integer> {
            @Override
            public SingleOrMultiSelectDescriptorQ[] values() {
                return SingleOrMultiSelectDescriptorQ.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum StandardOrMainFacetQ implements EnumWithCode<Integer> {

        /**
         * no description
         */
        STANDARD(0, "Standard"),
        /**
         *  facet (with non modified descriptor)
         */
        MAIN(1, "Main");

        private final Integer code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<StandardOrMainFacetQ, Integer> {
            @Override
            public StandardOrMainFacetQ[] values() {
                return StandardOrMainFacetQ.values();
            }
        }
    }

    /**
     * Manager Viewmodel for @{link RecipeFacet}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.recipe_description.RecipeFacet.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Facet for Recipe",
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                    + "solid swatchbook .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
     *
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
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Facet code for recipes") String code,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Facet name") String name,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Facet text (text to show on the screen describing the facet)") String textToShowOnTheScreenDescribingTheFacet,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "0=Standard facets with descriptors available in R_Descface table\n"
                            + "1=Facets with descriptors available in RBrand table") DescriptorsAvailableForRecipeOrBrandQ descriptorsAvailableForRecipeOrBrandQ,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "0 = facet with single-selection of descriptor\n"
                            + "1 = facets with multi-selection of descriptors") SingleOrMultiSelectDescriptorQ singleOrMultiSelectDescriptorQ,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "0 = standard facet\n"
                            + "1 = Main facet (with non modified descriptor)") StandardOrMainFacetQ standardOrMainFacetQ,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Label on how to ask the facet question") String labelOnHowToAskTheFacetQuestion
    ) {
    }

    /**
     * SecondaryKey for @{link RecipeFacet}
     *
     * @param code Facet code for recipes
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String code
    ) implements ISecondaryKey<RecipeFacet> {
        @Override
        public Class<RecipeFacet> correspondingClass() {
            return RecipeFacet.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link RecipeFacet} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable RecipeFacet",
            describedAs = "Unresolvable RecipeFacet",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.recipe_description.RecipeFacet.Unresolvable")
    @Embeddable
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
