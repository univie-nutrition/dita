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
import java.util.function.UnaryOperator;

import org.apache.causeway.commons.io.FileUtils;

import dita.tooling.domgen.DomainGenerator;
import dita.tooling.domgen.LicenseHeader;
import dita.tooling.orm.OrmModel;
import lombok.SneakyThrows;

public class GdEntityGen {

    @SneakyThrows
    public static void main(final String[] args) {

        if(args.length==0) {
            System.err.println("please provide the destination directory as input parameter - exiting");
            System.exit(1);
        }

        var javaGenerator = JavaGenerator.create(args[0]);
        javaGenerator.purgeDestinationFiles();

        var schemaAssembler = SchemaAssembler.assemble("gd-schema");
        schemaAssembler.writeAssembly("gd-params.schema.yaml");

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

    private record JavaGenerator(File destDir) {

        static JavaGenerator create(final String destDirName) {
            final File destDir = new File(destDirName);
            FileUtils.existingDirectoryElseFail(destDir);
            return new JavaGenerator(destDir);
        }

        void generateDestinationFiles(final UnaryOperator<DomainGenerator.Config.ConfigBuilder> customizer) {
            generateDestinationFiles(customizer.apply(DomainGenerator.Config.builder()).build());
        }

        void generateDestinationFiles(final DomainGenerator.Config config) {
            new DomainGenerator(config)
                .writeToDirectory(destDir);
        }

        @SneakyThrows
        void purgeDestinationFiles() {
            FileUtils.searchFiles(destDir, dir->true, file->true, FileUtils::deleteFile);
        }

    }

    private record SchemaAssembler(File resourceRoot, OrmModel.Schema schema) {

        static SchemaAssembler assemble(final String schemaFilesYamlFolder) {
            final File projRoot = new File("").getAbsoluteFile();
            final File resourceRoot = new File(projRoot, "src/main/resources");
            FileUtils.existingDirectoryElseFail(resourceRoot);

            var schema = OrmModel.Schema.fromYamlFolder(
                    new File(resourceRoot, schemaFilesYamlFolder));

            return new SchemaAssembler(resourceRoot, schema);
        }

        void writeAssembly(final String relativeDestinationSchemaFileName) {
            schema.writeToFileAsYaml(
                    new File(resourceRoot, relativeDestinationSchemaFileName),
                    LicenseHeader.ASF_V2);
        }

    }

}
