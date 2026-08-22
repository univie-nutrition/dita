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
package dita.globodiet.manager.versions;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.LabelPosition;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.valuetypes.vega.applib.builder.VegaNodeLinkDiagram;
import org.apache.causeway.valuetypes.vega.applib.value.Vega;

import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.FontawesomeConstants;
import dita.globodiet.manager.versions.VersionsService.VersionFilter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".BlobStoreView")
@DomainObjectLayout(
        cssClassFa = FontawesomeConstants.ICON_VERSIONS)
public class VersionsView {

    @Inject VersionsService versionsService;

    @ObjectSupport
    public String title() {
        return "Manage Parameter-Data Versions";
    }

    @PropertyLayout(labelPosition = LabelPosition.NONE)
    public Vega getChangeTree() {
    	return new VegaNodeLinkDiagram(settings->
    			settings
    				.textAngle(-45),
    			getVersions().stream()
    				.map(v->new VegaNodeLinkDiagram.DataPoint(
    						v.id(), "[%d] %s".formatted(v.id(), v.name()), v.parentId()))
    				.toList()
    			)
    	.build();
    }

    @Collection
    public Can<ParameterDataVersion> getVersions() {
        return versionsService.getVersions()
                .filter(VersionFilter.NOT_DELETED);
    }
}
