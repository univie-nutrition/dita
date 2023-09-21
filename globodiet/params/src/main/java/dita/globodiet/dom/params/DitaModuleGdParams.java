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
package dita.globodiet.dom.params;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({

        // Menu Entries
        dita.globodiet.dom.params.EntitiesMenu.class,

        // Entities
        dita.globodiet.dom.params.general_info.AnthropometricAverage.class,
        dita.globodiet.dom.params.food_descript.Brand.class,
        dita.globodiet.dom.params.interview.CenterInvolved.class,
        dita.globodiet.dom.params.general_info.PlaceOfConsumptionDisplayItem.class,
        dita.globodiet.dom.params.interview.CountryInvolved.class,
        dita.globodiet.dom.params.food_list.ComposedRecipeIngredient.class,
        dita.globodiet.dom.params.setting.DayOfWeek.class,
        dita.globodiet.dom.params.food_coefficient.DensityFactorForFood.class,
        dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor.class,
        dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted.class,
        dita.globodiet.dom.params.supplement.DietarySupplementClassification.class,
        dita.globodiet.dom.params.supplement.DietarySupplementDescriptor.class,
        dita.globodiet.dom.params.supplement.DietarySupplementFacet.class,
        dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood.class,
        dita.globodiet.dom.params.general_info.TranslationInCountryLanguage.class,
        dita.globodiet.dom.params.food_descript.RuleAppliedToFacet.class,
        dita.globodiet.dom.params.food_descript.Facet.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood.class,
        dita.globodiet.dom.params.general_info.FoodConsumptionOccasion.class,
        dita.globodiet.dom.params.food_descript.ExceptionForSomeFoodToTheFacetDescriptorPathway.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias.class,
        dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor.class,
        dita.globodiet.dom.params.classification.FoodGroup.class,
        dita.globodiet.dom.params.interview.Interviewer.class,
        dita.globodiet.dom.params.food_table.FoodOrRecipeOrAttachment.class,
        dita.globodiet.dom.params.quantif.HouseholdMeasure.class,
        dita.globodiet.dom.params.quantif.PhotoForQuantity.class,
        dita.globodiet.dom.params.quantif.Shape.class,
        dita.globodiet.dom.params.food_quantif.StandardPortionForFood.class,
        dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrASubgroup.class,
        dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrASubSubgroup.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredient.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification.class,
        dita.globodiet.dom.params.recipe_list.Recipe.class,
        dita.globodiet.dom.params.setting.Month.class,
        dita.globodiet.dom.params.food_table.NutrientValuesPerNutrientAndItem.class,
        dita.globodiet.dom.params.setting.MacroNutrientLimit.class,
        dita.globodiet.dom.params.food_table.NutrientListAndDefinition.class,
        dita.globodiet.dom.params.setting.SelectedParameter.class,
        dita.globodiet.dom.params.setting.SelectedParameterForDataEntry.class,
        dita.globodiet.dom.params.general_info.PlaceOfConsumption.class,
        dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods.class,
        dita.globodiet.dom.params.probing.ProbingQuestion.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFood.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup.class,
        dita.globodiet.dom.params.recipe_quantif.QuantificationMethodsPathwayForRecipeGroup.class,
        dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe.class,
        dita.globodiet.dom.params.recipe_description.BrandForRecipe.class,
        dita.globodiet.dom.params.recipe_description.RuleAppliedToFacets.class,
        dita.globodiet.dom.params.recipe_description.RecipeFacet.class,
        dita.globodiet.dom.params.recipe_description.RecipeDescriptor.class,
        dita.globodiet.dom.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor.class,
        dita.globodiet.dom.params.recipe_description.ExceptionToFacetsPathwayForRecipe.class,
        dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood.class,
        dita.globodiet.dom.params.setting.DefinitionOfRecipePathway.class,
        dita.globodiet.dom.params.classification.RecipeGroup.class,
        dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup.class,
        dita.globodiet.dom.params.general_info.SpecialDayPredefinedAnswer.class,
        dita.globodiet.dom.params.general_info.SpecialDietPredefinedAnswer.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood.class,
        dita.globodiet.dom.params.setting.NoteStatus.class,
        dita.globodiet.dom.params.classification.FoodSubgroup.class,
        dita.globodiet.dom.params.interview.SubjectToBeInterviewed.class,
        dita.globodiet.dom.params.setting.GroupOrSubgroupThatCanBeSubstitutable.class,
        dita.globodiet.dom.params.supplement.DietarySupplement.class,
        dita.globodiet.dom.params.quantif.ThicknessForShapeMethod.class,

        // Mixins
        dita.globodiet.dom.params.food_descript.Brand_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentBrandMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_descript.Brand_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentBrandMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_descript.Brand_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentBrandMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.interview.CenterInvolved_attachedCountry.class,
        dita.globodiet.dom.params.interview.CountryInvolved_dependentCenterInvolvedMappedByAttachedCountry.class,
        dita.globodiet.dom.params.general_info.PlaceOfConsumptionDisplayItem_placeOfConsumption.class,
        dita.globodiet.dom.params.general_info.PlaceOfConsumption_dependentPlaceOfConsumptionDisplayItemMappedByPlaceOfConsumption.class,
        dita.globodiet.dom.params.food_list.ComposedRecipeIngredient_foodOrRecipe.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentComposedRecipeIngredientMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentComposedRecipeIngredientMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.food_coefficient.DensityFactorForFood_foodOrRecipe.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentDensityFactorForFoodMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentDensityFactorForFoodMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.food_coefficient.DensityFactorForFood_facetDescriptors.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentDensityFactorForFoodMappedByFacetDescriptors.class,
        dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentImprobableSequenceOfFacetAndDescriptorMappedByFood.class,
        dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentImprobableSequenceOfFacetAndDescriptorMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentImprobableSequenceOfFacetAndDescriptorMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentImprobableSequenceOfFacetAndDescriptorMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_facet.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentImprobableSequenceOfFacetAndDescriptorMappedByFacet.class,
        dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor_descriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentImprobableSequenceOfFacetAndDescriptorMappedByDescriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_facet.class,
        dita.globodiet.dom.params.food_descript.Facet_dependentFacetDescriptorMappedByFacet.class,
        dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted_facet.class,
        dita.globodiet.dom.params.food_descript.Facet_dependentFacetDescriptorThatCannotBeSubstitutedMappedByFacet.class,
        dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted_descriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentFacetDescriptorThatCannotBeSubstitutedMappedByDescriptor.class,
        dita.globodiet.dom.params.supplement.DietarySupplementDescriptor_facet.class,
        dita.globodiet.dom.params.supplement.DietarySupplementFacet_dependentDietarySupplementDescriptorMappedByFacet.class,
        dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentEdiblePartCoefficientForFoodMappedByFood.class,
        dita.globodiet.dom.params.food_coefficient.EdiblePartCoefficientForFood_facetDescriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentEdiblePartCoefficientForFoodMappedByFacetDescriptor.class,
        dita.globodiet.dom.params.food_descript.RuleAppliedToFacet_facet.class,
        dita.globodiet.dom.params.food_descript.Facet_dependentRuleAppliedToFacetMappedByFacet.class,
        dita.globodiet.dom.params.food_descript.RuleAppliedToFacet_facetDescriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentRuleAppliedToFacetMappedByFacetDescriptor.class,
        dita.globodiet.dom.params.food_descript.RuleAppliedToFacet_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentRuleAppliedToFacetMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_descript.RuleAppliedToFacet_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentRuleAppliedToFacetMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_descript.RuleAppliedToFacet_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentRuleAppliedToFacetMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood_fatGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentPercentOfFatLeftInTheDishForFoodMappedByFatGroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood_fatSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatLeftInTheDishForFoodMappedByFatSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatLeftInTheDishForFood_fatSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatLeftInTheDishForFoodMappedByFatSubSubgroup.class,
        dita.globodiet.dom.params.food_descript.ExceptionForSomeFoodToTheFacetDescriptorPathway_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentExceptionForSomeFoodToTheFacetDescriptorPathwayMappedByFood.class,
        dita.globodiet.dom.params.food_descript.ExceptionForSomeFoodToTheFacetDescriptorPathway_mandatoryInSequenceOfFacets.class,
        dita.globodiet.dom.params.food_descript.Facet_dependentExceptionForSomeFoodToTheFacetDescriptorPathwayMappedByMandatoryInSequenceOfFacets.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentFoodOrProductOrAliasMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentFoodOrProductOrAliasMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentFoodOrProductOrAliasMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentCrossReferenceBetweenFoodGroupAndDescriptorMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentCrossReferenceBetweenFoodGroupAndDescriptorMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentCrossReferenceBetweenFoodGroupAndDescriptorMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor_facet.class,
        dita.globodiet.dom.params.food_descript.Facet_dependentCrossReferenceBetweenFoodGroupAndDescriptorMappedByFacet.class,
        dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor_descriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentCrossReferenceBetweenFoodGroupAndDescriptorMappedByDescriptor.class,
        dita.globodiet.dom.params.interview.Interviewer_country.class,
        dita.globodiet.dom.params.interview.CountryInvolved_dependentInterviewerMappedByCountry.class,
        dita.globodiet.dom.params.interview.Interviewer_center.class,
        dita.globodiet.dom.params.interview.CenterInvolved_dependentInterviewerMappedByCenter.class,
        dita.globodiet.dom.params.food_quantif.StandardPortionForFood_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentStandardPortionForFoodMappedByFood.class,
        dita.globodiet.dom.params.quantif.StandardUnitForFoodOrRecipe_foodOrRecipe.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrASubgroup_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentMaximumValueForARecipeOrASubgroupMappedByRecipeGroup.class,
        dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrASubgroup_recipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentMaximumValueForARecipeOrASubgroupMappedByRecipeSubgroup.class,
        dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrASubgroup_recipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentMaximumValueForARecipeOrASubgroupMappedByRecipe.class,
        dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrASubSubgroup_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentMaximumValueForAFoodOrASubSubgroupMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrASubSubgroup_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentMaximumValueForAFoodOrASubSubgroupMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrASubSubgroup_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentMaximumValueForAFoodOrASubSubgroupMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrASubSubgroup_facetDescriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentMaximumValueForAFoodOrASubSubgroupMappedByFacetDescriptor.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredient_recipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentRecipeIngredientMappedByRecipe.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredient_foodOrRecipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentRecipeIngredientMappedByFoodOrRecipeGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentRecipeIngredientMappedByFoodOrRecipeGroup.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredient_foodOrRecipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentRecipeIngredientMappedByFoodOrRecipeSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentRecipeIngredientMappedByFoodOrRecipeSubgroup.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredient_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentRecipeIngredientMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredient_facetDescriptors.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentRecipeIngredientMappedByFacetDescriptors.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredient_foodOrRecipe.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentRecipeIngredientMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentRecipeIngredientMappedByFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_recipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentRecipeIngredientQuantificationMappedByRecipe.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_ingredientFoodOrRecipe.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentRecipeIngredientQuantificationMappedByIngredientFoodOrRecipe.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_shape.class,
        dita.globodiet.dom.params.quantif.Shape_dependentRecipeIngredientQuantificationMappedByShape.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_shapeSurfaceInCm2Obj.class,
        dita.globodiet.dom.params.quantif.Shape_dependentRecipeIngredientQuantificationMappedByShapeSurfaceInCm2Obj.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_shapeThickness.class,
        dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_dependentRecipeIngredientQuantificationMappedByShapeThickness.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_thicknessInMmObj.class,
        dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_dependentRecipeIngredientQuantificationMappedByThicknessInMmObj.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_photoNumberOfTheSelectedPhotoObj.class,
        dita.globodiet.dom.params.quantif.PhotoForQuantity_dependentRecipeIngredientQuantificationMappedByPhotoNumberOfTheSelectedPhotoObj.class,
        dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification_photoQuantityObj.class,
        dita.globodiet.dom.params.quantif.PhotoForQuantity_dependentRecipeIngredientQuantificationMappedByPhotoQuantityObj.class,
        dita.globodiet.dom.params.recipe_list.Recipe_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentRecipeMappedByRecipeGroup.class,
        dita.globodiet.dom.params.recipe_list.Recipe_recipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentRecipeMappedByRecipeSubgroup.class,
        dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods_probingQuestion.class,
        dita.globodiet.dom.params.probing.ProbingQuestion_dependentProbingQuestionPathwayForFoodsMappedByProbingQuestion.class,
        dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentProbingQuestionPathwayForFoodsMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentProbingQuestionPathwayForFoodsMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentProbingQuestionPathwayForFoodsMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentProbingQuestionPathwayForFoodsMappedByFood.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFood_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentQuantificationMethodsPathwayForFoodMappedByFood.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFood_photo.class,
        dita.globodiet.dom.params.quantif.Shape_dependentQuantificationMethodsPathwayForFoodMappedByPhoto.class,
        dita.globodiet.dom.params.quantif.PhotoForQuantity_dependentQuantificationMethodsPathwayForFoodMappedByPhoto.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentQuantificationMethodsPathwayForFoodGroupMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentQuantificationMethodsPathwayForFoodGroupMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentQuantificationMethodsPathwayForFoodGroupMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_physicalStateFacetDescriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentQuantificationMethodsPathwayForFoodGroupMappedByPhysicalStateFacetDescriptor.class,
        dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup_photo.class,
        dita.globodiet.dom.params.quantif.Shape_dependentQuantificationMethodsPathwayForFoodGroupMappedByPhoto.class,
        dita.globodiet.dom.params.quantif.PhotoForQuantity_dependentQuantificationMethodsPathwayForFoodGroupMappedByPhoto.class,
        dita.globodiet.dom.params.recipe_quantif.QuantificationMethodsPathwayForRecipeGroup_recipeGroupObj.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentQuantificationMethodsPathwayForRecipeGroupMappedByRecipeGroupObj.class,
        dita.globodiet.dom.params.recipe_quantif.QuantificationMethodsPathwayForRecipeGroup_photo.class,
        dita.globodiet.dom.params.quantif.Shape_dependentQuantificationMethodsPathwayForRecipeGroupMappedByPhoto.class,
        dita.globodiet.dom.params.quantif.PhotoForQuantity_dependentQuantificationMethodsPathwayForRecipeGroupMappedByPhoto.class,
        dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe_recipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentQuantificationMethodPathwayForRecipeMappedByRecipe.class,
        dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe_photo.class,
        dita.globodiet.dom.params.quantif.Shape_dependentQuantificationMethodPathwayForRecipeMappedByPhoto.class,
        dita.globodiet.dom.params.quantif.PhotoForQuantity_dependentQuantificationMethodPathwayForRecipeMappedByPhoto.class,
        dita.globodiet.dom.params.recipe_description.BrandForRecipe_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentBrandForRecipeMappedByRecipeGroup.class,
        dita.globodiet.dom.params.recipe_description.BrandForRecipe_recipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentBrandForRecipeMappedByRecipeSubgroup.class,
        dita.globodiet.dom.params.recipe_description.RuleAppliedToFacets_facetWhereTheRuleMustBeAppliedObj.class,
        dita.globodiet.dom.params.food_descript.Facet_dependentRuleAppliedToFacetsMappedByFacetWhereTheRuleMustBeAppliedObj.class,
        dita.globodiet.dom.params.recipe_description.RuleAppliedToFacets_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentRuleAppliedToFacetsMappedByRecipeGroup.class,
        dita.globodiet.dom.params.recipe_description.RuleAppliedToFacets_recipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentRuleAppliedToFacetsMappedByRecipeSubgroup.class,
        dita.globodiet.dom.params.recipe_description.RecipeDescriptor_recipeFacet.class,
        dita.globodiet.dom.params.recipe_description.RecipeFacet_dependentRecipeDescriptorMappedByRecipeFacet.class,
        dita.globodiet.dom.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentCrossReferenceBetweenRecipeGroupAndDescriptorMappedByRecipeGroup.class,
        dita.globodiet.dom.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor_recipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentCrossReferenceBetweenRecipeGroupAndDescriptorMappedByRecipeSubgroup.class,
        dita.globodiet.dom.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor_recipeFacet.class,
        dita.globodiet.dom.params.recipe_description.RecipeFacet_dependentCrossReferenceBetweenRecipeGroupAndDescriptorMappedByRecipeFacet.class,
        dita.globodiet.dom.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor_recipeDescriptor.class,
        dita.globodiet.dom.params.recipe_description.RecipeDescriptor_dependentCrossReferenceBetweenRecipeGroupAndDescriptorMappedByRecipeDescriptor.class,
        dita.globodiet.dom.params.recipe_description.ExceptionToFacetsPathwayForRecipe_recipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentExceptionToFacetsPathwayForRecipeMappedByRecipe.class,
        dita.globodiet.dom.params.recipe_description.ExceptionToFacetsPathwayForRecipe_recipeFacet.class,
        dita.globodiet.dom.params.recipe_description.RecipeFacet_dependentExceptionToFacetsPathwayForRecipeMappedByRecipeFacet.class,
        dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentRawToCookedConversionFactorForFoodMappedByFood.class,
        dita.globodiet.dom.params.food_coefficient.RawToCookedConversionFactorForFood_facetDescriptors.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentRawToCookedConversionFactorForFoodMappedByFacetDescriptors.class,
        dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes_probingQuestion.class,
        dita.globodiet.dom.params.probing.ProbingQuestion_dependentProbingQuestionPathwayForRecipesMappedByProbingQuestion.class,
        dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentProbingQuestionPathwayForRecipesMappedByRecipeGroup.class,
        dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes_recipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentProbingQuestionPathwayForRecipesMappedByRecipeSubgroup.class,
        dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes_recipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentProbingQuestionPathwayForRecipesMappedByRecipe.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentRecipeSubgroupMappedByRecipeGroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFood.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFatGroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFatSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFatSubSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFat.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFat.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipeGroup.class,
        dita.globodiet.dom.params.classification.RecipeGroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByRecipeGroup.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipeSubgroup.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByRecipeSubgroup.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_recipe.class,
        dita.globodiet.dom.params.recipe_list.Recipe_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByRecipe.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFatGroup.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFatSubgroup.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFatSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFatSubSubgroup.class,
        dita.globodiet.dom.params.recipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe_fssFat.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFat.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFoodGroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_food.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentPercentOfFatUseDuringCookingForFoodMappedByFood.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_fatGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFatGroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_fatSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFatSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_fatSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentPercentOfFatUseDuringCookingForFoodMappedByFatSubSubgroup.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_fat.class,
        dita.globodiet.dom.params.food_list.FoodOrProductOrAlias_dependentPercentOfFatUseDuringCookingForFoodMappedByFat.class,
        dita.globodiet.dom.params.food_coefficient.PercentOfFatUseDuringCookingForFood_cookingMethodFacetDescriptor.class,
        dita.globodiet.dom.params.food_descript.FacetDescriptor_dependentPercentOfFatUseDuringCookingForFoodMappedByCookingMethodFacetDescriptor.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentFoodSubgroupMappedByFoodGroup.class,
        dita.globodiet.dom.params.interview.SubjectToBeInterviewed_country.class,
        dita.globodiet.dom.params.interview.CountryInvolved_dependentSubjectToBeInterviewedMappedByCountry.class,
        dita.globodiet.dom.params.interview.SubjectToBeInterviewed_center.class,
        dita.globodiet.dom.params.interview.CenterInvolved_dependentSubjectToBeInterviewedMappedByCenter.class,
        dita.globodiet.dom.params.setting.GroupOrSubgroupThatCanBeSubstitutable_foodGroup.class,
        dita.globodiet.dom.params.classification.FoodGroup_dependentGroupOrSubgroupThatCanBeSubstitutableMappedByFoodGroup.class,
        dita.globodiet.dom.params.setting.GroupOrSubgroupThatCanBeSubstitutable_foodSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentGroupOrSubgroupThatCanBeSubstitutableMappedByFoodSubgroup.class,
        dita.globodiet.dom.params.setting.GroupOrSubgroupThatCanBeSubstitutable_foodSubSubgroup.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentGroupOrSubgroupThatCanBeSubstitutableMappedByFoodSubSubgroup.class,
        dita.globodiet.dom.params.supplement.DietarySupplement_classification.class,
        dita.globodiet.dom.params.supplement.DietarySupplementClassification_dependentDietarySupplementMappedByClassification.class,
        dita.globodiet.dom.params.supplement.DietarySupplement_facet.class,
        dita.globodiet.dom.params.supplement.DietarySupplementFacet_dependentDietarySupplementMappedByFacet.class,
        dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_foodSubgroups.class,
        dita.globodiet.dom.params.classification.FoodSubgroup_dependentThicknessForShapeMethodMappedByFoodSubgroups.class,
        dita.globodiet.dom.params.quantif.ThicknessForShapeMethod_recipeSubgroups.class,
        dita.globodiet.dom.params.classification.RecipeSubgroup_dependentThicknessForShapeMethodMappedByRecipeSubgroups.class,

        })
public class DitaModuleGdParams {
}
