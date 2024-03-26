/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.survey.dom;

import java.time.Instant;
import java.util.Map;

import jakarta.inject.Inject;

import org.causewaystuff.blobstore.applib.BlobDescriptor;
import org.causewaystuff.blobstore.applib.BlobDescriptor.Compression;
import org.causewaystuff.blobstore.applib.BlobStore;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.exceptions.RecoverableException;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.core.metamodel.context.MetaModelContext;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

import dita.commons.util.BlobUtils;
import dita.globodiet.survey.view.SurveyTreeRootNodeHelperService;

@Action(
        semantics = SemanticsOf.IDEMPOTENT
)
@ActionLayout(
        fieldSetId = "interviewUploads",
        sequence = "1",
        describedAs = "Uploads an interview XML file.",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
@ExtensionMethod({Campaigns.class, BlobUtils.class})
public class Campaign_uploadInterview {

    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;
    @Inject private SurveyTreeRootNodeHelperService surveyTreeRootNodeHelperService;

    //private InterviewXmlParser parser = new InterviewXmlParser();

    private final Campaign mixee;

    @MemberSupport
    public Campaign act(
            @Parameter(fileAccept = ".xml,.zip")
            @ParameterLayout(
                    describedAs = "Either a single interview xml file or multiple provided as a zip.")
            final Blob interviewFileOrFiles) {

        if(interviewFileOrFiles.isZipped()) {
            interviewFileOrFiles.unzipAsBlobStream(CommonMimeType.XML)
                .forEach(this::uploadToBlobStore);
        } else if(interviewFileOrFiles.isXml()) {
            uploadToBlobStore(interviewFileOrFiles);
        } else {
            throw new RecoverableException(String.format("unsupported mime %s%n",
                    interviewFileOrFiles.getMimeType().toString()));
        }
        surveyTreeRootNodeHelperService.invalidateCache(mixee.secondaryKey());
        return mixee;
    }

    // -- HELPER

    private void uploadToBlobStore(final Blob xmlBlob) {
        //TODO remove debug
        System.err.printf("upload %s (%s)%n", xmlBlob.getName(), xmlBlob.sha256Hex());

        var zippedBlob = xmlBlob.zip();

        var createdBy = MetaModelContext.instanceElseFail().getInteractionService().currentInteractionContextElseFail()
                .getUser().getName();
        var blobDescriptor = new BlobDescriptor(
                mixee.namedPath().add(xmlBlob.getName()),
                CommonMimeType.XML,
                createdBy,
                Instant.now(),
                (long)zippedBlob.getBytes().length,
                Compression.ZIP,
                Map.of("uncompressed-size", "" + xmlBlob.getBytes().length,
                        "sha256", "" + xmlBlob.sha256Hex()));
        surveyBlobStore.putBlob(blobDescriptor, zippedBlob);
    }

}
