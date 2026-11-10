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
package dita.globodiet.survey.dom;

import java.util.function.UnaryOperator;

import org.apache.causeway.commons.collections.Can;

import lombok.extern.slf4j.Slf4j;

import dita.commons.food.composition.FoodCompositionRepository;
import dita.commons.sid.dmap.DirectMap;
import dita.commons.sid.qmap.QualifiedMap;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.globodiet.survey.util.AssociatedRecipeResolver;
import dita.globodiet.survey.util.DuplicateIngredientMerger;
import dita.globodiet.survey.util.FoodGroupReplacerBasedOnFDM;
import dita.globodiet.survey.util.FryingFatCollector;
import dita.globodiet.survey.util.FryingFatDeduplicator;
import dita.globodiet.survey.util.IngredientToRecipeResolver;
import dita.globodiet.survey.util.QualifiedMappingResolver;
import dita.recall24.dto.InterviewSet24;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Slf4j
public record ReportContext(
    SurveyConfig surveyConfig,
    FoodCompositionRepository foodCompositionRepository,
    FoodDescriptionModel foodDescriptionModel,
    DirectMap specialDayMapping,
    DirectMap specialDietMapping,
    DirectMap fcoMapping,
    DirectMap pocMapping,
    QualifiedMap nutMapping,
    InterviewSet24 interviewSet) {

    public static ReportContext empty() {
        return new ReportContext(
            SurveyConfig.empty(),
            FoodCompositionRepository.empty(), FoodDescriptionModel.empty(),
            DirectMap.empty(), DirectMap.empty(), DirectMap.empty(),
            DirectMap.empty(), QualifiedMap.empty(),
            InterviewSet24.empty());
    }

    public static ReportContextFactory factory(
            final BlobStore surveyBlobStore,
            final Can<Campaign.SecondaryKey> campaignKeys) {
        return new ReportContextFactory(surveyBlobStore, campaignKeys);
    }

    /**
     * Say there exists a Nutrient Mapping for a Food that has an associated Recipe,
     * then {@link AssociatedRecipeResolverTL}/{@link IngredientToRecipeResolver} have precedence over
     * {@link QualifiedMappingResolver}, that is, for each level of 'recipe nesting' we first
     * replace food by their associated recipe and only then apply nutrient mapping.
     *
     * <p>This design should allow for Food always to be resolvable to Nutrients,
     * provided all required Nutrient Mappings are made available.
     */
    public ReportContext defaultTransform() {
        var fryingFatCollector = new FryingFatCollector();

        return transform(in -> in.isEmpty() ? in : in
            // 1. Resolve all ingredients -> recipe associations (name suffixes)
            .transform(new AssociatedRecipeResolver(foodDescriptionModel()))
            // 2. Apply nutrient mappings, that is, adds mapping targets
            // as FCDB annotation. Targets are BLS and other FCDBs (or Recipes).
            // Then resolve ingredients that were mapped to recipes via FCDB annotation
            .transform(new QualifiedMappingResolver(nutMapping()))
            .transform(new IngredientToRecipeResolver(foodDescriptionModel()))
            // 3. Resolve all ingredients -> recipe associations (name suffixes)
            .transform(new AssociatedRecipeResolver(foodDescriptionModel()))

            // 4. Re-apply mappings for newly resolved recipe compositions
            .transform(new QualifiedMappingResolver(nutMapping()))
            .transform(new IngredientToRecipeResolver(foodDescriptionModel()))
            // 5. Final pass: resolve all ingredients -> recipe associations (name suffixes)
            .transform(new AssociatedRecipeResolver(foodDescriptionModel()))

            // 6. Post-processing & deduplication
            .transform(fryingFatCollector)
            .transform(new FryingFatDeduplicator(fryingFatCollector.fryingFatHandlersAsMap()))
            .transform(new QualifiedMappingResolver(nutMapping()))
            .transform(new FoodGroupReplacerBasedOnFDM(foodDescriptionModel()))
            .transform(new DuplicateIngredientMerger())
        );
    }


    public ReportContext transform(final UnaryOperator<InterviewSet24> interviewTransformer) {
        return new ReportContext(surveyConfig, foodCompositionRepository, foodDescriptionModel,
            specialDayMapping, specialDietMapping, fcoMapping, pocMapping, nutMapping,
            interviewTransformer.apply(interviewSet));
    }

    public boolean isEmpty() {
        return interviewSet.isEmpty();
    }

}
