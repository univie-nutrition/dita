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
package at.ac.univie.nutrition.dita.recall24.util;

import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.JaxbUtils;

import at.ac.univie.nutrition.dita.recall24.dto.InterviewSetDto;
import lombok.SneakyThrows;

class _JaxbWriter {

    private final JAXBContext jaxbContext;
    private final Marshaller jaxbMarshaller;

    @SneakyThrows
    _JaxbWriter() {
        jaxbContext = JaxbUtils.jaxbContextFor(InterviewSetDto.class, true);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    Try<String> tryToString(final InterviewSetDto survey) {
        return Try.call(()->{
            final var stringWriter = new StringWriter();
            jaxbMarshaller.marshal(survey, stringWriter);
            return stringWriter.toString();
        });
    }

    Try<Void> tryWriteTo(final InterviewSetDto survey, final DataSink dataSink) {
        return Try.run(()->{
            dataSink.writeAll(os->{
                jaxbMarshaller.marshal(survey, os);
            });
        });
    }

}
