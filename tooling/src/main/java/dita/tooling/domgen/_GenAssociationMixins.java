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

import java.util.Optional;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.ParameterizedTypeName;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenAssociationMixins {

    JavaModel toJavaModel(
            final DomainGenerator.Config config,
            final OrmModel.Field fieldWithForeignKeys,
            final Can<OrmModel.Field> foreignFields) {

        val entityModel = fieldWithForeignKeys.parentEntity();
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

        val isPlural = field.plural();

        val entityModel = field.parentEntity();
        val typeModelBuilder = TypeSpec.classBuilder(_Mixins.propertyMixinClassName(field))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(isPlural
                        ? _Annotations.collection()
                        : _Annotations.property(Snapshot.EXCLUDED))
                .addAnnotation(
                        isPlural
                        ? _Annotations.collectionLayout(
                                field.formatDescription("\n"),
                                Where.NOWHERE)
                        : _Annotations.propertyLayout(
                                field.sequence() + ".1", field.formatDescription("\n"),
                                Where.REFERENCES_PARENT))
                .addAnnotation(RequiredArgsConstructor.class)
                .addField(_Fields.inject(ForeignKeyLookupService.class, "foreignKeyLookup"))
                .addField(_Fields.mixee(ClassName.get(packageName, entityModel.name()), Modifier.FINAL, Modifier.PRIVATE))
                ;

        mixedInAssociation(config, field, foreignFields, Modifier.PUBLIC)
            .ifPresent(typeModelBuilder::addMethod);

        return typeModelBuilder.build();
    }

    private Optional<MethodSpec> mixedInAssociation(
            final DomainGenerator.Config config,
            final OrmModel.Field field,
            final Can<OrmModel.Field> foreignFields,
            final Modifier ... modifiers) {

        val isPlural = field.plural();
        val localKeyGetter = field.getter();

        //TODO debug
        //System.err.printf("--resolve %s%n", foreignFields);

        record Foreign(ClassName foreignEntity, String foreignKeyGetter) {
        }

        final Can<Foreign> foreigners = foreignFields.stream()
                .map((OrmModel.Field foreignField)->{
                    val foreignEntity = foreignField.parentEntity();
                    val foreignPackageName = config.fullPackageName(foreignEntity.namespace());
                    val foreignEntityClass = ClassName.get(foreignPackageName, foreignEntity.name());
                    return new Foreign(foreignEntityClass, foreignField.getter());
                })
                .collect(Can.toCan());

        if(foreigners.getCardinality().isZero()) {
            return Optional.empty();
        }

        val distinctForeignEntities = foreignFields.stream()
                .map(OrmModel.Field::parentEntity)
                .distinct()
                .collect(Can.toCan());

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(isPlural
                    ? "coll"
                    : "prop")
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.memberSupport())
                .returns(distinctForeignEntities.isCardinalityMultiple()
                        ? ClassName.OBJECT // common super type
                        : isPlural
                                ? ParameterizedTypeName.get(
                                        ClassName.get(Can.class),
                                        foreigners.getFirstElseFail().foreignEntity())
                                : foreigners.getFirstElseFail().foreignEntity());

        if(isPlural) {
            _Assert.assertTrue(distinctForeignEntities.isCardinalityOne(),
                    ()->"not implemented for multiple referenced foreign entity types");

            val foreignType = foreigners.getFirstElseFail().foreignEntity();
            builder.addCode("""
                return foreignKeyLookup
                    .plural(
                        this,
                        mixee, mixee.$2L(),
                        // foreign
                        $3T.class,
                        $1T.of($4L));
                """,
                Can.class, //1
                localKeyGetter, //2
                foreignType, //3
                foreigners.stream()
                    .map(f->String.format("%s::%s", f.foreignEntity().simpleName(), f.foreignKeyGetter()))
                    .collect(Collectors.joining(", ")) //4
                );

            return Optional.of(builder.build());
        }

        switch(foreigners.size()) {
        case 1: {
            val foreigner = foreigners.getSingletonOrFail();
            builder.addCode("""
                    return foreignKeyLookup
                        .unary(
                            this,
                            // local
                            mixee, mixee.$1L(),
                            // foreign
                            $2T.class, $2T::$3L)
                        .orElse(null);
                    """, localKeyGetter,
                    foreigner.foreignEntity(), foreigner.foreignKeyGetter());
            break;
        }
        case 2: {
            val foreigner1 = foreigners.getElseFail(0);
            val foreigner2 = foreigners.getElseFail(1);
            if(distinctForeignEntities.isCardinalityOne()) {
                // SHARED FOREIGN ENTITY TYPE
                builder.addCode("""
                        return foreignKeyLookup
                            .binary(
                                this,
                                // local
                                mixee, mixee.$1L(),
                                // foreign
                                $2T.class, foreign->foreign.$3L(), foreign->foreign.$4L())
                            .orElse(null);
                        """, localKeyGetter, foreigner1.foreignEntity(),
                        foreigner1.foreignKeyGetter(), foreigner2.foreignKeyGetter());
            } else {
                // TWO FOREIGN ENTITY TYPES
                builder.addCode("""
                        return foreignKeyLookup
                            .either(
                                this,
                                // local
                                mixee, mixee.$1L(),
                                // foreign
                                $2T.class, foreign->foreign.$3L(),
                                $4T.class, foreign->foreign.$5L())
                            .map(either->either.isLeft()
                                ? either.leftIfAny()
                                : either.rightIfAny())
                            .orElse(null);
                        """, localKeyGetter,
                        foreigner1.foreignEntity(), foreigner1.foreignKeyGetter(),
                        foreigner2.foreignEntity(), foreigner2.foreignKeyGetter());
            }
            break;
        }
        default:
            System.err.printf("WARNING: %d foreign key count not supported in %s; skipping mixin generation%n",
                    foreigners.size(),
                    foreignFields.map(f->f.parentEntity().name() + "::" + f.name()));
            return Optional.empty();
        };

        return Optional.of(builder.build());
    }

}
