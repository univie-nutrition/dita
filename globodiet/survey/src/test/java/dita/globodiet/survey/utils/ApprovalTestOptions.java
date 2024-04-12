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
package dita.globodiet.survey.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.approvaltests.core.Options;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApprovalTestOptions {

    public Options jsonOptions() {
        var objectMapper = new ObjectMapper();
        return new Options()
            .withScrubber(s -> {
                try {
                    String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(objectMapper.readTree(s));
                    return prettyJson;
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            })
            .forFile()
            .withExtension(".json");
    }

    public Options yamlOptions() {
        return new Options()
            .forFile()
            .withExtension(".yaml");
    }

}
