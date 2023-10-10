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
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;

/**
 * Raw to cooked conversion factors for foods
 */
@Named("dita.globodiet.params.food_coefficient.RawToCookedConversionFactorForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "Raw to cooked conversion factors for foods"
)
@PersistenceCapable(
        table = "RAWCOOK"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RawToCookedConversionFactorForFood {
    @Inject
    SearchService searchService;

    /**
     * Food identification number (FOODNUM)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Food identification number (FOODNUM)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "ID_NUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String foodCode;

    /**
     * Raw to cooked factor
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Raw to cooked factor",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RC_FACTOR",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double rawToCookedFactor;

    /**
     * Facet string; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Facet string; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACETS_STR",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String facetDescriptorsLookupKey;

    /**
     * Priority order
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Priority order",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PRIORITY",
            allowsNull = "false",
            length = 3
    )
    @Getter
    @Setter
    private String priority;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "RawToCookedConversionFactorForFood(" + "foodCode=" + getFoodCode() + ","
         +"rawToCookedFactor=" + getRawToCookedFactor() + ","
         +"facetDescriptorsLookupKey=" + getFacetDescriptorsLookupKey() + ","
         +"priority=" + getPriority() + ")";
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            navigable = Navigable.PARENT,
            hidden = Where.EVERYWHERE
    )
    @NotPersistent
    public RawToCookedConversionFactorForFood.Manager getNavigableParent() {
        return new RawToCookedConversionFactorForFood.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link RawToCookedConversionFactorForFood}
     */
    @Named("dita.globodiet.params.food_coefficient.RawToCookedConversionFactorForFood.Manager")
    @DomainObjectLayout(
            describedAs = "Raw to cooked conversion factors for foods"
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
            return "Manage Raw To Cooked Conversion Factor For Food";
        }

        @Collection
        public final List<RawToCookedConversionFactorForFood> getListOfRawToCookedConversionFactorForFood(
                ) {
            return searchService.search(RawToCookedConversionFactorForFood.class, RawToCookedConversionFactorForFood::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
