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
package dita.globodiet.dom.params.recipe_coefficient;

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
import org.causewaystuff.domsupport.services.lookup.Cloneable;
import org.causewaystuff.domsupport.services.search.SearchService;

/**
 * % of fat/sauce/sweetener (FSS) added after cooking (regarding recipes)
 */
@Named("dita.globodiet.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "% of fat/sauce/sweetener (FSS) added after cooking (regarding recipes)",
        cssClassFa = "solid percent"
)
@PersistenceCapable(
        table = "SPFACORE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe implements Cloneable<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RECI_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Recipe subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RECI_SUBGR",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeSubgroupCode;

    /**
     * Recipe code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Recipe code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RECI_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    /**
     * Group code for fat sauce or sweetener (FSS)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Group code for fat sauce or sweetener (FSS)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fssGroupCode;

    /**
     * Subgroup code for fat sauce or sweetener (FSS)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
            describedAs = "Subgroup code for fat sauce or sweetener (FSS)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_SUBG1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fssSubgroupCode;

    /**
     * Sub-subgroup code for fat sauce or sweetener (FSS)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "6",
            describedAs = "Sub-subgroup code for fat sauce or sweetener (FSS)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_SUBG2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fssSubSubgroupCode;

    /**
     * Code for fat sauce or sweetener (FSS)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "7",
            describedAs = "Code for fat sauce or sweetener (FSS)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String fssCode;

    /**
     * Percentage of fat sauce or sweetener (FSS) that had been added
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Percentage of fat sauce or sweetener (FSS) that had been added",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PCT_ADDED",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double percentageOfFat;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe(" + "recipeGroupCode=" + getRecipeGroupCode() + ","
         +"recipeSubgroupCode=" + getRecipeSubgroupCode() + ","
         +"recipeCode=" + getRecipeCode() + ","
         +"fssGroupCode=" + getFssGroupCode() + ","
         +"fssSubgroupCode=" + getFssSubgroupCode() + ","
         +"fssSubSubgroupCode=" + getFssSubSubgroupCode() + ","
         +"fssCode=" + getFssCode() + ","
         +"percentageOfFat=" + getPercentageOfFat() + ")";
    }

    @Programmatic
    @Override
    public PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe copy() {
        var copy = repositoryService.detachedEntity(new PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe());
        copy.setRecipeGroupCode(getRecipeGroupCode());
        copy.setRecipeSubgroupCode(getRecipeSubgroupCode());
        copy.setRecipeCode(getRecipeCode());
        copy.setFssGroupCode(getFssGroupCode());
        copy.setFssSubgroupCode(getFssSubgroupCode());
        copy.setFssSubSubgroupCode(getFssSubSubgroupCode());
        copy.setFssCode(getFssCode());
        copy.setPercentageOfFat(getPercentageOfFat());
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
    public PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.Manager getNavigableParent() {
        return new PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe}
     */
    @Named("dita.globodiet.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.Manager")
    @DomainObjectLayout(
            describedAs = "% of fat/sauce/sweetener (FSS) added after cooking (regarding recipes)",
            cssClassFa = "solid percent"
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
            return "Manage Percent Of Fat Or Sauce Or Sweetener Added After Cooking For Recipe";
        }

        @Collection
        public final List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> getListOfPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe(
                ) {
            return searchService.search(PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class, PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
