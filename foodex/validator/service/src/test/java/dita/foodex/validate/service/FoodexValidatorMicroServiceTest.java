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

import java.io.IOException;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.viewer.restfulobjects.client.RestfulClient;
import org.apache.causeway.viewer.restfulobjects.client.RestfulClientConfig;

import dita.foodex.validate.api.ValidationResponse;

class FoodexValidatorMicroServiceTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test @Disabled("needs a backend service spinup")
    void test() throws JsonParseException, JsonMappingException, IOException {

        RestfulClientConfig clientConfig = RestfulClientConfig.builder()
        .restfulBaseUrl("http://localhost:8080/")
        // setup request/response debug logging
        .useRequestDebugLogging(true) // default = false
        .build();

        RestfulClient client = RestfulClient.ofConfig(clientConfig);

        Builder request = client.request("validate");

        Entity<String> args = Entity.json("{\"fullCode\" : \"A01SP#F04.A0EZM$F22.A07SS$F28.A07HS\"}");

        Response response = request.post(args);

        Try<ValidationResponse> digest = client.digest(response, ValidationResponse.class);

        if(digest.isSuccess()) {
            System.out.println("result: "+ digest.getValue().orElse(null));
        } else {
            digest.getFailure().get().printStackTrace();
        }

        assertTrue(digest.isSuccess());

    }



}
