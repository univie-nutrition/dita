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
package dita.recall24.dto;

import java.time.LocalTime;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.causeway.applib.jaxb.JavaTimeJaxbAdapters;

import lombok.Data;

@XmlRootElement(name="meal")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MealDto {

    @XmlElement(name="hourOfDay")
    @XmlJavaTypeAdapter(value=JavaTimeJaxbAdapters.LocalTimeToStringAdapter.class)
    private LocalTime hourOfDay;

    @XmlElement(name="foodConsumptionOccasion")
    private String foodConsumptionOccasionId;

    @XmlElement(name="foodConsumptionPlace")
    private String foodConsumptionPlaceId;

    @XmlElementWrapper(name="memorized")
    @XmlElement(name="memorizedFood", type=MemorizedFoodDto.class)
    private List<MemorizedFoodDto> memorizedFood;

}
