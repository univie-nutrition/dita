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
package dita.globodiet.manager.schema.transform;

import java.util.Optional;

import io.github.causewaystuff.companion.codegen.model.Schema;
import io.github.causewaystuff.companion.codegen.model.Schema.Entity;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.types.BiString;
import dita.commons.types.TabularData.NameTransformer;

public record TableToEntityTransformerFromSchema(
        String logicalNamespacePrefix,
        Schema.Domain schema)
implements NameTransformer {

    @Override
    public String transformTable(final String tableName) {
        var entity = lookupEntityElseFail(tableName);
        return String.format("%s.%s.%s", logicalNamespacePrefix, entity.namespace(), entity.name());
    }

    @Override
    public String transformColumn(final BiString columnName) {
        var entity = lookupEntityElseFail(columnName.left());
        return entity.lookupFieldByColumnName(columnName.right())
                .map(field->field.name())
                .orElseThrow(()->_Exceptions.noSuchElement("column '%s' not found in schema",
                        columnName));
    }

    // -- HELPER

    private Optional<Entity> lookupEntity(final String tableName) {
        return schema.entities().values().stream()
                .filter(e->e.table().equals(tableName))
                .findFirst();
    }

    private Entity lookupEntityElseFail(final String tableName) {
        return lookupEntity(tableName)
                .orElseThrow(()->_Exceptions.noSuchElement("table '%s' not found in schema", tableName));
    }
}
