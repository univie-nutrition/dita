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
package dita.globodiet.survey;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;

import org.apache.causeway.testing.integtestsupport.applib.CausewayIntegrationTestAbstract;

import lombok.NonNull;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.qmap.QualifiedMap;
import dita.commons.types.Message;
import dita.globodiet.survey.recall24.InterviewXmlParser;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.api.InterviewSet24;
import dita.recall24.api.RecallNode24;
import dita.recall24.api.RecallNode24.Builder24;
import dita.recall24.api.Record24;
import dita.recall24.util.Recall24DtoUtils;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.commons.compression.SevenZUtils;

public abstract class DitaGdSurveyIntegrationTest
extends CausewayIntegrationTestAbstract {

    @Inject @Qualifier("survey") protected BlobStore surveyBlobStore;

    protected FoodCompositionRepository loadFcdb() {
        var fcdbDataSource = SevenZUtils.decompress(
                surveyBlobStore.lookupBlob(NamedPath.of("fcdb", "fcdb.yaml.7z")).orElseThrow().asDataSource());

        var foodCompositionRepo = FoodCompositionRepository.tryFromYaml(fcdbDataSource)
            .valueAsNonNullElseFail();

        return foodCompositionRepo;
    }

    protected QualifiedMap loadNutMapping() {
        var mapDataSource = SevenZUtils.decompress(
                surveyBlobStore.lookupBlob(NamedPath.of("qmap", "qmap.yaml.7z")).orElseThrow().asDataSource());

        var qMap = QualifiedMap.tryFromYaml(mapDataSource)
            .valueAsNonNullElseFail();

        return qMap;
    }

    protected Stream<InterviewSet24.Dto> loadAndStreamInterviews(
            final @NonNull NamedPath path,
            final @Nullable Consumer<Message> messageConsumer) {
        return InterviewUtils.streamSources(surveyBlobStore, path, true)
            .map(ds->InterviewXmlParser.parse(ds, messageConsumer));
    }

    /**
     * Converts ingredient identifiers to NutriDb (prefixed) identifiers.
     */
    protected UnaryOperator<InterviewSet24.Dto> nutriDbTransfomer(){

        record NutriDbTransfomer() implements RecallNode24.Transfomer {

            @Override
            public void accept(final Builder24<?> builder) {
                switch (builder) {
                case Record24.Builder recBuilder -> toNutriDbPrefixes(recBuilder);
                default -> {}
                };
            }
            private void toNutriDbPrefixes(final Record24.Builder recBuilder) {

//FIXME[23]
//                return switch (ingr.parentRecord().type()) {
//                case FOOD -> ingr.withSid("N" + FormatUtils.noLeadingZeros(ingr.sid()));
//                default -> ingr;
//                };
            }
        }

        return Recall24DtoUtils.transform(new NutriDbTransfomer());
    }

}
