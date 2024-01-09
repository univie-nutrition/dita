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
package dita.globodiet.manager.services.food;

import java.util.Comparator;
import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.collections._Lists;

import dita.globodiet.dom.params.classification.FoodGrouping;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodGroupDeps.FoodGroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import dita.globodiet.dom.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup;
import dita.globodiet.dom.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFoodGroup;
import lombok.NonNull;

@Service
public class FoodFacetHelperService {

    @Inject private FactoryService factoryService;
    @Inject private FoodHelperService foodHelperService;

    public List<FacetDescriptorPathwayForFoodGroup> effectiveFacetDescriptorPathwayForFoodClassification(
            final @NonNull Either<FoodGroup, FoodSubgroup> foodClassification) {

        final List<FacetDescriptorPathwayForFoodGroup> facetDescriptorPathwayForFoodGroup = foodClassification
            .fold(
                foodGroup->lookupFacetDescriptorPathwayForFoodGroup(foodGroup),
                foodSubOrSubSubgroup->{
                    var hasSubSubgroup = _Strings.isEmpty(foodSubOrSubSubgroup.getFoodSubSubgroupCode());
                    var lookupResult = hasSubSubgroup
                        ? lookupFacetDescriptorPathwayForFoodSubgroup(foodSubOrSubSubgroup)
                        : lookupFacetDescriptorPathwayForFoodSubSubgroup(foodSubOrSubSubgroup);

                    // if lookup was too specific, relax one level
                    if(lookupResult.isEmpty()
                            && hasSubSubgroup) {
                        lookupResult = lookupFacetDescriptorPathwayForFoodSubSubgroup(foodSubOrSubSubgroup);
                    }
                    // again, if lookup was too specific, fallback to top-level = FoodGroup
                    return lookupResult.isEmpty()
                        ? lookupFacetDescriptorPathwayForFoodGroup(foodHelperService.foodGroup(foodClassification.rightIfAny()))
                        : lookupResult;
                });

        return facetDescriptorPathwayForFoodGroup;
    }

    public FoodGrouping effectiveGroupingUsedForFacetDescriptorPathway(final @NonNull Food food) {
        var foodClassification = foodHelperService.foodClassification(food);
        final FoodGrouping foodGrouping = foodClassification
                .fold(
                    foodGroup->(FoodGrouping)foodGroup,
                    foodSubOrSubSubgroup->{
                        var hasSubSubgroup = _Strings.isEmpty(foodSubOrSubSubgroup.getFoodSubSubgroupCode());
                        var lookupResult = hasSubSubgroup
                            ? lookupFacetDescriptorPathwayForFoodSubgroup(foodSubOrSubSubgroup)
                            : lookupFacetDescriptorPathwayForFoodSubSubgroup(foodSubOrSubSubgroup);

                        // if lookup was too specific, relax one level
                        if(lookupResult.isEmpty()
                                && hasSubSubgroup) {
                            lookupResult = lookupFacetDescriptorPathwayForFoodSubSubgroup(foodSubOrSubSubgroup);
                        }
                        // again, if lookup was too specific, fallback to top-level = FoodGroup
                        return lookupResult.isEmpty()
                            ? (FoodGrouping)foodHelperService.foodGroup(foodClassification.rightIfAny())
                            : foodSubOrSubSubgroup;
                    });
            return foodGrouping;
    }

    public List<FacetDescriptorPathwayForFoodGroup> effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(
            final @NonNull Either<FoodGroup, FoodSubgroup> foodClassification) {
        return effectiveFacetDescriptorPathwayForFoodClassification(foodClassification).stream()
            .sorted(displayOrder())
            .toList();
    }

    public List<FacetDescriptorPathwayForFoodGroup> effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(
            final @NonNull Food food) {
        var effectiveGrouping = effectiveGroupingUsedForFacetDescriptorPathway(food);
        final Either<FoodGroup, FoodSubgroup> foodClassification = effectiveGrouping instanceof FoodSubgroup
                    ? Either.right((FoodSubgroup)effectiveGrouping)
                    : Either.left((FoodGroup)effectiveGrouping);
        return effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(
                foodClassification);
    }

    // -- HELPER

    private List<FacetDescriptorPathwayForFoodGroup> lookupFacetDescriptorPathwayForFoodGroup(final FoodGroup foodGroup) {
        var mixin = factoryService.mixin(FoodGroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodGroup.class, foodGroup);
        //exclude those, that have a subgroup (or sub-subgroup)
        return _Lists.filter(mixin.coll(), groupPathway->groupPathway.getFoodSubgroupCode()==null);
    }

    private List<FacetDescriptorPathwayForFoodGroup> lookupFacetDescriptorPathwayForFoodSubgroup(final FoodSubgroup foodSubgroup) {
        var mixin = factoryService.mixin(FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup.class, foodSubgroup);
        //exclude those, that have a sub-subgroup
        return _Lists.filter(mixin.coll(), groupPathway->groupPathway.getFoodSubSubgroupCode()==null);
    }

    private List<FacetDescriptorPathwayForFoodGroup> lookupFacetDescriptorPathwayForFoodSubSubgroup(final FoodSubgroup foodSubgroup) {
        var mixin = factoryService.mixin(FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup.class, foodSubgroup);
        return mixin.coll();
    }

    private Comparator<FacetDescriptorPathwayForFoodGroup> displayOrder() {
        return Comparator.comparing(FacetDescriptorPathwayForFoodGroup::getFacetDisplayOrder)
                .thenComparing(FacetDescriptorPathwayForFoodGroup::getDescriptorDisplayOrder);
    }

}
