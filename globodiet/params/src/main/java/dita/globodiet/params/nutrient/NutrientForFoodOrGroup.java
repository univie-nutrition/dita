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
package dita.globodiet.params.nutrient;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
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
 * Cross reference between food (or food group) and nutrient values (usually multiple).
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.nutrient.NutrientForFoodOrGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Cross reference between food (or food group) and nutrient values (usually multiple).",
        cssClassFa = "solid flask .nutrient-color,\n"
                        + "solid utensils .nutrient-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "ITEMS_DEF"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_NutrientForFoodOrGroup",
        members = {"code"}
)
public class NutrientForFoodOrGroup implements Cloneable<NutrientForFoodOrGroup>, HasSecondaryKey<NutrientForFoodOrGroup> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Unique code, that relates @{table NTR_VALUE}.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Unique code, that relates @{table NTR_VALUE}."
    )
    @Column(
            name = "ITEM_SEQ",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int code;

    /**
     * Type of record:
     * F=food,
     * R=recipe,
     * A2=fat attached,
     * A3=liquid attached
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Type of record:\n"
                            + "F=food,\n"
                            + "R=recipe,\n"
                            + "A2=fat attached,\n"
                            + "A3=liquid attached"
    )
    @Column(
            name = "TYPE",
            allowsNull = "false",
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
    private TypeOfRecord typeOfRecord;

    /**
     * Food or recipe group
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Food or recipe group",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodOrRecipeGroupCode;

    /**
     * Food or recipe sub-group
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Food or recipe sub-group",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodOrRecipeSubgroupCode;

    /**
     * Food sub-sub-group
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
            describedAs = "Food sub-sub-group",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    /**
     * Food or Recipe code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "6",
            describedAs = "Food or Recipe code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "ID_NUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodOrRecipeCode;

    /**
     * Facet string
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Facet string"
    )
    @Column(
            name = "FACET_STR",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String facetString;

    /**
     * Brand name
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Brand name"
    )
    @Column(
            name = "BRANDNAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String brandName;

    /**
     * Priority order
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "9",
            describedAs = "Priority order"
    )
    @Column(
            name = "PRIORITY",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int priority;

    /**
     * Attached records: only for the Type=A2 & A3
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "10",
            describedAs = "Attached records: only for the Type=A2 & A3"
    )
    @Column(
            name = "ITEM_SEQ_SEQ",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer attachedRecords;

    /**
     * Comment
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "11",
            describedAs = "Comment"
    )
    @Column(
            name = "COMMENT",
            allowsNull = "true",
            length = 4096
    )
    @Getter
    @Setter
    private String comment;

    @ObjectSupport
    public String title() {
        return String.format("code=%d, type=%s", code, typeOfRecord.title());
    }

    @Override
    public String toString() {
        return "NutrientForFoodOrGroup(" + "code=" + getCode() + ","
         +"typeOfRecord=" + getTypeOfRecord() + ","
         +"foodOrRecipeGroupCode=" + getFoodOrRecipeGroupCode() + ","
         +"foodOrRecipeSubgroupCode=" + getFoodOrRecipeSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"foodOrRecipeCode=" + getFoodOrRecipeCode() + ","
         +"facetString=" + getFacetString() + ","
         +"brandName=" + getBrandName() + ","
         +"priority=" + getPriority() + ","
         +"attachedRecords=" + getAttachedRecords() + ","
         +"comment=" + getComment() + ")";
    }

    @Programmatic
    @Override
    public NutrientForFoodOrGroup copy() {
        var copy = repositoryService.detachedEntity(new NutrientForFoodOrGroup());
        copy.setCode(getCode());
        copy.setTypeOfRecord(getTypeOfRecord());
        copy.setFoodOrRecipeGroupCode(getFoodOrRecipeGroupCode());
        copy.setFoodOrRecipeSubgroupCode(getFoodOrRecipeSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setFoodOrRecipeCode(getFoodOrRecipeCode());
        copy.setFacetString(getFacetString());
        copy.setBrandName(getBrandName());
        copy.setPriority(getPriority());
        copy.setAttachedRecords(getAttachedRecords());
        copy.setComment(getComment());
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
    public NutrientForFoodOrGroup.Manager getNavigableParent() {
        return new NutrientForFoodOrGroup.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @RequiredArgsConstructor
    public enum TypeOfRecord {
        /**
         * no description
         */
        FOOD("F", "Food"),

        /**
         * no description
         */
        RECIPE("R", "Recipe"),

        /**
         * no description
         */
        FAT_ATTACHED("A2", "Fat attached"),

        /**
         * no description
         */
        LIQUID_ATTACHED("A3", "Liquid attached");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link NutrientForFoodOrGroup}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.nutrient.NutrientForFoodOrGroup.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Cross reference between food (or food group) and nutrient values (usually multiple).",
            cssClassFa = "solid flask .nutrient-color,\n"
                            + "solid utensils .nutrient-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Nutrient For Food Or Group";
        }

        @Collection
        public final List<NutrientForFoodOrGroup> getListOfNutrientForFoodOrGroup() {
            return searchService.search(NutrientForFoodOrGroup.class, NutrientForFoodOrGroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link NutrientForFoodOrGroup}
     * @param code Unique code, that relates @{table NTR_VALUE}.
     * @param typeOfRecord Type of record:
     * F=food,
     * R=recipe,
     * A2=fat attached,
     * A3=liquid attached
     * @param foodOrRecipeGroup Food or recipe group
     * @param foodOrRecipeSubgroup Food or recipe sub-group
     * @param foodSubSubgroup Food sub-sub-group
     * @param foodOrRecipe Food or Recipe code
     * @param facetString Facet string
     * @param brandName Brand name
     * @param priority Priority order
     * @param attachedRecords Attached records: only for the Type=A2 & A3
     * @param comment Comment
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Unique code, that relates @{table NTR_VALUE}."
            )
            int code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Type of record:\n"
                                    + "F=food,\n"
                                    + "R=recipe,\n"
                                    + "A2=fat attached,\n"
                                    + "A3=liquid attached"
            )
            TypeOfRecord typeOfRecord,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food or recipe group"
            )
            FoodGroup foodOrRecipeGroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food or recipe sub-group"
            )
            FoodSubgroup foodOrRecipeSubgroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food sub-sub-group"
            )
            FoodSubgroup foodSubSubgroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food or Recipe code"
            )
            Food foodOrRecipe,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Facet string"
            )
            String facetString,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Brand name"
            )
            String brandName,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Priority order"
            )
            int priority,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Attached records: only for the Type=A2 & A3"
            )
            Integer attachedRecords,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Comment"
            )
            String comment) {
    }

    /**
     * SecondaryKey for @{link NutrientForFoodOrGroup}
     * @param code Unique code, that relates @{table NTR_VALUE}.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(int code) implements ISecondaryKey<NutrientForFoodOrGroup> {
        @Override
        public Class<NutrientForFoodOrGroup> correspondingClass() {
            return NutrientForFoodOrGroup.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link NutrientForFoodOrGroup} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable NutrientForFoodOrGroup",
            describedAs = "Unresolvable NutrientForFoodOrGroup",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.nutrient.NutrientForFoodOrGroup.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends NutrientForFoodOrGroup implements ViewModel {
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
