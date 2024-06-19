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
package dita.commons.qmap;

import java.util.function.UnaryOperator;

import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;

/**
 * A Qualified Map Entry relates a data object from one system to another,
 * respecting a qualifier.
 */
public record QualifiedMapEntry(
        
        /**
         * Key holding the semantic identifier of the data object that is mapped from
         * and constraints under which this map entry is applicable.
         */
        QualifiedMapKey key,
        
        /**
         * Semantic identifier of the data object that is mapped to.
         */
        SemanticIdentifier target) {
    
    
    public QualifiedMapEntry(
            SemanticIdentifier source,
            SemanticIdentifierSet qualifier,
            SemanticIdentifier target) {
        this(new QualifiedMapKey(source, qualifier), target);
    }
    
    /**
     * Semantic identifier of the data object that is mapped from.
     */
    public SemanticIdentifier source() {
        return key.source();
    }
    
    /**
     * Constraints under which this map entry is applicable.
     */
    public SemanticIdentifierSet qualifier() {
        return key.qualifier();
    }
    
    // -- WITHER

    @Deprecated
    public QualifiedMapEntry withSource(final SemanticIdentifier source) {
        return new QualifiedMapEntry(source, qualifier(), target);
    }
    @Deprecated
    public QualifiedMapEntry withQualifier(final SemanticIdentifierSet qualifier) {
        return new QualifiedMapEntry(source(), qualifier, target);
    }
    public QualifiedMapEntry withKey(final QualifiedMapKey key) {
        return new QualifiedMapEntry(key, target);
    }
    public QualifiedMapEntry withTarget(final SemanticIdentifier target) {
        return new QualifiedMapEntry(key, target);
    }

    // -- MAPPER

    @Deprecated
    public QualifiedMapEntry mapSource(final UnaryOperator<SemanticIdentifier> sourceMapper) {
        return withSource(sourceMapper.apply(source()));
    }
    @Deprecated
    public QualifiedMapEntry mapQualifier(final UnaryOperator<SemanticIdentifierSet> qualifierMapper) {
        return withQualifier(qualifierMapper.apply(qualifier()));
    }
    public QualifiedMapEntry mapKey(final UnaryOperator<QualifiedMapKey> keyMapper) {
        return withKey(keyMapper.apply(key()));
    }
    public QualifiedMapEntry mapTarget(final UnaryOperator<SemanticIdentifier> targetMapper) {
        return withTarget(targetMapper.apply(target));
    }

    // -- ADVANCED MAPPER

    public QualifiedMapEntry mapQualifierElementwise(final UnaryOperator<SemanticIdentifier> qualifierElementwiseMapper) {
        return mapQualifier(set->SemanticIdentifierSet.ofStream(set.elements().stream().map(qualifierElementwiseMapper)));
    }
    
}
