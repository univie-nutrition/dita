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
package dita.foodon.systems;

import java.util.function.Predicate;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SidFactory;

/// food composition data base (naming conventions)
public record FcdbSystem(SystemId systemId, String title) {

    public final static FcdbSystem BLS302 = new FcdbSystem(new SystemId("de.bls", "3.02"), "BLS v3.02");
    public final static FcdbSystem BLS40 = new FcdbSystem(new SystemId("de.bls", "4.0"), "BLS v4.0");
    public final static FcdbSystem UNIVIE_NUTRIDB = new FcdbSystem(new SystemId("at.univie.ndb", "1.0"), "Universität Wien");
    public final static FcdbSystem OENWT = new FcdbSystem(new SystemId("at.oenwt", "2025.12"), "ÖNWT 2025-12");

    @Deprecated // perhaps unify
    public SidFactory sidFactory() {
        return new SidFactory(systemId);
    }

    public SemanticIdentifier sid(final ObjectId objectId) {
        return systemId.sid(objectId);
    }

    public static SemanticIdentifier brand(final String objectSimpleId) {
        return ObjectId.Context.BRAND.sid(objectSimpleId);
    }
    public SemanticIdentifier component(final String objectSimpleId) {
        return ObjectId.Context.COMPONENT.sid(systemId, objectSimpleId);
    }
    public SemanticIdentifier recipe(final String objectSimpleId) {
        return ObjectId.Context.RECIPE.sid(systemId, objectSimpleId);
    }
    public SemanticIdentifier food(final String objectSimpleId) {
        return ObjectId.Context.FOOD.sid(systemId, objectSimpleId);
    }
    public SemanticIdentifier foodDescriptor(final String objectSimpleId) {
        return ObjectId.Context.FOOD_DESCRIPTOR.sid(systemId, objectSimpleId);
    }
    public SemanticIdentifier recipeDescriptor(final String objectSimpleId) {
        return ObjectId.Context.RECIPE_DESCRIPTOR.sid(systemId, objectSimpleId);
    }
    public SemanticIdentifier foodGroup(final String objectSimpleId) {
        return ObjectId.Context.FOOD_GROUP.sid(systemId, objectSimpleId);
    }
    public SemanticIdentifier recipeGroup(final String objectSimpleId) {
        return ObjectId.Context.RECIPE_GROUP.sid(systemId, objectSimpleId);
    }

    public Predicate<SemanticIdentifier> sameSystem() {
        return other->systemId.equals(other.systemId());
    }

}
