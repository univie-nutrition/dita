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

import java.util.function.BiFunction;
import java.util.stream.IntStream;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.util.function.ThrowingFunction;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;

@UtilityClass
class _Utils {

    char INNER_DELIMITER = '/';
    char OUTER_DELIMITER = ':';
    String BOX_START = "SID[";
    String BOX_END = "]";
    char QUOTATION_START = '‹';
    char QUOTATION_END = '›';

    // -- FORMATTER

    String format(final @NonNull SystemId systemId) {
        return StringUtils.hasLength(systemId.version())
                ? _Strings.nullToEmpty(systemId.system()) + INNER_DELIMITER + systemId.version()
                : _Strings.nullToEmpty(systemId.system());
    }
    String format(final @NonNull ObjectId objectId) {
        return StringUtils.hasLength(objectId.context())
                ? objectId.context() + INNER_DELIMITER + escapeLiteral(objectId.object())
                : escapeLiteral(objectId.object());
    }
    String formatBoxed(final @NonNull SemanticIdentifier sid) {
        return BOX_START + formatUnboxed(sid) + BOX_END;
    }
    String formatUnboxed(final @NonNull SemanticIdentifier sid) {
        return sid.systemId().toString()
                + OUTER_DELIMITER + sid.objectId().toString();
    }

    // -- PARSER

    SystemId parseSystemId(final @Nullable String stringified) {
        if(_Strings.isEmpty(stringified)) return SystemId.empty();
        return splitAndApply(stringified, INNER_DELIMITER,
                (lhs, rhs)->new SystemId(
                    lhs,
                    rhs),
                _->new SystemId(stringified));
    }

    ObjectId parseObjectId(final @Nullable String stringified) {
        if(_Strings.isEmpty(stringified)) return ObjectId.empty();

        int c1 = stringified.indexOf(INNER_DELIMITER);
        int c2 = stringified.indexOf(QUOTATION_START);
        if(c1>-1
                && c2>-1
                && c2<c1) {
            // if first ' comes before first /, then the / is part of the escaped literal
            return new ObjectId(unescapeLiteral(stringified));
        }

        return splitAndApply(stringified, INNER_DELIMITER,
                (lhs, rhs)->new ObjectId(
                    lhs,
                    unescapeLiteral(rhs)),
                _->new ObjectId(unescapeLiteral(stringified)));
    }

    /**
     * Parses both boxed and un-boxed formats.
     */
    SemanticIdentifier parseSid(final @Nullable String stringified) {
        if(_Strings.isEmpty(stringified)) return SemanticIdentifier.empty();
        var sid = stringified;
        if(stringified.startsWith(BOX_START)) {
            if(!stringified.endsWith(BOX_END)) {
                throw _Exceptions.illegalArgument("cannot parse '%s' as SemanticIdentifier (missing closing of box)",
                        stringified);
            }
            sid = sid.substring(BOX_START.length(), sid.length()-BOX_END.length());
        }
        return splitAndApply(sid, OUTER_DELIMITER,
                (lhs, rhs)->new SemanticIdentifier(
                    SystemId.parse(lhs),
                    ObjectId.parse(rhs)),
                _->{throw _Exceptions.illegalArgument("cannot parse '%s' as SemanticIdentifier", stringified);});
    }

    // -- VALIDATE

    String validate(final @Nullable String in) {
        var arg = _Strings.nullToEmpty(_Strings.trim(in));
        streamInvalidChars(arg)
            .forEach(codePoint->{
                throw _Exceptions.illegalArgument("SemanticIdentifier parts may not contain any special characters. '%s'",
                        "" + (char)codePoint);
            });
        return arg;
    }

    String validateObject(final String in) {
        var arg = _Strings.nullToEmpty(in);
        assertStringHasNoInvalidQuotationMarkers(arg);
        return arg;
    }

    // -- HELPER

    private String escapeLiteral(final @NonNull String in) {
        if(streamInvalidChars(in).findAny().isEmpty()) {
            return in; // pass-through
        }
        assertStringHasNoInvalidQuotationMarkers(in);
        return "" + QUOTATION_START + in + QUOTATION_END;

    }
    private String unescapeLiteral(final @Nullable String in) {
        var arg = _Strings.nullToEmpty(in);
        if(arg.startsWith("" + QUOTATION_START)) {
            if(!arg.endsWith("" + QUOTATION_END)) {
                throw _Exceptions.illegalArgument("escaped literal must end with " + QUOTATION_END + " character");
            }
            if(arg.length()<2) {
                throw _Exceptions.illegalArgument("escaped literal must at least have length 2");
            }
            return _Strings.substring(arg, 1, -1);
        }
        return arg;
    }

    private IntStream streamInvalidChars(final @Nullable String in) {
        return _Strings.nullToEmpty(in).codePoints()
            .filter(codePoint->codePoint!='.')
            .filter(codePoint->!Character.isJavaIdentifierPart(codePoint));
    }

    private <T> T splitAndApply(
            final @Nullable String s,
            final @Nullable int delimiterChar,
            final BiFunction<String, String, T> fun,
            final ThrowingFunction<String, T> onMissingDelimiter) {
        if(_Strings.isEmpty(s)) return fun.apply(null, null);
        int c = s.indexOf(delimiterChar);
        if(c<0) return onMissingDelimiter.apply(s);
        return fun.apply(
                s.substring(0, c),
                s.substring(c+1));
    }

    private void assertStringHasNoInvalidQuotationMarkers(final @Nullable String in) {
        if(_Strings.isEmpty(in)) return;
        if(in.indexOf(QUOTATION_START)>-1) {
            throw _Exceptions.illegalArgument(
                    "literal must not contain " + QUOTATION_START + " character");
        }
        if(in.indexOf(QUOTATION_END)>-1) {
            throw _Exceptions.illegalArgument(
                    "literal must not contain " + QUOTATION_END + " character");
        }
    }

}
