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
package dita.globodiet.dom.params.quantif;

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
 * Household Measure
 */
@Named("dita.globodiet.params.quantif.HouseholdMeasure")
@DomainObject
@DomainObjectLayout(
        describedAs = "Household Measure"
)
@PersistenceCapable(
        table = "M_HHM"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class HouseholdMeasure {
    /**
     * HouseholdMeasure code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "HouseholdMeasure code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "HHM_CODE",
            allowsNull = "false",
            length = 4
    )
    @Getter
    @Setter
    private String code;

    /**
     * HouseholdMeasure volume in cm3.
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "HouseholdMeasure volume in cm3.",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "HHM_VOLUME",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double volumeInCm3;

    /**
     * HouseholdMeasure fractions ( 1/4,2/3,...), specified in a text field separated by commas.<br>
     * The value of each expression will be evaluated as factor of the total volume
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "HouseholdMeasure fractions ( 1/4,2/3,...), specified in a text field separated by commas.<br>\n"
                            + "The value of each expression will be evaluated as factor of the total volume",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "HHM_FRACT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String fractions;

    /**
     * Comment attached to the HouseholdMeasure
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Comment attached to the HouseholdMeasure",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "COMMENT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String comment;

    /**
     * Order to display the HouseholdMeasure
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Order to display the HouseholdMeasure",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "D_ORDER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double displayOrder;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
