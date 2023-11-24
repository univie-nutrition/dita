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
package dita.globodiet.dom.params.classification;

import dita.commons.services.lookup.Cloneable;
import dita.commons.services.lookup.HasSecondaryKey;
import dita.commons.services.lookup.ISecondaryKey;
import dita.commons.services.search.SearchService;
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
 * Recipe subgroup
 */
@Named("dita.globodiet.params.classification.RecipeSubgroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Recipe subgroup",
        cssClassFa = "solid layer-group .recipe-color,\n"
                        + "solid circle-chevron-down .recipe-color .fa-overlay .fa-overlay-right-bottom-65\n"
)
@PersistenceCapable(
        table = "RSUBGR"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeSubgroup implements Cloneable<RecipeSubgroup>, RecipeGrouping, HasSecondaryKey<RecipeSubgroup> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe group code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Recipe group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe sub-group code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Recipe sub-group code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SUBGROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Name of the recipe (sub-)group
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Name of the recipe (sub-)group",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Short Name of the recipe (sub-)group
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Short Name of the recipe (sub-)group",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAMES_SHORT",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String shortName;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s)", name, recipeGroupCode, code);
    }

    @Override
    public String toString() {
        return "RecipeSubgroup(" + "recipeGroupCode=" + getRecipeGroupCode() + ","
         +"code=" + getCode() + ","
         +"name=" + getName() + ","
         +"shortName=" + getShortName() + ")";
    }

    @Programmatic
    @Override
    public RecipeSubgroup copy() {
        var copy = repositoryService.detachedEntity(new RecipeSubgroup());
        copy.setRecipeGroupCode(getRecipeGroupCode());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setShortName(getShortName());
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
    public RecipeSubgroup.Manager getNavigableParent() {
        return new RecipeSubgroup.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getRecipeGroupCode(), getCode());
    }

    /**
     * Manager Viewmodel for @{link RecipeSubgroup}
     */
    @Named("dita.globodiet.params.classification.RecipeSubgroup.Manager")
    @DomainObjectLayout(
            describedAs = "Recipe subgroup",
            cssClassFa = "solid layer-group .recipe-color,\n"
                            + "solid circle-chevron-down .recipe-color .fa-overlay .fa-overlay-right-bottom-65\n"
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
            return "Manage Recipe Subgroup";
        }

        @Collection
        public final List<RecipeSubgroup> getListOfRecipeSubgroup() {
            return searchService.search(RecipeSubgroup.class, RecipeSubgroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link RecipeSubgroup}
     * @param recipeGroup Recipe group code
     * @param code Recipe sub-group code
     * @param name Name of the recipe (sub-)group
     * @param shortName Short Name of the recipe (sub-)group
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Recipe group code"
            )
            RecipeGroup recipeGroup,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Recipe sub-group code"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Name of the recipe (sub-)group"
            )
            String name,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Short Name of the recipe (sub-)group"
            )
            String shortName) {
    }

    /**
     * SecondaryKey for @{link RecipeSubgroup}
     * @param recipeGroupCode Recipe group code
     * @param code Recipe sub-group code
     */
    public final record SecondaryKey(
            String recipeGroupCode,
            String code) implements ISecondaryKey<RecipeSubgroup> {
        @Override
        public Class<RecipeSubgroup> correspondingClass() {
            return RecipeSubgroup.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link RecipeSubgroup} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable RecipeSubgroup",
            cssClassFa = "skull red"
    )
    @Named("dita.globodiet.params.classification.RecipeSubgroup.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends RecipeSubgroup implements ViewModel {
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
