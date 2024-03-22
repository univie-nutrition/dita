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
package dita.globodiet.dom.params.nutrient;

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
import org.causewaystuff.domsupport.services.lookup.Cloneable;
import org.causewaystuff.domsupport.services.lookup.HasSecondaryKey;
import org.causewaystuff.domsupport.services.lookup.ISecondaryKey;
import org.causewaystuff.domsupport.services.search.SearchService;

/**
 * Nutrient definition (energy, proteins, carbohydrates, etc.).
 */
@Named("dita.globodiet.params.nutrient.Nutrient")
@DomainObject
@DomainObjectLayout(
        describedAs = "Nutrient definition (energy, proteins, carbohydrates, etc.).",
        cssClassFa = "solid flask .nutrient-color"
)
@PersistenceCapable(
        table = "NUTRIENT"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_Nutrient",
        members = {"nutrientCode"}
)
public class Nutrient implements Cloneable<Nutrient>, HasSecondaryKey<Nutrient> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Nutrient code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
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
        return String.format("%s (unit=%s, code=%d)", nutrientName, nutrientUnit, nutrientCode);
    }

    @Override
    public String toString() {
        return "Nutrient(" + "nutrientCode=" + getNutrientCode() + ","
         +"nutrientName=" + getNutrientName() + ","
         +"nutrientUnit=" + getNutrientUnit() + ","
         +"displayInTheNutrientChecksScreen=" + getDisplayInTheNutrientChecksScreen() + ","
         +"commentOnNutrient=" + getCommentOnNutrient() + ")";
    }

    @Programmatic
    @Override
    public Nutrient copy() {
        var copy = repositoryService.detachedEntity(new Nutrient());
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
    public Nutrient.Manager getNavigableParent() {
        return new Nutrient.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getNutrientCode());
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
     * Manager Viewmodel for @{link Nutrient}
     */
    @Named("dita.globodiet.params.nutrient.Nutrient.Manager")
    @DomainObjectLayout(
            describedAs = "Nutrient definition (energy, proteins, carbohydrates, etc.).",
            cssClassFa = "solid flask .nutrient-color"
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
            return "Manage Nutrient";
        }

        @Collection
        public final List<Nutrient> getListOfNutrient() {
            return searchService.search(Nutrient.class, Nutrient::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Nutrient}
     * @param nutrientCode Nutrient code
     * @param nutrientName Nutrient Name
     * @param nutrientUnit Nutrient unit (e.g. kcal, g, mg…)
     * @param displayInTheNutrientChecksScreen 0=not displayed in the 'nutrient checks' screen
     * 1=displayed in the 'nutrient checks' screen
     * @param commentOnNutrient Comment on nutrient
     */
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Nutrient code"
            )
            int nutrientCode,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Nutrient Name"
            )
            String nutrientName,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Nutrient unit (e.g. kcal, g, mg…)"
            )
            String nutrientUnit,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=not displayed in the 'nutrient checks' screen\n"
                                    + "1=displayed in the 'nutrient checks' screen"
            )
            DisplayInTheNutrientChecksScreen displayInTheNutrientChecksScreen,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Comment on nutrient"
            )
            String commentOnNutrient) {
    }

    /**
     * SecondaryKey for @{link Nutrient}
     * @param nutrientCode Nutrient code
     */
    public final record SecondaryKey(int nutrientCode) implements ISecondaryKey<Nutrient> {
        @Override
        public Class<Nutrient> correspondingClass() {
            return Nutrient.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Nutrient} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable Nutrient",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.nutrient.Nutrient.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends Nutrient implements ViewModel {
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
