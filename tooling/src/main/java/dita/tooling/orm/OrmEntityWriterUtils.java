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
package dita.tooling.orm;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.FileUtils;

import dita.tooling.orm.OrmEntityGenerator.JavaModel;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrmEntityWriterUtils {

    public void writeToDirectory(final @Nullable Stream<JavaModel> modelStream, final @NonNull File destDir) {

        if(modelStream==null) {
            return;
        }

        FileUtils.existingDirectoryElseFail(destDir);

        modelStream
        .forEach(javaModel->{

                final String className = javaModel.className().canonicalName();

                System.err.println("=================================");
                System.err.println("writing: " + className);
                System.err.println("=================================");

                val destFile = new File(destDir, className);
                val javaFile = javaModel.buildJavaFile();

                try {
                    javaFile.writeTo(destFile.toPath(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw _Exceptions.unrecoverable(e, "failed to write java file %s", destFile);
                }
        });

    }

}
