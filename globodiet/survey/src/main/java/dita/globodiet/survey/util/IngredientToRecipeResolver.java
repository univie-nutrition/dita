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
package dita.globodiet.survey.util;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.recall24.dto.Annotated;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;

/**
 * If recipe ingredients are mapped to (nested) recipes,
 * this transformer resolves those nodes.
 */
public record IngredientToRecipeResolver(
        FoodDescriptionModel foodDescriptionModel,
        FoodToCompositeConverter foodToCompositeConverter) implements Transfomer {

    public IngredientToRecipeResolver(final FoodDescriptionModel foodDescriptionModel) {
        this(foodDescriptionModel, new FoodToCompositeConverter(foodDescriptionModel));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecallNode24> T transform(final T node) {

        return switch(node) {
            case Record24.Composite composite -> {
                var builder = (Composite.Builder) composite.asBuilder();
                var newComposite = builder.replaceSubRecords(this::transform) // recursive
                    .build();
                yield (T) newComposite;
            }
            case Record24.Food food -> {
                var fcdbId = food.lookupAnnotation("fcdbId")
                        .map(Annotated.Annotation.valueAs(SemanticIdentifier.class))
                        .orElse(null);
                if(fcdbId!=null) {
                    if(ObjectId.Context.RECIPE.matches(fcdbId)) {
                        var recipe = foodDescriptionModel.lookupRecipeBySid(fcdbId)
                                .orElse(null);
                        if(recipe!=null) {
                            var composite = foodToCompositeConverter.foodToRecipe(food, recipe, "recipe mapped by ingredient")
                                .build();
                            yield (T) composite;
                        }
                    }
                }
                yield (T) food;
            }
            default -> node;
        };
    }

}
