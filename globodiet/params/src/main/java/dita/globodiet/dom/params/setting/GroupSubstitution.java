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
package dita.globodiet.dom.params.setting;

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
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;

/**
 * Group/subgroup that can be substituted
 */
@Named("dita.globodiet.params.setting.GroupSubstitution")
@DomainObject
@DomainObjectLayout(
        describedAs = "Group/subgroup that can be substituted"
)
@PersistenceCapable(
        table = "SUBSTIT"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class GroupSubstitution {
    @Inject
    SearchService searchService;

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
                            + "1=Recipe classification",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE",
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
            name = "GROUP",
            allowsNull = "false",
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
            name = "SUBGROUP1",
            allowsNull = "true",
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
            name = "SUBGROUP2",
            allowsNull = "true",
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
            name = "F_CLASS",
            allowsNull = "true",
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
            name = "R_CLASS",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String applyToRecipeGroupsLookupKey;

    @ObjectSupport
    public String title() {
        return String.format("For %s (code=%s|%s|%s)->(f:%s|r:%s)", 
         type.title(), 
         dita.commons.format.FormatUtils.emptyToDash(foodGroupCode), 
         dita.commons.format.FormatUtils.emptyToDash(foodSubgroupCode), 
         dita.commons.format.FormatUtils.emptyToDash(foodSubSubgroupCode),
         dita.commons.format.FormatUtils.emptyToDash(applyToFoodGroupsLookupKey),
         dita.commons.format.FormatUtils.emptyToDash(applyToRecipeGroupsLookupKey))
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

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            navigable = Navigable.PARENT,
            hidden = Where.EVERYWHERE
    )
    @NotPersistent
    public GroupSubstitution.Manager getNavigableParent() {
        return new GroupSubstitution.Manager(searchService, "");
    }

    @RequiredArgsConstructor
    public enum Type {
        /**
         * no description
         */
        FOOD("0", "Food"),

        /**
         * no description
         */
        RECIPE("1", "Recipe");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link GroupSubstitution}
     */
    @Named("dita.globodiet.params.setting.GroupSubstitution.Manager")
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
