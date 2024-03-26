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

import java.util.List;

import jakarta.inject.Inject;

import org.causewaystuff.blobstore.applib.BlobDescriptor;
import org.causewaystuff.blobstore.applib.BlobStore;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

@Collection
@RequiredArgsConstructor
@ExtensionMethod(Campaigns.class)
public class Campaign_interviewUploads {

    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;
    //private InterviewXmlParser parser = new InterviewXmlParser();

    private final Campaign mixee;

    @MemberSupport
    public List<InterviewUpload> coll() {
        return surveyBlobStore.listDescriptors(mixee.namedPath(), true)
                .stream()
                .filter(desc->desc.mimeType().equals(CommonMimeType.XML))
                .map(BlobDescriptor::path)
                .map(namedPath->namedPath.toString("/"))
                .map(InterviewUpload::new)
                .toList();
    }

}