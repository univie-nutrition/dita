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

import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.value.Clob;
import org.apache.causeway.applib.value.NamedWithMimeType.CommonMimeType;
import org.apache.causeway.commons.io.DataSink;

import lombok.RequiredArgsConstructor;

import dita.recall24.reporter.todo.TodoReporters;
import io.github.causewaystuff.blobstore.applib.BlobStore;

@Action(
        semantics = SemanticsOf.IDEMPOTENT
)
@ActionLayout(
        fieldSetId = "interviewUploads",
        sequence = "2",
        position = Position.PANEL,
        cssClass = "btn-primary",
        cssClassFa = "solid list-check",
        describedAs = "Generates a mapping todo file, that can be processed by external systems.")
@RequiredArgsConstructor
public class Campaign_downloadMappingTodos {

    @Inject @Qualifier("survey") private BlobStore surveyBlobStore;

    final Campaign mixee;

    /**
     * @see Survey_downloadMappingTodos
     */
    @MemberSupport
    public Clob act() {
        var interviewSet = Campaigns.interviewSet(mixee, surveyBlobStore);
        var nutMapping = Campaigns.nutMapping(mixee, surveyBlobStore);
        var systemId = Campaigns.systemId(mixee);

        var yaml = new StringBuilder();
        var todoReporter = new TodoReporters.TodoReporter(interviewSet, systemId, nutMapping);
        todoReporter.report(
                DataSink.ofStringConsumer(yaml, StandardCharsets.UTF_8));

        return Clob.of("mapping-todos", CommonMimeType.YAML, yaml.toString());
    }

}
