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
package dita.globodiet.dom.params.food_list;

import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
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
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Composed Recipe Ingredient
 */
@Named("dita.globodiet.params.food_list.ComposedRecipeIngredient")
@DomainObject
@DomainObjectLayout(
        describedAs = "Composed Recipe Ingredient"
)
@PersistenceCapable(
        table = "CRING"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class ComposedRecipeIngredient {
    /**
     * Recipe identification number (FOODNUM in the FOODS.DBF file)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Recipe identification number (FOODNUM in the FOODS.DBF file)",
            hidden = Where.NOWHERE
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
     * Food (ingredient) Identification Code
     * either Foods.foodnum OR Mixedrec.r_idnum
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Food (ingredient) Identification Code\n"
                            + "either Foods.foodnum OR Mixedrec.r_idnum",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOODNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodOrRecipeCode;

    /**
     * If type=1 ingredient from Food list,
     * If type=2 ingredient from Recipe list
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "If type=1 ingredient from Food list,\n"
                            + "If type=2 ingredient from Recipe list",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private Type type;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @RequiredArgsConstructor
    public enum Type {
        /**
         * ingredient from Food list
         */
        FOOD("1", "Food"),

        /**
         * ingredient from Recipe list
         */
        RECIPE("2", "Recipe");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link ComposedRecipeIngredient}
     */
    @Named("dita.globodiet.params.food_list.ComposedRecipeIngredient.Manager")
    @DomainObjectLayout(
            describedAs = "Composed Recipe Ingredient"
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
            return "Manage Composed Recipe Ingredient";
        }

        @Collection
        public final List<ComposedRecipeIngredient> getListOfComposedRecipeIngredient() {
            return searchService.search(ComposedRecipeIngredient.class, ComposedRecipeIngredient::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
