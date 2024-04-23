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

import java.time.LocalDate;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.causeway.applib.jaxb.JavaTimeJaxbAdapters;

import lombok.Data;

import dita.commons.jaxb.JaxbAdapters;
import dita.commons.types.Sex;

@XmlRootElement(name="respondent")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RespondentDto {

    @XmlElement(name="alias")
    private String alias;

    @XmlElement(name="dateOfBirth")
    @XmlJavaTypeAdapter(value=JavaTimeJaxbAdapters.LocalDateToStringAdapter.class)
    private LocalDate dateOfBirth;

    @XmlElement(name="sex")
    @XmlJavaTypeAdapter(value=JaxbAdapters.SexAdapter.class)
    private Sex sex;

    @XmlElementWrapper(name="interviews")
    @XmlElement(name="interview", type=InterviewDto.class)
    private List<InterviewDto> interviews;

}
