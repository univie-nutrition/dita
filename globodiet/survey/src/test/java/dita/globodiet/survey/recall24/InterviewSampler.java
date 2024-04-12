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

import io.github.causewaystuff.blobstore.applib.BlobStore;

record InterviewSampler(BlobStore blobStore) {

    static String sampleXml() {
        return
"""
﻿<?xml version="1.0" encoding="utf-8"?>
<ITV>
    <Param>
        <PAR_ExportDate>2022-04-23T09:27:30.4995651+02:00</PAR_ExportDate>
        <Project PRO_Code="AUT-FCS_2014">
            <PRO_Language>de-AT</PRO_Language>
        </Project>
        <Country CTY_code="AT_" />
    </Param>
    <ListeInterviews>
        <Interview>
            <ITV_Id p4:nil="true" xmlns:p4="http://www.w3.org/2001/XMLSchema-instance" />
            <ITV_RecallDate>2021-04-06T00:00:00</ITV_RecallDate>
            <CTR_Code>001</CTR_Code>
            <ITR_Code>00000003</ITR_Code>
            <ITR_CTY_Code>AT_</ITR_CTY_Code>
            <ITR_CTR_Code>001</ITR_CTR_Code>
            <SBT_Code>01_01_SL_2</SBT_Code>
            <SBT_Name>Svenson</SBT_Name>
            <SBT_FirstName>Sven</SBT_FirstName>
            <SBT_Sex>2</SBT_Sex>
            <SBT_BirthDate>1933-03-17T00:00:00</SBT_BirthDate>
            <ITG_Release>2004-07-01T00:00:00</ITG_Release>
            <ITG_Num>2</ITG_Num>
            <ITG_Date>2021-05-02T00:00:00</ITG_Date>
            <ITG_SubjectHeight>160</ITG_SubjectHeight>
            <ITG_SubjectWeight>54.900001525878906</ITG_SubjectWeight>
            <ITG_VURecall>06:00</ITG_VURecall>
            <ITG_VUNextRecall>07:30</ITG_VUNextRecall>
            <ITG_EnergyRequirement>2001.4354248046875</ITG_EnergyRequirement>
            <ITG_EnergyCalculated>768</ITG_EnergyCalculated>
            <ITG_TimeGI>00:23:02</ITG_TimeGI>
            <ITG_TimeQL>00:04:36</ITG_TimeQL>
            <ITG_TimeDQ>00:18:42</ITG_TimeDQ>
            <ITG_TimeDS>00:00:00</ITG_TimeDS>
            <ITG_TimeALL>00:49:56</ITG_TimeALL>
            <ITG_RecomputeDate>2022-04-23T09:27:24</ITG_RecomputeDate>
            <ITG_SPD_code>00</ITG_SPD_code>
            <ITG_SPT_code>99</ITG_SPT_code>
            <ITG_Version>1</ITG_Version>
            <ListeNotes>
                <Note>
                    <NOT_Status>02</NOT_Status>
                    <NOT_Memo>Hello World!</NOT_Memo>
                    <NOT_Date>2023-01-23T09:15:20</NOT_Date>
                    <NOT_Type>G</NOT_Type>
                    <NOT_QuantityConsumed>0</NOT_QuantityConsumed>
                    <NOT_MaxVal>false</NOT_MaxVal>
                    <NOT_EmptyQLI>false</NOT_EmptyQLI>
                </Note>
                <Note>
                    <NOT_Status>00</NOT_Status>
                    <NOT_Memo>Größtenteils Schonkost</NOT_Memo>
                    <NOT_Date>2011-02-07T09:48:51</NOT_Date>
                    <NOT_Type>G_DT</NOT_Type>
                    <NOT_QuantityConsumed>0</NOT_QuantityConsumed>
                    <NOT_MaxVal>false</NOT_MaxVal>
                    <NOT_EmptyQLI>false</NOT_EmptyQLI>
                </Note>
            </ListeNotes>
            <ListeLignesITV>
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>01</ITL_FCO_Code>
                    <ITL_FCOHour> 0700</ITL_FCOHour>
                    <ITL_TOK>0</ITL_TOK>
                    <ITL_QLINUM>0</ITL_QLINUM>
                    <ITL_ING_NUM>0</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>1</ITL_Type>
                    <ITL_Text>VOR DEM FRÜHSTÜCK (NACH DEM AUFSTE ------------------------- 07h00 , Zu Hause</ITL_Text>
                    <ITL_Estim_QTY>0</ITL_Estim_QTY>
                    <ITL_NGRAMS>0</ITL_NGRAMS>
                    <ITL_Conver>0</ITL_Conver>
                    <ITL_EDIB_CSTE>0</ITL_EDIB_CSTE>
                    <ITL_DENSITY>0</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>0</ITL_FAT_PCT>
                    <ITL_PCT_CONS>0</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>0</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>0</ITL_CONS_QTY>
                    <ITL_R_COOKED>0</ITL_R_COOKED>
                    <ITL_R_EDIB>0</ITL_R_EDIB>
                    <ITL_RAW_Q>0</ITL_RAW_Q>
                    <ITL_RAW_Q_W>0</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>0</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments />
                    <ListeNotes />
                </LigneITV>
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>01</ITL_FCO_Code>
                    <ITL_FCOHour> 0700</ITL_FCOHour>
                    <ITL_TOK>1</ITL_TOK>
                    <ITL_QLINUM>0</ITL_QLINUM>
                    <ITL_ING_NUM>0</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>2</ITL_Type>
                    <ITL_Text>Kaffee</ITL_Text>
                    <ITL_Estim_QTY>0</ITL_Estim_QTY>
                    <ITL_NGRAMS>0</ITL_NGRAMS>
                    <ITL_Conver>0</ITL_Conver>
                    <ITL_EDIB_CSTE>0</ITL_EDIB_CSTE>
                    <ITL_DENSITY>0</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>0</ITL_FAT_PCT>
                    <ITL_PCT_CONS>0</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>0</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>0</ITL_CONS_QTY>
                    <ITL_R_COOKED>0</ITL_R_COOKED>
                    <ITL_R_EDIB>0</ITL_R_EDIB>
                    <ITL_RAW_Q>0</ITL_RAW_Q>
                    <ITL_RAW_Q_W>0</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>0</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments />
                    <ListeNotes />
                </LigneITV>
                """
                +
                recipeWithIngredients()
                +
                """
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>01</ITL_FCO_Code>
                    <ITL_FCOHour> 0700</ITL_FCOHour>
                    <ITL_TOK>1</ITL_TOK>
                    <ITL_QLINUM>1</ITL_QLINUM>
                    <ITL_ING_NUM>0</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>4</ITL_Type>
                    <ITL_FoodNum>01339</ITL_FoodNum>
                    <ITL_Group>13</ITL_Group>
                    <ITL_SubGroup1>03</ITL_SubGroup1>
                    <ITL_SubGroup2>01</ITL_SubGroup2>
                    <ITL_DS_Classif />
                    <ITL_F_Type />
                    <ITL_I_Type />
                    <ITL_R_Modif>0</ITL_R_Modif>
                    <ITL_I_Modif>0</ITL_I_Modif>
                    <ITL_R_Type />
                    <ITL_Text>mit Zucker</ITL_Text>
                    <ITL_Name>Kaffee, mit Koffein</ITL_Name>
                    <ITL_Facets_STR>0699,0201,1200,0702</ITL_Facets_STR>
                    <ITL_BrandName />
                    <ITL_Status>1</ITL_Status>
                    <ITL_Estim_QTY>125</ITL_Estim_QTY>
                    <ITL_Q_Method>H</ITL_Q_Method>
                    <ITL_QM_Code>H129</ITL_QM_Code>
                    <ITL_QUNIT>V</ITL_QUNIT>
                    <ITL_NGRAMS>250</ITL_NGRAMS>
                    <ITL_Proport>1/2</ITL_Proport>
                    <ITL_HHMFract>1</ITL_HHMFract>
                    <ITL_R_Fract>1</ITL_R_Fract>
                    <ITL_RawCooked>2</ITL_RawCooked>
                    <ITL_ConsRawCo>2</ITL_ConsRawCo>
                    <ITL_Conver>1</ITL_Conver>
                    <ITL_EDIB>1</ITL_EDIB>
                    <ITL_EDIB_CSTE>1</ITL_EDIB_CSTE>
                    <ITL_DENSITY>1</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>1</ITL_FAT_PCT>
                    <ITL_PCT_CONS>0</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>0</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>125</ITL_CONS_QTY>
                    <ITL_R_COOKED>1</ITL_R_COOKED>
                    <ITL_R_EDIB>1</ITL_R_EDIB>
                    <ITL_RAW_Q>125</ITL_RAW_Q>
                    <ITL_RAW_Q_W>125</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>1608</ITL_ITEM_SEQ>
                    <ListeITV_Quantity>
                        <ITV_Quantity_Service>
                            <ITQ_Order>1</ITQ_Order>
                            <ITQ_SH_Surface>0</ITQ_SH_Surface>
                            <ITQ_TH_Thick>0</ITQ_TH_Thick>
                            <ITQ_PH_Code>1</ITQ_PH_Code>
                            <ITQ_PH_QTY>11</ITQ_PH_QTY>
                            <ITQ_FRACT>1</ITQ_FRACT>
                        </ITV_Quantity_Service>
                    </ListeITV_Quantity>
                    <ListeITV_Nutriments>
                        <ITV_Nutriment>
                            <NTR_Code>4</NTR_Code>
                            <ITV_NTR_Value>3.9600000381469727</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>3</NTR_Code>
                            <ITV_NTR_Value>6.1599998474121094</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>2</NTR_Code>
                            <ITV_NTR_Value>0.43999999761581421</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>1</NTR_Code>
                            <ITV_NTR_Value>61.599998474121094</ITV_NTR_Value>
                        </ITV_Nutriment>
                    </ListeITV_Nutriments>
                    <ListeNotes />
                </LigneITV>
            </ListeLignesITV>
        </Interview>
    </ListeInterviews>
</ITV>
""";
    }

    private static String recipeWithIngredients() {
        return """
                            <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>02</ITL_FCO_Code>
                    <ITL_FCOHour> 0630</ITL_FCOHour>
                    <ITL_TOK>3</ITL_TOK>
                    <ITL_QLINUM>1</ITL_QLINUM>
                    <ITL_ING_NUM>0</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>3</ITL_Type>
                    <ITL_FoodNum>00232</ITL_FoodNum>
                    <ITL_Group>03</ITL_Group>
                    <ITL_SubGroup1 />
                    <ITL_SubGroup2 />
                    <ITL_DS_Classif />
                    <ITL_F_Type />
                    <ITL_I_Type />
                    <ITL_R_Modif>3</ITL_R_Modif>
                    <ITL_I_Modif>0</ITL_I_Modif>
                    <ITL_R_Type>1.2</ITL_R_Type>
                    <ITL_Text>Supermarkt (industriell), gekühlt bei 5-8°C, Plastikgefäss (z.B. PET-Flasche)</ITL_Text>
                    <ITL_Name>Eieraufstrich</ITL_Name>
                    <ITL_Facets_STR>0103,0200,0317,0405</ITL_Facets_STR>
                    <ITL_BrandName>Marke / Produktname unbekannt</ITL_BrandName>
                    <ITL_Status>1</ITL_Status>
                    <ITL_Estim_QTY>25</ITL_Estim_QTY>
                    <ITL_Q_Method>G</ITL_Q_Method>
                    <ITL_QM_Code />
                    <ITL_QUNIT>G</ITL_QUNIT>
                    <ITL_NGRAMS>25</ITL_NGRAMS>
                    <ITL_Proport>1</ITL_Proport>
                    <ITL_HHMFract>1</ITL_HHMFract>
                    <ITL_R_Fract>1</ITL_R_Fract>
                    <ITL_RawCooked>2</ITL_RawCooked>
                    <ITL_ConsRawCo>2</ITL_ConsRawCo>
                    <ITL_Conver>1</ITL_Conver>
                    <ITL_EDIB>1</ITL_EDIB>
                    <ITL_EDIB_CSTE>1</ITL_EDIB_CSTE>
                    <ITL_DENSITY>1</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>1</ITL_FAT_PCT>
                    <ITL_PCT_CONS>0</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>0</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>25</ITL_CONS_QTY>
                    <ITL_R_COOKED>-1</ITL_R_COOKED>
                    <ITL_R_EDIB>-1</ITL_R_EDIB>
                    <ITL_RAW_Q>-1</ITL_RAW_Q>
                    <ITL_RAW_Q_W>-1</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>0</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments />
                    <ListeNotes />
                </LigneITV>
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>02</ITL_FCO_Code>
                    <ITL_FCOHour> 0630</ITL_FCOHour>
                    <ITL_TOK>3</ITL_TOK>
                    <ITL_QLINUM>1</ITL_QLINUM>
                    <ITL_ING_NUM>1</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>5</ITL_Type>
                    <ITL_FoodNum>01529</ITL_FoodNum>
                    <ITL_Group>15</ITL_Group>
                    <ITL_SubGroup1>01</ITL_SubGroup1>
                    <ITL_SubGroup2>02</ITL_SubGroup2>
                    <ITL_DS_Classif />
                    <ITL_F_Type />
                    <ITL_I_Type>2</ITL_I_Type>
                    <ITL_R_Modif>0</ITL_R_Modif>
                    <ITL_I_Modif>0</ITL_I_Modif>
                    <ITL_R_Type />
                    <ITL_Text>vollfett/normal</ITL_Text>
                    <ITL_Name>Mayonnaise</ITL_Name>
                    <ITL_Facets_STR>0699,0400,1000,0801</ITL_Facets_STR>
                    <ITL_BrandName />
                    <ITL_Status>1</ITL_Status>
                    <ITL_Estim_QTY>72</ITL_Estim_QTY>
                    <ITL_Q_Method>G</ITL_Q_Method>
                    <ITL_QM_Code />
                    <ITL_QUNIT>G</ITL_QUNIT>
                    <ITL_NGRAMS>72</ITL_NGRAMS>
                    <ITL_Proport>1</ITL_Proport>
                    <ITL_HHMFract>1</ITL_HHMFract>
                    <ITL_R_Fract>1</ITL_R_Fract>
                    <ITL_RawCooked>2</ITL_RawCooked>
                    <ITL_ConsRawCo>2</ITL_ConsRawCo>
                    <ITL_Conver>1</ITL_Conver>
                    <ITL_EDIB>1</ITL_EDIB>
                    <ITL_EDIB_CSTE>1</ITL_EDIB_CSTE>
                    <ITL_DENSITY>1</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>1</ITL_FAT_PCT>
                    <ITL_PCT_CONS>26.966289520263672</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>26.966291427612305</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>6.7415728569030762</ITL_CONS_QTY>
                    <ITL_R_COOKED>1</ITL_R_COOKED>
                    <ITL_R_EDIB>1</ITL_R_EDIB>
                    <ITL_RAW_Q>6.7415728569030762</ITL_RAW_Q>
                    <ITL_RAW_Q_W>6.7415728569030762</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>1751</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments>
                        <ITV_Nutriment>
                            <NTR_Code>4</NTR_Code>
                            <ITV_NTR_Value>5.2584266662597656</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>3</NTR_Code>
                            <ITV_NTR_Value>0.067415729165077209</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>2</NTR_Code>
                            <ITV_NTR_Value>0.067415729165077209</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>1</NTR_Code>
                            <ITV_NTR_Value>47.191009521484375</ITV_NTR_Value>
                        </ITV_Nutriment>
                    </ListeITV_Nutriments>
                    <ListeNotes />
                </LigneITV>
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>02</ITL_FCO_Code>
                    <ITL_FCOHour> 0630</ITL_FCOHour>
                    <ITL_TOK>3</ITL_TOK>
                    <ITL_QLINUM>1</ITL_QLINUM>
                    <ITL_ING_NUM>2</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>5</ITL_Type>
                    <ITL_FoodNum>00916</ITL_FoodNum>
                    <ITL_Group>09</ITL_Group>
                    <ITL_SubGroup1>01</ITL_SubGroup1>
                    <ITL_SubGroup2 />
                    <ITL_DS_Classif />
                    <ITL_F_Type />
                    <ITL_I_Type>1</ITL_I_Type>
                    <ITL_R_Modif>0</ITL_R_Modif>
                    <ITL_I_Modif>0</ITL_I_Modif>
                    <ITL_R_Type />
                    <ITL_Text>Huhn, gekocht in Wasser</ITL_Text>
                    <ITL_Name>Ei</ITL_Name>
                    <ITL_Facets_STR>0107,0303</ITL_Facets_STR>
                    <ITL_BrandName />
                    <ITL_Status>1</ITL_Status>
                    <ITL_Estim_QTY>180</ITL_Estim_QTY>
                    <ITL_Q_Method>U</ITL_Q_Method>
                    <ITL_QM_Code>0001</ITL_QM_Code>
                    <ITL_QUNIT>G</ITL_QUNIT>
                    <ITL_NGRAMS>60</ITL_NGRAMS>
                    <ITL_Proport>3</ITL_Proport>
                    <ITL_HHMFract>1</ITL_HHMFract>
                    <ITL_R_Fract>1</ITL_R_Fract>
                    <ITL_RawCooked>1</ITL_RawCooked>
                    <ITL_ConsRawCo>2</ITL_ConsRawCo>
                    <ITL_Conver>1</ITL_Conver>
                    <ITL_EDIB>1</ITL_EDIB>
                    <ITL_EDIB_CSTE>1</ITL_EDIB_CSTE>
                    <ITL_DENSITY>1</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>1</ITL_FAT_PCT>
                    <ITL_PCT_CONS>67.415725708007812</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>67.415733337402344</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>16.853933334350586</ITL_CONS_QTY>
                    <ITL_R_COOKED>1</ITL_R_COOKED>
                    <ITL_R_EDIB>0.87999999523162842</ITL_R_EDIB>
                    <ITL_RAW_Q>16.853933334350586</ITL_RAW_Q>
                    <ITL_RAW_Q_W>19.152196884155273</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>981</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments>
                        <ITV_Nutriment>
                            <NTR_Code>4</NTR_Code>
                            <ITV_NTR_Value>1.8539326190948486</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>2</NTR_Code>
                            <ITV_NTR_Value>2.1910114288330078</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>1</NTR_Code>
                            <ITV_NTR_Value>26.12359619140625</ITV_NTR_Value>
                        </ITV_Nutriment>
                    </ListeITV_Nutriments>
                    <ListeNotes />
                </LigneITV>
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>02</ITL_FCO_Code>
                    <ITL_FCOHour> 0630</ITL_FCOHour>
                    <ITL_TOK>3</ITL_TOK>
                    <ITL_QLINUM>1</ITL_QLINUM>
                    <ITL_ING_NUM>3</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>5</ITL_Type>
                    <ITL_FoodNum>01547</ITL_FoodNum>
                    <ITL_Group>15</ITL_Group>
                    <ITL_SubGroup1>03</ITL_SubGroup1>
                    <ITL_SubGroup2 />
                    <ITL_DS_Classif />
                    <ITL_F_Type />
                    <ITL_I_Type>2</ITL_I_Type>
                    <ITL_R_Modif>0</ITL_R_Modif>
                    <ITL_I_Modif>0</ITL_I_Modif>
                    <ITL_R_Type />
                    <ITL_Text>gehackt, frisch</ITL_Text>
                    <ITL_Name>Schnittlauch</ITL_Name>
                    <ITL_Facets_STR>0205,0499</ITL_Facets_STR>
                    <ITL_BrandName />
                    <ITL_Status>1</ITL_Status>
                    <ITL_Estim_QTY>10</ITL_Estim_QTY>
                    <ITL_Q_Method>G</ITL_Q_Method>
                    <ITL_QM_Code />
                    <ITL_QUNIT>G</ITL_QUNIT>
                    <ITL_NGRAMS>10</ITL_NGRAMS>
                    <ITL_Proport>1</ITL_Proport>
                    <ITL_HHMFract>1</ITL_HHMFract>
                    <ITL_R_Fract>1</ITL_R_Fract>
                    <ITL_RawCooked>2</ITL_RawCooked>
                    <ITL_ConsRawCo>2</ITL_ConsRawCo>
                    <ITL_Conver>1</ITL_Conver>
                    <ITL_EDIB>1</ITL_EDIB>
                    <ITL_EDIB_CSTE>1</ITL_EDIB_CSTE>
                    <ITL_DENSITY>1</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>1</ITL_FAT_PCT>
                    <ITL_PCT_CONS>3.7453181743621826</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>3.7453184127807617</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>0.93632960319519043</ITL_CONS_QTY>
                    <ITL_R_COOKED>1</ITL_R_COOKED>
                    <ITL_R_EDIB>1</ITL_R_EDIB>
                    <ITL_RAW_Q>0.93632960319519043</ITL_RAW_Q>
                    <ITL_RAW_Q_W>0.93632960319519043</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>1902</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments>
                        <ITV_Nutriment>
                            <NTR_Code>4</NTR_Code>
                            <ITV_NTR_Value>0.0093632964417338371</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>3</NTR_Code>
                            <ITV_NTR_Value>0.018726592883467674</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>2</NTR_Code>
                            <ITV_NTR_Value>0.028089888393878937</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>1</NTR_Code>
                            <ITV_NTR_Value>0.23408240079879761</ITV_NTR_Value>
                        </ITV_Nutriment>
                    </ListeITV_Nutriments>
                    <ListeNotes />
                </LigneITV>
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>02</ITL_FCO_Code>
                    <ITL_FCOHour> 0630</ITL_FCOHour>
                    <ITL_TOK>3</ITL_TOK>
                    <ITL_QLINUM>1</ITL_QLINUM>
                    <ITL_ING_NUM>4</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>5</ITL_Type>
                    <ITL_FoodNum>00103</ITL_FoodNum>
                    <ITL_Group>02</ITL_Group>
                    <ITL_SubGroup1>07</ITL_SubGroup1>
                    <ITL_SubGroup2 />
                    <ITL_DS_Classif />
                    <ITL_F_Type />
                    <ITL_I_Type>2</ITL_I_Type>
                    <ITL_R_Modif>0</ITL_R_Modif>
                    <ITL_I_Modif>0</ITL_I_Modif>
                    <ITL_R_Type />
                    <ITL_Text>gehackt, frisch, roh/unverarbeitet</ITL_Text>
                    <ITL_Name>Knoblauch</ITL_Name>
                    <ITL_Facets_STR>0205,0499,0999,1200,0399,2800</ITL_Facets_STR>
                    <ITL_BrandName />
                    <ITL_Status>1</ITL_Status>
                    <ITL_Estim_QTY>4</ITL_Estim_QTY>
                    <ITL_Q_Method>G</ITL_Q_Method>
                    <ITL_QM_Code />
                    <ITL_QUNIT>G</ITL_QUNIT>
                    <ITL_NGRAMS>4</ITL_NGRAMS>
                    <ITL_Proport>1</ITL_Proport>
                    <ITL_HHMFract>1</ITL_HHMFract>
                    <ITL_R_Fract>1</ITL_R_Fract>
                    <ITL_RawCooked>2</ITL_RawCooked>
                    <ITL_ConsRawCo>1</ITL_ConsRawCo>
                    <ITL_Conver>1</ITL_Conver>
                    <ITL_EDIB>1</ITL_EDIB>
                    <ITL_EDIB_CSTE>1</ITL_EDIB_CSTE>
                    <ITL_DENSITY>1</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>1</ITL_FAT_PCT>
                    <ITL_PCT_CONS>1.4981272220611572</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>1.4981273412704468</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>0.37453183531761169</ITL_CONS_QTY>
                    <ITL_R_COOKED>1</ITL_R_COOKED>
                    <ITL_R_EDIB>0.85000002384185791</ITL_R_EDIB>
                    <ITL_RAW_Q>0.37453183531761169</ITL_RAW_Q>
                    <ITL_RAW_Q_W>0.44062566757202148</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>152</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments>
                        <ITV_Nutriment>
                            <NTR_Code>4</NTR_Code>
                            <ITV_NTR_Value>0.00374531839042902</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>3</NTR_Code>
                            <ITV_NTR_Value>0.093632958829402924</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>2</NTR_Code>
                            <ITV_NTR_Value>0.026217227801680565</ITV_NTR_Value>
                        </ITV_Nutriment>
                        <ITV_Nutriment>
                            <NTR_Code>1</NTR_Code>
                            <ITV_NTR_Value>0.48689138889312744</ITV_NTR_Value>
                        </ITV_Nutriment>
                    </ListeITV_Nutriments>
                    <ListeNotes />
                </LigneITV>
                <LigneITV>
                    <ITL_POC_Code>01</ITL_POC_Code>
                    <ITL_FCO_Code>02</ITL_FCO_Code>
                    <ITL_FCOHour> 0630</ITL_FCOHour>
                    <ITL_TOK>3</ITL_TOK>
                    <ITL_QLINUM>1</ITL_QLINUM>
                    <ITL_ING_NUM>5</ITL_ING_NUM>
                    <ITL_S_ING_NUM>0</ITL_S_ING_NUM>
                    <ITL_Type>5</ITL_Type>
                    <ITL_FoodNum>01571</ITL_FoodNum>
                    <ITL_Group>15</ITL_Group>
                    <ITL_SubGroup1>04</ITL_SubGroup1>
                    <ITL_SubGroup2 />
                    <ITL_DS_Classif />
                    <ITL_F_Type />
                    <ITL_I_Type>1</ITL_I_Type>
                    <ITL_R_Modif>0</ITL_R_Modif>
                    <ITL_I_Modif>0</ITL_I_Modif>
                    <ITL_R_Type />
                    <ITL_Text />
                    <ITL_Name>Salz, Koch</ITL_Name>
                    <ITL_Facets_STR />
                    <ITL_BrandName />
                    <ITL_Status>1</ITL_Status>
                    <ITL_Estim_QTY>1</ITL_Estim_QTY>
                    <ITL_Q_Method>G</ITL_Q_Method>
                    <ITL_QM_Code />
                    <ITL_QUNIT>G</ITL_QUNIT>
                    <ITL_NGRAMS>1</ITL_NGRAMS>
                    <ITL_Proport>1</ITL_Proport>
                    <ITL_HHMFract>1</ITL_HHMFract>
                    <ITL_R_Fract>1</ITL_R_Fract>
                    <ITL_RawCooked>2</ITL_RawCooked>
                    <ITL_ConsRawCo>2</ITL_ConsRawCo>
                    <ITL_Conver>1</ITL_Conver>
                    <ITL_EDIB>1</ITL_EDIB>
                    <ITL_EDIB_CSTE>1</ITL_EDIB_CSTE>
                    <ITL_DENSITY>1</ITL_DENSITY>
                    <ITL_FATLEFTO>false</ITL_FATLEFTO>
                    <ITL_FAT_PCT>1</ITL_FAT_PCT>
                    <ITL_PCT_CONS>0.37453180551528931</ITL_PCT_CONS>
                    <ITL_PCT_ESTIM>0.37453183531761169</ITL_PCT_ESTIM>
                    <ITL_CONS_QTY>0.093632958829402924</ITL_CONS_QTY>
                    <ITL_R_COOKED>1</ITL_R_COOKED>
                    <ITL_R_EDIB>1</ITL_R_EDIB>
                    <ITL_RAW_Q>0.093632958829402924</ITL_RAW_Q>
                    <ITL_RAW_Q_W>0.093632958829402924</ITL_RAW_Q_W>
                    <ITL_MAX>0</ITL_MAX>
                    <ITL_SUPPL>false</ITL_SUPPL>
                    <ITL_ITEM_SEQ>0</ITL_ITEM_SEQ>
                    <ListeITV_Quantity />
                    <ListeITV_Nutriments />
                    <ListeNotes />
                </LigneITV>
                """;
    }

}
