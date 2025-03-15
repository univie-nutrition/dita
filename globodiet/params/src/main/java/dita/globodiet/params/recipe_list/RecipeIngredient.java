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
package dita.globodiet.params.recipe_list;

import io.github.causewaystuff.companion.applib.jpa.EnumConverter;
import io.github.causewaystuff.companion.applib.jpa.EnumWithCode;
import io.github.causewaystuff.companion.applib.jpa.Persistable;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
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

/**
 * Mixed recipes: Ingredients description/quantification
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.recipe_list.RecipeIngredient")
@DomainObject
@DomainObjectLayout(
        describedAs = "Mixed recipes: Ingredients description/quantification"
)
@Entity
@Table(
        name = "MIXEDING"
)
public class RecipeIngredient implements Persistable, Cloneable<RecipeIngredient> {
    @Inject
    @Transient
    RepositoryService repositoryService;

    @Inject
    @Transient
    SearchService searchService;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    /**
     * Recipe ID number the ingredient belongs to
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Recipe ID number the ingredient belongs to",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"R_IDNUM\"",
            nullable = false,
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
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "1 = ingredient fixed\n"
                    + "2 = ingredient substitutable\n"
                    + "3 = fat during cooking\n"
                    + "A2 = type of fat used\n"
                    + "A3 = type of milk/liquid used"
    )
    @Column(
            name = "\"ING_TYPE\"",
            nullable = false,
            length = 2
    )
    @Getter
    @Setter
    @Convert(
            converter = Substitutable.Converter.class
    )
    private Substitutable substitutable;

    /**
     * Food type (GI or blank)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Food type (GI or blank)"
    )
    @Column(
            name = "\"TYPE\"",
            nullable = true,
            length = 2
    )
    @Getter
    @Setter
    private String foodType;

    /**
     * Description text (facet/descriptor text)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Description text (facet/descriptor text)"
    )
    @Column(
            name = "\"TEXT\"",
            nullable = true,
            length = 4096
    )
    @Getter
    @Setter
    private String descriptionText;

    /**
     * Ingredient name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Ingredient name"
    )
    @Column(
            name = "\"NAME\"",
            nullable = false,
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Ingredient food or recipe group
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "6",
            describedAs = "Ingredient food or recipe group",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"GROUP\"",
            nullable = false,
            length = 2
    )
    @Getter
    @Setter
    private String foodOrRecipeGroupCode;

    /**
     * Ingredient food or recipe subgroup
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "7",
            describedAs = "Ingredient food or recipe subgroup",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"SUBGROUP1\"",
            nullable = false,
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
            fieldSetId = "foreign",
            sequence = "8",
            describedAs = "Ingredient food sub-subgroup",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"SUBGROUP2\"",
            nullable = true,
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroupCode;

    /**
     * Ingredient brand (if any)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "9",
            describedAs = "Ingredient brand (if any)"
    )
    @Column(
            name = "\"BRANDNAME\"",
            nullable = true,
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
            fieldSetId = "foreign",
            sequence = "10",
            describedAs = "Facets-Descriptors codes used to describe the ingredient;\n"
                    + "multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"FACETS_STR\"",
            nullable = true,
            length = 4096
    )
    @Getter
    @Setter
    private String facetDescriptorsLookupKey;

    /**
     * 1 = ingredient described and quantified
     * 2 = otherwise
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "11",
            describedAs = "1 = ingredient described and quantified\n"
                    + "2 = otherwise"
    )
    @Column(
            name = "\"STATUS\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = DescribedAndQuantifiedQ.Converter.class
    )
    private DescribedAndQuantifiedQ describedAndQuantifiedQ;

    /**
     * Final quantity in g (with coefficient applied)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "12",
            describedAs = "Final quantity in g (with coefficient applied)"
    )
    @Column(
            name = "\"CONS_QTY\"",
            nullable = false
    )
    @Getter
    @Setter
    private double finalQuantityInG;

    /**
     * Estimated quantity (before coefficient applied)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "13",
            describedAs = "Estimated quantity (before coefficient applied)"
    )
    @Column(
            name = "\"ESTIM_QTY\"",
            nullable = false
    )
    @Getter
    @Setter
    private double estimatedQuantityBeforeCoefficientApplied;

    /**
     * Quantity Estimated Raw or Cooked
     * 1 = Raw
     * 2 = Cooked or Not applicable
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "14",
            describedAs = "Quantity Estimated Raw or Cooked\n"
                    + "1 = Raw\n"
                    + "2 = Cooked or Not applicable"
    )
    @Column(
            name = "\"RAWCOOKED\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = QuantityEstimatedRawOrCooked.Converter.class
    )
    private QuantityEstimatedRawOrCooked quantityEstimatedRawOrCooked;

    /**
     * Quantity Consumed Raw or Cooked
     * 1 = Raw
     * 2 = Cooked or Not applicable
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "15",
            describedAs = "Quantity Consumed Raw or Cooked\n"
                    + "1 = Raw\n"
                    + "2 = Cooked or Not applicable"
    )
    @Column(
            name = "\"CONSRAWCO\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = QuantityConsumedRawOrCooked.Converter.class
    )
    private QuantityConsumedRawOrCooked quantityConsumedRawOrCooked;

    /**
     * Conversion factor raw->cooked
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "16",
            describedAs = "Conversion factor raw->cooked"
    )
    @Column(
            name = "\"CONVER\"",
            nullable = false
    )
    @Getter
    @Setter
    private double conversionFactorRawToCooked;

    /**
     * Quantity as estimated: 1=without un-edible part & 2=with un-edible part
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "17",
            describedAs = "Quantity as estimated: 1=without un-edible part & 2=with un-edible part"
    )
    @Column(
            name = "\"EDIB\"",
            nullable = false,
            length = 1
    )
    @Getter
    @Setter
    @Convert(
            converter = WithUnediblePartQ.Converter.class
    )
    private WithUnediblePartQ withUnediblePartQ;

    /**
     * Conversion factor for edible part
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "18",
            describedAs = "Conversion factor for edible part"
    )
    @Column(
            name = "\"EDIB_CSTE\"",
            nullable = false
    )
    @Getter
    @Setter
    private double conversionFactorForEdiblePart;

    /**
     * Quantity in gram/volume attached to the selected Photo, HHM, STDU
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "19",
            describedAs = "Quantity in gram/volume attached to the selected Photo, HHM, STDU"
    )
    @Column(
            name = "\"NGRAMS\"",
            nullable = false
    )
    @Getter
    @Setter
    private double quantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU;

    /**
     * Proportion of Photo, HHM, STDU
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "20",
            describedAs = "Proportion of Photo, HHM, STDU"
    )
    @Column(
            name = "\"PROPORT\"",
            nullable = false,
            length = 5
    )
    @Getter
    @Setter
    private String proportionOfPhotoHHMSTDU;

    /**
     * Type of quantification method
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "21",
            describedAs = "Type of quantification method"
    )
    @Column(
            name = "\"Q_METHOD\"",
            nullable = true,
            length = 1
    )
    @Getter
    @Setter
    private String typeOfQuantificationMethod;

    /**
     * Quantification method code
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "22",
            describedAs = "Quantification method code"
    )
    @Column(
            name = "\"QM_CODE\"",
            nullable = true,
            length = 4
    )
    @Getter
    @Setter
    private String quantificationMethodCode;

    /**
     * Density Coefficient only for HHM
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "23",
            describedAs = "Density Coefficient only for HHM"
    )
    @Column(
            name = "\"DENSITY\"",
            nullable = false
    )
    @Getter
    @Setter
    private double densityCoefficientOnlyForHHM;

    /**
     * Sequential Number for Ingredients within a Mixed Recipe
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "24",
            describedAs = "Sequential Number for Ingredients within a Mixed Recipe"
    )
    @Column(
            name = "\"ING_NUM\"",
            nullable = false
    )
    @Getter
    @Setter
    private double sequentialNumberForIngredientsWithinARecipe;

    /**
     * Fat Left-Over Percentage
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "25",
            describedAs = "Fat Left-Over Percentage"
    )
    @Column(
            name = "\"FATL_PCT\"",
            nullable = false
    )
    @Getter
    @Setter
    private double fatLeftOverPercentage;

    /**
     * Fat Left-Over Code (F=False, T=True)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "26",
            describedAs = "Fat Left-Over Code (F=False, T=True)"
    )
    @Column(
            name = "\"FATLEFTO\"",
            nullable = false
    )
    @Getter
    @Setter
    private boolean fatLeftOverQ;

    /**
     * HHM Fraction
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "27",
            describedAs = "HHM Fraction"
    )
    @Column(
            name = "\"HHMFRACT\"",
            nullable = true,
            length = 5
    )
    @Getter
    @Setter
    private String hhmFraction;

    /**
     * Consumed quantity in pound
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "28",
            describedAs = "Consumed quantity in pound"
    )
    @Column(
            name = "\"POUND\"",
            nullable = false
    )
    @Getter
    @Setter
    private double consumedQuantityInPound;

    /**
     * Consumed quantity in ounce
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "29",
            describedAs = "Consumed quantity in ounce"
    )
    @Column(
            name = "\"OUNCE\"",
            nullable = false
    )
    @Getter
    @Setter
    private double consumedQuantityInOunce;

    /**
     * Consumed quantity in quart
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "30",
            describedAs = "Consumed quantity in quart"
    )
    @Column(
            name = "\"QUART\"",
            nullable = false
    )
    @Getter
    @Setter
    private double consumedQuantityInQuart;

    /**
     * Consumed quantity in pint
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "31",
            describedAs = "Consumed quantity in pint"
    )
    @Column(
            name = "\"PINT\"",
            nullable = false
    )
    @Getter
    @Setter
    private double consumedQuantityInPint;

    /**
     * Consumed quantity in flounce
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "32",
            describedAs = "Consumed quantity in flounce"
    )
    @Column(
            name = "\"FLOUNCE\"",
            nullable = false
    )
    @Getter
    @Setter
    private double consumedQuantityInFlounce;

    /**
     * Sequential Number for Ingredients within a Sub-Recipe
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "33",
            describedAs = "Sequential Number for Ingredients within a Sub-Recipe"
    )
    @Column(
            name = "\"S_ING_NUM\"",
            nullable = false
    )
    @Getter
    @Setter
    private double sequentialNumberForIngredientsWithinASubRecipe;

    /**
     * Raw quantity without inedible (sans dechet)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "34",
            describedAs = "Raw quantity without inedible (sans dechet)"
    )
    @Column(
            name = "\"RAW_Q\"",
            nullable = false
    )
    @Getter
    @Setter
    private double rawQuantityWithoutInedible;

    /**
     * Percentage/Proportion as Estimated for Recipe Ingredients
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "35",
            describedAs = "Percentage/Proportion as Estimated for Recipe Ingredients"
    )
    @Column(
            name = "\"PCT_ESTIM\"",
            nullable = true
    )
    @Getter
    @Setter
    private Double percentageOrProportionAsEstimatedForRecipeIngredients;

    /**
     * Percentage/Proportion as Consumed for Recipe Ingredients
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "36",
            describedAs = "Percentage/Proportion as Consumed for Recipe Ingredients"
    )
    @Column(
            name = "\"PCT_CONS\"",
            nullable = true
    )
    @Getter
    @Setter
    private Double percentageOrProportionAsConsumedForRecipeIngredients;

    /**
     * 1 = food
     * 2 = recipe
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "37",
            describedAs = "1 = food\n"
                    + "2 = recipe"
    )
    @Column(
            name = "\"TYPE_IT\"",
            nullable = false
    )
    @Getter
    @Setter
    @Convert(
            converter = TypeOfItem.Converter.class
    )
    private TypeOfItem typeOfItem;

    /**
     * Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "38",
            describedAs = "Ingredient Food or Recipe ID number; either Foods.foodnum OR Mixedrec.r_idnum",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "\"IDNUM\"",
            nullable = false,
            length = 5
    )
    @Getter
    @Setter
    private String foodOrRecipeCode;

    /**
     * Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "39",
            describedAs = "Unit of selected quantity for method Photo, std U, std P (G=gram, V=volum)"
    )
    @Column(
            name = "\"Q_UNIT\"",
            nullable = true,
            length = 1
    )
    @Getter
    @Setter
    private String unitOfSelectedQuantityForMethod;

    /**
     * has no description
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "40",
            describedAs = "has no description"
    )
    @Column(
            name = "\"PCT_RAW\"",
            nullable = true
    )
    @Getter
    @Setter
    private Double percentageRaw;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "RecipeIngredient(" + "recipeCode=" + getRecipeCode() + ","
         +"substitutable=" + getSubstitutable() + ","
         +"foodType=" + getFoodType() + ","
         +"descriptionText=" + getDescriptionText() + ","
         +"name=" + getName() + ","
         +"foodOrRecipeGroupCode=" + getFoodOrRecipeGroupCode() + ","
         +"foodOrRecipeSubgroupCode=" + getFoodOrRecipeSubgroupCode() + ","
         +"foodSubSubgroupCode=" + getFoodSubSubgroupCode() + ","
         +"brandName=" + getBrandName() + ","
         +"facetDescriptorsLookupKey=" + getFacetDescriptorsLookupKey() + ","
         +"describedAndQuantifiedQ=" + getDescribedAndQuantifiedQ() + ","
         +"finalQuantityInG=" + getFinalQuantityInG() + ","
         +"estimatedQuantityBeforeCoefficientApplied=" + getEstimatedQuantityBeforeCoefficientApplied() + ","
         +"quantityEstimatedRawOrCooked=" + getQuantityEstimatedRawOrCooked() + ","
         +"quantityConsumedRawOrCooked=" + getQuantityConsumedRawOrCooked() + ","
         +"conversionFactorRawToCooked=" + getConversionFactorRawToCooked() + ","
         +"withUnediblePartQ=" + getWithUnediblePartQ() + ","
         +"conversionFactorForEdiblePart=" + getConversionFactorForEdiblePart() + ","
         +"quantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU=" + getQuantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU() + ","
         +"proportionOfPhotoHHMSTDU=" + getProportionOfPhotoHHMSTDU() + ","
         +"typeOfQuantificationMethod=" + getTypeOfQuantificationMethod() + ","
         +"quantificationMethodCode=" + getQuantificationMethodCode() + ","
         +"densityCoefficientOnlyForHHM=" + getDensityCoefficientOnlyForHHM() + ","
         +"sequentialNumberForIngredientsWithinARecipe=" + getSequentialNumberForIngredientsWithinARecipe() + ","
         +"fatLeftOverPercentage=" + getFatLeftOverPercentage() + ","
         +"fatLeftOverQ=" + isFatLeftOverQ() + ","
         +"hhmFraction=" + getHhmFraction() + ","
         +"consumedQuantityInPound=" + getConsumedQuantityInPound() + ","
         +"consumedQuantityInOunce=" + getConsumedQuantityInOunce() + ","
         +"consumedQuantityInQuart=" + getConsumedQuantityInQuart() + ","
         +"consumedQuantityInPint=" + getConsumedQuantityInPint() + ","
         +"consumedQuantityInFlounce=" + getConsumedQuantityInFlounce() + ","
         +"sequentialNumberForIngredientsWithinASubRecipe=" + getSequentialNumberForIngredientsWithinASubRecipe() + ","
         +"rawQuantityWithoutInedible=" + getRawQuantityWithoutInedible() + ","
         +"percentageOrProportionAsEstimatedForRecipeIngredients=" + getPercentageOrProportionAsEstimatedForRecipeIngredients() + ","
         +"percentageOrProportionAsConsumedForRecipeIngredients=" + getPercentageOrProportionAsConsumedForRecipeIngredients() + ","
         +"typeOfItem=" + getTypeOfItem() + ","
         +"foodOrRecipeCode=" + getFoodOrRecipeCode() + ","
         +"unitOfSelectedQuantityForMethod=" + getUnitOfSelectedQuantityForMethod() + ","
         +"percentageRaw=" + getPercentageRaw() + ")";
    }

    @Programmatic
    @Override
    public RecipeIngredient copy() {
        var copy = repositoryService.detachedEntity(new RecipeIngredient());
        copy.setRecipeCode(getRecipeCode());
        copy.setSubstitutable(getSubstitutable());
        copy.setFoodType(getFoodType());
        copy.setDescriptionText(getDescriptionText());
        copy.setName(getName());
        copy.setFoodOrRecipeGroupCode(getFoodOrRecipeGroupCode());
        copy.setFoodOrRecipeSubgroupCode(getFoodOrRecipeSubgroupCode());
        copy.setFoodSubSubgroupCode(getFoodSubSubgroupCode());
        copy.setBrandName(getBrandName());
        copy.setFacetDescriptorsLookupKey(getFacetDescriptorsLookupKey());
        copy.setDescribedAndQuantifiedQ(getDescribedAndQuantifiedQ());
        copy.setFinalQuantityInG(getFinalQuantityInG());
        copy.setEstimatedQuantityBeforeCoefficientApplied(getEstimatedQuantityBeforeCoefficientApplied());
        copy.setQuantityEstimatedRawOrCooked(getQuantityEstimatedRawOrCooked());
        copy.setQuantityConsumedRawOrCooked(getQuantityConsumedRawOrCooked());
        copy.setConversionFactorRawToCooked(getConversionFactorRawToCooked());
        copy.setWithUnediblePartQ(getWithUnediblePartQ());
        copy.setConversionFactorForEdiblePart(getConversionFactorForEdiblePart());
        copy.setQuantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU(getQuantityInGramPerVolumeAttachedToTheSelectedPhotoOrHHMOrSTDU());
        copy.setProportionOfPhotoHHMSTDU(getProportionOfPhotoHHMSTDU());
        copy.setTypeOfQuantificationMethod(getTypeOfQuantificationMethod());
        copy.setQuantificationMethodCode(getQuantificationMethodCode());
        copy.setDensityCoefficientOnlyForHHM(getDensityCoefficientOnlyForHHM());
        copy.setSequentialNumberForIngredientsWithinARecipe(getSequentialNumberForIngredientsWithinARecipe());
        copy.setFatLeftOverPercentage(getFatLeftOverPercentage());
        copy.setFatLeftOverQ(isFatLeftOverQ());
        copy.setHhmFraction(getHhmFraction());
        copy.setConsumedQuantityInPound(getConsumedQuantityInPound());
        copy.setConsumedQuantityInOunce(getConsumedQuantityInOunce());
        copy.setConsumedQuantityInQuart(getConsumedQuantityInQuart());
        copy.setConsumedQuantityInPint(getConsumedQuantityInPint());
        copy.setConsumedQuantityInFlounce(getConsumedQuantityInFlounce());
        copy.setSequentialNumberForIngredientsWithinASubRecipe(getSequentialNumberForIngredientsWithinASubRecipe());
        copy.setRawQuantityWithoutInedible(getRawQuantityWithoutInedible());
        copy.setPercentageOrProportionAsEstimatedForRecipeIngredients(getPercentageOrProportionAsEstimatedForRecipeIngredients());
        copy.setPercentageOrProportionAsConsumedForRecipeIngredients(getPercentageOrProportionAsConsumedForRecipeIngredients());
        copy.setTypeOfItem(getTypeOfItem());
        copy.setFoodOrRecipeCode(getFoodOrRecipeCode());
        copy.setUnitOfSelectedQuantityForMethod(getUnitOfSelectedQuantityForMethod());
        copy.setPercentageRaw(getPercentageRaw());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @Transient
    public RecipeIngredient.Manager getNavigableParent() {
        return new RecipeIngredient.Manager(searchService, "");
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum Substitutable implements EnumWithCode<String> {

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

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<Substitutable, String> {
            @Override
            public Substitutable[] values() {
                return Substitutable.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum DescribedAndQuantifiedQ implements EnumWithCode<String> {

        /**
         * no description
         */
        INGREDIENT_DESCRIBED_AND_QUANTIFIED("1", "ingredient described and quantified"),
        /**
         * no description
         */
        OTHERWISE("2", "otherwise");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<DescribedAndQuantifiedQ, String> {
            @Override
            public DescribedAndQuantifiedQ[] values() {
                return DescribedAndQuantifiedQ.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum QuantityEstimatedRawOrCooked implements EnumWithCode<String> {

        /**
         * no description
         */
        RAW("1", "Raw"),
        /**
         * no description
         */
        COOKED_OR_NOT_APPLICABLE("2", "Cooked or Not applicable");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<QuantityEstimatedRawOrCooked, String> {
            @Override
            public QuantityEstimatedRawOrCooked[] values() {
                return QuantityEstimatedRawOrCooked.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum QuantityConsumedRawOrCooked implements EnumWithCode<String> {

        /**
         * no description
         */
        RAW("1", "Raw"),
        /**
         * no description
         */
        COOKED_OR_NOT_APPLICABLE("2", "Cooked or Not applicable");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<QuantityConsumedRawOrCooked, String> {
            @Override
            public QuantityConsumedRawOrCooked[] values() {
                return QuantityConsumedRawOrCooked.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum WithUnediblePartQ implements EnumWithCode<String> {

        /**
         * no description
         */
        UN_EDIBLE_EXCLUDED("1", "un-edible excluded"),
        /**
         * no description
         */
        UN_EDIBLE_INCLUDED("2", "un-edible included");

        private final String code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<WithUnediblePartQ, String> {
            @Override
            public WithUnediblePartQ[] values() {
                return WithUnediblePartQ.values();
            }
        }
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum TypeOfItem implements EnumWithCode<Integer> {

        /**
         * no description
         */
        FOOD(1, "Food"),
        /**
         * no description
         */
        RECIPE(2, "Recipe");

        private final Integer code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<TypeOfItem, Integer> {
            @Override
            public TypeOfItem[] values() {
                return TypeOfItem.values();
            }
        }
    }

    /**
     * Manager Viewmodel for @{link RecipeIngredient}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.recipe_list.RecipeIngredient.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Mixed recipes: Ingredients description/quantification"
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
            return "Manage Recipe Ingredient";
        }

        @Collection
        public final List<RecipeIngredient> getListOfRecipeIngredient() {
            return searchService.search(RecipeIngredient.class, RecipeIngredient::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
