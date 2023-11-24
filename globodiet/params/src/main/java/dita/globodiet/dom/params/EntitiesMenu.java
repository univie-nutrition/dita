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
package dita.globodiet.dom.params;

import dita.commons.services.search.SearchService;
import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.classification.RecipeGroup;
import dita.globodiet.dom.params.classification.RecipeSubgroup;
import dita.globodiet.dom.params.food_coefficient.DensityFactorForFood;
import dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood;
import dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood;
import dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood;
import dita.globodiet.dom.params.food_descript.FacetDescriptorPathwayForFood;
import dita.globodiet.dom.params.food_descript.FacetDescriptorPathwayForFoodGroup;
import dita.globodiet.dom.params.food_descript.FoodBrand;
import dita.globodiet.dom.params.food_descript.FoodDescriptor;
import dita.globodiet.dom.params.food_descript.FoodFacet;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor;
import dita.globodiet.dom.params.food_descript.RuleAppliedToFacet;
import dita.globodiet.dom.params.food_list.ComposedRecipeIngredient;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrGroup;
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFood;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodPathwayForFood;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.dom.params.food_quantif.StandardPortionForFood;
import dita.globodiet.dom.params.food_table.ItemDefinition;
import dita.globodiet.dom.params.food_table.NutrientListAndDefinition;
import dita.globodiet.dom.params.food_table.NutrientValuesPerNutrientAndItem;
import dita.globodiet.dom.params.general_info.AnthropometricAverage;
import dita.globodiet.dom.params.general_info.FoodConsumptionOccasion;
import dita.globodiet.dom.params.general_info.FoodConsumptionOccasionDisplayItem;
import dita.globodiet.dom.params.general_info.PlaceOfConsumption;
import dita.globodiet.dom.params.general_info.SpecialDayPredefinedAnswer;
import dita.globodiet.dom.params.general_info.SpecialDietPredefinedAnswer;
import dita.globodiet.dom.params.general_info.TranslationInCountryLanguage;
import dita.globodiet.dom.params.interview.CenterInvolved;
import dita.globodiet.dom.params.interview.CountryInvolved;
import dita.globodiet.dom.params.interview.Interviewer;
import dita.globodiet.dom.params.interview.SubjectToBeInterviewed;
import dita.globodiet.dom.params.probing.ProbingQuestion;
import dita.globodiet.dom.params.quantif.HouseholdMeasure;
import dita.globodiet.dom.params.quantif.PhotoForQuantity;
import dita.globodiet.dom.params.quantif.Shape;
import dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe;
import dita.globodiet.dom.params.quantif.ThicknessForShapeMethod;
import dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe;
import dita.globodiet.dom.params.recipe_description.FacetDescriptorPathwayForRecipe;
import dita.globodiet.dom.params.recipe_description.FacetDescriptorPathwayForRecipeGroup;
import dita.globodiet.dom.params.recipe_description.RecipeBrand;
import dita.globodiet.dom.params.recipe_description.RecipeDescriptor;
import dita.globodiet.dom.params.recipe_description.RecipeFacet;
import dita.globodiet.dom.params.recipe_description.RuleAppliedToFacets;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification;
import dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrGroup;
import dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipe;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipeGroup;
import dita.globodiet.dom.params.setting.DayOfWeek;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted;
import dita.globodiet.dom.params.setting.GroupSubstitution;
import dita.globodiet.dom.params.setting.MacroNutrientLimit;
import dita.globodiet.dom.params.setting.Month;
import dita.globodiet.dom.params.setting.NoteStatus;
import dita.globodiet.dom.params.setting.RecipeTypePathway;
import dita.globodiet.dom.params.setting.SelectedParameter;
import dita.globodiet.dom.params.setting.SelectedParameterForDataEntry;
import dita.globodiet.dom.params.supplement.DietarySupplement;
import dita.globodiet.dom.params.supplement.DietarySupplementClassification;
import dita.globodiet.dom.params.supplement.DietarySupplementDescriptor;
import dita.globodiet.dom.params.supplement.DietarySupplementFacet;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.services.factory.FactoryService;

@Named("dita.globodiet.params.EntitiesMenu")
@DomainService(
        nature = org.apache.causeway.applib.annotation.NatureOfService.VIEW
)
public class EntitiesMenu {
    @Inject
    private FactoryService factoryService;

    @Inject
    private SearchService searchService;

    @Action
    @ActionLayout(
            cssClassFa = "solid utensils .food-color,\n"
                            + "solid layer-group .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public FoodGroup.Manager manageFoodGroup() {
        return factoryService.viewModel(new FoodGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid utensils .food-color,\n"
                            + "solid layer-group .food-color .ov-size-80 .ov-right-55 .ov-bottom-55,\n"
                            + "solid circle-chevron-down .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
    )
    public FoodSubgroup.Manager manageFoodSubgroup() {
        return factoryService.viewModel(new FoodSubgroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                            + "solid layer-group .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public RecipeGroup.Manager manageRecipeGroup() {
        return factoryService.viewModel(new RecipeGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                            + "solid layer-group .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55,\n"
                            + "solid circle-chevron-down .recipe-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
    )
    public RecipeSubgroup.Manager manageRecipeSubgroup() {
        return factoryService.viewModel(new RecipeSubgroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public DensityFactorForFood.Manager manageDensityFactorForFood() {
        return factoryService.viewModel(new DensityFactorForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public EdiblePartCoefficientForFood.Manager manageEdiblePartCoefficientForFood() {
        return factoryService.viewModel(new EdiblePartCoefficientForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public PercentOfFatLeftInTheDishForFood.Manager managePercentOfFatLeftInTheDishForFood() {
        return factoryService.viewModel(new PercentOfFatLeftInTheDishForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.Manager managePercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood(
            ) {
        return factoryService.viewModel(new PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public PercentOfFatUseDuringCookingForFood.Manager managePercentOfFatUseDuringCookingForFood() {
        return factoryService.viewModel(new PercentOfFatUseDuringCookingForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public RawToCookedConversionFactorForFood.Manager manageRawToCookedConversionFactorForFood() {
        return factoryService.viewModel(new RawToCookedConversionFactorForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                            + "solid tag .food-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                            + "solid exclamation-circle .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
    )
    public FacetDescriptorPathwayForFood.Manager manageFacetDescriptorPathwayForFood() {
        return factoryService.viewModel(new FacetDescriptorPathwayForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                            + "solid tag .food-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
    )
    public FacetDescriptorPathwayForFoodGroup.Manager manageFacetDescriptorPathwayForFoodGroup() {
        return factoryService.viewModel(new FacetDescriptorPathwayForFoodGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid utensils .food-color,\n"
                            + "brands shopify .brand-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public FoodBrand.Manager manageFoodBrand() {
        return factoryService.viewModel(new FoodBrand.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid utensils .food-color,\n"
                            + "solid tag .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public FoodDescriptor.Manager manageFoodDescriptor() {
        return factoryService.viewModel(new FoodDescriptor.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid utensils .food-color,\n"
                            + "solid swatchbook .food-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public FoodFacet.Manager manageFoodFacet() {
        return factoryService.viewModel(new FoodFacet.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public ImprobableSequenceOfFacetAndDescriptor.Manager manageImprobableSequenceOfFacetAndDescriptor(
            ) {
        return factoryService.viewModel(new ImprobableSequenceOfFacetAndDescriptor.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public RuleAppliedToFacet.Manager manageRuleAppliedToFacet() {
        return factoryService.viewModel(new RuleAppliedToFacet.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public ComposedRecipeIngredient.Manager manageComposedRecipeIngredient() {
        return factoryService.viewModel(new ComposedRecipeIngredient.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid utensils .food-color"
    )
    public Food.Manager manageFood() {
        return factoryService.viewModel(new Food.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public MaximumValueForAFoodOrGroup.Manager manageMaximumValueForAFoodOrGroup() {
        return factoryService.viewModel(new MaximumValueForAFoodOrGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                            + "solid question .food-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
    )
    public ProbingQuestionPathwayForFood.Manager manageProbingQuestionPathwayForFood() {
        return factoryService.viewModel(new ProbingQuestionPathwayForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                            + "solid scale-balanced .food-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                            + "solid exclamation-circle .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
    )
    public QuantificationMethodPathwayForFood.Manager manageQuantificationMethodPathwayForFood() {
        return factoryService.viewModel(new QuantificationMethodPathwayForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .food-color,\n"
                            + "solid scale-balanced .food-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
    )
    public QuantificationMethodPathwayForFoodGroup.Manager manageQuantificationMethodPathwayForFoodGroup(
            ) {
        return factoryService.viewModel(new QuantificationMethodPathwayForFoodGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public StandardPortionForFood.Manager manageStandardPortionForFood() {
        return factoryService.viewModel(new StandardPortionForFood.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid paperclip"
    )
    public ItemDefinition.Manager manageItemDefinition() {
        return factoryService.viewModel(new ItemDefinition.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public NutrientListAndDefinition.Manager manageNutrientListAndDefinition() {
        return factoryService.viewModel(new NutrientListAndDefinition.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public NutrientValuesPerNutrientAndItem.Manager manageNutrientValuesPerNutrientAndItem() {
        return factoryService.viewModel(new NutrientValuesPerNutrientAndItem.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "ruler"
    )
    public AnthropometricAverage.Manager manageAnthropometricAverage() {
        return factoryService.viewModel(new AnthropometricAverage.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid user-clock"
    )
    public FoodConsumptionOccasion.Manager manageFoodConsumptionOccasion() {
        return factoryService.viewModel(new FoodConsumptionOccasion.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "regular message"
    )
    public FoodConsumptionOccasionDisplayItem.Manager manageFoodConsumptionOccasionDisplayItem() {
        return factoryService.viewModel(new FoodConsumptionOccasionDisplayItem.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid building-user"
    )
    public PlaceOfConsumption.Manager managePlaceOfConsumption() {
        return factoryService.viewModel(new PlaceOfConsumption.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public SpecialDayPredefinedAnswer.Manager manageSpecialDayPredefinedAnswer() {
        return factoryService.viewModel(new SpecialDayPredefinedAnswer.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public SpecialDietPredefinedAnswer.Manager manageSpecialDietPredefinedAnswer() {
        return factoryService.viewModel(new SpecialDietPredefinedAnswer.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public TranslationInCountryLanguage.Manager manageTranslationInCountryLanguage() {
        return factoryService.viewModel(new TranslationInCountryLanguage.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "building .default-color"
    )
    public CenterInvolved.Manager manageCenterInvolved() {
        return factoryService.viewModel(new CenterInvolved.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "earth-europe"
    )
    public CountryInvolved.Manager manageCountryInvolved() {
        return factoryService.viewModel(new CountryInvolved.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid user"
    )
    public Interviewer.Manager manageInterviewer() {
        return factoryService.viewModel(new Interviewer.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-circle-question"
    )
    public SubjectToBeInterviewed.Manager manageSubjectToBeInterviewed() {
        return factoryService.viewModel(new SubjectToBeInterviewed.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid circle-question"
    )
    public ProbingQuestion.Manager manageProbingQuestion() {
        return factoryService.viewModel(new ProbingQuestion.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public HouseholdMeasure.Manager manageHouseholdMeasure() {
        return factoryService.viewModel(new HouseholdMeasure.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public PhotoForQuantity.Manager managePhotoForQuantity() {
        return factoryService.viewModel(new PhotoForQuantity.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public Shape.Manager manageShape() {
        return factoryService.viewModel(new Shape.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public StandardUnitForFoodOrRecipe.Manager manageStandardUnitForFoodOrRecipe() {
        return factoryService.viewModel(new StandardUnitForFoodOrRecipe.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public ThicknessForShapeMethod.Manager manageThicknessForShapeMethod() {
        return factoryService.viewModel(new ThicknessForShapeMethod.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.Manager managePercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe(
            ) {
        return factoryService.viewModel(new PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid tag .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                            + "solid exclamation-circle .recipe-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
    )
    public FacetDescriptorPathwayForRecipe.Manager manageFacetDescriptorPathwayForRecipe() {
        return factoryService.viewModel(new FacetDescriptorPathwayForRecipe.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid tag .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
    )
    public FacetDescriptorPathwayForRecipeGroup.Manager manageFacetDescriptorPathwayForRecipeGroup(
            ) {
        return factoryService.viewModel(new FacetDescriptorPathwayForRecipeGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                            + "brands shopify .brand-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public RecipeBrand.Manager manageRecipeBrand() {
        return factoryService.viewModel(new RecipeBrand.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                            + "solid tag .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public RecipeDescriptor.Manager manageRecipeDescriptor() {
        return factoryService.viewModel(new RecipeDescriptor.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid stroopwafel .recipe-color,\n"
                            + "solid swatchbook .recipe-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public RecipeFacet.Manager manageRecipeFacet() {
        return factoryService.viewModel(new RecipeFacet.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public RuleAppliedToFacets.Manager manageRuleAppliedToFacets() {
        return factoryService.viewModel(new RuleAppliedToFacets.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid stroopwafel .recipe-color"
    )
    public Recipe.Manager manageRecipe() {
        return factoryService.viewModel(new Recipe.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public RecipeIngredient.Manager manageRecipeIngredient() {
        return factoryService.viewModel(new RecipeIngredient.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public RecipeIngredientQuantification.Manager manageRecipeIngredientQuantification() {
        return factoryService.viewModel(new RecipeIngredientQuantification.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public MaximumValueForARecipeOrGroup.Manager manageMaximumValueForARecipeOrGroup() {
        return factoryService.viewModel(new MaximumValueForARecipeOrGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid question .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
    )
    public ProbingQuestionPathwayForRecipe.Manager manageProbingQuestionPathwayForRecipe() {
        return factoryService.viewModel(new ProbingQuestionPathwayForRecipe.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid scale-balanced .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85,\n"
                            + "solid exclamation-circle .recipe-color-em .ov-size-60 .ov-left-50 .ov-bottom-85\n"
    )
    public QuantificationMethodPathwayForRecipe.Manager manageQuantificationMethodPathwayForRecipe(
            ) {
        return factoryService.viewModel(new QuantificationMethodPathwayForRecipe.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid scale-balanced .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
    )
    public QuantificationMethodPathwayForRecipeGroup.Manager manageQuantificationMethodPathwayForRecipeGroup(
            ) {
        return factoryService.viewModel(new QuantificationMethodPathwayForRecipeGroup.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public DayOfWeek.Manager manageDayOfWeek() {
        return factoryService.viewModel(new DayOfWeek.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public FacetDescriptorThatCannotBeSubstituted.Manager manageFacetDescriptorThatCannotBeSubstituted(
            ) {
        return factoryService.viewModel(new FacetDescriptorThatCannotBeSubstituted.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public GroupSubstitution.Manager manageGroupSubstitution() {
        return factoryService.viewModel(new GroupSubstitution.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public MacroNutrientLimit.Manager manageMacroNutrientLimit() {
        return factoryService.viewModel(new MacroNutrientLimit.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public Month.Manager manageMonth() {
        return factoryService.viewModel(new Month.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public NoteStatus.Manager manageNoteStatus() {
        return factoryService.viewModel(new NoteStatus.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-walking-arrow-right .recipe-color"
    )
    public RecipeTypePathway.Manager manageRecipeTypePathway() {
        return factoryService.viewModel(new RecipeTypePathway.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public SelectedParameter.Manager manageSelectedParameter() {
        return factoryService.viewModel(new SelectedParameter.Manager(searchService, ""));
    }

    @Action
    @ActionLayout
    public SelectedParameterForDataEntry.Manager manageSelectedParameterForDataEntry() {
        return factoryService.viewModel(new SelectedParameterForDataEntry.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid tablets .supplement-color"
    )
    public DietarySupplement.Manager manageDietarySupplement() {
        return factoryService.viewModel(new DietarySupplement.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid tablets .supplement-color,\n"
                            + "solid layer-group .supplement-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public DietarySupplementClassification.Manager manageDietarySupplementClassification() {
        return factoryService.viewModel(new DietarySupplementClassification.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid tablets .supplement-color,\n"
                            + "solid tag .supplement-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public DietarySupplementDescriptor.Manager manageDietarySupplementDescriptor() {
        return factoryService.viewModel(new DietarySupplementDescriptor.Manager(searchService, ""));
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid tablets .supplement-color,\n"
                            + "solid swatchbook .supplement-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
    )
    public DietarySupplementFacet.Manager manageDietarySupplementFacet() {
        return factoryService.viewModel(new DietarySupplementFacet.Manager(searchService, ""));
    }
}
