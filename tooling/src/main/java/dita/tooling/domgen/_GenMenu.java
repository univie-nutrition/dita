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

import java.util.Collection;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.ParameterizedTypeName;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.applib.annotation.NatureOfService;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.tooling.domgen.DomainGenerator.QualifiedType;
import dita.tooling.orm.OrmModel;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenMenu {

    public QualifiedType qualifiedType(
            final DomainGenerator.Config config,
            final Collection<OrmModel.Entity> entityModels) {

        val typeModelBuilder = TypeSpec.classBuilder("EntitiesMenu")
                .addAnnotation(_Annotations.named(
                        config.fullLogicalName(config.entitiesModulePackageName()) + "." + "EntitiesMenu"))
                .addAnnotation(_Annotations.domainService(NatureOfService.VIEW))
                .addModifiers(Modifier.PUBLIC)
                .addField(_Fields.inject(RepositoryService.class, "repositoryService", Modifier.PRIVATE))
                .addMethods(asMethods(entityModels, config, Modifier.PUBLIC))
                ;

//TODO gen XML as well
//        val imports = entityModels.stream()
//        .map(entityModel->config.javaPoetClassName(entityModel))
//        .toList();

        return new QualifiedType(
                config.fullPackageName(config.entitiesModulePackageName()),
                typeModelBuilder.build());
    }

    // -- HELPER

    private Iterable<MethodSpec> asMethods(
            final Collection<OrmModel.Entity> entityModels,
            final DomainGenerator.Config config,
            final Modifier ... modifiers) {
        return entityModels.stream()
                .map(entityModel->asMenuEntry(entityModel, config, modifiers))
                .collect(Collectors.toList());
    }

    private MethodSpec asMenuEntry(
            final OrmModel.Entity entityModel,
            final DomainGenerator.Config config,
            final Modifier ... modifiers) {
        return MethodSpec.methodBuilder("listAll" + entityModel.name())
                    .addModifiers(modifiers)
                    .returns(ParameterizedTypeName.get(
                            ClassName.get(java.util.List.class),
                            //WildcardTypeName.subtypeOf(
                                    config.javaPoetClassName(entityModel)
                            //)
                            ))
                    .addAnnotation(_Annotations.action())
                    .addAnnotation(_Annotations.actionLayout(entityModel.icon()))
                    .addCode("return repositoryService.allInstances($1L.class);", entityModel.name())
                    .build();
    }

}
