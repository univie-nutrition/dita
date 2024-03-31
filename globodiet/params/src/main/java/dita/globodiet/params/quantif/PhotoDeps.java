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
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.params.quantif;

import dita.globodiet.params.pathway.QuantificationMethodPathwayForFood;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFoodGroup_photoOrShape;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFood_photoOrShape;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForRecipe;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForRecipeGroup;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForRecipeGroup_photoOrShape;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForRecipe_photoOrShape;
import io.github.causewaystuff.companion.applib.decorate.CollectionTitleDecorator;
import io.github.causewaystuff.companion.applib.services.lookup.DependantLookupService;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import javax.annotation.processing.Generated;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Generated("io.github.causewaystuff.companion.codegen.domgen._GenDependants")
@Configuration
public class PhotoDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(Photo_dependentQuantificationMethodPathwayForFoodMappedByPhotoOrShape.class,
        Photo_dependentQuantificationMethodPathwayForFoodGroupMappedByPhotoOrShape.class,
        Photo_dependentQuantificationMethodPathwayForRecipeMappedByPhotoOrShape.class,
        Photo_dependentQuantificationMethodPathwayForRecipeGroupMappedByPhotoOrShape.class,
        Photo_dependentRecipeIngredientQuantificationMappedByPhoto.class);
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class Photo_dependentQuantificationMethodPathwayForFoodMappedByPhotoOrShape {
        @Inject
        DependantLookupService dependantLookup;

        private final Photo mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForFood> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForFood.class,
                QuantificationMethodPathwayForFood_photoOrShape.class,
                QuantificationMethodPathwayForFood_photoOrShape::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class Photo_dependentQuantificationMethodPathwayForFoodGroupMappedByPhotoOrShape {
        @Inject
        DependantLookupService dependantLookup;

        private final Photo mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForFoodGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForFoodGroup.class,
                QuantificationMethodPathwayForFoodGroup_photoOrShape.class,
                QuantificationMethodPathwayForFoodGroup_photoOrShape::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class Photo_dependentQuantificationMethodPathwayForRecipeMappedByPhotoOrShape {
        @Inject
        DependantLookupService dependantLookup;

        private final Photo mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForRecipe> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForRecipe.class,
                QuantificationMethodPathwayForRecipe_photoOrShape.class,
                QuantificationMethodPathwayForRecipe_photoOrShape::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class Photo_dependentQuantificationMethodPathwayForRecipeGroupMappedByPhotoOrShape {
        @Inject
        DependantLookupService dependantLookup;

        private final Photo mixee;

        @MemberSupport
        public List<QuantificationMethodPathwayForRecipeGroup> coll() {
            return dependantLookup.findDependants(
                QuantificationMethodPathwayForRecipeGroup.class,
                QuantificationMethodPathwayForRecipeGroup_photoOrShape.class,
                QuantificationMethodPathwayForRecipeGroup_photoOrShape::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class Photo_dependentRecipeIngredientQuantificationMappedByPhoto {
        @Inject
        DependantLookupService dependantLookup;

        private final Photo mixee;

        @MemberSupport
        public List<RecipeIngredientQuantification> coll() {
            return dependantLookup.findDependants(
                RecipeIngredientQuantification.class,
                RecipeIngredientQuantification_photo.class,
                RecipeIngredientQuantification_photo::prop,
                mixee);
        }
    }
}
