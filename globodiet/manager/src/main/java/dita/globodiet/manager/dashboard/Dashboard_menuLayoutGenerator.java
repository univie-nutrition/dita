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
package dita.globodiet.manager.dashboard;

import java.util.stream.Collectors;

import jakarta.inject.Inject;

import io.github.causewaystuff.companion.codegen.model.Schema;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import lombok.RequiredArgsConstructor;

import dita.globodiet.manager.metadata.EntitySchemaProvider;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_menuLayoutGenerator {

    @Inject EntitySchemaProvider entitySchemaProvider;

    final Dashboard dashboard;

    @MemberSupport
    public AsciiDoc act() {

        var gdParamsSchema = entitySchemaProvider.asDomain();
        var entitiesWithoutRelations =  gdParamsSchema.findEntitiesWithoutRelations();
        var interviewMenu =  gdParamsSchema.entities().values().stream()
                .filter(e->e.namespace().equals("params.interview"))
                .collect(Can.toCan());
        var supplementMenu =  gdParamsSchema.entities().values().stream()
                .filter(e->e.namespace().equals("params.supplement"))
                .collect(Can.toCan());
        //TODO needs 2 named sections: Food and Recipe
        var pathwayMenu =  gdParamsSchema.entities().values().stream()
                .filter(e->e.name().contains("Pathway"))
                .sorted((a, b)->a.name().compareTo(b.name()))
                .collect(Can.toCan());
        var allManagersMenu =  gdParamsSchema.entities().values().stream()
                .sorted((a, b)->a.name().compareTo(b.name()))
                .collect(Can.toCan());

        var adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Menu Entries"));
        adoc.append(doc->{
            var sourceBlock = AsciiDocFactory.sourceBlock(doc, "xml", String.format(
            """
            <mb:menu>
                <mb:named>Interviews</mb:named>
                <mb:section>
                    %s
                </mb:section>
            </mb:menu>
            <mb:menu>
                <mb:named>Pathways</mb:named>
                <mb:section>
                    %s
                </mb:section>
            </mb:menu>
            <mb:menu>
                <mb:named>Supplements</mb:named>
                <mb:section>
                    %s
                </mb:section>
            </mb:menu>
            <mb:menu>
                <mb:named>Having no Relations</mb:named>
                <mb:section>
                    %s
                </mb:section>
            </mb:menu>
            <mb:menu>
                <mb:named>All Managers</mb:named>
                <mb:section>
                    %s
                </mb:section>
            </mb:menu>
            """,
            toServiceActionXmlLayoutEntries(interviewMenu),
            toServiceActionXmlLayoutEntries(pathwayMenu),
            toServiceActionXmlLayoutEntries(supplementMenu),
            toServiceActionXmlLayoutEntries(entitiesWithoutRelations),
            toServiceActionXmlLayoutEntries(allManagersMenu)
            ));
            sourceBlock.setTitle("menu-layout.xml");
        });

        return adoc.buildAsValue();
    }

    // -- HELPER

    private static String toServiceActionXmlLayoutEntries(final Can<Schema.Entity> entities) {
        return entities.stream()
                .map(Dashboard_menuLayoutGenerator::toServiceActionXmlLayoutEntry)
                .collect(Collectors.joining("\n        "));
    }

    private static String toServiceActionXmlLayoutEntry(final Schema.Entity entity) {
        return String.format(
                """
                <mb:serviceAction objectType="dita.globodiet.params.EntitiesMenu" id="%s"/>""",
                "manage" + entity.name());
    }

}
