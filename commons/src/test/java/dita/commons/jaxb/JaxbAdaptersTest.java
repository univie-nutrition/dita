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
package dita.commons.jaxb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.SneakyThrows;
import lombok.val;

import dita.commons.io.JaxbAdapters;
import dita.commons.types.Sex;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

class JaxbAdaptersTest {

    @Test @SneakyThrows
    void roundtripOnQuantity() {

        var adapter = new JaxbAdapters.QuantityAdapter();
        val quantity = Quantities.getQuantity(15.54, Units.GRAM);

        val stringified = adapter.marshal(quantity);
        assertEquals("15.54[g]", stringified);

        val quantityAfterRoundTrip = adapter.unmarshal(stringified);
        assertEquals(quantity, quantityAfterRoundTrip);
    }

    @ParameterizedTest @SneakyThrows
    @EnumSource(Sex.class)
    void roundtripOnGender(final Sex gender) {

        var adapter = new JaxbAdapters.SexAdapter();

        val stringified = adapter.marshal(gender);
        val genderAfterRoundTrip = adapter.unmarshal(stringified);
        assertEquals(gender, genderAfterRoundTrip);
    }

}
