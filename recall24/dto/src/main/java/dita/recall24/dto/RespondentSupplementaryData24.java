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

import java.math.BigDecimal;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

public record RespondentSupplementaryData24(

            /**
             * Parent interview.
             */
            @JsonIgnore
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
            BigDecimal heightCM,

            /**
             * Respondent's weight in units of kilogram,
             * as measured on the interview date.
             */
            BigDecimal weightKG,

            /**
             * Wake-up time on day of consumption.
             * Marks the starting time of reported consumption data.
             */
            LocalTime wakeupTimeOnDayOfConsumption,

            /**
             * Wake-up time on next day.
             * Marks the end 'hour' of reported consumption data,
             * on the day after the day of consumption.
             */
            LocalTime wakeupTimeOnDayAfterConsumption

            ) {

    public RespondentSupplementaryData24(
        final String specialDietId,
        final String specialDayId,
        final BigDecimal heightCM,
        final BigDecimal weightKG,
        final LocalTime wakeupOnRecallDay,
        final LocalTime wakeupOnNextDay) {
        this(new ObjectRef<>(null), specialDietId, specialDayId, heightCM, weightKG, wakeupOnRecallDay, wakeupOnNextDay);
    }

    public Interview24 parentInterview() {
        return parentInterviewRef.getValue();
    }

}
