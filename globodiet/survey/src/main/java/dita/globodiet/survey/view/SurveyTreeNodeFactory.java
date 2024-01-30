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
import org.apache.causeway.commons.io.YamlUtils;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.recall24.model.Interview24;
import dita.recall24.model.Respondent24;
import lombok.val;

public record SurveyTreeNodeFactory(TreePath parent) {

    public SurveyTreeNode respondentNode(final int ordinal, final Respondent24 resp) {
        final String title = resp.alias();
        AsciiDoc content = adoc(title, "Details", YamlUtils.toStringUtf8(resp));
        return new SurveyTreeNode(title, "user", content, parent.append(ordinal), Can.empty());
    }

    public SurveyTreeNode interviewNode(final int ordinal, final Interview24 interview) {
        final String title = interview.respondentAlias();
        AsciiDoc content = adoc(title, "Details", YamlUtils.toStringUtf8(interview));
        return new SurveyTreeNode(title, "solid person-circle-question", content, parent.append(ordinal), Can.empty());
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
