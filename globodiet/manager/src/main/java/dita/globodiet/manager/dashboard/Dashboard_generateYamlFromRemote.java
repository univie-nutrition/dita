/*
 *  	Licensed to the Apache Software Foundation (ASF) under one
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

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.value.Clob;

import dita.causeway.replicator.tables.model.DataTableService;
import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.globodiet.manager.blobstore.BlobStore;
import lombok.RequiredArgsConstructor;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_generateYamlFromRemote {

    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("entity2table") TabularData.NameTransformer entity2table;
    @Inject DataTableService dataTableService;

    final Dashboard dashboard;

    public enum Profile {
        SQLSERVER,
        MARIALIVE
    }

    public enum ExportFormat {
        TABLE,
        ENTITY
    }

    @MemberSupport
    public Clob act(
            @Parameter final Profile profile,
            @Parameter final ExportFormat format,
            @Parameter final boolean rowSortingEnabled) {

        return new SecondaryDataStore(dataTableService)
            .createPersistenceManagerFactory(profile.name())
            .map(pmf->{
                var pm = pmf.getPersistenceManager();
                try {
                    var transformer = format==ExportFormat.ENTITY
                            ? TabularData.NameTransformer.IDENTITY
                            : entity2table;

                    var clob = tableSerializer.clobFromSecondaryConnection(
                            "gd-params-" + profile.name().toLowerCase(),
                            transformer,
                            BlobStore.paramsTableFilter(),
                            pm,
                            rowSortingEnabled);

                    return clob;

                } finally {
                    pm.close();
                    pmf.close();
                }
            })
            .orElseThrow();
    }

    public Profile defaultProfile() {
        return Profile.SQLSERVER;
    }

    public ExportFormat defaultFormat() {
        return ExportFormat.TABLE;
    }

}
