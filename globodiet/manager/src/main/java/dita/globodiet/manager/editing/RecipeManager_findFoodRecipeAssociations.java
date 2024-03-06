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
package dita.globodiet.manager.editing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.TextUtils;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import dita.commons.format.FormatUtils;
import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.manager.DitaModuleGdManager;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="listOfRecipe", position = Position.PANEL)
@RequiredArgsConstructor
public class RecipeManager_findFoodRecipeAssociations {

    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    //@Inject private CollectionContentsAsExcelExporter excelExporter;

    final Recipe.Manager recipeManager;

    /**
     * WIP - yet used for analysis of existing data, specific to Austrian data
     */
    @MemberSupport @SneakyThrows
    public List<FoodRecipeAssociation> act() {

        var recipes = Can.ofCollection(repositoryService.allInstances(Recipe.class));
        var foodRecpAssociations = recipes.stream()
            .filter(this::isPrefixed)
            .map(recipe->{

                var associatedFoodCode = FormatUtils.fillWithLeadingZeros(5,
                        TextUtils.cutter(recipe.getName()).keepBefore("_").getValue());
                var associatedFood = foreignKeyLookupService.unique(new Food.SecondaryKey(associatedFoodCode));

                var recipeSimpleName = TextUtils.cutter(recipe.getName()).keepAfter("_").getValue();
                var recipeExtendedName = String.format("%s {assocFood=%s}", recipeSimpleName, FormatUtils.noLeadingZeros(associatedFoodCode));
                var foodSimpleName = associatedFood.getFoodNativeName();
                var foodExtendedName = String.format("%s {assocRecp=%s}", foodSimpleName, FormatUtils.noLeadingZeros(recipe.getCode()));

                var foodRecpAssoc = new FoodRecipeAssociation(
                        recipe.getCode(),
                        recipeSimpleName,
                        recipeExtendedName,

                        associatedFood.getCode(),
                        foodSimpleName,
                        foodExtendedName,
                        String.format("%s(%s)", recipe.getStatus().name(), recipe.getStatus().getMatchOn()),
                        coffeeFoodCodes.contains(associatedFoodCode)
                            ? "SHOWN(1)"
                            : "HIDDEN(!1)",
                        coffeeFoodCodes.contains(associatedFoodCode)
                            ? recipe.getStatus() == Recipe.Status.FINALIZED
                            : recipe.getStatus() != Recipe.Status.FINALIZED,
                        true);

                associatedFood.setFoodNativeName(foodExtendedName);
                recipe.setName(recipeExtendedName);

                return foodRecpAssoc;
        })
        .collect(Collectors.toCollection(ArrayList::new));
        /*
        var foods = Can.ofCollection(repositoryService.allInstances(Food.class));
        var foodRecpAssociationsBySimilarName = recipes.stream()
                .filter(_Predicates.not(this::isPrefixed))
                .map(recipe->{

                    var associatedFood = foods.stream()
                            .filter(food->Objects.equals(food.getFoodNativeName(), recipe.getName()))
                            .findFirst()
                            .orElse(null);
                    if(associatedFood==null) return null;

                    var associatedFoodCode = associatedFood.getCode();

                    var recipeSimpleName = recipe.getName();
                    var recipeExtendedName = String.format("%s {assocFood=%s}", recipeSimpleName, FormatUtils.noLeadingZeros(associatedFoodCode));
                    var foodSimpleName = associatedFood.getFoodNativeName();
                    var foodExtendedName = String.format("%s {assocRecp=%s}", foodSimpleName, FormatUtils.noLeadingZeros(recipe.getCode()));

                    System.err.printf("%s<->%s%n", foodExtendedName, recipeExtendedName);

                    var foodRecpAssoc = new FoodRecipeAssociation(
                            recipe.getCode(),
                            recipeSimpleName,
                            recipeExtendedName,

                            associatedFood.getCode(),
                            foodSimpleName,
                            foodExtendedName,
                            String.format("%s(%s)", recipe.getStatus().name(), recipe.getStatus().getMatchOn()),
                            coffeeFoodCodes.contains(associatedFoodCode)
                                ? "SHOWN(1)"
                                : "HIDDEN(!1)",
                            coffeeFoodCodes.contains(associatedFoodCode)
                                ? recipe.getStatus() == Recipe.Status.FINALIZED
                                : recipe.getStatus() != Recipe.Status.FINALIZED,
                            false);
                    return foodRecpAssoc;
            })
            .filter(Objects::nonNull)
            .toList();

        foodRecpAssociations.addAll(foodRecpAssociationsBySimilarName);*/

        return foodRecpAssociations;
    }

    private boolean isPrefixed(final Recipe recipe) {
        return Character.isDigit(recipe.getName().charAt(0));
    }

    @Named(DitaModuleGdManager.NAMESPACE + ".FoodRecipeAssociation")
    @DomainObject(nature = Nature.VIEW_MODEL)
    public static record FoodRecipeAssociation(
        String recipeCode,
        String recipeName,
        String recipeNameWithAttributes,
        String foodCode,
        String foodName,
        String foodNameWithAttributes,
        String recipeCurrentStatus,
        String recipeDesiredStatus,
        boolean recipeCurrentMeetsDesiredStatus,
        boolean prefixed) {

        public StringBuilder appendAsYaml(final StringBuilder yaml) {
            yaml.append(" -recipeCode: ").append(recipeCode).append("\n");
            yaml.append("  recipeName: ").append(recipeName).append("\n");
            yaml.append("  recipeNameWithAttributes: ").append(recipeNameWithAttributes).append("\n");

            yaml.append("  foodCode: ").append(foodCode).append("\n");
            yaml.append("  foodName: ").append(foodName).append("\n");
            yaml.append("  foodNameWithAttributes: ").append(foodNameWithAttributes).append("\n");

            yaml.append("  recipeCurrentStatus: ").append(recipeCurrentStatus).append("\n");
            yaml.append("  recipeDesiredStatus: ").append(recipeDesiredStatus).append("\n");
            return yaml;
        }
    }

    final List<String> coffeeFoodCodes = List.of(
            "01333",
            "01334",
            "01335",
            "01330",
            "01331",
            "01332",
            "01336",
            "01337",
            "01338",
            "01339",
            "01340",
            "01341",
            "01342",
            "01343",
            "01719",
            "01720",
            "01721",
            "02246",
            "02248",
            "02247",
            "01961",
            "01960",
            "01959",
            "02251",
            "02250",
            "02249",
            "01344",
            "01345",
            "01346");

}
