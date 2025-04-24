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
package dita.commons.types;

import java.util.ArrayList;
import java.util.List;

/// comparison of 2 typed collections
public record Diff<L, R>(
    /// L elements without correspondence in R
    List<L> leftOuter,
    /// R elements without correspondence in L
    List<R> rightOuter,
    /// L elements matching their corresponding R element
    List<Pair<L, R>> innerMatch,
    /// L elements not matching their corresponding R element
    List<Pair<L, R>> innerMismatch) {

    /// typed {@link Diff} instance with mutable members
    public static <L, R> Diff<L, R> typed(final Class<L> leftType, final Class<R> rightType) {
        return new Diff<>(new ArrayList<L>(), new ArrayList<R>(),
            new ArrayList<Pair<L, R>>(), new ArrayList<Pair<L, R>>());
    }

    public Diff<L, R> addLeftOuter(final L left) {
        leftOuter.add(left);
        return this;
    }

    public Diff<L, R> addRightOuter(final R right) {
        rightOuter.add(right);
        return this;
    }

    public Diff<L, R> addInnerMatch(final L left, final R right) {
        innerMatch.add(Pair.of(left, right));
        return this;
    }

    public Diff<L, R> addInnerMismatch(final L left, final R right) {
        innerMismatch.add(Pair.of(left, right));
        return this;
    }

}

