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
import dita.recall24.api.Interview24;
import dita.recall24.api.InterviewSet24;
import dita.recall24.api.Meal24;
import dita.recall24.api.MemorizedFood24;
import dita.recall24.api.Record24;
import dita.recall24.api.Respondent24;
import dita.recall24.api.RespondentSupplementaryData24;
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

    public InterviewSet24.Dto fromDto(final dita.recall24.mutable.InterviewSet interviewSet) {
        val respondents = _NullSafe.stream(interviewSet.getRespondents())
            .map(dto->fromDto(dto))
            .collect(Can.toCan());
        return InterviewSet24.Dto.of(respondents);
    }

    public dita.recall24.mutable.InterviewSet toDto(
            final InterviewSet24.Dto model) {
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
            final Respondent24.Dto model) {
        dto.setAlias(model.alias());
        dto.setDateOfBirth(model.dateOfBirth());
        dto.setSex(model.sex());
        return dto;
    }

    dita.recall24.mutable.Interview updateDtoFromModelFields(
            final dita.recall24.mutable.Interview dto,
            final Interview24.Dto model) {
        dto.setRespondentAlias(model.respondentAlias());
        dto.setInterviewOrdinal(model.interviewOrdinal());
        dto.setInterviewDate(model.interviewDate());
        dto.setRespondentMetaData(toDto(model.respondentSupplementaryData()));
        return dto;
    }

    dita.recall24.mutable.RespondentSupplementaryData updateDtoFromModelFields(
            final dita.recall24.mutable.RespondentSupplementaryData dto,
            final RespondentSupplementaryData24.Dto model) {
        dto.setSpecialDietId(model.specialDietId());
        dto.setSpecialDayId(model.specialDayId());
        dto.setHeightCM(model.heightCM());
        dto.setWeightKG(model.weightKG());
        return dto;
    }

    dita.recall24.mutable.Meal updateDtoFromModelFields(
            final dita.recall24.mutable.Meal dto,
            final Meal24.Dto model) {
        dto.setHourOfDay(model.hourOfDay());
        dto.setFoodConsumptionOccasionId(model.foodConsumptionOccasionId());
        dto.setFoodConsumptionPlaceId(model.foodConsumptionPlaceId());
        return dto;
    }

    dita.recall24.mutable.MemorizedFood updateDtoFromModelFields(
            final dita.recall24.mutable.MemorizedFood dto,
            final MemorizedFood24.Dto model) {
        dto.setName(model.name());
        return dto;
    }

    dita.recall24.mutable.Record updateDtoFromModelFields(
            final dita.recall24.mutable.Record dto,
            final Record24.Dto model) {
        //dto.setType(model.type());
        dto.setName(model.name());
        dto.setFacetSids(model.facetSids());
        return dto;
    }

    // -- HELPER

    // -- DTO TO MODEL

    private Respondent24.Dto fromDto(final dita.recall24.mutable.Respondent dto) {
        var interviews = _NullSafe.stream(dto.getInterviews())
            .map(Recall24DtoUtils::fromDto)
            .collect(Can.toCan());
        var respondent = new Respondent24.Dto(dto.getAlias(), dto.getDateOfBirth(), dto.getSex(), interviews);
        interviews.forEach(iv->iv.parentRespondentRef().setValue(respondent));
        return respondent;
    }

    private Interview24.Dto fromDto(final dita.recall24.mutable.Interview dto) {
        val meals = _NullSafe.stream(dto.getMeals())
                .map(meal->fromDto(meal))
                .collect(Can.toCan());
        val interview = new Interview24.Dto(ObjectRef.empty(),
                dto.getInterviewDate(),
                IntRef.of(dto.getInterviewOrdinal()),
                fromDto(dto.getRespondentMetaData()),
                meals);
        interview.respondentSupplementaryData().parentInterviewRef().setValue(interview);
        meals.forEach(meal->meal.parentInterviewRef().setValue(interview));
        return interview;
    }

    private RespondentSupplementaryData24.Dto fromDto(final dita.recall24.mutable.RespondentSupplementaryData dto) {
        val respondentMetaData = new RespondentSupplementaryData24.Dto(ObjectRef.empty(),
                dto.getSpecialDietId(),
                dto.getSpecialDayId(),
                dto.getHeightCM(),
                dto.getWeightKG());
        return respondentMetaData;
    }

    private Meal24.Dto fromDto(final dita.recall24.mutable.Meal dto) {
        val memorizedFood = _NullSafe.stream(dto.getMemorizedFood())
                .map(mf->fromDto(mf))
                .collect(Can.toCan());
        val meal = new Meal24.Dto(ObjectRef.empty(),
                dto.getHourOfDay(),
                dto.getFoodConsumptionOccasionId(),
                dto.getFoodConsumptionPlaceId(),
                memorizedFood);
        memorizedFood.forEach(mf->mf.parentMealRef().setValue(meal));
        return meal;
    }

    private MemorizedFood24.Dto fromDto(final dita.recall24.mutable.MemorizedFood dto) {
        val topLevelRecords = _NullSafe.stream(dto.getTopLevelRecords())
                .map(record->fromDto(record))
                .collect(Can.toCan());
        val memorizedFood = new MemorizedFood24.Dto(ObjectRef.empty(),
                dto.getName(),
                topLevelRecords);
        topLevelRecords.forEach(r->r.parentMemorizedFoodRef().setValue(memorizedFood));
        return memorizedFood;
    }

    private Record24.Dto fromDto(final dita.recall24.mutable.Record dto) {
        return Record24.product(null, null, null, null, null, null); // FIXME
//        val ingredients = _NullSafe.stream(dto.getIngredients())
//                .map(ingr->fromDto(ingr))
//                .collect(Can.toCan());
//        val record24 = new Record24.Dto(ObjectRef.empty(),
//                dto.getType(),
//                dto.getName(),
//                dto.getFacetSids(),
//                ingredients);
//        ingredients.forEach(i->i.parentRecordRef().setValue(record24));
//        return record24;
    }

    // -- MODEL TO DTO

    private dita.recall24.mutable.Respondent toDto(
            final Respondent24.Dto model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Respondent(), model);
        dto.setInterviews(model.interviews()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.Interview toDto(
            final Interview24.Dto model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Interview(), model);
        dto.setMeals(model.meals()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.RespondentSupplementaryData toDto(
            final RespondentSupplementaryData24.Dto model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.RespondentSupplementaryData(), model);
        return dto;
    }

    private dita.recall24.mutable.Meal toDto(
            final Meal24.Dto model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Meal(), model);
        dto.setMemorizedFood(model.memorizedFood()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.MemorizedFood toDto(
            final MemorizedFood24.Dto model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.MemorizedFood(), model);
        dto.setTopLevelRecords(model.topLevelRecords()
                .stream()
                .map(Recall24DtoUtils::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private dita.recall24.mutable.Record toDto(
            final Record24.Dto model) {
        val dto = updateDtoFromModelFields(new dita.recall24.mutable.Record(), model); 
//FIXME        
//        dto.setIngredients(model.ingredients()
//                .stream()
//                .map(Recall24DtoUtils::toDto)
//                .collect(Collectors.toList()));
        return dto;
    }

}
