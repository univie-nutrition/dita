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
import java.util.List;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Introspection;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.core.metamodel.context.MetaModelContext;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

@DomainObject(introspection = Introspection.ANNOTATION_REQUIRED)
@DomainObjectLayout(
        describedAs = "Interview correction step that contains multiple YAML files.",
        cssClassFa = "solid users-viewfinder .campaign-color,\n"
                + "solid broom .consumptionDataCleaner-color .ov-size-60 .ov-right-55 .ov-bottom-55"
)
public record CorrectionStep(
        @Programmatic
        Survey.SecondaryKey surveyKey,
        @PropertyLayout(sequence = "1")
        int stepOrdinal,
        @Collection
        List<CorrectionUpload> correctionUploads
        ) implements Serializable {

    @ObjectSupport
    public String title() {
        return "Step " + stepOrdinal;
    }

    @PropertyLayout(sequence = "2")
    public int getCorrectionFileCount() {
        return correctionUploads.size();
    }

    @Property(
            snapshot = Snapshot.EXCLUDED)
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT)
    public Survey getNavigableParent() {
        return MetaModelContext.instance()
           .flatMap(mmc->mmc.lookupService(ForeignKeyLookupService.class))
           .map(foreignKeyLookupService->foreignKeyLookupService.unique(surveyKey()))
           .orElse(null);
    }

}
