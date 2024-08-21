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
package dita.foodon.fdm;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.DataSource;

import lombok.experimental.UtilityClass;

import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Table;

/**
 * Provides a set of food ontologies,
 * that allow to describe consumed food,
 * including categorization (grouping) and descriptive facets.
 */
@UtilityClass
public class FoodDescriptionModel {

    public record Food(
            String code,
            String name,
            boolean isAlias,
            String foodGroupCode,
            String foodSubgroupCode,
            String foodSubSubgroupCode) {
    }

    public record Recipe(
            String code,
            String name,
            boolean isAlias,
            String recipeGroupCode,
            String recipeSubgroupCode) {
    }

    public record FoodFacet(
            String code,
            String name) {
    }

    public record FoodDescriptor(
            String code,
            String facetCode,
            String name) {
    }

    public record GlobodietReader(TabularData tabularData) {
        
        // -- FACTORIES

        public static GlobodietReader readFromZippedYaml(final DataSource ds) {
            var yaml = Blob.tryRead("fdm", CommonMimeType.ZIP, ds)
                    .valueAsNonNullElseFail()
                    .unZip(CommonMimeType.YAML)
                    .toClob(StandardCharsets.UTF_8)
                    .asString();

            var tabularData = TabularData.populateFromYaml(yaml, TabularData.Format.defaults());
            return new GlobodietReader(tabularData);
        }
        
        // -- FOOD

        public Stream<FoodDescriptionModel.Food> streamFood() {
            return lookupTableByKey("dita.globodiet.params.food_list.Food").stream()
                .flatMap(dataTable->dataTable.rows().stream())
                .map(row->foodFromRowData(row.cellLiterals()));
        }

        public Map<String, Food> foodByCode() {
            final Map<String, Food> map = new HashMap<>();
            streamFood()
                .forEach(food->map.put(food.code(), food));
            return map;
        }

        // -- RECIPE

        public Stream<Recipe> streamRecipe() {
            return lookupTableByKey("dita.globodiet.params.recipe_list.Recipe").stream()
                .flatMap(dataTable->dataTable.rows().stream())
                .map(row->recipeFromRowData(row.cellLiterals()));
        }

        public Map<String, Recipe> recipeByCode() {
            final Map<String, Recipe> map = new HashMap<>();
            streamRecipe()
                .forEach(recipe->map.put(recipe.code(), recipe));
            return map;
        }

        // -- FOOD FACETS

        public Stream<FoodFacet> streamFoodFacet() {
            return lookupTableByKey("dita.globodiet.params.food_descript.FoodFacet").stream()
                .flatMap(dataTable->dataTable.rows().stream())
                .map(row->facetFromRowData(row.cellLiterals()));
        }

        public Stream<FoodDescriptor> streamFoodDescriptor() {
            return lookupTableByKey("dita.globodiet.params.food_descript.FoodDescriptor").stream()
                .flatMap(dataTable->dataTable.rows().stream())
                .map(row->descriptorFromRowData(row.cellLiterals()));
        }
        
        // -- HELPER
        
        // dita.globodiet.params.food_list.Food
        // dita.globodiet.params.food_descript.FoodDescriptor
        // dita.globodiet.params.food_descript.FoodFacet
        // dita.globodiet.params.food_list.FoodGroup
        // dita.globodiet.params.food_list.FoodSubgroup
        // dita.globodiet.params.recipe_list.Recipe
        // dita.globodiet.params.recipe_description.RecipeDescriptor
        // dita.globodiet.params.recipe_description.RecipeFacet
        // dita.globodiet.params.recipe_list.RecipeGroup
        // dita.globodiet.params.recipe_list.RecipeSubgroup    
        private Optional<Table> lookupTableByKey(final String key) {
            return tabularData.dataTables().stream()
                    .filter(dataTable->dataTable.key().equals(key))
                    .findFirst();
        }
        
        private static Food foodFromRowData(final List<String> cellLiterals) {
            return new Food(
                    cellLiterals.get(7),
                    cellLiterals.get(0),
                    "ALIAS".equals(cellLiterals.get(1)),
                    cellLiterals.get(4),
                    cellLiterals.get(5),
                    cellLiterals.get(6));
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
        private static Recipe recipeFromRowData(final List<String> cellLiterals) {
            return new Recipe(
                    cellLiterals.get(8),
                    cellLiterals.get(0),
                    "true".equals(cellLiterals.get(3)),
                    cellLiterals.get(6),
                    cellLiterals.get(7)
                    );
        }
        
        // 0 "name: Facet name"
        // 1 "text: Facet text (text to show on the screen describing the facet)"
        // 2 "type: 0=Standard facets with descriptors available in Descface table|1=Facets with descriptors available in Brandnam table|2=Facets with descriptors available in Foods table - facet 15 type of fat|3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used"
        // 3 "typeCardinality: 0 = facet with single-selection of descriptor|1 = facets with multi-selection of descriptors"
        // 4 "group: If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.|Comma is used as delimiter (e.g. 10,050701,050702)"
        // 5 "labelOnHowToAskTheFacetQuestion: Label on how to ask the facet question"
        // 6 "code: Facet code"
        private static FoodFacet facetFromRowData(final List<String> cellLiterals) {
            return new FoodFacet(
                    cellLiterals.get(6),
                    cellLiterals.get(0)
                    );
        }
        
        // 0 "name: Descriptor name"
        // 1 "cooking: 0=default without consequences in the algorithms regarding cooking|1=raw (not cooked)|2=asks the question 'fat used during cooking?'|3=found in austrian data for 'frittiert' - invalid enum constant?"
        // 2 "choice: 0=Multiple choice (allowed)|1=Single (exclusive) choice"
        // 3 "otherQ: 0=Regular choice|1=Choice with additional text as provided by the interviewer (other: [...])"
        // 4 "facetCode: Facet code"
        // 5 "code: Descriptor code"
        private static FoodDescriptor descriptorFromRowData(final List<String> cellLiterals) {
            return new FoodDescriptor(
                    cellLiterals.get(5),
                    cellLiterals.get(4),
                    cellLiterals.get(0)
                    );
        }
        
    }

}
