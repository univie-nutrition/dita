workspace {

    !impliedRelationships "false" 
    !identifiers "hierarchical" 

    model {
        ProjectxxxEntities = softwareSystem "Project xxx Entities" "Software system having all the xxx entities." {
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
        ProjectxxxEntities.paramsrecipe_list.RecipeIngredient -> ProjectxxxEntities.paramsrecipe_list.Recipe "recipe￪,foodOrRecipe￪" 
        ProjectxxxEntities.paramsrecipe_description.RecipeBrand -> ProjectxxxEntities.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        ProjectxxxEntities.paramsinterview.SubjectToBeInterviewed -> ProjectxxxEntities.paramsinterview.CountryInvolved "country￪" 
        ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForRecipe -> ProjectxxxEntities.paramspathway.ProbingQuestion "probingQuestion￪" 
        ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification -> ProjectxxxEntities.paramsquantif.Photo "photo￪" 
        ProjectxxxEntities.paramsquantif.ThicknessForShape -> ProjectxxxEntities.paramsrecipe_list.RecipeSubgroup "recipeGrouping￪￪,recipeGrouping￪￪" 
        ProjectxxxEntities.paramsquantif.MaximumValueForFoodOrGroup -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "facetDescriptor￪￪,facetDescriptor￪￪" 
        ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup -> ProjectxxxEntities.paramsfood_list.Food "foodOrRecipe￪" 
        ProjectxxxEntities.paramsquantif.StandardUnitForFoodOrRecipe -> ProjectxxxEntities.paramsfood_list.Food "foodOrRecipe￪" 
        ProjectxxxEntities.paramssetting.FoodConsumptionOccasionDisplayItem -> ProjectxxxEntities.paramssetting.FoodConsumptionOccasion "foodConsumptionOccasion￪" 
        ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification -> ProjectxxxEntities.paramsfood_list.Food "ingredientFoodOrRecipe￪" 
        ProjectxxxEntities.paramsinterview.Interviewer -> ProjectxxxEntities.paramsinterview.CountryInvolved "country￪" 
        ProjectxxxEntities.paramssetting.GroupSubstitution -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramsfood_coefficient.RawToCookedConversionFactorForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪" 
        ProjectxxxEntities.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪,fat￪" 
        ProjectxxxEntities.paramsfood_descript.FoodFacetRule -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "facetDescriptor￪￪,facetDescriptor￪￪" 
        ProjectxxxEntities.paramsfood_descript.FoodFacetRule -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramssetting.GroupSubstitution -> ProjectxxxEntities.paramsfood_list.FoodSubgroup "applyToFoodGroups￪￪,applyToFoodGroups￪￪,applyToFoodGroups￪￪" 
        ProjectxxxEntities.paramsfood_coefficient.EdiblePartCoefficientForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup -> ProjectxxxEntities.paramsquantif.Shape "photoOrShape￪" 
        ProjectxxxEntities.paramsfood_descript.FoodDescriptor -> ProjectxxxEntities.paramsfood_descript.FoodFacet "facet￪" 
        ProjectxxxEntities.paramssetting.FacetDescriptorThatCannotBeSubstituted -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "descriptor￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "physicalStateFacetDescriptor￪￪,physicalStateFacetDescriptor￪￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪" 
        ProjectxxxEntities.paramsrecipe_description.RecipeDescriptor -> ProjectxxxEntities.paramsrecipe_description.RecipeFacet "recipeFacet￪" 
        ProjectxxxEntities.paramsquantif.ThicknessForShape -> ProjectxxxEntities.paramsfood_list.FoodSubgroup "foodGrouping￪￪,foodGrouping￪￪,foodGrouping￪￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup -> ProjectxxxEntities.paramsquantif.Photo "photoOrShape￪" 
        ProjectxxxEntities.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "descriptor￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramsfood_descript.FoodFacetRule -> ProjectxxxEntities.paramsfood_descript.FoodFacet "facet￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipe -> ProjectxxxEntities.paramsrecipe_description.RecipeFacet "selectedRecipeFacet￪" 
        ProjectxxxEntities.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipe -> ProjectxxxEntities.paramsquantif.Shape "photoOrShape￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFoodGroup -> ProjectxxxEntities.paramsfood_descript.FoodFacet "foodFacet￪" 
        ProjectxxxEntities.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> ProjectxxxEntities.paramsrecipe_list.Recipe "recipe￪" 
        ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup -> ProjectxxxEntities.virtual.FCK "foodOrRecipeGroup￪,foodOrRecipeGroup￪,foodOrRecipeSubgroup￪,foodOrRecipeSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramsrecipe_list.RecipeIngredient -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "facetDescriptors￪￪,facetDescriptors￪￪" 
        ProjectxxxEntities.paramsrecipe_description.RecipeFacetRule -> ProjectxxxEntities.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        ProjectxxxEntities.paramsquantif.StandardPortionForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪" 
        ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForRecipe -> ProjectxxxEntities.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFoodGroup -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramsnutrient.NutrientValue -> ProjectxxxEntities.paramsnutrient.Nutrient "nutrient￪" 
        ProjectxxxEntities.paramsrecipe_list.RecipeIngredient -> ProjectxxxEntities.virtual.FCK "foodOrRecipeGroup￪,foodOrRecipeGroup￪,foodOrRecipeSubgroup￪,foodOrRecipeSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipeGroup -> ProjectxxxEntities.paramsquantif.Photo "photoOrShape￪" 
        ProjectxxxEntities.paramsinterview.SubjectToBeInterviewed -> ProjectxxxEntities.paramsinterview.CenterInvolved "center￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipeGroup -> ProjectxxxEntities.virtual.FCK "recipeGroup￪" 
        ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForFood -> ProjectxxxEntities.paramspathway.ProbingQuestion "probingQuestion￪" 
        ProjectxxxEntities.paramsrecipe_description.RecipeFacetRule -> ProjectxxxEntities.paramsfood_descript.FoodFacet "facetWhereTheRuleMustBeApplied" 
        ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪" 
        ProjectxxxEntities.paramsinterview.Interviewer -> ProjectxxxEntities.paramsinterview.CenterInvolved "center￪" 
        ProjectxxxEntities.paramsfood_list.FoodSubgroup -> ProjectxxxEntities.virtual.FCK "foodGroup￪" 
        ProjectxxxEntities.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪,fssGroup￪,fssSubgroup￪,fssSubSubgroup￪" 
        ProjectxxxEntities.paramsfood_descript.FoodBrand -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramsquantif.MaximumValueForRecipeOrGroup -> ProjectxxxEntities.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        ProjectxxxEntities.paramssupplement.DietarySupplement -> ProjectxxxEntities.paramssupplement.DietarySupplementFacet "facet￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipe -> ProjectxxxEntities.paramsrecipe_list.Recipe "recipe￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFoodGroup -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "foodDescriptor￪" 
        ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification -> ProjectxxxEntities.paramsrecipe_list.Recipe "recipe￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipeGroup -> ProjectxxxEntities.paramsrecipe_description.RecipeFacet "recipeFacet￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFood -> ProjectxxxEntities.paramsfood_descript.FoodFacet "selectedFoodFacet￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFood -> ProjectxxxEntities.paramsquantif.Shape "photoOrShape￪" 
        ProjectxxxEntities.paramsfood_list.ComposedRecipeIngredient -> ProjectxxxEntities.paramsfood_list.Food "foodOrRecipe￪" 
        ProjectxxxEntities.paramssupplement.DietarySupplementDescriptor -> ProjectxxxEntities.paramssupplement.DietarySupplementFacet "facet￪" 
        ProjectxxxEntities.paramsnutrient.NutrientValue -> ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup "nutrientForFoodOrGroup￪" 
        ProjectxxxEntities.paramsrecipe_list.Recipe -> ProjectxxxEntities.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        ProjectxxxEntities.paramssetting.FacetDescriptorThatCannotBeSubstituted -> ProjectxxxEntities.paramsfood_descript.FoodFacet "facet￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipeGroup -> ProjectxxxEntities.paramsquantif.Shape "photoOrShape￪" 
        ProjectxxxEntities.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood -> ProjectxxxEntities.virtual.FCK "fatGroup￪,fatSubgroup￪,fatSubSubgroup￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipeGroup -> ProjectxxxEntities.virtual.FCK "recipeGroup￪,recipeSubgroup￪" 
        ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification -> ProjectxxxEntities.paramsquantif.ThicknessForShape "shapeThickness￪" 
        ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForRecipe -> ProjectxxxEntities.paramsrecipe_list.Recipe "recipe￪" 
        ProjectxxxEntities.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> ProjectxxxEntities.paramsfood_list.Food "fss￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipe -> ProjectxxxEntities.paramsrecipe_list.Recipe "recipe￪" 
        ProjectxxxEntities.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪,fatGroup￪,fatSubgroup￪,fatSubSubgroup￪" 
        ProjectxxxEntities.paramsfood_coefficient.RawToCookedConversionFactorForFood -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "facetDescriptors￪￪,facetDescriptors￪￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFood -> ProjectxxxEntities.paramsquantif.Photo "photoOrShape￪" 
        ProjectxxxEntities.paramsquantif.MaximumValueForFoodOrGroup -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramssetting.GroupSubstitution -> ProjectxxxEntities.paramsrecipe_list.RecipeSubgroup "applyToRecipeGroups￪￪,applyToRecipeGroups￪￪" 
        ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup -> ProjectxxxEntities.paramsrecipe_list.Recipe "foodOrRecipe￪" 
        ProjectxxxEntities.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood -> ProjectxxxEntities.paramsfood_list.Food "food￪,fss￪" 
        ProjectxxxEntities.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe -> ProjectxxxEntities.virtual.FCK "recipeGroup￪,recipeSubgroup￪,fssGroup￪,fssSubgroup￪,fssSubSubgroup￪" 
        ProjectxxxEntities.paramsfood_list.ComposedRecipeIngredient -> ProjectxxxEntities.paramsrecipe_list.Recipe "foodOrRecipe￪" 
        ProjectxxxEntities.paramsquantif.StandardUnitForFoodOrRecipe -> ProjectxxxEntities.paramsrecipe_list.Recipe "foodOrRecipe￪" 
        ProjectxxxEntities.paramsfood_coefficient.DensityFactorForFoodOrRecipe -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "facetDescriptors￪￪,facetDescriptors￪￪" 
        ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification -> ProjectxxxEntities.paramsquantif.Shape "shape￪" 
        ProjectxxxEntities.paramsrecipe_list.RecipeIngredient -> ProjectxxxEntities.paramsfood_list.Food "foodOrRecipe￪" 
        ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipe -> ProjectxxxEntities.paramsquantif.Photo "photoOrShape￪" 
        ProjectxxxEntities.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> ProjectxxxEntities.paramsfood_list.Food "food￪" 
        ProjectxxxEntities.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "cookingMethodFacetDescriptor￪￪,cookingMethodFacetDescriptor￪￪" 
        ProjectxxxEntities.paramsfood_list.Food -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramsfood_coefficient.DensityFactorForFoodOrRecipe -> ProjectxxxEntities.paramsfood_list.Food "foodOrRecipe￪" 
        ProjectxxxEntities.paramssupplement.DietarySupplement -> ProjectxxxEntities.paramssupplement.DietarySupplementClassification "classification￪" 
        ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipeGroup -> ProjectxxxEntities.paramsrecipe_description.RecipeDescriptor "recipeDescriptor￪" 
        ProjectxxxEntities.paramsinterview.CenterInvolved -> ProjectxxxEntities.paramsinterview.CountryInvolved "attachedCountry￪" 
        ProjectxxxEntities.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor -> ProjectxxxEntities.paramsfood_descript.FoodFacet "facet￪" 
        ProjectxxxEntities.paramsquantif.MaximumValueForRecipeOrGroup -> ProjectxxxEntities.paramsrecipe_list.Recipe "recipe￪" 
        ProjectxxxEntities.paramsrecipe_list.RecipeSubgroup -> ProjectxxxEntities.virtual.FCK "recipeGroup￪" 
        ProjectxxxEntities.paramsfood_coefficient.EdiblePartCoefficientForFood -> ProjectxxxEntities.paramsfood_descript.FoodDescriptor "facetDescriptor￪￪,facetDescriptor￪￪" 
        ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForFood -> ProjectxxxEntities.virtual.FCK "foodGroup￪,foodSubgroup￪,foodSubSubgroup￪" 
        ProjectxxxEntities.paramsfood_coefficient.DensityFactorForFoodOrRecipe -> ProjectxxxEntities.paramsrecipe_list.Recipe "foodOrRecipe￪" 
    }

    views {
        systemContext ProjectxxxEntities "SystemContext" "An example of a System Context diagram." {
            include ProjectxxxEntities 
        }

        container ProjectxxxEntities "a0" "a1" {
            include ProjectxxxEntities.paramsfood_list 
            include ProjectxxxEntities.paramsinterview 
            include ProjectxxxEntities.paramsfood_coefficient 
            include ProjectxxxEntities.paramsnutrient 
            include ProjectxxxEntities.paramspathway 
            include ProjectxxxEntities.paramsquantif 
            include ProjectxxxEntities.paramsrecipe_coefficient 
            include ProjectxxxEntities.paramsrecipe_description 
            include ProjectxxxEntities.paramsrecipe_list 
            include ProjectxxxEntities.paramssetting 
            include ProjectxxxEntities.paramssupplement 
            include ProjectxxxEntities.virtual 
            include ProjectxxxEntities.paramsfood_descript 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsfood_coefficient "params0food_coefficient" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodDescriptor 
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include ProjectxxxEntities.paramsfood_coefficient.EdiblePartCoefficientForFood 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include ProjectxxxEntities.virtual.FCK 
            include ProjectxxxEntities.paramsfood_coefficient.RawToCookedConversionFactorForFood 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsfood_descript "params0food_descript" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodBrand 
            include ProjectxxxEntities.paramsfood_descript.FoodDescriptor 
            include ProjectxxxEntities.paramsfood_descript.FoodFacet 
            include ProjectxxxEntities.paramsfood_descript.FoodFacetRule 
            include ProjectxxxEntities.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor 
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFood 
            include ProjectxxxEntities.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFoodGroup 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include ProjectxxxEntities.paramsfood_coefficient.EdiblePartCoefficientForFood 
            include ProjectxxxEntities.paramsquantif.MaximumValueForFoodOrGroup 
            include ProjectxxxEntities.paramsrecipe_description.RecipeFacetRule 
            include ProjectxxxEntities.paramsrecipe_list.RecipeIngredient 
            include ProjectxxxEntities.paramssetting.FacetDescriptorThatCannotBeSubstituted 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include ProjectxxxEntities.virtual.FCK 
            include ProjectxxxEntities.paramsfood_coefficient.RawToCookedConversionFactorForFood 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsfood_list "params0food_list" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor 
            include ProjectxxxEntities.paramsfood_list.ComposedRecipeIngredient 
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramsfood_list.FoodSubgroup 
            include ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFood 
            include ProjectxxxEntities.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForFood 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFood 
            include ProjectxxxEntities.paramsfood_coefficient.EdiblePartCoefficientForFood 
            include ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification 
            include ProjectxxxEntities.paramsquantif.StandardPortionForFood 
            include ProjectxxxEntities.paramsquantif.StandardUnitForFoodOrRecipe 
            include ProjectxxxEntities.paramsquantif.ThicknessForShape 
            include ProjectxxxEntities.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.paramsrecipe_list.RecipeIngredient 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood 
            include ProjectxxxEntities.paramssetting.GroupSubstitution 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include ProjectxxxEntities.virtual.FCK 
            include ProjectxxxEntities.paramsfood_coefficient.RawToCookedConversionFactorForFood 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsinterview "params0interview" "no desc" {
            include ProjectxxxEntities.paramsinterview.CenterInvolved 
            include ProjectxxxEntities.paramsinterview.CountryInvolved 
            include ProjectxxxEntities.paramsinterview.Interviewer 
            include ProjectxxxEntities.paramsinterview.SubjectToBeInterviewed 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsnutrient "params0nutrient" "no desc" {
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramsnutrient.Nutrient 
            include ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup 
            include ProjectxxxEntities.paramsnutrient.NutrientValue 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.virtual.FCK 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramspathway "params0pathway" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodDescriptor 
            include ProjectxxxEntities.paramsfood_descript.FoodFacet 
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFood 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFoodGroup 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipeGroup 
            include ProjectxxxEntities.paramspathway.ProbingQuestion 
            include ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForFood 
            include ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFood 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipeGroup 
            include ProjectxxxEntities.paramsquantif.Photo 
            include ProjectxxxEntities.paramsquantif.Shape 
            include ProjectxxxEntities.paramsrecipe_description.RecipeDescriptor 
            include ProjectxxxEntities.paramsrecipe_description.RecipeFacet 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.virtual.FCK 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsquantif "params0quantif" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodDescriptor 
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramsfood_list.FoodSubgroup 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFood 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipeGroup 
            include ProjectxxxEntities.paramsquantif.MaximumValueForFoodOrGroup 
            include ProjectxxxEntities.paramsquantif.MaximumValueForRecipeOrGroup 
            include ProjectxxxEntities.paramsquantif.Photo 
            include ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification 
            include ProjectxxxEntities.paramsquantif.Shape 
            include ProjectxxxEntities.paramsquantif.StandardPortionForFood 
            include ProjectxxxEntities.paramsquantif.StandardUnitForFoodOrRecipe 
            include ProjectxxxEntities.paramsquantif.ThicknessForShape 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.paramsrecipe_list.RecipeSubgroup 
            include ProjectxxxEntities.virtual.FCK 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsrecipe_coefficient "params0recipe_coefficient" "no desc" {
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.virtual.FCK 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsrecipe_description "params0recipe_description" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodFacet 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipeGroup 
            include ProjectxxxEntities.paramsrecipe_description.RecipeBrand 
            include ProjectxxxEntities.paramsrecipe_description.RecipeDescriptor 
            include ProjectxxxEntities.paramsrecipe_description.RecipeFacet 
            include ProjectxxxEntities.paramsrecipe_description.RecipeFacetRule 
            include ProjectxxxEntities.virtual.FCK 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramsrecipe_list "params0recipe_list" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodDescriptor 
            include ProjectxxxEntities.paramsfood_list.ComposedRecipeIngredient 
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup 
            include ProjectxxxEntities.paramsfood_coefficient.DensityFactorForFoodOrRecipe 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipe 
            include ProjectxxxEntities.paramsquantif.MaximumValueForRecipeOrGroup 
            include ProjectxxxEntities.paramsquantif.RecipeIngredientQuantification 
            include ProjectxxxEntities.paramsquantif.StandardUnitForFoodOrRecipe 
            include ProjectxxxEntities.paramsquantif.ThicknessForShape 
            include ProjectxxxEntities.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.paramsrecipe_list.RecipeIngredient 
            include ProjectxxxEntities.paramsrecipe_list.RecipeSubgroup 
            include ProjectxxxEntities.paramssetting.GroupSubstitution 
            include ProjectxxxEntities.virtual.FCK 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramssetting "params0setting" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodDescriptor 
            include ProjectxxxEntities.paramsfood_descript.FoodFacet 
            include ProjectxxxEntities.paramsfood_list.FoodSubgroup 
            include ProjectxxxEntities.paramsrecipe_list.RecipeSubgroup 
            include ProjectxxxEntities.paramssetting.FacetDescriptorThatCannotBeSubstituted 
            include ProjectxxxEntities.paramssetting.FoodConsumptionOccasion 
            include ProjectxxxEntities.paramssetting.FoodConsumptionOccasionDisplayItem 
            include ProjectxxxEntities.paramssetting.GroupSubstitution 
            include ProjectxxxEntities.virtual.FCK 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.paramssupplement "params0supplement" "no desc" {
            include ProjectxxxEntities.paramssupplement.DietarySupplement 
            include ProjectxxxEntities.paramssupplement.DietarySupplementClassification 
            include ProjectxxxEntities.paramssupplement.DietarySupplementDescriptor 
            include ProjectxxxEntities.paramssupplement.DietarySupplementFacet 
            autolayout tb 300 300 
        }

        component ProjectxxxEntities.virtual "virtual" "no desc" {
            include ProjectxxxEntities.paramsfood_descript.FoodBrand 
            include ProjectxxxEntities.paramsfood_descript.FoodFacetRule 
            include ProjectxxxEntities.paramsfood_descript.ImprobableSequenceOfFacetAndDescriptor 
            include ProjectxxxEntities.paramsfood_list.Food 
            include ProjectxxxEntities.paramsfood_list.FoodSubgroup 
            include ProjectxxxEntities.paramsnutrient.NutrientForFoodOrGroup 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForFoodGroup 
            include ProjectxxxEntities.paramspathway.FacetDescriptorPathwayForRecipeGroup 
            include ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForFood 
            include ProjectxxxEntities.paramspathway.ProbingQuestionPathwayForRecipe 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForFoodGroup 
            include ProjectxxxEntities.paramspathway.QuantificationMethodPathwayForRecipeGroup 
            include ProjectxxxEntities.paramsquantif.MaximumValueForFoodOrGroup 
            include ProjectxxxEntities.paramsquantif.MaximumValueForRecipeOrGroup 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatLeftInTheDishForFood 
            include ProjectxxxEntities.paramsrecipe_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipe 
            include ProjectxxxEntities.paramsrecipe_description.RecipeBrand 
            include ProjectxxxEntities.paramsrecipe_description.RecipeFacetRule 
            include ProjectxxxEntities.paramsrecipe_list.Recipe 
            include ProjectxxxEntities.paramsrecipe_list.RecipeIngredient 
            include ProjectxxxEntities.paramsrecipe_list.RecipeSubgroup 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood 
            include ProjectxxxEntities.paramssetting.GroupSubstitution 
            include ProjectxxxEntities.paramsfood_coefficient.PercentOfFatUseDuringCookingForFood 
            include ProjectxxxEntities.virtual.FCK 
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

