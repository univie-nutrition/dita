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
package dita.tooling.orm;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import jakarta.inject.Named;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import org.springframework.util.ReflectionUtils;

import org.apache.causeway.applib.annotation.DomainObject;

import lombok.NonNull;
import lombok.val;

public record OrmEntityGenerator(@NonNull OrmModel.Schema schema) {

    public record JavaModel(
            @NonNull String logicalTypeName,
            @NonNull ClassName className,
            @NonNull TypeSpec typeSpec) {

        public JavaModel(
                final String logicalName,
                final String packageName,
                final TypeSpec typeSpec) {
            this(
                    logicalName,
                    ClassName.get(
                        packageName,
                        (String) ReflectionUtils.getField(ReflectionUtils.findField(typeSpec.getClass(), "name"), typeSpec)
                    ),
                    typeSpec);
        }
        public JavaFile buildJavaFile() {
            return JavaFile.builder(className().packageName(), typeSpec)
                    .build();
        }
    }

    public Stream<JavaModel> streamAsJavaModels(
            final @NonNull String logicalNameSpacePrefix,
            final @NonNull String packagePrefix) {
        return schema.entities().values().stream()
        .map(entityModel->toJavaModel(logicalNameSpacePrefix, packagePrefix, entityModel));
    }

    public void writeToDirectory(final @NonNull Stream<JavaModel> modelStream, final @NonNull File dest) {
        OrmEntityWriterUtils.writeToDirectory(modelStream, dest);
    }

    // -- JAVA MODEL GEN

    private JavaModel toJavaModel(
            final String logicalNameSpacePrefix, final String packagePrefix, final OrmModel.Entity entityModel) {

        String logicalNameSpace = logicalNameSpacePrefix + entityModel.namespace();
        String packageName = packagePrefix + entityModel.namespace();
        return new JavaModel(logicalNameSpace, packageName, classModel(logicalNameSpace, entityModel));
    }

    private TypeSpec classModel(final String logicalNameSpace, final OrmModel.Entity entityModel) {

        val typeModelBuilder = TypeSpec.classBuilder(entityModel.name())
                .addAnnotation(annotNamed(logicalNameSpace + "." + entityModel.name()))
                .addAnnotation(annotDomainObject())
                .addAnnotation(annotPersistenceCapable())
                .addAnnotation(annotDatastoreIdentity())
                .addModifiers(Modifier.PUBLIC)
                //.addSuperinterfaces(asClassNames(t.getInterfaces()))
                .addFields(asFields(entityModel.fields(), Modifier.PRIVATE))
                //.addMethods(asMethods(t.getFields(), Modifier.PUBLIC))
                ;
        return typeModelBuilder.build();
    }

    private AnnotationSpec annotNamed(final String logicalTypeName) {
        return AnnotationSpec.builder(Named.class)
                .addMember("value", "$1L", logicalTypeName)
                .build();
    }
    private AnnotationSpec annotDomainObject() {
        return AnnotationSpec.builder(DomainObject.class)
                .build();
    }
    private AnnotationSpec annotPersistenceCapable() {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "PersistenceCapable"))
                .build();
    }
    private AnnotationSpec annotDatastoreIdentity() {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "DatastoreIdentity"))
                .addMember("strategy", "$1L", "javax.jdo.annotations.IdGeneratorStrategy.IDENTITY")
                .addMember("column", "$1L", "id")
                .build();
    }

    private Iterable<FieldSpec> asFields(
            final List<OrmModel.Field> fields,
            final Modifier ... modifiers) {
        return fields.stream()
                .map(field->
                        FieldSpec.builder(field.asJavaType(), field.name(), modifiers)
                            .build())
                .collect(Collectors.toList());
    }

}
