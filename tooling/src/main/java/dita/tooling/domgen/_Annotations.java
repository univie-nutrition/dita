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

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.javapoet.AnnotationSpec;
import org.springframework.javapoet.ClassName;
import org.springframework.lang.Nullable;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DependentDefaultsPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.NatureOfService;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.collections._Multimaps.ListMultimap;

import lombok.Builder;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _Annotations {

    // -- JAVA

    AnnotationSpec override() {
        return AnnotationSpec.builder(ClassName.get(Override.class))
                .build();
    }

    // -- LOMBOK

    AnnotationSpec allArgsConstructor() {
        return AnnotationSpec.builder(ClassName.get("lombok", "AllArgsConstructor"))
                .build();
    }
    AnnotationSpec requiredArgsConstructor() {
        return AnnotationSpec.builder(ClassName.get("lombok", "RequiredArgsConstructor"))
                .build();
    }
    AnnotationSpec lombokValue() {
        return AnnotationSpec.builder(ClassName.get("lombok", "Value"))
                .build();
    }
    AnnotationSpec getter() {
        return AnnotationSpec.builder(ClassName.get("lombok", "Getter"))
                .build();
    }
    AnnotationSpec getterWithOverride() {
        return AnnotationSpec.builder(ClassName.get("lombok", "Getter"))
                .addMember("onMethod_", "{@Override}")
                .build();
    }
    AnnotationSpec setter() {
        return AnnotationSpec.builder(ClassName.get("lombok", "Setter"))
                .build();
    }
    AnnotationSpec accessorsFluent() {
        return AnnotationSpec.builder(ClassName.get("lombok.experimental", "Accessors"))
                .addMember("fluent", "true")
                .build();
    }

    // -- JAKARTA

    /**
     * @param logicalTypeName - logical type name (Apache Causeway semantics)
     */
    AnnotationSpec named(final String logicalTypeName) {
        return AnnotationSpec.builder(Named.class)
                .addMember("value", "$1S", logicalTypeName)
                .build();
    }
    AnnotationSpec inject() {
        return AnnotationSpec.builder(Inject.class)
                .build();
    }

    // -- SPRING

    AnnotationSpec configuration() {
        return AnnotationSpec.builder(Configuration.class)
                .build();
    }
    AnnotationSpec imports(final ListMultimap<String, ClassName> importsByCategory) {

        val sb = new StringBuilder();

        importsByCategory.entrySet()
        .forEach(entry->{

            // category comment
            sb.append("\n").append(String.format("// %s\n", entry.getKey()));

            final String importsLiteral = entry.getValue().stream()
                    .map(_import->String.format("%s.class,", _import.canonicalName()))
                    .collect(Collectors.joining("\n"));

            sb.append(importsLiteral).append("\n");

        });

        return AnnotationSpec.builder(Import.class)
                .addMember("value", "{\n$1L\n}", sb.toString())
                .build();
    }

    // -- CAUSEWAY

    AnnotationSpec domainObject() {
        return AnnotationSpec.builder(DomainObject.class)
                .build();
    }
    AnnotationSpec domainService(final NatureOfService natureOfService) {
        return AnnotationSpec.builder(DomainService.class)
                .addMember("nature", NatureOfService.class.getName() + ".$1L", natureOfService.name())
                .build();
    }
    /**
     * @param describedAs - entity description
     * @param cssClassFa - entity icon (Font Awesome)
     */
    AnnotationSpec domainObjectLayout(
            final @Nullable String describedAs,
            final @Nullable String cssClassFa) {
        val builder = AnnotationSpec.builder(DomainObjectLayout.class);
        _Strings.nonEmpty(describedAs)
            .ifPresent(__->builder.addMember("describedAs", "$1S", describedAs));
        _Strings.nonEmpty(cssClassFa)
            .ifPresent(__->builder.addMember("cssClassFa", "$1S", cssClassFa));
        return builder.build();
    }
    AnnotationSpec action() {
        return AnnotationSpec.builder(Action.class)
                .build();
    }
    AnnotationSpec actionLayout(final String cssClassFa) {
        val builder = AnnotationSpec.builder(ActionLayout.class);
        _Strings.nonEmpty(cssClassFa)
            .ifPresent(__->builder.addMember("cssClassFa", "$1S", cssClassFa));
        return builder.build();
    }
    AnnotationSpec property() {
        return AnnotationSpec.builder(Property.class)
                .build();
    }
    AnnotationSpec property(final Optionality optionality) {
        return AnnotationSpec.builder(Property.class)
                .addMember("optionality", "$1T.$2L", Optionality.class, optionality.name())
                .build();
    }
    public static AnnotationSpec property(final Optionality optionality, final Editing editing) {
        return AnnotationSpec.builder(Property.class)
                .addMember("optionality", "$1T.$2L", Optionality.class, optionality.name())
                .addMember("editing", "$1T.$2L", Editing.class, editing.name())
                .build();
    }
    AnnotationSpec property(final Snapshot snapshot) {
        return AnnotationSpec.builder(Property.class)
                .addMember("snapshot", "$1T.$2L", Snapshot.class, snapshot.name())
                .build();
    }
    AnnotationSpec collection() {
        return AnnotationSpec.builder(Collection.class)
                .build();
    }
    AnnotationSpec collectionLayout(final String describedAs, final Where hiddenWhere) {
        return AnnotationSpec.builder(CollectionLayout.class)
                .addMember("describedAs", "$1S", describedAs)
                .addMember("hidden", "$1T.$2L", Where.class, hiddenWhere.name())
                .build();
    }
    AnnotationSpec parameter(final DependentDefaultsPolicy dependentDefaultsPolicy) {
        return AnnotationSpec.builder(Parameter.class)
                .addMember("dependentDefaultsPolicy", "$1T.$2L", DependentDefaultsPolicy.class, dependentDefaultsPolicy.name())
                .build();
    }
    AnnotationSpec parameter(final DependentDefaultsPolicy dependentDefaultsPolicy, final Optionality optionality) {
        return AnnotationSpec.builder(Parameter.class)
                .addMember("dependentDefaultsPolicy", "$1T.$2L", DependentDefaultsPolicy.class, dependentDefaultsPolicy.name())
                .addMember("optionality", "$1T.$2L", Optionality.class, optionality.name())
                .build();
    }
    AnnotationSpec parameterLayout(final String describedAs) {
        return AnnotationSpec.builder(ParameterLayout.class)
                .addMember("describedAs", "$1S", describedAs)
                .build();
    }

    @Builder
    static record PropertyLayoutRecord(
        String fieldSetId,
        String sequence,
        String describedAs,
        Where hiddenWhere) {
    }
    AnnotationSpec propertyLayout(final PropertyLayoutRecord argsAsRecord) {
        val builder = AnnotationSpec.builder(PropertyLayout.class);
        _Strings.nonEmpty(argsAsRecord.fieldSetId())
            .ifPresent(fieldSetId->builder.addMember("fieldSetId", "$1S", fieldSetId));
        _Strings.nonEmpty(argsAsRecord.sequence())
            .ifPresent(sequence->builder.addMember("sequence", "$1S", sequence));
        _Strings.nonEmpty(argsAsRecord.describedAs())
            .ifPresent(describedAs->builder.addMember("describedAs", "$1S", describedAs));
        Optional.ofNullable(argsAsRecord.hiddenWhere())
            .ifPresent(hiddenWhere->builder.addMember("hidden", "$1T.$2L", Where.class, hiddenWhere.name()));
        return builder.build();
    }

    AnnotationSpec memberSupport() {
        return AnnotationSpec.builder(MemberSupport.class)
                .build();
    }

    AnnotationSpec objectSupport() {
        return AnnotationSpec.builder(ObjectSupport.class)
                .build();
    }

    AnnotationSpec programmatic() {
        return AnnotationSpec.builder(Programmatic.class)
                .build();
    }

    // -- JDO

    AnnotationSpec persistenceCapable() {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "PersistenceCapable"))
                .build();
    }
    AnnotationSpec persistenceCapable(final String tableName) {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "PersistenceCapable"))
                .addMember("table", "$1S", tableName)
                .build();
    }
    AnnotationSpec datastoreIdentity() {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "DatastoreIdentity"))
                .addMember("strategy", "$1L", "javax.jdo.annotations.IdGeneratorStrategy.IDENTITY")
                .addMember("column", "$1S", "id")
                .build();
    }
    /**
     * @param columnName - name of the db column, if null or empty uses default name
     * @param allowsNull - whether null is allowed as database value for this column
     * @param maxLength - ignored if less than one
     */
    AnnotationSpec column(
            final String columnName,
            final boolean allowsNull,
            final int maxLength) {
        val annotBuilder = AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "Column"));
        _Strings.nonEmpty(_Strings.trim(columnName))
            .ifPresent(name->annotBuilder.addMember("name", "$1S", name));
        annotBuilder.addMember("allowsNull", "$1S", "" + allowsNull);
        if(maxLength>0) {
            annotBuilder.addMember("length", "$1L", Math.min(maxLength, 1024*4)); // upper bound = 4k
        }
        return annotBuilder.build();
    }

    /**
     * <pre>{@code @Extension(vendorName="datanucleus", key="datastore", value="store2")}</pre>
     */
    AnnotationSpec datanucleusDatastore(final String datastore) {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "Extension"))
            .addMember("vendorName", "$1S", "datanucleus")
            .addMember("key", "$1S", "datastore")
            .addMember("value", "$1S", datastore)
            .build();
    }

    /**
     * <pre>{@code @Extension(vendorName="datanucleus", key="enum-value-getter", value="getValue")}</pre>
     */
    AnnotationSpec datanucleusEnumValueGetter(final String enumValueGetter) {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "Extension"))
            .addMember("vendorName", "$1S", "datanucleus")
            .addMember("key", "$1S", "enum-value-getter")
            .addMember("value", "$1S", enumValueGetter)
            .build();
    }

    /**
     * <pre>{@code @Extension(vendorName="datanucleus", key="enum-check-constraint", value="true")}</pre>
     */
    AnnotationSpec datanucleusCheckEnumConstraint(final boolean checkEnumConstraint) {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "Extension"))
            .addMember("vendorName", "$1S", "datanucleus")
            .addMember("key", "$1S", "enum-check-constraint")
            .addMember("value", "$1S", checkEnumConstraint)
            .build();
    }

}
