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

import dita.globodiet.params.food_descript.FoodDescriptor;
import dita.globodiet.params.food_descript.FoodFacet;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
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
 * Facet/descriptor pathway for food group/subgroup.
 * Optionally can be superseded by @{table FOODFAEX}.
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet/descriptor pathway for food group/subgroup.\n"
                        + "Optionally can be superseded by @{table FOODFAEX}.",
        cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                        + "solid tag .food-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "GROUPFAC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_FacetDescriptorPathwayForFoodGroup",
        members = {"foodGroupCode", "foodSubgroupCode", "foodSubSubgroupCode", "foodFacetCode", "foodDescriptorCode"}
)
public class FacetDescriptorPathwayForFoodGroup implements Cloneable<FacetDescriptorPathwayForFoodGroup>, HasSecondaryKey<FacetDescriptorPathwayForFoodGroup> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Food group code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
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
     * Food Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "2",
            describedAs = "Food Subgroup code",
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
     * Food Sub-subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "3",
            describedAs = "Food Sub-subgroup code",
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
            fieldSetId = "identity",
            sequence = "4",
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
    private String foodFacetCode;

    /**
     * Descriptor code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "5",
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
    private String foodDescriptorCode;

    /**
     * Default flag (if set to 'D' it is the default descriptor)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Default flag (if set to 'D' it is the default descriptor)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DEFAULT",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String defaultFlag;

    /**
     * Not in name flag
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Not in name flag",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NOTINNAME",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String notInNameFlag;

    /**
     * Order to display the facets within a group/subgroup
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "8",
            describedAs = "Order to display the facets within a group/subgroup",
            hidden = Where.NOWHERE
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
            sequence = "9",
            describedAs = "Order to display the descriptors within a group/subgroup and a facet",
            hidden = Where.NOWHERE
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
        return String.format("Pathway (group=%s|%s|%s, descriptor=%s|%s)",
            foodGroupCode, 
            dita.commons.format.FormatUtils.emptyToDash(foodSubgroupCode), 
            dita.commons.format.FormatUtils.emptyToDash(foodSubSubgroupCode),
            foodFacetCode, 
            foodDescriptorCode)
        ;
    }

    @Override
    public String toString() {
        return "FacetDescriptorPathwayForFoodGroup(" + "foodGroupCode=" + getFoodGroupCode() + ","
         +"foodSubgroupCode=" + getFoodSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"foodFacetCode=" + getFoodFacetCode() + ","
         +"foodDescriptorCode=" + getFoodDescriptorCode() + ","
         +"defaultFlag=" + getDefaultFlag() + ","
         +"notInNameFlag=" + getNotInNameFlag() + ","
         +"facetDisplayOrder=" + getFacetDisplayOrder() + ","
         +"descriptorDisplayOrder=" + getDescriptorDisplayOrder() + ")";
    }

    @Programmatic
    @Override
    public FacetDescriptorPathwayForFoodGroup copy() {
        var copy = repositoryService.detachedEntity(new FacetDescriptorPathwayForFoodGroup());
        copy.setFoodGroupCode(getFoodGroupCode());
        copy.setFoodSubgroupCode(getFoodSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setFoodFacetCode(getFoodFacetCode());
        copy.setFoodDescriptorCode(getFoodDescriptorCode());
        copy.setDefaultFlag(getDefaultFlag());
        copy.setNotInNameFlag(getNotInNameFlag());
        copy.setFacetDisplayOrder(getFacetDisplayOrder());
        copy.setDescriptorDisplayOrder(getDescriptorDisplayOrder());
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
    public FacetDescriptorPathwayForFoodGroup.Manager getNavigableParent() {
        return new FacetDescriptorPathwayForFoodGroup.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getFoodGroupCode(), 
        getFoodSubgroupCode(), 
        getFoodSubSubgroupCode(), 
        getFoodFacetCode(), 
        getFoodDescriptorCode());
    }

    /**
     * Manager Viewmodel for @{link FacetDescriptorPathwayForFoodGroup}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Facet/descriptor pathway for food group/subgroup.\n"
                            + "Optionally can be superseded by @{table FOODFAEX}.",
            cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                            + "solid tag .food-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
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
            return "Manage Facet Descriptor Pathway For Food Group";
        }

        @Collection
        public final List<FacetDescriptorPathwayForFoodGroup> getListOfFacetDescriptorPathwayForFoodGroup(
                ) {
            return searchService.search(FacetDescriptorPathwayForFoodGroup.class, FacetDescriptorPathwayForFoodGroup::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link FacetDescriptorPathwayForFoodGroup}
     * @param foodGroup Food group code
     * @param foodSubgroup Food Subgroup code
     * @param foodSubSubgroup Food Sub-subgroup code
     * @param foodFacet Facet code
     * @param foodDescriptor Descriptor code
     * @param defaultFlag Default flag (if set to 'D' it is the default descriptor)
     * @param notInNameFlag Not in name flag
     * @param facetDisplayOrder Order to display the facets within a group/subgroup
     * @param descriptorDisplayOrder Order to display the descriptors within a group/subgroup and a facet
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Food group code"
            )
            FoodGroup foodGroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food Subgroup code"
            )
            FoodSubgroup foodSubgroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Food Sub-subgroup code"
            )
            FoodSubgroup foodSubSubgroup,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Facet code"
            )
            FoodFacet foodFacet,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Descriptor code"
            )
            FoodDescriptor foodDescriptor,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Default flag (if set to 'D' it is the default descriptor)"
            )
            String defaultFlag,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Not in name flag"
            )
            String notInNameFlag,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Order to display the facets within a group/subgroup"
            )
            int facetDisplayOrder,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY
            )
            @ParameterLayout(
                    describedAs = "Order to display the descriptors within a group/subgroup and a facet"
            )
            int descriptorDisplayOrder) {
    }

    /**
     * SecondaryKey for @{link FacetDescriptorPathwayForFoodGroup}
     * @param foodGroupCode Food group code
     * @param foodSubgroupCode Food Subgroup code
     * @param foodSubSubgroupCode Food Sub-subgroup code
     * @param foodFacetCode Facet code
     * @param foodDescriptorCode Descriptor code
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String foodGroupCode,
            String foodSubgroupCode,
            String foodSubSubgroupCode,
            String foodFacetCode,
            String foodDescriptorCode) implements ISecondaryKey<FacetDescriptorPathwayForFoodGroup> {
        @Override
        public Class<FacetDescriptorPathwayForFoodGroup> correspondingClass() {
            return FacetDescriptorPathwayForFoodGroup.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FacetDescriptorPathwayForFoodGroup} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Unresolvable FacetDescriptorPathwayForFoodGroup",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends FacetDescriptorPathwayForFoodGroup implements ViewModel {
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
