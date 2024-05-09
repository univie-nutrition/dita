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

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;

public record SemanticIdentifierSet(
        /**
         * Expects elements already to be sorted.<br>
         * Use factory method {@link SemanticIdentifierSet#ofCollection} if unsure.
         */
        Can<SemanticIdentifier> elements) {

    private static final SemanticIdentifierSet EMPTY = new SemanticIdentifierSet(Can.empty());

    public static SemanticIdentifierSet empty() {
        return EMPTY;
    }

    public static SemanticIdentifierSet nullToEmpty(final @Nullable SemanticIdentifierSet set) {
        return set!=null
                ? set
                : EMPTY;
    }

    public static SemanticIdentifierSet ofCollection(final @Nullable Collection<SemanticIdentifier> collection) {
        if(_NullSafe.isEmpty(collection)) {
            return EMPTY;
        }
        return new SemanticIdentifierSet(Can.ofCollection(collection).sorted(SemanticIdentifierSet::compare));
    }
    public static SemanticIdentifierSet ofStream(final @Nullable Stream<SemanticIdentifier> stream) {
        var ids = Can.ofStream(stream);
        return ids.isEmpty()
                ? EMPTY
                : new SemanticIdentifierSet(ids.sorted(SemanticIdentifierSet::compare));
    }


    // -- UTILITY

    public static int compare(final SemanticIdentifier a, final SemanticIdentifier b) {
        int c = _Strings.compareNullsFirst(a.systemId(), b.systemId());
        return c!=0
            ? c
            : _Strings.compareNullsFirst(a.objectId(), b.objectId());
    }

}
