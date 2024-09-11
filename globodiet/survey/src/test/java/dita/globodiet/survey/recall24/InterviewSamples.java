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

import java.util.function.Consumer;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.io.DataSource;

import lombok.RequiredArgsConstructor;

import dita.commons.types.Message;
import dita.globodiet.survey.util.SidUtils;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.commons.compression.SevenZUtils;

@RequiredArgsConstructor
enum InterviewSamples {
    SAMPLES("Ref_Legacy.7z"),
    COMPOSITES("Ref_Composites_202405281318.7z"),
    FAT_SOUCE_SWEETENERS("Ref_FatSouceSweetener_202405300942.7z");
    final String resourceName;

    public InterviewSet24.Dto asInterviewSet(@Nullable final Consumer<Message> onMsg) {
        var ds = SevenZUtils.decompress(DataSource.ofInputStreamEagerly(
                InterviewSamples.class.getResourceAsStream(resourceName)));
        var interviewSet24 = InterviewXmlParser.parse(ds, SidUtils.globoDietSystemId(), onMsg);
        return interviewSet24;
    }
}
