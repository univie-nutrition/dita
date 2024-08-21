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
package dita.foodon.term;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;

import dita.commons.sid.SemanticIdentifier;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

/**
 * DRAFT: Represents a term within a hierarchy of ontology terms.
 * @see "https://univie-nutrition.github.io/dita/component-main/1.0.0/designdocs/OntologyTermAndAnnotations.html"
 */
public record OntoTerm(
        SemanticIdentifier termId,

        @JsonIgnore
        ObjectRef<OntoTerm> broadenedRef,

        List<OntoTerm> narrowed,

        /** common term (typically in English) */
        String name
        ) {

    public OntoTerm(
            @Nullable final OntoTerm parent,
            final SemanticIdentifier termId,
            final String name) {
        this(   termId,
                parent!=null ? ObjectRef.of(parent) : ObjectRef.empty(),
                new ArrayList<>(),
                name);
        if(parent!=null) {
            parent.narrowed.add(this);
        }
    }

    public Optional<OntoTerm> broadened() {
        return Optional.ofNullable(broadenedRef.getValue());
    }

}
