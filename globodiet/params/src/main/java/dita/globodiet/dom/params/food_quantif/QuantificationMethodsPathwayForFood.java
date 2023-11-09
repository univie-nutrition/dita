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
package dita.globodiet.dom.params.food_quantif;

import dita.commons.services.lookup.Cloneable;
import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
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
 * Quantification methods pathway for individual foods
 */
@Named("dita.globodiet.params.food_quantif.QuantificationMethodsPathwayForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "Quantification methods pathway for individual foods"
)
@PersistenceCapable(
        table = "QM_FOODS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class QuantificationMethodsPathwayForFood implements Cloneable<QuantificationMethodsPathwayForFood> {
    @Inject
    RepositoryService repositoryService;

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
     * Quantification method code:
     * 'P' for photo,
     * 'H' for HHM,
     * 'U' for stdu,
     * 'S' for standard portion,
     * 'A' for shape
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Quantification method code:\n"
                            + "'P' for photo,\n"
                            + "'H' for HHM,\n"
                            + "'U' for stdu,\n"
                            + "'S' for standard portion,\n"
                            + "'A' for shape",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "METHOD",
            allowsNull = "false",
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
    private QuantificationMethod quantificationMethod;

    /**
     * Photo code (if method='P' and 'A');
     * either M_photos.ph_code or M_shapes.sh_code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Photo code (if method='P' and 'A');\n"
                            + "either M_photos.ph_code or M_shapes.sh_code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "METH_CODE",
            allowsNull = "true",
            length = 4
    )
    @Getter
    @Setter
    private String photoCode;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "QuantificationMethodsPathwayForFood(" + "foodCode=" + getFoodCode() + ","
         +"quantificationMethod=" + getQuantificationMethod() + ","
         +"photoCode=" + getPhotoCode() + ")";
    }

    @Programmatic
    @Override
    public QuantificationMethodsPathwayForFood copy() {
        var copy = repositoryService.detachedEntity(new QuantificationMethodsPathwayForFood());
        copy.setFoodCode(getFoodCode());
        copy.setQuantificationMethod(getQuantificationMethod());
        copy.setPhotoCode(getPhotoCode());
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
    public QuantificationMethodsPathwayForFood.Manager getNavigableParent() {
        return new QuantificationMethodsPathwayForFood.Manager(searchService, "");
    }

    @RequiredArgsConstructor
    public enum QuantificationMethod {
        /**
         * no description
         */
        PHOTO("P", "Photo"),

        /**
         * no description
         */
        HOUSEHOLD_MEASURE("H", "Household Measure"),

        /**
         * no description
         */
        STANDARD_UNIT("U", "Standard Unit"),

        /**
         * no description
         */
        STANDARD_PORTION("S", "Standard Portion"),

        /**
         * no description
         */
        SHAPE("A", "Shape");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link QuantificationMethodsPathwayForFood}
     */
    @Named("dita.globodiet.params.food_quantif.QuantificationMethodsPathwayForFood.Manager")
    @DomainObjectLayout(
            describedAs = "Quantification methods pathway for individual foods"
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
            return "Manage Quantification Methods Pathway For Food";
        }

        @Collection
        public final List<QuantificationMethodsPathwayForFood> getListOfQuantificationMethodsPathwayForFood(
                ) {
            return searchService.search(QuantificationMethodsPathwayForFood.class, QuantificationMethodsPathwayForFood::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
