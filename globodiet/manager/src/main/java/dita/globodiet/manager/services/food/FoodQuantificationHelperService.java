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

import java.util.List;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.functional.Either;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.collections._Lists;

import lombok.NonNull;

import dita.globodiet.dom.params.classification.FoodGrouping;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.FoodDeps.Food_dependentQuantificationMethodPathwayForFoodMappedByFood;
import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodGroupDeps.FoodGroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import dita.globodiet.dom.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubSubgroup;
import dita.globodiet.dom.params.food_list.FoodSubgroupDeps.FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubgroup;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFood;
import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.manager.services.grouping.GroupingHelperService;
import dita.globodiet.manager.util.GroupingUtils;

@Service
public class FoodQuantificationHelperService {

    @Inject private FactoryService factoryService;
    @Inject private GroupingHelperService groupingHelperService;

    public List<QuantificationMethodPathwayForFoodGroup> effectiveQuantificationMethodPathwayForFoodClassification(
            final @NonNull FoodGrouping foodGrouping) {
        final @NonNull Either<FoodGroup, FoodSubgroup> foodClassification = foodGrouping.toEither();
        final List<QuantificationMethodPathwayForFoodGroup> facetQuantificationMethodForFoodGroup = foodClassification
            .fold(
                foodGroup->listQuantificationMethodPathwayForFoodGroup(foodGroup),
                foodSubOrSubSubgroup->{
                    var isSubSubgroup = GroupingUtils.isSubSubgroup(foodSubOrSubSubgroup);
                    var lookupResult = isSubSubgroup
                        ? listQuantificationMethodPathwayForFoodSubSubgroup(foodSubOrSubSubgroup)
                        : listQuantificationMethodPathwayForFoodSubgroup(foodSubOrSubSubgroup);

                    // if lookup was too specific, relax one level
                    if(lookupResult.isEmpty()
                            && isSubSubgroup) {
                        lookupResult = listQuantificationMethodPathwayForFoodSubgroup(foodSubOrSubSubgroup);
                    }
                    // again, if lookup was too specific, fallback to top-level = FoodGroup
                    return lookupResult.isEmpty()
                        ? listQuantificationMethodPathwayForFoodGroup(groupingHelperService.foodGroup(foodClassification.rightIfAny()))
                        : lookupResult;
                });

        return facetQuantificationMethodForFoodGroup;
    }

    public FoodGrouping effectiveGroupingUsedForQuantificationPathway(final Food food) {
        var foodClassification = groupingHelperService.foodClassification(food);
        final FoodGrouping foodGrouping = foodClassification
                .fold(
                    foodGroup->(FoodGrouping)foodGroup,
                    foodSubOrSubSubgroup->{
                        var groupingResult = foodSubOrSubSubgroup; // assignment is non final
                        var isSubSubgroup = GroupingUtils.isSubSubgroup(foodSubOrSubSubgroup);
                        var lookupResult = isSubSubgroup
                            ? listQuantificationMethodPathwayForFoodSubSubgroup(foodSubOrSubSubgroup)
                            : listQuantificationMethodPathwayForFoodSubgroup(foodSubOrSubSubgroup);

                        // if lookup was too specific, relax one level
                        if(lookupResult.isEmpty()
                                && isSubSubgroup) {

                            groupingResult = groupingHelperService.foodSubSubgroupToSubgroup(foodSubOrSubSubgroup);
                            lookupResult = listQuantificationMethodPathwayForFoodSubgroup(groupingResult);
                        }
                        // again, if lookup was too specific, fallback to top-level = FoodGroup
                        return lookupResult.isEmpty()
                            ? (FoodGrouping)groupingHelperService.foodGroup(foodClassification.rightIfAny())
                            : groupingResult;
                    });
        return foodGrouping;
    }

    public List<QuantificationMethodPathwayForFood> listQuantificationMethodPathwayForFood(final @NonNull Food food) {
        var mixin = factoryService.mixin(Food_dependentQuantificationMethodPathwayForFoodMappedByFood.class, food);
        return mixin.coll();
    }

    // -- HELPER

    private List<QuantificationMethodPathwayForFoodGroup> listQuantificationMethodPathwayForFoodGroup(
            final FoodGroup foodGroup) {
        var mixin = factoryService.mixin(
                FoodGroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodGroup.class,
                foodGroup);
        // include only those, that have no subgroup (or sub-subgroup)
        return _Lists.filter(mixin.coll(), groupPathway->groupPathway.getFoodSubgroupCode()==null);
    }

    private List<QuantificationMethodPathwayForFoodGroup> listQuantificationMethodPathwayForFoodSubgroup(
            final FoodSubgroup foodSubgroup) {
        _Assert.assertFalse(GroupingUtils.isSubSubgroup(foodSubgroup));
        var mixin = factoryService.mixin(
                FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubgroup.class,
                foodSubgroup);
        // include only those, that have no sub-subgroup)
        return _Lists.filter(mixin.coll(), groupPathway->groupPathway.getFoodSubSubgroupCode()==null);
    }

    private List<QuantificationMethodPathwayForFoodGroup> listQuantificationMethodPathwayForFoodSubSubgroup(
            final FoodSubgroup foodSubSubgroup) {
        var mixin = factoryService.mixin(
                FoodSubgroup_dependentQuantificationMethodPathwayForFoodGroupMappedByFoodSubSubgroup.class,
                foodSubSubgroup);
        return mixin.coll();
    }

//    private Comparator<QuantificationMethodPathwayForFoodGroup> displayOrder() {
//        return Comparator.comparing(QuantificationMethodPathwayForFoodGroup::getFacetDisplayOrder)
//                .thenComparing(QuantificationMethodPathwayForFoodGroup::getDescriptorDisplayOrder);
//    }

}
