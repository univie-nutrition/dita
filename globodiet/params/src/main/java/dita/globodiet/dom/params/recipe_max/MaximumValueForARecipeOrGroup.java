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
package dita.globodiet.dom.params.recipe_max;

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
 * Maximum value for a recipe or a (sub-)group
 */
@Named("dita.globodiet.params.recipe_max.MaximumValueForARecipeOrGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Maximum value for a recipe or a (sub-)group"
)
@PersistenceCapable(
        table = "MAXRVAL"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class MaximumValueForARecipeOrGroup {
    /**
     * Recipe group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe group code\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_GROUP",
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
            describedAs = "Recipe subgroup code\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_SUBGROUP",
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
            describedAs = "Recipe code\n"
                            + "----\n"
                            + "required=false, unique=true",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    /**
     * Maximum value
     */
    @Property
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Maximum value\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "MAXIMA",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double maximumValue;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    /**
     * Manager Viewmodel for @{link MaximumValueForARecipeOrGroup}
     */
    @Named("dita.globodiet.params.recipe_max.MaximumValueForARecipeOrGroup.Manager")
    @DomainObjectLayout(
            describedAs = "Maximum value for a recipe or a (sub-)group"
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
            return "Manage Maximum Value For A Recipe Or Group";
        }

        @Collection
        public final List<MaximumValueForARecipeOrGroup> getListOfMaximumValueForARecipeOrGroup() {
            return searchService.search(MaximumValueForARecipeOrGroup.class, MaximumValueForARecipeOrGroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
