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

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.FieldSpec;
import org.springframework.javapoet.TypeSpec;

import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenEntity {

    JavaModel toJavaModel(
            final OrmModel.Entity entityModel,
            final DomainGenerator.Config config) {

        val logicalNameSpace = config.fullLogicalName(entityModel.namespace());
        val packageName = config.fullPackageName(entityModel.namespace());
        return new JavaModel(
                logicalNameSpace,
                packageName,
                classModel(logicalNameSpace, entityModel), config.licenseHeader());
    }

    // -- HELPER

    private TypeSpec classModel(final String logicalNamespace, final OrmModel.Entity entityModel) {

        val typeModelBuilder = TypeSpec.classBuilder(entityModel.name())
                .addJavadoc(entityModel.formatDescription("<br>\n"))
                .addAnnotation(_Annotations.named(logicalNamespace + "." + entityModel.name()))
                .addAnnotation(_Annotations.domainObject())
                .addAnnotation(_Annotations.domainObjectLayout(entityModel.formatDescription("\n")))
                .addAnnotation(_Annotations.persistenceCapable(entityModel.table()))
                .addAnnotation(_Annotations.datastoreIdentity())
                .addModifiers(Modifier.PUBLIC)
                //.addSuperinterfaces(asClassNames(t.getInterfaces()))
                .addFields(asFields(entityModel.fields(), Modifier.PRIVATE))
                //.addMethods(asMethods(t.getFields(), Modifier.PUBLIC))
                ;
        return typeModelBuilder.build();
    }

    private Iterable<FieldSpec> asFields(
            final List<OrmModel.Field> fields,
            final Modifier ... modifiers) {
        return fields.stream()
                .map(field->
                        FieldSpec.builder(field.asJavaType(), field.name(), modifiers)
                            .addJavadoc(field.formatDescription("<br>\n"))
                            .addAnnotation(_Annotations.property())
                            .addAnnotation(_Annotations.propertyLayout(field.formatDescription("\n")))
                            .addAnnotation(_Annotations.column(field.column(), !field.required(), field.maxLength()))
                            .addAnnotation(_Annotations.getter())
                            .addAnnotation(_Annotations.setter())
                            .build())
                .collect(Collectors.toList());
    }

}
