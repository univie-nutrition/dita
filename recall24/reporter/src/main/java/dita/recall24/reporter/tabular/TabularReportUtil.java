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
package dita.recall24.reporter.tabular;

import lombok.experimental.UtilityClass;

import dita.recall24.dto.InterviewSet24;

@UtilityClass
public class TabularReportUtil {

    public enum Aggregation {
        /**
         * Each consumption is reported as is.
         */
        NONE,
        /**
         * Sum total of nutrient values for each meal.
         */
        MEAL,
        /**
         * Sum total of nutrient values for each interview.
         */
        INTERVIEW,
        /**
         * Average of nutrient values for each respondent per interview (averaged over all interviews available for a given respondent).
         */
        RESPONDENT_AVERAGE,
        /**
         * Variant that groups by food-group.
         */
        RESPONDENT_AVERAGE_GROUP_BY_FOOD_GROUP,
        /**
         * Variant that groups by food-group and food-subgroup.
         */
        RESPONDENT_AVERAGE_GROUP_BY_FOOD_GROUP_AND_SUBGROUP
    }

    public record TabularReport(
            InterviewSet24.Dto interviewSet,
            Aggregation aggregation) {
    }

}
