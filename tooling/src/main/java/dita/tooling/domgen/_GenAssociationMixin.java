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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.ParameterizedTypeName;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenAssociationMixin {

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
        val distinctForeignEntities = foreignFields.stream()
                .map(OrmModel.Field::parentEntity)
                .distinct()
                .collect(Can.toCan());
        val useEitherPattern = foreignFields.size()==2
                && distinctForeignEntities.isCardinalityMultiple();

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
                                field.sequence() + ".1", field.formatDescription("\n", "----",
                                        String.format("required=%b, unique=%b", field.required(), field.unique())),
                                useEitherPattern
                                    ? Where.NOWHERE
                                    : Where.REFERENCES_PARENT))
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

        record Foreign(
                ClassName foreignEntity,
                String strictness,
                String foreignKeyGetter,
                int secondaryKeyCardinality,
                String significantArgument,
                String argList) {
        }

        final Can<Foreign> foreigners = foreignFields
                .map((OrmModel.Field foreignField)->{
                    val foreignEntityClass = _Foreign.foreignClassName(field, foreignField, config);
                    var argList = Can.ofCollection(field.discriminatorFields())
                            .add(field)
                            .stream()
                            .map(OrmModel.Field::getter)
                            .map(getter->String.format("mixee.%s()", getter))
                            .collect(Collectors.toCollection(ArrayList::new));
                    val argCount = argList.size();
                    val significantArgument = argList.get(argCount-1);
                    final int foreignSecondaryKeyArgCount = foreignField.parentEntity().secondaryKey().size();

                    // fill up with null args
                    final int fillSize = foreignSecondaryKeyArgCount - argCount;
                    IntStream.range(0, fillSize)
                        .forEach(__->argList.add("null"));

                    val strictness = field.required()
                            ? "unique"
                            : "nullable";

                    return new Foreign(foreignEntityClass, strictness,
                            foreignField.getter(), foreignSecondaryKeyArgCount,
                            significantArgument,
                            argList.stream().collect(Collectors.joining(", ")));
                });

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
                return foreignKeyLookup.decodeLookupKeyList($1T.class, mixee.$2L())
                    .map(foreignKeyLookup::unique);
                """,
                foreignType,
                localKeyGetter);

            return Optional.of(builder.build());
        }

        switch(foreigners.size()) {
        case 1: {

            var foreigner = foreigners.getSingletonOrFail();

            if(foreigner.secondaryKeyCardinality()==0) {
                throw _Exceptions.unrecoverable("%s needs to implement a SecondaryKey", foreigner.foreignEntity());
            }

            builder.addCode("""
                    if($4L==null) return null;
                    final var lookupKey = new $2T.SecondaryKey($3L);
                    return foreignKeyLookup.$1L(lookupKey);
                    """, foreigner.strictness(), foreigner.foreignEntity(), foreigner.argList(),
                    foreigner.significantArgument()
                    );


            break;

        }
        case 2: {
            val foreigner1 = foreigners.getElseFail(0);
            val foreigner2 = foreigners.getElseFail(1);
            if(distinctForeignEntities.isCardinalityOne()) {
                // SHARED FOREIGN ENTITY TYPE
                builder.addCode("""
                        return foreignKeyLookup.decodeLookupKeyList($1T.class, mixee.$2L())
                            .map(foreignKeyLookup::unique)
                            .getSingleton().orElse(null);
                        """, foreigner1.foreignEntity(),
                        localKeyGetter);
            } else {
                // TWO FOREIGN ENTITY TYPES

                builder.addCode("""
                        final int switchOn = foreignKeyLookup.switchOn(mixee);
                        switch(switchOn) {
                        case 1: {
                            if($7L==null) return null;
                            final var lookupKey = new $5T.SecondaryKey($3L);
                            return foreignKeyLookup.$1L(lookupKey);
                        }
                        case 2: {
                            if($8L==null) return null;
                            final var lookupKey = new $6T.SecondaryKey($4L);
                            return foreignKeyLookup.$2L(lookupKey);
                        }}
                        throw $9T.unexpectedCodeReach();
                        """,
                        foreigner1.strictness(),
                        foreigner2.strictness(),
                        foreigner1.argList(),
                        foreigner2.argList(),
                        foreigner1.foreignEntity(),
                        foreigner2.foreignEntity(),
                        foreigner1.significantArgument(),
                        foreigner2.significantArgument(),
                        _Exceptions.class);
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
