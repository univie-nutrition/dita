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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

import dita.commons.types.Message;
import dita.globodiet.survey.recall24.InterviewXmlParser;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.model.InterviewSet24;
import dita.recall24.model.Node24;
import dita.recall24.model.corr.Correction24;
import dita.recall24.util.Recall24ModelUtils;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;

@UtilityClass
public class Campaigns {

    public static String ANNOTATION_MESSAGES = "messages";

    public NamedPath namedPath(final Campaign campaign) {
        if(campaign==null
                || _Strings.isNullOrEmpty(campaign.getSurveyCode())
                || _Strings.isNullOrEmpty(campaign.getCode())) {
            return NamedPath.of("blackhole");
        }
        return NamedPath.of(campaign.getSurveyCode().toLowerCase())
                .add(NamedPath.of(campaign.getCode().toLowerCase()));
    }

    public InterviewSet24 interviewSet(
            final Campaign campaign,
            final BlobStore surveyBlobStore) {

        var messageConsumer = new MessageConsumer();

        var correction = Correction24.tryFromYaml(_Strings.blankToNullOrTrim(campaign.getCorrection()))
            .valueAsNullableElseFail();

        //debug
        //messageConsumer.accept(Message.info("generated at %s", LocalDateTime.now()));

        return surveyBlobStore==null
            ? InterviewSet24.empty()
            : InterviewUtils.streamSources(surveyBlobStore, namedPath(campaign), true)
                .map(ds->InterviewXmlParser.parse(ds, messageConsumer))
                .map(Recall24ModelUtils.correct(correction))
                .reduce((a, b)->a.join(b, messageConsumer))
                .map(InterviewSet24::normalized)
                .map(messageConsumer::annotate)
                .orElseGet(InterviewSet24::empty);
    }

    // -- HELPER

    /**
     * First collects {@link Message}(s),
     * then annotates given {@link InterviewSet24} with those.
     */
    private static class MessageConsumer implements Consumer<Message> {

        private final List<Message> messages = new ArrayList<>();

        @Override
        public void accept(final Message message) {
            messages.add(message);
        }

        InterviewSet24 annotate(final InterviewSet24 interviewSet) {
            return messages.isEmpty()
                    ? interviewSet
                    : interviewSet.annotate(toAnnotation());
        }

        private final Node24.Annotation toAnnotation() {
            return new Node24.Annotation(Campaigns.ANNOTATION_MESSAGES, Can.ofCollection(messages));
        }

    }

}
