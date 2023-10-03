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
package dita.globodiet.dom.params.setting;

import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
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
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Note status
 */
@Named("dita.globodiet.params.setting.NoteStatus")
@DomainObject
@DomainObjectLayout(
        describedAs = "Note status"
)
@PersistenceCapable(
        table = "STATUS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class NoteStatus {
    /**
     * Status code
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Status code\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "S_CODE",
            allowsNull = "false",
            length = 3
    )
    @Getter
    @Setter
    private String statusCode;

    /**
     * Status label
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Status label\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "S_LABEL",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String statusLabel;

    /**
     * Allow the possibility to display or not the note in the view note window:
     * 0=No hide,
     * 1=Yes hide (e.g. 1=hide for status “action done”)
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Allow the possibility to display or not the note in the view note window:\n"
                            + "0=No hide,\n"
                            + "1=Yes hide (e.g. 1=hide for status “action done”)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "S_HIDE",
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
    private DisplayNoteInTheViewNoteWindow displayNoteInTheViewNoteWindow;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @RequiredArgsConstructor
    public enum DisplayNoteInTheViewNoteWindow {
        /**
         * don't hide hide
         */
        YES(0, "Yes"),

        /**
         * hide: e.g. 1=hide for status “action done”
         */
        NO(1, "No");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link NoteStatus}
     */
    @Named("dita.globodiet.params.setting.NoteStatus.Manager")
    @DomainObjectLayout(
            describedAs = "Note status"
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
            return "Manage Note Status";
        }

        @Collection
        public final List<NoteStatus> getListOfNoteStatus() {
            return searchService.search(NoteStatus.class, NoteStatus::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
