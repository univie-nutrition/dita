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
package dita.globodiet.dom.params.recipe_description;

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
 * Cross reference between Recipe (sub)group and Facet/Descriptor (default pathway)
 */
@Named("dita.globodiet.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Cross reference between Recipe (sub)group and Facet/Descriptor (default pathway)",
        cssClassFa = "solid right-left"
)
@PersistenceCapable(
        table = "R_GROUPFAC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class CrossReferenceBetweenRecipeGroupAndDescriptor {
    /**
     * Recipe group code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Recipe group code<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RGROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "2",
            describedAs = "Recipe Subgroup code<br>----<br>required=false, unique=true",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RSUBGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeSubgroupCode;

    /**
     * Recipe Facet code
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Recipe Facet code<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeFacetCode;

    /**
     * Recipe Descriptor code
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Recipe Descriptor code<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RDESCR_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeDescriptorCode;

    /**
     * Default flag (if set to 'D', it is the default descriptor else blank)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "5",
            describedAs = "Default flag (if set to 'D', it is the default descriptor else blank)<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDEFAULT",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String defaultFlagQ;

    /**
     * Not in name flag (if set to 'N', the descriptor is not in the name else blank)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "6",
            describedAs = "Not in name flag (if set to 'N', the descriptor is not in the name else blank)<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RNOTINNAME",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String notInNameQ;

    /**
     * Order to display the facets within a group/subgroup
     */
    @Property
    @PropertyLayout(
            sequence = "7",
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
            sequence = "8",
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
