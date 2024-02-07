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

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.FileUtils;
import org.apache.causeway.commons.io.ZipUtils.ZipOptions;

import dita.blobstore.api.BlobDescriptor;
import dita.blobstore.api.BlobStore;
import dita.commons.types.NamedPath;
import dita.commons.types.ResourceFolder;
import dita.globodiet.survey.recall24.InterviewXmlParser;
import dita.recall24.model.InterviewSet24;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InterviewUtils {

    public InterviewSet24 parse(final Clob interviewSource) {
        return new InterviewXmlParser().parse(interviewSource);
    }

    public Clob unzip(final Blob zippedInterviewSource) {
        var unzippedBlob = zippedInterviewSource
                .unZip(CommonMimeType.XML, ZipOptions.builder()
                        .zipEntryCharset(StandardCharsets.ISO_8859_1)
                        .zipEntryFilter(entry->{
                            //TODO replace with logger
                            System.err.printf("== Parsing Zip entry %s (%.2fKB)%n",
                                    entry.getName(),
                                    0.001*entry.getSize());
                            return true;
                        })
                        .build());
        return unzippedBlob
                .toClob(StandardCharsets.UTF_8);
    }

    public Stream<Clob> streamSources(final BlobStore surveyBlobStore, final NamedPath path, final boolean recursive) {
        return surveyBlobStore.listDescriptors(path, recursive)
            .stream()
            .filter(desc->desc.mimeType().equals(CommonMimeType.XML))
            .map(BlobDescriptor::path)
            .map(surveyBlobStore::lookupBlob)
            .map(Optional::orElseThrow)
            .map(InterviewUtils::unzip);
    }

    @Deprecated
    @SneakyThrows
    public Can<DataSource> scanSources(final ResourceFolder folder) {
        return FileUtils.searchFiles(folder.root(), dir->true, file->file.getName().endsWith(".xml.zip"))
                .stream()
                .map(DataSource::ofFile)
                .collect(Can.toCan());
    }

    @Deprecated
    public InterviewSet24 parse(final DataSource interviewSource) {
        return new InterviewXmlParser().parse(interviewSource);
    }

    @Deprecated
    public DataSource unzip(final DataSource zippedInterviewSource) {
        var unzipped = Blob.of("zipped", CommonMimeType.ZIP, zippedInterviewSource.bytes())
                .unZip(CommonMimeType.XML, ZipOptions.builder()
                        .zipEntryCharset(StandardCharsets.ISO_8859_1)
                        .zipEntryFilter(entry->{
                            System.err.printf("== Parsing Zip entry %s (%.2fKB)%n",
                                    entry.getName(),
                                    0.001*entry.getSize());
                            return true;
                        })
                        .build());
        return DataSource.ofBytes(unzipped.getBytes());
    }


}
