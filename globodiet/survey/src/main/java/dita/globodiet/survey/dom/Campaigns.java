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
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.context._Context;

import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.qmap.QualifiedMap;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.types.Message;
import dita.foodon.fdm.FdmUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.RecallNode24;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.commons.base.util.RuntimeUtils;
import io.github.causewaystuff.commons.compression.SevenZUtils;

@UtilityClass
public class Campaigns {

    public static String ANNOTATION_MESSAGES = "messages";

    enum DataSourceLocation {
        INTERVIEW,
        FCDB,
        QMAP_NUT,
        QMAP_FCO,
        QMAP_POC,
        FDM;
        NamedPath namedPath(final Campaign.SecondaryKey campaignKey) {
            if(campaignKey==null
                    || _Strings.isNullOrEmpty(campaignKey.surveyCode())
                    || _Strings.isNullOrEmpty(campaignKey.code())) {
                return NamedPath.of("blackhole");
            }
            var root = NamedPath.of("surveys", campaignKey.surveyCode().toLowerCase());
            return switch(this) {
                case INTERVIEW -> root.add("campaigns").add(NamedPath.of(campaignKey.code().toLowerCase()));
                case FCDB -> root.add("fcdb").add("fcdb.yaml");
                case QMAP_NUT -> root.add("qmap").add("nut.yaml");
                case QMAP_FCO -> root.add("qmap").add("fco.yaml");
                case QMAP_POC -> root.add("qmap").add("poc.yaml");
                case FDM -> root.add("fdm").add("fdm.yaml");
            };
        }
    }

    // -- SYSTEM ID

    private Map<String, SystemId> systemIdBySurveyCode;

    /**
     * System ID part of semantic identifiers for given campaign's survey.
     * e.g. {@code at.gd/2.0}
     */
    public SystemId systemId(final Campaign.SecondaryKey campaignKey) {
        if(systemIdBySurveyCode==null) {
            systemIdBySurveyCode = new ConcurrentHashMap<>();
        }
        return systemIdBySurveyCode.computeIfAbsent(campaignKey.surveyCode(), _->systemId(survey(campaignKey)));
    }

    /**
     * System ID part of semantic identifiers for given survey.
     * e.g. {@code at.gd/2.0}
     */
    public SystemId systemId(@Nullable final Survey survey) {
        return Optional.ofNullable(survey)
            .map(Survey::getSystemId)
            .map(SystemId::parse)
            .orElseGet(()->new SystemId("undefined"));
    }

    // -- CORRECTION

    public Correction24 correction(final Campaign.SecondaryKey campaignKey) {
        return DataUtil.correction(survey(campaignKey).getCorrection());
    }
    private Survey survey(final Campaign.SecondaryKey campaignKey) {
        var repo = RuntimeUtils.getRepositoryService();
        var survey = repo.firstMatch(Survey.class, s->campaignKey.surveyCode().equals(s.getCode()));
        return survey
            // JUnit support
            .orElseGet(()->_Context.lookup(Survey.class).orElse(null));
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

    // -- FCDB

    public FoodCompositionRepository fcdb(
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore) {
        var fcdbDataSource = blobStore.lookupBlob(DataSourceLocation.FCDB.namedPath(campaignKey))
                .orElseThrow()
                .asDataSource();
        var foodCompositionRepo = FoodCompositionRepository.tryFromYaml(SevenZUtils.decompress(fcdbDataSource))
                .valueAsNonNullElseFail();
        return foodCompositionRepo;
    }

    // -- Q-MAP

    public QualifiedMap nutMapping(
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_NUT, campaignKey, blobStore);
    }

    public QualifiedMap fcoMapping(
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_FCO, campaignKey, blobStore);
    }

    public QualifiedMap pocMapping(
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_POC, campaignKey, blobStore);
    }

    public FoodDescriptionModel foodDescriptionModel(
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore) {
        var fdmDataSource = blobStore.lookupBlobAndUncompress(DataSourceLocation.FDM.namedPath(campaignKey))
                .orElseThrow()
                .asDataSource();
        return FdmUtils.fromYaml(fdmDataSource);
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

        private final RecallNode24.Annotation toAnnotation() {
            return new RecallNode24.Annotation(Campaigns.ANNOTATION_MESSAGES, Can.ofCollection(messages));
        }

    }

    private QualifiedMap loadQmap(
            final DataSourceLocation loc,
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore) {
        var mapDataSource =
                blobStore
                    .lookupBlob(loc.namedPath(campaignKey))
                    .orElseThrow()
                    .asDataSource();
        switch(loc) {
            case QMAP_NUT ->
                mapDataSource = SevenZUtils.decompress(mapDataSource);
            default -> {}
        }
        return QualifiedMap.tryFromYaml(mapDataSource).valueAsNonNullElseFail();
    }

    private InterviewSet24 interviewSet(
            final SystemId systemId,
            final Campaign.SecondaryKey campaignKey,
            final BlobStore blobStore,
            final Correction24 correction,
            final MessageConsumer messageConsumer) {

        var interviewSet = InterviewUtils
            .interviewSetFromBlobStore(
                    DataSourceLocation.INTERVIEW.namedPath(campaignKey),
                    blobStore,
                    systemId,
                    correction,
                    messageConsumer);

        return interviewSet;
    }

}
