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
package dita.recall24.immutable;

import java.math.BigDecimal;

import javax.measure.Quantity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.causeway.commons.internal.base._Strings;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@Deprecated
/**
 * Implements {@link dita.recall24.api.Ingredient24} as Java record type.
 * @see Ingredient
 */
public record Ingredient(
        @JsonIgnore
        ObjectRef<Record> parentRecordRef,
        String sid,
        String name,
        String facetSids,
        BigDecimal rawPerCookedRatio,
        Quantity<?> quantityCooked
        //,double fractionRelativeToParentRecipe
        ) implements dita.recall24.api.Ingredient24, RecallNode {

    public static Ingredient of(
            final String sid,
            final String name,
            final String facetSids,
            final BigDecimal rawPerCookedRatio,
            final Quantity<?> quantityCooked) {
        var ingredient24 = new Ingredient(ObjectRef.empty(), sid, name, facetSids, rawPerCookedRatio, quantityCooked);
        return ingredient24;
    }

    @Override
    public Record parentRecord() {
        return parentRecordRef.getValue();
    }

    public Ingredient withSid(final String sid) {
        return of(sid, name(), facetSids(), rawPerCookedRatio(), quantityCooked());
    }

    public QualifiedMap.QualifiedMapKey qualifiedMapKey(final String systemId) {
        var source = new SemanticIdentifier(systemId, sid());
        return new QualifiedMapKey(source,
                SemanticIdentifierSet.ofStream(_Strings.splitThenStream(facetSids(), ",")
                        .map(facetId->new SemanticIdentifier(systemId, facetId))));
    }

}
