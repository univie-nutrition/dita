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
package dita.globodiet.dom.params.recipe_quantif;

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
 * Quantification methods pathway for recipe group/subgroup.
 * Optionally can be superseded by @{table QM_RECIP}.
 */
@Named("dita.globodiet.params.recipe_quantif.QuantificationMethodPathwayForRecipeGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Quantification methods pathway for recipe group/subgroup.\n"
                        + "Optionally can be superseded by @{table QM_RECIP}.",
        cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                        + "solid scale-balanced .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                        + "solid exclamation-circle .recipe-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "QM_RCLAS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class QuantificationMethodPathwayForRecipeGroup implements Cloneable<QuantificationMethodPathwayForRecipeGroup> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe group
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe group",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroup;

    /**
     * Quantification method code:
     * 'P' for photo,
     * 'H' for HHM,
     * 'U' for stdu,
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

    /**
     * Comment
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Comment",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "COMMENT",
            allowsNull = "false",
            length = 200
    )
    @Getter
    @Setter
    private String comment;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "QuantificationMethodPathwayForRecipeGroup(" + "recipeGroup=" + getRecipeGroup() + ","
         +"quantificationMethod=" + getQuantificationMethod() + ","
         +"photoCode=" + getPhotoCode() + ","
         +"comment=" + getComment() + ")";
    }

    @Programmatic
    @Override
    public QuantificationMethodPathwayForRecipeGroup copy() {
        var copy = repositoryService.detachedEntity(new QuantificationMethodPathwayForRecipeGroup());
        copy.setRecipeGroup(getRecipeGroup());
        copy.setQuantificationMethod(getQuantificationMethod());
        copy.setPhotoCode(getPhotoCode());
        copy.setComment(getComment());
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
    public QuantificationMethodPathwayForRecipeGroup.Manager getNavigableParent() {
        return new QuantificationMethodPathwayForRecipeGroup.Manager(searchService, "");
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
     * Manager Viewmodel for @{link QuantificationMethodPathwayForRecipeGroup}
     */
    @Named("dita.globodiet.params.recipe_quantif.QuantificationMethodPathwayForRecipeGroup.Manager")
    @DomainObjectLayout(
            describedAs = "Quantification methods pathway for recipe group/subgroup.\n"
                            + "Optionally can be superseded by @{table QM_RECIP}.",
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid scale-balanced .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                            + "solid exclamation-circle .recipe-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
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
            return "Manage Quantification Method Pathway For Recipe Group";
        }

        @Collection
        public final List<QuantificationMethodPathwayForRecipeGroup> getListOfQuantificationMethodPathwayForRecipeGroup(
                ) {
            return searchService.search(QuantificationMethodPathwayForRecipeGroup.class, QuantificationMethodPathwayForRecipeGroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
