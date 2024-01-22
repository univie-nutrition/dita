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
package dita.commons.util;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph;
import org.apache.causeway.applib.services.metamodel.objgraph.ObjectGraph.Transformers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectGraphTransformers {

    /**
     * rename packages (strip 'dita.globodiet.params')
     */
    public ObjectGraph.Transformer packagePrefixRemover(final String prefix) {
        return Transformers.objectModifier(obj->
            obj.packageName().startsWith(prefix)
                ? obj.withPackageName(obj.packageName().substring(prefix.length()))
                : obj);
    }

    /**
     * rename relations (strip 'Code' and 'LookupKey' suffixes)
     */
    public ObjectGraph.Transformer relationNameNormalizer() {
        return g->{
            var newRels = g.relations().stream()
            .map(rel->rel.withDescription(rel.description().replace("Code", "￪").replace("LookupKey", "￪￪")))
            .toList();
            g.relations().clear();
            g.relations().addAll(newRels);
            return g;
        };
    }

    /**
     * Removes unrelated objects (those that have no relations).
     */
    public ObjectGraph.Transformer unrelatedObjectRemover() {
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

    public ObjectGraph.Transformer virtualRelationRemover() {
        return g->{
            g.relations()
                .removeIf(rel->rel.to().packageName().equals("virtual"));
            return g;
        };
    }

    public ObjectGraph.Transformer virtualObjectAdder(){
        return g->{
            var transformed = new ObjectGraph();

            g.objects().stream()
                .map(obj->obj.withId(obj.id())) // copy
                .forEach(transformed.objects()::add);

            var fck = new ObjectGraph.Object("fck", "virtual", "FCK", Optional.empty(), Optional.of("Food Classifier Key"), List.of());
            transformed.objects().add(fck);

            var objectById = transformed.objectById();
            var fckLabels = Set.of(
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
        };
    }

}
