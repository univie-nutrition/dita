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
package dita.globodiet.manager.versions;

import java.io.File;
import java.io.FileFilter;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Predicate;

import jakarta.inject.Named;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.FileUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import dita.globodiet.manager.DitaModuleGdManager;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;

@Service
@Named(DitaModuleGdManager.NAMESPACE + ".VersionsService")
public class VersionsService {

    @Deprecated
    @Value("${dita.globodiet.survey.blobstore.resource}")
    private String blobStoreRoot;

    @Deprecated
    @Getter(lazy = true) @Accessors(fluent=true)
    private final File rootDirectory = new File(new File(blobStoreRoot), "versions");

    @Autowired @Qualifier("survey")
    private BlobStore blobStore;

    @RequiredArgsConstructor
    public enum VersionFilter implements Predicate<ParameterDataVersion> {
        NOT_DELETED(version->!version.isDeleted())
        ;
        final Predicate<ParameterDataVersion> predicate;
        @Override public boolean test(final ParameterDataVersion version) {
            return predicate.test(version);
        }
    }

    /**
     * Lists all {@link ParameterDataVersion}(s), as recovered from file-system on the fly.
     */
    public Can<ParameterDataVersion> getVersions() {

        var versionDirs = blobStore.listDescriptors(NamedPath.of(), false);

        System.err.println("----- VERSIONS");

        System.err.printf("blobStore: %s%n", blobStore.getClass());

        versionDirs.forEach(dir->{
            System.err.printf("dir: %s%n", dir);
        });

        System.err.println("--------------");

        return rootDirectory().exists()
                ? Can.ofArray(
                    rootDirectory().listFiles((FileFilter) dir -> dir.isDirectory()
                            && new File(dir, "manifest.yaml").exists()))
                    .map(ParameterDataVersion::fromDirectory)
                    .sorted((a, b)->b.getCreationTime().compareTo(a.getCreationTime()))
                : Can.empty();
    }

    /**
     * Lists all {@link ParameterDataVersion}(s), as recovered from file-system on the fly.
     */
    public Can<ParameterDataVersion> getVersions1() {
        return rootDirectory().exists()
                ? Can.ofArray(
                    rootDirectory().listFiles((FileFilter) dir -> dir.isDirectory()
                            && new File(dir, "manifest.yaml").exists()))
                    .map(ParameterDataVersion::fromDirectory)
                    .sorted((a, b)->b.getCreationTime().compareTo(a.getCreationTime()))
                : Can.empty();
    }

    public Optional<ParameterDataVersion> lookupVersion(final int versionId) {
        return getVersions()
                .stream()
                .filter(v->v.get__id() == versionId)
                .findFirst();
    }

    /**
     * Does not actually delete from blob-store,
     * just changes the manifest, such that given version no longer appears in the UI.
     */
    public void delete(final ParameterDataVersion version) {
        version.setDeleted(true);
        writeManifest(version);
    }

    /**
     * Restores a previously deleted version.
     */
    public void restore(final ParameterDataVersion version) {
        version.setDeleted(false);
        writeManifest(version);
    }

    /**
     * <ol>
     * <li>generate the clone's directory</li>
     * <li>write the clone's manifest</li>
     * <li>copy the 'gd-params.yaml.zip' file from master to clone directory</li>
     * </ol>
     * @param master - the version to generate a clone from
     * @param clone - __id is auto generated - so can be left zero
     */
    public void clone(final ParameterDataVersion master, final ParameterDataVersion clone) {
        final int cloneId = getNextFreeVersionId();
        clone.set__id(cloneId);

        var cloneDir = FileUtils.makeDir(new File(rootDirectory(), "" + cloneId));
        clone.writeManifest(cloneDir);

        var masterDir = lookupVersionFolderElseFail(master);

        FileUtils.copy(
                new File(masterDir, "gd-params.yaml.zip"),
                new File(cloneDir, "gd-params.yaml.zip"));
    }

    /**
     * MS-SQL Server backup file that can be imported with the <i>GloboDiet</i> client application.
     */
    public Blob getBAK(final ParameterDataVersion parameterDataVersion) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm");
        var timestamp = formatter.format(parameterDataVersion.getCreationTime());
        var resultingFileName = String.format("GloboDiet-%s.7z", timestamp);
        return resolve7ZippedResource(parameterDataVersion, "GloboDiet", Optional.of(resultingFileName));
    }

    // -- UTILITY

    public void writeManifest(final @Nullable ParameterDataVersion version) {
        if(version==null) {
            return;
        }
        version.writeManifest(lookupVersionFolderElseFail(version));
    }

    // -- HELPER

    /**
     * Resolves a file resource relative to the given version's blob-store sub-folder.
     */
    private DataSource resolveResource(final ParameterDataVersion parameterDataVersion, final String resourceName) {
        var versionFolder = new File(rootDirectory(), "" + parameterDataVersion.get__id());
        return DataSource.ofFile(new File(versionFolder, resourceName));
    }

    /**
     * Loads a zipped resource into a Blob, but does not unzip.
     */
    private Blob resolve7ZippedResource(
            final ParameterDataVersion parameterDataVersion,
            final String resourceName,
            final Optional<String> filenameOverride) {
        return new Blob(filenameOverride.orElse(resourceName),
                CommonMimeType._7Z.mimeType(),
                resolveResource(parameterDataVersion, resourceName + ".7z").bytes());
    }

    Blob resolveZippedResource(
            final ParameterDataVersion parameterDataVersion,
            final String resourceName,
            final Optional<String> filenameOverride) {
        return new Blob(filenameOverride.orElse(resourceName),
                CommonMimeType.ZIP.mimeType(),
                resolveResource(parameterDataVersion, resourceName + ".zip").bytes());
    }

    /**
     * Get the next free id by just incrementing the max id found when enumerating all versions.
     */
    private int getNextFreeVersionId() {
        return 1 + getVersions().stream()
                .mapToInt(ParameterDataVersion::get__id)
                .max()
                .orElse(10001);
    }

    private File lookupVersionFolderElseFail(final @NonNull ParameterDataVersion version) {
        var versionFolder = new File(rootDirectory(), "" + version.get__id());
        return FileUtils.existingDirectoryElseFail(versionFolder);
    }

}
