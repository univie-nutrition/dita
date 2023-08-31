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
 * Improbable sequence of facets/descriptors
 */
@Named("dita.globodiet.params.food_descript.ImprobableSequencesOfFacetsAndDescriptors")
@DomainObject
@DomainObjectLayout(
        describedAs = "Improbable sequence of facets/descriptors"
)
@PersistenceCapable(
        table = "DESC_IMP"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class ImprobableSequencesOfFacetsAndDescriptors {
    /**
     * Food identification number
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food identification number"
    )
    @Column(
            name = "FOODNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodIdentificationNumber;

    /**
     * Food group code
     */
    @Property
    @PropertyLayout(
            sequence = "2",
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
     * Food subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food subgroup code"
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
     * Food sub-subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Food sub-subgroup code"
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
            sequence = "5",
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
            sequence = "6",
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
     * Facet string
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Facet string"
    )
    @Column(
            name = "FACET_STR",
            allowsNull = "true",
            length = 1000000000
    )
    @Getter
    @Setter
    private String facetString;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
