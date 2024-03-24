/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.globodiet.manager.editing.food;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PrecedingParamsPolicy;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.RequiredArgsConstructor;

import org.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodDescriptor;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFoodGroup.RawOrCookedAsConsumed;
import dita.globodiet.dom.params.quantif.Photo;
import dita.globodiet.dom.params.quantif.PhotoOrShape;
import dita.globodiet.dom.params.quantif.Shape;
import dita.globodiet.manager.util.QuantificationMethodPathwayKey;

/**
 */
@Action
@ActionLayout(
        associateWith = "effectiveQuantificationMethodPathways",
        position = Position.PANEL,
        sequence = "1",
        describedAs = "Add quantification method to the (effective) grouping for the food quantification pathway."
        )
@RequiredArgsConstructor
public class Food_addQuantificationMethodPathwayToGroup {

    private final static String PHYSICAL_STATE_FACET_CODE = "02"; //TODO convert to configuration option

    @Inject private FactoryService factoryService;
    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    protected final Food mixee;

    @MemberSupport
    public Food act(
            @Parameter(
                    optionality = Optionality.OPTIONAL)
            @ParameterLayout(describedAs = "The quantification method pathway "
                    + "is bound to the given physical-state facet/descriptor. Can be left empty.\n\n"
                    + "Choices provided are those that are effective for the current food.")
            final FoodDescriptor physicalState,

            @Parameter(
                    optionality = Optionality.OPTIONAL,
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES)
            @ParameterLayout(describedAs = "The quantification method pathway "
                    + "is potentially bound to whether the food was cooked or not. Can be left empty.")
            final RawOrCookedAsConsumed rawOrCookedAsConsumed,

            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES)
            final QuantificationMethodPathwayForFoodGroup.QuantificationMethod quantificationMethod,

            @Parameter(
                    optionality = Optionality.OPTIONAL,
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET)
            final PhotoOrShape photoOrShape,

            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES)
            final String comment

            ) {

        var effectiveGrouping = factoryService.mixin(Food_effectiveGroupingUsedForQuantificationPathway.class, mixee).prop();
        var groupingKey = effectiveGrouping.groupingKey();
        var physicalState4DigitKey = physicalState!=null
                ? physicalState.secondaryKey().facetCode() + physicalState.secondaryKey().code()
                : null;

        var entity = repositoryService.detachedEntity(new QuantificationMethodPathwayForFoodGroup());

        entity.setFoodGroupCode(groupingKey.groupKey());
        entity.setFoodSubgroupCode(groupingKey.subgroupKey());
        entity.setFoodSubSubgroupCode(groupingKey.subSubgroupKey());

        entity.setPhysicalStateFacetDescriptorLookupKey(physicalState4DigitKey);
        entity.setRawOrCookedAsConsumed(rawOrCookedAsConsumed);

        entity.setQuantificationMethod(quantificationMethod);
        entity.setPhotoOrShapeCode(photoOrShape!=null
                ? photoOrShape.getCode()
                : null);

        entity.setComment(comment);

        repositoryService.persist(entity);
        foreignKeyLookupService.clearCache(FacetDescriptorPathwayForFoodGroup.class);

        return mixee;
    }

    @MemberSupport
    private Can<FoodDescriptor> choicesPhysicalState() {
        var effectiveFoodDescriptors = Can.ofCollection(factoryService.mixin(Food_effectiveFoodDescriptors.class, mixee).coll());
        return effectiveFoodDescriptors.filter(fd->PHYSICAL_STATE_FACET_CODE.equals(fd.getFacetCode()));
    }

    @MemberSupport
    private Can<PhotoOrShape> choicesPhotoOrShape(
            final FoodDescriptor physicalState,
            final RawOrCookedAsConsumed rawOrCookedAsConsumed,
            final QuantificationMethodPathwayForFoodGroup.QuantificationMethod quantificationMethod
            ) {

        switch (quantificationMethod) {
        case PHOTO: {
            return Can.ofCollection(repositoryService.allInstances(Photo.class))
                    .map(PhotoOrShape.class::cast);
        }
        case SHAPE: {
            return Can.ofCollection(repositoryService.allInstances(Shape.class))
                    .map(PhotoOrShape.class::cast);
        }
        default:
            return Can.empty();
        }
    }

    @MemberSupport private String validate(
            final FoodDescriptor physicalState,
            final RawOrCookedAsConsumed rawOrCookedAsConsumed,
            final QuantificationMethodPathwayForFoodGroup.QuantificationMethod quantificationMethod,
            final PhotoOrShape photoOrShape,
            final String comment) {
        if(QuantificationMethodPathwayKey.QuantificationMethod.valueOf(quantificationMethod).isPhotoOrShape()) {
            if(photoOrShape==null) {
                return "Photo or Shape required for quantification method " + quantificationMethod.name();
            }
        }
        if(_Strings.isNullOrEmpty(_Strings.blankToNullOrTrim(comment))) {
            return "blank comment not allowed";
        }
        return null;
    }

}
