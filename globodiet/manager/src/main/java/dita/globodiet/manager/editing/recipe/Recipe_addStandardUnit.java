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
import java.util.stream.IntStream;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

import dita.commons.format.FormatUtils;
import org.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.manager.editing.food.Food_addStandardUnit;

/**
 * @see Food_addStandardUnit
 */
@Action
@ActionLayout(
        associateWith = "dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe",
        position = Position.PANEL,
        sequence = "1",
        describedAs = "Add a Standard Unit to this Recipe's Quantification Pathway")
@RequiredArgsConstructor
public class Recipe_addStandardUnit {

    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    final Recipe mixee;

    @MemberSupport
    public Recipe act(
            @Parameter final String code,
            @Parameter final StandardUnitForFoodOrRecipe.RawOrCooked rawOrCooked,
            @Parameter final StandardUnitForFoodOrRecipe.WithUnediblePartQ withUnediblePartQ,
            @Parameter @ParameterLayout(multiLine = 3)
            final String comment,
            @Parameter final double quantity,
            @Parameter final StandardUnitForFoodOrRecipe.Unit unit,
            @Parameter final double displayOrder) {

        var entity = repositoryService.detachedEntity(new StandardUnitForFoodOrRecipe());

        entity.setCode(code);
        entity.setType(StandardUnitForFoodOrRecipe.Type.RECIPE);
        entity.setFoodOrRecipeCode(mixee.getCode());
        entity.setRawOrCooked(rawOrCooked);
        entity.setWithUnediblePartQ(withUnediblePartQ);
        entity.setComment(comment);
        entity.setQuantity(quantity);
        entity.setUnit(unit);
        entity.setDisplayOrder(displayOrder);

        repositoryService.persist(entity);
        foreignKeyLookupService.clearCache(StandardUnitForFoodOrRecipe.class);
        return mixee;
    }

    @MemberSupport
    public List<String> choicesCode() {
        return IntStream.rangeClosed(1, 30) //TODO simply autogen. next free
            .mapToObj(i->FormatUtils.fillWithLeadingZeros(4, "00"+i))
            .toList();
    }

}
