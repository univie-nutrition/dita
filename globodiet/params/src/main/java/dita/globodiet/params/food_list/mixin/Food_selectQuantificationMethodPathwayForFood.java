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
package dita.globodiet.params.food_list.mixin;

import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.PrecedingParamsPolicy;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;

import lombok.RequiredArgsConstructor;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFood;
import dita.globodiet.params.quantif.Photo;
import dita.globodiet.params.quantif.PhotoOrShape;
import dita.globodiet.params.quantif.Shape;
import dita.globodiet.params.util.QuantificationMethodPathwayKey;

/**
 */
@Action
@ActionLayout(
        associateWith = "dependentQuantificationMethodPathwayForFoodMappedByFood",
        position = Position.PANEL,
        sequence = "2",
        describedAs = "Edit the food quantification subset to be selected in effect for the food quantification pathway.")
@RequiredArgsConstructor
public class Food_selectQuantificationMethodPathwayForFood {

    @Inject private FactoryService factoryService;
    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    protected final Food mixee;

    @MemberSupport
    public Food act(
            @Parameter
            final QuantificationMethodPathwayForFood.QuantificationMethod quantificationMethod,

            @Parameter(
                    optionality = Optionality.OPTIONAL,
                    precedingParamsPolicy = PrecedingParamsPolicy.RESET)
            final PhotoOrShape photoOrShape
        ) {

        var entity = repositoryService.detachedEntity(new QuantificationMethodPathwayForFood());

        entity.setFoodCode(mixee.getCode());
        entity.setQuantificationMethod(quantificationMethod);
        entity.setPhotoOrShapeCode(photoOrShape!=null
                ? photoOrShape.getCode()
                : null);

        repositoryService.persist(entity);
        foreignKeyLookupService.clearCache(FacetDescriptorPathwayForFood.class);

        return mixee;
    }

    @MemberSupport
    private Can<PhotoOrShape> choicesPhotoOrShape(
            final QuantificationMethodPathwayForFood.QuantificationMethod quantificationMethod
            ) {

        var qmps = factoryService.mixin(Food_effectiveQuantificationMethodPathways.class, mixee).coll();

        var availablePhotoOrShapeCodes = qmps.stream()
            .filter(qmp->qmp.getQuantificationMethod().name().equals(quantificationMethod.name()))
            .map(qmp->qmp.getPhotoOrShapeCode())
            .collect(Collectors.toSet());

        switch (quantificationMethod) {
        case PHOTO: {
            return Can.ofCollection(repositoryService.allInstances(Photo.class))
                    .filter(photo->availablePhotoOrShapeCodes.contains(photo.getCode()))
                    .map(PhotoOrShape.class::cast);
        }
        case SHAPE: {
            return Can.ofCollection(repositoryService.allInstances(Shape.class))
                    .filter(shape->availablePhotoOrShapeCodes.contains(shape.getCode()))
                    .map(PhotoOrShape.class::cast);
        }
        default:
            return Can.empty();
        }
    }

    @MemberSupport private String validate(
            final QuantificationMethodPathwayForFood.QuantificationMethod quantificationMethod,
            final PhotoOrShape photoOrShape) {
        if(QuantificationMethodPathwayKey.QuantificationMethod.valueOf(quantificationMethod).isPhotoOrShape()) {
            if(photoOrShape==null) {
                return "Photo or Shape required for quantification method " + quantificationMethod.name();
            }
        }
        return null;
    }

}
