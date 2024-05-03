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

import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

/**
 * A Semantic Identifier references data objects across system boundaries.
 */
public record SemanticIdentifier(
        /**
         * Identifies the system (and optionally version) of the referenced data object.
         */
        String systemId,
        /**
         * Identifies the category, class or hierarchy the data object belongs to.
         */
        String categoryId,
        /**
         * Uniquely identifies the data object within the category and/or system.
         */
        String objectId) {

    public SemanticIdentifier {
        validateArgument(systemId);
        validateArgument(categoryId);
        validateArgument(objectId);
    }

    public static SemanticIdentifier destringify(final String string) {
        if(_NullSafe.size(string)<7) {
            throw _Exceptions.illegalArgument("cannot parse '%s' as SemanticIdentifier (too short)", string);
        }
        var commaSeparatedValues = _Strings.substring(string, 4, -1);
        var parts = _Strings.splitThenStream(commaSeparatedValues, ",")
            .map(String::trim)
            .map(_Strings::emptyToNull)
            .toList();
        _Assert.assertEquals(3, parts.size(), ()->
            String.format("cannot parse '%s' as SemanticIdentifier (invalid token count)", commaSeparatedValues));
        try {
            return new SemanticIdentifier(parts.get(0), parts.get(1), parts.get(2));
        } catch (Exception e) {
            throw _Exceptions.illegalArgument(e, "cannot parse '%s' as SemanticIdentifier", commaSeparatedValues);
        }
    }

    public String stringify() {
        return "SID[" + _Strings.nullToEmpty(systemId)
            + "," + _Strings.nullToEmpty(categoryId)
            + "," + _Strings.nullToEmpty(objectId) + "]";
    }

    // -- HELPER

    private static void validateArgument(@Nullable final String string) {
        if(string!=null) _Assert.assertTrue(string.indexOf(',') + string.indexOf(' ') == -2);
    }

}
