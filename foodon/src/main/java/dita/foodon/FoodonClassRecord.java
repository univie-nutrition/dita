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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

import lombok.Builder;

@Builder
public record FoodonClassRecord(
        OWLOntology ontology,
        OWLClass owlClass,
        Optional<String> foodonTermId,
        Optional<String> langualTermId,
        Optional<String> rdfLabel) {

    public static FoodonClassRecord create(
            final OWLOntology ontology,
            final OWLClass owlClass) {

        var builder = FoodonClassRecord.builder()
                .ontology(ontology)
                .owlClass(owlClass)
                .foodonTermId(Optional.empty())
                .langualTermId(Optional.empty())
                .rdfLabel(Optional.empty());

        builder.foodonTermId(OwlUtils.parseFoodonTermId(owlClass.getIRI()));

        var annots = Extractors.annotations(ontology, owlClass);
        annots.forEach(
                annot->{
                    var annotIri = annot.getProperty().getIRI();
                    var annotLiteral = annot.literalValue()
                            .map(OWLLiteral::getLiteral);

                    if(OwlUtils.isRDFLabel(annotIri)) {
                        builder.rdfLabel(annotLiteral);
                    } else if(annotLiteral.isPresent()
                            && OwlUtils.hasDbXref(annotIri)) {
                        builder.langualTermId(OwlUtils.parseLangualTermId(annotLiteral.get()));
                    } else {
                        //debug
//                        System.err.printf("annot: %s|%s|%s %n",
//                                annotIri.getNamespace(),
//                                annotIri.getRemainder(),
//                                annotLiteral);
                    }
                });

        return builder.build();
    }

    @Override
    public final String toString() {
        return String.format("FoodonClassRecord[%s]",
                Stream.of(
                        foodonTermId.map(s->"foodonTermId="+s),
                        langualTermId.map(s->"langualTermId="+s),
                        rdfLabel.map(s->"rdfLabel="+s))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining(", ")));
    }

}
