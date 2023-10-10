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

import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.sql.Timestamp;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
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
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;

/**
 * Subjects to be interviewed
 */
@Named("dita.globodiet.params.interview.SubjectToBeInterviewed")
@DomainObject
@DomainObjectLayout(
        describedAs = "Subjects to be interviewed",
        cssClassFa = "solid person-circle-question"
)
@PersistenceCapable(
        table = "SUBJECTS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class SubjectToBeInterviewed {
    @Inject
    SearchService searchService;

    /**
     * Interview number
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Interview number",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "INT_NUM",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer interviewNumber;

    /**
     * Subject birth date
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Subject birth date",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PAT_BDATE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Timestamp subjectBirthDate;

    /**
     * Subject code
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Subject code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PAT_CODE",
            allowsNull = "true",
            length = 20
    )
    @Getter
    @Setter
    private String subjectCode;

    /**
     * Subject first name
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Subject first name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PAT_FNAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String subjectFirstName;

    /**
     * Subject height in cm
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Subject height in cm",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PAT_HEIGHT",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double subjectHeightInCm;

    /**
     * Subject name
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Subject name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PAT_NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String subjectName;

    /**
     * Subject sex (1=man, 2=woman)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Subject sex (1=man, 2=woman)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PAT_SEX",
            allowsNull = "true",
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
    private SubjectSex subjectSex;

    /**
     * Subject weight in kg
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Subject weight in kg",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PAT_WEIGHT",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double subjectWeightInKg;

    /**
     * 0=interview to be done,
     * 1=interview done
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "9",
            describedAs = "0=interview to be done,\n"
                            + "1=interview done",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DONE",
            allowsNull = "true"
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
    private DoneQ doneQ;

    /**
     * Country code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "10",
            describedAs = "Country code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "COUNTRY",
            allowsNull = "true",
            length = 3
    )
    @Getter
    @Setter
    private String countryCode;

    /**
     * Center code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "11",
            describedAs = "Center code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "CENTER",
            allowsNull = "true",
            length = 3
    )
    @Getter
    @Setter
    private String centerCode;

    /**
     * Interviewer Country code
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "12",
            describedAs = "Interviewer Country code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "INTV_COUNTRY",
            allowsNull = "true",
            length = 3
    )
    @Getter
    @Setter
    private String interviewerCountryCode;

    /**
     * Interviewer Center code
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "13",
            describedAs = "Interviewer Center code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "INTV_CENTER",
            allowsNull = "true",
            length = 3
    )
    @Getter
    @Setter
    private String interviewerCenterCode;

    /**
     * Interviewer code
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "14",
            describedAs = "Interviewer code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "INTV_CODE",
            allowsNull = "true",
            length = 20
    )
    @Getter
    @Setter
    private String interviewerCode;

    /**
     * Recall Date (mm/dd/yy)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "15",
            describedAs = "Recall Date (mm/dd/yy)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DATE_REC",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Timestamp recallDate;

    @ObjectSupport
    public String title() {
        return String.format("%s, %s (code=%s)", subjectName, subjectFirstName, subjectCode);
    }

    @Override
    public String toString() {
        return "SubjectToBeInterviewed(" + "interviewNumber=" + getInterviewNumber() + ","
         +"subjectBirthDate=" + getSubjectBirthDate() + ","
         +"subjectCode=" + getSubjectCode() + ","
         +"subjectFirstName=" + getSubjectFirstName() + ","
         +"subjectHeightInCm=" + getSubjectHeightInCm() + ","
         +"subjectName=" + getSubjectName() + ","
         +"subjectSex=" + getSubjectSex() + ","
         +"subjectWeightInKg=" + getSubjectWeightInKg() + ","
         +"doneQ=" + getDoneQ() + ","
         +"countryCode=" + getCountryCode() + ","
         +"centerCode=" + getCenterCode() + ","
         +"interviewerCountryCode=" + getInterviewerCountryCode() + ","
         +"interviewerCenterCode=" + getInterviewerCenterCode() + ","
         +"interviewerCode=" + getInterviewerCode() + ","
         +"recallDate=" + getRecallDate() + ")";
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            navigable = Navigable.PARENT,
            hidden = Where.EVERYWHERE
    )
    @NotPersistent
    public SubjectToBeInterviewed.Manager getNavigableParent() {
        return new SubjectToBeInterviewed.Manager(searchService, "");
    }

    @RequiredArgsConstructor
    public enum SubjectSex {
        /**
         * no description
         */
        MALE("1", "male"),

        /**
         * no description
         */
        FEMALE("2", "female");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum DoneQ {
        /**
         * no description
         */
        PENDING(0, "pending"),

        /**
         * no description
         */
        DONE(1, "done");

        @Getter
        private final Integer matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link SubjectToBeInterviewed}
     */
    @Named("dita.globodiet.params.interview.SubjectToBeInterviewed.Manager")
    @DomainObjectLayout(
            describedAs = "Subjects to be interviewed",
            cssClassFa = "solid person-circle-question"
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
            return "Manage Subject To Be Interviewed";
        }

        @Collection
        public final List<SubjectToBeInterviewed> getListOfSubjectToBeInterviewed() {
            return searchService.search(SubjectToBeInterviewed.class, SubjectToBeInterviewed::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
