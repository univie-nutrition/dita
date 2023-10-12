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
package dita.globodiet.manager.services.rule;

import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.consent.InteractionInitiatedBy;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.object.ManagedObjects;
import org.apache.causeway.core.metamodel.object.PackedManagedObject;
import org.apache.causeway.core.metamodel.objectmanager.ObjectManager;
import org.apache.causeway.core.metamodel.spec.feature.MixedIn;
import org.apache.causeway.core.metamodel.specloader.SpecificationLoader;
import org.apache.causeway.core.metamodel.util.Facets;

import dita.commons.services.rules.RuleChecker;
import lombok.NonNull;

@Component
public class ResolvableDependenciesRuleChecker
implements RuleChecker {

    @Inject FactoryService factoryService;
    @Inject RepositoryService repositoryService;
    @Inject SpecificationLoader specificationLoader;
    @Inject ObjectManager objectManager;

    @Override
    public String title() {
        return "Resolvable Dependencies";
    }
    @Override
    public String description() {
        return "Checks that all dependencies (foreign keys) can be resolved.";
    }


    @Override
    public Can<RuleViolation> check(@NonNull final Class<?> entityType) {

        var entitySpec = specificationLoader.specForTypeElseFail(entityType);
        var mixedInProperties = entitySpec.streamProperties(MixedIn.ONLY)
            .filter(Facets.hiddenWhereMatches(hiddenWhere->hiddenWhere==Where.PARENTED_TABLES
                || hiddenWhere==Where.NOWHERE))
            .collect(Can.toCan());
        var mixedInCollections = entitySpec.streamCollections(MixedIn.ONLY)
            .filter(Facets.hiddenWhereMatches(hiddenWhere->hiddenWhere==Where.PARENTED_TABLES
                || hiddenWhere==Where.NOWHERE))
            .collect(Can.toCan());
        var instances = repositoryService.allInstances(entityType);

        var collector = new UnresolvableCollector(new TreeSet<String>());

        instances.stream()
            .map(objectManager::adapt)
            .forEach(instance->{
                mixedInProperties.stream()
                    .map(prop->prop.get(instance, InteractionInitiatedBy.PASS_THROUGH))
                    .forEach(collector::collect);

                mixedInCollections.stream()
                    .map(coll->coll.get(instance, InteractionInitiatedBy.PASS_THROUGH))
                    .filter(Predicate.not(ManagedObjects::isNullOrUnspecifiedOrEmpty))
                    .filter(PackedManagedObject.class::isInstance)
                    .map(PackedManagedObject.class::cast)
                    .map(PackedManagedObject::unpack)
                    .flatMap(Can::stream)
                    .forEach(collector::collect);
            });

        return collector.unresolvables().isEmpty()
                ? Can.empty()
                : Can.of(RuleViolation.warning("Issues:\n%s",
                        collector.unresolvables().stream()
                        .map(s->"- " + s)
                        .collect(Collectors.joining("\n"))));
    }

    // -- HELPER

    static record UnresolvableCollector(
            TreeSet<String> unresolvables) {

        void collect(final ManagedObject obj) {
            if(isUnresolvable(obj)) {
                unresolvables.add(obj.getTitle());
            }
        }

        private boolean isUnresolvable(final ManagedObject obj) {
            return !ManagedObjects.isNullOrUnspecifiedOrEmpty(obj)
                    && obj.getSpecification().getCorrespondingClass().getName().endsWith("Unresolvable");
        }
    }

}
