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
package dita.globodiet.dom.params.recipe_max;

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
import org.apache.causeway.applib.annotation.Where;

/**
 * Maximum value for a recipe or a (sub-)group
 */
@Named("dita.globodiet.params.recipe_max.MaximumValueForARecipeOrASubgroup")
@DomainObject
@DomainObjectLayout(
        describedAs = "Maximum value for a recipe or a (sub-)group"
)
@PersistenceCapable(
        table = "MAXRVAL"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class MaximumValueForARecipeOrASubgroup {
    /**
     * Recipe group code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Recipe group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe subgroup code
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Recipe subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_SUBGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeSubgroupCode;

    /**
     * Recipe code
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Recipe code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    /**
     * Maximum value
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Maximum value",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "MAXIMA",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double maximumValue;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
