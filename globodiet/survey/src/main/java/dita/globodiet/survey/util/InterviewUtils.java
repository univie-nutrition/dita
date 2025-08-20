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
package dita.globodiet.survey.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.springframework.util.function.ThrowingSupplier;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.types.Message;
import dita.globodiet.survey.recall24.InterviewXmlParser;
import dita.recall24.dto.Annotated;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Respondent24;
import dita.recall24.dto.util.InterviewSetParser;
import dita.recall24.dto.util.Recall24DtoUtils;
import io.github.causewaystuff.blobstore.applib.BlobCacheHandler;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.blobstore.applib.BlobDescriptor.Compression;
import io.github.causewaystuff.commons.base.cache.CachableAggregate;
import io.github.causewaystuff.commons.base.types.NamedPath;
import io.github.causewaystuff.commons.compression.SevenZCacheHandler;

@UtilityClass
@Slf4j
public class InterviewUtils {

    public Stream<Clob> streamSources(final BlobStore surveyBlobStore, final NamedPath path, final boolean recursive) {
        return surveyBlobStore.listDescriptors(path, recursive)
            .stream()
            .filter(desc->desc.mimeType().equals(CommonMimeType.XML)
                || desc.mimeType().equals(CommonMimeType.YAML))
            .map(desc->{
                return surveyBlobStore.lookupBlobAndUncompress(desc.path())
                    .orElseThrow()
                    .toClob(StandardCharsets.UTF_8);
            });
    }

    /// applies respondent correction and filtering
    public Stream<Interview24> streamInterviewsFromBlobStore(
            final NamedPath namedPath,
            final BlobStore surveyBlobStore,
            final SystemId systemId,
            final Correction24 correction,
            final Consumer<Message> messageConsumer) {

        log.info("load from namedPath: {}", namedPath);

        return surveyBlobStore==null
            ? Stream.empty()
            : InterviewUtils.streamSources(surveyBlobStore, namedPath, true)
                .map(ds->CommonMimeType.YAML.matches(ds.mimeType())
                    ? parseYaml(ds, systemId, messageConsumer)
                    : InterviewXmlParser.parse(ds, systemId, messageConsumer)
                )
                .flatMap(List::stream)
                .map(iv->prependPathToDataSourceAnnotation(namedPath, iv))
                .map(InterviewUtils::toInterviewSet)
                .map(Recall24DtoUtils.correctRespondents(correction))
                .flatMap(InterviewSet24::streamInterviews);
    }

    private static Interview24 prependPathToDataSourceAnnotation(
        final NamedPath namedPath,
        final Interview24 iv) {
        iv.dataSource()
            .ifPresent(path->iv.withDataSource(NamedPath.of(namedPath.lastNameElseFail()).add(path)));
        return iv;
    }

    private static InterviewSet24 toInterviewSet(final Interview24 interview) {
        var respondentStub = interview.parentRespondent();
        var respondent = new Respondent24(respondentStub.alias(), respondentStub.dateOfBirth(), respondentStub.sex(), Can.of(interview));
        return new InterviewSet24(Can.of(respondent), Collections.emptyMap());
    }

    public CachableAggregate<InterviewSet24> cachableInterviewSet(
            final File zipFile,
            final ThrowingSupplier<? extends InterviewSet24> costlySupplier) {
        return new CachableAggregate<InterviewSet24>(costlySupplier, new SevenZCacheHandler<>(zipFile,
            //reader
            InterviewSetParser::parseJson,
            // writer
            interviewSet->
                interviewSet.toJson().getBytes(StandardCharsets.UTF_8)));
    }

    public CachableAggregate<InterviewSet24> cachableInterviewSetYaml(
            final File zipFile,
            final ThrowingSupplier<? extends InterviewSet24> costlySupplier) {
        return new CachableAggregate<InterviewSet24>(costlySupplier, new SevenZCacheHandler<>(zipFile,
            //reader
            ds->InterviewSetParser.parseYaml(new String(ds.bytes(), StandardCharsets.UTF_8)),
            // writer
            interviewSet->
                interviewSet.toYaml().getBytes(StandardCharsets.UTF_8)));
    }

    public CachableAggregate<InterviewSet24> cachableInterviewSet(
            final NamedPath path,
            final Compression compression,
            final BlobStore blobStore,
            final ThrowingSupplier<? extends InterviewSet24> costlySupplier) {
        return new CachableAggregate<InterviewSet24>(costlySupplier, new BlobCacheHandler<>(
            path,
            compression,
            blobStore,
            //reader
            blob->InterviewSetParser.parseJson(blob.asDataSource()),
            // writer
            interviewSet->Blob.of(
                path.lastNameElseFail(),
                CommonMimeType.JSON,
                interviewSet.toJson().getBytes(StandardCharsets.UTF_8))));
    }

    public void warnEmptyDataSource(
        final Object source,
        final @Nullable Consumer<Message> messageConsumer) {

        var messageConsumerOrFallback = Optional.ofNullable(messageConsumer)
            .orElseGet(Message::consumerWritingToSyserr);
        var sourceName = switch (source) {
            case Clob clob -> clob.name();
            default -> source.getClass().getName();
        };
        messageConsumerOrFallback
            .accept(Message.warn("empty interview data source detected: %s", sourceName));
    }

    private List<Interview24> parseYaml(final Clob interviewSource, final SystemId systemId, final Consumer<Message> messageConsumer) {
        var interviewSet = InterviewSetParser.parseYaml(interviewSource.asString());
        if(interviewSet==null) {
            InterviewUtils.warnEmptyDataSource(interviewSource, messageConsumer);
            return List.of();
        }
        var interviewList = interviewSet.streamInterviews().toList();
        interviewList.forEach(iv->iv.putAnnotation(Annotated.DATASOURCE, interviewSource.name()));
        return interviewList;
    }

}
