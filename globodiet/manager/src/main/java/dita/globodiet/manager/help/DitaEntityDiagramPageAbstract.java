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
package dita.globodiet.manager.help;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.causewaystuff.companion.codegen.model.Schema;

import org.apache.causeway.applib.id.LogicalType;
import org.apache.causeway.applib.services.metamodel.BeanSort;
import org.apache.causeway.applib.services.metamodel.MetaModelService;
import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;
import org.apache.causeway.extensions.docgen.help.topics.domainobjects.EntityDiagramPageAbstract;



import dita.commons.util.ObjectGraphTransformers;

abstract class DitaEntityDiagramPageAbstract extends EntityDiagramPageAbstract {

    private final Schema.Domain gdParamsSchema;

    protected DitaEntityDiagramPageAbstract(
            final Schema.Domain gdParamsSchema,
            final MetaModelService metaModelService) {
        super(metaModelService);
        this.gdParamsSchema = gdParamsSchema;
    }

    @Override
    final protected boolean accept(final BeanSort beanSort, final LogicalType logicalType) {
        if(!beanSort.isEntity()) return false;
        var ns = "" + logicalType.getNamespace();
        return !ns.equals("causeway")
                && !ns.startsWith("causeway.");
    }

    @Override
    final protected ObjectGraph createObjectGraph() {
        return super.createObjectGraph()
        .transform(ObjectGraphTransformers.packagePrefixRemover("dita."))
        .transform(ObjectGraphTransformers.packagePrefixRemover("globodiet."))
        .transform(ObjectGraphTransformers.relationNameNormalizer())
        // add foreign key relations from schema
        .transform(g->{
            var schemaGraph = gdParamsSchema.asObjectGraph()
                    .transform(ObjectGraphTransformers.virtualObjectAdder())
                    .transform(ObjectGraphTransformers.relationNameNormalizer());

            var idRemapping = new HashMap<String, ObjectGraph.Object>();

            var gObjects = new ArrayList<>(g.objects());

            for(var s: schemaGraph.objects()) {
                gObjects.stream()
                    .filter(x->x.fqName().equals(s.fqName()))
                    .findFirst()
                    .ifPresentOrElse(
                            x->idRemapping.put(s.id(), x),
                            ()->{
                                // virtual objects in schema
                                g.objects().add(s); // potential concurrent modification, hence defensive copy above
                                idRemapping.put(s.id(), s);
                            });
            }

            schemaGraph
                .relations()
                .forEach(rel->{
                    g.relations().add(rel.copy(idRemapping));
                });

            return g;

        })
        .transform(ObjectGraphTransformers.unrelatedObjectRemover())
        .transform(ObjectGraphTransformers.virtualRelationRemover())
        .transform(ObjectGraph.Transformers.relationMerger());
    }


//    @Override
//    public AsciiDoc getContent() {
//        var title = getTitle();
//        var plantumlSource = entityTypesAsDiagram();
//
//        var doc = AsciiDocFactory.doc();
//        doc.setTitle(getTitle());
//
//        final String asciidocSource = AsciiDocWriter.toString(doc);
//
//        var diagramBlock = AsciiDocFactory
//                .diagramBlock(doc, "plantuml", Can.of(getTitle() ,"svg"), plantumlSource);
//
//        var adoc = AsciiDoc.valueOf(asciidocSource);
//        return adoc;
//    }


}

