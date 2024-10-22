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
package dita.globodiet.survey.view;

import java.util.Optional;

import org.apache.causeway.commons.collections.Can;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import dita.commons.types.Message;
import dita.commons.types.Message.Severity;
import dita.globodiet.survey.dom.Campaigns;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Respondent24;

@UtilityClass
class DataUtil {

    Can<Message> messages(
            @NonNull final InterviewSet24 interviewSet){
        final Can<Message> messages = interviewSet.annotation(Campaigns.ANNOTATION_MESSAGES)
                .map(RecallNode24.Annotation.valueAsCan(Message.class))
                .orElseGet(Can::empty);
        return messages;
    }

    /**
     * Considering ERROR and WARNING.
     */
    Optional<Severity> findHighestMessageSeverityForRespondent(
            @NonNull final Respondent24 respondent,
            @NonNull final Can<Message> messages) {
        return messages.stream()
            .filter(message->message.severity().ordinal()<=Severity.WARNING.ordinal())
            .filter(message->message.text().contains(respondent.alias()))
            .map(Message::severity)
            .reduce((a, b)->a.ordinal()<b.ordinal() ? a : b);
    }

}
