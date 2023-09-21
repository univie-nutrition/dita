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
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.ParameterizedTypeName;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.commons.collections.Can;

import dita.commons.services.foreignkey.DependantLookupService;
import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenDependantsMixins {

    JavaModel toJavaModel(
            final DomainGenerator.Config config,
            final OrmModel.Field fieldWithForeignKeys,
            // all sharing the same foreignEntity, as guaranteed by the caller
            final Can<OrmModel.Field> foreignFields,
            final ClassName propertyMixinClassName) {

        val localEntity = foreignFields.getFirstElseFail().parentEntity(); // entity this mixin contributes to
        val packageName = config.fullPackageName(localEntity.namespace());
        return new JavaModel(
                "",
                packageName,
                classModel(config, packageName, localEntity, fieldWithForeignKeys,
                        foreignFields, propertyMixinClassName), config.licenseHeader());
    }

    private TypeSpec classModel(
            final DomainGenerator.Config config,
            final String packageName, // shared with entity and mixin
            final OrmModel.Entity localEntity, // entity this mixin contributes to
            final OrmModel.Field fieldWithForeignKeys,
            final Can<OrmModel.Field> foreignFields,
            final ClassName propertyMixinClassName) {

        val typeModelBuilder = TypeSpec.classBuilder(_Mixins.collectionMixinClassName(localEntity, fieldWithForeignKeys))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(_Annotations.collection())
                .addAnnotation(RequiredArgsConstructor.class)
                .addField(_Fields.inject(DependantLookupService.class, "dependantLookup"))
                .addField(_Fields.mixee(ClassName.get(packageName, localEntity.name()), Modifier.FINAL, Modifier.PRIVATE))
                .addMethod(mixedInCollection(config, localEntity, fieldWithForeignKeys, foreignFields, propertyMixinClassName, Modifier.PUBLIC))
                ;

        return typeModelBuilder.build();
    }

    private MethodSpec mixedInCollection(
            final DomainGenerator.Config config,
            final OrmModel.Entity localEntity, // entity this mixin contributes to
            final OrmModel.Field fieldWithForeignKeys,
            final Can<OrmModel.Field> foreignFields,
            final ClassName associationMixinClassName,
            final Modifier ... modifiers) {

        val dependantEntity = fieldWithForeignKeys.parentEntity();
        val dependantType = config.javaPoetClassName(dependantEntity);

        final MethodSpec.Builder builder = MethodSpec.methodBuilder("coll")
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.memberSupport())
                .returns(ParameterizedTypeName.get(ClassName.get(java.util.List.class), dependantType))
                .addCode("""
                        return dependantLookup.findDependants(
                            $1T.class,
                            $2T.class,
                            $2T::$3L,
                            mixee);
                        """,
                        dependantType,
                        associationMixinClassName,
                        fieldWithForeignKeys.plural()
                            ? "coll"
                            : "prop");
        return builder.build();
    }

}
