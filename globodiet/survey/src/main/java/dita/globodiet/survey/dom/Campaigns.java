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
import org.apache.causeway.core.metamodel.context.MetaModelContext;

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
        NamedPath namedPath(final Campaign campaign) {
            if(campaign==null
                    || _Strings.isNullOrEmpty(campaign.getSurveyCode())
                    || _Strings.isNullOrEmpty(campaign.getCode())) {
                return NamedPath.of("blackhole");
            }
            var root = NamedPath.of("surveys", campaign.getSurveyCode().toLowerCase());
            return switch(this) {
                case INTERVIEW -> root.add("campaigns").add(NamedPath.of(campaign.getCode().toLowerCase()));
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
    public SystemId systemId(
            final Campaign campaign) {
        if(systemIdBySurveyCode==null) {
            systemIdBySurveyCode = new ConcurrentHashMap<>();
        }
        return systemIdBySurveyCode.computeIfAbsent(campaign.getSurveyCode(), surveyCode->{
            return systemId(
                    MetaModelContext.instanceElseFail()
                        .getRepositoryService()
                        .uniqueMatch(Survey.class, survey->surveyCode.equals(survey.getCode()))
                        .orElse(null));
        });
    }

    /**
     * System ID part of semantic identifiers for given survey.
     * e.g. {@code at.gd/2.0}
     */
    public SystemId systemId(final @Nullable Survey survey) {
        return Optional.ofNullable(survey)
            .map(Survey::getSystemId)
            .map(SystemId::parse)
            .orElseGet(()->new SystemId("undefined"));
    }

    // -- INTERVIEW SET

    private InterviewSet24 interviewSet(
            final Campaign campaign,
            final BlobStore blobStore,
            final MessageConsumer messageConsumer) {

        var correction = Correction24.tryFromYaml(_Strings.blankToNullOrTrim(campaign.getCorrection()))
            .valueAsNullableElseFail();

        var interviewSet = InterviewUtils
            .interviewSetFromBlobStore(
                    DataSourceLocation.INTERVIEW.namedPath(campaign),
                    blobStore,
                    systemId(campaign),
                    correction,
                    messageConsumer);

        return interviewSet;
    }

    public InterviewSet24 interviewSet(
            final Campaign campaign,
            final BlobStore blobStore) {
        var messageConsumer = new MessageConsumer();
        var interviewSet = interviewSet(campaign, blobStore, messageConsumer);
        messageConsumer.annotate(interviewSet);
        return interviewSet;
    }

    public InterviewSet24 interviewSet(
            final Can<Campaign> campaigns,
            final BlobStore blobStore) {
        if(campaigns.isEmpty()) {
            return InterviewSet24.empty();
        }
        var messageConsumer = new MessageConsumer();
        var interviewSet = campaigns.stream()
                .map(campaign->Campaigns.interviewSet(campaign, blobStore, messageConsumer))
                .reduce((a, b)->a.join(b, messageConsumer))
                .orElse(null);
        messageConsumer.annotate(interviewSet);
        return interviewSet;
    }

    // -- FCDB

    public FoodCompositionRepository fcdb(
            final Campaign campaign,
            final BlobStore blobStore) {
        var fcdbDataSource = blobStore.lookupBlob(DataSourceLocation.FCDB.namedPath(campaign))
                .orElseThrow()
                .asDataSource();
        var foodCompositionRepo = FoodCompositionRepository.tryFromYaml(SevenZUtils.decompress(fcdbDataSource))
                .valueAsNonNullElseFail();
        return foodCompositionRepo;
    }

    // -- Q-MAP

    public QualifiedMap nutMapping(
            final Campaign campaign,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_NUT, campaign, blobStore);
    }

    public QualifiedMap fcoMapping(
            final Campaign campaign,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_FCO, campaign, blobStore);
    }

    public QualifiedMap pocMapping(
            final Campaign campaign,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_POC, campaign, blobStore);
    }

    public FoodDescriptionModel foodDescriptionModel(
            final Campaign campaign,
            final BlobStore blobStore) {
        var fdmDataSource = blobStore.lookupBlobAndUncompress(DataSourceLocation.FDM.namedPath(campaign))
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
            final Campaign campaign,
            final BlobStore blobStore) {
        var mapDataSource =
                blobStore
                    .lookupBlob(loc.namedPath(campaign))
                    .orElseThrow()
                    .asDataSource();
        switch(loc) {
            case QMAP_NUT ->
                mapDataSource = SevenZUtils.decompress(mapDataSource);
            default -> {}
        }
        return QualifiedMap.tryFromYaml(mapDataSource).valueAsNonNullElseFail();
    }

}
