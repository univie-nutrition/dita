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

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.info.BuildProperties;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.HomePage;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml.InsertMode;
import dita.commons.types.TabularData;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion;
import dita.tooling.orm.OrmModel;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".Dashboard")
@HomePage
public class Dashboard
implements HasCurrentlyCheckedOutVersion {

    @Inject Optional<BuildProperties> buildProperties;
    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("entity2table") TabularData.NameTransformer entity2table;
    @Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;
    @Inject OrmModel.Schema gdParamsSchema;

    @ObjectSupport
    public String title() {
        return "Dashboard";
    }

    @PropertyLayout(named="Software Version", fieldSetName="About", sequence = "1")
    public String getVersion() {
        return buildProperties
                .map(props->props.getVersion())
                .orElse("n/a");
    }

    @PropertyLayout(named="Build Date", fieldSetName="About", sequence = "2")
    public LocalDate getBuildDate() {
        return buildProperties
                    .map(props->LocalDate.ofInstant(props.getTime(), ZoneOffset.UTC))
                    .orElse(null);
    }

    public enum ExportFormat {
        TABLE,
        ENTITY
    }

    @Action(restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(fieldSetName="About", position = Position.PANEL)
    public Clob generateYaml(@Parameter final ExportFormat format) {
        val clob = tableSerializer.clob("gd-params",
                format==ExportFormat.ENTITY
                    ? TabularData.NameTransformer.IDENTITY
                    : entity2table,
                BlobStore.paramsTableFilter());
        return clob;
    }

    @Action(restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(fieldSetName="About", position = Position.PANEL)
    public AsciiDoc loadYaml(
            @Parameter
            @ParameterLayout(named = "tableData")
            final Clob tableData) {

        val adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Table Import Result"));
        adoc.append(doc->{
            val sourceBlock = AsciiDocFactory.sourceBlock(doc, "yml",
                    tableSerializer.load(tableData,
                            table2entity,
                            BlobStore.paramsTableFilter(), InsertMode.DELETE_ALL_THEN_ADD));
            sourceBlock.setTitle("Serialized Table Data (yaml)");
        });

        return adoc.buildAsValue();
    }

    @Action(restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(fieldSetName="About", position = Position.PANEL)
    public AsciiDoc menuLayoutGenerator() {

        val entitiesWithoutRelations =  gdParamsSchema.findEntitiesWithoutRelations();
        val interviewMenu =  gdParamsSchema.entities().values().stream()
                .filter(e->e.namespace().equals("params.interview")).collect(Can.toCan());
        val supplementMenu =  gdParamsSchema.entities().values().stream()
                .filter(e->e.namespace().equals("params.supplement")).collect(Can.toCan());

        val adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Menu Entries"));
        adoc.append(doc->{
            val sourceBlock = AsciiDocFactory.sourceBlock(doc, "xml", String.format(
            """
            <mb:menu>
                <mb:named>Supplements</mb:named>
                <mb:section>
            %s
                </mb:section>
            </mb:menu>
            <mb:menu>
                <mb:named>Interviews</mb:named>
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
            """,
            toServiceActionXmlLayoutEntries(supplementMenu),
            toServiceActionXmlLayoutEntries(interviewMenu),
            toServiceActionXmlLayoutEntries(entitiesWithoutRelations)
            ));
            sourceBlock.setTitle("menu-layout.xml");
        });

        return adoc.buildAsValue();
    }
    private static String toServiceActionXmlLayoutEntries(final Can<OrmModel.Entity> entities) {
        return entities.stream()
                .map(Dashboard::toServiceActionXmlLayoutEntry)
                .collect(Collectors.joining());
    }
    private static String toServiceActionXmlLayoutEntry(final OrmModel.Entity entity) {
        return String.format(
                """
                        <mb:serviceAction objectType="dita.globodiet.params.EntitiesMenu" id="%s"/>""",
                "listAll" + entity.name());
    }

}


