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
 * Dietary supplement facet
 */
@Named("dita.globodiet.params.supplement.DietarySupplementFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement facet"
)
@PersistenceCapable(
        table = "DS_FACET"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class DietarySupplementFacet {
    /**
     * Facet code for Dietary Supplement
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet code for Dietary Supplement"
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
     * Facet name for Dietary Supplement
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Facet name for Dietary Supplement"
    )
    @Column(
            name = "DSFACET_NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String facetName;

    /**
     * To identify the mandatory facet used for quantification: 1=yes, 0=no.<br>
     * Only 1 facet (physical state) is used for quantification.
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "To identify the mandatory facet used for quantification: 1=yes, 0=no.\n"
                            + "Only 1 facet (physical state) is used for quantification."
    )
    @Column(
            name = "DSFACET_QUANT",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer mandatoryFacetUsedForQuantificationQ;

    /**
     * Facet with Mono or Multi selection of descriptors<br>
     * 0=mono,<br>
     * 1=multi
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Facet with Mono or Multi selection of descriptors\n"
                            + "0=mono,\n"
                            + "1=multi"
    )
    @Column(
            name = "DSFACET_TYPE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer singleOrMultiSelectionOfDescriptorsQ;

    /**
     * For maintenance: Main facets to be attributed to all supplements: 1=yes, 0=no.
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "For maintenance: Main facets to be attributed to all supplements: 1=yes, 0=no."
    )
    @Column(
            name = "DSFACET_MAIN",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer attributedToAllSupplementsQ;

    /**
     * Order to ask the facet (first, second...)
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Order to ask the facet (first, second...)"
    )
    @Column(
            name = "DSFACET_ORDER",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer orderToAsk;

    /**
     * Label on how to ask the facet question
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Label on how to ask the facet question"
    )
    @Column(
            name = "DSFACET_QUEST",
            allowsNull = "true",
            length = 300
    )
    @Getter
    @Setter
    private String labelOnHowToAskTheFacetQuestion;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}