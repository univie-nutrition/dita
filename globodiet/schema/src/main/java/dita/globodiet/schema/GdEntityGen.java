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

import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.FileUtils;

import dita.tooling.domgen.DomainGenerator;
import dita.tooling.domgen.LicenseHeader;
import dita.tooling.orm.OrmModel;
import lombok.val;

public class GdEntityGen {

    public static void main(final String[] args) {

        /* TODO perhaps only specify the project root dir and we then put generated files
         * under <root>/target/generated-sources/cwy (cwy...causeway)
         */
        if(args.length==0) {
            System.err.println("please provide the destination directory as input parameter - exiting");
            System.exit(1);
        }

        final File destDir = new File(args[0]);
        FileUtils.existingDirectoryElseFail(destDir);

        val yaml = DataSource.ofResource(GdEntityGen.class, "/gd-params.schema.yaml")
            .tryReadAsStringUtf8()
            .valueAsNonNullElseFail();

        val schema = OrmModel.Schema.fromYaml(yaml);

        val config = DomainGenerator.Config.builder()
            .logicalNamespacePrefix("dita.globodiet")
            .packageNamePrefix("dita.globodiet.dom")
            .licenseHeader(LicenseHeader.ASF_V2)
            .schema(schema)
            .entitiesModulePackageName("params")
            .entitiesModuleClassSimpleName("DitaModuleGdParams")
            .build();

        new DomainGenerator(config)
            .writeToDirectory(destDir);

        System.out.println("done.");

    }
}