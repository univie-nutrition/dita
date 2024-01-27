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
package dita.commons.types;

import java.io.File;
import java.util.Optional;

import org.apache.causeway.commons.io.FileUtils;

import lombok.SneakyThrows;

public record ResourceFolder(File root) {

    // -- FACTORIES

    public static ResourceFolder projectRoot() {
        return new ResourceFolder(new File("").getAbsoluteFile());
    }
    public static ResourceFolder resourceRoot() {
        return new ResourceFolder(projectRoot().relativeFile("src/main/resources"));
    }
    public static ResourceFolder testResourceRoot() {
        return new ResourceFolder(projectRoot().relativeFile("src/test/resources"));
    }
    public static ResourceFolder ofFileName(final String fileName) {
        return ofFile(new File(fileName));
    }
    public static ResourceFolder ofFile(final File rootDir) {
        return new ResourceFolder(FileUtils.existingDirectoryElseFail(rootDir));
    }

    // -- UTILITIES

    public Optional<ResourceFolder> relative(final String relativeFolderName) {
        return FileUtils.existingDirectory(relativeFile(relativeFolderName))
                .map(ResourceFolder::ofFile);
    }

    public File relativeFile(final String relativeFileName) {
        return new File(root, relativeFileName);
    }

    public File relativeFile(final String relativeFileNameTemplate, final String arg) {
        return new File(root, String.format(relativeFileNameTemplate, arg));
    }

    @SneakyThrows
    public void purgeFiles() {
        FileUtils.searchFiles(root, dir->true, file->true, FileUtils::deleteFile);
    }
}
