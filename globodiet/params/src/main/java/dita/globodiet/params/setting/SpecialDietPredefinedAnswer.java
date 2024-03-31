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
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.search.SearchService;

/**
 * Special diet predefined answer
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.setting.SpecialDietPredefinedAnswer")
@DomainObject
@DomainObjectLayout(
        describedAs = "Special diet predefined answer"
)
@PersistenceCapable(
        table = "SPECDIET"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class SpecialDietPredefinedAnswer implements Cloneable<SpecialDietPredefinedAnswer> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Special diet code
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Special diet code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SPD_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String specialDietCode;

    /**
     * Special diet label
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Special diet label",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SPD_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String specialDietLabel;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "SpecialDietPredefinedAnswer(" + "specialDietCode=" + getSpecialDietCode() + ","
         +"specialDietLabel=" + getSpecialDietLabel() + ")";
    }

    @Programmatic
    @Override
    public SpecialDietPredefinedAnswer copy() {
        var copy = repositoryService.detachedEntity(new SpecialDietPredefinedAnswer());
        copy.setSpecialDietCode(getSpecialDietCode());
        copy.setSpecialDietLabel(getSpecialDietLabel());
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
    public SpecialDietPredefinedAnswer.Manager getNavigableParent() {
        return new SpecialDietPredefinedAnswer.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link SpecialDietPredefinedAnswer}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.setting.SpecialDietPredefinedAnswer.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Special diet predefined answer"
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
            return "Manage Special Diet Predefined Answer";
        }

        @Collection
        public final List<SpecialDietPredefinedAnswer> getListOfSpecialDietPredefinedAnswer() {
            return searchService.search(SpecialDietPredefinedAnswer.class, SpecialDietPredefinedAnswer::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
