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
package dita.globodiet.manager.metadata;

import java.util.Map;

import jakarta.inject.Inject;

import org.causewaystuff.companion.codegen.model.OrmModel;
import org.datanucleus.enhancement.Persistable;

import org.apache.causeway.applib.annotation.LabelPosition;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.id.LogicalType;
import org.apache.causeway.core.metamodel.specloader.SpecificationLoader;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import lombok.RequiredArgsConstructor;

@Property
@PropertyLayout(
        labelPosition = LabelPosition.TOP,
        fieldSetId = "metadata",
        hidden = Where.ALL_TABLES)
@RequiredArgsConstructor
public class Persistable_schema {

    @Inject OrmModel.Schema schema;
    @Inject SpecificationLoader specLoader;

    final Persistable mixee;

    public AsciiDoc prop() {

        var entitySchema = specLoader.specForType(mixee.getClass())
                .map(spec->spec.getLogicalType())
                .map(logicalType->schema.entities().get(toLookupKeyIntoSchemaEntities(logicalType)))
                .orElse(null);

        if(entitySchema==null) {
            return AsciiDoc.valueOf("no metadata available");
        }

        var subSchema = new OrmModel.Schema(Map.of(entitySchema.key(), entitySchema));
        subSchema.toYaml();

        return new AsciiDocBuilder()
                .append(doc->AsciiDocFactory.sourceBlock(doc, "yaml", subSchema.toYaml()))
                .buildAsValue();
    }

    // -- HELPER

    private String toLookupKeyIntoSchemaEntities(final LogicalType logicalType) {
        return logicalType.getNamespace().substring("dita.globodiet.".length())
                + "."
                + logicalType.getLogicalTypeSimpleName();
    }

}
