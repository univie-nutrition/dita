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

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Predicate;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Table;
import dita.foodon.fdm.FdmUtils;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.params.food_descript.FoodDescriptor;
import dita.globodiet.params.food_descript.FoodFacet;
import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.food_list.FoodGroup;
import dita.globodiet.params.food_list.FoodSubgroup;
import dita.globodiet.params.recipe_description.RecipeDescriptor;
import dita.globodiet.params.recipe_description.RecipeFacet;
import dita.globodiet.params.recipe_list.Recipe;
import dita.globodiet.params.recipe_list.RecipeGroup;
import dita.globodiet.params.recipe_list.RecipeIngredient;
import dita.globodiet.params.recipe_list.RecipeSubgroup;
import dita.globodiet.params.setting.FoodConsumptionOccasion;
import dita.globodiet.params.setting.PlaceOfConsumption;
import dita.globodiet.params.setting.SpecialDayPredefinedAnswer;
import dita.globodiet.params.setting.SpecialDietPredefinedAnswer;
import dita.globodiet.survey.util.SidUtils;

@Service
@Named(DitaModuleGdManager.NAMESPACE + ".VersionsExportService")
public class VersionsExportService {

    public enum ExportFormat {
        TABLE,
        ENTITY
    }

    private static Can<Class<?>> FDM_ENTITIES = Can.of(
            Food.class,
            FoodGroup.class,
            FoodSubgroup.class,
            FoodFacet.class,
            FoodDescriptor.class,

            Recipe.class,
            RecipeIngredient.class,
            RecipeGroup.class,
            RecipeSubgroup.class,
            RecipeFacet.class,
            RecipeDescriptor.class
            );

    private static Can<Class<?>> QMAP_ENTITIES = Can.of(
            FoodConsumptionOccasion.class,
            PlaceOfConsumption.class,
            SpecialDayPredefinedAnswer.class,
            SpecialDietPredefinedAnswer.class
            );


    @Inject private VersionsService versionsService;
    @Inject private TableSerializerYaml tableSerializer;

    @Inject @Qualifier("entity2table") private TabularData.NameTransformer entity2table;
    @Inject @Qualifier("table2entity") private TabularData.NameTransformer table2entity;

    public Blob getFoodDescriptionModelAsYaml(final ParameterDataVersion parameterDataVersion) {
        var yamlTabular = tablesAsYamlFromVersion(parameterDataVersion,
                table->FDM_ENTITIES.stream()
                    .anyMatch(cls->table.key().endsWith("." + cls.getSimpleName())),
                ExportFormat.ENTITY);

        var tabularData = TabularData.populateFromYaml(yamlTabular, TabularData.Format.defaults());
        var fdmFactory = new FdmFactory(SystemId.parse(parameterDataVersion.getSystemId()), tabularData);
        var yaml = FdmUtils.toYaml(fdmFactory.createFoodDescriptionModel());

        return Clob.of("fdm", CommonMimeType.YAML, yaml)
                .toBlobUtf8()
                .zip();
    }

    public Blob getSpecialDayAndOthers(final ParameterDataVersion parameterDataVersion) {
        var yamlTabular = tablesAsYamlFromVersion(parameterDataVersion,
                table->QMAP_ENTITIES.stream()
                    .anyMatch(cls->table.key().endsWith("." + cls.getSimpleName())),
                ExportFormat.ENTITY);

        var tabularData = TabularData.populateFromYaml(yamlTabular, TabularData.Format.defaults());

        var qMapFactory = new QMapFactory(SystemId.parse(parameterDataVersion.getSystemId()), SidUtils.languageQualifier("de"), tabularData);
        var entryBuilder = qMapFactory.createZipOfYamls();

        return Blob.of("qmaps-export", CommonMimeType.ZIP, entryBuilder.toBytes());
    }

    /**
     * Returns a gd-params YAML from (primary) data-base.
     * @param tableFilter
     */
    public String tablesAsYamlFromVersion(
            final ParameterDataVersion parameterDataVersion,
            final Predicate<? super Table> tableFilter,
            final ExportFormat format) {
        var tableDataClob = getTableData(parameterDataVersion);
        var nameTransformer = format==ExportFormat.TABLE
                ? TabularData.NameTransformer.IDENTITY
                : table2entity;

        var tables = TabularData.populateFromYaml(
                tableDataClob.asString(),
                format())
            .transform(nameTransformer)
            .dataTables()
            .filter(tableFilter);

        return new TabularData(tables)
                .toYaml(format().withRowSorting(true));
    }

    /**
     * Returns a gd-params YAML from (primary) data-base.
     * @param tableFilter
     */
    public Clob tablesAsYamlFromRepository(
            final Predicate<ObjectSpecification> tableFilter,
            final ExportFormat format,
            final boolean rowSortingEnabled) {
        var clob = tableSerializer.clobFromRepository("gd-params",
                format==ExportFormat.ENTITY
                    ? TabularData.NameTransformer.IDENTITY
                    : entity2table,
                tableFilter,
                rowSortingEnabled);
        return clob;
    }

    // -- UTILITY

    public static Predicate<ObjectSpecification> paramsTableFilter() {
        return entityType->entityType.logicalTypeName()
                .startsWith("dita.globodiet.params.");
    }

    // -- HELPER

    private Clob getTableData(final ParameterDataVersion parameterDataVersion) {
        return versionsService.resolveZippedResource(parameterDataVersion, "gd-params.yaml", Optional.empty())
                .unZip(CommonMimeType.YAML)
                .toClob(StandardCharsets.UTF_8);
    }

    private static TabularData.Format format() {
        return TabularData.Format.defaults();
    }

}
