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
package dita.globodiet.dom.params.quantif;

import dita.commons.services.lookup.Cloneable;
import dita.commons.services.lookup.HasSecondaryKey;
import dita.commons.services.lookup.ISecondaryKey;
import dita.commons.services.search.SearchService;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.classification.RecipeSubgroup;
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
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * Thickness for shape method
 */
@Named("dita.globodiet.params.quantif.ThicknessForShapeMethod")
@DomainObject
@DomainObjectLayout(
        describedAs = "Thickness for shape method"
)
@PersistenceCapable(
        table = "THICKNESS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class ThicknessForShapeMethod implements Cloneable<ThicknessForShapeMethod>, HasSecondaryKey<ThicknessForShapeMethod> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Thickness code (e.g. A,B,C,58_1,58_2...)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Thickness code (e.g. A,B,C,58_1,58_2...)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TH_CODE",
            allowsNull = "false",
            length = 10
    )
    @Getter
    @Setter
    private String code;

    /**
     * has no description
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "has no description",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TH_THICK",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double thickness;

    /**
     * Comment attached to the thickness (e.g. small, medium, large…)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Comment attached to the thickness (e.g. small, medium, large…)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TH_COMMENT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String comment;

    /**
     * For the food items, the food (sub)groups for which this thickness has to be proposed.
     * These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)
     * When this field is empty, that means that this thickness has always to be proposed
     * whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1
     * and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "For the food items, the food (sub)groups for which this thickness has to be proposed.\n"
                            + "These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)\n"
                            + "When this field is empty, that means that this thickness has always to be proposed\n"
                            + "whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1\n"
                            + "and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "TH_FDCLASS",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String foodSubgroupsLookupKey;

    /**
     * For the recipe items, the recipe (sub)groups for which this thickness has to be proposed.
     * These (sub)groups have to be separated with a comma (e.g. 01,02,0301)
     * When this field is empty, that means that this thickness has always to be proposed
     * whatever the recipe classification; muliple rsubgr.group and/or rsubgr.subgroup commaseparated (e.g. 01,0601)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
            describedAs = "For the recipe items, the recipe (sub)groups for which this thickness has to be proposed.\n"
                            + "These (sub)groups have to be separated with a comma (e.g. 01,02,0301)\n"
                            + "When this field is empty, that means that this thickness has always to be proposed\n"
                            + "whatever the recipe classification; muliple rsubgr.group and/or rsubgr.subgroup commaseparated (e.g. 01,0601)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "TH_RCPCLASS",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String recipeSubgroupsLookupKey;

    @ObjectSupport
    public String title() {
        return String.format("%f (code=%s)", thickness);
    }

    @Override
    public String toString() {
        return "ThicknessForShapeMethod(" + "code=" + getCode() + ","
         +"thickness=" + getThickness() + ","
         +"comment=" + getComment() + ","
         +"foodSubgroupsLookupKey=" + getFoodSubgroupsLookupKey() + ","
         +"recipeSubgroupsLookupKey=" + getRecipeSubgroupsLookupKey() + ")";
    }

    @Programmatic
    @Override
    public ThicknessForShapeMethod copy() {
        var copy = repositoryService.detachedEntity(new ThicknessForShapeMethod());
        copy.setCode(getCode());
        copy.setThickness(getThickness());
        copy.setComment(getComment());
        copy.setFoodSubgroupsLookupKey(getFoodSubgroupsLookupKey());
        copy.setRecipeSubgroupsLookupKey(getRecipeSubgroupsLookupKey());
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
    public ThicknessForShapeMethod.Manager getNavigableParent() {
        return new ThicknessForShapeMethod.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    /**
     * Manager Viewmodel for @{link ThicknessForShapeMethod}
     */
    @Named("dita.globodiet.params.quantif.ThicknessForShapeMethod.Manager")
    @DomainObjectLayout(
            describedAs = "Thickness for shape method"
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
            return "Manage Thickness For Shape Method";
        }

        @Collection
        public final List<ThicknessForShapeMethod> getListOfThicknessForShapeMethod() {
            return searchService.search(ThicknessForShapeMethod.class, ThicknessForShapeMethod::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link ThicknessForShapeMethod}
     * @param code Thickness code (e.g. A,B,C,58_1,58_2...)
     * @param thickness has no description
     * @param comment Comment attached to the thickness (e.g. small, medium, large…)
     * @param foodSubgroups For the food items, the food (sub)groups for which this thickness has to be proposed.
     * These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)
     * When this field is empty, that means that this thickness has always to be proposed
     * whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1
     * and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)
     * @param recipeSubgroups For the recipe items, the recipe (sub)groups for which this thickness has to be proposed.
     * These (sub)groups have to be separated with a comma (e.g. 01,02,0301)
     * When this field is empty, that means that this thickness has always to be proposed
     * whatever the recipe classification; muliple rsubgr.group and/or rsubgr.subgroup commaseparated (e.g. 01,0601)
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Thickness code (e.g. A,B,C,58_1,58_2...)"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "has no description"
            )
            double thickness,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Comment attached to the thickness (e.g. small, medium, large…)"
            )
            String comment,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "For the food items, the food (sub)groups for which this thickness has to be proposed.\n"
                                    + "These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)\n"
                                    + "When this field is empty, that means that this thickness has always to be proposed\n"
                                    + "whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1\n"
                                    + "and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)"
            )
            FoodSubgroup foodSubgroups,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "For the recipe items, the recipe (sub)groups for which this thickness has to be proposed.\n"
                                    + "These (sub)groups have to be separated with a comma (e.g. 01,02,0301)\n"
                                    + "When this field is empty, that means that this thickness has always to be proposed\n"
                                    + "whatever the recipe classification; muliple rsubgr.group and/or rsubgr.subgroup commaseparated (e.g. 01,0601)"
            )
            RecipeSubgroup recipeSubgroups) {
    }

    /**
     * SecondaryKey for @{link ThicknessForShapeMethod}
     * @param code Thickness code (e.g. A,B,C,58_1,58_2...)
     */
    public final record SecondaryKey(
            String code) implements ISecondaryKey<ThicknessForShapeMethod> {
        @Override
        public Class<ThicknessForShapeMethod> correspondingClass() {
            return ThicknessForShapeMethod.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link ThicknessForShapeMethod} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable ThicknessForShapeMethod",
            cssClassFa = "skull red"
    )
    @Named("dita.globodiet.params.quantif.ThicknessForShapeMethod.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends ThicknessForShapeMethod implements ViewModel {
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
