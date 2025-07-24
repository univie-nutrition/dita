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

import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import dita.commons.util.FormatUtils;
import io.github.causewaystuff.blobstore.applib.BlobDescriptor;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;

@DomainObjectLayout(
        describedAs = "Interview correction YAML file.",
        cssClassFa = "solid users-viewfinder .campaign-color,\n"
                + "solid broom .consumptionDataCleaner-color .ov-size-60 .ov-right-55 .ov-bottom-55"
)
public record CorrectionUpload(
        @PropertyLayout(hidden = Where.EVERYWHERE)
        NamedPath namedPath,
        @PropertyLayout(sequence = "2")
        String sha256,
        @PropertyLayout(hidden = Where.EVERYWHERE)
        Survey.SecondaryKey surveyKey
        ) implements Serializable {

    static CorrectionUpload of(final BlobDescriptor blobDescriptor, final Survey.SecondaryKey surveyKey) {
        return new CorrectionUpload(
                blobDescriptor.path(),
                blobDescriptor.attributes().getOrDefault("sha256", "n/a"),
                surveyKey);
    }

    @ObjectSupport
    public String title() {
        return getPath();
    }

    @PropertyLayout(sequence = "1")
    public String getPath() {
        return namedPath.subPath(3).toString("/");
    }

    @PropertyLayout(sequence = "3", hidden = Where.ALL_TABLES)
    public AsciiDoc getContent() {
        return FormatUtils.adocSourceBlock("yaml", readYamlContent());
    }

    @Programmatic
    public String readYamlContent() {
        var surveyBlobStore = MetaModelContext.instanceElseFail().getServiceInjector().injectServicesInto(new BlobStoreHolder())
                .surveyBlobStore;
        var client = new BlobStoreClient(surveyKey, surveyBlobStore);
        return client.correctionYaml(this);
    }

    static class BlobStoreHolder {
        @Inject @Qualifier("survey") BlobStore surveyBlobStore;
    }

}
