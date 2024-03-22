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
package dita.globodiet.dom.params.food_descript;

import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PrecedingParamsPolicy;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.causewaystuff.domsupport.services.lookup.Cloneable;
import org.causewaystuff.domsupport.services.lookup.HasSecondaryKey;
import org.causewaystuff.domsupport.services.lookup.ISecondaryKey;
import org.causewaystuff.domsupport.services.search.SearchService;

/**
 * Brand names are used in the food description phase
 */
@Named("dita.globodiet.params.food_descript.FoodBrand")
@DomainObject
@DomainObjectLayout(
        describedAs = "Brand names are used in the food description phase",
        cssClassFa = "solid utensils .food-color,\n"
                        + "brands shopify .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "BRANDNAM"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_FoodBrand",
        members = {"nameOfBrand", "foodGroupCode", "foodSubgroupCode", "foodSubSubgroupCode"}
)
public class FoodBrand implements Cloneable<FoodBrand>, HasSecondaryKey<FoodBrand> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Name of brand
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Name of brand",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String nameOfBrand;

    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Food group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "false",
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
            fieldSetId = "identity",
            sequence = "3",
            describedAs = "Food subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
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
            fieldSetId = "identity",
            sequence = "4",
            describedAs = "Food sub-subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    @ObjectSupport
    public String title() {
        return String.format("%s", nameOfBrand);
    }

    @Override
    public String toString() {
        return "FoodBrand(" + "nameOfBrand=" + getNameOfBrand() + ","
         +"foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ")";
    }

    @Programmatic
    @Override
    public FoodBrand copy() {
        var copy = repositoryService.detachedEntity(new FoodBrand());
        copy.setNameOfBrand(getNameOfBrand());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
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
    public FoodBrand.Manager getNavigableParent() {
        return new FoodBrand.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getNameOfBrand(), 
        getFoodGroupCode(), 
        getFoodSubgroupCode(), 
        getFoodSubSubgroupCode());
    }

    /**
     * Manager Viewmodel for @{link FoodBrand}
     */
    @Named("dita.globodiet.params.food_descript.FoodBrand.Manager")
    @DomainObjectLayout(
            describedAs = "Brand names are used in the food description phase",
            cssClassFa = "solid utensils .food-color,\n"
                            + "brands shopify .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Food Brand";
        }

        @Collection
        public final List<FoodBrand> getListOfFoodBrand() {
            return searchService.search(FoodBrand.class, FoodBrand::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link FoodBrand}
     * @param nameOfBrand Name of brand
     * @param foodGroup Food group code
     * @param foodSubgroup Food subgroup code
     * @param foodSubSubgroup Food sub-subgroup code
     */
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Name of brand"
            )
            String nameOfBrand,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Food group code"
            )
            FoodGroup foodGroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food subgroup code"
            )
            FoodSubgroup foodSubgroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food sub-subgroup code"
            )
            FoodSubgroup foodSubSubgroup) {
    }

    /**
     * SecondaryKey for @{link FoodBrand}
     * @param nameOfBrand Name of brand
     * @param foodGroupCode Food group code
     * @param foodSubgroupCode Food subgroup code
     * @param foodSubSubgroupCode Food sub-subgroup code
     */
    public final record SecondaryKey(
            String nameOfBrand,
            String foodGroupCode,
            String foodSubgroupCode,
            String foodSubSubgroupCode) implements ISecondaryKey<FoodBrand> {
        @Override
        public Class<FoodBrand> correspondingClass() {
            return FoodBrand.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodBrand} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable FoodBrand",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.food_descript.FoodBrand.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends FoodBrand implements ViewModel {
        @Getter(
                onMethod_ = {@Override}
        )
        @Accessors(
                fluent = true
        )
        private final String viewModelMemento;

        @Override
        public String title() {
            return viewModelMemento;
        }
    }
}
