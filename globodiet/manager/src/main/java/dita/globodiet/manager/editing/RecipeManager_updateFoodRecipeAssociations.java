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

import java.io.File;
import java.util.List;

import jakarta.inject.Inject;

import org.apache.wicket.util.file.Files;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.TextUtils;
import org.apache.causeway.core.metamodel.tabular.simple.DataTable;
import org.apache.causeway.extensions.tabular.excel.exporter.CollectionContentsAsExcelExporter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import dita.commons.format.FormatUtils;
import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.recipe_list.Recipe;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="listOfRecipe", position = Position.PANEL)
@RequiredArgsConstructor
public class RecipeManager_updateFoodRecipeAssociations {

    @Inject private RepositoryService repositoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject private CollectionContentsAsExcelExporter excelExporter;

    final Recipe.Manager recipeManager;

    /**
     * WIP - yet used for analysis of existing data
     */
    @MemberSupport @SneakyThrows
    public Blob act() {

        var recipes = Can.ofCollection(repositoryService.allInstances(Recipe.class));
        var foodRecpAssociations = recipes.stream()
            .filter(recipe->Character.isDigit(recipe.getName().charAt(0)))
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
                            :"HIDDEN(2,3,4)",
                        coffeeFoodCodes.contains(associatedFoodCode)
                            ? recipe.getStatus() == Recipe.Status.FINALIZED
                            : recipe.getStatus() != Recipe.Status.FINALIZED
                        );

                return foodRecpAssoc;
        }).toList();

        var dataTable = DataTable.forDomainType(FoodRecipeAssociation.class);
        dataTable.setDataElementPojos(foodRecpAssociations);

        val tempFile = File.createTempFile(this.getClass().getCanonicalName(), "FoodRecipeAssociations");
        try {
            excelExporter.createExport(dataTable, tempFile);
            return Blob.of("FoodRecipeAssociations", CommonMimeType.XLSX, DataSource.ofFile(tempFile).bytes());
        } finally {
            Files.remove(tempFile); // cleanup
        }
    }

    @DomainObject(nature = Nature.VIEW_MODEL)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FoodRecipeAssociation {
        @Property
        private String recipeCode;
        @Property
        private String recipeName;
        @Property
        private String recipeNameWithAttributes;
        @Property
        private String foodCode;
        @Property
        private String foodName;
        @Property
        private String foodNameWithAttributes;
        @Property
        private String recipeCurrentStatus;
        @Property
        private String recipeDesiredStatus;
        @Property
        private boolean recipeCurrentMeetsDesiredStatus;

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
