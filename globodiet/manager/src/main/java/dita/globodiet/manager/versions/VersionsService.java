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

import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dita.globodiet.manager.DitaModuleGdManager;
import io.github.causewaystuff.blobstore.applib.BlobDescriptor;
import io.github.causewaystuff.blobstore.applib.BlobStore;
import io.github.causewaystuff.commons.base.types.NamedPath;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Service
@Named(DitaModuleGdManager.NAMESPACE + ".VersionsService")
public record VersionsService(
		BlobStore blobStore) {

    public VersionsService(@Qualifier("survey") final BlobStore blobStore) {
    	this.blobStore = Objects.requireNonNull(blobStore, ()->"no blobstore");
    }

    @RequiredArgsConstructor
    public enum VersionFilter implements Predicate<ParameterDataVersion> {
        NOT_DELETED(version->!version.deleted())
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
    	return blobStore.listDescriptors(NamedPath.of("versions"), true)
            .stream()
            .map(BlobDescriptor::path)
            .filter(path->path.lastName()
            		.map(name->name.equals("manifest.yaml"))
            		.orElse(false))
            .map(blobStore::lookupBlob)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Blob::asDataSource)
            .map(ParameterDataVersionDto::fromDataSource)
            .sorted((a, b)->Integer.compare(b.getId(), a.getId()))
            .map(dto-> new ParameterDataVersion(dto, this))
            .collect(Can.toCan());
    }

    public Optional<ParameterDataVersion> lookupVersion(final String versionId) {
        return lookupVersionDto(versionId)
        		.map(dto-> new ParameterDataVersion(dto, this));
    }
    public ParameterDataVersion lookupVersionElseFail(final String versionId) {
        return lookupVersion(versionId)
        		.orElseThrow(()->new NoSuchElementException("Version %s not found".formatted(versionId)));
    }

    public Optional<ParameterDataVersionDto> lookupVersionDto(final String versionId) {
    	return lookupResource(versionId, "manifest.yaml")
    			.map(Blob::asDataSource)
    			.map(ParameterDataVersionDto::fromDataSource);
    }

    public Optional<Blob> lookupResource(final String versionId, final String name) {
		return blobStore.lookupBlob(NamedPath.of("versions", versionId, name));
	}
    public Blob lookupResourceElseFail(final String versionId, final String name) {
		return lookupResource(versionId, name)
				.orElseThrow(()->new NoSuchElementException(
						NamedPath.of("versions", versionId, name)
						.toString("/")));
	}


//    /**
//     * Does not actually delete from blob-store,
//     * just changes the manifest, such that given version no longer appears in the UI.
//     */
//    public void delete(final ParameterDataVersion version) {
//        version.setDeleted(true);
//        writeManifest(version);
//    }
//
//    /**
//     * Restores a previously deleted version.
//     */
//    public void restore(final ParameterDataVersion version) {
//        version.setDeleted(false);
//        writeManifest(version);
//    }

//    /**
//     * <ol>
//     * <li>generate the clone's directory</li>
//     * <li>write the clone's manifest</li>
//     * <li>copy the 'gd-params.yaml.zip' file from master to clone directory</li>
//     * </ol>
//     * @param master - the version to generate a clone from
//     * @param clone - __id is auto generated - so can be left zero
//     */
//    public void clone(final ParameterDataVersion master, final ParameterDataVersion clone) {
//        final int cloneId = getNextFreeVersionId();
//        clone.setId(cloneId);
//
//        var cloneDir = FileUtils.makeDir(new File(rootDirectory(), "" + cloneId));
//        clone.writeManifest(cloneDir);
//
//        var masterDir = lookupVersionFolderElseFail(master);
//
//        FileUtils.copy(
//                new File(masterDir, "gd-params.yaml.zip"),
//                new File(cloneDir, "gd-params.yaml.zip"));
//    }

    /**
     * MS-SQL Server backup file that can be imported with the <i>GloboDiet</i> client application.
     */
    public Blob getBAK(final ParameterDataVersion parameterDataVersion) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm");
        var timestamp = formatter.format(parameterDataVersion.creationTime());
        var resultingFileName = String.format("GloboDiet-%s.7z", timestamp);
        return resolve7ZippedResource(parameterDataVersion, "GloboDiet", Optional.of(resultingFileName));
    }

	public Blob zippedParameterDataYaml(final ParameterDataVersion parameterDataVersion) {
		return resolveZippedResource(parameterDataVersion, "gd-params.yaml", Optional.empty());
	}

    // -- UTILITY

//    public void writeManifest(final @Nullable ParameterDataVersion version) {
//        if(version==null)
//            return;
//        version.writeManifest(lookupVersionFolderElseFail(version));
//    }

    // -- HELPER

    /**
     * Resolves a file resource relative to the given version's blob-store sub-folder.
     */
    private Blob resolveResource(final ParameterDataVersion parameterDataVersion, final String resource) {
    	var versionId = "" + parameterDataVersion.id();
    	var path = NamedPath.of("versions", versionId, resource);
    	return blobStore.lookupBlob(path)
    			.orElseThrow(()->new NoSuchElementException(path.toString("/")));
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
                resolveResource(parameterDataVersion, resourceName).bytes());
    }

    Blob resolveZippedResource(
            final ParameterDataVersion parameterDataVersion,
            final String resourceName,
            final Optional<String> filenameOverride) {
        return new Blob(filenameOverride.orElse(resourceName),
                CommonMimeType.ZIP.mimeType(),
                resolveResource(parameterDataVersion, resourceName).bytes());
    }

//    /**
//     * Get the next free id by just incrementing the max id found when enumerating all versions.
//     */
//    private int getNextFreeVersionId() {
//        return 1 + getVersions().stream()
//                .mapToInt(ParameterDataVersion::id)
//                .max()
//                .orElse(10001);
//    }
//
//    private File lookupVersionFolderElseFail(final @NonNull ParameterDataVersion version) {
//        var versionFolder = new File(rootDirectory(), "" + version.id());
//        return FileUtils.existingDirectoryElseFail(versionFolder);
//    }

}
