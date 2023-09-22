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
package dita.commons.services.foreignkey;

import java.util.Optional;
import java.util.function.Function;

import org.apache.causeway.commons.collections.Can;

public interface ForeignKeyLookupService {

    <L, F> Optional<F> binary(
            // context
            Object caller,
            // local
            L localEntity, Object localField,
            // foreign
            Class<F> foreignType,
            Function<F, Object> foreignFieldGetter1,
            Function<F, Object> foreignFieldGetter2);

    /**
     * TODO[DITA-110] Yet this does not allow for reverse lookup mixins to properly find dependants.
     */
    <L, F> Can<F> plural(
            // context
            Object caller,
            // local
            L localEntity, Object localField,
            // foreign
            Class<F> foreignType,
            final Can<Function<F, Object>> foreignFieldGetters);

    int switchOn(Object entity);

    <T> T unique(ISecondaryKey<T> lookupKey);
    <T> T nullable(ISecondaryKey<T> lookupKey);

}
