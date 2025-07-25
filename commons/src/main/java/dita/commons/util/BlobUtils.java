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
package dita.commons.util;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.ZipUtils;
import org.apache.causeway.commons.io.ZipUtils.ZipOptions;

import lombok.experimental.UtilityClass;

import io.github.causewaystuff.commons.base.types.NamedPath;

@UtilityClass
public class BlobUtils {

    public boolean isXml(final @Nullable Blob blob) {
        return blob==null
                ? false
                : CommonMimeType.XML.matches(blob.mimeType())
                    || blob.mimeType().toString().contains("xml");
    }

    public boolean isYaml(final @Nullable Blob blob) {
        return blob==null
                ? false
                : CommonMimeType.YAML.matches(blob.mimeType())
                    || blob.name().endsWith(".yaml")
                    || blob.name().endsWith(".yml");
    }

    public boolean isZipped(final @Nullable Blob blob) {
        return blob==null
                ? false
                : CommonMimeType.ZIP.matches(blob.mimeType())
                    || blob.mimeType().toString().contains("zip");
    }

    public Stream<Blob> unzipAsBlobStream(final @Nullable Blob blob,
            final UnaryOperator<ZipOptions.ZipOptionsBuilder> zipOptionsCustomizer,
            final Function<String, CommonMimeType> mimeTypeForZipEntryNameResolver) {
        return blob==null
                ? Stream.empty()
                : ZipUtils.streamZipEntries(blob.asDataSource(),
                        zipOptionsCustomizer.apply(ZipOptions.builder())
                        .build())
                    .map(ds->{
                        var path = NamedPath.parse(ds.zipEntry().getName(), "/");
                        var name = path.names().getLastElseFail();
                        var zipEntryAsBlob = Blob.of(name, mimeTypeForZipEntryNameResolver.apply(name), ds.bytes());
                        return zipEntryAsBlob;
                    });
    }

    public Stream<Blob> unzipAsBlobStream(final @Nullable Blob blob, final @NonNull CommonMimeType mime) {
        return unzipAsBlobStream(blob, (final ZipOptions.ZipOptionsBuilder opts)->
                opts.zipEntryFilter(zipEntry->!zipEntry.isDirectory()
                        && matches(zipEntry, mime)),
                _->mime);
    }

    private boolean matches(final ZipEntry zipEntry, final CommonMimeType mime) {
        return mime.getProposedFileExtensions().stream()
                .anyMatch(zipEntry.getName().toLowerCase()::endsWith);
    }

}
