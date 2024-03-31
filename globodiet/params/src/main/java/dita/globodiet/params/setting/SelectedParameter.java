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
 * Selected parameters
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.setting.SelectedParameter")
@DomainObject
@DomainObjectLayout(
        describedAs = "Selected parameters"
)
@PersistenceCapable(
        table = "PARAM"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class SelectedParameter implements Cloneable<SelectedParameter> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Parameter code
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Parameter code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PARAM_LAB",
            allowsNull = "true",
            length = 40
    )
    @Getter
    @Setter
    private String parameterCode;

    /**
     * Parameter value
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Parameter value",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PARAM_LIB",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String parameterValue;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "SelectedParameter(" + "parameterCode=" + getParameterCode() + ","
         +"parameterValue=" + getParameterValue() + ")";
    }

    @Programmatic
    @Override
    public SelectedParameter copy() {
        var copy = repositoryService.detachedEntity(new SelectedParameter());
        copy.setParameterCode(getParameterCode());
        copy.setParameterValue(getParameterValue());
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
    public SelectedParameter.Manager getNavigableParent() {
        return new SelectedParameter.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link SelectedParameter}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.setting.SelectedParameter.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Selected parameters"
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
            return "Manage Selected Parameter";
        }

        @Collection
        public final List<SelectedParameter> getListOfSelectedParameter() {
            return searchService.search(SelectedParameter.class, SelectedParameter::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
