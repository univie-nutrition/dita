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
package dita.globodiet.survey.recall24;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.causeway.applib.jaxb.JavaTimeJaxbAdapters;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.JsonUtils;

import dita.commons.types.Gender;
import dita.commons.types.ObjectRef;
import dita.recall24.api.Record24.Type;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.Interview24;
import dita.recall24.model.Meal24;
import dita.recall24.model.MemorizedFood24;
import dita.recall24.model.Record24;
import dita.recall24.model.Respondent24;
import dita.recall24.model.RespondentMetaData24;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
class _Dtos {

    @XmlRootElement(name="ITV")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Itv {
        @XmlElement(name="Param")
        private Param param;
        @XmlElementWrapper(name="ListeInterviews")
        @XmlElement(name="Interview", type=Interview.class)
        private List<Interview> interviews;
        //dump
        String toJson() {
            return JsonUtils.toStringUtf8(this, JsonUtils::indentedOutput);
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Param {
        @XmlElement(name="PAR_ExportDate")
        private String exportDate;
        @XmlElement(name="Project")
        private Project Project;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Project {
        @XmlAttribute(name = "PRO_Code")
        protected String code;
        @XmlElement(name="PRO_Language")
        private String language;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Country {
        @XmlAttribute(name = "CTY_code")
        protected String code;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Interview {
        @XmlElement(name="ITV_Id")
        private String id;
        @XmlElement(name="ITV_RecallDate")
        private String recallDate;
        @XmlElement(name="CTR_Code")
        private String ctrCode;
        @XmlElement(name="ITR_Code")
        private String itrCode;
        @XmlElement(name="ITR_CTY_Code")
        private String itrCtyCode;
        @XmlElement(name="ITR_CTR_Code")
        private String itrCtrCode;
        @XmlElement(name="SBT_Code")
        private String subjectCode;
        @XmlElement(name="SBT_Name")
        private String subjectName;
        @XmlElement(name="SBT_FirstName")
        private String subjectFirstName;
        @XmlElement(name="SBT_Sex")
        private int subjectSex;
        @XmlElement(name="SBT_BirthDate")
        @XmlJavaTypeAdapter(value=JavaTimeJaxbAdapters.LocalDateTimeToStringAdapter.class)
        private LocalDateTime subjectBirthDate;
        @XmlElement(name="ITG_Release")
        private String itgRelase;
        @XmlElement(name="ITG_Num")
        private String itgNum;
        @XmlElement(name="ITG_Date")
        @XmlJavaTypeAdapter(value=JavaTimeJaxbAdapters.LocalDateTimeToStringAdapter.class)
        private LocalDateTime interviewDate;
        @XmlElement(name="ITG_SubjectHeight")
        private float heightCM;
        @XmlElement(name="ITG_SubjectWeight")
        private float weightKG;
        @XmlElement(name="ITG_VURecall")
        private String itgVURecall;
        @XmlElement(name="ITG_VUNextRecall")
        private String itgVUNextRecall;
        @XmlElement(name="ITG_EnergyRequirement")
        private String itgEnergyRequirement;
        @XmlElement(name="ITG_EnergyCalculated")
        private String itgEnergyCalculated;
        @XmlElement(name="ITG_TimeGI")
        private String itgTimeGI;
        @XmlElement(name="ITG_TimeQL")
        private String itgTimeQL;
        @XmlElement(name="ITG_TimeDQ")
        private String itgTimeDQ;
        @XmlElement(name="ITG_TimeDS")
        private String itgTimeDS;
        @XmlElement(name="ITG_TimeALL")
        private String itgTimeALL;
        @XmlElement(name="ITG_RecomputeDate")
        private String itgRecomputeDate;
        @XmlElement(name="ITG_SPD_code")
        private String specialDayCode;
        @XmlElement(name="ITG_SPT_code")
        private String specialDietCode;
        @XmlElement(name="ITG_Version")
        private String itgVersion;

        @XmlElementWrapper(name="ListeNotes")
        @XmlElement(name="Note", type=Note.class)
        private List<Note> notes;

        @XmlElementWrapper(name="ListeLignesITV")
        @XmlElement(name="LigneITV", type=ListEntry.class)
        private List<ListEntry> listEntries;

        Interview24 toInterview24() {

            Respondent24 respondent = new Respondent24(
                    subjectName + "|" + subjectFirstName,
                    subjectBirthDate.toLocalDate(),
                    Gender.values()[subjectSex]);

            _NullSafe.stream(listEntries)
                .map(ListEntry::toRecall24)
                .forEach(obj24->{
                    System.err.printf("%s%n", obj24);
                });

            Can<Meal24> meals = Can.empty();

            return Interview24.of(
                    respondent,
                    interviewDate.toLocalDate(),
                    new RespondentMetaData24(
                            ObjectRef.<Interview24>empty(),
                            specialDietCode,
                            specialDayCode,
                            heightCM,
                            weightKG),
                    meals);
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Note {
        @XmlElement(name="NOT_Status")
        private String status;
        @XmlElement(name="NOT_Memo")
        private String memo;
        @XmlElement(name="NOT_Date")
        private String date;
        @XmlElement(name="NOT_Type")
        private String type;
        @XmlElement(name="NOT_QuantityConsumed")
        private String quantityConsumed;
        @XmlElement(name="NOT_MaxVal")
        private String maxVal;
        @XmlElement(name="NOT_EmptyQLI")
        private String emptyQLI;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class ListEntry {
        @XmlElement(name="ITL_POC_Code")
        private String foodConsumptionPlaceId;
        @XmlElement(name="ITL_FCO_Code")
        private String foodConsumptionOccasionId;
        @XmlElement(name="ITL_FCOHour")
        private String ITL_FCOHour;
        @XmlElement(name="ITL_TOK")
        private String ITL_TOK;
        @XmlElement(name="ITL_QLINUM")
        private String ITL_QLINUM;
        @XmlElement(name="ITL_ING_NUM")
        private String ITL_ING_NUM;
        @XmlElement(name="ITL_S_ING_NUM")
        private String ITL_S_ING_NUM;
        @XmlElement(name="ITL_Type")
        private String listEntryType;
        @XmlElement(name="ITL_FoodNum")
        private String ITL_FoodNum;
        @XmlElement(name="ITL_Group")
        private String ITL_Group;
        @XmlElement(name="ITL_SubGroup1")
        private String ITL_SubGroup1;
        @XmlElement(name="ITL_SubGroup2")
        private String ITL_SubGroup2;
        @XmlElement(name="ITL_DS_Classif") //empty
        private String ITL_DS_Classif;
        @XmlElement(name="ITL_F_Type") //empty
        private String ITL_F_Type;
        @XmlElement(name="ITL_I_Type") //empty
        private String ITL_I_Type;
        @XmlElement(name="ITL_R_Modif")
        private String ITL_R_Modif;
        @XmlElement(name="ITL_I_Modif")
        private String ITL_I_Modif;
        @XmlElement(name="ITL_R_Type") //empty
        private String ITL_R_Type;
        @XmlElement(name="ITL_Text")
        private String ITL_Text;
        @XmlElement(name="ITL_Name")
        private String ITL_Name;
        @XmlElement(name="ITL_Facets_STR")
        private String ITL_Facets_STR;
        @XmlElement(name="ITL_BrandName") //empty
        private String ITL_BrandName;
        @XmlElement(name="ITL_Status")
        private String ITL_Status;
        @XmlElement(name="ITL_Estim_QTY")
        private String ITL_Estim_QTY;
        @XmlElement(name="ITL_Q_Method")
        private String ITL_Q_Method;
        @XmlElement(name="ITL_QM_Code")
        private String ITL_QM_Code;
        @XmlElement(name="ITL_QUNIT")
        private String ITL_QUNIT;
        @XmlElement(name="ITL_NGRAMS")
        private String ITL_NGRAMS;
        @XmlElement(name="ITL_Proport")
        private String ITL_Proport;
        @XmlElement(name="ITL_HHMFract")
        private String ITL_HHMFract;
        @XmlElement(name="ITL_R_Fract")
        private String ITL_R_Fract;
        @XmlElement(name="ITL_RawCooked")
        private String ITL_RawCooked;
        @XmlElement(name="ITL_ConsRawCo")
        private String ITL_ConsRawCo;
        @XmlElement(name="ITL_Conver")
        private String ITL_Conver;
        @XmlElement(name="ITL_EDIB")
        private String ITL_EDIB;
        @XmlElement(name="ITL_EDIB_CSTE")
        private String ITL_EDIB_CSTE;
        @XmlElement(name="ITL_DENSITY")
        private String ITL_DENSITY;
        @XmlElement(name="ITL_FATLEFTO")
        private String ITL_FATLEFTO;
        @XmlElement(name="ITL_FAT_PCT")
        private String ITL_FAT_PCT;
        @XmlElement(name="ITL_PCT_CONS")
        private String ITL_PCT_CONS;
        @XmlElement(name="ITL_PCT_ESTIM")
        private String ITL_PCT_ESTIM;
        @XmlElement(name="ITL_CONS_QTY")
        private String ITL_CONS_QTY;
        @XmlElement(name="ITL_R_COOKED")
        private String ITL_R_COOKED;
        @XmlElement(name="ITL_R_EDIB")
        private String ITL_R_EDIB;
        @XmlElement(name="ITL_RAW_Q")
        private String ITL_RAW_Q;
        @XmlElement(name="ITL_RAW_Q_W")
        private String ITL_RAW_Q_W;
        @XmlElement(name="ITL_MAX")
        private String ITL_MAX;
        @XmlElement(name="ITL_SUPPL")
        private String ITL_SUPPL;
        @XmlElement(name="ITL_ITEM_SEQ")
        private String ITL_ITEM_SEQ;

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        static class Quantity {
            @XmlElement(name="ITQ_Order")
            private String order;
            @XmlElement(name="ITQ_SH_Surface")
            private String shSurface;
            @XmlElement(name="ITQ_TH_Thick")
            private String thThick;
            @XmlElement(name="ITQ_PH_Code")
            private String phCode;
            @XmlElement(name="ITQ_PH_QTY")
            private String phQuantity;
            @XmlElement(name="ITQ_FRACT")
            private String fraction;
        }
        @XmlElementWrapper(name="ListeITV_Quantity")
        @XmlElement(name="ITV_Quantity_Service", type=Quantity.class)
        private List<Quantity> quantities;

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        static class Nutrient {
            @XmlElement(name="NTR_Code")
            private String code;
            @XmlElement(name="ITV_NTR_Value")
            private double value;
        }
        @XmlElementWrapper(name="ListeITV_Nutriments")
        @XmlElement(name="ITV_Nutriment", type=Nutrient.class)
        private List<Nutrient> nutrients;

        @XmlElementWrapper(name="ListeNotes")
        @XmlElement(name="Note", type=Note.class)
        private List<Note> notes;

        Object toRecall24() {
            return switch (ListEntryType.parse(listEntryType)) {
            case FoodConsumptionOccasion -> toMeal24();
            case QuickListItem -> toMemorizedFood24();
            case Food -> toRecord24(Type.FOOD);
            case FoodSelectedAsARecipeIngredient -> toRecord24(Type.FOOD); //TODO information is lost here
            case FatDuringCookingForFood -> toRecord24(Type.INFORMAL); // TODO verify data contained is duplicated
            case Recipe -> toRecord24(Type.COMPOSITE); //TODO ad-hoc or prepared?

            case QuickListItemForDietarySupplement -> toRecord24(Type.INCOMPLETE);
            case DietarySupplement -> toRecord24(Type.INCOMPLETE);
            case FatDuringCookingForIngredient -> toRecord24(Type.INCOMPLETE);
            case FatSauceOrSweeteners -> toRecord24(Type.INCOMPLETE);
            case RecipeSelectedAsARecipeIngredient -> toRecord24(Type.INCOMPLETE);
            case TypeOfFatUsedFacet -> toRecord24(Type.INCOMPLETE);
            case TypeOfMilkOrLiquidUsedFacet -> toRecord24(Type.INCOMPLETE);
            default -> throw new IllegalArgumentException("Unexpected value: " + ListEntryType.parse(listEntryType));
            };
        }

        Meal24 toMeal24() {
            LocalTime hourOfDay = parseLocalTimeFrom4Digits(ITL_FCOHour.trim());
            Can<MemorizedFood24> memorizedFood = Can.empty();
            return Meal24.of(hourOfDay, foodConsumptionOccasionId, foodConsumptionPlaceId, memorizedFood);
        }

        MemorizedFood24 toMemorizedFood24() {
            String name = "TODO"; //TODO
            Can<Record24> records = Can.empty();
            return MemorizedFood24.of(name, records);
        }

        Record24 toRecord24(final Type type) {
            String name = "TODO"; //TODO
            Can<Ingredient24> ingredients = Can.empty();
            return Record24.of(type, name, ITL_Facets_STR, ingredients);
        }

    }

    private LocalTime parseLocalTimeFrom4Digits(final String hhmm) {
        var hh = hhmm.substring(0, 2);
        var mm = hhmm.substring(2, 4);
        return LocalTime.of(Integer.parseInt(hh), Integer.parseInt(mm));
    }

    //

    private enum ListEntryType {
        FoodConsumptionOccasion,
        QuickListItem,
        QuickListItemForDietarySupplement,
        Recipe,
        RecipeSelectedAsARecipeIngredient,
        Food,
        FoodSelectedAsARecipeIngredient,
        FatDuringCookingForFood,
        FatDuringCookingForIngredient,
        TypeOfFatUsedFacet,
        TypeOfMilkOrLiquidUsedFacet,
        FatSauceOrSweeteners,
        DietarySupplement;
        @SneakyThrows
        static ListEntryType parse(final String byName) {
            switch(byName){
            case "1": return FoodConsumptionOccasion;
            case "2": return QuickListItem;
            case "2S": return QuickListItemForDietarySupplement;
            case "3": return Recipe;
            case "3S": return RecipeSelectedAsARecipeIngredient;
            case "4": return Food;
            case "5": return FoodSelectedAsARecipeIngredient;
            case "6": return FatDuringCookingForFood;
            case "7": return FatDuringCookingForIngredient;
            case "A2": return TypeOfFatUsedFacet;
            case "A3": return TypeOfMilkOrLiquidUsedFacet;
            case "8": return FatSauceOrSweeteners;
            case "9": return DietarySupplement;
            default:
                throw new ParseException("unmapped value '"+byName+"' for 'RecordType'", 0);
            }
        }
    }

}
