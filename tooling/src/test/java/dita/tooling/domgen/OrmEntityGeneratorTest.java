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
package dita.tooling.domgen;

import org.junit.jupiter.api.Test;

import dita.tooling.orm.OrmModel;
import lombok.val;

class OrmEntityGeneratorTest {

    @Test
    void entityGen() {
        val schema = OrmModel.examples().getElseFail(0);

        val config = DomainGenerator.Config.builder()
                .logicalNamespacePrefix("test.logical")
                .packageNamePrefix("test.actual")
                .licenseHeader(LicenseHeader.ASF_V2)
                .schema(schema)
                .entitiesModulePackageName("mod")
                .entitiesModuleClassSimpleName("MyEntitiesModule")
                .build();

        val entityGen = new DomainGenerator(config);

        entityGen.streamAsJavaModels()
            .forEach(sample->{
                System.err.println("---------------------------------------");
                System.err.printf("%s%n", sample.buildJavaFile().toString());
            });
    }
}
