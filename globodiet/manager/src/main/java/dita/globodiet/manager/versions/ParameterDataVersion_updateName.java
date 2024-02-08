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

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;

import lombok.RequiredArgsConstructor;

@Action
@ActionLayout(associateWith = "name")
@RequiredArgsConstructor
public class ParameterDataVersion_updateName {

    @Inject VersionsService blobStore;

    final ParameterDataVersion version;

    @MemberSupport
    public ParameterDataVersion act(
            @Parameter
            final String name) {

        version.setName(name);
        blobStore.writeManifest(version);
        return version;
    }

    @MemberSupport public String disable() {
        return version.guardAgainstDeleted() // just in case
                .or(()->version.guardAgainstSticky("This version is marked STICKY by an administrator, hence cannot be edited."))
                .orElse(null);
    }

    @MemberSupport public String defaultName() {
        return version.getName();
    }

}
