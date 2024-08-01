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

import io.github.causewaystuff.companion.codegen.model.Schema;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.info.BuildProperties;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.HomePage;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.PropertyLayout;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.globodiet.manager.DitaModuleGdManager;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".Dashboard")
@DomainObjectLayout(cssClassFa = "solid gauge .dashboard-color")
@HomePage
public class Dashboard {

    @Inject Optional<BuildProperties> buildProperties;
    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("entity2table") TabularData.NameTransformer entity2table;
    @Inject @Qualifier("table2entity") TabularData.NameTransformer table2entity;
    @Inject Schema.Domain gdParamsSchema;

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

}


