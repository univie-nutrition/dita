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
package dita.globodiet.manager.editing.other;

import java.util.List;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PrecedingParamsPolicy;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;

import lombok.RequiredArgsConstructor;

import dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup;

@Action(choicesFrom = "listOfFacetDescriptorPathwayForFoodGroup")
@ActionLayout(
        associateWith = "listOfFacetDescriptorPathwayForFoodGroup",
        position = Position.PANEL,
        sequence = "2",
        describedAs = "Shifts facet display order for selected entries.")
@RequiredArgsConstructor
public class FacetDescriptorPathwayForFoodGroupManager_shiftFacetDisplayOrder {

    @Inject private RepositoryService repositoryService;

    protected final FacetDescriptorPathwayForFoodGroup.Manager mixee;

    @MemberSupport
    public FacetDescriptorPathwayForFoodGroup.Manager act(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.MANDATORY)
            @ParameterLayout(
                    describedAs = "Order to display the facets within a group/subgroup")
            final int facetDisplayOrderShift,
            final List<FacetDescriptorPathwayForFoodGroup> toBeReordered) {

        Can.ofCollection(toBeReordered)
        .forEach((FacetDescriptorPathwayForFoodGroup pathway)->{
            var facetDisplayOrder = pathway.getFacetDisplayOrder() + facetDisplayOrderShift;
            pathway.setFacetDisplayOrder(facetDisplayOrder);
            repositoryService.persistAndFlush(pathway);
        });

        return mixee;
    }

}
