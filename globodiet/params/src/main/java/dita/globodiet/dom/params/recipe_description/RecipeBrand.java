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
 * Brandname list for mixed recipes
 */
@Named("dita.globodiet.params.recipe_description.RecipeBrand")
@DomainObject
@DomainObjectLayout(
        describedAs = "Brandname list for mixed recipes",
        cssClassFa = "solid stroopwafel .recipe-color,\n"
                        + "brands shopify .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "R_BRAND"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeBrand implements Cloneable<RecipeBrand> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe group
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe group",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RGROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe subgroup
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Recipe subgroup",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RSUBGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeSubgroupCode;

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
            name = "RNAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String recipeName;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s)", recipeName, recipeGroupCode, recipeSubgroupCode);
    }

    @Override
    public String toString() {
        return "RecipeBrand(" + "recipeGroupCode=" + getRecipeGroupCode() + ","
         +"recipeSubgroupCode=" + getRecipeSubgroupCode() + ","
         +"recipeName=" + getRecipeName() + ")";
    }

    @Programmatic
    @Override
    public RecipeBrand copy() {
        var copy = repositoryService.detachedEntity(new RecipeBrand());
        copy.setRecipeGroupCode(getRecipeGroupCode());
        copy.setRecipeSubgroupCode(getRecipeSubgroupCode());
        copy.setRecipeName(getRecipeName());
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
    public RecipeBrand.Manager getNavigableParent() {
        return new RecipeBrand.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link RecipeBrand}
     */
    @Named("dita.globodiet.params.recipe_description.RecipeBrand.Manager")
    @DomainObjectLayout(
            describedAs = "Brandname list for mixed recipes",
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                            + "brands shopify .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Recipe Brand";
        }

        @Collection
        public final List<RecipeBrand> getListOfRecipeBrand() {
            return searchService.search(RecipeBrand.class, RecipeBrand::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
