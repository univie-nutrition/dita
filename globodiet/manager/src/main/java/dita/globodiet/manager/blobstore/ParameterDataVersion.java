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
package dita.globodiet.manager.blobstore;

import java.io.File;

import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;

import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.FontawesomeConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".ParameterDataVersion")
@DomainObjectLayout(
        cssClassFa = FontawesomeConstants.ICON_BLOBSTORE)
@NoArgsConstructor
public class ParameterDataVersion {

    public static ParameterDataVersion fromDirectory(final @NonNull File dir) {
        val paramDataVersion = new ParameterDataVersion();
        paramDataVersion.setName(dir.getName());
        return paramDataVersion;
    }

    @ObjectSupport
    public String title() {
        return String.format("Parameter-Data [%s]", getName());
    }

    @Property(optionality = Optionality.MANDATORY)
    @Getter @Setter
    private String name;

    @Property
    @PropertyLayout(multiLine = 4)
    @Getter @Setter
    private String description;

    @Action
    @ActionLayout(fieldSetName="Details", position = Position.PANEL)
    public void checkout() {
        //TODO load model from blob-store
        // warn if pending changes
    }

}
