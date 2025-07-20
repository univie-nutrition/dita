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

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;

import lombok.RequiredArgsConstructor;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.qmap.QualifiedMap;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.foodon.fdm.FdmUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.blobstore.applib.BlobDescriptor;
import io.github.causewaystuff.blobstore.applib.BlobDescriptor.Compression;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.cache.CachableAggregate;
import io.github.causewaystuff.commons.base.types.NamedPath;

public record BlobStoreClient(
        Survey.SecondaryKey surveyKey,
        BlobStore blobStore) {

    @RequiredArgsConstructor
    public enum DataSourceLocation {
        FCDB(CommonMimeType.YAML, Compression.SEVEN_ZIP,
                root->root.add("fcdb").add("fcdb.yaml")),
        QMAP_NUT(CommonMimeType.YAML, Compression.SEVEN_ZIP,
                root->root.add("qmap").add("nut.yaml")),
        QMAP_FCO(CommonMimeType.YAML, Compression.NONE,
                root->root.add("qmap").add("fco.yaml")),
        QMAP_POC(CommonMimeType.YAML, Compression.NONE,
                root->root.add("qmap").add("poc.yaml")),
        QMAP_SDAY(CommonMimeType.YAML, Compression.NONE,
                root->root.add("qmap").add("sday.yaml")),
        QMAP_SDIET(CommonMimeType.YAML, Compression.NONE,
                root->root.add("qmap").add("sdiet.yaml")),
        FDM(CommonMimeType.YAML, Compression.SEVEN_ZIP,
                root->root.add("fdm").add("fdm.yaml")),
        CORRECTIONS(CommonMimeType.YAML, Compression.NONE,
                root->root.add("corrections").add("corrections.yaml")),
        INTERVIEWS_CORRECTED(CommonMimeType.JSON, Compression.SEVEN_ZIP,
                root->root.add("caches").add("interviews-corrected.json"));

        public final CommonMimeType mime;
        public final Compression compression;
        final Function<NamedPath, NamedPath> bdf;

        public BlobDescriptor blobDescriptor(final NamedPath root) {
            return BlobDescriptor.builder()
                    .mimeType(mime)
                    .compression(compression)
                    .path(namedPath(root))
                    .build();
        }

        public NamedPath namedPath(final NamedPath root) {
            return bdf.apply(root);
        }
    }

    public NamedPath surveyPath() {
        return NamedPath.of("surveys", surveyKey.code().toLowerCase());
    }

    public Optional<Blob> lookupBlobAndUncompress(final DataSourceLocation loc) {
        return blobStore.lookupBlobAndUncompress(loc.namedPath(surveyPath()));
    }

    // -- CORRECTIONS

    public String correctionYaml() {
        var yaml = lookupBlobAndUncompress(DataSourceLocation.CORRECTIONS)
                .map(Blob::toClobUtf8)
                .map(Clob::asString)
                .orElse("");
        return yaml;
    }

    public void putCorrection(final String correctionYaml) {
        var desc = DataSourceLocation.CORRECTIONS.blobDescriptor(surveyPath());
        blobStore.putBlob(desc,
                Blob.of(desc.path().lastNameElseFail(),
                        desc.mimeType(),
                        correctionYaml.getBytes(StandardCharsets.UTF_8)));
    }

    // -- INTERVIEW CACHE

    public CachableAggregate<InterviewSet24> cachableInterviewsCorrected(
            final SystemId systemId,
            final Can<Campaign.SecondaryKey> campaignKeys){
        return InterviewUtils.cachableInterviewSet(
                DataSourceLocation.INTERVIEWS_CORRECTED.blobDescriptor(surveyPath()),
                blobStore,
                ()->interviewsCorrected(systemId, campaignKeys));
    }

    /** bypasses caching */
    public InterviewSet24 interviewsCorrected(
            final SystemId systemId,
            final Can<Campaign.SecondaryKey> campaignKeys) {
        return Campaigns.interviewSetCorrected(systemId, campaignKeys, DataUtil.correction(correctionYaml()), foodDescriptionModel(), blobStore);
    }

    public void invalidateAllInterviewCaches() {
        blobStore.deleteBlob(DataSourceLocation.INTERVIEWS_CORRECTED.namedPath(surveyPath()));
    }


    // -- FCDB

    public FoodCompositionRepository fcdb() {
        var fcdbDataSource = lookupBlobAndUncompress(DataSourceLocation.FCDB)
                .orElseThrow()
                .asDataSource();
        var foodCompositionRepo = FoodCompositionRepository.tryFromYaml(fcdbDataSource)
                .valueAsNonNullElseFail();
        return foodCompositionRepo;
    }

    // -- Q-MAP

    public QualifiedMap nutMapping() {
        return loadQmap(DataSourceLocation.QMAP_NUT);
    }

    public QualifiedMap fcoMapping() {
        return loadQmap(DataSourceLocation.QMAP_FCO);
    }

    public QualifiedMap pocMapping() {
        return loadQmap(DataSourceLocation.QMAP_POC);
    }

    public QualifiedMap specialDayMapping() {
        return loadQmap(DataSourceLocation.QMAP_SDAY);
    }

    public QualifiedMap specialDietMapping() {
        return loadQmap(DataSourceLocation.QMAP_SDIET);
    }

    public FoodDescriptionModel foodDescriptionModel() {
        var fdmDataSource = lookupBlobAndUncompress(DataSourceLocation.FDM)
                .orElseThrow()
                .asDataSource();
        return FdmUtils.fromYaml(fdmDataSource);
    }

    // -- HELPER

    private QualifiedMap loadQmap(final DataSourceLocation loc) {
        var mapDataSource = lookupBlobAndUncompress(loc)
                .orElseThrow()
                .asDataSource();
        return QualifiedMap.tryFromYaml(mapDataSource).valueAsNonNullElseFail();
    }

}
