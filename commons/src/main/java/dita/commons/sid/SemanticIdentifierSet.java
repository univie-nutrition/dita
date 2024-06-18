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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;

public record SemanticIdentifierSet(
        /**
         * Expects elements already to be sorted.<br>
         * Use factory method {@link SemanticIdentifierSet#ofCollection} if unsure.
         */
        Can<SemanticIdentifier> elements) implements Comparable<SemanticIdentifierSet> {

    private static final SemanticIdentifierSet EMPTY = new SemanticIdentifierSet(Can.empty());

    // -- FACTORIES

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
        return new SemanticIdentifierSet(Can.ofCollection(collection).sorted(SemanticIdentifier::compare));
    }

    public static SemanticIdentifierSet ofStream(final @Nullable Stream<SemanticIdentifier> stream) {
        var ids = Can.ofStream(stream);
        return ids.isEmpty()
                ? EMPTY
                : new SemanticIdentifierSet(ids.sorted(SemanticIdentifier::compare));
    }

    public static SemanticIdentifierSet ofStream(
            final @Nullable String systemId,
            final @Nullable Stream<String> facetIds) {
        return facetIds==null
                ? EMPTY
                : SemanticIdentifierSet.ofStream(
                    facetIds
                        .map(facetId->new SemanticIdentifier(systemId, facetId)));
    }

    // --

    @Override
    public int compareTo(final SemanticIdentifierSet o) {
        return compare(this, o);
    }

    /**
     * Skips systemId information.
     * @param delimiter that separates the element objectIds
     */
    public String shortFormat(final String delimiter) {
        return elements().stream()
            .map(SemanticIdentifier::objectId)
            .collect(Collectors.joining(delimiter));
    }

    /**
     * @param delimiter that separates the elements
     */
    public String fullFormat(final String delimiter) {
        return elements().stream()
            .map(elem->elem.fullFormat(":"))
            .collect(Collectors.joining(delimiter));
    }

    // -- UTILITY

    public static int compare(
            final @Nullable SemanticIdentifierSet a,
            final @Nullable SemanticIdentifierSet b) {
        if(a==null) return b==null
                    ? 0
                    : -1;
        if(b==null) return 1;

        final int na = a.elements().size();
        final int nb = b.elements().size();

        for(int i=0; i<Math.max(na, nb); i++) {
            var elemA = a.elements().get(i).orElse(null);
            var elemB = b.elements().get(i).orElse(null);
            final int c = SemanticIdentifier.compare(elemA, elemB);
            if(c!=0) return c;

        }
        return 0;
    }

}
