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
package dita.tooling.structgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslFormatter;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.AutomaticLayout.RankDirection;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

import dita.tooling.orm.OrmModel;
import dita.tooling.orm.OrmModel.Schema;
import lombok.SneakyThrows;
import lombok.val;

@Deprecated
record StructurizrGenerator(
        OrmModel.Schema schema,
        Workspace workspace) {

    public StructurizrGenerator(final OrmModel.Schema schema) {
        this(schema, new Workspace("", ""));
        generateWorkspace();
    }

    @SneakyThrows
    public String diagramDsl() {
        return StructurizrDslFormatter.toDsl(workspace());
    }

    // -- WORKSPACE GENERATOR

    private void generateWorkspace() {
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        SoftwareSystem softwareSystem = model.addSoftwareSystem("Dita GD Params", "Software system having all the GD Params entities.");

        var containerByName = new HashMap<String, Container>();

        generateEntitiesAsComponents(softwareSystem, containerByName);

        // create a model to describe a user using a software system
        //Person user = model.addPerson("User", "A user of my software system.");
        //user.uses(softwareSystem, "Uses");

        // create a system context diagram showing people and software systems

        SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        var cv  = views.createContainerView(softwareSystem, "a0", "a1");
        cv.addAllElements();
        cv.enableAutomaticLayout(RankDirection.TopBottom);

        containerByName.forEach((name, container)->{
            var compView  = views.createComponentView(container, name, "desc");
            compView.addAllElements();
            compView.enableAutomaticLayout(RankDirection.TopBottom);
        });

        // add some styling to the diagram elements
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);
    }

    private void generateEntitiesAsComponents(final SoftwareSystem softwareSystem, final Map<String, Container> containerByName) {
        var entitiesWithoutRelations = schema.findEntitiesWithoutRelations();

        new EntityBuilder<Component>(schema(), entity->{

            var containerName = entitiesWithoutRelations.contains(entity)
                    ? "Unrelated"
                    : entity.namespace();

            Container container =
                    containerByName.computeIfAbsent(containerName, name->softwareSystem.addContainer(name));

            var comp = container.addComponent(entity.name());
            //comp.setGroup(entity.namespace());
            return comp;
        })
        .addRelations();
    }

    private static class EntityBuilder<T> {
        private final OrmModel.Schema schema;
        private final Map<OrmModel.Entity, T> tByEntity = new HashMap<>();

        EntityBuilder(final Schema schema, final Function<OrmModel.Entity, T> factory) {
            this.schema = schema;
            for(val entity : schema.entities().values()) {
                var component = factory.apply(entity);
                tByEntity.put(entity, component);
            }
        }
        void addRelations() {
            for(val entity : schema.entities().values()) {
                var from = (Component)tByEntity.get(entity);
                if(from==null) continue;
                var counter = new LongAdder();
                entity.fields().forEach(field->{
                    field.foreignFields().stream()
                        .forEach(foreignField->{
                            if(isFoodClassificationKey(foreignField)) {
                                if(counter.intValue()==0){
                                    var container = from.getContainer();
                                    var fck = Optional.ofNullable(container.getComponentWithName("fck"))
                                            .orElseGet(()->container.addComponent("fck"));
                                    //from.uses(fck, field.name());
                                    from.addTags("FCK");

                                    from.setGroup("FCK");
                                }
                                counter.increment();
                            } else if(isRecipeClassificationKey(foreignField)) {
                                from.setGroup("RCK"); // TODO could be both
                            } else {
                                var to = (Component)tByEntity.get(foreignField.parentEntity());
                                from.uses(to, field.name());
                            }

                        });
                });
            }
        }
        boolean isFoodClassificationKey(final OrmModel.Field field) {
            var fqColumnName = field.fqColumnName();
            return fqColumnName.equalsIgnoreCase("GROUPS.GROUP")
                    || fqColumnName.equalsIgnoreCase("SUBGROUP.SUBGROUP1")
                    || fqColumnName.equalsIgnoreCase("SUBGROUP.SUBGROUP2");
        }
        boolean isRecipeClassificationKey(final OrmModel.Field field) {
            var fqColumnName = field.fqColumnName();
            return fqColumnName.equalsIgnoreCase("RGROUPS.GROUP")
                    || fqColumnName.equalsIgnoreCase("RSUBGR.SUBGROUP");
        }

    }

}
