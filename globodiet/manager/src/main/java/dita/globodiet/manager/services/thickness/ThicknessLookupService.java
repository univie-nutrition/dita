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
package dita.globodiet.manager.services.thickness;

import jakarta.inject.Inject;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;

import dita.globodiet.dom.params.classification.FoodGrouping.FoodGroupingKey;
import dita.globodiet.dom.params.classification.RecipeGrouping.RecipeGroupingKey;
import dita.globodiet.dom.params.quantif.ThicknessForShape;

public class ThicknessLookupService {

    @Inject RepositoryService repositoryService;

    public Can<ThicknessForShape> lookupThicknessForFood(final FoodGroupingKey key) {
        return Can.ofCollection(repositoryService.allMatches(ThicknessForShape.class, thickness->
            key.equalsOrIsContainedIn(thickness.getFoodGroupingLookupKey())));
    }

    public Can<ThicknessForShape> lookupThicknessForRecipe(final RecipeGroupingKey key) {
        return Can.ofCollection(repositoryService.allMatches(ThicknessForShape.class, thickness->
            key.equalsOrIsContainedIn(thickness.getRecipeGroupingLookupKey())));
    }

}
