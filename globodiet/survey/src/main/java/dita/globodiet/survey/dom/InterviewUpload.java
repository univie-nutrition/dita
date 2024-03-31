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
package dita.globodiet.survey.dom;

import io.github.causewaystuff.blobstore.applib.BlobDescriptor;

import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.PropertyLayout;

@DomainObjectLayout(
        describedAs = "GloboDiet exported interview XML file.",
        cssClassFa = "solid file .survey-color,\n"
                        + "solid user .survey-color .ov-size-60 .ov-right-55 .ov-bottom-55\n"
)
public record InterviewUpload(
        @PropertyLayout(sequence = "1")
        String namedPath,
        @PropertyLayout(sequence = "2")
        String sha256
        ) {

    static InterviewUpload of(final BlobDescriptor blobDescriptor) {
        return new InterviewUpload(
                blobDescriptor.path().toString("/"),
                blobDescriptor.attributes().getOrDefault("sha256", "n/a"));
    }

}
