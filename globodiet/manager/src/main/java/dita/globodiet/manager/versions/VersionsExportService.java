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

import lombok.val;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.commons.types.TabularData.Table;
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
import dita.globodiet.params.recipe_list.RecipeSubgroup;

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
            RecipeGroup.class,
            RecipeSubgroup.class,
            RecipeFacet.class,
            RecipeDescriptor.class
            );   
    
    @Inject private VersionsService versionsService;
    
    @Inject private TableSerializerYaml tableSerializer;

    @Inject @Qualifier("entity2table") private TabularData.NameTransformer entity2table;
    @Inject @Qualifier("table2entity") private TabularData.NameTransformer table2entity;

    public Blob getFoodDescriptionModelAsYaml(final ParameterDataVersion parameterDataVersion) {
        var yaml = tablesAsYamlFromVersion(parameterDataVersion, 
                table->FDM_ENTITIES.stream()
                    .anyMatch(cls->table.key().endsWith("." + cls.getSimpleName())),
                ExportFormat.ENTITY);
        return Clob.of("fdm", CommonMimeType.YAML, yaml)
                .toBlob(StandardCharsets.UTF_8)
                .zip();
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
     
        val tables = TabularData.populateFromYaml(
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
        val clob = tableSerializer.clobFromRepository("gd-params",
                format==ExportFormat.ENTITY
                    ? TabularData.NameTransformer.IDENTITY
                    : entity2table,
                tableFilter,
                rowSortingEnabled);
        return clob;
    }
    
    // -- UTILITY

    public static Predicate<ObjectSpecification> paramsTableFilter() {
        return entityType->entityType.getLogicalTypeName()
                .startsWith("dita.globodiet.params.");
    }
    
    // -- HELPER
    
    private Clob getTableData(final ParameterDataVersion parameterDataVersion) {
        return versionsService.resolveZippedResource(parameterDataVersion, "gd-params.yml", Optional.empty())
                .unZip(CommonMimeType.YAML)
                .toClob(StandardCharsets.UTF_8);
    }
    
    private static TabularData.Format format() {
        return TabularData.Format.defaults();
    }

}
