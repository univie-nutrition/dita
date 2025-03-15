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
package dita.globodiet.params.interview;

import io.github.causewaystuff.companion.applib.jpa.Persistable;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.Class;
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
 * Country involved
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.interview.CountryInvolved")
@DomainObject
@DomainObjectLayout(
        describedAs = "Country involved",
        cssClassFa = "earth-europe"
)
@Entity
@Table(
        name = "COUNTRY"
)
public class CountryInvolved implements Persistable, Cloneable<CountryInvolved>, HasSecondaryKey<CountryInvolved> {
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
     * Country code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Country code"
    )
    @Column(
            name = "\"CTRYCODE\"",
            nullable = false,
            length = 3
    )
    @Getter
    @Setter
    private String countryCode;

    /**
     * Country name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Country name"
    )
    @Column(
            name = "\"CTRY_NAME\"",
            nullable = false,
            length = 50
    )
    @Getter
    @Setter
    private String countryName;

    @ObjectSupport
    public String title() {
        return String.format("%s", countryName);
    }

    @Override
    public String toString() {
        return "CountryInvolved(" + "countryCode=" + getCountryCode() + ","
         +"countryName=" + getCountryName() + ")";
    }

    @Programmatic
    @Override
    public CountryInvolved copy() {
        var copy = repositoryService.detachedEntity(new CountryInvolved());
        copy.setCountryCode(getCountryCode());
        copy.setCountryName(getCountryName());
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
    public CountryInvolved.Manager getNavigableParent() {
        return new CountryInvolved.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCountryCode());
    }

    /**
     * Manager Viewmodel for @{link CountryInvolved}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.interview.CountryInvolved.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
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
        @PropertyLayout(
                fieldSetId = "searchBar"
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
     *
     * @param countryCode Country code
     * @param countryName Country name
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Country code") String countryCode,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Country name") String countryName
    ) {
    }

    /**
     * SecondaryKey for @{link CountryInvolved}
     *
     * @param countryCode Country code
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String countryCode
    ) implements ISecondaryKey<CountryInvolved> {
        @Override
        public Class<CountryInvolved> correspondingClass() {
            return CountryInvolved.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link CountryInvolved} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable CountryInvolved",
            describedAs = "Unresolvable CountryInvolved",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.interview.CountryInvolved.Unresolvable")
    @Embeddable
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
