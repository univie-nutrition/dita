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
package dita.globodiet.params.food_descript;

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
 * Facet describing food (not recipe)
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.food_descript.FoodFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet describing food (not recipe)",
        cssClassFa = "solid utensils .food-color,\n"
                        + "solid swatchbook .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "FACETS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_FoodFacet",
        members = {"code"}
)
public class FoodFacet implements Cloneable<FoodFacet>, HasSecondaryKey<FoodFacet> {
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
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_CODE",
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
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Facet name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_NAME",
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
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Facet text (text to show on the screen describing the facet)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_TEXT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String text;

    /**
     * 0=Standard facets with descriptors available in Descface table
     * 1=Facets with descriptors available in Brandnam table
     * 2=Facets with descriptors available in Foods table - facet 15 type of fat
     * 3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "0=Standard facets with descriptors available in Descface table\n"
                            + "1=Facets with descriptors available in Brandnam table\n"
                            + "2=Facets with descriptors available in Foods table - facet 15 type of fat\n"
                            + "3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_TYPE",
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
    private Type type;

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
                            + "1 = facets with multi-selection of descriptors",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_TYPE_S",
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
    private TypeCardinality typeCardinality;

    /**
     * If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.
     * Comma is used as delimiter (e.g. 10,050701,050702)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.\n"
                            + "Comma is used as delimiter (e.g. 10,050701,050702)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_GRP",
            allowsNull = "true",
            length = 4096
    )
    @Getter
    @Setter
    private String group;

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
            name = "FACET_QUEST",
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
        return "FoodFacet(" + "code=" + getCode() + ","
         +"name=" + getName() + ","
         +"text=" + getText() + ","
         +"type=" + getType() + ","
         +"typeCardinality=" + getTypeCardinality() + ","
         +"group=" + getGroup() + ","
         +"labelOnHowToAskTheFacetQuestion=" + getLabelOnHowToAskTheFacetQuestion() + ")";
    }

    @Programmatic
    @Override
    public FoodFacet copy() {
        var copy = repositoryService.detachedEntity(new FoodFacet());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setText(getText());
        copy.setType(getType());
        copy.setTypeCardinality(getTypeCardinality());
        copy.setGroup(getGroup());
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
    public FoodFacet.Manager getNavigableParent() {
        return new FoodFacet.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @RequiredArgsConstructor
    public enum Type {
        /**
         *  facets with descriptors available in Descface table
         */
        STANDARD(0, "Standard"),

        /**
         * Facets with descriptors available in Brandnam table
         */
        BRAND(1, "Brand"),

        /**
         * Facets with descriptors available in Foods table - facet 15 type of fat
         */
        FAT(2, "Fat"),

        /**
         * Facets with descriptors available in Foods table - facet 16 type of milk/liquid used
         */
        LIQUID(3, "Liquid");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum TypeCardinality {
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

    /**
     * Manager Viewmodel for @{link FoodFacet}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.food_descript.FoodFacet.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Facet describing food (not recipe)",
            cssClassFa = "solid utensils .food-color,\n"
                            + "solid swatchbook .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Food Facet";
        }

        @Collection
        public final List<FoodFacet> getListOfFoodFacet() {
            return searchService.search(FoodFacet.class, FoodFacet::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link FoodFacet}
     * @param code Facet code
     * @param name Facet name
     * @param text Facet text (text to show on the screen describing the facet)
     * @param type 0=Standard facets with descriptors available in Descface table
     * 1=Facets with descriptors available in Brandnam table
     * 2=Facets with descriptors available in Foods table - facet 15 type of fat
     * 3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used
     * @param typeCardinality 0 = facet with single-selection of descriptor
     * 1 = facets with multi-selection of descriptors
     * @param group If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.
     * Comma is used as delimiter (e.g. 10,050701,050702)
     * @param labelOnHowToAskTheFacetQuestion Label on how to ask the facet question
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet code"
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet name"
            )
            String name,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet text (text to show on the screen describing the facet)"
            )
            String text,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=Standard facets with descriptors available in Descface table\n"
                                    + "1=Facets with descriptors available in Brandnam table\n"
                                    + "2=Facets with descriptors available in Foods table - facet 15 type of fat\n"
                                    + "3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used"
            )
            Type type,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0 = facet with single-selection of descriptor\n"
                                    + "1 = facets with multi-selection of descriptors"
            )
            TypeCardinality typeCardinality,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.\n"
                                    + "Comma is used as delimiter (e.g. 10,050701,050702)"
            )
            String group,
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
     * SecondaryKey for @{link FoodFacet}
     * @param code Facet code
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(String code) implements ISecondaryKey<FoodFacet> {
        @Override
        public Class<FoodFacet> correspondingClass() {
            return FoodFacet.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodFacet} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable FoodFacet",
            describedAs = "Unresolvable FoodFacet",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.food_descript.FoodFacet.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends FoodFacet implements ViewModel {
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
