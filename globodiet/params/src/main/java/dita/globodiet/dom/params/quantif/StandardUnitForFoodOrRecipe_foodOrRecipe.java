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

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.dom.params.recipe_list.Recipe;
import jakarta.inject.Inject;
import java.lang.Object;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;

@Property(
        snapshot = Snapshot.EXCLUDED
)
@PropertyLayout(
        sequence = "2.1",
        describedAs = "Food or Recipe identification number (=FOODMUM for food or =R_IDNUM for recipe); either Foods.foodnum OR Mixedrec.r_idnum",
        hidden = Where.REFERENCES_PARENT
)
@RequiredArgsConstructor
public class StandardUnitForFoodOrRecipe_foodOrRecipe {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final StandardUnitForFoodOrRecipe mixee;

    @MemberSupport
    public Object prop() {
        return foreignKeyLookup
            .either(
                this,
                // local
                mixee, mixee.getFoodOrRecipeCode(),
                // foreign
                FoodOrProductOrAlias.class, foreign->foreign.getFoodIdNumber(),
                Recipe.class, foreign->foreign.getCode())
            .map(either->either.isLeft()
                ? either.leftIfAny()
                : either.rightIfAny())
            .orElse(null);
    }
}
