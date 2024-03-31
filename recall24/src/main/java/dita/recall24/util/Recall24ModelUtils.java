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
package dita.recall24.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;

import dita.commons.types.IntRef;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;
import dita.recall24.dto.IngredientDto;
import dita.recall24.dto.InterviewDto;
import dita.recall24.dto.InterviewSetDto;
import dita.recall24.dto.MealDto;
import dita.recall24.dto.MemorizedFoodDto;
import dita.recall24.dto.RecordDto;
import dita.recall24.dto.RespondentDto;
import dita.recall24.dto.RespondentMetaDataDto;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.Interview24;
import dita.recall24.model.InterviewSet24;
import dita.recall24.model.Meal24;
import dita.recall24.model.MemorizedFood24;
import dita.recall24.model.Record24;
import dita.recall24.model.Respondent24;
import dita.recall24.model.RespondentMetaData24;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Recall24ModelUtils {

    // -- CONVERIONS

    public InterviewSet24 fromDto(final InterviewSetDto interviewSet) {

        val respondents = _NullSafe.stream(interviewSet.getRespondents())
            .map(dto->fromDto(dto))
            .collect(Can.toCan());

        val cc = new ConversionContext(respondents, interviewSet.getRespondents());

        val interviews = _NullSafe.stream(interviewSet.getInterviews())
                .map(dto->fromDto(dto, cc))
                .collect(Can.toCan());

        return InterviewSet24.of(respondents, interviews);
    }

    public InterviewSetDto toDto(
            final InterviewSet24 model) {
        val dto = new InterviewSetDto();
        dto.setRespondents(
            model.respondents()
                .stream()
                .map(Recall24ModelUtils::toDto)
                .collect(Collectors.toList()));
        dto.setInterviews(
                model.interviews()
                    .stream()
                    .map(Recall24ModelUtils::toDto)
                    .collect(Collectors.toList()));
        return dto;
    }

    // -- HELPER

    private static class ConversionContext {

        private final Map<String, Respondent24> respondent24ByAlias = new HashMap<>();

        public ConversionContext(final Iterable<Respondent24> respondents24, final Iterable<RespondentDto> respondents) {
            _NullSafe.stream(respondents24)
                .forEach(r->respondent24ByAlias.put(r.alias(), r));
        }

        Respondent24 respondent24ByAliasElseFail(final @NonNull String alias) {
            return Objects.requireNonNull(respondent24ByAlias.get(alias),
                    ()->String.format("failed to lookup Respondent24 by alias '%s'", alias));
        }

    }

    // -- DTO TO MODEL

    private Respondent24 fromDto(
            final RespondentDto dto) {
        return new Respondent24(dto.getAlias(), dto.getDateOfBirth(), dto.getSex());
    }

    private Interview24 fromDto(
            final InterviewDto dto, final ConversionContext cc) {
        val meals = _NullSafe.stream(dto.getMeals())
                .map(meal->fromDto(meal))
                .collect(Can.toCan());
        val interview = new Interview24(cc.respondent24ByAliasElseFail(dto.getRespondentAlias()),
                dto.getInterviewDate(),
                IntRef.of(dto.getInterviewOrdinal()),
                fromDto(dto.getRespondentMetaData()),
                meals);
        interview.respondentMetaData().parentInterviewRef().setValue(interview);
        meals.forEach(meal->meal.parentInterviewRef().setValue(interview));
        return interview;
    }

    private RespondentMetaData24 fromDto(final RespondentMetaDataDto dto) {
        val respondentMetaData = new RespondentMetaData24(ObjectRef.empty(),
                dto.getSpecialDietId(),
                dto.getSpecialDayId(),
                dto.getHeightCM(),
                dto.getWeightKG());
        return respondentMetaData;
    }

    private Meal24 fromDto(final MealDto dto) {
        val memorizedFood = _NullSafe.stream(dto.getMemorizedFood())
                .map(mf->fromDto(mf))
                .collect(Can.toCan());
        val meal = new Meal24(ObjectRef.empty(),
                dto.getHourOfDay(),
                dto.getFoodConsumptionOccasionId(),
                dto.getFoodConsumptionPlaceId(),
                memorizedFood);
        memorizedFood.forEach(mf->mf.parentMealRef().setValue(meal));
        return meal;
    }

    private MemorizedFood24 fromDto(final MemorizedFoodDto dto) {
        val records = _NullSafe.stream(dto.getRecords())
                .map(record->fromDto(record))
                .collect(Can.toCan());
        val memorizedFood = new MemorizedFood24(ObjectRef.empty(),
                dto.getName(),
                records);
        records.forEach(r->r.parentMemorizedFoodRef().setValue(memorizedFood));
        return memorizedFood;
    }

    private Record24 fromDto(final RecordDto dto) {
        val ingredients = _NullSafe.stream(dto.getIngredients())
                .map(ingr->fromDto(ingr))
                .collect(Can.toCan());
        val record24 = new Record24(ObjectRef.empty(),
                dto.getType(),
                dto.getName(),
                dto.getFacetSids(),
                ingredients);
        ingredients.forEach(i->i.parentRecordRef().setValue(record24));
        return record24;
    }

    private Ingredient24 fromDto(final IngredientDto dto) {
        val ingredient = new Ingredient24(ObjectRef.empty(),
                dto.getSid(),
                dto.getFacetSids(),
                dto.getRawPerCookedRatio(),
                dto.getQuantityCooked());
        return ingredient;
    }

    // -- MODEL TO DTO

    private RespondentDto toDto(
            final Respondent24 model) {
        val dto = new RespondentDto();
            dto.setAlias(model.alias());
            dto.setDateOfBirth(model.dateOfBirth());
            dto.setSex(model.gender());
        return dto;
    }

    private InterviewDto toDto(
            final Interview24 model) {
        val dto = new InterviewDto();
        dto.setRespondentAlias(model.respondentAlias());
        dto.setInterviewOrdinal(model.interviewOrdinal());
        dto.setInterviewDate(model.interviewDate());
        dto.setRespondentMetaData(toDto(model.respondentMetaData()));
        dto.setMeals(model.meals()
                .stream()
                .map(Recall24ModelUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private RespondentMetaDataDto toDto(
            final RespondentMetaData24 model) {
        val dto = new RespondentMetaDataDto();
        dto.setSpecialDietId(model.specialDietId());
        dto.setSpecialDayId(model.specialDayId());
        dto.setHeightCM(model.heightCM());
        dto.setWeightKG(model.weightKG());
        return dto;
    }

    private MealDto toDto(
            final Meal24 model) {
        val dto = new MealDto();
        dto.setHourOfDay(model.hourOfDay());
        dto.setFoodConsumptionOccasionId(model.foodConsumptionOccasionId());
        dto.setFoodConsumptionPlaceId(model.foodConsumptionPlaceId());
        dto.setMemorizedFood(model.memorizedFood()
                .stream()
                .map(Recall24ModelUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private MemorizedFoodDto toDto(
            final MemorizedFood24 model) {
        val dto = new MemorizedFoodDto();
        dto.setName(model.name());
        dto.setRecords(model.records()
                .stream()
                .map(Recall24ModelUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private RecordDto toDto(
            final Record24 model) {
        val dto = new RecordDto();
        dto.setType(model.type());
        dto.setName(model.name());
        dto.setFacetSids(model.facetSids());
        dto.setIngredients(model.ingredients()
                .stream()
                .map(Recall24ModelUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private IngredientDto toDto(
            final Ingredient24 model) {
        val dto = new IngredientDto();
        dto.setSid(model.sid());
        dto.setFacetSids(model.facetSids());
        dto.setRawPerCookedRatio(model.rawPerCookedRatio());
        dto.setQuantityCooked(model.quantityCooked());
        return dto;
    }

}
