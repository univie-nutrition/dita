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
import java.util.Set;
import java.util.function.UnaryOperator;

import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.FileUtils;

import dita.commons.types.ResourceFolder;
import dita.tooling.domgen.DomainGenerator;
import dita.tooling.domgen.LicenseHeader;
import dita.tooling.orm.OrmModel;
import dita.tooling.structgen.ObjectGraphRendererStructurizr;
import lombok.SneakyThrows;
import lombok.val;

public class GdEntityGen {

    @SneakyThrows
    public static void main(final String[] args) {

        if(args.length==0) {
            System.err.println("please provide the destination directory as input parameter - exiting");
            System.exit(1);
        }

        var resourceRoot = ResourceFolder.resourceRoot();
        var schemaAssembler = SchemaAssembler.assemble(resourceRoot.relativeFile("gd-schema"));
        schemaAssembler.writeAssembly(
                LicenseHeader.ASF_V2,
                resourceRoot.relativeFile("gd-params.schema.yaml"));

        schemaAssembler.schema()
            .asObjectGraph()
            .transform(new ObjectGraphTransformer())
            .asDiagramDslSource(new ObjectGraphRendererStructurizr())
            .pipe(DataSink.ofFile(resourceRoot.relativeFile("gd-params.structurizr.dsl")));

        var javaGenerator = new JavaGenerator(ResourceFolder.ofFileName(args[0]));
        javaGenerator.destDir().purgeFiles();
        javaGenerator.generateDestinationFiles(cfg->cfg
                .logicalNamespacePrefix("dita.globodiet")
                .packageNamePrefix("dita.globodiet.dom")
                .licenseHeader(LicenseHeader.ASF_V2)
                .schema(schemaAssembler.schema())
                //.datastore("store2") // DN Data Federation
                .entitiesModulePackageName("params")
                .entitiesModuleClassSimpleName("DitaModuleGdParams"));

        System.out.println("done.");
    }

    // -- HELPER

    private record JavaGenerator(ResourceFolder destDir) {
        void generateDestinationFiles(final UnaryOperator<DomainGenerator.Config.ConfigBuilder> customizer) {
            generateDestinationFiles(customizer.apply(DomainGenerator.Config.builder()).build());
        }
        void generateDestinationFiles(final DomainGenerator.Config config) {
            new DomainGenerator(config)
                .writeToDirectory(destDir.root());
        }
    }

    private record SchemaAssembler(OrmModel.Schema schema) {
        static SchemaAssembler assemble(final File yamlFolder) {
            FileUtils.existingDirectoryElseFail(yamlFolder);
            var schema = OrmModel.Schema.fromYamlFolder(yamlFolder);
            return new SchemaAssembler(schema);
        }
        void writeAssembly(final LicenseHeader licenseHeader, final File destinationSchemaFile) {
            schema.writeToFileAsYaml(
                    destinationSchemaFile,
                    licenseHeader);
        }
    }

    private record ObjectGraphTransformer() implements ObjectGraph.Transformer {

        @Override
        public ObjectGraph transform(final ObjectGraph g) {

            val transformed = new ObjectGraph();

            g.objects().stream()
                .map(obj->obj.withId(obj.id())) // copy
                .forEach(transformed.objects()::add);

            val objectById = transformed.objectById();
            val excludedLabels = Set.of(
                    "foodGroupCode",
                    "foodSubgroupCode",
                    "foodSubSubgroupCode");

            g.relations().stream()
                .filter(rel->!excludedLabels.contains(rel.label()))
                .map(rel->rel.withFrom(objectById.get(rel.fromId())).withTo(objectById.get(rel.toId()))) // copy
                .forEach(transformed.relations()::add);

            return transformed;
        }
    }

}
