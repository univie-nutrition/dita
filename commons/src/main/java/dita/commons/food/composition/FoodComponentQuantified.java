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
package dita.commons.food.composition;

import java.math.BigDecimal;

import javax.measure.Quantity;

import dita.commons.food.composition.FoodComponent.ComponentUnit;
import dita.commons.sid.SemanticIdentifier;

public record FoodComponentQuantified(
        FoodComponent foodComponent,
        Quantity<?> quantity) {

    public SemanticIdentifier componentId() {
        return foodComponent.componentId();
    }

    public ComponentUnit componentUnit() {
        return foodComponent.componentUnit();
    }

    public BigDecimal quantityValue() {
        final Number n = quantity().getValue();
        return switch (n) {
            case BigDecimal bd -> bd;
            case Integer i -> BigDecimal.valueOf(i);
            default -> throw new IllegalArgumentException("Unhandled number type: " + n.getClass());
        };
    }

}
