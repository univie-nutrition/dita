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

import dita.blobstore.api.BlobStore;

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

}
