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
package dita.globodiet.survey.recall24;

import java.io.File;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.FileUtils;

import dita.commons.types.ResourceFolder;
import lombok.SneakyThrows;

record _InterviewSampler(Can<DataSource> interviewSources) {

    _InterviewSampler(final ResourceFolder sourceFolder) {
        this(scan(sourceFolder.root()));
    }

    @SneakyThrows
    private static Can<DataSource> scan(final File folder) {
        return FileUtils.searchFiles(folder, dir->true, file->file.getName().endsWith(".xml.zip"))
                .stream()
                .map(DataSource::ofFile)
                .collect(Can.toCan());
    }

}
