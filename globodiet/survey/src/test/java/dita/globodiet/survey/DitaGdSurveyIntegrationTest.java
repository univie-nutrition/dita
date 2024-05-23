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
import dita.commons.format.FormatUtils;
import dita.commons.qmap.QualifiedMap;
import dita.commons.types.Message;
import dita.globodiet.survey.recall24.InterviewXmlParser;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.InterviewSet24;
import dita.recall24.model.Node24;
import dita.recall24.util.Recall24ModelUtils;
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

    protected Stream<InterviewSet24> loadAndStreamInterviews(
            final @NonNull NamedPath path,
            final @Nullable Consumer<Message> messageConsumer) {
        return InterviewUtils.streamSources(surveyBlobStore, path, true)
            .map(ds->InterviewXmlParser.parse(ds, messageConsumer));
    }

    protected Stream<InterviewSet24> loadAndStreamInterviews2(
            final @NonNull NamedPath path,
            final @Nullable Consumer<Message> messageConsumer) {
        return InterviewUtils.streamSources(surveyBlobStore, path, true)
            .map(ds->InterviewXmlParser.parse(ds, messageConsumer));
    }

    /**
     * Converts ingredient identifiers to NutriDb (prefixed) identifiers.
     */
    protected UnaryOperator<InterviewSet24> nutriDbTransfomer(){

        record NutriDbTransfomer() implements UnaryOperator<Node24> {
            @Override public Node24 apply(final Node24 node) {
                return switch (node) {
                case Ingredient24 ingr -> toNutriDbPrefixes(ingr);
                default -> node;
                };
            }
            private Ingredient24 toNutriDbPrefixes(final Ingredient24 ingr) {
                return switch (ingr.parentRecord().type()) {
                case FOOD -> ingr.withSid("N" + FormatUtils.noLeadingZeros(ingr.sid()));
                default -> ingr;
                };
            }
        }

        return Recall24ModelUtils.transform(new NutriDbTransfomer());
    }

}
