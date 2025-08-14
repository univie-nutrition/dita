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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.springframework.util.StringUtils;
import org.springframework.util.function.ThrowingFunction;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;

@UtilityClass
class ParseFormatUtils {

    char INNER_DELIMITER = '/';
    char OUTER_DELIMITER = ':';
    String BOX_START = "SID[";
    String BOX_END = "]";
    char QUOTATION_START = '‹';
    char QUOTATION_END = '›';
    char SET_DELIMITER = ',';

    // -- FORMATTER

    String format(final @NonNull SystemId systemId) {
        return StringUtils.hasLength(systemId.version())
                ? _Strings.nullToEmpty(systemId.system()) + INNER_DELIMITER + systemId.version()
                : _Strings.nullToEmpty(systemId.system());
    }
    String format(final @NonNull ObjectId objectId) {
        return StringUtils.hasLength(objectId.context())
                ? objectId.context() + INNER_DELIMITER + escapeLiteral(objectId.objectSimpleId())
                : escapeLiteral(objectId.objectSimpleId());
    }
    String formatBoxed(final @NonNull SemanticIdentifier sid) {
        return BOX_START + formatUnboxed(sid) + BOX_END;
    }
    String formatUnboxed(final @NonNull SemanticIdentifier sid) {
        return sid.systemId().toString()
                + OUTER_DELIMITER + sid.objectId().toString();
    }
    String formatBoxed(final @NonNull SemanticIdentifierSet semanticIdentifierSet) {
        return semanticIdentifierSet.elements().stream()
                .map(ParseFormatUtils::formatBoxed)
                .collect(Collectors.joining(", "));
    }
    String formatUnboxed(final @NonNull SemanticIdentifierSet semanticIdentifierSet) {
        return semanticIdentifierSet.elements().stream()
                .map(ParseFormatUtils::formatUnboxed)
                .collect(Collectors.joining(", "));
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
        if(_Strings.isEmpty(stringified) || stringified.equals("-")) return SemanticIdentifier.empty();
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

    List<SemanticIdentifier> parseSidList(final @Nullable String stringifiedSet) {
        if(_Strings.isEmpty(stringifiedSet)) return List.of();
        // inner class
        class Helper {
            String remainder;
            int nextSetDelimiterIndex(final int offset) {
                final int c1 = remainder.indexOf(SET_DELIMITER, offset);
                if(c1>-1) {
                    final int c2 = remainder.indexOf(QUOTATION_START, offset);
                    if(c2>-1) {
                        final int c3 = remainder.indexOf(QUOTATION_END, offset);
                        if(c3<0) {
                            throw _Exceptions.illegalArgument("escaped literal must end with " + QUOTATION_END + " character");
                        }
                        if(c2<c1) {
                            // skip ahead after c3
                            return nextSetDelimiterIndex(c3 + 1);
                        }
                    }
                }
                return c1;
            }
        }
        var helper = new Helper();
        helper.remainder = stringifiedSet;
        int nextSetDelimiterIndex = 0;
        var sids = new ArrayList<SemanticIdentifier>();
        while((nextSetDelimiterIndex = helper.nextSetDelimiterIndex(0)) > -1) {
            sids.add(parseSid(helper.remainder.substring(0, nextSetDelimiterIndex).trim()));
            helper.remainder = helper.remainder.substring(nextSetDelimiterIndex + 1).stripLeading();
        }
        sids.add(parseSid(helper.remainder.trim()));
        return sids;
    }

    SemanticIdentifierSet parseSidSet(final @Nullable String stringifiedSet) {
        return SemanticIdentifierSet.ofCollection(parseSidList(stringifiedSet));
    }

    // -- VALIDATE

    String validate(final @Nullable String in) {
        var arg = _Strings.nullToEmpty(_Strings.trim(in));
        streamInvalidChars(arg)
            .forEach(codePoint->{
                throw _Exceptions.illegalArgument(
                        "SemanticIdentifier parts may not contain any special characters. "
                        + "Character '%s' in '%s'",
                        "" + (char)codePoint,
                        in);
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
        if(stringIsQuotationMarked(arg)) {
            return _Strings.substring(arg, 1, -1);
        }
        return arg;
    }

    private IntStream streamInvalidChars(final @Nullable String in) {
        return _Strings.nullToEmpty(in).codePoints()
            .filter(codePoint->codePoint!='.')
            .filter(codePoint->codePoint!='-')
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
                    "literal must not contain " + QUOTATION_START + " character in '%s'", in);
        }
        if(in.indexOf(QUOTATION_END)>-1) {
            throw _Exceptions.illegalArgument(
                    "literal must not contain " + QUOTATION_END + " character in '%s'", in);
        }
    }

    /**
     * Whether has quotation marks at begin and end.
     */
    private boolean stringIsQuotationMarked(final @Nullable String in) {
        if(_Strings.isEmpty(in)) return false;
        if(in.startsWith("" + QUOTATION_START)) {
            if(!in.endsWith("" + QUOTATION_END)) {
                throw _Exceptions.illegalArgument("escaped literal must end with " + QUOTATION_END + " character");
            }
            if(in.length()<2) {
                throw _Exceptions.illegalArgument("escaped literal must at least have length 2");
            }
            return true;
        }
        return false;
    }

}
