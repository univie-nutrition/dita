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

import org.apache.causeway.commons.collections.Can;

import lombok.NonNull;

public interface RuleChecker {

    static enum Criticality {
        INFORMAL,
        WARNING,
        SEVERE
    }

    static record RuleViolation(
            @NonNull Criticality criticality,
            @NonNull String message) {
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
    }

    Can<RuleViolation> check(@NonNull Class<?> entityType);

}
