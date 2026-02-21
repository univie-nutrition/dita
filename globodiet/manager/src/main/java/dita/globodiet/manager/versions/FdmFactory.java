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
package dita.globodiet.manager.versions;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.collections._Streams;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.sid.SidFactory;
import dita.commons.types.Pair;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Table;
import dita.commons.util.FormatUtils;
import dita.foodon.fdm.FdmUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredient;

record FdmFactory(
        SidFactory sidFactory,
        TabularData tabularData) {

    public FdmFactory(
            final SystemId systemId,
            final TabularData tabularData) {
        this(new SidFactory(systemId), tabularData);
    }

    public SystemId systemId() {
        return sidFactory.systemId();
    }

    // -- FACTORIES

    public FoodDescriptionModel createFoodDescriptionModel() {
        return new FoodDescriptionModel(
                FdmUtils.collectFoodBySid(streamFood()),
                FdmUtils.collectRecipeBySid(streamRecipe()),
                FdmUtils.collectIngredientsByRecipeSid(streamRecipeIngredient()),
                FdmUtils.collectClassificationFacetBySid(
                        _Streams.<ClassificationFacet>concat(
                                streamFoodGroups(),
                                streamFoodSubgroups(),
                                streamFoodFacet(),
                                streamFoodDescriptor(),
                                streamRecipeGroups(),
                                streamRecipeSubgroups(),
                                streamRecipeFacet(),
                                streamRecipeDescriptor())
                        ));
    }

    // -- FOOD

    public Stream<FoodDescriptionModel.Food> streamFood() {
        return lookupTableByKey("dita.globodiet.params.food_list.Food").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->foodFromRowData(row.cellLiterals()))
            .filter(Objects::nonNull);
    }

    public Stream<FoodDescriptionModel.ClassificationFacet> streamFoodGroups() {
        return lookupTableByKey("dita.globodiet.params.food_list.FoodGroup").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->foodGroupFromRowData(ObjectId.Context.FOOD_GROUP, row.cellLiterals()))
            .filter(Objects::nonNull);
    }
    public Stream<FoodDescriptionModel.ClassificationFacet> streamFoodSubgroups() {
        return lookupTableByKey("dita.globodiet.params.food_list.FoodSubgroup").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->foodSubgroupFromRowData(ObjectId.Context.FOOD_GROUP, row.cellLiterals()))
            .filter(Objects::nonNull);
    }

    // -- RECIPE

    public Stream<Recipe> streamRecipe() {
        return lookupTableByKey("dita.globodiet.params.recipe_list.Recipe").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeFromRowData(row.cellLiterals()));
    }

    public Stream<FoodDescriptionModel.ClassificationFacet> streamRecipeGroups() {
        return lookupTableByKey("dita.globodiet.params.recipe_list.RecipeGroup").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeGroupFromRowData(ObjectId.Context.RECIPE_GROUP, row.cellLiterals()))
            .filter(Objects::nonNull);
    }
    public Stream<FoodDescriptionModel.ClassificationFacet> streamRecipeSubgroups() {
        return lookupTableByKey("dita.globodiet.params.recipe_list.RecipeSubgroup").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeSubgroupFromRowData(ObjectId.Context.RECIPE_GROUP, row.cellLiterals()))
            .filter(Objects::nonNull);
    }

    // -- RECIPE INGREDIENT

    public Stream<RecipeIngredient> streamRecipeIngredient() {
        return lookupTableByKey("dita.globodiet.params.recipe_list.RecipeIngredient").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeIngredientFromRowData(row.cellLiterals()));
    }

    // -- FOOD FACETS

    public Stream<ClassificationFacet> streamFoodFacet() {
        return lookupTableByKey("dita.globodiet.params.food_descript.FoodFacet").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->foodFacetFromRowData(ObjectId.Context.FOOD_DESCRIPTOR, row.cellLiterals()));
    }

    public Stream<ClassificationFacet> streamFoodDescriptor() {
        return lookupTableByKey("dita.globodiet.params.food_descript.FoodDescriptor").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->foodDescriptorFromRowData(ObjectId.Context.FOOD_DESCRIPTOR, row.cellLiterals()));
    }

    // -- RECIPE FACETS

    public Stream<ClassificationFacet> streamRecipeFacet() {
        return lookupTableByKey("dita.globodiet.params.recipe_description.RecipeFacet").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeFacetFromRowData(ObjectId.Context.RECIPE_DESCRIPTOR, row.cellLiterals()));
    }

    public Stream<ClassificationFacet> streamRecipeDescriptor() {
        return lookupTableByKey("dita.globodiet.params.recipe_description.RecipeDescriptor").stream()
            .flatMap(dataTable->dataTable.rows().stream())
            .map(row->recipeDescriptorFromRowData(ObjectId.Context.RECIPE_DESCRIPTOR, row.cellLiterals()));
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

    // dita.globodiet.params.food_list.Food == FOODS
    // 0 -7 "FOODNUM: Identification Code for Food, Product, On-the-fly Recipe or Alias"
    // 1 -4 "GROUP: Food Group code"
    // 2 -5 "SUBGROUP1: Food Subgroup code"
    // 3 -6 "SUBGROUP2: Food Sub(sub)group code"
    // 4 -0 "NAME: Native (localized) name of this Food, Product, On-the-fly Recipe or Alias"
    // 5 -1 "TYPE: Type of item:|(none) -> Normal Food Item|GI -> Generic Food Item|SH -> Shadow Item|CR -> Composed Recipe (On-the-fly Recipe)|Definition: its different ingredients can be identified and|quantified separately after preparation|(e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)|or just before mixing (e.g. coffee with milk).|Composed recipes are built during the interview: there is no a priori list of composed recipes.|They are made from items listed below/linked to a quick list item.|Example: Salad|- Lettuce|- Tomato|- Cucumber|- Salad dressing (can be a recipe in some projects where all sauces are in recipes)"
    // 6 -2 "ORDER: Auxiliary field to force an internal order within each subgroup|(if GI then 1 otherwise 2, this forces the GI at the top)"
    // 7 -3 "SUPPL: 0=food|1=dietary supplement"
    @Nullable
    private Food foodFromRowData(final List<String> cellLiterals) {
        var isAlias = "ALIAS".equals(cellLiterals.get(5));
        if(isAlias) return null;
        var nameWithAttributes = cellLiterals.get(4);
        return new Food(
                sidFactory().food(cellLiterals.get(0)),
                nameNoBraces(nameWithAttributes),
                sidFactory.foodGroup(FormatUtils.concat(
                        cellLiterals.get(1),
                        cellLiterals.get(2),
                        cellLiterals.get(3))),
                attributesFromName(systemId(), nameWithAttributes));
    }

    // dita.globodiet.params.recipe_list.Recipe == MIXEDREC
    // 0 "R_IDNUM: Recipe ID number"
    // 1 "R_GROUP: Group code of the recipe classification. (for alias entries: not set, but perhaps could be)"
    // 2 "R_SUBGROUP: Subgroup code of the recipe classification"
    // 3 "R_NAME: Recipe name"
    // 4 "R_TYPE: Type of recipe:|1.1=Open – Known|1.2=Open – Unknown|1.3=Open with brand|2.1=Closed|2.2=Closed with brand|3.0=Commercial|4.1=New – Known|4.2=New – Unknown"
    // 5 "R_BRAND: Brand name for commercial recipe"
    // 6 "TYPE: whether is an alias (SH=shadow)"
    // 7 "R_SUB: 0=recipe without sub-recipe|1=recipe with sub-recipe"
    // 8 "STATUS: 1=finalized (enabled for interviews)|2=with unknown quantity (disabled for interviews)|3=to be completed (disabled for interviews)|4=empty (disabled for interviews)"
    @Nullable
    private Recipe recipeFromRowData(final List<String> cellLiterals) {
        var isAlias = "true".equals(cellLiterals.get(6));
        if(isAlias) return null;
        var nameWithAttributes = cellLiterals.get(3);

        return new Recipe(
                sidFactory().recipe(cellLiterals.get(0)),
                nameNoBraces(nameWithAttributes),
                sidFactory().recipeGroup(FormatUtils.concat(
                        cellLiterals.get(1),
                        cellLiterals.get(2))),
                    attributesFromName(systemId(), nameWithAttributes));
    }

    /// Food or Recipe name may be suffixed with a curly-braced attribute list
    /// @returns the trimmed name with any curly-braced attribute list removed
    private String nameNoBraces(final String nameWithAttributes) {
        return TextUtils.cutter(nameWithAttributes).keepBefore("{").getValue().trim();
    }

    /// looks for key/value pairs in literal `.. {.., key=value,..}`
    /// replaces simple associations with their fully qualified SID
    private Map<String, String> attributesFromName(final SystemId systemId, final String curlybracedAttributeList) {
        final String commaSeparatedKeyValuePairs = TextUtils.cutter(curlybracedAttributeList)
            .keepAfter("{")
            .keepBeforeLast("}")
            .getValue();
        var kvPairs = Pair.parseKeyAndValuePairs(commaSeparatedKeyValuePairs);
        var map = Pair.toMap(kvPairs, LinkedHashMap::new);
        map.computeIfPresent("assocFood", (_, code)->ObjectId.Context.FOOD.sid(
            systemId,
            FormatUtils.fillWithLeadingZeros(5, code)).toStringNoBox());
        map.computeIfPresent("assocRecp", (_, code)->ObjectId.Context.RECIPE.sid(
            systemId,
            FormatUtils.fillWithLeadingZeros(5, code)).toStringNoBox());
        return Collections.unmodifiableMap(map);
    }

    // dita.globodiet.params.recipe_list.RecipeIngredient = MIXEDING
    // 0  -34 "R_IDNUM: Recipe ID number the ingredient belongs to"
    // 1  - "GROUP: Ingredient food or recipe group"
    // 2  - "SUBGROUP1: Ingredient food or recipe subgroup"
    // 3  - "SUBGROUP2: Ingredient food sub-subgroup"
    // 4  -38 "FACETS_STR: Facets-Descriptors codes used to describe the ingredient;|multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)"
    // 5  -39 "IDNUM: Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum"
    // 6  - "ING_TYPE: 1 = ingredient fixed|2 = ingredient substitutable|3 = fat during cooking|A2 = type of fat used|A3 = type of milk/liquid used"
    // 7  - "TYPE: Food type (GI or blank)"
    // 8  - "TEXT: Description text (facet/descriptor text)"
    // 9  - "NAME: Ingredient name"
    // 10 - "BRANDNAME: Ingredient brand (if any)"
    // 11 - "STATUS: 1 = ingredient described and quantified|2 = otherwise"
    // 12 -6 "CONS_QTY: Final quantity in g (with coefficient applied)"
    // 13 - "ESTIM_QTY: Estimated quantity (before coefficient applied)"
    // 14 - "RAWCOOKED: Quantity Estimated Raw or Cooked|1 = Raw|2 = Cooked or Not applicable"
    // 15 - "CONSRAWCO: Quantity Consumed Raw or Cooked|1 = Raw|2 = Cooked or Not applicable"
    // 16 - "CONVER: Conversion factor raw->cooked"
    // 17 - "EDIB: Quantity as estimated: 1=without un-edible part & 2=with un-edible part"
    // 18 - "EDIB_CSTE: Conversion factor for edible part"
    // 19 - "NGRAMS: Quantity in gram/volume attached to the selected Photo, HHM, STDU"
    // 20 - "PROPORT: Proportion of Photo, HHM, STDU"
    // 21 - "Q_METHOD: Type of quantification method"
    // 22 - "QM_CODE: Quantification method code"
    // 23 - "DENSITY: Density Coefficient only for HHM"
    // 24 - "ING_NUM: Sequential Number for Ingredients within a Mixed Recipe"
    // 25 - "FATL_PCT: Fat Left-Over Percentage"
    // 26 - "FATLEFTO: Fat Left-Over Code (F=False, T=True)"
    // 27 - "HHMFRACT: HHM Fraction"
    // 28 - "POUND: Consumed quantity in pound"
    // 29 - "OUNCE: Consumed quantity in ounce"
    // 30 - "QUART: Consumed quantity in quart"
    // 31 - "PINT: Consumed quantity in pint"
    // 32 - "FLOUNCE: Consumed quantity in flounce"
    // 33 - "S_ING_NUM: Sequential Number for Ingredients within a Sub-Recipe"
    // 34 - "RAW_Q: Raw quantity without inedible (sans dechet)"
    // 35 - "PCT_ESTIM: Percentage/Proportion as Estimated for Recipe Ingredients"
    // 36 - "PCT_CONS: Percentage/Proportion as Consumed for Recipe Ingredients"
    // 37 - "TYPE_IT: 1 = food|2 = recipe"
    // 38 - "Q_UNIT: Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)"
    // 39 -33 "PCT_RAW: has no description"
    private RecipeIngredient recipeIngredientFromRowData(final List<String> cellLiterals) {
        return new RecipeIngredient(
                sidFactory().recipe(cellLiterals.get(0)),
                sidFactory().food(cellLiterals.get(5)),
                SemanticIdentifierSet.ofStream(_Strings.splitThenStream(cellLiterals.get(4), ",")
                        .map(sidFactory()::foodDescriptor)),
                new BigDecimal(cellLiterals.get(12)),
                bigdecElseDefault(cellLiterals.get(39), null)
                );
    }

    // dita.globodiet.params.food_descript.FoodFacet = FACETS
    // 0 -6 "FACET_CODE: Facet code"
    // 1 -0 "FACET_NAME: Facet name"
    // 2 - "FACET_TEXT: Facet text (text to show on the screen describing the facet)"
    // 3 - "FACET_TYPE: 0=Standard facets with descriptors available in Descface table|1=Facets with descriptors available in Brandnam table|2=Facets with descriptors available in Foods table - facet 15 type of fat|3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used"
    // 4 - "FACET_TYPE_S: 0 = facet with single-selection of descriptor|1 = facets with multi-selection of descriptors"
    // 5 - "FACET_GRP: If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.|Comma is used as delimiter (e.g. 10,050701,050702)"
    // 6 - "FACET_QUEST: Label on how to ask the facet question"
    private ClassificationFacet foodFacetFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), cellLiterals.get(0)),
                cellLiterals.get(1)
                );
    }

    // dita.globodiet.params.recipe_description.RecipeDescriptor == R_DESCFACE
    // 0 -4 "RFACET_CODE: Facet code for recipes"
    // 1 -5 "RDESCR_CODE: Descriptor code for recipes"
    // 2 -0 "RDESCR_NAME: Descriptor name"
    // 3 - "RDESCR_TYPE: Only for facet recipe production:|0=not homemade descriptor|1=Homemade descriptor"
    // 4 - "RDESCR_KNOWN: Only for facet known/unknown: 1=unknown 2=known"
    // 5 - "RDESCR_OTHER: Descriptor with type='other' : 1=yes 0=no"
    // 6 - "RDESCR_SINGLE: 0=not single descriptor|1=single descriptor"
    private ClassificationFacet foodDescriptorFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), FormatUtils.concat(
                        cellLiterals.get(0),
                        cellLiterals.get(1))),
                cellLiterals.get(2)
                );
    }

    // dita.globodiet.params.recipe_description.RecipeFacet == R_FACET
    // 0 -6 "RFACET_CODE: Facet code for recipes"
    // 1 -0 "RFACET_NAME: Facet name"
    // 2 - "RFACET_TEXT: Facet text (text to show on the screen describing the facet)"
    // 3 - "RFACET_TYPE: 0=Standard facets with descriptors available in R_Descface table|1=Facets with descriptors available in RBrand table"
    // 4 - "RFACET_TYPE_S: 0 = facet with single-selection of descriptor|1 = facets with multi-selection of descriptors"
    // 5 - "RFACET_MAIN: 0 = standard facet|1 = Main facet (with non modified descriptor)"
    // 6 - "RFACET_QUEST: Label on how to ask the facet question"
    private ClassificationFacet recipeFacetFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), cellLiterals.get(0)),
                cellLiterals.get(1)
                );
    }

    // dita.globodiet.params.recipe_description.RecipeDescriptor == R_DESCFACE
    // 0 -5 "RFACET_CODE: Facet code for recipes"
    // 1 -6 "RDESCR_CODE: Descriptor code for recipes"
    // 2 -0 "RDESCR_NAME: Descriptor name"
    // 3 - "RDESCR_TYPE: Only for facet recipe production:|0=not homemade descriptor|1=Homemade descriptor"
    // 4 - "RDESCR_KNOWN: Only for facet known/unknown: 1=unknown 2=known"
    // 5 - "RDESCR_OTHER: Descriptor with type='other' : 1=yes 0=no"
    // 6 - "RDESCR_SINGLE: 0=not single descriptor|1=single descriptor"
    private ClassificationFacet recipeDescriptorFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), FormatUtils.concat(
                        cellLiterals.get(0),
                        cellLiterals.get(1))),
                cellLiterals.get(2)
                );
    }

    // dita.globodiet.params.food_list.FoodGroup == GROUPS
    // 0 -2 "GROUP: Food group code"
    // 1 -0 "NAME: Food group name"
    // 2 - "NAME_SHORT: Food group short name"
    private ClassificationFacet foodGroupFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), cellLiterals.get(0)),
                cellLiterals.get(1)
                );
    }

    // dita.globodiet.params.food_list.FoodSubgroup == SUBGROUP
    // 0 -5 "GROUP: Food group code"
    // 1 -6 "SUBGROUP1: Food sub-group code"
    // 2 -7 "SUBGROUP2: Food sub-sub-group code"
    // 3 -0 "NAME: Name of the food (sub-)(sub-)group"
    // 4 - "SGRP_FSS: 0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup"
    // 5 - "SGRP_FLO: 0=non fat/sauce subgroup|1= fat/sauce subgroup that can be left over in the dish"
    // 6 - "SGRP_FDC: 0=non fat during cooking subgroup|1= fat during cooking subgroup"
    // 7 - "NAME_SHORT: Short Name of the food (sub-)(sub-)group"
    private ClassificationFacet foodSubgroupFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), FormatUtils.concat(
                        cellLiterals.get(0),
                        cellLiterals.get(1),
                        cellLiterals.get(2))),
                cellLiterals.get(3)
                );
    }

    // dita.globodiet.params.recipe_list.RecipeGroup == RGROUPS
    // 0 -2 "GROUP: Recipe Group code"
    // 1 -0 "NAME: Name of the Recipe group"
    // 2 - "NAMEG_SHORT: Short Name of the Recipe group"
    private ClassificationFacet recipeGroupFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), cellLiterals.get(0)),
                cellLiterals.get(1)
                );
    }

    // dita.globodiet.params.recipe_list.RecipeSubgroup == RSUBGR
    // 0 -2 "GROUP: Recipe group code"
    // 1 -3 "SUBGROUP: Recipe sub-group code"
    // 2 -0 "NAME: Name of the recipe (sub-)group"
    // 3 - "NAMES_SHORT: Short Name of the recipe (sub-)group"
    private ClassificationFacet recipeSubgroupFromRowData(
            final ObjectId.Context context,
            final List<String> cellLiterals) {
        return new ClassificationFacet(
                context.sid(systemId(), FormatUtils.concat(
                        cellLiterals.get(0),
                        cellLiterals.get(1))),
                cellLiterals.get(2)
                );
    }

    private static BigDecimal bigdecElseDefault(@Nullable final String value, final BigDecimal fallback) {
        return StringUtils.hasLength(value)
            ? new BigDecimal(value)
            : fallback;
    }

}

