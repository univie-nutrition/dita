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
package dita.globodiet.params.quantif;

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
import org.causewaystuff.companion.applib.services.lookup.Cloneable;
import org.causewaystuff.companion.applib.services.search.SearchService;

/**
 * Maximum quantity consumed that could be entered in the interview application for a given recipe
 * (or group) before the control message warns the interviewer of an implausible value.
 */
@Generated("org.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.quantif.MaximumValueForRecipeOrGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Maximum quantity consumed that could be entered in the interview application for a given recipe\n"
                        + "(or group) before the control message warns the interviewer of an implausible value."
)
@PersistenceCapable(
        table = "MAXRVAL"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class MaximumValueForRecipeOrGroup implements Cloneable<MaximumValueForRecipeOrGroup> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Recipe subgroup code",
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
     * Recipe code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Recipe code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    /**
     * Maximum value
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
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

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "MaximumValueForRecipeOrGroup(" + "recipeGroupCode=" + getRecipeGroupCode() + ","
         +"recipeSubgroupCode=" + getRecipeSubgroupCode() + ","
         +"recipeCode=" + getRecipeCode() + ","
         +"maximumValue=" + getMaximumValue() + ")";
    }

    @Programmatic
    @Override
    public MaximumValueForRecipeOrGroup copy() {
        var copy = repositoryService.detachedEntity(new MaximumValueForRecipeOrGroup());
        copy.setRecipeGroupCode(getRecipeGroupCode());
        copy.setRecipeSubgroupCode(getRecipeSubgroupCode());
        copy.setRecipeCode(getRecipeCode());
        copy.setMaximumValue(getMaximumValue());
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
    public MaximumValueForRecipeOrGroup.Manager getNavigableParent() {
        return new MaximumValueForRecipeOrGroup.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link MaximumValueForRecipeOrGroup}
     */
    @Generated("org.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.quantif.MaximumValueForRecipeOrGroup.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Maximum quantity consumed that could be entered in the interview application for a given recipe\n"
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
            return "Manage Maximum Value For Recipe Or Group";
        }

        @Collection
        public final List<MaximumValueForRecipeOrGroup> getListOfMaximumValueForRecipeOrGroup() {
            return searchService.search(MaximumValueForRecipeOrGroup.class, MaximumValueForRecipeOrGroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
