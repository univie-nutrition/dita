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

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenPropertyMixins {

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
        val entityModel = field.parentEntity();
        val typeModelBuilder = TypeSpec.classBuilder(_Mixins.propertyMixinClassName(field))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(_Annotations.property(Snapshot.EXCLUDED))
                .addAnnotation(_Annotations.propertyLayout(field.sequence() + ".1", field.formatDescription("\n")))
                .addAnnotation(RequiredArgsConstructor.class)
                .addField(_Fields.inject(ForeignKeyLookupService.class, "foreignKeyLookup"))
                .addField(_Fields.mixee(ClassName.get(packageName, entityModel.name()), Modifier.FINAL, Modifier.PRIVATE))
                ;

        mixedInProperty(config, field, foreignFields, Modifier.PUBLIC)
            .ifPresent(typeModelBuilder::addMethod);

        return typeModelBuilder.build();
    }

    private Optional<MethodSpec> mixedInProperty(
            final DomainGenerator.Config config,
            final OrmModel.Field field,
            final Can<OrmModel.Field> foreignFields,
            final Modifier ... modifiers) {

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

        val foreignEntities = foreignFields.stream()
                .map(OrmModel.Field::parentEntity)
                .distinct()
                .collect(Can.toCan());

        final MethodSpec.Builder builder = MethodSpec.methodBuilder("prop")
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.memberSupport())
                .returns(foreignEntities.isCardinalityOne()
                        ? foreigners.getFirstElseFail().foreignEntity()
                        : ClassName.OBJECT); // common super type

        switch(foreigners.size()) {
        case 1: {
            val foreigner = foreigners.getSingletonOrFail();
            builder.addCode("""
                    return foreignKeyLookup
                        .unary(
                            // local
                            mixee, $1S, mixee.$2L(),
                            // foreign
                            $3T.class, foreign->foreign.$4L())
                        .orElse(null);
                    """, field.name(), localKeyGetter,
                    foreigner.foreignEntity(), foreigner.foreignKeyGetter());
            break;
        }
        case 2: {
            val foreigner1 = foreigners.getElseFail(0);
            val foreigner2 = foreigners.getElseFail(1);
            if(foreignEntities.isCardinalityOne()) {
                // SHARED FOREIGN ENTITY TYPE
                builder.addCode("""
                        return foreignKeyLookup
                            .binary(
                                // local
                                mixee, mixee.$1L(),
                                // foreign
                                $2T.class, foreign->foreign.$3L(), foreign->foreign.$4L())
                            .orElse(null);
                        """, localKeyGetter, foreigner1.foreignEntity(),
                        foreigner1.foreignKeyGetter(), foreigner2.foreignKeyGetter());
            } else {
                // MUTLIPLE FOREIGN ENTITY TYPES
                builder.addCode("""
                        return foreignKeyLookup
                            .binary(
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
        case 3: {
            _Assert.assertTrue(foreignEntities.isCardinalityOne(),
                    ()->"not implemented for multiple referenced foreign entity types");
            val foreigner1 = foreigners.getElseFail(0);
            val foreigner2 = foreigners.getElseFail(1);
            val foreigner3 = foreigners.getElseFail(2);
            builder.addCode("""
                return foreignKeyLookup
                    .ternary(
                        // local
                        mixee, mixee.$1L(),
                        // foreign
                        $2T.class, foreign->foreign.$3L(), foreign->foreign.$4L(), foreign->foreign.$5L())
                    .orElse(null);
                """, localKeyGetter, foreigner1.foreignEntity(),
                foreigner1.foreignKeyGetter(),
                foreigner2.foreignKeyGetter(),
                foreigner3.foreignKeyGetter());
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
