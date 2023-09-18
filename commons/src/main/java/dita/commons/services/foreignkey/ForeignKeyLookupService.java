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

import org.apache.causeway.applib.value.Markup;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Either;

public interface ForeignKeyLookupService {

    <L, F> Optional<F> unary(
            // local
            L localEntity, Object localField,
            // foreign
            Class<F> foreignType,
            Function<F, Object> foreignFieldGetter);

    <L, F> Optional<F> binary(
            // local
            L localEntity, Object localField,
            // foreign
            Class<F> foreignType,
            Function<F, Object> foreignFieldGetter1,
            Function<F, Object> foreignFieldGetter2);

    <L, F1, F2> Optional<Either<F1, F2>> either(
            // local
            L localEntity, Object localField,
            // foreign 1
            Class<F1> foreignType1, Function<F1, Object> foreignFieldGetter1,
            // foreign 2
            Class<F2> foreignType2, Function<F2, Object> foreignFieldGetter2);

    /**
     * TODO[DITA-110] Yet this does not allow for reverse lookup mixins to properly find dependants.
     */
    <L, F> Markup plural(
            // local
            L localEntity, Object localField,
            // foreign
            Class<F> foreignType,
            final Can<Function<F, Object>> foreignFieldGetters);

}
