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
package dita.globodiet.manager.lookup;

import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DomainObjectLayout;

import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import dita.globodiet.dom.params.recipe_description.RecipeDescriptor;
import dita.globodiet.manager.lookup.SecondaryKeys.FacetDescriptorKey;
import dita.globodiet.manager.lookup.SecondaryKeys.RecipeDescriptorKey;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Unresolvables {

    // -- FACET DESCRIPTOR

    public FacetDescriptorNotFound facetDescriptorNotFound(final FacetDescriptorKey key) {
        val entity = new FacetDescriptorNotFound("UNRESOLVABLE");
        entity.setFacetCode(key.facetCode());
        entity.setCode(key.descriptorCode());
        return entity;
    }

    @DomainObjectLayout(
            cssClassFa = "tag red")
    public class FacetDescriptorNotFound extends FacetDescriptor
    implements ViewModel {
        public FacetDescriptorNotFound(final String memento) { setName(memento); }
        @Override public String viewModelMemento() { return getName(); }
    }

    // -- RECIPE DESCRIPTOR

    public RecipeDescriptorNotFound recipeDescriptorNotFound(final RecipeDescriptorKey key) {
        val entity = new RecipeDescriptorNotFound("UNRESOLVABLE");
        entity.setCode(key.recipeDescriptorCode());
        entity.setRecipeFacetCode(key.recipeFacetCode());
        return entity;
    }

    @DomainObjectLayout(
            cssClassFa = "solid tag red")
    public class RecipeDescriptorNotFound extends RecipeDescriptor
    implements ViewModel {
        public RecipeDescriptorNotFound(final String memento) { setName(memento); }
        @Override public String viewModelMemento() { return getName(); }
    }

    // -- FOOD SUBGROUP

    public FoodSubgroupNotFound foodSubgroupNotFound(final String d1, final String d2, final String d3) {
        val entity = new FoodSubgroupNotFound("UNRESOLVABLE");
        entity.setFoodGroupCode(d1);
        entity.setFoodSubgroupCode(d2);
        entity.setFoodSubSubgroupCode(d3);
        return entity;
    }

    @DomainObjectLayout(
            cssClassFa = "solid layer-group red")
    public class FoodSubgroupNotFound extends FoodSubgroup
    implements ViewModel {
        public FoodSubgroupNotFound(final String memento) { setName(memento); }
        @Override public String viewModelMemento() { return getName(); }
    }

    // --

}
