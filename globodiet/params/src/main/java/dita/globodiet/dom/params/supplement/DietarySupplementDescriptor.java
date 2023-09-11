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
package dita.globodiet.dom.params.supplement;

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
 * Dietary supplement descriptor
 */
@Named("dita.globodiet.params.supplement.DietarySupplementDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement descriptor"
)
@PersistenceCapable(
        table = "DS_DESCFACE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class DietarySupplementDescriptor {
    /**
     * Facet code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet code"
    )
    @Column(
            name = "DSFACET_CODE",
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
            sequence = "2",
            describedAs = "Descriptor code"
    )
    @Column(
            name = "DSDESCR_CODE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String descriptorCode;

    /**
     * Descriptor name
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Descriptor name"
    )
    @Column(
            name = "DSDESCR_NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String descriptorName;

    /**
     * Only for the facet with Dsfacet_type=1,<br>
     * for the supplement quantification If HHM=1 Then HHM method is proposed Else No HHM=0
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Only for the facet with Dsfacet_type=1,<br>\n"
                            + "for the supplement quantification If HHM=1 Then HHM method is proposed Else No HHM=0"
    )
    @Column(
            name = "DSDESCR_HHM",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer householdMeasuresProposedOrNot;

    /**
     * Default Descriptor.<br>
     * When this facet is displayed, the cursor has to be focussed on the default descriptor (only 1 defaulty):<br>
     * 1=default,<br>
     * 0=other
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Default Descriptor.<br>\n"
                            + "When this facet is displayed, the cursor has to be focussed on the default descriptor (only 1 defaulty):<br>\n"
                            + "1=default,<br>\n"
                            + "0=other"
    )
    @Column(
            name = "DSDESCR_DEFAULT",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer defaultDescriptor;

    /**
     * Descriptor with type='other' : 1=yes 0=no
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Descriptor with type='other' : 1=yes 0=no"
    )
    @Column(
            name = "DSDESCR_OTHER",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer otherQ;

    /**
     * 0=not single descriptor 1=single descriptor
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "0=not single descriptor 1=single descriptor"
    )
    @Column(
            name = "DSDESCR_SINGLE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer singleDescriptorQ;

    /**
     * Display order (1=first, 2=second…)
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Display order (1=first, 2=second…)"
    )
    @Column(
            name = "DSDESCR_ORDER",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer displayOrderFirstOrSecond;

    /**
     * Not in name flag
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Not in name flag"
    )
    @Column(
            name = "NOTINNAME",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer notInName;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
