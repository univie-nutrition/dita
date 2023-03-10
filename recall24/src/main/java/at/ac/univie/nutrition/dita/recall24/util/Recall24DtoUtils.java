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

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.DataSource;

import at.ac.univie.nutrition.dita.recall24.dto.InterviewSetDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Recall24DtoUtils {

    // -- SURVEY IO

    public Try<InterviewSetDto> tryReadSurvey(final DataSource dataSource) {
        return new _JaxbReader()
                .readFromXml(dataSource);
    }

    public Try<Void> tryWriteSurvey(final InterviewSetDto interviewSet, final DataSink dataSink) {
        return new _JaxbWriter()
                .tryWriteTo(interviewSet, dataSink);
    }

    public Try<Blob> tryZip(final String zipEntryName, final InterviewSetDto interviewSet) {
        return new _JaxbWriter()
                .tryToString(interviewSet)
                .mapEmptyToFailure()
                .mapSuccessAsNullable(xml->
                    Clob.of(zipEntryName, CommonMimeType.XML, xml)
                    .toBlobUtf8()
                    .zip());
    }

    public Try<InterviewSetDto> tryUnzip(final Blob blob) {
        return tryReadSurvey(blob
                .unZip(CommonMimeType.XML)
                .asDataSource());
    }

}
