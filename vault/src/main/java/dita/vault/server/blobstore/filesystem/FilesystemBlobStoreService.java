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
package dita.vault.server.blobstore.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.YamlUtils;

import dita.commons.types.ResourceFolder;
import dita.vault.model.VaultDocument;
import dita.vault.server.blobstore.BlobStoreService;
import lombok.NonNull;
import lombok.val;

@Service
public class FilesystemBlobStoreService implements BlobStoreService {

    private final ResourceFolder vaultDirectory;

    @Autowired
    public FilesystemBlobStoreService(
            @Value("${dita.vault.dir}")
            final String vaultDir) {
        this.vaultDirectory = new ResourceFolder(new File(vaultDir));
    }

    record Manifest(List<VaultDocument> documents) {
        public static Manifest fromDirectory(final @NonNull File dir) {
            val dataSource = DataSource.ofFile(new File(dir, "manifest.yml"));
            val manifest = YamlUtils.tryRead(Manifest.class, dataSource)
                .valueAsNonNullElseFail();
            return manifest;
        }
    }

    public Can<Manifest> loadManifests() {
        var rootDirectory = vaultDirectory.root();
        return rootDirectory.exists()
                ? Can.ofArray(
                    rootDirectory.listFiles((FileFilter) dir -> dir.isDirectory()
                            && new File(dir, "manifest.yml").exists()))
                    .map(Manifest::fromDirectory)
                : Can.empty();
    }

}
