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
package dita.globodiet.dom.params.food_coefficient;

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
 * % of fat left in the dish for food
 */
@Named("dita.globodiet.params.food_coefficient.PercentOfFatLeftInTheDishForFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "% of fat left in the dish for food",
        cssClassFa = "solid percent"
)
@PersistenceCapable(
        table = "FATLEFTO"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PercentOfFatLeftInTheDishForFood {
    /**
     * Group code of the FAT's group
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Group code of the FAT's group",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String fatGroupCode;

    /**
     * Subgroup code of the FAT
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Subgroup code of the FAT",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fatSubgroupCode;

    /**
     * Sub-Subgroup code of the FAT
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Sub-Subgroup code of the FAT",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fatSubSubgroupCode;

    /**
     * Percentage of fat left in the dish
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Percentage of fat left in the dish",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PCT_LEFT",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double percentageOfFatLeftInTheDish;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
