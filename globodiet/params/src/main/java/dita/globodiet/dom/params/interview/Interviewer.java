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
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.interview;

import jakarta.inject.Named;
import java.lang.String;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Interviewer
 */
@Named("dita.globodiet.params.interview.Interviewer")
@DomainObject
@DomainObjectLayout(
        describedAs = "Interviewer",
        cssClassFa = "solid user"
)
@PersistenceCapable(
        table = "INTVIEWR"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class Interviewer {
    /**
     * Interviewer code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Interviewer code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "INTV_CODE",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String interviewerCode;

    /**
     * Interviewer family name
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Interviewer family name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "INTV_FNAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String interviewerFamilyName;

    /**
     * Interviewer name
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Interviewer name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "INTV_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String interviewerName;

    /**
     * Country code
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Country code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "COUNTRY",
            allowsNull = "false",
            length = 3
    )
    @Getter
    @Setter
    private String countryCode;

    /**
     * Center code
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Center code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "CENTER",
            allowsNull = "false",
            length = 3
    )
    @Getter
    @Setter
    private String centerCode;

    @ObjectSupport
    public String title() {
        return String.format("%s, %s (code=%s)", interviewerFamilyName, interviewerName, interviewerCode);
    }
}
