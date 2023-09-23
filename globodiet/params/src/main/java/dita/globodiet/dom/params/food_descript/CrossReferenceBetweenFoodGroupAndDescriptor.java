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
package dita.globodiet.dom.params.food_descript;

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
 * Cross reference between Food (sub)group and Facet/Descriptor (default pathway)
 */
@Named("dita.globodiet.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Cross reference between Food (sub)group and Facet/Descriptor (default pathway)",
        cssClassFa = "solid right-left"
)
@PersistenceCapable(
        table = "GROUPFAC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class CrossReferenceBetweenFoodGroupAndDescriptor {
    /**
     * Food group code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food group code<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String foodGroupCode;

    /**
     * Food Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "2",
            describedAs = "Food Subgroup code<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubgroupCode;

    /**
     * Food Sub-subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food Sub-subgroup code<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    /**
     * Facet code
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Facet code<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String facetCode;

    /**
     * Descriptor code
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Descriptor code<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "DESCR_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String descriptorCode;

    /**
     * Default flag (if set to 'D' it is the default descriptor)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "6",
            describedAs = "Default flag (if set to 'D' it is the default descriptor)<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DEFAULT",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String defaultFlag;

    /**
     * Not in name flag
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "7",
            describedAs = "Not in name flag<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NOTINNAME",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String notInNameFlag;

    /**
     * Order to display the facets within a group/subgroup
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Order to display the facets within a group/subgroup<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ORDER_FAC",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int facetDisplayOrder;

    /**
     * Order to display the descriptors within a group/subgroup and a facet
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Order to display the descriptors within a group/subgroup and a facet<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ORDER_DESC",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int descriptorDisplayOrder;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
