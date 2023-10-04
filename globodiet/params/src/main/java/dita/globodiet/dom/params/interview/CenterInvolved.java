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
 * Center involved
 */
@Named("dita.globodiet.params.interview.CenterInvolved")
@DomainObject
@DomainObjectLayout(
        describedAs = "Center involved",
        cssClassFa = "building"
)
@PersistenceCapable(
        table = "CENTERS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class CenterInvolved implements HasSecondaryKey<CenterInvolved> {
    /**
     * Center code
     */
    @Property
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Center code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CNTR_CODE",
            allowsNull = "false",
            length = 3
    )
    @Getter
    @Setter
    private String centerCode;

    /**
     * Attached Country code
     */
    @Property
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Attached Country code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "CTRYCODE",
            allowsNull = "false",
            length = 3
    )
    @Getter
    @Setter
    private String attachedCountryCode;

    /**
     * Center name
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Center name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CNTR_NAME",
            allowsNull = "false",
            length = 50
    )
    @Getter
    @Setter
    private String centerName;

    @ObjectSupport
    public String title() {
        return String.format("%s", centerName);
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCenterCode());
    }

    /**
     * Manager Viewmodel for @{link CenterInvolved}
     */
    @Named("dita.globodiet.params.interview.CenterInvolved.Manager")
    @DomainObjectLayout(
            describedAs = "Center involved",
            cssClassFa = "building"
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
            return "Manage Center Involved";
        }

        @Collection
        public final List<CenterInvolved> getListOfCenterInvolved() {
            return searchService.search(CenterInvolved.class, CenterInvolved::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link CenterInvolved}
     * @param centerCode Center code
     * @param attachedCountry Attached Country code
     * @param centerName Center name
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Center code"
            )
            String centerCode,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Attached Country code"
            )
            CountryInvolved attachedCountry,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Center name"
            )
            String centerName) {
    }

    /**
     * SecondaryKey for @{link CenterInvolved}
     * @param centerCode Center code
     */
    public final record SecondaryKey(String centerCode) implements ISecondaryKey<CenterInvolved> {
        @Override
        public Class<CenterInvolved> correspondingClass() {
            return CenterInvolved.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link CenterInvolved} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable CenterInvolved",
            cssClassFa = "skull red"
    )
    @Named("dita.globodiet.params.interview.CenterInvolved.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends CenterInvolved implements ViewModel {
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
