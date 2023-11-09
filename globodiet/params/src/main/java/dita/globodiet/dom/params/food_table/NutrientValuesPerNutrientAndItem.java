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
package dita.globodiet.dom.params.food_table;

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
 * Nutrient values per nutrient and item
 */
@Named("dita.globodiet.params.food_table.NutrientValuesPerNutrientAndItem")
@DomainObject
@DomainObjectLayout(
        describedAs = "Nutrient values per nutrient and item"
)
@PersistenceCapable(
        table = "NTR_VALUE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class NutrientValuesPerNutrientAndItem implements Cloneable<NutrientValuesPerNutrientAndItem> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Item Sequential number
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Item Sequential number",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SEQ",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int itemSequentialNumber;

    /**
     * Nutrient code
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Nutrient code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_CODE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int nutrientCode;

    /**
     * Nutrient value for the attached item & nutrient
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Nutrient value for the attached item & nutrient",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "VAL",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double nutrientValueForTheAttachedItemAndNutrient;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "NutrientValuesPerNutrientAndItem(" + "itemSequentialNumber=" + getItemSequentialNumber() + ","
         +"nutrientCode=" + getNutrientCode() + ","
         +"nutrientValueForTheAttachedItemAndNutrient=" + getNutrientValueForTheAttachedItemAndNutrient() + ")";
    }

    @Programmatic
    @Override
    public NutrientValuesPerNutrientAndItem copy() {
        var copy = repositoryService.detachedEntity(new NutrientValuesPerNutrientAndItem());
        copy.setItemSequentialNumber(getItemSequentialNumber());
        copy.setNutrientCode(getNutrientCode());
        copy.setNutrientValueForTheAttachedItemAndNutrient(getNutrientValueForTheAttachedItemAndNutrient());
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
    public NutrientValuesPerNutrientAndItem.Manager getNavigableParent() {
        return new NutrientValuesPerNutrientAndItem.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link NutrientValuesPerNutrientAndItem}
     */
    @Named("dita.globodiet.params.food_table.NutrientValuesPerNutrientAndItem.Manager")
    @DomainObjectLayout(
            describedAs = "Nutrient values per nutrient and item"
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
            return "Manage Nutrient Values Per Nutrient And Item";
        }

        @Collection
        public final List<NutrientValuesPerNutrientAndItem> getListOfNutrientValuesPerNutrientAndItem(
                ) {
            return searchService.search(NutrientValuesPerNutrientAndItem.class, NutrientValuesPerNutrientAndItem::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
