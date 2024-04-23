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
package dita.recall24.model;

import java.time.LocalDate;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;

import dita.commons.types.Sex;
import io.github.causewaystuff.treeview.applib.annotations.TreeSubNodes;

public record Respondent24(
        /**
         * Anonymized respondent identifier, unique to the corresponding survey.
         */
        String alias,

        LocalDate dateOfBirth,

        Sex sex,

        /**
         * Interviews that this respondent was subject to.
         */
        @TreeSubNodes
        Can<Interview24> interviews

        ) implements dita.recall24.api.Respondent24, Node24 {


    /**
     * Interviews are sorted by interview-date.
     * All ordinals are filled in. //TODO
     */
    Respondent24 normalize() {
        var interviewsSorted = interviews()
                .sorted((a, b)->a.interviewDate().compareTo(b.interviewDate()));

        interviewsSorted.forEach(IndexedConsumer.offset(1, (ordinal, inv)->
            inv.interviewOrdinalRef().setValue(ordinal))); // fill in interview's ordinal

        return new Respondent24(alias, dateOfBirth, sex, interviewsSorted);
    }

}
