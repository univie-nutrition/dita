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
package dita.globodiet.params.food_descript;

import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
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

/**
 * Improbable sequence of facets/descriptors
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.food_descript.ImprobableSequenceOfFacetAndDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Improbable sequence of facets/descriptors"
)
@PersistenceCapable(
        table = "DESC_IMP"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class ImprobableSequenceOfFacetAndDescriptor implements Cloneable<ImprobableSequenceOfFacetAndDescriptor> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Food identification number
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Food identification number",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOODNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodCode;

    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
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
     * Food sub-subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Food sub-subgroup code",
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
     * Facet code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "5",
            describedAs = "Facet code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String facetCode;

    /**
     * Descriptor code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "6",
            describedAs = "Descriptor code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "DESCR_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String descriptorCode;

    /**
     * Facet string
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Facet string",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_STR",
            allowsNull = "false",
            length = 4096
    )
    @Getter
    @Setter
    private String facetString;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "ImprobableSequenceOfFacetAndDescriptor(" + "foodCode=" + getFoodCode() + ","
         +"foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"facetCode=" + getFacetCode() + ","
         +"descriptorCode=" + getDescriptorCode() + ","
         +"facetString=" + getFacetString() + ")";
    }

    @Programmatic
    @Override
    public ImprobableSequenceOfFacetAndDescriptor copy() {
        var copy = repositoryService.detachedEntity(new ImprobableSequenceOfFacetAndDescriptor());
        copy.setFoodCode(getFoodCode());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setFacetCode(getFacetCode());
        copy.setDescriptorCode(getDescriptorCode());
        copy.setFacetString(getFacetString());
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
    public ImprobableSequenceOfFacetAndDescriptor.Manager getNavigableParent() {
        return new ImprobableSequenceOfFacetAndDescriptor.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link ImprobableSequenceOfFacetAndDescriptor}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.food_descript.ImprobableSequenceOfFacetAndDescriptor.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Improbable sequence of facets/descriptors"
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
            return "Manage Improbable Sequence Of Facet And Descriptor";
        }

        @Collection
        public final List<ImprobableSequenceOfFacetAndDescriptor> getListOfImprobableSequenceOfFacetAndDescriptor(
                ) {
            return searchService.search(ImprobableSequenceOfFacetAndDescriptor.class, ImprobableSequenceOfFacetAndDescriptor::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
