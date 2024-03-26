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

import org.causewaystuff.commons.base.types.NamedPath;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.ZipUtils;
import org.apache.causeway.commons.io.ZipUtils.ZipOptions;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlobUtils {

    public boolean isXml(final @Nullable Blob blob) {
        return blob==null
                ? false
                : CommonMimeType.XML.matches(blob.getMimeType())
                    || blob.getMimeType().toString().contains("xml");
    }

    public boolean isZipped(final @Nullable Blob blob) {
        return blob==null
                ? false
                : CommonMimeType.ZIP.matches(blob.getMimeType())
                    || blob.getMimeType().toString().contains("zip");
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

    public Stream<Blob> unzipAsBlobStream(final @Nullable Blob blob, final @NonNull CommonMimeType filter) {
        return unzipAsBlobStream(blob, (ZipOptions.ZipOptionsBuilder opts)->
                opts.zipEntryFilter(zipEntry->!zipEntry.isDirectory()
                        && zipEntry.getName().toLowerCase().endsWith(".xml")),
                _->filter);
    }

}
