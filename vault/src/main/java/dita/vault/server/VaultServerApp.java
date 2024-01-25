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

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import dita.commons.types.ResourceFolder;
import dita.vault.model.VaultConfig;
import dita.vault.server.VaultServerApp.ServerConfiguration;
import dita.vault.server.blobstore.filesystem.FilesystemBlobStoreService;

/**
 * Bootstrap the application.
 */
@SpringBootApplication
@Import({
    ServerConfiguration.class,
    VaultResource.class,
})
public class VaultServerApp extends SpringBootServletInitializer {

    /**
     * @implNote this is to support the <em>Spring Boot Maven Plugin</em>, which auto-detects an
     * entry point by searching for classes having a {@code main(...)}
     */
    public static void main(final String[] args) {
        SpringApplication.run(new Class[] { VaultServerApp.class }, args);
    }


    @Configuration
    @Import({
        FilesystemBlobStoreService.class
    })
    public static class ServerConfiguration {
        @Bean
        public VaultConfig vaultConfig(
                @Value("${dita.vault.dir}")
                final String vaultDir) {
            var vaultDirectory = new ResourceFolder(new File(vaultDir));
            return new VaultConfig("1.0.0", vaultDirectory);
        }
    }

}
