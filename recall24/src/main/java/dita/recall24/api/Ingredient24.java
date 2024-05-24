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
package dita.recall24.api;

import java.math.BigDecimal;

import javax.measure.Quantity;

@Deprecated
public interface Ingredient24 extends RecallNode24 {

    /**
     * The parent {@link Record24}.
     */
    Record24 parentRecord();

    /**
     * Ingredient identifier.
     */
    String sid();

    /**
     * The name of this ingredient.
     */
    String name();

    /**
     * Comma separated list of facet identifiers,
     * ordered (by some natural order).
     */
    String facetSids();

    /**
     * Raw per cooked ratio, ranging from 0. to 1.
     */
    BigDecimal rawPerCookedRatio();

    /**
     * {@link Quantity} cooked.
     */
    Quantity<?> quantityCooked();

//    /**
//     * Mass/amount fraction relative to parent recipe.
//     */
//    double fractionRelativeToParentRecipe();

    default public String discriminator() {
        return "" + sid() + ";" + facetSids();
    }

}