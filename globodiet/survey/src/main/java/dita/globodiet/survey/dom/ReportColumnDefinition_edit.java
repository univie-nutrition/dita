/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.survey.dom;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;

import lombok.RequiredArgsConstructor;

@Action
@ActionLayout(
        sequence = "1",
        associateWith = "listingView",
        describedAs = "Edit the columns "
                + "which the interview reporting should include.",
        cssClassFa = "solid pencil",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
public class ReportColumnDefinition_edit {

    private final ReportColumnDefinition mixee;

    @MemberSupport
    public ReportColumnDefinition act(
            @Parameter
            @ParameterLayout(
                    describedAs = "Line by line defines a column to include.\n"
                                    + "(lines can be commented out with a leading #)",
                    multiLine = 24
            )
            final String listing) {

        return mixee;
    }

    @MemberSupport
    public String defaultListing() {
        return mixee.getColumnListing();
    }

}
