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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.commons.internal.collections._Multimaps;
import org.apache.causeway.commons.internal.collections._Multimaps.ListMultimap;

import dita.tooling.domgen.DomainGenerator.JavaFileModel;
import dita.tooling.domgen.DomainGenerator.QualifiedType;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenModule {

    public QualifiedType qualifiedType(
            final @NonNull DomainGenerator.Config config,
            final @NonNull List<JavaFileModel> entities,
            final @NonNull List<JavaFileModel> mixins) {

        val packageName = config.fullPackageName(config.entitiesModulePackageName());

        final ListMultimap<String, ClassName> importsByCategory = _Multimaps
                .newListMultimap(LinkedHashMap<String, List<ClassName>>::new, ArrayList::new);

        importsByCategory.put("Menu Entries", List.of(
                ClassName.get(packageName, "EntitiesMenu")));

        importsByCategory.put("Entities", entities.stream()
                .map(JavaFileModel::className)
                .toList());

        importsByCategory.put("Mixins", mixins.stream()
                .map(JavaFileModel::className)
                .toList());

        final ClassName nameOfClassToGenerate =
                ClassName.get(packageName, config.entitiesModuleClassSimpleName());

        val typeModelBuilder = TypeSpec.classBuilder(nameOfClassToGenerate)
                .addAnnotation(_Annotations.configuration())
                .addAnnotation(_Annotations.imports(importsByCategory))
                .addModifiers(Modifier.PUBLIC)
                ;
        return new QualifiedType(
                packageName,
                typeModelBuilder.build());
    }

}
