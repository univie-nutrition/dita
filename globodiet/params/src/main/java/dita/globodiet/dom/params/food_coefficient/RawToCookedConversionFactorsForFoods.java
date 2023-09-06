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
 * Raw to cooked conversion factors for foods
 */
@Named("dita.globodiet.params.food_coefficient.RawToCookedConversionFactorsForFoods")
@DomainObject
@DomainObjectLayout(
        describedAs = "Raw to cooked conversion factors for foods"
)
@PersistenceCapable(
        table = "RAWCOOK"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RawToCookedConversionFactorsForFoods {
    /**
     * Food identification number (FOODNUM)
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food identification number (FOODNUM)"
    )
    @Column(
            name = "ID_NUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodIdNumber;

    /**
     * Raw to cooked factor
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Raw to cooked factor"
    )
    @Column(
            name = "RC_FACTOR",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double rawToCookedFactor;

    /**
     * Facet string; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Facet string; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)"
    )
    @Column(
            name = "FACETS_STR",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String facetDescriptorLookupKey;

    /**
     * Priority order
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Priority order"
    )
    @Column(
            name = "PRIORITY",
            allowsNull = "true",
            length = 3
    )
    @Getter
    @Setter
    private String priority;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
