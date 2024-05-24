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
package dita.recall24.mutable;

import java.math.BigDecimal;

import javax.measure.Quantity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

import dita.commons.jaxb.JaxbAdapters;

@Deprecated
@XmlRootElement(name="ingredient")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public final class Ingredient implements RecallNode {

    @XmlElement(name="sid")
    private String sid;

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="facetSids")
    private String facetSids;

    @XmlElement(name="rawPerCookedRatio")
    private BigDecimal rawPerCookedRatio;

    @XmlElement(name="quantityCooked")
    @XmlJavaTypeAdapter(value=JaxbAdapters.QuantityAdapter.class)
    private Quantity<?> quantityCooked;

}
