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

import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.types.Message;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.dto.Annotated;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;

@UtilityClass
public class Campaigns {

    public static String ANNOTATION_MESSAGES = "messages";

    public Survey.SecondaryKey surveySecondaryKey(final Campaign.SecondaryKey campaignKey) {
        return new Survey.SecondaryKey(campaignKey.surveyCode());
    }

    NamedPath interviewNamedPath(final Campaign.SecondaryKey campaignKey) {
        if(campaignKey==null
                || _Strings.isNullOrEmpty(campaignKey.surveyCode())
                || _Strings.isNullOrEmpty(campaignKey.code())) {
            return NamedPath.of("blackhole");
        }
        var root = NamedPath.of("surveys", campaignKey.surveyCode().toLowerCase());
        return root.add("campaigns").add(NamedPath.of(campaignKey.code().toLowerCase()));
    }

    // -- INTERVIEW SET

    /**
     * Returns interview-set from a single campaign. Just corrected, not prepared.
     */
    public InterviewSet24 interviewSetCorrected(
            final SystemId systemId,
            final Campaign.SecondaryKey campaignKey,
            final Correction24 correction,
            final BlobStore blobStore) {
        var messageConsumer = new MessageConsumer();
        var interviewSet = interviewSet(systemId, campaignKey, blobStore, correction, messageConsumer);
        messageConsumer.annotate(interviewSet);
        return interviewSet;
    }

    /**
     * Returns interview-set from a multiple campaigns. Just corrected, not prepared.
     */
    public InterviewSet24 interviewSetCorrected(
            final SystemId systemId,
            final Can<Campaign.SecondaryKey> campaignKeys,
            final Correction24 correction,
            final BlobStore blobStore) {
        var messageConsumer = new MessageConsumer();
        var interviewSet = campaignKeys.stream()
                .map(campaignKey->Campaigns.interviewSet(systemId, campaignKey, blobStore, correction, messageConsumer))
                .reduce((a, b)->a.join(b, messageConsumer))
                .orElseGet(InterviewSet24::empty);
        messageConsumer.annotate(interviewSet);
        return interviewSet;
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

        @Override
        public String toString() {
            return messages.toString();
        }

        InterviewSet24 annotate(final InterviewSet24 interviewSet) {
            return messages.isEmpty()
                    ? interviewSet
                    : interviewSet.annotate(toAnnotation());
        }

        private final Annotated.Annotation toAnnotation() {
            return new Annotated.Annotation(Campaigns.ANNOTATION_MESSAGES, Can.ofCollection(messages));
        }

    }

    private InterviewSet24 interviewSet(
            final SystemId systemId,
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore,
            final Correction24 correction,
            final MessageConsumer messageConsumer) {

        var interviewSet = InterviewUtils
            .interviewSetFromBlobStore(
                interviewNamedPath(campaignKey),
                blobStore,
                systemId,
                correction,
                messageConsumer);

        return interviewSet;
    }

}
