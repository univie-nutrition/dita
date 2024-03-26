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
 * A  campaign defines a part of a food consumption survey that contains several interviews.
 * Campaigns can be defined to be the whole study,
 * a seasonal part of a study, a regional part of a study etc.
 */
@Generated("org.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.survey.dom.Campaign")
@DomainObject
@DomainObjectLayout(
        describedAs = "A  campaign defines a part of a food consumption survey that contains several interviews.\n"
                        + "Campaigns can be defined to be the whole study,\n"
                        + "a seasonal part of a study, a regional part of a study etc.",
        cssClassFa = "solid users-viewfinder .campaign-color"
)
@PersistenceCapable(
        table = "CAMPAIGN"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_Campaign",
        members = {"surveyCode", "code"}
)
public class Campaign implements Cloneable<Campaign>, HasSecondaryKey<Campaign> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Unique (survey scoped) campaign identifier.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Unique (survey scoped) campaign identifier.",
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
     * Survey code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Survey code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SURVEY",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String surveyCode;

    /**
     * Descriptive campaign name.
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Descriptive campaign name.",
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

    /**
     * Detailed information for this campaign.
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Detailed information for this campaign.",
            multiLine = 4,
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESCRIPTION",
            allowsNull = "true",
            length = 240
    )
    @Getter
    @Setter
    private String description;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
    }

    @Override
    public String toString() {
        return "Campaign(" + "code=" + getCode() + ","
         +"surveyCode=" + getSurveyCode() + ","
         +"name=" + getName() + ","
         +"description=" + getDescription() + ")";
    }

    @Programmatic
    @Override
    public Campaign copy() {
        var copy = repositoryService.detachedEntity(new Campaign());
        copy.setCode(getCode());
        copy.setSurveyCode(getSurveyCode());
        copy.setName(getName());
        copy.setDescription(getDescription());
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
    public Campaign.Manager getNavigableParent() {
        return new Campaign.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getSurveyCode(), 
        getCode());
    }

    /**
     * Manager Viewmodel for @{link Campaign}
     */
    @Named("dita.globodiet.survey.dom.Campaign.Manager")
    @DomainObjectLayout(
            describedAs = "A  campaign defines a part of a food consumption survey that contains several interviews.\n"
                            + "Campaigns can be defined to be the whole study,\n"
                            + "a seasonal part of a study, a regional part of a study etc.",
            cssClassFa = "solid users-viewfinder .campaign-color"
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
            return "Manage Campaign";
        }

        @Collection
        public final List<Campaign> getListOfCampaign() {
            return searchService.search(Campaign.class, Campaign::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Campaign}
     * @param code Unique (survey scoped) campaign identifier.
     * @param survey Survey code
     * @param name Descriptive campaign name.
     * @param description Detailed information for this campaign.
     */
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Unique (survey scoped) campaign identifier."
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Survey code"
            )
            Survey survey,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptive campaign name."
            )
            String name,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Detailed information for this campaign.",
                    multiLine = 4
            )
            String description) {
    }

    /**
     * SecondaryKey for @{link Campaign}
     * @param surveyCode Survey code
     * @param code Unique (survey scoped) campaign identifier.
     */
    public final record SecondaryKey(
            String surveyCode,
            String code) implements ISecondaryKey<Campaign> {
        @Override
        public Class<Campaign> correspondingClass() {
            return Campaign.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Campaign} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable Campaign",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.survey.dom.Campaign.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends Campaign implements ViewModel {
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
