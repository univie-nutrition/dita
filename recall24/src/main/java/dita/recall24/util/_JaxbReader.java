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
package dita.recall24.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.JaxbUtils;
import org.apache.causeway.commons.io.ZipUtils;

import lombok.SneakyThrows;
import lombok.val;

import dita.recall24.mutable.InterviewSet;

class _JaxbReader {

    private final JAXBContext jaxbContext;
    private final Unmarshaller jaxbUnmarshaller;

    @SneakyThrows
    _JaxbReader() {
        jaxbContext = JaxbUtils.jaxbContextFor(InterviewSet.class, true);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    InterviewSet unzip(final DataSource ds) {

        return ZipUtils.streamZipEntries(ds)
            .map(this::readFromXml)
            .findFirst()
            .orElseGet(()->Try.failure(_Exceptions.noSuchElement("Zip entry not found.")))
            .valueAsNonNullElseFail();
    }

    Try<InterviewSet> readFromXml(final DataSource dataSource) {
        return dataSource.tryReadAndApply(this::readFromXml);
    }

    // -- HELPER


    private InterviewSet readFromXml(final InputStream is)
            throws XMLStreamException, IOException, JAXBException {

        val interviewSet = (InterviewSet) jaxbUnmarshaller.unmarshal(is);

//        // post-processing, set transient fields
//        final Map<String, Respondent24> respondentsByAlias = new HashMap<>();
//        interviewSet.getRespondents().forEach(r->respondentsByAlias.put(r.getAlias(), r));
//        interviewSet.getInterviews().forEach(intv->{
//            intv.setRespondent(respondentsByAlias.get(intv.getRespondentAlias()));
//            intv.getMeals().forEach(meal->{
//                meal.setParent(intv);
//                meal.getMemorizedFood().forEach(mem->{
//                    mem.setParent(meal);
//
//                    val orinalCounter = new LongAdder();
//
//                    mem.getRecords().forEach(rec->{
//                        rec.setParent(mem);
//                        rec.getIngredients().forEach(ingr->{
//                            ingr.setParent(rec);
//                            ingr.setFacetSet(FacetSortedSet.parse(ingr.getFacetSids()));
//                        });
//                        orinalCounter.increment();
//                        rec.setOrdinal(orinalCounter.intValue());
//                    });
//
//                });
//            });
//        });

        return interviewSet;
    }

}
