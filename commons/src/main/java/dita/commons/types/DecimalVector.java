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

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.assertions._Assert;

import dita.commons.util.NumberUtils;

public record DecimalVector(
        /**
         * Never null. Allows elements to be Double.NaN.
         */
        double[] decimals) {

    final static DecimalVector EMPTY = new DecimalVector(new double[0]);
    public static DecimalVector empty() { return EMPTY; }
    public boolean isEmpty() { return cardinality() == 0; }

    public DecimalVector(@Nullable final double[] decimals) {
        this.decimals = decimals!=null
                ? decimals
                : new double[0];
    }

    public int cardinality() {
        return decimals.length;
    }

    public double get(final int index) {
        return decimals[index];
    }

    /**
     * Element-wise addition (supporting NaN and infinities).
     * <p>
     * Empty vectors are considered identity elements under addition.
     */
    public DecimalVector add(@Nullable final DecimalVector vector) {
        if(vector==null || vector.isEmpty()) return this;
        if(isEmpty()) return vector;
        _Assert.assertEquals(cardinality(), vector.cardinality());
        final double[] sum = new double[cardinality()];
        for (int i = 0; i < sum.length; i++) {
            sum[i] = decimals[i] + vector.decimals[i];
        }
        return new DecimalVector(sum);
    }

    /**
     * Scalar multiplication (supporting NaN and infinities).
     */
    public DecimalVector multiply(final double operand) {
        if(isEmpty()) return EMPTY;
        final double[] result = new double[cardinality()];
        for (int i = 0; i < result.length; i++) {
            result[i] = decimals[i] * operand;
        }
        return new DecimalVector(result);
    }

    /**
     * Checks element-wise equality. (empty vectors are considered equal)
     */
    public boolean equals(@Nullable final DecimalVector vector, final double epsilon) {
        if(vector==null || vector.isEmpty()) return this.isEmpty();
        return NumberUtils.numberArrayEquals(decimals, vector.decimals, epsilon);
    }

    public static DecimalVector nullToEmpty(@Nullable final DecimalVector vector) {
        return vector!=null
                ? vector
                : EMPTY;
    }

}