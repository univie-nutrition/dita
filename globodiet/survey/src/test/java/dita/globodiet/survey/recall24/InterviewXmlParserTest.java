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

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Cardinality;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.ZipUtils.ZipOptions;

import dita.commons.types.ResourceFolder;

class InterviewXmlParserTest {

    private InterviewXmlParser parser = new InterviewXmlParser();

    //@Test
    void parsing() {
        var xml = _InterviewSampler.sampleXml();
        var iSet = parser.parse(DataSource.ofStringUtf8(xml));
        System.err.printf("%s%n", iSet.toJson());
    }

    //TODO WIP
    @Test
    void aggregation() {

        ResourceFolder.testResourceRoot().relative("secret")
        .map(_InterviewSampler::new)
        .ifPresent(sampler->{

            var interviewSources = sampler.interviewSources();

            assertEquals(Cardinality.MULTIPLE, interviewSources.getCardinality());

            interviewSources.stream()
            //.limit(1)
            .forEach(zippedDs->{

                var unzipped = Blob.of("zipped", CommonMimeType.ZIP, zippedDs.bytes())
                    .unZip(CommonMimeType.XML, ZipOptions.builder()
                            .zipEntryCharset(StandardCharsets.ISO_8859_1)
                            .zipEntryFilter(entry->{
                                System.err.printf("== Parsing Zip entry %s (%.2fKB)%n",
                                        entry.getName(),
                                        0.001*entry.getSize());
                                return true;
                            })
                            .build());

                var ds = DataSource.ofBytes(unzipped.getBytes());
                var iSet = parser.parse(ds);
                //System.err.printf("%s%n", iSet.toJson());
            });

        });
    }
}