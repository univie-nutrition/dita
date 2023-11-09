/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.globodiet.manager.editing;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.commons.services.idgen.IdGeneratorService;
import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.Food.DietarySupplementQ;
import dita.globodiet.dom.params.food_list.Food.GroupOrdinal;
import dita.globodiet.dom.params.food_list.Food.TypeOfItem;
import dita.globodiet.dom.params.food_list.FoodDeps;
import dita.globodiet.dom.params.food_list.Food_foodGroup;
import dita.globodiet.dom.params.food_list.Food_foodSubSubgroup;
import dita.globodiet.dom.params.food_list.Food_foodSubgroup;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion;
import dita.globodiet.manager.util.FoodUtils;
import lombok.RequiredArgsConstructor;

@Action
@ActionLayout(
        fieldSetId="details",
        position = Position.PANEL,
        describedAs = "Clone (create a copy of) this Food")
@RequiredArgsConstructor
public class Food_clone {

    @Inject BlobStore blobStore;
    @Inject RepositoryService repositoryService;
    @Inject FactoryService factoryService;
    @Inject IdGeneratorService idGeneratorService;
    @Inject ForeignKeyLookupService foreignKeyLookupService;

    protected final Food mixee;

    public Food act(@ParameterTuple final Food.Params p) {
        var clone = factoryService.detachedEntity(new Food());

        clone.setCode(p.code());
        Optional.ofNullable(p.foodGroup())
            .map(FoodGroup::getCode)
            .ifPresent(clone::setFoodGroupCode);
        Optional.ofNullable(p.foodSubgroup())
            .map(FoodSubgroup::getFoodSubgroupCode)
            .ifPresent(clone::setFoodSubgroupCode);
        Optional.ofNullable(p.foodSubSubgroup())
            .map(FoodSubgroup::getFoodSubSubgroupCode)
            .ifPresent(clone::setFoodSubSubgroupCode);

        clone.setFoodNativeName(p.foodNativeName());
        clone.setTypeOfItem(p.typeOfItem());
        clone.setGroupOrdinal(p.groupOrdinal());
        clone.setDietarySupplementQ(p.dietarySupplementQ());

        repositoryService.persist(clone);
        foreignKeyLookupService.clearCache(clone.getClass());

//      for(var foodDependantMixinClass : FoodDeps.mixinClasses()) {
//      var foodDependantMixin = factoryService.mixin(foodDependantMixinClass, mixee);
//      var entityObjectsToBeCloned = (List<Object>) foodDependantMixinClass.getMethod("coll").invoke(foodDependantMixin);
//      }


        factoryService.mixin(FoodDeps.Food_dependentDensityFactorForFoodMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodOrRecipeCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentEdiblePartCoefficientForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForFoodMappedByFssFat.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatUseDuringCookingForFoodMappedByFat.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentRawToCookedConversionFactorForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentExceptionForSomeFoodToTheFacetDescriptorPathwayMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentImprobableSequenceOfFacetAndDescriptorMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentComposedRecipeIngredientMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodOrRecipeCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentProbingQuestionPathwayForFoodsMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentQuantificationMethodsPathwayForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentStandardPortionForFoodMappedByFood.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentItemDefinitionMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodOrRecipeCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentStandardUnitForFoodOrRecipeMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodOrRecipeCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentPercentOfFatOrSauceOrSweetenerAddedAfterCookingForRecipeMappedByFssFat.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFssFatCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentRecipeIngredientMappedByFoodOrRecipe.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setFoodOrRecipeCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });
        factoryService.mixin(FoodDeps.Food_dependentRecipeIngredientQuantificationMappedByIngredientFoodOrRecipe.class, mixee)
        .coll()
        .forEach(origin->{
            var clonedDependant = origin.copy();
            clonedDependant.setIngredientFoodOrRecipeCode(clone.getCode());
            repositoryService.persist(clonedDependant);
            foreignKeyLookupService.clearCache(clonedDependant.getClass());
        });

        return clone;
    }

    // -- DEFAULTS

    @MemberSupport public String defaultCode(final Food.Params p) {
        return idGeneratorService.nextElseFail(String.class, Food.class);
    }
    @MemberSupport public FoodGroup defaultFoodGroup(final Food.Params p) {
        return factoryService.mixin(Food_foodGroup.class, mixee).prop();
    }
    @MemberSupport public FoodSubgroup defaultFoodSubgroup(final Food.Params p) {
        return factoryService.mixin(Food_foodSubgroup.class, mixee).prop();
    }
    @MemberSupport public FoodSubgroup defaultFoodSubSubgroup(final Food.Params p) {
        return factoryService.mixin(Food_foodSubSubgroup.class, mixee).prop();
    }
    @MemberSupport public String defaultFoodNativeName(final Food.Params p) {
        return mixee.getFoodNativeName() + " (Clone)";
    }
    @MemberSupport public TypeOfItem defaultTypeOfItem(final Food.Params p) {
        return mixee.getTypeOfItem();
    }
    @MemberSupport public GroupOrdinal defaultGroupOrdinal(final Food.Params p) {
        return mixee.getGroupOrdinal();
    }
    @MemberSupport public DietarySupplementQ defaultDietarySupplementQ(final Food.Params p) {
        return mixee.getDietarySupplementQ();
    }

    // -- CHOICES

    @MemberSupport public List<FoodGroup> choicesFoodGroup(final Food.Params p) {
        return repositoryService.allInstances(FoodGroup.class);
    }
    @MemberSupport public List<FoodSubgroup> choicesFoodSubgroup(final Food.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()==null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode()));
    }
    @MemberSupport public List<FoodSubgroup> choicesFoodSubSubgroup(final Food.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()!=null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode())
                    && Objects.equals(fg.getFoodSubgroupCode(), p.foodSubgroup().getFoodSubgroupCode()));
    }

    // -- VALIDATION

    /**
     * Guard against proposed food.code() already in use.
     */
    @MemberSupport public String validateAct(final Food.Params p) {
        var secKey = new Food.SecondaryKey(p.code());
        var alreadyExisting = foreignKeyLookupService.nullable(secKey);
        return alreadyExisting!=null
                ? String.format("Code '%s' is already used as an identifier for another Food (%s).", p.code(), alreadyExisting)
                : null;
    }

    /**
     * Guard against ill formatted food code.
     */
    @MemberSupport public String validateCode(final Food.Params p) {
        return FoodUtils.validateFoodCode(p.code());
    }

    // -- ENABLING

    @MemberSupport public String disableAct() {
        //TODO refactor into util
        return (new HasCurrentlyCheckedOutVersion() {}).guardAgainstCannotEditVersion(blobStore)
                .orElse(null);
    }

}
