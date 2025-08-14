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

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.sid.qmap.QualifiedMap.QualifiedMapKey;

@UtilityClass
public class JpaConverters {

    @Converter(autoApply = true)
    public static class SemanticIdentifierConverter implements AttributeConverter<SemanticIdentifier, String> {
        @Override
        public String convertToDatabaseColumn(final SemanticIdentifier v) {
            return v!=null
                ? v.toStringNoBox()
                : null;
        }
        @Override
        public SemanticIdentifier convertToEntityAttribute(final String v) {
            try {
                return SemanticIdentifier.parse(v);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
    }

    @Converter(autoApply = true)
    public static class SemanticIdentifierSetConverter implements AttributeConverter<SemanticIdentifierSet, String> {
        @Override
        public String convertToDatabaseColumn(final SemanticIdentifierSet v) {
            return v!=null
                ? v.toStringNoBox()
                : null;
        }
        @Override
        public SemanticIdentifierSet convertToEntityAttribute(final String v) {
            try {
                return SemanticIdentifierSet.parse(v);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
    }

    @Converter(autoApply = true)
    public static class QualifiedMapKeyConverter implements AttributeConverter<QualifiedMapKey, String> {
        @Override
        public String convertToDatabaseColumn(final QualifiedMapKey v) {
            return v!=null
                ? v.fullFormat(",", ",")
                : null;
        }
        @Override
        public QualifiedMapKey convertToEntityAttribute(final String v) {
            try {
                var elements = SemanticIdentifierSet.parse(v).elements();
                return elements.getFirst()
                    .map(source->new QualifiedMapKey(source, new SemanticIdentifierSet(elements.remove(0))))
                    .orElse(null);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
    }

}
