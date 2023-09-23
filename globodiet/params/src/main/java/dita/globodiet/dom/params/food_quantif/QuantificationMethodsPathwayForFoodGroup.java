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
package dita.globodiet.dom.params.food_quantif;

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
 * Quantification methods pathway for food groups/subgroups
 */
@Named("dita.globodiet.params.food_quantif.QuantificationMethodsPathwayForFoodGroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Quantification methods pathway for food groups/subgroups"
)
@PersistenceCapable(
        table = "QM_GROUP"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class QuantificationMethodsPathwayForFoodGroup {
    /**
     * Food group code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food group code",
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
     * Food subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Food subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubgroupCode;

    /**
     * Food sub-subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food sub-subgroup code",
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
     * Descriptor code of Physical state facet descface.facet_code+descface.descr_code (e.g. 0205)
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Descriptor code of Physical state facet descface.facet_code+descface.descr_code (e.g. 0205)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "PHYS_STATE",
            allowsNull = "true",
            length = 4
    )
    @Getter
    @Setter
    private String physicalStateFacetDescriptorLookupKey;

    /**
     * 1=raw, 2=cooked (as Consumed)
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "1=raw, 2=cooked (as Consumed)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RAW_COOKED",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String rawOrCookedAsConsumed;

    /**
     * Quantification method code:<br>
     * 'P' for photo,<br>
     * 'H' for HHM,<br>
     * 'U' for stdu,<br>
     * 'S' for standard portion,<br>
     * 'A' for shape
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Quantification method code:<br>\n"
                            + "'P' for photo,<br>\n"
                            + "'H' for HHM,<br>\n"
                            + "'U' for stdu,<br>\n"
                            + "'S' for standard portion,<br>\n"
                            + "'A' for shape",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "METHOD",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String quantificationMethodCode;

    /**
     * Photo code (if method='P' and 'A');<br>
     * either M_photos.ph_code or M_shapes.sh_code
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Photo code (if method='P' and 'A');<br>\n"
                            + "either M_photos.ph_code or M_shapes.sh_code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "METH_CODE",
            allowsNull = "true",
            length = 4
    )
    @Getter
    @Setter
    private String photoCode;

    /**
     * Comment
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Comment",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "COMMENT",
            allowsNull = "false",
            length = 200
    )
    @Getter
    @Setter
    private String comment;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
