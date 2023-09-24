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

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.TypeSpec;

import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel.Entity;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenInterface {

    JavaModel toJavaModel(
            final Entity superTypeHolder,
            final DomainGenerator.Config config) {

        val logicalNameSpace = config.fullLogicalName(superTypeHolder.superTypeNamespace());
        val packageName = config.fullPackageName(superTypeHolder.superTypeNamespace());
        val superTypeName = ClassName.get(packageName, superTypeHolder.superTypeSimpleName());

        return new JavaModel(
                logicalNameSpace,
                packageName,
                TypeSpec.interfaceBuilder(superTypeName)
                    .addModifiers(Modifier.PUBLIC).build(),
                config.licenseHeader());
    }

}
