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
package dita.globodiet.params.recipe_list;

import dita.globodiet.params.classification.RecipeGrouping;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
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
import org.apache.causeway.applib.annotation.Nature;
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

/**
 * Recipe subgroup
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.recipe_list.RecipeSubgroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Recipe subgroup",
        cssClassFa = "solid stroopwafel .recipe-color,\n"
                        + "solid layer-group .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55,\n"
                        + "solid circle-chevron-down .recipe-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "RSUBGR"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_RecipeSubgroup",
        members = {"recipeGroupCode", "code"}
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
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @NotPersistent
    public RecipeSubgroup.Manager getNavigableParent() {
        return new RecipeSubgroup.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getRecipeGroupCode(), 
        getCode());
    }

    /**
     * Manager Viewmodel for @{link RecipeSubgroup}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.recipe_list.RecipeSubgroup.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Recipe subgroup",
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                            + "solid layer-group .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55,\n"
                            + "solid circle-chevron-down .recipe-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
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
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Recipe group code"
            )
            RecipeGroup recipeGroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Recipe sub-group code"
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Name of the recipe (sub-)group"
            )
            String name,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
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
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
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
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Unresolvable RecipeSubgroup",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.recipe_list.RecipeSubgroup.Unresolvable")
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
