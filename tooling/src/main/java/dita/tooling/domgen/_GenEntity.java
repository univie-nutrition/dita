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

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.springframework.javapoet.ClassName;
import org.springframework.javapoet.FieldSpec;
import org.springframework.javapoet.MethodSpec;
import org.springframework.javapoet.ParameterizedTypeName;
import org.springframework.javapoet.TypeSpec;

import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.internal.base._Strings;

import dita.commons.services.foreignkey.HasSecondaryKey;
import dita.commons.services.foreignkey.ISecondaryKey;
import dita.tooling.domgen.DomainGenerator.Config;
import dita.tooling.domgen.DomainGenerator.JavaModel;
import dita.tooling.orm.OrmModel;
import dita.tooling.orm.OrmModel.Entity;
import dita.tooling.orm.OrmModel.Field;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _GenEntity {

    JavaModel toJavaModel(
            final OrmModel.Entity entityModel,
            final DomainGenerator.Config config) {

        val logicalNameSpace = config.fullLogicalName(entityModel.namespace());
        val packageName = config.fullPackageName(entityModel.namespace());
        return new JavaModel(
                logicalNameSpace,
                packageName,
                classModel(logicalNameSpace, entityModel, config), config.licenseHeader());
    }

    // -- HELPER

    private TypeSpec classModel(final String logicalNamespace, final OrmModel.Entity entityModel,
            final Config config) {

        var typeModelBuilder = TypeSpec.classBuilder(entityModel.name())
                .addJavadoc(entityModel.formatDescription("<br>\n"))
                .addAnnotation(_Annotations.named(logicalNamespace + "." + entityModel.name()))
                .addAnnotation(_Annotations.domainObject())
                .addAnnotation(_Annotations.domainObjectLayout(
                        entityModel.formatDescription("<br>\n"),
                        entityModel.icon()))
                .addAnnotation(_Annotations.persistenceCapable(entityModel.table()))
                .addAnnotation(_Annotations.datastoreIdentity())
                .addModifiers(Modifier.PUBLIC)
                .addMethod(asTitleMethod(entityModel, Modifier.PUBLIC))
                .addFields(asFields(entityModel.fields(), Modifier.PRIVATE))
                ;

        // super type

        if(entityModel.hasSuperType()) {
            val packageName = config.fullPackageName(entityModel.superTypeNamespace());
            val superTypeName = ClassName.get(packageName, entityModel.superTypeSimpleName());
            typeModelBuilder.addSuperinterface(superTypeName);
        }

        // inner enums

        entityModel.fields().stream()
                .filter(OrmModel.Field::isEnum)
                .forEach(field->
                    typeModelBuilder.addType(
                            _Enums.enumForColumn(field.asJavaType(), field.enumConstants())));

        if(entityModel.hasSecondaryKey()) {

            typeModelBuilder.addSuperinterface(ParameterizedTypeName.get(
                    ClassName.get(HasSecondaryKey.class),
                    ClassName.get("", entityModel.name())));

            // inner secondary key class

            val secondaryKeyClass = TypeSpec.classBuilder("SecondaryKey")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addSuperinterface(ParameterizedTypeName.get(
                            ClassName.get(ISecondaryKey.class),
                            ClassName.get("", entityModel.name())))
                    .addJavadoc("SecondaryKey for @{link $1L}", entityModel.name())
                    .addAnnotation(_Annotations.lombokValue())
                    .addField(_Fields.serialVersionUID(1L))
                    .addFields(asSecondaryKeyFields(entityModel.secondaryKeyFields(), Modifier.PRIVATE))
                    .addMethod(MethodSpec.methodBuilder("correspondingClass")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(_Annotations.override())
                            .returns(ParameterizedTypeName.get(
                                    ClassName.get(Class.class),
                                    ClassName.get("", entityModel.name())))
                            .addCode("return $1L.class;", entityModel.name())
                            .build())
                    /*
                    @Override
                    public Unresolvable unresolvable() {
                        return new Unresolvable(String.format("UNRESOLVABLE %s", this));
                    }
                     */
                    .addMethod(MethodSpec.methodBuilder("unresolvable")
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                            .addAnnotation(_Annotations.override())
                            .returns(ClassName.get("", "Unresolvable"))
                            .addCode("""
                                    return new Unresolvable(String.format("UNRESOLVABLE %s", this));""")
                            .build())
                    .build();

            typeModelBuilder.addType(secondaryKeyClass);
            typeModelBuilder.addMethod(asSecondaryKeyMethod(secondaryKeyClass, entityModel.secondaryKeyFields(), Modifier.PUBLIC));

            // inner unresolvable class

            val unresolvableClass = TypeSpec.classBuilder("Unresolvable")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .superclass(ClassName.get("", entityModel.name()))
                    .addSuperinterface(ClassName.get(org.apache.causeway.applib.ViewModel.class))
                    .addAnnotation(_Annotations.domainObjectLayout(
                            String.format("Unresolvable %s", entityModel.name()),
                            "skull red"))
                    .addJavadoc("Placeholder @{link ViewModel} for @{link $1L} "
                            + "in case of an unresolvable secondary key.", entityModel.name())
                    .addAnnotation(_Annotations.requiredArgsConstructor())
                    .addField(FieldSpec.builder(ClassName.get(String.class), "viewModelMemento", Modifier.PRIVATE, Modifier.FINAL)
                            .addAnnotation(_Annotations.getterWithOverride())
                            .addAnnotation(_Annotations.accessorsFluent())
                            .build())

                    .addMethod(MethodSpec.methodBuilder("title")
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(_Annotations.override())
                            .returns(ClassName.get(String.class))
                            .addCode("return viewModelMemento;")
                            .build())
                    .build();

            typeModelBuilder.addType(unresolvableClass);
            typeModelBuilder.addMethod(asUnresolvableMethod(unresolvableClass, entityModel.secondaryKeyFields(), Modifier.PUBLIC));


        }
        return typeModelBuilder.build();
    }

    private MethodSpec asTitleMethod(final Entity entityModel, final Modifier ... modifiers) {
        final String code = _Strings.nonEmpty(entityModel.title())
                .orElse("this.toString()");
        return MethodSpec.methodBuilder("title")
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.objectSupport())
                .returns(ClassName.get("java.lang", "String"))
                .addCode("return " + code + ";")
                .build();
    }

    private Iterable<FieldSpec> asFields(
            final List<OrmModel.Field> fields,
            final Modifier ... modifiers) {
        return fields.stream()
                .map(field->{
                    var fieldBuilder = FieldSpec.builder(
                            field.isEnum()
                                ? field.asJavaEnumType()
                                : field.asJavaType(),
                            field.name(),
                            modifiers)
                    .addJavadoc(field.formatDescription("\n"))
                    .addAnnotation(!field.required()
                            ? _Annotations.property(Optionality.OPTIONAL)
                            : _Annotations.property())
                    .addAnnotation(_Annotations.propertyLayout(
                            field.sequence(),
                            field.formatDescription("<br>", "----",
                                    String.format("required=%b, unique=%b", field.required(), field.unique())),
                            field.hasForeignKeys()
                            ? Where.ALL_TABLES
                            : Where.NOWHERE))
                    .addAnnotation(_Annotations.column(field.column(), !field.required(), field.maxLength()))
                    .addAnnotation(_Annotations.getter())
                    .addAnnotation(_Annotations.setter());

                    if(field.isEnum()) {
                        fieldBuilder
                            .addAnnotation(_Annotations.datanucleusCheckEnumConstraint(true))
                            .addAnnotation(_Annotations.datanucleusEnumValueGetter("getMatchOn"));
                    }

                    return fieldBuilder.build();
                })
                .collect(Collectors.toList());
    }

    private Iterable<FieldSpec> asSecondaryKeyFields(
            final List<OrmModel.Field> fields,
            final Modifier ... modifiers) {
        return fields.stream()
                .map(field->
                    FieldSpec.builder(field.asJavaType(), field.name(), modifiers)
                    .addJavadoc(field.formatDescription("<br>"))
                    .build())
                .collect(Collectors.toList());
    }

    /*
        @Override
        public Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
     */
    private MethodSpec asUnresolvableMethod(
            final TypeSpec unresolvableClass,
            final List<OrmModel.Field> fields,
            final Modifier ... modifiers) {
        return MethodSpec.methodBuilder("unresolvable")
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.programmatic())
                .returns(ClassName.get("", "Unresolvable"))
                .addCode("""
                        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey($1L)));""",
                        asArgList(fields))
                .build();
    }

    private MethodSpec asSecondaryKeyMethod(
            final TypeSpec secondaryKeyClass,
            final List<OrmModel.Field> fields,
            final Modifier ... modifiers) {
        return MethodSpec.methodBuilder("secondaryKey")
                .addModifiers(modifiers)
                .addAnnotation(_Annotations.programmatic())
                .returns(ClassName.get("", "SecondaryKey"))
                .addCode(String.format("return new SecondaryKey(%s);", asArgList(fields)))
                .build();
    }

    private String asArgList(final List<Field> fields) {
        return fields.stream()
                .map(field->field.getter() + "()")
                .collect(Collectors.joining(", "));
    }

}
