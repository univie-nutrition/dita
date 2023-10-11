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
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.quantif;

import dita.commons.services.lookup.DependantLookupService;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFood;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_photo;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFood_photo;
import dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification;
import dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_photo;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe_photo;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodsPathwayForRecipeGroup;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodsPathwayForRecipeGroup_photo;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhotoForQuantityDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(PhotoForQuantity_dependentQuantificationMethodsPathwayForFoodMappedByPhoto.class,
        PhotoForQuantity_dependentQuantificationMethodsPathwayForFoodGroupMappedByPhoto.class,
        PhotoForQuantity_dependentRecipeIngredientQuantificationMappedByPhoto.class,
        PhotoForQuantity_dependentQuantificationMethodPathwayForRecipeMappedByPhoto.class,
        PhotoForQuantity_dependentQuantificationMethodsPathwayForRecipeGroupMappedByPhoto.class);
    }

    @Collection
    @RequiredArgsConstructor
    public static class PhotoForQuantity_dependentQuantificationMethodsPathwayForFoodMappedByPhoto {
        @Inject
        DependantLookupService dependantLookup;

        private final PhotoForQuantity mixee;

        @MemberSupport
        public List<QuantificationMethodsPathwayForFood> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodsPathwayForFood.class,
                QuantificationMethodsPathwayForFood_photo.class,
                QuantificationMethodsPathwayForFood_photo::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class PhotoForQuantity_dependentQuantificationMethodsPathwayForFoodGroupMappedByPhoto {
        @Inject
        DependantLookupService dependantLookup;

        private final PhotoForQuantity mixee;

        @MemberSupport
        public List<QuantificationMethodsPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodsPathwayForFoodGroup.class,
                QuantificationMethodsPathwayForFoodGroup_photo.class,
                QuantificationMethodsPathwayForFoodGroup_photo::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class PhotoForQuantity_dependentRecipeIngredientQuantificationMappedByPhoto {
        @Inject
        DependantLookupService dependantLookup;

        private final PhotoForQuantity mixee;

        @MemberSupport
        public List<RecipeIngredientQuantification> coll() {
            return dependantLookup.findDependants(
                RecipeIngredientQuantification.class,
                RecipeIngredientQuantification_photo.class,
                RecipeIngredientQuantification_photo::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class PhotoForQuantity_dependentQuantificationMethodPathwayForRecipeMappedByPhoto {
        @Inject
        DependantLookupService dependantLookup;

        private final PhotoForQuantity mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForRecipe> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForRecipe.class,
                QuantificationMethodPathwayForRecipe_photo.class,
                QuantificationMethodPathwayForRecipe_photo::prop,
                mixee);
        }
    }

    @Collection
    @RequiredArgsConstructor
    public static class PhotoForQuantity_dependentQuantificationMethodsPathwayForRecipeGroupMappedByPhoto {
        @Inject
        DependantLookupService dependantLookup;

        private final PhotoForQuantity mixee;

        @MemberSupport
        public List<QuantificationMethodsPathwayForRecipeGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodsPathwayForRecipeGroup.class,
                QuantificationMethodsPathwayForRecipeGroup_photo.class,
                QuantificationMethodsPathwayForRecipeGroup_photo::prop,
                mixee);
        }
    }
}