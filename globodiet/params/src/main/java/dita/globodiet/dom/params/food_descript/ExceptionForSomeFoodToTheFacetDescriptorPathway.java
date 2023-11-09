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
package dita.globodiet.dom.params.food_descript;

import dita.commons.services.lookup.Cloneable;
import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
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
 * Exception for some food to the Facets/Descriptors pathway
 */
@Named("dita.globodiet.params.food_descript.ExceptionForSomeFoodToTheFacetDescriptorPathway")
@DomainObject
@DomainObjectLayout(
        describedAs = "Exception for some food to the Facets/Descriptors pathway"
)
@PersistenceCapable(
        table = "FOODFAEX"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class ExceptionForSomeFoodToTheFacetDescriptorPathway implements Cloneable<ExceptionForSomeFoodToTheFacetDescriptorPathway> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * food ID number
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "food ID number",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOODNUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String foodCode;

    /**
     * Facet codes that MUST appear in the sequence of facets corresponding to this food
     * (superseeding its group pathway).
     * The list of descriptors will be the ones defined for the subgroup in GROUPFAC file
     * (Assuming always a subset)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Facet codes that MUST appear in the sequence of facets corresponding to this food\n"
                            + "(superseeding its group pathway).\n"
                            + "The list of descriptors will be the ones defined for the subgroup in GROUPFAC file\n"
                            + "(Assuming always a subset)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String mandatoryInSequenceOfFacetsCode;

    /**
     * Order to display the facets for the attached food (same order as order_fac from Groupfac table)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Order to display the facets for the attached food (same order as order_fac from Groupfac table)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ORDER_FAC",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int displayOrder;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "ExceptionForSomeFoodToTheFacetDescriptorPathway(" + "foodCode=" + getFoodCode() + ","
         +"mandatoryInSequenceOfFacetsCode=" + getMandatoryInSequenceOfFacetsCode() + ","
         +"displayOrder=" + getDisplayOrder() + ")";
    }

    @Programmatic
    @Override
    public ExceptionForSomeFoodToTheFacetDescriptorPathway copy() {
        var copy = repositoryService.detachedEntity(new ExceptionForSomeFoodToTheFacetDescriptorPathway());
        copy.setFoodCode(getFoodCode());
        copy.setMandatoryInSequenceOfFacetsCode(getMandatoryInSequenceOfFacetsCode());
        copy.setDisplayOrder(getDisplayOrder());
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
    public ExceptionForSomeFoodToTheFacetDescriptorPathway.Manager getNavigableParent() {
        return new ExceptionForSomeFoodToTheFacetDescriptorPathway.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link ExceptionForSomeFoodToTheFacetDescriptorPathway}
     */
    @Named("dita.globodiet.params.food_descript.ExceptionForSomeFoodToTheFacetDescriptorPathway.Manager")
    @DomainObjectLayout(
            describedAs = "Exception for some food to the Facets/Descriptors pathway"
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
            return "Manage Exception For Some Food To The Facet Descriptor Pathway";
        }

        @Collection
        public final List<ExceptionForSomeFoodToTheFacetDescriptorPathway> getListOfExceptionForSomeFoodToTheFacetDescriptorPathway(
                ) {
            return searchService.search(ExceptionForSomeFoodToTheFacetDescriptorPathway.class, ExceptionForSomeFoodToTheFacetDescriptorPathway::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
