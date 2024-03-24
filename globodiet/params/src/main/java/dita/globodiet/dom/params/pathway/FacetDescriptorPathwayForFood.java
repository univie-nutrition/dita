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
package dita.globodiet.dom.params.pathway;

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
import org.causewaystuff.companion.applib.services.lookup.Cloneable;
import org.causewaystuff.companion.applib.services.search.SearchService;

/**
 * Facet/descriptor pathway for individual food.
 * Supersedes this food's group facet/descriptor pathway from @{table GROUPFAC}.
 */
@Named("dita.globodiet.params.pathway.FacetDescriptorPathwayForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet/descriptor pathway for individual food.\n"
                        + "Supersedes this food's group facet/descriptor pathway from @{table GROUPFAC}.",
        cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                        + "solid tag .food-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                        + "solid exclamation-circle .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "FOODFAEX"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FacetDescriptorPathwayForFood implements Cloneable<FacetDescriptorPathwayForFood> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Food unique id.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Food unique id.",
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
     * Food Facet codes that will appear in the sequence of facets corresponding to this Food
     * (supersedes this Food's group pathway).
     * The list of descriptors will be the ones defined for the subgroup in @{table GROUPFAC}.
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Food Facet codes that will appear in the sequence of facets corresponding to this Food\n"
                            + "(supersedes this Food's group pathway).\n"
                            + "The list of descriptors will be the ones defined for the subgroup in @{table GROUPFAC}.",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String selectedFoodFacetCode;

    /**
     * Order to display the facets for the referenced food (same order as ORDER_FAC in @{table GROUPFAC})
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Order to display the facets for the referenced food (same order as ORDER_FAC in @{table GROUPFAC})",
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
        return "FacetDescriptorPathwayForFood(" + "foodCode=" + getFoodCode() + ","
         +"selectedFoodFacetCode=" + getSelectedFoodFacetCode() + ","
         +"displayOrder=" + getDisplayOrder() + ")";
    }

    @Programmatic
    @Override
    public FacetDescriptorPathwayForFood copy() {
        var copy = repositoryService.detachedEntity(new FacetDescriptorPathwayForFood());
        copy.setFoodCode(getFoodCode());
        copy.setSelectedFoodFacetCode(getSelectedFoodFacetCode());
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
    public FacetDescriptorPathwayForFood.Manager getNavigableParent() {
        return new FacetDescriptorPathwayForFood.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link FacetDescriptorPathwayForFood}
     */
    @Named("dita.globodiet.params.pathway.FacetDescriptorPathwayForFood.Manager")
    @DomainObjectLayout(
            describedAs = "Facet/descriptor pathway for individual food.\n"
                            + "Supersedes this food's group facet/descriptor pathway from @{table GROUPFAC}.",
            cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                            + "solid tag .food-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                            + "solid exclamation-circle .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
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
            return "Manage Facet Descriptor Pathway For Food";
        }

        @Collection
        public final List<FacetDescriptorPathwayForFood> getListOfFacetDescriptorPathwayForFood() {
            return searchService.search(FacetDescriptorPathwayForFood.class, FacetDescriptorPathwayForFood::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
