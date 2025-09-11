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

import java.util.ArrayList;
import java.util.List;

import org.apache.causeway.commons.collections.Can;
import dita.commons.sid.SemanticIdentifier;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.Record24.Food;
import dita.recall24.dto.Record24.FryingFat;

public record FryingFatHandler(
    Composite composite,
    /**
     * frying fat as used during cooking, associated with the above food
     */
    FryingFat fryingFat,
    List<Food> handledIngredients) {

    public FryingFatHandler(
            final Composite composite,
            final FryingFat fryingFat) {
        this(composite, fryingFat, new ArrayList<>());
    }

    Record24 handleIngredient(final Food newIngredient, final SemanticIdentifier foodGroupSid) {
        if(foodGroupSid.objectId().objectSimpleId().startsWith("10")) { //TODO externalize hardcoded fat group '10' as config
            handledIngredients.add(newIngredient);
            return origFattyFoodAsComment(newIngredient);
        }
        return newIngredient;
    }

    void print() {
        if(handledIngredients.size()==0) return;

        var coors = Correction24.CompositeCorr.Coordinates.of(composite);
        System.err.printf("COMPOSITE: %s%n", composite.name());
        System.err.printf("  coors: '%s %s #%s %s %s'%n",
            coors.sid().objectId(),
            coors.respondentId(),
            coors.interviewOrdinal(),
            coors.mealHourOfDay(),
            coors.source().names().join("/"));
        System.err.printf("  FRYING_FAT: %s %.2fg%n", fryingFat.name(), fryingFat.amountConsumed().doubleValue());
        if(handledIngredients.size()==1) {
            var handledIngredient = handledIngredients.get(0);
            System.err.printf("  INGREDIENT_REMOVED: %s %.2fg%n", handledIngredient.name(), handledIngredient.amountConsumed().doubleValue());
        } else {
            System.err.printf("  #WARNING fatty ingredient count = %d%n", handledIngredients.size());
            handledIngredients.forEach(handledIngredient->
                System.err.printf("  INGREDIENT_REMOVED: %s %.2fg%n", handledIngredient.name(), handledIngredient.amountConsumed().doubleValue()));
        }
    }

    // -- HELPER

    private Record24.Comment origFattyFoodAsComment(final Food origFood) {
        var name = "fatty recipe ingredient overruled by frying fat data below\n"
            + "%s, amount=%s".formatted(
                origFood.name().replace(" {", ", ").replace("}", ""),
                origFood.consumptionUnit().format(origFood.amountConsumed()));

        return Record24.comment(name, origFood.sid(), origFood.facetSids(),
                origFood.streamAnnotations().collect(Can.toCan()));
    }

}
