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
package dita.globodiet.survey.entities;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
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
import org.causewaystuff.companion.applib.services.lookup.Cloneable;
import org.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import org.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import org.causewaystuff.companion.applib.services.search.SearchService;

/**
 * A survey collects one or more campaigns.
 */
@Generated("org.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.survey.entities.Survey")
@DomainObject
@DomainObjectLayout(
        describedAs = "A survey collects one or more campaigns.",
        cssClassFa = "solid users-viewfinder .survey-color"
)
@PersistenceCapable(
        table = "SURVEY"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_Survey",
        members = {"code"}
)
public class Survey implements Cloneable<Survey>, HasSecondaryKey<Survey> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Unique (application scoped) survey identifier.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Unique (application scoped) survey identifier.",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CODE",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String code;

    /**
     * Descriptive survey name.
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Descriptive survey name.",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 120
    )
    @Getter
    @Setter
    private String name;

    @ObjectSupport
    public String title() {
        return String.format("%s, %s (code=%s)", name, code);
    }

    @Override
    public String toString() {
        return "Survey(" + "code=" + getCode() + ","
         +"name=" + getName() + ")";
    }

    @Programmatic
    @Override
    public Survey copy() {
        var copy = repositoryService.detachedEntity(new Survey());
        copy.setCode(getCode());
        copy.setName(getName());
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
    public Survey.Manager getNavigableParent() {
        return new Survey.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    /**
     * Manager Viewmodel for @{link Survey}
     */
    @Named("dita.globodiet.survey.entities.Survey.Manager")
    @DomainObjectLayout(
            describedAs = "A survey collects one or more campaigns.",
            cssClassFa = "solid users-viewfinder .survey-color"
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
            return "Manage Survey";
        }

        @Collection
        public final List<Survey> getListOfSurvey() {
            return searchService.search(Survey.class, Survey::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Survey}
     * @param code Unique (application scoped) survey identifier.
     * @param name Descriptive survey name.
     */
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Unique (application scoped) survey identifier."
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptive survey name."
            )
            String name) {
    }

    /**
     * SecondaryKey for @{link Survey}
     * @param code Unique (application scoped) survey identifier.
     */
    public final record SecondaryKey(String code) implements ISecondaryKey<Survey> {
        @Override
        public Class<Survey> correspondingClass() {
            return Survey.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Survey} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable Survey",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.survey.entities.Survey.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends Survey implements ViewModel {
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
