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

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.collections._Multimaps;

import lombok.val;
import lombok.experimental.UtilityClass;

import dita.commons.types.IntRef;
import dita.commons.types.Sex;
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
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
public class Recall24ModelUtils {

    // -- DATA JOINING

    public InterviewSet24 join(final Iterable<Interview24> iterable) {

        record Helper(
                String alias,
                LocalDate dateOfBirth,
                Sex sex) {
            static Helper helper(final Interview24 interview) {
                var respondent = interview.parentRespondent();
                return new Helper(respondent.alias(), respondent.dateOfBirth(), respondent.sex());
            }
            Respondent24 createRespondent(final Can<Interview24> interviews) {
                var respondent = new Respondent24(alias, dateOfBirth, sex, interviews);
                interviews.forEach(iv->{
                    _Assert.assertEquals(alias, iv.parentRespondent().alias());
                    if(!Objects.equals(dateOfBirth, iv.parentRespondent().dateOfBirth())) {
                        System.err.printf("dateOfBirth mismatch joining data for alias %s%n", alias);
                    }
                    if(!Objects.equals(sex, iv.parentRespondent().sex())) {
                        System.err.printf("sex mismatch joining data for alias %s%n", alias);
                    }
                    iv.parentRespondentRef().setValue(respondent);
                });
                return respondent;
            }
        }

        var interviewsByRespondentAlias = _Multimaps.<String, Interview24>newListMultimap();
        iterable.forEach(interview->
            interviewsByRespondentAlias
                    .putElement(interview.parentRespondent().alias(), interview));

        final Can<Respondent24> respondents = interviewsByRespondentAlias.entrySet()
            .stream()
            .map(entry->{
                var interviews = entry.getValue();
                var helper = Helper.helper(interviews.get(0));
                var respondent = helper.createRespondent(Can.ofCollection(interviews));
                return respondent;
            })
            .collect(Can.toCan());

        return InterviewSet24.of(respondents).normalized();
    }

    // -- CONVERSIONS

    public InterviewSet24 fromDto(final InterviewSetDto interviewSet) {
        val respondents = _NullSafe.stream(interviewSet.getRespondents())
            .map(dto->fromDto(dto))
            .collect(Can.toCan());
        return InterviewSet24.of(respondents);
    }

    public InterviewSetDto toDto(
            final InterviewSet24 model) {
        val dto = new InterviewSetDto();
        dto.setRespondents(
            model.respondents()
                .stream()
                .map(Recall24ModelUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    // -- HELPER

    // -- DTO TO MODEL

    private Respondent24 fromDto(final RespondentDto dto) {
        var interviews = _NullSafe.stream(dto.getInterviews())
            .map(Recall24ModelUtils::fromDto)
            .collect(Can.toCan());
        var respondent = new Respondent24(dto.getAlias(), dto.getDateOfBirth(), dto.getSex(), interviews);
        interviews.forEach(iv->iv.parentRespondentRef().setValue(respondent));
        return respondent;
    }

    private Interview24 fromDto(final InterviewDto dto) {
        val meals = _NullSafe.stream(dto.getMeals())
                .map(meal->fromDto(meal))
                .collect(Can.toCan());
        val interview = new Interview24(ObjectRef.empty(),
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
                dto.getName(),
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
            dto.setSex(model.sex());
            dto.setInterviews(model.interviews()
                    .stream()
                    .map(Recall24ModelUtils::toDto)
                    .collect(Collectors.toList()));
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
