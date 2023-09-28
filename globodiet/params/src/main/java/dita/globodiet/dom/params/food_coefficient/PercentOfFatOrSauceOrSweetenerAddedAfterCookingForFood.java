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
package dita.globodiet.dom.params.food_coefficient;

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
 * % of fat/sauce/sweetener added after cooking attached to a food
 */
@Named("dita.globodiet.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "% of fat/sauce/sweetener added after cooking attached to a food",
        cssClassFa = "solid percent"
)
@PersistenceCapable(
        table = "SPFACOOK"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood {
    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food group code\n"
                            + "----\n"
                            + "required=false, unique=false",
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
            sequence = "2",
            describedAs = "Food subgroup code\n"
                            + "----\n"
                            + "required=false, unique=false",
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
     * Food sub-Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food sub-Subgroup code\n"
                            + "----\n"
                            + "required=false, unique=false",
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
            sequence = "4",
            describedAs = "Food code\n"
                            + "----\n"
                            + "required=false, unique=false",
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
     * Fat group code for F/S/S
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "5",
            describedAs = "Fat group code for F/S/S\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fssFatGroupCode;

    /**
     * Fat subgroup code for F/S/S
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "6",
            describedAs = "Fat subgroup code for F/S/S\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_SUBG1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fssFatSubgroupCode;

    /**
     * Fat sub-subgroup code for F/S/S
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "7",
            describedAs = "Fat sub-subgroup code for F/S/S\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_SUBG2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fssFatSubSubgroupCode;

    /**
     * Fat code for F/S/S
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "8",
            describedAs = "Fat code for F/S/S\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "X_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String fssFatCode;

    /**
     * Percentage of fat
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Percentage of fat\n"
                            + "----\n"
                            + "required=true, unique=false",
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

    /**
     * Manager Viewmodel for @{link PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood}
     */
    @Named("dita.globodiet.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.Manager")
    @DomainObjectLayout(
            describedAs = "% of fat/sauce/sweetener added after cooking attached to a food",
            cssClassFa = "solid percent"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
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
