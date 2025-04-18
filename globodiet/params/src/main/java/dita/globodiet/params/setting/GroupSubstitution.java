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
package dita.globodiet.params.setting;

import io.github.causewaystuff.companion.applib.jpa.EnumConverter;
import io.github.causewaystuff.companion.applib.jpa.EnumWithCode;
import io.github.causewaystuff.companion.applib.jpa.Persistable;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
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
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * Group/subgroup that can be substituted
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.setting.GroupSubstitution")
@DomainObject
@DomainObjectLayout(
        describedAs = "Group/subgroup that can be substituted"
)
@Entity
@Table(
        name = "SUBSTIT"
)
public class GroupSubstitution implements Persistable, Cloneable<GroupSubstitution> {
    @Inject
    @Transient
    RepositoryService repositoryService;

    @Inject
    @Transient
    SearchService searchService;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    /**
     * 0=Food classification
     * 1=Recipe classification
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "0=Food classification\n"
                    + "1=Recipe classification"
    )
    @Column(
            name = "\"TYPE\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = Type.Converter.class
    )
    private Type type;

    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Food group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"GROUP\"",
            nullable = false,
            length = 2
    )
    @Getter
    @Setter
    private String foodGroupCode;

    /**
     * Food subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Food subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"SUBGROUP1\"",
            nullable = true,
            length = 2
    )
    @Getter
    @Setter
    private String foodSubgroupCode;

    /**
     * Food sub-Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Food sub-Subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"SUBGROUP2\"",
            nullable = true,
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    /**
     * List of food groups/subgroups to apply the substitution (e.g. “01”, “13,1602,0507,0508”)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
            describedAs = "List of food groups/subgroups to apply the substitution (e.g. “01”, “13,1602,0507,0508”)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"F_CLASS\"",
            nullable = true,
            length = 100
    )
    @Getter
    @Setter
    private String applyToFoodGroupsLookupKey;

    /**
     * List of recipe groups/subgroups to apply the substitution (e.g. “01”, “02, 0403, 0702”)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "6",
            describedAs = "List of recipe groups/subgroups to apply the substitution (e.g. “01”, “02, 0403, 0702”)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"R_CLASS\"",
            nullable = true,
            length = 100
    )
    @Getter
    @Setter
    private String applyToRecipeGroupsLookupKey;

    @ObjectSupport
    public String title() {
        return String.format("For %s (code=%s|%s|%s)->(f:%s|r:%s)", 
         type.title(), 
         dita.commons.util.FormatUtils.emptyToDash(foodGroupCode), 
         dita.commons.util.FormatUtils.emptyToDash(foodSubgroupCode), 
         dita.commons.util.FormatUtils.emptyToDash(foodSubSubgroupCode),
         dita.commons.util.FormatUtils.emptyToDash(applyToFoodGroupsLookupKey),
         dita.commons.util.FormatUtils.emptyToDash(applyToRecipeGroupsLookupKey))
        ;
    }

    @Override
    public String toString() {
        return "GroupSubstitution(" + "type=" + getType() + ","
         +"foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"applyToFoodGroupsLookupKey=" + getApplyToFoodGroupsLookupKey() + ","
         +"applyToRecipeGroupsLookupKey=" + getApplyToRecipeGroupsLookupKey() + ")";
    }

    @Programmatic
    @Override
    public GroupSubstitution copy() {
        var copy = repositoryService.detachedEntity(new GroupSubstitution());
        copy.setType(getType());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setApplyToFoodGroupsLookupKey(getApplyToFoodGroupsLookupKey());
        copy.setApplyToRecipeGroupsLookupKey(getApplyToRecipeGroupsLookupKey());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @Transient
    public GroupSubstitution.Manager getNavigableParent() {
        return new GroupSubstitution.Manager(searchService, "");
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum Type implements EnumWithCode<String> {

        /**
         * no description
         */
        FOOD("0", "Food"),
        /**
         * no description
         */
        RECIPE("1", "Recipe");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<Type, String> {
            @Override
            public Type[] values() {
                return Type.values();
            }
        }
    }

    /**
     * Manager Viewmodel for @{link GroupSubstitution}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.setting.GroupSubstitution.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Group/subgroup that can be substituted"
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
            return "Manage Group Substitution";
        }

        @Collection
        public final List<GroupSubstitution> getListOfGroupSubstitution() {
            return searchService.search(GroupSubstitution.class, GroupSubstitution::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
