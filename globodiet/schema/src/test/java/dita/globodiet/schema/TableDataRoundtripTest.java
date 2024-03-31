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
package dita.globodiet.schema;

import java.io.File;
import java.util.Optional;

import io.github.causewaystuff.companion.codegen.domgen.LicenseHeader;
import io.github.causewaystuff.companion.codegen.model.OrmModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.TextUtils;

import lombok.val;

import dita.commons.types.TabularData;
import dita.globodiet.schema.transform.EntityToTableTransformerFromSchema;
import dita.globodiet.schema.transform.TableToEntityTransformerFromSchema;

class TableDataRoundtripTest {

    // disabled until we have fake data for testing, that can be published
    @DisabledIfSystemProperty(named = "isRunningWithSurefire", matches = "true")
    @Test
    void transformerRoundtrip() {

        final String gdParamDataLowLevelYaml = DataSource.ofResource(
                TableDataRoundtripTest.class, "gd-params.yml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail();

        val dbLow = TabularData.populateFromYaml(gdParamDataLowLevelYaml, TabularData.Format.defaults());

        val schema = OrmModel.Schema.fromYaml(DataSource.ofResource(GdEntityGen.class, "/gd-params.schema.yaml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail());

        val t2eTransformer = new TableToEntityTransformerFromSchema("dita.globodiet", schema);
        val dbHigh = dbLow.transform(t2eTransformer);

        val e2tTransformer = new EntityToTableTransformerFromSchema("dita.globodiet", schema);
        val dbLowAfterRoundtrip = dbHigh.transform(e2tTransformer);

// debug
//        TextUtils.writeLinesToFile(TextUtils.readLines(dbLow.toYaml(TabularUtils.Format.defaults())),
//              new File("src/test/java/dita/globodiet/schema/gd-params-low.yml"), StandardCharsets.UTF_8);

        assertEquals(dbLow, dbLowAfterRoundtrip);

        System.out.println("done.");
    }

    // -- UTIL

    // disabled until we have fake data for testing, that can be published
    @DisabledIfSystemProperty(named = "isRunningWithSurefire", matches = "true")
    //@Test
    void nullableAutodetect() {

        final String gdParamDataLowLevelYaml = DataSource.ofResource(
                TableDataRoundtripTest.class, "gd-params.yml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail();

        var dbLow = TabularData.populateFromYaml(gdParamDataLowLevelYaml, TabularData.Format.defaults());

        var schema = OrmModel.Schema.fromYaml(DataSource.ofResource(GdEntityGen.class, "/gd-params.schema.yaml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail());

        dbLow.dataTables().stream()
        //.filter(table->table.rows().size()>0)
        .forEach(table->{
            table.columns().forEach(IndexedConsumer.zeroBased((colIndex, col)->{
                var entity = schema.lookupEntityByTableName(table.key()).orElseThrow();
                var field = entity.lookupFieldByColumnName(col.name()).orElseThrow();
                var columnValues = table.rows().stream()
                        .map(row->row.cellLiterals().get(colIndex))
                        .map(_Strings::emptyToNull)
                        .collect(Can.toCan());
                var nullable = columnValues.size()<table.rows().size();
                var required = !nullable;
                var hasDuplicates = columnValues.distinct().size()<columnValues.size();
                var unique = !hasDuplicates;

                if(false) {
                if(required && !field.required()) {
                    System.err.printf("required %s.%s -> but schema says nullable%n",
                            table.key(), col.name());
                    //field.withRequired(true);
                } else if(!required && field.required()) {
                    System.err.printf("nullable %s.%s -> but schema says required%n",
                            table.key(), col.name());
                    //field.withRequired(false);
                }
                if(unique && !field.unique()) {
                    System.err.printf("unique %s.%s -> but schema says repeatable%n",
                            table.key(), col.name());
                    //field.withUnique(true);
                } else if(!unique && field.unique()) {
                    System.err.printf("repeated %s.%s -> but schema says unique%n",
                            table.key(), col.name());
                    //field.withUnique(false);
                }
                }

//                field.description().forEach(line->{
//
//                    enumMatch(line, "0=", "0").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "0 =", "0").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "1=", "1").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "1 =", "1").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "2=", "2").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "2 =", "2").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "3=", "3").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "3 =", "3").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "4=", "4").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                    enumMatch(line, "5 =", "5").ifPresent(e->{
//                        System.err.printf("enum %s.%s -> %s%n", table.key(), col.name(), e);
//                        field.enumeration().add(e);
//                    });
//                });

            }));
        });

        schema.splitIntoFiles(new File("d:/tmp/", "gd-schema"), LicenseHeader.ASF_V2);

        System.out.println("done.");
    }

    static Optional<String> enumMatch(final String line, final String matchOn, final String enumValue) {
        if(line.contains(matchOn)) {
            return Optional.of(enumValue + ":" + TextUtils.cutter(line).keepAfter(matchOn).getValue().trim());
        }
        return Optional.empty();
    }



    @DisabledIfSystemProperty(named = "isRunningWithSurefire", matches = "true")
    //@Test
    void genSortedChecklist() {

        var schema = OrmModel.Schema.fromYaml(DataSource.ofResource(GdEntityGen.class, "/gd-params.schema.yaml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail());

        schema.entities().values()
            .stream()
            .sorted((a, b)->a.name().compareTo(b.name()))
            .forEach(t->{
                System.err.printf("- [ ] %s (%s)%n", t.name(), t.table());
            });
    }

    @DisabledIfSystemProperty(named = "isRunningWithSurefire", matches = "true")
    @Test
    void havingTitles() {

        var schema = OrmModel.Schema.fromYaml(DataSource.ofResource(GdEntityGen.class, "/gd-params.schema.yaml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail());

        schema.entities().values()
            .stream()
            .sorted((a, b)->a.name().compareTo(b.name()))
            .forEach(t->{
                if(_Strings.isNullOrEmpty(t.title()))
                System.err.printf("- [ ] %s (%s)%n", t.name(), t.table());
            });
    }


}
