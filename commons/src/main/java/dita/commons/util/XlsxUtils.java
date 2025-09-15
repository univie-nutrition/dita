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
package dita.commons.util;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class XlsxUtils {

    //public String EXECUTABLE = "C:/Program Files/LibreOffice/program/scalc.exe";
    public String EXECUTABLE = "/usr/bin/libreoffice";

    @RequiredArgsConstructor
    private enum Executable {
        LINUX(List.of("/usr/bin/libreoffice", "--calc")),
        WIN(List.of("C:/Program Files/LibreOffice/program/scalc.exe"));
        final List<String> args;
        static List<String> get(final String additionalArg) {
            for(var executable : values()) {
                if(new File(executable.args.get(0)).exists()) {
                    return Stream.concat(executable.args.stream(), Stream.of(additionalArg))
                            .toList();
                }
            }
            return List.of();
        }
    }

    @SneakyThrows
    public Process launchViewer(final File xlsxFile) {
        var pb = new ProcessBuilder();
        pb.command(Executable.get(xlsxFile.getAbsolutePath()));
        pb.inheritIO();
        return pb.start();
    }

    @SneakyThrows
    public void launchViewerAndWaitFor(final File xlsxFile) {
        launchViewer(xlsxFile).waitFor();
    }

}
