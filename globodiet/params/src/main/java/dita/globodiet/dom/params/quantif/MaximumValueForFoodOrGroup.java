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
package dita.globodiet.dom.params.quantif;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
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
import org.causewaystuff.companion.applib.services.lookup.Cloneable;
import org.causewaystuff.companion.applib.services.search.SearchService;

/**
 * Maximum quantity consumed that could be entered in the interview application for a given food
 * (or group) before the control message warns the interviewer of an implausible value.
 */
@Generated("org.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.quantif.MaximumValueForFoodOrGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Maximum quantity consumed that could be entered in the interview application for a given food\n"
                        + "(or group) before the control message warns the interviewer of an implausible value."
)
@PersistenceCapable(
        table = "MAXVAL"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class MaximumValueForFoodOrGroup implements Cloneable<MaximumValueForFoodOrGroup> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Food group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "true",
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
            sequence = "2",
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
            sequence = "3",
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
     * Food code
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Food code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodCode;

    /**
     * Maximum value
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Maximum value",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "MAXIMA",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double maximumValue;

    /**
     * Facet string; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "6",
            describedAs = "Facet string; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACETS_STR",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String facetDescriptorLookupKey;

    /**
     * Priority order
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Priority order",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PRIORITY",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String priority;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "MaximumValueForFoodOrGroup(" + "foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"foodCode=" + getFoodCode() + ","
         +"maximumValue=" + getMaximumValue() + ","
         +"facetDescriptorLookupKey=" + getFacetDescriptorLookupKey() + ","
         +"priority=" + getPriority() + ")";
    }

    @Programmatic
    @Override
    public MaximumValueForFoodOrGroup copy() {
        var copy = repositoryService.detachedEntity(new MaximumValueForFoodOrGroup());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setFoodCode(getFoodCode());
        copy.setMaximumValue(getMaximumValue());
        copy.setFacetDescriptorLookupKey(getFacetDescriptorLookupKey());
        copy.setPriority(getPriority());
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
    public MaximumValueForFoodOrGroup.Manager getNavigableParent() {
        return new MaximumValueForFoodOrGroup.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link MaximumValueForFoodOrGroup}
     */
    @Named("dita.globodiet.params.quantif.MaximumValueForFoodOrGroup.Manager")
    @DomainObjectLayout(
            describedAs = "Maximum quantity consumed that could be entered in the interview application for a given food\n"
                            + "(or group) before the control message warns the interviewer of an implausible value."
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
            return "Manage Maximum Value For Food Or Group";
        }

        @Collection
        public final List<MaximumValueForFoodOrGroup> getListOfMaximumValueForFoodOrGroup() {
            return searchService.search(MaximumValueForFoodOrGroup.class, MaximumValueForFoodOrGroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
