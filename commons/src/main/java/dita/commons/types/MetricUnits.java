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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.UnitFormat;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Energy;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import lombok.experimental.UtilityClass;

import tech.units.indriya.AbstractUnit;
import tech.units.indriya.format.EBNFUnitFormat;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.TransformedUnit;
import tech.units.indriya.unit.Units;

@UtilityClass
public class MetricUnits {

    // -- CUSTOM UNITS
    
    public static final Unit<Energy> CALORIES = 
            new TransformedUnit<>("cal", Units.JOULE, Units.JOULE, MultiplyConverter.ofRational(4184, 1000));

    public static final Unit<Dimensionless> BREAD_EXCHANGE = 
            AbstractUnit.ONE.alternate("BE").asType(Dimensionless.class);

    public static final Unit<Dimensionless> PARTS = 
            AbstractUnit.ONE.alternate("parts").asType(Dimensionless.class);
    
    // -- FACTORIES

    public Quantity<Dimensionless> one() {
        return Quantities.getQuantity(1, AbstractUnit.ONE);
    }

    public static Quantity<?> dimensionless(final Number amount) {
        return Quantities.getQuantity(amount, AbstractUnit.ONE);
    }

    public Quantity<Mass> grams(final Number amount) {
        return Quantities.getQuantity(amount, Units.GRAM);
    }

    public Quantity<Mass> milliGrams(final Number amount) {
        return Quantities.getQuantity(amount, MetricPrefix.MILLI(Units.GRAM));
    }

    public Quantity<Volume> litres(final Number amount) {
        return Quantities.getQuantity(amount, Units.LITRE);
    }

    public Quantity<Volume> milliLitres(final Number amount) {
        return Quantities.getQuantity(amount, MetricPrefix.MILLI(Units.LITRE));
    }

    // -- CONVERSION

    /**
     * Optionally converts given {@link Quantity} to desired {@link Unit},
     * based on whether both units are compatible.
     */
    @SuppressWarnings("unchecked")
    public <Q extends Quantity<Q>> Optional<Quantity<Q>> asUnit(final Quantity<?> quantity, final Unit<Q> unit) {
        return quantity.getUnit().isCompatible(unit)
                ? Optional.of(((Quantity<Q>)quantity).to(unit))
                : Optional.empty();
    }

    // -- FORMATTING

    private final DecimalFormat decimal3Format =
            new DecimalFormat("##0.000",DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    private final UnitFormat unitFormat = EBNFUnitFormat.getInstance();

    public String formatted(final Quantity<?> quantity) {
        return formatted(quantity.getValue()) + formatted(quantity.getUnit());
    }

    public String formatted(final Number value) {
        return decimal3Format.format(value);
    }

    /** SI prefixed unit symbol */
    public String formatted(final Unit<?> unit) {
        return unitFormat.format(unit);
    }

}
