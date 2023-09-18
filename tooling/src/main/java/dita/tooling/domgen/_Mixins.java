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

import org.apache.causeway.commons.internal.base._Strings;

import dita.tooling.orm.OrmModel;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
class _Mixins {

    private final static List<String> knownPropertyNameSuffixes = List.of(
                "Code",
                "LookupKey");

    String propertyMixinClassName(final OrmModel.Field field) {
        val entityModel = field.parentEntity();
        return entityModel.name() + "_" + normalizedPropertyName(field);
    }

    String normalizedPropertyName(final OrmModel.Field field) {
        val mixedInPropertyName = knownPropertyNameSuffixes.stream()
                .filter(field.name()::endsWith)
                .findFirst()
                .map(suffix->_Strings.substring(field.name(), 0, -suffix.length()))
                .orElseGet(()->field.name() + "Obj");
        return mixedInPropertyName;
    }

    String collectionMixinClassName(
            final OrmModel.Entity localEntity,
            final OrmModel.Field dependantField) {
        val dependantEntity = dependantField.parentEntity();
        return localEntity.name()
                + "_dependent"
                + _Strings.capitalize(dependantEntity.name())
                + "MappedBy"
                + _Strings.capitalize(normalizedPropertyName(dependantField));
    }

}