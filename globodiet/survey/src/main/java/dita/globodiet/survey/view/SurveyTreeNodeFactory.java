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

import org.apache.causeway.applib.graph.tree.TreePath;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedFunction;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import lombok.val;

import dita.globodiet.survey.dom.Campaign;
import dita.recall24.model.Interview24;
import dita.recall24.model.InterviewSet24;
import dita.recall24.model.Meal24;
import dita.recall24.model.Respondent24;

public record SurveyTreeNodeFactory(TreePath parent) {

    public static SurveyTreeNode emptyNode() {
        String title = "Campaign";
        AsciiDoc content = adoc(title, "Details", "empty");
        return new SurveyTreeNode(title, "solid users-viewfinder", content, TreePath.root(), Can.empty());
    }

    public static SurveyTreeNode surveyNode(final InterviewSet24 interviewSet, final Campaign campaign) {

        var respNodes = interviewSet.respondents().map(IndexedFunction.zeroBased((i, resp)->
                new SurveyTreeNodeFactory(TreePath.root()).respondentNode(i, resp, interviewSet.interviews()
                        .filter(intv->intv.matchesRespondent(resp)))));

        record Details(int respondentCount, int interviewCount) {
            static Details of(final InterviewSet24 interviewSet) {
                return new Details(
                        interviewSet.respondents().size(),
                        interviewSet.interviews().size());
            }
        }

        String title = "Campaign - " + campaign.title();
        AsciiDoc content = adoc(title, "Details", Details.of(interviewSet));
        return new SurveyTreeNode(title, "solid users-viewfinder", content, TreePath.root(), respNodes);
    }

    public SurveyTreeNode respondentNode(final int ordinal, final Respondent24 resp, final Can<Interview24> interviews) {

        var respPath = parent.append(ordinal);
        var interviewNodes = interviews.map(IndexedFunction.zeroBased(
                new SurveyTreeNodeFactory(respPath)::interviewNode));

        String title = resp.alias();
        AsciiDoc content = adoc(title, "Details", resp);
        return new SurveyTreeNode(title, "user", content, respPath, interviewNodes);
    }

    public SurveyTreeNode interviewNode(final int ordinal, final Interview24 interview) {
        String title = String.format("%s #%s %s",
                interview.respondentAlias(),
                interview.interviewOrdinal(),
                interview.interviewDate());
        AsciiDoc content = adoc(title, "Details", interview);

        var treePath = parent.append(ordinal);
        var mealNodes = interview.meals().map(IndexedFunction.zeroBased(
                new SurveyTreeNodeFactory(treePath)::mealNode));

        return new SurveyTreeNode(title, "solid person-circle-question", content, parent.append(ordinal), mealNodes);
    }

    public SurveyTreeNode mealNode(final int ordinal, final Meal24 meal) {
        String title = String.format("Meal at %s",
                meal.hourOfDay());
        AsciiDoc content = adoc(title, "Details", meal);
        return new SurveyTreeNode(title,
                "solid mug-hot, regular clock .ov-size-80 .ov-right-55 .ov-bottom-55",
                content, parent.append(ordinal), Can.empty());
    }

    private static AsciiDoc adoc(
            final String title,
            final String yamlBlockLabel,
            final Object details) {
        return adoc(title, yamlBlockLabel, YamlUtils.toStringUtf8(details));
    }

    private static AsciiDoc adoc(
            final String title,
            final String yamlBlockLabel,
            final String yaml) {
        val adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle(title));
        adoc.append(doc->{
            val sourceBlock = AsciiDocFactory.sourceBlock(doc, "yaml", yaml);
            sourceBlock.setTitle(yamlBlockLabel);
        });
        return adoc.buildAsValue();
    }

}
