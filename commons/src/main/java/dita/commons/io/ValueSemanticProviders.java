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
package dita.commons.io;

import org.apache.causeway.applib.value.semantics.Parser;
import org.apache.causeway.applib.value.semantics.Renderer;
import org.apache.causeway.applib.value.semantics.ValueDecomposition;
import org.apache.causeway.applib.value.semantics.ValueSemanticsAbstract;
import org.apache.causeway.applib.value.semantics.ValueSemanticsProvider;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.schema.common.v2.ValueType;

import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier;

@UtilityClass
public class ValueSemanticProviders {

    public static class SidValueSemantics
    extends ValueSemanticsAbstract<SemanticIdentifier>
    implements
        Parser<SemanticIdentifier>,
        Renderer<SemanticIdentifier> {

        @Override
        public Class<SemanticIdentifier> getCorrespondingClass() {
            return SemanticIdentifier.class;
        }

        @Override
        public ValueType getSchemaValueType() {
            return ValueType.STRING; // this type can be easily converted to string and back
        }

        // -- COMPOSER

        @Override
        public ValueDecomposition decompose(final SemanticIdentifier value) {
            return decomposeAsString(value, SemanticIdentifier::toStringNoBox, ()->null);
        }

        @Override
        public SemanticIdentifier compose(final ValueDecomposition decomposition) {
            return composeFromString(decomposition, this::parseElseNull, ()->null);
        }

        private SemanticIdentifier parseElseNull(final String sid) {
            return SemanticIdentifier.parse(sid);
        }

        // -- RENDERER

        @Override
        public String titlePresentation(final ValueSemanticsProvider.Context context, final SemanticIdentifier value) {
            return renderTitle(value, SemanticIdentifier::toStringNoBox);
        }

        @Override
        public String htmlPresentation(final ValueSemanticsProvider.Context context, final SemanticIdentifier value) {
            return renderHtml(value, this::toHtmlLink);
        }

        private String toHtmlLink(final SemanticIdentifier sid) {
            return """
                    <span class="badge rounded-pill bg-light" style="color: #976140;">%s</span>
                    <span class="badge rounded-pill bg-light">%s</span>"""
                        .formatted(sid.systemId(), sid.objectId());
        }

        // -- PARSER

        @Override
        public String parseableTextRepresentation(final ValueSemanticsProvider.Context context, final SemanticIdentifier value) {
            return value != null ? value.toString(): null;
        }

        @Override
        public SemanticIdentifier parseTextRepresentation(final ValueSemanticsProvider.Context context, final String text) {
            return parseElseNull(_Strings.blankToNullOrTrim(text));
        }

        @Override
        public int typicalLength() {
            return 100;
        }

        @Override
        public int maxLength() {
            return 2083;
        }

    }

}
