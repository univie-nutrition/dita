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
package dita.globodiet.dom.params.recipe_list;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_list.Food;
import jakarta.inject.Inject;
import java.lang.Object;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

@Property(
        snapshot = Snapshot.EXCLUDED
)
@PropertyLayout(
        sequence = "38.1",
        describedAs = "Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum\n"
                        + "----\n"
                        + "required=true, unique=false",
        hidden = Where.NOWHERE
)
@RequiredArgsConstructor
public class RecipeIngredient_foodOrRecipe {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final RecipeIngredient mixee;

    @MemberSupport
    public Object prop() {
        final int switchOn = foreignKeyLookup.switchOn(mixee);
        switch(switchOn) {
        case 1: {
            if(mixee.getFoodOrRecipeCode()==null) return null;
            final var lookupKey = new Food.SecondaryKey(mixee.getFoodOrRecipeCode());
            return foreignKeyLookup.unique(lookupKey);
        }
        case 2: {
            if(mixee.getFoodOrRecipeCode()==null) return null;
            final var lookupKey = new Recipe.SecondaryKey(mixee.getFoodOrRecipeCode());
            return foreignKeyLookup.unique(lookupKey);
        }}
        throw _Exceptions.unexpectedCodeReach();
    }
}
