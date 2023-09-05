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
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocWriter;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml.InsertMode;
import dita.commons.types.tabular.DataBase;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".Dashboard")
@HomePage
public class Dashboard
implements HasCurrentlyCheckedOutVersion {

    @Inject Optional<BuildProperties> buildProperties;
    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("entity2table") DataBase.NameTransformer entity2table;
    @Inject @Qualifier("table2entity") DataBase.NameTransformer table2entity;

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
        val clob = tableSerializer.clob("gd-params", null, BlobStore.paramsTableFilter());
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
                tableSerializer.load(tableData,
                        table2entity,
                        BlobStore.paramsTableFilter(), InsertMode.DELETE_ALL_THEN_ADD));
        sourceBlock.setTitle("Serialized Table Data (yaml)");

        return new AsciiDoc(AsciiDocWriter.toString(doc));
    }

}


