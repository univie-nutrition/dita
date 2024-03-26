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

import jakarta.inject.Inject;

import org.causewaystuff.blobstore.applib.BlobStore;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

import dita.globodiet.survey.view.SurveyVM;

@Action
@ActionLayout(
        fieldSetId = "interviewUploads",
        position = Position.PANEL,
        cssClass = "btn-success",
        cssClassFa = "solid fa-binoculars",
        describedAs = "Inspect the content of uploaded interview files.")
@RequiredArgsConstructor
@ExtensionMethod(Campaigns.class)
public class Campaign_inspectInterviews {

    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    final Campaign mixee;

    @MemberSupport
    public SurveyVM act() {
        return SurveyVM.forRoot(mixee.secondaryKey(), mixee.surveyTreeRootNode(surveyBlobStore));
    }

}
