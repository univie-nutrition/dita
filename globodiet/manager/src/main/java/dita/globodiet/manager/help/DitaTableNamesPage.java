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

import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.asciidoctor.ast.Table;
import org.springframework.stereotype.Component;

import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.metamodel.facets.members.iconfa.FaFacet;
import org.apache.causeway.core.metamodel.facets.members.iconfa.FaLayersProvider;
import org.apache.causeway.extensions.docgen.help.applib.HelpPage;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

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
    @Inject MetaModelContext mmc;

    @Override
    public String getTitle() {
        return "Tables (Parameter DB)";
    }

    @Override
    public AsciiDoc getContent() {
        val adoc = new AsciiDocBuilder();
        var namespaces = gdParamsSchema.entities().values().stream()
            .map(e->e.namespace())
            .collect(Collectors.toCollection(TreeSet::new));

        adoc.append(doc->{
            var section = AsciiDocFactory.section(doc, "Tables (UI vs Database) grouped by Namespace");

            var table = table(section);
            table.setAttribute("cols", "^1a,3a,2m,8m", true);
            headCell(table, 0, 0, "Icon");
            headCell(table, 0, 1, "UI");
            headCell(table, 0, 2, "Database");
            headCell(table, 0, 3, "Description");

            namespaces.forEach(ns->appendNamespaceSection(table, ns));
        });

        return adoc.buildAsValue();
    }

    private void appendNamespaceSection(final Table table, final String namespace) {

        { // section header within table
            var row = row(table);
            cell(table, row, "");
            cell(table, row, namespace.substring("params".length() + 1).toUpperCase());
            cell(table, row, "");
            cell(table, row, "");
        }

        gdParamsSchema.entities().values().stream()
        .filter(e->e.namespace().equalsIgnoreCase(namespace))
        .sorted((a, b)->a.name().compareTo(b.name()))
        .forEach(t->{
            var row = row(table);
            var logicalName = "dita.globodiet."  + t.namespace() + "." + t.name();
            cell(table, row, htmlPassThroughIconFor(logicalName));
            cell(table, row, "`" + t.name() + "`");
            cell(table, row, t.table());
            cell(table, row, t.formatDescription("\n"));
        });

    }

    // -- HELPER

    private String htmlPassThroughIconFor(final String logicalName) {
        return faIcon(logicalName)
                .map(FontAwesomeLayers::toHtml)
                .map(faHtml->String.format("""
                        ++++
                        %s
                        ++++
                        """, faHtml))
                .orElse("");
    }

    private Optional<FontAwesomeLayers> faIcon(final String logicalName) {
        return mmc.getSpecificationLoader().specForLogicalTypeName(logicalName)
                .map(spec->spec.getFacet(FaFacet.class))
                .filter(FaLayersProvider.class::isInstance)
                .map(FaLayersProvider.class::cast)
                .map(FaLayersProvider::getLayers);
    }

}

