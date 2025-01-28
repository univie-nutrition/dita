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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import org.springframework.util.function.ThrowingConsumer;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.DataSource;

import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import io.github.causewaystuff.commons.compression.SevenZUtils;

/**
 * @see <a href="https://owlapi.sourceforge.net/owled2011_tutorial.pdf">Tutorial</a>
 */
@RequiredArgsConstructor
@Log4j2
public final class FoodOntologyCatalog {

    @Getter @Accessors(fluent=true)
    private final @NonNull OWLOntology rootOntology;

    @Getter @Accessors(fluent=true)
    private final OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();

    public OWLOntologyManager manager() {
        return rootOntology.getOWLOntologyManager();
    }

    public OWLDataFactory dataFactory() {
        return manager().getOWLDataFactory();
    }

    public OWLClass owlThing() {
        return dataFactory().getOWLThing();
    }

    public FoodonClassRecord toFoodonClassRecord(final OWLClass owlClass) {
        return FoodonClassRecord.create(rootOntology, owlClass);
    }

    // -- EXTRACTORS

    public Can<OWLAnnotation> annotations(final OWLClass owlClass) {
        return Extractors.annotations(rootOntology(), owlClass);
    }

    public Can<OWLLiteral> annotationLables(final OWLClass owlClass) {
        return Extractors.literals(rootOntology(), owlClass);
    }

    // -- TRAVERSAL

    public Stream<HierarchyEntry> streamDepthFirst(final OWLReasoner reasoner) {
        return new HierarchyStreamer(reasoner)
                .streamDepthFirst(owlThing());
    }

    public void reasonWith(final ThrowingConsumer<OWLReasoner> reasonerConsumer) {
        var reasoner = reasonerFactory().createNonBufferingReasoner(rootOntology);
        try {
            reasonerConsumer.accept(reasoner);
        } finally {
            reasoner.dispose();
        }
    }

    /**
     * Dumps all labels for matching IRIs.
     * @param iri e.g. FOODON, CDNO (case sensitive)
     */
    public void dump(final String iri, final PrintStream out) {
        System.out.println("Ontology : " + rootOntology.getOntologyID());
        System.out.println("Format   : " + manager().getOntologyFormat(rootOntology));
        reasonWith(reasoner->
            streamDepthFirst(reasoner)
                .filter(hEntry->hEntry.owlClass.getIRI()!=null)
                .filter(hEntry->hEntry.owlClass.getIRI().toString().contains(iri))
                //.limit(200)
                .forEach(hEntry->
                    out.println(_Strings.of(hEntry.nestingLevel() * 2, ' ')
                            + labelFor(reasoner.getRootOntology(), hEntry.owlClass())))
        );
    }

    /**
     * @param iri e.g. FOODON, CDNO (case sensitive)
     * @param searchTerms e.g. beverage (case insensitive)
     */
    public Can<FoodonClassRecord> searchInAnnotationLabels(
            final String iri,
            final String ... searchTerms) {

        var searchTermsLower = Can.ofArray(searchTerms)
             .map(String::toLowerCase);

        return rootOntology.getClassesInSignature().stream()
            .filter(owlClass->Optional.ofNullable(owlClass.getIRI())
                    .flatMap(IRI::getRemainder)
                    .map(r->r.contains(iri))
                    .orElse(false))
            .map(owlClass->{
                var literals = Extractors.literals(rootOntology, owlClass);
                if(literals.stream()
                        .anyMatch(OwlUtils.searchAllMatch(searchTermsLower))) {
                    return FoodonClassRecord.create(rootOntology, owlClass);
                }
                return null;
            })
            .collect(Can.toCan());
    }

    // variant with reasoner
    public Can<FoodonClassRecord> searchInAnnotationLabels2(
            final String iri,
            final String search) {

        var result = new ArrayList<OWLClass>();
        var searchLower = search.toLowerCase();

        reasonWith(reasoner->
            streamDepthFirst(reasoner)
                .filter(hEntry->hEntry.owlClass.getIRI()!=null)
                .filter(hEntry->hEntry.owlClass.getIRI().toString().contains(iri))
                .forEach(hEntry->{
                    var owlClass = hEntry.owlClass;
                    var literals = Extractors.literals(rootOntology, owlClass);
                    if(literals.stream()
                        .anyMatch(lit->lit.getLiteral().toLowerCase().contains(searchLower))) {
                        result.add(owlClass);
                    }
                })
        );
        return Can.ofCollection(result)
                .map(owlClass->FoodonClassRecord.create(rootOntology, owlClass));
    }

    // -- HIERARCHY ENTRY

    public record HierarchyEntry(
            OWLClass owlClass,
            int nestingLevel) {
    }

    // -- SINGLETON

    @Getter(lazy=true, value = AccessLevel.PRIVATE)
    private static final FoodOntologyCatalog INSTANCE = load();

    public static FoodOntologyCatalog instance() {
        return getINSTANCE();
    }

    // -- HELPER

    private static FoodOntologyCatalog load() {
        log.info("about to load 'foodon.owl' ontology");
        var ds = SevenZUtils.decompress(DataSource.ofResource(FoodOntologyCatalog.class, "/foodon.owl.7z"));

        var manager = OWLManager.createOWLOntologyManager();
        var rootOntology = ds.tryReadAndApply(manager::loadOntologyFromOntologyDocument)
                .valueAsNonNullElseFail();

        log.info("done loading 'foodon.owl' ontology");
        return new FoodOntologyCatalog(rootOntology);
    }

    private record HierarchyStreamer(
            OWLReasoner reasoner) {
        /**
         * Visits the class hierarchy for the given ontology from this class down.
         * Makes no attempt to deal sensibly with multiple inheritance.
         */
        Stream<HierarchyEntry> streamDepthFirst(final OWLClass owlClass) {
            return stream(owlClass, 0);
        }
        private Stream<HierarchyEntry> stream(
                final OWLClass owlClass, final int level) {
            if (!reasoner.isSatisfiable(owlClass)) return Stream.empty();
            var childStream = reasoner.getSubClasses(owlClass, true).getFlattened().stream()
                    .filter(child->!child.equals(owlClass))
                    .flatMap(child->stream(child, level + 1));
            return Stream.concat(Stream.of(new HierarchyEntry(owlClass, level)), childStream);
        }
    }

    private static String labelFor(final OWLOntology ontology, final OWLClass owlClass) {
        var parts = Extractors.literals(ontology, owlClass)
            .map(OWLLiteral::getLiteral);
        return parts
                .add(owlClass.getIRI().toQuotedString())
                .stream()
                .collect(Collectors.joining("; "));
    }

}
