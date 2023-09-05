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
import java.lang.Boolean;
import java.lang.Double;
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
 * Mixed recipes: Ingredients description/quantification
 */
@Named("dita.globodiet.params.recipe_list.MixedRecipeIngredient")
@DomainObject
@DomainObjectLayout(
        describedAs = "Mixed recipes: Ingredients description/quantification"
)
@PersistenceCapable(
        table = "MIXEDING"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class MixedRecipeIngredient {
    /**
     * Recipe ID number the ingredient belongs to
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Recipe ID number the ingredient belongs to"
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String recipeIdNumberTheIngredientBelongsTo;

    /**
     * Ingr. Substitutable(=2) or Ingr. Fixed(=1) or fat during cooking(=3)<br>
     * or type of fat used (=A2) or type of milk/liquid used(=A3)
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Ingr. Substitutable(=2) or Ingr. Fixed(=1) or fat during cooking(=3)\n"
                            + "or type of fat used (=A2) or type of milk/liquid used(=A3)"
    )
    @Column(
            name = "ING_TYPE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String ingredientSubstitutable;

    /**
     * Food type (GI or blank)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food type (GI or blank)"
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodType;

    /**
     * Description text (facet/descriptor text)
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Description text (facet/descriptor text)"
    )
    @Column(
            name = "TEXT",
            allowsNull = "true",
            length = 1000000000
    )
    @Getter
    @Setter
    private String descriptionText;

    /**
     * Ingredient name
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Ingredient name"
    )
    @Column(
            name = "NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String ingredientName;

    /**
     * Ingredient food or recipe group
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Ingredient food or recipe group"
    )
    @Column(
            name = "GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String ingredientFoodOrRecipeGroup;

    /**
     * Ingredient food or recipe subgroup
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Ingredient food or recipe subgroup"
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String ingredientFoodOrRecipeSubgroup;

    /**
     * Ingredient food sub-subgroup
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Ingredient food sub-subgroup"
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String ingredientFoodSubSubgroup;

    /**
     * Ingredient brand name (if any)
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Ingredient brand name (if any)"
    )
    @Column(
            name = "BRANDNAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String ingredientBrandName;

    /**
     * Facets-Descriptors codes used to describe the ingredient; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)
     */
    @Property
    @PropertyLayout(
            sequence = "10",
            describedAs = "Facets-Descriptors codes used to describe the ingredient; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)"
    )
    @Column(
            name = "FACETS_STR",
            allowsNull = "true",
            length = 1000000000
    )
    @Getter
    @Setter
    private String facetDescriptorsCodesToDescribeTheIngredient;

    /**
     * =1 if ingredient described and quantified else =2
     */
    @Property
    @PropertyLayout(
            sequence = "11",
            describedAs = "=1 if ingredient described and quantified else =2"
    )
    @Column(
            name = "STATUS",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String ingredientDescribedAndQuantifiedQ;

    /**
     * Final quantity in g (with coefficient applied)
     */
    @Property
    @PropertyLayout(
            sequence = "12",
            describedAs = "Final quantity in g (with coefficient applied)"
    )
    @Column(
            name = "CONS_QTY",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double finalQuantityInG;

    /**
     * Estimated quantity (before coefficient applied)
     */
    @Property
    @PropertyLayout(
            sequence = "13",
            describedAs = "Estimated quantity (before coefficient applied)"
    )
    @Column(
            name = "ESTIM_QTY",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double estimatedQuantityBeforeCoefficientApplied;

    /**
     * Quantity Estimated Raw or Cooked<br>
     * 1 = Raw<br>
     * 2 = Cooked or Not applicable
     */
    @Property
    @PropertyLayout(
            sequence = "14",
            describedAs = "Quantity Estimated Raw or Cooked\n"
                            + "1 = Raw\n"
                            + "2 = Cooked or Not applicable"
    )
    @Column(
            name = "RAWCOOKED",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String quantityEstimatedRawOrCooked;

    /**
     * Quantity Consumed Raw or Cooked<br>
     * 1 = Raw<br>
     * 2 = Cooked or Not applicable
     */
    @Property
    @PropertyLayout(
            sequence = "15",
            describedAs = "Quantity Consumed Raw or Cooked\n"
                            + "1 = Raw\n"
                            + "2 = Cooked or Not applicable"
    )
    @Column(
            name = "CONSRAWCO",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String quantityConsumedRawOrCooked;

    /**
     * Conversion factor raw->cooked
     */
    @Property
    @PropertyLayout(
            sequence = "16",
            describedAs = "Conversion factor raw->cooked"
    )
    @Column(
            name = "CONVER",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double conversionFactorRawToCooked;

    /**
     * Quantity as estimated: 1=without un-edible part & 2=with un-edible part
     */
    @Property
    @PropertyLayout(
            sequence = "17",
            describedAs = "Quantity as estimated: 1=without un-edible part & 2=with un-edible part"
    )
    @Column(
            name = "EDIB",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String withUnediblePartQ;

    /**
     * Conversion factor for edible part
     */
    @Property
    @PropertyLayout(
            sequence = "18",
            describedAs = "Conversion factor for edible part"
    )
    @Column(
            name = "EDIB_CSTE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double conversionFactorForEdiblePart;

    /**
     * Quantity in gram/volume attached to the selected Photo, HHM, STDU
     */
    @Property
    @PropertyLayout(
            sequence = "19",
            describedAs = "Quantity in gram/volume attached to the selected Photo, HHM, STDU"
    )
    @Column(
            name = "NGRAMS",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double quantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU;

    /**
     * Proportion of Photo, HHM, STDU
     */
    @Property
    @PropertyLayout(
            sequence = "20",
            describedAs = "Proportion of Photo, HHM, STDU"
    )
    @Column(
            name = "PROPORT",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String proportionOfPhotoHHMSTDU;

    /**
     * Type of quantification method
     */
    @Property
    @PropertyLayout(
            sequence = "21",
            describedAs = "Type of quantification method"
    )
    @Column(
            name = "Q_METHOD",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String typeOfQuantificationMethod;

    /**
     * Quantification method code
     */
    @Property
    @PropertyLayout(
            sequence = "22",
            describedAs = "Quantification method code"
    )
    @Column(
            name = "QM_CODE",
            allowsNull = "true",
            length = 4
    )
    @Getter
    @Setter
    private String quantificationMethodCode;

    /**
     * Density Coefficient only for HHM
     */
    @Property
    @PropertyLayout(
            sequence = "23",
            describedAs = "Density Coefficient only for HHM"
    )
    @Column(
            name = "DENSITY",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double densityCoefficientOnlyForHHM;

    /**
     * Sequential Number for Ingredients within a Mixed Recipe
     */
    @Property
    @PropertyLayout(
            sequence = "24",
            describedAs = "Sequential Number for Ingredients within a Mixed Recipe"
    )
    @Column(
            name = "ING_NUM",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double sequentialNumberForIngredientsWithinAMixedRecipe;

    /**
     * Fat Left-Over Percentage
     */
    @Property
    @PropertyLayout(
            sequence = "25",
            describedAs = "Fat Left-Over Percentage"
    )
    @Column(
            name = "FATL_PCT",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double fatLeftOverPercentage;

    /**
     * Fat Left-Over Code (F=False, T=True)
     */
    @Property
    @PropertyLayout(
            sequence = "26",
            describedAs = "Fat Left-Over Code (F=False, T=True)"
    )
    @Column(
            name = "FATLEFTO",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Boolean fatLeftOverQ;

    /**
     * HHM Fraction
     */
    @Property
    @PropertyLayout(
            sequence = "27",
            describedAs = "HHM Fraction"
    )
    @Column(
            name = "HHMFRACT",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String hhmFraction;

    /**
     * Consumed quantity in pound
     */
    @Property
    @PropertyLayout(
            sequence = "28",
            describedAs = "Consumed quantity in pound"
    )
    @Column(
            name = "POUND",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double consumedQuantityInPound;

    /**
     * Consumed quantity in ounce
     */
    @Property
    @PropertyLayout(
            sequence = "29",
            describedAs = "Consumed quantity in ounce"
    )
    @Column(
            name = "OUNCE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double consumedQuantityInOunce;

    /**
     * Consumed quantity in quart
     */
    @Property
    @PropertyLayout(
            sequence = "30",
            describedAs = "Consumed quantity in quart"
    )
    @Column(
            name = "QUART",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double consumedQuantityInQuart;

    /**
     * Consumed quantity in pint
     */
    @Property
    @PropertyLayout(
            sequence = "31",
            describedAs = "Consumed quantity in pint"
    )
    @Column(
            name = "PINT",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double consumedQuantityInPint;

    /**
     * Consumed quantity in flounce
     */
    @Property
    @PropertyLayout(
            sequence = "32",
            describedAs = "Consumed quantity in flounce"
    )
    @Column(
            name = "FLOUNCE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double consumedQuantityInFlounce;

    /**
     * Sequential Number for Ingredients within a Sub-Recipe
     */
    @Property
    @PropertyLayout(
            sequence = "33",
            describedAs = "Sequential Number for Ingredients within a Sub-Recipe"
    )
    @Column(
            name = "S_ING_NUM",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double sequentialNumberForIngredientsWithinASubRecipe;

    /**
     * Raw quantity without inedible (sans dechet)
     */
    @Property
    @PropertyLayout(
            sequence = "34",
            describedAs = "Raw quantity without inedible (sans dechet)"
    )
    @Column(
            name = "RAW_Q",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double rawQuantityWithoutInedible;

    /**
     * Percentage/Proportion as Estimated for Recipe Ingredients
     */
    @Property
    @PropertyLayout(
            sequence = "35",
            describedAs = "Percentage/Proportion as Estimated for Recipe Ingredients"
    )
    @Column(
            name = "PCT_ESTIM",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double percentageOrProportionAsEstimatedForRecipeIngredients;

    /**
     * Percentage/Proportion as Consumed for Recipe Ingredients
     */
    @Property
    @PropertyLayout(
            sequence = "36",
            describedAs = "Percentage/Proportion as Consumed for Recipe Ingredients"
    )
    @Column(
            name = "PCT_CONS",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double percentageOrProportionAsConsumedForRecipeIngredients;

    /**
     * Type of item: 1 for food, 2 for recipe
     */
    @Property
    @PropertyLayout(
            sequence = "37",
            describedAs = "Type of item: 1 for food, 2 for recipe"
    )
    @Column(
            name = "TYPE_IT",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer typeOfItem;

    /**
     * Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum
     */
    @Property
    @PropertyLayout(
            sequence = "38",
            describedAs = "Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum"
    )
    @Column(
            name = "IDNUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String ingredientFoodOrRecipeIdNumber;

    /**
     * Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)
     */
    @Property
    @PropertyLayout(
            sequence = "39",
            describedAs = "Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)"
    )
    @Column(
            name = "Q_UNIT",
            allowsNull = "true",
            length = 1
    )
    @Getter
    @Setter
    private String unitOfSelectedQuantityForMethod;

    /**
     * has no description
     */
    @Property
    @PropertyLayout(
            sequence = "40",
            describedAs = "has no description"
    )
    @Column(
            name = "PCT_RAW",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double percentageRaw;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
