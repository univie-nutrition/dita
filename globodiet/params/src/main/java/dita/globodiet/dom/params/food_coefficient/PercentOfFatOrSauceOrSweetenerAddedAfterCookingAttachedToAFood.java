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
 * 
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.food_coefficient;

import jakarta.inject.Named;
import java.lang.Double;
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

/**
 * % of fat/sauce/sweetener added after cooking attached to a food
 */
@Named("dita.globodiet.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingAttachedToAFood")
@DomainObject
@DomainObjectLayout(
        describedAs = "% of fat/sauce/sweetener added after cooking attached to a food"
)
@PersistenceCapable(
        table = "SPFACOOK"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class PercentOfFatOrSauceOrSweetenerAddedAfterCookingAttachedToAFood {
    /**
     * Food group code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food group code"
    )
    @Column(
            name = "FOOD_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodGroupCode;

    /**
     * Food subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Food subgroup code"
    )
    @Column(
            name = "FOOD_SUBG1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubgroupCode;

    /**
     * Food sub-Subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food sub-Subgroup code"
    )
    @Column(
            name = "FOOD_SUBG2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    /**
     * Food code
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Food code"
    )
    @Column(
            name = "FOOD_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodCode;

    /**
     * Fat group code for F/S/S
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Fat group code for F/S/S"
    )
    @Column(
            name = "X_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fatGroupCodeForFSS;

    /**
     * Fat subgroup code for F/S/S
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Fat subgroup code for F/S/S"
    )
    @Column(
            name = "X_SUBG1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fatSubgroupCodeForFSS;

    /**
     * Fat sub-subgroup code for F/S/S
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Fat sub-subgroup code for F/S/S"
    )
    @Column(
            name = "X_SUBG2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String fatSubSubgroupCodeForFSS;

    /**
     * Fat code for F/S/S
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Fat code for F/S/S"
    )
    @Column(
            name = "X_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String fatCodeForFSS;

    /**
     * Percentage of fat
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Percentage of fat"
    )
    @Column(
            name = "PCT_ADDED",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double percentageOfFat;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
