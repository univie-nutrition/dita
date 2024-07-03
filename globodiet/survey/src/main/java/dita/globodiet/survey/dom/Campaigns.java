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

import dita.commons.qmap.QualifiedMap;
import dita.commons.types.Message;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.RecallNode24;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.commons.compression.SevenZUtils;

@UtilityClass
public class Campaigns {

    public static String ANNOTATION_MESSAGES = "messages";

    enum DataSourceLocation {
        INTERVIEW,
        FCDB,
        QMAP;
        NamedPath namedPath(final Campaign campaign) {
            if(campaign==null
                    || _Strings.isNullOrEmpty(campaign.getSurveyCode())
                    || _Strings.isNullOrEmpty(campaign.getCode())) {
                return NamedPath.of("blackhole");
            }
            var root = NamedPath.of(campaign.getSurveyCode().toLowerCase());
            return switch(this) {
                case INTERVIEW -> root.add("campaigns").add(NamedPath.of(campaign.getCode().toLowerCase()));
                case FCDB -> root.add("fcdb").add("fcdb.yaml.7z");
                case QMAP -> root.add("qmap").add("qmap.yaml.7z");
            };
        }
    }

    // -- INTERVIEW SET

    public InterviewSet24.Dto interviewSet(
            final Campaign campaign,
            final BlobStore blobStore) {

        var messageConsumer = new MessageConsumer();

        var correction = Correction24.tryFromYaml(_Strings.blankToNullOrTrim(campaign.getCorrection()))
            .valueAsNullableElseFail();

        var interviewSet = InterviewUtils
            .interviewSetFromBlobStrore(
                    DataSourceLocation.INTERVIEW.namedPath(campaign),
                    blobStore,
                    correction,
                    messageConsumer);

        //debug
        //messageConsumer.accept(Message.info("generated at %s", LocalDateTime.now()));

        messageConsumer.annotate(interviewSet);

        return interviewSet;
    }

    // -- NUT MAPPING (Q-MAP)

    public QualifiedMap nutMapping(
            final Campaign campaign,
            final BlobStore blobStore) {

        var mapDataSource = SevenZUtils.decompress(
                blobStore
                    .lookupBlob(DataSourceLocation.QMAP.namedPath(campaign))
                    .orElseThrow()
                    .asDataSource());

        var qMap = QualifiedMap.tryFromYaml(mapDataSource)
            .valueAsNonNullElseFail();

        return qMap;
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

        InterviewSet24.Dto annotate(final InterviewSet24.Dto interviewSet) {
            return messages.isEmpty()
                    ? interviewSet
                    : interviewSet.annotate(toAnnotation());
        }

        private final RecallNode24.Annotation toAnnotation() {
            return new RecallNode24.Annotation(Campaigns.ANNOTATION_MESSAGES, Can.ofCollection(messages));
        }

    }

}
