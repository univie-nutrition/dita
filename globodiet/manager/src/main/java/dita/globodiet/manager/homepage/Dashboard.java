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
package dita.globodiet.manager.homepage;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.function.Predicate;

import jakarta.inject.Inject;
import jakarta.inject.Named;

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
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.tooling.model4adoc.AsciiDocFactory;
import org.apache.causeway.tooling.model4adoc.AsciiDocWriter;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.globodiet.manager.DitaModuleGdManager;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".Dashboard")
@HomePage
public class Dashboard {

    @Inject Optional<BuildProperties> buildProperties;
    @Inject TableSerializerYaml tableSerializer;

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

    @Action(restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(fieldSetName="About", position = Position.PANEL)
    public Clob generateYaml() {
        val clob = tableSerializer.clob("gd-params", tableFilter());
        return clob;
    }

    @Action(restrictTo = RestrictTo.PROTOTYPING)
    @ActionLayout(fieldSetName="About", position = Position.PANEL)
    public AsciiDoc loadYaml(
            @Parameter
            @ParameterLayout(named = "tableData")
            final Clob tableData) {

        val doc = AsciiDocFactory.doc();
        doc.setTitle("Table Import Result");

        val sourceBlock = AsciiDocFactory.sourceBlock(doc, "yml",
                tableSerializer.load(tableData, tableFilter()));
        sourceBlock.setTitle("Serialized Table Data (yaml)");

        return new AsciiDoc(AsciiDocWriter.toString(doc));
    }

    private Predicate<ObjectSpecification> tableFilter() {
        return entityType->entityType.getLogicalTypeName()
                .startsWith("dita.globodiet.params.");
    }

}


