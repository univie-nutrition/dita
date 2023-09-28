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
package dita.globodiet.dom.params.interview;

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
 * Country involved
 */
@Named("dita.globodiet.params.interview.CountryInvolved")
@DomainObject
@DomainObjectLayout(
        describedAs = "Country involved",
        cssClassFa = "earth-europe"
)
@PersistenceCapable(
        table = "COUNTRY"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class CountryInvolved implements HasSecondaryKey<CountryInvolved> {
    /**
     * Country code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Country code\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CTRYCODE",
            allowsNull = "false",
            length = 3
    )
    @Getter
    @Setter
    private String countryCode;

    /**
     * Country name
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Country name\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CTRY_NAME",
            allowsNull = "false",
            length = 50
    )
    @Getter
    @Setter
    private String countryName;

    @ObjectSupport
    public String title() {
        return String.format("%s", countryName);
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCountryCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getCountryCode())));
    }

    /**
     * Manager Viewmodel for @{link CountryInvolved}
     */
    @Named("dita.globodiet.params.interview.CountryInvolved.Manager")
    @DomainObjectLayout(
            describedAs = "Country involved",
            cssClassFa = "earth-europe"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
        )
        @Getter
        @Setter
        private String search;

        @ObjectSupport
        public String title() {
            return "Manage Country Involved";
        }

        @Collection
        public final List<CountryInvolved> getListOfCountryInvolved() {
            return searchService.search(CountryInvolved.class, CountryInvolved::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link CountryInvolved}
     * @param countryCode Country code
     * @param countryName Country name
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Country code"
            )
            String countryCode,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Country name"
            )
            String countryName) {
    }

    /**
     * SecondaryKey for @{link CountryInvolved}
     * @param countryCode Country code
     */
    public final record SecondaryKey(String countryCode) implements ISecondaryKey<CountryInvolved> {
        @Override
        public Class<CountryInvolved> correspondingClass() {
            return CountryInvolved.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link CountryInvolved} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable CountryInvolved",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends CountryInvolved implements ViewModel {
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
