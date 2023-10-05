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
package dita.globodiet.dom.params.quantif;

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
 * Photo and its quantities
 */
@Named("dita.globodiet.params.quantif.PhotoForQuantity")
@DomainObject
@DomainObjectLayout(
        describedAs = "Photo and its quantities"
)
@PersistenceCapable(
        table = "M_PHOTOS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PhotoForQuantity implements HasSecondaryKey<PhotoForQuantity> {
    /**
     * Photo series code (P001,P002,P003,...)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Photo series code (P001,P002,P003,...)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PH_CODE",
            allowsNull = "false",
            length = 4
    )
    @Getter
    @Setter
    private String code;

    /**
     * Quantification string that defines the quantities of each photos (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Quantification string that defines the quantities of each photos (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PH_QSTR",
            allowsNull = "false",
            length = 4096
    )
    @Getter
    @Setter
    private String quantificationStringThatDefinesTheQuantitiesOfEachPhotos;

    /**
     * 1 = raw,
     * 2 = cooked (as estimated)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "1 = raw,\n"
                            + "2 = cooked (as estimated)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RAW_COOKED",
            allowsNull = "false",
            length = 1
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
    private RawOrCooked rawOrCooked;

    /**
     * 1 = without un-edible part,
     * 2 = with un-edible (as estimated)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "1 = without un-edible part,\n"
                            + "2 = with un-edible (as estimated)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "EDIB",
            allowsNull = "false",
            length = 1
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
    private WithUnediblePartQ withUnediblePartQ;

    /**
     * G = in grams, V = in ml (volume)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "G = in grams, V = in ml (volume)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PH_UNIT",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String unit;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @RequiredArgsConstructor
    public enum RawOrCooked {
        /**
         * no description
         */
        RAW("1", "raw"),

        /**
         * as estimated
         */
        COOKED("2", "cooked");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum WithUnediblePartQ {
        /**
         * without un-edible part
         */
        UN_EDIBLE_EXCLUDED("1", "un-edible excluded"),

        /**
         * with un-edible (as estimated)
         */
        UN_EDIBLE_INCLUDED("2", "un-edible included");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link PhotoForQuantity}
     */
    @Named("dita.globodiet.params.quantif.PhotoForQuantity.Manager")
    @DomainObjectLayout(
            describedAs = "Photo and its quantities"
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
            return "Manage Photo For Quantity";
        }

        @Collection
        public final List<PhotoForQuantity> getListOfPhotoForQuantity() {
            return searchService.search(PhotoForQuantity.class, PhotoForQuantity::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link PhotoForQuantity}
     * @param code Photo series code (P001,P002,P003,...)
     * @param quantificationStringThatDefinesTheQuantitiesOfEachPhotos Quantification string that defines the quantities of each photos (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)
     * @param rawOrCooked 1 = raw,
     * 2 = cooked (as estimated)
     * @param withUnediblePartQ 1 = without un-edible part,
     * 2 = with un-edible (as estimated)
     * @param unit G = in grams, V = in ml (volume)
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Photo series code (P001,P002,P003,...)"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Quantification string that defines the quantities of each photos (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)"
            )
            String quantificationStringThatDefinesTheQuantitiesOfEachPhotos,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "1 = raw,\n"
                                    + "2 = cooked (as estimated)"
            )
            RawOrCooked rawOrCooked,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "1 = without un-edible part,\n"
                                    + "2 = with un-edible (as estimated)"
            )
            WithUnediblePartQ withUnediblePartQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "G = in grams, V = in ml (volume)"
            )
            String unit) {
    }

    /**
     * SecondaryKey for @{link PhotoForQuantity}
     * @param code Photo series code (P001,P002,P003,...)
     */
    public final record SecondaryKey(String code) implements ISecondaryKey<PhotoForQuantity> {
        @Override
        public Class<PhotoForQuantity> correspondingClass() {
            return PhotoForQuantity.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link PhotoForQuantity} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable PhotoForQuantity",
            cssClassFa = "skull red"
    )
    @Named("dita.globodiet.params.quantif.PhotoForQuantity.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends PhotoForQuantity implements ViewModel {
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
