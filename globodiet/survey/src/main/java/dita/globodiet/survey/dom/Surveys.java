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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.context._Context;

import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.qmap.QualifiedMap;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.foodon.fdm.FdmUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.recall24.dto.Correction24;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.commons.base.util.RuntimeUtils;
import io.github.causewaystuff.commons.compression.SevenZUtils;

@UtilityClass
public class Surveys {

    enum DataSourceLocation {
        FCDB,
        QMAP_NUT,
        QMAP_FCO,
        QMAP_POC,
        QMAP_SDAY,
        QMAP_SDIET,
        FDM;
        NamedPath namedPath(final Survey.SecondaryKey surveyKey) {
            if(surveyKey==null
                    || _Strings.isNullOrEmpty(surveyKey.code())) {
                return NamedPath.of("blackhole");
            }
            var root = NamedPath.of("surveys", surveyKey.code().toLowerCase());
            return switch(this) {
                case FCDB -> root.add("fcdb").add("fcdb.yaml");
                case QMAP_NUT -> root.add("qmap").add("nut.yaml");
                case QMAP_FCO -> root.add("qmap").add("fco.yaml");
                case QMAP_POC -> root.add("qmap").add("poc.yaml");
                case QMAP_SDAY -> root.add("qmap").add("sday.yaml");
                case QMAP_SDIET -> root.add("qmap").add("sdiet.yaml");
                case FDM -> root.add("fdm").add("fdm.yaml");
            };
        }
    }

    // -- SYSTEM ID

    private Map<String, SystemId> systemIdBySurveyCode = new ConcurrentHashMap<>();

    /**
     * System ID part of semantic identifiers for given survey secondary key.
     * e.g. {@code at.gd/2.0}
     */
    public SystemId systemId(final Survey.SecondaryKey surveyKey) {
        return systemIdBySurveyCode.computeIfAbsent(surveyKey.code(), _->systemId(survey(surveyKey)));
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

    public Correction24 correction(final Survey.SecondaryKey surveyKey) {
        return correction(survey(surveyKey));
    }
    public Correction24 correction(final Survey survey) {
        return DataUtil.correction(survey.getCorrection());
    }
    private Survey survey(final Survey.SecondaryKey surveyKey) {
        var repo = RuntimeUtils.getRepositoryService();
        var survey = repo.firstMatch(Survey.class, s->surveyKey.code().equals(s.getCode()));
        return survey
            // JUnit support
            .orElseGet(()->_Context.lookup(Survey.class).orElse(null));
    }

    // -- FCDB

    public FoodCompositionRepository fcdb(
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        var fcdbDataSource = blobStore.lookupBlob(DataSourceLocation.FCDB.namedPath(surveyKey))
                .orElseThrow()
                .asDataSource();
        var foodCompositionRepo = FoodCompositionRepository.tryFromYaml(SevenZUtils.decompress(fcdbDataSource))
                .valueAsNonNullElseFail();
        return foodCompositionRepo;
    }

    // -- Q-MAP

    public QualifiedMap nutMapping(
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_NUT, surveyKey, blobStore);
    }

    public QualifiedMap fcoMapping(
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_FCO, surveyKey, blobStore);
    }

    public QualifiedMap pocMapping(
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_POC, surveyKey, blobStore);
    }

    public QualifiedMap specialDayMapping(
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_SDAY, surveyKey, blobStore);
    }

    public QualifiedMap specialDietMapping(
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        return loadQmap(DataSourceLocation.QMAP_SDIET, surveyKey, blobStore);
    }

    public FoodDescriptionModel foodDescriptionModel(
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        var fdmDataSource = blobStore.lookupBlobAndUncompress(DataSourceLocation.FDM.namedPath(surveyKey))
                .orElseThrow()
                .asDataSource();
        return FdmUtils.fromYaml(fdmDataSource);
    }

    // -- HELPER

    private QualifiedMap loadQmap(
            final DataSourceLocation loc,
            final Survey.SecondaryKey surveyKey,
            final BlobStore blobStore) {
        var mapDataSource =
                blobStore
                    .lookupBlob(loc.namedPath(surveyKey))
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
