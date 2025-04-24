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
package dita.globodiet.survey.util;

import java.util.Optional;

import org.jspecify.annotations.Nullable;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.util.FormatUtils;

record CodeFromName(@Nullable String code) {

    static CodeFromName parseAssocFood(final String nameAndCode) {
        return parse("assocFood", nameAndCode);
    }
    static CodeFromName parseAssocRecipe(final String nameAndCode) {
        return parse("assocRecp", nameAndCode);
    }
    private static CodeFromName parse(final String attributeName, final String nameAndCode) {
        int c1 = nameAndCode.indexOf("{");
        return (c1==-1)
            ? new CodeFromName(null)
            : new CodeFromName(
                lookupAttribute(attributeName, nameAndCode)
                    .map(code->FormatUtils.fillWithLeadingZeros(5, code))
                    .orElse(null));
    }
    Optional<SemanticIdentifier> associatedRecipeSid(final SystemId systemId) {
        return Optional.ofNullable(code())
                .map(code->ObjectId.Context.RECIPE.sid(systemId, code));
    }

    /// looks for `value` in `{.., key=value,..}`
    private static Optional<String> lookupAttribute(final String attributeKey, final String curlybracedAttributeList) {
        final String commaSeparatedKeyValuePairs = TextUtils.cutter(curlybracedAttributeList)
            .keepAfter("{")
            .keepBeforeLast("}")
            .getValue();
        final String pattern = attributeKey + "=";

        return _Strings.splitThenStream(commaSeparatedKeyValuePairs, ",")
            .map(String::trim)
            .filter(kv->kv.startsWith(pattern))
            .map(kv->kv.substring(pattern.length()))
            .filter(StringUtils::hasLength)
            .findFirst();
    }

}

