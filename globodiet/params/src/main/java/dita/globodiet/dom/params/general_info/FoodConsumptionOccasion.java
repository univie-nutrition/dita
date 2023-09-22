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
package dita.globodiet.dom.params.general_info;

import jakarta.inject.Named;
import java.lang.Integer;
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
 * Food Consumption Occasion
 */
@Named("dita.globodiet.params.general_info.FoodConsumptionOccasion")
@DomainObject
@DomainObjectLayout(
        describedAs = "Food Consumption Occasion"
)
@PersistenceCapable(
        table = "FCO"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FoodConsumptionOccasion {
    /**
     * FCO code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "FCO code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_CODE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * FCO long label (text displayed on screen)
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "FCO long label (text displayed on screen)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String textDisplayedOnScreen;

    /**
     * FCO type: if =1 the FCO can be selected several times (e.g. During morning)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "FCO type: if =1 the FCO can be selected several times (e.g. During morning)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_MODE",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String mode;

    /**
     * FCO short label to identify easily the FCO
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "FCO short label to identify easily the FCO",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_SNAME",
            allowsNull = "true",
            length = 50
    )
    @Getter
    @Setter
    private String shortLabelToIdentifyEasily;

    /**
     * 1=main FCO (to be displayed in nutrient check screen)<br>
     * 0=non main FCO
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "1=main FCO (to be displayed in nutrient check screen)<br>\n"
                            + "0=non main FCO",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_PRINCIPAL",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer displayInNutrientCheckScreenQ;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
