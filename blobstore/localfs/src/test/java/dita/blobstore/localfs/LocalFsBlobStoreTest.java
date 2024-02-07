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
package dita.blobstore.localfs;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import org.apache.causeway.commons.io.FileUtils;

import dita.blobstore.api.BlobStoreFactory;
import dita.blobstore.api.BlobStoreFactory.BlobStoreConfiguration;
import dita.blobstore.test.BlobStoreTester;
import dita.blobstore.test.BlobStoreTester.Scenario;

class LocalFsBlobStoreTest {

    private File root;
    private LocalFsBlobStore blobStore;
    private BlobStoreConfiguration config;

    @BeforeEach
    void setup() {
        this.root = FileUtils.tempDir("dita-test");
        this.config = new BlobStoreFactory.BlobStoreConfiguration(LocalFsBlobStore.class, root.getAbsolutePath());
        this.blobStore = new LocalFsBlobStore(config);
    }

    @AfterEach
    void cleanup() {
        FileUtils.deleteDirectory(root);
    }

    @ParameterizedTest
    @EnumSource(BlobStoreTester.ScenarioSample.class)
    void roundtrip(final BlobStoreTester.ScenarioSample scenarioSample) {
        var scenario = scenarioSample.create();
        var tester = new BlobStoreTester(blobStore);
        tester.setup(scenario);
        tester.assertExpectations(scenario);
        assertSameResultsFromScan(scenario);
        tester.cleanup(scenario);
    }

    void assertSameResultsFromScan(final Scenario scenario) {
        var secondaryBlobStore = new LocalFsBlobStore(config);
        var tester = new BlobStoreTester(secondaryBlobStore);
        tester.assertExpectations(scenario);
    }

}
