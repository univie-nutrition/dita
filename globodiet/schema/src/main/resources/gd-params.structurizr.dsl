workspace {

    !impliedRelationships "false" 
    !identifiers "hierarchical" 

    model {
        DitaGDParams = softwareSystem "Dita GD Params" "Software system having all the GD Params entities." {
            paramsfood_list = container "params.food_list" {
                ComposedRecipeIngredient = component "ComposedRecipeIngredient" "Composed Recipe Ingredient" "Entity" 
                Food = component "Food" "Food, Product, On-the-fly Recipe or Alias" "Entity" 
                FoodSubgroup = component "FoodSubgroup" "Food groups further narrowed down by subgroups and optional sub-subgroups" "Entity" 
            }
            paramsinterview = container "params.interview" {
                CenterInvolved = component "CenterInvolved" "Center involved" "Entity" 
                CountryInvolved = component "CountryInvolved" "Country involved" "Entity" 
                Interviewer = component "Interviewer" "Interviewer" "Entity" 
                SubjectToBeInterviewed = component "SubjectToBeInterviewed" "Subjects to be interviewed" "Entity" 
            }
            paramsfood_coefficient = container "params.food_coefficient" {
                DensityFactorForFoodOrRecipe = component "DensityFactorForFoodOrRecipe" "Density factor for food" "Entity" 
                EdiblePartCoefficientForFood = component "EdiblePartCoefficientForFood" "Edible part coefficients for foods" "Entity" 
                PercentOfFatLeftInTheDishForFood = component "PercentOfFatLeftInTheDishForFood" "% of fat left in the dish for food" "Entity" 
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood = component "PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood" "% of fat/sauce/sweetener (FSS) added after cooking (regarding food)" "Entity" 
                PercentOfFatUseDuringCookingForFood = component "PercentOfFatUseDuringCookingForFood" "% of fat use during cooking attached to a food" "Entity" 
                RawToCookedConversionFactorForFood = component "RawToCookedConversionFactorForFood" "Raw to cooked conversion factors for foods" "Entity" 
            }
            paramsnutrient = container "params.nutrient" {
                Nutrient = component "Nutrient" "Nutrient definition (energy, proteins, carbohydrates, etc.)." "Entity" 
                NutrientForFoodOrGroup = component "NutrientForFoodOrGroup" "Cross reference between food (or food group) and nutrient values (usually multiple)." "Entity" 
                NutrientValue = component "NutrientValue" "Nutrient value for nutrient-for-food-or-group. The nutrient-for-food-or-group code origniates from @{table ITEMS_DEF}, which is cross-referencing food(-groups) with this table." "Entity" 
            }
            paramspathway = container "params.pathway" {
                FacetDescriptorPathwayForFood = component "FacetDescriptorPathwayForFood" "Facet/descriptor pathway for individual food. Supersedes this food's group facet/descriptor pathway from @{table GROUPFAC}." "Entity" 
                FacetDescriptorPathwayForFoodGroup = component "FacetDescriptorPathwayForFoodGroup" "Facet/descriptor pathway for food group/subgroup. Optionally can be superseded by @{table FOODFAEX}." "Entity" 
                FacetDescriptorPathwayForRecipe = component "FacetDescriptorPathwayForRecipe" "Facet/descriptor pathway for individual recipe. Supersedes this Recipe's group facet/descriptor pathway from @{table R_GROUPFAC}." "Entity" 
                FacetDescriptorPathwayForRecipeGroup = component "FacetDescriptorPathwayForRecipeGroup" "Facet/descriptor pathway for recipe group/subgroup. Optionally can be superseded by @{table R_RCPFAEX}." "Entity" 
                ProbingQuestion = component "ProbingQuestion" "Probing question" "Entity" 
                ProbingQuestionPathwayForFood = component "ProbingQuestionPathwayForFood" "Probing question pathway for food." "Entity" 
                ProbingQuestionPathwayForRecipe = component "ProbingQuestionPathwayForRecipe" "Probing question pathway for recipe." "Entity" 
                QuantificationMethodPathwayForFood = component "QuantificationMethodPathwayForFood" "Quantification method pathway for individual food. Supersedes this food's group quantification method pathway from @{table QM_GROUP}." "Entity" 
                QuantificationMethodPathwayForFoodGroup = component "QuantificationMethodPathwayForFoodGroup" "Quantification method pathway for food group/subgroup. Optionally can be superseded by @{table QM_FOODS}." "Entity" 
                QuantificationMethodPathwayForRecipe = component "QuantificationMethodPathwayForRecipe" "Quantification method pathway for individual recipe. Supersedes this recipe's group quantification method pathway from @{table QM_RCLAS}." "Entity" 
                QuantificationMethodPathwayForRecipeGroup = component "QuantificationMethodPathwayForRecipeGroup" "Quantification methods pathway for recipe group/subgroup. Optionally can be superseded by @{table QM_RECIP}." "Entity" 
            }
            paramsquantif = container "params.quantif" {
                MaximumValueForFoodOrGroup = component "MaximumValueForFoodOrGroup" "Maximum quantity consumed that could be entered in the interview application for a given food (or group) before the control message warns the interviewer of an implausible value." "Entity" 
                MaximumValueForRecipeOrGroup = component "MaximumValueForRecipeOrGroup" "Maximum quantity consumed that could be entered in the interview application for a given recipe (or group) before the control message warns the interviewer of an implausible value." "Entity" 
                Photo = component "Photo" "Photo and its quantities" "Entity" 
                RecipeIngredientQuantification = component "RecipeIngredientQuantification" "Mixed recipes: Ingredients quantification for shape and photo methods" "Entity" 
                Shape = component "Shape" "Shape for quantity" "Entity" 
                StandardPortionForFood = component "StandardPortionForFood" "standard portions for foods" "Entity" 
                StandardUnitForFoodOrRecipe = component "StandardUnitForFoodOrRecipe" "standard units for foods and recipes" "Entity" 
                ThicknessForShape = component "ThicknessForShape" "Thickness for shape method" "Entity" 
            }
            paramsrecipe_coefficient = container "params.recipe_coefficient" {
                PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe = component "PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe" "% of fat/sauce/sweetener (FSS) added after cooking (regarding recipes)" "Entity" 
            }
            paramsrecipe_description = container "params.recipe_description" {
                RecipeBrand = component "RecipeBrand" "Brandname list for mixed recipes" "Entity" 
                RecipeDescriptor = component "RecipeDescriptor" "Descriptor per facet" "Entity" 
                RecipeFacet = component "RecipeFacet" "Facet for Recipe" "Entity" 
                RecipeFacetRule = component "RecipeFacetRule" "Rule applied to recipe facet." "Entity" 
            }
            paramsrecipe_list = container "params.recipe_list" {
                Recipe = component "Recipe" "Mixed (a priory) Recipe (not an on-the-fly one): After preparation, the different ingredients cannot be identified and quantified separately, as those are derived from cook books (for homemade and similar recipes), internet or are based on information received from the industry (for commercial recipes). They are entered as standard recipes with the RECIPE MANAGER and handled at the country level prior to the commencement of the interviews. The mixed recipe database may contain three types of recipes: 1) ‘open recipes’, 2) ‘closed recipes’ and 3) ‘strictly commercial with brand recipes’. For each mixed recipe, information on the expected variation of ingredients within the study population needs to be collected, too. If the variation is found to be great, the recipe should be treated as an open recipe. It is also possible to enter several standard variations of a recipe depending on regions. If no variations are expected, a recipe should be treated as a closed recipe or as a strictly commercial recipe. Furthermore, it has to be decided if each ingredient is fixed or substitutable. When entering the recipe ingredients with RECIPE MANAGER, the ingredients are described and quantified like in the food pathway." "Entity" 
                RecipeIngredient = component "RecipeIngredient" "Mixed recipes: Ingredients description/quantification" "Entity" 
                RecipeSubgroup = component "RecipeSubgroup" "Recipe subgroup" "Entity" 
            }
            paramssetting = container "params.setting" {
                FacetDescriptorThatCannotBeSubstituted = component "FacetDescriptorThatCannotBeSubstituted" "Facet/descriptor that cannot be substituted" "Entity" 
                FoodConsumptionOccasion = component "FoodConsumptionOccasion" "Food Consumption Occasion" "Entity" 
                FoodConsumptionOccasionDisplayItem = component "FoodConsumptionOccasionDisplayItem" "List of foods often eaten at each food consumption occasion. During the quick list step, this list will appear as a reminder or a help to the interviewer when a food consumption occasion (FCO) is proposed (before choosing a FCO and after filling in a FCO quick list)." "Entity" 
                GroupSubstitution = component "GroupSubstitution" "Group/subgroup that can be substituted" "Entity" 
            }
            paramssupplement = container "params.supplement" {
                DietarySupplement = component "DietarySupplement" "Dietary supplement" "Entity" 
                DietarySupplementClassification = component "DietarySupplementClassification" "Dietary supplement classification" "Entity" 
                DietarySupplementDescriptor = component "DietarySupplementDescriptor" "Dietary supplement descriptor" "Entity" 
                DietarySupplementFacet = component "DietarySupplementFacet" "Dietary supplement facet" "Entity" 
            }
            virtual = container "virtual" {
                FCK = component "FCK" "Food Classifier Key" "Entity" 
            }
            paramsfood_descript = container "params.food_descript" {
                FoodBrand = component "FoodBrand" "Brand names are used in the food description phase" "Entity" 
                FoodDescriptor = component "FoodDescriptor" "Descriptor for food facets (not recipe facets)" "Entity" 
                FoodFacet = component "FoodFacet" "Facet describing food (not recipe)" "Entity" 
                FoodFacetRule = component "FoodFacetRule" "Rule applied to food facet." "Entity" 
                ImprobableSequenceOfFacetAndDescriptor = component "ImprobableSequenceOfFacetAndDescriptor" "Improbable sequence of facets/descriptors" "Entity" 
            }
        }
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsrecipe_list.Recipe "recipe￪,foodOrRecipe￪" 
        DitaGDParams.paramsrecipe_description.RecipeBrand -> DitaGDParams.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        DitaGDParams.paramsinterview.SubjectToBeInterviewed -> DitaGDParams.paramsinterview.CountryInvolved "country￪" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe -> DitaGDParams.paramspathway.ProbingQuestion "probingQuestion￪" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsquantif.Photo "photo￪" 
        DitaGDParams.paramsquantif.ThicknessForShape -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "recipeSubgroups￪￪,recipeSubgroups￪￪" 
        DitaGDParams.paramsquantif.MaximumValueForFoodOrGroup -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptor￪￪,facetDescriptor￪￪" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsfood_list.Food "foodOrRecipe￪" 
        DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe -> DitaGDParams.paramsfood_list.Food "foodOrRecipe￪" 
        DitaGDParams.paramssetting.FoodConsumptionOccasionDisplayItem -> DitaGDParams.paramssetting.FoodConsumptionOccasion "foodConsumptionOccasion￪" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsfood_list.Food "ingredientFoodOrRecipe￪" 
        DitaGDParams.paramsinterview.Interviewer -> DitaGDParams.paramsinterview.CountryInvolved "country￪" 
        DitaGDParams.paramssetting.GroupSubstitution -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood -> DitaGDParams.paramsfood_list.Food "food￪" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_list.Food "food￪,fat￪" 
        DitaGDParams.paramsfood_descript.FoodFacetRule -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptor￪￪,facetDescriptor￪￪" 
        DitaGDParams.paramsfood_descript.FoodFacetRule -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramssetting.GroupSubstitution -> DitaGDParams.paramsfood_list.FoodSubgroup "applyToFoodGroups￪￪,applyToFoodGroups￪￪,applyToFoodGroups￪￪" 
        DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood -> DitaGDParams.paramsfood_list.Food "food￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup -> DitaGDParams.paramsquantif.Shape "photoOrShape￪" 
        DitaGDParams.paramsfood_descript.FoodDescriptor -> DitaGDParams.paramsfood_descript.FoodFacet "facet￪" 
        DitaGDParams.paramssetting.FacetDescriptorThatCannotBeSubstituted -> DitaGDParams.paramsfood_descript.FoodDescriptor "descriptor￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup -> DitaGDParams.paramsfood_descript.FoodDescriptor "physicalStateFacetDescriptor￪￪,physicalStateFacetDescriptor￪￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFood -> DitaGDParams.paramsfood_list.Food "food￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFood -> DitaGDParams.paramsfood_list.Food "food￪" 
        DitaGDParams.paramsrecipe_description.RecipeDescriptor -> DitaGDParams.paramsrecipe_description.RecipeFacet "recipeFacet￪" 
        DitaGDParams.paramsquantif.ThicknessForShape -> DitaGDParams.paramsfood_list.FoodSubgroup "foodSubgroups￪￪,foodSubgroups￪￪,foodSubgroups￪￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup -> DitaGDParams.paramsquantif.Photo "photoOrShape￪" 
        DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> DitaGDParams.paramsfood_descript.FoodDescriptor "descriptor￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramsfood_descript.FoodFacetRule -> DitaGDParams.paramsfood_descript.FoodFacet "facet￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipe -> DitaGDParams.paramsrecipe_description.RecipeFacet "selectedRecipeFacet￪" 
        DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe -> DitaGDParams.paramsquantif.Shape "photoOrShape￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup -> DitaGDParams.paramsfood_descript.FoodFacet "foodFacet￪" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipe￪" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.virtual.FCK "foodOrRecipeGroup￪,foodOrRecipeGroup￪,foodOrRecipeSubgroup￪,foodOrRecipeSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptors￪￪,facetDescriptors￪￪" 
        DitaGDParams.paramsrecipe_description.RecipeFacetRule -> DitaGDParams.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        DitaGDParams.paramsquantif.StandardPortionForFood -> DitaGDParams.paramsfood_list.Food "food￪" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe -> DitaGDParams.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramsnutrient.NutrientValue -> DitaGDParams.paramsnutrient.Nutrient "nutrient￪" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.virtual.FCK "foodOrRecipeGroup￪,foodOrRecipeGroup￪,foodOrRecipeSubgroup￪,foodOrRecipeSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup -> DitaGDParams.paramsquantif.Photo "photoOrShape￪" 
        DitaGDParams.paramsinterview.SubjectToBeInterviewed -> DitaGDParams.paramsinterview.CenterInvolved "center￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup -> DitaGDParams.virtual.FCK "recipeGroup￪" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForFood -> DitaGDParams.paramspathway.ProbingQuestion "probingQuestion￪" 
        DitaGDParams.paramsrecipe_description.RecipeFacetRule -> DitaGDParams.paramsfood_descript.FoodFacet "facetWhereTheRuleMustBeApplied" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForFood -> DitaGDParams.paramsfood_list.Food "food￪" 
        DitaGDParams.paramsinterview.Interviewer -> DitaGDParams.paramsinterview.CenterInvolved "center￪" 
        DitaGDParams.paramsfood_list.FoodSubgroup -> DitaGDParams.virtual.FCK "foodGroup￪" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪,fssGroup￪,fssSubgroup￪,fssSubSubgroup￪" 
        DitaGDParams.paramsfood_descript.FoodBrand -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup -> DitaGDParams.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        DitaGDParams.paramssupplement.DietarySupplement -> DitaGDParams.paramssupplement.DietarySupplementFacet "facet￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipe￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup -> DitaGDParams.paramsfood_descript.FoodDescriptor "foodDescriptor￪" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsrecipe_list.Recipe "recipe￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup -> DitaGDParams.paramsrecipe_description.RecipeFacet "recipeFacet￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForFood -> DitaGDParams.paramsfood_descript.FoodFacet "selectedFoodFacet￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFood -> DitaGDParams.paramsquantif.Shape "photoOrShape￪" 
        DitaGDParams.paramsfood_list.ComposedRecipeIngredient -> DitaGDParams.paramsfood_list.Food "foodOrRecipe￪" 
        DitaGDParams.paramssupplement.DietarySupplementDescriptor -> DitaGDParams.paramssupplement.DietarySupplementFacet "facet￪" 
        DitaGDParams.paramsnutrient.NutrientValue -> DitaGDParams.paramsnutrient.NutrientForFoodOrGroup "nutrientForFoodOrGroup￪" 
        DitaGDParams.paramsrecipe_list.Recipe -> DitaGDParams.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        DitaGDParams.paramssetting.FacetDescriptorThatCannotBeSubstituted -> DitaGDParams.paramsfood_descript.FoodFacet "facet￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup -> DitaGDParams.paramsquantif.Shape "photoOrShape￪" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood -> DitaGDParams.virtual.FCK "fatGroup￪,fatSubgroup￪,fatSubSubgroup￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup -> DitaGDParams.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsquantif.ThicknessForShape "shapeThickness￪" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipe￪" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.paramsfood_list.Food "fss￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipe -> DitaGDParams.paramsrecipe_list.Recipe "recipe￪" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪,fatGroup￪,fatSubgroup￪,fatSubSubgroup￪" 
        DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptors￪￪,facetDescriptors￪￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForFood -> DitaGDParams.paramsquantif.Photo "photoOrShape￪" 
        DitaGDParams.paramsquantif.MaximumValueForFoodOrGroup -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramssetting.GroupSubstitution -> DitaGDParams.paramsrecipe_list.RecipeSubgroup "applyToRecipeGroups￪￪,applyToRecipeGroups￪￪" 
        DitaGDParams.paramsnutrient.NutrientForFoodOrGroup -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipe￪" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> DitaGDParams.paramsfood_list.Food "food￪,fss￪" 
        DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> DitaGDParams.virtual.FCK "recipeGroup￪,recipeSubgroup￪,fssGroup￪,fssSubgroup￪,fssSubSubgroup￪" 
        DitaGDParams.paramsfood_list.ComposedRecipeIngredient -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipe￪" 
        DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipe￪" 
        DitaGDParams.paramsfood_coefficient.DensityFactorForFoodOrRecipe -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptors￪￪,facetDescriptors￪￪" 
        DitaGDParams.paramsquantif.RecipeIngredientQuantification -> DitaGDParams.paramsquantif.Shape "shape￪" 
        DitaGDParams.paramsrecipe_list.RecipeIngredient -> DitaGDParams.paramsfood_list.Food "foodOrRecipe￪" 
        DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe -> DitaGDParams.paramsquantif.Photo "photoOrShape￪" 
        DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> DitaGDParams.paramsfood_list.Food "food￪" 
        DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> DitaGDParams.paramsfood_descript.FoodDescriptor "cookingMethodFacetDescriptor￪￪,cookingMethodFacetDescriptor￪￪" 
        DitaGDParams.paramsfood_list.Food -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramsfood_coefficient.DensityFactorForFoodOrRecipe -> DitaGDParams.paramsfood_list.Food "foodOrRecipe￪" 
        DitaGDParams.paramssupplement.DietarySupplement -> DitaGDParams.paramssupplement.DietarySupplementClassification "classification￪" 
        DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup -> DitaGDParams.paramsrecipe_description.RecipeDescriptor "recipeDescriptor￪" 
        DitaGDParams.paramsinterview.CenterInvolved -> DitaGDParams.paramsinterview.CountryInvolved "attachedCountry￪" 
        DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> DitaGDParams.paramsfood_descript.FoodFacet "facet￪" 
        DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup -> DitaGDParams.paramsrecipe_list.Recipe "recipe￪" 
        DitaGDParams.paramsrecipe_list.RecipeSubgroup -> DitaGDParams.virtual.FCK "recipeGroup￪" 
        DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood -> DitaGDParams.paramsfood_descript.FoodDescriptor "facetDescriptor￪￪,facetDescriptor￪￪" 
        DitaGDParams.paramspathway.ProbingQuestionPathwayForFood -> DitaGDParams.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        DitaGDParams.paramsfood_coefficient.DensityFactorForFoodOrRecipe -> DitaGDParams.paramsrecipe_list.Recipe "foodOrRecipe￪" 
    }

    views {
        systemContext DitaGDParams "SystemContext" "An example of a System Context diagram." {
            include DitaGDParams 
        }

        container DitaGDParams "a0" "a1" {
            include DitaGDParams.paramsfood_list 
            include DitaGDParams.paramsinterview 
            include DitaGDParams.paramsfood_coefficient 
            include DitaGDParams.paramsnutrient 
            include DitaGDParams.paramspathway 
            include DitaGDParams.paramsquantif 
            include DitaGDParams.paramsrecipe_coefficient 
            include DitaGDParams.paramsrecipe_description 
            include DitaGDParams.paramsrecipe_list 
            include DitaGDParams.paramssetting 
            include DitaGDParams.paramssupplement 
            include DitaGDParams.virtual 
            include DitaGDParams.paramsfood_descript 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsfood_coefficient "params0food_coefficient" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodDescriptor 
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include DitaGDParams.virtual.FCK 
            include DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsfood_descript "params0food_descript" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodBrand 
            include DitaGDParams.paramsfood_descript.FoodDescriptor 
            include DitaGDParams.paramsfood_descript.FoodFacet 
            include DitaGDParams.paramsfood_descript.FoodFacetRule 
            include DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor 
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForFood 
            include DitaGDParams.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood 
            include DitaGDParams.paramsquantif.MaximumValueForFoodOrGroup 
            include DitaGDParams.paramsrecipe_description.RecipeFacetRule 
            include DitaGDParams.paramsrecipe_list.RecipeIngredient 
            include DitaGDParams.paramssetting.FacetDescriptorThatCannotBeSubstituted 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include DitaGDParams.virtual.FCK 
            include DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsfood_list "params0food_list" "no desc" {
            include DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor 
            include DitaGDParams.paramsfood_list.ComposedRecipeIngredient 
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsfood_list.FoodSubgroup 
            include DitaGDParams.paramsnutrient.NutrientForFoodOrGroup 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForFood 
            include DitaGDParams.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include DitaGDParams.paramspathway.ProbingQuestionPathwayForFood 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForFood 
            include DitaGDParams.paramsfood_coefficient.EdiblePartCoefficientForFood 
            include DitaGDParams.paramsquantif.RecipeIngredientQuantification 
            include DitaGDParams.paramsquantif.StandardPortionForFood 
            include DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe 
            include DitaGDParams.paramsquantif.ThicknessForShape 
            include DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.paramsrecipe_list.RecipeIngredient 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood 
            include DitaGDParams.paramssetting.GroupSubstitution 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include DitaGDParams.virtual.FCK 
            include DitaGDParams.paramsfood_coefficient.RawToCookedConversionFactorForFood 
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
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsnutrient.Nutrient 
            include DitaGDParams.paramsnutrient.NutrientForFoodOrGroup 
            include DitaGDParams.paramsnutrient.NutrientValue 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.virtual.FCK 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramspathway "params0pathway" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodDescriptor 
            include DitaGDParams.paramsfood_descript.FoodFacet 
            include DitaGDParams.paramsfood_list.Food 
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
            include DitaGDParams.paramsquantif.Photo 
            include DitaGDParams.paramsquantif.Shape 
            include DitaGDParams.paramsrecipe_description.RecipeDescriptor 
            include DitaGDParams.paramsrecipe_description.RecipeFacet 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.virtual.FCK 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsquantif "params0quantif" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodDescriptor 
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsfood_list.FoodSubgroup 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForFood 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup 
            include DitaGDParams.paramsquantif.MaximumValueForFoodOrGroup 
            include DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup 
            include DitaGDParams.paramsquantif.Photo 
            include DitaGDParams.paramsquantif.RecipeIngredientQuantification 
            include DitaGDParams.paramsquantif.Shape 
            include DitaGDParams.paramsquantif.StandardPortionForFood 
            include DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe 
            include DitaGDParams.paramsquantif.ThicknessForShape 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.paramsrecipe_list.RecipeSubgroup 
            include DitaGDParams.virtual.FCK 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsrecipe_coefficient "params0recipe_coefficient" "no desc" {
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.virtual.FCK 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsrecipe_description "params0recipe_description" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodFacet 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipe 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup 
            include DitaGDParams.paramsrecipe_description.RecipeBrand 
            include DitaGDParams.paramsrecipe_description.RecipeDescriptor 
            include DitaGDParams.paramsrecipe_description.RecipeFacet 
            include DitaGDParams.paramsrecipe_description.RecipeFacetRule 
            include DitaGDParams.virtual.FCK 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramsrecipe_list "params0recipe_list" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodDescriptor 
            include DitaGDParams.paramsfood_list.ComposedRecipeIngredient 
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsnutrient.NutrientForFoodOrGroup 
            include DitaGDParams.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipe 
            include DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipe 
            include DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup 
            include DitaGDParams.paramsquantif.RecipeIngredientQuantification 
            include DitaGDParams.paramsquantif.StandardUnitForFoodOrRecipe 
            include DitaGDParams.paramsquantif.ThicknessForShape 
            include DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.paramsrecipe_list.RecipeIngredient 
            include DitaGDParams.paramsrecipe_list.RecipeSubgroup 
            include DitaGDParams.paramssetting.GroupSubstitution 
            include DitaGDParams.virtual.FCK 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramssetting "params0setting" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodDescriptor 
            include DitaGDParams.paramsfood_descript.FoodFacet 
            include DitaGDParams.paramsfood_list.FoodSubgroup 
            include DitaGDParams.paramsrecipe_list.RecipeSubgroup 
            include DitaGDParams.paramssetting.FacetDescriptorThatCannotBeSubstituted 
            include DitaGDParams.paramssetting.FoodConsumptionOccasion 
            include DitaGDParams.paramssetting.FoodConsumptionOccasionDisplayItem 
            include DitaGDParams.paramssetting.GroupSubstitution 
            include DitaGDParams.virtual.FCK 
            autolayout tb 300 300 
        }

        component DitaGDParams.paramssupplement "params0supplement" "no desc" {
            include DitaGDParams.paramssupplement.DietarySupplement 
            include DitaGDParams.paramssupplement.DietarySupplementClassification 
            include DitaGDParams.paramssupplement.DietarySupplementDescriptor 
            include DitaGDParams.paramssupplement.DietarySupplementFacet 
            autolayout tb 300 300 
        }

        component DitaGDParams.virtual "virtual" "no desc" {
            include DitaGDParams.paramsfood_descript.FoodBrand 
            include DitaGDParams.paramsfood_descript.FoodFacetRule 
            include DitaGDParams.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor 
            include DitaGDParams.paramsfood_list.Food 
            include DitaGDParams.paramsfood_list.FoodSubgroup 
            include DitaGDParams.paramsnutrient.NutrientForFoodOrGroup 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForFoodGroup 
            include DitaGDParams.paramspathway.FacetDescriptorPathwayForRecipeGroup 
            include DitaGDParams.paramspathway.ProbingQuestionPathwayForFood 
            include DitaGDParams.paramspathway.ProbingQuestionPathwayForRecipe 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include DitaGDParams.paramspathway.QuantificationMethodPathwayForRecipeGroup 
            include DitaGDParams.paramsquantif.MaximumValueForFoodOrGroup 
            include DitaGDParams.paramsquantif.MaximumValueForRecipeOrGroup 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood 
            include DitaGDParams.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include DitaGDParams.paramsrecipe_description.RecipeBrand 
            include DitaGDParams.paramsrecipe_description.RecipeFacetRule 
            include DitaGDParams.paramsrecipe_list.Recipe 
            include DitaGDParams.paramsrecipe_list.RecipeIngredient 
            include DitaGDParams.paramsrecipe_list.RecipeSubgroup 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood 
            include DitaGDParams.paramssetting.GroupSubstitution 
            include DitaGDParams.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include DitaGDParams.virtual.FCK 
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

