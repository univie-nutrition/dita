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
package dita.globodiet.dom.params.recipe_list;

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
 * Mixed recipes: Names, identification number and class
 */
@Named("dita.globodiet.params.recipe_list.MixedRecipeNames")
@DomainObject
@DomainObjectLayout(
        describedAs = "Mixed recipes: Names, identification number and class"
)
@PersistenceCapable(
        table = "MIXEDREC"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class MixedRecipeNames {
    /**
     * Recipe ID number
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Recipe ID number"
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String recipeIDNumber;

    /**
     * Group code of the recipe classification
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Group code of the recipe classification"
    )
    @Column(
            name = "R_GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String groupCodeOfTheRecipeClassification;

    /**
     * Subgroup code of the recipe classification
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Subgroup code of the recipe classification"
    )
    @Column(
            name = "R_SUBGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String subgroupCodeOfTheRecipeClassification;

    /**
     * Recipe name
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Recipe name"
    )
    @Column(
            name = "R_NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String recipeName;

    /**
     * Type of recipe: 1.1=Open – Known 1.2=Open – Unknown 1.3=Open with brand 2.1=Closed 2.2=Closed with brand 3.0=Commercial 4.1=New – Known 4.2=New – Unknown
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Type of recipe: 1.1=Open – Known 1.2=Open – Unknown 1.3=Open with brand 2.1=Closed 2.2=Closed with brand 3.0=Commercial 4.1=New – Known 4.2=New – Unknown"
    )
    @Column(
            name = "R_TYPE",
            allowsNull = "true",
            length = 3
    )
    @Getter
    @Setter
    private String typeOfRecipe;

    /**
     * Brand name for commercial recipe
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Brand name for commercial recipe"
    )
    @Column(
            name = "R_BRAND",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String brandNameForCommercialRecipe;

    /**
     * SH = Shadow or blank
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "SH = Shadow or blank"
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String shadowOrBlank;

    /**
     * 0=recipe without sub-recipe<br>
     * 1=recipe with sub-recipe
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "0=recipe without sub-recipe\n"
                            + "1=recipe with sub-recipe"
    )
    @Column(
            name = "R_SUB",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer recipeWithOrWithoutSubRecipe;

    /**
     * has no description
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "has no description"
    )
    @Column(
            name = "STATUS",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String status;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
