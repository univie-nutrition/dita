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
package dita.globodiet.dom.params.supplement;

import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
 * Dietary supplement
 */
@Named("dita.globodiet.params.supplement.DietarySupplement")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement"
)
@PersistenceCapable(
        table = "SUPPLEMENTS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class DietarySupplement {
    /**
     * Vitamin/supplement code
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Vitamin/supplement code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CODE_VITA",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String vitaminOrSupplementCode;

    /**
     * Vitamin/supplement name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Vitamin/supplement name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String vitaminOrSupplementName;

    /**
     * Blank or 'GI' for generic vitamin/supplement
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Blank or 'GI' for generic vitamin/supplement",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String blankOrGIForGenericVitaminOrSupplement;

    /**
     * Dietary Supplement classification code (optional)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Dietary Supplement classification code (optional)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "DS_CLASS",
            allowsNull = "true",
            length = 20
    )
    @Getter
    @Setter
    private String classificationCode;

    /**
     * Sequence of facet codes that indicates the facets to be displayed for this supplement (e.g. 03,04,01)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
            describedAs = "Sequence of facet codes that indicates the facets to be displayed for this supplement (e.g. 03,04,01)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "DS_FACET",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String facetCode;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", vitaminOrSupplementName, vitaminOrSupplementCode);
    }

    /**
     * Manager Viewmodel for @{link DietarySupplement}
     */
    @Named("dita.globodiet.params.supplement.DietarySupplement.Manager")
    @DomainObjectLayout(
            describedAs = "Dietary supplement"
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
            return "Manage Dietary Supplement";
        }

        @Collection
        public final List<DietarySupplement> getListOfDietarySupplement() {
            return searchService.search(DietarySupplement.class, DietarySupplement::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
