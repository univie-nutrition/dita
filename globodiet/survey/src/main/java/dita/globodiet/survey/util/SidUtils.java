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
package dita.globodiet.survey.util;

import java.util.List;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.ObjectId.Context;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;

// perhaps move to commons
@UtilityClass
public class SidUtils {

    public SemanticIdentifierSet languageQualifier(final String languageId) {
        return SemanticIdentifierSet.ofCollection(List.of(
                ObjectId.Context.LANGUAGE.sid(languageId)));
    }

    /**
     * GloboDiet specific predefined contexts.
     * @implSpec system-agnostic come first, that allows for optimization of
     *      {@link Context#isSystemAgnostic(ObjectId)}
     */
    @RequiredArgsConstructor
    public enum GdContext {
        RECIPE("recp"),
        FOOD_DESCRIPTOR("fd"),
        RECIPE_DESCRIPTOR("rd"),
        FOOD_GROUP("fg"),
        RECIPE_GROUP("rg"),
        ;
        GdContext(final String id){ this(id, false); }
        @Getter @Accessors(fluent=true)
        final String id;
        @Getter
        final boolean systemAgnostic;
        // -- FACTORIES
        public ObjectId objectId(final String objectSimpleId) {
            return new ObjectId(id(), objectSimpleId);
        }
        public SemanticIdentifier sid(final String objectSimpleId) {
            return sid(SystemId.empty(), objectSimpleId);
        }
        public SemanticIdentifier sid(final SystemId systemId, final String objectSimpleId) {
            return new SemanticIdentifier(systemId, objectId(objectSimpleId));
        }
        public boolean matches(final @Nullable ClassificationFacet classificationFacet) {
            if(classificationFacet==null) return false;
            return matches(classificationFacet.sid());
        }
        public boolean matches(final @Nullable SemanticIdentifier sid) {
            if(sid==null) return false;
            return id().equals(sid.objectId().context());
        }
        static boolean isSystemAgnostic(@NonNull final ObjectId objectId) {
            for(var context : Context.values()) {
                if(!context.isSystemAgnostic()) return false;
                if(context.id().equals(objectId.context())) return true;
            }
            return false;
        }
    }

}
