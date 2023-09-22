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
 * Country involved
 */
@Named("dita.globodiet.params.interview.CountryInvolved")
@DomainObject
@DomainObjectLayout(
        describedAs = "Country involved",
        cssClassFa = "earth-europe"
)
@PersistenceCapable(
        table = "COUNTRY"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class CountryInvolved {
    /**
     * Country code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Country code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CTRYCODE",
            allowsNull = "true",
            length = 3
    )
    @Getter
    @Setter
    private String countryCode;

    /**
     * Country name
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Country name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CTRY_NAME",
            allowsNull = "true",
            length = 50
    )
    @Getter
    @Setter
    private String countryName;

    @ObjectSupport
    public String title() {
        return String.format("%s", countryName);
    }
}
