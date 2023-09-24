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
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Mixed recipes: Ingredients description/quantification
 */
@Named("dita.globodiet.params.recipe_list.RecipeIngredient")
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
public class RecipeIngredient {
    /**
     * Recipe ID number the ingredient belongs to
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Recipe ID number the ingredient belongs to<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "R_IDNUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    /**
     * 1 = ingredient fixed
     * 2 = ingredient substitutable
     * 3 = fat during cooking
     * A2 = type of fat used
     * A3 = type of milk/liquid used
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "1 = ingredient fixed<br>2 = ingredient substitutable<br>3 = fat during cooking<br>A2 = type of fat used<br>A3 = type of milk/liquid used<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ING_TYPE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private Substitutable substitutable;

    /**
     * Food type (GI or blank)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food type (GI or blank)<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "4",
            describedAs = "Description text (facet/descriptor text)<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
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
            describedAs = "Ingredient name<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Ingredient food or recipe group
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Ingredient food or recipe group<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "GROUP",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String foodOrRecipeGroupCode;

    /**
     * Ingredient food or recipe subgroup
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Ingredient food or recipe subgroup<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String foodOrRecipeSubgroupCode;

    /**
     * Ingredient food sub-subgroup
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "8",
            describedAs = "Ingredient food sub-subgroup<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
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
     * Ingredient brand (if any)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "9",
            describedAs = "Ingredient brand (if any)<br>----<br>required=false, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "BRANDNAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String brandName;

    /**
     * Facets-Descriptors codes used to describe the ingredient;
     * multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "10",
            describedAs = "Facets-Descriptors codes used to describe the ingredient;<br>multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)<br>----<br>required=false, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACETS_STR",
            allowsNull = "true",
            length = 1000000000
    )
    @Getter
    @Setter
    private String facetDescriptorsLookupKey;

    /**
     * 1 = ingredient described and quantified
     * 2 = otherwise
     */
    @Property
    @PropertyLayout(
            sequence = "11",
            describedAs = "1 = ingredient described and quantified<br>2 = otherwise<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "STATUS",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private DescribedAndQuantifiedQ describedAndQuantifiedQ;

    /**
     * Final quantity in g (with coefficient applied)
     */
    @Property
    @PropertyLayout(
            sequence = "12",
            describedAs = "Final quantity in g (with coefficient applied)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CONS_QTY",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double finalQuantityInG;

    /**
     * Estimated quantity (before coefficient applied)
     */
    @Property
    @PropertyLayout(
            sequence = "13",
            describedAs = "Estimated quantity (before coefficient applied)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ESTIM_QTY",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double estimatedQuantityBeforeCoefficientApplied;

    /**
     * Quantity Estimated Raw or Cooked
     * 1 = Raw
     * 2 = Cooked or Not applicable
     */
    @Property
    @PropertyLayout(
            sequence = "14",
            describedAs = "Quantity Estimated Raw or Cooked<br>1 = Raw<br>2 = Cooked or Not applicable<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RAWCOOKED",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private QuantityEstimatedRawOrCooked quantityEstimatedRawOrCooked;

    /**
     * Quantity Consumed Raw or Cooked
     * 1 = Raw
     * 2 = Cooked or Not applicable
     */
    @Property
    @PropertyLayout(
            sequence = "15",
            describedAs = "Quantity Consumed Raw or Cooked<br>1 = Raw<br>2 = Cooked or Not applicable<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CONSRAWCO",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private QuantityConsumedRawOrCooked quantityConsumedRawOrCooked;

    /**
     * Conversion factor raw->cooked
     */
    @Property
    @PropertyLayout(
            sequence = "16",
            describedAs = "Conversion factor raw->cooked<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "CONVER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double conversionFactorRawToCooked;

    /**
     * Quantity as estimated: 1=without un-edible part & 2=with un-edible part
     */
    @Property
    @PropertyLayout(
            sequence = "17",
            describedAs = "Quantity as estimated: 1=without un-edible part & 2=with un-edible part<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "EDIB",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private WithUnediblePartQ withUnediblePartQ;

    /**
     * Conversion factor for edible part
     */
    @Property
    @PropertyLayout(
            sequence = "18",
            describedAs = "Conversion factor for edible part<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "EDIB_CSTE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double conversionFactorForEdiblePart;

    /**
     * Quantity in gram/volume attached to the selected Photo, HHM, STDU
     */
    @Property
    @PropertyLayout(
            sequence = "19",
            describedAs = "Quantity in gram/volume attached to the selected Photo, HHM, STDU<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NGRAMS",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double quantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU;

    /**
     * Proportion of Photo, HHM, STDU
     */
    @Property
    @PropertyLayout(
            sequence = "20",
            describedAs = "Proportion of Photo, HHM, STDU<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PROPORT",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String proportionOfPhotoHHMSTDU;

    /**
     * Type of quantification method
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "21",
            describedAs = "Type of quantification method<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "22",
            describedAs = "Quantification method code<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
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
            describedAs = "Density Coefficient only for HHM<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DENSITY",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double densityCoefficientOnlyForHHM;

    /**
     * Sequential Number for Ingredients within a Mixed Recipe
     */
    @Property
    @PropertyLayout(
            sequence = "24",
            describedAs = "Sequential Number for Ingredients within a Mixed Recipe<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ING_NUM",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double sequentialNumberForIngredientsWithinARecipe;

    /**
     * Fat Left-Over Percentage
     */
    @Property
    @PropertyLayout(
            sequence = "25",
            describedAs = "Fat Left-Over Percentage<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FATL_PCT",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double fatLeftOverPercentage;

    /**
     * Fat Left-Over Code (F=False, T=True)
     */
    @Property
    @PropertyLayout(
            sequence = "26",
            describedAs = "Fat Left-Over Code (F=False, T=True)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FATLEFTO",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private boolean fatLeftOverQ;

    /**
     * HHM Fraction
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "27",
            describedAs = "HHM Fraction<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
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
            describedAs = "Consumed quantity in pound<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "POUND",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double consumedQuantityInPound;

    /**
     * Consumed quantity in ounce
     */
    @Property
    @PropertyLayout(
            sequence = "29",
            describedAs = "Consumed quantity in ounce<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "OUNCE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double consumedQuantityInOunce;

    /**
     * Consumed quantity in quart
     */
    @Property
    @PropertyLayout(
            sequence = "30",
            describedAs = "Consumed quantity in quart<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "QUART",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double consumedQuantityInQuart;

    /**
     * Consumed quantity in pint
     */
    @Property
    @PropertyLayout(
            sequence = "31",
            describedAs = "Consumed quantity in pint<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PINT",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double consumedQuantityInPint;

    /**
     * Consumed quantity in flounce
     */
    @Property
    @PropertyLayout(
            sequence = "32",
            describedAs = "Consumed quantity in flounce<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FLOUNCE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double consumedQuantityInFlounce;

    /**
     * Sequential Number for Ingredients within a Sub-Recipe
     */
    @Property
    @PropertyLayout(
            sequence = "33",
            describedAs = "Sequential Number for Ingredients within a Sub-Recipe<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "S_ING_NUM",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double sequentialNumberForIngredientsWithinASubRecipe;

    /**
     * Raw quantity without inedible (sans dechet)
     */
    @Property
    @PropertyLayout(
            sequence = "34",
            describedAs = "Raw quantity without inedible (sans dechet)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RAW_Q",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double rawQuantityWithoutInedible;

    /**
     * Percentage/Proportion as Estimated for Recipe Ingredients
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "35",
            describedAs = "Percentage/Proportion as Estimated for Recipe Ingredients<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "36",
            describedAs = "Percentage/Proportion as Consumed for Recipe Ingredients<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "PCT_CONS",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Double percentageOrProportionAsConsumedForRecipeIngredients;

    /**
     * 1 = food
     * 2 = recipe
     */
    @Property
    @PropertyLayout(
            sequence = "37",
            describedAs = "1 = food<br>2 = recipe<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "TYPE_IT",
            allowsNull = "false"
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private TypeOfItem typeOfItem;

    /**
     * Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum
     */
    @Property
    @PropertyLayout(
            sequence = "38",
            describedAs = "Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "IDNUM",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String foodOrRecipeCode;

    /**
     * Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "39",
            describedAs = "Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)<br>----<br>required=false, unique=false",
            hidden = Where.NOWHERE
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "40",
            describedAs = "has no description<br>----<br>required=false, unique=true",
            hidden = Where.NOWHERE
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

    @RequiredArgsConstructor
    public enum Substitutable {
        /**
         * no description
         */
        INGREDIENT_FIXED("1", "ingredient fixed"),

        /**
         * no description
         */
        INGREDIENT_SUBSTITUTABLE("2", "ingredient substitutable"),

        /**
         * no description
         */
        FAT_DURING_COOKING("3", "fat during cooking"),

        /**
         * no description
         */
        TYPE_OF_FAT_USED("A2", "type of fat used"),

        /**
         * no description
         */
        TYPE_OF_MILK_LIQUID_USED("A3", "type of milk/liquid used");

        @Getter
        private final String matchOn;

        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum DescribedAndQuantifiedQ {
        /**
         * no description
         */
        INGREDIENT_DESCRIBED_AND_QUANTIFIED("1", "ingredient described and quantified"),

        /**
         * no description
         */
        OTHERWISE("2", "otherwise");

        @Getter
        private final String matchOn;

        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum QuantityEstimatedRawOrCooked {
        /**
         * no description
         */
        RAW("1", "Raw"),

        /**
         * no description
         */
        COOKED_OR_NOT_APPLICABLE("2", "Cooked or Not applicable");

        @Getter
        private final String matchOn;

        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum QuantityConsumedRawOrCooked {
        /**
         * no description
         */
        RAW("1", "Raw"),

        /**
         * no description
         */
        COOKED_OR_NOT_APPLICABLE("2", "Cooked or Not applicable");

        @Getter
        private final String matchOn;

        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum WithUnediblePartQ {
        /**
         * no description
         */
        UN_EDIBLE_EXCLUDED("1", "un-edible excluded"),

        /**
         * no description
         */
        UN_EDIBLE_INCLUDED("2", "un-edible included");

        @Getter
        private final String matchOn;

        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum TypeOfItem {
        /**
         * no description
         */
        FOOD(1, "Food"),

        /**
         * no description
         */
        RECIPE(2, "Recipe");

        @Getter
        private final int matchOn;

        @Accessors(
                fluent = true
        )
        private final String title;
    }
}
