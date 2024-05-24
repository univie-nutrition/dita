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

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.io.JsonUtils;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import lombok.val;
import lombok.experimental.UtilityClass;

import dita.commons.jaxb.JaxbAdapters;
import dita.commons.types.Message;
import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.Campaigns;
import dita.recall24.immutable.Ingredient;
import dita.recall24.immutable.Interview;
import dita.recall24.immutable.InterviewSet;
import dita.recall24.immutable.Meal;
import dita.recall24.immutable.MemorizedFood;
import dita.recall24.immutable.RecallNode;
import dita.recall24.immutable.Record;
import dita.recall24.immutable.Respondent;

@UtilityClass
public class SurveyTreeNodeContentFactory {

    // -- TITLE

    String title(final InterviewSet interviewSet, final Campaign campaign) {
        return "Campaign - " + campaign.title();
    }

    String title(final Respondent respondent) {
        return respondent.alias();
    }

    String title(final Interview interview) {
        return String.format("%s #%s %s",
                interview.respondentAlias(),
                interview.interviewOrdinal(),
                interview.interviewDate());
    }

    String title(final Meal meal) {
        return String.format("Meal at %s",
                meal.hourOfDay());
    }

    String title(final MemorizedFood mem) {
        return String.format("Memorized Food '%s'",
                mem.name());
    }

    String title(final Record rec) {
        return String.format("Record %s '%s'",
                rec.type().name(),
                rec.name());
    }

    String title(final Ingredient ingr) {
        return String.format("Ingredient (sid=%s)",
                ingr.sid());
    }

    // -- ICON

    String icon(final InterviewSet interviewSet) {
        return "solid users-viewfinder";
    }

    String icon(final Respondent respondent) {
        return "user";
    }

    String icon(final Interview interview) {
        return "solid person-circle-question";
    }

    String icon(final Meal meal) {
        return "solid mug-hot, regular clock .ov-size-80 .ov-right-55 .ov-bottom-55";
    }

    // -- CONTENT

    AsciiDoc content(final InterviewSet interviewSet, final Campaign campaign) {
        record Details(int respondentCount, int interviewCount, Can<Message> messages) {
            static Details of(final InterviewSet interviewSet) {
                final Can<Message> messages = interviewSet.annotation(Campaigns.ANNOTATION_MESSAGES)
                        .map(RecallNode.Annotation.valueAsCan(Message.class))
                        .orElseGet(Can::empty);
                return new Details(
                        interviewSet.respondents().size(),
                        interviewSet.interviewCount(),
                        messages);
            }
        }
        return adoc("Details", Details.of(interviewSet));
    }

    AsciiDoc content(final Respondent respondent) {
        return adoc("Details", respondent);
    }

    AsciiDoc content(final Interview interview) {
        return adoc("Details", interview);
    }

    AsciiDoc content(final Meal meal) {
        return adoc("Details", meal);
    }

    AsciiDoc content(final MemorizedFood mem) {
        return adoc("Details", mem);
    }

    AsciiDoc content(final Record rec) {
        return adoc("Details", rec);
    }

    AsciiDoc content(final Ingredient ingr) {
        return adoc("Details", ingr);
    }

    // -- HELPER

    private static AsciiDoc adoc(
            //final String title,
            final String yamlBlockLabel,
            final Object details) {
        return adoc(/*title, */yamlBlockLabel, YamlUtils.toStringUtf8(details, JsonUtils.JacksonCustomizer
                .wrapXmlAdapter(new JaxbAdapters.QuantityAdapter())));
    }

    private static AsciiDoc adoc(
            //final String title,
            final String yamlBlockLabel,
            final String yaml) {
        val adoc = new AsciiDocBuilder();
        //adoc.append(doc->doc.setTitle(title));
        adoc.append(doc->{
            val sourceBlock = AsciiDocFactory.sourceBlock(doc, "yaml", yaml);
            sourceBlock.setTitle(yamlBlockLabel);
        });
        return adoc.buildAsValue();
    }

}
