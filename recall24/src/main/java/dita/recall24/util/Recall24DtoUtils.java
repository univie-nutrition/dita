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
import dita.recall24.immutable.Ingredient;
import dita.recall24.immutable.Interview;
import dita.recall24.immutable.InterviewSet;
import dita.recall24.immutable.Meal;
import dita.recall24.immutable.MemorizedFood;
import dita.recall24.immutable.Record;
import dita.recall24.immutable.Respondent;
import dita.recall24.immutable.RespondentSupplementaryData;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
public class Recall24DtoUtils {

    // -- SURVEY IO

    public Try<dita.recall24.mutable.InterviewSet> tryReadSurvey(final DataSource dataSource) {
        return new _JaxbReader()
                .readFromXml(dataSource);
    }

    public Try<Void> tryWriteSurvey(final dita.recall24.mutable.InterviewSet interviewSet, final DataSink dataSink) {
        return new _JaxbWriter()
                .tryWriteTo(interviewSet, dataSink);
    }

    public Try<Blob> tryZip(final String zipEntryName, final dita.recall24.mutable.InterviewSet interviewSet) {
        return new _JaxbWriter()
                .tryToString(interviewSet)
                .mapEmptyToFailure()
                .mapSuccessAsNullable(xml->
                    Clob.of(zipEntryName, CommonMimeType.XML, xml)
                    .toBlobUtf8()
                    .zip());
    }

    public Try<dita.recall24.mutable.InterviewSet> tryUnzip(final Blob blob) {
        return tryReadSurvey(blob
                .unZip(CommonMimeType.XML)
                .asDataSource());
    }

    // -- CONVERSIONS

    public InterviewSet fromDto(final dita.recall24.mutable.InterviewSet interviewSet) {
        val respondents = _NullSafe.stream(interviewSet.getRespondents())
            .map(dto->fromDto(dto))
            .collect(Can.toCan());
        return InterviewSet.of(respondents);
    }

    public dita.recall24.mutable.InterviewSet toDto(
            final InterviewSet model) {
        val dto = new dita.recall24.mutable.InterviewSet();
        dto.setRespondents(
            model.respondents()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    // -- UPDATE DTO FROM MODEL FIELDS

    dita.recall24.mutable.Respondent updateDtoFromModelFields(
            final dita.recall24.mutable.Respondent dto,
            final Respondent model) {
        dto.setAlias(model.alias());
        dto.setDateOfBirth(model.dateOfBirth());
        dto.setSex(model.sex());
        return dto;
    }

    dita.recall24.mutable.Interview updateDtoFromModelFields(
            final dita.recall24.mutable.Interview dto,
            final Interview model) {
        dto.setRespondentAlias(model.respondentAlias());
        dto.setInterviewOrdinal(model.interviewOrdinal());
        dto.setInterviewDate(model.interviewDate());
        dto.setRespondentMetaData(toDto(model.respondentMetaData()));
        return dto;
    }

    dita.recall24.mutable.RespondentSupplementaryData updateDtoFromModelFields(
            final dita.recall24.mutable.RespondentSupplementaryData dto,
            final RespondentSupplementaryData model) {
        dto.setSpecialDietId(model.specialDietId());
        dto.setSpecialDayId(model.specialDayId());
        dto.setHeightCM(model.heightCM());
        dto.setWeightKG(model.weightKG());
        return dto;
    }

    dita.recall24.mutable.Meal updateDtoFromModelFields(
            final dita.recall24.mutable.Meal dto,
            final Meal model) {
        dto.setHourOfDay(model.hourOfDay());
        dto.setFoodConsumptionOccasionId(model.foodConsumptionOccasionId());
        dto.setFoodConsumptionPlaceId(model.foodConsumptionPlaceId());
        return dto;
    }

    dita.recall24.mutable.MemorizedFood updateDtoFromModelFields(
            final dita.recall24.mutable.MemorizedFood dto,
            final MemorizedFood model) {
        dto.setName(model.name());
        return dto;
    }

    dita.recall24.mutable.Record updateDtoFromModelFields(
            final dita.recall24.mutable.Record dto,
            final Record model) {
        dto.setType(model.type());
        dto.setName(model.name());
        dto.setFacetSids(model.facetSids());
        return dto;
    }

    dita.recall24.mutable.Ingredient updateDtoFromModelFields(
            final dita.recall24.mutable.Ingredient dto,
            final Ingredient model) {
        dto.setSid(model.sid());
        dto.setFacetSids(model.facetSids());
        dto.setRawPerCookedRatio(model.rawPerCookedRatio());
        dto.setQuantityCooked(model.quantityCooked());
        return dto;
    }

    // -- HELPER

    // -- DTO TO MODEL

    private Respondent fromDto(final dita.recall24.mutable.Respondent dto) {
        var interviews = _NullSafe.stream(dto.getInterviews())
            .map(Recall24DtoUtils::fromDto)
            .collect(Can.toCan());
        var respondent = new Respondent(dto.getAlias(), dto.getDateOfBirth(), dto.getSex(), interviews);
        interviews.forEach(iv->iv.parentRespondentRef().setValue(respondent));
        return respondent;
    }

    private Interview fromDto(final dita.recall24.mutable.Interview dto) {
        val meals = _NullSafe.stream(dto.getMeals())
                .map(meal->fromDto(meal))
                .collect(Can.toCan());
        val interview = new Interview(ObjectRef.empty(),
                dto.getInterviewDate(),
                IntRef.of(dto.getInterviewOrdinal()),
                fromDto(dto.getRespondentMetaData()),
                meals);
        interview.respondentMetaData().parentInterviewRef().setValue(interview);
        meals.forEach(meal->meal.parentInterviewRef().setValue(interview));
        return interview;
    }

    private RespondentSupplementaryData fromDto(final dita.recall24.mutable.RespondentSupplementaryData dto) {
        val respondentMetaData = new RespondentSupplementaryData(ObjectRef.empty(),
                dto.getSpecialDietId(),
                dto.getSpecialDayId(),
                dto.getHeightCM(),
                dto.getWeightKG());
        return respondentMetaData;
    }

    private Meal fromDto(final dita.recall24.mutable.Meal dto) {
        val memorizedFood = _NullSafe.stream(dto.getMemorizedFood())
                .map(mf->fromDto(mf))
                .collect(Can.toCan());
        val meal = new Meal(ObjectRef.empty(),
                dto.getHourOfDay(),
                dto.getFoodConsumptionOccasionId(),
                dto.getFoodConsumptionPlaceId(),
                memorizedFood);
        memorizedFood.forEach(mf->mf.parentMealRef().setValue(meal));
        return meal;
    }

    private MemorizedFood fromDto(final dita.recall24.mutable.MemorizedFood dto) {
        val topLevelRecords = _NullSafe.stream(dto.getTopLevelRecords())
                .map(record->fromDto(record))
                .collect(Can.toCan());
        val memorizedFood = new MemorizedFood(ObjectRef.empty(),
                dto.getName(),
                topLevelRecords);
        topLevelRecords.forEach(r->r.parentMemorizedFoodRef().setValue(memorizedFood));
        return memorizedFood;
    }

    private Record fromDto(final dita.recall24.mutable.Record dto) {
        val ingredients = _NullSafe.stream(dto.getIngredients())
                .map(ingr->fromDto(ingr))
                .collect(Can.toCan());
        val record24 = new Record(ObjectRef.empty(),
                dto.getType(),
                dto.getName(),
                dto.getFacetSids(),
                ingredients);
        ingredients.forEach(i->i.parentRecordRef().setValue(record24));
        return record24;
    }

    private Ingredient fromDto(final dita.recall24.mutable.Ingredient dto) {
        val ingredient = new Ingredient(ObjectRef.empty(),
                dto.getSid(),
                dto.getName(),
                dto.getFacetSids(),
                dto.getRawPerCookedRatio(),
                dto.getQuantityCooked());
        return ingredient;
    }

    // -- MODEL TO DTO

    private dita.recall24.mutable.Respondent toDto(
            final Respondent model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Respondent(), model);
        dto.setInterviews(model.interviews()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.Interview toDto(
            final Interview model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Interview(), model);
        dto.setMeals(model.meals()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.RespondentSupplementaryData toDto(
            final RespondentSupplementaryData model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.RespondentSupplementaryData(), model);
        return dto;
    }

    private dita.recall24.mutable.Meal toDto(
            final Meal model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Meal(), model);
        dto.setMemorizedFood(model.memorizedFood()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.MemorizedFood toDto(
            final MemorizedFood model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.MemorizedFood(), model);
        dto.setTopLevelRecords(model.topLevelRecords()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.Record toDto(
            final Record model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Record(), model);
        dto.setIngredients(model.ingredients()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.Ingredient toDto(
            final Ingredient model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Ingredient(), model);
        return dto;
    }

}
