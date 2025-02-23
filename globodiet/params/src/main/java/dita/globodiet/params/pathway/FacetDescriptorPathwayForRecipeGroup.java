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
package dita.globodiet.params.pathway;

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
 * Facet/descriptor pathway for recipe group/subgroup.
 * Optionally can be superseded by @{table R_RCPFAEX}.
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.pathway.FacetDescriptorPathwayForRecipeGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet/descriptor pathway for recipe group/subgroup.\n"
                        + "Optionally can be superseded by @{table R_RCPFAEX}.",
        cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                        + "solid tag .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "R_GROUPFAC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FacetDescriptorPathwayForRecipeGroup implements Cloneable<FacetDescriptorPathwayForRecipeGroup> {
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
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RGROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Recipe Subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RSUBGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeSubgroupCode;

    /**
     * Recipe Facet code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Recipe Facet code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeFacetCode;

    /**
     * Recipe Descriptor code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Recipe Descriptor code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RDESCR_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeDescriptorCode;

    /**
     * Default flag (if set to 'D', it is the default descriptor else blank)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Default flag (if set to 'D', it is the default descriptor else blank)"
    )
    @Column(
            name = "RDEFAULT",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String defaultFlagQ;

    /**
     * Not in name flag (if set to 'N', the descriptor is not in the name else blank)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Not in name flag (if set to 'N', the descriptor is not in the name else blank)"
    )
    @Column(
            name = "RNOTINNAME",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String notInNameQ;

    /**
     * Order to display the facets within a group/subgroup
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Order to display the facets within a group/subgroup"
    )
    @Column(
            name = "ORDER_FAC",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int facetDisplayOrder;

    /**
     * Order to display the descriptors within a group/subgroup and a facet
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Order to display the descriptors within a group/subgroup and a facet"
    )
    @Column(
            name = "ORDER_DESC",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int descriptorDisplayOrder;

    @ObjectSupport
    public String title() {
        return String.format("Pathway (group=%s|%s, descriptor=%s|%s)",
            recipeGroupCode, 
            dita.commons.util.FormatUtils.emptyToDash(recipeSubgroupCode), 
            recipeFacetCode, 
            recipeDescriptorCode)
        ;
    }

    @Override
    public String toString() {
        return "FacetDescriptorPathwayForRecipeGroup(" + "recipeGroupCode=" + getRecipeGroupCode() + ","
         +"recipeSubgroupCode=" + getRecipeSubgroupCode() + ","
         +"recipeFacetCode=" + getRecipeFacetCode() + ","
         +"recipeDescriptorCode=" + getRecipeDescriptorCode() + ","
         +"defaultFlagQ=" + getDefaultFlagQ() + ","
         +"notInNameQ=" + getNotInNameQ() + ","
         +"facetDisplayOrder=" + getFacetDisplayOrder() + ","
         +"descriptorDisplayOrder=" + getDescriptorDisplayOrder() + ")";
    }

    @Programmatic
    @Override
    public FacetDescriptorPathwayForRecipeGroup copy() {
        var copy = repositoryService.detachedEntity(new FacetDescriptorPathwayForRecipeGroup());
        copy.setRecipeGroupCode(getRecipeGroupCode());
        copy.setRecipeSubgroupCode(getRecipeSubgroupCode());
        copy.setRecipeFacetCode(getRecipeFacetCode());
        copy.setRecipeDescriptorCode(getRecipeDescriptorCode());
        copy.setDefaultFlagQ(getDefaultFlagQ());
        copy.setNotInNameQ(getNotInNameQ());
        copy.setFacetDisplayOrder(getFacetDisplayOrder());
        copy.setDescriptorDisplayOrder(getDescriptorDisplayOrder());
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
    public FacetDescriptorPathwayForRecipeGroup.Manager getNavigableParent() {
        return new FacetDescriptorPathwayForRecipeGroup.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link FacetDescriptorPathwayForRecipeGroup}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.pathway.FacetDescriptorPathwayForRecipeGroup.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Facet/descriptor pathway for recipe group/subgroup.\n"
                            + "Optionally can be superseded by @{table R_RCPFAEX}.",
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid tag .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
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
            return "Manage Facet Descriptor Pathway For Recipe Group";
        }

        @Collection
        public final List<FacetDescriptorPathwayForRecipeGroup> getListOfFacetDescriptorPathwayForRecipeGroup(
                ) {
            return searchService.search(FacetDescriptorPathwayForRecipeGroup.class, FacetDescriptorPathwayForRecipeGroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
