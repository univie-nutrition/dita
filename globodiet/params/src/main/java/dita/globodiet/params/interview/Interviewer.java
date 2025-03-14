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
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * Interviewer
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.interview.Interviewer")
@DomainObject
@DomainObjectLayout(
        describedAs = "Interviewer",
        cssClassFa = "solid user"
)
@Entity
@Table(
        name = "INTVIEWR"
)
public class Interviewer implements Persistable, Cloneable<Interviewer> {
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
     * Interviewer code
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Interviewer code"
    )
    @Column(
            name = "\"INTV_CODE\"",
            nullable = false,
            length = 20
    )
    @Getter
    @Setter
    private String interviewerCode;

    /**
     * Interviewer family name
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Interviewer family name"
    )
    @Column(
            name = "\"INTV_FNAME\"",
            nullable = true,
            length = 100
    )
    @Getter
    @Setter
    private String interviewerFamilyName;

    /**
     * Interviewer name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Interviewer name"
    )
    @Column(
            name = "\"INTV_NAME\"",
            nullable = false,
            length = 100
    )
    @Getter
    @Setter
    private String interviewerName;

    /**
     * Country code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Country code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"COUNTRY\"",
            nullable = false,
            length = 3
    )
    @Getter
    @Setter
    private String countryCode;

    /**
     * Center code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
            describedAs = "Center code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"CENTER\"",
            nullable = false,
            length = 3
    )
    @Getter
    @Setter
    private String centerCode;

    @ObjectSupport
    public String title() {
        return String.format("%s, %s (code=%s)", interviewerFamilyName, interviewerName, interviewerCode);
    }

    @Override
    public String toString() {
        return "Interviewer(" + "interviewerCode=" + getInterviewerCode() + ","
         +"interviewerFamilyName=" + getInterviewerFamilyName() + ","
         +"interviewerName=" + getInterviewerName() + ","
         +"countryCode=" + getCountryCode() + ","
         +"centerCode=" + getCenterCode() + ")";
    }

    @Programmatic
    @Override
    public Interviewer copy() {
        var copy = repositoryService.detachedEntity(new Interviewer());
        copy.setInterviewerCode(getInterviewerCode());
        copy.setInterviewerFamilyName(getInterviewerFamilyName());
        copy.setInterviewerName(getInterviewerName());
        copy.setCountryCode(getCountryCode());
        copy.setCenterCode(getCenterCode());
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
    public Interviewer.Manager getNavigableParent() {
        return new Interviewer.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link Interviewer}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.interview.Interviewer.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Interviewer",
            cssClassFa = "solid user"
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
            return "Manage Interviewer";
        }

        @Collection
        public final List<Interviewer> getListOfInterviewer() {
            return searchService.search(Interviewer.class, Interviewer::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
