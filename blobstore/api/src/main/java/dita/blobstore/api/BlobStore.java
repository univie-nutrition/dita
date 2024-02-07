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
package dita.blobstore.api;

import java.util.Optional;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.commons.collections.Can;

import dita.commons.types.NamedPath;
import lombok.NonNull;

public interface BlobStore {

    void putBlob(@NonNull BlobDescriptor blobDescriptor, @NonNull Blob blob);

    Can<BlobDescriptor> listDescriptors(@Nullable NamedPath path, boolean recursive);
    Optional<BlobDescriptor> lookupDescriptor(@Nullable NamedPath path);
    Optional<Blob> lookupBlob(@Nullable NamedPath path);
    void deleteBlob(@Nullable NamedPath path);

}
