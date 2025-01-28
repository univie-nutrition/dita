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
package dita.commons.services.rules;

import java.net.URI;
import java.util.List;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.collections.Can;

import org.jspecify.annotations.NonNull;

public interface RuleChecker {

    static enum Criticality {
        INFORMAL,
        WARNING,
        SEVERE
    }

    static record RuleViolation(
            @NonNull Criticality criticality,
            @NonNull String message,
            @Nullable Can<URI> uris) {
        public static RuleViolation informal(final String msg) {
            return new RuleViolation(Criticality.INFORMAL, msg);
        }
        public static RuleViolation warning(final String msg) {
            return new RuleViolation(Criticality.WARNING, msg);
        }
        public static RuleViolation severe(final String msg) {
            return new RuleViolation(Criticality.SEVERE, msg);
        }
        public static RuleViolation informal(final String format, final Object...args) {
            return informal(String.format(format, args));
        }
        public static RuleViolation warning(final String format, final Object...args) {
            return warning(String.format(format, args));
        }
        public static RuleViolation severe(final String format, final Object...args) {
            return severe(String.format(format, args));
        }
        public RuleViolation(
                @NonNull final Criticality criticality,
                @NonNull final String message) {
            this(criticality, message, Can.empty());
        }
        public String formatAsYaml() {
            return message();
        }
        public RuleViolation withURI(final URI uri) {
            return new RuleViolation(criticality, message, Can.of(uri));
        }
        public RuleViolation withURIs(final List<URI> uris) {
            return new RuleViolation(criticality, message, Can.ofCollection(uris));
        }
    }

    Can<RuleViolation> check(@NonNull Class<?> entityType);

    String title();
    String description();

}
