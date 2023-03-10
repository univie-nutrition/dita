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
package at.ac.univie.nutrition.dita.recall24.dto;

import javax.measure.Quantity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import at.ac.univie.nutrition.dita.commons.jaxb.JaxbAdapters;
import lombok.Data;

@XmlRootElement(name="ingredient")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class IngredientDto {

    @XmlElement(name="sid")
    private String sid;

    @XmlElement(name="facetSids")
    private String facetSids;

    @XmlElement(name="rawPerCookedRatio")
    private double rawPerCookedRatio;

    @XmlElement(name="quantityCooked")
    @XmlJavaTypeAdapter(value=JaxbAdapters.QuantityAdapter.class)
    private Quantity<?> quantityCooked;

}
