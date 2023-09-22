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
package dita.globodiet.dom.params.recipe_list;

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
 * Mixed recipes: Ingredients quantification for shape and photo methods
 */
@Named("dita.globodiet.params.recipe_list.RecipeIngredientQuantification")
@DomainObject
@DomainObjectLayout(
        describedAs = "Mixed recipes: Ingredients quantification for shape and photo methods"
)
@PersistenceCapable(
        table = "MIXEDING_QT"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeIngredientQuantification {
    /**
     * Recipe ID number the ingredient belong to
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Recipe ID number the ingredient belong to",
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
     * Sequential Number within a Mixed Recipe for Ingredient
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Sequential Number within a Mixed Recipe for Ingredient",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ING_NUM",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double sequentialNumberWithinARecipeForIngredient;

    /**
     * Ingredient Food or Recipe ID number
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Ingredient Food or Recipe ID number",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FOODNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String ingredientFoodOrRecipeCode;

    /**
     * Order of shape selection (e.g. 1)
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Order of shape selection (e.g. 1)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ORDER",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double orderOfShapeSelection;

    /**
     * Shape code (e.g. S001)
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Shape code (e.g. S001)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SH_CODE",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String shapeCode;

    /**
     * Shape surface in cm2 (e.g. 200cm2). 2 decimals
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Shape surface in cm2 (e.g. 200cm2). 2 decimals",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SH_SURFACE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double shapeSurfaceInCm2;

    /**
     * Shape: Thickness code (e.g. A or 58_1)
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Shape: Thickness code (e.g. A or 58_1)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "TH_CODE",
            allowsNull = "true",
            length = 10
    )
    @Getter
    @Setter
    private String shapeThicknessCode;

    /**
     * Thickness in mm (e.g. 40mm, 0.05 mm). 5 decimals
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Thickness in mm (e.g. 40mm, 0.05 mm). 5 decimals",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TH_THICK",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double thicknessInMm;

    /**
     * Photo: Number of the Selected Photo (e.g. 1 or 4)
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Photo: Number of the Selected Photo (e.g. 1 or 4)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "PH_CODE",
            allowsNull = "true",
            length = 4
    )
    @Getter
    @Setter
    private String photoCode;

    /**
     * Photo quantity
     */
    @Property
    @PropertyLayout(
            sequence = "10",
            describedAs = "Photo quantity",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PH_QTY",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double photoQuantity;

    /**
     * Shape & Photo: Fraction or unit (e.g. 3/5 or 1 or 2)
     */
    @Property
    @PropertyLayout(
            sequence = "11",
            describedAs = "Shape & Photo: Fraction or unit (e.g. 3/5 or 1 or 2)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FRACT",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String shapeAndPhotoFractionOrUnit;

    /**
     * Sequential Number for Ingredients within a Sub-Recipe
     */
    @Property
    @PropertyLayout(
            sequence = "12",
            describedAs = "Sequential Number for Ingredients within a Sub-Recipe",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "S_ING_NUM",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double sequentialNumberForIngredientsWithinASubRecipe;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
