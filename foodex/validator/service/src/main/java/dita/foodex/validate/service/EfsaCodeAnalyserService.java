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
package dita.foodex.validate.service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import main.ICT;

@Service
@Slf4j
public class EfsaCodeAnalyserService {

    @lombok.Value @Builder
    public static class Options {
        String workingDir;
        @Builder.Default String mtxCode = "MTX";
        boolean localCatalogueLookup;
    }

    private static class Validator extends ICT {

        public Validator(final Options options) throws MtxNotFoundException, InterruptedException {
            super(options.getMtxCode(), options.isLocalCatalogueLookup());
        }

        protected String validate(final String fullCode) {
            var warnings = new ArrayList<ValidationResult>();
            super.performWarningChecks(fullCode, true, warnings::add);

            return warnings.stream()
                    .map(ValidationResult::format)
                    .collect(Collectors.joining("|"));
        }

    }

    // --

    @Value("${workingDir}") String workingDir;

    private Validator validator;

    public String validate(final String fullCode) {
        return validator.validate(fullCode);
    }

    @PostConstruct
    public void init() {

        log.info("loading from workingDir = " + workingDir);

        init(Options.builder()
                .workingDir(workingDir)
                .build());
    }

    public void init(final Options options) {
        // set the working directory to find files
        var workDir = options.getWorkingDir() + System.getProperty("file.separator");
        System.setProperty("user.dir", workDir);
        try {
            validator = new Validator(options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void consoleLoggerOnly() {
        ConfigurationBuilder<BuiltConfiguration> builder =
                ConfigurationBuilderFactory.newConfigurationBuilder();

        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        builder.add(console);

        Configurator.initialize(builder.build());
    }

}
