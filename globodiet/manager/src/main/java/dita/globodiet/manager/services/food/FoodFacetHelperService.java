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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.collections._Lists;

import lombok.NonNull;

import dita.globodiet.params.food_descript.FoodFacet;
import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.food_list.FoodDeps.Food_dependentFacetDescriptorPathwayForFoodMappedByFood;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodGroupDeps.FoodGroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import dita.globodiet.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup;
import dita.globodiet.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFood;
import dita.globodiet.params.pathway.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.manager.services.grouping.GroupingHelperService;
import dita.globodiet.manager.util.GroupingUtils;
import dita.globodiet.params.classification.FoodGrouping;

@Service
public class FoodFacetHelperService {

    @Inject private FactoryService factoryService;
    @Inject private GroupingHelperService groupingHelperService;

    public List<FacetDescriptorPathwayForFoodGroup> effectiveFacetDescriptorPathwayForFoodClassification(
            final @NonNull FoodGrouping foodGrouping) {
        final @NonNull Either<FoodGroup, FoodSubgroup> foodClassification = foodGrouping.toEither();
        final List<FacetDescriptorPathwayForFoodGroup> facetDescriptorPathwayForFoodGroup = foodClassification
            .fold(
                foodGroup->listFacetDescriptorPathwayForFoodGroup(foodGroup),
                foodSubOrSubSubgroup->{
                    var isSubSubgroup = GroupingUtils.isSubSubgroup(foodSubOrSubSubgroup);
                    var lookupResult = isSubSubgroup
                        ? listFacetDescriptorPathwayForFoodSubSubgroup(foodSubOrSubSubgroup)
                        : listFacetDescriptorPathwayForFoodSubgroup(foodSubOrSubSubgroup);

                    // if lookup was too specific, relax one level
                    if(lookupResult.isEmpty()
                            && isSubSubgroup) {
                        lookupResult = listFacetDescriptorPathwayForFoodSubgroup(foodSubOrSubSubgroup);
                    }
                    // again, if lookup was too specific, fallback to top-level = FoodGroup
                    return lookupResult.isEmpty()
                        ? listFacetDescriptorPathwayForFoodGroup(groupingHelperService.foodGroup(foodClassification.rightIfAny()))
                        : lookupResult;
                });

        return facetDescriptorPathwayForFoodGroup;
    }

    public FoodGrouping effectiveGroupingUsedForFacetDescriptorPathway(final @NonNull Food food) {
        var foodClassification = groupingHelperService.foodClassification(food);
        final FoodGrouping foodGrouping = foodClassification
                .fold(
                    foodGroup->(FoodGrouping)foodGroup,
                    foodSubOrSubSubgroup->{
                        var groupingResult = foodSubOrSubSubgroup; // assignment is non final
                        var isSubSubgroup = GroupingUtils.isSubSubgroup(foodSubOrSubSubgroup);
                        var lookupResult = isSubSubgroup
                            ? listFacetDescriptorPathwayForFoodSubSubgroup(foodSubOrSubSubgroup)
                            : listFacetDescriptorPathwayForFoodSubgroup(foodSubOrSubSubgroup);

                        // if lookup was too specific, relax one level
                        if(lookupResult.isEmpty()
                                && isSubSubgroup) {

                            groupingResult = groupingHelperService.foodSubSubgroupToSubgroup(foodSubOrSubSubgroup);
                            lookupResult = listFacetDescriptorPathwayForFoodSubgroup(groupingResult);
                        }
                        // again, if lookup was too specific, fallback to top-level = FoodGroup
                        return lookupResult.isEmpty()
                            ? (FoodGrouping)groupingHelperService.foodGroup(foodClassification.rightIfAny())
                            : groupingResult;
                    });
        return foodGrouping;
    }

    public List<FacetDescriptorPathwayForFoodGroup> effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(
            final @NonNull FoodGrouping foodGrouping) {
        return effectiveFacetDescriptorPathwayForFoodClassification(foodGrouping).stream()
                .sorted(displayOrder())
                .toList();
    }

    public List<FacetDescriptorPathwayForFoodGroup> effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(
            final @NonNull Food food) {
        return effectiveFacetDescriptorPathwayForFoodClassificationHonoringDisplayOrder(
                effectiveGroupingUsedForFacetDescriptorPathway(food));
    }

    public List<FacetDescriptorPathwayForFood> listFacetDescriptorPathwayForFood(final @NonNull Food food) {
        var mixin = factoryService.mixin(Food_dependentFacetDescriptorPathwayForFoodMappedByFood.class, food);
        return mixin.coll();
    }

    public List<String> facetCodesAllowedForFoodGrouping(final FoodGrouping foodGrouping) {
        return effectiveFacetDescriptorPathwayForFoodClassification(foodGrouping)
                .stream()
                .map(FacetDescriptorPathwayForFoodGroup::getFoodFacetCode)
                .toList();
    }

    public List<FacetDescriptorPathwayForFood> selectedFacetDescriptorPathwayForFood(
            final @Nullable Food food) {
        if(food==null) return Collections.emptyList();
        var mixin = factoryService.mixin(Food_dependentFacetDescriptorPathwayForFoodMappedByFood.class, food);
        return mixin.coll();
    }

    public Set<FoodFacet.SecondaryKey> selectedFacetDescriptorPathwayForFoodAsFoodFacetSecondaryKeySet(
            final @Nullable Food food) {
        return selectedFacetDescriptorPathwayForFood(food)
            .stream()
            .map(FacetDescriptorPathwayForFood::getSelectedFoodFacetCode)
            .map(FoodFacet.SecondaryKey::new)
            .collect(Collectors.toSet());
    }

    // -- HELPER

    private List<FacetDescriptorPathwayForFoodGroup> listFacetDescriptorPathwayForFoodGroup(
            final FoodGroup foodGroup) {
        var mixin = factoryService.mixin(
                FoodGroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodGroup.class,
                foodGroup);
        // include only those, that have no subgroup (or sub-subgroup)
        return _Lists.filter(mixin.coll(), groupPathway->groupPathway.getFoodSubgroupCode()==null
                && groupPathway.getFoodSubSubgroupCode()==null);
    }

    private List<FacetDescriptorPathwayForFoodGroup> listFacetDescriptorPathwayForFoodSubgroup(
            final FoodSubgroup foodSubgroup) {
        _Assert.assertFalse(GroupingUtils.isSubSubgroup(foodSubgroup));
        var mixin = factoryService.mixin(
                FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubgroup.class,
                foodSubgroup);
        // include only those, that have no sub-subgroup)
        return _Lists.filter(mixin.coll(), groupPathway->groupPathway.getFoodSubSubgroupCode()==null);
    }

    private List<FacetDescriptorPathwayForFoodGroup> listFacetDescriptorPathwayForFoodSubSubgroup(
            final FoodSubgroup foodSubSubgroup) {
        var mixin = factoryService.mixin(
                FoodSubgroup_dependentFacetDescriptorPathwayForFoodGroupMappedByFoodSubSubgroup.class,
                foodSubSubgroup);
        return mixin.coll();
    }

    private Comparator<FacetDescriptorPathwayForFoodGroup> displayOrder() {
        return Comparator.comparing(FacetDescriptorPathwayForFoodGroup::getFacetDisplayOrder)
                .thenComparing(FacetDescriptorPathwayForFoodGroup::getDescriptorDisplayOrder);
    }

}
