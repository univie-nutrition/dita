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
import org.apache.causeway.applib.annotation.Where;

/**
 * Facet describing food (not recipe)
 */
@Named("dita.globodiet.params.food_descript.Facet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet describing food (not recipe)",
        cssClassFa = "tags olive"
)
@PersistenceCapable(
        table = "FACETS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class Facet {
    /**
     * Facet code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Facet name
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Facet name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Facet text (text to show on the screen describing the facet)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Facet text (text to show on the screen describing the facet)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_TEXT",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String text;

    /**
     * 0=Standard facets with descriptors available in Descface table<br>
     * 1=Facets with descriptors available in Brandnam table<br>
     * 2=Facets with descriptors available in Foods table - facet 15 type of fat<br>
     * 3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "0=Standard facets with descriptors available in Descface table<br>\n"
                            + "1=Facets with descriptors available in Brandnam table<br>\n"
                            + "2=Facets with descriptors available in Foods table - facet 15 type of fat<br>\n"
                            + "3=Facets with descriptors available in Foods table - facet 16 type of milk/liquid used",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_TYPE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer type;

    /**
     * 0 = facet with mono-selection of descriptor<br>
     * 1 = facets with multi-selection of descriptors
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "0 = facet with mono-selection of descriptor<br>\n"
                            + "1 = facets with multi-selection of descriptors",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_TYPE_S",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer typeCardinality;

    /**
     * If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.<br>
     * Comma is used as delimiter (e.g. 10,050701,050702)
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "If Facet_type=2, series of groups/subgroups used to display the foods from the Foods table.<br>\n"
                            + "Comma is used as delimiter (e.g. 10,050701,050702)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_GRP",
            allowsNull = "true",
            length = 1000000000
    )
    @Getter
    @Setter
    private String group;

    /**
     * Label on how to ask the facet question
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Label on how to ask the facet question",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FACET_QUEST",
            allowsNull = "true",
            length = 300
    )
    @Getter
    @Setter
    private String labelOnHowToAskTheFacetQuestion;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
    }
}
