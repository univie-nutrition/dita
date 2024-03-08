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
package dita.globodiet.schema;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.FileUtils;

import lombok.SneakyThrows;

import dita.commons.types.ResourceFolder;
import dita.commons.util.ObjectGraphTransformers;
import dita.tooling.domgen.DomainGenerator;
import dita.tooling.domgen.LicenseHeader;
import dita.tooling.orm.OrmModel;
import dita.tooling.structgen.ObjectGraphRendererStructurizr;

public class GdEntityGen {

    @SneakyThrows
    public static void main(final String[] args) {

        var resourceRoot = ResourceFolder.resourceRoot();
        var optionsModel = OptionsModel.parse(args);

        optionsModel.resourceFolderByName()
        .forEach((name, javaDestinationFolder) -> {

            var schemaAssembler = SchemaAssembler.assemble(resourceRoot.relativeFile(name));
            schemaAssembler.writeAssembly(
                    resourceRoot.relativeFile("gd-%s.schema.yaml", name));
            schemaAssembler.writeDiagram(
                    resourceRoot.relativeFile("gd-%s.structurizr.dsl", name));

            schemaAssembler.writeJavaFiles(cfg->cfg
                    .destinationFolder(javaDestinationFolder)
                    .logicalNamespacePrefix("dita.globodiet")
                    .packageNamePrefix("dita.globodiet.dom")
                    .onPurgeKeep(file->file.getName().endsWith(".layout.xml"))
                    .entitiesModulePackageName(name)
                    .entitiesModuleClassSimpleName("DitaModuleGd" + _Strings.capitalize(name)));
        });

        System.out.println("done.");
    }

    // -- HELPER

    private record OptionsModel(Map<String, ResourceFolder> resourceFolderByName) {
        static OptionsModel parse(final String[] args) {
            if(args.length==0) {
                System.err.println("""
                        please provide the destination directories as input parameters like e.g.
                        params=/path/to/params/src/main/java
                        survey=/path/to/survey/src/main/java
                        """);
                System.exit(1);
            }
            var map = new HashMap<String, ResourceFolder>();
            Can.ofArray(args).stream()
                .map(kv->_Strings.parseKeyValuePair(kv, '=').orElseThrow())
                .forEach(kvp->map.put(kvp.getKey(), ResourceFolder.ofFileName(kvp.getValue()) ));
            return new OptionsModel(map);
        }
    }

    private record SchemaAssembler(LicenseHeader licenseHeader, OrmModel.Schema schema) {
        static SchemaAssembler assemble(final File yamlFolder) {
            FileUtils.existingDirectoryElseFail(yamlFolder);
            var schema = OrmModel.Schema.fromYamlFolder(yamlFolder);
            return new SchemaAssembler(LicenseHeader.ASF_V2, schema);
        }
        void writeAssembly(final File destinationSchemaFile) {
            schema.writeToFileAsYaml(
                    destinationSchemaFile,
                    licenseHeader);
        }
        void writeDiagram(final File destinationDiagramFile) {
            schema
                .asObjectGraph()
                .transform(ObjectGraphTransformers.virtualObjectAdder())
                .transform(ObjectGraphTransformers.relationNameNormalizer())

                //.transform(ObjectGraphTransformers.packageNameNormalizer())

                .transform(ObjectGraphTransformers.unrelatedObjectRemover())
                //.transform(ObjectGraphTransformers.virtualRelationRemover())
                .transform(ObjectGraph.Transformers.relationMerger())
                .asDiagramDslSource(new ObjectGraphRendererStructurizr())
                .pipe(DataSink.ofFile(destinationDiagramFile));
        }
        void writeJavaFiles(
                final UnaryOperator<DomainGenerator.Config.ConfigBuilder> customizer) {
            var config = customizer.apply(DomainGenerator.Config.builder()
                    //.datastore("store2") // DN Data Federation
                    .schema(schema))
                    .licenseHeader(licenseHeader)
                    .build();
            config.destinationFolder().purgeFiles(config.onPurgeKeep());
            new DomainGenerator(config)
                .writeToDirectory(config.destinationFolder().root());
        }
    }

}
