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
package dita.globodiet.dom.params.food_table;

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
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Nutrient list and definition
 */
@Named("dita.globodiet.params.food_table.NutrientListAndDefinition")
@DomainObject
@DomainObjectLayout(
        describedAs = "Nutrient list and definition"
)
@PersistenceCapable(
        table = "NUTRIENT"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class NutrientListAndDefinition {
    /**
     * Nutrient code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Nutrient code<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_CODE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int nutrientCode;

    /**
     * Nutrient Name
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Nutrient Name<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String nutrientName;

    /**
     * Nutrient unit (e.g. kcal, g, mg…)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Nutrient unit (e.g. kcal, g, mg…)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_UNIT",
            allowsNull = "false",
            length = 20
    )
    @Getter
    @Setter
    private String nutrientUnit;

    /**
     * 0=not displayed in the 'nutrient checks' screen
     * 1=displayed in the 'nutrient checks' screen
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "0=not displayed in the 'nutrient checks' screen<br>1=displayed in the 'nutrient checks' screen<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NTR_DISPLAY",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int whetherDisplayedInTheNutrientChecksScreen;

    /**
     * Comment on nutrient
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "5",
            describedAs = "Comment on nutrient<br>----<br>required=false, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "COMMENT",
            allowsNull = "true",
            length = 1000000000
    )
    @Getter
    @Setter
    private String commentOnNutrient;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
