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

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.JavaFile;
import org.springframework.javapoet.TypeSpec;
import org.springframework.util.ReflectionUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.collections._Multimaps;

import dita.tooling.orm.OrmModel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;

public record DomainGenerator(@NonNull DomainGenerator.Config config) {

    @Value @Builder @Accessors(fluent=true)
    public static class Config {
        @Builder.Default
        private final @NonNull String logicalNamespacePrefix = "";
        @Builder.Default
        private final @NonNull String packageNamePrefix = "";
        @Builder.Default
        private final @NonNull LicenseHeader licenseHeader = LicenseHeader.NONE;
        private final @NonNull OrmModel.Schema schema;
        @Builder.Default
        private final @NonNull String entitiesModulePackageName = "";
        @Builder.Default
        private final @NonNull String entitiesModuleClassSimpleName = "EntitiesModule";

        public String fullLogicalName(final String realativeName) {
            return Can.of(
                    _Strings.emptyToNull(logicalNamespacePrefix()),
                    _Strings.emptyToNull(realativeName))
                    .stream()
                    .collect(Collectors.joining("."));
        }
        public String fullPackageName(final String realativeName) {
            return Can.of(
                    _Strings.emptyToNull(packageNamePrefix()),
                    _Strings.emptyToNull(realativeName))
                    .stream()
                    .collect(Collectors.joining("."));
        }
        public ClassName javaPoetClassName(final OrmModel.Entity entityModel) {
            return ClassName.get(fullPackageName(entityModel.namespace()), entityModel.name());
        }
    }

    public record JavaModel(
            @NonNull String logicalTypeName,
            @NonNull ClassName className,
            @NonNull TypeSpec typeSpec,
            @NonNull LicenseHeader licenseHeader) {

        public JavaModel(
                final String logicalName,
                final String packageName,
                final TypeSpec typeSpec,
                final LicenseHeader licenseHeader) {
            this(
                    logicalName,
                    ClassName.get(
                        packageName,
                        (String) ReflectionUtils.getField(ReflectionUtils.findField(typeSpec.getClass(), "name"), typeSpec)
                    ),
                    typeSpec,
                    licenseHeader);
        }
        /**
         * Does not include license header, this is only written later via {@link _DomainWriter}.
         */
        public JavaFile buildJavaFile() {
            val javaFileBuilder = JavaFile.builder(className().packageName(), typeSpec)
                    .addFileComment("Auto-generated by DitA-Tooling")
                    .indent("    ");
            return javaFileBuilder.build();
        }
    }

    public record DomainModel(
            @NonNull OrmModel.Schema schema,
            @NonNull List<JavaModel> modules,
            @NonNull List<JavaModel> entities,
            @NonNull List<JavaModel> entityMixins,
            @NonNull List<JavaModel> menus) {

        DomainModel(final OrmModel.Schema schema) {
            this(schema, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        Stream<JavaModel> streamJavaModels() {
            return Stream.of(modules, menus, entities, entityMixins).flatMap(List::stream);
        }
    }

    public DomainModel createDomainModel() {

        val entityModels = config().schema().entities().values().stream().toList();

        val domainModel = new DomainModel(config().schema());

        // entities
        entityModels.stream()
            .map(entityModel->_GenEntity.toJavaModel(entityModel, config()))
            .forEach(domainModel.entities()::add);

        // entity mixins
        entityModels.stream()
            .forEach(entityModel->{
                entityModel.fields().stream()
                .filter(field->field.hasForeignKeys())
                .forEach(field->{
                    val foreignFields = field.foreignFields(domainModel.schema());

                    final JavaModel propertyMixinModel;

                    domainModel.entityMixins().add(propertyMixinModel =
                            _GenAssociationMixins.toJavaModel(config(), field, foreignFields));

                    val propertyMixin = propertyMixinModel.className;

                    // for each property mixin created, there is at least one collection counterpart

                    switch (foreignFields.getCardinality()) {
                    case ZERO:
                        return; // unexpected code reach
                    case ONE:
                        domainModel.entityMixins().add(
                                _GenDependantsMixins.toJavaModel(config(), field, foreignFields, propertyMixin));
                        return;
                    case MULTIPLE:
                        // group foreign fields by foreign entity, then for each foreign entity create a collection mixin
                        val multiMap = _Multimaps.<OrmModel.Entity, OrmModel.Field>newListMultimap();
                        foreignFields.forEach(foreignField->multiMap.putElement(foreignField.parentEntity(), foreignField));
                        multiMap.forEach((foreignEntity, groupedForeignFields)->{
                            domainModel.entityMixins().add(
                                    _GenDependantsMixins.toJavaModel(config(), field, Can.ofCollection(groupedForeignFields),
                                            propertyMixin));
                        });
                        return;
                    }
                });
            });

        // assert, that we have no mixin created twice
        {
            val mixinNames = new HashSet<String>();
            val messages = new ArrayList<String>();
            domainModel.entityMixins().stream().map(x->x.className().canonicalName())
                .forEach(fqcn->{if(!mixinNames.add(fqcn)) { messages.add(String.format("duplicated mixin %s", fqcn));}});
            _Assert.assertTrue(messages.isEmpty(), ()->messages.stream().collect(Collectors.joining("\n")));
        }

        // module
        domainModel.modules().add(_GenModule.toJavaModel(config(), domainModel.entities(), domainModel.entityMixins()));

        // menu entries
        domainModel.menus().add(_GenMenu.toJavaModel(config(), entityModels));

        return domainModel;
    }

    public void writeToDirectory(final @NonNull File dest) {
        _DomainWriter.writeToDirectory(createDomainModel().streamJavaModels(), dest);
    }

}
