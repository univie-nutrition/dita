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
package dita.globodiet.dom.params.recipe_quantif;

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
 * Quantification methods pathway for recipes
 */
@Named("dita.globodiet.params.recipe_quantif.QuantificationMethodPathwayForRecipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "Quantification methods pathway for recipes"
)
@PersistenceCapable(
        table = "QM_RECIP"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class QuantificationMethodPathwayForRecipe {
    /**
     * Recipe identification number (R_IDNUM)
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Recipe identification number (R_IDNUM)<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "ID_NUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    /**
     * Quantification method code:
     * 'P' for photo,
     * 'H' for HHM,
     * 'U' for stdu,
     * 'A' Shape
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Quantification method code:<br>'P' for photo,<br>'H' for HHM,<br>'U' for stdu,<br>'A' Shape<br>----<br>required=true, unique=false",
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
     * Photo code (if method='P' and 'A');
     * either M_photos.ph_code or M_shapes.sh_code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "3",
            describedAs = "Photo code (if method='P' and 'A');<br>either M_photos.ph_code or M_shapes.sh_code<br>----<br>required=false, unique=false",
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

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
