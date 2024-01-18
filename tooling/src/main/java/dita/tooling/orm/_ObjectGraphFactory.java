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
package dita.tooling.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;

import lombok.val;

record _ObjectGraphFactory(OrmModel.Schema schema) implements ObjectGraph.Factory {

    @Override
    public ObjectGraph create() {
        var objectGraph = new ObjectGraph();

        final Map<OrmModel.Entity, ObjectGraph.Object> objByEntity = new HashMap<>();

        // add objects
        for(val entity : schema.entities().values()) {
            var obj = object(entity);
            objectGraph.objects().add(obj);
            objByEntity.put(entity, obj);
        }
        // add relations
        for(val entity : schema.entities().values()) {
            var from = objByEntity.get(entity);
            if(from==null) continue;
            entity.fields().forEach(field->{
                field.foreignFields().stream()
                    .forEach(foreignField->{
                        var to = objByEntity.get(foreignField.parentEntity());
                        objectGraph.relations().add(relation(from, to, field.name()));
                    });
            });
        }
        return objectGraph;
    }

    private static ObjectGraph.Object object(final OrmModel.Entity entity) {
        final String id = entity.namespace() + "." + entity.name();
        boolean isAbstract = false;
        val obj = new ObjectGraph.Object(id,
                entity.namespace(),
                entity.name(),
                isAbstract
                    ? Optional.of("abstract")
                    : Optional.empty(),
                new ArrayList<>());
        return obj;
    }

    private static ObjectGraph.Relation relation(
            final ObjectGraph.Object from,
            final ObjectGraph.Object to,
            final String label) {
        val relation = new ObjectGraph.Relation(
                ObjectGraph.RelationType.ONE_TO_ONE,
                from, to,
                label, "");
        return relation;
    }

}
