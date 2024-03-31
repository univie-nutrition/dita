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

import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

public record RespondentMetaData24(

        /**
         * Parent interview.
         */
        ObjectRef<Interview24> parentInterviewRef,

        /**
         * Diet as practiced on the interview date.
         */
        String specialDietId,

        /**
         * Special day as practiced on the interview date.
         */
        String specialDayId,

        /**
         * Respondent's height in units of centimeter,
         * as measured on the interview date.
         */
        double heightCM,

        /**
         * Respondent's weight in units of kilogram,
         * as measured on the interview date.
         */
        double weightKG

        ) implements dita.recall24.api.RespondentMetaData24 {

    @Override
    public Interview24 parentInterview() {
        return parentInterviewRef.getValue();
    }
}
