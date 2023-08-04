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
package dita.globodiet.manager.blobstore;

import java.io.FileFilter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Predicate;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.wicket.util.file.File;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.FileUtils;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.globodiet.manager.DitaModuleGdManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@Named(DitaModuleGdManager.NAMESPACE + ".BlobStore")
public class BlobStore {

    @Inject TableSerializerYaml tableSerializer;

    private File rootDirectory = new File(System.getenv("dita.blobstore.root"));

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
     * There can be only ONE version checked out, which is then shared among all users for editing or viewing.
     */
    @Getter @Nullable
    private ParameterDataVersion currentlyCheckedOutVersion;

    /**
     * Lists all {@link ParameterDataVersion}(s), as recovered from file-system on the fly.
     */
    public Can<ParameterDataVersion> getVersions() {
        return rootDirectory.exists()
                ? Can.ofArray(
                    rootDirectory.listFiles((FileFilter) dir -> dir.isDirectory()
                            && new File(dir, "manifest.yml").exists()))
                    .map(ParameterDataVersion::fromDirectory)
                : Can.empty();
    }

    /**
     * Does not actually delete from blob-store,
     * just changes the manifest, such that given version no longer appears in the UI.
     */
    public void delete(final ParameterDataVersion version) {
        version.setDeleted(true);
    }

    /**
     * Restores a previously deleted version.
     */
    public void restore(final ParameterDataVersion version) {
        version.setDeleted(false);
    }

    /**
     * There can be only ONE version checked out, which is then shared among all users for editing or viewing.
     */
    public void checkout(final @Nullable ParameterDataVersion version) {
        //TODO clear all before load
        tableSerializer.load(getTableData(version), paramsTableFilter());
        this.currentlyCheckedOutVersion = version;
    }

    /**
     * <ol>
     * <li>generate the clone's directory</li>
     * <li>write the clone's manifest</li>
     * <li>copy the 'gd-params.yml.zip' file from master to clone directory</li>
     * </ol>
     * @param master - the version to generate a clone from
     * @param clone - __id is auto generated - so can be left zero
     */
    public void clone(final ParameterDataVersion master, final ParameterDataVersion clone) {
        final int cloneId = getNextFreeVersionId();
        clone.set__id(cloneId);

        val cloneDir = FileUtils.makeDir(new File(rootDirectory, "" + cloneId));
        clone.writeManifest(cloneDir);

        val masterDir = new File(rootDirectory, "" + master.get__id());

        FileUtils.copy(
                new File(masterDir, "gd-params.yml.zip"),
                new File(cloneDir, "gd-params.yml.zip"));
    }

    /**
     * MS-SQL Server backup file that can be imported with the <i>GloboDiet</i> client application.
     */
    public Blob getBAK(final ParameterDataVersion parameterDataVersion) {
        val resultingFileName = String.format("gd-params-%d.bak.zip", parameterDataVersion.get__id());
        return resolveZippedResource(parameterDataVersion, "gd-params.bak", Optional.of(resultingFileName));
    }

    public Clob getTableData(final ParameterDataVersion parameterDataVersion) {
        return resolveZippedResource(parameterDataVersion, "gd-params.yml", Optional.empty())
                .unZip(CommonMimeType.YAML)
                .toClob(StandardCharsets.UTF_8);
    }

    // -- UTILITY

    public static Predicate<ObjectSpecification> paramsTableFilter() {
        return entityType->entityType.getLogicalTypeName()
                .startsWith("dita.globodiet.params.");
    }

    // -- HELPER

    /**
     * Resolves a file resource relative to the given version's blob-store sub-folder.
     */
    private DataSource resolveResource(final ParameterDataVersion parameterDataVersion, final String resourceName) {
        val versionFolder = new File(rootDirectory, "" + parameterDataVersion.get__id());
        return DataSource.ofFile(new File(versionFolder, resourceName));
    }

    /**
     * Loads a zipped resource into a Blob, but does not unzip.
     */
    private Blob resolveZippedResource(
            final ParameterDataVersion parameterDataVersion,
            final String resourceName,
            final Optional<String> filenameOverride) {
        return new Blob(filenameOverride.orElse(resourceName),
                CommonMimeType.ZIP.getMimeType(),
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

}
