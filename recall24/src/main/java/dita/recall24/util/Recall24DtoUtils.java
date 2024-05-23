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

import java.util.stream.Collectors;

import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.DataSource;

import lombok.val;
import lombok.experimental.UtilityClass;

import dita.commons.types.IntRef;
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
public class Recall24DtoUtils {

    // -- SURVEY IO

    public Try<InterviewSetDto> tryReadSurvey(final DataSource dataSource) {
        return new _JaxbReader()
                .readFromXml(dataSource);
    }

    public Try<Void> tryWriteSurvey(final InterviewSetDto interviewSet, final DataSink dataSink) {
        return new _JaxbWriter()
                .tryWriteTo(interviewSet, dataSink);
    }

    public Try<Blob> tryZip(final String zipEntryName, final InterviewSetDto interviewSet) {
        return new _JaxbWriter()
                .tryToString(interviewSet)
                .mapEmptyToFailure()
                .mapSuccessAsNullable(xml->
                    Clob.of(zipEntryName, CommonMimeType.XML, xml)
                    .toBlobUtf8()
                    .zip());
    }

    public Try<InterviewSetDto> tryUnzip(final Blob blob) {
        return tryReadSurvey(blob
                .unZip(CommonMimeType.XML)
                .asDataSource());
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
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    // -- UPDATE DTO FROM MODEL FIELDS

    RespondentDto updateDtoFromModelFields(
            final RespondentDto dto,
            final Respondent24 model) {
        dto.setAlias(model.alias());
        dto.setDateOfBirth(model.dateOfBirth());
        dto.setSex(model.sex());
        return dto;
    }

    InterviewDto updateDtoFromModelFields(
            final InterviewDto dto,
            final Interview24 model) {
        dto.setRespondentAlias(model.respondentAlias());
        dto.setInterviewOrdinal(model.interviewOrdinal());
        dto.setInterviewDate(model.interviewDate());
        dto.setRespondentMetaData(toDto(model.respondentMetaData()));
        return dto;
    }

    RespondentMetaDataDto updateDtoFromModelFields(
            final RespondentMetaDataDto dto,
            final RespondentMetaData24 model) {
        dto.setSpecialDietId(model.specialDietId());
        dto.setSpecialDayId(model.specialDayId());
        dto.setHeightCM(model.heightCM());
        dto.setWeightKG(model.weightKG());
        return dto;
    }

    MealDto updateDtoFromModelFields(
            final MealDto dto,
            final Meal24 model) {
        dto.setHourOfDay(model.hourOfDay());
        dto.setFoodConsumptionOccasionId(model.foodConsumptionOccasionId());
        dto.setFoodConsumptionPlaceId(model.foodConsumptionPlaceId());
        return dto;
    }

    MemorizedFoodDto updateDtoFromModelFields(
            final MemorizedFoodDto dto,
            final MemorizedFood24 model) {
        dto.setName(model.name());
        return dto;
    }

    RecordDto updateDtoFromModelFields(
            final RecordDto dto,
            final Record24 model) {
        dto.setType(model.type());
        dto.setName(model.name());
        dto.setFacetSids(model.facetSids());
        return dto;
    }

    IngredientDto updateDtoFromModelFields(
            final IngredientDto dto,
            final Ingredient24 model) {
        dto.setSid(model.sid());
        dto.setFacetSids(model.facetSids());
        dto.setRawPerCookedRatio(model.rawPerCookedRatio());
        dto.setQuantityCooked(model.quantityCooked());
        return dto;
    }

    // -- HELPER

    // -- DTO TO MODEL

    private Respondent24 fromDto(final RespondentDto dto) {
        var interviews = _NullSafe.stream(dto.getInterviews())
            .map(Recall24DtoUtils::fromDto)
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
        val topLevelRecords = _NullSafe.stream(dto.getTopLevelRecords())
                .map(record->fromDto(record))
                .collect(Can.toCan());
        val memorizedFood = new MemorizedFood24(ObjectRef.empty(),
                dto.getName(),
                topLevelRecords);
        topLevelRecords.forEach(r->r.parentMemorizedFoodRef().setValue(memorizedFood));
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
        val dto = updateDtoFromModelFields(new RespondentDto(), model);
        dto.setInterviews(model.interviews()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private InterviewDto toDto(
            final Interview24 model) {
        val dto = updateDtoFromModelFields(new InterviewDto(), model);
        dto.setMeals(model.meals()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private RespondentMetaDataDto toDto(
            final RespondentMetaData24 model) {
        val dto = updateDtoFromModelFields(new RespondentMetaDataDto(), model);
        return dto;
    }

    private MealDto toDto(
            final Meal24 model) {
        val dto = updateDtoFromModelFields(new MealDto(), model);
        dto.setMemorizedFood(model.memorizedFood()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private MemorizedFoodDto toDto(
            final MemorizedFood24 model) {
        val dto = updateDtoFromModelFields(new MemorizedFoodDto(), model);
        dto.setTopLevelRecords(model.topLevelRecords()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private RecordDto toDto(
            final Record24 model) {
        val dto = updateDtoFromModelFields(new RecordDto(), model);
        dto.setIngredients(model.ingredients()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private IngredientDto toDto(
            final Ingredient24 model) {
        val dto = updateDtoFromModelFields(new IngredientDto(), model);
        return dto;
    }

}
