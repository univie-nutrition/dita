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

import java.util.function.UnaryOperator;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._Strings;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A Semantic Identifier references data objects across system boundaries.
 * <p>
 * <ul>
 * <li>system: at.gd</li>
 * <li>version: 2.0</li>
 * <li>context: food | recipe | food.desc | recipe.desc | food.group etc.</li>
 * <li>object: 00123</li>
 * </ul>
 * <p>
 * formats to -> SID[at.gd/2.0, food/00123]
 */
@Builder
public record SemanticIdentifier (
        /**
         * Identifies the system of the referenced data object.
         * (optionally includes version information)
         */
        @NonNull SystemId systemId,
        /**
         * Uniquely identifies the data object within the system.
         * (optionally includes context information)
         */
        @NonNull ObjectId objectId) implements Comparable<SemanticIdentifier> {

    // -- SYSTEM ID

    public record SystemId(
            /**
             * Identifies the system of the referenced data object.
             */
            String system,
            /**
             * Identifies the system's version of the referenced data object.
             */
            String version) implements Comparable<SystemId> {

        public static SystemId empty() {
            return new SystemId(null, null);
        }

        public static SystemId parse(final @Nullable String systemIdStringified) {
            return _Utils.parseSystemId(systemIdStringified);
        }

        public SystemId(final String system, final String version) {
            this.system = _Utils.validate(system);
            this.version = _Utils.validate(version);
        }

        /**
         * SystemId constructor with empty version part.
         */
        public SystemId(final String system) {
            this(system, null);
        }

        // -- WITHER

        public SystemId withSystem(final String system) {
            return new SystemId(system, version());
        }
        public SystemId withVersion(final String version) {
            return new SystemId(system(), version);
        }

        // -- MAPPER

        public SystemId mapSystem(final UnaryOperator<String> systemMapper) {
            return withSystem(systemMapper.apply(system()));
        }
        public SystemId mapVersion(final UnaryOperator<String> versionMapper) {
            return withVersion(versionMapper.apply(version()));
        }

        // -- CONTRACT

        @Override
        public int compareTo(final @Nullable SystemId o) {
            return compare(this, o);
        }

        /**
         * Identifies the system of the referenced data object.
         * (optionally suffixed with a version)
         */
        @Override
        public final String toString() {
            return _Utils.format(this);
        }

        public static int compare(
                final @Nullable SystemId a,
                final @Nullable SystemId b) {
            if(a==null) return b==null
                        ? 0
                        : -1;
            if(b==null) return 1;
            final int c = _Strings.compareNullsFirst(a.system(), b.system());
            return c!=0
                ? c
                // TODO should use DEWEY compare here
                : _Strings.compareNullsFirst(a.version(), b.version());
        }

    }

    // -- OBJECT ID

    public record ObjectId(
            /**
             * Uniquely identifies the data object's context within the system.
             */
            String context,
            /**
             * Uniquely identifies the data object within the system's context.
             */
            String object) implements Comparable<ObjectId> {

        /**
         * Some predefined contexts.
         */
        @RequiredArgsConstructor
        public enum Context {
            LITERAL("lit"),
            LANGUAGE("lang"),
            COMPONENT("comp"),
            FOOD("food"),
            RECIPE("recp"),
            BRAND("brand"),
            FOOD_DESCRIPTOR("fd"),
            RECIPE_DESCRIPTOR("rd"),
            FOOD_GROUP("fg"),
            RECIPE_GROUP("rg"),
            ;
            @Getter @Accessors(fluent=true)
            final String id;
        }

        public static ObjectId empty() {
            return new ObjectId((String)null, null);
        }

        public static ObjectId parse(final @Nullable String objectIdStringified) {
            return _Utils.parseObjectId(objectIdStringified);
        }

        public ObjectId(final String context, final String object) {
            this.context = _Utils.validate(context);
            this.object = _Utils.validateObject(object);
        }

        public ObjectId(final @NonNull Context context, final String object) {
            this(context.id, object);
        }

        /**
         * ObjectId constructor with empty context part.
         */
        public ObjectId(final String object) {
            this((String)null, object);
        }

        // -- WITHER

        public ObjectId withContext(final String context) {
            return new ObjectId(context, object());
        }
        public ObjectId withObject(final String object) {
            return new ObjectId(context(), object);
        }

        // -- MAPPER

        public ObjectId mapContext(final UnaryOperator<String> contextMapper) {
            return withContext(contextMapper.apply(context()));
        }
        public ObjectId mapObject(final UnaryOperator<String> objectMapper) {
            return withObject(objectMapper.apply(object()));
        }

        // -- CONTRACT

        @Override
        public int compareTo(final @Nullable ObjectId o) {
            return compare(this, o);
        }

        /**
         * Uniquely identifies the data object within the system.
         * (optionally prefixed with a context)
         */
        @Override
        public final String toString() {
            return _Utils.format(this);
        }

        public static int compare(
                final @Nullable ObjectId a,
                final @Nullable ObjectId b) {
            if(a==null) return b==null
                        ? 0
                        : -1;
            if(b==null) return 1;
            final int c = _Strings.compareNullsFirst(a.context(), b.context());
            return c!=0
                ? c
                // TODO should use DEWEY compare here
                : _Strings.compareNullsFirst(a.object(), b.object());
        }

    }

    // -- SID IMPL

    public static SemanticIdentifier empty() {
        return new SemanticIdentifier(SystemId.empty(), ObjectId.empty());
    }

    public SemanticIdentifier(final String systemId, final String objectId) {
        this(SystemId.parse(systemId), ObjectId.parse(objectId));
    }

    // -- PARSER

    /**
     * Inverse of {@link SemanticIdentifier#toString()}.
     * @see SemanticIdentifier#toString()
     */
    public static SemanticIdentifier parse(final @Nullable String stringified) {
        return _Utils.parseSid(stringified);
    }

    // -- WITHER

    public SemanticIdentifier withSystemId(final SystemId systemId) {
        return new SemanticIdentifier(systemId, objectId());
    }
    public SemanticIdentifier withObjectId(final ObjectId objectId) {
        return new SemanticIdentifier(systemId(), objectId);
    }

    // -- MAPPER

    public SemanticIdentifier mapSystemId(final UnaryOperator<SystemId> systemIdMapper) {
        return withSystemId(systemIdMapper.apply(systemId()));
    }
    public SemanticIdentifier mapObjectId(final UnaryOperator<ObjectId> objectIdMapper) {
        return withObjectId(objectIdMapper.apply(objectId()));
    }


    @Override
    public int compareTo(final @Nullable SemanticIdentifier o) {
        return compare(this, o);
    }

    /**
     * e.g. {@code at.gd/2.0:food/00123}
     */
    public String toStringNoBox() {
        return _Utils.formatUnboxed(this);
    }

    /**
     * e.g. {@code SID[at.gd/2.0:food/00123]}
     */
    @Override
    public final String toString() {
        return _Utils.formatBoxed(this);
    }

    // -- UTILITY

    public static int compare(
            final @Nullable SemanticIdentifier a,
            final @Nullable SemanticIdentifier b) {
        if(a==null) return b==null
                    ? 0
                    : -1;
        if(b==null) return 1;
        final int c = a.systemId().compareTo(b.systemId());
        return c!=0
            ? c
            : a.objectId().compareTo(b.objectId());
    }

}
