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
package dita.tooling.orm;

import jakarta.inject.Named;

import org.springframework.javapoet.AnnotationSpec;
import org.springframework.javapoet.ClassName;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _Annotations {

    private final static String FIRST_ARG_DOUBLE_QUOTED = "$1S";
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
                .addMember("value", FIRST_ARG_DOUBLE_QUOTED, logicalTypeName)
                .build();
    }

    // -- CAUSEWAY

    AnnotationSpec domainObject() {
        return AnnotationSpec.builder(DomainObject.class)
                .build();
    }
    /**
     * @param describedAs - entity description
     */
    AnnotationSpec domainObjectLayout(final String describedAs) {
        return AnnotationSpec.builder(DomainObjectLayout.class)
                .addMember("describedAs", FIRST_ARG_DOUBLE_QUOTED, describedAs)
                .build();
    }
    AnnotationSpec property() {
        return AnnotationSpec.builder(Property.class)
                .build();
    }
    /**
     * @param describedAs - property description
     */
    AnnotationSpec propertyLayout(final String describedAs) {
        return AnnotationSpec.builder(PropertyLayout.class)
                .addMember("describedAs", FIRST_ARG_DOUBLE_QUOTED, describedAs)
                .build();
    }

    // -- JDO

    AnnotationSpec persistenceCapable() {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "PersistenceCapable"))
                .build();
    }

    AnnotationSpec datastoreIdentity() {
        return AnnotationSpec.builder(ClassName.get("javax.jdo.annotations", "DatastoreIdentity"))
                .addMember("strategy", "$1L", "javax.jdo.annotations.IdGeneratorStrategy.IDENTITY")
                .addMember("column", FIRST_ARG_DOUBLE_QUOTED, "id")
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
            .ifPresent(name->annotBuilder.addMember("name", FIRST_ARG_DOUBLE_QUOTED, name));
        annotBuilder.addMember("allowsNull", FIRST_ARG_DOUBLE_QUOTED, "" + allowsNull);
        if(maxLength>0) {
            annotBuilder.addMember("length", "$1L", maxLength);
        }
        return annotBuilder.build();
    }

}
