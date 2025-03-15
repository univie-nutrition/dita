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
package dita.globodiet.survey.dom;

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
import jakarta.persistence.UniqueConstraint;
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
 * A survey collects one or more campaigns.
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.survey.dom.Survey")
@DomainObject
@DomainObjectLayout(
        describedAs = "A survey collects one or more campaigns.",
        cssClassFa = "solid users .survey-color"
)
@Entity
@Table(
        name = "SURVEY",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "code"
        )
)
public class Survey implements Persistable, Cloneable<Survey>, HasSecondaryKey<Survey> {
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
     * Unique (application scoped) survey identifier.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Unique (application scoped) survey identifier."
    )
    @Column(
            name = "\"CODE\"",
            nullable = false,
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
            describedAs = "Descriptive survey name."
    )
    @Column(
            name = "\"NAME\"",
            nullable = false,
            length = 120
    )
    @Getter
    @Setter
    private String name;

    /**
     * System ID part of semantic identifiers for this survey.
     * e.g. at.gd/2.0
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "System ID part of semantic identifiers for this survey.\n"
                    + "e.g. at.gd/2.0"
    )
    @Column(
            name = "\"SYSID\"",
            nullable = false,
            length = 20
    )
    @Getter
    @Setter
    private String systemId;

    /**
     * Yaml formatted interview data corrections.
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Yaml formatted interview data corrections.",
            hidden = Where.EVERYWHERE
    )
    @Column(
            name = "\"CORRECTION\"",
            nullable = true,
            columnDefinition = "CLOB"
    )
    @Getter
    @Setter
    private String correction;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
    }

    @Override
    public String toString() {
        return "Survey(" + "code=" + getCode() + ","
         +"name=" + getName() + ","
         +"systemId=" + getSystemId() + ","
         +"correction=" + getCorrection() + ")";
    }

    @Programmatic
    @Override
    public Survey copy() {
        var copy = repositoryService.detachedEntity(new Survey());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setSystemId(getSystemId());
        copy.setCorrection(getCorrection());
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
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.survey.dom.Survey.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "A survey collects one or more campaigns.",
            cssClassFa = "solid users .survey-color"
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
     *
     * @param code Unique (application scoped) survey identifier.
     * @param name Descriptive survey name.
     * @param systemId System ID part of semantic identifiers for this survey.
     * e.g. at.gd/2.0
     * @param correction Yaml formatted interview data corrections.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Unique (application scoped) survey identifier.") String code,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Descriptive survey name.") String name,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "System ID part of semantic identifiers for this survey.\n"
                            + "e.g. at.gd/2.0") String systemId,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.OPTIONAL) @ParameterLayout(describedAs = "Yaml formatted interview data corrections.") String correction
    ) {
    }

    /**
     * SecondaryKey for @{link Survey}
     *
     * @param code Unique (application scoped) survey identifier.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String code
    ) implements ISecondaryKey<Survey> {
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
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable Survey",
            describedAs = "Unresolvable Survey",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.survey.dom.Survey.Unresolvable")
    @Embeddable
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
