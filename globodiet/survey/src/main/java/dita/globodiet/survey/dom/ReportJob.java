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
package dita.globodiet.survey.dom;

import java.io.Serializable;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.core.metamodel.context.MetaModelContext;

import lombok.RequiredArgsConstructor;

import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

@DomainObjectLayout(
        describedAs = "Report Jobs are execetuded in background to typically run a few minutes."
                + " On completion they change their state from RUNNING to DONE and "
                + "the finisished report becomes available for download.",
        cssClassFa = "solid spinner .job-running-color"
)
public record ReportJob(
        @PropertyLayout(hidden = Where.EVERYWHERE)
        NamedPath namedPath,
        @PropertyLayout(hidden = Where.EVERYWHERE)
        Survey.SecondaryKey surveyKey
        ) implements Serializable {

    @RequiredArgsConstructor
    public enum JobState {
        CANCELLED("regular circle-xmark .job-cancelled-color"),
        RUNNING("solid spinner .job-running-color"),
        DONE("regular circle-check .job-done-color");
        final String fa;
    }

    @ObjectSupport
    public String title() {
        return "%s [%s]".formatted(getPath(), getState());
    }

    @ObjectSupport
    public ObjectSupport.IconResource icon(final ObjectSupport.IconWhere iconWhere) {
        return new ObjectSupport.FontAwesomeIconResource(FontAwesomeLayers.fromQuickNotation(getState().fa));
    }

    @Property(snapshot = Snapshot.EXCLUDED)
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT)
    public Survey getNavigableParent() {
        return servicesHolder().foreignKeyLookup.unique(surveyKey);
    }

    @PropertyLayout(sequence = "1")
    public String getPath() {
        return namedPath.subPath(3).toString("/");
    }

    @PropertyLayout(sequence = "2")
    public JobState getState() {
        return client().jobState(this);
    }

    @Action
    public Blob download() {
        return client().download(this);
    }
    @MemberSupport
    public String disableDownload() {
        var state = getState();
        return getState() != JobState.DONE
            ? "Report job is " + state
            : null;
    }

    // -- HELPER

    private ServicesHolder servicesHolder() {
        return MetaModelContext.instanceElseFail()
            .getServiceInjector().injectServicesInto(new ServicesHolder());
    }

    private BlobStoreClient client() {
        return new BlobStoreClient(surveyKey, servicesHolder().surveyBlobStore);
    }

    static class ServicesHolder {
        @Inject @Qualifier("survey") BlobStore surveyBlobStore;
        @Inject ForeignKeyLookupService foreignKeyLookup;
    }

}
