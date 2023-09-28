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
import org.springframework.javapoet.CodeBlock;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.ParameterizedTypeName;

import lombok.experimental.UtilityClass;

@UtilityClass
class _Methods {

    MethodSpec objectSupport(final String methodName, final CodeBlock code, final Modifier ... modifiers) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.objectSupport())
                .returns(ClassName.get("java.lang", "String"))
                .addCode(code)
                .build();
    }

    MethodSpec viewModelMemento(final CodeBlock code) {
        return MethodSpec.methodBuilder("viewModelMemento")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(_Annotations.override())
                .returns(ClassName.get("java.lang", "String"))
                .addCode(code)
                .build();
    }

    MethodSpec managerSearch(final String entityName) {
        return MethodSpec.methodBuilder("getListOf" + entityName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(_Annotations.collection())
                .returns(ParameterizedTypeName.get(
                        ClassName.get(java.util.List.class),
                        ClassName.get("", entityName)))
                .addCode("""
                        return searchService.search($1L.class, $1L::title, search);""",
                        entityName)
                .build();
    }

}
