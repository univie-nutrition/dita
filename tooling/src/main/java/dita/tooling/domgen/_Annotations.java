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

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.javapoet.AnnotationSpec;
import org.springframework.javapoet.ClassName;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.NatureOfService;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.collections._Multimaps.ListMultimap;

import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _Annotations {

    // -- LOMBOK

    AnnotationSpec getter() {
        return AnnotationSpec.builder(ClassName.get("lombok", "Getter"))
                .build();
    }
    AnnotationSpec setter() {
        return AnnotationSpec.builder(ClassName.get("lombok", "Setter"))
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
     */
    AnnotationSpec domainObjectLayout(final String describedAs) {
        return AnnotationSpec.builder(DomainObjectLayout.class)
                .addMember("describedAs", "$1S", describedAs)
                .build();
    }
    AnnotationSpec action() {
        return AnnotationSpec.builder(Action.class)
                .build();
    }
    AnnotationSpec property() {
        return AnnotationSpec.builder(Property.class)
                .build();
    }
    /**
     * @param describedAs - property description
     */
    AnnotationSpec propertyLayout(final String sequence, final String describedAs) {
        return AnnotationSpec.builder(PropertyLayout.class)
                .addMember("sequence", "$1S", sequence)
                .addMember("describedAs", "$1S", describedAs)
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
            annotBuilder.addMember("length", "$1L", maxLength);
        }
        return annotBuilder.build();
    }

}
