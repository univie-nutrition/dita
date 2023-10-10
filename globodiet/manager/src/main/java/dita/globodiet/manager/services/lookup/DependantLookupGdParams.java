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
package dita.globodiet.manager.services.lookup;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import jakarta.inject.Inject;

import com.google.common.collect.ImmutableCollection;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;

import dita.commons.services.lookup.DependantLookupService;

@Service
public class DependantLookupGdParams
implements DependantLookupService {

    @Inject FactoryService factoryService;
    @Inject RepositoryService repositoryService;

    @Override
    public <D, M, L> List<D> findDependants(
            final Class<D> dependantType,
            final Class<M> dependantAssociationMixinClass,
            final Function<M, L> dependantAssociationMixinGetter,
            final L localEntity) {
        return repositoryService.allMatches(dependantType, candidate->
            equalsOrIsContainedIn(
                    localEntity,
                    dependantAssociationMixinGetter.apply(
                            factoryService.mixin(dependantAssociationMixinClass, candidate))));
    }

    public static boolean equalsOrIsContainedIn(final Object a, final Object b) {
        if(b instanceof ImmutableCollection coll) {
            return coll.contains(a);
        }
        if(b instanceof Collection coll) {
            return coll.contains(a);
        }
        return (a == b)
                || (a != null && a.equals(b));
    }

    @Override
    public Can<Object> findAllDependants(final Object localEntity) {
        //TODO[DITA-8] findAllDependants
        return Can.empty();
    }

}
