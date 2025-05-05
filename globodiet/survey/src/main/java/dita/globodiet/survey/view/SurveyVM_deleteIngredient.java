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
package dita.globodiet.survey.view;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;

import lombok.RequiredArgsConstructor;

import dita.recall24.dto.RecallNode24;

@Action
@ActionLayout(
        associateWith = "content",
        sequence = "2",
        position = Position.PANEL)
@RequiredArgsConstructor
public class SurveyVM_deleteIngredient {
    
    private final SurveyVM mixee;
    
    @MemberSupport
    public String act() {
        return "TODO (%s)".formatted(recallNode().getClass());
    }
    
    @MemberSupport
    public boolean hidden() {
        return !(recallNode() instanceof dita.recall24.dto.Record24.Food);
    }
    
    RecallNode24 recallNode() {
        return mixee.recallNode();
    }
    
}
