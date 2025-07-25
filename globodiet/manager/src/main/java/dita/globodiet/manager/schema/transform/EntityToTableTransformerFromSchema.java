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

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.types.BiString;
import dita.commons.types.TabularData.NameTransformer;
import io.github.causewaystuff.companion.codegen.model.Schema;
import io.github.causewaystuff.companion.codegen.model.Schema.Entity;

public record EntityToTableTransformerFromSchema(
        String logicalNamespacePrefix,
        Schema.Domain schema)
implements NameTransformer {

    @Override
    public String transformTable(final String logicalTypeName) {
        return lookupEntityElseFail(logicalTypeName).table();
    }

    @Override
    public String transformColumn(final BiString propertyName) {
        var entity = lookupEntityElseFail(propertyName.left());
        return entity.fields().stream()
            .peek(field->{

                _Assert.assertTrue(
                        Character.isLowerCase(field.name().charAt(0)), field.name());

            })
            .filter(field->field.name().equalsIgnoreCase(propertyName.right()))
            .findFirst()
            .map(field->field.column())
            .orElseThrow(()->_Exceptions.noSuchElement("property '%s' not found in schema",
                    propertyName));
    }

    // -- HELPER

    private Optional<Entity> lookupEntity(final String logicalTypeName) {
        return Optional.ofNullable(schema.entities().get(logicalTypeName))
                .or(()->Optional.ofNullable(schema.entities().get(partialKey(logicalTypeName))))
                .or(()->Optional.ofNullable(schema.entities().get(simpleKey(logicalTypeName))));
    }

    private Entity lookupEntityElseFail(final String logicalTypeName) {
        return lookupEntity(logicalTypeName)
            .orElseThrow(()->_Exceptions.noSuchElement("entity '%s' also using keys '%s' and '%s' not found in schema\n%s",
                    logicalTypeName,
                    partialKey(logicalTypeName),
                    simpleKey(logicalTypeName),
                    schema.entities().keySet()));
    }

    private String partialKey(final String logicalTypeName) {
        return logicalTypeName.substring(logicalNamespacePrefix.length() + 1);
    }
    private String simpleKey(final String logicalTypeName) {
        return _Strings.splitThenStream(logicalTypeName, ".")
            .collect(Can.toCan())
            .getLastElseFail();
    }

}
