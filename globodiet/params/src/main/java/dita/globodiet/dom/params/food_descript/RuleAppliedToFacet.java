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
 * Rule applied to facet
 */
@Named("dita.globodiet.params.food_descript.RuleAppliedToFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Rule applied to facet"
)
@PersistenceCapable(
        table = "FACETRUL"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RuleAppliedToFacet {
    /**
     * Facet where the rule must be applied.
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet where the rule must be applied.<br>----<br>required=true, unique=false",
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
     * Facet code + Descriptor code that must exist in the current food description
     * to allow the facet (FACET_CODE) to be asked.
     * Additionally a group/subgroup code can be defined to force the food being described
     * to belong to a specific food group to allow the facet to be asked (leave it to blanks if not applicable).
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Facet code + Descriptor code that must exist in the current food description<br>to allow the facet (FACET_CODE) to be asked.<br>Additionally a group/subgroup code can be defined to force the food being described<br>to belong to a specific food group to allow the facet to be asked (leave it to blanks if not applicable).<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACDESC",
            allowsNull = "false",
            length = 4
    )
    @Getter
    @Setter
    private String facetDescriptorLookupKey;

    /**
     * Group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "3",
            describedAs = "Group code<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
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
     * Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "4",
            describedAs = "Subgroup code<br>----<br>required=false, unique=false",
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
     * Sub-subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "5",
            describedAs = "Sub-subgroup code<br>----<br>required=false, unique=true",
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

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
