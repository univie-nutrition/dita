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
package dita.globodiet.dom.params.nutrient;

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
 * Nutrient value for nutrient-for-food-or-group.
 * The nutrient-for-food-or-group code origniates from @{table ITEMS_DEF},
 * which is cross-referencing food(-groups) with this table.
 */
@Named("dita.globodiet.params.nutrient.NutrientValue")
@DomainObject
@DomainObjectLayout(
        describedAs = "Nutrient value for nutrient-for-food-or-group.\n"
                        + "The nutrient-for-food-or-group code origniates from @{table ITEMS_DEF},\n"
                        + "which is cross-referencing food(-groups) with this table.",
        cssClassFa = "solid flask .nutrient-color,\n"
                        + "solid hashtag .nutrient-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "NTR_VALUE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class NutrientValue implements Cloneable<NutrientValue> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Unique code for nutrient-for-food-or-group.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Unique code for nutrient-for-food-or-group.",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SEQ",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int nutrientForFoodOrGroupCode;

    /**
     * Nutrient code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Nutrient code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "NTR_CODE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int nutrientCode;

    /**
     * Nutrient value for the corresponding nutrient and food (or group).
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Nutrient value for the corresponding nutrient and food (or group).",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "VAL",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double value;

    @ObjectSupport
    public String title() {
        return String.format("%s (nutrientCode=%d)", value, nutrientCode);
    }

    @Override
    public String toString() {
        return "NutrientValue(" + "nutrientForFoodOrGroupCode=" + getNutrientForFoodOrGroupCode() + ","
         +"nutrientCode=" + getNutrientCode() + ","
         +"value=" + getValue() + ")";
    }

    @Programmatic
    @Override
    public NutrientValue copy() {
        var copy = repositoryService.detachedEntity(new NutrientValue());
        copy.setNutrientForFoodOrGroupCode(getNutrientForFoodOrGroupCode());
        copy.setNutrientCode(getNutrientCode());
        copy.setValue(getValue());
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
    public NutrientValue.Manager getNavigableParent() {
        return new NutrientValue.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link NutrientValue}
     */
    @Named("dita.globodiet.params.nutrient.NutrientValue.Manager")
    @DomainObjectLayout(
            describedAs = "Nutrient value for nutrient-for-food-or-group.\n"
                            + "The nutrient-for-food-or-group code origniates from @{table ITEMS_DEF},\n"
                            + "which is cross-referencing food(-groups) with this table.",
            cssClassFa = "solid flask .nutrient-color,\n"
                            + "solid hashtag .nutrient-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Nutrient Value";
        }

        @Collection
        public final List<NutrientValue> getListOfNutrientValue() {
            return searchService.search(NutrientValue.class, NutrientValue::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
