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
package dita.commons.sid;

import java.util.Optional;

import org.springframework.util.StringUtils;

import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.types.LanguageId;

public record SidFactory(SystemId systemId) {

    public SemanticIdentifier sid(final ObjectId objectId) {
        return new SemanticIdentifier(systemId, objectId);
    }

    /// `de:Gurke`
    public static Optional<SemanticIdentifier> literal(final LanguageId languageId, final String literal) {
        return literal(languageId, null, literal);
    }

    /// `de:name/Gurke`
    public static Optional<SemanticIdentifier> literal(final LanguageId languageId, final String name, final String literal) {
        if(!StringUtils.hasLength(literal)) return Optional.empty();
        return Optional.of(new SemanticIdentifier(new SystemId(languageId.lowerCase(), null), new ObjectId(name, literal)));
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

}
