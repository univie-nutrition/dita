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
import dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification;
import dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_shapeThickness;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThicknessForShapeMethodDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(ThicknessForShapeMethod_dependentRecipeIngredientQuantificationMappedByShapeThickness.class);
    }

    @Collection
    @RequiredArgsConstructor
    public static class ThicknessForShapeMethod_dependentRecipeIngredientQuantificationMappedByShapeThickness {
        @Inject
        DependantLookupService dependantLookup;

        private final ThicknessForShapeMethod mixee;

        @MemberSupport
        public List<RecipeIngredientQuantification> coll() {
            return dependantLookup.findDependants(
                RecipeIngredientQuantification.class,
                RecipeIngredientQuantification_shapeThickness.class,
                RecipeIngredientQuantification_shapeThickness::prop,
                mixee);
        }
    }
}
