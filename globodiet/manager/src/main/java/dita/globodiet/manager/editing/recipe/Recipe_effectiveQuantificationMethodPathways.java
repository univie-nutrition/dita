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
package dita.globodiet.manager.editing.recipe;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Where;

import lombok.RequiredArgsConstructor;

import dita.globodiet.dom.params.pathway.QuantificationMethodPathwayForRecipeGroup;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.manager.services.recipe.RecipeQuantificationHelperService;
import dita.globodiet.manager.util.QuantificationMethodPathwayKey;

/**
 * With {@link QuantificationMethodPathwayForRecipeGroup} a set of quantification methods is defined
 * for a specific recipe group.
 * Optionally, for an individual recipe, only a subset of those methods can be selected.
 */
@Collection
@CollectionLayout(
        hidden = Where.ALL_TABLES,
        sequence = "0.2",
        describedAs = "Quantification Method Pathways in effect associated with this individual recipe.\n\n"
                + "With QuantificationMethodForRecipeGroup (table QM_RCLAS) a set of methods is defined "
                + "for a specific recipe group.\n\n"
                + "Optionally, for an individual recipe, only a subset of those methods can be selected "
                + "using QuantificationMethodPathwayForRecipe (table QM_RECIP).")
@RequiredArgsConstructor
public class Recipe_effectiveQuantificationMethodPathways {

    @Inject private RecipeQuantificationHelperService recipeQuantificationHelperService;

    protected final Recipe mixee;

    @MemberSupport
    public List<QuantificationMethodPathwayForRecipeGroup> coll() {

        var grouping = recipeQuantificationHelperService.effectiveGroupingUsedForQuantificationPathway(mixee);

        var quantificationMethodPathwayAsDefinedByRecipeClassification = recipeQuantificationHelperService
                .effectiveQuantificationMethodPathwayForRecipeClassification(grouping);

        // filter by selected at food level (Dependent Quantification Method Pathway For Recipe mapped by Recipe)
        var quantificationMethodPathwayForRecipe = recipeQuantificationHelperService.listQuantificationMethodPathwayForRecipe(mixee);
        if(quantificationMethodPathwayForRecipe.isEmpty()) {
            return quantificationMethodPathwayAsDefinedByRecipeClassification;
        }
        final Set<QuantificationMethodPathwayKey> selectedKeys = quantificationMethodPathwayForRecipe.stream()
            .map(QuantificationMethodPathwayKey::valueOf)
            .collect(Collectors.toSet());
        return quantificationMethodPathwayAsDefinedByRecipeClassification.stream()
                .filter(qmp->selectedKeys.contains(QuantificationMethodPathwayKey.valueOf(qmp)))
                //TODO ordering?
                .toList();
    }

}
