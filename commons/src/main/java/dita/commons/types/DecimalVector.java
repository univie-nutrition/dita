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

import java.math.BigDecimal;

public record DecimalVector(
        int cardinality,
        /**
         * Allows elements to be null.
         */
        BigDecimal[] decimals) {

    final static DecimalVector EMPTY = new DecimalVector(0, null);

    public static DecimalVector empty() {
        return EMPTY;
    }

    public DecimalVector add(final DecimalVector vector) {
        final BigDecimal[] sum = new BigDecimal[cardinality];
        for (int i = 0; i < sum.length; i++) {
            sum[i] = decimals()[i].add(vector.decimals()[i]);
        }
        return new DecimalVector(cardinality, sum);
    }
}
