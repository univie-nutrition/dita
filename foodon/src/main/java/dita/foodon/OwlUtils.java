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
package dita.foodon;

import java.util.Optional;
import java.util.function.Predicate;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OwlUtils {

    public boolean isRDFSchema(final @Nullable IRI iri) {
        if(iri==null) return false;
        return "http://www.w3.org/2000/01/rdf-schema#"
                .equals(iri.getNamespace());
    }

    public boolean isRDFLabel(final @Nullable IRI iri) {
        return isRDFSchema(iri)
                && "label".equals(iri.getRemainder().orElse(null));
    }

    public boolean hasDbXref(final @Nullable IRI iri) {
        if(iri==null) return false;
        return "http://www.geneontology.org/formats/oboInOwl#"
                    .equals(iri.getNamespace())
                && "hasDbXref"
                    .equals(iri.getRemainder().orElse(null));
    }

    /**
     * Returns FOODON_03400709 given say http://purl.obolibrary.org/obo/FOODON_03400709
     */
    public Optional<String> parseFoodonTermId(final @Nullable IRI iri) {
        var url = Optional.ofNullable(iri)
            .map(IRI::toString)
            .orElse("");
        int c = url.indexOf("/obo/");
        if(c>-1) {
            return Optional.of(url.substring(c+5));
        }
        return Optional.empty();
    }

    /**
     * Returns A0714 given say http://www.langual.org/langual_thesaurus.asp?termid=A0714
     */
    public Optional<String> parseLangualTermId(final @Nullable String url) {
        if(_Strings.isEmpty(url)) return Optional.empty();
        int c = url.indexOf("?termid=");
        if(c>-1) {
            return Optional.of(url.substring(c+8));
        }
        return Optional.empty();
    }

    /**
     * In support of searching literals, whether they match all search terms.
     */
    public Predicate<OWLLiteral> searchAllMatch(final Can<String> searchTerms) {
        final long termCount = searchTerms.size();
        return owlLiteral->searchTerms.stream()
                .filter(owlLiteral.getLiteral().toLowerCase()::contains)
                .count() == termCount;
    }

}
