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
package dita.globodiet.survey;

import java.time.ZonedDateTime;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;

import dita.blobstore.api.BlobDescriptor;
import dita.blobstore.api.BlobStore;
import dita.commons.types.NamedPath;

@SpringBootTest(classes = {
        DitaModuleGdSurvey.class
        })
@PrivateDataTest
class DitaModuleGdSurveyTest {

    @Inject @Qualifier("survey") BlobStore surveyBlobStore;

    @Test
    void test() {
        assertNotNull(surveyBlobStore);

        var blob = Blob.of("test", CommonMimeType.BIN, new byte[] {1, 2, 3, 4});
        var createdOn = ZonedDateTime.now();
        var path = NamedPath.of("a", "b", "myblob.bin");
        var blobDesc = new BlobDescriptor(path, CommonMimeType.BIN, "unknown", createdOn, 4, "NONE");

        // expected precondition
        assertTrue(surveyBlobStore.lookupDescriptor(path).isEmpty());
        assertTrue(surveyBlobStore.lookupBlob(path).isEmpty());

        // when
        surveyBlobStore.putBlob(blobDesc, blob);

        // then
        var blobDescRecovered = surveyBlobStore.lookupDescriptor(path).orElse(null);
        assertNotNull(blobDescRecovered);
        var blobRecovered = surveyBlobStore.lookupBlob(path).orElse(null);
        assertNotNull(blobRecovered);

        // cleanup
        surveyBlobStore.deleteBlob(path);

        // expected postcondition
        assertTrue(surveyBlobStore.lookupDescriptor(path).isEmpty());
        assertTrue(surveyBlobStore.lookupBlob(path).isEmpty());
    }
}
