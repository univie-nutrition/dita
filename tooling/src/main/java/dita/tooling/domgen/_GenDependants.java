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

import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.springframework.context.annotation.Configuration;
import org.springframework.javapoet.AnnotationSpec;
import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.ParameterizedTypeName;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.commons.collections.Can;

import dita.commons.services.lookup.DependantLookupService;
import dita.tooling.domgen.DomainGenerator.QualifiedType;
import dita.tooling.orm.OrmModel;
import dita.tooling.orm.OrmModel.Entity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenDependants {

    QualifiedType qualifiedType(
            final DomainGenerator.Config config,
            final OrmModel.Entity entityModel,
            final Can<DependantMixinSpec> mixinSpecs) {

        val packageName = config.fullPackageName(entityModel.namespace());

        val typeModelBuilder = TypeSpec.classBuilder(entityModel.name() + "Deps")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Configuration.class)
                        .build());

        // inner mixin classes

        mixinSpecs.forEach(mixinSpec->{
            OrmModel.Field fieldWithForeignKeys = mixinSpec.fieldWithForeignKeys();
            Can<OrmModel.Field> foreignFields = mixinSpec.foreignFields();
            ClassName propertyMixinClassName = mixinSpec.propertyMixinClassName();
            val localEntity = mixinSpec.localEntity();

            val innerMixin = TypeSpec.classBuilder(mixinSpec.mixinClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(_Annotations.collection(attr->attr))
                .addAnnotation(RequiredArgsConstructor.class)
                .addField(_Fields.inject(DependantLookupService.class, "dependantLookup"))
                .addField(_Fields.mixee(ClassName.get(packageName, localEntity.name()), Modifier.FINAL, Modifier.PRIVATE))
                .addMethod(mixedInCollection(config, localEntity, fieldWithForeignKeys, foreignFields, propertyMixinClassName,
                        Modifier.PUBLIC))
                ;

            typeModelBuilder.addType(innerMixin.build());
        });

        // static method that provides all mixin classes we generated above
        typeModelBuilder.addMethod(MethodSpec.methodBuilder("mixinClasses")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(
                        ClassName.get(Can.class),
                        ParameterizedTypeName.get(
                                ClassName.get(Class.class),
                                ClassName.get("", "?"))))
                .addCode("""
                        return Can.of($1L);""",
                        mixinSpecs.stream()
                            .map(DependantMixinSpec::mixinClassName)
                            .map(name->name + ".class")
                            .collect(Collectors.joining(",\n")))
                .build());

        return new QualifiedType(
                packageName,
                typeModelBuilder.build());
    }

    static record DependantMixinSpec(
        OrmModel.Field fieldWithForeignKeys,
                // all sharing the same foreignEntity, as guaranteed by the caller
        Can<OrmModel.Field> foreignFields,
        ClassName propertyMixinClassName) {
        /**
         * entity this mixin contributes to
         */
        Entity localEntity() {
            return foreignFields.getFirstElseFail().parentEntity();
        }
        String mixinClassName() {
            return _Mixins.collectionMixinClassName(localEntity(), fieldWithForeignKeys);
        }
    }

    // -- HELPER

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
