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
package dita.globodiet.dom.params.recipe_description;

import dita.commons.decorate.CollectionTitleDecorator;
import dita.commons.services.lookup.DependantLookupService;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipe;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipeGroup;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipeGroup_recipeFacet;
import dita.globodiet.dom.params.pathway.FacetDescriptorPathwayForRecipe_recipeFacet;
import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeFacetDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(RecipeFacet_dependentFacetDescriptorPathwayForRecipeMappedByRecipeFacet.class,
        RecipeFacet_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeFacet.class,
        RecipeFacet_dependentRecipeDescriptorMappedByRecipeFacet.class);
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeFacet_dependentFacetDescriptorPathwayForRecipeMappedByRecipeFacet {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeFacet mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForRecipe> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForRecipe.class,
                FacetDescriptorPathwayForRecipe_recipeFacet.class,
                FacetDescriptorPathwayForRecipe_recipeFacet::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeFacet_dependentFacetDescriptorPathwayForRecipeGroupMappedByRecipeFacet {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeFacet mixee;

        @MemberSupport
        public List<FacetDescriptorPathwayForRecipeGroup> coll() {
            return dependantLookup.findDependants(
                FacetDescriptorPathwayForRecipeGroup.class,
                FacetDescriptorPathwayForRecipeGroup_recipeFacet.class,
                FacetDescriptorPathwayForRecipeGroup_recipeFacet::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class RecipeFacet_dependentRecipeDescriptorMappedByRecipeFacet {
        @Inject
        DependantLookupService dependantLookup;

        private final RecipeFacet mixee;

        @MemberSupport
        public List<RecipeDescriptor> coll() {
            return dependantLookup.findDependants(
                RecipeDescriptor.class,
                RecipeDescriptor_recipeFacet.class,
                RecipeDescriptor_recipeFacet::prop,
                mixee);
        }
    }
}
