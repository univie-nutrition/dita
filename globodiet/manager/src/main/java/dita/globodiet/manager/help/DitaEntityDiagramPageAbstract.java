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

import org.apache.causeway.applib.id.LogicalType;
import org.apache.causeway.applib.services.metamodel.BeanSort;
import org.apache.causeway.applib.services.metamodel.MetaModelService;
import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;
import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph.RelationType;
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
        // add foreign key relations from schema
        .transform(g->{

            val objectByEntity = new HashMap<OrmModel.Entity, ObjectGraph.Object>();
            val relationsToRender = new ArrayList<ObjectGraph.Relation>(g.relations());

            gdParamsSchema.entities().values()
                .forEach(entity->{
                    g.objects().stream()
                        .filter(obj->obj.packageName().endsWith(entity.namespace())
                                && obj.name().equals(entity.name()))
                        .findAny()
                        .ifPresent(obj->objectByEntity.put(entity, obj));
                });

            gdParamsSchema.entities().values()
                .forEach(entity->{

                    final ObjectGraph.Object from = objectByEntity.get(entity);

                    entity.fields().forEach(field->{
                        field.foreignKeys().stream()
                            .map(gdParamsSchema::lookupForeignKeyFieldElseFail)
                            .forEach(foreignField->{

                                final ObjectGraph.Object to = objectByEntity.get(foreignField.parentEntity());

                                relationsToRender.add(new ObjectGraph.Relation(
                                        RelationType.MERGED_ASSOCIATIONS, from, to, field.name(), ""));
                            });
                    });
                });


            val transformed = new ObjectGraph();
            transformed.objects().addAll(g.objects());
            transformed.relations().addAll(relationsToRender);
            return transformed;

        })
        // rename packages (strip 'dita.globodiet.params')
        .transform(g->{
            val stripLen = "dita.globodiet.params.".length();

            val transformed = new ObjectGraph();

            g.objects().stream()
                .map(obj->obj.withPackageName(obj.packageName().substring(stripLen)))
                .forEach(transformed.objects()::add);

            val objectById = transformed.objectById();

            g.relations().stream()
                .map(rel->rel.withFrom(objectById.get(rel.fromId())).withTo(objectById.get(rel.toId())))
                .forEach(transformed.relations()::add);

            return transformed;
        })
        .transform(ObjectGraph.Transformers.relationMerger());
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

