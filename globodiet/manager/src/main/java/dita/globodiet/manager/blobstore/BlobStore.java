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
import java.util.function.Predicate;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.wicket.util.file.File;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;

import dita.causeway.replicator.tables.serialize.TableSerializerYaml;
import dita.globodiet.manager.DitaModuleGdManager;
import lombok.val;

@Service
@Named(DitaModuleGdManager.NAMESPACE + ".BlobStore")
public class BlobStore {

    @Inject TableSerializerYaml tableSerializer;

    private File rootDirectory = new File(System.getenv("dita.blobstore.root"));

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
     *
     * @param version
     */
    public void checkout(final ParameterDataVersion version) {

    }

    /**
     * @param master - the version to generate a clone from
     * @param clone - __id is auto generated - so can be left zero
     */
    public void clone(final ParameterDataVersion master, final ParameterDataVersion clone) {

    }

    /**
     * MS SQL Server backup file to be used with the GloboDiet client application.
     */
    public Blob getBAK(final ParameterDataVersion parameterDataVersion) {
        val fileName = String.format("gd-params-%d.bak", parameterDataVersion.get__id());
        return new Blob(fileName,
                CommonMimeType.BIN.getMimeType(),
                resolveResource(parameterDataVersion, "gd-params.bak").bytes());
    }

    public Clob getTableData(final ParameterDataVersion parameterDataVersion) {
        return Blob.of("gd-params.yml",
                CommonMimeType.YAML,
                resolveResource(parameterDataVersion, "gd-params.yml").bytes())
                .toClob(StandardCharsets.UTF_8);
    }

    /**
     * Resolves a file resource relative to the given version's blob-store sub-folder.
     */
    public DataSource resolveResource(final ParameterDataVersion parameterDataVersion, final String resourceName) {
        val versionFolder = new File(rootDirectory, "" + parameterDataVersion.get__id());
        return DataSource.ofFile(new File(versionFolder, resourceName));
    }

    public ParameterDataVersion getCurrentlyCheckedOutVersion() {
        return null;
        // TODO Auto-generated method stub
    }

    public void loadVersion(final ParameterDataVersion parameterDataVersion) {
        tableSerializer.load(getTableData(parameterDataVersion), paramsTableFilter());
    }

    public static Predicate<ObjectSpecification> paramsTableFilter() {
        return entityType->entityType.getLogicalTypeName()
                .startsWith("dita.globodiet.params.");
    }

}
