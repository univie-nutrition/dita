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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DecimalVectorTest {

    @Test
    void add() {
        var a = new DecimalVector(new double[]{1, 2});
        var b = new DecimalVector(new double[]{3, 4});
        var c = new DecimalVector(new double[]{5, Double.NaN});

        assertTrue(a.add(b).equals(new DecimalVector(new double[]{4, 6}), 0));
        assertFalse(a.add(b).equals(new DecimalVector(new double[]{4, 6, 1}), 0));
        assertFalse(a.add(b).equals(new DecimalVector(new double[]{4, 7}), 0));

        assertTrue(a.add(c).equals(new DecimalVector(new double[]{6, Double.NaN}), 0));
        assertTrue(c.add(c).equals(new DecimalVector(new double[]{10, Double.NaN}), 0));
    }

    @Test
    void nanAlgebra() {
        assertEquals(Double.NaN, 1. * Double.NaN);
        assertEquals(Double.POSITIVE_INFINITY, 1. * Double.POSITIVE_INFINITY);
        assertEquals(Double.NEGATIVE_INFINITY, -1. * Double.POSITIVE_INFINITY);
        assertEquals(Double.NaN, Double.POSITIVE_INFINITY + Double.NEGATIVE_INFINITY);
        assertEquals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY + Double.POSITIVE_INFINITY);
        assertEquals(Double.POSITIVE_INFINITY, Math.abs(Double.NEGATIVE_INFINITY));
    }

    @Test
    void overflow() {
        assertEquals(Double.POSITIVE_INFINITY, Double.MAX_VALUE + Double.MAX_VALUE);
        assertFalse(Double.POSITIVE_INFINITY<=1);
    }

}
