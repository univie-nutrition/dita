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

import org.apache.causeway.commons.io.JsonUtils;
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import lombok.val;
import lombok.experimental.UtilityClass;

import dita.commons.jaxb.JaxbAdapters;
import dita.globodiet.survey.dom.Campaign;
import dita.recall24.model.Ingredient24;
import dita.recall24.model.Interview24;
import dita.recall24.model.InterviewSet24;
import dita.recall24.model.Meal24;
import dita.recall24.model.MemorizedFood24;
import dita.recall24.model.Record24;
import dita.recall24.model.Respondent24;

@UtilityClass
public class SurveyTreeNodeContentFactory {

    // -- TITLE

    String title(final InterviewSet24 interviewSet, final Campaign campaign) {
        return "Campaign - " + campaign.title();
    }

    String title(final Respondent24 respondent) {
        return respondent.alias();
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
                rec.type().name(),
                rec.name());
    }

    String title(final Ingredient24 ingr) {
        return String.format("Ingredient (sid=%s)",
                ingr.sid());
    }

    // -- ICON

    String icon(final InterviewSet24 interviewSet) {
        return "solid users-viewfinder";
    }

    String icon(final Respondent24 respondent) {
        return "user";
    }

    String icon(final Interview24 interview) {
        return "solid person-circle-question";
    }

    String icon(final Meal24 meal) {
        return "solid mug-hot, regular clock .ov-size-80 .ov-right-55 .ov-bottom-55";
    }

    // -- CONTENT

    AsciiDoc content(final InterviewSet24 interviewSet, final Campaign campaign) {
        record Details(int respondentCount, int interviewCount) {
            static Details of(final InterviewSet24 interviewSet) {
                return new Details(
                        interviewSet.respondents().size(),
                        interviewSet.interviews().size());
            }
        }
        String title = title(interviewSet, campaign);
        return adoc(title, "Details", Details.of(interviewSet));
    }

    AsciiDoc content(final Respondent24 respondent) {
        return adoc(title(respondent), "Details", respondent);
    }

    AsciiDoc content(final Interview24 interview) {
        return adoc(title(interview), "Details", interview);
    }

    AsciiDoc content(final Meal24 meal) {
        return adoc(title(meal), "Details", meal);
    }

    AsciiDoc content(final MemorizedFood24 mem) {
        return adoc(title(mem), "Details", mem);
    }

    AsciiDoc content(final Record24 rec) {
        return adoc(title(rec), "Details", rec);
    }

    AsciiDoc content(final Ingredient24 ingr) {
        return adoc(title(ingr), "Details", ingr);
    }

    // -- HELPER

    private static AsciiDoc adoc(
            final String title,
            final String yamlBlockLabel,
            final Object details) {
        return adoc(title, yamlBlockLabel, YamlUtils.toStringUtf8(details, JsonUtils.JacksonCustomizer
                .wrapXmlAdapter(new JaxbAdapters.QuantityAdapter())));
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
