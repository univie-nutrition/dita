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
package dita.globodiet.dom.params.general_info;

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
 * Place of Consumption
 */
@Named("dita.globodiet.params.general_info.PlaceOfConsumption")
@DomainObject
@DomainObjectLayout(
        describedAs = "Place of Consumption",
        cssClassFa = "solid building-user"
)
@PersistenceCapable(
        table = "POC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PlaceOfConsumption implements HasSecondaryKey<PlaceOfConsumption> {
    /**
     * Place of consumption code
     */
    @Property
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Place of consumption code\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "POC_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Place of consumption name
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Place of consumption name\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "POC_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * 0=not a 'Other' place
     * 1='Other' place
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "0=not a 'Other' place\n"
                            + "1='Other' place\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "POC_OTHER",
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
    private OtherPlaceQ otherPlaceQ;

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
    public enum OtherPlaceQ {
        /**
         * not a 'Other' place
         */
        SPECIFIC(0, "specific"),

        /**
         * 'Other' place
         */
        OTHER(1, "other");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link PlaceOfConsumption}
     */
    @Named("dita.globodiet.params.general_info.PlaceOfConsumption.Manager")
    @DomainObjectLayout(
            describedAs = "Place of Consumption",
            cssClassFa = "solid building-user"
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
            return "Manage Place Of Consumption";
        }

        @Collection
        public final List<PlaceOfConsumption> getListOfPlaceOfConsumption() {
            return searchService.search(PlaceOfConsumption.class, PlaceOfConsumption::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link PlaceOfConsumption}
     * @param code Place of consumption code
     * @param name Place of consumption name
     * @param otherPlaceQ 0=not a 'Other' place
     * 1='Other' place
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Place of consumption code"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Place of consumption name"
            )
            String name,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=not a 'Other' place\n"
                                    + "1='Other' place"
            )
            OtherPlaceQ otherPlaceQ) {
    }

    /**
     * SecondaryKey for @{link PlaceOfConsumption}
     * @param code Place of consumption code
     */
    public final record SecondaryKey(String code) implements ISecondaryKey<PlaceOfConsumption> {
        @Override
        public Class<PlaceOfConsumption> correspondingClass() {
            return PlaceOfConsumption.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link PlaceOfConsumption} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable PlaceOfConsumption",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends PlaceOfConsumption implements ViewModel {
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
