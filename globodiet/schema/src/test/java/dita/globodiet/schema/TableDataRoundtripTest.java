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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.causeway.commons.io.DataSource;

import dita.commons.types.TabularData;
import dita.globodiet.schema.transform.EntityToTableTransformerFromSchema;
import dita.globodiet.schema.transform.TableToEntityTransformerFromSchema;
import dita.tooling.orm.OrmModel;
import lombok.val;

class TableDataRoundtripTest {

    // disabled until we have fake data for testing, that can be published
    @DisabledIfSystemProperty(named = "isRunningWithSurefire", matches = "true")
    @Test
    void transformerRoundtrip() {

        final String gdParamDataHighLevelYaml = DataSource.ofResource(
                TableDataRoundtripTest.class, "gd-params-hi.yml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail();

        val dbHigh = TabularData.populateFromYaml(gdParamDataHighLevelYaml, TabularData.Format.defaults());

        val schema = OrmModel.Schema.fromYaml(DataSource.ofResource(GdEntityGen.class, "/gd-params.schema.yaml")
                .tryReadAsStringUtf8()
                .valueAsNonNullElseFail());

        val e2tTransformer = new EntityToTableTransformerFromSchema("dita.globodiet", schema);
        val dbLow = dbHigh.transform(e2tTransformer);

        val t2eTransformer = new TableToEntityTransformerFromSchema("dita.globodiet", schema);
        val dbHighRecovered = dbLow.transform(t2eTransformer);

// debug
//        TextUtils.writeLinesToFile(TextUtils.readLines(dbLow.toYaml(TabularUtils.Format.defaults())),
//              new File("src/test/java/dita/globodiet/schema/gd-params-low.yml"), StandardCharsets.UTF_8);

        assertEquals(dbHigh, dbHighRecovered);

        System.out.println("done.");
    }

    // -- UTIL



}
