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

import java.math.BigDecimal;

import javax.annotation.processing.Generated;

import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * has no description
 * @param respondentOrdinal sequential
 * respondent
 * index
 * @param respondentAlias anonymized
 * respondent identifier,
 * unique to the
 * corresponding survey
 * @param interviewOrdinal respondent's
 * n-th interview
 * (chronological)
 * @param fco food
 * consumption
 * occasion
 * code
 * @param meal meal happened
 * when and where
 * @param quantity quantity
 * consumed [g]
 * @param GCALZB Nut.: Energie inkl. Ballaststoffen[kcal]
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenViewmodel")
@Named("dita.recall24.reporter.dom.ConsumptionRow")
@DomainObject
@DomainObjectLayout(
        describedAs = "has no description"
)
public record ConsumptionRow(
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "1",
                describedAs = "sequential\n"
                                + "respondent\n"
                                + "index",
                hidden = Where.NOWHERE
        )
        int respondentOrdinal,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "2",
                describedAs = "anonymized\n"
                                + "respondent identifier,\n"
                                + "unique to the\n"
                                + "corresponding survey",
                hidden = Where.NOWHERE
        )
        String respondentAlias,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "3",
                describedAs = "respondent's\n"
                                + "n-th interview\n"
                                + "(chronological)",
                hidden = Where.NOWHERE
        )
        int interviewOrdinal,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "4",
                describedAs = "food\n"
                                + "consumption\n"
                                + "occasion\n"
                                + "code",
                hidden = Where.NOWHERE
        )
        String fco,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "5",
                describedAs = "meal happened\n"
                                + "when and where",
                hidden = Where.NOWHERE
        )
        String meal,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "6",
                describedAs = "quantity\n"
                                + "consumed [g]",
                hidden = Where.NOWHERE
        )
        BigDecimal quantity,
        @PropertyLayout(
                fieldSetId = "details",
                sequence = "7",
                describedAs = "Nut.: Energie inkl. Ballaststoffen[kcal]",
                hidden = Where.NOWHERE
        )
        BigDecimal GCALZB) {
    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
