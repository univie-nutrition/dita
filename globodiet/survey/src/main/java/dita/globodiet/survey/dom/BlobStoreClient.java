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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.applib.exceptions.UnrecoverableException;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.dmap.DirectMap;
import dita.commons.sid.qmap.QualifiedMap;
import dita.foodon.fdm.FdmUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.dom.ReportJob.JobState;
import dita.globodiet.survey.util.InterviewUtils;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.blobstore.applib.BlobDescriptor;
import io.github.causewaystuff.blobstore.applib.BlobDescriptor.Compression;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.cache.CachableAggregate;
import io.github.causewaystuff.commons.base.types.NamedPath;

@Slf4j
public record BlobStoreClient(
        Survey.SecondaryKey surveyKey,
        BlobStore blobStore) {

    @RequiredArgsConstructor
    public enum DataSourceLocation {
        SURVEY_CONFIG(CommonMimeType.YAML, Compression.NONE,
            root->root.add("config.yaml")),
        FCDB(CommonMimeType.YAML, Compression.SEVEN_ZIP,
                root->root.add("fcdb").add("fcdb.yaml")),
        QMAP_NUT(CommonMimeType.YAML, Compression.SEVEN_ZIP,
                root->root.add("maps").add("nut.yaml")),
        DMAP_FCO(CommonMimeType.YAML, Compression.NONE,
                root->root.add("maps").add("fco.yaml")),
        DMAP_POC(CommonMimeType.YAML, Compression.NONE,
                root->root.add("maps").add("poc.yaml")),
        DMAP_SDAY(CommonMimeType.YAML, Compression.NONE,
                root->root.add("maps").add("sday.yaml")),
        DMAP_SDIET(CommonMimeType.YAML, Compression.NONE,
                root->root.add("maps").add("sdiet.yaml")),
        FDM(CommonMimeType.YAML, Compression.SEVEN_ZIP,
                root->root.add("fdm").add("fdm.yaml")),
        CORRECTIONS(CommonMimeType.YAML, Compression.NONE,
                root->root.add("corrections")),
        REPORTS(CommonMimeType.XLSX, Compression.NONE,
            root->root.add("reports")),
        INTERVIEWS_CORRECTED(CommonMimeType.JSON, Compression.SEVEN_ZIP,
                root->root.add("caches").add("interviews-corrected.json"));

        public final CommonMimeType mime;
        public final Compression compression;
        final Function<NamedPath, NamedPath> bdf;

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

    // -- UPLOAD

    public BlobDescriptor uploadToBlobStore(
        @NonNull final NamedPath path,
        @NonNull final Compression compression,
        @NonNull final Blob blob) {
        return blobStore.putBlob(path, blob, desc->desc.withCompression(compression));
    }

    // -- SURVEY CONFIG

    public SurveyConfig surveyConfig() {
        var yaml = lookupBlobAndUncompress(DataSourceLocation.SURVEY_CONFIG)
                .orElseThrow()
                .toClobUtf8()
                .asString();
        return SurveyConfig.fromYaml(yaml);
    }

    // -- CORRECTIONS

    public Correction24 correction() {
        return correctionUploads().stream()
            .map(this::correctionYaml)
            .map(DataUtil::correction)
            .reduce(Correction24.empty(), Correction24::join);
    }

    public List<CorrectionUpload> correctionUploads() {
        return blobStore.listDescriptors(DataSourceLocation.CORRECTIONS.namedPath(surveyPath()), false)
            .stream()
            .peek(x->System.err.printf("%s%n", x))
            .filter(desc->CommonMimeType.YAML.equals(desc.mimeType()))
            .map(desc->CorrectionUpload.of(desc, surveyKey))
            .toList();
    }

    public String correctionYaml(final CorrectionUpload correctionUpload) {
        var yaml = blobStore.lookupBlobAndUncompress(DataSourceLocation.CORRECTIONS.namedPath(surveyPath())
                    .add(correctionUpload.namedPath().lastNameElseFail()))
                .map(Blob::toClobUtf8)
                .map(Clob::asString)
                .orElse("");
        return yaml;
    }

    /// validation is performed before uploading (on failure throws before any upload)
    public void uploadCorrectionYaml(final String createdBy, final Iterable<Blob> blobs) {

        blobs.forEach(blob->{
            var correctionYaml = blob.toClobUtf8().asString();
            // validate
            Correction24.tryFromYaml(correctionYaml)
                .mapFailure(ex->new UnrecoverableException("failed to validate %s; hence upload was cancelled".formatted(blob.name()), ex))
                .ifFailureFail();
        });

        blobs.forEach(blob->{
            log.info("upload {} ({} bytes)", blob.name(), blob.bytes().length);
            var path = DataSourceLocation.CORRECTIONS.namedPath(surveyPath())
                .add(blob.name());

            var blobAsYaml = Blob.of(blob.name(), CommonMimeType.YAML, blob.bytes());
            uploadToBlobStore(path, Compression.NONE, blobAsYaml);
        });
    }

    // -- REPORT JOBS

    private static ReportJobExecutor JOB_EXECUTOR = new ReportJobExecutor();

    public ReportJob runReportJob(final String name, final Supplier<Blob> blobSupplier) {
        var uploadPath = DataSourceLocation.REPORTS.namedPath(surveyPath()).add(name + ".xlsx");

        JOB_EXECUTOR.run(uploadPath, ()->
            uploadToBlobStore(uploadPath, Compression.NONE, blobSupplier.get()));

        return new ReportJob(uploadPath, surveyKey);
    }

    public JobState jobState(final ReportJob reportJob) {
        if(JOB_EXECUTOR.runningJobs().contains(reportJob.namedPath()))
            return JobState.RUNNING;
        if(blobStore.lookupBlob(reportJob.namedPath()).isPresent())
            return JobState.DONE;
        return JobState.CANCELLED;
    }

    public Blob download(final ReportJob reportJob) {
        return blobStore.lookupBlob(reportJob.namedPath())
            .orElse(null);
    }

    public List<ReportJob> reportJobs() {
        return blobStore.listDescriptors(DataSourceLocation.REPORTS.namedPath(surveyPath()), false)
            .stream()
            .filter(desc->CommonMimeType.XLSX.equals(desc.mimeType()))
            .map(desc->new ReportJob(desc.path(), surveyKey))
            .toList();
    }

    public void deleteJobs(final List<ReportJob> jobs) {
        _NullSafe.stream(jobs)
            .forEach(job->
                blobStore.deleteBlob(job.namedPath()));
    }

    // -- INTERVIEW CACHE

    public CachableAggregate<InterviewSet24> cachableInterviewsCorrected(
            final SystemId systemId,
            final Can<Campaign.SecondaryKey> campaignKeys){
        return InterviewUtils.cachableInterviewSet(
                DataSourceLocation.INTERVIEWS_CORRECTED.namedPath(surveyPath()),
                DataSourceLocation.INTERVIEWS_CORRECTED.compression,
                blobStore,
                ()->interviewsCorrected(systemId, campaignKeys, correction()));
    }

    /** bypasses caching
     * @param correction */
    public InterviewSet24 interviewsCorrected(
            final SystemId systemId,
            final Can<Campaign.SecondaryKey> campaignKeys,
            final Correction24 correction) {
        return Campaigns.interviewSetCorrected(systemId, campaignKeys, correction, foodDescriptionModel(), blobStore);
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

    public DirectMap fcoMapping() {
        return loadDmap(DataSourceLocation.DMAP_FCO);
    }

    public DirectMap pocMapping() {
        return loadDmap(DataSourceLocation.DMAP_POC);
    }

    public DirectMap specialDayMapping() {
        return loadDmap(DataSourceLocation.DMAP_SDAY);
    }

    public DirectMap specialDietMapping() {
        return loadDmap(DataSourceLocation.DMAP_SDIET);
    }

    public FoodDescriptionModel foodDescriptionModel() {
        var fdmDataSource = lookupBlobAndUncompress(DataSourceLocation.FDM)
                .orElseThrow()
                .asDataSource();
        return FdmUtils.fromYaml(fdmDataSource);
    }

    // -- HELPER

    private DirectMap loadDmap(final DataSourceLocation loc) {
        var mapDataSource = lookupBlobAndUncompress(loc)
                .orElseThrow()
                .asDataSource();
        return DirectMap.tryFromYaml(mapDataSource).valueAsNonNullElseFail();
    }

    private QualifiedMap loadQmap(final DataSourceLocation loc) {
        var mapDataSource = lookupBlobAndUncompress(loc)
                .orElseThrow()
                .asDataSource();
        return QualifiedMap.tryFromYaml(mapDataSource).valueAsNonNullElseFail();
    }

}
