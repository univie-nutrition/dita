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

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.RestrictTo;
import org.apache.causeway.applib.value.Clob;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.commons.types.TabularData;
import dita.globodiet.manager.blobstore.BlobStore;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Action(restrictTo = RestrictTo.PROTOTYPING)
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_generateYaml {

    @Inject TableSerializerYaml tableSerializer;
    @Inject @Qualifier("entity2table") TabularData.NameTransformer entity2table;

    final Dashboard dashboard;

    public enum ExportFormat {
        TABLE,
        ENTITY
    }

    @MemberSupport
    public Clob act(
            @Parameter final ExportFormat format) {
        val clob = tableSerializer.clob("gd-params",
                format==ExportFormat.ENTITY
                    ? TabularData.NameTransformer.IDENTITY
                    : entity2table,
                BlobStore.paramsTableFilter());
        return clob;
    }

}
