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
package dita.globodiet.dom.params.food_coefficient;

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
 * % of fat/sauce/sweetener (FSS) added after cooking (regarding food)
 */
@Named("dita.globodiet.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "% of fat/sauce/sweetener (FSS) added after cooking (regarding food)",
        cssClassFa = "solid percent"
)
@PersistenceCapable(
        table = "SPFACOOK"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood implements Cloneable<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Food group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOOD_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodGroupCode;

    /**
     * Food subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Food subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOOD_SUBG1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubgroupCode;

    /**
     * Food sub-subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Food sub-subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOOD_SUBG2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    /**
     * Food code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Food code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOOD_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodCode;

    /**
     * Group code for fat sauce or sweetener (FSS)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
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
            sequence = "6",
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
            sequence = "7",
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
            sequence = "8",
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
            sequence = "9",
            describedAs = "Percentage of fat sauce or sweetener (FSS) that had been added",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PCT_ADDED",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double percentageAdded;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood(" + "foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"foodCode=" + getFoodCode() + ","
         +"fssGroupCode=" + getFssGroupCode() + ","
         +"fssSubgroupCode=" + getFssSubgroupCode() + ","
         +"fssSubSubgroupCode=" + getFssSubSubgroupCode() + ","
         +"fssCode=" + getFssCode() + ","
         +"percentageAdded=" + getPercentageAdded() + ")";
    }

    @Programmatic
    @Override
    public PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood copy() {
        var copy = repositoryService.detachedEntity(new PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setFoodCode(getFoodCode());
        copy.setFssGroupCode(getFssGroupCode());
        copy.setFssSubgroupCode(getFssSubgroupCode());
        copy.setFssSubSubgroupCode(getFssSubSubgroupCode());
        copy.setFssCode(getFssCode());
        copy.setPercentageAdded(getPercentageAdded());
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
    public PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.Manager getNavigableParent() {
        return new PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood}
     */
    @Named("dita.globodiet.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.Manager")
    @DomainObjectLayout(
            describedAs = "% of fat/sauce/sweetener (FSS) added after cooking (regarding food)",
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
            return "Manage Percent Of Fat Or Sauce Or Sweetener Added After Cooking For Food";
        }

        @Collection
        public final List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> getListOfPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood(
                ) {
            return searchService.search(PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class, PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
