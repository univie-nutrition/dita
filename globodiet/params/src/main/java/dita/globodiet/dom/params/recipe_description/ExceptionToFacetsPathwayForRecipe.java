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
 * Exceptions for some Recipes to the Facets/Descriptors pathway
 */
@Named("dita.globodiet.params.recipe_description.ExceptionToFacetsPathwayForRecipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "Exceptions for some Recipes to the Facets/Descriptors pathway"
)
@PersistenceCapable(
        table = "R_RCPFAEX"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class ExceptionToFacetsPathwayForRecipe {
    /**
     * Recipe ID number
     */
    @Property
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
     * Recipe Facet codes that MUST appear in the sequence of facets corresponding to this recipe
     * (superseeding its group pathway).
     * The list of descriptors will be the ones defined for the subgroup in R_GROUPFAC file
     * (Assuming always a subset)
     */
    @Property
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Recipe Facet codes that MUST appear in the sequence of facets corresponding to this recipe\n"
                            + "(superseeding its group pathway).\n"
                            + "The list of descriptors will be the ones defined for the subgroup in R_GROUPFAC file\n"
                            + "(Assuming always a subset)",
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

    /**
     * Manager Viewmodel for @{link ExceptionToFacetsPathwayForRecipe}
     */
    @Named("dita.globodiet.params.recipe_description.ExceptionToFacetsPathwayForRecipe.Manager")
    @DomainObjectLayout(
            describedAs = "Exceptions for some Recipes to the Facets/Descriptors pathway"
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
            return "Manage Exception To Facets Pathway For Recipe";
        }

        @Collection
        public final List<ExceptionToFacetsPathwayForRecipe> getListOfExceptionToFacetsPathwayForRecipe(
                ) {
            return searchService.search(ExceptionToFacetsPathwayForRecipe.class, ExceptionToFacetsPathwayForRecipe::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
