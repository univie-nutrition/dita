workspace {

    !impliedRelationships "false" 
    !identifiers "hierarchical" 

    model {
        DitaGDParams = softwareSystem "Dita GD Params" "Software system having all the GD Params entities." {
            paramsfood_list = container "params.food_list" {
                ComposedRecipeIngredient = component "ComposedRecipeIngredient" 
                Food = component "Food" 
                FoodGroup = component "FoodGroup" 
                FoodSubgroup = component "FoodSubgroup" 
            }
            paramsfood_coefficient = container "params.food_coefficient" {
                DensityFactorForFood = component "DensityFactorForFood" 
                EdiblePartCoefficientForFood = component "EdiblePartCoefficientForFood" 
                PercentOfFatLeftInTheDishForFood = component "PercentOfFatLeftInTheDishForFood" 
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood = component "PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood" 
                PercentOfFatUseDuringCookingForFood = component "PercentOfFatUseDuringCookingForFood" 
                RawToCookedConversionFactorForFood = component "RawToCookedConversionFactorForFood" 
            }
            paramsinterview = container "params.interview" {
                CenterInvolved = component "CenterInvolved" 
                CountryInvolved = component "CountryInvolved" 
                Interviewer = component "Interviewer" 
                SubjectToBeInterviewed = component "SubjectToBeInterviewed" 
            }
            paramsnutrient = container "params.nutrient" {
                Nutrient = component "Nutrient" 
                NutrientForFoodOrGroup = component "NutrientForFoodOrGroup" 
                NutrientValue = component "NutrientValue" 
            }
            paramspathway = container "params.pathway" {
                FacetDescriptorPathwayForFood = component "FacetDescriptorPathwayForFood" 
                FacetDescriptorPathwayForFoodGroup = component "FacetDescriptorPathwayForFoodGroup" 
                FacetDescriptorPathwayForRecipe = component "FacetDescriptorPathwayForRecipe" 
                FacetDescriptorPathwayForRecipeGroup = component "FacetDescriptorPathwayForRecipeGroup" 
                ProbingQuestion = component "ProbingQuestion" 
                ProbingQuestionPathwayForFood = component "ProbingQuestionPathwayForFood" 
                ProbingQuestionPathwayForRecipe = component "ProbingQuestionPathwayForRecipe" 
                QuantificationMethodPathwayForFood = component "QuantificationMethodPathwayForFood" 
                QuantificationMethodPathwayForFoodGroup = component "QuantificationMethodPathwayForFoodGroup" 
                QuantificationMethodPathwayForRecipe = component "QuantificationMethodPathwayForRecipe" 
                QuantificationMethodPathwayForRecipeGroup = component "QuantificationMethodPathwayForRecipeGroup" 
                RecipeTypePathway = component "RecipeTypePathway" 
            }
            paramsquantif = container "params.quantif" {
                HouseholdMeasure = component "HouseholdMeasure" 
                MaximumValueForFoodOrGroup = component "MaximumValueForFoodOrGroup" 
                MaximumValueForRecipeOrGroup = component "MaximumValueForRecipeOrGroup" 
                PhotoForQuantity = component "PhotoForQuantity" 
                RecipeIngredientQuantification = component "RecipeIngredientQuantification" 
                Shape = component "Shape" 
                StandardPortionForFood = component "StandardPortionForFood" 
                StandardUnitForFoodOrRecipe = component "StandardUnitForFoodOrRecipe" 
                ThicknessForShapeMethod = component "ThicknessForShapeMethod" 
            }
            paramsrecipe_coefficient = container "params.recipe_coefficient" {
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe = component "PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe" 
            }
            paramsrecipe_description = container "params.recipe_description" {
                RecipeBrand = component "RecipeBrand" 
                RecipeDescriptor = component "RecipeDescriptor" 
                RecipeFacet = component "RecipeFacet" 
                RecipeFacetRule = component "RecipeFacetRule" 
            }
            paramsrecipe_list = container "params.recipe_list" {
                Recipe = component "Recipe" 
                RecipeGroup = component "RecipeGroup" 
                RecipeIngredient = component "RecipeIngredient" 
                RecipeSubgroup = component "RecipeSubgroup" 
            }
            paramssetting = container "params.setting" {
                AnthropometricAverage = component "AnthropometricAverage" 
                DayOfWeek = component "DayOfWeek" 
                FacetDescriptorThatCannotBeSubstituted = component "FacetDescriptorThatCannotBeSubstituted" 
                FoodConsumptionOccasion = component "FoodConsumptionOccasion" 
                FoodConsumptionOccasionDisplayItem = component "FoodConsumptionOccasionDisplayItem" 
                GroupSubstitution = component "GroupSubstitution" 
                MacroNutrientLimit = component "MacroNutrientLimit" 
                Month = component "Month" 
                NoteStatus = component "NoteStatus" 
                PlaceOfConsumption = component "PlaceOfConsumption" 
                SelectedParameter = component "SelectedParameter" 
                SelectedParameterForDataEntry = component "SelectedParameterForDataEntry" 
                SpecialDayPredefinedAnswer = component "SpecialDayPredefinedAnswer" 
                SpecialDietPredefinedAnswer = component "SpecialDietPredefinedAnswer" 
                TranslationInCountryLanguage = component "TranslationInCountryLanguage" 
            }
            paramssupplement = container "params.supplement" {
                DietarySupplement = component "DietarySupplement" 
                DietarySupplementClassification = component "DietarySupplementClassification" 
                DietarySupplementDescriptor = component "DietarySupplementDescriptor" 
                DietarySupplementFacet = component "DietarySupplementFacet" 
            }
            paramsfood_descript = container "params.food_descript" {
                FoodBrand = component "FoodBrand" 
                FoodDescriptor = component "FoodDescriptor" 
                FoodFacet = component "FoodFacet" 
                FoodFacetRule = component "FoodFacetRule" 
                ImprobableSequenceOfFacetAndDescriptor = component "ImprobableSequenceOfFacetAndDescriptor" 
            }
        }
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_list.FoodSubgroup "fatSubgroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_list.FoodSubgroup "fatSubSubgroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_list.Food "fatCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_descript.FoodDescriptor "cookingMethodFacetDescriptorLookupKey" 
        DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptorsLookupKey" 
        DitaGDParams.paramsfood_descript.FoodDescriptor -> DitaGDParams.paramsfood_descript.FoodFacet "facetCode" 
        DitaGDParams.paramsfood_descript.FoodFacetRule -> DitaGDParams.paramsfood_descript.FoodFacet "facetCode" 
        DitaGDParams.paramsfood_descript.FoodFacetRule -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptorLookupKey" 
        DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> DitaGDParams.paramsfood_descript.FoodFacet "facetCode" 
        DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> DitaGDParams.paramsfood_descript.FoodDescriptor "descriptorCode" 
        DitaGDParams.paramsfood_list.ComposedRecipeIngredient -> DitaGDParams.paramsfood_list.Food "foodOrRecipeCode" 
        DitaGDParams.paramsfood_list.ComposedRecipeIngredient -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipeCode" 
        DitaGDParams.paramsinterview.CenterInvolved -> DitaGDParams.paramsinterview.CountryInvolved "attachedCountryCode" 
        DitaGDParams.paramsinterview.Interviewer -> DitaGDParams.paramsinterview.CountryInvolved "countryCode" 
        DitaGDParams.paramsinterview.Interviewer -> DitaGDParams.paramsinterview.CenterInvolved "centerCode" 
        DitaGDParams.paramsinterview.SubjectToBeInterviewed -> DitaGDParams.paramsinterview.CountryInvolved "countryCode" 
        DitaGDParams.paramsinterview.SubjectToBeInterviewed -> DitaGDParams.paramsinterview.CenterInvolved "centerCode" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsfood_list.FoodGroup "foodOrRecipeGroupCode" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsrecipe_list.RecipeGroup "foodOrRecipeGroupCode" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsfood_list.FoodSubgroup "foodOrRecipeSubgroupCode" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "foodOrRecipeSubgroupCode" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsfood_list.Food "foodOrRecipeCode" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipeCode" 
        DitaGDParams.paramsnutrient.NutrientValue -> DitaGDParams.paramsnutrient.NutrientForFoodOrGroup "nutrientForFoodOrGroupCode" 
        DitaGDParams.paramsnutrient.NutrientValue -> DitaGDParams.paramsnutrient.Nutrient "nutrientCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFood -> DitaGDParams.paramsfood_descript.FoodFacet "mandatoryInSequenceOfFacetsCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup -> DitaGDParams.paramsfood_descript.FoodFacet "facetCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup -> DitaGDParams.paramsfood_descript.FoodDescriptor "descriptorCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipeCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipe -> DitaGDParams.paramsrecipe_description.RecipeFacet "recipeFacetCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup -> DitaGDParams.paramsrecipe_description.RecipeFacet "recipeFacetCode" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup -> DitaGDParams.paramsrecipe_description.RecipeDescriptor "recipeDescriptorCode" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForFood -> DitaGDParams.paramspathway.ProbingQuestion "probingQuestionCode" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe -> DitaGDParams.paramspathway.ProbingQuestion "probingQuestionCode" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupCode" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipeCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFood -> DitaGDParams.paramsquantif.PhotoForQuantity "photoCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFood -> DitaGDParams.paramsquantif.Shape "photoCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup -> DitaGDParams.paramsfood_descript.FoodDescriptor "physicalStateFacetDescriptorLookupKey" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup -> DitaGDParams.paramsquantif.PhotoForQuantity "photoCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup -> DitaGDParams.paramsquantif.Shape "photoCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipeCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe -> DitaGDParams.paramsquantif.PhotoForQuantity "photoCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe -> DitaGDParams.paramsquantif.Shape "photoCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroup" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup -> DitaGDParams.paramsquantif.PhotoForQuantity "photoCode" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup -> DitaGDParams.paramsquantif.Shape "photoCode" 
        DitaGDParams.paramsquantif.MaximumValueForFoodOrGroup -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptorLookupKey" 
        DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupCode" 
        DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup -> DitaGDParams.paramsrecipe_list.Recipe "recipeCode" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsrecipe_list.Recipe "recipeCode" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsfood_list.Food "ingredientFoodOrRecipeCode" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsquantif.Shape "shapeCode" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsquantif.ThicknessForShapeMethod "shapeThicknessCode" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsquantif.PhotoForQuantity "photoCode" 
        DitaGDParams.paramsquantif.StandardPortionForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe -> DitaGDParams.paramsfood_list.Food "foodOrRecipeCode" 
        DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipeCode" 
        DitaGDParams.paramsquantif.ThicknessForShapeMethod -> DitaGDParams.paramsfood_list.FoodSubgroup "foodSubgroupsLookupKey" 
        DitaGDParams.paramsquantif.ThicknessForShapeMethod -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupsLookupKey" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupCode" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipeCode" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsfood_list.FoodGroup "fssGroupCode" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsfood_list.FoodSubgroup "fssSubgroupCode" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsfood_list.FoodSubgroup "fssSubSubgroupCode" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsfood_list.Food "fssCode" 
        DitaGDParams.paramsrecipe_description.RecipeBrand -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramsrecipe_description.RecipeBrand -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupCode" 
        DitaGDParams.paramsrecipe_description.RecipeDescriptor -> DitaGDParams.paramsrecipe_description.RecipeFacet "recipeFacetCode" 
        DitaGDParams.paramsrecipe_description.RecipeFacetRule -> DitaGDParams.paramsfood_descript.FoodFacet "facetWhereTheRuleMustBeApplied" 
        DitaGDParams.paramsrecipe_description.RecipeFacetRule -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramsrecipe_description.RecipeFacetRule -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupCode" 
        DitaGDParams.paramsrecipe_list.Recipe -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramsrecipe_list.Recipe -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroupCode" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsrecipe_list.Recipe "recipeCode" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsfood_list.FoodGroup "foodOrRecipeGroupCode" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsrecipe_list.RecipeGroup "foodOrRecipeGroupCode" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsfood_list.FoodSubgroup "foodOrRecipeSubgroupCode" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "foodOrRecipeSubgroupCode" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptorsLookupKey" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsfood_list.Food "foodOrRecipeCode" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipeCode" 
        DitaGDParams.paramsrecipe_list.RecipeSubgroup -> DitaGDParams.paramsrecipe_list.RecipeGroup "recipeGroupCode" 
        DitaGDParams.paramssetting.FacetDescriptorThatCannotBeSubstituted -> DitaGDParams.paramsfood_descript.FoodFacet "facetCode" 
        DitaGDParams.paramssetting.FacetDescriptorThatCannotBeSubstituted -> DitaGDParams.paramsfood_descript.FoodDescriptor "descriptorCode" 
        DitaGDParams.paramssetting.FoodConsumptionOccasionDisplayItem -> DitaGDParams.paramssetting.FoodConsumptionOccasion "foodConsumptionOccasionCode" 
        DitaGDParams.paramssetting.GroupSubstitution -> DitaGDParams.paramsfood_list.FoodSubgroup "applyToFoodGroupsLookupKey" 
        DitaGDParams.paramssetting.GroupSubstitution -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "applyToRecipeGroupsLookupKey" 
        DitaGDParams.paramssupplement.DietarySupplement -> DitaGDParams.paramssupplement.DietarySupplementClassification "classificationCode" 
        DitaGDParams.paramssupplement.DietarySupplement -> DitaGDParams.paramssupplement.DietarySupplementFacet "facetCode" 
        DitaGDParams.paramssupplement.DietarySupplementDescriptor -> DitaGDParams.paramssupplement.DietarySupplementFacet "facetCode" 
        DitaGDParams.paramsfood_coefficient.DensityFactorForFood -> DitaGDParams.paramsfood_list.Food "foodOrRecipeCode" 
        DitaGDParams.paramsfood_coefficient.DensityFactorForFood -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipeCode" 
        DitaGDParams.paramsfood_coefficient.DensityFactorForFood -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptorsLookupKey" 
        DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptorLookupKey" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood -> DitaGDParams.paramsfood_list.FoodGroup "fatGroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood -> DitaGDParams.paramsfood_list.FoodSubgroup "fatSubgroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood -> DitaGDParams.paramsfood_list.FoodSubgroup "fatSubSubgroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> DitaGDParams.paramsfood_list.FoodGroup "fssGroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> DitaGDParams.paramsfood_list.FoodSubgroup "fssSubgroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> DitaGDParams.paramsfood_list.FoodSubgroup "fssSubSubgroupCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> DitaGDParams.paramsfood_list.Food "fssCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_list.Food "foodCode" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_list.FoodGroup "fatGroupCode" 
    }

    views {
        systemContext DitaGDParams "SystemContext" "An example of a System Context diagram." {
            include DitaGDParams 
        }

        container DitaGDParams "a0" "a1" {
            include DitaGDParams.paramsfood_list 
            include DitaGDParams.paramsfood_coefficient 
            include DitaGDParams.paramsinterview 
            include DitaGDParams.paramsnutrient 
            include DitaGDParams.paramspathway 
            include DitaGDParams.paramsquantif 
            include DitaGDParams.paramsrecipe_coefficient 
            include DitaGDParams.paramsrecipe_description 
            include DitaGDParams.paramsrecipe_list 
            include DitaGDParams.paramssetting 
            include DitaGDParams.paramssupplement 
            include DitaGDParams.paramsfood_descript 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsfood_coefficient "params0food_coefficient" "no desc" {
            include DitaGDParams.paramsfood_coefficient.DensityFactorForFood 
            include DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsfood_descript "params0food_descript" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodBrand 
            include DitaGDParams.paramsfood_descript.FoodDescriptor 
            include DitaGDParams.paramsfood_descript.FoodFacet 
            include DitaGDParams.paramsfood_descript.FoodFacetRule 
            include DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsfood_list "params0food_list" "no desc" {
            include DitaGDParams.paramsfood_list.ComposedRecipeIngredient 
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsfood_list.FoodGroup 
            include DitaGDParams.paramsfood_list.FoodSubgroup 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsinterview "params0interview" "no desc" {
            include DitaGDParams.paramsinterview.CenterInvolved 
            include DitaGDParams.paramsinterview.CountryInvolved 
            include DitaGDParams.paramsinterview.Interviewer 
            include DitaGDParams.paramsinterview.SubjectToBeInterviewed 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsnutrient "params0nutrient" "no desc" {
            include DitaGDParams.paramsnutrient.Nutrient 
            include DitaGDParams.paramsnutrient.NutrientForFoodOrGroup 
            include DitaGDParams.paramsnutrient.NutrientValue 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramspathway "params0pathway" "no desc" {
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForFood 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipe 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup 
            include DitaGDParams.paramspathway.ProbingQuestion 
            include DitaGDParams.paramspathway.ProbingQuestionPathwayForFood 
            include DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForFood 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup 
            include DitaGDParams.paramspathway.RecipeTypePathway 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsquantif "params0quantif" "no desc" {
            include DitaGDParams.paramsquantif.HouseholdMeasure 
            include DitaGDParams.paramsquantif.MaximumValueForFoodOrGroup 
            include DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup 
            include DitaGDParams.paramsquantif.PhotoForQuantity 
            include DitaGDParams.paramsquantif.RecipeIngredientQuantification 
            include DitaGDParams.paramsquantif.Shape 
            include DitaGDParams.paramsquantif.StandardPortionForFood 
            include DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe 
            include DitaGDParams.paramsquantif.ThicknessForShapeMethod 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsrecipe_coefficient "params0recipe_coefficient" "no desc" {
            include DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsrecipe_description "params0recipe_description" "no desc" {
            include DitaGDParams.paramsrecipe_description.RecipeBrand 
            include DitaGDParams.paramsrecipe_description.RecipeDescriptor 
            include DitaGDParams.paramsrecipe_description.RecipeFacet 
            include DitaGDParams.paramsrecipe_description.RecipeFacetRule 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsrecipe_list "params0recipe_list" "no desc" {
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.paramsrecipe_list.RecipeGroup 
            include DitaGDParams.paramsrecipe_list.RecipeIngredient 
            include DitaGDParams.paramsrecipe_list.RecipeSubgroup 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramssetting "params0setting" "no desc" {
            include DitaGDParams.paramssetting.AnthropometricAverage 
            include DitaGDParams.paramssetting.DayOfWeek 
            include DitaGDParams.paramssetting.FacetDescriptorThatCannotBeSubstituted 
            include DitaGDParams.paramssetting.FoodConsumptionOccasion 
            include DitaGDParams.paramssetting.FoodConsumptionOccasionDisplayItem 
            include DitaGDParams.paramssetting.GroupSubstitution 
            include DitaGDParams.paramssetting.MacroNutrientLimit 
            include DitaGDParams.paramssetting.Month 
            include DitaGDParams.paramssetting.NoteStatus 
            include DitaGDParams.paramssetting.PlaceOfConsumption 
            include DitaGDParams.paramssetting.SelectedParameter 
            include DitaGDParams.paramssetting.SelectedParameterForDataEntry 
            include DitaGDParams.paramssetting.SpecialDayPredefinedAnswer 
            include DitaGDParams.paramssetting.SpecialDietPredefinedAnswer 
            include DitaGDParams.paramssetting.TranslationInCountryLanguage 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramssupplement "params0supplement" "no desc" {
            include DitaGDParams.paramssupplement.DietarySupplement 
            include DitaGDParams.paramssupplement.DietarySupplementClassification 
            include DitaGDParams.paramssupplement.DietarySupplementDescriptor 
            include DitaGDParams.paramssupplement.DietarySupplementFacet 
            autolayout tb 300 300 
        }

        styles {
            element "Person" {
                shape "Person" 
                background "#08427b" 
                color "#ffffff" 
            }
            element "Software System" {
                background "#1168bd" 
                color "#ffffff" 
            }
        }

    }

}

