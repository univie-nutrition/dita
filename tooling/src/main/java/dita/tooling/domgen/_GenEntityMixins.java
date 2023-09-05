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

import java.util.Objects;
import java.util.Optional;

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.FieldSpec;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenEntityMixins {

    JavaModel toJavaModel(
            final DomainGenerator.Config config,
            final OrmModel.Field fieldWithForeignKeys,
            final Can<OrmModel.Field> foreignFields) {

        val entityModel = fieldWithForeignKeys.parent();
        val packageName = config.fullPackageName(entityModel.namespace());
        return new JavaModel(
                "",
                packageName,
                classModel(config, packageName, fieldWithForeignKeys,
                        foreignFields), config.licenseHeader());
    }

    private TypeSpec classModel(
            final DomainGenerator.Config config,
            final String packageName, // shared with entity and mixin
            final OrmModel.Field field,
            final Can<OrmModel.Field> foreignFields) {
        val entityModel = field.parent();
        val typeModelBuilder = TypeSpec.classBuilder(mixinClassName(field))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(_Annotations.property(Snapshot.EXCLUDED))
                .addAnnotation(_Annotations.propertyLayout(field.sequence() + ".1", field.formatDescription("\n")))
                .addAnnotation(RequiredArgsConstructor.class)
                .addField(injectedField(RepositoryService.class, "repositoryService"))
                .addField(mixeeField(ClassName.get(packageName, entityModel.name()), Modifier.FINAL, Modifier.PRIVATE))
                ;

        mixedInProperty(config, field, foreignFields, Modifier.PUBLIC)
            .ifPresent(typeModelBuilder::addMethod);

        return typeModelBuilder.build();
    }

    private FieldSpec injectedField(
            final Class<?> injectedType,
            final String fieldName,
            final Modifier ... modifiers) {
        return FieldSpec.builder(injectedType, fieldName, modifiers)
            .addAnnotation(_Annotations.inject())
            .build();
    }

    private FieldSpec mixeeField(
            final ClassName mixeeType,
            final Modifier ... modifiers) {
        return FieldSpec.builder(mixeeType, "mixee", modifiers)
            .build();
    }

    private Optional<MethodSpec> mixedInProperty(
            final DomainGenerator.Config config,
            final OrmModel.Field field,
            final Can<OrmModel.Field> foreignFields,
            final Modifier ... modifiers) {

        val localKeyGetter = field.getter();

        //TODO debug
        //System.err.printf("--resolve %s%n", foreignFields);

        val foreignEntities = foreignFields.stream()
            .map(OrmModel.Field::parent)
            //.peek(x->System.err.printf("peek %s%n", x)) //TODO debug
            .distinct()
            .collect(Can.toCan());


        if(foreignEntities.getCardinality().isZero()) {
            return Optional.empty();
        }
        if(foreignEntities.getCardinality().isMultiple()) {
            System.err.printf("WARNING: singleton entity check for foreign fields %s failed; skipping mixin generation%n",
                    foreignFields.map(f->f.parent().name() + "::" + f.name()));
            return Optional.empty();
        }

        val foreignEntity = foreignEntities.getSingletonOrFail();
        val foreignPackageName = config.fullPackageName(foreignEntity.namespace());
        val foreignEntityClass = ClassName.get(foreignPackageName, foreignEntity.name());

        val foreignKeyGetters = foreignFields.stream()
            .map(OrmModel.Field::getter)
            .collect(Can.toCan());

        final MethodSpec.Builder builder = MethodSpec.methodBuilder("prop")
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.memberSupport())
                .returns(foreignEntityClass);


        switch(foreignKeyGetters.size()) {
        case 1: builder.addCode("""
                    return repositoryService
                        .uniqueMatch($1T.class,
                            foreign->$2T.equals(foreign.$3L(), mixee.$4L()))
                        .orElse(null);
                    """, foreignEntityClass, Objects.class, foreignKeyGetters.getFirstElseFail(), localKeyGetter);
            break;
        case 2: builder.addCode("""
                return repositoryService
                    .uniqueMatch($1T.class,
                        foreign->$2T.equals(foreign.$3L(), mixee.$4L())
                            && $2T.equals(foreign.$5L(), mixee.$6L())
                        )
                    .orElse(null);
                """, foreignEntityClass, Objects.class,
                    foreignKeyGetters.getFirstElseFail(), localKeyGetter,
                    foreignKeyGetters.getElseFail(1), localKeyGetter);
            break;
        case 3: builder.addCode("""
                return repositoryService
                    .uniqueMatch($1T.class,
                        foreign->$2T.equals(foreign.$3L(), mixee.$4L())
                            && $2T.equals(foreign.$5L(), mixee.$6L())
                            && $2T.equals(foreign.$7L(), mixee.$8L())
                        )
                    .orElse(null);
                """, foreignEntityClass, Objects.class,
                    foreignKeyGetters.getFirstElseFail(), localKeyGetter,
                    foreignKeyGetters.getElseFail(1), localKeyGetter,
                    foreignKeyGetters.getElseFail(2), localKeyGetter);
            break;
        default:
            System.err.printf("WARNING: %d foreign key count not supported in %s; skipping mixin generation%n",
                    foreignKeyGetters.size(),
                    foreignFields.map(f->f.parent().name() + "::" + f.name()));
            return Optional.empty();
        };

        return Optional.of(builder.build());
    }

    private String mixinClassName(final OrmModel.Field field) {
        val entityModel = field.parent();
        val mixedInPropertyName = field.name().endsWith("Code")
                ? _Strings.substring(field.name(), 0, -4)
                : field.name() + "Obj";
        return entityModel.name() + "_" + mixedInPropertyName;
    }

}
