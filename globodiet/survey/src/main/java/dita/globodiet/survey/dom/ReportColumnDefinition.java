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
import org.apache.causeway.applib.annotation.LabelPosition;
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
 * A column-definition defines which columns to include
 * with an interview report.
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.survey.dom.ReportColumnDefinition")
@DomainObject
@DomainObjectLayout(
        describedAs = "A column-definition defines which columns to include\n"
                + "with an interview report.",
        cssClassFa = "solid table-columns .reportColumnDefinition-color\n"
)
@PersistenceCapable(
        table = "ReportColumnDefinition"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_ReportColumnDefinition",
        members = {"surveyCode", "code"}
)
public class ReportColumnDefinition implements Cloneable<ReportColumnDefinition>, HasSecondaryKey<ReportColumnDefinition> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Survey code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
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
     * Unique (survey scoped) column-definition identifier.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Unique (survey scoped) column-definition identifier."
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
     * Descriptive column-definition name.
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Descriptive column-definition name."
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
     * Detailed information for this column-definition.
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Detailed information for this column-definition.",
            multiLine = 4
    )
    @Column(
            name = "DESCRIPTION",
            allowsNull = "true",
            length = 240
    )
    @Getter
    @Setter
    private String description;

    /**
     * Line by line defines a column to include.
     * (lines can be commented out with a leading #)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            cssClass = "listing",
            fieldSetId = "listing",
            sequence = "5",
            describedAs = "Line by line defines a column to include.\n"
                    + "(lines can be commented out with a leading #)",
            hidden = Where.EVERYWHERE,
            multiLine = 24,
            labelPosition = LabelPosition.NONE
    )
    @Column(
            name = "COLLISTING",
            allowsNull = "true",
            jdbcType = "CLOB"
    )
    @Getter
    @Setter
    private String columnListing;

    @ObjectSupport
    public String title() {
        return String.format("%s (%s)", name, surveyCode);
    }

    @Override
    public String toString() {
        return "ReportColumnDefinition(" + "surveyCode=" + getSurveyCode() + ","
         +"code=" + getCode() + ","
         +"name=" + getName() + ","
         +"description=" + getDescription() + ","
         +"columnListing=" + getColumnListing() + ")";
    }

    @Programmatic
    @Override
    public ReportColumnDefinition copy() {
        var copy = repositoryService.detachedEntity(new ReportColumnDefinition());
        copy.setSurveyCode(getSurveyCode());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setDescription(getDescription());
        copy.setColumnListing(getColumnListing());
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
    public ReportColumnDefinition.Manager getNavigableParent() {
        return new ReportColumnDefinition.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getSurveyCode(), 
        getCode());
    }

    /**
     * Manager Viewmodel for @{link ReportColumnDefinition}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.survey.dom.ReportColumnDefinition.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "A column-definition defines which columns to include\n"
                    + "with an interview report.",
            cssClassFa = "solid table-columns .reportColumnDefinition-color\n"
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
            return "Manage Report Column Definition";
        }

        @Collection
        public final List<ReportColumnDefinition> getListOfReportColumnDefinition() {
            return searchService.search(ReportColumnDefinition.class, ReportColumnDefinition::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link ReportColumnDefinition}
     *
     * @param survey Survey code
     * @param code Unique (survey scoped) column-definition identifier.
     * @param name Descriptive column-definition name.
     * @param description Detailed information for this column-definition.
     * @param columnListing Line by line defines a column to include.
     * (lines can be commented out with a leading #)
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Survey code") Survey survey,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Unique (survey scoped) column-definition identifier.") String code,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Descriptive column-definition name.") String name,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.OPTIONAL) @ParameterLayout(describedAs = "Detailed information for this column-definition.", multiLine = 4) String description,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.OPTIONAL) @ParameterLayout(describedAs = "Line by line defines a column to include.\n"
                            + "(lines can be commented out with a leading #)", multiLine = 24) String columnListing
    ) {
    }

    /**
     * SecondaryKey for @{link ReportColumnDefinition}
     *
     * @param surveyCode Survey code
     * @param code Unique (survey scoped) column-definition identifier.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String surveyCode,
            String code
    ) implements ISecondaryKey<ReportColumnDefinition> {
        @Override
        public Class<ReportColumnDefinition> correspondingClass() {
            return ReportColumnDefinition.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link ReportColumnDefinition} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable ReportColumnDefinition",
            describedAs = "Unresolvable ReportColumnDefinition",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.survey.dom.ReportColumnDefinition.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends ReportColumnDefinition implements ViewModel {
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
