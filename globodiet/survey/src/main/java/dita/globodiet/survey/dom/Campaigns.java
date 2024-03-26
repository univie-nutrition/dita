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

import org.causewaystuff.blobstore.applib.BlobStore;
import org.causewaystuff.commons.base.types.NamedPath;

import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

import dita.globodiet.survey.util.InterviewUtils;
import dita.globodiet.survey.view.SurveyTreeNode;
import dita.globodiet.survey.view.SurveyTreeNodeFactory;
import dita.recall24.model.InterviewSet24;

@UtilityClass
public class Campaigns {

    public NamedPath namedPath(final Campaign campaign) {
        if(campaign==null
                || _Strings.isNullOrEmpty(campaign.getCode())) {
            return NamedPath.of("blackhole");
        }
        return NamedPath.of(campaign.getCode().toLowerCase());
    }

    public SurveyTreeNode surveyTreeRootNode(final Campaign campaign, final BlobStore surveyBlobStore) {

        if(surveyBlobStore==null) {
            return SurveyTreeNodeFactory.emptyNode();
        }

        return InterviewUtils.streamSources(surveyBlobStore, namedPath(campaign), true)
            .map(InterviewUtils::parse)
            .reduce(InterviewSet24::join)
            .map(InterviewSet24::normalized)
            .map(ivSet24->SurveyTreeNodeFactory.surveyNode(ivSet24, campaign))
            .orElseGet(SurveyTreeNodeFactory::emptyNode);
    }

}
