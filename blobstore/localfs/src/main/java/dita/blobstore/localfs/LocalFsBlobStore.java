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
package dita.blobstore.localfs;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.commons.collections.Can;

import dita.blobstore.api.BlobDescriptor;
import dita.blobstore.api.BlobStore;
import dita.blobstore.api.BlobStoreFactory.BlobStoreConfiguration;

@Repository
public class LocalFsBlobStore implements BlobStore {

    private final Path fileSystemPath;

    public LocalFsBlobStore(final BlobStoreConfiguration config) {
        this.fileSystemPath = new File(config.resource()).toPath();
    }

    @Override
    public void putBlob(final BlobDescriptor blobDescriptor, final Blob blob) {
        // TODO Auto-generated method stub

    }

    @Override
    public Can<BlobDescriptor> listDescriptors(final Path path, final boolean recursive) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<BlobDescriptor> lookupDescriptor(final Path path) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<Blob> lookupBlob(final Path path) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public void deleteBlob(final Path path) {
        // TODO Auto-generated method stub

    }

}
