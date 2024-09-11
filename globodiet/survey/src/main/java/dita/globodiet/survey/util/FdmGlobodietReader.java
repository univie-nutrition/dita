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

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.DataSource;

import dita.commons.format.FormatUtils;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Table;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredient;

public record FdmGlobodietReader(
        SystemId systemId,
        TabularData tabularData) {

    // -- FACTORIES

    public static FdmGlobodietReader fromZippedYaml(
            final SystemId systemId,
            final DataSource ds) {
        var yaml = Blob.tryRead("fdm", CommonMimeType.ZIP, ds)
                .valueAsNonNullElseFail()
                .unZip(CommonMimeType.YAML)
                .toClob(StandardCharsets.UTF_8)
                .asString();

        var tabularData = TabularData.populateFromYaml(yaml, TabularData.Format.defaults());
        return new FdmGlobodietReader(systemId, tabularData);
    }

    public FoodDescriptionModel createFoodDescriptionModel() {
        return new FoodDescriptionModel(
                foodBySid(),
                recipeBySid(),
                ingredientsByRecipeSid());
    }

    // -- FOOD

    public Stream<FoodDescriptionModel.Food> streamFood() {
        return lookupTableByKey("dita.globodiet.params.food_list.Food").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->foodFromRowData(row.cellLiterals()))
            .filter(Objects::nonNull);
    }

    public Map<SemanticIdentifier, Food> foodBySid() {
        final Map<SemanticIdentifier, Food> map = new HashMap<>();
        streamFood()
            .forEach(food->map.put(food.sid(), food));
        return map;
    }

    // -- RECIPE

    public Stream<Recipe> streamRecipe() {
        return lookupTableByKey("dita.globodiet.params.recipe_list.Recipe").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeFromRowData(row.cellLiterals()));
    }

    public Map<SemanticIdentifier, Recipe> recipeBySid() {
        final Map<SemanticIdentifier, Recipe> map = new HashMap<>();
        streamRecipe()
            .forEach(recipe->map.put(recipe.sid(), recipe));
        return map;
    }

    // -- RECIPE INGREDIENT

    public Stream<RecipeIngredient> streamRecipeIngredient() {
        return lookupTableByKey("dita.globodiet.params.recipe_list.RecipeIngredient").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeIngredientFromRowData(row.cellLiterals()));
    }

    public Map<SemanticIdentifier, List<RecipeIngredient>> ingredientsByRecipeSid() {
        final Map<SemanticIdentifier, List<RecipeIngredient>> map = new HashMap<>();
        streamRecipeIngredient()
            .forEach(recipeIngredient->{
                var list = map.get(recipeIngredient.recipeSid());
                if(list==null) {
                    list = new ArrayList<>();
                    map.put(recipeIngredient.recipeSid(), list);
                }
                list.add(recipeIngredient);
            });
        return map;
    }

    // -- FOOD FACETS

    public Stream<ClassificationFacet> streamFoodFacet() {
        return lookupTableByKey("dita.globodiet.params.food_descript.FoodFacet").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->facetFromRowData(SidUtils.GdContext.FOOD_DESCRIPTOR, row.cellLiterals()));
    }

    public Stream<ClassificationFacet> streamFoodDescriptor() {
        return lookupTableByKey("dita.globodiet.params.food_descript.FoodDescriptor").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->descriptorFromRowData(SidUtils.GdContext.FOOD_DESCRIPTOR, row.cellLiterals()));
    }

    // -- HELPER

    // dita.globodiet.params.food_list.Food
    // dita.globodiet.params.food_descript.FoodDescriptor
    // dita.globodiet.params.food_descript.FoodFacet
    // dita.globodiet.params.food_list.FoodGroup
    // dita.globodiet.params.food_list.FoodSubgroup
    // dita.globodiet.params.recipe_list.Recipe
    // dita.globodiet.params.recipe_list.RecipeIngredient
    // dita.globodiet.params.recipe_description.RecipeDescriptor
    // dita.globodiet.params.recipe_description.RecipeFacet
    // dita.globodiet.params.recipe_list.RecipeGroup
    // dita.globodiet.params.recipe_list.RecipeSubgroup
    private Optional<Table> lookupTableByKey(final String key) {
        return tabularData.dataTables().stream()
                .filter(dataTable->dataTable.key().equals(key))
                .findFirst();
    }

    @Nullable
    private Food foodFromRowData(final List<String> cellLiterals) {
        var isAlias = "ALIAS".equals(cellLiterals.get(1));
        if(isAlias) return null;
        return new Food(
                ObjectId.Context.FOOD.sid(systemId, cellLiterals.get(7)),
                cellLiterals.get(0),
                SidUtils.GdContext.FOOD_GROUP.sid(systemId, FormatUtils.concat(
                        cellLiterals.get(4),
                        cellLiterals.get(5),
                        cellLiterals.get(6))));
    }

    // 0 "name: Recipe name"
    // 1 "recipeType: Type of recipe:|1.1=Open – Known|1.2=Open – Unknown|1.3=Open with brand|2.1=Closed|2.2=Closed with brand|3.0=Commercial|4.1=New – Known|4.2=New – Unknown"
    // 2 "brandNameForCommercialRecipe: Brand name for commercial recipe"
    // 3 "aliasQ: whether is an alias (SH=shadow)"
    // 4 "hasSubRecipeQ: 0=recipe without sub-recipe|1=recipe with sub-recipe"
    // 5 "status: 1=finalized (enabled for interviews)|2=with unknown quantity (disabled for interviews)|3=to be completed (disabled for interviews)|4=empty (disabled for interviews)"
    // 6 "recipeGroupCode: Group code of the recipe classification."
    // 7 "recipeSubgroupCode: Subgroup code of the recipe classification"
    // 8 "code: Recipe ID number"
    @Nullable
    private Recipe recipeFromRowData(final List<String> cellLiterals) {
        var isAlias = "true".equals(cellLiterals.get(3));
        if(isAlias) return null;
        return new Recipe(
                SidUtils.GdContext.RECIPE.sid(systemId, cellLiterals.get(8)),
                cellLiterals.get(0),
                SidUtils.GdContext.RECIPE_GROUP.sid(systemId, FormatUtils.concat(
                        cellLiterals.get(6),
                        cellLiterals.get(7))));
    }

    // 0 "substitutable: 1 = ingredient fixed|2 = ingredient substitutable|3 = fat during cooking|A2 = type of fat used|A3 = type of milk/liquid used"
    // 1 "foodType: Food type (GI or blank)"
    // 2 "descriptionText: Description text (facet/descriptor text)"
    // 3 "name: Ingredient name"
    // 4 "brandName: Ingredient brand (if any)"
    // 5 "describedAndQuantifiedQ: 1 = ingredient described and quantified|2 = otherwise"
    // 6 "finalQuantityInG: Final quantity in g (with coefficient applied)"
    // 7 "estimatedQuantityBeforeCoefficientApplied: Estimated quantity (before coefficient applied)"
    // 8 "quantityEstimatedRawOrCooked: Quantity Estimated Raw or Cooked|1 = Raw|2 = Cooked or Not applicable"
    // 9 "quantityConsumedRawOrCooked: Quantity Consumed Raw or Cooked|1 = Raw|2 = Cooked or Not applicable"
    // 10 "conversionFactorRawToCooked: Conversion factor raw->cooked"
    // 11 "withUnediblePartQ: Quantity as estimated: 1=without un-edible part & 2=with un-edible part"
    // 12 "conversionFactorForEdiblePart: Conversion factor for edible part"
    // 13 "quantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU: Quantity in gram/volume attached to the selected Photo, HHM, STDU"
    // 14 "proportionOfPhotoHHMSTDU: Proportion of Photo, HHM, STDU"
    // 15 "typeOfQuantificationMethod: Type of quantification method"
    // 16 "quantificationMethodCode: Quantification method code"
    // 17 "densityCoefficientOnlyForHHM: Density Coefficient only for HHM"
    // 18 "sequentialNumberForIngredientsWithinARecipe: Sequential Number for Ingredients within a Mixed Recipe"
    // 19 "fatLeftOverPercentage: Fat Left-Over Percentage"
    // 20 "fatLeftOverQ: Fat Left-Over Code (F=False, T=True)"
    // 21 "hhmFraction: HHM Fraction"
    // 22 "consumedQuantityInPound: Consumed quantity in pound"
    // 23 "consumedQuantityInOunce: Consumed quantity in ounce"
    // 24 "consumedQuantityInQuart: Consumed quantity in quart"
    // 25 "consumedQuantityInPint: Consumed quantity in pint"
    // 26 "consumedQuantityInFlounce: Consumed quantity in flounce"
    // 27 "sequentialNumberForIngredientsWithinASubRecipe: Sequential Number for Ingredients within a Sub-Recipe"
    // 28 "rawQuantityWithoutInedible: Raw quantity without inedible (sans dechet)"
    // 29 "percentageOrProportionAsEstimatedForRecipeIngredients: Percentage/Proportion as Estimated for Recipe Ingredients"
    // 30 "percentageOrProportionAsConsumedForRecipeIngredients: Percentage/Proportion as Consumed for Recipe Ingredients"
    // 31 "typeOfItem: 1 = food|2 = recipe"
    // 32 "unitOfSelectedQuantityForMethod: Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)"
    // 33 "percentageRaw: has no description"
    // 34 "recipeCode: Recipe ID number the ingredient belongs to"
    // 35 "foodOrRecipeGroupCode: Ingredient food or recipe group"
    // 36 "foodOrRecipeSubgroupCode: Ingredient food or recipe subgroup"
    // 37 "foodSubSubgroupCode: Ingredient food sub-subgroup"
    // 38 "facetDescriptorsLookupKey: Facets-Descriptors codes used to describe the ingredient;|multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)"
    // 39 "foodOrRecipeCode: Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum"
    private RecipeIngredient recipeIngredientFromRowData(final List<String> cellLiterals) {
        return new RecipeIngredient(
                SidUtils.GdContext.RECIPE.sid(systemId, cellLiterals.get(34)),
                ObjectId.Context.FOOD.sid(systemId, cellLiterals.get(39)),
                new BigDecimal(cellLiterals.get(6))
                );
    }

    // 0 "name: Facet name"
    // 1 "text: Facet text (text to show on the screen describing the facet)"
    // 2 "type: 0=Standard facets with descriptors available in Descface table|1=Facets with descriptors available in Brandnam table|2=Facets with descriptors available in Foods table - facet 15 type of fat|3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used"
    // 3 "typeCardinality: 0 = facet with single-selection of descriptor|1 = facets with multi-selection of descriptors"
    // 4 "group: If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.|Comma is used as delimiter (e.g. 10,050701,050702)"
    // 5 "labelOnHowToAskTheFacetQuestion: Label on how to ask the facet question"
    // 6 "code: Facet code"
    private ClassificationFacet facetFromRowData(
            final SidUtils.GdContext context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId, cellLiterals.get(6)),
                cellLiterals.get(0)
                );
    }

    // 0 "name: Descriptor name"
    // 1 "cooking: 0=default without consequences in the algorithms regarding cooking|1=raw (not cooked)|2=asks the question 'fat used during cooking?'|3=found in austrian data for 'frittiert' - invalid enum constant?"
    // 2 "choice: 0=Multiple choice (allowed)|1=Single (exclusive) choice"
    // 3 "otherQ: 0=Regular choice|1=Choice with additional text as provided by the interviewer (other: [...])"
    // 4 "facetCode: Facet code"
    // 5 "code: Descriptor code"
    private ClassificationFacet descriptorFromRowData(
            final SidUtils.GdContext context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId, FormatUtils.concat(
                        cellLiterals.get(4),
                        cellLiterals.get(5))),
                cellLiterals.get(0)
                );
    }

}

