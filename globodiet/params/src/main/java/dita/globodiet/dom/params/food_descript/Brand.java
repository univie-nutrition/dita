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
package dita.globodiet.dom.params.food_descript;

import dita.commons.services.lookup.HasSecondaryKey;
import dita.commons.services.lookup.ISecondaryKey;
import dita.commons.services.search.SearchService;
import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DependentDefaultsPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Brand names are used in the food description phase
 */
@Named("dita.globodiet.params.food_descript.Brand")
@DomainObject
@DomainObjectLayout(
        describedAs = "Brand names are used in the food description phase",
        cssClassFa = "brands shopify deeppink"
)
@PersistenceCapable(
        table = "BRANDNAM"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class Brand implements HasSecondaryKey<Brand> {
    /**
     * Name of brand
     */
    @Property
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
    @Property
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

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getNameOfBrand(), getFoodGroupCode(), getFoodSubgroupCode(), getFoodSubSubgroupCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getNameOfBrand(), getFoodGroupCode(), getFoodSubgroupCode(), getFoodSubSubgroupCode())));
    }

    /**
     * Manager Viewmodel for @{link Brand}
     */
    @Named("dita.globodiet.params.food_descript.Brand.Manager")
    @DomainObjectLayout(
            describedAs = "Brand names are used in the food description phase",
            cssClassFa = "brands shopify deeppink"
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
            return "Manage Brand";
        }

        @Collection
        public final List<Brand> getListOfBrand() {
            return searchService.search(Brand.class, Brand::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Brand}
     * @param nameOfBrand Name of brand
     * @param foodGroup Food group code
     * @param foodSubgroup Food subgroup code
     * @param foodSubSubgroup Food sub-subgroup code
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Name of brand"
            )
            String nameOfBrand,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Food group code"
            )
            FoodGroup foodGroup,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.UPDATE_DEPENDENT,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food subgroup code"
            )
            FoodSubgroup foodSubgroup,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.UPDATE_DEPENDENT,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food sub-subgroup code"
            )
            FoodSubgroup foodSubSubgroup) {
    }

    /**
     * SecondaryKey for @{link Brand}
     * @param nameOfBrand Name of brand
     * @param foodGroupCode Food group code
     * @param foodSubgroupCode Food subgroup code
     * @param foodSubSubgroupCode Food sub-subgroup code
     */
    public final record SecondaryKey(
            String nameOfBrand,
            String foodGroupCode,
            String foodSubgroupCode,
            String foodSubSubgroupCode) implements ISecondaryKey<Brand> {
        @Override
        public Class<Brand> correspondingClass() {
            return Brand.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Brand} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable Brand",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends Brand implements ViewModel {
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
