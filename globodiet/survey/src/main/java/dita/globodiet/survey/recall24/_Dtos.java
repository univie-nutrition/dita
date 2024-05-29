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

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.jaxb.JavaTimeJaxbAdapters;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;

import dita.globodiet.survey.recall24._Dtos.ListEntry.ListEntryType;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

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
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Param {
        @XmlElement(name="PAR_ExportDate")
        private String exportDate;
        @XmlElement(name="Project")
        private Project project;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Project {
        /** Project name. */
        @XmlAttribute(name = "PRO_Code")
        protected String name;
        /** Language used by these interviews/records. */
        @XmlElement(name="PRO_Language")
        private String language;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Country {
        /** Country code */
        @XmlAttribute(name = "CTY_code")
        protected String code;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Interview {
        /** Empty not used (at least at time of writing)*/
        @XmlElement(name="ITV_Id")
        private String id;

        /** Date when the interview occurred. */
        @XmlElement(name="ITV_RecallDate")
        @XmlJavaTypeAdapter(value=JavaTimeJaxbAdapters.LocalDateTimeToStringAdapter.class)
        private LocalDateTime interviewDate;

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

        /** Date when the consumptions occurred. */
        @XmlElement(name="ITG_Date")
        @XmlJavaTypeAdapter(value=JavaTimeJaxbAdapters.LocalDateTimeToStringAdapter.class)
        private LocalDateTime consumptionDate;

        @XmlElement(name="ITG_SubjectHeight")
        private BigDecimal heightCM;
        @XmlElement(name="ITG_SubjectWeight")
        private BigDecimal weightKG;
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

        // -- UTILITY

        record ListEntryTreeNode(
                ObjectRef<ListEntryTreeNode> parentNode,
                ListEntry entry,
                List<ListEntryTreeNode> childNodes) {
            static ListEntryTreeNode root() {
                return new ListEntryTreeNode(ObjectRef.empty(), null, new ArrayList<>());
            }
            static ListEntryTreeNode node(final ListEntry entry) {
                return new ListEntryTreeNode(ObjectRef.empty(), entry, new ArrayList<>());
            }
            boolean isRoot() {
                return entry==null;
            }
            ListEntryType type() {
                return isRoot() ? null : entry.listEntryType();
            }
            ListEntryTreeNode add(final ListEntry entry) {
                var childNode = node(entry);
                childNodes.add(childNode);
                childNode.parentNode.setValue(this);
                return childNode;
            }
            void visitDepthFirst(final Consumer<ListEntry> visitor) {
                if(!isRoot()) visitor.accept(this.entry());
                childNodes.forEach(node->node.visitDepthFirst(visitor));
            }
        }

        ListEntryTreeNode asTree() {
            var root = ListEntryTreeNode.root();
            var current = new ListEntryTreeNode[] {null, null, null, null}; // 4 (tree) levels of placeholders

            listEntries.forEach(IndexedConsumer.zeroBased((i, listEntry)->{
                var listEntryType = listEntry.listEntryType();
                switch (listEntryType) {
                case FoodConsumptionOccasion:
                    current[0] = root.add(listEntry);
                    current[1] = null;
                    current[2] = null;
                    current[3] = null;
                    return;
                case QuickListItem:
                case QuickListItemForDietarySupplement:
                    current[1] = current[0].add(listEntry);
                    current[2] = null;
                    current[3] = null;
                    return;
                case Food:
                case Recipe:
                case DietarySupplement:
                    current[2] = current[1].add(listEntry);
                    current[3] = null;
                    return;
                case FoodSelectedAsARecipeIngredient: {
                    var recipe = current[2];
                    _Assert.assertEquals(ListEntryType.Recipe, recipe.type());
                    current[3] = recipe.add(listEntry);
                    return;
                }
                case TypeOfFatUsedFacet:
                case TypeOfMilkOrLiquidUsedFacet: {
                    var foodOrRecipeOrSupplement = current[2];
                    var ingredient = current[3];
                    if(ingredient==null) {
                        assertSharedOrdinals(foodOrRecipeOrSupplement.entry(), listEntry);
                        foodOrRecipeOrSupplement.add(listEntry); // leaf node
                    } else {
                        assertSharedOrdinalsForIngredient(ingredient.entry(), listEntry);
                        ingredient.add(listEntry); // leaf node
                    }
                    return;
                }
                case FatDuringCookingForFood: {
                    var food = current[2];
                    _Assert.assertEquals(ListEntryType.Food, food.type());
                    assertSharedOrdinals(food.entry(), listEntry);
                    food.add(listEntry); // leaf node
                    return;
                }
                case FatDuringCookingForIngredient: {
                    var ingredient = current[3];
                    _Assert.assertEquals(ListEntryType.FoodSelectedAsARecipeIngredient, ingredient.type());
                    assertSharedOrdinalsForIngredient(ingredient.entry(), listEntry);
                    ingredient.add(listEntry); // leaf node
                    return;
                }
                case FatSauceOrSweeteners: {
                    var foodOrRecipeOrSupplement = current[2];
                    var ingredient = current[3];
                    if(ingredient==null) {
                        System.err.printf("ignored for food: %s %n  %s%n  %s%n", listEntryType, foodOrRecipeOrSupplement.entry(), listEntry);
                        assertSharedOrdinals(foodOrRecipeOrSupplement.entry(), listEntry);
                        foodOrRecipeOrSupplement.add(listEntry); // leaf node
                    } else {
                        System.err.printf("ignored for ingredient: %s %n  %s%n  %s%n", listEntryType, ingredient.entry(), listEntry);
                        assertSharedOrdinalsForIngredient(ingredient.entry(), listEntry);
                        ingredient.add(listEntry); // leaf node
                    }
                    return;
                }
                //case RecipeSelectedAsARecipeIngredient: //Not (yet) available (as stated in docs)
                }

                throw _Exceptions.unmatchedCase(listEntryType);
            }));

            {   // verify we have all nodes
                var nodeCounter = new LongAdder();
                root.visitDepthFirst(_->nodeCounter.increment());
                _Assert.assertEquals(listEntries.size(), nodeCounter.intValue(),
                        ()->"tree size not equal to list size");
            }
            return root;
        }

        private static void assertSharedOrdinalsForIngredient(final ListEntry a, final ListEntry b) {
            assertSharedOrdinals(a, b);
            _Assert.assertEquals(a.ingredientOrdinal, b.ingredientOrdinal, ()->String.format("ingredient ordinal mismatch; "
                    + "informal entry is expected to share the ingredient ordinal with the food or ingredient "
                    + "it is associated with (listEntryType left: %s, right: %s)", a.listEntryType().name(), b.listEntryType().name()));
        }

        private static void assertSharedOrdinals(final ListEntry a, final ListEntry b) {
            _Assert.assertEquals(a.mealOrdinal, b.mealOrdinal, ()->"meal ordinal mismatch; "
                    + "informal entry is expected to share the meal ordinal with the food or ingredient "
                    + "it is associated with");
            _Assert.assertEquals(a.recordOrdinal, b.recordOrdinal, ()->"record ordinal mismatch; "
                    + "informal entry is expected to share the record ordinal with the food or ingredient "
                    + "it is associated with");
        }

    }

    /*<Note>
        <NOT_Status>99</NOT_Status>
        <NOT_Memo>•••●LEERER QUICKLIST-Eintrag●•••¤[LF]¤Rezept bekannt , in der Arbeit gemacht¤[LF]¤Leinsamen - 1 EL, DM bio/Zurück zum Ursprung - mit Wasser gequellt¤[LF]¤Chiasamen - 1 EL, Natur aktiv, mit Wasser gequellt¤[LF]¤Wasser, Leitung - 100 ml¤[LF]¤Braunhirsemehl - 1 EL, Staudigl/Urkornhof¤[LF]¤Naturjogurt - 3 EL, Spar Natur Pur¤[LF]¤Pistazienmuß - 1 EL, Marke KA wie Nutella in der Art¤[LF]¤¤[LF]¤¤[LF]¤¤[LF]¤</NOT_Memo>
        <NOT_Date>2024-03-19T23:19:43</NOT_Date>
        <NOT_Type>SPE</NOT_Type>
        <NOT_QuantityConsumed>0</NOT_QuantityConsumed>
        <NOT_MaxVal>false</NOT_MaxVal>
        <NOT_EmptyQLI>false</NOT_EmptyQLI>
    </Note>*/
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Note {
        @XmlElement(name="NOT_Status")
        private String status; // 3 chars max
        @XmlElement(name="NOT_Memo")
        private String memo;
        @XmlElement(name="NOT_Date")
        @XmlJavaTypeAdapter(value=JavaTimeJaxbAdapters.LocalDateTimeToStringAdapter.class)
        private LocalDateTime date;
        @XmlElement(name="NOT_Type")
        private String type;
        @XmlElement(name="NOT_QuantityConsumed")
        private BigDecimal quantityConsumed;
        @XmlElement(name="NOT_MaxVal")
        private boolean maxVal;
        @XmlElement(name="NOT_EmptyQLI")
        private boolean emptyQLI;
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
        @Nullable @XmlElement(name="ITL_F_Type")
        private String foodType;
        /** Recipe Type only for recipe or Sub-recipe (for TYPE=3 or 3S):<br>
         * 1.1=Open – Known<br>
         * 1.2=Open – Unknown<br>
         * 1.3=Open with brand<br>
         * 2.1=Closed<br>
         * 2.2=Closed with brand<br>
         * 3.0=Strictly commercial<br>
         * 4.1=New – Known<br>
         * 4.2=New – Unknown */
        @Nullable @XmlElement(name="ITL_R_Type")
        private String recipeType;
        /** Ingredient Type only for ingredient or sub-recipe (for TYPE=5 or 3S or 5S):<br>
         * 1 Fixed<br>
         * 2 Substitutable<br>
         * 3 Fat during cooking<br>
         * A2 Fat used Facet<br>
         * A3 Milk/liquid used */
        @Nullable @XmlElement(name="ITL_I_Type")
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
        private BigDecimal consumedQuantity;
        /** Estimated quantity (before having applied conversion factors). (recomputed value as docs state)*/
        @XmlElement(name="ITL_Estim_QTY")
        private BigDecimal estimatedQuantity;

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
        private BigDecimal quantityAmount;
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
        private int estimatedRawOrCookedOrNotApplicable;
        /** Variable indicating whether the quantity was Consumed Raw or Cooked:<br>
         * 1 = Raw<br>
         * 2 = Cooked or Not applicable*/
        @XmlElement(name="ITL_ConsRawCo")
        private int consumedRawOrCookedOrNotApplicable;
        /** Raw to Cooked Coefficient*/
        @XmlElement(name="ITL_Conver")
        private BigDecimal rawPerCookedRatio;

        /** Variable indicating whether the quantity was estimated with/without inedible part:<br>
         * 1 = Without or not applicable<br>
         * 2 = With*/
        @XmlElement(name="ITL_EDIB")
        private int ITL_EDIB;

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
            private BigDecimal value;
        }
        @XmlElementWrapper(name="ListeITV_Nutriments")
        @XmlElement(name="ITV_Nutriment", type=Nutrient.class)
        private List<Nutrient> nutrients;

        @XmlElementWrapper(name="ListeNotes")
        @XmlElement(name="Note", type=Note.class)
        private List<Note> notes;

        // -- UTILITY

        @Getter @Accessors(fluent=true)
        @RequiredArgsConstructor
        enum ListEntryType {
            FoodConsumptionOccasion("1"),
            QuickListItem("2"),
            QuickListItemForDietarySupplement("2S"),
            Recipe("3"),
            //RecipeSelectedAsARecipeIngredient("3S"), //Not yet available (as stated in docs)
            Food("4"),
            FoodSelectedAsARecipeIngredient("5"),

            /**
             * When a food is consumed “fried” or an ingredient “deep fried”,
             * fat(s) during cooking is asked and their quantities are automatically computed.
             * <p>
             * Shares the QLINUM with the preceding {@link ListEntryType#Food}.
             */
            FatDuringCookingForFood("6"),
            /**
             * When a food is consumed “fried” or an ingredient “deep fried”,
             * fat(s) during cooking is asked and their quantities are automatically computed.
             * <p>
             * Shares the QLINUM and ING_NUM with the preceding {@link ListEntryType#Food}.
             */
            FatDuringCookingForIngredient("7"),

            /**
             * Type of Fat used (informal).
             * <p>
             * For some foods GloboDiet is asking on qualitative information on its fat(s) or liquid(s) components.
             * As there are qualitative information, theses fat(s) and liquid(s) foods don’t have attached consumed
             * quantity (=0g).
             * <p>
             * Shares the QLINUM with the preceding {@link ListEntryType#Food}.
             */
            TypeOfFatUsedFacet("A2"),
            /**
             * Type of milk/liquid used (informal).
             * <p>
             * For some foods GloboDiet is asking on qualitative information on its fat(s) or liquid(s) components.
             * As there are qualitative information, theses fat(s) and liquid(s) foods don’t have attached consumed
             * quantity (=0g).
             * <p>
             * Shares the QLINUM with the preceding {@link ListEntryType#Food}.
             */
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

        ListEntryType listEntryType() {
            return ListEntryType.parse(listEntryType);
        }
    }

}
