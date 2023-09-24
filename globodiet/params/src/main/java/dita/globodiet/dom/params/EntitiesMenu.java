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
import dita.globodiet.dom.params.food_descript.Brand;
import dita.globodiet.dom.params.food_descript.CrossReferenceBetweenFoodGroupAndDescriptor;
import dita.globodiet.dom.params.food_descript.ExceptionForSomeFoodToTheFacetDescriptorPathway;
import dita.globodiet.dom.params.food_descript.Facet;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import dita.globodiet.dom.params.food_descript.ImprobableSequenceOfFacetAndDescriptor;
import dita.globodiet.dom.params.food_descript.RuleAppliedToFacet;
import dita.globodiet.dom.params.food_list.ComposedRecipeIngredient;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.dom.params.food_max.MaximumValueForAFoodOrGroup;
import dita.globodiet.dom.params.food_probing.ProbingQuestionPathwayForFoods;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFood;
import dita.globodiet.dom.params.food_quantif.QuantificationMethodsPathwayForFoodGroup;
import dita.globodiet.dom.params.food_quantif.StandardPortionForFood;
import dita.globodiet.dom.params.food_table.FoodOrRecipeOrAttachment;
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
import dita.globodiet.dom.params.recipe_description.BrandForRecipe;
import dita.globodiet.dom.params.recipe_description.CrossReferenceBetweenRecipeGroupAndDescriptor;
import dita.globodiet.dom.params.recipe_description.ExceptionToFacetsPathwayForRecipe;
import dita.globodiet.dom.params.recipe_description.RecipeDescriptor;
import dita.globodiet.dom.params.recipe_description.RecipeFacet;
import dita.globodiet.dom.params.recipe_description.RuleAppliedToFacets;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.dom.params.recipe_list.RecipeIngredient;
import dita.globodiet.dom.params.recipe_list.RecipeIngredientQuantification;
import dita.globodiet.dom.params.recipe_max.MaximumValueForARecipeOrGroup;
import dita.globodiet.dom.params.recipe_probing.ProbingQuestionPathwayForRecipes;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodPathwayForRecipe;
import dita.globodiet.dom.params.recipe_quantif.QuantificationMethodsPathwayForRecipeGroup;
import dita.globodiet.dom.params.setting.DayOfWeek;
import dita.globodiet.dom.params.setting.DefinitionOfRecipePathway;
import dita.globodiet.dom.params.setting.FacetDescriptorThatCannotBeSubstituted;
import dita.globodiet.dom.params.setting.GroupOrSubgroupThatCanBeSubstitutable;
import dita.globodiet.dom.params.setting.MacroNutrientLimit;
import dita.globodiet.dom.params.setting.Month;
import dita.globodiet.dom.params.setting.NoteStatus;
import dita.globodiet.dom.params.setting.SelectedParameter;
import dita.globodiet.dom.params.setting.SelectedParameterForDataEntry;
import dita.globodiet.dom.params.supplement.DietarySupplement;
import dita.globodiet.dom.params.supplement.DietarySupplementClassification;
import dita.globodiet.dom.params.supplement.DietarySupplementDescriptor;
import dita.globodiet.dom.params.supplement.DietarySupplementFacet;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.services.repository.RepositoryService;

@Named("dita.globodiet.params.EntitiesMenu")
@DomainService(
        nature = org.apache.causeway.applib.annotation.NatureOfService.VIEW
)
public class EntitiesMenu {
    @Inject
    private RepositoryService repositoryService;

    @Action
    @ActionLayout(
            cssClassFa = "solid layer-group olive"
    )
    public List<FoodGroup> listAllFoodGroup() {
        return repositoryService.allInstances(FoodGroup.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid layer-group darkgreen"
    )
    public List<FoodSubgroup> listAllFoodSubgroup() {
        return repositoryService.allInstances(FoodSubgroup.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid layer-group olive"
    )
    public List<RecipeGroup> listAllRecipeGroup() {
        return repositoryService.allInstances(RecipeGroup.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid layer-group deepgreen"
    )
    public List<RecipeSubgroup> listAllRecipeSubgroup() {
        return repositoryService.allInstances(RecipeSubgroup.class);
    }

    @Action
    @ActionLayout
    public List<DensityFactorForFood> listAllDensityFactorForFood() {
        return repositoryService.allInstances(DensityFactorForFood.class);
    }

    @Action
    @ActionLayout
    public List<EdiblePartCoefficientForFood> listAllEdiblePartCoefficientForFood() {
        return repositoryService.allInstances(EdiblePartCoefficientForFood.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public List<PercentOfFatLeftInTheDishForFood> listAllPercentOfFatLeftInTheDishForFood() {
        return repositoryService.allInstances(PercentOfFatLeftInTheDishForFood.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood> listAllPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood(
            ) {
        return repositoryService.allInstances(PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public List<PercentOfFatUseDuringCookingForFood> listAllPercentOfFatUseDuringCookingForFood() {
        return repositoryService.allInstances(PercentOfFatUseDuringCookingForFood.class);
    }

    @Action
    @ActionLayout
    public List<RawToCookedConversionFactorForFood> listAllRawToCookedConversionFactorForFood() {
        return repositoryService.allInstances(RawToCookedConversionFactorForFood.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "brands shopify deeppink"
    )
    public List<Brand> listAllBrand() {
        return repositoryService.allInstances(Brand.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid right-left"
    )
    public List<CrossReferenceBetweenFoodGroupAndDescriptor> listAllCrossReferenceBetweenFoodGroupAndDescriptor(
            ) {
        return repositoryService.allInstances(CrossReferenceBetweenFoodGroupAndDescriptor.class);
    }

    @Action
    @ActionLayout
    public List<ExceptionForSomeFoodToTheFacetDescriptorPathway> listAllExceptionForSomeFoodToTheFacetDescriptorPathway(
            ) {
        return repositoryService.allInstances(ExceptionForSomeFoodToTheFacetDescriptorPathway.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "tags olive"
    )
    public List<Facet> listAllFacet() {
        return repositoryService.allInstances(Facet.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "tag darkgreen"
    )
    public List<FacetDescriptor> listAllFacetDescriptor() {
        return repositoryService.allInstances(FacetDescriptor.class);
    }

    @Action
    @ActionLayout
    public List<ImprobableSequenceOfFacetAndDescriptor> listAllImprobableSequenceOfFacetAndDescriptor(
            ) {
        return repositoryService.allInstances(ImprobableSequenceOfFacetAndDescriptor.class);
    }

    @Action
    @ActionLayout
    public List<RuleAppliedToFacet> listAllRuleAppliedToFacet() {
        return repositoryService.allInstances(RuleAppliedToFacet.class);
    }

    @Action
    @ActionLayout
    public List<ComposedRecipeIngredient> listAllComposedRecipeIngredient() {
        return repositoryService.allInstances(ComposedRecipeIngredient.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid utensils darkgreen"
    )
    public List<FoodOrProductOrAlias> listAllFoodOrProductOrAlias() {
        return repositoryService.allInstances(FoodOrProductOrAlias.class);
    }

    @Action
    @ActionLayout
    public List<MaximumValueForAFoodOrGroup> listAllMaximumValueForAFoodOrGroup() {
        return repositoryService.allInstances(MaximumValueForAFoodOrGroup.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid right-left"
    )
    public List<ProbingQuestionPathwayForFoods> listAllProbingQuestionPathwayForFoods() {
        return repositoryService.allInstances(ProbingQuestionPathwayForFoods.class);
    }

    @Action
    @ActionLayout
    public List<QuantificationMethodsPathwayForFood> listAllQuantificationMethodsPathwayForFood() {
        return repositoryService.allInstances(QuantificationMethodsPathwayForFood.class);
    }

    @Action
    @ActionLayout
    public List<QuantificationMethodsPathwayForFoodGroup> listAllQuantificationMethodsPathwayForFoodGroup(
            ) {
        return repositoryService.allInstances(QuantificationMethodsPathwayForFoodGroup.class);
    }

    @Action
    @ActionLayout
    public List<StandardPortionForFood> listAllStandardPortionForFood() {
        return repositoryService.allInstances(StandardPortionForFood.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid paperclip"
    )
    public List<FoodOrRecipeOrAttachment> listAllFoodOrRecipeOrAttachment() {
        return repositoryService.allInstances(FoodOrRecipeOrAttachment.class);
    }

    @Action
    @ActionLayout
    public List<NutrientListAndDefinition> listAllNutrientListAndDefinition() {
        return repositoryService.allInstances(NutrientListAndDefinition.class);
    }

    @Action
    @ActionLayout
    public List<NutrientValuesPerNutrientAndItem> listAllNutrientValuesPerNutrientAndItem() {
        return repositoryService.allInstances(NutrientValuesPerNutrientAndItem.class);
    }

    @Action
    @ActionLayout
    public List<AnthropometricAverage> listAllAnthropometricAverage() {
        return repositoryService.allInstances(AnthropometricAverage.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid user-clock"
    )
    public List<FoodConsumptionOccasion> listAllFoodConsumptionOccasion() {
        return repositoryService.allInstances(FoodConsumptionOccasion.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "regular message"
    )
    public List<FoodConsumptionOccasionDisplayItem> listAllFoodConsumptionOccasionDisplayItem() {
        return repositoryService.allInstances(FoodConsumptionOccasionDisplayItem.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid building-user"
    )
    public List<PlaceOfConsumption> listAllPlaceOfConsumption() {
        return repositoryService.allInstances(PlaceOfConsumption.class);
    }

    @Action
    @ActionLayout
    public List<SpecialDayPredefinedAnswer> listAllSpecialDayPredefinedAnswer() {
        return repositoryService.allInstances(SpecialDayPredefinedAnswer.class);
    }

    @Action
    @ActionLayout
    public List<SpecialDietPredefinedAnswer> listAllSpecialDietPredefinedAnswer() {
        return repositoryService.allInstances(SpecialDietPredefinedAnswer.class);
    }

    @Action
    @ActionLayout
    public List<TranslationInCountryLanguage> listAllTranslationInCountryLanguage() {
        return repositoryService.allInstances(TranslationInCountryLanguage.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "building"
    )
    public List<CenterInvolved> listAllCenterInvolved() {
        return repositoryService.allInstances(CenterInvolved.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "earth-europe"
    )
    public List<CountryInvolved> listAllCountryInvolved() {
        return repositoryService.allInstances(CountryInvolved.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid user"
    )
    public List<Interviewer> listAllInterviewer() {
        return repositoryService.allInstances(Interviewer.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid person-circle-question"
    )
    public List<SubjectToBeInterviewed> listAllSubjectToBeInterviewed() {
        return repositoryService.allInstances(SubjectToBeInterviewed.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid circle-question"
    )
    public List<ProbingQuestion> listAllProbingQuestion() {
        return repositoryService.allInstances(ProbingQuestion.class);
    }

    @Action
    @ActionLayout
    public List<HouseholdMeasure> listAllHouseholdMeasure() {
        return repositoryService.allInstances(HouseholdMeasure.class);
    }

    @Action
    @ActionLayout
    public List<PhotoForQuantity> listAllPhotoForQuantity() {
        return repositoryService.allInstances(PhotoForQuantity.class);
    }

    @Action
    @ActionLayout
    public List<Shape> listAllShape() {
        return repositoryService.allInstances(Shape.class);
    }

    @Action
    @ActionLayout
    public List<StandardUnitForFoodOrRecipe> listAllStandardUnitForFoodOrRecipe() {
        return repositoryService.allInstances(StandardUnitForFoodOrRecipe.class);
    }

    @Action
    @ActionLayout
    public List<ThicknessForShapeMethod> listAllThicknessForShapeMethod() {
        return repositoryService.allInstances(ThicknessForShapeMethod.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid percent"
    )
    public List<PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe> listAllPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe(
            ) {
        return repositoryService.allInstances(PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "brands shopify deeppink"
    )
    public List<BrandForRecipe> listAllBrandForRecipe() {
        return repositoryService.allInstances(BrandForRecipe.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid right-left"
    )
    public List<CrossReferenceBetweenRecipeGroupAndDescriptor> listAllCrossReferenceBetweenRecipeGroupAndDescriptor(
            ) {
        return repositoryService.allInstances(CrossReferenceBetweenRecipeGroupAndDescriptor.class);
    }

    @Action
    @ActionLayout
    public List<ExceptionToFacetsPathwayForRecipe> listAllExceptionToFacetsPathwayForRecipe() {
        return repositoryService.allInstances(ExceptionToFacetsPathwayForRecipe.class);
    }

    @Action
    @ActionLayout
    public List<RecipeDescriptor> listAllRecipeDescriptor() {
        return repositoryService.allInstances(RecipeDescriptor.class);
    }

    @Action
    @ActionLayout
    public List<RecipeFacet> listAllRecipeFacet() {
        return repositoryService.allInstances(RecipeFacet.class);
    }

    @Action
    @ActionLayout
    public List<RuleAppliedToFacets> listAllRuleAppliedToFacets() {
        return repositoryService.allInstances(RuleAppliedToFacets.class);
    }

    @Action
    @ActionLayout
    public List<Recipe> listAllRecipe() {
        return repositoryService.allInstances(Recipe.class);
    }

    @Action
    @ActionLayout
    public List<RecipeIngredient> listAllRecipeIngredient() {
        return repositoryService.allInstances(RecipeIngredient.class);
    }

    @Action
    @ActionLayout
    public List<RecipeIngredientQuantification> listAllRecipeIngredientQuantification() {
        return repositoryService.allInstances(RecipeIngredientQuantification.class);
    }

    @Action
    @ActionLayout
    public List<MaximumValueForARecipeOrGroup> listAllMaximumValueForARecipeOrGroup() {
        return repositoryService.allInstances(MaximumValueForARecipeOrGroup.class);
    }

    @Action
    @ActionLayout(
            cssClassFa = "solid right-left"
    )
    public List<ProbingQuestionPathwayForRecipes> listAllProbingQuestionPathwayForRecipes() {
        return repositoryService.allInstances(ProbingQuestionPathwayForRecipes.class);
    }

    @Action
    @ActionLayout
    public List<QuantificationMethodPathwayForRecipe> listAllQuantificationMethodPathwayForRecipe(
            ) {
        return repositoryService.allInstances(QuantificationMethodPathwayForRecipe.class);
    }

    @Action
    @ActionLayout
    public List<QuantificationMethodsPathwayForRecipeGroup> listAllQuantificationMethodsPathwayForRecipeGroup(
            ) {
        return repositoryService.allInstances(QuantificationMethodsPathwayForRecipeGroup.class);
    }

    @Action
    @ActionLayout
    public List<DayOfWeek> listAllDayOfWeek() {
        return repositoryService.allInstances(DayOfWeek.class);
    }

    @Action
    @ActionLayout
    public List<DefinitionOfRecipePathway> listAllDefinitionOfRecipePathway() {
        return repositoryService.allInstances(DefinitionOfRecipePathway.class);
    }

    @Action
    @ActionLayout
    public List<FacetDescriptorThatCannotBeSubstituted> listAllFacetDescriptorThatCannotBeSubstituted(
            ) {
        return repositoryService.allInstances(FacetDescriptorThatCannotBeSubstituted.class);
    }

    @Action
    @ActionLayout
    public List<GroupOrSubgroupThatCanBeSubstitutable> listAllGroupOrSubgroupThatCanBeSubstitutable(
            ) {
        return repositoryService.allInstances(GroupOrSubgroupThatCanBeSubstitutable.class);
    }

    @Action
    @ActionLayout
    public List<MacroNutrientLimit> listAllMacroNutrientLimit() {
        return repositoryService.allInstances(MacroNutrientLimit.class);
    }

    @Action
    @ActionLayout
    public List<Month> listAllMonth() {
        return repositoryService.allInstances(Month.class);
    }

    @Action
    @ActionLayout
    public List<NoteStatus> listAllNoteStatus() {
        return repositoryService.allInstances(NoteStatus.class);
    }

    @Action
    @ActionLayout
    public List<SelectedParameter> listAllSelectedParameter() {
        return repositoryService.allInstances(SelectedParameter.class);
    }

    @Action
    @ActionLayout
    public List<SelectedParameterForDataEntry> listAllSelectedParameterForDataEntry() {
        return repositoryService.allInstances(SelectedParameterForDataEntry.class);
    }

    @Action
    @ActionLayout
    public List<DietarySupplement> listAllDietarySupplement() {
        return repositoryService.allInstances(DietarySupplement.class);
    }

    @Action
    @ActionLayout
    public List<DietarySupplementClassification> listAllDietarySupplementClassification() {
        return repositoryService.allInstances(DietarySupplementClassification.class);
    }

    @Action
    @ActionLayout
    public List<DietarySupplementDescriptor> listAllDietarySupplementDescriptor() {
        return repositoryService.allInstances(DietarySupplementDescriptor.class);
    }

    @Action
    @ActionLayout
    public List<DietarySupplementFacet> listAllDietarySupplementFacet() {
        return repositoryService.allInstances(DietarySupplementFacet.class);
    }
}
