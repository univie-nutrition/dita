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
package dita.vault.server;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dita.vault.model.VaultBlob;
import dita.vault.model.VaultClob;
import dita.vault.server.blobstore.BlobStoreService;

@RestController
public class VaultResource {

    @Autowired
    private BlobStoreService blobStoreService;

    @GetMapping("/version")
    public String version() {
        return "1.0.0";
    }

//    @GetMapping("/document")
//    public VaultDocument document(
//            @RequestParam
//            final String uuid) {
//        return new VaultDocument(UUID.fromString(uuid), "data.xml", "todo", null);
//    }

    @GetMapping("/documentIds")
    public List<UUID> documentIds() {
        return List.of(UUID.randomUUID(), UUID.randomUUID());
    }

//    @GetMapping("/documents")
//    public List<VaultDocument> documents() {
//        return List.of(UUID.randomUUID(), UUID.randomUUID())
//                .stream()
//                .map(uuid->new VaultDocument(uuid, "data.xml", "todo", null))
//                .toList();
//    }

    @GetMapping("/blob")
    public VaultBlob blob(
            @RequestParam
            final String uuid) {
        //return new VaultBlob(UUID.fromString(uuid), Blob.of("data", CommonMimeType.XML, new byte[] {1,2,3,4}));
        return new VaultBlob(UUID.fromString(uuid), new byte[] {1,2,3,4});
    }

    @GetMapping("/clob")
    public VaultClob clob(
            @RequestParam
            final String uuid) {
        //return new VaultBlob(UUID.fromString(uuid), Blob.of("data", CommonMimeType.XML, new byte[] {1,2,3,4}));
        return new VaultClob(UUID.fromString(uuid), "hello\"..'".toCharArray());
    }

}
