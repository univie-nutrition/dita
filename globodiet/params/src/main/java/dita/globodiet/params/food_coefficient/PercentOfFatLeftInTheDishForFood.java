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
package dita.globodiet.params.food_coefficient;

import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
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
import org.apache.causeway.applib.annotation.Nature;
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
 * % of fat left in the dish for food
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.food_coefficient.PercentOfFatLeftInTheDishForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "% of fat left in the dish for food",
        cssClassFa = "solid percent"
)
@PersistenceCapable(
        table = "FATLEFTO"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PercentOfFatLeftInTheDishForFood implements Cloneable<PercentOfFatLeftInTheDishForFood> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Group code of the fat
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Group code of the fat",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String fatGroupCode;

    /**
     * Subgroup code of the fat
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Subgroup code of the fat",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fatSubgroupCode;

    /**
     * Sub-Subgroup code of the fat
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Sub-Subgroup code of the fat",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fatSubSubgroupCode;

    /**
     * Percentage of fat left in the dish
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Percentage of fat left in the dish",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PCT_LEFT",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double percentageOfFatLeftInTheDish;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "PercentOfFatLeftInTheDishForFood(" + "fatGroupCode=" + getFatGroupCode() + ","
         +"fatSubgroupCode=" + getFatSubgroupCode() + ","
         +"fatSubSubgroupCode=" + getFatSubSubgroupCode() + ","
         +"percentageOfFatLeftInTheDish=" + getPercentageOfFatLeftInTheDish() + ")";
    }

    @Programmatic
    @Override
    public PercentOfFatLeftInTheDishForFood copy() {
        var copy = repositoryService.detachedEntity(new PercentOfFatLeftInTheDishForFood());
        copy.setFatGroupCode(getFatGroupCode());
        copy.setFatSubgroupCode(getFatSubgroupCode());
        copy.setFatSubSubgroupCode(getFatSubSubgroupCode());
        copy.setPercentageOfFatLeftInTheDish(getPercentageOfFatLeftInTheDish());
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
    public PercentOfFatLeftInTheDishForFood.Manager getNavigableParent() {
        return new PercentOfFatLeftInTheDishForFood.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link PercentOfFatLeftInTheDishForFood}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.food_coefficient.PercentOfFatLeftInTheDishForFood.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "% of fat left in the dish for food",
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
            return "Manage Percent Of Fat Left In The Dish For Food";
        }

        @Collection
        public final List<PercentOfFatLeftInTheDishForFood> getListOfPercentOfFatLeftInTheDishForFood(
                ) {
            return searchService.search(PercentOfFatLeftInTheDishForFood.class, PercentOfFatLeftInTheDishForFood::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
