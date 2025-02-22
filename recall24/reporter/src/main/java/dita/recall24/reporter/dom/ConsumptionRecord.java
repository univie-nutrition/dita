/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by Causeway-Stuff code generator.
package dita.recall24.reporter.dom;

import dita.commons.types.DecimalVector;
import jakarta.inject.Named;
import java.lang.String;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import lombok.Builder;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.PropertyLayout;

/**
 * Represents a single food consumption as flattened data,
 * useful for tabular data exports.
 * @param respondentOrdinal sequential
 * respondent
 * index
 * @param respondentAlias anonymized
 * respondent identifier,
 * unique to the
 * corresponding survey
 * @param respondentSex 1=male
 * 2=female
 * @param respondentAge age at first
 * consumption day
 * =ageInDays/365.2422
 * @param interviewCount respondent's total
 * number of interviews
 * considered with
 * this report
 * @param interviewOrdinal respondent's
 * n-th interview
 * (chronological)
 * @param consumptionDate date of
 * consumption
 * @param consumptionDayOfWeek day of week
 * of consmpt.
 * Mo=1..Su=7
 * @param specialDay homeoffice,
 * feast, etc.
 * @param specialDiet diabetes,
 * gluten-free
 * etc.
 * @param fco consumption
 * occasion
 * code
 * @param poc place of
 * consumption
 * code
 * @param meal meal happened
 * when and where
 * @param mealOrdinal Dewey
 * decimal
 * order
 * @param recordType type of food
 * record
 * @param food name of consumed
 * food
 * @param foodId food or
 * composite
 * identifier
 * @param groupId food or
 * composite
 * group
 * @param facetIds facet descriptor
 * identifiers
 * (comma separated)
 * @param quantity quantity
 * consumed [g]
 * @param fcdbId food composition
 * database identifier
 * (nutrient mapping)
 * @param nutrients has no description
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenViewmodel")
@Named("dita.recall24.reporter.dom.ConsumptionRecord")
@DomainObject
@DomainObjectLayout(
        named = "Consumption",
        describedAs = "Represents a single food consumption as flattened data,\n"
                        + "useful for tabular data exports."
)
@Builder
public record ConsumptionRecord(
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "1",
                describedAs = "sequential\n"
                                + "respondent\n"
                                + "index"
        )
        int respondentOrdinal,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "2",
                describedAs = "anonymized\n"
                                + "respondent identifier,\n"
                                + "unique to the\n"
                                + "corresponding survey"
        )
        String respondentAlias,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "3",
                describedAs = "1=male\n"
                                + "2=female"
        )
        int respondentSex,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "4",
                describedAs = "age at first\n"
                                + "consumption day\n"
                                + "=ageInDays/365.2422"
        )
        BigDecimal respondentAge,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "5",
                describedAs = "respondent's total\n"
                                + "number of interviews\n"
                                + "considered with\n"
                                + "this report"
        )
        int interviewCount,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "6",
                describedAs = "respondent's\n"
                                + "n-th interview\n"
                                + "(chronological)"
        )
        int interviewOrdinal,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "7",
                describedAs = "date of\n"
                                + "consumption"
        )
        LocalDate consumptionDate,
        @PropertyLayout(
                named = "Dowoc",
                fieldSetId = "details",
                sequence = "8",
                describedAs = "day of week\n"
                                + "of consmpt.\n"
                                + "Mo=1..Su=7"
        )
        int consumptionDayOfWeek,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "9",
                describedAs = "homeoffice,\n"
                                + "feast, etc."
        )
        String specialDay,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "10",
                describedAs = "diabetes,\n"
                                + "gluten-free\n"
                                + "etc."
        )
        String specialDiet,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "11",
                describedAs = "consumption\n"
                                + "occasion\n"
                                + "code"
        )
        String fco,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "12",
                describedAs = "place of\n"
                                + "consumption\n"
                                + "code"
        )
        String poc,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "13",
                describedAs = "meal happened\n"
                                + "when and where"
        )
        String meal,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "14",
                describedAs = "Dewey\n"
                                + "decimal\n"
                                + "order"
        )
        String mealOrdinal,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "15",
                describedAs = "type of food\n"
                                + "record"
        )
        String recordType,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "16",
                describedAs = "name of consumed\n"
                                + "food"
        )
        String food,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "17",
                describedAs = "food or\n"
                                + "composite\n"
                                + "identifier"
        )
        String foodId,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "18",
                describedAs = "food or\n"
                                + "composite\n"
                                + "group"
        )
        String groupId,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "19",
                describedAs = "facet descriptor\n"
                                + "identifiers\n"
                                + "(comma separated)"
        )
        String facetIds,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "20",
                describedAs = "quantity\n"
                                + "consumed [g]"
        )
        BigDecimal quantity,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "21",
                describedAs = "food composition\n"
                                + "database identifier\n"
                                + "(nutrient mapping)"
        )
        String fcdbId,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "22",
                describedAs = "has no description"
        )
        DecimalVector nutrients) {
    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
