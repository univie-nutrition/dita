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
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.params.quantif;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Double;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.causewaystuff.companion.applib.services.lookup.Cloneable;
import org.causewaystuff.companion.applib.services.search.SearchService;

/**
 * Mixed recipes: Ingredients quantification for shape and photo methods
 */
@Generated("org.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.quantif.RecipeIngredientQuantification")
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
public class RecipeIngredientQuantification implements Cloneable<RecipeIngredientQuantification> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Recipe ID number the ingredient belong to
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
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
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
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
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
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
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
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
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
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
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
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
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
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
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
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

    @Override
    public String toString() {
        return "RecipeIngredientQuantification(" + "recipeCode=" + getRecipeCode() + ","
         +"sequentialNumberWithinARecipeForIngredient=" + getSequentialNumberWithinARecipeForIngredient() + ","
         +"ingredientFoodOrRecipeCode=" + getIngredientFoodOrRecipeCode() + ","
         +"orderOfShapeSelection=" + getOrderOfShapeSelection() + ","
         +"shapeCode=" + getShapeCode() + ","
         +"shapeSurfaceInCm2=" + getShapeSurfaceInCm2() + ","
         +"shapeThicknessCode=" + getShapeThicknessCode() + ","
         +"thicknessInMm=" + getThicknessInMm() + ","
         +"photoCode=" + getPhotoCode() + ","
         +"photoQuantity=" + getPhotoQuantity() + ","
         +"shapeAndPhotoFractionOrUnit=" + getShapeAndPhotoFractionOrUnit() + ","
         +"sequentialNumberForIngredientsWithinASubRecipe=" + getSequentialNumberForIngredientsWithinASubRecipe() + ")";
    }

    @Programmatic
    @Override
    public RecipeIngredientQuantification copy() {
        var copy = repositoryService.detachedEntity(new RecipeIngredientQuantification());
        copy.setRecipeCode(getRecipeCode());
        copy.setSequentialNumberWithinARecipeForIngredient(getSequentialNumberWithinARecipeForIngredient());
        copy.setIngredientFoodOrRecipeCode(getIngredientFoodOrRecipeCode());
        copy.setOrderOfShapeSelection(getOrderOfShapeSelection());
        copy.setShapeCode(getShapeCode());
        copy.setShapeSurfaceInCm2(getShapeSurfaceInCm2());
        copy.setShapeThicknessCode(getShapeThicknessCode());
        copy.setThicknessInMm(getThicknessInMm());
        copy.setPhotoCode(getPhotoCode());
        copy.setPhotoQuantity(getPhotoQuantity());
        copy.setShapeAndPhotoFractionOrUnit(getShapeAndPhotoFractionOrUnit());
        copy.setSequentialNumberForIngredientsWithinASubRecipe(getSequentialNumberForIngredientsWithinASubRecipe());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            navigable = Navigable.PARENT,
            hidden = Where.EVERYWHERE
    )
    @NotPersistent
    public RecipeIngredientQuantification.Manager getNavigableParent() {
        return new RecipeIngredientQuantification.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link RecipeIngredientQuantification}
     */
    @Generated("org.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.quantif.RecipeIngredientQuantification.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Mixed recipes: Ingredients quantification for shape and photo methods"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
        )
        @PropertyLayout(
                fieldSetId = "searchBar"
        )
        @Getter
        @Setter
        private String search;

        @ObjectSupport
        public String title() {
            return "Manage Recipe Ingredient Quantification";
        }

        @Collection
        public final List<RecipeIngredientQuantification> getListOfRecipeIngredientQuantification(
                ) {
            return searchService.search(RecipeIngredientQuantification.class, RecipeIngredientQuantification::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
