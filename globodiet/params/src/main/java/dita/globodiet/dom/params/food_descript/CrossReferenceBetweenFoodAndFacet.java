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
package dita.globodiet.dom.params.food_descript;

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

/**
 * Cross reference between Food (sub)group and Facet/Descriptor (default pathway)
 */
@Named("dita.globodiet.params.food_descript.CrossReferenceBetweenFoodAndFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Cross reference between Food (sub)group and Facet/Descriptor (default pathway)"
)
@PersistenceCapable(
        table = "GROUPFAC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class CrossReferenceBetweenFoodAndFacet {
    /**
     * Food group code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food group code"
    )
    @Column(
            name = "GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodGroupCode;

    /**
     * Food Subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Food Subgroup code"
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
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food Sub-subgroup code"
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
            describedAs = "Facet code"
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "true",
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
            describedAs = "Descriptor code"
    )
    @Column(
            name = "DESCR_CODE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String descriptorCode;

    /**
     * Default flag (if set to 'D' it is the default descriptor)
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Default flag (if set to 'D' it is the default descriptor)"
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
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Not in name flag"
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
     * Order to displayed the facets within a group/subgroup
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Order to displayed the facets within a group/subgroup"
    )
    @Column(
            name = "ORDER_FAC",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer orderToDisplayedTheFacetsWithinAGroupOrSubgroup;

    /**
     * Order to displayed the descriptors within a group/subgroup and a facet
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Order to displayed the descriptors within a group/subgroup and a facet"
    )
    @Column(
            name = "ORDER_DESC",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer orderToDisplayedTheDescriptorsWithinAGroupOrSubgroupAndAFacet;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
