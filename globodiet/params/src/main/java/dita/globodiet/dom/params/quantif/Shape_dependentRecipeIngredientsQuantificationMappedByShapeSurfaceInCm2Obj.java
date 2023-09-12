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
 * 
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.quantif;

import dita.commons.services.foreignkey.DependantLookupService;
import dita.globodiet.dom.params.recipe_list.RecipeIngredientsQuantification;
import dita.globodiet.dom.params.recipe_list.RecipeIngredientsQuantification_shapeSurfaceInCm2Obj;
import jakarta.inject.Inject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;

@Collection
@RequiredArgsConstructor
public class Shape_dependentRecipeIngredientsQuantificationMappedByShapeSurfaceInCm2Obj {
    @Inject
    DependantLookupService dependantLookup;

    private final Shape mixee;

    @MemberSupport
    public List<RecipeIngredientsQuantification> coll() {
        return dependantLookup.findDependants(
            RecipeIngredientsQuantification.class,
            RecipeIngredientsQuantification_shapeSurfaceInCm2Obj.class,
            RecipeIngredientsQuantification_shapeSurfaceInCm2Obj::prop,
            mixee);
    }
}
