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
package dita.globodiet.params.food_list.mixin;

import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PrecedingParamsPolicy;

import dita.globodiet.params.food_list.Food.DietarySupplementQ;
import dita.globodiet.params.food_list.Food.GroupOrdinal;
import dita.globodiet.params.food_list.Food.TypeOfItem;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;

/**
 * Copy of {@link dita.globodiet.params.food_list.Food.Params}
 * with additional parameters needed to create a clone.
 *
 * @see dita.globodiet.params.food_list.Food.Params
 */
public record FoodParamsForClone(
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY
        )
        @ParameterLayout(
                describedAs = "Identification Code for Food, Product, On-the-fly Recipe or Alias"
        )
        String code,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.OPTIONAL
        )
        @ParameterLayout(
                describedAs = "Food Group code"
        )
        FoodGroup foodGroup,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                optionality = Optionality.OPTIONAL
        )
        @ParameterLayout(
                describedAs = "Food Subgroup code"
        )
        FoodSubgroup foodSubgroup,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.RESET,
                optionality = Optionality.OPTIONAL
        )
        @ParameterLayout(
                describedAs = "Food Sub(sub)group code"
        )
        FoodSubgroup foodSubSubgroup,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY
        )
        @ParameterLayout(
                describedAs = "Native (localized) name of this Food, Product, On-the-fly Recipe or Alias"
        )
        String foodNativeName,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY
        )
        @ParameterLayout(
                describedAs = "Type of item:\n"
                                + "(none) -> Normal Food Item\n"
                                + "GI -> Generic Food Item\n"
                                + "SH -> Shadow Item\n"
                                + "CR -> Composed Recipe (On-the-fly Recipe)\n"
                                + "Definition: its different ingredients can be identified and\n"
                                + "quantified separately after preparation\n"
                                + "(e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)\n"
                                + "or just before mixing (e.g. coffee with milk).\n"
                                + "Composed recipes are built during the interview: there is no a priori list of composed recipes.\n"
                                + "They are made from items listed below/linked to a quick list item.\n"
                                + "Example: Salad\n"
                                + "- Lettuce\n"
                                + "- Tomato\n"
                                + "- Cucumber\n"
                                + "- Salad dressing (can be a recipe in some projects where all sauces are in recipes)"
        )
        TypeOfItem typeOfItem,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY
        )
        @ParameterLayout(
                describedAs = "Auxiliary field to force an internal order within each subgroup\n"
                                + "(if GI then 1 otherwise 2, this forces the GI at the top)"
        )
        GroupOrdinal groupOrdinal,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY
        )
        @ParameterLayout(
                describedAs = "0=food\n"
                                + "1=dietary supplement"
        )
        DietarySupplementQ dietarySupplementQ,

        // -- ADDITIONAL PARAMETERS

        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY)
        @ParameterLayout(
                describedAs = "Nutrient value for energy in kcal.")
        double energy,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY)
        @ParameterLayout(
                describedAs = "Nutrient value for fat in g.")
        double fat,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY)
        @ParameterLayout(
                describedAs = "Nutrient value for carbohydrates in g.")
        double carbohydrate,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY)
        @ParameterLayout(
                describedAs = "Nutrient value for protein in g.")
        double protein,
        @Parameter(
                precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                optionality = Optionality.MANDATORY)
        @ParameterLayout(
                describedAs = "Nutrient value for alcohol in g.")
        double alcohol

        ) {
}
