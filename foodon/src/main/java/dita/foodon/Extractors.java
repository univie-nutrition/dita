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

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.EntitySearcher;

import org.apache.causeway.commons.collections.Can;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
class Extractors {

    Stream<OWLAnnotation> streamAnnotations(final OWLOntology ontology, final OWLClass owlClass) {
        var annotations = new ArrayList<OWLAnnotation>();
        var extractor = new OWLAnnotationExtractor(annotations::add);
        EntitySearcher.getAnnotationObjects(owlClass, ontology)
            .forEach((final OWLAnnotation anno)->anno.accept(extractor));
        return annotations.stream();
    }

    Stream<OWLLiteral> streamLiterals(final OWLOntology ontology, final OWLClass owlClass) {
        return streamAnnotations(ontology, owlClass)
            .map(annot->annot.literalValue())
            .filter(Optional::isPresent)
            .map(Optional::get);
    }

    Can<OWLLiteral> literals(final OWLOntology ontology, final OWLClass owlClass) {
        return streamLiterals(ontology, owlClass)
            .collect(Can.toCan());
    }

    // -- HELPER

    @RequiredArgsConstructor
    private static class OWLAnnotationExtractor implements OWLAnnotationObjectVisitor {
        private final Consumer<OWLAnnotation> onAnnotation;
        @Override public void visit(final OWLAnnotation node) {
            onAnnotation.accept(node);
        }
    }

}
