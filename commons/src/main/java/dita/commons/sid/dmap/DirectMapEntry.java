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
package dita.commons.sid.dmap;

import java.util.function.UnaryOperator;

import dita.commons.sid.SemanticIdentifier;

/**
 * A Qualified Map Entry relates a data object from one system to another,
 * respecting a qualifier.
 */
public record DirectMapEntry(

        /**
         * Semantic identifier of the data object that is mapped from.
         */
        SemanticIdentifier source,

        /**
         * Semantic identifier of the data object that is mapped to.
         */
        SemanticIdentifier target) {

    // -- WITHER

    public DirectMapEntry withKey(final SemanticIdentifier source) {
        return new DirectMapEntry(source, target);
    }
    public DirectMapEntry withTarget(final SemanticIdentifier target) {
        return new DirectMapEntry(source, target);
    }

    // -- MAPPER

    public DirectMapEntry mapSource(final UnaryOperator<SemanticIdentifier> sourceMapper) {
        return withKey(sourceMapper.apply(source()));
    }
    public DirectMapEntry mapTarget(final UnaryOperator<SemanticIdentifier> targetMapper) {
        return withTarget(targetMapper.apply(target));
    }

}
