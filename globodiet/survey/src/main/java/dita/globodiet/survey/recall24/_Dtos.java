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

import org.causewaystuff.commons.types.internal.ObjectRef;

import org.apache.causeway.applib.jaxb.JavaTimeJaxbAdapters;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.collections.ImmutableEnumSet;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.graph.GraphUtils.GraphKernel;
import org.apache.causeway.commons.graph.GraphUtils.GraphKernel.GraphCharacteristic;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.JsonUtils;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

import dita.commons.types.Sex;
import dita.recall24.api.Record24.Type;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.Interview24;
import dita.recall24.model.Meal24;
import dita.recall24.model.MemorizedFood24;
import dita.recall24.model.Record24;
import dita.recall24.model.Respondent24;
import dita.recall24.model.RespondentMetaData24;

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
                    Sex.values()[subjectSex]);

            final var tree = new GraphKernel(1 + listEntries.size(), ImmutableEnumSet.noneOf(GraphCharacteristic.class));
            final int rootIndex = listEntries.size();
            final int[] current = new int[] {-1, -1, -1, -1, -1, -1};

            listEntries.forEach(IndexedConsumer.zeroBased((i, listEntry)->{
                var listEntryType = listEntry.listEntryType();
                switch (listEntryType) {
                case FoodConsumptionOccasion:
                    tree.addEdge(rootIndex, current[0] = i);
                    return;
                case QuickListItem:
                    tree.addEdge(current[0], current[1] = i);
                    return;
                case QuickListItemForDietarySupplement:
                    tree.addEdge(current[0], current[1] = i);
                    return;
                case DietarySupplement:
                    tree.addEdge(current[1], current[2] = i);
                    return;
                case Food:
                    tree.addEdge(current[1], current[2] = i);
                    return;
                case Recipe:
                    tree.addEdge(current[1], current[2] = i);
                    return;
                case FatDuringCookingForFood:
                    tree.addEdge(current[2], current[3] = i);
                    return;
                case FatDuringCookingForIngredient:
                case FatSauceOrSweeteners:
                case FoodSelectedAsARecipeIngredient:
                //case RecipeSelectedAsARecipeIngredient: //Not yet available (as stated in docs)
                case TypeOfFatUsedFacet:
                case TypeOfMilkOrLiquidUsedFacet:
                    break;
                }
                _Exceptions.unmatchedCase(listEntryType);
            }));

            var meals = tree.streamNeighbors(rootIndex)
                .mapToObj(i0->{
                    var fcoEntry = listEntries.get(i0);
                    var memorizedFoods = tree.streamNeighbors(i0)
                    .mapToObj(i1->{
                        var qliEntry = listEntries.get(i1);
                        var records = tree.streamNeighbors(i1)
                        .mapToObj(i2->{
                            var recordEntry = listEntries.get(i2);
                            var record24 = recordEntry.toRecord24();
                            tree.streamNeighbors(i2)
                            .forEach(i3->{
                                System.err.printf(" - - - %s%n", listEntries.get(i3).listEntryType());
                            });
                            return record24;
                        })
                        .collect(Can.toCan());

                        var memorizedFood24 = qliEntry.toMemorizedFood24(records);
                        return memorizedFood24;
                    })
                    .collect(Can.toCan());

                    var meal24 = fcoEntry.toMeal24(memorizedFoods);
                    return meal24;
                })
                .collect(Can.toCan());

            System.err.println("dita.globodiet.survey.recall24._Dtos.Interview.toInterview24()...");
            meals.forEach(meal->{
                System.err.printf("%s%n", meal);
                meal.memorizedFood().forEach(mem->{
                    System.err.printf(" - %s%n", mem);
                    mem.records().forEach(rec->{
                        System.err.printf(" - - %s%n", rec);
                    });
                });
            });

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
        /** FoodConsumptionPlace */
        @XmlElement(name="ITL_POC_Code")
        private String foodConsumptionPlaceId;
        /** FoodConsumptionOccasion */
        @XmlElement(name="ITL_FCO_Code")
        private String foodConsumptionOccasionId;
        /** FoodConsumption Occasion Hour of Day*/
        @XmlElement(name="ITL_FCOHour")
        private String foodConsumptionHourOfDay;

        /** ListEntry's sequential number */
        @XmlElement(name="ITL_TOK")
        private int mealOrdinal;
        /** Sequential Number Within the Quick List Item */
        @XmlElement(name="ITL_QLINUM")
        private int recordOrdinal;
        /** Sequential Number within a Mixed Recipe for Ingredients */
        @XmlElement(name="ITL_ING_NUM")
        private int ingredientOrdinal;
        /** Sequential Number for Ingredients within a Sub-Recipe */
        @XmlElement(name="ITL_S_ING_NUM")
        private int ingredientSubOrdinal;

        /** ListEntry's Type/Specialization */
        @XmlElement(name="ITL_Type")
        private String listEntryType;
        /** Food Type only for Food/ingredient/fat (for TYPE>3):
         * GI Generic Item, SH Shadow, ' ' not Generic */
        @XmlElement(name="ITL_F_Type")
        private String ITL_F_Type;
        /** Recipe Type only for recipe or Sub-recipe (for TYPE=3 or 3S):<br>
         * 1.1=Open – Known<br>
         * 1.2=Open – Unknown<br>
         * 1.3=Open with brand<br>
         * 2.1=Closed<br>
         * 2.2=Closed with brand<br>
         * 3.0=Strictly commercial<br>
         * 4.1=New – Known<br>
         * 4.2=New – Unknown */
        @XmlElement(name="ITL_R_Type")
        private String recipeType;
        /** Ingredient Type only for ingredient or sub-recipe (for TYPE=5 or 3S or 5S):<br>
         * 1 Fixed<br>
         * 2 Substitutable<br>
         * 3 Fat during cooking<br>
         * A2 Fat used Facet<br>
         * A3 Milk/liquid used */
        @XmlElement(name="ITL_I_Type")
        private String ingredientType;

        /** Type of modification done on Recipe or sub-recipes (for TYPE=3 or 3S):<br>
         * 0 = No modification (default)<br>
         * 1 = Modified in New interview mode - No update needed (when added & deleted ingredients available)<br>
         * 2 = Modified in New interview mode – Update needed (when added or deleted ingredients available
         * and with ingredients with % of consumed quantities > cut-off point)<br>
         * 3 = Recipe modified in Edit interview mode. Recipe defined with R_Modif=0 in New interview mode.<br>
         * 4 = Recipe modified in Edit interview mode. Recipe defined with R_Modif=1 in New interview mode.<br>
         * 5 = Recipe modified in Edit interview mode. Recipe defined with R_Modif=2 in New interview mode.
         * <p>
         * Definition: A recipe is considered as modified only when at least one of its important ingredients
         * (with quantity > 5 % of the whole recipe portion) had been added or deleted during the interview.*/
        @XmlElement(name="ITL_R_Modif")
        private int recipeModificationType;
        /** Type of modification done on Ingredient (for Type=5 or 3s or 5S):<br>
         * 0 = No modification (default)<br>
         * 1 = Added in New interview mode<br>
         * 2 = Deleted in New interview mode (for ingredient with consumed quantities > cut-off point)<br>
         * 3 = Substituted in New interview mode<br>
         * 4 = Added in Edit interview mode<br>
         * 5 = Substituted in Edit interview mode
         * <p>
         * Definition: A recipe is considered as modified only when at least one of its important ingredients
         * (with quantity > 5 % of the whole recipe portion) had been added or deleted during the interview.*/
        @XmlElement(name="ITL_I_Modif")
        private int ingredientModificationType;

        /** Food or Recipe or Ingredient or Supplement Code */
        @XmlElement(name="ITL_FoodNum")
        private String foodOrSimilarCode;
        /** Food or Recipe or Ingredient Group */
        @XmlElement(name="ITL_Group")
        private String groupCode;
        /** Food or Recipe or Ingredient Sub-Group */
        @XmlElement(name="ITL_SubGroup1")
        private String subgroupCode;
        /** Food or Ingredient Sub-Sub-Group */
        @XmlElement(name="ITL_SubGroup2")
        private String subSubgroupCode;

        /** Dietary supplement classification code */
        @XmlElement(name="ITL_DS_Classif")
        private String supplementClassification;

        /** FCO or Quick list item label or food/recipe/ingredient/supplement description label */
        @XmlElement(name="ITL_Text")
        private String label;
        /** Recipe or Food or Ingredient or supplement Name */
        @XmlElement(name="ITL_Name")
        private String name;

        /** Sequence of Facets/Descriptors codes delimited by comma (FFDD,FFDD,... e.g. '0401,0304')
         * <p>
         * 4 additional codes are available:<br>
         * <b>1201</b> is used when a brand or product name has been entered (new brand) during the interview.<br>
         * <b>1299</b> is used when a brand or product name has been selected from the available list.<br>
         * <b>1501</b> is used when one or several fats have been selected during the interview to describe the food/ingredient.<br>
         * This fat information is available as record(s) attached to the described food/ingredient with TYPE=’A2’.<br>
         * <b>1601</b> is used when one or several liquids have been selected during the interview to describe the food/ingredient.<br>
         * This liquid information is available as record(s) attached to the described food/ingredient with TYPE=’A3’.
         * <p>
         * Particular case of the type of fat used for cooking (TYPE=A2) and the type of liquid used for cooking (TYPE=A3):<br>
         * For some foods GloboDiet application is asking on qualitative information on its fat(s) or liquid(s) components.
         * As there are qualitative information, theses fat(s) and liquid(s) foods don’t have attached consumed quantity (=0g)
         * The link between these items (non-fat and fat/liquid used) is kept via a shared ITL_QLINUM.
         */
        @XmlElement(name="ITL_Facets_STR")
        private String facetDescriptorCodes;

        /** BrandName */
        @XmlElement(name="ITL_BrandName")
        private String brandName;

        /** Control Variable:<br>
         * ' ' = not described and not quantified<br>
         * 1 = described and quantified<br>
         * 2 = described but not quantified<br>
         * 3 = recipe to be completed, ingredient should be reviewed */
        @XmlElement(name="ITL_Status")
        private String status;

        /** Consumed quantity in grams (after having applied conversion factors). (recomputed value as docs state)*/
        @XmlElement(name="ITL_CONS_QTY")
        private double consumedQuantity;
        /** Estimated quantity (before having applied conversion factors). (recomputed value as docs state)*/
        @XmlElement(name="ITL_Estim_QTY")
        private double estimatedQuantity;

        /** Quantification method:
         * G=Gram<br>
         * V=Volume<br>
         * P=Photo<br>
         * U=Standard Unit<br>
         * S=Standard Portion<br>
         * H=HHM A=Shapes<br>
         * W=Whole recipe fraction<br>
         * ?=Unknown */
        @XmlElement(name="ITL_Q_Method")
        private String quantificationMethod;
        /** Photo series or HHM or Shape or STDU or STDP Code */
        @XmlElement(name="ITL_QM_Code")
        private String quantificationCode;
        /** Unit (G=gram, V=volume) of the selected Quantity for method Photo, Std Unit, Std Portion */
        @XmlElement(name="ITL_QUNIT")
        private String quantificationUnit;

        /** Quantity in gram/ml attached to the selected method */
        @XmlElement(name="ITL_NGRAMS")
        private double quantityAmount;
        /** Proportion/fraction of selected quantity*/
        @XmlElement(name="ITL_Proport")
        private String quantityFraction;
        /** Reported HHM Fraction for method HHM */
        @XmlElement(name="ITL_HHMFract")
        private String householdMeasureFraction;
        /** Recipe fraction for ‘whole recipe fraction’ method*/
        @XmlElement(name="ITL_R_Fract")
        private String recipeFraction;

        /** Variable indicating whether the quantity was Estimated Raw or Cooked:<br>
         * 1 = Raw<br>
         * 2 = Cooked or Not applicable */
        @XmlElement(name="ITL_RawCooked")
        private String ITL_RawCooked;
        /** Variable indicating whether the quantity was Consumed Raw or Cooked:<br>
         * 1 = Raw<br>
         * 2 = Cooked or Not applicable*/
        @XmlElement(name="ITL_ConsRawCo")
        private String ITL_ConsRawCo;
        /** Raw to Cooked Coefficient*/
        @XmlElement(name="ITL_Conver")
        private double ITL_Conver;

        /** Variable indicating whether the quantity was Estimated With/Without Inedible Part:<br>
         * 1 = Without or not applicable<br>
         * 2 = With*/
        @XmlElement(name="ITL_EDIB")
        private String ITL_EDIB;

        /** TODO */
        @XmlElement(name="ITL_EDIB_CSTE")
        private String ITL_EDIB_CSTE;

        /** TODO */
        @XmlElement(name="ITL_FATLEFTO")
        private String ITL_FATLEFTO;

        /** TODO */
        @XmlElement(name="ITL_FAT_PCT")
        private String ITL_FAT_PCT;

        /** TODO */
        @XmlElement(name="ITL_PCT_CONS")
        private String ITL_PCT_CONS;

        /** TODO */
        @XmlElement(name="ITL_PCT_ESTIM")
        private String ITL_PCT_ESTIM;

        /** TODO */
        @XmlElement(name="ITL_R_COOKED")
        private String ITL_R_COOKED;

        /** TODO */
        @XmlElement(name="ITL_R_EDIB")
        private String ITL_R_EDIB;

        /** TODO */
        @XmlElement(name="ITL_RAW_Q")
        private String ITL_RAW_Q;

        /** TODO */
        @XmlElement(name="ITL_RAW_Q_W")
        private String ITL_RAW_Q_W;

        /** TODO */
        @XmlElement(name="ITL_MAX")
        private String ITL_MAX;

        /** TODO */
        @XmlElement(name="ITL_SUPPL")
        private String ITL_SUPPL;

        /** TODO */
        @XmlElement(name="ITL_ITEM_SEQ")
        private String ITL_ITEM_SEQ;

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        static class Quantity {
            /** TODO */
            @XmlElement(name="ITQ_Order")
            private String order;
            /** TODO */
            @XmlElement(name="ITQ_SH_Surface")
            private String shSurface;
            /** TODO */
            @XmlElement(name="ITQ_TH_Thick")
            private String thThick;
            /** TODO */
            @XmlElement(name="ITQ_PH_Code")
            private String phCode;
            /** TODO */
            @XmlElement(name="ITQ_PH_QTY")
            private String phQuantity;
            /** TODO */
            @XmlElement(name="ITQ_FRACT")
            private String fraction;
        }
        @XmlElementWrapper(name="ListeITV_Quantity")
        @XmlElement(name="ITV_Quantity_Service", type=Quantity.class)
        private List<Quantity> quantities;

        @XmlAccessorType(XmlAccessType.FIELD)
        @Data
        static class Nutrient {
            /** TODO */
            @XmlElement(name="NTR_Code")
            private String code;
            /** TODO */
            @XmlElement(name="ITV_NTR_Value")
            private double value;
        }
        @XmlElementWrapper(name="ListeITV_Nutriments")
        @XmlElement(name="ITV_Nutriment", type=Nutrient.class)
        private List<Nutrient> nutrients;

        @XmlElementWrapper(name="ListeNotes")
        @XmlElement(name="Note", type=Note.class)
        private List<Note> notes;

        ListEntryType listEntryType() {
            return ListEntryType.parse(listEntryType);
        }

        Meal24 toMeal24(final Can<MemorizedFood24> memorizedFood) {
            LocalTime hourOfDay = parseLocalTimeFrom4Digits(foodConsumptionHourOfDay.trim());
            return Meal24.of(hourOfDay, foodConsumptionOccasionId, foodConsumptionPlaceId, memorizedFood);
        }

        MemorizedFood24 toMemorizedFood24(final Can<Record24> records) {
            _Assert.assertTrue(_Strings.isNullOrEmpty(name));
            return MemorizedFood24.of(label, records);
        }

        Record24 toRecord24() {
            return switch (listEntryType()) {
            case Food -> toRecord24(Type.FOOD);
            case FoodSelectedAsARecipeIngredient -> toRecord24(Type.FOOD); //TODO information is lost here
            case FatDuringCookingForFood -> toRecord24(Type.INFORMAL); // TODO verify data contained is duplicated
            case Recipe -> toRecord24(Type.COMPOSITE); //TODO ad-hoc or prepared?

            case DietarySupplement -> toRecord24(Type.INCOMPLETE);
            case FatDuringCookingForIngredient -> toRecord24(Type.INCOMPLETE);
            case FatSauceOrSweeteners -> toRecord24(Type.INCOMPLETE);
            //case RecipeSelectedAsARecipeIngredient -> toRecord24(Type.INCOMPLETE); //Not yet available (as stated in docs)
            case TypeOfFatUsedFacet -> toRecord24(Type.INCOMPLETE);
            case TypeOfMilkOrLiquidUsedFacet -> toRecord24(Type.INCOMPLETE);
            default -> throw new IllegalArgumentException("Unexpected value: " + ListEntryType.parse(listEntryType));
            };
        }

        Record24 toRecord24(final Type type) {
            //TODO label() might be non empty -> information lost
            Can<Ingredient24> ingredients = Can.empty(); //TODO
            return Record24.of(type, name, facetDescriptorCodes, ingredients);
        }

    }

    private LocalTime parseLocalTimeFrom4Digits(final String hhmm) {
        var hh = hhmm.substring(0, 2);
        var mm = hhmm.substring(2, 4);
        return LocalTime.of(Integer.parseInt(hh), Integer.parseInt(mm));
    }

    @Getter @Accessors(fluent=true)
    @RequiredArgsConstructor
    private enum ListEntryType {
        FoodConsumptionOccasion("1"),
        QuickListItem("2"),
        QuickListItemForDietarySupplement("2S"),
        Recipe("3"),
        //RecipeSelectedAsARecipeIngredient("3S"), //Not yet available (as stated in docs)
        Food("4"),
        FoodSelectedAsARecipeIngredient("5"),
        FatDuringCookingForFood("6"),
        FatDuringCookingForIngredient("7"),
        TypeOfFatUsedFacet("A2"),
        TypeOfMilkOrLiquidUsedFacet("A3"),
        /**
         * Lines/records with Type=8 should not be used for data analysis because the reported consumed quantities
         * are redundant with other lines/records.
         */
        FatSauceOrSweeteners("8"),
        DietarySupplement("9");
        final String code;
        @SneakyThrows
        static ListEntryType parse(final String code) {
            for (var type : ListEntryType.values()) {
                if(type.code.equalsIgnoreCase(code)) {
                    return type;
                }
            }
            throw new ParseException("unmapped code '"+code+"' for 'RecordType'", 0);
        }
    }

}
