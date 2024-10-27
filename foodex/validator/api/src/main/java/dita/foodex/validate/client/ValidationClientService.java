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
package dita.foodex.validate.client;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.viewer.restfulobjects.client.RestfulClient;
import org.apache.causeway.viewer.restfulobjects.client.RestfulClientConfig;

import dita.foodex.validate.api.ValidationResponse;
import lombok.Getter;
import lombok.Setter;


@Service
public class ValidationClientService {

    public static final String KEY_VALIDATOR_RESOURCE_URL = "dita.foodex.validator.resourceUrl";
    public static final String KEY_VALIDATOR_DEBUG = "dita.foodex.validator.verbose";

    private RestfulClient client;

    @Getter @Setter private String validatorResourceUrl;
    @Getter @Setter private Boolean verbose;

    @PostConstruct
    public void init() {

        if(_NullSafe.isEmpty(validatorResourceUrl)) {
            validatorResourceUrl = System.getProperty(KEY_VALIDATOR_RESOURCE_URL);
        }
        if(_NullSafe.isEmpty(validatorResourceUrl)) {
            validatorResourceUrl = "http://localhost:8080/"; // fallback
        }

        if(verbose==null) {
            var verboseString = System.getProperty(KEY_VALIDATOR_DEBUG);
            if(verboseString!=null) {
                try {
                    verbose = Boolean.valueOf(verboseString);
                } catch (Exception e) {
                    verbose = null;
                }
            }
        }
        if(verbose==null) {
            verbose=false; // fallback
        }

        var clientConfig = RestfulClientConfig.builder()
        .restfulBaseUrl(validatorResourceUrl)
        .useRequestDebugLogging(verbose) //for debugging
        .build();

        client = RestfulClient.ofConfig(clientConfig);
    }

    public ValidationResponse validate(final String fullCode) {

        if(_NullSafe.isEmpty(fullCode)) {
            return ValidationResponse.empty();
        }

        Builder request = client.request("validate");

        Entity<String> args = Entity.json("{\"fullCode\" : \"" + fullCode + "\"}");

        Response response = request.post(args);

        Try<ValidationResponse> digest = client.digest(response, ValidationResponse.class);

        if(digest.isSuccess()) {
            return digest.getValue().orElse(null);
        }

        return ValidationResponse.failure(fullCode, (Exception) digest.getFailure().get());


    }

}
