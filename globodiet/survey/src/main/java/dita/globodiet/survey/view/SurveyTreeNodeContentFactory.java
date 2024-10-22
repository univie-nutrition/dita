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

import java.util.stream.Collectors;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.TextUtils;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.experimental.UtilityClass;

import dita.commons.format.FormatUtils;
import dita.commons.types.Message;
import dita.globodiet.survey.dom.Campaign;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Respondent24;

@UtilityClass
public class SurveyTreeNodeContentFactory {

    // -- TITLE

    String title(final InterviewSet24 interviewSet, final Campaign campaign) {
        return "Campaign - " + campaign.title();
    }

    String title(final Respondent24 respondent, Can<Message> messages) {
        final String interviewCountIndicator = switch (respondent.interviewCount()) {
            case 0 -> "⓿";
            case 1 -> "❶";
            case 2 -> "❷";
            case 3 -> "❸";
            case 4 -> "❹";
            default -> "(" + respondent.interviewCount() + ")";
        };
        return String.format("%s %s", respondent.alias(), interviewCountIndicator);
    }

    String title(final Interview24 interview) {
        return String.format("%s #%s %s",
                interview.respondentAlias(),
                interview.interviewOrdinal(),
                interview.interviewDate());
    }

    String title(final Meal24 meal) {
        return String.format("Meal at %s",
                meal.hourOfDay());
    }

    String title(final MemorizedFood24 mem) {
        return String.format("Memorized Food '%s'",
                mem.name());
    }

    String title(final Record24 rec) {
        return String.format("Record %s '%s'",
                rec.getClass().getSimpleName(),
                rec.name());
    }

    // -- ICON

    String icon(final InterviewSet24 interviewSet) {
        return "solid users-viewfinder";
    }

    String icon(final Respondent24 respondent, Can<Message> messages) {
        return DataUtil.findHighestMessageSeverityForRespondent(respondent, messages)
            .map(severity->"solid user, solid triangle-exclamation .ov-size-80 .ov-right-55 .ov-bottom-55 .respondent-"
                    + severity.name().toLowerCase())
            .orElse("solid user");
    }

    String icon(final Interview24 interview) {
        return "solid person-circle-question";
    }

    String icon(final Meal24 meal) {
        return "solid mug-hot, regular clock .ov-size-80 .ov-right-55 .ov-bottom-55";
    }

    // -- CONTENT

    AsciiDoc content(final InterviewSet24 interviewSet, final Campaign campaign) {
        record Details(int respondentCount, int interviewCount, Can<Message> messages) {
            static Details of(final InterviewSet24 interviewSet) {
                return new Details(
                        interviewSet.respondents().size(),
                        interviewSet.interviewCount(),
                        DataUtil.messages(interviewSet));
            }
        }
        return adoc("Details", Details.of(interviewSet));
    }

    AsciiDoc content(final Respondent24 respondent) {
        return adoc("Details", respondent);
    }

    AsciiDoc content(final Interview24 interview) {
        return adoc("Details", interview);
    }

    AsciiDoc content(final Meal24 meal) {
        return adoc("Details", meal);
    }

    AsciiDoc content(final MemorizedFood24 mem) {
        return adoc("Details", mem);
    }

    AsciiDoc content(final Record24 rec) {
        return adoc("Details", rec);
    }

    // -- HELPER

    private static AsciiDoc adoc(
            //final String title,
            final String yamlBlockLabel,
            final Object details) {

        var yaml = YamlUtils.toStringUtf8(details, FormatUtils.yamlOptions());

        return FormatUtils.adocSourceBlockWithTile(yamlBlockLabel, "yaml",
                TextUtils.readLines(yaml)
                    .stream()
                    .filter(line->
                        !line.contains("typeOfFatUsedDuringCooking: null")
                        && !line.contains("typeOfMilkOrLiquidUsedDuringCooking: null"))
                    .collect(Collectors.joining("\n"))
                );
    }

}
