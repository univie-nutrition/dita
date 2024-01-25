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
package dita.vault.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.client.RestTemplate;

import org.apache.causeway.applib.value.Clob;

import dita.vault.model.VaultConfig;
import dita.vault.model.VaultDocument;
import lombok.SneakyThrows;

public record VaultTemplate(URI url) {

    public VaultTemplate(final String url) {
        this(createURL(url));
    }

    public VaultConfig getConfig() {
        var restTemplate = new RestTemplate();
        return restTemplate.getForObject(url.resolve("/config"), VaultConfig.class);
    }

    public VaultDocument getDocument(final UUID uuid) {
        var restTemplate = new RestTemplate();
        return restTemplate.getForObject(url.resolve("/document?uuid=" + uuid), VaultDocument.class);
    }

    public void putDocument(final UUID uuid, final Clob clob) {
        // TODO Auto-generated method stub

    }

    public void removeDocument(final UUID uuid) {
        // TODO Auto-generated method stub

    }

    // -- HELPER

    @SneakyThrows
    private static URI createURL(final String url) {
        return URI.create(url);
    }

}
