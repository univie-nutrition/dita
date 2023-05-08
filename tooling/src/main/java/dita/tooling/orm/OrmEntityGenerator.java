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
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import org.springframework.util.ReflectionUtils;

import lombok.NonNull;
import lombok.val;

public record OrmEntityGenerator(@NonNull OrmModel.Schema schema) {

    public record JavaModel(
            @NonNull ClassName name,
            @NonNull TypeSpec typeSpec) {

        public JavaModel(final String packageName, final TypeSpec typeSpec) {
            this(ClassName.get(
                        packageName,
                        (String) ReflectionUtils.getField(ReflectionUtils.findField(typeSpec.getClass(), "name"), typeSpec)
                    ),
                    typeSpec);
        }
        public JavaFile buildJavaFile() {
            return JavaFile.builder(name.packageName(), typeSpec)
                    .build();
        }
    }

    public Stream<JavaModel> streamAsJavaModels(final @NonNull String packageName) {
        return schema.entities().values().stream()
        .map(entityModel->toJavaModel(packageName, entityModel));
    }

    public void writeToDirectory(final @NonNull Stream<JavaModel> modelStream, final @NonNull File dest) {
        OrmEntityWriterUtils.writeToDirectory(modelStream, dest);
    }

    // -- JAVA MODEL GEN

    private JavaModel toJavaModel(final @NonNull String packageName, final OrmModel.Entity entityModel) {
        return new JavaModel(packageName, classModel(entityModel));
    }

    private TypeSpec classModel(final OrmModel.Entity entityModel) {

        val typeModelBuilder = TypeSpec.classBuilder(entityModel.name())
                .addModifiers(Modifier.PUBLIC)
                //.addSuperinterfaces(asClassNames(t.getInterfaces()))
                //.addFields(asFields(t.getFields(), Modifier.PRIVATE))
                //.addMethods(asMethods(t.getFields(), Modifier.PUBLIC))
                ;
        return typeModelBuilder.build();
    }

}
