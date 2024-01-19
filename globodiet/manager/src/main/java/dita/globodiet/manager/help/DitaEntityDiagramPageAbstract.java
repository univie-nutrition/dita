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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.causeway.applib.id.LogicalType;
import org.apache.causeway.applib.services.metamodel.BeanSort;
import org.apache.causeway.applib.services.metamodel.MetaModelService;
import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;
import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph.Transformers;
import org.apache.causeway.extensions.docgen.help.topics.domainobjects.EntityDiagramPageAbstract;

import dita.tooling.orm.OrmModel;
import lombok.val;

abstract class DitaEntityDiagramPageAbstract extends EntityDiagramPageAbstract {

    private final OrmModel.Schema gdParamsSchema;

    protected DitaEntityDiagramPageAbstract(
            final OrmModel.Schema gdParamsSchema,
            final MetaModelService metaModelService) {
        super(metaModelService);
        this.gdParamsSchema = gdParamsSchema;
    }

    @Override
    final protected boolean accept(final BeanSort beanSort, final LogicalType logicalType) {
        if(!beanSort.isEntity()) return false;
        val ns = "" + logicalType.getNamespace();
        return !ns.equals("causeway")
                && !ns.startsWith("causeway.");
    }

    @Override
    final protected ObjectGraph createObjectGraph() {
        return super.createObjectGraph()
        .transform(packageNameNormalizer())
        // add foreign key relations from schema
        .transform(g->{
            var schemaGraph = gdParamsSchema.asObjectGraph()
                    .transform(new VirtualObjectAdder());
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
        .transform(unrelatedObjectRemover())
        .transform(virtualRelationRemover())
        .transform(ObjectGraph.Transformers.relationMerger());
    }

    /**
     * rename packages (strip 'dita.globodiet.params')
     */
    private static ObjectGraph.Transformer packageNameNormalizer() {
        val stripLen = "dita.globodiet.".length();
        return Transformers.objectModifier(obj->
            obj.withPackageName(obj.packageName().substring(stripLen)));
    }

    /**
     * Removes unrelated objects (those that have no relations).
     */
    private static ObjectGraph.Transformer unrelatedObjectRemover() {
        return g->{
            var relatedObjects = new HashSet<ObjectGraph.Object>();
            g.relations().forEach(rel->{
                relatedObjects.add(rel.from());
                relatedObjects.add(rel.to());
            });
            g.objects()
                .removeIf(obj->!relatedObjects.contains(obj));
            return g;
        };
    }

    private static ObjectGraph.Transformer virtualRelationRemover() {
        return g->{
            g.relations()
                .removeIf(rel->rel.to().packageName().equals("virtual"));
            return g;
        };
    }

    private record VirtualObjectAdder() implements ObjectGraph.Transformer {

        @Override
        public ObjectGraph transform(final ObjectGraph g) {

            val transformed = new ObjectGraph();

            g.objects().stream()
                .map(obj->obj.withId(obj.id())) // copy
                .forEach(transformed.objects()::add);

            var fck = new ObjectGraph.Object("fck", "virtual", "FCK", Optional.empty(), Optional.of("Food Classifier Key"), List.of());
            transformed.objects().add(fck);

            val objectById = transformed.objectById();
            val fckLabels = Set.of(
                    "foodGroupCode",
                    "foodSubgroupCode",
                    "foodSubSubgroupCode",
                    "fatGroupCode",
                    "fatSubgroupCode",
                    "fatSubSubgroupCode",
                    "fssGroupCode",
                    "fssSubgroupCode",
                    "fssSubSubgroupCode",

                    "recipeGroupCode",
                    "recipeSubgroupCode",
                    // hybrids
                    "foodOrRecipeGroupCode",
                    "foodOrRecipeSubgroupCode",
                    "foodOrRecipeSubSubgroupCode"
                    );

            g.relations().stream()
                .map(rel->{
                    var from = objectById.get(rel.fromId());
                    var to = fckLabels.contains(rel.description())
                            ? fck
                            : objectById.get(rel.toId());
                    return rel
                        .withFrom(from)
                        .withTo(to); // copy
                })
                .forEach(transformed.relations()::add);

            return transformed;
        }
    }


//    @Override
//    public AsciiDoc getContent() {
//        val title = getTitle();
//        val plantumlSource = entityTypesAsDiagram();
//
//        val doc = AsciiDocFactory.doc();
//        doc.setTitle(getTitle());
//
//        final String asciidocSource = AsciiDocWriter.toString(doc);
//
//        val diagramBlock = AsciiDocFactory
//                .diagramBlock(doc, "plantuml", Can.of(getTitle() ,"svg"), plantumlSource);
//
//        val adoc = AsciiDoc.valueOf(asciidocSource);
//        return adoc;
//    }


}

