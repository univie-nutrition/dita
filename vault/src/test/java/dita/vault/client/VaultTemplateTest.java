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

import java.util.UUID;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;

import dita.commons.types.ResourceFolder;
import dita.vault.DitaModuleVault;
import dita.vault.model.VaultClob;

@SpringBootTest(classes = {
        DitaModuleVault.class
})
class VaultTemplateTest {

    private int port = 8081;
    private VaultTemplate vaultTemplate = new VaultTemplate("http://localhost:" + port);

    //@Test
    void getVersion() {
        assertThat(vaultTemplate.getConfig().version())
            .contains("1.0.0");
    }

//    @Test
//    void getDocumentByUuid() {
//        var uuid = UUID.randomUUID();
//        assertThat(vaultTemplate.getDocument(uuid))
//            .isEqualTo(new VaultDocument(uuid, "data.xml", "todo", null));
//    }

    //@Test
    void roundTrip() {
        var testFile = ResourceFolder.resourceRoot().relativeFile("node.xml");
        var uuid = UUID.randomUUID();
        var clob = Clob.tryReadUtf8("data.xml", CommonMimeType.XML, testFile)
                .valueAsNonNullElseFail();

        vaultTemplate.putDocument(uuid, clob);
        var document = vaultTemplate.getDocument(uuid);

        vaultTemplate.removeDocument(uuid);

        new VaultClob(uuid, clob.asString().toCharArray()); //TODO not most straight forward
    }

}
