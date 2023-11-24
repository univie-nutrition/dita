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
package dita.globodiet.dom.params.recipe_description;

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
 * Facet/descriptor pathway for individual recipe.
 * Supersedes this Recipe's group facet/descriptor pathway from @{table R_GROUPFAC}.
 */
@Named("dita.globodiet.params.recipe_description.FacetDescriptorPathwayForRecipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet/descriptor pathway for individual recipe.\n"
                        + "Supersedes this Recipe's group facet/descriptor pathway from @{table R_GROUPFAC}.",
        cssClassFa = "solid person-walking-arrow-right .recipe-color"
)
@PersistenceCapable(
        table = "R_RCPFAEX"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FacetDescriptorPathwayForRecipe implements Cloneable<FacetDescriptorPathwayForRecipe> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe ID number
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe ID number",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    /**
     * Recipe Facet codes that will appear in the sequence of facets corresponding to this Recipe
     * (supersedes this Recipe's group pathway).
     * The list of descriptors will be the ones defined for the subgroup in @{table R_GROUPFAC}.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Recipe Facet codes that will appear in the sequence of facets corresponding to this Recipe\n"
                            + "(supersedes this Recipe's group pathway).\n"
                            + "The list of descriptors will be the ones defined for the subgroup in @{table R_GROUPFAC}.",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeFacetCode;

    /**
     * has no description
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "has no description",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ORDER_FAC",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int order;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "FacetDescriptorPathwayForRecipe(" + "recipeCode=" + getRecipeCode() + ","
         +"recipeFacetCode=" + getRecipeFacetCode() + ","
         +"order=" + getOrder() + ")";
    }

    @Programmatic
    @Override
    public FacetDescriptorPathwayForRecipe copy() {
        var copy = repositoryService.detachedEntity(new FacetDescriptorPathwayForRecipe());
        copy.setRecipeCode(getRecipeCode());
        copy.setRecipeFacetCode(getRecipeFacetCode());
        copy.setOrder(getOrder());
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
    public FacetDescriptorPathwayForRecipe.Manager getNavigableParent() {
        return new FacetDescriptorPathwayForRecipe.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link FacetDescriptorPathwayForRecipe}
     */
    @Named("dita.globodiet.params.recipe_description.FacetDescriptorPathwayForRecipe.Manager")
    @DomainObjectLayout(
            describedAs = "Facet/descriptor pathway for individual recipe.\n"
                            + "Supersedes this Recipe's group facet/descriptor pathway from @{table R_GROUPFAC}.",
            cssClassFa = "solid person-walking-arrow-right .recipe-color"
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
            return "Manage Facet Descriptor Pathway For Recipe";
        }

        @Collection
        public final List<FacetDescriptorPathwayForRecipe> getListOfFacetDescriptorPathwayForRecipe(
                ) {
            return searchService.search(FacetDescriptorPathwayForRecipe.class, FacetDescriptorPathwayForRecipe::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
