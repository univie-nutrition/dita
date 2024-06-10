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
package dita.commons.sid;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._Strings;

import lombok.NonNull;

/**
 * A Semantic Identifier references data objects across system boundaries.
 */
public record SemanticIdentifier (
        /**
         * Identifies the system (and optionally version) of the referenced data object.
         */
        String systemId,
        /**
         * Uniquely identifies the data object within the system.
         */
        String objectId) implements Comparable<SemanticIdentifier> {

    @Override
    public int compareTo(final @Nullable SemanticIdentifier o) {
        return compare(this, o);
    }

    /**
     * @param delimiter that separates systemId and objectId
     */
    public String fullFormat(final @NonNull String delimiter) {
        return _Strings.nullToEmpty(systemId) + delimiter + _Strings.nullToEmpty(objectId);
    }

    // -- UTILITY

    public static int compare(
            final @Nullable SemanticIdentifier a,
            final @Nullable SemanticIdentifier b) {
        if(a==null) return b==null
                    ? 0
                    : -1;
        if(b==null) return 1;
        final int c = _Strings.compareNullsFirst(a.systemId(), b.systemId());
        return c!=0
            ? c
            : _Strings.compareNullsFirst(a.objectId(), b.objectId());
    }

}
