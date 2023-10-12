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
package dita.globodiet.manager.dashboard;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.DependentDefaultsPolicy;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.commons.services.rules.RuleChecker;
import dita.commons.services.rules.RuleChecker.RuleViolation;
import dita.globodiet.dom.params.DitaModuleGdParams;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Action
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_ruleChecker {

    @Autowired(required = false) List<RuleChecker> checkers;

    final Dashboard dashboard;

    public enum GroupBy {
        CHECKER,
        ENTITY
    }

    @MemberSupport
    public AsciiDoc act(
            @Parameter
            @ParameterLayout(
                    describedAs = "Select which Rule Checkers to run")
            final Can<String> checkers,

            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES)
            @ParameterLayout(
                    describedAs = """
                            The report can be grouped by
                            individual Rule Checker or
                            individual Entity""")
            final GroupBy groupBy) {

        val checkersSorted = checkersSorted(checkers);

        val adoc = new AsciiDocBuilder();
        adoc.append(doc->{
            doc.setTitle("Rule Checker Report");
            AsciiDocFactory.block(doc, "Selected Checkers:");
            val list = AsciiDocFactory.list(doc);
            checkersSorted.forEach(checker->{
                AsciiDocFactory.listItem(list, String.format("%s: %s",
                        checker.title(),
                        checker.description()));
            });
        });

        switch (groupBy) {
        case CHECKER: {
            checkersSorted.forEach(checker->{
                adoc.append(doc->{

                    var checkerSection = AsciiDocFactory.section(doc, "Checker: " + checker.title());
                    AsciiDocFactory.block(checkerSection, checker.description());

                    streamEntityClasses().forEach(entityClass->{
                        var violations = checker.check(entityClass);
                        if(violations.isEmpty()) return;

                        val sourceBlock = AsciiDocFactory.sourceBlock(checkerSection, "yaml",
                                violations.stream()
                                .map(RuleViolation::formatAsYaml)
                                .collect(Collectors.joining("\n")));
                        sourceBlock.setTitle("Entity: " + entityClass.getSimpleName());

                    });

                });
            });
            break;
        }
        case ENTITY: {
            streamEntityClasses().forEach(entityClass->{
                adoc.append(doc->{

                    var entitySection = AsciiDocFactory.section(doc, "Entity: " + entityClass.getSimpleName());

                    checkersSorted.forEach(checker->{
                        var violations = checker.check(entityClass);
                        if(violations.isEmpty()) return;

                        val sourceBlock = AsciiDocFactory.sourceBlock(entitySection, "yaml",
                                violations.stream()
                                .map(RuleViolation::formatAsYaml)
                                .collect(Collectors.joining("\n")));
                        sourceBlock.setTitle(String.format("Checker: %s (%s)",
                                checker.title(),
                                checker.description()));

                    });

                });
            });
            break;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + groupBy);
        }
        return adoc.buildAsValue();
    }

    @MemberSupport
    public Can<String> defaultCheckers() {
        return choicesCheckers();
    }
    @MemberSupport
    public Can<String> choicesCheckers() {
        return checkersSorted().map(RuleChecker::title);
    }

    @MemberSupport
    public GroupBy defaultGroupBy() {
        return GroupBy.CHECKER;
    }

    @MemberSupport
    public String disableAct() {
        return _NullSafe.isEmpty(checkers)
                ? "No Rule Checkers available."
                : null;
    }

    // -- HELPER

    private Can<RuleChecker> checkersSorted() {
        return checkersSorted(checkers);
    }

    private Can<RuleChecker> checkersSorted(final Can<String> checkerNames) {
        return checkersSorted(checkers)
                .filter(checker->checkerNames.contains(checker.title()));
    }

    private static Can<RuleChecker> checkersSorted(final Iterable<RuleChecker> unsorted) {
        return _NullSafe.stream(unsorted)
                .sorted((a, b)->a.title().compareTo(b.title()))
                .collect(Can.toCan());
    }

    private static Stream<Class<?>> streamEntityClasses() {
        return _NullSafe.stream(DitaModuleGdParams.entityClasses());
    }

}
