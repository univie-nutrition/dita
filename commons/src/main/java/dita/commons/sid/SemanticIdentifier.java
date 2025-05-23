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

import java.io.Serializable;
import java.util.function.UnaryOperator;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.applib.annotation.Value;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.Builder;
import lombok.Getter;
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
@Builder @Value
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
        @NonNull ObjectId objectId) implements Comparable<SemanticIdentifier>, Serializable {

    // -- SYSTEM ID

    public record SystemId(
            /**
             * Identifies the system of the referenced data object.
             */
            String system,
            /**
             * Identifies the system's version of the referenced data object.
             */
            String version) implements Comparable<SystemId>, Serializable {

        // -- FACTORIES

        public static SystemId empty() {
            return new SystemId(null, null);
        }

        public static SystemId parse(final @Nullable String systemIdStringified) {
            return ParseFormatUtils.parseSystemId(systemIdStringified);
        }

        // -- CONSTRUCTION

        public SystemId(final String system, final String version) {
            this.system = ParseFormatUtils.validate(system);
            this.version = ParseFormatUtils.validate(version);
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
            return ParseFormatUtils.format(this);
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
            String objectSimpleId) implements Comparable<ObjectId>, Serializable {

        /**
         * Some predefined contexts.
         * @implSpec system-agnostic come first, that allows for optimization of
         *      {@link Context#isSystemAgnostic(ObjectId)}
         */
        @RequiredArgsConstructor
        public enum Context {
            LANGUAGE("language", true),
            LITERAL("literal", true),
            BRAND("brand", true),
            COMPONENT("comp"),
            RECIPE("recp"),
            FOOD("food"),
            ;
            Context(final String id){ this(id, false); }
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
            static boolean isSystemAgnostic(@NonNull final ObjectId objectId) {
                for(var context : Context.values()) {
                    if(!context.isSystemAgnostic()) return false;
                    if(context.id().equals(objectId.context())) return true;
                }
                return false;
            }
            public boolean matches(@Nullable final String contextId) {
                return id().equals(contextId);
            }
            public boolean matches(@Nullable final ObjectId objecjId) {
                return objecjId!=null
                        && id().equals(objecjId.context());
            }
            public boolean matches(@Nullable final SemanticIdentifier sid) {
                return sid!=null
                        && id().equals(sid.objectId().context());
            }
        }

        // -- FACTORIES

        public static ObjectId empty() {
            return new ObjectId((String)null, null);
        }

        /**
         * Placeholder for 'work in progress'.
         */
        public static ObjectId wip() {
            return new ObjectId((String)null, "WIP");
        }

        public static ObjectId parse(final @Nullable String objectIdStringified) {
            return ParseFormatUtils.parseObjectId(objectIdStringified);
        }

        // -- CONSTRUCTION

        public ObjectId(final String context, final String objectSimpleId) {
            this.context = ParseFormatUtils.validate(context);
            this.objectSimpleId = ParseFormatUtils.validateObject(objectSimpleId);
        }

        public ObjectId(final @NonNull Context context, final String objectSimpleId) {
            this(context.id, objectSimpleId);
        }

        /**
         * ObjectId constructor with empty context part.
         */
        public ObjectId(final String objectSimpleId) {
            this((String)null, objectSimpleId);
        }

        // -- WITHER

        public ObjectId withContext(final @Nullable String context) {
            return new ObjectId(context, objectSimpleId());
        }
        public ObjectId withContext(final @Nullable Context context) {
            return context!=null
                    ? withContext(context.id())
                    : withContext((String)null);
        }
        public ObjectId withObjectSimpleId(final String objectSimpleId) {
            return new ObjectId(context(), objectSimpleId);
        }

        // -- MAPPER

        public ObjectId mapContext(final UnaryOperator<String> contextMapper) {
            return withContext(contextMapper.apply(context()));
        }
        public ObjectId mapObjectSimpleId(final UnaryOperator<String> objectMapper) {
            return withObjectSimpleId(objectMapper.apply(objectSimpleId()));
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
            return ParseFormatUtils.format(this);
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
                //TODO[dita-commons] should use DEWEY compare here
                : _Strings.compareNullsFirst(a.objectSimpleId(), b.objectSimpleId());
        }

    }

    // -- SID IMPL

    private static final SemanticIdentifier EMPTY = new SemanticIdentifier(SystemId.empty(), ObjectId.empty());
    public static SemanticIdentifier empty() { return EMPTY; }

    /**
     * Placeholder for 'work in progress'.
     */
    public static SemanticIdentifier wip() {
        return new SemanticIdentifier(SystemId.empty(), ObjectId.wip());
    }

    @Deprecated // inconsistent with dita.recall24.reporter.tabular.TabularFactory#literalDe
    public static SemanticIdentifier literal(final String literal) {
        return ObjectId.Context.LITERAL.sid(literal);
    }

    public static SemanticIdentifier parse(final String systemId, final String objectId) {
        return new SemanticIdentifier(SystemId.parse(systemId), ObjectId.parse(objectId));
    }

    // -- PARSER

    /**
     * Inverse of {@link SemanticIdentifier#toString()}.
     * @see SemanticIdentifier#toString()
     */
    public static SemanticIdentifier parse(final @Nullable String stringified) {
        return ParseFormatUtils.parseSid(stringified);
    }

    // -- CONSTRUCTION

    public SemanticIdentifier(
            @NonNull final SystemId systemId,
            @NonNull final ObjectId objectId) {
        this.systemId = ObjectId.Context.isSystemAgnostic(objectId)
                ? SystemId.empty()
                : systemId;
        this.objectId = objectId;
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

    // -- PREDICATES

    public boolean isEmpty() {
        return EMPTY.equals(this);
    }

    // -- CONTRACT

    @Override
    public int compareTo(final @Nullable SemanticIdentifier o) {
        return compare(this, o);
    }

    /**
     * e.g. {@code at.gd/2.0:food/00123}
     */
    public String toStringNoBox() {
        return ParseFormatUtils.formatUnboxed(this);
    }

    /**
     * e.g. {@code SID[at.gd/2.0:food/00123]}
     */
    @Override
    public final String toString() {
        return ParseFormatUtils.formatBoxed(this);
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
