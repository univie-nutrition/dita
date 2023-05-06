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
package dita.recall24.api;

import java.time.LocalDate;

import org.apache.causeway.commons.collections.Can;

/**
 * Represents a (single) 24h recall interview event.
 */
public interface Interview24 {

    /**
     * Interview date.
     */
    LocalDate interviewDate();

    /**
     *  Each respondent can have one ore more interviews within the context of a specific survey.
     *  This ordinal denotes the n-th interview (when ordered by interview date).
     */
    int interviewOrdinal();

    // -- PARENT / CHILD

    /**
     * Respondent for this interview.
     */
    Respondent24 respondent();

    /**
     * Respondent meta-data for this interview.
     */
    RespondentMetaData24 respondentMetaData();

    /**
     * The meals of this interview.
     */
    Can<? extends Meal24> meals();

    // -- SHORTCUTS

    /**
     * Gets the respondent's alias.
     * @see Respondent24#alias()
     */
    default String respondentAlias() {
        return respondent().alias();
    }

}




