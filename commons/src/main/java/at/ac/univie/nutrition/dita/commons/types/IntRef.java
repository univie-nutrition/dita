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
package at.ac.univie.nutrition.dita.commons.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Specifically designed to be used with Java record types,
 * to allow 'transient' integer references, such as counters.
 */
@AllArgsConstructor
public final class IntRef {

    public static IntRef of(final int value) {
        return new IntRef(value);
    }

    public static IntRef zero() {
        return new IntRef(0);
    }

    @Getter @Setter
    private int value;

    @Override
    public boolean equals(final Object obj) {
        return this==obj;
    }

    @Override
    public int hashCode() {
        return 2;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
