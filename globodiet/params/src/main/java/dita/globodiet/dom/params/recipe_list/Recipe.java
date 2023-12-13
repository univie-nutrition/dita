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
package dita.globodiet.dom.params.recipe_list;

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
 * Mixed (a priory) Recipe (not an on-the-fly one):
 * After preparation, the different ingredients cannot be identified
 * and quantified separately, as those are derived from cook books (for homemade and similar recipes),
 * internet or are based on information received from the industry (for commercial recipes).
 * They are entered as standard recipes with the RECIPE MANAGER and handled at the country level
 * prior to the commencement of the interviews.
 * The mixed recipe database may contain three types of recipes:
 * 1) ‘open recipes’,
 * 2) ‘closed recipes’ and
 * 3) ‘strictly commercial with brand recipes’.
 * For each mixed recipe, information on the expected variation of ingredients
 * within the study population needs to be collected, too.
 * If the variation is found to be great, the recipe should be treated as an open recipe.
 * It is also possible to enter several standard variations of a recipe depending on regions.
 * If no variations are expected, a recipe should be treated as a closed recipe
 * or as a strictly commercial recipe.
 * Furthermore, it has to be decided if each ingredient is fixed or substitutable.
 * When entering the recipe ingredients with RECIPE MANAGER,
 * the ingredients are described and quantified like in the food pathway.
 */
@Named("dita.globodiet.params.recipe_list.Recipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "Mixed (a priory) Recipe (not an on-the-fly one):\n"
                        + "After preparation, the different ingredients cannot be identified\n"
                        + "and quantified separately, as those are derived from cook books (for homemade and similar recipes),\n"
                        + "internet or are based on information received from the industry (for commercial recipes).\n"
                        + "They are entered as standard recipes with the RECIPE MANAGER and handled at the country level\n"
                        + "prior to the commencement of the interviews.\n"
                        + "The mixed recipe database may contain three types of recipes:\n"
                        + "1) ‘open recipes’,\n"
                        + "2) ‘closed recipes’ and\n"
                        + "3) ‘strictly commercial with brand recipes’.\n"
                        + "For each mixed recipe, information on the expected variation of ingredients\n"
                        + "within the study population needs to be collected, too.\n"
                        + "If the variation is found to be great, the recipe should be treated as an open recipe.\n"
                        + "It is also possible to enter several standard variations of a recipe depending on regions.\n"
                        + "If no variations are expected, a recipe should be treated as a closed recipe\n"
                        + "or as a strictly commercial recipe.\n"
                        + "Furthermore, it has to be decided if each ingredient is fixed or substitutable.\n"
                        + "When entering the recipe ingredients with RECIPE MANAGER,\n"
                        + "the ingredients are described and quantified like in the food pathway.",
        cssClassFa = "solid stroopwafel .recipe-color"
)
@PersistenceCapable(
        table = "MIXEDREC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class Recipe implements Cloneable<Recipe>, HasSecondaryKey<Recipe> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe ID number
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Recipe ID number",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String code;

    /**
     * Group code of the recipe classification
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Group code of the recipe classification",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Subgroup code of the recipe classification
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Subgroup code of the recipe classification",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_SUBGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeSubgroupCode;

    /**
     * Recipe name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Recipe name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Type of recipe:
     * 1.1=Open – Known
     * 1.2=Open – Unknown
     * 1.3=Open with brand
     * 2.1=Closed
     * 2.2=Closed with brand
     * 3.0=Commercial
     * 4.1=New – Known
     * 4.2=New – Unknown
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Type of recipe:\n"
                            + "1.1=Open – Known\n"
                            + "1.2=Open – Unknown\n"
                            + "1.3=Open with brand\n"
                            + "2.1=Closed\n"
                            + "2.2=Closed with brand\n"
                            + "3.0=Commercial\n"
                            + "4.1=New – Known\n"
                            + "4.2=New – Unknown",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_TYPE",
            allowsNull = "false",
            length = 3
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
    private RecipeType recipeType;

    /**
     * Brand name for commercial recipe
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Brand name for commercial recipe",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_BRAND",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String brandNameForCommercialRecipe;

    /**
     * whether is an alias (SH=shadow)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "whether is an alias (SH=shadow)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 2
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
    private AliasQ aliasQ;

    /**
     * 0=recipe without sub-recipe
     * 1=recipe with sub-recipe
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "0=recipe without sub-recipe\n"
                            + "1=recipe with sub-recipe",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_SUB",
            allowsNull = "false"
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
    private HasSubRecipeQ hasSubRecipeQ;

    /**
     * has no description
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "9",
            describedAs = "has no description",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "STATUS",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String status;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
    }

    @Override
    public String toString() {
        return "Recipe(" + "code=" + getCode() + ","
         +"recipeGroupCode=" + getRecipeGroupCode() + ","
         +"recipeSubgroupCode=" + getRecipeSubgroupCode() + ","
         +"name=" + getName() + ","
         +"recipeType=" + getRecipeType() + ","
         +"brandNameForCommercialRecipe=" + getBrandNameForCommercialRecipe() + ","
         +"aliasQ=" + getAliasQ() + ","
         +"hasSubRecipeQ=" + getHasSubRecipeQ() + ","
         +"status=" + getStatus() + ")";
    }

    @Programmatic
    @Override
    public Recipe copy() {
        var copy = repositoryService.detachedEntity(new Recipe());
        copy.setCode(getCode());
        copy.setRecipeGroupCode(getRecipeGroupCode());
        copy.setRecipeSubgroupCode(getRecipeSubgroupCode());
        copy.setName(getName());
        copy.setRecipeType(getRecipeType());
        copy.setBrandNameForCommercialRecipe(getBrandNameForCommercialRecipe());
        copy.setAliasQ(getAliasQ());
        copy.setHasSubRecipeQ(getHasSubRecipeQ());
        copy.setStatus(getStatus());
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
    public Recipe.Manager getNavigableParent() {
        return new Recipe.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @RequiredArgsConstructor
    public enum RecipeType {
        /**
         * no description
         */
        OPEN_KNOWN("1.1", "Open – Known"),

        /**
         * no description
         */
        OPEN_UNKNOWN("1.2", "Open – Unknown"),

        /**
         * no description
         */
        OPEN_WITH_BRAND("1.3", "Open with brand"),

        /**
         * no description
         */
        CLOSED("2.1", "Closed"),

        /**
         * no description
         */
        CLOSED_WITH_BRAND("2.2", "Closed with brand"),

        /**
         * no description
         */
        COMMERCIAL("3.0", "Commercial"),

        /**
         * no description
         */
        NEW_KNOWN("4.1", "New – Known"),

        /**
         * no description
         */
        NEW_UNKNOWN("4.2", "New – Unknown");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum AliasQ {
        /**
         * is regular entry
         */
        REGULAR("", "regular"),

        /**
         * is alias entry
         */
        ALIAS("", "alias");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum HasSubRecipeQ {
        /**
         * recipe without sub-recipe
         */
        SUB_RECIPE_EXCLUDED(0, "sub-recipe excluded"),

        /**
         * recipe with sub-recipe
         */
        SUB_RECIPE_INCLUDED(1, "sub-recipe included");

        @Getter
        private final int matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link Recipe}
     */
    @Named("dita.globodiet.params.recipe_list.Recipe.Manager")
    @DomainObjectLayout(
            describedAs = "Mixed (a priory) Recipe (not an on-the-fly one):\n"
                            + "After preparation, the different ingredients cannot be identified\n"
                            + "and quantified separately, as those are derived from cook books (for homemade and similar recipes),\n"
                            + "internet or are based on information received from the industry (for commercial recipes).\n"
                            + "They are entered as standard recipes with the RECIPE MANAGER and handled at the country level\n"
                            + "prior to the commencement of the interviews.\n"
                            + "The mixed recipe database may contain three types of recipes:\n"
                            + "1) ‘open recipes’,\n"
                            + "2) ‘closed recipes’ and\n"
                            + "3) ‘strictly commercial with brand recipes’.\n"
                            + "For each mixed recipe, information on the expected variation of ingredients\n"
                            + "within the study population needs to be collected, too.\n"
                            + "If the variation is found to be great, the recipe should be treated as an open recipe.\n"
                            + "It is also possible to enter several standard variations of a recipe depending on regions.\n"
                            + "If no variations are expected, a recipe should be treated as a closed recipe\n"
                            + "or as a strictly commercial recipe.\n"
                            + "Furthermore, it has to be decided if each ingredient is fixed or substitutable.\n"
                            + "When entering the recipe ingredients with RECIPE MANAGER,\n"
                            + "the ingredients are described and quantified like in the food pathway.",
            cssClassFa = "solid stroopwafel .recipe-color"
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
            return "Manage Recipe";
        }

        @Collection
        public final List<Recipe> getListOfRecipe() {
            return searchService.search(Recipe.class, Recipe::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Recipe}
     * @param code Recipe ID number
     * @param recipeGroup Group code of the recipe classification
     * @param recipeSubgroup Subgroup code of the recipe classification
     * @param name Recipe name
     * @param recipeType Type of recipe:
     * 1.1=Open – Known
     * 1.2=Open – Unknown
     * 1.3=Open with brand
     * 2.1=Closed
     * 2.2=Closed with brand
     * 3.0=Commercial
     * 4.1=New – Known
     * 4.2=New – Unknown
     * @param brandNameForCommercialRecipe Brand name for commercial recipe
     * @param aliasQ whether is an alias (SH=shadow)
     * @param hasSubRecipeQ 0=recipe without sub-recipe
     * 1=recipe with sub-recipe
     * @param status has no description
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Recipe ID number"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Group code of the recipe classification"
            )
            RecipeGroup recipeGroup,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.UPDATE_DEPENDENT,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Subgroup code of the recipe classification"
            )
            RecipeSubgroup recipeSubgroup,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Recipe name"
            )
            String name,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Type of recipe:\n"
                                    + "1.1=Open – Known\n"
                                    + "1.2=Open – Unknown\n"
                                    + "1.3=Open with brand\n"
                                    + "2.1=Closed\n"
                                    + "2.2=Closed with brand\n"
                                    + "3.0=Commercial\n"
                                    + "4.1=New – Known\n"
                                    + "4.2=New – Unknown"
            )
            RecipeType recipeType,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Brand name for commercial recipe"
            )
            String brandNameForCommercialRecipe,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "whether is an alias (SH=shadow)"
            )
            AliasQ aliasQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "0=recipe without sub-recipe\n"
                                    + "1=recipe with sub-recipe"
            )
            HasSubRecipeQ hasSubRecipeQ,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "has no description"
            )
            String status) {
    }

    /**
     * SecondaryKey for @{link Recipe}
     * @param code Recipe ID number
     */
    public final record SecondaryKey(String code) implements ISecondaryKey<Recipe> {
        @Override
        public Class<Recipe> correspondingClass() {
            return Recipe.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Recipe} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable Recipe",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.recipe_list.Recipe.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends Recipe implements ViewModel {
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
