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
package dita.globodiet.manager.help;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.springframework.stereotype.Component;

import org.apache.causeway.extensions.docgen.help.applib.HelpPage;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;

import static org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory.cell;
import static org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory.headCell;
import static org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory.row;
import static org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory.table;

import dita.globodiet.manager.DitaModuleGdManager;
import dita.tooling.orm.OrmModel;
import lombok.val;

@Component
@Named(DitaModuleGdManager.NAMESPACE + ".DitaTableNamesPage")
public class DitaTableNamesPage implements HelpPage {

    @Inject OrmModel.Schema gdParamsSchema;

    @Override
    public String getTitle() {
        return "Table Names (Parameter DB)";
    }

    @Override
    public AsciiDoc getContent() {

        val adoc = new AsciiDocBuilder();
        adoc.append(doc->{
            doc.setTitle("Table Names (UI vs Database)");

            var table = table(doc);
            table.setAttribute("cols", "3m,2m", true);

            headCell(table, 0, 0, "UI");
            headCell(table, 0, 1, "Database");

            gdParamsSchema.entities().values()
            .stream()
            .sorted((a, b)->a.name().compareTo(b.name()))
            .forEach(t->{
                var row = row(table);
                cell(table, row, t.name());
                cell(table, row, t.table());
            });
        });

//        // debug table
//        return new AsciiDocBuilder().append(doc->{
//            AsciiDocFactory.sourceBlock(doc, "txt", adoc.buildAsString());
//        })
//        .buildAsValue();

        return adoc.buildAsValue();
    }

}

