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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.functional.Either;

import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodFacet;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.FoodDeps.Food_dependentFacetDescriptorPathwayForFoodMappedByFood;
import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForFood;
import lombok.NonNull;

@Service
public class FoodHelperService {

    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

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
            .map(FacetDescriptorPathwayForFood::getMandatoryInSequenceOfFacetsCode)
            .map(FoodFacet.SecondaryKey::new)
            .collect(Collectors.toSet());
    }

    // -- CLASSIFICATION

    public FoodGroup.SecondaryKey foodGroupAsSecondaryKey(final @NonNull Food food) {
        return new FoodGroup.SecondaryKey(food.getFoodGroupCode());
    }

    public FoodGroup.SecondaryKey foodGroupAsSecondaryKey(final @NonNull FoodSubgroup foodSubgroup) {
        return new FoodGroup.SecondaryKey(foodSubgroup.getFoodGroupCode());
    }

    public FoodGroup foodGroup(final @NonNull Food food) {
        return foreignKeyLookupService.unique(foodGroupAsSecondaryKey(food));
    }

    public FoodGroup foodGroup(final @NonNull FoodSubgroup foodSubgroup) {
        return foreignKeyLookupService.unique(foodGroupAsSecondaryKey(foodSubgroup));
    }

    public FoodSubgroup.SecondaryKey foodSubgroupAsSecondaryKey(final @NonNull Food food) {
        return new FoodSubgroup.SecondaryKey(
                food.getFoodGroupCode(),
                food.getFoodSubgroupCode(),
                food.getFoodSubSubgroupCode());
    }

    public Either<FoodGroup, FoodSubgroup> foodClassification(final @NonNull Food food) {
        var sub = foreignKeyLookupService.nullable(foodSubgroupAsSecondaryKey(food));
        return sub!=null
                ? Either.right(sub)
                : Either.left(foodGroup(food));
    }

}
